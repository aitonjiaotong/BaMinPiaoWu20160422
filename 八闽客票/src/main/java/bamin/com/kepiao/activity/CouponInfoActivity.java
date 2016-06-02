package bamin.com.kepiao.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiton.administrator.shane_library.shane.utils.GsonUtils;
import com.aiton.administrator.shane_library.shane.utils.HTTPUtils;
import com.aiton.administrator.shane_library.shane.utils.VolleyListener;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bamin.com.kepiao.R;
import bamin.com.kepiao.constant.Constant;
import bamin.com.kepiao.models.about_redpacket.RedPacket;
import bamin.com.kepiao.utils.DialogShow;

/**
 * 优化1.0
 */
public class CouponInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView mListView_redBag;
    private String mId;
    private List<RedPacket> mRedPacketList = new ArrayList<>();
    private MyAdapter mMyAdapter;
    private TextView mTextView_noneRedBag;
    private ProgressBar mProgressBar_reFreash;
    private String mUseRedBag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_info);
        initSp();
        initIntent();
        findID();
        initUI();
        initDate();
        setListener();
    }

    /**
     * 获取用户id
     */
    private void initSp() {
        SharedPreferences sp = getSharedPreferences(Constant.SP_KEY.SP_NAME, Context.MODE_PRIVATE);
        mId = sp.getString(Constant.SP_KEY.ID, "");
    }

    private void initIntent() {
        Intent intent = getIntent();
        mUseRedBag = intent.getStringExtra("UseRedBag");
    }

    private void findID() {
        mListView_redBag = (ListView) findViewById(R.id.listView_redBag);
        mTextView_noneRedBag = (TextView) findViewById(R.id.textView_noneRedBag);
        mProgressBar_reFreash = (ProgressBar) findViewById(R.id.progressBar_reFreash);
    }

    private void initUI() {
        mMyAdapter = new MyAdapter();
        mListView_redBag.setAdapter(mMyAdapter);
    }

    /**
     * 从服务器获取优惠券信息
     */
    private void initDate() {
        mListView_redBag.setVisibility(View.GONE);
        mTextView_noneRedBag.setVisibility(View.GONE);
        mProgressBar_reFreash.setVisibility(View.VISIBLE);
        String url = Constant.HOST_TICKET + "/redenvelope/getredenvelopebyuser";
        Map<String, String> map = new HashMap<>();
        map.put("account_id", mId);
        HTTPUtils.post(CouponInfoActivity.this, url, map, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }

            @Override
            public void onResponse(String s) {
                mProgressBar_reFreash.setVisibility(View.GONE);
                Type type = new TypeToken<ArrayList<RedPacket>>() {
                }.getType();
                mRedPacketList = GsonUtils.parseJSONArray(s, type);
                if (mRedPacketList.size() > 0) {
                    mListView_redBag.setVisibility(View.VISIBLE);
                    mTextView_noneRedBag.setVisibility(View.GONE);
                    mMyAdapter.notifyDataSetChanged();
                } else {
                    mTextView_noneRedBag.setVisibility(View.VISIBLE);
                    mListView_redBag.setVisibility(View.GONE);
                }
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mRedPacketList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View inflate = getLayoutInflater().inflate(R.layout.redbag_item, null);
            TextView textView_useTime = (TextView) inflate.findViewById(R.id.textView_useTime);
            RelativeLayout linear_redBag = (RelativeLayout) inflate.findViewById(R.id.linear_redBag);
            ImageView imageView_redBagFlag = (ImageView) inflate.findViewById(R.id.imageView_redBagFlag);
            TextView textView_redBagValue = (TextView) inflate.findViewById(R.id.textView_redBagValue);

            textView_redBagValue.setText(mRedPacketList.get(position).getAmount() + "");
            textView_useTime.setText(mRedPacketList.get(position).getValidity());
            //红包是否过期和使用
            int flag = mRedPacketList.get(position).getFlag();
            if (flag == 0) {
                linear_redBag.setBackgroundResource(R.drawable.redbag_shape);
                imageView_redBagFlag.setVisibility(View.GONE);
            } else if (flag == 1) {
                linear_redBag.setBackgroundResource(R.drawable.redbag_used_shape);
                imageView_redBagFlag.setImageResource(R.mipmap.yishiyong);
                imageView_redBagFlag.setVisibility(View.VISIBLE);
            } else if (flag == 2) {
                linear_redBag.setBackgroundResource(R.drawable.redbag_used_shape);
                imageView_redBagFlag.setImageResource(R.mipmap.yiguoqi);
                imageView_redBagFlag.setVisibility(View.VISIBLE);
            }
            return inflate;
        }
    }

    private void setListener() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        if ("UseRedBag".equals(mUseRedBag)){
            mListView_redBag.setOnItemClickListener(new RedBagItemClickListener());
        }
    }

    class RedBagItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int flag = mRedPacketList.get(position).getFlag();
            if (1==flag){
                DialogShow.setDialog(CouponInfoActivity.this, "优惠券已使用", "确认");
            }else if(2==flag){
                DialogShow.setDialog(CouponInfoActivity.this, "优惠券已过期", "确认");
            }else if (0==flag){
                Intent intent = new Intent();
                intent.setAction("RedBag");
                intent.putExtra("RedBag", mRedPacketList.get(position));
                sendBroadcast(intent);
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                AnimFromRightToLeft();
                break;
        }
    }

    private void AnimFromRightToLeft() {
        overridePendingTransition(R.anim.fade_in, R.anim.push_left_out);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            AnimFromRightToLeft();
        }
        return super.onKeyDown(keyCode, event);
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
