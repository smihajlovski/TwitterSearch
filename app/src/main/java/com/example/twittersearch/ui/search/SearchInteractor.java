package com.example.twittersearch.ui.search;

import com.example.twittersearch.data.model.Statuses;
import com.example.twittersearch.data.model.Tweet;
import com.example.twittersearch.data.model.TwitterToken;
import com.example.twittersearch.data.remote.ApiService;
import com.example.twittersearch.managers.SchedulerProviderManager;
import com.example.twittersearch.ui.search.contract.ISearchInteractor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

public class SearchInteractor implements ISearchInteractor {

    @Inject
    ApiService apiService;
    @Inject
    SearchInteractor() {
        super();
    }

    @Override
    public Observable<List<Tweet>> fetchTweetsFromApi(String query) {
        return apiService.fetchTweets(query).map(Statuses::getTweetList);
    }

    @Override
    public Single<String> fetchTwitterToken(String grantType) {
        return apiService.fetchTwitterToken(grantType).map(TwitterToken::getAccessToken);
    }
}
