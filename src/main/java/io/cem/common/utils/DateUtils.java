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
	//"yyyy-MM-dd HH:mm:ss:SSS";
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

    /*
     *  返回指定日期前n天的第一天
     *
     * 示例：
     *  Date x = create(2018,5,11);
        Date rs = getPreviousDay(x,10);
        System.out.println("begin:"+rs);
     * 返回：begin:Tue May 01 17:11:52 CST 2018
     */
    public static Date getPreviousDay(Date d,int n){
        Calendar cal  = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DATE, -n);
        return cal.getTime();
    }
    /*
     *  返回指定日期前n天
     *
     * 示例：
     *  Date x = create(2018,5,11);
        List<Date> rs = getPreviousDays(x,3);
     *  返回：
     *  Thu May 10 17:29:18 CST 2018
        Wed May 09 17:29:18 CST 2018
        Tue May 08 17:29:18 CST 2018
     *
     */
    public static List<Date> getPreviousDays(Date d,int n){
        List<Date> rs = new ArrayList<Date>();
        for(int i=1;i<=n;i++){
            Calendar cal  = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.DATE, -i);
            rs.add(cal.getTime());
        }
        return rs;
    }
    /**
     * 返回指定日期对象所在月份的第一天
     * @param d 要处理的日期
     * @return Date
     * 示例：
     * 当前月5月，调用 Date rs = getStartDayForMonth(new Date());
     * 返回：Tue May 01 00:00:00 CST 2018
     */
    public static Date getStartDayForMonth(Date d){
        return setStartEndDay(d,1);
    }
    /**
     * 返回指定日期对象所在月份的最后一天
     * @param d 要处理的日期
     * @return Date
     * 示例：
     * 当前月5月，调用 Date rs = getEndDayForMonth(new Date());
     * 返回：Thu May 31 00:00:00 CST 2018
     */
    public static Date getEndDayForMonth(Date d){
        return setStartEndDay(d,0);
    }
    /*
     * type = 0 返回当月的最后一天
     * type = 非0其它值 返回当月的第一天
     * 示例： 当前为5月 调用 setStartEndDay(new Date(),1)
     * 返回：Tue May 01 00:00:00 CST 2018
     * 调用 setStartEndDay(new Date(),0)
     * 返回：Thu May 31 00:00:00 CST 2018
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
    /*
     *  @desc 返回当前月前的n个月,包括当前月
     *  @params int N
     *  @return List<Date>
     *      示例：当前月为5月 调用 List<Date> rs = getLastMouths(4);
     *      返回：rs = [Thu May 10 11:44:02 CST 2018, Tue Apr 10 11:44:02 CST 2018, Sat Mar 10 11:44:02 CST 2018, Sat Feb 10 11:44:02 CST 2018]
     */
    public static List<Date> getPreviousMouths(int n){
        List<Date> mouths = new ArrayList<Date>();
        mouths.add(new Date());
        Calendar cal  = Calendar.getInstance();
        for(int i=0; i<n-1; i++) {
            cal.add(Calendar.MONTH, -1);
            //String m = formatter.format(cal.getTime());
            Date d = cal.getTime();
            mouths.add(d);
        }

        return mouths;
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

    //下面方法废弃

/*
*  @desc 返回当前月前的n个月,包括当前月
*  @params int N
*  @return List<Date>
*      示例：当前月为5月 调用 List<Date> rs = getLastMouths(4);
*      返回：rs = [Thu May 10 11:44:02 CST 2018, Tue Apr 10 11:44:02 CST 2018, Sat Mar 10 11:44:02 CST 2018, Sat Feb 10 11:44:02 CST 2018]
 */
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
      *  返回指定日期前n天的第一天
     *
     * 示例： 当前为5月10日， 调用 Date rs = getLastDay(new Date(),5)
            * 返回：Sat May 05 13:23:04 CST 2018
            */
    public static Date getLastDay(Date d,int number){
        Calendar cal  = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DATE, -number);
        return cal.getTime();
    }


    /*
     *  返回指定日期前n天
     *
     * 示例： 当前为5月10日， 调用 List<Date> rs = getLastNumberDays(new Date(),5)
     * 返回：[Wed May 09 13:40:19 CST 2018, Tue May 08 13:40:19 CST 2018, Mon May 07 13:40:19 CST 2018, Sun May 06 13:40:19 CST 2018, Sat May 05 13:40:19 CST 2018]
     */
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


    public static void main(String args[]){
        //System.out.println(new Date());
        //System.out.println(getLastMouths(4));

        //System.out.println(setStartEndDay(new Date(),1));
        //System.out.println(setStartEndDay(new Date(),0));
        Date x = create(2018,5,11);
        List<Date> rs = getPreviousDays(x,3);
        for(Date d:rs){
            System.out.println(d);
        }




    }
}
