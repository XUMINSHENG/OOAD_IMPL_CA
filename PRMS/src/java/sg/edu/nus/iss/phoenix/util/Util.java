/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.util;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author A0134434M
 */
public class Util {

    public static final String C_Date_Format = "yyyy-MM-dd";
    public static final String C_Time_Format = "HH:mm:ss";

    public static Date stringToDate(String str) throws ParseException {
        try {
            DateFormat formatter = new SimpleDateFormat(C_Date_Format);
            return (Date) formatter.parse(str);
        } catch (ParseException e) {
            throw e;
        }
    }

    public static Time stringToTime(String str) {
        return Time.valueOf(str);
    }

    public static String dateToString(Date date) {
        DateFormat formatter = new SimpleDateFormat(C_Date_Format);
        return formatter.format(date);
    }

    public static String timeToString(Time time) {
        DateFormat formatter = new SimpleDateFormat(C_Time_Format);
        return formatter.format(time);
    }

    public static Calendar DateAddTime(Date date, Time time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal = CalAddTime(cal, time);
        return cal;
    }

    public static Calendar CalAddTime(Calendar cal, Time time) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(cal.getTime());
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(time);
        cal1.add(Calendar.HOUR_OF_DAY, cal2.get(Calendar.HOUR_OF_DAY));
        cal1.add(Calendar.MINUTE, cal2.get(Calendar.MINUTE));
        return cal1;
    }

    public static boolean IsNull(String string) {
        return (string == null) || (string.length() == 0);
    }
}
