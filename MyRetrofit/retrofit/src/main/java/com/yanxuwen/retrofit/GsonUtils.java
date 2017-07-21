package com.yanxuwen.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yanxuwen.retrofit.JsonSerializer.DoubleDefault0Adapter;
import com.yanxuwen.retrofit.JsonSerializer.FloatDefault0Adapter;
import com.yanxuwen.retrofit.JsonSerializer.IntegerDefault0Adapter;
import com.yanxuwen.retrofit.JsonSerializer.LongDefault0Adapter;

import java.lang.reflect.Type;

/**
 * Created by yanxuwen on 2017/6/16.
 */

public class GsonUtils {
    /**
     * 转换为json
     */
    public static Object fromJson(String str, Type type){
        //处理gson字段为空，跟类型不匹配问题
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                .registerTypeAdapter(long.class, new LongDefault0Adapter())
                .registerTypeAdapter(Float.class, new FloatDefault0Adapter())
                .registerTypeAdapter(float.class, new FloatDefault0Adapter())
                .create();
        return gson.fromJson(str, type);
    }
}
