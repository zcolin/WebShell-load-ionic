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
import com.zcolin.frame.util.ToastUtil;
import com.zcolin.zwebview.ZWebView;


/**
 * 带JsBridge的webview的Demo
 */
@ActivityParam(isShowToolBar = false)
public class WebViewActivity extends BaseActivity {
    private ZWebView webView;
    private String   url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        this.url = getIntent().getStringExtra("url");

        initWebView();
        loadUrl();
    }

    public void onResume() {
        super.onResume();
        webView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.onPause();
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }

    public void initWebView() {
        webView = findViewById(R.id.webView);
        webView.setSupportJsBridge();
        webView.setSupportHorizontalProgressBar();
        webView.setSupportCircleProgressBar();
    }

    public void loadUrl() {
        webView.loadUrl(url);

        webView.registerHandler("callNativeFunction", (data, function) -> {
            ToastUtil.toastShort(data);
            function.onCallBack("Native 返回数据");
        });
    }
}
