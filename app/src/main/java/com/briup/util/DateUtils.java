package com.briup.util;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    // Date format
    public static final String DATE_FORMAT             = "yyyy-MM-dd";
    public static final String TIME_FORMAT             = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_YYYY_MM          = "yyyy-MM";
    public static final String FORMAT_YYYY             = "yyyy";
    public static final String FORMAT_HH_MM            = "HH:mm";
    public static final String FORMAT_HH_MM_SS         = "HH:mm:ss";
    public static final String FORMAT_MM_SS            = "mm:ss";
    public static final String FORMAT_MM_DD_HH_MM      = "MM-dd HH:mm";
    public static final String FORMAT_MM_DD_HH_MM_SS   = "MM-dd HH:mm:ss";
    public static final String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_YYYY2MM2DD       = "yyyy.MM.dd";
    public static final String FORMAT_YYYY2MM2DD_HH_MM = "yyyy.MM.dd HH:mm";
    public static final String FORMAT_MMCDD_HH_MM      = "MM月dd日 HH:mm";
    public static final String FORMAT_MMCDD            = "MM月dd日";
    public static final String FORMAT_YYYYCMMCDD       = "yyyy年MM月dd日";

    public static final long ONE_DAY = 1000 * 60 * 60 * 24;

    //Determine if the selected date is this week
    public static boolean isThisWeek(Date time) {
        //        //Start on Sunday
        //      Calendar calendar = Calendar.getInstance();

        //Start on Monday
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);

        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);

        calendar.setTime(time);

        int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);

        if (paramWeek == currentWeek) {
            return true;
        }
        return false;
    }

    //Determine if the selected date is today
    public static boolean isToday(Date time) {
        return isThisTime(time, "yyyy-MM-dd");
    }

    //Determine if the selected date is this month
    public static boolean isThisMonth(Date time) {
        return isThisTime(time, "yyyy-MM");
    }

    //Determine if the selected date is this month
    public static boolean isThisYear(Date time) {
        return isThisTime(time, "yyyy");
    }

    //Determine if the selected date is yesterday
    public static boolean isYesterDay(Date time) {
        Calendar cal = Calendar.getInstance();
        long     lt  = time.getTime() / 86400000;
        long     ct  = cal.getTimeInMillis() / 86400000;
        if ((ct - lt) == 1) {
            return true;
        } else {
            return false;
        }
    }

    private static boolean isThisTime(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        String param = sdf.format(date);//参数时间

        String now = sdf.format(new Date());//当前时间

        if (param.equals(now)) {
            return true;
        }
        return false;
    }

    /**
     * Get the number of days in a year and month
     */
    public static int getDayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0); //输入类型为int类型
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Get the number of days between two times
     *
     * @return time1 - time
     */
    public static int getDayOffset(long time1, long time2) {
        // Set the time to 0 o'clock on the day
        long offsetTime;
        if (time1 > time2) {
            offsetTime = time1 - getDayStartTime(getCalendar(time2)).getTimeInMillis();
        } else {
            offsetTime = getDayStartTime(getCalendar(time1)).getTimeInMillis() - time2;
        }
        return (int) (offsetTime / ONE_DAY);
    }

    public static Calendar getCalendar(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar;
    }

    public static Calendar getDayStartTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * Get current time
     *
     * @param time
     * @return
     */
    public static String getDurationInString(long time) {
        String durStr = "";
        if (time == 0) {
            return "0秒";
        }
        time = time / 1000;
        long hour = time / (60 * 60);
        time = time - (60 * 60) * hour;
        long min = time / 60;
        time = time - 60 * min;
        long sec = time;
        if (hour != 0) {
            durStr = hour + "时" + min + "分" + sec + "秒";
        } else if (min != 0) {
            durStr = min + "分" + sec + "秒";
        } else {
            durStr = sec + "秒";
        }
        return durStr;
    }

    /**
     * Get current time
     *
     * @param time
     * @return
     */
    public static String getDurationInString1(long time) {
        String durStr = "";
        if (time == 0) {
            return "00:00";
        }
        time = time / 1000;
        long hour = time / (60 * 60);
        time = time - (60 * 60) * hour;
        long min = time / 60;
        time = time - 60 * min;
        long sec = time;
        if (hour != 0) {
            durStr = hour + ":" + min + ":" + sec;
        } else if (min != 0) {
            durStr = min + ":" + sec;
        } else {
            durStr = min + ":" + sec;
        }
        return durStr;
    }

    /**
     * Get the current day of the week
     *
     * @param dt
     * @return
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Calendar cal      = Calendar.getInstance();

        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    /**
     * Convert date format string to long
     *
     * @param date
     * @param format
     * @return
     */
    public static long convertToLong(String date, String format) {
        try {
            if (null != date && !"".equals(date)) {
                if (TextUtils.isEmpty(format)) {
                    format = TIME_FORMAT;
                }
                SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.getDefault());
                return formatter.parse(date).getTime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * Convert long integer numbers to date format strings
     *
     * @param time
     * @return
     */
    public static String convertToString(long time) {
        if (time > 0) {
            SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
            Date date      = new Date(time);
            return formatter.format(date);
        }
        return "";
    }

    /**
     * Convert long integer numbers to date format strings
     *
     * @param time
     * @return
     */
    public static String convertToString1(long time) {
        if (time >= 0) {
            SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_MM_SS, Locale.getDefault());
            Date date      = new Date(time);
            return formatter.format(date);
        }
        return "";
    }

    /**
     * Convert long integer numbers to date format strings
     *
     * @param time
     * @return
     */
    public static String convertToString2(long time) {
        if (time >= 0) {
            SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
            Date date      = new Date(time * 1000 + 8 * 60 * 60 * 1000);
            return formatter.format(date);
        }
        return "";
    }

}
