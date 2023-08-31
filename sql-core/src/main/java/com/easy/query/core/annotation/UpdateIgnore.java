package com.easy.query.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

/**
 * 更新对象时会自动忽略该列,任何时候都不会更新set这个
 * 除非你手动指定更新使用expression update,或者track update在追踪模式下update变更了
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface UpdateIgnore {
    /**
     * 是否需要在追踪查询中加入update set
     * @return
     */
    boolean updateSetInTrackDiff() default false;
}
