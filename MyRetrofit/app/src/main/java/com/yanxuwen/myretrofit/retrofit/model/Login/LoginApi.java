package com.yanxuwen.myretrofit.retrofit.model.Login;

import com.yanxuwen.myretrofit.Utils.ConfigUtils;
import com.yanxuwen.retrofit.Annotation.Description;
import com.yanxuwen.retrofit.Annotation.value;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by yanxuwen on 2017/7/21.
 */

@Description("登录")
public interface LoginApi   {
    @value
    final String url="api/member/login";
    @Headers({"Content-Type: application/json",
            "Accept:application/vnd.app.a1+json"})
    @POST(url)
    public abstract Observable<String> postman(@Body RequestBody description);
}
