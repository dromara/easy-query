package org.jdqc.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * <pre>{@code
 * @Table("t_user")
 * public class User{}
 * }</pre>
 * @FileName: Table.java
 * @Description: 添加实体对象和表名称的对应
 * @Date: 2023/2/10 22:59
 * @Created by xuejiaming
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface Table {
    /**
     * 对应的表名称
     */
    String value() default "";

    /**
     * 数据库schema
     */
    String schema() default "";
}
