package com.easy.query.core.sharding.limit;

import java.util.concurrent.TimeUnit;

/**
 * create time 2023/5/25 13:34
 * 文件说明
 *
 * @author xuejiaming
 */
public interface MultiConnectionLimit {
    /**
     * 添加节流器
     * @param dataSourceName
     * @param maxShardingQueryLimit
     * @param dataSourcePoolSize
     */
    void addThrottlers(String dataSourceName, int maxShardingQueryLimit, int dataSourcePoolSize);
    /**
     * 返回null就说明失败
     * @param timeout
     * @param unit
     * @return
     */
    SemaphoreReleaseOnlyOnce tryAcquire(String dataSourceName,long timeout, TimeUnit unit);
    void release(SemaphoreReleaseOnlyOnce semaphoreReleaseOnlyOnce);
}
