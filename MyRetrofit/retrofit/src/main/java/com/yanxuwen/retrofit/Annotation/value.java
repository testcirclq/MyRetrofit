package com.yanxuwen.retrofit.Annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 作者：严旭文 on 2016/8/4 15:51
 * 邮箱：420255048@qq.com
 * 用于获取变量的值
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface value {
}
