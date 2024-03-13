package com.test.mutlidatasource.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2024/3/13 16:01
 * 文件说明
 *
 * @author xuejiaming
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DynamicDataSource {
    /**
     * 动态数据源
     * @return
     */
    String value() default "";
}
