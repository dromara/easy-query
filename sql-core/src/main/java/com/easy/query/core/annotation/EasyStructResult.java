package com.easy.query.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2024/3/1 09:35
 * 文件说明
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface EasyStructResult {
    /**
     * 从哪个实体传递过来
     * @return
     */
    String from();
}
