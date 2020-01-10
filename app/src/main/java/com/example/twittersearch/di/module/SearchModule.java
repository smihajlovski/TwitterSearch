package com.example.twittersearch.di.module;

import com.example.twittersearch.di.scope.PerFragment;
import com.example.twittersearch.ui.search.SearchInteractor;
import com.example.twittersearch.ui.search.SearchPresenter;
import com.example.twittersearch.ui.search.contract.ISearchInteractor;
import com.example.twittersearch.ui.search.contract.ISearchPresenter;
import com.example.twittersearch.ui.search.contract.ISearchView;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    @Provides
    @PerFragment
    ISearchInteractor provideClientInteractor(SearchInteractor interactor) {
        return interactor;
    }

    @Provides
    @PerFragment
    ISearchPresenter<ISearchView, ISearchInteractor> provideClientPresenter(SearchPresenter<ISearchView, ISearchInteractor> presenter) {
        return presenter;
    }
}
