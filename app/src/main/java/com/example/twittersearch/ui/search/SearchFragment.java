package com.example.twittersearch.ui.search;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.twittersearch.R;
import com.example.twittersearch.data.model.ApiError;
import com.example.twittersearch.data.model.Tweet;
import com.example.twittersearch.databinding.FragmentSearchBinding;
import com.example.twittersearch.managers.SharedPreferencesManager;
import com.example.twittersearch.managers.UtilsManager;
import com.example.twittersearch.ui.base.BaseFragment;
import com.example.twittersearch.ui.search.contract.ISearchInteractor;
import com.example.twittersearch.ui.search.contract.ISearchView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import retrofit2.HttpException;

public class SearchFragment extends BaseFragment<FragmentSearchBinding> implements ISearchView {

    @Inject
    SearchPresenter<ISearchView, ISearchInteractor> searchPresenter;
    @Inject
    SharedPreferencesManager sharedPreferencesManager;

    private SearchTweetsAdapter searchTweetsAdapter;
    private List<Tweet> tweetList;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_search;
    }

    static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentComponent().inject(this);
        searchPresenter.onAttach(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        init();
        initSearchView();
        fetchTweets();
        return binder.getRoot();
    }

    @Override
    public void onDestroyView() {
        searchPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onTokenFetched(String token) {
        if (!TextUtils.isEmpty(token)) {
            sharedPreferencesManager.saveToken(token);
            searchPresenter.fetchTweets(binder.searchView);
        }
    }

    @Override
    public void onTweetsFetched(List<Tweet> tweets) {
        this.tweetList.clear();
        if (tweets != null && !tweets.isEmpty()) {
            this.tweetList.addAll(tweets);
            searchTweetsAdapter.notifyDataSetChanged();
            binder.searchTweets.setVisibility(View.GONE);
            binder.recyclerView.setVisibility(View.VISIBLE);
        } else {
            binder.searchTweets.setVisibility(View.VISIBLE);
            binder.searchTweets.setText(R.string.can_not_find_tweets);
            binder.recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onEmptyQueryResult(boolean isResultEmpty) {
        if (isResultEmpty) {
            searchTweetsAdapter.notifyDataSetChanged();
            binder.searchTweets.setVisibility(View.VISIBLE);
            binder.searchTweets.setText(R.string.search_tweets);
            binder.recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(ApiError error) {
        binder.searchTweets.setVisibility(View.VISIBLE);
        binder.recyclerView.setVisibility(View.GONE);
        if (error != null && error.getThrowable() != null){
            if (error.getThrowable() instanceof ConnectException) {
                binder.searchTweets.setText(R.string.no_connection);
            } else if (error.getThrowable() instanceof UnknownHostException) {
                binder.searchTweets.setText(R.string.no_connection);
            } else if (error.getThrowable() instanceof TimeoutException ||
                    error.getThrowable() instanceof SocketTimeoutException) {
                binder.searchTweets.setText(R.string.timeout);
            } else if (error.getThrowable() instanceof HttpException) {
                binder.searchTweets.setText(extractErrorMessage(error));
            } else {
                binder.searchTweets.setText(extractErrorMessage(error));
            }
        }
    }

    private void init() {
        tweetList = new ArrayList<>();
        searchTweetsAdapter = new SearchTweetsAdapter(getContext(), tweetList);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider_grey));
        binder.recyclerView.addItemDecoration(itemDecorator);
        binder.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binder.recyclerView.setAdapter(searchTweetsAdapter);
        binder.recyclerView.setClipToPadding(false);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initSearchView() {
        View underline = binder.searchView.findViewById(R.id.search_plate);
        underline.setBackgroundColor(Color.TRANSPARENT);
        EditText searchEditText = binder.searchView.findViewById(R.id.search_src_text);
        searchEditText.setHintTextColor(getResources().getColor(R.color.colorGrey));
        searchEditText.setTextColor(getResources().getColor(R.color.colorBlack));
        ((ImageView) binder.searchView.findViewById(R.id.search_close_btn)).setColorFilter(getResources().getColor(R.color.colorGrey));
        ((ImageView) binder.searchView.findViewById(R.id.search_mag_icon)).setColorFilter(getResources().getColor(R.color.colorGrey));
        binder.searchView.setQueryHint(getString(R.string.search));
        binder.recyclerView.setOnTouchListener((v, event) -> {
            UtilsManager.hideKeyboard(getContext(), binder.recyclerView);
            binder.searchView.clearFocus();
            return false;
        });
    }

    private void fetchTweets() {
        if (TextUtils.isEmpty(sharedPreferencesManager.fetchTokenFromPrefs())) {
            searchPresenter.fetchTwitterToken();
        } else {
            searchPresenter.fetchTweets(binder.searchView);
        }
    }

    private String extractErrorMessage(ApiError error) {
        String errorMessage = "";
        if (error != null && (error.getTwitterErrorList() != null && !error.getTwitterErrorList().isEmpty())) {
            for (int i = 0; i < error.getTwitterErrorList().size(); i++) {
                if (!TextUtils.isEmpty(error.getTwitterErrorList().get(i).getMessage())) {
                    errorMessage = errorMessage.concat(error.getTwitterErrorList().get(i).getMessage());
                    errorMessage = errorMessage.concat(getString(R.string.empty_space));
                }
            }
        }
        return errorMessage;
    }
}
