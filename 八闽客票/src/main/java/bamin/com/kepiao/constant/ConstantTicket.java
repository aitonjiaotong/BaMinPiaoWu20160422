package bamin.com.kepiao.constant;


/**
 * Created by Shane on 2016/1/16.
 */
public class ConstantTicket
{
    //    public static final String JDT_TICKET_HOST = "http://www.aiton.com.cn:808/JDTTicket.asmx/";
    public static final String JDT_TICKET_HOST = "http://www.bmcxfj.com:8088/JDTTicket.asmx/";

    public class URL
    {
        //获取所属公司下的
        public static final String GET_COMPANY_SUBZONE = EverythingConstant.HOST + "/bmpw/front/loadsetoutzone";
        //获取所属公司下的
        public static final String GET_ZONE_STREE = EverythingConstant.HOST + "/bmpw/front/loadarrivezone";
        //服务器上Banner的地址
        public static final String GET_BANNER_IMG = EverythingConstant.HOST + "/bmpw/picture/getpictures";
        //检测软件升级-八闽云服务地址
        public static final String UP_GRADE = EverythingConstant.HOST + "/bmpw/apk/upgradea_piaowu.txt";
        //获取联系人
        public static final String GET_USED_CONTACT = EverythingConstant.HOST + "/bmpw/person/findperson";
        //删除联系人
        public static final String DELETE_USED_CONTACT = EverythingConstant.HOST + "/bmpw/person/delperson";

        //购票须知
        public static final String TICKET_NOTICE = EverythingConstant.HOST + "/bmpw/front/goupiaoxuzhi";
        //票务的软件介绍
        public static final String SOFTWARE_INFO = "http://q.eqxiu.com/s/16f5zj5p";
        //八闽出行_三明绿者卡
        public static final String EVETHING_LDCARD = "http://q.eqxiu.com/s/B3xTuwQc";
        //八闽出行_软件介绍
        public static final String EVETHING_SOFTWARE_INFO = "http://u.eqxiu.com/s/bOwsz5Xr";
        //在线公交查询的H5页面
        public static final String ONLINE_BUS_WEB_HTML = "http://bus.xmjdt.com/";

    }

    /**
     * 请求码
     */
    public static final class Request
    {
        //选择出发地及目的地页面的相关请求码
        public static final int REQUEST_CODE_CHOOSE_SET_OUT = 1;
        public static final int REQUEST_CODE_CHOOSE_ARRIVE = 2;
        public static final int REQUEST_CODE_COMMIT_ORDER = 3;
    }

    /**
     * 返回码
     */
    public static final class ResultCode
    {
        //选择出发地址相关的返回码
        public static final int RESULT_CODE_SET_OUT_ADDR = 1;
        public static final int RESULT_CODE_SEARCH_ADDR = 2;
        public static final int RESULT_CODE_COMMONLY_USED_ADDR = 3;
        //选择目的地相关的返回码
        public static final int RESULT_CODE_ARRIVE_ADDR = 4;
        public static final int RESULT_CODE_ARRIVE_SEARCH_ADDR = 5;
        public static final int RESULT_CODE_ARRIVE_COMMONLY_USED_ADDR = 6;
        public static final int RESULT_CODE_COMMIT_ORDER = 7;
    }

    /**
     * Intent传值的KEY
     */
    public static final class IntentKey
    {

        public static final String CURR_YEAR = "year";
        public static final String CURR_MONTH = "month";
        public static final String CURR_DAY_OF_MONTH = "day_of_month";

        public static final String FINAIL_SET_OUT_STATION = "set_out_station";
        public static final String FINAIL_ARRIVE_STATION = "arrive_station";

        //选择出发地址相关的Key
        public static final String KEY_SET_OUT_ZONE_NAME = "SetOutZoneName";
        //选择目的地址相关的Key
        public static final String KEY_ARRIVE_ZONE_NAME = "ArriveZoneName";
    }


}
