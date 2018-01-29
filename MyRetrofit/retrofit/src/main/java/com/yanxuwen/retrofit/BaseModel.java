package com.yanxuwen.retrofit;

/**
 * Created by bbxpc on 2016/3/11.
 * 最基本的，每个接口都需要用到的，
 */
public class BaseModel {
    private String status;
    private String message;
    public String getStatus(){
        return status!=null?status:"";
    }
    public String getMessage(){
        return message;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
