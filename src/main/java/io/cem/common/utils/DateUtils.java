package io.cem.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * 日期处理
 */
public class DateUtils {
	/** 时间格式(yyyy-MM-dd) */
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
    public static Date format(String date,int pattern){
        DateFormat df = null;
        Date rs = null;

        if(pattern==1){
            df = new SimpleDateFormat(DATE_PATTERN);
        }else{
            df = new SimpleDateFormat(DATE_TIME_PATTERN);
        }
        try {
            rs =  df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return rs;
    }
    public static void main(String args[]){
	    String [] m = new String[]{"1","2","3","4","5","6"};
        HashMap p = new HashMap();
        for(String i:m){
            p.put("k"+i,i);
        }
        System.out.println(p);
    }
}
