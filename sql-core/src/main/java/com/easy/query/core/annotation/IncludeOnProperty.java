package com.easy.query.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2024/10/18 15:25
 * 文件说明
 *
 * @author xuejiaming
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(IncludeOnProperties.class)
public @interface IncludeOnProperty {
    /**
     * 属性名
     * @return
     */
    String name();

    /**
     * 这个值为读取到内存对象的属性的值然后进行toString操作得到的
     * @return
     */
    String value();

    /**
     * 是否匹配null值
     * @return
     */
    boolean matchNull() default false;

    /**
     * 值是不是只需要包含即可
     * @return
     */
    boolean valueContains() default false;
}
