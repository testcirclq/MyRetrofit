# 前言
Retrofit的简洁，用过的人都是赞不绝口，然而我们的目标是更加简洁，不同请求类型，只需要粘贴复制即可。
# 依赖
   compile 'com.yanxuwen.retrofit:retrofit:1.1.9'
# 目录结构

![image.png](http://upload-images.jianshu.io/upload_images/6835615-13e6e1fc2a36f1c8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
我们可以看到“Token请求”跟“版本更新请求”2个接口分的很明显，添加别的接口的时候，像这样写法写，就可以很方便的查看我们的哪个接口。
#首先先介绍3个最基本的父类MyBaseModel,MyBaseRequest,MyBaseTask
#####MyBaseModel:
添加你公司通用的返回字段，如status跟message等字段，
~~~
public class MyBaseModel extends BaseModel {
}
~~~
这里没有写任何字段是因为在BaseModel 已经写好了这2个字段，如果你们公司不是这2个字段，则在MyBaseModel添加你需要的字段【注意：是通用的返回字段，不是Token返回的所有字段】

##### MyBaseRequest:
~~~
public class MyBaseRequest extends BaseRequest {
    public String access_token;
    public String uid;
    public String app_version;//版本
    public String device_type_phone;//设备类型
    public String app_type;//app类型


    public MyBaseRequest() {
        super();
    }

    public MyBaseRequest(Context context) {
        app_version = SystemUtil.getAppVersionName(context);
        device_type_phone = SystemUtil.getBrand();
        app_type = "appType";
    }

}
~~~
自动为每个接口添加“版本号”，“设备类型”，“app类型”，这样我们不用每次请求都添加一般，减少繁琐，
【注意：只适合post请求，get请求无法自动添加。】

##### MyBaseTask:
~~~
public class MyBaseTask extends BaseTask {

    public LoadingDialog loadview;

    public MyBaseTask(Context context) {
        super(context);
        //如果你们公司是根据status字段在判断是否成功，则你可以直接传入0，代表返回0则成功，其他状态为失败
        setSuccessConditionCode(0);
        //实例化加载框，这里使用的是我的加载框，你们可以根据你的加载框进行实例化
        loadview=new LoadingDialog(context,500);
    }
    @Override
    public Observable<String> getObservable() {
        return null;
    }

    @Override
    public String getDataType() {
        return null;
    }

    @Override
    public BaseRequest requestClass() {
        return null;
    }

    @Override
    public Class<?> getBaseApiClass() {
        return null;
    }

    @Override
    public Type returnClass() {
        return null;
    }

    @Override
    public void onCompleted() {}
    @Override
    public void onError(Object object) {}
    /**请求成功*/
    @Override
    public void onSuccess(Object object) {}
    @Override
    /**请求失败*/
    public void onFail(Object object) {}
    /**添加头部*/
    public Map<String,String> addHeaders(){
        return null;
    }
    /**是否加密,默认不加密*/
    public boolean isEncrypt(){return false;};
    /** 设置是否显示加载框,模式是*/
    public  boolean isShow(){return true;};
    /** 设置是否显示Log*/
    public  boolean isLog(){return true;};
    /**是否直接返回String,默认为false*/
    public  boolean isReturnString(){return false;};
    /**是否统一显示后台返回的提示文字,默认false*/
    public  boolean isToast(){return false;};
    /**显示加载框*/
    public void onShowLoading(){
        if (loadview != null && !loadview.isShowing()) {
            loadview.show();
        }
    }
    /**取消加载框*/
    public void onDismissLoading(){
        if (loadview != null) {
            loadview.onDismiss();
        }
    }
    /**加密*/
    public String onEncrypti(String json){return json;}
    /**解密*/
    public String onDecrypt(String json){return json;}
    /**特殊处理，如可以根据object返回数据进行解析，如状态为-1000则跳转登录*/
    public boolean ProcessingData(ObserverListener.STATUS status, Object object){
        if(object instanceof BaseModel){
            MyBaseModel mMyBaseModel=(MyBaseModel)object;
            if(mMyBaseModel.getStatus()==-1000){
                //执行你要的操作，如：跳转登录
                return false;
            }
        }
        return true;
    }
}

~~~
配置这里都写得很清楚，不懂的直接复制就可以，到时候在联系本人，我会很详细的介绍。
#####配置好3个base后，我们还需要在配置下BaseActivity，与application
BaseActivity
~~~
public class BaseActivity extends AppCompatActivity implements ObserverListener {
    public RequestUtils mRequestUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRequestUtils=new RequestUtils(this);
    }
    protected void onResume() {
        //添加观察者
        Publisher.getInstance().addOb(this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除观察者
        Publisher.getInstance().removeOb(this);
    }
    /**
     * 判断数据请求是否是失败
     */
    public boolean isDataFail(STATUS status){
        if(status== STATUS.FAIL){
            Log.e("xxx","失败");
            return true;
        }else if(status==STATUS.ERROR){
            Log.e("xxx","网络异常");
            return true;
        }
        else if(status==STATUS.SPECIAL){
            return true;
        }
        return false;
    }
    @Override
    public void onNotifyData(STATUS status, String type, Object object) {
    }
}

~~~
application,只是简单的初始化而已，初始化传递baseurl而已
~~~
  ApiManager.init(ConfigUtils.BASE_URL);
  ApiManager.initHttpsType(ApiManager.HttpType.HTTP);
~~~
# 好了，配置好4个base后，我们就可以一个简单的请求。也许很多人已经迷茫了，4个base都看不懂，没关系，来找我，我会很详细的给你介绍，弄懂后，要网络请求真的比你想象中的还要简单。


# 我们先来看下Token包下的结构：

![image.png](http://upload-images.jianshu.io/upload_images/6835615-923ba14a3a09d35d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
我们可以看到其实就是继承上面的3个base而已。

##### 请求体跟返回体，我们就不多介绍了，主要介绍下TokenApi跟TokenTask

##### TokenApi，这里就不需要多介绍了，用过的人一看就知道，这里就贴代码给大家看就会，
~~~
/**
 * Token接口
 * @Description("")尽量设置描述，以便方便查询已经log的打印
 * @HttpType 设置默认的请求类型，如果不设置，则根据ApiManager.initHttpsType设置的默认请求类型
 */
@Description("Token请求")
public interface TokenApi   {
    @value
    final String url="user/verify_token";
    @POST(url)
    public abstract Observable<String> postman(@Body RequestBody description);
}
~~~
【主意：Description，强烈建议4个类都需要添加Description注解，因为这样我们在请求过程中，会自动打印显示哪个请求执行了什么。如下： 】


![image.png](http://upload-images.jianshu.io/upload_images/6835615-2b8cb67661a3fdd0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
看到没哪个接口执行了什么，请求地址，请求参数，返回参数都会自动打印出来，这样容易根据我们需要的数据。是不是很爽。

##### TokenTask，这里也不多介绍了，一些方法介绍都在他的父类，看一下就知道，唯一一个就是【动态添加头部】
~~~
public class TokenTask extends MyBaseTask {
    TokenBuild mBuild;
    public TokenTask(Activity context) {
        super(context);
        //执行传递参数
        mBuild =new TokenBuild(context);
        mBuild.setAccess_token("QcTfVgAAAAAHALMfqOxKcR8pHctCebKcxktS");
        mBuild.setUid("917048770148592");
        mBuild.setPhone("15060568265");
    }
    @Override
    public Observable<String> getObservable() {
        return  ((TokenApi)getBaseApi()).postman(getRequestBody());
    }
    @Override
    public boolean isEncrypt() {
        return true;
    }
    @Override
    public boolean isShow() {return true;}
    @Override
    public boolean isReturnString() {return false;}

    @Override
    public boolean isToast() {
        return true;
    }

    @Override
    public String getDataType() {
        return Msg.TOKEN;//【注意：这边定义要请求类型，该类型是字符串，然后就在在Activity返回该字符串，用于我们区别是什么请求返回的】
    }
    @Override
    public MyBaseRequest requestClass() {
        return mBuild;
    }
    @Override
    public Type returnClass() {return Token.class;}
    @Override
    public Class<?> getBaseApiClass() {return TokenApi.class;}
    @Override
    public void onCompleted() {}
    @Override
    public void onError(Object object) {}
    @Override
    public void onSuccess(Object object) {}
    @Override
    public void onFail(Object object) {}
    //如果有动态头部，则重载该方法，添加你需要的头部即可，
//    public Map<String, String> addHeaders() {
//        Map<String, String> map = new HashMap<>();
//        String apiKey = UserUtils.getInfo().getApiKey();
//        String signature = MD5.getSignature("video:fetch-recommend-video-list");
//        String caa = "Caa " + MD5.Base64(apiKey + ":" + signature + " " + MD5.getSignatureTime(), false);
//        map.put("Authorization", caa);
//        return map;
//    }
}
~~~

##### 好了这样我们请求写完了，只要在Activity上调用  mRequestUtils.requestToken();就可以执行Token请求了，，然后重载onNotifyData，如下代码，由于我们配置了BaseActivity，所以请求完成后就会自动调用了本类Activity的onNotifyData方法，Msg.TOKEN就是token返回的数据，Msg.VERSION:就是版本更新返回的数据
~~~
 /**
     * 请求数据返回结果
     *
     * @param status 请求成功还是失败
     * @param type
     * @param object 类型
     */
    public void onNotifyData(STATUS status, String type, Object object) {
        switch (type) {
            case Msg.TOKEN:
                if (isDataFail(status)) {
                    return;
                }
                if(object!=null){
                   //执行你的操作
                }
                return;
            case Msg.VERSION:
                if (isDataFail(status)) {
                    return;
                }
                if(object!=null){
                    //执行你的操作
                }
                return;
            default:
                break;
        }
        super.onNotifyData(status, type, object);
    }
~~~
# github   https://github.com/yanxuwen/MyRetrofit.git
# 有什么问题欢迎加qq:420255048咨询
