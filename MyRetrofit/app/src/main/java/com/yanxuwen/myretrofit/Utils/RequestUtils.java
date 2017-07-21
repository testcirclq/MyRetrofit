package com.yanxuwen.myretrofit.Utils;

import android.app.Activity;

import com.yanxuwen.myretrofit.retrofit.model.Login.LoginTask;
import com.yanxuwen.myretrofit.retrofit.model.Token.TokenTask;
import com.yanxuwen.myretrofit.retrofit.model.Version.VersionTask;

/**
 * Created by yanxuwen on 2017/7/20.
 */

public class RequestUtils {
    public Activity context;
    public RequestUtils(Activity context){
        this.context=context;
    }
    /**
     * 请求Token
     */
    public void requestToken(){
        new TokenTask(context).request();
    }

    /**
     * 获取版本
     */
    public  void requestVersion(){
        new VersionTask(context).request();
    }
    /**
   登录
     */
    public  void requestLogin(){
        new LoginTask(context).request();
    }
}
