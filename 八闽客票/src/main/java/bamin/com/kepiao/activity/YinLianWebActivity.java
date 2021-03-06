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
import bamin.com.kepiao.constant.Constant;

public class YinLianWebActivity extends AppCompatActivity {

    private WebView mWebView_yinlian;
    private String mOrderID;
    private String mPrice;
    private String mRedenvelope_id;
    private String mBookLogAID;

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
        mOrderID = intent.getStringExtra(Constant.IntentKey.PAY_ORDERID);
        mPrice = intent.getStringExtra(Constant.IntentKey.PAY_PRICE);
        mRedenvelope_id= intent.getStringExtra(Constant.IntentKey.PAY_REDENVELOPE_ID);
        mBookLogAID = intent.getStringExtra("BookLogAID");
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
            public void closeActivity(final String value) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(YinLianWebActivity.this, "" + value, Toast.LENGTH_SHORT).show();
                        //1表示失败  0 表示成功
                        if ("1".equals(value)) {
                            startToMainActivity();
                        } else if ("0".equals(value)) {
                            Intent intent = new Intent();
                            intent.putExtra("BookLogAID", mBookLogAID);
                            intent.setClass(YinLianWebActivity.this, OrderDeatilActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        }, "javaMethod");
//        mWebView_yinlian.loadUrl(Constant.HOST_TICKET+"/unionpay/frontPay?BMorderId="+mOrderID+"&txnAmt="+mPrice+"&redEnvelope_id="+mRedenvelope_id);
        mWebView_yinlian.loadUrl(Constant.HOST_TICKET+"/unionpay/frontPay?BMorderId="+mOrderID+"&txnAmt="+mPrice+"&redEnvelope_id="+mRedenvelope_id+"&model=android");
    }
    /**
     * 跳转主页面
     */
    private void startToMainActivity()
    {
        Intent intent = new Intent();
        intent.putExtra("OrderDeatilActivity", "OrderDeatilActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setClass(YinLianWebActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void findID() {
        mWebView_yinlian = (WebView) findViewById(R.id.webView_yinlian);
    }

    @Override
    public void onBackPressed() {
        startToMainActivity();
    }
}
