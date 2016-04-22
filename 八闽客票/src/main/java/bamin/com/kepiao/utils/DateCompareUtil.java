package bamin.com.kepiao.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCompareUtil {
    public static boolean DateCompare(String s1, String s2) throws Exception {
        //设定时间的模板 
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //得到指定模范的时间
        Date d1 = sdf.parse(s1);
        Date d2 = sdf.parse(s2);
        //比较
        long count =(d1.getTime() - d2.getTime()) / (24 * 3600 * 1000);
        if ( count< 11&&count>=0) {
//            System.out.println("小于三天");
            return true;
        } else {
//            System.out.println("大于三天");
            return false;
        }

    }
    public static String DateMath(String s1, String s2) throws Exception {
        //设定时间的模板
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //得到指定模范的时间
        Date d1 = sdf.parse(s1);
        Date d2 = sdf.parse(s2);
        //比较
        long count = (d1.getTime() - d2.getTime()) / (24 * 3600 * 1000);
        if (count == 0) {
//            System.out.println("小于三天");
            return "等于";
        } else if (count == 10) {
//            System.out.println("大于三天");
            return "等于10";
        }else {
            return "";
        }

    }
//
//    public static void main(String args[]) throws Exception {
//        new DateCompareUtil().DateCompare("2011-11-28 11:15:11", "2011-12-02 11:15:11");
//    }
}