package bamin.com.kepiao.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aiton.administrator.shane_library.shane.utils.GsonUtils;
import com.aiton.administrator.shane_library.shane.utils.HTTPUtils;
import com.aiton.administrator.shane_library.shane.utils.VolleyListener;
import com.android.volley.VolleyError;
import com.github.lguipeng.library.animcheckbox.AnimCheckBox;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bamin.com.kepiao.R;
import bamin.com.kepiao.constant.ConstantTicket;
import bamin.com.kepiao.models.about_used_contact.AddContant;
import bamin.com.kepiao.models.about_used_contact.UsedContactInfo;

/**
 * 常用乘客页面
 * 优化1.0
 */
public class UsedContact extends AppCompatActivity implements View.OnClickListener {
    private List<UsedContactInfo> mUsedContactInfoList = new ArrayList<>();
    private MyAdapter mAdapter;
    private ListView mUsed_contact_listview;
    private String mAddContact;
    private String mId;
    private TextView mAdd_passager;
    private List<UsedContactInfo> theAddContact = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_used_contact);
        initItent();
        initSp();
        initUI();
        initData();
        setListener();
    }

    private void initSp() {
        SharedPreferences sp = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        mId = sp.getString("id", "");
    }

    private void initItent() {
        Intent intent = getIntent();
        mAddContact = intent.getStringExtra("addContact");
    }

    /**
     * 从服务端获取联系人
     */
    private void initData() {
        String url = ConstantTicket.URL.GET_USED_CONTACT;
        Map<String, String> map = new HashMap<>();
        map.put("account_id", mId);
        HTTPUtils.post(UsedContact.this, url, map, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }

            @Override
            public void onResponse(String s) {
                Type type = new TypeToken<ArrayList<UsedContactInfo>>() {
                }.getType();
                mUsedContactInfoList = GsonUtils.parseJSONArray(s, type);
//                初始化选乘客的position
                for (int i = 0; i < mUsedContactInfoList.size(); i++) {
                    theAddContact.add(i, null);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setListener() {
        findViewById(R.id.iv_back).setOnClickListener(this);
        mAdd_passager.setOnClickListener(this);
        mUsed_contact_listview.setOnItemClickListener(new MyItemClickListener());
        mUsed_contact_listview.setOnItemLongClickListener(new MyItemLongClickListener());
        findViewById(R.id.textView_add_passager).setOnClickListener(this);
    }

    class MyItemLongClickListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            setDialog("删除联系人"+mUsedContactInfoList.get(position).getName(), position);
            return true;
        }
    }

    class MyItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            if ("FillinOrderActivity".equals(mAddContact)) {
                AnimCheckBox animcheck_contact = (AnimCheckBox) view.findViewById(R.id.animcheck_contact);
                if (animcheck_contact.isChecked()) {
                    animcheck_contact.setChecked(false, true);

                    Log.e("onItemClick ", "checked");
                } else {
                    animcheck_contact.setChecked(true, true);
                    Log.e("onItemClick ", "unchecked");
                }
            } else if ("MineFragment".equals(mAddContact)) {
                intent.putExtra("bianji", "MineFragment");
                intent.putExtra("ticketPassager", mUsedContactInfoList.get(position));
                intent.setClass(UsedContact.this, AddFetcherActivity.class);
                startActivity(intent);
                animFromSmallToBigIN();
            }
        }
    }

    /**
     * 从小到大打开动画
     */
    private void animFromSmallToBigIN() {
        overridePendingTransition(R.anim.magnify_fade_in, R.anim.fade_out);
    }

    /**
     * 从大到小结束动画
     */
    private void animFromBigToSmallOUT() {
        overridePendingTransition(R.anim.fade_in, R.anim.big_to_small_fade_out);
    }

    private void initUI() {
        mAdd_passager = (TextView) findViewById(R.id.add_passager);
        mUsed_contact_listview = (ListView) findViewById(R.id.used_contact_listview);
        mAdapter = new MyAdapter();
        mUsed_contact_listview.setAdapter(mAdapter);
        if ("FillinOrderActivity".equals(mAddContact)) {
            mAdd_passager.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mUsedContactInfoList.size();
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
            View inflate = getLayoutInflater().inflate(R.layout.used_contact_listitem, null);
            TextView name = (TextView) inflate.findViewById(R.id.name);
            name.setText(mUsedContactInfoList.get(position).getName());
            TextView phone_num = (TextView) inflate.findViewById(R.id.phone_num);
            phone_num.setText(mUsedContactInfoList.get(position).getPhone());
            inflate.findViewById(R.id.imageView_bianji).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("bianji", "MineFragment");
                    intent.putExtra("ticketPassager", mUsedContactInfoList.get(position));
                    intent.setClass(UsedContact.this, AddFetcherActivity.class);
                    startActivity(intent);
                    animFromSmallToBigIN();
                }
            });
            RelativeLayout rela_animCheckBox = (RelativeLayout) inflate.findViewById(R.id.rela_animCheckBox);
            if ("FillinOrderActivity".equals(mAddContact)) {
                rela_animCheckBox.setVisibility(View.VISIBLE);
            }
            AnimCheckBox animcheck_contact = (AnimCheckBox) inflate.findViewById(R.id.animcheck_contact);
            animcheck_contact.setChecked(false,true);
            animcheck_contact.setOnCheckedChangeListener(new AnimCheckBox.OnCheckedChangeListener() {
                @Override
                public void onChange(boolean checked) {
                    if (checked) {
                        theAddContact.remove(position);
                        theAddContact.add(position, mUsedContactInfoList.get(position));
                    } else {
                        theAddContact.remove(position);
                        theAddContact.add(position, null);
                    }
                }
            });
            return inflate;
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.textView_add_passager:
                intent.setClass(UsedContact.this, AddFetcherActivity.class);
                startActivity(intent);
                AnimFromRightToLeftIN();
                break;
            case R.id.add_passager:
                List<UsedContactInfo> theAddContactList = new ArrayList<>();
                for (int i = 0; i < theAddContact.size(); i++) {
                    UsedContactInfo usedContactInfo = theAddContact.get(i);
                    if (usedContactInfo != null) {
                        theAddContactList.add(usedContactInfo);
                    }
                }
                AddContant addContant = new AddContant(theAddContactList);
                intent.putExtra("theAddContactList", addContant);
                setResult(ConstantTicket.ResultCode.RESULT_CODE_COMMIT_ORDER,intent);
                finish();
                animFromBigToSmallOUT();
                break;
            case R.id.iv_back:
                finish();
                AnimFromRightToLeftOUT();
                break;
        }
    }

    private void AnimFromRightToLeftOUT() {
        overridePendingTransition(R.anim.fade_in, R.anim.push_left_out);
    }

    private void AnimFromRightToLeftIN() {
        overridePendingTransition(R.anim.push_left_in, R.anim.fade_out);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            AnimFromRightToLeftOUT();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setDialog(String messageTxt, final int position) {
        View doublebuttondialog = getLayoutInflater().inflate(R.layout.doublebuttondialog, null);
        TextView message = (TextView) doublebuttondialog.findViewById(R.id.message);
        message.setText(messageTxt);
        AlertDialog.Builder builder = new AlertDialog.Builder(UsedContact.this);
        final AlertDialog dialog = builder.setView(doublebuttondialog)
                .create();
        dialog.setCancelable(false);
        dialog.show();
        doublebuttondialog.findViewById(R.id.ISee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                deleteUsedContact(position);
                //删除listview的联系人
                mUsedContactInfoList.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        doublebuttondialog.findViewById(R.id.button_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 向服务器发请求删除联系人
     */
    private void deleteUsedContact(int position) {
        String url = ConstantTicket.URL.DELETE_USED_CONTACT;
        Map<String, String> map = new HashMap<>();
        map.put("person_id", mUsedContactInfoList.get(position).getId() + "");
        HTTPUtils.post(UsedContact.this, url, map, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }

            @Override
            public void onResponse(String s) {

            }
        });
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
