package org.easy.query.core.annotation;

import java.lang.annotation.*;

/**
 * @FileName: UpdateIgnore.java
 * @Description: 文件说明
 * @Date: 2023/2/11 21:38
 * @Created by xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface UpdateIgnore {
}
