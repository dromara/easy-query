package com.easy.query.core.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

/**
 * @FileName: EasyQueryTrack.java
 * @Description: 文件说明
 * @Date: 2023/3/20 21:00
 * @author xuejiaming
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EasyQueryTrack {
    /**
     * 是否开启
     * @return
     */
    boolean enable() default true;
    String tag() default "";
}
