package com.example.twittersearch.managers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.example.twittersearch.common.Constants;

public class UtilsManager {

    public static CircularProgressDrawable createCircularProgressDrawable(Context context) {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(Constants.CIRCULAR_PROGRESS_STROKE_WIDTH);
        circularProgressDrawable.setCenterRadius(Constants.CIRCULAR_PROGRESS_CENTER_RADIUS);
        circularProgressDrawable.start();
        return circularProgressDrawable;
    }

    public static void hideKeyboard(Context context, View view) {
        if (view != null && context != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
