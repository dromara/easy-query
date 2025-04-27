package com.easy.query.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2025/4/27 14:49
 * 文件说明
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TableIndexes {
    /**
     * 用来处理一张表多个索引的情况
     * @return
     */
    TableIndex[] value();
}