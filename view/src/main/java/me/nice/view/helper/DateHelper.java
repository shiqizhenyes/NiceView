package me.nice.view.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateHelper {

    public static Calendar getCalendarOfDateWithOutHourMinutesSecond(Date date){
        final Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }


    public static Calendar getCalendarOfDate(Date date){
        final Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar;
    }


    public static int getHour(Date date){
        return getCalendarOfDate(date).get(Calendar.HOUR);
    }

    public static int getHourOfDay(Date date){
        return getCalendarOfDate(date).get(Calendar.HOUR);
    }

    public static int getHour(Date date, boolean isAmPm){
        if(isAmPm){
            return getHourOfDay(date);
        } else {
            return getHour(date);
        }
    }

    public static int getMinuteOf(Date date) {
        return getCalendarOfDate(date).get(Calendar.MINUTE);
    }

    /**
     * 取得最小分钟时间差的整数倍数时间（分钟）
     * @param date
     * @param minuteStep
     * @return
     */
    public static int getMinuteOf(Date date, int minuteStep) {
        int minute = getCalendarOfDate(date).get(Calendar.MINUTE);
        int minuteRemainder = minute % minuteStep;

        if (minuteRemainder > 0) {
            return minute - minuteRemainder + minuteStep;
        }

        return minute;
    }

    public static Date today() {
        return Calendar.getInstance(Locale.getDefault()).getTime();
    }

    public static int getMonth(Date date) {
        return getCalendarOfDate(date).get(Calendar.MONTH);
    }

    public static int getDay(Date date){
        return getCalendarOfDate(date).get(Calendar.DAY_OF_MONTH);
    }
}
