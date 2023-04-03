package com.easy.query.core.basic.plugin.interceptor;

/**
 * create time 2023/4/3 10:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyInterceptorEntry {
    private final String name;
    private final boolean defaultEnable;

    public EasyInterceptorEntry(String name, boolean defaultEnable){

        this.name = name;
        this.defaultEnable = defaultEnable;
    }

    public String getName() {
        return name;
    }

    public boolean isDefaultEnable() {
        return defaultEnable;
    }
}
