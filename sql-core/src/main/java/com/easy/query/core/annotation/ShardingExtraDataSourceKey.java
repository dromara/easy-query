package com.easy.query.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2023/4/8 22:38
 * 用于表示对应字段是额外分库字段,默认参与update set列如果不需要可以添加 {@link UpdateIgnore}
 * 比如用户按创建时间分库 ds_2020,ds_2021那么如果你是雪花id,雪花id也可以解析出对应的时间
 * 所以也可以配置雪花id的精确定为到某哥数据源这个时候可以添加该注解来实现多字段分片
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface ShardingExtraDataSourceKey {
}
