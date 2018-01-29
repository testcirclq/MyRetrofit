package com.yanxuwen.myretrofit.retrofit.model.Login;

import android.app.Activity;
import android.widget.Toast;

import com.yanxuwen.myretrofit.retrofit.Msg.Msg;
import com.yanxuwen.myretrofit.retrofit.MyBaseTask;
import com.yanxuwen.retrofit.BaseRequest;
import com.yanxuwen.retrofit.Msg.ObserverListener;

import java.lang.reflect.Type;

import rx.Observable;

/**
 * Created by yanxuwen on 2017/5/26.
 */

public class LoginTask extends MyBaseTask {
    String  API_ERROR_ERROR[];
    String  API_ERROR_MSG[];
    public void  initApiCode(){
        API_ERROR_CODE=new Integer[]{401,422/**,422,422,422*/,500};
        API_ERROR_ERROR =new String[]{"InvalidCredentials","TheUsernameFieldIsRequired","TheUsernameFormatIsInvalid","TheSelectedUsernameDoesNotExist","ThePasswordFieldIsRequired","ServerError"};
        API_ERROR_MSG=new String[]{"密码错误","用户名不能为空","用户名格式错误","用户名不存在","密码不能为空","服务器错误"};
    }


    LoginBuild mBuild;
    public LoginTask(Activity context, ObserverListener ob) {
        super(context,ob);
        mBuild =new LoginBuild(context);
        mBuild.setUsername("15060568265");
        mBuild.setPassword("e10adc3949ba59abbe56e057f20f883e");
    }
    @Override
    public Observable<String> getObservable() {
        return  ((LoginApi)getBaseApi()).onPostman(getRequestBody());
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
        return false;
    }

    @Override
    public String getDataType() {
        return Msg.LOGIN;
    }
    @Override
    public BaseRequest requestClass() {
        return mBuild;
    }
    @Override
    public Type returnClass() {
        //如果是直接返回一个数组形式的，则可以使用return new TypeToken<ArrayList<Login>>() {}.getType();
        return Login.class;
    }
    @Override
    public Class<?> getBaseApiClass() {return LoginApi.class;}
    @Override
    public void onCompleted() {}
    @Override
    public void onError(Object object) {}
    @Override
    public void onSuccess(Object object) {}
    @Override
    public void onFail(Object object) {
        if(object!=null){
            Login login=(Login)object;
            if(login!=null&&login.getError()!=null){
                for(int i=0;i<API_ERROR_ERROR.length-1;i++){
                    if(login.getError().equals(API_ERROR_ERROR[i])){
                        Toast.makeText(context,API_ERROR_MSG[i],Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        }
    }
}

