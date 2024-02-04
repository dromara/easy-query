package com.easy.query.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2024/1/29 21:47
 * 提供注解允许插件自动修改类属性
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface EasyAnonymous {
    /**
     * 匿名对象所属模块
     * @return
     */
    String moduleName();

    /**
     * 匿名对象所属包名
     * @return
     */
    String packageName();

    /**
     * 是否使用file代理
     * @return
     */
    boolean entityFileProxy() default false;
}
