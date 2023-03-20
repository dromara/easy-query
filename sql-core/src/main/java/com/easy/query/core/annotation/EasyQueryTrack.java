package com.easy.query.core.annotation;

import java.lang.annotation.*;

/**
 * @FileName: EasyQueryTrack.java
 * @Description: 文件说明
 * @Date: 2023/3/20 21:00
 * @Created by xuejiaming
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
}
