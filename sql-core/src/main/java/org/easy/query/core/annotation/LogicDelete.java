package org.easy.query.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @FileName: LogicDelete.java
 * @Description: 逻辑删除标识
 * @Date: 2023/2/26 22:47
 * @Created by xuejiaming
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.FIELD})
public @interface LogicDelete {
}
