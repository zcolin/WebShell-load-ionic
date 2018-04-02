/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 上午8:51
 * ********************************************************
 */
package com.app.initial.amodule.main.activity;

import android.os.Bundle;

import com.app.initial.R;
import com.app.initial.amodule.base.ActivityParam;
import com.app.initial.amodule.base.BaseActivity;
import com.zcolin.zx5webview.ZX5WebView;


/**
 * 带JsBridge的webview的Demo
 */
@ActivityParam(isShowToolBar = false)
public class X5WebViewActivity extends BaseActivity {
    private ZX5WebView webView;
    private String     url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x5webview);
        this.url = getIntent().getStringExtra("url");

        initWebView();
        loadUrl();
    }

    public void initWebView() {
        webView = findViewById(R.id.webView);
        webView.setSupportJsBridge();
        webView.setSupportHorizontalProgressBar();
        webView.setSupportCircleProgressBar();
    }

    public void loadUrl() {
        webView.loadUrl(url);
    }
}
