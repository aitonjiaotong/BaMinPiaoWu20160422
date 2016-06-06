package bamin.com.kepiao.wxapi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler, View.OnClickListener
{
    private String TAG = "WXPayEntryActivity";
    private IWXAPI api;
    private String mWechatPayOutTrandeNo;
    private String mWechatPayBookLogaId;
    private Map<String, String> mChedckOrderResultParams;
    private String mWechatPayOrderId;
    private int mWechatPayRedId;
    private int mWechatPayResultCode;
    private ImageView mIv_wechat_pay_back;
    private String mRealPayPrice;
    private ImageView mIv_order_pay_result_img;
    private RelativeLayout mRl_order_pay_info_bg;
    private TextView mTv_order_pay_success_title;
    private TextView mTv_order_pay_success_subtitle;
    private ProgressDialog mProgressDialog;
    private TextView mTv_btn_wechat_pay_result;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);
        findViewID();
        setListener();
        initUI();
        api = WXAPIFactory.createWXAPI(this, Constant.WechatPay.APP_ID);
        api.handleIntent(getIntent(), this);

    }

    private void initUI()
    {
        mProgressDialog = new ProgressDialog(WXPayEntryActivity.this);
        mProgressDialog.setMessage("请稍候,订单信息确认中…");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        mRl_order_pay_info_bg.setVisibility(View.GONE);
    }

    private void setListener()
    {
        mIv_wechat_pay_back.setOnClickListener(this);
        mTv_btn_wechat_pay_result.setOnClickListener(this);
    }

    private void findViewID()
    {
        mIv_wechat_pay_back = (ImageView) findViewById(R.id.iv_wechat_pay_back);
        mIv_order_pay_result_img = (ImageView) findViewById(R.id.iv_order_pay_result_img);
        mRl_order_pay_info_bg = (RelativeLayout) findViewById(R.id.rl_order_pay_info_bg);
        mTv_order_pay_success_title = (TextView) findViewById(R.id.tv_order_pay_success_title);
        mTv_order_pay_success_subtitle = (TextView) findViewById(R.id.tv_order_pay_success_subtitle);
        mTv_btn_wechat_pay_result = (TextView) findViewById(R.id.tv_btn_wechat_pay_result);
    }

    @Override
    public void onReq(BaseReq baseReq)
    {

    }

    @Override
    public void onResp(BaseResp resp)
    {
        Log.e(TAG, "onResp: --微信支付返回码->>" + resp.errCode);

        switch (resp.errCode)
        {
            case 0:
                //调用微信支付结果成功
                getSharedPreferencesForCheck();
                if (mWechatPayOutTrandeNo != null && !"".equals(mWechatPayOutTrandeNo))
                {
                    confrimOrder();
                }
                break;
            case -1:
                //微信支付异常
                mWechatPayResultCode = -1;
                mProgressDialog.dismiss();
                mRl_order_pay_info_bg.setVisibility(View.VISIBLE);
                mIv_order_pay_result_img.setImageResource(R.mipmap.ic_failure);
                mRl_order_pay_info_bg.setBackgroundResource(R.color.order_failure);
                mTv_order_pay_success_title.setText(R.string.order_failure_title);
                mTv_order_pay_success_subtitle.setText(R.string.order_failure);

                break;
            case -2:
                //用户主动取消微信支付
                mWechatPayResultCode = -2;
                mProgressDialog.dismiss();
                mRl_order_pay_info_bg.setVisibility(View.VISIBLE);
                mIv_order_pay_result_img.setImageResource(R.mipmap.ic_failure);
                mRl_order_pay_info_bg.setBackgroundResource(R.color.order_failure);
                mTv_order_pay_success_title.setText(R.string.order_cancel_title);
                mTv_order_pay_success_subtitle.setText(R.string.order_cancel);
                break;
        }
    }

    /**
     * 获取本地存储的outTradeNo与bookLogAID
     */
    public void getSharedPreferencesForCheck()
    {
        SharedPreferences sp = getSharedPreferences(Constant.WechatPay.ABOUT_WECHAT_PAY, Context.MODE_PRIVATE);
        mWechatPayOutTrandeNo = sp.getString(Constant.WechatPay.ABOUT_WECHAT_PAY_OUT_TRADE_NO, null);
        mWechatPayBookLogaId = sp.getString(Constant.WechatPay.ABOUT_WECHAT_PAY_BOOKLOGAID, null);
        mWechatPayOrderId = sp.getString(Constant.WechatPay.ABOUT_WECHAT_PAY_ORDERID, null);
        mWechatPayRedId = sp.getInt(Constant.WechatPay.ABOUT_WECHAT_PAY_REDID, -1);
        mRealPayPrice = sp.getString(Constant.WechatPay.ABOUT_WECHAT_PAY_REALPAYPRICE, null);
        Log.e(TAG, "getSharedPreferencesForCheck: --mWechatPayOutTrandeNo->>" + mWechatPayOutTrandeNo);
        Log.e(TAG, "getSharedPreferencesForCheck: --mWechatPayBookLogaId->>" + mWechatPayBookLogaId);
        Log.e(TAG, "getSharedPreferencesForCheck: --mWechatPayOrderId->>" + mWechatPayOrderId);
        Log.e(TAG, "getSharedPreferencesForCheck: --mWechatPayRedId->>" + mWechatPayRedId);

    }


    /**
     * 支付成功，确认订单;向后台发送所用的红包/订单id/实际支付金额
     */
    private void confrimOrder()
    {
        String url01 = Constant.HOST_TICKET + "/order/completeorder";
        Map<String, String> map = new HashMap<>();
        if (-1 != mWechatPayRedId)
        {
            map.put("redEnvelope_id", mWechatPayRedId + "");
        } else
        {
            map.put("id", mWechatPayOrderId);
            map.put("real_pay", mRealPayPrice + "");
        }
        map.put("pay_model", "微信支付");
        map.put("serial", mWechatPayOutTrandeNo);
        HTTPUtils.post(WXPayEntryActivity.this, url01, map, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
            }

            @Override
            public void onResponse(String s)
            {
                Log.e(TAG, "onResponse: --向后台提交订单确认->>" + s);

                //0是成功 1是异常
                if ("0".equals(s))
                {
                    //没有延迟的订单确认
                    mProgressDialog.dismiss();
                    mRl_order_pay_info_bg.setVisibility(View.VISIBLE);
                    mIv_order_pay_result_img.setImageResource(R.mipmap.ic_successful);
                    mRl_order_pay_info_bg.setBackgroundResource(R.color.order_success);
                    mTv_order_pay_success_title.setText(R.string.order_success_title);
                    mTv_order_pay_success_subtitle.setText(R.string.order_success);

                } else if ("1".equals(s))
                {
                    //异常订单
                    mWechatPayResultCode = 1;
                    mProgressDialog.dismiss();
                    mRl_order_pay_info_bg.setVisibility(View.VISIBLE);
                    mIv_order_pay_result_img.setImageResource(R.mipmap.ic_failure);
                    mRl_order_pay_info_bg.setBackgroundResource(R.color.order_failure);
                    mTv_order_pay_success_title.setText(R.string.order_failure_title);
                    mTv_order_pay_success_subtitle.setText(R.string.order_failure);
                } else
                {
                    //有延迟的订单确认
                    mWechatPayResultCode = 2;
                    mProgressDialog.dismiss();
                    mRl_order_pay_info_bg.setVisibility(View.VISIBLE);
                    mIv_order_pay_result_img.setImageResource(R.mipmap.ic_successful);
                    mRl_order_pay_info_bg.setBackgroundResource(R.color.order_success);
                    mTv_order_pay_success_title.setText(R.string.order_success_title);
                    mTv_order_pay_success_subtitle.setText(R.string.order_success);
                }
            }
        });


    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_btn_wechat_pay_result:
                switch (mWechatPayResultCode)
                {
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
                if (mWechatPayResultCode == 0)
                {
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("BookLogAID", mWechatPayBookLogaId);
                    intent.setClass(WXPayEntryActivity.this, OrderDeatilActivity.class);
                    startActivity(intent);
                    animFromBigToSmallOUT();
                } else
                {
                    finish();
                }
                break;
        }
    }

    /**
     * 从大到小结束动画
     */
    private void animFromBigToSmallOUT()
    {
        overridePendingTransition(R.anim.fade_in, R.anim.big_to_small_fade_out);
    }
}