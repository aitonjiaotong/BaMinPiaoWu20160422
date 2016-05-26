package bamin.com.kepiao.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aiton.administrator.shane_library.shane.upgrade.UpgradeUtils;
import com.aiton.administrator.shane_library.shane.utils.GsonUtils;
import com.aiton.administrator.shane_library.shane.utils.HTTPUtils;
import com.aiton.administrator.shane_library.shane.utils.VolleyListener;
import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import bamin.com.kepiao.R;
import bamin.com.kepiao.constant.ConstantTicket;
import bamin.com.kepiao.constant.EverythingConstant;
import bamin.com.kepiao.fragment.Fragment01;
import bamin.com.kepiao.fragment.Fragment0201;
import bamin.com.kepiao.fragment.MineFragment;
import bamin.com.kepiao.models.VersionAndHouTaiIsCanUse;

/**
 * 优化1.0
 */
public class MainActivity extends AppCompatActivity {
    private String[] tabsItem = new String[]{
            "查询",
            "订单",
            "我的"
    };
    private Class[] fragment = new Class[]{
            Fragment01.class,
            Fragment0201.class,
            MineFragment.class
    };
    private int[] imgRes = new int[]{
            R.drawable.ic_home_search_selector,
            R.drawable.dingdan_selector,
            R.drawable.ic_home_me_selector
    };
    private FragmentTabHost mTabHost;
    private String mDeviceId;
    private String mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSp();
        //检查服务器是否存活和当前版本是否可用
        checkVersionAndHouTaiIsCanUse();
        mTabHost = (FragmentTabHost) findViewById(R.id.tabHost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtab);
        for (int i = 0; i < tabsItem.length; i++) {
            View inflate = getLayoutInflater().inflate(R.layout.tabs_item, null);
            TextView tabs_text = (TextView) inflate.findViewById(R.id.tabs_text);
            ImageView tabs_img = (ImageView) inflate.findViewById(R.id.tabs_img);
            tabs_text.setText(tabsItem[i]);
            tabs_img.setImageResource(imgRes[i]);
            mTabHost.addTab(mTabHost.newTabSpec(tabsItem[i]).setIndicator(inflate), fragment[i], null);
        }
        //设置当前tabs
        Intent intent = getIntent();
        String orderDeatilActivity = intent.getStringExtra("OrderDeatilActivity");
        if ("OrderDeatilActivity".equals(orderDeatilActivity)) {
            mTabHost.setCurrentTab(1);
        }
    }

    private void checkVersionAndHouTaiIsCanUse() {
        String url = EverythingConstant.HOST + "/bmpw/check/live";
        Map<String, String> map = new HashMap<>();
        map.put("flag", "1");
        HTTPUtils.post(MainActivity.this, url, map, new VolleyListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                setDialogCkeck("服务器正在升级，暂停服务", "确认");
            }

            @Override
            public void onResponse(String s) {
                VersionAndHouTaiIsCanUse versionAndHouTaiIsCanUse = GsonUtils.parseJSON(s, VersionAndHouTaiIsCanUse.class);
                int ableVersion = versionAndHouTaiIsCanUse.getAbleVersion();
                if (versionAndHouTaiIsCanUse.isLive()) {
                    if (EverythingConstant.ABLEVERSION < ableVersion) {
                        setDialogCkeckAble("当前版本不可用，请更新到最新版本", "确认");
                    } else {
                        checkUpGrade();
                        //检查是否在其他设备上登录
                        checkIsLoginOnOtherDevice();
                    }
                } else {
                    setDialogCkeck(versionAndHouTaiIsCanUse.getMessage(), "确认");
                }
            }
        });
    }

    private void initSp() {
        SharedPreferences sp = getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        mId = sp.getString("id", "");
        mDeviceId = sp.getString("DeviceId", "");
    }

    private void checkUpGrade() {
        UpgradeUtils.checkUpgrade(MainActivity.this, ConstantTicket.URL.UP_GRADE);
    }

    /**
     * 检查是否在其他设备上登录
     */
    private void checkIsLoginOnOtherDevice() {
        if (!"".equals(mDeviceId)) {
            String url = EverythingConstant.HOST + "/bmpw/account/findLogin_id";
            Map<String, String> map = new HashMap<>();
            map.put("account_id", mId);
            map.put("flag", "1");
            HTTPUtils.post(MainActivity.this, url, map, new VolleyListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }

                @Override
                public void onResponse(String s) {
                    if (!s.equals(mDeviceId)) {
                        setDialog("你的账号登录异常\n请重新登录", "确定");
                    }
                }
            });
        }
    }

    /**
     * 弹出未登录按钮跳转登录界面并清除登录信息
     *
     * @param messageTxt
     * @param iSeeTxt
     */
    private void setDialog(String messageTxt, String iSeeTxt) {
        View commit_dialog = getLayoutInflater().inflate(R.layout.commit_dialog, null);
        TextView message = (TextView) commit_dialog.findViewById(R.id.message);
        Button ISee = (Button) commit_dialog.findViewById(R.id.ISee);
        message.setText(messageTxt);
        ISee.setText(iSeeTxt);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                intent.setClass(MainActivity.this, SmsLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 从大到小结束动画
     */
    private void animFromBigToSmallOUT() {
        overridePendingTransition(R.anim.fade_in, R.anim.big_to_small_fade_out);
    }

    //dialog提示
    private void setDialogCkeck(String messageTxt, String iSeeTxt) {
        View commit_dialog = getLayoutInflater().inflate(R.layout.commit_dialog, null);
        TextView message = (TextView) commit_dialog.findViewById(R.id.message);
        Button ISee = (Button) commit_dialog.findViewById(R.id.ISee);
        message.setText(messageTxt);
        ISee.setText(iSeeTxt);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog dialog = builder.setView(commit_dialog)
                .create();
        dialog.setCancelable(false);
        dialog.show();
        commit_dialog.findViewById(R.id.ISee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //dialog提示

    private void setDialogCkeckAble(String messageTxt, String iSeeTxt) {
        View commit_dialog = getLayoutInflater().inflate(R.layout.commit_dialog, null);
        TextView message = (TextView) commit_dialog.findViewById(R.id.message);
        Button ISee = (Button) commit_dialog.findViewById(R.id.ISee);
        message.setText(messageTxt);
        ISee.setText(iSeeTxt);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog dialog = builder.setView(commit_dialog)
                .create();
        dialog.setCancelable(false);
        dialog.show();
        commit_dialog.findViewById(R.id.ISee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               UpgradeUtils.checkUpgradeIsAble(MainActivity.this,ConstantTicket.URL.UP_GRADE);
            }
        });
    }

    /**
     * 双击退出应用
     */
    private long currentTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - currentTime > 1000) {
            Toast toast = Toast.makeText(MainActivity.this, "双击退出应用", Toast.LENGTH_SHORT);
            toast.show();
            currentTime = System.currentTimeMillis();
        } else {
            finish();
            animFromBigToSmallOUT();
        }
    }

    /**
     * 权限的处理
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EverythingConstant.RequestAndResultCode.PERMISSION_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示")
                        .setMessage("是否直接拨打客服电话400-0593-330")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //用intent启动拨打电话
                                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4000593330"));
                                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    // TODO: Consider calling
                                    //    ActivityCompat#requestPermissions
                                    // here to request the missing permissions, and then overriding
                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                    //                                          int[] grantResults)
                                    // to handle the case where the user grants the permission. See the documentation
                                    // for ActivityCompat#requestPermissions for more details.
                                    return;
                                }
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("否", null)
                        .create()
                        .show();
            } else {

            }
        } else if (requestCode == EverythingConstant.RequestAndResultCode.PERMISSION_READ_SMS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, SmsLoginActivity.class);
                startActivityForResult(intent, 6);
                animFromSmallToBigIN();
            } else {

            }
        }
    }

    /**
     * 从小到大打开动画
     */
    private void animFromSmallToBigIN() {
        overridePendingTransition(R.anim.magnify_fade_in, R.anim.fade_out);
    }
}
