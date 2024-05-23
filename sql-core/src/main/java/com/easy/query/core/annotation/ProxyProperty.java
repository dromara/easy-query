package com.easy.query.core.annotation;

import com.easy.query.core.util.EasyStringUtil;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2023/2/10 23:07
 * 忽略当前属性到数据库的映射,约等于easy-query不知道当前属性的存在
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface ProxyProperty {
    /**
     * 别名 防止生成的代理对象属性和系统默认一致
     * @return
     */
    String value() default EasyStringUtil.EMPTY;

    /**
     * 是否生成any类型处理
     * @return
     */
    boolean generateAnyType() default false;
}
