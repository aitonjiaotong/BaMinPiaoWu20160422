package bamin.com.kepiao.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.umeng.analytics.MobclickAgent;

import bamin.com.kepiao.R;
import bamin.com.kepiao.constant.Constant;

/**
 * 优化1.0
 */
public class TicketNotice extends AppCompatActivity implements View.OnClickListener {

    private WebView mWebViewTicketNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_notice);
        initUI();
        setListener();

    }

    private void initUI()
    {
        initWebView();
    }

    private void initWebView()
    {
        mWebViewTicketNotice = (WebView) findViewById(R.id.webview_ticket_notice);
        WebSettings settings = mWebViewTicketNotice.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDefaultTextEncodingName("utf-8");//设置默认为utf-8
        mWebViewTicketNotice.setWebViewClient(new WebViewClient());
        mWebViewTicketNotice.loadUrl(Constant.Url.TICKET_NOTICE);
    }

    private void setListener() {
        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
        if(keyCode== KeyEvent.KEYCODE_BACK){
            finish();
            AnimFromRightToLeft();
        }
        return super.onKeyDown(keyCode, event);
    };
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
