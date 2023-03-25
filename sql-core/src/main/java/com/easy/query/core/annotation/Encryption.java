package com.easy.query.core.annotation;

import com.easy.query.core.encryption.EasyEncryptionStrategy;

import java.lang.annotation.*;

/**
 * 表示数据库列是加密列
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface Encryption {
    Class<? extends EasyEncryptionStrategy> strategy();
}
