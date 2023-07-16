package com.easy.query.core.annotation;

import com.easy.query.core.enums.RelationTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2023/6/17 19:21
 * 文件说明
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface Navigate {
    /**
     * 关联属性
     * 空为主键
     * @return
     */
    String selfProperty() default "";
    /**
     * 关联关系
     */
    RelationTypeEnum value();
    /**
     * 目标关联属性
     * 空为主键
     */
    String targetProperty();

    /**
     * 多对多中间表
     * @return
     */
    Class<?> mappingClass() default Object.class;
    String selfMappingProperty() default "";
    String targetMappingProperty() default "";
}
