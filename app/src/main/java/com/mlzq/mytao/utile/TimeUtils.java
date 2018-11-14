package com.mlzq.mytao.utile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dev on 2017/11/10.
 */

public class TimeUtils {
    /**
     * 获取指定时间对应的毫秒数
     * @param time "HH:mm:ss"
     * @return
     */
    public static long getTimeMillis(String time) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
            Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
            return curDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * 获取系统现在时间
     * @return
     */
    public static String getSystemTimeTOString() {
        long cur = System.currentTimeMillis();
        Date currentTime = new Date(cur);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }
    /**
     * 获取系统现在时间
     * @return
     */
    public static String getSystemTimeDayTOString() {
        long cur = System.currentTimeMillis();
        Date currentTime = new Date(cur);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取系统现在时间
     * @return
     */
    public static String getSystemMonthTOString() {
        long cur = System.currentTimeMillis();
        Date currentTime = new Date(cur);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    /**
     * 获取几天之前 正为后，负为前
     * @param day
     * @return
     */
    public  static String getDay(int  day){
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        calendar1.add(Calendar.DATE, day);
        return sdf1.format(calendar1.getTime());
        //System.out.println(three_days_ago);
    }


    /**
     *long 转String
     * @return
     */
    public static String getSystemDate(long time) {
        String res = null;
        try {
             // Log.e("时间戳",time+"");
            SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          //  Long time=new Long(Long.valueOf(s));
            Date date = new Date(time);
            res = format.format(date);

            //  Log.e("时间戳后",res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    /**
     *long 转String
     * @return
     */
    public static String getSystemHourDate(long time) {
        String res = null;
        try {
            // Log.e("时间戳",time+"");
            SimpleDateFormat format =  new SimpleDateFormat("HH:mm:ss");
            //  Long time=new Long(Long.valueOf(s));
            Date date = new Date(time);
            res = format.format(date);

            //  Log.e("时间戳后",res);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 转化时间戳
     * @param time
     * @return
     */
    public static long  getlongTime(String time){
        //Date或者String转化为时间戳
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String time="1970-01-06 11:45:55";
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.print("Format To times:"+date.getTime());
        //Log.e("TAG","时间:"+date.getTime());
        return date.getTime();
    }
    // string类型转换为date类型
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }
}
