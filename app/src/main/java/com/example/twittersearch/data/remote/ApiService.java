package com.example.twittersearch.data.remote;

import com.example.twittersearch.data.model.Statuses;
import com.example.twittersearch.data.model.TwitterToken;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("1.1/search/tweets.json")
    Observable<Statuses> fetchTweets(@Query("q") String query);

    @FormUrlEncoded
    @POST("oauth2/token")
    Single<TwitterToken> fetchTwitterToken(@Field("grant_type") String grantType);
}
