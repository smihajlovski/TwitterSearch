package com.example.twittersearch.ui.base;

import com.example.twittersearch.data.model.ApiError;

public interface BaseView {
    void showError(ApiError error);
}
