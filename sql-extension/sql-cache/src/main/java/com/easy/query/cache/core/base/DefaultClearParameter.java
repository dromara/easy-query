package com.easy.query.cache.core.base;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * create time 2024/1/26 09:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultClearParameter implements ClearParameter {
    private String cacheId;
    private String cacheIndexId;
    private CacheMethodEnum cacheMethodEnum;
    private LocalDateTime beforeTime;
    private String entityName;
    private Map<String, String> parameters;

    public DefaultClearParameter(String cacheId, String cacheIndexId, CacheMethodEnum cacheMethodEnum, LocalDateTime beforeTime, String entityName, Map<String, String> parameters) {
        Objects.requireNonNull(parameters, "parameters cannot be null");
        this.cacheId = cacheId;
        this.cacheIndexId = cacheIndexId;
        this.cacheMethodEnum = cacheMethodEnum;
        this.beforeTime = beforeTime;
        this.entityName = entityName;
        this.parameters = parameters;
    }

    public DefaultClearParameter() {
    }

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

    @Override
    public Map<String, String> getParameters() {
        return parameters;
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
