package com.yanxuwen.myretrofit.retrofit;

/**
 * Created by yxe on 2017/6/17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.yanxuwen.loadview.LoadingDialog;
import com.yanxuwen.retrofit.BaseModel;
import com.yanxuwen.retrofit.BaseRequest;
import com.yanxuwen.retrofit.BaseTask;
import com.yanxuwen.retrofit.Msg.ObserverListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

import rx.Observable;

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
        if(object instanceof MyBaseModel){
            MyBaseModel mMyBaseModel=(MyBaseModel)object;
            if(mMyBaseModel.getStatus()==-1000){
                //执行你要的操作，如：跳转登录
                return false;
            }
        }
        return true;
    }
}
