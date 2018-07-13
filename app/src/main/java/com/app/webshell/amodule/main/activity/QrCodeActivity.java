/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 下午5:16
 * ********************************************************
 */

package com.app.webshell.amodule.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.app.webshell.R;
import com.app.webshell.amodule.base.ActivityParam;
import com.app.webshell.amodule.base.BaseActivity;

import cn.hugo.android.scanner.view.BaseQrCodeScannerView;

/**
 * 扫描二维码页面
 */
@ActivityParam(isShowToolBar = false)
public class QrCodeActivity extends BaseActivity {

    private BaseQrCodeScannerView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        view = findViewById(R.id.scan_view);
        view.onCreate();
        view.setOnScanResultListener(result -> {
            Intent intent = new Intent();
            intent.putExtra("data", result);
            this.setResult(RESULT_OK, intent);
            mActivity.finish();
            return true;//true则结束页面，false则不结束
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        view.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        view.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return view.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}