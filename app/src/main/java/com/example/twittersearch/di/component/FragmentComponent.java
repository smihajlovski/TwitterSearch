package com.example.twittersearch.di.component;

import com.example.twittersearch.di.module.FragmentModule;
import com.example.twittersearch.di.module.SearchModule;
import com.example.twittersearch.di.scope.PerFragment;
import com.example.twittersearch.ui.search.SearchFragment;

import dagger.Component;

@PerFragment
@Component(dependencies = AppComponent.class, modules = {FragmentModule.class, SearchModule.class})
public interface FragmentComponent {

    void inject(SearchFragment fragment);
}
