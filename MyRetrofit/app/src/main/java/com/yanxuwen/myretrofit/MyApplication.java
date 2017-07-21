package com.yanxuwen.myretrofit;

import android.app.Application;

import com.yanxuwen.myretrofit.Utils.ConfigUtils;
import com.yanxuwen.retrofit.api.ApiManager;

import java.util.ArrayList;

/**
 * Created by yanxuwen on 2017/7/21.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ApiManager.init(ConfigUtils.BASE_URL);
        ApiManager.initHttpsType(ApiManager.HttpType.HTTP);
    }
}
