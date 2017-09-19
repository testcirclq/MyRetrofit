package com.yanxuwen.myretrofit.retrofit.model.Login;

import android.content.Context;

import com.yanxuwen.myretrofit.retrofit.MyBaseRequest;
import com.yanxuwen.retrofit.Annotation.Description;

/**
 * Created by yanxuwen on 2017/5/26.
 */
@Description("登录")
public class LoginBuild extends MyBaseRequest {
    /**
     * username :xxx
     * password :xxx
     */
    private String username;
    private String password;

    public LoginBuild(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
