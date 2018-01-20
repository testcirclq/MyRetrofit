package com.yanxuwen.myretrofit.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yanxuwen.myretrofit.R;
import com.yanxuwen.myretrofit.Utils.RequestUtils;
import com.yanxuwen.myretrofit.retrofit.Msg.Msg;
import com.yanxuwen.myretrofit.retrofit.model.Login.Login;
import com.yanxuwen.retrofit.Msg.ObserverListener;

public class MainActivity extends BaseActivity {
    TextView tv_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_text=(TextView)findViewById(R.id.tv_text);
    }
    public void onLogin(View v){
        mRequestUtils.requestLogin();
    }
    @Override
    /**
     * 请求数据返回结果
     *
     * @param status 请求成功还是失败
     * @param type
     * @param object 类型
     */
    public void onNotifyData(STATUS status, String type, Object object) {
        switch (type) {
            case Msg.LOGIN:
                if (isDataFail(status)) {
                    return;
                }
                if(object!=null){
                    Login mLogin=(Login)object;
                    if(mLogin!=null){
                        tv_text.setText("请求结果:"+mLogin.getAccess_token());
                    }
                }
                return;
            case Msg.TOKEN:
                if (isDataFail(status)) {
                    return;
                }
                if(object!=null){
                   //执行你的操作
                }
                return;
            case Msg.VERSION:
                if (isDataFail(status)) {
                    return;
                }
                if(object!=null){
                    //执行你的操作
                }
                return;
            default:
                break;
        }
        super.onNotifyData(status, type, object);
    }
}
