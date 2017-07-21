package com.yanxuwen.retrofit;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yanxuwen.retrofit.Annotation.AnnotationUtils;
import com.yanxuwen.retrofit.Annotation.value;
import com.yanxuwen.retrofit.Msg.ObserverListener;
import com.yanxuwen.retrofit.Msg.Publisher;
import com.yanxuwen.retrofit.api.ApiManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：严旭文 on 2016/4/25 16:57
 * 邮箱：420255048@qq.com
 */
public abstract class BaseTask {
    public enum STATUS{
        STATUS_SUCCESS,STATUS_FAIL,STATUS_TIMEOUT,STATUS_ERROR
    }
    private Publisher mPublisher;
    private boolean isResult=true;
    public ObserverListener ob;
    private Integer[] HTTP_CODE_FAIL=new Integer[]{};//HTTP异常那些是失败的，
    private Integer[] API_CODE_SUCCESS=new Integer[]{0};//后台返回成功代码，默认值为1个0
    /**请求类型*/
    private String type;
    private Observable<String> mObservable;
    private RequestBody description;
    public   Context context;
    public BaseTask(Context context){
        this.context = context;
        mPublisher=Publisher.getInstance();
    }

    /**
     * 该请求是：不管在哪个界面请求，都会返回当前操作的Activity。
     */
    @CallSuper
    public void request(){
        this.isResult=true;
        subscribe();
    }
    /**
     * @param isResult 判断是否要返回到当前操作Activity
     * 该请求是：isResult==true的时候会返回当前操作Activity
     *           isResult==false的时候，不返回
     */
    public void request(boolean isResult){
        this.isResult=isResult;
        subscribe();
    }

    /**
     * @param ob
     * 该请求是，哪里请求返回到哪里，主要用于类的请求，由于Activity都封装好了，但是普通类没有，所以主要针对类，用Activity请求也是可以
     * 注意：如果是类的话一定要加上 Publisher.getInstance().addOb(this);，因为Activity的baseActivity已经封装好了，所以Activity都不需要在写一次
     */
    public void request(ObserverListener ob){
        this.isResult=true;
        this.ob=ob;
        subscribe();
    }
    /**
     * mObservable使用对象进行订阅请求
     **/
    private void subscribe(){
        setType();
        if(mObservable!=null){
            if(isShow()){
                onShowLoading();
            }
        mObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        if(isShow()){
                            onDismissLoading();
                        }
                        BaseTask.this.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(isShow()){
                            onDismissLoading();
                        }
                        BaseModel mBaseModel =new BaseModel();
                        if(e instanceof HttpException){
                            HttpException httpException= (HttpException) e;
                            int code=httpException.code();
                            if(onHttpFailCondition(code)){
                                try {
                                    Response<?> mResponse = httpException.response();
                                    String errorBody= mResponse.errorBody().string();
                                    mBaseModel.status=code;
                                    mBaseModel.message=errorBody;
                                    ProcessData(ObserverListener.STATUS.FAIL, mBaseModel);
                                }catch (Exception e2){}
                            }

                        }else{
                            mBaseModel.status=e.hashCode();
                            mBaseModel.message= e.getMessage();
                            ProcessData(ObserverListener.STATUS.ERROR,mBaseModel);
                        }
                        Gson gson=new Gson();
                        String json=gson.toJson(mBaseModel);
                        if(isLog()) {
                            Log.d(LogType.http, "【" + getPackageName(requestClass()) + "】返回【接口描述：" + AnnotationUtils.getAnnotationDescription(getBaseApiClass()) + " ▎" +
                                    "url：" + getUrl() + "】");
                            Log.d(LogType.http, "【" + getPackageName(requestClass()) + "】返回:" + json);
                        }
                    }

                    @Override
                    public void onNext(String str) {
                        //如果有加密，则事先要解密下,由于暂时没加密，则先用String decrypt=str;替代
                        String decrypt = onDecrypt(str);
                        if(isLog()) {
                            Log.d(LogType.http, "【" + getPackageName(requestClass()) + "】返回【接口描述：" + AnnotationUtils.getAnnotationDescription(getBaseApiClass()) + " ▎" +
                                    "url：" + getUrl() + "】");
                        }
                        try {
                            JSONObject json = new JSONObject(decrypt);
                            decrypt = json.toString();
                        } catch (JSONException e) {
                        }
                        if(isLog()) {
                            Log.d(LogType.http, "【" + getPackageName(requestClass()) + "】返回:" + decrypt);
                        }
                        if (decrypt != null) {
                            try {
                                Object object=GsonUtils.fromJson(decrypt,returnClass());

                                if (isReturnString()) {//直接返回String
                                    object = decrypt;
                                }
                                //请求成功
                                if (onSuccessCondition(object)) {
                                    ProcessData(ObserverListener.STATUS.SUCCESS,object);
                                } else {
                                    ProcessData(ObserverListener.STATUS.FAIL,object);
                                }
                            } catch (Exception e) {
                                if(isLog()) {
                                    Log.e(LogType.http, "报错" + decrypt);
                                }
                                BaseTask.this.onError("解析失败，请查看json格式");
                            }
                        } else {
                            BaseTask.this.onError("s为空");
                        }
                    }
                });
        }
    }

    /**
     * 【注意】  object 的父类不一定是BaseModel，要根据接口而定，但是如果是ERROR或者FAIL为HTTP指定的错误（onHttpFailCondition的code）,则父类为BaseModel
     * 所以判断之前要先if(object instanceof BaseModel
     * @param status 接口请求状态，成功，失败，异常
     * @param object 返回数据
     * @return 【true】，代表执行通用处理，既默认发送ObserverListener各个监听者，以及会根据开关显示Toast,
     *          【false】,则会截断ObserverListener跟Toast，主要用于一些特殊处理，比如我们可以根据当前的返回状态BaseModel的status来判断是否登录，如果未登录的，则跳转到登录界面去，则不继续发送通知
     *
     */
    public boolean ProcessingData(ObserverListener.STATUS status,Object object){
           return true;
    }
    /**
     * 处理消息
     */
    private void ProcessData(ObserverListener.STATUS status,Object object){
        if(!ProcessingData(status,object)){
            if (isResult) {
                mPublisher.publishdata(ob, ObserverListener.STATUS.SPECIAL, getDataType(), object);
            }
            return;
        }
        switch (status){
            case SUCCESS:
                BaseTask.this.onSuccess(object);
                break;
            case FAIL:
                BaseTask.this.onFail(object);
                break;
            case ERROR:
                BaseTask.this.onError(object);
                break;
            case TIMEOUT:
                break;
        }
        if (isResult) {
            mPublisher.publishdata(ob,status, getDataType(), object);
        }
        //如果是成功跟报错则统一显示消息
        if(status== ObserverListener.STATUS.SUCCESS||status== ObserverListener.STATUS.FAIL){
            BaseModel baseResult = (BaseModel) object;
            //统一显示消息
            if(baseResult!=null&&baseResult.getMessage()!=null&&isToast()) {
                Toast.makeText(context, baseResult.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    /**
     * 1.使用gson将请求对象转化为String
     * 2.打印请求信息
     * 3.对数据进行加密
     * 4.把数据转换为RequestBody对象，进行传递
     * 5.初始化mObservable对象，在子类getObservable()处理，
     * 如((TokenApi)getBaseApi()).postString(getRequestBody());
     */
    private void setType(){
        Gson gson=new Gson();
        String json=gson.toJson(requestClass());
        if(isLog()) {
            Log.d(LogType.http, "【" + getPackageName(requestClass()) + "】请求【接口描述：" + AnnotationUtils.getAnnotationDescription(getBaseApiClass()) + " ▎" +
                    "url：" + getUrl() + "】");
            Log.d(LogType.http, "【" + getPackageName(requestClass()) + "】请求:" + json);
        }
        //执行加密
        if(isEncrypt()){
            //对JSON进行加密
            json=onEncrypti(json);
        }
         description =
                RequestBody.create(MediaType.parse("text/html"), json);
        mObservable=getObservable();
    }
    public  RequestBody getRequestBody(){
        return description;
    }
    /**
     * getBaseApi根据传递的.class[如TokenApi.class]通过Retrofit获取对应的Api对象,【域名是通用的】，如果想要特定的域名则使用getBaseApi(String baseUrl)的方法
     * getBaseApi与getBaseApiClass的区别是，，getBaseApi是对象，getBaseApiClass是.class
     * */
    public  Object getBaseApi(){
        String baseUrl=getBaseUrl();
        //如果没有设置BaseUrl注解或者为空，则默认公用的baseUrl
        if(baseUrl==null||baseUrl.equals("")){
            return  ApiManager.createServiceStringBuilder2(getBaseApiClass(),context,getHttpType(),addHeaders());
        }else{
            return  ApiManager.createServiceStringBuilder2(getBaseApiClass(),baseUrl,context,getHttpType(),addHeaders());
        }
    }
    public Map<String,String> addHeaders(){
        return null;
    }

    /**调用XXXAPI(TokenApi)里面的方法。执行请求操作*/
    public abstract  Observable<String> getObservable();
    /**请求类型，用于判断对应的类型*/
    public abstract String getDataType();
    /**请求类型，记得在构造函数初始化*/
    public abstract BaseRequest requestClass();
    /**主要用于打印一些api里面的一些url地址*/
    public abstract Class<?>  getBaseApiClass();
    /**返回的类型，用于Json转换为对象用*/
    public abstract Type returnClass();


    /**开始请求*/
    public abstract void onCompleted();
    /**请求异常*/
    public abstract void onError(Object object);
    /**请求成功*/
    public abstract void onSuccess(Object object);
    /**请求失败*/
    public abstract void onFail(Object object);

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

    /*显示加载框*/
    public void onShowLoading(){}
    /*取消加载框*/
    public void onDismissLoading(){}
    /*加密*/
    public String onEncrypti(String json){return json;}
    /*解密*/
    public String onDecrypt(String json){return json;}
    /*成功条件，就是后台返回给你，根据字段判断是请求失败还是成功。通常都是用status字段，如果不是可以自己定义一个*/
    public boolean onSuccessCondition(Object object){
        try {
            BaseModel baseResult = (BaseModel) object;
            for(int i=0;i<API_CODE_SUCCESS.length;i++){
                if (baseResult.getStatus() == API_CODE_SUCCESS[i]) {
                    return true;
                }
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }
    /*请求成功条件的code值*/
    public void setSuccessConditionCode(Integer... code){
        API_CODE_SUCCESS=code;
    }
    /*Http失败条件，
    * 跟onSuccessCondition区别在于，onSuccessCondition是后台返回请求成功，但是根据某个字段的值来判断，请求是失败还是正确
    * onHttpFailCondition  是用于某些用Http请求异常返回的值，，比如我们最常见的404
    * 判断那些HTTP异常值是失败，哪些是异常，，异常跟失败是不同的。
    * */
    public boolean onHttpFailCondition(int code){
        for(int i=0;i<HTTP_CODE_FAIL.length;i++){
            if(code==HTTP_CODE_FAIL[i]){
                return true;
            }
        }
        return false;
    }
    /*传递的值是判断那些值是失败，不在这个值里面全部是异常，，异常以失败是不同的意思*/
    public void onHttpFailConditionCode(Integer... code){
        HTTP_CODE_FAIL=code;
    }






    /**
     * 获取包名的最后一个包，主要用于log知道是哪个请求
     */
    public String getPackageName(Object object){
        Class c = object.getClass(); // 获取类的类型类
        String str_package ="";
        try{
            str_package  = c.getPackage().toString();
            str_package=str_package.substring(str_package.lastIndexOf(".")+1,str_package.length());
        }catch (Exception e){}
        return  str_package;
    }

    /**
     * @return
     * 获取url
     */
    public String getUrl(){
        String url="";
        try{
            url= (String) AnnotationUtils.getAnnotationFieldValue(getBaseApiClass(),value.class,"url");
        }catch(Exception e){}
        return url;
    }

    /**
     * @return
     * 获取特定的baseUrl,注意不是公用的baseUrl
     */
    public String getBaseUrl(){
        String url="";
        try{
            url= (String) AnnotationUtils.getAnnotationFieldValue(getBaseApiClass(),value.class,"baseUrl");
        }catch(Exception e){}
        return url;
    }
    /**
     * @return
     * 获取mHttpType
     */
    public ApiManager.HttpType getHttpType(){
        ApiManager.HttpType httpType = null;
        try{
            httpType= AnnotationUtils.getAnnotationHttpType(getBaseApiClass());
        }catch(Exception e){
        }
        if(httpType==null) httpType= ApiManager.default_httpType;
        return httpType;
    }


}
