package com.haiyu.manager.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp {

    //获取当前系统时间戳
    public static long getCurrnetTimeStamp() {
        return System.currentTimeMillis();
    }
    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws Exception{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    //将时间戳转换为Date
    /*
     * 将时间戳转换为时间
     */
    public static String stampToDate(String timeStamp){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(timeStamp);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }



}
