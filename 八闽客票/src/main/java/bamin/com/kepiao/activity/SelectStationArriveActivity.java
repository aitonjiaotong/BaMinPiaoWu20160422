package bamin.com.kepiao.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiton.administrator.shane_library.shane.utils.GsonUtils;
import com.aiton.administrator.shane_library.shane.utils.HTTPUtils;
import com.aiton.administrator.shane_library.shane.utils.VolleyListener;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bamin.com.kepiao.R;
import bamin.com.kepiao.constant.ConstantTicket;
import bamin.com.kepiao.customView.MyGridView;
import bamin.com.kepiao.models.about_companysubzone.CompanySubZone;
import bamin.com.kepiao.models.about_companysubzone.SubZone_;
import bamin.com.kepiao.models.about_sites.Sites;
import bamin.com.kepiao.sql.MySqLite;
import bamin.com.kepiao.utils.GetLastWordUtil;

public class SelectStationArriveActivity extends AppCompatActivity implements View.OnClickListener
{
    //数据库相关----------start
    private String TAB_NAME = "arrive";
    private int mVersion = 1;
    private SQLiteDatabase mDb;
    private ContentValues mValues;
    //数据库相关----------end

    //控制子gridview的开关
    private int mIsOpen = -1;
    private int mShiPostion = 0;
    private ImageView mIv_back;
    //常用地址相关
    private ListView mLv_commonly_used_address;
    private List<String> mComUsedAddrData = new ArrayList<String>();
    private CommuonUsedAddrAdapter mAdapter = new CommuonUsedAddrAdapter();

    //省份的所有数据List列表
    private List<CompanySubZone> parent_list_data = new ArrayList<CompanySubZone>();
    //省份的所有数据List列表(省份名称的字符串)
    private List<String> parent_list_name = new ArrayList<String>();
    private List<SubZone_> parent_list_xianshi_name = new ArrayList<>();
    //关联省份与省份下一级的市
    private Map<String, List<String>> map = new HashMap<String, List<String>>();
    //搜索列表相关
    private List<Sites> mSitesData = new ArrayList<Sites>();
    private List<String> mUserSearchSitesData = new ArrayList<String>();

    private TextView mTv_btn_arrive;
    private TextView mTv_btn_comm_used_addr;
    private GridView mGridView_xianshi;
    private RelativeLayout mXianshi_rela;
    private MyGridViewAdapter mMyGridViewAdapter;
    private ListView mArrive_listView;
    private MyArriveAdapter mMyArriveAdapter;
    private EditText mEt_search_city;
    private ListView mLv_search_addr;
    private String mUser_input;
    private boolean isCommonlyAddr = true;
    private ImageView mIv_clear;
    private SearchAddrAdapter mSearchAdapter = new SearchAddrAdapter();
    private RelativeLayout mRl_for_search_address;
    private LinearLayout mLl_for_progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_station_arrive);

        initDB();
        findViewID();
        initUI();
        setOnclick();
        initData();
    }

    private void initData()
    {
        initBaseData();
        initSitesData();
    }

    private void initBaseData()
    {
        HTTPUtils.get(SelectStationArriveActivity.this, ConstantTicket.URL.GET_ZONE_STREE, new VolleyListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
            }

            @Override
            public void onResponse(String s)
            {
                /**--------解析Json------------------*/
                Type type = new TypeToken<ArrayList<CompanySubZone>>()
                {
                }.getType();
                ArrayList<CompanySubZone> o = GsonUtils.parseJSONArray(s, type);
                //加载解析Json得到各省份数据
                parent_list_data.clear();
                parent_list_data.addAll(o);
                for (int i = 0; i < parent_list_data.size(); i++)
                {
                    //获取各省份名称，放置于List容器中，用于适配器中更新相关数据
                    parent_list_name.add(i, parent_list_data.get(i).getZoneName());
                    List<String> list1 = new ArrayList<String>();//保存省份下一级的各市地区名称(字符串)
                    for (int j = 0; j < parent_list_data.get(i).getSubZones().size(); j++)
                    {
                        list1.add(parent_list_data.get(i).getSubZones().get(j).getZoneName());
                    }
                    map.put(parent_list_data.get(i).getZoneName(), list1);
                }
                mMyArriveAdapter.notifyDataSetChanged();
                mLl_for_progress_bar.setVisibility(View.GONE);

            }
        });


    }

    private void findViewID()
    {
        mRl_for_search_address = (RelativeLayout) findViewById(R.id.rl_for_search_address);
        mIv_clear = (ImageView) findViewById(R.id.iv_clear);
        mXianshi_rela = (RelativeLayout) findViewById(R.id.xianshi_rela);
        mGridView_xianshi = (GridView) findViewById(R.id.gridView_xianshi);
        mIv_back = (ImageView) findViewById(R.id.iv_back);
        mLv_commonly_used_address = (ListView) findViewById(R.id.lv_commonly_used_address);

        mArrive_listView = (ListView) findViewById(R.id.arrive_listView);
        mTv_btn_arrive = (TextView) findViewById(R.id.tv_btn_arrive);
        mTv_btn_comm_used_addr = (TextView) findViewById(R.id.tv_btn_comm_used_addr);
        mEt_search_city = (EditText) findViewById(R.id.et_search_city);
        mLv_search_addr = (ListView) findViewById(R.id.lv_search_address);
        mLl_for_progress_bar = (LinearLayout) findViewById(R.id.ll_for_progress_bar);

    }

    private void initDB()
    {
        MySqLite mySqLite = new MySqLite(SelectStationArriveActivity.this, mVersion);
        mDb = mySqLite.getWritableDatabase();
        mValues = new ContentValues();
    }


    private void initUI()
    {
        initEdiText();
        initCommonlyUsedAddr();
        initArriveAddr();
        initUserSearchAddr();
    }

    private void initArriveAddr()
    {
        mMyArriveAdapter = new MyArriveAdapter();
        mArrive_listView.setAdapter(mMyArriveAdapter);
        mArrive_listView.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL)
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {

            }
        });

        mMyGridViewAdapter = new MyGridViewAdapter();
        mGridView_xianshi.setAdapter(mMyGridViewAdapter);
    }

    private void initCommonlyUsedAddr()
    {
        mLv_commonly_used_address.setAdapter(mAdapter);
        mLv_commonly_used_address.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent();
                if ("沙县".equals(mComUsedAddrData.get(position)))
                {
                    intent.putExtra(ConstantTicket.IntentKey.KEY_ARRIVE_ZONE_NAME, mComUsedAddrData.get(position));
                } else
                {
                    intent.putExtra(ConstantTicket.IntentKey.KEY_ARRIVE_ZONE_NAME, GetLastWordUtil.GetRidOfLastWord(mComUsedAddrData.get(position)));
                }
                setResult(ConstantTicket.ResultCode.RESULT_CODE_ARRIVE_COMMONLY_USED_ADDR, intent);
                finish();
                animFromBigToSmallOUT();
            }
        });
        queryDB();
        mAdapter.notifyDataSetChanged();
    }

    private void initEdiText()
    {
        mEt_search_city.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if (mSitesData != null && mSitesData.size() > 0)
                {
                    mLl_for_progress_bar.setVisibility(View.GONE);
                } else
                {
                    mLl_for_progress_bar.setVisibility(View.VISIBLE);
                }
                mUser_input = s.toString();
                if ("".equals(mUser_input))
                {
                    mIv_clear.setVisibility(View.GONE);//搜索框的清除按钮
                    mRl_for_search_address.setVisibility(View.GONE);
                    mLl_for_progress_bar.setVisibility(View.GONE);//提示加载
                } else
                {
                    mIv_clear.setVisibility(View.VISIBLE);//搜索框的清除按钮
                    mRl_for_search_address.setVisibility(View.VISIBLE);
                    mUserSearchSitesData.clear();
                    mSearchAdapter.notifyDataSetChanged();
                    //比对用户输入的内容，并提取更新显示相关控件
                    for (int i = 0; i < mSitesData.size(); i++)
                    {
                        String siteName = mSitesData.get(i).getSiteName();
                        if (mSitesData.get(i).getSiteName().startsWith(mUser_input.trim()) || mSitesData.get(i).getSiteCode().toLowerCase().startsWith(mUser_input.trim().toLowerCase()))
                        {
                            mUserSearchSitesData.add(siteName);
                            mSearchAdapter.notifyDataSetChanged();
                        }
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
    }

    //初始化用户搜索列表ListView
    private void initUserSearchAddr()
    {
        mLv_search_addr.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL)
                {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
            }
        });

        mLv_search_addr.setAdapter(mSearchAdapter);
        mLv_search_addr.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //判断数据库中是否有保存过的数据
                Cursor mCursor_query = mDb.query(TAB_NAME, new String[]{"addr_name"}, "addr_name=?", new String[]{mUserSearchSitesData.get(position)}, null, null, null);
                if (!mCursor_query.moveToNext())
                {
                    mValues.put("addr_name", mUserSearchSitesData.get(position));
                    mDb.insert(TAB_NAME, null, mValues);
                } else
                {
                    mDb.delete(TAB_NAME, "addr_name = ?", new String[]{mUserSearchSitesData.get(position)});
                    mValues.put("addr_name", mUserSearchSitesData.get(position));
                    mDb.insert(TAB_NAME, null, mValues);
                }
                mCursor_query.close();

                Intent data = new Intent();
                if ("沙县".equals(mUserSearchSitesData.get(position)))
                {
                    data.putExtra(ConstantTicket.IntentKey.KEY_ARRIVE_ZONE_NAME, mUserSearchSitesData.get(position));
                } else
                {
                    data.putExtra(ConstantTicket.IntentKey.KEY_ARRIVE_ZONE_NAME, GetLastWordUtil.GetRidOfLastWord(mUserSearchSitesData.get(position)));
                }
                setResult(ConstantTicket.ResultCode.RESULT_CODE_ARRIVE_SEARCH_ADDR, data);
                finish();
                animFromBigToSmallOUT();
            }
        });
    }

    /**
     * 用户搜索时显示列表的适配器
     */
    class SearchAddrAdapter extends BaseAdapter
    {
        public int getCount()
        {
            return mUserSearchSitesData.size();
        }

        public Object getItem(int position)
        {
            return null;
        }

        public long getItemId(int position)
        {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View layout = getLayoutInflater().inflate(R.layout.list_item_search_city, null);
            TextView tv_city_search = (TextView) layout.findViewById(R.id.tv_city);
            if (mUserSearchSitesData != null && mUserSearchSitesData.size() > 0)
            {
                tv_city_search.setText(mUserSearchSitesData.get(position));
            }
            return layout;
        }
    }

    class MyArriveAdapter extends BaseAdapter implements View.OnClickListener
    {

        private MyGridView mShi_gridView;
        private MyGridAdapter mMyGridAdapter;

        @Override
        public int getCount()
        {
            return parent_list_data.size();
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
            View inflate = getLayoutInflater().inflate(R.layout.arrive_listitem, null);
            LinearLayout shi_linear = (LinearLayout) inflate.findViewById(R.id.shi_linear);
            TextView listTv = (TextView) inflate.findViewById(R.id.listTv);
            listTv.setText(parent_list_data.get(position).getZoneName());
            listTv.setTag(position);
            listTv.setOnClickListener(this);
            mShi_gridView = (MyGridView) inflate.findViewById(R.id.shi_gridView);
            mMyGridAdapter = new MyGridAdapter();
            mShi_gridView.setAdapter(mMyGridAdapter);
            mShi_gridView.setOnItemClickListener(new MyShiGridViewItemListener());
            if (position == mIsOpen)
            {
                mMyGridAdapter.notifyDataSetChanged();
                shi_linear.setVisibility(View.VISIBLE);
            } else
            {
                shi_linear.setVisibility(View.GONE);
            }
            return inflate;
        }

        class MyShiGridViewItemListener implements AdapterView.OnItemClickListener
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                parent_list_xianshi_name.clear();
                parent_list_xianshi_name.addAll(parent_list_data.get(mShiPostion).getSubZones().get(position).getSubZones());
                mMyGridViewAdapter.notifyDataSetChanged();
                mArrive_listView.setVisibility(View.GONE);//到达列表
                mXianshi_rela.setVisibility(View.VISIBLE);//到达县市列表
            }
        }

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.listTv:
                    int position = (int) v.getTag();
                    if (position == mIsOpen)
                    {
                        mIsOpen = -1;
                    } else
                    {
                        mIsOpen = position;
                        mShiPostion = position;
                    }
                    mMyArriveAdapter.notifyDataSetChanged();
                    break;
            }
        }

        class MyGridAdapter extends BaseAdapter
        {

            @Override
            public int getCount()
            {
                return parent_list_data.get(mShiPostion).getSubZones().size();
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
                View inflate = getLayoutInflater().inflate(R.layout.list_item_city_set_out, null);
                TextView shi_tv = (TextView) inflate.findViewById(R.id.tv_city);
                shi_tv.setText(parent_list_data.get(mShiPostion).getSubZones().get(position).getZoneName());
                return inflate;
            }
        }
    }

    class MyGridViewAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            return parent_list_xianshi_name.size();
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
            View inflate = getLayoutInflater().inflate(R.layout.list_item_city_set_out, null);
            TextView tv_city = (TextView) inflate.findViewById(R.id.tv_city);
            tv_city.setText(parent_list_xianshi_name.get(position).getZoneName());
            return inflate;
        }
    }


//    /**
//     * 加载所有站点
//     */
//    private void initSitesData()
//    {
//        HTTPUtils.get(SelectStationArriveActivity.this, ConstantTicket.URLFromAiTon.GET_SITE, new VolleyListener()
//        {
//            @Override
//            public void onErrorResponse(VolleyError volleyError)
//            {
//            }
//
//            @Override
//            public void onResponse(String s)
//            {
//                Type type = new TypeToken<ArrayList<Sites>>()
//                {
//                }.getType();
//                mSitesData = GsonUtils.parseJSONArray(s, type);
//                mLl_for_progress_bar.setVisibility(View.GONE);
//            }
//        });
//    }

    /**
     * 将所有站点的Json数据文件放在本地打包
     */
    private void initSitesData()
    {
        InputStream inputStream = getResources().openRawResource(R.raw.siteinfo01);
        String siteInfo = getString(inputStream);
        Type type = new TypeToken<ArrayList<Sites>>(){}.getType();
        mSitesData = GsonUtils.parseJSONArray(siteInfo, type);
        mLl_for_progress_bar.setVisibility(View.GONE);
    }

    /**
     * 解析本地Json数据文件的String数据
     */
    public static String getString(InputStream inputStream)
    {
        InputStreamReader inputStreamReader = null;
        try
        {
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        } catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line;
        try
        {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return sb.toString();
    }


    private void setOnclick()
    {
        mIv_back.setOnClickListener(this);
        mTv_btn_arrive.setOnClickListener(this);
        mTv_btn_comm_used_addr.setOnClickListener(this);
        findViewById(R.id.back_to_shengshi).setOnClickListener(this);
        mGridView_xianshi.setOnItemClickListener(new MyGridViewOnItemClickListener());
        mIv_clear.setOnClickListener(this);
    }

    class MyGridViewOnItemClickListener implements AdapterView.OnItemClickListener
    {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            //判断数据库中是否有保存过的数据
            Cursor mCursor_query = mDb.query(TAB_NAME, new String[]{"addr_name"}, "addr_name=?", new String[]{parent_list_xianshi_name.get(position).getZoneName()}, null, null, null);
            if (!mCursor_query.moveToNext())
            {
                mValues.put("addr_name", parent_list_xianshi_name.get(position).getZoneName());
                mDb.insert(TAB_NAME, null, mValues);
            } else
            {
                mDb.delete(TAB_NAME, "addr_name = ?", new String[]{parent_list_xianshi_name.get(position).getZoneName()});
                mValues.put("addr_name", parent_list_xianshi_name.get(position).getZoneName());
                mDb.insert(TAB_NAME, null, mValues);
            }
            mCursor_query.close();
            Intent intent = new Intent();
            if ("沙县".equals(parent_list_xianshi_name.get(position).getZoneName()))
            {
                intent.putExtra(ConstantTicket.IntentKey.KEY_ARRIVE_ZONE_NAME, parent_list_xianshi_name.get(position).getZoneName());
            } else
            {
                intent.putExtra(ConstantTicket.IntentKey.KEY_ARRIVE_ZONE_NAME, GetLastWordUtil.GetRidOfLastWord(parent_list_xianshi_name.get(position).getZoneName()));
            }
            setResult(ConstantTicket.ResultCode.RESULT_CODE_ARRIVE_ADDR, intent);
            //保存用户选择后的地址到本地，储存为常用地址---start
            finish();
            animFromBigToSmallOUT();
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.back_to_shengshi:
                mArrive_listView.setVisibility(View.VISIBLE);//到达列表
                mXianshi_rela.setVisibility(View.GONE);//到达县市列表
                break;
            case R.id.iv_back:
                finish();
                AnimFromRightToLeft();
                break;
            case R.id.tv_btn_arrive:
                isCommonlyAddr = false;
                mTv_btn_arrive.setBackgroundResource(R.color.tabs_select);
                mTv_btn_arrive.setTextColor(getResources().getColor(R.color.white));
                mTv_btn_comm_used_addr.setBackgroundResource(R.color.gray);
                mTv_btn_comm_used_addr.setTextColor(getResources().getColor(R.color.fillin_order_pay_gray_bg));
                mLv_commonly_used_address.setVisibility(View.GONE);//常用地址
                mArrive_listView.setVisibility(View.VISIBLE);//到达列表
                mXianshi_rela.setVisibility(View.GONE);//县级地址列表
                if (parent_list_data != null && parent_list_data.size() > 0)
                {
                    mLl_for_progress_bar.setVisibility(View.GONE);
                } else
                {
                    mLl_for_progress_bar.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_btn_comm_used_addr:
                isCommonlyAddr = true;
                mTv_btn_comm_used_addr.setBackgroundResource(R.color.tabs_select);
                mTv_btn_comm_used_addr.setTextColor(getResources().getColor(R.color.white));
                mTv_btn_arrive.setBackgroundResource(R.color.gray);
                mTv_btn_arrive.setTextColor(getResources().getColor(R.color.fillin_order_pay_gray_bg));

                mLv_commonly_used_address.setVisibility(View.VISIBLE);//常用地址
                mArrive_listView.setVisibility(View.GONE);//到达列表
                mXianshi_rela.setVisibility(View.GONE);//县级地址列表
                break;
            case R.id.iv_clear:
                mEt_search_city.setText("");
                mRl_for_search_address.setVisibility(View.GONE);//用户搜索列表
                mLl_for_progress_bar.setVisibility(View.GONE);//提示加载
                break;
        }
    }

    class CommuonUsedAddrAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            if (mComUsedAddrData != null && mComUsedAddrData.size() > 0)
            {
                return mComUsedAddrData.size();
            } else
            {
                return 1;
            }
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
            View layout = getLayoutInflater().inflate(R.layout.list_item_commonly_used_address, null);
            TextView tv_com_used_addr = (TextView) layout.findViewById(R.id.tv_commonly_used_address);
            if (mComUsedAddrData != null && mComUsedAddrData.size() > 0)
            {
                tv_com_used_addr.setText(mComUsedAddrData.get(position));
                mLv_commonly_used_address.setClickable(true);
                mLv_commonly_used_address.setEnabled(true);
            } else
            {
                tv_com_used_addr.setText("没有查找到数据！");
                mLv_commonly_used_address.setClickable(false);
                mLv_commonly_used_address.setEnabled(false);
            }
            return layout;
        }
    }

    /**
     * 查询本地数据库
     */
    public void queryDB()
    {
        Cursor mCursor_query = mDb.query(TAB_NAME, null, null, null, null, null, null);
        mComUsedAddrData.clear();
        boolean moveToFirst = mCursor_query.moveToFirst();
        while (moveToFirst)
        {
            String addr_name = mCursor_query.getString(mCursor_query.getColumnIndex("addr_name"));
            mComUsedAddrData.add(addr_name);
            moveToFirst = mCursor_query.moveToNext();
        }
        Collections.reverse(mComUsedAddrData);
        mCursor_query.close();
    }

    private void AnimFromRightToLeft()
    {
        overridePendingTransition(R.anim.fade_in, R.anim.push_left_out);
    }

    /**
     * 从大到小结束动画
     */
    private void animFromBigToSmallOUT()
    {
        overridePendingTransition(R.anim.fade_in, R.anim.big_to_small_fade_out);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            finish();
            AnimFromRightToLeft();
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


