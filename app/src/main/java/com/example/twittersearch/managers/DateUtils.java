package com.example.twittersearch.managers;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public class DateUtils {

    private static final String DATE_FORMAT_PATTERN_ONE = "EEEE dd MMMM yyyy • HH:mm";
    private static final String DATE_FORMAT_PATTERN_TWITTER = "EEE MMM dd hh:mm:ss Z yyyy";

    public static String formatDatePerPattern(String currentDateString) {
        String formattedDateString = "";
        Date newDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN_TWITTER, Locale.getDefault());
        try {
            newDate = simpleDateFormat.parse(currentDateString);
        } catch (ParseException e) {
            Timber.e("#formatDatePerPattern %s", e.getMessage());
        }
        if (newDate != null) {
            simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN_ONE, Locale.getDefault());
            formattedDateString = simpleDateFormat.format(newDate);
        }
        if (!TextUtils.isEmpty(formattedDateString)) {
            return formattedDateString;
        } else {
            return currentDateString;
        }
    }
}
