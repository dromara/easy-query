package com.easy.query.core.annotation;

import com.easy.query.core.enums.RelationMappingTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2024/5/14 08:41
 * entity对象表不会生效
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface NavigateFlat {
    RelationMappingTypeEnum value();
    //    /**
//     * 一对多比如SchoolClass映射多个schoolTeachers
//     * 那么当{@code basicMappingPropPath} 为 [schoolTeachers]且是基本类型那么会将schoolTeachers的主键进行赋值如果需要其他属性
//     * 则使用[schoolTeachers.name]schoolTeacherNames
//     * @return
//     */
    String[] mappingPath() default {};

    /**
     * 如果静态对象名称等于字段名称+_PATH,比如userName那么你编写
     * @return
     */
    String pathAlias() default "";
}
