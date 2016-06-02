package bamin.com.kepiao.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.aiton.administrator.shane_library.shane.utils.GsonUtils;
import com.aiton.administrator.shane_library.shane.utils.HTTPUtils;
import com.aiton.administrator.shane_library.shane.utils.UILUtils;
import com.aiton.administrator.shane_library.shane.utils.VolleyListener;
import com.android.volley.VolleyError;

import bamin.com.kepiao.R;
import bamin.com.kepiao.constant.Constant;
import bamin.com.kepiao.models.about_redpacket.RedPacket;

public class BannerFragment extends Fragment
{


    //红包相关-----start
    private PopupWindow pop;
    //红包相关-----end
    private View mLayout;
    private int mPosition;
    private String mImageUrl;
    private ImageView mBanner_image;
    private String mRedPacketUrl;

    public BannerFragment()
    {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public BannerFragment(int pager_index, String imageUrl, String redpacketUrl)
    {
        this.mPosition = pager_index;
        this.mImageUrl = imageUrl;
        this.mRedPacketUrl = redpacketUrl;
    }

    @SuppressLint("ValidFragment")
    public BannerFragment(int pager_index)
    {
        this.mPosition = pager_index;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        mLayout = inflater.inflate(R.layout.fragment_banner, null);
        initUI();
        return mLayout;
    }

    private void initUI()
    {
        mBanner_image = (ImageView) mLayout.findViewById(R.id.iv_banner_image);
        UILUtils.displayImageNoAnim(mImageUrl, mBanner_image);
        mBanner_image.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!"".equals(mRedPacketUrl))
                {
                    SharedPreferences sp = getActivity().getSharedPreferences(Constant.SP_KEY.SP_NAME, Context.MODE_PRIVATE);
                    String account_id = sp.getString("id", "");
                    String isLogin = sp.getString("phoneNum", "");
                    //弹出抢红包对话框
                    if (!"".equals(isLogin))
                    {
                        HTTPUtils.get(getActivity(), mRedPacketUrl + "&account_id=" + account_id, new VolleyListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                            }

                            @Override
                            public void onResponse(String s) {
                                if ("".equals(s)) {
                                    Toast.makeText(getActivity(), "您已领取过红包，每个用户限领一份", Toast.LENGTH_SHORT).show();
                                } else {
                                    RedPacket redPacket = GsonUtils.parseJSON(s, RedPacket.class);
                                    showPopWindows("￥" + redPacket.getAmount());
                                }
                            }
                        });
                    } else
                    {
                        Toast.makeText(getActivity(), "您还未登录", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private PopupWindow showPopWindows(String moneyStr)
    {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_red_packet, null);
        TextView tv_rule = (TextView) view.findViewById(R.id.tv_rule);
        tv_rule.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        TextView money = (TextView) view.findViewById(R.id.tv_money);
        money.setText(moneyStr);
        view.findViewById(R.id.btn_ikow).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pop.dismiss();
            }
        });

        pop = new PopupWindow(view, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        //设置背景变暗-----start
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener()
        {

            @Override
            public void onDismiss()
            {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        //设置背景变暗-----end
        BitmapDrawable bitmapDrawable = new BitmapDrawable();
        pop.setBackgroundDrawable(bitmapDrawable);
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        pop.showAtLocation(view, Gravity.CENTER, 0, 0);
        return pop;
    }
}
