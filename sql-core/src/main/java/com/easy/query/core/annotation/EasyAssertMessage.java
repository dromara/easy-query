package com.easy.query.core.annotation;

import com.easy.query.core.util.EasyUtil;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2024/1/28 22:31
 * 文件说明
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface EasyAssertMessage {
    /**
     * 如果[findNotNull,firstNotNull,singleNotNull]为空(默认) 那么默认错误提示就是[notNull]的错误
     * @return
     */
    String value() default EasyUtil.NOT_NULL;
}
