package com.easy.query.core.annotation;

import com.easy.query.core.basic.plugin.encryption.EncryptionStrategy;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;


/**
 * 表示数据库列是加密列
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface Encryption {
    Class<? extends EncryptionStrategy> strategy();

    /**
     * 是否需要支持模糊查询
     * 默认支持等于匹配查询
     * @return
     */
    boolean supportQueryLike() default false;
}
