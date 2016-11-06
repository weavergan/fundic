package org.fund.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
    public static Calendar calendar = Calendar.getInstance();

    public static String dateToString(Date d) {
        if (d == null) {
            d = new Date();
        }
        String dateStr = dateFormat.format(d);
        return dateStr;
    }

    public static Date stringToDate(String dateStr) {
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {}

        return date;
    }

    // 获取日期的整形格式
    public static Integer getDateFormat2(Date d) {
        if (d == null) {
            d = new Date();
        }
        return Integer.parseInt(dateFormat2.format(d));
    }

    // 获取一个季度之后的日期
    public static Integer getDateFormat2AddQuarter(Integer oldDate) {
        Date date;
        try {
            date = dateFormat2.parse(String.valueOf(oldDate));
        } catch (ParseException e) {
            date = new Date();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 3);
        date = c.getTime();
        return Integer.parseInt(dateFormat2.format(date));
    }

    public static Integer getDateFormat2AddQuarter() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 3);
        date = c.getTime();
        return Integer.parseInt(dateFormat2.format(date));
    }

    // 获取半年之后的日期
    public static Integer getDateFormat2AddHalfYear(Integer oldDate) {
        Date date;
        try {
            date = dateFormat2.parse(String.valueOf(oldDate));
        } catch (ParseException e) {
            date = new Date();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 6);
        date = c.getTime();
        return Integer.parseInt(dateFormat2.format(date));
    }

    public static Integer getDateFormat2AddHalfYear() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 6);
        date = c.getTime();
        return Integer.parseInt(dateFormat2.format(date));
    }

    // 获取一年之后的日期
    public static Integer getDateFormat2AddOneYear(Integer oldDate) {
        Date date;
        try {
            date = dateFormat2.parse(String.valueOf(oldDate));
        } catch (ParseException e) {
            date = new Date();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, 1);
        date = c.getTime();
        return Integer.parseInt(dateFormat2.format(date));
    }

    public static Integer getDateFormat2AddOneYear() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, 1);
        date = c.getTime();
        return Integer.parseInt(dateFormat2.format(date));
    }

    // date1的日期是否在date2之前
    public static boolean before(String date1, String date2) throws ParseException {
        Date a = dateFormat.parse(date1);
        Date b = dateFormat.parse(date2);
        if (a.before(b))
            return true;
        else
            return false;
    }

    public static String dateIntegerToString(Integer date) {
        String temp = String.valueOf(date);
        String dateStr = temp.substring(0, 4) + "-" + temp.substring(4, 6) + "-" + temp.substring(6);

        return dateStr;
    }
}
