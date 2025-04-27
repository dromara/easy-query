package com.easy.query.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2025/4/26 16:58
 * 描述表的相关索引
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Repeatable(TableIndexes.class)
public @interface TableIndex {
    /**
     * 是否唯一索引
     * @return
     */
    boolean unique() default false;

    /**
     * 索引名称不设置默认为tableName_column1_column2_index
     * @return
     */
    String name() default "";

    /**
     * 索引字段填写实体属性名称
     * @return
     */
    String[] fields();

    /**
     * 索引默认采用asc排序,那些字段需要使用desc排序
     * @return
     */
    String[] descendingFields() default {};
}
