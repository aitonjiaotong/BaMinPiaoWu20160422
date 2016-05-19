package bamin.com.kepiao.fragment;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aiton.administrator.shane_library.shane.utils.GsonUtils;
import com.aiton.administrator.shane_library.shane.utils.HTTPUtils;
import com.aiton.administrator.shane_library.shane.utils.UILUtils;
import com.aiton.administrator.shane_library.shane.utils.VolleyListener;
import com.android.datetimepicker.date.DatePickerDialog;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bamin.com.kepiao.R;
import bamin.com.kepiao.activity.SelectStationArriveActivity;
import bamin.com.kepiao.activity.SelectStationSetOutActivity;
import bamin.com.kepiao.activity.TicketActivity;
import bamin.com.kepiao.constant.ConstantTicket;
import bamin.com.kepiao.customView.ViewPagerIndicator;
import bamin.com.kepiao.models.about_banner.BannerInfo;
import bamin.com.kepiao.utils.DateCompareUtil;
import bamin.com.kepiao.utils.DialogShow;


public class Fragment01 extends Fragment implements View.OnClickListener
{

    private Calendar c = Calendar.getInstance();
    private View mLayout;
    private ViewPager mViewPager_banner;
    private List<BannerInfo> bannerData = new ArrayList<BannerInfo>();
    private boolean isFrist = true;
    private boolean mDragging;
    private int mPagerCount = Integer.MAX_VALUE / 3;
    private LinearLayout mChooseSetOut;
    private LinearLayout mChooseArrive;
    private ImageView mIv_Exchange;
    private TextView mTv_setOut;
    private TextView mTv_arrive;
    private TextView mTv_date;
    private LinearLayout mChooseTime;
    private boolean isChoose;
    private int mYear;
    private int mMonth;
    private int mDayOfMonth;
    private Button mBtn_query;
    private boolean mDateCompare;
    //    private boolean isLogin = false;
    private String mPhoneNum;
    private AlertDialog mAlertDialog;
    private ViewPagerIndicator mViewPagerIndicator;
    private ImageView mIv_cliscan;
    private LinearLayout mLl_title_bar;
    private ImageView mIv_cliscan_close;
    private PopupWindow mPopupWindow;
    private ImageView mIv_cli_scan_show;

    public Fragment01()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (mLayout == null)
        {
            mLayout = inflater.inflate(R.layout.fragment_fragment01, null);
            initData();
            initUI();
            setOnClick();
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mLayout.getParent();
        if (parent != null)
        {
            parent.removeView(mLayout);
        }
        return mLayout;
    }


    private void initData()
    {
        //初始化时间
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        //初始化Banner数据
        initBannerData();
    }

    private void initBannerData()
    {
        HTTPUtils.get(getActivity(), ConstantTicket.URL.GET_BANNER_IMG, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {

            }

            @Override
            public void onResponse(String s)
            {
                Type type = new TypeToken<ArrayList<BannerInfo>>()
                {
                }.getType();
                bannerData = GsonUtils.parseJSONArray(s, type);
                mViewPager_banner.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
            }
        });
    }


    private void initUI()
    {
        initBanner();
        mChooseSetOut = (LinearLayout) mLayout.findViewById(R.id.ll_choose_set_out);
        mTv_setOut = (TextView) mLayout.findViewById(R.id.tv_set_out);
        mChooseArrive = (LinearLayout) mLayout.findViewById(R.id.ll_choose_arrive);
        mTv_arrive = (TextView) mLayout.findViewById(R.id.tv_arrive);
        mChooseTime = (LinearLayout) mLayout.findViewById(R.id.ll_time);
        mTv_date = (TextView) mLayout.findViewById(R.id.tv_date);
        mTv_date.setText(mYear + "-" + mMonth + "-" + mDayOfMonth);
        mIv_Exchange = (ImageView) mLayout.findViewById(R.id.iv_exchange);
        mBtn_query = (Button) mLayout.findViewById(R.id.btn_query);
        mIv_cliscan = (ImageView) mLayout.findViewById(R.id.iv_cliscan);
        mLl_title_bar = (LinearLayout) mLayout.findViewById(R.id.ll_title_bar);
    }

    /**
     * 设置监听
     */
    private void setOnClick()
    {
        mChooseSetOut.setOnClickListener(this);
        mChooseArrive.setOnClickListener(this);
        mChooseTime.setOnClickListener(this);
        mIv_Exchange.setOnClickListener(this);
        mBtn_query.setOnClickListener(this);
        mIv_cliscan.setOnClickListener(this);
    }

    /**
     * 设置广告条
     */
    private void initBanner()
    {
        mViewPager_banner = (ViewPager) mLayout.findViewById(R.id.vp_headerview_pager);
        mViewPager_banner.addOnPageChangeListener(new BannerOnPageChangeListener());
        mViewPagerIndicator = (ViewPagerIndicator) mLayout.findViewById(R.id.ViewPagerIndicator);
        if (isFrist)
        {
            autoScroll();
        }
    }


    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.ll_time:
                //跳转日期选择器
                showDatePickerDialog();
                break;
            case R.id.ll_choose_set_out:
                //跳转出发车站选择界面
                intent.setClass(getActivity(), SelectStationSetOutActivity.class);
                startActivityForResult(intent, ConstantTicket.Request.REQUEST_CODE_CHOOSE_SET_OUT);
                animFromLeftToRight();
                break;
            case R.id.ll_choose_arrive:
                //跳转到达车站选择界面
                intent.setClass(getActivity(), SelectStationArriveActivity.class);
                startActivityForResult(intent, ConstantTicket.Request.REQUEST_CODE_CHOOSE_ARRIVE);
                animFromLeftToRight();
                break;
            case R.id.iv_exchange:
                setIsChoose();
                //设置切换出发车站及到达车站
                if (!isChoose)
                {
                    DialogShow.setDialog(getActivity(), "您暂未选择相关站点", "确认");
                } else
                {
                    String setOutLocation = mTv_setOut.getText().toString();
                    String arriveLocation = mTv_arrive.getText().toString();
                    mTv_arrive.setText(setOutLocation);
                    mTv_setOut.setText(arriveLocation);
                }
                break;
            case R.id.btn_query:
//                if (isLogin) {
                setIsChoose();
                if (!isChoose)
                {
                    DialogShow.setDialog(getActivity(), "您暂未选择相关站点", "确认");
                } else
                {
                    intent.setClass(getActivity(), TicketActivity.class);
                    intent.putExtra(ConstantTicket.IntentKey.CURR_YEAR, mYear);
                    intent.putExtra(ConstantTicket.IntentKey.CURR_MONTH, mMonth);
                    intent.putExtra(ConstantTicket.IntentKey.CURR_DAY_OF_MONTH, mDayOfMonth);
                    String str_setOut = mTv_setOut.getText().toString();
                    String str_arrive = mTv_arrive.getText().toString();
                    intent.putExtra(ConstantTicket.IntentKey.FINAIL_SET_OUT_STATION, str_setOut);
                    intent.putExtra(ConstantTicket.IntentKey.FINAIL_ARRIVE_STATION, str_arrive);
                    startActivity(intent);
                    animFromSmallToBigIN();
                }
//                } else {
//                    intent.setClass(getActivity(), SmsLoginActivity.class);
//                    startActivity(intent);
//                }

                break;
            case R.id.iv_cliscan:
                //弹出二维码
                showPopCliScan();
                break;
            case R.id.iv_cliscan_close:
                mPopupWindow.dismiss();
                break;
        }
    }

    private void showPopCliScan()
    {
        View inflate = getActivity().getLayoutInflater().inflate(R.layout.layout_pop_cli_scan_show, null);
        mIv_cliscan_close = (ImageView) inflate.findViewById(R.id.iv_cliscan_close);
        mIv_cliscan_close.setOnClickListener(this);
        mIv_cli_scan_show = (ImageView) inflate.findViewById(R.id.iv_cli_scan_show);
        UILUtils.displayImageNoAnim(ConstantTicket.URL.CLI_SCAN, mIv_cli_scan_show, false);
        //最后一个参数为true，点击PopupWindow消失,宽必须为match，不然肯呢个会导致布局显示不完全
        mPopupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //设置外部点击无效
        mPopupWindow.setOutsideTouchable(false);
        //设置背景变暗
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        BitmapDrawable bitmapDrawable = new BitmapDrawable();
        mPopupWindow.setBackgroundDrawable(bitmapDrawable);
        mPopupWindow.showAtLocation(mLl_title_bar, Gravity.CENTER, 0, 0);
    }


    /**
     * 从小到大打开动画
     */
    private void animFromSmallToBigIN()
    {
        getActivity().overridePendingTransition(R.anim.magnify_fade_in, R.anim.fade_out);
    }

    private void setIsChoose()
    {
        if ("请选择出发地".equals(mTv_setOut.getText().toString().trim()) || "请选择目的地".equals(mTv_arrive.getText().toString().trim()))
        {
            isChoose = false;
        } else
        {
            isChoose = true;
        }
    }

    /**
     * 界面跳转动画
     */
    private void animFromLeftToRight()
    {
        getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.fade_out);
    }

    public void showDatePickerDialog()
    {
        DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth)
            {
                try
                {
                    mDateCompare = DateCompareUtil.DateCompare(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth, c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH));
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                if (mDateCompare)
                {
                    mYear = year;
                    mMonth = monthOfYear + 1;
                    mDayOfMonth = dayOfMonth;
                    mTv_date.setText(mYear + "-" + mMonth + "-" + mDayOfMonth);
//                    mAlertDialog.cancel();
                } else
                {
                    Toast.makeText(getActivity(), "预售10天内的车票，请重新选择！", Toast.LENGTH_SHORT).show();
                    mTv_date.setText(mYear + "-" + mMonth + "-" + mDayOfMonth);
                }
            }
        }, mYear, mMonth - 1, mDayOfMonth).show(getActivity().getFragmentManager(), "datePicker");

    }

    class MyPagerAdapter extends FragmentPagerAdapter
    {

        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            int pager_index = position % bannerData.size();
            return new BannerFragment(pager_index, bannerData.get(pager_index).getUrl(), bannerData.get(pager_index).getUrl2());
        }

        @Override
        public int getCount()
        {
            return mPagerCount;
        }
    }

    class BannerOnPageChangeListener implements ViewPager.OnPageChangeListener
    {
        public void onPageScrollStateChanged(int state)
        {
            switch (state)
            {
                case ViewPager.SCROLL_STATE_IDLE:
                    mDragging = false;
                    break;
                case ViewPager.SCROLL_STATE_DRAGGING:
                    mDragging = true;
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    mDragging = false;
                    break;
                default:
                    break;
            }
        }

        public void onPageScrolled(int position, float arg1, int arg2)
        {
            position = position % 3;
            mViewPagerIndicator.move(arg1, position);
        }

        public void onPageSelected(int arg0)
        {
        }
    }

    private void autoScroll()
    {
        mViewPager_banner.setCurrentItem(mPagerCount / 2);
        mViewPager_banner.postDelayed(new Runnable()
        {
            public void run()
            {
                int position = mViewPager_banner.getCurrentItem() + 1;
                if (!mDragging)
                {
                    isFrist = false;
                    mViewPager_banner.setCurrentItem(position);
                }
                mViewPager_banner.postDelayed(this, 3000);
            }
        }, 3000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null)
        {
            switch (requestCode)
            {
                //选择出发地请求码
                case ConstantTicket.Request.REQUEST_CODE_CHOOSE_SET_OUT:
                    switch (resultCode)
                    {
                        case ConstantTicket.ResultCode.RESULT_CODE_SET_OUT_ADDR:
                            //选择出发城市地址的返回码
                            mTv_setOut.setText(data.getStringExtra(ConstantTicket.IntentKey.KEY_SET_OUT_ZONE_NAME));
                            break;
                        case ConstantTicket.ResultCode.RESULT_CODE_SEARCH_ADDR:
                            //搜索出发城市地址的返回码
                            mTv_setOut.setText(data.getStringExtra(ConstantTicket.IntentKey.KEY_SET_OUT_ZONE_NAME));
                            break;
                        case ConstantTicket.ResultCode.RESULT_CODE_COMMONLY_USED_ADDR:
                            //常用出发城市地址的返回码
                            mTv_setOut.setText(data.getStringExtra(ConstantTicket.IntentKey.KEY_SET_OUT_ZONE_NAME));
                            break;
                    }
                    break;
                //选择目的地请求码
                case ConstantTicket.Request.REQUEST_CODE_CHOOSE_ARRIVE:
                    switch (resultCode)
                    {
                        case ConstantTicket.ResultCode.RESULT_CODE_ARRIVE_ADDR:
                            //选择目的地的返回码
                            mTv_arrive.setText(data.getStringExtra(ConstantTicket.IntentKey.KEY_ARRIVE_ZONE_NAME));
                            break;
                        case ConstantTicket.ResultCode.RESULT_CODE_ARRIVE_SEARCH_ADDR:
                            //搜索目的地的返回码
                            mTv_arrive.setText(data.getStringExtra(ConstantTicket.IntentKey.KEY_ARRIVE_ZONE_NAME));
                            break;
                        case ConstantTicket.ResultCode.RESULT_CODE_ARRIVE_COMMONLY_USED_ADDR:
                            //常用目的地的返回码
                            mTv_arrive.setText(data.getStringExtra(ConstantTicket.IntentKey.KEY_ARRIVE_ZONE_NAME));
                            break;
                    }
                    break;
            }
        }
    }

    /**
     * 从大到小结束动画
     */
    private void animFromBigToSmallOUT()
    {
        getActivity().overridePendingTransition(R.anim.fade_in, R.anim.big_to_small_fade_out);
    }

}
