package com.example.twittersearch.di.component;

import com.example.twittersearch.data.remote.ApiService;
import com.example.twittersearch.di.module.AppModule;
import com.example.twittersearch.managers.SchedulerProviderManager;
import com.example.twittersearch.managers.SharedPreferencesManager;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import io.reactivex.disposables.CompositeDisposable;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    ApiService apiService();

    SchedulerProviderManager schedulerProvider();

    CompositeDisposable compositeDisposable();

    SharedPreferencesManager sharedPreferencesManager();

    Gson gson();
}
