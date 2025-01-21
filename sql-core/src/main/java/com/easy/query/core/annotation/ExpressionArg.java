package com.easy.query.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2025/1/20 21:38
 * 文件说明
 *
 * @author xuejiaming
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpressionArg {
    String prop() default "";
    String val() default "";

    /**
     * 注解值和当前值一致则表示要忽略
     * @return
     */
    String ignoreVal() default "";

    /**
     * 值解析器
     * @return
     */
    Class<?> valType() default String.class;
}
