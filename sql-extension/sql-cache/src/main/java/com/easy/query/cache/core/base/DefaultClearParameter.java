package com.easy.query.cache.core.base;

import java.time.LocalDateTime;

/**
 * create time 2024/1/26 09:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultClearParameter implements ClearParameter {
    private  String cacheId;
    private  String cacheIndexId;
    private  CacheMethodEnum cacheMethodEnum;
    private  LocalDateTime beforeTime;
    private  String entityName;

    public DefaultClearParameter(String cacheId, String cacheIndexId, CacheMethodEnum cacheMethodEnum, LocalDateTime beforeTime, String entityName){

        this.cacheId = cacheId;
        this.cacheIndexId = cacheIndexId;
        this.cacheMethodEnum = cacheMethodEnum;
        this.beforeTime = beforeTime;
        this.entityName = entityName;
    }
    public DefaultClearParameter(){}
    @Override
    public String getCacheId() {
        return cacheId;
    }

    @Override
    public String getCacheIndexId() {
        return cacheIndexId;
    }

    @Override
    public CacheMethodEnum getClearMethod() {
        return cacheMethodEnum;
    }

    @Override
    public LocalDateTime getBeforeTime() {
        return beforeTime;
    }

    @Override
    public String getEntityName() {
        return entityName;
    }

    public void setCacheId(String cacheId) {
        this.cacheId = cacheId;
    }

    public void setCacheIndexId(String cacheIndexId) {
        this.cacheIndexId = cacheIndexId;
    }

    public void setCacheMethodEnum(CacheMethodEnum cacheMethodEnum) {
        this.cacheMethodEnum = cacheMethodEnum;
    }

    public void setBeforeTime(LocalDateTime beforeTime) {
        this.beforeTime = beforeTime;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }
}
