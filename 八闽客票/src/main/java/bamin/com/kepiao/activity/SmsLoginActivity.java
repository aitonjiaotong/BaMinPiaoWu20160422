package bamin.com.kepiao.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aiton.administrator.shane_library.shane.utils.GsonUtils;
import com.aiton.administrator.shane_library.shane.utils.HTTPUtils;
import com.aiton.administrator.shane_library.shane.utils.VolleyListener;
import com.android.volley.VolleyError;
import com.umeng.analytics.MobclickAgent;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import bamin.com.kepiao.R;
import bamin.com.kepiao.constant.EverythingConstant;
import bamin.com.kepiao.models.User;
import bamin.com.kepiao.utils.Installation;
import bamin.com.kepiao.utils.IsMobileNOorPassword;
import bamin.com.kepiao.utils.SmsContent;

public class SmsLoginActivity extends AppCompatActivity implements View.OnClickListener
{

    private ImageView mPhone_num_cancle;
    private ImageView mSms_cancle;
    private EditText mPhone_num;
    private EditText mSms;
    private Button mSendSms;
    private Button mLogin;
    private Runnable mR;
    private int[] mI;
    private boolean isSend = false;
    private String mPhoneNum;
    private User mUser;
    private TextView mTextView_quickLogin;
    private TextView mTextView_accountLogin;
    private ImageView mImageView_tiao01;
    private int basicColor;
    private int offset;
    private int mSystem_gray;
    private LinearLayout mLinear_quickLogin;
    private LinearLayout mLinear_accountLogin;
    private TextView mTextView_register;
    private EditText mEditText_phoneNum;
    private EditText mEditText_password;
    private boolean isQuickLogin = true;
    private String mSuijiMath;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_login);
        findID();
        initUI();
        setListener();
        initAnim();
    }

    private void initUI()
    {
        basicColor = getResources().getColor(R.color.title_bar);
        mSystem_gray = getResources().getColor(R.color.black);
        SmsContent smsContent = new SmsContent(SmsLoginActivity.this, new Handler(), mSms);
        // 注册短信变化监听
        this.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, smsContent);
    }

//    private void sms() {
//        mEh = new EventHandler() {
//            @Override
//            public void afterEvent(int event, int result, Object data) {
//                switch (event) {
//                    case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
//                        if (result == SMSSDK.RESULT_COMPLETE) {
//                            toast("短信验证成功");
//                            //每次存储唯一标识
//                            final String DeviceId = Installation.id(SmsLoginActivity.this);
//                            //向后台服务推送用户短信验证成功，发送手机号----start----//
//                            String url = EverythingConstant.HOST + "/bmpw/front/FrontLogin?phone=" + mPhoneNum + "&login_id=" + DeviceId;
//                            HTTPUtils.get(SmsLoginActivity.this, url, new VolleyListener() {
//                                public void onErrorResponse(VolleyError volleyError) {
//                                }
//
//                                public void onResponse(String s) {
//                                    mUser = GsonUtils.parseJSON(s, User.class);
//                                    //存储手机号和用户id到本地
//                                    SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
//                                    SharedPreferences.Editor edit = sp.edit();
//                                    edit.putString("phoneNum", mPhoneNum);
//                                    edit.putString("id", mUser.getId() + "");
//                                    edit.putString("DeviceId", DeviceId);
//                                    edit.commit();
//                                    //友盟统计
//                                    MobclickAgent.onProfileSignIn(mPhoneNum);
//                                    finish();
//                                }
//                            });
//                            //向后台服务推送用户短信验证成功，发送手机号----end----//
//                        } else {
//                            toast("短信验证失败");
//                        }
//                        break;
//                    case SMSSDK.EVENT_GET_VERIFICATION_CODE:
//                        if (result == SMSSDK.RESULT_COMPLETE) {
//                            toast("获取验证码成功");
//                        } else {
//                            toast("获取验证码失败" + "登录过于频繁，12小时后再试");
//                        }
//                        break;
//                }
//            }
//        };
//        SMSSDK.registerEventHandler(mEh);
//    }

    private void toast(final String str)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(SmsLoginActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    private void findID()
    {
        mPhone_num_cancle = (ImageView) findViewById(R.id.phone_num_cancle);
        mSms_cancle = (ImageView) findViewById(R.id.sms_cancle);
        mPhone_num = (EditText) findViewById(R.id.phone_num);
        mSms = (EditText) findViewById(R.id.sms);
        mSendSms = (Button) findViewById(R.id.sendSms);
        mLogin = (Button) findViewById(R.id.login);
        mTextView_quickLogin = (TextView) findViewById(R.id.textView_quickLogin);
        mTextView_accountLogin = (TextView) findViewById(R.id.textView_accountLogin);
        mImageView_tiao01 = (ImageView) findViewById(R.id.imageView_tiao01);
        mLinear_quickLogin = (LinearLayout) findViewById(R.id.linear_quickLogin);
        mLinear_accountLogin = (LinearLayout) findViewById(R.id.linear_accountLogin);
        mTextView_register = (TextView) findViewById(R.id.textView_register);
        mEditText_phoneNum = (EditText) findViewById(R.id.editText_phoneNum);
        mEditText_password = (EditText) findViewById(R.id.editText_password);
    }

    private void setListener()
    {
        mLogin.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        mPhone_num.addTextChangedListener(new MyPhoneNumTextWatcher());
        mSms.addTextChangedListener(new MySmsTextWatcher());
        mPhone_num_cancle.setOnClickListener(this);
        mSms_cancle.setOnClickListener(this);
        mSendSms.setOnClickListener(this);
        mTextView_quickLogin.setOnClickListener(this);
        mTextView_accountLogin.setOnClickListener(this);
        mTextView_register.setOnClickListener(this);
        findViewById(R.id.textView_frogetPassword).setOnClickListener(this);
    }

    private void initAnim()
    {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = screenW / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        mImageView_tiao01.setImageMatrix(matrix);// 设置动画初始位置
    }

    class MyPhoneNumTextWatcher implements TextWatcher
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (s.length() > 0)
            {
                mPhone_num_cancle.setVisibility(View.VISIBLE);

            } else
            {
                mPhone_num_cancle.setVisibility(View.INVISIBLE);
            }
            if (s.length() == 11)
            {
                mSms.requestFocus();
                if (!isSend)
                {
                    mSendSms.setEnabled(true);
                }
            } else
            {
                mSendSms.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s)
        {

        }
    }

    class MySmsTextWatcher implements TextWatcher
    {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (s.length() > 0)
            {
                mSms_cancle.setVisibility(View.VISIBLE);
            } else
            {
                mSms_cancle.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s)
        {

        }
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.textView_frogetPassword:
                intent.setClass(SmsLoginActivity.this, UpdatePasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.textView_register:
                intent.setClass(SmsLoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.textView_quickLogin:
                isQuickLogin = true;
                mTextView_quickLogin.setTextColor(basicColor);
                mTextView_accountLogin.setTextColor(mSystem_gray);
                Animation animation = new TranslateAnimation(offset, 0, 0, 0);
                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(300);
                mImageView_tiao01.startAnimation(animation);
                mLogin.setVisibility(View.GONE);
                mLinear_quickLogin.setVisibility(View.VISIBLE);
                mLinear_accountLogin.setVisibility(View.INVISIBLE);
                break;
            case R.id.textView_accountLogin:
                isQuickLogin = false;
                mTextView_accountLogin.setTextColor(basicColor);
                mTextView_quickLogin.setTextColor(mSystem_gray);
                Animation animation02 = new TranslateAnimation(0, offset, 0, 0);
                animation02.setFillAfter(true);// True:图片停在动画结束位置
                animation02.setDuration(300);
                mImageView_tiao01.startAnimation(animation02);
                mLogin.setVisibility(View.VISIBLE);
                mLinear_quickLogin.setVisibility(View.INVISIBLE);
                mLinear_accountLogin.setVisibility(View.VISIBLE);
                break;
            case R.id.login:
                if (isQuickLogin)
                {
//                    SMSSDK.submitVerificationCode("+86", mPhoneNum, mSms.getText().toString().trim());
                    if (mSuijiMath.equals(mSms.getText().toString().trim()))
                    {
                        toast("短信验证成功");
                        //每次存储唯一标识
                        final String DeviceId = Installation.id(SmsLoginActivity.this);
                        //向后台服务推送用户短信验证成功，发送手机号----start----//
                        String url = EverythingConstant.HOST + "/bmpw/front/FrontLogin?phone=" + mPhoneNum + "&login_id=" + DeviceId + "&flag=" + "1";
                        HTTPUtils.get(SmsLoginActivity.this, url, new VolleyListener()
                        {
                            public void onErrorResponse(VolleyError volleyError)
                            {
                            }

                            public void onResponse(String s)
                            {
                                mUser = GsonUtils.parseJSON(s, User.class);
                                //存储手机号和用户id到本地
                                SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putString("phoneNum", mPhoneNum);
                                edit.putString("id", mUser.getId() + "");
                                edit.putString("DeviceId", DeviceId);
                                edit.commit();
                                //友盟统计
                                MobclickAgent.onProfileSignIn(mPhoneNum);
                                finish();
                            }
                        });
                        //向后台服务推送用户短信验证成功，发送手机号----end----//
                    } else
                    {
                        toast("短信验证失败");
                    }
                } else
                {
                    final String phone = mEditText_phoneNum.getText().toString().trim();
                    String password = mEditText_password.getText().toString().trim();
                    //每次存储唯一标识
                    final String DeviceId = Installation.id(SmsLoginActivity.this);
                    String url = EverythingConstant.HOST + "/bmpw/front/account/loginbypassword";
                    Map<String, String> map = new HashMap<>();
                    map.put("phone", phone);
                    map.put("login_id", DeviceId);
                    map.put("password", password);
                    map.put("flag", "1");
                    HTTPUtils.post(SmsLoginActivity.this, url, map, new VolleyListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError volleyError)
                        {

                        }

                        @Override
                        public void onResponse(String s)
                        {
                            if ("".equals(s))
                            {
                                toast("用户名或密码错误");
                            } else
                            {
                                mUser = GsonUtils.parseJSON(s, User.class);
                                //存储手机号和用户id到本地
                                SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
                                SharedPreferences.Editor edit = sp.edit();
                                edit.putString("phoneNum", phone);
                                edit.putString("id", mUser.getId() + "");
                                edit.putString("DeviceId", DeviceId);
                                Log.e("onResponse ", "DeviceId " + DeviceId);
                                edit.commit();
                                toast("登录成功");
                                //友盟统计
                                MobclickAgent.onProfileSignIn(mPhoneNum);
                                Intent intent = new Intent();
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.setClass(SmsLoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
                break;
            case R.id.iv_back:
                finish();
                AnimFromRightToLeft();
                break;
            case R.id.phone_num_cancle:
                mPhone_num.setText("");
                break;
            case R.id.sms_cancle:
                mSms.setText("");
                break;
            case R.id.sendSms:
                sendSMS();
                break;
        }
    }

    private void sendSMS()
    {
        mPhoneNum = mPhone_num.getText().toString().trim();
        boolean mobileNO = IsMobileNOorPassword.isMobileNO(mPhoneNum);
        if (mobileNO)
        {
            mSendSms.setEnabled(false);
            mI = new int[]{60};

            mR = new Runnable()
            {
                @Override
                public void run()
                {
                    mSendSms.setText((mI[0]--) + "秒后重发");
                    if (mI[0] == 0)
                    {
                        mSendSms.setEnabled(true);
                        mSendSms.setText("获取验证码");
                        isSend = false;
                        return;
                    } else
                    {
                        isSend = true;
                    }
                    mSendSms.postDelayed(mR, 1000);
                }
            };
            mSendSms.postDelayed(mR, 0);
            mLogin.setVisibility(View.VISIBLE);
            getSms();
        } else
        {
            Toast.makeText(SmsLoginActivity.this, "输入的手机格式有误", Toast.LENGTH_SHORT).show();
            mPhone_num.setText("");
        }
    }

    private void getSms()
    {
        mSuijiMath = (int) (Math.random() * 9000 + 1000) + "";
        String url = null;
        try
        {
            url = "http://221.179.180.158:9007/QxtSms/QxtFirewall?OperID=gaosukeyun&OperPass=EEf70kad&SendTime=&ValidTime=&AppendID=1234&DesMobile=" + mPhoneNum + "&Content=" + URLEncoder.encode("【八闽集团】验证码是", "gbk") + mSuijiMath + URLEncoder.encode(".（切勿告知他人，验证码5分钟内有效）", "gbk");
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        HTTPUtils.get(SmsLoginActivity.this, url, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {

            }

            @Override
            public void onResponse(String s)
            {
                String substring = s.substring(17, 19);
                Log.e("onResponse", "" + substring);
                if ("03".equals(substring) || "0\"".equals(substring))
                {
                    toast("获取验证码成功");
                } else if ("02".equals(substring))
                {
                    toast("IP限制");
                } else if ("04".equals(substring))
                {
                    toast("用户名错误");
                } else if ("05".equals(substring))
                {
                    toast("密码错误");
                } else if ("07".equals(substring))
                {
                    toast("发送时间错误");
                } else if ("08".equals(substring))
                {
                    toast("信息内容为黑内容");
                } else if ("09".equals(substring))
                {
                    toast("该用户的该内容 受同天内，内容不能重复发 限制");
                } else if ("10".equals(substring))
                {
                    toast("扩展号错误");
                } else if ("97".equals(substring))
                {
                    toast("短信参数有误");
                } else if ("11".equals(substring))
                {
                    toast("余额不足");
                } else if ("-1".equals(substring))
                {
                    toast("程序异常");
                }
            }
        });
    }

    private void AnimFromRightToLeft()
    {
        overridePendingTransition(R.anim.fade_in, R.anim.push_left_out);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
            AnimFromRightToLeft();
        }
        return super.onKeyDown(keyCode, event);
    }

    ;

    public void onResume()
    {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause()
    {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
