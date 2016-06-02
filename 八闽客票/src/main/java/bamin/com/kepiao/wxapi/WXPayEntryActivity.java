package bamin.com.kepiao.wxapi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aiton.administrator.shane_library.shane.utils.HTTPUtils;
import com.aiton.administrator.shane_library.shane.utils.VolleyListener;
import com.android.volley.VolleyError;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import bamin.com.kepiao.R;
import bamin.com.kepiao.activity.MainActivity;
import bamin.com.kepiao.activity.OrderDeatilActivity;
import bamin.com.kepiao.constant.Constant;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler, View.OnClickListener {
    private String APP_ID = "wx40b57f5f7c117af3";
    private IWXAPI api;
    private String mWechatPayOutTrandeNo;
    private String mWechatPayBookLogaId;
    private Map<String, String> mChedckOrderResultParams;
    private String mWechatPayOrderId;
    private int mWechatPayRedId;
    private int mWechatPayResultCode;
    private TextView mTv_btn_wechat_pay_result;
    private ImageView mIv_result_img;
    private TextView mTv_result_show;
    private ImageView mIv_wechat_pay_back;
    private String mRealPayPrice;
//    private boolean isQianTaiComfrim = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);
        findViewID();
        setListener();
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        api.handleIntent(getIntent(), this);

    }

    private void setListener() {
        mIv_wechat_pay_back.setOnClickListener(this);
        mTv_btn_wechat_pay_result.setOnClickListener(this);
    }

    private void findViewID() {
        mIv_wechat_pay_back = (ImageView) findViewById(R.id.iv_wechat_pay_back);
        mTv_btn_wechat_pay_result = (TextView) findViewById(R.id.tv_btn_wechat_pay_result);
        mIv_result_img = (ImageView) findViewById(R.id.iv_result_img);
        mTv_result_show = (TextView) findViewById(R.id.tv_result_show);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case 0:
                //调用微信支付结果成功
                Log.e("onResponse ", "调用微信支付结果成功;errCode " + resp.errCode);
                getSharedPreferencesForCheck();
                if (mWechatPayOutTrandeNo != null && !"".equals(mWechatPayOutTrandeNo)) {
                    checkOrderResult(mWechatPayOutTrandeNo);
                }
                break;
            case -1:
                //微信支付异常
                Log.e("onResponse ", "调用微信支付结果失败;errCode " + resp.errCode);
                mTv_btn_wechat_pay_result.setText("返回");
                mWechatPayResultCode = -1;
                mIv_result_img.setImageResource(R.mipmap.wechat_pay_erro);
                mTv_result_show.setText("—支付异常—");
                Toast.makeText(WXPayEntryActivity.this, "支付异常,请重试！", Toast.LENGTH_SHORT).show();
                break;
            case -2:
                //用户主动取消微信支付
                Log.e("onResponse ", "调用微信支付结果取消;errCode " + resp.errCode);
                mWechatPayResultCode = -2;
                mTv_btn_wechat_pay_result.setText("返回");
                mIv_result_img.setImageResource(R.mipmap.wechat_pay_erro);
                mTv_result_show.setText("—您已取消微信支付—");
                Toast.makeText(WXPayEntryActivity.this, "您已取消微信支付", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 获取本地存储的outTradeNo与bookLogAID
     */
    public void getSharedPreferencesForCheck() {
        SharedPreferences sp = getSharedPreferences(Constant.WechatPay.ABOUT_WECHAT_PAY, Context.MODE_PRIVATE);
        mWechatPayOutTrandeNo = sp.getString(Constant.WechatPay.ABOUT_WECHAT_PAY_OUT_TRADE_NO, null);
        mWechatPayBookLogaId = sp.getString(Constant.WechatPay.ABOUT_WECHAT_PAY_BOOKLOGAID, null);
        mWechatPayOrderId = sp.getString(Constant.WechatPay.ABOUT_WECHAT_PAY_ORDERID, null);
        mWechatPayRedId = sp.getInt(Constant.WechatPay.ABOUT_WECHAT_PAY_REDID, -1);
        mRealPayPrice = sp.getString(Constant.WechatPay.ABOUT_WECHAT_PAY_REALPAYPRICE, null);
        Log.e("onResponse ", "mWechatPayOutTrandeNo " + mWechatPayOutTrandeNo);
        Log.e("onResponse ", "mWechatPayBookLogaId " + mWechatPayBookLogaId);
        Log.e("onResponse ", "mWechatPayOrderId " + mWechatPayOrderId);
        Log.e("onResponse ", "mWechatPayRedId " + mWechatPayRedId);
    }

    /**
     * 微信支付：向后台服务端查询实际的支付结果
     */
    private void checkOrderResult(String outTrandeNo) {
        mChedckOrderResultParams = new HashMap<>();
        Log.e("checkOrderResult ", "checkOrderResultOutTrandeNo " + outTrandeNo);
        mChedckOrderResultParams.put("out_trade_no", outTrandeNo);
        HTTPUtils.post(WXPayEntryActivity.this, Constant.WechatPay.CHECKED_WECHAT_ORDER_RESULT_URL, mChedckOrderResultParams, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }

            @Override
            public void onResponse(String s) {
                Log.e("onResponse ", "向后台服务端查询实际的支付结果 " + s);
                if ("false".equals(s)) {
                    mWechatPayResultCode = -1;
                    mTv_btn_wechat_pay_result.setText("返回");
                    mIv_result_img.setImageResource(R.mipmap.wechat_pay_erro);
                    mTv_result_show.setText("—支付失败—");
                    Toast.makeText(WXPayEntryActivity.this, "支付失败,请重试！", Toast.LENGTH_SHORT).show();
                } else {
                    mWechatPayResultCode = 0;
                    mTv_btn_wechat_pay_result.setEnabled(false);
                    mTv_btn_wechat_pay_result.setVisibility(View.GONE);
                    mIv_result_img.setImageResource(R.mipmap.wechat_pay_ok);
                    mTv_result_show.setText("—支付成功—\n出票中，请稍候……");
                    confrimOrder();
                }
            }
        });
    }

    /**
     * 支付成功，确认订单;向后台发送所用的红包/订单id/实际支付金额
     */
    private void confrimOrder() {
        mTv_btn_wechat_pay_result.setVisibility(View.VISIBLE);
        String url01 = Constant.HOST_TICKET + "/order/completeorder";
        Map<String, String> map = new HashMap<>();
        if (-1 != mWechatPayRedId) {
            map.put("redEnvelope_id", mWechatPayRedId + "");
        } else {
            map.put("order_id", mWechatPayOrderId);
            map.put("real_pay", mRealPayPrice + "");
        }
        map.put("pay_model","微信支付");
        map.put("serial",mWechatPayOutTrandeNo);
        HTTPUtils.post(WXPayEntryActivity.this, url01, map, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }

            @Override
            public void onResponse(String s) {
//                if (isQianTaiComfrim) {
                    //0是成功 1是异常
                    if ("0".equals(s)) {
                        Log.e("onResponse ", "onResponse 没有延迟的订单确认" + s);
                        //没有延迟的订单确认
                        mTv_btn_wechat_pay_result.setEnabled(true);
                        mTv_btn_wechat_pay_result.setText("点击查看订单详情");
                        mTv_result_show.setText("—支付成功—\n出票成功!");
                    } else if ("1".equals(s)) {
                        mWechatPayResultCode = 1;
                        mTv_btn_wechat_pay_result.setEnabled(true);
                        mTv_btn_wechat_pay_result.setText("返回");
                        mTv_result_show.setText("—支付成功—\n出票出现异常\n" + "请联系客服(400-0593-330)");
                    } else {
                        //有延迟的订单确认
                        mWechatPayResultCode = 2;
                        mTv_btn_wechat_pay_result.setEnabled(true);
                        mTv_btn_wechat_pay_result.setText("返回");
                        mTv_result_show.setText("—支付成功—\n系统出票中，请稍候在订单详情中查询!");

                    }
                }
//            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_btn_wechat_pay_result:
                switch (mWechatPayResultCode) {
                    case 0:
                        Intent intent = new Intent();
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("BookLogAID", mWechatPayBookLogaId);
                        intent.setClass(WXPayEntryActivity.this, OrderDeatilActivity.class);
                        startActivity(intent);
                        animFromBigToSmallOUT();
                        break;
                    case -1:
                        //微信支付失败
                        Intent intent_erro = new Intent();
                        intent_erro.putExtra("OrderDeatilActivity", "OrderDeatilActivity");
                        intent_erro.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent_erro.setClass(WXPayEntryActivity.this, MainActivity.class);
                        startActivity(intent_erro);
                        finish();
                        break;
                    case -2:
                        //微信支付用户取消支付
                        Intent intent_cancel = new Intent();
                        intent_cancel.putExtra("OrderDeatilActivity", "OrderDeatilActivity");
                        intent_cancel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent_cancel.setClass(WXPayEntryActivity.this, MainActivity.class);
                        startActivity(intent_cancel);
                        finish();
                        break;
                    case 1:
                        //订单出现异常，请联系客服！
                        Intent intent_err = new Intent();
                        intent_err.putExtra("OrderDeatilActivity", "OrderDeatilActivity");
                        intent_err.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent_err.setClass(WXPayEntryActivity.this, MainActivity.class);
                        startActivity(intent_err);
                        finish();
                        break;
                    case 2:
                        //有延迟的订单
                        Intent intent1 = new Intent();
                        intent1.putExtra("OrderDeatilActivity", "OrderDeatilActivity");
                        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent1.setClass(WXPayEntryActivity.this, MainActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                }
                break;
            case R.id.iv_wechat_pay_back:
                if (mWechatPayResultCode == 0) {
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("BookLogAID", mWechatPayBookLogaId);
                    intent.setClass(WXPayEntryActivity.this, OrderDeatilActivity.class);
                    startActivity(intent);
                    animFromBigToSmallOUT();
                } else {
                    finish();
                }
                break;
        }
    }

    /**
     * 从大到小结束动画
     */
    private void animFromBigToSmallOUT() {
        overridePendingTransition(R.anim.fade_in, R.anim.big_to_small_fade_out);
    }
}
