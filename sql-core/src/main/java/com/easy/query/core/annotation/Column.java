package com.easy.query.core.annotation;
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
    String value() default "";
//
//    /**
//     * 该字段在数据库中是否为null，如果为null那么在update整个对象的时候
//     * 如果nullable为null就会更新为null
//     * 如果nullable不为null那么属性为null将不会更新
//     * 如果属性是PrimaryKey那么一定不为null
//     */
//    boolean nullable() default true;

//    /**
//     * 是否是主键
//     */
//    boolean primary() default false;
//
//    /**
//     * 是否是自增键
//     */
//    boolean increment() default false;
//
//    /**
//     * 乐观锁
//     */
//    boolean version() default false;
}
