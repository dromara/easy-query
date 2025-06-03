package com.easy.query.test.cache;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    public boolean isExpired() {
        return System.currentTimeMillis() > expire;
    }

    @JsonIgnore
    public boolean hasJson() {
        return json != null;
    }
}
