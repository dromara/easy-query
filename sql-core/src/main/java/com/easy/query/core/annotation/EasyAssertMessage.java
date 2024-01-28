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
    String notNull() default EasyUtil.NOT_NULL;
    String firstNotNull() default EasyStringUtil.EMPTY;
    String singleNotNull() default EasyStringUtil.EMPTY;
    String singleMoreThan() default EasyUtil.SINGLE_MORE_THAN;
}
