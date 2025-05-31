package com.easy.query.cache.core.base;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * create time 2024/1/25 20:46
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ClearParameter {
    String getCacheId();
    String getCacheIndexId();
    CacheMethodEnum getClearMethod();
    LocalDateTime getBeforeTime();
    String getTableName();
    Map<String,String> getParameters();

}
