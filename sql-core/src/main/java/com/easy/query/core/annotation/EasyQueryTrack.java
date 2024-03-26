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
     * 这个属性没有存在的意义因为加了注解表示就是要开启的所以不会有加了注解还要关闭的情况并且因为无法动态控制所以这个属性的意义不大
     * 先保留后续删除
     */
    @Deprecated
    boolean enable() default true;

    /**
     * 空时表示全部的easy-query都需要追踪，需要指定就用逗号分割多个bean的name比如easyQuery,myEasyQuery或者db1,db2具体看配置
     */
    String tag() default "";
}
