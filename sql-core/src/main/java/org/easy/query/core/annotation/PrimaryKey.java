package org.easy.query.core.annotation;

import java.lang.annotation.*;

/**
 * @FileName: PrimaryKey.java
 * @Description: 文件说明
 * @Date: 2023/2/11 11:16
 * @Created by xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface PrimaryKey {
    /**
     * 是否是自增键
     */
    boolean increment() default false;
}
