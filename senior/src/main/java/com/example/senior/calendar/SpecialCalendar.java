package com.example.senior.calendar;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * SpecialCalendar
 *
 * @author lao
 * @date 2019/4/13
 */


/*
*
*  闰年计算
* */
public class SpecialCalendar {
    private static final  String TAG = "SpecialCalendar";

    //判断是否为润年
    public static boolean isLeapYear(int year) {
        if (year % 100 == 0 && year % 400 == 0) {
            return true;
        } else if (year % 100 != 0 && year % 4 == 0) {
            return true;
        } else {
            return false;
        }
    }

    //得到某月有多少天
    public static int getDaysOfMonth(boolean isLeapYear, int month) {
        int daysOfMonth = 0;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                daysOfMonth = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                daysOfMonth = 30;
                break;
            case 2:
                if (isLeapYear) {
                    daysOfMonth = 29;
                } else {
                    daysOfMonth  = 28;
                }
        }
        return daysOfMonth;
    }

    //指定某年中的某月的第一天为星期几
    public static int getWeekdayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month -1 ,1);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        Log.d(TAG, "==== dayOfWeek====" + dayOfWeek);
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        Log.d(TAG, "===daysOfWeek====" + dayOfWeek);
        return dayOfWeek;
    }

    public static int getTodayWeek() {
        int week = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        week = calendar.get(Calendar.WEEK_OF_YEAR);
        return week;
    }
}
