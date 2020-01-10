package com.example.twittersearch.ui.search.contract;

import androidx.appcompat.widget.SearchView;

import com.example.twittersearch.ui.base.IBasePresenter;

public interface ISearchPresenter<V extends ISearchView, I extends ISearchInteractor> extends IBasePresenter<V, I> {

    void fetchTwitterToken();

    void fetchTweets(SearchView searchView);
}
