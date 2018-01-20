package com.yanxuwen.myretrofit.retrofit.model.Version;

import android.content.Context;

import com.yanxuwen.myretrofit.retrofit.MyBaseRequest;

/**
 * 作者：严旭文 on 2016/7/4 17:28
 * 邮箱：420255048@qq.com
 */
public class VersionBuild  extends MyBaseRequest {
    /**
     * version :
     */

    private String version;

    public VersionBuild(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
