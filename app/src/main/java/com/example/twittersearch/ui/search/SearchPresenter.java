package com.example.twittersearch.ui.search;

import android.text.TextUtils;

import androidx.appcompat.widget.SearchView;

import com.example.twittersearch.common.Constants;
import com.example.twittersearch.data.model.ApiError;
import com.example.twittersearch.data.model.Tweet;
import com.example.twittersearch.managers.RxSearchObservableManager;
import com.example.twittersearch.managers.SchedulerProviderManager;
import com.example.twittersearch.ui.base.BasePresenter;
import com.example.twittersearch.ui.search.contract.ISearchInteractor;
import com.example.twittersearch.ui.search.contract.ISearchPresenter;
import com.example.twittersearch.ui.search.contract.ISearchView;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import timber.log.Timber;

import static com.example.twittersearch.common.Constants.SEARCH_DEBOUNCE_TIMEOUT;

public class SearchPresenter<V extends ISearchView, I extends ISearchInteractor> extends BasePresenter<V, I> implements ISearchPresenter<V, I> {

    private Observable<Boolean> emptyTweetsObservable = Observable.just(true);

    @Inject
    Gson gson;

    private ApiError apiError;

    @Inject
    SearchPresenter(I baseInteractor, SchedulerProviderManager schedulerProviderManager, CompositeDisposable compositeDisposable) {
        super(baseInteractor, schedulerProviderManager, compositeDisposable);
    }

    @Override
    public void fetchTwitterToken() {
        getCompositeDisposable().add(getInteractor()
                .fetchTwitterToken(Constants.CLIENT_CREDENTIALS)
                .subscribeOn(getSchedulerProviderManager().io())
                .observeOn(getSchedulerProviderManager().ui())
                .subscribe(
                        twitterToken -> {
                            if (isViewAttached()) {
                                getView().onTokenFetched(twitterToken);
                            }
                        }, this::handleApiError
                ));
    }

    @Override
    public void fetchTweets(SearchView searchView) {
        getCompositeDisposable()
                .add(RxSearchObservableManager.fromView(searchView)
                        .debounce(SEARCH_DEBOUNCE_TIMEOUT, TimeUnit.MILLISECONDS)
                        .filter(text -> {
                            if (text.isEmpty()) {
                                onEmptyTweetsFetched();
                                return false;
                            } else {
                                return true;
                            }
                        })
                        .switchMap((Function<String, ObservableSource<List<Tweet>>>) query -> fetchTweetsFromApiQuery(searchView.getQuery().toString()))
                        .subscribe(tweets -> {
                            if (isViewAttached()) {
                                getView().onTweetsFetched(tweets);
                            }
                        }, this::handleApiError));
    }

    private Observable<List<Tweet>> fetchTweetsFromApiQuery(String query) {
        return getInteractor()
                .fetchTweetsFromApi(query)
                .subscribeOn(getSchedulerProviderManager().io())
                .observeOn(getSchedulerProviderManager().ui())
                .doOnError(this::handleApiError)
                .onErrorResumeNext(Observable.empty());
    }

    private void onEmptyTweetsFetched() {
        getCompositeDisposable().add(emptyTweetsObservable
                .subscribeOn(getSchedulerProviderManager().io())
                .observeOn(getSchedulerProviderManager().ui())
                .subscribe(isResultEmpty -> {
                    if (isViewAttached()) {
                        getView().onEmptyQueryResult(isResultEmpty);
                    }
                }));
    }

    private void handleApiError(Throwable throwable) {
        Timber.e("#handleApiError onError %s", throwable.getMessage());
        ResponseBody responseBody = ((HttpException) throwable).response().errorBody();
        String errorBody;
        try {
            if (responseBody != null) {
                errorBody = responseBody.string();
                apiError = convertErrorToApiError(errorBody);
                apiError.setThrowable(throwable);
            }
        } catch (IOException exception) {
            Timber.e("#handleApiError exception %s", exception.getMessage());
        }
        if (isViewAttached()) {
            getView().showError(apiError);
        }
    }

    private ApiError convertErrorToApiError(String error) {
        if (!TextUtils.isEmpty(error)) {
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(error);
            return gson.fromJson(jsonElement, ApiError.class);
        } else {
            return new ApiError();
        }
    }
}
