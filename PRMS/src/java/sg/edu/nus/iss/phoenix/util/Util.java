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
import java.util.Date;

/**
 *
 * @author A0134434M
 */
public class Util {
    public static final String C_Date_Format = "yyyy-MM-dd";
    public static final String C_Time_Format = "hh:mm:ss";
    
    
    public static Date stringToDate(String str) throws ParseException {
        try{
            DateFormat formatter = new SimpleDateFormat(C_Date_Format); 
            return (Date)formatter.parse(str);
        }catch(ParseException  e){
            throw e;
        }
    }
    
    public static Time stringToTime(String str) {
        return Time.valueOf(str);
    }
    
    public static String dateToString(Date date){
        DateFormat formatter = new SimpleDateFormat(C_Date_Format); 
        return formatter.format(date);
    }
    
    public static String dateToString(Time time){
        DateFormat formatter = new SimpleDateFormat(C_Time_Format); 
        return formatter.format(time);
    }
    
}
