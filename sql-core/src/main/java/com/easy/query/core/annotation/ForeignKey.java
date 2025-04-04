package com.easy.query.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2025/4/1 22:44
 * 表示当前属性为外键属性，仅entity生效
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface ForeignKey {
    /**
     * 外键名称
     * @return
     */
    String value() default "";

    /**
     * 逻辑外键 如果为逻辑外键则不会再code-first的时候生成外键
     * @return
     */
    boolean logic() default false;
}
