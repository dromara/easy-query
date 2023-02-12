package org.easy.query.core.annotation;

import java.lang.annotation.*;

/**
 * 乐观锁
 * @FileName: Version.java
 * @Description: 文件说明
 * @Date: 2023/2/11 11:17
 * @Created by xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface Version {
}
