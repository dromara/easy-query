package com.easy.query.core.basic.thread;

import com.easy.query.core.sharding.EasyShardingOption;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * create time 2023/4/14 21:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyShardingExecutorService implements EasyShardingExecutorService{
    private static final String DEFAULT_NAME_PREFIX="EasySharding_";
    private final ExecutorService shardingExecutor;
    public DefaultEasyShardingExecutorService(EasyShardingOption easyShardingOption){

        int executorSize = easyShardingOption.getExecutorSize();
        EasyShardingThreadFactory easyShardingThreadFactory = new EasyShardingThreadFactory(DEFAULT_NAME_PREFIX);
        shardingExecutor=executorSize<=0? Executors.newCachedThreadPool(easyShardingThreadFactory): Executors.newFixedThreadPool(executorSize,easyShardingThreadFactory);
    }
    @Override
    public ExecutorService getExecutorService() {
        return shardingExecutor;
    }
}
