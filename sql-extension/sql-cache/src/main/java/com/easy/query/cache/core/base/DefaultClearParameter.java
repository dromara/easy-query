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
    private final String cacheId;
    private final String cacheIndexId;
    private final CacheMethodEnum cacheMethodEnum;
    private final LocalDateTime beforeTime;
    private final String tableName;
    private final Map<String, String> parameters;

    public DefaultClearParameter(String cacheId, String cacheIndexId, CacheMethodEnum cacheMethodEnum, LocalDateTime beforeTime, String tableName, Map<String, String> parameters) {
        Objects.requireNonNull(parameters, "parameters cannot be null");
        this.cacheId = cacheId;
        this.cacheIndexId = cacheIndexId;
        this.cacheMethodEnum = cacheMethodEnum;
        this.beforeTime = beforeTime;
        this.tableName = tableName;
        this.parameters = parameters;
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
    public String getTableName() {
        return tableName;
    }

    @Override
    public Map<String, String> getParameters() {
        return parameters;
    }
}
