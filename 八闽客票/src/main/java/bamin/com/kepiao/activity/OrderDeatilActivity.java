package bamin.com.kepiao.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aiton.administrator.shane_library.shane.utils.GsonUtils;
import com.aiton.administrator.shane_library.shane.utils.HTTPUtils;
import com.aiton.administrator.shane_library.shane.utils.VolleyListener;
import com.aiton.administrator.shane_library.shane.widget.ListView4ScrollView;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.umeng.analytics.MobclickAgent;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import bamin.com.kepiao.R;
import bamin.com.kepiao.constant.ConstantTicket;
import bamin.com.kepiao.models.about_order.OrderDetial;
import bamin.com.kepiao.models.about_order.QueryOrder;
import bamin.com.kepiao.utils.TimeAndDateFormate;

/**
 * 优化1.0
 */
public class OrderDeatilActivity extends AppCompatActivity implements View.OnClickListener
{

    private TextView mTextView_detialOrderDate;
    private TextView mTextView_detialOrderStartPlace;
    private QueryOrder mQueryOrder;
    private String mMOrderInfoBookLogAID;
    private ImageView mTiaoxingma;
    private TextView mTextView_detialOrderEndPlace;
    private TextView mTextView_detialOrderTime;
    private TextView mTextView_detialOrderTicketCount;
    private TextView mTextView_detialOrderTicketAllPrice;
    private TextView mTextView_detialOrderTicketGetNum;
    private ProgressBar mProgressBar_detail_order;
    private LinearLayout mOrder_detail_linear;
    private List<OrderDetial> mOrderDetials = new ArrayList<>();
    private ListView4ScrollView mListView4ScrollView_passager;
    private MyAdapter mAdapter;
    private String mIsSure;
    private double mInsurePrice;
    private String mEveryting_order_list;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_deatil);
        initIntent();
        findID();
        queryOrder();
        queryOrderDetial();
        setListener();
    }

    /**
     * 查询乘客座位号等信息
     */
    private void queryOrderDetial()
    {
        String url = ConstantTicket.JDT_TICKET_HOST +
                "QueryTicket?getTicketCodeOrAID=" + mMOrderInfoBookLogAID;
        HTTPUtils.get(OrderDeatilActivity.this, url, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
            }

            @Override
            public void onResponse(String s)
            {
                Document doc = null;
                try
                {
                    doc = DocumentHelper.parseText(s);
                    Element testElement = doc.getRootElement();
                    String testxml = testElement.asXML();
                    String result = testxml.substring(testxml.indexOf(">") + 1, testxml.lastIndexOf("<"));
                    Type type = new TypeToken<ArrayList<OrderDetial>>()
                    {
                    }.getType();
                    mOrderDetials = GsonUtils.parseJSONArray(result, type);
                    mAdapter.notifyDataSetChanged();
                } catch (DocumentException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    private void findID()
    {
        mTiaoxingma = (ImageView) findViewById(R.id.tiaoxingma);
        mTextView_detialOrderDate = (TextView) findViewById(R.id.textView_detialOrderDate);
        mTextView_detialOrderStartPlace = (TextView) findViewById(R.id.textView_detialOrderStartPlace);
        mTextView_detialOrderEndPlace = (TextView) findViewById(R.id.textView_detialOrderEndPlace);
        mTextView_detialOrderTime = (TextView) findViewById(R.id.textView_detialOrderTime);
        mTextView_detialOrderTicketCount = (TextView) findViewById(R.id.textView_detialOrderTicketCount);
        mTextView_detialOrderTicketAllPrice = (TextView) findViewById(R.id.textView_detialOrderTicketAllPrice);
        mTextView_detialOrderTicketGetNum = (TextView) findViewById(R.id.textView_detialOrderTicketGetNum);
        mOrder_detail_linear = (LinearLayout) findViewById(R.id.order_detail);
        mProgressBar_detail_order = (ProgressBar) findViewById(R.id.progressBar_detail_order);
        mListView4ScrollView_passager = (ListView4ScrollView) findViewById(R.id.ListView4ScrollView_passager);
        mAdapter = new MyAdapter();
        mListView4ScrollView_passager.setAdapter(mAdapter);
    }

    class MyAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return mOrderDetials.size();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View inflate = getLayoutInflater().inflate(R.layout.order_passager_listitem, null);
            TextView textView_detial_passager = (TextView) inflate.findViewById(R.id.textView_detial_passager);
            TextView textView_detail_seat = (TextView) inflate.findViewById(R.id.textView_detail_seat);
            TextView textView_planCode = (TextView) findViewById(R.id.textView_planCode);
            if (mOrderDetials.size() > 0)
            {
                textView_detial_passager.setText("乘客：" + mOrderDetials.get(position).getPassengerName());
                textView_detail_seat.setText("座位号：" + mOrderDetials.get(position).getSeatNumber());
                textView_planCode.setText(mOrderDetials.get(position).getPlanScheduleCode());
            }
            return inflate;
        }
    }

    /**
     * 查询订单
     */
    private void queryOrder()
    {
        mOrder_detail_linear.setVisibility(View.GONE);
        mProgressBar_detail_order.setVisibility(View.VISIBLE);
        String url = ConstantTicket.JDT_TICKET_HOST +
                "QueryBookLog?getTicketCodeOrAID=" + mMOrderInfoBookLogAID;
        HTTPUtils.get(OrderDeatilActivity.this, url, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
            }

            @Override
            public void onResponse(String s)
            {
                Document doc = null;
                try
                {
                    doc = DocumentHelper.parseText(s);
                    Element testElement = doc.getRootElement();
                    String testxml = testElement.asXML();
                    String result = testxml.substring(testxml.indexOf(">") + 1, testxml.lastIndexOf("<"));
                    mQueryOrder = GsonUtils.parseJSON(result, QueryOrder.class);
                    mOrder_detail_linear.setVisibility(View.VISIBLE);
                    mProgressBar_detail_order.setVisibility(View.GONE);
                    initUI();
                } catch (DocumentException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 获取订单号
     */
    private void initIntent()
    {
        Intent intent = getIntent();
        mMOrderInfoBookLogAID = intent.getStringExtra("BookLogAID");
        mIsSure = intent.getStringExtra("isSure");
        mInsurePrice = intent.getDoubleExtra("insurePrice", 0);
        mEveryting_order_list = intent.getStringExtra("everyting_order_list");
        Log.e("initIntent ", "mMOrderInfoBookLogAID " + mMOrderInfoBookLogAID);
    }

    private void setListener()
    {
        findViewById(R.id.ic_header_back).setOnClickListener(this);
    }

    private void initUI()
    {
        //生成条形码
        try
        {
            mTiaoxingma.setImageBitmap(CreateOneDCode(mQueryOrder.getGetTicketCode()));
        } catch (WriterException e)
        {
            e.printStackTrace();
        }

        mTextView_detialOrderDate.setText("出发日期" + TimeAndDateFormate.dateFormate(mQueryOrder.getSetoutTime()));
        mTextView_detialOrderStartPlace.setText(mQueryOrder.getStartSiteName());
        mTextView_detialOrderEndPlace.setText(mQueryOrder.getEndSiteName());
        mTextView_detialOrderTime.setText(TimeAndDateFormate.timeFormate(mQueryOrder.getSetoutTime()));
        mTextView_detialOrderTicketCount.setText("购票张数：" + mQueryOrder.getFullTicket());
        mTextView_detialOrderTicketAllPrice.setText("票价：¥" + mQueryOrder.getPrice() + "  保险：¥" + mInsurePrice + "×" + mQueryOrder.getFullTicket());
        mTextView_detialOrderTicketGetNum.setText("取票号：" + mQueryOrder.getGetTicketCode());
    }

    /**
     * 用于将给定的内容生成成一维码 注：目前生成内容为中文的话将直接报错，要修改底层jar包的内容
     *
     * @param content 将要生成一维码的内容
     * @return 返回生成好的一维码bitmap
     * @throws WriterException WriterException异常
     */
    public Bitmap CreateOneDCode(String content) throws WriterException
    {
        // 生成一维条码,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.CODE_128, 800, 200);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                if (matrix.get(x, y))
                {
                    pixels[y * width + x] = 0xff000000;
                }
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ic_header_back:
                if ("everyting_order_list".equals(mEveryting_order_list))
                {
                    finish();
                } else
                {
                    backToMainActivity();
                    animFromBigToSmallOUT();
                }
                break;
        }
    }

    private void backToMainActivity()
    {
        if ("isSure".equals(mIsSure))
        {
            finish();
        } else
        {
            Intent intent = new Intent();
            intent.putExtra("OrderDeatilActivity", "OrderDeatilActivity");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setClass(OrderDeatilActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 从大到小结束动画
     */
    private void animFromBigToSmallOUT()
    {
        overridePendingTransition(R.anim.fade_in, R.anim.big_to_small_fade_out);
    }

    //重写back方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ("everyting_order_list".equals(mEveryting_order_list) && keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
        } else if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            backToMainActivity();
            animFromBigToSmallOUT();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

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
