package com.easy.query.core.annotation;

import com.easy.query.core.sharding.initializer.ShardingInitializer;
import com.easy.query.core.sharding.initializer.UnShardingInitializer;
import com.easy.query.core.util.EasyStringUtil;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * <pre>{@code
 * @Table("t_user")
 * public class User{}
 * }</pre>
 * create time 2023/2/10 22:59
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface Table {
    /**
     * 对应的表名称
     */
    String value() default EasyStringUtil.EMPTY;

    /**
     * 数据库schema 支持多级比如a.b
     */
    String schema() default "";

    /**
     * 需要忽略的属性
     * @return
     */
    String[] ignoreProperties() default {};

    /**
     * 分片表名初始化器
     * @return
     */
    Class<? extends ShardingInitializer> shardingInitializer() default UnShardingInitializer.class;

    /**
     * 旧表名
     * @return
     */
    String oldName() default "";

    /**
     * 表注释
     * @return
     */
    String comment() default "";

    /**
     * 表名是否添加关键字
     * @return
     */
    boolean keyword() default true;
}
