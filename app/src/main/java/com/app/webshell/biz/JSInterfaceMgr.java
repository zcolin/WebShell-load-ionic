/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-5-3 下午3:03
 * ********************************************************
 */

package com.app.webshell.biz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Base64;

import com.amap.api.location.AMapLocation;
import com.app.webshell.amodule.main.activity.QrCodeActivity;
import com.app.webshell.amodule.main.activity.WebViewActivity;
import com.app.webshell.app.App;
import com.app.webshell.http.HttpUrl;
import com.app.webshell.util.PhotoSelectedUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zcolin.frame.app.BaseFrameActivity;
import com.zcolin.frame.permission.PermissionHelper;
import com.zcolin.frame.permission.PermissionsResultAction;
import com.zcolin.frame.util.AppUtil;
import com.zcolin.frame.util.BitmapUtil;
import com.zcolin.frame.util.DeviceUtil;
import com.zcolin.frame.util.ToastUtil;
import com.zcolin.gui.ZDialogAsyncProgress;
import com.zcolin.libamaplocation.LocationUtil;
import com.zcolin.zwebview.jsbridge.BridgeWebView;
import com.zcolin.zwebview.jsbridge.CallBackFunction;
import com.zhihu.matisse.Matisse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import cn.sharesdk.util.ShareSocial;

import static android.app.Activity.RESULT_OK;

/**
 * JS调用统一管理类
 */
public class JSInterfaceMgr {
    private static final String JS_GOBACK = "js_goBack";                        //调用js返回上一个页面

    private static final String NATIVE_STARTPAGE    = "native_startPage";      //调用原生启动新的ionic页面
    private static final String NATIVE_STARTWEBPAGE = "native_startWebPage";   //调用原生启动的url页面
    private static final String NATIVE_RELOGIN      = "native_reLogin";        //调用原生重新登录
    private static final String NATIVE_FINISHPAGE   = "native_finishPage";     //调用原生关闭当前页面
    private static final String NATIVE_TOAST        = "native_toast";          //调用原生toast

    private static final String NATIVE_APPVERSION   = "native_appVersion";       //调用原生获取当前版本号
    private static final String NATIVE_UUID         = "native_uuid";             //调用原生获取原生UUID
    private static final String NATIVE_SHARE        = "native_share";            //调用原生分享
    private static final String NATIVE_SCAN_QRCODE  = "native_scanQrCode";       //调用原生扫描二维码
    private static final String NATIVE_LOCATION     = "native_location";         //调用原生定位
    private static final String NATIVE_SELECT_IMAGE = "native_selectImage";      //调用原生选择图片


    /**
     * 使用ionic的pagename和params拼接成url
     */
    public static String getUrl(String pageName, String params) {
        String url = null;
        if (pageName != null) {
            url = String.format("file:///android_asset/www/index.html?pageName=%s&token=%s&baseUrl=%s", pageName, UserMgr.getTokenFromFile(), HttpUrl.BASE_URL);
            if (!TextUtils.isEmpty(params)) {
                url += "&params=" + params;
            }
        }
        return url;
    }

    /**
     * 原生调用js返回函数，回调时result为true表示js拦截了返回，result为false表示js没有拦截返回。
     */
    public static void goBack(BridgeWebView webView, CallBackFunction callBackFunction) {
        if (webView != null) {
            webView.callHandler(JS_GOBACK, "", s -> {
                try {
                    JsonObject jsonApply = new JsonParser().parse(s).getAsJsonObject();
                    boolean result = jsonApply.get("result").getAsBoolean();
                    if (callBackFunction != null) {
                        callBackFunction.onCallBack(String.valueOf(result));
                    }
                } catch (Exception e) {
                    errorCallBack(callBackFunction, 0, "json 解析失败!");
                }
            });
        }
    }

    /**
     * 注册所有桥接函数
     */
    public static void registerAll(BridgeWebView webView) {
        Class cls = JSInterfaceMgr.class;
        Method[] methods = cls.getDeclaredMethods();
        if (methods != null && methods.length > 0) {
            for (Method method : methods) {
                if (method.getName() != null && method.getName().startsWith("register") && !method.getName().equals("registerAll")) {
                    try {
                        method.invoke(null, webView);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 注册js调用原生启动新页面（ionic pageName页面）功能
     * 请求数据格式｛"pageName":"HomePage", "params":"{'xxx':'xxx'}","title":"我是title"｝, 其中params是ionic传递个下一个页面的参数, title为标题文字
     */
    public static void registerStartPage(BridgeWebView webView) {
        webView.registerHandler(NATIVE_STARTPAGE, (data, callBackFunction) -> {
            Context context = webView.getContext();
            try {
                JsonObject jsonApply = new JsonParser().parse(data).getAsJsonObject();
                String pageName = jsonApply.get("pageName").getAsString();
                String params = jsonApply.has("params") ? jsonApply.get("params").getAsString() : null;
                String title = jsonApply.has("title") ? jsonApply.get("title").getAsString() : null;
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", JSInterfaceMgr.getUrl(pageName, params));
                intent.putExtra("title", title);
                if (context instanceof BaseFrameActivity) {
                    ((BaseFrameActivity) context).startActivityWithCallback(intent, (resultCode, data1) -> {
                        if (callBackFunction != null && resultCode == RESULT_OK) {
                            JsonObject jsonReply = new JsonObject();
                            jsonReply.addProperty("code", 200);
                            jsonReply.addProperty("params", data1 == null ? null : data1.getStringExtra("params"));
                            callBackFunction.onCallBack(jsonReply.toString());
                        }
                    });
                } else {
                    context.startActivity(intent);
                    if (callBackFunction != null) {
                        successCallBack(callBackFunction);
                    }
                }
            } catch (Exception e) {
                errorCallBack(callBackFunction, 0, "json 解析失败!");
            }
        });
    }

    /**
     * 注册js调用原生启动新页面（url连接页面）功能
     * 请求数据格式｛"url":"http://xxxx", "title":"我是title"｝
     */
    public static void registerStartWebPage(BridgeWebView webView) {
        webView.registerHandler(NATIVE_STARTWEBPAGE, (data, callBackFunction) -> {
            Context context = webView.getContext();
            try {
                JsonObject jsonApply = new JsonParser().parse(data).getAsJsonObject();
                String url = jsonApply.get("url").getAsString();
                String title = jsonApply.has("title") ? jsonApply.get("title").getAsString() : null;
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                intent.putExtra("isInterceptGoBack", false);
                if (context instanceof BaseFrameActivity) {
                    ((BaseFrameActivity) context).startActivityWithCallback(intent, (resultCode, data1) -> {
                        if (callBackFunction != null && resultCode == RESULT_OK) {
                            JsonObject jsonReply = new JsonObject();
                            jsonReply.addProperty("code", 200);
                            jsonReply.addProperty("params", data1 == null ? null : data1.getStringExtra("params"));
                            callBackFunction.onCallBack(jsonReply.toString());
                        }
                    });
                } else {
                    context.startActivity(intent);
                    if (callBackFunction != null) {
                        successCallBack(callBackFunction);
                    }
                }
            } catch (Exception e) {
                errorCallBack(callBackFunction, 0, "json 解析失败!");
            }
        });
    }

    /**
     * 注册js调用原生重新登录功能
     */
    public static void registerReLogin(BridgeWebView webView) {
        webView.registerHandler(NATIVE_RELOGIN, (data, callBackFunction) -> {
            Context context = webView.getContext();
            UserMgr.logout();
            //            AppUtil.quitSystem();
            //            Intent intent = new Intent();
            //            intent.setClass(context, LoginActivity.class);
            //            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //            context.startActivity(intent);
            if (callBackFunction != null) {
                successCallBack(callBackFunction);
            }
        });
    }

    /**
     * 注册JS调用关闭原生页面（关闭WebView）功能
     */
    public static void registerFinishPage(BridgeWebView webView) {
        webView.registerHandler(NATIVE_FINISHPAGE, (data, callBackFunction) -> {
            try {
                if (webView.getContext() instanceof Activity) {
                    ((Activity) webView.getContext()).finish();
                }

                if (callBackFunction != null) {
                    successCallBack(callBackFunction);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 注册js调用原生toast功能
     * 请求数据格式｛"message":"我是message", "duration":2000｝其中duration为可选参数
     */
    public static void registerToast(BridgeWebView webView) {
        webView.registerHandler(NATIVE_TOAST, (data, callBackFunction) -> {
            try {
                JsonObject jsonApply = new JsonParser().parse(data).getAsJsonObject();
                String message = jsonApply.has("message") ? jsonApply.get("message").getAsString() : "";
                long duration = jsonApply.has("duration") ? jsonApply.get("duration").getAsLong() : 0;
                if (duration > 2000) {
                    ToastUtil.toastLong(message);
                } else {
                    ToastUtil.toastShort(message);
                }

                if (callBackFunction != null) {
                    successCallBack(callBackFunction);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 注册js调用获取原生app版本号功能
     * <p>
     * 返回数据格式：｛"versionName":"1.5.3", "versionCode":2000｝
     */
    public static void registerGetAppVersion(BridgeWebView webView) {
        webView.registerHandler(NATIVE_APPVERSION, (data, callBackFunction) -> {
            if (callBackFunction != null) {
                JsonObject jsonReply = new JsonObject();
                String versionName = AppUtil.getVersionName(App.APP_CONTEXT);
                int versionCode = AppUtil.getVersionCode(App.APP_CONTEXT);
                jsonReply.addProperty("code", 200);
                jsonReply.addProperty("versionName", versionName);
                jsonReply.addProperty("versionCode", versionCode);
                callBackFunction.onCallBack(jsonReply.toString());
            }
        });
    }

    /**
     * 注册js调用获取原生uuid功能
     * <p>
     * 返回数据格式：｛"uuid":"xxxxxxxxxxxxxxxxxxxxxxxxxxxxx"｝
     */
    public static void registerGetUUID(BridgeWebView webView) {
        webView.registerHandler(NATIVE_UUID, (data, callBackFunction) -> {
            if (callBackFunction != null) {
                JsonObject jsonReply = new JsonObject();
                String uuid = DeviceUtil.getUUID();
                jsonReply.addProperty("code", 200);
                jsonReply.addProperty("uuid", uuid);
                callBackFunction.onCallBack(jsonReply.toString());
            }
        });
    }

    /**
     * 注册js调用定位功能
     * <p>
     * 返回数据格式：｛"code":200,"msg":"", "latitude":"xxxxxxxxxxxxxxxxxxxxxxxxxxxxx",....｝
     */
    public static void registerLocation(BridgeWebView webView) {
        webView.registerHandler(NATIVE_LOCATION, (data, callBackFunction) -> {
            if (callBackFunction != null) {
                if (webView.getContext() != null && webView.getContext() instanceof BaseFrameActivity) {
                    BaseFrameActivity activity = ((BaseFrameActivity) webView.getContext());
                    PermissionHelper.requestLocationPermission(activity, new PermissionsResultAction() {
                        @Override
                        public void onGranted() {
                            LocationUtil locationUtil = new LocationUtil(App.APP_CONTEXT);
                            locationUtil.startLocation(new LocationUtil.OnGetLocation() {
                                @Override
                                public void getLocation(AMapLocation aMapLocation) {
                                    JsonObject jsonReply = new JsonObject();
                                    jsonReply.addProperty("code", 200);
                                    jsonReply.addProperty("latitude", aMapLocation.getLatitude());
                                    jsonReply.addProperty("longitude", aMapLocation.getLongitude());
                                    jsonReply.addProperty("province", aMapLocation.getProvince());
                                    jsonReply.addProperty("city", aMapLocation.getCity());
                                    jsonReply.addProperty("cityCode", aMapLocation.getCityCode());
                                    jsonReply.addProperty("district", aMapLocation.getDistrict());
                                    jsonReply.addProperty("adCode", aMapLocation.getAdCode());
                                    jsonReply.addProperty("address", aMapLocation.getAddress());
                                    jsonReply.addProperty("road", aMapLocation.getRoad());
                                    jsonReply.addProperty("street", aMapLocation.getStreet());
                                    jsonReply.addProperty("streetNum", aMapLocation.getStreetNum());
                                    jsonReply.addProperty("country", aMapLocation.getCountry());
                                    callBackFunction.onCallBack(jsonReply.toString());
                                }

                                @Override
                                public void locationFail() {
                                    errorCallBack(callBackFunction, 0, "定位失败！");
                                }
                            });
                        }

                        @Override
                        public void onDenied(String permission) {
                            errorCallBack(callBackFunction, 0, "用户未授予定位权限！");
                        }
                    });
                } else {
                    errorCallBack(callBackFunction, 0, "WebView 运行环境错误!");
                }
            }
        });
    }

    /**
     * 注册js调用扫描二维码功能
     * <p>
     * 返回数据格式：｛"code":200,"msg":"", "result":"xxxxxxxxxxxxxxxxxxxxxxxxxxxxx"｝
     */
    public static void registerScanQrCode(BridgeWebView webView) {
        webView.registerHandler(NATIVE_SCAN_QRCODE, (data, callBackFunction) -> {
            if (callBackFunction != null) {
                if (webView.getContext() != null && webView.getContext() instanceof BaseFrameActivity) {
                    BaseFrameActivity activity = ((BaseFrameActivity) webView.getContext());
                    PermissionHelper.requestCameraPermission(activity, new PermissionsResultAction() {
                        @Override
                        public void onGranted() {
                            Intent intent = new Intent(activity, QrCodeActivity.class);
                            activity.startActivityWithCallback(intent, (resultCode, data1) -> {
                                if (resultCode == RESULT_OK) {
                                    JsonObject jsonReply = new JsonObject();
                                    jsonReply.addProperty("code", 200);
                                    jsonReply.addProperty("result", data1.getStringExtra("data"));
                                    callBackFunction.onCallBack(jsonReply.toString());
                                } else {
                                    errorCallBack(callBackFunction, 0, "未扫描到二维码！");
                                }
                            });
                        }

                        @Override
                        public void onDenied(String permission) {
                            errorCallBack(callBackFunction, 0, "用户未授予摄像头权限！");
                        }
                    });
                } else {
                    errorCallBack(callBackFunction, 0, "WebView 运行环境错误!");
                }
            }
        });
    }

    /**
     * 注册js调用选择照片功能
     * <p>
     * 请求数据格式：｛"maxNum":"9", "minPixel":1024｝
     * 返回数据格式：｛"code":200,"msg":"", "images":"xxxxxxxxxxxxxxxxxxxxxxxxxxxxx"｝
     */
    public static void registerSelectImgage(BridgeWebView webView) {
        webView.registerHandler(NATIVE_SELECT_IMAGE, (data, callBackFunction) -> {
            if (callBackFunction != null) {
                if (webView.getContext() != null && webView.getContext() instanceof BaseFrameActivity) {
                    BaseFrameActivity activity = ((BaseFrameActivity) webView.getContext());
                    try {
                        JsonObject jsonApply = new JsonParser().parse(data).getAsJsonObject();
                        int maxNum = jsonApply.has("mxaNumber") ? jsonApply.get("mxaNumber").getAsInt() > 0 ? jsonApply.get("mxaNumber").getAsInt() : 1 : 1;
                        int minPixel = jsonApply.has("minPixel") ? jsonApply.get("minPixel").getAsInt() : 0;
                        PhotoSelectedUtil.selectPhoto(activity, maxNum, (resultCode, data1) -> {
                            if (resultCode == RESULT_OK && data != null) {
                                List<String> mSelect = Matisse.obtainPathResult(data1);
                                if (mSelect != null && mSelect.size() > 0) {
                                    ZDialogAsyncProgress.instance(webView.getContext()).setDoInterface(new ZDialogAsyncProgress.DoInterface() {
                                        @Override
                                        public ZDialogAsyncProgress.ProcessInfo onDoInback() {
                                            JsonArray jsonArray = new JsonArray();
                                            for (String s : mSelect) {
                                                if (minPixel > 0) {
                                                    Bitmap bitmap = BitmapUtil.decodeBitmap(s, minPixel, minPixel);
                                                    byte[] bytes = BitmapUtil.bitmapToByte(bitmap);
                                                    byte[] encode = Base64.encode(bytes, Base64.NO_WRAP);
                                                    jsonArray.add("data:image/png;base64," + new String(encode));
                                                } else {
                                                    Bitmap bitmap = BitmapUtil.decodeBitmap(s);
                                                    byte[] bytes = BitmapUtil.bitmapToByte(bitmap);
                                                    byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
                                                    jsonArray.add("data:image/png;base64," + new String(encode));
                                                }
                                            }

                                            JsonObject jsonReply = new JsonObject();
                                            jsonReply.addProperty("code", 200);
                                            jsonReply.add("images", jsonArray);

                                            ZDialogAsyncProgress.ProcessInfo info = new ZDialogAsyncProgress.ProcessInfo();
                                            info.msg = jsonReply.toString();
                                            return info;
                                        }

                                        @Override
                                        public void onPostExecute(ZDialogAsyncProgress.ProcessInfo info) {
                                            callBackFunction.onCallBack(info.msg);
                                        }
                                    }).execute(0);
                                } else {
                                    errorCallBack(callBackFunction, 0, "用户未选择图片!");
                                }
                            } else {
                                errorCallBack(callBackFunction, 0, "用户未选择图片!");
                            }
                        });
                    } catch (Exception e) {
                        errorCallBack(callBackFunction, 0, "json 解析失败!");
                    }
                } else {
                    errorCallBack(callBackFunction, 0, "WebView 运行环境错误!");
                }
            }
        });
    }

    /**
     * 注册js调用分享功能
     * <p>
     * 请求数据格式：｛"title":"xx","content":"xxx","targetUrl":"xxx","imageUrl":"xxx"｝
     * 返回数据:{"code":200,"msg":""}
     */
    public static void registerShare(BridgeWebView webView) {
        webView.registerHandler(NATIVE_SHARE, (data, callBackFunction) -> {
            if (callBackFunction != null) {
                if (webView.getContext() != null && webView.getContext() instanceof BaseFrameActivity) {
                    BaseFrameActivity activity = ((BaseFrameActivity) webView.getContext());
                    try {
                        JsonObject jsonApply = new JsonParser().parse(data).getAsJsonObject();
                        String title = jsonApply.has("title") ? jsonApply.get("title").getAsString() : "";
                        String content = jsonApply.has("content") ? jsonApply.get("content").getAsString() : "";
                        String targetUrl = jsonApply.has("targetUrl") ? jsonApply.get("targetUrl").getAsString() : "";
                        String imageUrl = jsonApply.has("imageUrl") ? jsonApply.get("imageUrl").getAsString() : "";
                        ShareSocial.instance().setTitle(title).setContent(content).setTargetUrl(targetUrl).setImgUrl(imageUrl).share(activity);

                        JsonObject jsonReply = new JsonObject();
                        jsonReply.addProperty("code", 200);
                        callBackFunction.onCallBack(jsonReply.toString());
                    } catch (Exception e) {
                        errorCallBack(callBackFunction, 0, "json 解析失败!");
                    }
                } else {
                    errorCallBack(callBackFunction, 0, "WebView 运行环境错误!");
                }
            }
        });
    }

    private static void errorCallBack(CallBackFunction callBackFunction, int code, String msg) {
        JsonObject jsonReply = new JsonObject();
        if (code != 0) {
            jsonReply.addProperty("code", code);
        }
        jsonReply.addProperty("msg", msg);
        callBackFunction.onCallBack(jsonReply.toString());
    }

    private static void successCallBack(CallBackFunction callBackFunction) {
        JsonObject jsonReply = new JsonObject();
        jsonReply.addProperty("code", 200);
        callBackFunction.onCallBack(jsonReply.toString());
    }
}
