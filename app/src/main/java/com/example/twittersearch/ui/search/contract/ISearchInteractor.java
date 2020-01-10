package com.example.twittersearch.ui.search.contract;

import com.example.twittersearch.data.model.Tweet;
import com.example.twittersearch.data.model.TwitterToken;
import com.example.twittersearch.ui.base.IBaseInteractor;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface ISearchInteractor extends IBaseInteractor {

    Observable<List<Tweet>> fetchTweetsFromApi(String query);

    Single<String> fetchTwitterToken(String grantType);
}
