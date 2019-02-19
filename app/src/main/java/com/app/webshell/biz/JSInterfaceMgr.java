/*
 * *********************************************************
 *   author   colin
 *   company  telchina
 *   email    wanglin2046@126.com
 *   date     18-5-3 下午3:03
 * ********************************************************
 */

package com.app.webshell.biz;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.app.webshell.amodule.main.activity.QrCodeActivity;
import com.app.webshell.amodule.main.activity.WebViewActivity;
import com.app.webshell.app.App;
import com.app.webshell.util.PhotoSelectedUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zcolin.frame.app.BaseApp;
import com.zcolin.frame.app.BaseFrameActivity;
import com.zcolin.frame.http.ZHttp;
import com.zcolin.frame.http.response.ZStringResponse;
import com.zcolin.frame.permission.PermissionHelper;
import com.zcolin.frame.permission.PermissionsResultAction;
import com.zcolin.frame.util.AppUtil;
import com.zcolin.frame.util.BitmapUtil;
import com.zcolin.frame.util.DeviceUtil;
import com.zcolin.frame.util.LogUtil;
import com.zcolin.frame.util.NUriParseUtil;
import com.zcolin.frame.util.NetworkUtil;
import com.zcolin.frame.util.SPUtil;
import com.zcolin.frame.util.ToastUtil;
import com.zcolin.gui.ZDialogAsyncProgress;
import com.zcolin.libamaplocation.LocationUtil;
import com.zcolin.zwebview.jsbridge.BridgeWebView;
import com.zcolin.zwebview.jsbridge.CallBackFunction;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.sharesdk.util.ShareSocial;
import okhttp3.Call;
import okhttp3.Response;

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
    private static final String NATIVE_SELECT_FILE  = "native_selectFile";       //调用选择文件
    private static final String NATIVE_HTTP         = "native_http";             //调用http
    private static final String NATIVE_PUT_STORAGE  = "native_put_storage";      //存储本地值
    private static final String NATIVE_GET_STORAGE  = "native_get_storage";      //获取本地值

    /**
     * 使用ionic的pagename和params拼接成url
     */
    public static String getUrl(String pageName, String params) {
        String url = null;
        if (pageName != null) {
            //            url = String.format("file:///android_asset/www/index.html?pageName=%s", pageName);
            url = String.format("http://10.10.38.145:8100?pageName=%s", pageName);

            if (!TextUtils.isEmpty(params)) {
                url += "&params=" + params;
            }
        }
        return url;
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
     * 原生调用js返回函数，回调时result为true表示js拦截了返回，result为false表示js没有拦截返回。
     */
    public static void goBack(BridgeWebView webView, CallBackFunction callBackFunction) {
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
                String params = jsonApply.has("params") ? !jsonApply.get("params").isJsonNull() ? jsonApply.get("params").getAsString() : null : null;
                String title = jsonApply.has("title") ? !jsonApply.get("title").isJsonNull() ? jsonApply.get("title").getAsString() : null : null;
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
                    successCallBack(callBackFunction);
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
                String title = jsonApply.has("title") ? !jsonApply.get("title").isJsonNull() ? jsonApply.get("title").getAsString() : null : null;
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
                    successCallBack(callBackFunction);
                }
            } catch (Exception e) {
                errorCallBack(callBackFunction, 0, "json 解析失败!");
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

                successCallBack(callBackFunction);
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
                String message = jsonApply.has("message") ? !jsonApply.get("message").isJsonNull() ? jsonApply.get("message").getAsString() : "" : "";
                long duration = jsonApply.has("duration") ? !jsonApply.get("duration").isJsonNull() ? jsonApply.get("duration").getAsLong() : 0 : 0;
                if (duration > 2000) {
                    ToastUtil.toastLong(message);
                } else {
                    ToastUtil.toastShort(message);
                }

                successCallBack(callBackFunction);
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
     * 注册js调用分享功能
     * <p>
     * 请求数据格式：｛"title":"xx","content":"xxx","targetUrl":"xxx","imageUrl":"xxx"｝
     * 返回数据:{"code":200,"msg":""}
     */
    public static void registerShare(BridgeWebView webView) {
        webView.registerHandler(NATIVE_SHARE, (data, callBackFunction) -> {
            if (webView.getContext() == null) {
                errorCallBack(callBackFunction, 0, "WebView 运行环境错误!");
                return;
            }

            try {
                JsonObject jsonApply = new JsonParser().parse(data).getAsJsonObject();
                String title = jsonApply.has("title") ? !jsonApply.get("title").isJsonNull() ? jsonApply.get("title").getAsString() : "" : "";
                String content = jsonApply.has("content") ? !jsonApply.get("content").isJsonNull() ? jsonApply.get("content").getAsString() : "" : "";
                String targetUrl = jsonApply.has("targetUrl") ? !jsonApply.get("targetUrl").isJsonNull() ? jsonApply.get("targetUrl").getAsString() : "" : "";
                String imageUrl = jsonApply.has("imageUrl") ? !jsonApply.get("imageUrl").isJsonNull() ? jsonApply.get("imageUrl").getAsString() : "" : "";
                ShareSocial.instance().setTitle(title).setContent(content).setTargetUrl(targetUrl).setImgUrl(imageUrl).share(webView.getContext());

                if (callBackFunction != null) {
                    JsonObject jsonReply = new JsonObject();
                    jsonReply.addProperty("code", 200);
                    callBackFunction.onCallBack(jsonReply.toString());
                }
            } catch (Exception e) {
                errorCallBack(callBackFunction, 0, "json 解析失败!");
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
            if (callBackFunction == null) {
                return;
            }

            if (webView.getContext() == null || !(webView.getContext() instanceof BaseFrameActivity)) {
                errorCallBack(callBackFunction, 0, "WebView 运行环境错误!");
                return;
            }

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
        });
    }

    /**
     * 注册js调用定位功能
     * <p>
     * 返回数据格式：｛"code":200,"msg":"", "latitude":"xxxxxxxxxxxxxxxxxxxxxxxxxxxxx",....｝
     */
    public static void registerLocation(BridgeWebView webView) {
        webView.registerHandler(NATIVE_LOCATION, (data, callBackFunction) -> {
            if (callBackFunction == null) {
                return;
            }

            if (webView.getContext() == null || !(webView.getContext() instanceof BaseFrameActivity)) {
                errorCallBack(callBackFunction, 0, "WebView 运行环境错误!");
                return;
            }

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
        });
    }


    /**
     * 注册js调用选择照片功能
     * <p>
     * 请求数据格式：｛"maxNumber":"9", "minPixel":1024｝
     * 返回数据格式：｛"code":200,"msg":"", "images":"xxxxxxxxxxxxxxxxxxxxxxxxxxxxx"｝
     */
    public static void registerSelectImage(BridgeWebView webView) {
        webView.registerHandler(NATIVE_SELECT_IMAGE, (data, callBackFunction) -> {
            if (callBackFunction == null) {
                return;
            }

            if (webView.getContext() == null || !(webView.getContext() instanceof BaseFrameActivity)) {
                errorCallBack(callBackFunction, 0, "WebView 运行环境错误!");
                return;
            }

            int maxNum = 1;
            int minPixel = 0;
            try {
                JsonObject jsonApply = new JsonParser().parse(data).getAsJsonObject();
                maxNum = jsonApply.has("maxNumber") ? jsonApply.get("maxNumber").getAsInt() > 0 ? jsonApply.get("maxNumber").getAsInt() : 1 : 1;
                minPixel = jsonApply.has("minPixel") ? jsonApply.get("minPixel").getAsInt() : 0;
            } catch (Exception e) {
                errorCallBack(callBackFunction, 0, "json 解析失败!");
            }

            int finalMinPixel = minPixel;
            BaseFrameActivity activity = ((BaseFrameActivity) webView.getContext());
            PhotoSelectedUtil.selectPhoto(activity, maxNum, (resultCode, data1) -> {
                if (resultCode != RESULT_OK || data1 == null) {
                    errorCallBack(callBackFunction, 0, "用户取消选择!");
                    return;
                }

                List<String> mSelect = Matisse.obtainPathResult(data1);
                if (mSelect == null || mSelect.size() == 0) {
                    errorCallBack(callBackFunction, 0, "用户未选择图片!");
                    return;
                }

                ZDialogAsyncProgress.instance(webView.getContext()).setDoInterface(new ZDialogAsyncProgress.DoInterface() {
                    @Override
                    public ZDialogAsyncProgress.ProcessInfo onDoInback() {
                        JsonArray jsonArray = new JsonArray();
                        for (String s : mSelect) {
                            Bitmap bitmap = null;
                            if (finalMinPixel > 0) {
                                bitmap = BitmapUtil.decodeBitmap(s, finalMinPixel, finalMinPixel);
                            } else {
                                bitmap = BitmapUtil.decodeBitmap(s);
                            }

                            JsonObject jsonObj = new JsonObject();
                            jsonObj.addProperty("name", new File(s).getName());
                            jsonObj.addProperty("data", "data:image/png;base64," + BitmapUtil.toBase64(bitmap));
                            jsonObj.addProperty("path", s);
                            jsonArray.add(jsonObj);
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
            });
        });
    }

    /**
     * 注册js调用选择文件功能
     * <p>
     * 请求数据格式：｛"type":"image/jpeg"｝ 默认*\/*
     * 返回数据格式：｛"code":200,"msg":"", "images":"xxxxxxxxxxxxxxxxxxxxxxxxxxxxx"｝
     */
    public static void registerSelectFile(BridgeWebView webView) {
        webView.registerHandler(NATIVE_SELECT_FILE, (data, callBackFunction) -> {
            if (callBackFunction == null) {
                return;
            }

            if (webView.getContext() == null || !(webView.getContext() instanceof BaseFrameActivity)) {
                errorCallBack(callBackFunction, 0, "WebView 运行环境错误!");
                return;
            }

            BaseFrameActivity activity = ((BaseFrameActivity) webView.getContext());
            PermissionHelper.requestPermission(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionsResultAction() {
                @Override
                public void onGranted() {
                    String type = "*/*";
                    try {
                        JsonObject jsonApply = new JsonParser().parse(data).getAsJsonObject();
                        type = jsonApply.has("type") ? jsonApply.get("type").getAsString() : "*/*";
                    } catch (Exception e) {
                        errorCallBack(callBackFunction, 0, "json 解析失败!");
                    }

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.setType(type);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivityWithCallback(intent, (resultCode, data12) -> {
                        if (resultCode != RESULT_OK || data12 == null || data12.getData() == null) {
                            errorCallBack(callBackFunction, 0, "用户取消选择!");
                            return;
                        }

                        Uri uri = data12.getData();
                        File file = NUriParseUtil.getFileFromUri(uri);
                        if (file != null) {
                            JsonObject jsonObj = new JsonObject();
                            jsonObj.addProperty("name", file.getName());
                            jsonObj.addProperty("path", file.getAbsolutePath());
                            JsonObject jsonReply = new JsonObject();
                            jsonReply.addProperty("code", 200);
                            jsonReply.add("file", jsonObj);
                            callBackFunction.onCallBack(jsonReply.toString());
                        } else {
                            errorCallBack(callBackFunction, 0, "文件不存在");
                        }
                    });
                }

                @Override
                public void onDenied(String permission) {
                    errorCallBack(callBackFunction, 0, "用户未授权!");
                }
            });
        });
    }

    /**
     * 注册js调用选择文件功能
     * <p>
     * 请求数据格式：｛"type":"image/jpeg"｝ 默认*\/*
     * 返回数据格式：｛"code":200,"msg":"", "images":"xxxxxxxxxxxxxxxxxxxxxxxxxxxxx"｝
     */
    public static void registerHttp(BridgeWebView webView) {
        webView.registerHandler(NATIVE_HTTP, (data, callBackFunction) -> {
            if (callBackFunction == null) {
                return;
            }

            String method = "post";
            String url = null;
            LinkedHashMap<String, String> headers = null;
            HashMap<String, String> params = null;
            HashMap<String, File> files = null;
            try {
                JsonObject jsonApply = new JsonParser().parse(data).getAsJsonObject();
                if (jsonApply.has("url")) {
                    url = jsonApply.get("url").getAsString();
                } else {
                    errorCallBack(callBackFunction, 0, "url信息错误!");
                }

                if (jsonApply.has("method")) {
                    method = !jsonApply.get("method").isJsonNull() ? jsonApply.get("method").getAsString() : "post";
                }

                if (jsonApply.has("headers")) {
                    JsonObject jsonHeaders = !jsonApply.get("headers").isJsonNull() ? jsonApply.get("headers").getAsJsonObject() : null;
                    if (jsonHeaders != null) {
                        headers = new LinkedHashMap<>();
                        Set<Map.Entry<String, JsonElement>> jsonHeadersSet = jsonHeaders.entrySet();
                        for (Map.Entry<String, JsonElement> stringJsonElementEntry : jsonHeadersSet) {
                            headers.put(stringJsonElementEntry.getKey(), stringJsonElementEntry.getValue().getAsString());
                        }
                    }
                }

                if (jsonApply.has("params")) {
                    JsonObject jsonParams = !jsonApply.get("params").isJsonNull() ? jsonApply.get("params").getAsJsonObject() : null;
                    if (jsonParams != null) {
                        params = new HashMap<>();
                        Set<Map.Entry<String, JsonElement>> jsonHeadersSet = jsonParams.entrySet();
                        for (Map.Entry<String, JsonElement> stringJsonElementEntry : jsonHeadersSet) {
                            params.put(stringJsonElementEntry.getKey(), stringJsonElementEntry.getValue().getAsString());
                        }
                    }
                }

                if (jsonApply.has("files")) {
                    JsonObject jsonFiles = !jsonApply.get("files").isJsonNull() ? jsonApply.get("files").getAsJsonObject() : null;
                    if (jsonFiles != null) {
                        files = new HashMap<>();
                        Set<Map.Entry<String, JsonElement>> jsonHeadersSet = jsonFiles.entrySet();
                        for (Map.Entry<String, JsonElement> stringJsonElementEntry : jsonHeadersSet) {
                            files.put(stringJsonElementEntry.getKey(), new File(stringJsonElementEntry.getValue().getAsString()));
                        }
                    }
                }
            } catch (Exception e) {
                errorCallBack(callBackFunction, 0, "json 解析失败!");
                return;
            }

            if (files != null && files.size() > 0) {
                ZHttp.uploadFileWithHeader(url, headers, params, files, new ZStringResponse() {
                    @Override
                    public void onSuccess(Response response, String resObj) {
                        JsonObject jsonReply = new JsonObject();
                        jsonReply.addProperty("code", 200);
                        jsonReply.addProperty("result", resObj);
                        callBackFunction.onCallBack(jsonReply.toString());
                    }

                    @Override
                    public void onError(int code, Call call, Exception e) {
                        httpErrorCallBack(callBackFunction, code, e);
                    }

                });
            } else if ("get".equals(method)) {
                ZHttp.getWithHeader(url, headers, params, new ZStringResponse() {
                    @Override
                    public void onSuccess(Response response, String resObj) {
                        JsonObject jsonReply = new JsonObject();
                        jsonReply.addProperty("code", 200);
                        jsonReply.addProperty("result", resObj);
                        callBackFunction.onCallBack(jsonReply.toString());
                    }

                    @Override
                    public void onError(int code, Call call, Exception e) {
                        httpErrorCallBack(callBackFunction, code, e);
                    }
                });
            } else {
                ZHttp.postWithHeader(url, headers, params, new ZStringResponse() {
                    @Override
                    public void onSuccess(Response response, String resObj) {
                        JsonObject jsonReply = new JsonObject();
                        jsonReply.addProperty("code", 200);
                        jsonReply.addProperty("result", resObj);
                        callBackFunction.onCallBack(jsonReply.toString());
                    }

                    @Override
                    public void onError(int code, Call call, Exception e) {
                        httpErrorCallBack(callBackFunction, code, e);
                    }
                });
            }
        });
    }

    private static void httpErrorCallBack(CallBackFunction callBackFunction, int code, Exception ex) {
        if (callBackFunction == null) {
            return;
        }
        String str;
        if (ex instanceof SocketTimeoutException || code == 0) {
            if (!NetworkUtil.isNetworkAvailable(BaseApp.APP_CONTEXT)) {
                str = "当前无网络连接，请开启网络！";
            } else {
                str = "连接服务器失败, 请检查网络或稍后重试";
            }
            errorCallBack(callBackFunction, 0, str);
        } else {
            errorCallBack(callBackFunction, code, LogUtil.ExceptionToString(ex));
        }
    }

    /**
     * 存放本地值
     * 请求数据格式：{"key1":"value1","key2":"value2"}
     * 返回数据格式：｛"code":200,"msg":""｝
     */
    public static void registerPutStorage(BridgeWebView webView) {
        webView.registerHandler(NATIVE_PUT_STORAGE, (data, callBackFunction) -> {
            if (webView.getContext() == null || !(webView.getContext() instanceof BaseFrameActivity)) {
                errorCallBack(callBackFunction, 0, "WebView 运行环境错误!");
                return;
            }

            HashMap<String, String> params = new HashMap<>();
            try {
                JsonObject jsonApply = new JsonParser().parse(data).getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> jsonHeadersSet = jsonApply.entrySet();
                for (Map.Entry<String, JsonElement> stringJsonElementEntry : jsonHeadersSet) {
                    params.put(stringJsonElementEntry.getKey(), stringJsonElementEntry.getValue().getAsString());
                }
            } catch (Exception e) {
                errorCallBack(callBackFunction, 0, "json 解析失败!");
                return;
            }

            Set<Map.Entry<String, String>> entrySet = params.entrySet();
            for (Map.Entry<String, String> stringStringEntry : entrySet) {
                SPUtil.putString(stringStringEntry.getKey(), stringStringEntry.getValue());
            }
            successCallBack(callBackFunction);
        });
    }

    /**
     * 获取本地值
     * 请求数据格式：{"key":"xxx"}
     * 返回数据格式：｛"code":200,"msg":""｝
     */
    public static void registerGetStorage(BridgeWebView webView) {
        webView.registerHandler(NATIVE_GET_STORAGE, (data, callBackFunction) -> {
            if (webView.getContext() == null || !(webView.getContext() instanceof BaseFrameActivity)) {
                errorCallBack(callBackFunction, 0, "WebView 运行环境错误!");
                return;
            }

            HashMap<String, String> params = new HashMap<>();
            try {
                JsonObject jsonApply = new JsonParser().parse(data).getAsJsonObject();
                JsonArray jsonArray = jsonApply.get("keys").getAsJsonArray();
                for (JsonElement jsonElement : jsonArray) {
                    params.put(jsonElement.getAsString(), SPUtil.getString(jsonElement.getAsString(), null));
                }
            } catch (Exception e) {
                errorCallBack(callBackFunction, 0, "json 解析失败!");
                return;
            }

            JsonObject jsonReply = new JsonObject();
            jsonReply.addProperty("code", 200);
            JsonObject jsonObject = new JsonObject();
            Set<Map.Entry<String, String>> entries = params.entrySet();
            for (Map.Entry<String, String> stringStringEntry : entries) {
                jsonObject.addProperty(stringStringEntry.getKey(), stringStringEntry.getValue());
            }
            jsonReply.add("result", jsonObject);
            callBackFunction.onCallBack(jsonReply.toString());
        });
    }

    /**
     * 通用错误返回
     */
    private static void errorCallBack(CallBackFunction callBackFunction, int code, String msg) {
        if (callBackFunction != null) {
            JsonObject jsonReply = new JsonObject();
            if (code != 0) {
                jsonReply.addProperty("code", code);
            }
            jsonReply.addProperty("msg", msg);
            callBackFunction.onCallBack(jsonReply.toString());
        }
    }

    /**
     * 通用成功返回
     */
    private static void successCallBack(CallBackFunction callBackFunction) {
        if (callBackFunction != null) {
            JsonObject jsonReply = new JsonObject();
            jsonReply.addProperty("code", 200);
            callBackFunction.onCallBack(jsonReply.toString());
        }
    }
}
