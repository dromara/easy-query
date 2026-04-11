package com.easy.query.api.proxy.client;

import com.easy.query.core.proxy.DbSet;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2026/4/11 14:49
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class BaseDbSetContext {
    protected final BaseEntityClient baseEntityClient;

    public BaseDbSetContext(BaseEntityClient baseEntityClient){
        this.baseEntityClient = baseEntityClient;
    }

    protected <TProxy extends ProxyEntity<TProxy, T>, T> DbSet<TProxy, T> createDbSet(TProxy tProxy){
        return baseEntityClient.createDbSet(tProxy);
    }
}
