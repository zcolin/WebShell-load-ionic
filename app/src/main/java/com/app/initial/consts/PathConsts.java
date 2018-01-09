/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 下午5:16
 * ********************************************************
 */

package com.app.initial.consts;

import com.app.initial.R;
import com.app.initial.app.App;
import com.zcolin.frame.app.FramePathConst;

/**
 * 地址常量类
 */
public class PathConsts extends FramePathConst {

    private static class InstancesClass {
        private static final PathConsts instance = new PathConsts();
    }

    public static PathConsts getInstance() {
        return PathConsts.InstancesClass.instance;
    }

    public String getPathSdcardApp() {
        return getPathSdcard() + "/" + App.APP_CONTEXT.getString(R.string.app_name) + "/";
    }

}
