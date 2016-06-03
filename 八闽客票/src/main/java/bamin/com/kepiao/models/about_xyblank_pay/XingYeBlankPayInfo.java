package bamin.com.kepiao.models.about_xyblank_pay;

/**
 * Created by Administrator on 2016/6/2.
 */
public class XingYeBlankPayInfo
{

    /**
     * sign : 03b5811af01c940be37aa3dc4db21055
     * map : {"charset":"UTF-8","services":"pay.weixin.jspay|pay.weixin.micropay|pay.weixin.native|pay.weixin.app","sign":"336A4643865B8D789BCFD7F8390D8948","sign_type":"MD5","status":"0","token_id":"3c639c248ef242067977adaa7021d2ae","version":"2.0"}
     */

    private String sign;
    /**
     * charset : UTF-8
     * services : pay.weixin.jspay|pay.weixin.micropay|pay.weixin.native|pay.weixin.app
     * sign : 336A4643865B8D789BCFD7F8390D8948
     * sign_type : MD5
     * status : 0
     * token_id : 3c639c248ef242067977adaa7021d2ae
     * version : 2.0
     */

    private MapBean map;

    public String getSign()
    {
        return sign;
    }

    public void setSign(String sign)
    {
        this.sign = sign;
    }

    public MapBean getMap()
    {
        return map;
    }

    public void setMap(MapBean map)
    {
        this.map = map;
    }

    public static class MapBean
    {
        private String charset;
        private String services;
        private String sign;
        private String sign_type;
        private String status;
        private String token_id;
        private String version;
        private String message;

        public String getCharset()
        {
            return charset;
        }

        public void setCharset(String charset)
        {
            this.charset = charset;
        }

        public String getServices()
        {
            return services;
        }

        public void setServices(String services)
        {
            this.services = services;
        }

        public String getSign()
        {
            return sign;
        }

        public void setSign(String sign)
        {
            this.sign = sign;
        }

        public String getSign_type()
        {
            return sign_type;
        }

        public void setSign_type(String sign_type)
        {
            this.sign_type = sign_type;
        }

        public String getStatus()
        {
            return status;
        }

        public void setStatus(String status)
        {
            this.status = status;
        }

        public String getToken_id()
        {
            return token_id;
        }

        public void setToken_id(String token_id)
        {
            this.token_id = token_id;
        }

        public String getVersion()
        {
            return version;
        }

        public void setVersion(String version)
        {
            this.version = version;
        }

        public String getMessage()
        {
            return message;
        }

        public void setMessage(String message)
        {
            this.message = message;
        }
    }
}
