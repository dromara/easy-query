package com.easy.query.cache.core.common;


/**
 * create time 2025/6/2 22:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class CacheItem {
    private String json;
    private long expire;

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public boolean cacheIsExpired() {
        return System.currentTimeMillis() > expire;
    }

    public boolean hasJson() {
        return json != null;
    }
}
