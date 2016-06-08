package bamin.com.kepiao.constant;

/**
 * Created by Administrator on 2016/4/7.
 */
public class Constant {
//    public static final String HOST = "http://www.aiton.com.cn:8080";//艾通后台服务器HOST地址
    public static final String HOST = "http://www.bmcxfj.com:8080/bmpw";//八闽后台服务器HOST地址
    public static final int ABLEVERSION = 1;//可用版本号
//    public static final String HOST_TICKET = "http://120.24.46.15:8080/aiton-tickets-app-webapp";//票务测试
    public static final String HOST_TICKET = "http://120.55.166.203:8020/aiton-tickets-app-webapp";//票务正式服务器
    public static final String GETSMS = "http://120.55.166.203:8010/aiton-app-webapp/public/sendmessage";//发送短信
    //    public static final String JDT_TICKET_HOST = "http://www.aiton.com.cn:808/JDTTicket.asmx/";
    public static final String JDT_TICKET_HOST = "http://www.bmcxfj.com:8088/JDTTicket.asmx/";

    public static final class Url {
        public static final String LOADLOGINID = HOST_TICKET + "/loadloginid";//检查是否在其他设备上登录
        public static final String LOGIN = HOST_TICKET + "/login";//登录
        public static final String REGISTEREDBYPASSWORD = HOST_TICKET + "/registeredbypassword";//注册
        public static final String UPDATEPASSWORD = HOST_TICKET + "/updatepassword";//修改密码和忘记密码
        public static final String GETLEFTTIME_TICKET = HOST_TICKET + "/pw/message/find";//获取售票剩余时间
        public static final String CHECKLIVE_TICKET = HOST_TICKET + "/live/cheklive";//检查服务器是否存活
        public static final String UPDATEICON = HOST_TICKET + "/account/updateIcon";//检查服务器是否存活
        //获取所属公司下的
        public static final String GET_COMPANY_SUBZONE = HOST_TICKET + "/front/loadsetoutzone";
        //获取所属公司下的
        public static final String GET_ZONE_STREE = HOST_TICKET + "/front/loadarrivezone";
        //服务器上Banner的地址
        public static final String GET_BANNER_IMG = HOST_TICKET + "/picture/getpictures";
        //检测软件升级-八闽云服务地址
        public static final String UP_GRADE = HOST_TICKET + "/apk/upgradea_piaowu.txt";
        //获取联系人
        public static final String GET_USED_CONTACT = HOST_TICKET + "/person/findperson";
        //删除联系人
        public static final String DELETE_USED_CONTACT = HOST_TICKET + "/person/delperson";
        //更新联系人
        public static final String UPDATEPERSON = HOST_TICKET + "/person/updateperson";
        //添加联系人
        public static final String ADDPERSON = HOST_TICKET + "/person/addperson";
        //购票须知
        public static final String TICKET_NOTICE = "http://bmcx.oss-cn-shanghai.aliyuncs.com/html/goupiaoxuzhi.html";
        //票务的软件介绍
        public static final String SOFTWARE_INFO = "http://q.eqxiu.com/s/16f5zj5p";
        //加载二维码图片地址
        public static final String CLI_SCAN = "http://bmcx.oss-cn-shanghai.aliyuncs.com/cliscan/android/bmcx_piaowu.png";
        //获取支付宝签名
        public static final String GETSIGN = HOST_TICKET+"/alipay/getsign";
    }

    public static final class Key{
        // 购票剩余时间限制
        public static long LEFT_BUY_TICKET_TIME = 1800L * 1000L;
        // 购票剩余时间提示语
        public static String LEFT_BUY_TICKET_MSG = "据发车时间半小时内，停止售票";
        public static void setLeftBuyTicketTime(long leftBuyTicketTime) {
            LEFT_BUY_TICKET_TIME = leftBuyTicketTime;
        }
        public static void setLeftBuyTicketMsg(String leftBuyTicketMsg) {
            LEFT_BUY_TICKET_MSG = leftBuyTicketMsg;
        }
    }

    public static final class SP_KEY{
        public static final String SP_NAME = "isLogin";//sp的名字
        public static final String SP_ISFRIST = "isfrist";//是否第一次进入APP
        public static final String ID = "id";//用户id
        public static final String SITE = "site";//站点
        public static final String START_SITE = "start_site";//出发站点
        public static final String END_SITE = "end_site";//目的站点
    }

    /**
     * 微信支付相关
     */
    public static final class WechatPay {
        //微信支付相关的APP_ID
        public static final String APP_ID = "wx40b57f5f7c117af3";
        //微信支付:获取后台服务端微信支付预支付订单号
        public static final String GET_WECHAT_ORDER_INFO_URL = HOST_TICKET + "/xy/getprepay";

        //微信支付:保存商户订单号到SharedPreferences的dataName
        public static final String ABOUT_WECHAT_PAY = "WechatPayOutTradeNo";
        //微信支付:保存商户订单号到SharedPreferences的KEY
        public static final String ABOUT_WECHAT_PAY_OUT_TRADE_NO = "WechatPayOutTradeNo";
        //微信支付:保存金点通票务的订单号到SharedPreferences的KEY
        public static final String ABOUT_WECHAT_PAY_BOOKLOGAID = "BookLogAID";
        //微信支付:保存服务端的订单号到SharedPreferences的KEY
        public static final String ABOUT_WECHAT_PAY_ORDERID = "OderID";
        //微信支付:保存红包ID到SharedPreferences的KEY
        public static final String ABOUT_WECHAT_PAY_REDID = "RedID";
        //微信支付:保存到本地的实际支付的金额的KEY
        public static final String ABOUT_WECHAT_PAY_REALPAYPRICE = "realPayPrice";

    }

    public static final class RequestAndResultCode {
        //0被自动升级占用了
        public static final int PERMISSION_CALL_PHONE = 1;
        public static final int PERMISSION_READ_SMS = 2;
        public static final int PERMISSION_READ_EXTERNAL_STORAGE = 3;
        public static final int PERMISSION_CAMERA = 4;
        public static final int PERMISSION_WRITE_EXTERNAL_STORAGE_SSENGJI = 5;
        //选择出发地及目的地页面的相关请求码
        public static final int REQUEST_CODE_CHOOSE_SET_OUT = 6;
        public static final int REQUEST_CODE_CHOOSE_ARRIVE = 7;
        public static final int REQUEST_CODE_COMMIT_ORDER = 8;
        //选择出发地址相关的返回码
        public static final int RESULT_CODE_SET_OUT_ADDR = 9;
        public static final int RESULT_CODE_SEARCH_ADDR = 10;
        public static final int RESULT_CODE_COMMONLY_USED_ADDR = 11;
        //选择目的地相关的返回码
        public static final int RESULT_CODE_ARRIVE_ADDR = 12;
        public static final int RESULT_CODE_ARRIVE_SEARCH_ADDR = 13;
        public static final int RESULT_CODE_ARRIVE_COMMONLY_USED_ADDR = 14;
        public static final int RESULT_CODE_COMMIT_ORDER = 15;
    }
    /**
     * Intent传值的KEY
     */
    public static final class IntentKey {
        public static final String CURR_YEAR = "year";
        public static final String CURR_MONTH = "month";
        public static final String CURR_DAY_OF_MONTH = "day_of_month";
        public static final String FINAIL_SET_OUT_STATION = "set_out_station";
        public static final String FINAIL_ARRIVE_STATION = "arrive_station";
        //选择出发地址相关的Key
        public static final String KEY_SET_OUT_ZONE_NAME = "SetOutZoneName";
        //选择目的地址相关的Key
        public static final String KEY_ARRIVE_ZONE_NAME = "ArriveZoneName";
        public static final String PAY_ORDERID = "OrderID";//向银联界面传的订单号
        public static final String PAY_PRICE = "price";//向银联界面传的支付金额
        public static final String PAY_REDENVELOPE_ID = "redEnvelope_id";//向银联界面传的支付方式
    }
}
