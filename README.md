# JS混合开发说明

#### 分为原生调用js和js调用原生两部分，原生调用js关键字以js_开头，js调用原生的关键字以native_开头。
###### 所有返回数据结构统一为｛"code":200,"msg":"xxx"｝。其中code=200为成功，其它为失败，msg为失败信息。

原生调用js
==
1. 返回上一个页面。
```
关键字：  js_goBack
请求参数：空
返回参数：{
            "result":true          //true表示网页端拦截（原生不需要处理），false表示网页端未拦截（原生可自己处理）
        }
回调时机：调用完关闭页面后
```


js调用原生
==
1. 启动新的webview加载ionic页面，标题栏使用原生标题栏 
```
关键字：  native_startPage
请求参数：{
            "pageName":"xxx",       //ionic的页面名称
            "params”:"xxx",         //ionic启动页面时需要传递的参数，已经urlencode，直接传递给新的ionic页面即可
            "title”:"xxx"           //原生标题栏名称，如果为空，则不显示原生标题栏
        } 
返回参数：{
            "cdoe":200,             //成功=200，失败为其他
            "msg":"xxx",            //成功可为空，失败填写失败原因
            "params":"xxx"          //根据业务制定 
        }
回调时机：新打开的页面关闭时回调
```
2. 启动新的webview加载外部链接，标题栏使用原生标题栏 
```
关键字：  native_startWebPage
请求参数：{
            "url":"xxx",            //链接地址
            "title”:"xxx"           //原生标题栏名称，如果为空，则不显示原生标题栏
        } 
返回参数：{
            "cdoe":200,             //成功=200，失败为其他
            "msg":"xxx",            //成功可为空，失败填写失败原因
            "params":"xxx"          //根据业务制定 
        }
回调时机：新打开的页面关闭时回调
```
3. 关闭当前WebView
```
关键字：  native_finishPage
请求参数：空
返回参数：{
            "cdoe":200,             //成功=200，失败为其他
            "msg":"xxx"             //成功可为空，失败填写失败原因
        }
回调时机：执行完关闭后
```
4. 弹出toast
```
关键字：  native_toast
请求参数：{
             "message":"xxx",           //显示文字
             "duration”:"xxx"           //显示时间， 默认为2000ms
        }
返回参数：{
            "cdoe":200,             //成功=200，失败为其他
            "msg":"xxx"             //成功可为空，失败填写失败原因
        }
回调时机：执行完弹出后
```
5. 获取当前版本号 
```
关键字：  native_appVersion
请求参数：空
返回参数：{
            "cdoe":200,             //成功=200，失败为其他
            "msg":"xxx",            //成功可为空，失败填写失败原因
            "versionName":"xxx",    // ios版本号使用此字段
            "versionCode":"xxx"     
        }
回调时机：执行完获取后
```
6. 获取当前UUID
```
关键字：  native_uuid
请求参数：空
返回参数：{
            "cdoe":200,             //成功=200，失败为其他
            "msg":"xxx",            //成功可为空，失败填写失败原因
            "uuid":"xxx"            //UUID
        }
回调时机：执行完获取后
```
7. 原生分享 
```
关键字：  native_share
请求参数：{
             "title":"xxx",            //分享标题
             "content”:"xxx",          //分享内容
             "targetUrl":"xxx",        //分享链接地址
             "imageUrl":"xxx"          //分享图片地址
        }
返回参数：{
            "cdoe":200,             //成功=200，失败为其他
            "msg":"xxx"             //成功可为空，失败填写失败原因
        }
回调时机：执行完分享后（以后可能改为分享完成后）
```
8. 扫描二维码
```
关键字：  native_scanQrCode
请求参数：无
返回参数：{
            "cdoe":200,             //成功=200，失败为其他
            "msg":"xxx",            //成功可为空，失败填写失败原因
            "result":"xxx"          //扫描结果，扫描过程中如果扫描结果未获取到，重新扫描不回调
        }
回调时机：获取到扫描结果后
```
9. 调用原生定位
```
关键字：  native_location
请求参数：无
返回参数：{
            "cdoe":200,             //成功=200，失败为其他
            "msg":"xxx",            //成功可为空，失败填写失败原因
            "latitude":"xxx",       //参照高德参数
            "longitude":"xxx",      //参照高德参数
            "province”:"xxx",       //参照高德参数
            "city":"xxx",           //参照高德参数
            "cityCode":"xxx",       //参照高德参数
            "district":"xxx",       //参照高德参数
            "adCode":"xxx",         //参照高德参数
            "address":"xxx",        //参照高德参数
            "road”:"xxx",           //参照高德参数
            "street":"xxx",         //参照高德参数
            "streetNum":"xxx",      //参照高德参数
            "country":"xxx"         //参照高德参数
        }
回调时机：获取到定位结果后(失败或成功)
```
10. 选择图片（测试结果比使用input获取慢很多，暂时可使用input代替）
```
关键字：  native_selectImage
请求参数：{
             "mxaNumber":"xxx",     //最大选择数量， 默认为1
             "minPixel”:"xxx",      //最低压缩分辨率
        }
返回参数：{
            "cdoe":200,             //成功=200，失败为其他
            "msg":"xxx",            //成功可为空，失败填写失败原因
            "images":
            [                       //图片集合
                ｛   
                    "name":"xxx",  //文件名称
                    "data":"xxx",  //base64数据
                    "path":"xxx"   //在原生端的路径
                ｝
            ]       
        }
回调时机：选择完图片后
```

11. 选择文件
```
关键字：  native_selectFile
请求参数：{
             "type":"image/jpeg",    //文件类型， 默认*/*
        }
返回参数：{
            "cdoe":200,             //成功=200，失败为其他
            "msg":"xxx",            //成功可为空，失败填写失败原因
            "file":
            ｛   
                "name":"xxx",  //文件名称
                "path":"xxx"   //在原生端的路径
            ｝
        }
回调时机：选择完文件后
```

12. 使用http传输
```
关键字：  native_http
请求参数：{
             "method":"post",       //post或者get，默认post
             "url":"http://xxxx",   //请求地址
             "headers":{            //报文头
                 "key1":"value1",
                 "key2":"value2"
             },
             "params":              //报文体
             {
                "key1":"value1",
                "key2":"value2"
             },
             "files":{              //上传文件
                "fileName1":"path1",
                "fileName2":"path2"
             }
        }
返回参数：{
            "cdoe":200,             //成功=200，失败为其他
            "msg":"xxx",            //成功可为空，失败填写失败原因
            "result":"{"code":200,"message":"xxx","key1":"value1"}"  //服务器返回参数
        }
回调时机：请求完成后
```
13. key-value存储
```
关键字：  native_put_storage
请求参数：{
            "key1":"value1",        //key-value
            "key2":"value2",        //key-value
        }
返回参数：{
            "cdoe":200,             //成功=200，失败为其他
            "msg":"xxx"            //成功可为空，失败填写失败原因
        }
回调时机：存储完成后
```
14. 获取key-value存储
```
关键字：  native_get_storage
请求参数：{
            "keys":
            [
                "key1",
                "key2"
            ]
        }
返回参数：{
            "cdoe":200,             //成功=200，失败为其他
            "msg":"xxx",            //成功可为空，失败填写失败原因
            "result":               //根据key获取的值
             {
                 "key1":"value1",        //key-value
                 "key2":"value2",        //key-value
             }
        }
回调时机：获取完成后
```