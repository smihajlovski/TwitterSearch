package com.example.twittersearch.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiError {
    @SerializedName("errors")
    private List<TwitterError> twitterErrorList;
    private Throwable throwable;

    public ApiError() {
    }

    public List<TwitterError> getTwitterErrorList() {
        return twitterErrorList;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
