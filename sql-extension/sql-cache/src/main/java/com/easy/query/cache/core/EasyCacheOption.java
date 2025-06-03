package com.easy.query.cache.core;

/**
 * create time 2024/1/25 13:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyCacheOption {
    private String keyPrefix = "CACHE";
    private String cacheIndex = "INDEX";
    private long expireMillisSeconds = 1000 * 60 * 60 * 3;//3小时
    private long valueNullExpireMillisSeconds = 1000 * 10;//10秒

    public String getEntityKey(Class<?> entityClass) {
        return this.keyPrefix + ":" + entityClass.getSimpleName();
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public long getExpireMillisSeconds() {
        return expireMillisSeconds;
    }

    public long getValueNullExpireMillisSeconds() {
        return valueNullExpireMillisSeconds;
    }

    public String getCacheIndex() {
        return cacheIndex;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public void setCacheIndex(String cacheIndex) {
        this.cacheIndex = cacheIndex;
    }

    public void setExpireMillisSeconds(long expireMillisSeconds) {
        this.expireMillisSeconds = expireMillisSeconds;
    }

    public void setValueNullExpireMillisSeconds(long valueNullExpireMillisSeconds) {
        this.valueNullExpireMillisSeconds = valueNullExpireMillisSeconds;
    }
}