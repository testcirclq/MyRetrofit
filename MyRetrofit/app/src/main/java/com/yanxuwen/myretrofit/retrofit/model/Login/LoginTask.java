package com.yanxuwen.myretrofit.retrofit.model.Login;

import android.app.Activity;
import android.util.Log;

import com.yanxuwen.myretrofit.retrofit.Msg.Msg;
import com.yanxuwen.myretrofit.retrofit.MyBaseRequest;
import com.yanxuwen.myretrofit.retrofit.MyBaseTask;
import com.yanxuwen.myretrofit.retrofit.model.Login.Login;
import com.yanxuwen.myretrofit.retrofit.model.Login.LoginApi;
import com.yanxuwen.myretrofit.retrofit.model.Login.LoginBuild;
import com.yanxuwen.retrofit.Msg.ObserverListener;

import java.lang.reflect.Type;

import rx.Observable;

/**
 * Created by yanxuwen on 2017/7/21.
 */

public class LoginTask extends MyBaseTask {
    LoginBuild mBuild;
    public LoginTask(Activity context,ObserverListener ob) {
        super(context,ob);
        //执行传递参数
        mBuild =new LoginBuild(context);
        mBuild.setMobile("15060568265");
        mBuild.setPassword("e10adc3949ba59abbe56e057f20f883e");
    }
    @Override
    public Observable<String> getObservable() {
        return  ((LoginApi)getBaseApi()).postman(getRequestBody());
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
        return Msg.LOGIN;
    }
    @Override
    public MyBaseRequest requestClass() {
        return mBuild;
    }
    @Override
    public Type returnClass() {return Login.class;}
    @Override
    public Class<?> getBaseApiClass() {return LoginApi.class;}
    @Override
    public void onCompleted() {}
    @Override
    public void onError(Object object) {
        Log.e("xxx","登录失败");
    }
    @Override
    public void onSuccess(Object object) {
        Log.e("xxx","登录成功");
    }
    @Override
    public void onFail(Object object) {}
}

