/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 上午8:51
 * ********************************************************
 */
package com.app.webshell.amodule.main.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.app.webshell.R;
import com.app.webshell.amodule.base.ActivityParam;
import com.app.webshell.amodule.base.BaseActivity;
import com.app.webshell.biz.JSInterfaceMgr;
import com.app.webshell.util.PhotoSelectedUtil;
import com.zcolin.frame.util.StringUtil;
import com.zcolin.zwebview.ZWebView;
import com.zhihu.matisse.Matisse;

import java.util.List;


/**
 * 和ionic交互的通用webView
 */
@ActivityParam(isShowToolBar = false)
public class WebViewActivity extends BaseActivity {
    private ZWebView webView;
    private String   url;
    private boolean isInterceptGoBack = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("url");
        isInterceptGoBack = getIntent().getBooleanExtra("isInterceptGoBack", true);
        if (!TextUtils.isEmpty(title)) {
            setContentView(initToolBar(R.layout.common_webview));
            setSupportActionBar(getToolbar());
            setToolbarTitle(title);
            setToolbarLeftBtnCompoundDrawableLeft(R.drawable.gui_icon_arrow_back);
        } else {
            setContentView(R.layout.common_webview);
        }

        initWebView();
        if (!StringUtil.isEmpty(url)) {
            loadUrl();
        }
    }

    protected void onToolBarLeftBtnClick() {
        onBackPressed();
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
        webView.getSettings().setUserAgentString(webView.getSettings().getUserAgentString() + "telchina:android");
        webView.setSupportHorizontalProgressBar();
        webView.setSupportCircleProgressBar();
        webView.setSupportJsBridge();
        webView.setSupportErrorView();
        webView.setSupportChooseFile(mActivity, (s, i) -> {
            if (s != null && s.toLowerCase().startsWith("image/")) {
                PhotoSelectedUtil.selectPhoto(mActivity, 9, (resultCode, data) -> {
                    if (resultCode == RESULT_OK && data != null) {
                        List<Uri> mSelectUri = Matisse.obtainResult(data);
                        if (mSelectUri != null && mSelectUri.size() > 0) {
                            webView.processResult(mSelectUri.toArray(new Uri[mSelectUri.size()]));
                        }
                    } else {
                        webView.processResult((Uri) null);
                    }
                });
            }
        });

        JSInterfaceMgr.registerAll(webView);
    }

    public void loadUrl() {
        webView.loadUrl(url);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        webView.processResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (isInterceptGoBack && webView.isInjectJSBridge()) {
            JSInterfaceMgr.goBack(webView, s -> {
                boolean result = Boolean.valueOf(s);
                if (!result) {
                    finish();
                }
            });
        } else {
            finish();
        }
    }
}
