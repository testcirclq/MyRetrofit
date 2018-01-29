package com.yanxuwen.retrofit.Annotation;

import com.yanxuwen.retrofit.api.ApiManager;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 作者：严旭文 on 2017/2/10 09:39
 * 邮箱：420255048@qq.com
 * 该注解是用来设置Http的请求类型，是http还是https的单双验证
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface HttpType {
    ApiManager.HttpType value();
}
