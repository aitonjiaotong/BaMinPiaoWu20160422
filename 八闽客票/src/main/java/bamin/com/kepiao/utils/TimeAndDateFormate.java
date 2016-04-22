package bamin.com.kepiao.utils;

import java.text.SimpleDateFormat;

/**
 * Created by zjb on 2016/2/25.
 */
public class TimeAndDateFormate {
    //转换时间
    public static String dateFormate(String setoutTime) {
        if(setoutTime!=null){

            long longtime = Long.parseLong(setoutTime.substring(6, setoutTime.length() - 2));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            return sdf.format(longtime);
        }else {
            return "获取失败";
        }
    }

    //转换时间
    public static String timeFormate(String setoutTime) {
        long longtime = Long.parseLong(setoutTime.substring(6, setoutTime.length() - 2));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(longtime);
    }
}
