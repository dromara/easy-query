package com.easy.query.core.basic.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * create time 2023/4/14 21:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyShardingThreadFactory implements ThreadFactory {
    private final AtomicLong threadIndex = new AtomicLong(0);
    private final String threadNamePrefix;
    private final boolean daemon;

    public EasyShardingThreadFactory(final String threadNamePrefix){
        this(threadNamePrefix,false);

    }
    public EasyShardingThreadFactory(final String threadNamePrefix, boolean daemon){

        this.threadNamePrefix = threadNamePrefix;
        this.daemon = daemon;
    }
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, threadNamePrefix + this.threadIndex.incrementAndGet());
        thread.setDaemon(daemon);
        return thread;
    }
}
