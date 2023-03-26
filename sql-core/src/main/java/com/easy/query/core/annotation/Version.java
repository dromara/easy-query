package com.easy.query.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

/**
 * 乐观锁
 * @FileName: Version.java
 * @Description: 文件说明
 * @Date: 2023/2/11 11:17
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface Version {
}
