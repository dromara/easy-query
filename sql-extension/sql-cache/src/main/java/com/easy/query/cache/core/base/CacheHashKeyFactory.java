package com.easy.query.cache.core.base;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * create time 2025/6/2 21:29
 * 文件说明
 *
 * @author xuejiaming
 */
public interface CacheHashKeyFactory {
    @NotNull
    String getKey(@Nullable Map<String, Object> map);
}
