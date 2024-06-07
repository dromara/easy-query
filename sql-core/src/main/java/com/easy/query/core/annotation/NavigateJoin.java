package com.easy.query.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2024/5/14 08:41
 * entity对象表不会生效
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface NavigateJoin {

    /**
     * 用来快速join relationType为ToOne的对象，比如userDTO下有这个{"address","name"}就是join获取 address然后把address.name赋值到这个对象
     * @return
     */
    String[] value() default {};
}
