package com.easy.query.test.cache;

/**
 * create time 2025/6/3 08:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class CacheMultiOption {
    private final long expireMillisSeconds;
    private final int initialCapacity;
    private final int maximumSize;

    public CacheMultiOption(long expireMillisSeconds, int initialCapacity, int maximumSize){

        this.expireMillisSeconds = expireMillisSeconds;
        this.initialCapacity = initialCapacity;
        this.maximumSize = maximumSize;
    }

    public long getExpireMillisSeconds() {
        return expireMillisSeconds;
    }

    public int getInitialCapacity() {
        return initialCapacity;
    }

    public int getMaximumSize() {
        return maximumSize;
    }
}
