package io.cem.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public static List<Date> getLastMouths(int N){
        List<Date> mouths = new ArrayList<Date>();
        Calendar cal  = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        for(int i=0; i<N; i++) {
            cal.add(Calendar.MONTH, -1);
            //String m = formatter.format(cal.getTime());
            Date d = cal.getTime();
            mouths.add(d);
        }
        return mouths;
    }
    public static Date setStartEndDay(Date d,int type){
        Calendar cal  = Calendar.getInstance();
        cal.setTime(d);
        if(type==0){
            cal.add(Calendar.MONTH, 1);//向前推一个月，如果不加这行，将得到上个月的最后一天。
            cal.set(Calendar.DAY_OF_MONTH,0);
        }else {
            cal.set(Calendar.DAY_OF_MONTH,1);

        }
        return cal.getTime();

    }
    public static void main(String args[]){

        List<Date> ds = getLastMouths(4);
                    //System.out.println(getLastMouths(4));
                    for(Date d:ds){
                        System.out.println(format(setStartEndDay(d,0)));
                    }


    }
}
