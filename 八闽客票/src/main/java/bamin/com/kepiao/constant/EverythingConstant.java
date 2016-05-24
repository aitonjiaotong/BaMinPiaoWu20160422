package bamin.com.kepiao.constant;

/**
 * Created by Administrator on 2016/4/7.
 */
public class EverythingConstant {
//    public static final String HOST = "http://www.aiton.com.cn:8080";//艾通后台服务器HOST地址
        public static final String HOST = "http://www.bmcxfj.com:8080";//八闽后台服务器HOST地址
    //        public static final String HOST = "http://192.168.1.100:8080";//本地服务器
    public static final int ABLEVERSION = 1;//可用版本号

    /**
     * 微信支付相关
     */
    public static final class WechatPay {
        //微信支付相关的APP_ID
        public static final String APP_ID = "wx40b57f5f7c117af3";
        //微信支付:获取后台服务端微信支付预支付订单号
        public static final String GET_WECHAT_ORDER_INFO_URL = HOST + "/bmpw/wx/wxpay";
        //微信支付:获取后台服务端微信支付支付结果  传入参数String out_trade_no(商户订单号)
        public static final String CHECKED_WECHAT_ORDER_RESULT_URL = HOST + "/bmpw/wx/wxcheck";

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

    /**
     * 总APP中用到的
     */
    //查询所有订单数据接口  传入数据：Integer account_id,Integer page
    //租车: 0 已结算  1 等待结算    票务 1 未支付 0 已支付 2 较早
    public static final String GET_ALL_ORDER_LIST = HOST + "/bmpw/cx/order/loadorderbyaccount";

    public static final class RequestAndResultCode {
        public static final int PERMISSION_CALL_PHONE = 0;
    }
}
