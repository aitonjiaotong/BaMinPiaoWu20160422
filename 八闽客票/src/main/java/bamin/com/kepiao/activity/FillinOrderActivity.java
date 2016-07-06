package bamin.com.kepiao.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aiton.administrator.shane_library.shane.utils.GsonUtils;
import com.aiton.administrator.shane_library.shane.utils.HTTPUtils;
import com.aiton.administrator.shane_library.shane.utils.VolleyListener;
import com.aiton.administrator.shane_library.shane.widget.ListView4ScrollView;
import com.android.volley.VolleyError;
import com.umeng.analytics.MobclickAgent;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bamin.com.kepiao.R;
import bamin.com.kepiao.constant.Constant;
import bamin.com.kepiao.models.about_order.OrderInfo;
import bamin.com.kepiao.models.about_order.OrderList;
import bamin.com.kepiao.models.about_ticket.TicketInfo;
import bamin.com.kepiao.models.about_used_contact.AddContant;
import bamin.com.kepiao.models.about_used_contact.UsedContactInfo;
import bamin.com.kepiao.utils.DialogShow;
import bamin.com.kepiao.utils.TimeAndDateFormate;

/**
 * 优化1.0
 */
public class FillinOrderActivity extends Activity implements View.OnClickListener {

    private ImageView mBack;
    private TicketInfo mTicketInfo;
    private int ticketNumBuy = 0;
    private int ticketChildNum = 0;
    private ListView4ScrollView mPassager_list;
    private List<UsedContactInfo> mTicketPassagerList = new ArrayList<>();
    private OrderInfo mOrderInfo;
    private CheckBox mCheckBox_baoxian;
    private TextView mTextView_insure;
    private String mPhoneNum;
    private double insurePrice;
    private double mRealPrice;
    private MyAdapter mAdapter;
    private ImageView mAdd_passager;
    private TextView mPassager_count;
    private TextView mChild_num;
    private ImageView mChild_delete;
    private ImageView mChild_add;
    private TextView mPassager_count_real;
    private TextView mTotal_price;
    private String mId;
    private TextView mStart_xianshi;
    private TextView mEnd_xianshi;
    private TextView mStart_station;
    private TextView mEnd_station;
    private TextView mCoachGradeName;
    private TextView mTicket_price;
    private TextView mSeatNum;
    private TextView mTicket_time;
    private TextView mSetoutTime;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fillin_order);
        initSp();
        initIntent();
        findID();
        initUI();
        setOnclick();
    }

    private void findID() {
        mTotal_price = (TextView) findViewById(R.id.total_price);
        mPassager_count_real = (TextView) findViewById(R.id.passager_Count_real);
        mChild_delete = (ImageView) findViewById(R.id.child_delete);
        mChild_add = (ImageView) findViewById(R.id.child_add);
        mChild_num = (TextView) findViewById(R.id.child_num);
        mPassager_count = (TextView) findViewById(R.id.passager_Count);
        mAdd_passager = (ImageView) findViewById(R.id.add_passager);
        mBack = (ImageView) findViewById(R.id.iv_back);
        mStart_xianshi = (TextView) findViewById(R.id.start_xianshi);
        mEnd_xianshi = (TextView) findViewById(R.id.end_xianshi);
        mStart_station = (TextView) findViewById(R.id.start_station);
        mEnd_station = (TextView) findViewById(R.id.end_station);
        mCoachGradeName = (TextView) findViewById(R.id.coachGradeName);
        mTicket_price = (TextView) findViewById(R.id.ticket_price);
        mSeatNum = (TextView) findViewById(R.id.seatNum);
        mPassager_list = (ListView4ScrollView) findViewById(R.id.passager_list);
        mTicket_time = (TextView) findViewById(R.id.ticket_time);
        mSetoutTime = (TextView) findViewById(R.id.setoutTime);
        mCheckBox_baoxian = (CheckBox) findViewById(R.id.checkBox_baoxian);
        mTextView_insure = (TextView) findViewById(R.id.textView_insure);
    }

    /**
     * 获取用户id
     */
    private void initSp() {
        SharedPreferences sp = getSharedPreferences(Constant.SP_KEY.SP_NAME, Context.MODE_PRIVATE);
        mId = sp.getString(Constant.SP_KEY.ID, "");
        mPhoneNum = sp.getString("phoneNum", "");
    }

    private void initIntent() {
        Intent intent = getIntent();
        mTicketInfo = (TicketInfo) intent.getSerializableExtra("ticketInfo");
        insurePrice=mTicketInfo.getInsurePrice();
    }

    private void initUI() {
        mStart_xianshi.setText(mTicketInfo.getStartSiteName());
        mEnd_xianshi.setText(mTicketInfo.getEndSiteName());
        mStart_station.setText(mTicketInfo.getStationName());
        mEnd_station.setText(mTicketInfo.getEndZoneName());
        mCoachGradeName.setText(mTicketInfo.getCoachGradeName());
        mTicket_price.setText("¥" + mTicketInfo.getFullPrice());
        mSeatNum.setText("座位数:" + mTicketInfo.getCoachSeatNumber());
        mTicket_time.setText(TimeAndDateFormate.dateFormate(mTicketInfo.getExecuteDate()));
        mSetoutTime.setText(TimeAndDateFormate.timeFormate(mTicketInfo.getSetoutTime()));
        mAdapter = new MyAdapter();
        mPassager_list.setAdapter(mAdapter);
        mChild_num.setText(ticketChildNum + "");
        refrashTicketNumAndPrice();
        mTextView_insure.setText("乘意险¥" + mTicketInfo.getInsurePrice());
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mTicketPassagerList.size();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View inflate = getLayoutInflater().inflate(R.layout.ticket_passager_item, null);
            TextView passager_name = (TextView) inflate.findViewById(R.id.passager_name);
            passager_name.setText(mTicketPassagerList.get(position).getName());
            inflate.findViewById(R.id.detete_contact).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ticketNumBuy = ticketNumBuy - 1;
                    mTicketPassagerList.remove(position);
                    mAdapter.notifyDataSetChanged();
                    if (ticketNumBuy<ticketChildNum){
                        ticketChildNum=0;
                        Toast.makeText(FillinOrderActivity.this, "请重新添加儿童", Toast.LENGTH_SHORT).show();
                    }
                    refrashTicketNumAndPrice();
                }
            });
            return inflate;
        }
    }

    private void setOnclick() {
        mAdd_passager.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mChild_delete.setOnClickListener(this);
        mChild_add.setOnClickListener(this);
        findViewById(R.id.order_commmit).setOnClickListener(this);
        mCheckBox_baoxian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mCheckBox_baoxian.isChecked()){
                    insurePrice=mTicketInfo.getInsurePrice();
                }else {
                    insurePrice=0;
                }
                refrashTicketNumAndPrice();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            //提交订单
            case R.id.order_commmit:
                String setoutTime = mTicketInfo.getSetoutTime();
                long longtime = Long.parseLong(setoutTime.substring(6, setoutTime.length() - 2));
                long currentTimeMillis = System.currentTimeMillis();
                if ((longtime - currentTimeMillis) < Constant.Key.LEFT_BUY_TICKET_TIME) {
                    DialogShow.setDialog(FillinOrderActivity.this, Constant.Key.LEFT_BUY_TICKET_MSG, "确认");
                } else {
                    if (ticketNumBuy > 0) {
                        setPopupWindows();
                        orderCommit();
                    } else {
                        DialogShow.setDialog(FillinOrderActivity.this, "请添加乘车人", "确定");
                    }
                }
                break;
            case R.id.child_delete:
                if (ticketChildNum > 0) {
                    ticketChildNum = ticketChildNum - 1;
                    mChild_num.setText(ticketChildNum + "");
                }
                break;
            case R.id.child_add:
                if (ticketNumBuy > 0) {
                    if (ticketChildNum < mTicketInfo.getCoachSeatNumber() / 10&&ticketChildNum<ticketNumBuy) {
                        ticketChildNum = ticketChildNum + 1;
                        mChild_num.setText(ticketChildNum + "");
                    } else {
                        DialogShow.setDialog(FillinOrderActivity.this, "携免票儿童童数已超规定比例", "确认");
                    }
                } else {
                    DialogShow.setDialog(FillinOrderActivity.this, "请添加乘客", "确认");
                }
                break;
            case R.id.add_passager:
                intent.putExtra("addContact", "FillinOrderActivity");
                intent.setClass(FillinOrderActivity.this, UsedContact.class);
                startActivityForResult(intent, Constant.RequestAndResultCode.REQUEST_CODE_COMMIT_ORDER);
                animFromSmallToBigIN();
                break;
            case R.id.iv_back:
                finish();
                AnimFromRightToLeftOUT();
                break;
        }
    }

    /**
     * 从小到大打开动画
     */
    private void animFromSmallToBigIN() {
        overridePendingTransition(R.anim.magnify_fade_in, R.anim.fade_out);
    }

    //弹出等待popupwindows，防止误操作
    private void setPopupWindows() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("提交订单……");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    mProgressDialog.dismiss();
                }
                return false;
            }
        });
//        View inflate = getLayoutInflater().inflate(R.layout.popupmenu01, null);
//        //最后一个参数为true，点击PopupWindow消失,宽必须为match，不然肯呢个会导致布局显示不完全
//        mPopupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        //设置外部点击无效
//        mPopupWindow.setOutsideTouchable(false);
//        //设置背景变暗
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = 0.7f;
//        getWindow().setAttributes(lp);
//        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//
//            @Override
//            public void onDismiss() {
//                WindowManager.LayoutParams lp = getWindow().getAttributes();
//                lp.alpha = 1f;
//                getWindow().setAttributes(lp);
//            }
//        });
//        mPopupWindow.showAtLocation(inflate, Gravity.CENTER, 0, 0);
    }

    private void orderCommit() {
        String passengerIdentitys = "";
        String passengerNames = "";
        String passengerInfo = "";
        for (int i = 0; i < mTicketPassagerList.size(); i++) {
            passengerIdentitys += "&passengerIdentitys=" + mTicketPassagerList.get(i).getIdcard();
            passengerNames += "&passengerNames=" + URLEncoder.encode(mTicketPassagerList.get(i).getName());
        }
        passengerInfo = passengerIdentitys + passengerNames;
        String url_web = Constant.JDT_TICKET_HOST +
                "SellTicket_NoBill_Booking?scheduleCompanyCode=" + "YongAn" +
                "&executeScheduleID=" + mTicketInfo.getExecuteScheduleID() +
                "&startSiteID=" + mTicketInfo.getStartSiteID() +
                "&endSiteID=" + mTicketInfo.getEndSiteID() +
                "&fullTickets=" + ticketNumBuy +
                "&halfTicket=" + "0" +
                "&carryChild=" + ticketChildNum +
                "&phoneNumber=" + mPhoneNum +
                passengerInfo +
                "&insured=" + mCheckBox_baoxian.isChecked();
        HTTPUtils.get(FillinOrderActivity.this, url_web, new VolleyListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(FillinOrderActivity.this, "票务系统连接中", Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
            }

            public void onResponse(String s) {
                Document doc = null;
                try {
                    doc = DocumentHelper.parseText(s);
                    Element testElement = doc.getRootElement();
                    String testxml = testElement.asXML();
                    String mResult;
                    mResult = testxml.substring(testxml.indexOf(">") + 1, testxml.lastIndexOf("<"));
                    mOrderInfo = GsonUtils.parseJSON(mResult, OrderInfo.class);
                    if (mOrderInfo != null) {
                        if (mOrderInfo.getBookLogAID() == null) {
                            if ("班次有关参数值错误，未能查询到对应班次".equals(mOrderInfo.getMessage())) {
                                Toast.makeText(FillinOrderActivity.this, "暂不支持三明地区以外的出发地", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(FillinOrderActivity.this, mOrderInfo.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            mProgressDialog.dismiss();
                        } else {
                            /**
                             * 传订单号到艾通服务器
                             */
                            commitOrderToAiTon();
                        }
                    } else {
                        Toast.makeText(FillinOrderActivity.this, "提交订单失败", Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();
                    }
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void commitOrderToAiTon() {
        String url = Constant.HOST_TICKET + "/front/addorder";
        Log.e("commitOrderToAiTon", "订单号" +mOrderInfo.getBookLogAID());
        Map<String, String> map = new HashMap<>();
        map.put("bookLogAID", mOrderInfo.getBookLogAID());
        map.put("account_id", mId);
        map.put("redEnvelope_id", "");
        map.put("insure",insurePrice+"");
        map.put("price",mRealPrice+"");
        map.put("yuliu1","android");
        HTTPUtils.post(FillinOrderActivity.this, url, map, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mProgressDialog.dismiss();
            }

            @Override
            public void onResponse(String s) {
                OrderList orderList = GsonUtils.parseJSON(s, OrderList.class);
                Intent intent = new Intent();
                intent.setClass(FillinOrderActivity.this, PayActivity.class);
                intent.putExtra("BookLogAID", mOrderInfo.getBookLogAID());
                intent.putExtra("OrderId", orderList.getId() + "");
                intent.putExtra("realPrice", mRealPrice);
                intent.putExtra("insurePrice", insurePrice);
                startActivity(intent);
                animFromSmallToBigIN();
                mProgressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== Constant.RequestAndResultCode.REQUEST_CODE_COMMIT_ORDER){
            if (resultCode== Constant.RequestAndResultCode.RESULT_CODE_COMMIT_ORDER){
                boolean isExit = false;
                AddContant theAddContact = (AddContant) data.getSerializableExtra("theAddContactList");
                List<UsedContactInfo> theAddContactList = theAddContact.getTheAddContact();
                for (int i = 0; i < theAddContactList.size(); i++) {
                    String idcard = theAddContactList.get(i).getIdcard();
                    for (int j = 0; j < mTicketPassagerList.size(); j++) {
                        if (idcard.equals(mTicketPassagerList.get(j).getIdcard())) {
                            DialogShow.setDialog(FillinOrderActivity.this, mTicketPassagerList.get(j).getName() + "已添加", "确定");
                            isExit = true;
                        }
                    }
                }
                if (!isExit) {
                    ticketNumBuy = ticketNumBuy + theAddContactList.size();
                    mTicketPassagerList.addAll(theAddContactList);
                    mAdapter.notifyDataSetChanged();
                    refrashTicketNumAndPrice();
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 更新界面票数和价钱
     */
    private void refrashTicketNumAndPrice() {
        mPassager_count.setText(ticketNumBuy + "");
        mPassager_count_real.setText("共" + ticketNumBuy + "人乘车");
        mRealPrice = ticketNumBuy * (mTicketInfo.getFullPrice() + insurePrice);
        mTotal_price.setText("￥" + mRealPrice);
    }

    /**
     * 从右往左结束动画
     */
    private void AnimFromRightToLeftOUT() {
        overridePendingTransition(R.anim.fade_in, R.anim.push_left_out);
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
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
