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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiton.administrator.shane_library.shane.utils.GsonUtils;
import com.aiton.administrator.shane_library.shane.utils.HTTPUtils;
import com.aiton.administrator.shane_library.shane.utils.PinyinUtils;
import com.aiton.administrator.shane_library.shane.utils.VolleyListener;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bamin.com.kepiao.R;
import bamin.com.kepiao.constant.Constant;
import bamin.com.kepiao.models.about_companysubzone.CompanySubZone;
import bamin.com.kepiao.models.about_companysubzone.SubZone_;
import bamin.com.kepiao.sql.MySqLite;
import bamin.com.kepiao.utils.GetLastWordUtil;

public class SelectStationSetOutActivity extends AppCompatActivity implements View.OnClickListener
{
    //数据库相关----------start
    private String TAB_NAME = "setout";
    private int mVersion = 1;
    private SQLiteDatabase mDb;
    private ContentValues mValues;
    //数据库相关----------end

    //常用地址相关------start
    private ListView mLv_commonly_used_address;
    private List<String> mCommonly_used_address_data = new ArrayList<String>();
    private UsedAddressAdapter mUsedAddressAdapter;
    //常用地址相关------end

    //出发地区相关--------start
    private GridView mGridview_address_set_out;
    private List<CompanySubZone> SetOutData = new ArrayList<CompanySubZone>();//整个Json返回值的数据List
    private List<SubZone_> mAddressSetOutData = new ArrayList<SubZone_>();//实际出发地的选择地区名称列表
    private SetOutAdapter mSetOutAdapter;
    //出发地区相关--------end

    //搜索列表相关--------start
    private List<String> pinYin_data = new ArrayList<String>();//拼音List数据列表
    private List<String> searchAddrData = new ArrayList<String>();//搜索List数据列表
    private ListView mLv_search_address;
    private RelativeLayout mRl_for_search_list;
    private SearchAddrAdapter mSearchAddrAdapter;
    //搜索列表相关--------end

    private ImageView mIv_back;
    private EditText mEt_search_zone;
    private RadioGroup mRg_set_out_tabs;
    private ImageView mIv_edit_text_clear;
    private LinearLayout mLl_for_progress_bar;
    private String mUser_input;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_station_set_out);

        MySqLite mySqLite = new MySqLite(SelectStationSetOutActivity.this, mVersion);
        mDb = mySqLite.getWritableDatabase();
        mValues = new ContentValues();
        findViewID();
        setListener();
        initUI();
        initData();
    }

    private void findViewID()
    {
        mIv_back = (ImageView) findViewById(R.id.iv_back);
        mEt_search_zone = (EditText) findViewById(R.id.et_search_zone);
        mRg_set_out_tabs = (RadioGroup) findViewById(R.id.rg_set_out_tabs);
        mIv_edit_text_clear = (ImageView) findViewById(R.id.iv_clear);
        mLv_commonly_used_address = (ListView) findViewById(R.id.lv_commonly_used_address);
        mGridview_address_set_out = (GridView) findViewById(R.id.gridview_address_set_out);
        mLl_for_progress_bar = (LinearLayout) findViewById(R.id.ll_for_progress_bar);
        mLv_search_address = (ListView) findViewById(R.id.lv_search_address);
        mRl_for_search_list = (RelativeLayout) findViewById(R.id.rl_for_search_list);
    }

    private void setListener()
    {
        mIv_back.setOnClickListener(this);
        mIv_edit_text_clear.setOnClickListener(this);
    }

    private void initUI()
    {
        initEditText();
        initSetOutTabsStage();
        initCommonlyUsedAddress();
        initSetOutAddress();
        initSearchList();
    }

    private void initSearchList()
    {
        mLv_search_address.setOnScrollListener(new AbsListView.OnScrollListener()
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
        mSearchAddrAdapter = new SearchAddrAdapter();
        mLv_search_address.setAdapter(mSearchAddrAdapter);
        mLv_search_address.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //判断数据库中是否有保存过的数据
                if (!hasCollected(TAB_NAME, "addr_name", searchAddrData.get(position)))
                {
                    mValues.put("addr_name", searchAddrData.get(position));
                    mDb.insert(TAB_NAME, null, mValues);
                } else
                {
                    mDb.delete(TAB_NAME, "addr_name = ?", new String[]{searchAddrData.get(position)});
                    mValues.put("addr_name", searchAddrData.get(position));
                    mDb.insert(TAB_NAME, null, mValues);
                }
                Intent data = new Intent();
                if ("沙县".equals(searchAddrData.get(position)))
                {
                    data.putExtra(Constant.IntentKey.KEY_SET_OUT_ZONE_NAME, searchAddrData.get(position));
                } else
                {
                    data.putExtra(Constant.IntentKey.KEY_SET_OUT_ZONE_NAME, GetLastWordUtil.GetRidOfLastWord(searchAddrData.get(position)));
                }
                setResult(Constant.RequestAndResultCode.RESULT_CODE_SET_OUT_ADDR, data);
                finish();
                animFromBigToSmallOUT();
            }
        });
    }

    private void initData()
    {
        HTTPUtils.get(SelectStationSetOutActivity.this, Constant.Url.GET_COMPANY_SUBZONE, new VolleyListener()
        {
            public void onErrorResponse(VolleyError volleyError)
            {
            }

            public void onResponse(String s)
            {
                Type type = new TypeToken<ArrayList<CompanySubZone>>()
                {
                }.getType();
                SetOutData = GsonUtils.parseJSONArray(s, type);
                mAddressSetOutData.clear();
                for (int i = 0; i < SetOutData.size(); i++)
                {
                    for (int j = 0; j < SetOutData.get(i).getSubZones().size(); j++)
                    {
                        mAddressSetOutData.addAll(SetOutData.get(i).getSubZones().get(j).getSubZones());
                    }
                }
                if (mAddressSetOutData != null && mAddressSetOutData.size() > 0)
                {
                    pinYin_data = getPinYin(mAddressSetOutData);
                    mSetOutAdapter.notifyDataSetChanged();
                    mLl_for_progress_bar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initSetOutAddress()
    {
        mSetOutAdapter = new SetOutAdapter();
        mGridview_address_set_out.setAdapter(mSetOutAdapter);
        mGridview_address_set_out.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent data = new Intent();
                if ("沙县".equals(mAddressSetOutData.get(position).getZoneName()))
                {
                    data.putExtra(Constant.IntentKey.KEY_SET_OUT_ZONE_NAME, mAddressSetOutData.get(position).getZoneName());
                } else
                {
                    data.putExtra(Constant.IntentKey.KEY_SET_OUT_ZONE_NAME, GetLastWordUtil.GetRidOfLastWord(mAddressSetOutData.get(position).getZoneName()));
                }
                setResult(Constant.RequestAndResultCode.RESULT_CODE_SET_OUT_ADDR, data);

                //保存用户选择后的地址到本地，储存为常用地址---start
                if (!hasCollected(TAB_NAME, "addr_name", mAddressSetOutData.get(position).getZoneName()))
                {
                    mValues.put("addr_name", mAddressSetOutData.get(position).getZoneName());
                    mDb.insert(TAB_NAME, null, mValues);

                } else
                {
                    mDb.delete(TAB_NAME, "addr_name = ?", new String[]{mAddressSetOutData.get(position).getZoneName()});
                    mValues.put("addr_name", mAddressSetOutData.get(position).getZoneName());
                    mDb.insert(TAB_NAME, null, mValues);
                }
                //保存用户选择后的地址到本地，储存为常用地址---end
                finish();
                animFromBigToSmallOUT();

            }
        });
    }

    private void initCommonlyUsedAddress()
    {
        mUsedAddressAdapter = new UsedAddressAdapter();
        mLv_commonly_used_address.setAdapter(mUsedAddressAdapter);
        queryDB();
        mUsedAddressAdapter.notifyDataSetChanged();
        mLv_commonly_used_address.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //判断数据库中是否有保存过的数据------start
                if (!hasCollected(TAB_NAME, "addr_name", mCommonly_used_address_data.get(position)))
                {
                    mValues.put("addr_name", mCommonly_used_address_data.get(position));
                    mDb.insert(TAB_NAME, null, mValues);
                } else
                {
                    mDb.delete(TAB_NAME, "addr_name = ?", new String[]{mCommonly_used_address_data.get(position)});
                    mValues.put("addr_name", mCommonly_used_address_data.get(position));
                    mDb.insert(TAB_NAME, null, mValues);
                }
                //判断数据库中是否有保存过的数据------start
                Intent data = new Intent();
                if ("沙县".equals(mCommonly_used_address_data.get(position)))
                {
                    data.putExtra(Constant.IntentKey.KEY_SET_OUT_ZONE_NAME, mCommonly_used_address_data.get(position));
                } else
                {
                    data.putExtra(Constant.IntentKey.KEY_SET_OUT_ZONE_NAME, GetLastWordUtil.GetRidOfLastWord(mCommonly_used_address_data.get(position)));
                }
                setResult(Constant.RequestAndResultCode.RESULT_CODE_SET_OUT_ADDR, data);
                finish();
                animFromBigToSmallOUT();
            }
        });

    }

    private void initSetOutTabsStage()
    {
        mRg_set_out_tabs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.rb_comm_used_addr:
                        mLl_for_progress_bar.setVisibility(View.GONE);
                        mLv_commonly_used_address.setVisibility(View.VISIBLE);
                        mGridview_address_set_out.setVisibility(View.GONE);
                        break;
                    case R.id.rb_set_out:
                        if (mAddressSetOutData != null && mAddressSetOutData.size() > 0)
                        {
                            mLl_for_progress_bar.setVisibility(View.GONE);
                        } else
                        {
                            mLl_for_progress_bar.setVisibility(View.VISIBLE);
                        }
                        mLv_commonly_used_address.setVisibility(View.GONE);
                        mGridview_address_set_out.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void initEditText()
    {
        mEt_search_zone.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                mUser_input = s.toString();
                if ("".equals(mUser_input))
                {
                    mIv_edit_text_clear.setVisibility(View.GONE);//搜索框的清除按钮
                    mRl_for_search_list.setVisibility(View.GONE);//搜索列表
                } else
                {
                    mIv_edit_text_clear.setVisibility(View.VISIBLE);//搜索框的清除按钮
                    mRl_for_search_list.setVisibility(View.VISIBLE);//搜索列表
                }
                searchAddrData.clear();
                mSearchAddrAdapter.notifyDataSetChanged();
                //比对用户输入的内容，并提取更新显示相关控件
                for (int i = 0; i < mAddressSetOutData.size(); i++)
                {
                    String zoneName = mAddressSetOutData.get(i).getZoneName();
                    if (mAddressSetOutData.get(i).getZoneName().startsWith(mUser_input.trim()) || pinYin_data.get(i).startsWith(mUser_input.trim().toLowerCase()))
                    {
                        searchAddrData.add(zoneName);
                        mSearchAddrAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.iv_back:
                finish();
                AnimFromRightToLeft();
                break;
            case R.id.iv_clear:
                mEt_search_zone.setText("");
                mRl_for_search_list.setVisibility(View.GONE);
                break;
        }
    }

    private void AnimFromRightToLeft()
    {
        overridePendingTransition(R.anim.fade_in, R.anim.push_left_out);
    }

    /**
     * 查询本地数据库
     */
    public void queryDB()
    {
        Cursor mCursor_query = mDb.query(TAB_NAME, null, null, null, null, null, null);
        mCommonly_used_address_data.clear();
        boolean moveToFirst = mCursor_query.moveToFirst();
        while (moveToFirst)
        {
            String addr_name = mCursor_query.getString(mCursor_query.getColumnIndex("addr_name"));
            mCommonly_used_address_data.add(addr_name);
            moveToFirst = mCursor_query.moveToNext();
        }
        Collections.reverse(mCommonly_used_address_data);
        mCursor_query.close();
    }

    /**
     * 查询本地数据库，判断是否有保存过的数据
     */
    private boolean hasCollected(String tabName, String columns, String selectionArags)
    {
        Cursor mCursor_query = mDb.query(tabName, new String[]{columns}, columns + "=?", new String[]{selectionArags}, null, null, null);
        boolean hasCollected = mCursor_query.moveToNext();
        mCursor_query.close();
        return hasCollected;
    }

    /**
     * 从大到小结束动画
     */
    private void animFromBigToSmallOUT()
    {
        overridePendingTransition(R.anim.fade_in, R.anim.big_to_small_fade_out);
    }

    /**
     * 汉字转拼音并截取首字母
     */
    public List<String> getPinYin(List<SubZone_> data)
    {
        List<String> pinyin_list = new ArrayList<>();
        for (int i = 0; i < data.size(); i++)
        {
            String pingYin = PinyinUtils.getAlpha(data.get(i).getZoneName());
            String str_lower = pingYin.toLowerCase();
            pinyin_list.add(str_lower);
        }
        return pinyin_list;
    }


    /**
     * 常用地址ListView适配器
     */
    class UsedAddressAdapter extends BaseAdapter
    {

        @Override
        public int getCount()
        {
            if (mCommonly_used_address_data != null && mCommonly_used_address_data.size() > 0)
            {
                if (mCommonly_used_address_data.size() > 6)
                {
                    return 6;
                } else
                {
                    return mCommonly_used_address_data.size();
                }
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
            if (mCommonly_used_address_data != null && mCommonly_used_address_data.size() > 0)
            {
                mLv_commonly_used_address.setClickable(true);
                mLv_commonly_used_address.setEnabled(true);
                tv_com_used_addr.setText(mCommonly_used_address_data.get(position));
            } else
            {
                mLv_commonly_used_address.setClickable(false);
                mLv_commonly_used_address.setEnabled(false);
                tv_com_used_addr.setText("没有查找到数据！");
            }
            return layout;
        }

    }

    /**
     * 出发地区的ListView适配器
     */
    class SetOutAdapter extends BaseAdapter
    {
        public int getCount()
        {
            if (mAddressSetOutData != null && mAddressSetOutData.size() > 0)
            {
                return mAddressSetOutData.size();
            } else
            {
                return 0;
            }
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
            View layout = getLayoutInflater().inflate(R.layout.list_item_city_set_out, null);
            TextView tv_cityName = (TextView) layout.findViewById(R.id.tv_city);
            TextView pinyin = (TextView) layout.findViewById(R.id.tv_pinyin);
            if (mAddressSetOutData != null && mAddressSetOutData.size() > 0)
            {
                tv_cityName.setText(mAddressSetOutData.get(position).getZoneName());
            }
            if (pinYin_data != null && pinYin_data.size() > 0)
            {
                pinyin.setText(pinYin_data.get(position));
            }
            return layout;
        }
    }

    /**
     * 用户搜索时显示列表的适配器
     */
    class SearchAddrAdapter extends BaseAdapter
    {
        public int getCount()
        {
            return searchAddrData.size();
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
            if (searchAddrData != null && searchAddrData.size() > 0)
            {
                tv_city_search.setText(searchAddrData.get(position));
            }
            return layout;
        }
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
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}