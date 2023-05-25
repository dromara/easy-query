package com.easy.query.core.basic.thread;

import com.easy.query.core.configuration.EasyQueryOption;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/4/14 21:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEasyShardingExecutorService implements ShardingExecutorService {
    private static final String DEFAULT_NAME_PREFIX="SHARDING_EXECUTOR_";
    private final ExecutorService shardingExecutor;
    public DefaultEasyShardingExecutorService(EasyQueryOption easyQueryOption){
        shardingExecutor=createExecutorService(easyQueryOption);
    }

    private ExecutorService createExecutorService(EasyQueryOption easyQueryOption){
        int executorMaximumPoolSize = easyQueryOption.getExecutorMaximumPoolSize();
        EasyQueryThreadFactory easyShardingThreadFactory = new EasyQueryThreadFactory(DEFAULT_NAME_PREFIX);
        if(executorMaximumPoolSize<=0){
            return Executors.newCachedThreadPool(easyShardingThreadFactory);
        }else{
            int executorCorePoolSize = easyQueryOption.getExecutorCorePoolSize();
            int executorQueueSize = easyQueryOption.getExecutorQueueSize();
            return new ThreadPoolExecutor(executorCorePoolSize, executorMaximumPoolSize,
                    60000L, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<>(executorQueueSize),
                   easyShardingThreadFactory);
        }

    }
    @Override
    public ExecutorService getExecutorService() {
        return shardingExecutor;
    }
}
