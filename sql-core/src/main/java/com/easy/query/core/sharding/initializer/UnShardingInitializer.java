package com.easy.query.core.sharding.initializer;

/**
 * create time 2023/4/24 09:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class UnShardingInitializer implements ShardingInitializer {

    public static final ShardingInitializer INSTANCE=new UnShardingInitializer();
    @Override
    public void initialize(ShardingEntityBuilder<?> builder) {

    }
}
