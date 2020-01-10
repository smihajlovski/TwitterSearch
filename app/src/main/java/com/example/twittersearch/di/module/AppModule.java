package com.example.twittersearch.di.module;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.example.twittersearch.BuildConfig;
import com.example.twittersearch.common.Constants;
import com.example.twittersearch.data.remote.ApiService;
import com.example.twittersearch.managers.SchedulerProviderManager;
import com.example.twittersearch.managers.SharedPreferencesManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    Context providerContext() {
        return application;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        return builder.create();
    }

    @Provides
    @Singleton
    SharedPreferencesManager provideSharedPreferencesManager(Application application) {
        return new SharedPreferencesManager(application);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @Singleton
    SchedulerProviderManager provideSchedulerProviderManager() {
        return new SchedulerProviderManager();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Application application) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        String concatenatedKeyAndSecret = Constants.CONSUMER_API_KEY + ":" + Constants.CONSUMER_API_SECRET;
        String keyAndSecretBase64 = Base64.encodeToString(concatenatedKeyAndSecret.getBytes(), Base64.NO_WRAP);

        return new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(application);
                    String token = sharedPreferencesManager.fetchTokenFromPrefs();
                    Request request = chain.request();
                    Request.Builder requestBuilder = request.newBuilder();
                    requestBuilder.addHeader("Content-Type", "application/x-www-form-urlencoded");

                    if (!TextUtils.isEmpty(token)) {
                        requestBuilder.addHeader("Authorization", "Bearer " + token);
                    } else {
                        requestBuilder.addHeader("Authorization", "Basic " + keyAndSecretBase64);
                    }
                    request = requestBuilder.build();
                    return chain.proceed(request);
                })
                .addInterceptor(interceptor)
                .build();
    }
}
