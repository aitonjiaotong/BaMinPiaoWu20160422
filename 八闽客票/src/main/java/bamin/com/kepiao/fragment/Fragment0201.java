package bamin.com.kepiao.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aiton.administrator.shane_library.shane.utils.GsonUtils;
import com.aiton.administrator.shane_library.shane.utils.HTTPUtils;
import com.aiton.administrator.shane_library.shane.utils.VolleyListener;
import com.android.volley.VolleyError;
import com.andview.refreshview.XRefreshView;
import com.umeng.analytics.MobclickAgent;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bamin.com.kepiao.R;
import bamin.com.kepiao.activity.OrderDeatilActivity;
import bamin.com.kepiao.activity.PayActivity;
import bamin.com.kepiao.constant.Constant;
import bamin.com.kepiao.customView.CustomerFooter;
import bamin.com.kepiao.models.about_order.AccountOrder;
import bamin.com.kepiao.models.about_order.QueryOrder;
import bamin.com.kepiao.utils.DialogShow;
import bamin.com.kepiao.utils.TimeAndDateFormate;


public class Fragment0201 extends Fragment {

    private View mInflate;
    private ListView mOrderListview;
    private String mId;
    private MyAdapter mMyAdapter;
    private List<AccountOrder.ContainsEntity> mAccountOrderEntityList = new ArrayList<>();
    private List<QueryOrder> mQueryOrderList = new ArrayList<>();
    private List<String> orderStateList = new ArrayList<>();
    private boolean mIsupdata = false;
    //请求订单的页数
    private int orderPageCount = 0;
    private AccountOrder mAccountOrder;
    //订单总页数
    private int mPages;
    private XRefreshView mCustom_view;
    private TextView mNoneOrder;
    private boolean isItemClickED = false;
    //是否安全刷新
    private boolean isSure = false;
    private QueryOrder mQueryOrderNull;

    public Fragment0201() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_fragment0201, null);
            /**
             * 获取用户id
             */
            SharedPreferences sp = getActivity().getSharedPreferences(Constant.SP_KEY.SP_NAME, Context.MODE_PRIVATE);
            mId = sp.getString(Constant.SP_KEY.ID, "");
            initUI();
//            clearData();
//            queryAccountIdToOrder();
        }
//缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) mInflate.getParent();
        if (parent != null) {
            parent.removeView(mInflate);
        }
        return mInflate;
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sp = getActivity().getSharedPreferences(Constant.SP_KEY.SP_NAME, Context.MODE_PRIVATE);
        mId = sp.getString(Constant.SP_KEY.ID, "");
        listGone();
    }

    @Override
    public void onStop() {
        super.onStop();
        mCustom_view.stopRefresh();
        mCustom_view.stopLoadMore();
    }

    /**
     * 根据用户id查询所有订单号
     */
    //查询所有有订单号
    private void queryAccountIdToOrder() {
        String url = Constant.HOST_TICKET + "/front/ladorderbyuser";
        Map<String, String> map = new HashMap<>();
        map.put("account_id", mId);
        map.put("page", orderPageCount + "");
        HTTPUtils.post(getActivity(), url, map, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mCustom_view.stopRefresh();
                mCustom_view.stopLoadMore();
            }

            @Override
            public void onResponse(String s) {
                Log.e("onResponse", "所有订单号" + s);
                //每查询一次页数要+1
                orderPageCount++;
                /**
                 *刷新时先清除，重新获取。
                 */
//                mAccountOrderList.clear();
//                mQueryOrderList.clear();
//                orderStateList.clear();
//                Type type = new TypeToken<ArrayList<AccountOrder>>() {
//                }.getType();
//                mAccountOrderList = GsonUtils.parseJSONArray(s, type);
                mAccountOrder = GsonUtils.parseJSON(s, AccountOrder.class);
//                /**
//                 * 翻转容器，让最近的排在最前面
//                 */
//                Collections.reverse(mAccountOrder.getOrders());
                mAccountOrderEntityList.addAll(mAccountOrder.getContains());
                mPages = mAccountOrder.getNum();
                /**
                 * 暂无订单显示与否
                 */
                if (mAccountOrderEntityList.size() > 0) {
                    /**
                     * 将所有订单对象和状态都实例化
                     */
                    mQueryOrderNull = new QueryOrder("1", 1, null, null, null, null, null, 1, false, false, null, null, null, null, null, 1.1, null, null, "正在生成");
                    for (int i = 0; i < mAccountOrder.getContains().size(); i++) {
                        mQueryOrderList.add(mQueryOrderNull);
                        orderStateList.add("正在生成");
                    }
                    /**
                     * 查询所有订单号的对象和状态
                     */
                    for (int i = 0; i < mAccountOrder.getContains().size(); i++) {
                        queryAllOrderInfo((orderPageCount - 1) * 8 + i);
                        queryAllOrderState((orderPageCount - 1) * 8 + i);
                    }
                } else {
                    mCustom_view.stopRefresh();
                    mCustom_view.stopLoadMore();
                    mNoneOrder.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 查询所有订单状态
     *
     * @param i
     */
    private void queryAllOrderState(final int i) {
        String url_web = Constant.JDT_TICKET_HOST +
                "SellTicket_Other_NoBill_GetBookStateAndMinuteToConfirm?scheduleCompanyCode=" + "YongAn" + "" +
                "&bookLogID=" + mAccountOrder.getContains().get(i - (orderPageCount - 1) * 8).getBookLogAID();
        HTTPUtils.get(getActivity(), url_web, new VolleyListener() {
            public void onErrorResponse(VolleyError volleyError) {
                mCustom_view.stopRefresh();
                mCustom_view.stopLoadMore();
            }

            public void onResponse(String s) {
                Document doc = null;
                try {
                    doc = DocumentHelper.parseText(s);
                    Element testElement = doc.getRootElement();
                    String testxml = testElement.asXML();
                    String result = testxml.substring(testxml.indexOf(">") + 1, testxml.lastIndexOf("<"));
                    String state = result.substring(2, 5);
                    orderStateList.remove(i);
                    orderStateList.add(i, state);
                    refreashSure();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IndexOutOfBoundsException e) {
                }
            }
        });
    }

    /**
     * 查询单个订单状态
     *
     * @param i
     */
    private void queryOrderState(final int i) {
        String url_web = Constant.JDT_TICKET_HOST +
                "SellTicket_Other_NoBill_GetBookStateAndMinuteToConfirm?scheduleCompanyCode=" + "YongAn" + "" +
                "&bookLogID=" + mAccountOrderEntityList.get(i).getBookLogAID();
        Log.e("queryOrderState", "订单列表ID" + mAccountOrderEntityList.get(i).getId());
        HTTPUtils.get(getActivity(), url_web, new VolleyListener() {
            public void onErrorResponse(VolleyError volleyError) {
            }

            public void onResponse(String s) {
                Document doc = null;
                try {
                    doc = DocumentHelper.parseText(s);
                    Element testElement = doc.getRootElement();
                    String testxml = testElement.asXML();
                    String result = testxml.substring(testxml.indexOf(">") + 1, testxml.lastIndexOf("<"));
                    String state = result.substring(2, 5);
                    if ("已确认".equals(state)) {
                        Intent intent = new Intent();
                        intent.putExtra("BookLogAID", mAccountOrderEntityList.get(i).getBookLogAID());
                        intent.putExtra("isSure", "isSure");
                        intent.putExtra("insurePrice", mAccountOrderEntityList.get(i).getInsure());
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.setClass(getActivity(), OrderDeatilActivity.class);
                        startActivity(intent);
                        animFromSmallToBigIN();
                    } else if ("未确认".equals(state)) {
                        //0是正在出票1是出票成功
                        if (mAccountOrderEntityList.get(i).getStatus() == 0) {
                            DialogShow.setDialog(getActivity(), "正在出票，请稍后下拉刷新试试", "确认");
                        } else if (mAccountOrderEntityList.get(i).getStatus() == 1) {

                        } else if (mAccountOrderEntityList.get(i).getStatus() == 3) {
                            DialogShow.setDialog(getActivity(), "订单出现异常，请联系客服", "确认");
                        } else {
                            Intent intent = new Intent();
                            intent.putExtra("BookLogAID", mAccountOrderEntityList.get(i).getBookLogAID());
                            intent.putExtra("realPrice", mAccountOrderEntityList.get(i).getPrice());
                            intent.putExtra("OrderId", mAccountOrderEntityList.get(i).getId() + "");
                            intent.putExtra("insurePrice", mAccountOrderEntityList.get(i).getInsure());
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.setClass(getActivity(), PayActivity.class);
                            startActivity(intent);
                            animFromSmallToBigIN();
                        }
                    } else if ("已撤销".equals(state)) {
                        DialogShow.setDialog(getActivity(), "订单已撤销", "确认");
                    } else if ("已取票".equals(state)) {
                        Intent intent = new Intent();
                        intent.putExtra("BookLogAID", mAccountOrderEntityList.get(i).getBookLogAID());
                        intent.putExtra("isSure", "isSure");
                        intent.putExtra("insurePrice", mAccountOrderEntityList.get(i).getInsure());
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.setClass(getActivity(), OrderDeatilActivity.class);
                        startActivity(intent);
                        animFromSmallToBigIN();
                    }
                    isItemClickED = false;
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IndexOutOfBoundsException e) {
                }
            }
        });
    }

    /**
     * 查询所有订单号的对象
     */
    private void queryAllOrderInfo(final int i) {
        String url = Constant.JDT_TICKET_HOST +
                "QueryBookLog?getTicketCodeOrAID=" + mAccountOrder.getContains().get(i - (orderPageCount - 1) * 8).getBookLogAID();
        HTTPUtils.get(getActivity(), url, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mCustom_view.stopRefresh();
                mCustom_view.stopLoadMore();
            }

            @Override
            public void onResponse(String s) {
                Document doc = null;
                try {
                    doc = DocumentHelper.parseText(s);
                    Element testElement = doc.getRootElement();
                    String testxml = testElement.asXML();
                    String result = testxml.substring(testxml.indexOf(">") + 1, testxml.lastIndexOf("<"));
                    QueryOrder queryOrder = GsonUtils.parseJSON(result, QueryOrder.class);
                    mQueryOrderList.remove(i);
                    mQueryOrderList.add(i, queryOrder);
                    refreashSure();

                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (IndexOutOfBoundsException e) {
                }
            }
        });
    }

    private void listGone() {
        /**
         * 防止刷新不一致崩掉
         */
        if (!orderStateList.contains("正在生成") && !mQueryOrderList.contains(mQueryOrderNull)) {
//            isSure = true;
//            mIsupdata = true;
//            mCustom_view.stopRefresh();
//            mCustom_view.stopLoadMore();
//            mMyAdapter.notifyDataSetChanged();
        } else {
            mOrderListview.setVisibility(View.GONE);
        }
    }

    private void refreashSure() {
        /**
         * 防止刷新不一致崩掉
         */
        if (!orderStateList.contains("正在生成") && !mQueryOrderList.contains(mQueryOrderNull)) {
//            isSure = true;
            mIsupdata = true;
            mCustom_view.stopRefresh();
            mCustom_view.stopLoadMore();
            mMyAdapter.notifyDataSetChanged();
            mOrderListview.setVisibility(View.VISIBLE);
        }
    }

    private void initUI() {
        mNoneOrder = (TextView) mInflate.findViewById(R.id.noneOrder);
        mCustom_view = (XRefreshView) mInflate.findViewById(R.id.custom_view);
        mOrderListview = (ListView) mInflate.findViewById(R.id.order_listView);
        mMyAdapter = new MyAdapter();
        mOrderListview.setAdapter(mMyAdapter);
        mOrderListview.setOnItemClickListener(new MyItemClickListener());
        initXRefreshView();
    }

    private void initXRefreshView() {
        mCustom_view.setPullLoadEnable(true);
        mCustom_view.setPinnedTime(1000);
        mCustom_view.setAutoLoadMore(true);
        mCustom_view.setMoveForHorizontal(true);
        mCustom_view.setCustomFooterView(new CustomerFooter(getActivity()));
        mCustom_view.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                mNoneOrder.setVisibility(View.GONE);
                clearData();
                queryAccountIdToOrder();
            }

            @Override
            public void onLoadMore(boolean isSlience) {
                if (orderPageCount < mPages) {
                    queryAccountIdToOrder();
                } else {
                    mCustom_view.stopLoadMore();
                    Toast.makeText(getActivity(), "没有更多订单了", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**
         * 根据用户id查询所有订单号
         */
        mCustom_view.startRefresh();
    }

    class MyItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            isItemClickED = !isItemClickED;
            if (isItemClickED) {
                queryOrderState(position);
            }
        }
    }

    /**
     * 从小到大打开动画
     */
    private void animFromSmallToBigIN() {
        getActivity().overridePendingTransition(R.anim.magnify_fade_in, R.anim.fade_out);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mQueryOrderList.size();
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
            View inflate = getLayoutInflater(getArguments()).inflate(R.layout.order_listitem, null);
            TextView textView_startPlace = (TextView) inflate.findViewById(R.id.textView_startPlace);
            TextView textView_endPlace = (TextView) inflate.findViewById(R.id.textView_endPlace);
            TextView textView_startTime = (TextView) inflate.findViewById(R.id.textView_startTime);
            TextView textView_ticketCount = (TextView) inflate.findViewById(R.id.textView_ticketCount);
            TextView textView_orderPrice = (TextView) inflate.findViewById(R.id.textView_orderPrice);
            ImageView imageView_ticketState = (ImageView) inflate.findViewById(R.id.imageView_ticketState);
            if (mIsupdata) {
                textView_startPlace.setText(mQueryOrderList.get(position).getStartSiteName());
                textView_endPlace.setText(mQueryOrderList.get(position).getEndSiteName());
                textView_startTime.setText("出发日期：" + TimeAndDateFormate.dateFormate(mQueryOrderList.get(position).getSetoutTime()));
                textView_ticketCount.setText("票数：" + mQueryOrderList.get(position).getFullTicket());
                /**
                 * 修改返回状态字眼
                 */
                if ("已确认".equals(orderStateList.get(position))) {
                    imageView_ticketState.setImageResource(R.mipmap.yizhifu);
                } else if ("未确认".equals(orderStateList.get(position))) {
                    if (mAccountOrderEntityList.get(position).getStatus() == 0) {
                        imageView_ticketState.setImageResource(R.mipmap.zhengzaichupiao);
                    } else if (mAccountOrderEntityList.get(position).getStatus() == 1) {
                        imageView_ticketState.setImageResource(R.mipmap.yizhifu);
                    } else if (mAccountOrderEntityList.get(position).getStatus() == 3) {
                        imageView_ticketState.setImageResource(R.mipmap.yichangdingdan);
                    } else {
                        imageView_ticketState.setImageResource(R.mipmap.weizhifu);
                    }
                } else if ("已撤销".equals(orderStateList.get(position))) {
                    imageView_ticketState.setImageResource(R.mipmap.yichexiao);
                } else if ("已取票".equals(orderStateList.get(position))) {
                    imageView_ticketState.setImageResource(R.mipmap.yiqupiao);
                }
                textView_orderPrice.setText(mAccountOrderEntityList.get(position).getPrice() + "");
            }
            return inflate;
        }
    }


    //清除订单页面数据
    private void clearData() {
        orderPageCount = 0;
        mAccountOrderEntityList.clear();
        mQueryOrderList.clear();
        orderStateList.clear();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }
}
