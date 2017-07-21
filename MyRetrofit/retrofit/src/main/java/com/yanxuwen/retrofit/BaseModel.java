package com.yanxuwen.retrofit;

/**
 * Created by bbxpc on 2016/3/11.
 * 最基本的，每个接口都需要用到的，
 */
public class BaseModel {
    public int status;
    public String message;
    public int getStatus(){
        return status;
    }
    public String getMessage(){
        return message;
    }
}
