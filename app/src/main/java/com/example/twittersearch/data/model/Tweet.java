package com.example.twittersearch.data.model;

import com.google.gson.annotations.SerializedName;

public class Tweet {
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("id")
    private long id;
    @SerializedName("text")
    private String tweetText;
    @SerializedName("user")
    private User user;

    public String getCreatedAt() {
        return createdAt;
    }

    public long getId() {
        return id;
    }

    public String getTweetText() {
        return tweetText;
    }

    public User getUser() {
        return user;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
