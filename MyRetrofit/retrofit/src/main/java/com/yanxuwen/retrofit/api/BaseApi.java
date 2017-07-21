package com.yanxuwen.retrofit.api;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 作者：严旭文 on 2016/7/4 11:44
 * 邮箱：420255048@qq.com
 */
public interface BaseApi {
    public   String url="";
    //////////////////////////////传递String类型,主要用于加密，如果没有加密，则使用下面的方法//////////////////////////////////////
    @POST(url)
   public Observable<String> onPostman(@Body RequestBody description);
    @POST(url)
    public Call<String> onPostman2(@Body RequestBody description);

//    ///////////////////////////////传递对象，retrofi会自动将对象转化为String类型，传递//////////////////////////////////////
//    @POST(url)
//    Observable<String> postObject(@Body TokenBuild mTokenBuild);
//    @POST(url)
//    Call<String> postObject2(@Body TokenBuild mTokenBuild);
}
