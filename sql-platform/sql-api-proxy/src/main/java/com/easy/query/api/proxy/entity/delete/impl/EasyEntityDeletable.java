package com.easy.query.api.proxy.entity.delete.impl;

import com.easy.query.api.proxy.entity.delete.abstraction.AbstractEntityDeletable;
import com.easy.query.core.basic.api.delete.ClientEntityDeletable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * @author xuejiaming
 * @FileName: EasyEntityDeletable.java
 * @Description: 文件说明
 * create time 2023/3/6 13:20
 */
public class EasyEntityDeletable<TProxy extends ProxyEntity<TProxy, T>, T> extends AbstractEntityDeletable<TProxy, T> {
    public EasyEntityDeletable(TProxy tProxy,ClientEntityDeletable<T> entityObjectDeletable) {
        super(tProxy,entityObjectDeletable);
    }
}
