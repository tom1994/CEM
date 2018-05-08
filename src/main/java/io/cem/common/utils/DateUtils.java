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
        mouths.add(new Date());

        Calendar cal  = Calendar.getInstance();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        for(int i=0; i<N-1; i++) {
            cal.add(Calendar.MONTH, -1);
            //String m = formatter.format(cal.getTime());
            Date d = cal.getTime();
            mouths.add(d);
        }

        return mouths;
    }
    /*
    * type =1 返回当月的第一天
    * type = 非1其它值 返回当月的最后一天
     */
    public static Date setStartEndDay(Date d,int type){
        Calendar cal  = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);

        if(type==0){
            cal.add(Calendar.MONTH, 1);//向前推一个月，如果不加这行，将得到上个月的最后一天。
            cal.set(Calendar.DAY_OF_MONTH,0);
        }else {
            cal.set(Calendar.DAY_OF_MONTH,1);

        }
        return cal.getTime();

    }
    public static Date getLastDay(Date d,int number){
        Calendar cal  = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DATE, -number);
        return cal.getTime();
    }

    public static Date getDayforBegin(Date d){
        Calendar cal  = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();
    }
    public static Date getDayforEnd(Date d){
        Calendar cal  = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY,23);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.SECOND,59);
        cal.set(Calendar.MILLISECOND,999);
        return cal.getTime();
    }

    public static List<Date> getLastNumberDays(Date d,int number){
        List<Date> rs = new ArrayList<Date>();
        for(int i=1;i<=number;i++){
            Calendar cal  = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.DATE, -i);
            rs.add(cal.getTime());
        }
        return rs;
    }

    public static List<String> formatDateList(List<Date> dates,int type){
        List<String> rs = new ArrayList<String>();
        SimpleDateFormat df = null;
        for(Date d:dates){
            if(type==1){
                df = new SimpleDateFormat("MM-dd");
            }else {
                df = new SimpleDateFormat("yyyy-MM-dd");
            }
            rs.add(df.format(d));
        }
        return rs;
    }

    public static Date create(Integer year,Integer mouth,Integer day){
        Calendar cal  = Calendar.getInstance();
        cal.set(year,mouth-1,day);
        return cal.getTime();
    }
    public static void main(String args[]){
        //System.out.println(new Date());
        //System.out.println(getLastMouths(4));

        System.out.println(setStartEndDay(new Date(),1));
        System.out.println(setStartEndDay(new Date(),0));


    }
}
