package bamin.com.kepiao.utils;

/**
 * Created by Administrator on 2016/2/29.
 */
public class GetLastWordUtil
{
    /**
     * 去除市/县
     */
    public static String GetRidOfLastWord(String s)
    {
        String newStr;
        if (s.endsWith("市"))
        {
            newStr = s.replace("市", "");
            return newStr;
        }
        if (s.endsWith("县"))
        {
            newStr = s.replace("县", "");
            return newStr;
        }
        if (s.endsWith("区"))
        {
            newStr = s.replace("区", "");
            return newStr;
        }
        return s;
    }
}
