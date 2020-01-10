package com.example.twittersearch.di.component;

import com.example.twittersearch.di.module.ActivityModule;
import com.example.twittersearch.di.scope.PerActivity;
import com.example.twittersearch.ui.search.SearchActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {
        ActivityModule.class})
public interface ActivityComponent {

    void inject(SearchActivity activity);
}
