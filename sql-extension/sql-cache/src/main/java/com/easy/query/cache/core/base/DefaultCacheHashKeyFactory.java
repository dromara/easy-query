package com.easy.query.cache.core.base;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Map;

/**
 * create time 2025/6/2 21:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultCacheHashKeyFactory implements CacheHashKeyFactory {
    @NotNull
    @Override
    public String getKey(@Nullable Map<String, Object> map) {
        if (map == null) {
            return "{}";
        }
        return toJson(map);
    }

    private String toJson(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            sb.append("\"").append(entry.getKey()).append("\":");
            Object value = entry.getValue();
            if (value instanceof Number || value instanceof Boolean) {
                sb.append(value);
            } else {
                sb.append("\"").append(value).append("\"");
            }
            if (iterator.hasNext()) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
