package bamin.com.kepiao.models.about_wechat_pay;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/3/24.
 */
public class WechatOrderInfo
{

    /**
     * sign : 97464FB53F51F8552C36C836C9D314FA
     * map : {"appid":"wxa67cb8a1e90e486a","noncestr":"TKCS94R315I6809A6M778J6433UOPTHR","package":"Sign=WXPay","partnerid":"1324165801","prepayid":"","timestamp":"1458886892"}
     */

    private String sign;
    /**
     * appid : wxa67cb8a1e90e486a
     * noncestr : TKCS94R315I6809A6M778J6433UOPTHR
     * package : Sign=WXPay
     * partnerid : 1324165801
     * prepayid :
     * timestamp : 1458886892
     */

    private MapEntity map;

    public String getSign()
    {
        return sign;
    }

    public void setSign(String sign)
    {
        this.sign = sign;
    }

    public MapEntity getMap()
    {
        return map;
    }

    public void setMap(MapEntity map)
    {
        this.map = map;
    }

    public static class MapEntity
    {
        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String timestamp;

        public String getAppid()
        {
            return appid;
        }

        public void setAppid(String appid)
        {
            this.appid = appid;
        }

        public String getNoncestr()
        {
            return noncestr;
        }

        public void setNoncestr(String noncestr)
        {
            this.noncestr = noncestr;
        }

        public String getPackageX()
        {
            return packageX;
        }

        public void setPackageX(String packageX)
        {
            this.packageX = packageX;
        }

        public String getPartnerid()
        {
            return partnerid;
        }

        public void setPartnerid(String partnerid)
        {
            this.partnerid = partnerid;
        }

        public String getPrepayid()
        {
            return prepayid;
        }

        public void setPrepayid(String prepayid)
        {
            this.prepayid = prepayid;
        }

        public String getTimestamp()
        {
            return timestamp;
        }

        public void setTimestamp(String timestamp)
        {
            this.timestamp = timestamp;
        }
    }
}
