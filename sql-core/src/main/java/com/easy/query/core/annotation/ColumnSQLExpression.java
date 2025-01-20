package com.easy.query.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2025/1/20 21:35
 * 文件说明
 *
 * @author xuejiaming
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnSQLExpression {
    /**
     * sql模板
     * @return
     */
    String sql();

    /**
     * 是否是真实咧
     * @return
     */
    boolean realColumn() default false;

    /**
     * 参数
     * @return
     */
    ExpressionArg[] args() default {};
}
