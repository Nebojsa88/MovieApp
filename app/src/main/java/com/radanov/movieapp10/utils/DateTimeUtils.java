package com.radanov.movieapp10.utils;


import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.TimeZone;

public final class DateTimeUtils {

    private static final String SECOND_DATE_FORMAT = "yyyy/MM/dd";


    public static String serverToUserFormat(String time, String formattingStyle) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(SECOND_DATE_FORMAT);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat userFormat = new SimpleDateFormat(formattingStyle);
        Date date = null;
        try {
            date = format.parse(time);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) return "";
        String timeZone = Calendar.getInstance().getTimeZone().getID();
        Date local = new Date(date.getTime() + TimeZone.getTimeZone(timeZone).getOffset(date.getTime()));
        return userFormat.format(local);
    }







}