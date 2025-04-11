package com.easy.query.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2025/4/1 22:44
 * 仅entity对象下的@Navigate生效
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface ForeignKey {

    /**
     * 逻辑外键 如果为逻辑外键则不会再code-first的时候生成外键
     * @return
     */
    boolean logic() default false;
}
