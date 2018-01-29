package com.yanxuwen.myretrofit.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yanxuwen.myretrofit.R;
import com.yanxuwen.myretrofit.Utils.RequestUtils;
import com.yanxuwen.retrofit.Msg.ObserverListener;
import com.yanxuwen.retrofit.Msg.Publisher;

/**
 * Created by yanxuwen on 2017/7/20.
 */

public class BaseActivity extends AppCompatActivity implements ObserverListener {
    public RequestUtils mRequestUtils;
    public Publisher mPublisher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPublisher=new Publisher();
        mRequestUtils=new RequestUtils(this,this);
        //添加观察者
        mPublisher.setOb(this);
    }
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除观察者
        mPublisher.removeOb();
    }
    /**
     * 判断数据请求是否是失败
     */
    public boolean isDataFail(STATUS status){
        if(status== STATUS.FAIL){
            Log.e("xxx","失败");
            return true;
        }else if(status==STATUS.ERROR){
            Log.e("xxx","网络异常");
            return true;
        }
        else if(status==STATUS.SPECIAL){
            return true;
        }
        return false;
    }
    @Override
    public void onNotifyData(STATUS status, String type, Object object) {
    }
}
