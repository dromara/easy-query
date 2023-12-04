package com.easy.query.api.proxy.entity.select.abstraction;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/6/23 22:08
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractEntityQueryable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends AbstractEntityQueryable<T1Proxy,T1> {


    public AbstractEntityQueryable1(T1Proxy t1Proxy, ClientQueryable<T1> entityQueryable) {
        super(t1Proxy, entityQueryable);
    }
}
