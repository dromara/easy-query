package com.easy.query.core.annotation;

import com.easy.query.core.basic.extension.navigate.NavigateValueSetter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2024/9/2 09:09
 * 文件说明
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface NavigateSetter {
    Class<? extends NavigateValueSetter> value();
}
