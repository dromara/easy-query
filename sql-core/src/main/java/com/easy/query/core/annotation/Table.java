package com.easy.query.core.annotation;

import com.easy.query.core.util.StringUtil;

import java.lang.annotation.*;

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
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface Table {
    /**
     * 对应的表名称
     */
    String value() default StringUtil.EMPTY;

    /**
     * 数据库schema
     */
    String schema() default "";

    /**
     * 需要忽略的属性
     * @return
     */
    String[] ignoreProperties() default {};
}
