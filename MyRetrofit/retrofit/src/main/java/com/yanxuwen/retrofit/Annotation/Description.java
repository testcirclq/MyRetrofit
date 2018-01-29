package com.yanxuwen.retrofit.Annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 作者：严旭文 on 2016/8/2 16:21
 * 邮箱：420255048@qq.com
 * 改注解是用于对类的描述，最主要是用途是log打印的时候随便打印出类的描述
 */
@Documented
@Target(TYPE)
@Retention(RUNTIME)
public @interface Description {
        String value() default "";
    }
