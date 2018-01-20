package com.yanxuwen.myretrofit.retrofit.model.Version;

import com.yanxuwen.myretrofit.Utils.ConfigUtils;
import com.yanxuwen.retrofit.Annotation.Description;
import com.yanxuwen.retrofit.Annotation.value;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 作者：严旭文 on 2016/7/4.
 * 邮箱：420255048@qq.com
 * 更新接口，由于更新接口需要加密，暂时不能加密，则请求报错
 * //@Description("")尽量设置描述，以便方便查询已经log的打印
 */
@Description("版本检测")
public interface VersionApi  {
    @value
    public String baseUrl= ConfigUtils.BASE_URL;//如果不需要使用公用的baseurl,该接口需要独立的一个baseurl那么只要加上这句话即可，注意变量一定要这么写baseUrl
    @value
    final String url="version/latest";
    @GET(url)
    public abstract Observable<String> postman(@Query("version") String version);
}
