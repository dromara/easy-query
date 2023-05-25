package com.easy.query.core.sharding.limit;

import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.configuration.EasyQueryShardingOption;
import com.easy.query.core.configuration.ShardingDataSource;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/5/25 13:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultMultiConnectionLimit implements MultiConnectionLimit {
    private static final Log log = LogFactory.getLog(DefaultMultiConnectionLimit.class);
    private final Map<String, Semaphore> throttlers = new ConcurrentHashMap<>();

    public DefaultMultiConnectionLimit(EasyQueryOption easyQueryOption) {
        initThrottlers(easyQueryOption);
    }

    private void initThrottlers(EasyQueryOption easyQueryOption) {
        int maxShardingQueryLimit = easyQueryOption.getMaxShardingQueryLimit();
        int defaultDataSourcePoolSize = easyQueryOption.getDefaultDataSourcePoolSize();
        addThrottlers(easyQueryOption.getDefaultDataSourceName(),maxShardingQueryLimit,defaultDataSourcePoolSize);
        EasyQueryShardingOption shardingOption = easyQueryOption.getShardingOption();
        if(shardingOption!=null){
            Set<ShardingDataSource> shardingDataSources = shardingOption.getShardingDataSources();
            if(shardingDataSources!=null){
                for (ShardingDataSource shardingDataSource : shardingDataSources) {
                    addThrottlers(shardingDataSource.getDataSourceName(),maxShardingQueryLimit,shardingDataSource.getDataSourcePoolSize());
                }
            }
        }
    }

    public void addThrottlers(String dataSourceName, int maxShardingQueryLimit, int dataSourcePoolSize) {
        throttlers.computeIfAbsent(dataSourceName, k -> {
            int permits = calcPermits(dataSourceName, maxShardingQueryLimit, dataSourcePoolSize);
            return new Semaphore(permits, true);
        });
    }

    private int calcPermits(String dataSourceName, int maxShardingQueryLimit, int dataSourcePoolSize) {
        if (dataSourcePoolSize < maxShardingQueryLimit) {
            log.warn("plz set dataSourcePoolSize value, dataSourceName:" + dataSourceName + ",dataSourcePoolSize:" + dataSourcePoolSize + " < maxShardingQueryLimit:" + maxShardingQueryLimit + ",should maxShardingQueryLimit < dataSourcePoolSize");
        }
        int permits = Math.floorDiv(dataSourcePoolSize, maxShardingQueryLimit);
        return Math.max(1, permits);
    }

    @Override
    public SemaphoreReleaseOnlyOnce tryAcquire(String dataSourceName, long timeout, TimeUnit unit) {
        Semaphore semaphore = throttlers.get(dataSourceName);
        if(semaphore==null){
            throw new EasyQueryInvalidOperationException("not found dataSourceName:"+dataSourceName+" throttler");
        }
        try {
            boolean acquire = semaphore.tryAcquire(timeout, unit);
            if(acquire){
               return new SemaphoreReleaseOnlyOnce(semaphore);
            }
            return  null;
        } catch (InterruptedException e) {
            throw new EasyQueryException(e);
        }
    }

    @Override
    public void release(SemaphoreReleaseOnlyOnce semaphoreReleaseOnlyOnce) {
        if(semaphoreReleaseOnlyOnce==null){
            return;
        }
        semaphoreReleaseOnlyOnce.release();
    }
}
