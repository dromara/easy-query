package com.easy.query.api.proxy.client;

/**
 * create time 2026/4/11 14:49
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class BaseContext {
    protected final EasyEntityQuery easyEntityQuery;

    public BaseContext(EasyEntityQuery easyEntityQuery){
        this.easyEntityQuery = easyEntityQuery;
    }
}
