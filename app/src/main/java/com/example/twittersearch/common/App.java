package com.example.twittersearch.common;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.example.twittersearch.BuildConfig;
import com.example.twittersearch.di.component.AppComponent;
import com.example.twittersearch.di.component.DaggerAppComponent;
import com.example.twittersearch.di.module.AppModule;

import timber.log.Timber;

public class App extends MultiDexApplication {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.uprootAll();
            Timber.plant(new Timber.DebugTree());
        }
    }

    public AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent
                    .builder()
                    .appModule(new AppModule(this))
                    .build();
        }
        return appComponent;
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }
}
