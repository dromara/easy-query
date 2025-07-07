package com.easy.query.cache.core.common;


import java.time.LocalDateTime;
import java.util.Objects;

/**
 * create time 2025/7/7 15:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class CacheDeleteEvent {
    private String cacheId;
    /**
     * {@link com.easy.query.cache.core.base.CacheMethodEnum}
     */
    private Integer cacheMethod;
    private LocalDateTime triggerTime;
    private LocalDateTime receivedTime;
    private String tableName;


    public String getCacheId() {
        return cacheId;
    }

    public void setCacheId(String cacheId) {
        this.cacheId = cacheId;
    }

    public Integer getCacheMethod() {
        return cacheMethod;
    }

    public void setCacheMethod(Integer cacheMethod) {
        this.cacheMethod = cacheMethod;
    }

    public LocalDateTime getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(LocalDateTime triggerTime) {
        this.triggerTime = triggerTime;
    }

    public LocalDateTime getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(LocalDateTime receivedTime) {
        this.receivedTime = receivedTime;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CacheDeleteEvent that = (CacheDeleteEvent) o;
        return Objects.equals(cacheId, that.cacheId) && Objects.equals(cacheMethod, that.cacheMethod) && Objects.equals(triggerTime, that.triggerTime) && Objects.equals(receivedTime, that.receivedTime) && Objects.equals(tableName, that.tableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cacheId, cacheMethod, triggerTime, receivedTime, tableName);
    }

    public static CacheDeleteEvent build(String cacheId, Integer cacheMethod, LocalDateTime triggerTime, LocalDateTime receivedTime, String tableName) {
        CacheDeleteEvent cacheDeleteEvent = new CacheDeleteEvent();
        cacheDeleteEvent.setCacheId(cacheId);
        cacheDeleteEvent.setCacheMethod(cacheMethod);
        cacheDeleteEvent.setTriggerTime(triggerTime);
        cacheDeleteEvent.setReceivedTime(receivedTime);
        cacheDeleteEvent.setTableName(tableName);
        return cacheDeleteEvent;
    }
}
