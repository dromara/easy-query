package com.easy.query.core.sharding.initializer;

/**
 * create time 2023/5/15 12:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityShardingInitializer<T> extends ShardingInitializer{
    @Override
    default void initialize(ShardingEntityBuilder<?> builder){
        configure((ShardingEntityBuilder<T>)builder);
    }
    void configure(ShardingEntityBuilder<T> builder);
}
