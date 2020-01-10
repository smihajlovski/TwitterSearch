package com.example.twittersearch.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Statuses {
    @SerializedName("statuses")
    private List<Tweet> tweetList;

    public List<Tweet> getTweetList() {
        return tweetList;
    }
}
