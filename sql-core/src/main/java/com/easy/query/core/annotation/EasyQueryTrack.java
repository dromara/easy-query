package com.easy.query.core.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

/**
 * create time 2023/3/20 21:00
 * 追踪注解默认可以自行实现主要用来处理追踪更新差异生成set
 *
 * @author xuejiaming
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EasyQueryTrack {
    /**
     * 是否开启 默认开启
     */
    boolean enable() default true;

    /**
     * 空时表示全部的easy-query都需要追踪，需要指定就用逗号分割多个bean的name比如easyQuery,myEasyQuery或者db1,db2具体看配置
     */
    String tag() default "";
}
