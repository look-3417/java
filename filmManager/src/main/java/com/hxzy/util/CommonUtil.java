package com.hxzy.util;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * @author nick
 * @description 通用工具类
 * @date create in 2020/9/11
 */
public class CommonUtil {
    public static final String PATTERN1 = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN2 = "yyyy-MM-dd";
    public static final String PATTERN3 = "HH:mm:ss";
    public static final String PATTERN4 = "yyyy/MM/dd";


    //将LocalDateTime转换为毫秒
    public static long localDateTime2mills(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
    //将LocalDateTime转换为Timestamp
    public static Timestamp localDateTime2TimeStamp(LocalDateTime localDateTime){
        long time = localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return new Timestamp(time);
    }

    //将LocalDateTime转换为指定格式的字符串
    public static String localDateTime2String(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.now(ZoneOffset.of("+8")).format(formatter);
    }

    //将字符串转换为LocalDateTime
    public static LocalDateTime str2LocalDateTime(String str,String pattern){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(str, df);
    }

    public static LocalDateTime timeStamp2LocalDateTime(Timestamp timestamp) {
        long time = timestamp.getTime();
        System.out.println(String.format("timestamp => %s",timestamp));
        // 时间戳转LocalDateTime         LocalDateTime.ofInstant(表示瞬间[毫秒],时区[中国时区编号为Asia/Shanghai])
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), TimeZone.getTimeZone("Asia/Shanghai").toZoneId());
    }
}
