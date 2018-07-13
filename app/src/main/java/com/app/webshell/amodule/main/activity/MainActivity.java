/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 下午5:16
 * ********************************************************
 */

package com.app.webshell.amodule.main.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.webshell.R;
import com.app.webshell.amodule.base.ActivityParam;
import com.app.webshell.amodule.base.BaseActivity;
import com.zcolin.frame.util.ActivityUtil;

import java.util.ArrayList;

/**
 * 程序主页面
 */
@ActivityParam(isShowToolBar = false)
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList<Button> listButton = new ArrayList<>();


    private ArrayList<Button> listButtonBottom = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        LinearLayout llContent = findViewById(R.id.ll_content);
        LinearLayout llContentBottom = findViewById(R.id.ll_content_bottom);
        listButton.add(addButton("Home", llContent));
        listButton.add(addButton("Contact", llContent));
        listButton.add(addButton("JSBridge", llContent));
        listButton.add(addButton("ECharts", llContent));
        listButton.add(addButton("Arcgis", llContent));
        listButton.add(addButton("TABS-URL", llContent));

        listButtonBottom.add(addButton("X5-Home", llContentBottom));
        listButtonBottom.add(addButton("X5-Contact", llContentBottom));
        listButtonBottom.add(addButton("X5-About", llContentBottom));
        listButtonBottom.add(addButton("X5-ECharts", llContentBottom));
        listButtonBottom.add(addButton("X5-Arcgis", llContentBottom));

        for (Button btn : listButton) {
            btn.setOnClickListener(this);
        }

        for (Button btn : listButtonBottom) {
            btn.setOnClickListener(this);
        }
    }

    private Button addButton(String text, LinearLayout llContent) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button button = new Button(mActivity);
        button.setText(text);
        button.setGravity(Gravity.CENTER);
        button.setAllCaps(false);
        llContent.addView(button, params);
        return button;
    }

    @Override
    public void onClick(View v) {
        if (v == listButton.get(0)) {
            ActivityUtil.startActivity(mActivity, WebViewActivity.class, "url", "file:///android_asset/www/index.html?type=home");
        } else if (v == listButton.get(1)) {
            ActivityUtil.startActivity(mActivity, WebViewActivity.class, "url", "file:///android_asset/www/index.html?type=contact");
        } else if (v == listButton.get(2)) {
            ActivityUtil.startActivity(mActivity, WebViewActivity.class, "url", "file:///android_asset/www/index.html?type=jsbridge");
        } else if (v == listButton.get(3)) {
            ActivityUtil.startActivity(mActivity, WebViewActivity.class, "url", "file:///android_asset/www/index.html?type=echarts");
        } else if (v == listButton.get(4)) {
            ActivityUtil.startActivity(mActivity, WebViewActivity.class, "url", "file:///android_asset/www/index.html?type=arcgis");
        } else if (v == listButton.get((5))) {
            ActivityUtil.startActivity(mActivity, WebViewActivity.class, "url", "http://10.10.38.145:8100");
        } 
        
        
        
        else if (v == listButtonBottom.get(0)) {
            ActivityUtil.startActivity(mActivity, X5WebViewActivity.class, "url", "file:///android_asset/www/index.html?type=home");
        } else if (v == listButtonBottom.get(1)) {
            ActivityUtil.startActivity(mActivity, X5WebViewActivity.class, "url", "file:///android_asset/www/index.html?type=contact");
        } else if (v == listButtonBottom.get(2)) {
            ActivityUtil.startActivity(mActivity, X5WebViewActivity.class, "url", "file:///android_asset/www/index.html?type=jsbridge");
        } else if (v == listButtonBottom.get(3)) {
            ActivityUtil.startActivity(mActivity, X5WebViewActivity.class, "url", "file:///android_asset/www/index.html?type=echarts");
        } else if (v == listButtonBottom.get(4)) {
            ActivityUtil.startActivity(mActivity, X5WebViewActivity.class, "url", "http://m.amap.com");
        }
    }
}
