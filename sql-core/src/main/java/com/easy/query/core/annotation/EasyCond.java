package com.easy.query.core.annotation;


import com.easy.query.core.api.dynamic.executor.search.EasySortType;
import com.easy.query.core.api.dynamic.executor.search.op.Op;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EasyCond 注解用于在字段上指定查询条件和排序规则。
 * 它支持条件约束、排序方式、表的别名以及其他与查询相关的属性。
 *
 * <p>此注解通常与数据库查询相关，用于生成动态查询条件。</p>
 * <p>注解的默认值可以自动推导字段类型，但也可以手动配置。提供灵活的条件筛选、排序设置。</p>
 * <p>当注解在类型上时，仅支持value、table、tableAlias、tableIndex属性，用来设置默认的设置</p>
 * <strong>当table、tableAlias、tableIndex都未设置时，将使用EasySearch设置的动态表匹配器</strong>
 *
 * @author bkbits
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EasyCond {
    /**
     * @return 是否启用cond，默认为true
     */
    boolean value() default true;

    /**
     * @return 映射的参数名，默认为字段名
     */
    String name() default "";

    /**
     * @return 映射的表类属性名，默认取字段名
     */
    String property() default "";

    /**
     * @return 表别名，优先于tableIndex与table，当一个表类多次连接时使用
     */
    String tableAlias() default "";

    /**
     * @return 表类，优先于tableIndex
     */
    Class<?> table() default Object.class;

    /**
     * @return 表索引，指定表索引
     */
    int tableIndex() default -1;

    /**
     * @return 默认查询条件，默认根据字段类型决定，string为Like，date为RangeClosed，List为In，其它为Equals
     */
    Class<? extends Op> cond() default Op.class;

    /**
     * @return 条件约束，默认为空不约束
     */
    Class<? extends Op>[] condOnly() default {};

    /**
     * @return 默认排序方式，默认正序
     */
    EasySortType sort() default EasySortType.Asc;

    /**
     * @return 排序约束，默认不限制
     */
    EasySortType[] sortOnly() default {EasySortType.Asc, EasySortType.Desc};
}
