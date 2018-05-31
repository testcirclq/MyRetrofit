package com.yanxuwen.retrofit.api;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.Patterns;
import com.yanxuwen.retrofit.LogType;
import com.yanxuwen.retrofit.MySSLSocketFactory;
import com.yanxuwen.retrofit.converter.string.StringConverterFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：严旭文 on 2016/4/25 16:53
 * 邮箱：420255048@qq.com
 */
public class ApiManager {

    public enum HttpType{
        HTTP,
        /**https单向*/
        HTTPS_SINGLE,
        /**https双向*/
        HTTPS_DOUBLE,
        /**https信任所有证书，建议不要*/
        HTTPS_NULL
    }
    public static HttpType default_httpType= HttpType.HTTP;
    public static  String API_BASE_URL ="";
    private static Retrofit.Builder mBuilder;
    private static Retrofit.Builder mBuilder2;
    private static Retrofit.Builder mStringBuilder;
    private static Retrofit.Builder mStringBuilder2;
    public static OkHttpClient.Builder  getOkHttpClientBuilder(Context context, HttpType mHttpType, final Map<String,String> headers){
        OkHttpClient.Builder mBuilder = new OkHttpClient().newBuilder();
        mBuilder.readTimeout(30, TimeUnit.SECONDS);
        mBuilder.writeTimeout(10, TimeUnit.SECONDS);
        mBuilder.connectTimeout(10, TimeUnit.SECONDS);
        switch (mHttpType){
            case HTTP:break;
            case HTTPS_SINGLE:
                mBuilder.sslSocketFactory(MySSLSocketFactory.getSSLCertifcation2(context));//获取SSLSocketFactory
                mBuilder.hostnameVerifier(new UnSafeHostnameVerifier());//添加hostName验证器
                break;
            case HTTPS_DOUBLE:
                mBuilder.sslSocketFactory(MySSLSocketFactory.getSSLCertifcation(context));//获取SSLSocketFactory
                mBuilder.hostnameVerifier(new UnSafeHostnameVerifier());//添加hostName验证器
                break;
            case HTTPS_NULL:
                mBuilder.sslSocketFactory(MySSLSocketFactory.getSSLCertifcation3());//获取SSLSocketFactory
                mBuilder.hostnameVerifier(new UnSafeHostnameVerifier());//添加hostName验证器
                break;
        }
        mBuilder .addNetworkInterceptor(getNetWorkInterceptor(context,headers));
        mBuilder .addInterceptor(getInterceptor(context));
//        try {
//            if(headers!=null) {
//                mBuilder.addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) {
//                        Request request = chain.request();
//                        Request.Builder request_builder = request.newBuilder();
//                        Iterator iter = headers.entrySet().iterator();        //获取key和value的set
//                        while (iter.hasNext()) {
//                            Map.Entry entry = (Map.Entry) iter.next();        //把hashmap转成Iterator再迭代到entry
//                            String key = (String) entry.getKey();        //从entry获取key
//                            String val = (String) entry.getValue();    //从entry获取value
//                            request_builder.addHeader(key, val);//循环添加头部
//                        }
//                        Response mResponse = null;
//                        try {
//                            Request mRequest=request_builder.build();
//                            mResponse = chain.proceed(mRequest);
//                        }catch (Exception e){}
//                        return mResponse;
//                    }
//
//                });
//            }
//        }catch (Exception e){}
        return mBuilder;
    }



    /**
     * 设置连接器  设置缓存
     */
    public static Interceptor getNetWorkInterceptor(final Context context,final Map<String,String> headers){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response mResponse = null;
                if (isNetworkAvailable(context)) {
                    //添加头部
                    if(headers!=null){
                        Request.Builder request_builder = request.newBuilder();
                        Iterator iter = headers.entrySet().iterator();        //获取key和value的set
                        while (iter.hasNext()) {
                            Map.Entry entry = (Map.Entry) iter.next();        //把hashmap转成Iterator再迭代到entry
                            String key = (String) entry.getKey();        //从entry获取key
                            String val = (String) entry.getValue();    //从entry获取value
                            request_builder.addHeader(key, val);//循环添加头部
                        }
                        Request mRequest=request_builder.build();
                        mResponse = chain.proceed(mRequest);
                    }else{
                        mResponse = chain.proceed(request);
                    }
                    int maxAge = 0 * 60;
                    // 有网络时 设置缓存超时时间0个小时
                    mResponse.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    mResponse = chain.proceed(request);
                    // 无网络时，设置超时为1周
                    int maxStale = 60 * 60 * 24 * 7;
                    mResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("Pragma")
                            .build();
                }
                return mResponse;
            }
        };
    }
    /**
     * 设置返回数据的  Interceptor  判断网络   没网读取缓存
     */
    public static Interceptor getInterceptor(final Context context){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!isNetworkAvailable(context)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                return chain.proceed(request);
            }
        };
    }
    public static OkHttpClient getOkHttpClient(Context context,HttpType mHttpType, final Map<String,String> headers) {
        return  getOkHttpClientBuilder(context,mHttpType,headers).build();
    }
    public static OkHttpClient getOkHttpClient(Context context,HttpType mHttpType) {
        return  getOkHttpClientBuilder(context,mHttpType,null).build();
    }

    /**
     * 初始化双向认证
     * @param client_key          客户端证书key   [双向]
     * @param truststore_key      服务端证书key   [双向]
     * @param client_pwd          客户端密码      [双向]
     * @param truststore_pwd      服务密码        [双向]
     * @param client_key_type     客户端类型      [双向]
     * @param truststore_key_type 服务端类型      [双向]
     */
    public static void initSSLSocketFactory(String client_key,String truststore_key,String client_pwd,String truststore_pwd,String client_key_type,
                                     String truststore_key_type){
        MySSLSocketFactory.init(client_key,truststore_key,client_pwd,truststore_pwd,client_key_type,truststore_key_type);
    }

    /**
     * 初始化单向认证
     * @param single_cer  证书名字
     */
    public static void initSSLSocketFactory(String single_cer){
        MySSLSocketFactory.init(single_cer);
    }
    public static void initHttpsType(HttpType type){
        default_httpType=type;
    }
    /**
     * 初始化域名，注意一定要在所有的请求参数前面初始化，最好在Application的onCreate里初始化
     */
    public static void init(String baseUrl){
        API_BASE_URL=baseUrl;
        /**返回对象*/
         mBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create());
        /**返回对象并且使用rxjava技术*/
         mBuilder2 = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        /**返回String*/
         mStringBuilder = new Retrofit.Builder()
                .addConverterFactory(StringConverterFactory.create());
        /**返回json并且使用rxjava技术*/
         mStringBuilder2 = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(StringConverterFactory.create());
    }
    /**返回对象*/
    public static <T> T createServiceBuilder(Class<T> serviceClass,String baseUrl,Context context,HttpType mHttpType) {
        Retrofit retrofit=null;
        if (Patterns.WEB_URL.matcher(baseUrl).matches()) {
            mBuilder.baseUrl(baseUrl);
            retrofit = mBuilder.client(getOkHttpClient(context,mHttpType)).build();
        }else{
            Log.e(LogType.http,"非法URL");
        }
        if(retrofit==null)return null;
        return retrofit.create(serviceClass);
    }
    /**返回对象并且使用rxjava技术*/
    public static <T> T createServiceBuilder2(Class<T> serviceClass,String baseUrl,Context context,HttpType mHttpType) {
        Retrofit retrofit=null;
        if (Patterns.WEB_URL.matcher(baseUrl).matches()) {
            mBuilder2.baseUrl(baseUrl);
            retrofit= mBuilder2.client(getOkHttpClient(context,mHttpType)).build();
        }else{
            Log.e(LogType.http,"非法URL");
        }
        if(retrofit==null)return null;
        return retrofit.create(serviceClass);
    }
    /**返回String*/
    public static <T> T createServiceStringBuilder(Class<T> serviceClass,String baseUrl,Context context,HttpType mHttpType) {
        Retrofit retrofit=null;
        if (Patterns.WEB_URL.matcher(baseUrl).matches()) {
            mStringBuilder.baseUrl(baseUrl);
            retrofit = mStringBuilder.client(getOkHttpClient(context,mHttpType)).build();
        }else{
            Log.e(LogType.http,"非法URL");
        }
        if(retrofit==null)return null;
        return retrofit.create(serviceClass);
    }
    /**返回json并且使用rxjava技术，指定特定域名*/
    public static <T> T createServiceStringBuilder2(Class<T> serviceClass,String baseUrl,Context context,HttpType mHttpType, final Map<String,String> headers) {
        Retrofit retrofit = null;
        if (Patterns.WEB_URL.matcher(baseUrl).matches()) {
            mStringBuilder2.baseUrl(baseUrl);
            retrofit = mStringBuilder2.client(getOkHttpClient(context,mHttpType,headers)).build();
        } else{
            Log.e(LogType.http,"非法URL");
        }
        if(retrofit==null)return null;
        return retrofit.create(serviceClass);
    }
    /**返回json并且使用rxjava技术,默认地址为API_BASE_URL*/
    public static <T> T createServiceStringBuilder2(Class<T> serviceClass,Context context,HttpType mHttpType, final Map<String,String> headers) {
      return  createServiceStringBuilder2(serviceClass,API_BASE_URL,context,mHttpType,headers);
    }
    private static class UnSafeHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;//自行添加判断逻辑，true->Safe，false->unsafe
        }
    }



    /////////////////////////////////检查网络
    /**
     * 检测当的网络（WLAN、3G/2G）状态
     * @param context Context
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected())
            {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }
}
