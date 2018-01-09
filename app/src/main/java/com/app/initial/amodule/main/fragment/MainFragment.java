/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 下午5:16
 * ********************************************************
 */

package com.app.initial.amodule.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.app.initial.R;
import com.app.initial.amodule.base.BaseFragment;
import com.app.initial.amodule.base.FragmentParam;

@FragmentParam(isShowToolBar = true, isShowReturn = true)
public class MainFragment extends BaseFragment {

    public static MainFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString("title", title);
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void createView(@Nullable Bundle savedInstanceState) {
        super.createView(savedInstanceState);
        setToolbarTitle(getArguments().getString("title"));
    }

    @Override
    protected int getRootViewLayId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void lazyLoad(@Nullable Bundle savedInstanceState) {
        super.lazyLoad(savedInstanceState);
    }
}
