package com.easy.query.core.annotation;

import com.easy.query.core.sharding.initializer.ShardingInitializer;
import com.easy.query.core.sharding.initializer.UnShardingInitializer;
import com.easy.query.core.util.EasyStringUtil;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * <pre>{@code
 * @Table("t_user")
 * public class User{}
 * }</pre>
 * @FileName: Table.java
 * @Description: 添加实体对象和表名称的对应
 * @Date: 2023/2/10 22:59
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
     * 数据库schema
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
}
