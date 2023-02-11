package org.jdqc.core.annotation;

import java.lang.annotation.*;

/**
 * @FileName: InsertIgnore.java
 * @Description: 文件说明
 * @Date: 2023/2/11 21:38
 * @Created by xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface InsertIgnore {
}
