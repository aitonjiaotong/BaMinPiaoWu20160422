package bamin.com.kepiao.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aiton.administrator.shane_library.shane.utils.GsonUtils;
import com.aiton.administrator.shane_library.shane.utils.HTTPUtils;
import com.aiton.administrator.shane_library.shane.utils.VolleyListener;
import com.android.volley.VolleyError;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import bamin.com.kepiao.R;
import bamin.com.kepiao.constant.EverythingConstant;
import bamin.com.kepiao.models.User;
import bamin.com.kepiao.utils.Installation;
import bamin.com.kepiao.utils.IsMobileNOorPassword;
import bamin.com.kepiao.utils.SubJsonStr;


public class UpdatePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout mLl_sendSMS;
    private LinearLayout mLl_editSMS;
    private LinearLayout mLl_register;
    private EditText mEditText_phoneNum;
    private Button mButton_sendSMS;
    private int mTitle_bar;
    private String mPhoneNum;
    private int[] mI;
    private Runnable mR;
    private int mBlack;
    private TextView mTextView_inputPhoneNum;
    private TextView mTextView_inputSMS;
    private TextView mTextView_inputPassword;
    private Button mButton_reSend;
    private Button mButton_commitSms;
    private EditText mEditText_sms;
    private EditText mEditText_password01;
    private EditText mEditText_password02;
    private User mUser;
    private String mFindBackPassword;
    private TextView mTextView_title;
    private String mSuijiMath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        initIntent();
        findID();
        initColor();
        initUI();
        setListener();
    }

    private void initIntent() {
        Intent intent = getIntent();
        mFindBackPassword = intent.getStringExtra("findBackPassword");
    }


    private void toast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(UpdatePasswordActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initColor() {
        mTitle_bar = getResources().getColor(R.color.title_bar);
        mBlack = getResources().getColor(R.color.black);
    }

    private void setListener() {
        mEditText_phoneNum.addTextChangedListener(new PhoneNumTextWatcher());
        mButton_sendSMS.setOnClickListener(this);
        mButton_reSend.setOnClickListener(this);
        mButton_commitSms.setOnClickListener(this);
        findViewById(R.id.button_zuce).setOnClickListener(this);
        findViewById(R.id.imageView_back).setOnClickListener(this);
    }

    private void initUI() {
        if ("findBackPassword".equals(mFindBackPassword)) {
            mTextView_title.setText("修改密码");
        }
    }

    private void findID() {
        mLl_sendSMS = (LinearLayout) findViewById(R.id.ll_sendSMS);
        mLl_editSMS = (LinearLayout) findViewById(R.id.ll_editSMS);
        mLl_register = (LinearLayout) findViewById(R.id.ll_register);
        mEditText_phoneNum = (EditText) findViewById(R.id.editText_phoneNum);
        mButton_sendSMS = (Button) findViewById(R.id.button_sendSMS);
        mTextView_inputPhoneNum = (TextView) findViewById(R.id.textView_inputPhoneNum);
        mTextView_inputSMS = (TextView) findViewById(R.id.textView_inputSMS);
        mTextView_inputPassword = (TextView) findViewById(R.id.textView_inputPassword);
        mButton_reSend = (Button) findViewById(R.id.button_reSend);
        mButton_commitSms = (Button) findViewById(R.id.button_commitSms);
        mEditText_sms = (EditText) findViewById(R.id.editText_SMS);
        mEditText_password01 = (EditText) findViewById(R.id.editText_password01);
        mEditText_password02 = (EditText) findViewById(R.id.editText_password02);
        mTextView_title = (TextView) findViewById(R.id.textView_title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_back:
                finish();
                animFromBigToSmallOUT();
                break;
            case R.id.button_zuce:
                String password01 = mEditText_password01.getText().toString().trim();
                String password02 = mEditText_password02.getText().toString().trim();
                if (password01.equals(password02)) {
                    boolean isPassword = IsMobileNOorPassword.isPassword(password01);
                    if (isPassword) {
                        //每次存储唯一标识
                        final String DeviceId = Installation.id(UpdatePasswordActivity.this);
                        String url = EverythingConstant.HOST + "/bmpw/front/account/updatepassword";
                        Map<String, String> map = new HashMap<>();
                        map.put("phone", mPhoneNum);
                        map.put("login_id", DeviceId);
                        map.put("password", password01);
                        map.put("flag", "1");
                        HTTPUtils.post(UpdatePasswordActivity.this, url, map, new VolleyListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                            }

                            @Override
                            public void onResponse(String s) {
                                if ("".equals(s)) {
                                    toast("密码修改失败");
                                } else {
                                    mUser = GsonUtils.parseJSON(s, User.class);
                                    //存储手机号和用户id到本地
                                    SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
                                    SharedPreferences.Editor edit = sp.edit();
                                    edit.putString("phoneNum", mPhoneNum);
                                    edit.putString("id", mUser.getId() + "");
                                    edit.putString("DeviceId", DeviceId);
                                    edit.putString("image",mUser.getImage());
                                    Log.e("onResponse", "图片地址" + mUser.getImage());
                                    edit.commit();
                                    if ("findBackPassword".equals(mFindBackPassword)) {
                                        toast("修改成功");
                                    } else {
                                        toast("登录成功");
                                    }
                                    //友盟统计
                                    MobclickAgent.onProfileSignIn(mPhoneNum);
                                    Intent intent = new Intent();
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.setClass(UpdatePasswordActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    } else {
                        toast("密码必须大于6位，且由字母及数字组合");
                    }
                } else {
                    toast("两次密码输入不一致");
                }
                break;
            case R.id.button_commitSms:
                if (mSuijiMath.equals(mEditText_sms.getText().toString().trim())){
                    toast("短信验证成功");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLl_editSMS.setVisibility(View.GONE);
                            mLl_register.setVisibility(View.VISIBLE);
                            mTextView_inputSMS.setTextColor(mBlack);
                            mTextView_inputPassword.setTextColor(mTitle_bar);
                        }
                    });
                }else {
                    toast("短信验证失败");
                }
                break;
            case R.id.button_sendSMS:
                mPhoneNum = mEditText_phoneNum.getText().toString().trim();
                boolean mobileNO = IsMobileNOorPassword.isMobileNO(mPhoneNum);
                if (mobileNO) {
                    sendSMS();
                } else {
                    Toast.makeText(UpdatePasswordActivity.this, "输入的手机格式有误", Toast.LENGTH_SHORT).show();
                    mEditText_phoneNum.setText("");
                }
                break;
            case R.id.button_reSend:
                sendSMS();
                break;
        }
    }

    private void sendSMS() {
        mI = new int[]{60};
        mLl_sendSMS.setVisibility(View.GONE);
        mLl_editSMS.setVisibility(View.VISIBLE);
        mTextView_inputSMS.setTextColor(mTitle_bar);
        mTextView_inputPhoneNum.setTextColor(mBlack);
        mButton_reSend.setEnabled(false);
        mR = new Runnable() {
            @Override
            public void run() {
                mButton_reSend.setText((mI[0]--) + "秒后重发");
                if (mI[0] == 0) {
                    mButton_reSend.setEnabled(true);
                    mButton_reSend.setText("获取验证码");
                    return;
                } else {
                }
                mButton_reSend.postDelayed(mR, 1000);
            }
        };
        mButton_reSend.postDelayed(mR, 0);
        mButton_commitSms.setEnabled(true);
        getSms();
    }
    private void getSms() {
        mSuijiMath = (int) (Math.random() * 9000 + 1000) + "";
        String url = "http://120.55.166.203:8010" + "/aiton-app-webapp/public/sendmessage";
        Log.e("getSms", "短信连接" + url);
        Map<String, String> map = new HashMap<>();
        map.put("phone",mPhoneNum);
        map.put("code",mSuijiMath+"");
        HTTPUtils.post(UpdatePasswordActivity.this, url, map, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }

            @Override
            public void onResponse(String s) {
                Log.e("onResponse", "短信返回值" + s);
                String dislodgeHeadTag = SubJsonStr.dislodgeHeadTag(s);
                String dislodgeHeadTag1 = SubJsonStr.dislodgeHeadTag(dislodgeHeadTag);
                if ("03".equals(dislodgeHeadTag1) || "0".equals(dislodgeHeadTag1)) {
                    toast("获取验证码成功");
                } else if ("02".equals(dislodgeHeadTag1)) {
                    toast("IP限制");
                } else if ("04".equals(dislodgeHeadTag1)) {
                    toast("用户名错误");
                } else if ("05".equals(dislodgeHeadTag1)) {
                    toast("密码错误");
                } else if ("07".equals(dislodgeHeadTag1)) {
                    toast("发送时间错误");
                } else if ("08".equals(dislodgeHeadTag1)) {
                    toast("信息内容为黑内容");
                } else if ("09".equals(dislodgeHeadTag1)) {
                    toast("该用户的该内容 受同天内，内容不能重复发 限制");
                } else if ("10".equals(dislodgeHeadTag1)) {
                    toast("扩展号错误");
                } else if ("97".equals(dislodgeHeadTag1)) {
                    toast("短信参数有误");
                } else if ("11".equals(dislodgeHeadTag1)) {
                    toast("余额不足");
                } else if ("-1".equals(dislodgeHeadTag1)) {
                    toast("程序异常");
                }
            }
        });
    }
    class PhoneNumTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 11) {
                mButton_sendSMS.setEnabled(true);
            } else {
                mButton_sendSMS.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
    /**
     * 从大到小结束动画
     */
    private void animFromBigToSmallOUT() {
        overridePendingTransition(R.anim.fade_in, R.anim.big_to_small_fade_out);
    }

    @Override
    public void onBackPressed() {
        finish();
        animFromBigToSmallOUT();
    }
}
