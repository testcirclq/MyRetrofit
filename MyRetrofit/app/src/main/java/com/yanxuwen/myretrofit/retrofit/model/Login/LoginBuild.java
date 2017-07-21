package com.yanxuwen.myretrofit.retrofit.model.Login;

import android.content.Context;

import com.yanxuwen.myretrofit.retrofit.MyBaseRequest;
import com.yanxuwen.retrofit.Annotation.Description;

/**
 * Created by yanxuwen on 2017/7/21.
 */
@Description("登录")
public class LoginBuild extends MyBaseRequest {
    /**
     * mobile :
     * password :
     */

    private String mobile;
    private String password;

    public LoginBuild(Context context) {
        super(context);
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
