/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-1-9 下午5:16
 * ********************************************************
 */

package com.app.webshell.app;

import com.app.webshell.BuildConfig;
import com.zcolin.frame.app.BaseApp;
import com.zcolin.frame.http.okhttp.OkHttpUtils;
import com.zcolin.frame.http.okhttp.cookie.CookieJarImpl;
import com.zcolin.frame.http.okhttp.cookie.store.MemoryCookieStore;
import com.zcolin.frame.http.okhttp.https.HttpsUtils;
import com.zcolin.frame.util.LogUtil;
import com.zcolin.zx5webview.ZX5WebView;

import java.util.concurrent.TimeUnit;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.util.ShareSocial;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import okhttp3.OkHttpClient;

/**
 * 程序入口
 */
public class App extends BaseApp {


    @Override
    public void onCreate() {
        super.onCreate();
        //if (LeakCanary.isInAnalyzerProcess(this)) {
        //            // This process is dedicated to LeakCanary for heap analysis.
        //            // You should not init your app in this process.
        //            return;
        //        }
        //        LeakCanary.install(this);
        // Normal app init code...

        //facebook调试工具
        //if (BuildConfig.DEBUG) {
        //    Stetho.initializeWithDefaults(this);
        //}
        LogUtil.LOG_DEBUG = BuildConfig.DEBUG;
        initHttpOptions();
        ZX5WebView.init(this);
        ShareSocial.setHiddenPlatForms(SinaWeibo.NAME, TencentWeibo.NAME, WechatFavorite.NAME);
    }

    private void initHttpOptions() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //facebook调试工具
        //        if (BuildConfig.DEBUG) {
        //            builder.addNetworkInterceptor(new StethoInterceptor());
        //        }
        OkHttpClient okHttpClient = builder.connectTimeout(10000L, TimeUnit.MILLISECONDS)
                                           .readTimeout(10000L, TimeUnit.MILLISECONDS)
                                           .cookieJar(cookieJar1)
                                           .hostnameVerifier((hostname, session) -> true)
                                           .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                                           .build();
        OkHttpUtils.initClient(okHttpClient);
    }

}
