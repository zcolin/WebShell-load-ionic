/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 下午5:16
 * ********************************************************
 */

package com.app.webshell.amodule.init;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.app.webshell.R;
import com.app.webshell.amodule.base.ActivityParam;
import com.app.webshell.amodule.base.BaseActivity;
import com.app.webshell.amodule.main.activity.MainActivity;
import com.zcolin.frame.http.ZHttp;
import com.zcolin.frame.imageloader.ImageLoaderUtils;
import com.zcolin.frame.util.ActivityUtil;


@ActivityParam(isFullScreen = true)
public class InitActivity extends BaseActivity {
    private static final int MIN_REACH_TIME = 2000;//在此页的最少停留时间
    public boolean isExit;//是否已经退出此页面，是否继续加载的标志
    public String  requestTag;//http请求标志

    public boolean isLoadComplete;
    public boolean isReachMinimumTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);

        //加载控件，初始化数据
        ImageView imageView = getView(R.id.imageView);
        ImageLoaderUtils.displayImage(mActivity, "http://img5.duitang.com/uploads/item/201206/06/20120606175216_jLxBw.thumb.700_0.jpeg", imageView);

        //加载业务数据
        new InitAsyncTask().execute(this);

        //加载图要个中国式的最小加载时间
        new Handler().postDelayed(() -> {
            isReachMinimumTime = true;
            initComplete();
        }, MIN_REACH_TIME);
    }

    /*
     * 加载完成回调
     */
    private void initComplete() {
        if (isReachMinimumTime && isLoadComplete && !isExit) {
            ActivityUtil.startActivity(mActivity, MainActivity.class);
            this.finish();
        }
    }

    /*
     * 后台加载异步任务类 
     */
    private class InitAsyncTask extends AsyncTask<Context, Integer, Message> {

        @Override
        protected Message doInBackground(Context... params) {
            Message msg = new Message();
            msg.what = 0;
            return msg;
        }

        @Override
        protected void onPostExecute(Message result) {
            if (result.what == 0 || result.what == 1 && !isExit) {
                isLoadComplete = true;
                initComplete();
            }
        }
    }

    @Override
    public void onBackPressed() {
        isExit = true;
        if (requestTag != null) {
            ZHttp.cancelRequest(requestTag);
        }

        mActivity.finish();
    }
}
