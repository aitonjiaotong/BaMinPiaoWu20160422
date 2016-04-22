package bamin.com.kepiao.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.umeng.analytics.MobclickAgent;

import bamin.com.kepiao.R;
import bamin.com.kepiao.constant.ConstantTicket;

public class SoftInfo extends AppCompatActivity implements View.OnClickListener {

    private WebView mWebViewTicketNotice;
    private LinearLayout mLl_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_info);
        initUI();
        setListener();
    }

    private void initUI() {
        initLoading();
        initWebView();
    }

    private void initLoading() {
        mLl_loading = (LinearLayout) findViewById(R.id.ll_loading);
    }

    private void initWebView() {
        mWebViewTicketNotice = (WebView) findViewById(R.id.webview_soft_info);
        WebSettings settings = mWebViewTicketNotice.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebViewTicketNotice.setWebViewClient(new MyWebViewClient());
        mWebViewTicketNotice.loadUrl(ConstantTicket.URL.SOFTWARE_INFO);
    }

    private void setListener() {
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                AnimFromRightToLeft();
                break;
        }
    }

    private void AnimFromRightToLeft() {
        overridePendingTransition(R.anim.fade_in, R.anim.push_left_out);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            AnimFromRightToLeft();
        }
        return super.onKeyDown(keyCode, event);
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mLl_loading.setVisibility(View.GONE);
            mWebViewTicketNotice.setVisibility(View.VISIBLE);
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        mWebViewTicketNotice.destroy();
    }
}
