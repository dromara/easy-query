package org.jdqc.core.annotation;
import java.lang.annotation.*;

/**
 * @FileName: Column.java
 * @Description: 文件说明
 * @Date: 2023/2/10 23:07
 * @Created by xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface Column {
    /**
     * 指定实体对象映射到数据库的名称
     */
    String value();

    /**
     * 是否忽略字段与数据库的映射关系
     */
    boolean ignore() default false;

    /**
     * 是否是主键
     */
    boolean primary() default false;

    /**
     * 是否是自增键
     */
    boolean increment() default false;

    /**
     * 乐观锁
     */
    boolean version() default false;
}
