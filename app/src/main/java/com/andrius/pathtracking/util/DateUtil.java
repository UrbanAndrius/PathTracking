package com.andrius.pathtracking.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

    public static String getCurrentTime() {
        Date currentTime = Calendar.getInstance().getTime();
        return formatter.format(currentTime);
    }

}
