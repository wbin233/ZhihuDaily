package com.example.dell_pc.wbin_zhihudaily.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by wbin on 2016/8/24.
 */
public class DateUtil {
    static Calendar calendar = Calendar.getInstance();
    static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

    public static void setCalendar(String date) {
        try {
            calendar.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void subDate() {
        calendar.add(Calendar.DATE, -1);
    }

    public static String getDate() {
        return format.format(calendar.getTime());
    }
}
