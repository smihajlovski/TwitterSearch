package com.example.twittersearch.ui.search.contract;

import com.example.twittersearch.data.model.Tweet;
import com.example.twittersearch.ui.base.BaseView;

import java.util.List;

public interface ISearchView extends BaseView {

    void onTokenFetched(String token);

    void onTweetsFetched(List<Tweet> tweetList);

    void onEmptyQueryResult(boolean isResultEmpty);
}
