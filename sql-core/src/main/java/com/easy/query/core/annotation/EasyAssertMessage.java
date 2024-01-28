package com.easy.query.core.annotation;

import com.easy.query.core.util.EasyStringUtil;
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
     * 如果[firstNotNull,singleNotNull]为空(默认) 那么默认错误提示就是[notNull]的错误
     * @return
     */
    String notNull() default EasyUtil.NOT_NULL;

    /**
     * 当调用[firstNotNull]方法返回的结果集为空的时候，那么提示的错误信息
     * 默认为空显示[notNull]的错误
     * @return
     */
    String firstNotNull() default EasyStringUtil.EMPTY;

    /**
     * 当调用[singleNotNull]方法返回的结果集为空的时候，那么提示的错误信息
     * 默认为空显示[notNull]的错误
     * @return
     */
    String singleNotNull() default EasyStringUtil.EMPTY;

    /**
     * 当调用[singleMoreThan]方法返回的结果集大于一条的时候，那么提示的错误信息
     * @return
     */
    String singleMoreThan() default EasyUtil.SINGLE_MORE_THAN;
}
