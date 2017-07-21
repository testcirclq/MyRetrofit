package com.yanxuwen.myretrofit.retrofit.model.Version;

import android.app.Activity;
import android.util.Log;

import com.yanxuwen.myretrofit.retrofit.Msg.Msg;
import com.yanxuwen.myretrofit.retrofit.MyBaseRequest;
import com.yanxuwen.myretrofit.retrofit.MyBaseTask;

import java.lang.reflect.Type;

import rx.Observable;

/**
 * 作者：严旭文 on 2016/7/4.
 * 邮箱：420255048@qq.com
 */
public class VersionTask extends MyBaseTask {
    VersionBuild mBuild;
    Activity context;
    public VersionTask(Activity context) {
        super(context);
        this.context=context;
        mBuild=new VersionBuild(context);
        mBuild.setVersion("3.5.0");
    }
    @Override
    public Observable<String> getObservable() {
        return  ((VersionApi)getBaseApi()).postman(mBuild.getVersion());
    }
    @Override
    public boolean isEncrypt() {
        return true;
    }
    @Override
    public boolean isShow() {
        return false;
    }
    @Override
    public boolean isReturnString() {
        return false;
    }

    @Override
    public boolean isToast() {
        return true;
    }

    @Override
    public String getDataType() {
        return Msg.VERSION;
    }
    @Override
    public MyBaseRequest requestClass() {
        return mBuild;
    }
    @Override
    public Type returnClass() {
        return Version.class;
    }
    @Override
    public  Class<?> getBaseApiClass() {return VersionApi.class;}
    @Override
    public void onCompleted() {}
    @Override
    public void onError(Object object){

    }
    @Override
    public void onSuccess(Object object){
        Log.e("xxx","检测成功");
    }
    @Override
    public void onFail(Object object){
        Log.e("xxx","检测失败");
    }
}

