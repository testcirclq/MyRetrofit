package com.yanxuwen.myretrofit.retrofit.model.Token;

import android.app.Activity;

import com.yanxuwen.myretrofit.retrofit.Msg.Msg;
import com.yanxuwen.myretrofit.retrofit.MyBaseRequest;
import com.yanxuwen.myretrofit.retrofit.MyBaseTask;
import com.yanxuwen.retrofit.Msg.ObserverListener;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import rx.Observable;


/**
 * 作者：严旭文 on 2016/4/25 16:53
 * 邮箱：420255048@qq.com
 */
public class TokenTask extends MyBaseTask {
    TokenBuild mBuild;
    public TokenTask(Activity context,ObserverListener ob) {
        super(context,ob);
        //执行传递参数
        mBuild =new TokenBuild(context);
        mBuild.setAccess_token("QcTfVgAAAAAHALMfqOxKcR8pHctCebKcxktS");
        mBuild.setUid("917048770148592");
        mBuild.setPhone("15060568265");
    }
    @Override
    public Observable<String> getObservable() {
        return  ((TokenApi)getBaseApi()).postman(getRequestBody());
    }
    @Override
    public boolean isEncrypt() {
        return true;
    }
    @Override
    public boolean isShow() {return true;}
    @Override
    public boolean isReturnString() {return false;}

    @Override
    public boolean isToast() {
        return true;
    }

    @Override
    public String getDataType() {
        return Msg.TOKEN;
    }
    @Override
    public MyBaseRequest requestClass() {
        return mBuild;
    }
    @Override
    public Type returnClass() {return Token.class;}
    @Override
    public Class<?> getBaseApiClass() {return TokenApi.class;}
    @Override
    public void onCompleted() {}
    @Override
    public void onError(Object object) {}
    @Override
    public void onSuccess(Object object) {}
    @Override
    public void onFail(Object object) {}
    //如果有动态头部，则重载该方法，添加你需要的头部即可，
//    public Map<String, String> addHeaders() {
//        Map<String, String> map = new HashMap<>();
//        String apiKey = UserUtils.getInfo().getApiKey();
//        String signature = MD5.getSignature("video:fetch-recommend-video-list");
//        String caa = "Caa " + MD5.Base64(apiKey + ":" + signature + " " + MD5.getSignatureTime(), false);
//        map.put("Authorization", caa);
//        return map;
//    }
}
