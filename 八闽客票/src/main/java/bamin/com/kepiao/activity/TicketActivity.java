package bamin.com.kepiao.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aiton.administrator.shane_library.shane.utils.GsonUtils;
import com.aiton.administrator.shane_library.shane.utils.HTTPUtils;
import com.aiton.administrator.shane_library.shane.utils.VolleyListener;
import com.android.datetimepicker.date.DatePickerDialog;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bamin.com.kepiao.R;
import bamin.com.kepiao.constant.ConstantTicket;
import bamin.com.kepiao.constant.EverythingConstant;
import bamin.com.kepiao.models.about_ticket.TicketInfo;
import bamin.com.kepiao.utils.DateCompareUtil;
import bamin.com.kepiao.utils.DialogShow;

public class TicketActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Calendar c = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private ListView mLv_ticket;
    private TicketListViewAdapter mAdapter = new TicketListViewAdapter();
    private LinearLayout[] checkLinear = new LinearLayout[3];
    private TextView[] checkText01 = new TextView[3];
    private TextView[] checkText02 = new TextView[3];
    private boolean[] isSelect = new boolean[]{false, false, false};
    private int checkPosition = 0;
    private int checkTimePosition = 0;
    private int checkStartStationPosition = 0;
    private int checkEndStationPosition = 0;
    private Set<String> checkStartStationSet = new LinkedHashSet<>();
    private Set<String> checkEndStationSet = new LinkedHashSet<>();
    private int mYear;
    private int mMonth;
    private int mDayOfMonth;
    private TextView mTv_today;
    private TextView mTv_yesterday;
    private TextView mTv_tomorrow;
    private ImageView mBack;
    private String mResult;
    private List<TicketInfo> mTicketInfoList = new ArrayList<>();
    private List<TicketInfo> mCheckTimeTicketInfoList = new ArrayList<>();
    private List<TicketInfo> mCheckStartTicketInfoList = new ArrayList<>();
    private List<TicketInfo> mCheckEndTicketInfoList = new ArrayList<>();
    private List<String> mCheckList = new ArrayList<>();//筛选容器
    private String start;
    private String end;
    private String mDateMath;
    private String mCheckedTime;
    private String mCurrentTime;
    private ProgressBar mRefrash;
    private TextView mTv_order_logout;
    private String mDeviceId;
    private String mId;
    private int queryTicketCount = 0;//控制车票查询次数，防止金点通偶尔网络异常
    private String mPhoneNum;
    private boolean isLogin = false;
    private ListView mListView_check;
    private MyCkeckListAdapter mCheckAdapter;
    private RelativeLayout mRela_dismiss;
    private RelativeLayout mRela_check;
    private boolean isClick = false;
    private boolean isShowDateDialog = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        initSp();
        initIntent();
        findID();
        initUI();
        initData();
        setOnclick();
    }

    private void findID() {
        mRela_check = (RelativeLayout) findViewById(R.id.rela_check);
        mRela_dismiss = (RelativeLayout) findViewById(R.id.rela_dismiss);
        checkText01[0] = (TextView) findViewById(R.id.textView_checkTime01);
        checkText01[1] = (TextView) findViewById(R.id.textView_checkStartStation01);
        checkText01[2] = (TextView) findViewById(R.id.textView_checkEndStartStation01);
        checkText02[0] = (TextView) findViewById(R.id.textView_checkTime02);
        checkText02[1] = (TextView) findViewById(R.id.textView_checkStartStation02);
        checkText02[2] = (TextView) findViewById(R.id.textView_checkEndStartStation02);
        checkLinear[0] = (LinearLayout) findViewById(R.id.time_check);
        checkLinear[1] = (LinearLayout) findViewById(R.id.start_station_check);
        checkLinear[2] = (LinearLayout) findViewById(R.id.end_station_check);
        mBack = (ImageView) findViewById(R.id.iv_back);
        mRefrash = (ProgressBar) findViewById(R.id.refrash);
        mTv_order_logout = (TextView) findViewById(R.id.tv_order_logout);
    }

    private void initSp() {
        SharedPreferences sp = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        mId = sp.getString("id", "");
        mDeviceId = sp.getString("DeviceId", "");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 向金点通查票
     */
    private void initData() {
        mTicketInfoList.clear();
        mCheckEndTicketInfoList.clear();
        mRefrash.setVisibility(View.VISIBLE);
        mLv_ticket.setVisibility(View.GONE);
        mTv_order_logout.setVisibility(View.GONE);
        String url_web = ConstantTicket.JDT_TICKET_HOST +
                "GetSellableScheduleByStartCityName?executeDate=" + mCheckedTime +
                "&startCityName=" + URLEncoder.encode(start) +
                "&endSiteNamePart=" + URLEncoder.encode(end);
        HTTPUtils.get(TicketActivity.this, url_web, new VolleyListener() {
            public void onErrorResponse(VolleyError volleyError) {
                if (queryTicketCount < 3) {
                    initData();
                    queryTicketCount++;
                } else {
                }

            }

            public void onResponse(String s) {
                Document doc = null;
                try {
                    doc = DocumentHelper.parseText(s);
                    Element testElement = doc.getRootElement();
                    String testxml = testElement.asXML();
                    mResult = testxml.substring(testxml.indexOf(">") + 1, testxml.lastIndexOf("<"));
                    Type type = new TypeToken<ArrayList<TicketInfo>>() {
                    }.getType();
                    mTicketInfoList = GsonUtils.parseJSONArray(mResult, type);
                    mCheckEndTicketInfoList.addAll(mTicketInfoList);
                    /**
                     * 没票提示
                     */
                    if (mTicketInfoList.size() == 0) {
                        mTv_order_logout.setVisibility(View.VISIBLE);
                    } else {
                        mTv_order_logout.setVisibility(View.GONE);
                    }
                    isClick = true;
                    mAdapter.notifyDataSetChanged();
                    mRefrash.setVisibility(View.GONE);
                    mLv_ticket.setVisibility(View.VISIBLE);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initIntent() {
        Intent intent = getIntent();
        mYear = intent.getIntExtra(ConstantTicket.IntentKey.CURR_YEAR, -1);
        mMonth = intent.getIntExtra(ConstantTicket.IntentKey.CURR_MONTH, -1);
        mDayOfMonth = intent.getIntExtra(ConstantTicket.IntentKey.CURR_DAY_OF_MONTH, -1);
        start = intent.getStringExtra(ConstantTicket.IntentKey.FINAIL_SET_OUT_STATION);
        end = intent.getStringExtra(ConstantTicket.IntentKey.FINAIL_ARRIVE_STATION);
    }

    private void initUI() {
        initBtnForTranTime();
        initTicketListView();
        initCheckList();
    }

    private void initCheckList() {
        mListView_check = (ListView) findViewById(R.id.listView_check);
        mCheckAdapter = new MyCkeckListAdapter();
        mListView_check.setAdapter(mCheckAdapter);
    }

    class MyCkeckListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mCheckList.size();
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
            View inflate = getLayoutInflater().inflate(R.layout.checklist_item, null);
            ImageView imageView_checkED = (ImageView) inflate.findViewById(R.id.imageView_checkED);
            if (position == checkPosition) {
                imageView_checkED.setVisibility(View.VISIBLE);
            }
            TextView textView_checkItem = (TextView) inflate.findViewById(R.id.textView_checkItem);
            textView_checkItem.setText(mCheckList.get(position));
            return inflate;
        }
    }

    private void initBtnForTranTime() {
        mTv_today = (TextView) findViewById(R.id.tv_today);
        mCheckedTime = mYear + "-" + mMonth + "-" + mDayOfMonth;
        mCurrentTime = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
        mTv_today.setText(mCheckedTime);
        mTv_yesterday = (TextView) findViewById(R.id.tv_yesterday);
        mTv_tomorrow = (TextView) findViewById(R.id.tv_tomorrow);
        checkTimeBtn();
    }

    private void checkTimeBtn() {
        try {
            mDateMath = DateCompareUtil.DateMath(mCheckedTime, mCurrentTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("等于".equals(mDateMath)) {
            mTv_yesterday.setEnabled(false);
            mTv_yesterday.setTextColor(Color.parseColor("#C0C0C0"));
        } else {
            mTv_yesterday.setEnabled(true);
            mTv_yesterday.setTextColor(Color.parseColor("#FFFFFF"));
        }
        if ("等于10".equals(mDateMath)) {
            mTv_tomorrow.setEnabled(false);
            mTv_tomorrow.setTextColor(Color.parseColor("#C0C0C0"));
        } else {
            mTv_tomorrow.setEnabled(true);
            mTv_tomorrow.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    private void initTicketListView() {
        mLv_ticket = (ListView) findViewById(R.id.lv_ticket);
        mLv_ticket.setAdapter(mAdapter);
    }

    private void setOnclick() {
        mListView_check.setOnItemClickListener(new CheckItemClickListener());
        mRela_dismiss.setOnClickListener(this);
        mTv_yesterday.setOnClickListener(this);
        mTv_tomorrow.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mTv_today.setOnClickListener(this);
        mLv_ticket.setOnItemClickListener(this);
        for (int i = 0; i < checkLinear.length; i++) {
            checkLinear[i].setOnClickListener(this);
        }
    }

    private void checkResult() {
        mCheckTimeTicketInfoList.clear();
        mCheckStartTicketInfoList.clear();
        mCheckEndTicketInfoList.clear();
        if (checkTimePosition == 0) {
            mCheckTimeTicketInfoList.addAll(mTicketInfoList);
        } else if (checkTimePosition == 1) {
            for (int i = 0; i < mTicketInfoList.size(); i++) {
                String setoutTime = mTicketInfoList.get(i).getSetoutTime();
                long longtime = Long.parseLong(setoutTime.substring(6, setoutTime.length() - 2));
                try {
                    Date checkedTimeDate = sdf.parse(mCheckedTime);
                    long checkTime = checkedTimeDate.getTime();
                    long redundantTime = (longtime - checkTime) / (3600 * 1000);
                    if (redundantTime >= 0 && redundantTime < 12) {
                        mCheckTimeTicketInfoList.add(mTicketInfoList.get(i));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else if (checkTimePosition == 2) {
            for (int i = 0; i < mTicketInfoList.size(); i++) {
                String setoutTime = mTicketInfoList.get(i).getSetoutTime();
                long longtime = Long.parseLong(setoutTime.substring(6, setoutTime.length() - 2));
                try {
                    Date checkedTimeDate = sdf.parse(mCheckedTime);
                    long checkTime = checkedTimeDate.getTime();
                    long redundantTime = (longtime - checkTime) / (3600 * 1000);
                    Log.e("checkResult ", "redundantTime" + redundantTime);
                    if (redundantTime >= 12 && redundantTime <= 18) {
                        mCheckTimeTicketInfoList.add(mTicketInfoList.get(i));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } else if (checkTimePosition == 3) {
            for (int i = 0; i < mTicketInfoList.size(); i++) {
                String setoutTime = mTicketInfoList.get(i).getSetoutTime();
                long longtime = Long.parseLong(setoutTime.substring(6, setoutTime.length() - 2));
                try {
                    Date checkedTimeDate = sdf.parse(mCheckedTime);
                    long checkTime = checkedTimeDate.getTime();
                    long redundantTime = (longtime - checkTime) / (3600 * 1000);
                    if (redundantTime >= 18 && redundantTime <= 24) {
                        mCheckTimeTicketInfoList.add(mTicketInfoList.get(i));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        if (checkStartStationPosition == 0) {
            mCheckStartTicketInfoList.addAll(mCheckTimeTicketInfoList);
        } else {
            for (int i = 0; i < mCheckTimeTicketInfoList.size(); i++) {
                if (mCheckTimeTicketInfoList.get(i).getStationName().equals(checkText02[1].getText().toString().trim())) {
                    mCheckStartTicketInfoList.add(mCheckTimeTicketInfoList.get(i));
                }
            }
        }
        if (checkEndStationPosition == 0) {
            mCheckEndTicketInfoList.addAll(mCheckStartTicketInfoList);
        } else {
            for (int i = 0; i < mCheckStartTicketInfoList.size(); i++) {
                if (mCheckStartTicketInfoList.get(i).getEndSiteName().equals(checkText02[2].getText().toString().trim())) {
                    mCheckEndTicketInfoList.add(mCheckStartTicketInfoList.get(i));
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    class CheckItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (isSelect[0]) {
                if (position == 0) {
                    checkText02[0].setText("全天");
                } else if (position == 1) {
                    checkText02[0].setText("早上");
                } else if (position == 2) {
                    checkText02[0].setText("下午");
                } else if (position == 3) {
                    checkText02[0].setText("晚上");
                }
                checkTimePosition = position;
            }
            if (isSelect[1]) {
                checkText02[1].setText(mCheckList.get(position));
                checkStartStationPosition = position;
            }
            if (isSelect[2]) {
                checkText02[2].setText(mCheckList.get(position));
                checkEndStationPosition = position;
            }
            for (int i = 0; i < isSelect.length; i++) {
                isSelect[i] = false;
            }
            isNoneCheck();
            checkResult();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rela_dismiss:
                for (int i = 0; i < isSelect.length; i++) {
                    isSelect[i] = false;
                }
                isNoneCheck();
                break;
            case R.id.time_check:
                isSelect[0] = !isSelect[0];
                isSelect[1] = false;
                isSelect[2] = false;
                if (isSelect[0]) {
                    ischeckED(0);
                    checkPosition = checkTimePosition;
                    initCheckTimeData();
                    mRela_dismiss.setVisibility(View.VISIBLE);
                    mRela_check.setVisibility(View.VISIBLE);
                } else {
                    isNoneCheck();

                }
                break;
            case R.id.start_station_check:
                isSelect[1] = !isSelect[1];
                isSelect[0] = false;
                isSelect[2] = false;
                if (isSelect[1]) {
                    ischeckED(1);
                    checkPosition = checkStartStationPosition;
                    initCheckStartStationData();
                    mRela_dismiss.setVisibility(View.VISIBLE);
                    mRela_check.setVisibility(View.VISIBLE);
                } else {
                    isNoneCheck();
                }
                break;
            case R.id.end_station_check:
                isSelect[2] = !isSelect[2];
                isSelect[0] = false;
                isSelect[1] = false;
                if (isSelect[2]) {
                    ischeckED(2);
                    checkPosition = checkEndStationPosition;
                    initCheckEndStationData();
                    mRela_dismiss.setVisibility(View.VISIBLE);
                    mRela_check.setVisibility(View.VISIBLE);
                } else {
                    isNoneCheck();
                }
                break;
            case R.id.tv_today:
                //防止双击弹出两个时间选择器
                isShowDateDialog = !isShowDateDialog;
                if (isShowDateDialog) {
                    showDatePickerDialog();
                    initPosition();
                }
                break;
            case R.id.tv_yesterday:
                if (isClick) {
                    isClick = false;
                    //得到指定模范的时间
                    try {
                        Date d = sdf.parse(mCheckedTime);
                        long d1 = d.getTime() - 24 * 3600 * 1000;
                        mCheckedTime = sdf.format(d1);
                        mTv_today.setText(mCheckedTime);
                        checkTimeBtn();
                        updateDate();
                        initData();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    initPosition();
                } else {
                }

                break;
            case R.id.tv_tomorrow:
                if (isClick) {
                    isClick = false;
                    //得到指定模范的时间
                    SimpleDateFormat sdf01 = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        Date d = sdf01.parse(mCheckedTime);
                        long d1 = d.getTime() + 24 * 3600 * 1000;
                        mCheckedTime = sdf01.format(d1);
                        mTv_today.setText(mCheckedTime);
                        checkTimeBtn();
                        updateDate();
                        initData();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    initPosition();
                } else {
                }

                break;
            case R.id.iv_back:
                finish();
                AnimFromRightToLeftOUT();
                break;
        }
    }

    private void initPosition() {
        checkTimePosition = 0;
        checkStartStationPosition = 0;
        checkEndStationPosition = 0;
        checkText02[0].setText("全天");
        checkText02[1].setText("全部车站");
        checkText02[2].setText("全部车站");
    }

    private void isNoneCheck() {
        int colorBlack = getResources().getColor(R.color.black);
        int colorBasic = getResources().getColor(R.color.title_bar);
        for (int i = 0; i < checkLinear.length; i++) {
            checkLinear[i].setBackgroundResource(R.color.white);
            checkText01[i].setTextColor(colorBlack);
            checkText02[i].setTextColor(colorBasic);
        }
        mRela_dismiss.setVisibility(View.GONE);
        mRela_check.setVisibility(View.GONE);
    }

    private void ischeckED(int i) {
        int colorWhite = getResources().getColor(R.color.white);
        int colorBlack = getResources().getColor(R.color.black);
        int colorBasic = getResources().getColor(R.color.title_bar);
        checkLinear[i % 3].setBackgroundResource(R.color.title_bar);
        checkLinear[(i + 1) % 3].setBackgroundResource(R.color.white);
        checkLinear[(i + 2) % 3].setBackgroundResource(R.color.white);
        checkText01[i].setTextColor(colorWhite);
        checkText01[(i + 1) % 3].setTextColor(colorBlack);
        checkText01[(i + 2) % 3].setTextColor(colorBlack);
        checkText02[i].setTextColor(colorWhite);
        checkText02[(i + 1) % 3].setTextColor(colorBasic);
        checkText02[(i + 2) % 3].setTextColor(colorBasic);
    }

    private void initCheckEndStationData() {
        mCheckList.clear();
        checkStartStationSet.clear();
        checkStartStationSet.add("全部车站");
        for (int i = 0; i < mTicketInfoList.size(); i++) {
            checkStartStationSet.add(mTicketInfoList.get(i).getEndSiteName());
        }
        Iterator<String> iterator = checkStartStationSet.iterator();
        while (iterator.hasNext()) {
            mCheckList.add(iterator.next());
        }
        mCheckAdapter.notifyDataSetChanged();
    }

    private void initCheckStartStationData() {
        mCheckList.clear();
        checkEndStationSet.clear();
        checkEndStationSet.add("全部车站");
        for (int i = 0; i < mTicketInfoList.size(); i++) {
            checkEndStationSet.add(mTicketInfoList.get(i).getStationName());
        }
        Iterator<String> iterator = checkEndStationSet.iterator();
        while (iterator.hasNext()) {
            mCheckList.add(iterator.next());
        }
        mCheckAdapter.notifyDataSetChanged();
    }

    private void initCheckTimeData() {
        mCheckList.clear();
        mCheckList.add("全天");
        mCheckList.add("早上00:00-12:00");
        mCheckList.add("下午12:00-18:00");
        mCheckList.add("晚上18:00-24:00");
        mCheckAdapter.notifyDataSetChanged();
    }


    private void updateDate() {
        /**
         * 将字符截成数组
         */
        String[] split = mCheckedTime.split("-");
        mYear = Integer.parseInt(split[0]);
        if (split[1].startsWith("0")) {
            split[1] = split[1].replace("0", "");
        }
        if (split[2].startsWith("0")) {
            split[2] = split[2].replace("0", "");
        }
        mMonth = Integer.parseInt(split[1]);
        mDayOfMonth = Integer.parseInt(split[2]);
    }

    /**
     * 从右往左结束动画
     */
    private void AnimFromRightToLeftOUT() {
        overridePendingTransition(R.anim.fade_in, R.anim.push_left_out);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String setoutTime = mTicketInfoList.get(position).getSetoutTime();
        long longtime = Long.parseLong(setoutTime.substring(6, setoutTime.length() - 2));
        long currentTimeMillis = System.currentTimeMillis();
        if (isLogin) {
            if ((longtime - currentTimeMillis) < 900L * 1000L) {
                DialogShow.setDialog(TicketActivity.this, "距发车时间15分钟内，停止售票", "确认");
            } else {
                checkIsLoginOnOtherDevice(mTicketInfoList.get(position));
            }
        } else {
            Intent intent = new Intent();
            intent.setClass(TicketActivity.this, SmsLoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        mPhoneNum = sp.getString("phoneNum", "");
        mDeviceId = sp.getString("DeviceId", "");
        mId = sp.getString("id", "");
        if ("".equals(mPhoneNum)) {
            isLogin = false;
        } else {
            isLogin = true;
        }
        MobclickAgent.onResume(this);
    }


    class TicketListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mCheckEndTicketInfoList.size();
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
            View layout = getLayoutInflater().inflate(R.layout.list_item_ticket, null);
            TextView start_time = (TextView) layout.findViewById(R.id.start_time);
            TextView start_station = (TextView) layout.findViewById(R.id.start_station);
            TextView destination = (TextView) layout.findViewById(R.id.destination);
            TextView ticket_price = (TextView) layout.findViewById(R.id.ticket_price);
            final TicketInfo ticketInfo = mCheckEndTicketInfoList.get(position);
            TextView reserve = (TextView) layout.findViewById(R.id.reserve);
            reserve.setText("预订\n余票:" + ticketInfo.getFreeSeats());
            String outTime = timeFormate(ticketInfo.getSetoutTime());
            start_time.setText(outTime);
            start_station.setText(ticketInfo.getStationName());
            destination.setText(ticketInfo.getEndSiteName());
            ticket_price.setText("¥" + ticketInfo.getFullPrice());
            return layout;
        }
    }

    /**
     * 从小到大打开动画
     */
    private void animFromSmallToBigIN() {
        overridePendingTransition(R.anim.magnify_fade_in, R.anim.fade_out);
    }

    private String timeFormate(String setoutTime) {
        long longtime = Long.parseLong(setoutTime.substring(6, setoutTime.length() - 2));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(longtime);
    }

    //重写back方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            AnimFromRightToLeftOUT();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 检查是否在其他设备上登录
     */
    private void checkIsLoginOnOtherDevice(final TicketInfo ticketInfo) {
        if (!"".equals(mDeviceId)) {
            String url = EverythingConstant.HOST + "/bmpw/account/findLogin_id";
            Map<String, String> map = new HashMap<>();
            map.put("account_id", mId);
            map.put("flag", "1");
            HTTPUtils.post(TicketActivity.this, url, map, new VolleyListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }

                @Override
                public void onResponse(String s) {
                    if (!s.equals(mDeviceId)) {
                        setDialog("你的账号登录异常\n请重新登录", "确定");
                    }
                    {
                        Intent intent = new Intent();
                        intent.putExtra("ticketInfo", ticketInfo);
                        intent.setClass(TicketActivity.this, FillinOrderActivity.class);
                        startActivity(intent);
                        animFromSmallToBigIN();
                    }
                }
            });
        }
    }

    //dialog提示
    private void setDialog(String messageTxt, String iSeeTxt) {
        View commit_dialog = getLayoutInflater().inflate(R.layout.commit_dialog, null);
        TextView message = (TextView) commit_dialog.findViewById(R.id.message);
        Button ISee = (Button) commit_dialog.findViewById(R.id.ISee);
        message.setText(messageTxt);
        ISee.setText(iSeeTxt);
        AlertDialog.Builder builder = new AlertDialog.Builder(TicketActivity.this);
        final AlertDialog dialog = builder.setView(commit_dialog)
                .create();
        dialog.setCancelable(false);
        dialog.show();
        commit_dialog.findViewById(R.id.ISee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //清除用户登录信息
                SharedPreferences sp = getSharedPreferences("isLogin", MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.clear();
                edit.commit();
                Intent intent = new Intent();
                intent.setClass(TicketActivity.this, SmsLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean mDateCompare;

    public void showDatePickerDialog() {
        DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
                try {
                    mDateCompare = DateCompareUtil.DateCompare(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth, c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mDateCompare) {
                    mYear = year;
                    mMonth = monthOfYear + 1;
                    mDayOfMonth = dayOfMonth;
                    mTv_today.setText(mYear + "-" + mMonth + "-" + mDayOfMonth);
                    mCheckedTime = mYear + "-" + mMonth + "-" + mDayOfMonth;
                    initData();
                    checkTimeBtn();
                } else {
                    Toast.makeText(TicketActivity.this, "预售11天内的车票，请重新选择！", Toast.LENGTH_SHORT).show();
                    mTv_today.setText(mYear + "-" + mMonth + "-" + mDayOfMonth);
                }
            }
        }, mYear, mMonth - 1, mDayOfMonth).show(TicketActivity.this.getFragmentManager(), "datePicker");

    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
