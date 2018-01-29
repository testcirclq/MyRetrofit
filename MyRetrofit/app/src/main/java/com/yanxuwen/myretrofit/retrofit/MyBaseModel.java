package com.yanxuwen.myretrofit.retrofit;

import com.yanxuwen.retrofit.BaseModel;

/**
 * Created by bbxpc on 2016/3/11.
 * 最基本的，每个接口都需要用到的，
 */
public class MyBaseModel extends BaseModel {
    /**
     * error : TheUsernameFieldIsRequired
     */

    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
