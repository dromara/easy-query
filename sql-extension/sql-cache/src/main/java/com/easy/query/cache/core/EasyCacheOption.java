package com.easy.query.cache.core;

/**
 * create time 2024/1/25 13:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyCacheOption {
    private  String keyPrefix="CACHE";
    private  String cacheIndex="INDEX";
    private  long timeoutMillisSeconds=1000 * 60 * 60*3;//3小时
    private  long valueNullTimeoutMillisSeconds=1000*10;//10秒
    public String getEntityKey(Class<?> entityClass){
        return this.keyPrefix+":"+entityClass.getSimpleName();
    }

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public long getTimeoutMillisSeconds() {
        return timeoutMillisSeconds;
    }

    public long getValueNullTimeoutMillisSeconds() {
        return valueNullTimeoutMillisSeconds;
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

    public void setTimeoutMillisSeconds(long timeoutMillisSeconds) {
        this.timeoutMillisSeconds = timeoutMillisSeconds;
    }

    public void setValueNullTimeoutMillisSeconds(long valueNullTimeoutMillisSeconds) {
        this.valueNullTimeoutMillisSeconds = valueNullTimeoutMillisSeconds;
    }
}