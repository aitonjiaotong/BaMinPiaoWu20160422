package bamin.com.kepiao.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bamin.com.kepiao.R;
import bamin.com.kepiao.activity.CouponInfoActivity;
import bamin.com.kepiao.activity.SmsLoginActivity;
import bamin.com.kepiao.activity.SoftInfo;
import bamin.com.kepiao.activity.TicketNotice;
import bamin.com.kepiao.activity.UsedContact;


public class MoreFragment extends Fragment implements View.OnClickListener {
    private View mInflate;
    private boolean isLogined = false;
    private String mPhoneNum;
    private String mId;

    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_more, null);
            initUI();
            setListener();
        }
//缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mInflate.getParent();
        if (parent != null) {
            parent.removeView(mInflate);
        }
        return mInflate;
    }

    private void setListener() {
        mInflate.findViewById(R.id.used_contact).setOnClickListener(this);
        mInflate.findViewById(R.id.couponInfo_rela).setOnClickListener(this);
        mInflate.findViewById(R.id.Discount).setOnClickListener(this);
        mInflate.findViewById(R.id.softInfo).setOnClickListener(this);
    }

    private void initUI() {

    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sp = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        mPhoneNum = sp.getString("phoneNum", "");
        mId = sp.getString("id", "");
        if ("".equals(mPhoneNum)) {
            isLogined = false;
        } else {
            isLogined = true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent intent = new Intent();
        switch (requestCode) {
            case 2:
                if (isLogined) {

                    intent.setClass(getActivity(), UsedContact.class);
                    startActivity(intent);
                }
                break;
            case 6:
                if (isLogined) {
                    intent.setClass(getActivity(), CouponInfoActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 界面跳转动画
     */
    private void animFromLeftToRight() {
        getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.fade_out);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.couponInfo_rela:
                if (!isLogined) {
                    intent.setClass(getActivity(), SmsLoginActivity.class);
                    startActivityForResult(intent, 6);
                    animFromLeftToRight();
                } else {
                    intent.setClass(getActivity(), CouponInfoActivity.class);
                    startActivity(intent);
                    animFromLeftToRight();
                }
                break;
            case R.id.used_contact:
                if (!isLogined) {
                    intent.setClass(getActivity(), SmsLoginActivity.class);
                    startActivityForResult(intent, 2);
                    animFromLeftToRight();
                } else {
                    intent.putExtra("addContact", "MineFragment");
                    intent.setClass(getActivity(), UsedContact.class);
                    startActivity(intent);
                    animFromLeftToRight();
                }
                break;
            case R.id.Discount:
                intent.setClass(getActivity(), TicketNotice.class);
                startActivity(intent);
                animFromLeftToRight();
                break;
            case R.id.softInfo:
                intent.setClass(getActivity(), SoftInfo.class);
                startActivity(intent);
                animFromLeftToRight();
                break;
        }
    }
}
