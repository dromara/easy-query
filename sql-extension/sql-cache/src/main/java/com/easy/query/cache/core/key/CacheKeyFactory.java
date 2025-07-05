package com.easy.query.cache.core.key;

import com.easy.query.core.basic.api.select.ClientQueryable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * create time 2025/6/2 21:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface CacheKeyFactory {
    @NotNull
    String getKey(@Nullable Map<String, Object> map);
    @NotNull
    String getKey(ClientQueryable<?> entityQueryable);
}
