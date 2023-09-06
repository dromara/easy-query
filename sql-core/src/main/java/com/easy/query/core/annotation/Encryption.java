package com.easy.query.core.annotation;

import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;


/**
 * create time 2023/3/22 21:27
 * 表示数据库列是加密列
 * 如果直接返回VO对象那么该对象也必须添加对应的注解
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface Encryption {
    /**
     * 加密策略自定义
     * @return
     */
    Class<? extends EncryptionStrategy> strategy();

    /**
     * 是否需要支持模糊查询
     * 默认支持等于匹配查询
     * true会将入参参数toString 进行分段加密 添加%号
     * @return
     */
    boolean supportQueryLike() default false;
}
