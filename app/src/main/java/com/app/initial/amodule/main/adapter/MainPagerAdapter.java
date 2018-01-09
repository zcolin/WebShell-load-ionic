/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 下午5:16
 * ********************************************************
 */

/*    
 * 
 * @author		: WangLin  
 * @Company: 	：FCBN
 * @date		: 2015年5月13日 
 * @version 	: V1.0
 */
package com.app.initial.amodule.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.app.initial.amodule.main.activity.MainActivity;


/**
 * 主页面 ViewPager适配器
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
    private MainActivity mainActivity;

    public MainPagerAdapter(FragmentManager fm, MainActivity mainActivity) {
        super(fm);
        this.mainActivity = mainActivity;
    }

    @Override
    public int getCount() {
        return MainActivity.TAB_SIZE;
    }

    @Override
    public Fragment getItem(int arg0) {
        return mainActivity.getFragByPosition(arg0);
    }
}
