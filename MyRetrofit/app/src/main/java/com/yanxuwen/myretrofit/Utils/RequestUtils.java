package com.yanxuwen.myretrofit.Utils;

import android.app.Activity;

import com.yanxuwen.myretrofit.retrofit.model.Login.LoginTask;
import com.yanxuwen.myretrofit.retrofit.model.Token.TokenTask;
import com.yanxuwen.myretrofit.retrofit.model.Version.VersionTask;
import com.yanxuwen.retrofit.Msg.ObserverListener;

/**
 * Created by yanxuwen on 2017/7/20.
 */

public class RequestUtils {
    public Activity context;
    public ObserverListener ob;
    public RequestUtils(Activity context,ObserverListener ob){
        this.context=context;
        this.ob=ob;
    }
    /**
     * 请求Token
     */
    public void requestToken(){
        new TokenTask(context,ob).request();
    }

    /**
     * 获取版本
     */
    public  void requestVersion(){
        new VersionTask(context,ob).request();
    }
    /**
   登录
     */
    public  void requestLogin(){
        new LoginTask(context,ob).request();
    }
}
