package com.yanxuwen.myretrofit.retrofit.model.Token;

import android.content.Context;

import com.yanxuwen.myretrofit.retrofit.MyBaseRequest;
import com.yanxuwen.retrofit.Annotation.Description;

/**
 * Token请求
 */
@Description("Token请求")
public class TokenBuild  extends MyBaseRequest {
    /**
     * phone :
     */

    private String phone;

    public TokenBuild(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
