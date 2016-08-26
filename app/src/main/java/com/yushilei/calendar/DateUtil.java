package com.yushilei.calendar;

import java.util.Calendar;

/**
 * @author by  yushilei.
 * @time 2016/8/26 -10:46.
 * @Desc
 */
public class DateUtil {
    private DateUtil() {
    }

    public static int getMonthDays(int year, int month) {
        Calendar instance = Calendar.getInstance();
        instance.set(year, month, 1);
        return instance.getActualMaximum(Calendar.DATE);
    }

    public static int getDayOfWeek(int year, int month) {
        Calendar instance = Calendar.getInstance();
        instance.set(year, month, 1);
        return instance.get(Calendar.DAY_OF_WEEK);

    }
}
