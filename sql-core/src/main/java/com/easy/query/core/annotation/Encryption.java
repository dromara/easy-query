package com.easy.query.core.annotation;

import java.lang.annotation.*;

/**
 * 表示数据库列是加密列
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface Encryption {
    int minWord() default 4;
}
