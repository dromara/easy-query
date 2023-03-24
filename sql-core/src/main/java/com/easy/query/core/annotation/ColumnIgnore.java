package com.easy.query.core.annotation;
import java.lang.annotation.*;

/**
 * @FileName: Column.java
 * @Description: 文件说明
 * @Date: 2023/2/10 23:07
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface ColumnIgnore {
}
