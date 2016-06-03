package bamin.com.kepiao.activity;

import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import bamin.com.kepiao.R;

public class YinLianWebActivity extends AppCompatActivity {

    private WebView mWebView_yinlian;
    private String mOrderID;
    private String mPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yin_lian_web);
        getIntentData();
        findID();
        initUI();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mOrderID = intent.getStringExtra("OrderID");
        mPrice = intent.getStringExtra("price");
    }

    private void initUI() {
        WebViewClient client = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url); // 在当前的webview中跳转到新的url
                return true;
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // handler.cancel(); // Android默认的处理方式
                handler.proceed(); // 接受所有网站的证书
                // handleMessage(Message msg); // 进行其他处理
            }
        };
        mWebView_yinlian.setWebViewClient(client);

        WebSettings settings = mWebView_yinlian.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView_yinlian.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void closeActivity(String value) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        YinLianWebActivity.this.finish();
                    }
                });
            }
        }, "javaMethod");
        mWebView_yinlian.loadUrl("http://192.168.1.112:8080/unionpay/frontPay?orderId="+mOrderID+"&txnAmt="+mPrice);
    }

    private void findID() {
        mWebView_yinlian = (WebView) findViewById(R.id.webView_yinlian);
    }
}
