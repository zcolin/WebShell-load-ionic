# 项目初始框架

本框架为项目初始搭建框架，功能如下
==
* 常用库的引入。
* App和Activity的初始化编写。
* 项目分包及结构划分。
* 常用功能如首页Tab页、登录注册、设置页面、提示框初始化等编写。
* 常用资源文件如colors、dimens、styles、attrs、text_styles等编写。

集成使用步骤
==
1. 将包名和app->build.gradle文件中的applicationId 改为需要的名字，applicationId和包名相同即可。
1. 替换签名文件zcolin_keystore，然后将签名配置文件key.properties中的密码修改。
1. 修改com.app.initial.http.entity.HttpBaseReplyBean中的字段为协议内容中的字段。
1. 修改res->values->colors中的主题颜色为需要的主题颜色。
1. 修改初始化、主页等页面为需要的样式。
1. Activity设置
    * 是否需要沉浸式的  {@link ActivityParam()} isImmerse  default true}
    * 是否需要带ToolBar的  {@link ActivityParam()} isShowToolBar  default true}
    * 是否需要ToolBar带返回按钮并且实现了返回的  {@link ActivityParam()} isShowReturn  default true}
    * 沉浸模式是否需要空出顶部状态栏距离{@link FragmentParam() isImmersePaddingTop default false}
    * 是否需要全屏的  {@link ActivityParam()} isFullScreen  default true}
1. Fragment设置
     * 是否需要带ToolBar的  {@link FragmentParam()} isShowToolBar  default true}
     * 是否需要ToolBar带返回按钮并且实现了返回的  {@link FragmentParam()} isShowReturn  default true}
     * 是否需要空出顶部状态栏距离{@link FragmentParam() isImmersePaddingTop default false}

分包功能介绍
==
* amodule为模块包，即具体的业务功能模块包。base为通用基类包，如Activity、Fragment、Adapter等的基类. 其下的包按功能模块划分。
模块下再分为Activity、Fragment、Adapter等包。如init-初始化过渡页模块、mian-主页模块、register-注册登录模块等。
* app为放置Application子类的包。
* biz为全局业务处理包，如UserMgr-登录用户管理, UpdateMgr-程序更新管理等。
* consts为全局静态变量包，如PathConst-路径常量。
* db为数据库相关操作包，db.entity为数据库实体包。
* http为http相关操作包，http.entity为http实体包。
* utils为全局工具类包。
* views为自定义控件包。

USAGE
==

###Frame核心框架使用示例
Activity使用
```
//BaseActivity，本程序使用的Activity的基类，继承于BaseFrameActivity, 默认带有toolbar和返回按钮

//没有ToolBar的Activity
@ActivityParam(isShowToolBar = false)
public class PassDataActivity extends BaseActivity {
    ...
}

//有ToolBar不带返回按钮的的Activity 
@ActivityParam(isShowToolBar = false, isShowReturn = false)
public class PassDataActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passdata);

        setToolbarTitle("标题");
    }
    
  @Override
    protected void onToolBarLeftBtnClick() {
        //左侧按钮点击事件
    }
}

//全屏Activity
@ActivityParam(isFullScreen = true)
public class PassDataActivity extends BaseActivity {
    ...
}

//沉浸式Activity
@ActivityParam(isImmerse = true)
public class PassDataActivity extends BaseActivity {
    ...
}

StartActivityForResult使用,不需要再在Activity中重写onActivityResult函数
```
startActivityWithCallback(intent, new ResultActivityHelper.ResultActivityListener() {
    @Override
    public void onResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ...
        }
    }
});
```

Http使用
```
// Http返回内容实体
public class DemoReply extends HttpBaseReplyBean {
    public String name;
    public int age;
}

//带有ProgressBar的Http调用
ZHttp.get(HttpUrl.URL_TEST, new ZResponse<DemoReply>(BaiduWeather.class, mActivity, "正在获取数据……") {
    @Override
    public void onError(int code, String error) {
        //TODO 错误处理 默认已经调用 ToastUtil.toastShort(LogUtil.ExceptionToString(e));
    }

    @Override
    public void onSuccess(Response response, DemoReply resObj) {
        if (resObj.results != null &&　resObj.results.size() > 0) {
        
        }
    }
});

//没有ProgressBar的调用
DemoApply entity= new DemoApply();
HttpUtil.post(HttpUrl.URL_TEST, entity, new ZResponse<DemoReply>() {
      @Override
      public void onError(int code, Call call, Exception e) {
          //TODO 错误处理 默认已经调用 ToastUtil.toastShort(LogUtil.ExceptionToString(e));  
      }
  
      @Override
      public void onSuccess(Response response, DemoReply resObj) {
          
      }
  });
  
//文件上传 
DemoApply entity= new DemoApply();
HttpUtil.uploadFile("URL_TEST", entity, "file_key", new File(""),new ZResponse<HttpCommonReply>(HttpCommonReply.class) {
  @Override
  public void onSuccess(Response response, HttpCommonReply httpCommonReply) {
      
  }
});
```

权限判断使用
```
//申请常用权限
PermissionHelper.requestCameraPermission(this, new PermissionsResultAction() {
        @Override
        public void onGranted() {
            ...
        }

        @Override
        public void onDenied(String permission) {
            ToastUtil.toastShort("请赋予本程序拍照权限！");
        }
    });
    
//申请多个权限
PermissionHelper.requestPermission(mActivity, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionsResultAction() {
    @Override
    public void onGranted() {
        ...
    }

    @Override
    public void onDenied(String permission) {
        ToastUtil.toastShort("请授予本程序拍照和读取SD卡文件权限!");
    }
});
```

ImageLoader图片加载使用
```
//图片显示
ImageLoaderUtils.displayImage(context, img_url, iv1);
//带默认图片显示
ImageLoaderUtils.displayImage(context, img_url, iv1, R.drawable.default);
//圆形图片显示
ImageLoaderUtils.displayCircleImage(context, img_url, imageView);
//圆角图片显示
ImageLoaderUtils.displayRoundCornersImage(context, img_url, imageView, 10);
```

DB使用:DB主要封装了DaoManager、DaoHelper,基本上所有操作都可以使用DaoManager.getDaoHleper()来操作。
其他的如实体类注解等使用可以google.
```
//Employ实体类
@Entity(nameInDb = "Employ", generateConstructors = false, generateGettersSetters = false)
public class Employee {
    @Id
    public long id;
    @NotNull
    public String name;
    public String group;
    public String company;
    public Date date;

//条件查询使用
QueryBuilder<Employee> queryBuilder = getDaoHelper().getQueryBuilder(Employee.class)
                                          .where(EmployeeDao.Properties.Group.eq("部门二"))
                                          .offset(1)
                                          .limit(3)
                                          .orderDesc(EmployeeDao.Properties.Date);
List<Employee> list = DaoManager.getDaoHelper().queryObjects(queryBuilder);

// 查询全部结果
List<Employee> list = DaoManager.getDaoHelper().queryAll(Employee.class);

// 插入
Employee employee = new Employee();
...
boolean b = getDaoHelper().insertObject(employee);

// 有则替换，无则插入
Employee employee = new Employee();
...
boolean b = getDaoHelper().insertOrReplaceObject(employee);

// 删除
Employee employee = new Employee();
...
getDaoHelper().deleteObject(employee);
```

常用工具类介绍与使用
在Frame框架Utils包中，可以查看源码或者查看生成的JavaDoc。
常用包括：
* ActivityUtil ：Activity 相关操作 工具类。
* AppUtil：APP管理工具类，如获取应用相关信息，应用退出 重启 安装 卸载.判断程序运行状况等函数的定义。
* ArrayUtils: 数组操作工具类。
* BitmapUtil: Bitmap工具类，如图片缩放，保存图片，复制图片，圆角处理等。
* CalendarUtil：日期操作工具类，主要格式化时间。
* DeviceUtil：设备操作工具类，如获取设备识别码，获取设置屏保时间，获取设置字体缩放大小等。
* DisplayUtil：像素转换工具类，如px互转dp，获取设备density等。
* FastClickUtils：防止控件被重复点击的辅助类。
* FileOpenUtil：使用第三方程序打开文件操作工具类。
* FileUtil：文件操作工具类,如文件拷贝，文件读写操作。
* GsonUtil：使用gson对Json数据和对象互转的工具类。
* KeyBoardUtil：软键盘操作如弹出、关闭工具类。
* LogUtil：日志操作工具类。
* MD5Util&RSAUtils:加密解密工具类。
* RegexUtil：正则工具类，匹配常用的如邮箱，手机号等。
* ScreenUtil：屏幕工具类，如获取屏幕宽高，获取截图等。
* SDCardUtil:SD卡工具类，如获取sd卡是否可用，获取sd可用容量等。
* SpannableStringBuilderUtil：SpannableStringBuilder的工具类。
* SpannableStringUtil：SpannableString的工具类。
* SPUtil：SharedPreferences配置文件读写封装。
* StateListUtil：StateList的构建类。
* StringFormatUtil：字符串格式化工具类。
* StringUtil：String相关操作工具类。
* SystemDownloadApk：托管系统下载工具类。
* SystemIntentUtil：调用系统Intent工具类。
* ToastUtil：Toast工具类，可以在子线程调用。


###UI框架使用示例

弹出框相关，ZDialog系列：
```
//异步执行，带有进度条的弹出框
ZDialogAsyncProgress dlg = new ZDialogAsyncProgress(context);
dlg.setDoInterface(new ZDialogAsyncProgress.DoInterface() {
    @Override
    public ZDialogAsyncProgress.ProcessInfo onDoInback() {
        return null;
    }

    @Override
    public void onPostExecute(ZDialogAsyncProgress.ProcessInfo info) {

    }
});
dlg.show();

//只有确定按钮的弹出框
ZAlert.instance(mActivity)
      .setTitle("ZAlert")
      .setMessage("这是一个Alert")
      .show();
      
//带有确定、取消按钮的弹出框      
ZConfirm.instance(mActivity)
      .setTitle("ZConfirm")
      .setMessage("这是一个通用对话框")
      .addSubmitListener(new ZDialog.ZDialogSubmitInterface() {
          @Override
          public boolean submit() {
              ToastUtil.toastShort("点击了确定");
              return true;
          }
      })
      .show();
      
//带有输入框的弹出框
ZDialogEdit.instance(context)
        .setTitle("ZDialogEdit")
        .setHint("请输入密码")
        .addSubmitStrListener(new ZDialog.ZDialogParamSubmitInterface<String>() {
            @Override
            public boolean submit(String s) {
                return true;
            }
        })
        .show(); 
        
        
//单选弹出框      
final String[] arrt = new String[]{"menu1", "menu2", "menu3", "menu4", "menu5"};
ZDialogRadioGroup.instance(mActivity)
    .setTitle("ZDialogRadioGroup")
    .setDatas(arrt, "menu1")
    .addSubmitListener(new ZDialog.ZDialogParamSubmitInterface<Integer>() {
        @Override
        public boolean submit(Integer integer) {
            ToastUtil.toastShort("选择了" + arrt[integer]);
            return true;
        }
    })
    .show();
    
//多选弹出框   
final String[] arrStr = new String[]{"menu1", "menu2", "menu3", "menu4", "menu5"};
ZDialogCheckBox.instance(mActivity)
        .setTitle("ZDialogCheckbBox")
        .setDatas(arrStr, null)
        .addValueSubmitListener(new ZDialog.ZDialogParamSubmitInterface<ArrayList<String>>() {
            @Override
            public boolean submit(ArrayList<String> s) {
                String str = "";
                for (String s1 : s) {
                    str += s1;
                    str += ",";
                }
                ToastUtil.toastShort("选中数据" + str);

                return true;
            }
        })
        .show();
    
//菜单弹出框    
final String[] arrt = new String[]{"menu1", "menu2", "menu3", "menu4", "menu5"};
ZDialogMenu.instance(mActivity)
        .setTitle("DlgMenu")
        .setDatas(arrt)
        .addSubmitListener(new ZDialog.ZDialogParamSubmitInterface<Integer>() {
            @Override
            public boolean submit(Integer integer) {
                ToastUtil.toastShort("选择了" + arrt[integer]);
                return true;
            }
        })
        .show();      
        
//gui库中的弹出框ZDialog系列控件都可以使用initLayout函数来重新设置Layout，但是自己的Layout的控件Id必须和库中的控件Id相同
ZDialogBottomView.initLayout(R.layout.dlg_bottomitem);
ZConfirm.initLayout(R.layout.dlg_confirm);
ZAlert.initLayout(layoutId);
ZDialogMenu.initLayout(layoutId);
ZDialogRadioGroup.initLayout(layoutId);
ZDialogCheckBox.initLayout(layoutId);
ZDialogWheelDate.initLayout(layoutId);
ZDialogEdit.initLayout(layoutId);
ZDialogProgress.initLayout(layoutId);
ZKeyValueEditView.initLayout(layoutId);
ZKeyValueView.initLayout(layoutId);
ZKeySwitchView.initLayout(layoutId);
```

ZKeyValue相关控件, 用来显示如设置页面行头Key行尾value的控件
包含：ZKeyValueView、ZKeyValueEditView、ZKeySwitchView。
```
//在xml文件中直接设置，也可以在代码中设置
<com.zcolin.gui.ZKeyValueView
    android:id="@+id/zkv_1"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:textAllCaps="false"
    app:zkv_is_arrow="true"         
    app:zkv_is_bottomline="true"
    app:zkv_key_gravity="left"
    app:zkv_key_img="@drawable/ic_launcher"
    app:zkv_key_text="KEY"
    app:zkv_key_text_color="@color/black_dark"
    app:zkv_key_text_size="20dp"
    app:zkv_value_text="VALUE"
    app:zkv_value_text_color="@color/black_dark"
    app:zkv_value_text_size="20dp"/>
```

ZWebView：
```
webView = (ZWebView) findViewById(R.id.webView);
webView.setSupportVideoFullScreen(this)
        .setSupportProgressBar()//支持顶部加载进度条
        .setSupportChooeFile(activity)//支持网页文件选择
        .setSupportVideoFullScreen（activity）//支持视频播放全屏
        .setSupportAutoZoom()//支持网页自动缩放
        .setSupportJsBridge();//支持JSBridge作为js通讯桥梁
```

Other
```
//ZViewPager 可以设置是否支持滑动
viewpager.setCanScroll(false);
//ZScrollView 可以设置滚动监听和最大高度的ScrollView
scrollView.setScrollViewListener(ScrollViewListener scrollViewListener)
scrollView.setMaxHeight(int maxHeight);

//ZTagLayout  标签控件流式布局
ZTagLayout.Tag tag = tagLayout.createTag(String.format("第%d个标签", 0))
                          .setData(data)
                          .setBackground(null)
                          .setTextColor(getResources().getColor(R.color.black_light))
                          .setPressTextColor(getResources().getColor(R.color.black_light))
                          .setSelectTextColor(getResources().getColor(R.color.colorPrimary))
                          .setIsSelected(i == 5);
tagLayout.addTag(tag);

//ZoomImageView 手势放大缩小图片控件
zoomImageView.setMinScale(0.5f);
zoomImageView.setMaxScale(2f);
zoomImageView.setOnPhotoTapListener(new ZoomImageView.OnPhotoTapListener() {
    @Override
    public void onPhotoTap(View view, float x, float y) {
        toggleCoverView();
    }
});

//ZBanner 导航轮播图
banner.setBannerStyle(ZBanner.NUM_INDICATOR_TITLE)
  .setIndicatorGravity(ZBanner.RIGHT)
  .setBannerTitle(listTitle)
  .setDelayTime(4000)
  .setOnBannerClickListener(new ZBanner.OnBannerClickListener() {
      @Override
      public void OnBannerClick(View view, int position) {

      }
  })
  .setImages(listUrl)
  .startAutoPlay();
  
//ZEditTextWithClear 带有清除按钮的EditText  

//ZEditTextWithPassword 带有密码是否显示的EditText

//ZBadgeView  小红点view
ZBadgeView badgeView = new ZBadgeView(context, view);
badgeView.setText("2");
badgeView.show();


```