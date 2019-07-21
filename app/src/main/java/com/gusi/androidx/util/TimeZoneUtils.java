package com.gusi.androidx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author ylw  2018/7/25 20:42
 */
public class TimeZoneUtils {
    /**
     * 时区时间 转 long
     *
     * @return
     */
    public static long zoneTime2Long(String zoneTime) {
        //此方法是将2017-11-18T07:12:06.615Z格式的时间转化为秒为单位的Long类型。
        zoneTime = zoneTime.replace("Z", " UTC");//UTC是本地时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date d = null;
        try {
            d = format.parse(zoneTime);
        } catch (ParseException e) {
        }
        return d.getTime();
    }
}
