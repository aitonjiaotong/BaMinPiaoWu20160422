package bamin.com.kepiao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aiton.administrator.shane_library.shane.utils.GsonUtils;
import com.aiton.administrator.shane_library.shane.utils.HTTPUtils;
import com.aiton.administrator.shane_library.shane.utils.VolleyListener;
import com.android.volley.VolleyError;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bamin.com.kepiao.R;
import bamin.com.kepiao.constant.Constant;
import bamin.com.kepiao.models.about_used_contact.UsedContactInfo;
import bamin.com.kepiao.models.about_used_contact.UsedPersonID;
import bamin.com.kepiao.utils.DialogShow;
import bamin.com.kepiao.utils.IsMobileNOorPassword;

/**
 * 优化1.0
 */
public class AddFetcherActivity extends Activity implements View.OnClickListener {

    private TextView mCancel;
    private EditText mPassager_name;
    private EditText mPerson_id;
    private EditText mPassager_phoneNum;
    private String mBianji;
    private UsedContactInfo mTicketPassager;
    private String mId;
    private TextView mTv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fetcher);
        initSp();
        initItent();
        findID();
        initUI();
        setListener();
    }
    /**
     * 获取用户id
     */
    private void initSp() {
        SharedPreferences sp = getSharedPreferences(Constant.SP_KEY.SP_NAME, Context.MODE_PRIVATE);
        mId = sp.getString(Constant.SP_KEY.ID, "");
    }

    private void initItent() {
        Intent intent = getIntent();
        mTicketPassager = (UsedContactInfo) intent.getSerializableExtra("ticketPassager");
        mBianji = intent.getStringExtra("bianji");
    }

    private void findID() {
        mCancel = (TextView) findViewById(R.id.tv_cancel);
        mPassager_name = (EditText) findViewById(R.id.passager_name);
        mPerson_id = (EditText) findViewById(R.id.person_ID);
        mPassager_phoneNum = (EditText) findViewById(R.id.passager_phoneNum);
        mTv_title = (TextView) findViewById(R.id.tv_title);
    }

    private void initUI() {
        if ("MineFragment".equals(mBianji)) {
            mTv_title.setText("编辑乘客");
            mPassager_name.setText(mTicketPassager.getName());
            mPerson_id.setText(mTicketPassager.getIdcard());
            mPassager_phoneNum.setText(mTicketPassager.getPhone());
            mPassager_name.setSelection(mTicketPassager.getName().length());
        }
    }

    private void setListener() {
        mCancel.setOnClickListener(this);
        findViewById(R.id.tv_save).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_save:
                checkInputFormat();
                break;
            case R.id.tv_cancel:
                finish();
                animFromLeftToRightOUT();
                break;
        }
    }

    /**
     * 检查输入的姓名、身份证、电话号码是否符合要求
     */
    private void checkInputFormat() {
        final String passagerName = mPassager_name.getText().toString().trim();
        final String personID = mPerson_id.getText().toString().trim();
        final String passagerPhoneNum = mPassager_phoneNum.getText().toString().trim();
        if ("".equals(passagerName)) {
            Toast.makeText(AddFetcherActivity.this, "请输入您的真实姓名", Toast.LENGTH_SHORT).show();
        } else {
            Pattern p = Pattern.compile("[\u4e00-\u9fa5]*");
            Matcher m = p.matcher(passagerName);
            if (m.matches()) {
                if (passagerName.length() < 2 || passagerName.length() > 15) {
                    Toast.makeText(AddFetcherActivity.this, "姓名长度为2-15个汉字！", Toast.LENGTH_SHORT).show();
                } else {
                    String yanzhengdizhi = "http://apis.juhe.cn/idcard/index?key=86c3cf00ca77641e108196c609ca3c08&cardno=" + personID;
                    HTTPUtils.get(AddFetcherActivity.this, yanzhengdizhi, new VolleyListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                        }

                        @Override
                        public void onResponse(String s) {
                            UsedPersonID usedPersonID = GsonUtils.parseJSON(s, UsedPersonID.class);
                            int error_code = usedPersonID.getError_code();
                            if (0 == error_code) {
                                //成功
                                boolean mobileNO = IsMobileNOorPassword.isMobileNO(passagerPhoneNum);
                                if (mobileNO) {
                                    if ("MineFragment".equals(mBianji)) {
                                        String url = Constant.Url.UPDATEPERSON;
                                        Map<String, String> map = new HashMap<>();
                                        map.put("id", mTicketPassager.getId() + "");
                                        map.put("name", passagerName);
                                        map.put("idcard", personID);
                                        map.put("phone", passagerPhoneNum);
                                        map.put("account_id", mId);
                                        HTTPUtils.post(AddFetcherActivity.this, url, map, new VolleyListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError volleyError) {
                                            }

                                            @Override
                                            public void onResponse(String s) {
                                                finish();
                                                animFromLeftToRightOUT();
                                            }
                                        });
                                    } else {
                                        String url = Constant.Url.ADDPERSON;
                                        Map<String, String> map = new HashMap<>();
                                        map.put("name", passagerName);
                                        map.put("idcard", personID);
                                        map.put("phone", passagerPhoneNum);
                                        map.put("account_id", mId);
                                        HTTPUtils.post(AddFetcherActivity.this, url, map, new VolleyListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError volleyError) {

                                            }

                                            @Override
                                            public void onResponse(String s) {
                                                if ("0".equals(s)) {
                                                    finish();
                                                    animFromLeftToRightOUT();
                                                } else {
                                                    Toast.makeText(AddFetcherActivity.this, "身份证号重复", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }

                                } else {
                                    Toast.makeText(AddFetcherActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                                }
                            } else if (203801 == error_code) {
                                DialogShow.setDialog(AddFetcherActivity.this, usedPersonID.getReason(), "确定");
                            } else if (203802 == error_code) {
                                DialogShow.setDialog(AddFetcherActivity.this, usedPersonID.getReason(), "确定");
                            } else if (203803 == error_code) {
                                DialogShow.setDialog(AddFetcherActivity.this, usedPersonID.getReason(), "确定");
                            } else if (203804 == error_code) {
                                DialogShow.setDialog(AddFetcherActivity.this, usedPersonID.getReason(), "确定");
                            } else {
                                Toast.makeText(AddFetcherActivity.this, "未知错误/未联网", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } else {
                Toast.makeText(AddFetcherActivity.this, "姓名不要包含数字和字符", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 界面跳转动画
     */
    private void animFromLeftToRightOUT() {
        overridePendingTransition(R.anim.fade_in, R.anim.push_right_out);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    /**
     * 重写返回键加动画
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            animFromLeftToRightOUT();
        }
        return super.onKeyDown(keyCode, event);
    }

}
