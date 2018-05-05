package com.hunk.java8;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatThreadLocal {

    private static  ThreadLocal<DateFormat> sdf = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };

    public static  Date convert(String str)throws  Exception{
        return  sdf.get().parse(str);
    }
}
