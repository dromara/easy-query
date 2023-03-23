package com.easy.query.core.annotation;

import java.lang.annotation.*;


/**
 * @FileName: EasyWhereCondition.java
 * @Description: 文件说明
 * @Date: 2023/3/22 21:27
 * @Created by xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface EasyWhereCondition {
    /**
     * ObjectQueryMapping配置优先级大于entityClass
     * @return
     */
    Class<?> entityClass() default Object.class;
    boolean allowEmptyStrings() default false;

    /**
     * ObjectQueryMapping配置优先级大于属性propName
     * @return
     */
    String propName() default "";
    Condition type() default Condition.LIKE;
    enum Condition {
        //等于
        EQUAL
        //不等于
        ,NOT_EQUAL
        //大于
        ,GREATER_THAN
        //小于
        ,LESS_THAN
        //模糊
        ,LIKE
        //左模糊 like 'word%'
        ,LIKE_START
        //右模糊 like '%word'
        ,LIKE_END
        //大于等于
        ,GREATER_THAN_EQUAL
        //小于等于
        ,LESS_THAN_EQUAL
        //in
        ,IN
        //not in
        ,NOT_IN
        //大于
        ,RANGE_LEFT_OPEN
        //大于等于
        ,RANGE_LEFT_CLOSED
        //小于
        ,RANGE_RIGHT_OPEN
        //小于等于
        ,RANGE_RIGHT_CLOSED
    }
}