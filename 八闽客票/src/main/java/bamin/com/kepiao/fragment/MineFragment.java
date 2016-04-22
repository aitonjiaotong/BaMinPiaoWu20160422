package bamin.com.kepiao.fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ecloud.pulltozoomview.PullToZoomScrollViewEx;
import com.umeng.analytics.MobclickAgent;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

import bamin.com.kepiao.R;
import bamin.com.kepiao.activity.CouponInfoActivity;
import bamin.com.kepiao.activity.SmsLoginActivity;
import bamin.com.kepiao.activity.SoftInfo;
import bamin.com.kepiao.activity.TicketNotice;
import bamin.com.kepiao.activity.UsedContact;
import bamin.com.kepiao.models.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    private final int PIC_FROM_CAMERA = 1;
    private final int PIC_FROM＿LOCALPHOTO = 0;
    private Uri photoUri;
    private PullToZoomScrollViewEx scrollView;
    private View mInflate;
    private ImageView mIc_avatar;
    private RelativeLayout mInfo;
    private RelativeLayout mUnLoginInfo;
    private String mPhoneNum;
    private TextView mName;
    private boolean isLogined = false;
    private PopupWindow mPopupWindow;
    private ScrollView mPullRootView;
    private String mId;
    private User mUser;
    private Button mButton_zuxiao;

    public MineFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mInflate == null) {
            mInflate = inflater.inflate(R.layout.fragment_fragment03, null);
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


    @Override
    public void onStart() {
        super.onStart();
        checkLogin();
    }

    private void checkLogin() {
        SharedPreferences sp = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
        mPhoneNum = sp.getString("phoneNum", "");
        mId = sp.getString("id", "");
        if ("".equals(mPhoneNum)) {
            isLogined = false;

            mInfo.setVisibility(View.INVISIBLE);
            mUnLoginInfo.setVisibility(View.VISIBLE);
            mIc_avatar.setImageResource(R.mipmap.ic_avatar);
            mName.setText("昵称");
            mButton_zuxiao.setVisibility(View.GONE);
        } else {
            isLogined = true;
            mInfo.setVisibility(View.VISIBLE);
            mUnLoginInfo.setVisibility(View.INVISIBLE);
            mButton_zuxiao.setVisibility(View.VISIBLE);
            mName.setText(mPhoneNum);
            String Path = "/upload/" + mPhoneNum + "upload.jpeg";
            File pictureFile = new File(Environment.getExternalStorageDirectory(), Path);
            if (pictureFile.exists()) {
                Uri uri = Uri.fromFile(pictureFile);
                Bitmap bitmap = decodeUriAsBitmap(uri);
                mIc_avatar.setImageBitmap(bitmap);
            }
        }
    }

    private void setListener() {
        mPullRootView.findViewById(R.id.setting).setOnClickListener(this);
        mButton_zuxiao = (Button) mPullRootView.findViewById(R.id.button_zuxiao);
        mButton_zuxiao.setOnClickListener(this);
        mPullRootView.findViewById(R.id.used_contact).setOnClickListener(this);
        mPullRootView.findViewById(R.id.Discount).setOnClickListener(this);
        mPullRootView.setOnClickListener(this);
        mPullRootView.findViewById(R.id.unlogin).setOnClickListener(this);
        mPullRootView.findViewById(R.id.ic_avatar).setOnClickListener(this);
        mPullRootView.findViewById(R.id.couponInfo_rela).setOnClickListener(this);
    }

    private void initUI() {
        loadViewForCode();
        scrollView = (PullToZoomScrollViewEx) mInflate.findViewById(R.id.scroll_view);
        mPullRootView = scrollView.getPullRootView();

        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenWidth = localDisplayMetrics.widthPixels;
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 24.0F)));
        scrollView.setHeaderLayoutParams(localObject);

        mIc_avatar = (ImageView) mPullRootView.findViewById(R.id.ic_avatar);
        mInfo = (RelativeLayout) mPullRootView.findViewById(R.id.info);
        mUnLoginInfo = (RelativeLayout) mPullRootView.findViewById(R.id.unLoginInfo);
        mName = (TextView) mPullRootView.findViewById(R.id.name);
    }

    /**
     * 根据不同方式选择图片设置ImageView
     *
     * @param type 0-本地相册选择，非0为拍照
     */
    private void doHandlerPhoto(int type) {
        try {
            //保存裁剪后的图片文件
            File pictureFileDir = new File(Environment.getExternalStorageDirectory(), "/upload");
            if (!pictureFileDir.exists()) {
                pictureFileDir.mkdirs();
            }
            File picFile = new File(pictureFileDir, mPhoneNum + "upload.jpeg");
            if (!picFile.exists()) {
                picFile.createNewFile();
            }
            photoUri = Uri.fromFile(picFile);

            if (type == PIC_FROM＿LOCALPHOTO) {
                Intent intent = getCropImageIntent();
                startActivityForResult(intent, PIC_FROM＿LOCALPHOTO);
            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(cameraIntent, PIC_FROM_CAMERA);
            }

        } catch (Exception e) {
            Log.i("HandlerPicError", "处理图片出现错误");
        }
    }

    /**
     * 调用图片剪辑程序
     */
    public Intent getCropImageIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        setIntentParams(intent);
        return intent;
    }

    /**
     * 启动裁剪
     */
    private void cropImageUriByTakePhoto() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        setIntentParams(intent);
        startActivityForResult(intent, PIC_FROM＿LOCALPHOTO);
    }

    /**
     * 设置公用参数
     */
    private void setIntentParams(Intent intent) {
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 400);
        intent.putExtra("noFaceDetection", true); // no face detection
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent intent = new Intent();
        switch (requestCode) {
            case PIC_FROM_CAMERA: // 拍照
                try {
                    cropImageUriByTakePhoto();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case PIC_FROM＿LOCALPHOTO:
                try {
                    if (photoUri != null) {
                        Bitmap bitmap = decodeUriAsBitmap(photoUri);
                        mIc_avatar.setImageBitmap(bitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
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
        }
    }

    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    private void loadViewForCode() {
        PullToZoomScrollViewEx scrollView = (PullToZoomScrollViewEx) mInflate.findViewById(R.id.scroll_view);
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.profile_head_view, null, false);
        View zoomView = LayoutInflater.from(getActivity()).inflate(R.layout.profile_zoom_view, null, false);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.profile_content_view, null, false);
        scrollView.setHeaderView(headView);
        scrollView.setZoomView(zoomView);
        scrollView.setScrollContentView(contentView);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.button_zuxiao:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("提醒");
                builder.setMessage("确定要退出吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences sp = getActivity().getSharedPreferences("isLogin", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.clear();
                        edit.commit();
                        checkLogin();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
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
            case R.id.setting:
                intent.setClass(getActivity(), SoftInfo.class);
                startActivity(intent);
                animFromLeftToRight();
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
            case R.id.ic_avatar:
                if (!isLogined) {
                    intent.setClass(getActivity(), SmsLoginActivity.class);
                    startActivityForResult(intent, 5);
                } else {
                        setPopupWindows();
                }
                break;
            case R.id.unlogin:
                if (!isLogined) {
                    intent.setClass(getActivity(), SmsLoginActivity.class);
                    startActivityForResult(intent, 5);
                    animFromLeftToRight();
                } else {

                }
                break;
            case R.id.click_local:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            0);
                }else{
                    doHandlerPhoto(PIC_FROM＿LOCALPHOTO);// 从相册中去获取
                    mPopupWindow.dismiss();
                }
                break;
            case R.id.click_camera:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                            0);
                }else{
                    doHandlerPhoto(PIC_FROM_CAMERA);// 用户点击了从照相机获取
                    mPopupWindow.dismiss();
                }
                break;
            case R.id.cancle:
                mPopupWindow.dismiss();
                break;
        }

    }

    /**
     * 界面跳转动画
     */
    private void animFromLeftToRight() {
        getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.fade_out);
    }

    private void setPopupWindows() {
        View inflate = getLayoutInflater(getArguments()).inflate(R.layout.activity_check_head_img, null);
        inflate.findViewById(R.id.click_local).setOnClickListener(this);
        inflate.findViewById(R.id.click_camera).setOnClickListener(this);
        inflate.findViewById(R.id.cancle).setOnClickListener(this);
        //最后一个参数为true，点击PopupWindow消失,宽必须为match，不然肯呢个会导致布局显示不完全
        mPopupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //设置外部点击无效
        mPopupWindow.setOutsideTouchable(false);
        //设置背景变暗
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        //设置popupwindows动画
        mPopupWindow.setAnimationStyle(R.style.AnimFromButtomToTop);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        BitmapDrawable bitmapDrawable = new BitmapDrawable();
        mPopupWindow.setBackgroundDrawable(bitmapDrawable);
        mPopupWindow.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);
//        animFromLeftToRight();
    }

    /**
     * 图片转成string
     *
     * @param bitmap
     * @return
     */
    public String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);

    }

    /**
     * string转成bitmap
     *
     * @param st
     */
    public Bitmap convertStringToIcon(String st) {
        // OutputStream out;
        Bitmap bitmap = null;
        try {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
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
