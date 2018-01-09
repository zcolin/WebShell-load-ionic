/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 下午5:16
 * ********************************************************
 */

package com.app.initial.amodule.main.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.app.initial.R;
import com.app.initial.amodule.base.ActivityParam;
import com.app.initial.amodule.base.BaseActivity;
import com.app.initial.amodule.main.adapter.MainPagerAdapter;
import com.app.initial.amodule.main.fragment.MainFragment;
import com.zcolin.frame.app.BaseFrameFrag;
import com.zcolin.frame.util.DisplayUtil;
import com.zcolin.gui.ZTabView;
import com.zcolin.gui.ZViewPager;

/**
 * 程序主页面
 */
@ActivityParam(isShowToolBar = false)
public class MainActivity extends BaseActivity {
    public static final int TAB_SIZE = 4;

    private BaseFrameFrag[] arrTabFrag = new BaseFrameFrag[TAB_SIZE];
    private ZViewPager mViewPager;
    private ZTabView   tabView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRes();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initRes() {
        mViewPager = findViewById(R.id.view_pager);
        tabView = findViewById(R.id.view_tabview);
    }

    private void initData() {
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(mainPagerAdapter);
        setUpTab();
    }

    public void setUpTab() {
        tabView.initAsTabIcon(mViewPager);
        tabView.setOnPageChangeListener(new MainPagerListener());

        tabView.addZTab(getNewTab("主页"));
        tabView.addZTab(getNewTab("频道"));
        tabView.addZTab(getNewTab("发现"));
        tabView.addZTab(getNewTab("我"));
    }

    /*
     * 创建ZTab
     */
    private ZTabView.ZTab getNewTab(String str) {
        float textSize = getResources().getDimension(R.dimen.textsize_small);
        ZTabView.ZTab tab = tabView.getNewIconTab(R.drawable.ic_launcher, R.drawable.ic_launcher, str);
        tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        tab.setTextColor(getResources().getColorStateList(R.color.main_tab_textcolor_selector));
        int padding = DisplayUtil.dip2px(this, 10);
        tab.setPadding(padding, padding, padding, padding);
        return tab;
    }

    public BaseFrameFrag getFragByPosition(int pos) {
        if (arrTabFrag[pos] == null) {
            if (pos == 0) {
                arrTabFrag[pos] = MainFragment.newInstance("主页");
            } else if (pos == 1) {
                arrTabFrag[pos] = MainFragment.newInstance("频道");
            } else if (pos == 2) {
                arrTabFrag[pos] = MainFragment.newInstance("发现");
            } else if (pos == 3) {
                arrTabFrag[pos] = MainFragment.newInstance("我");
            }
        }
        return arrTabFrag[pos];
    }

    /*
    * ViewPager监听类 
    */
    private class MainPagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            if (arg0 == 0) {

            } else {

            }
        }
    }
}
