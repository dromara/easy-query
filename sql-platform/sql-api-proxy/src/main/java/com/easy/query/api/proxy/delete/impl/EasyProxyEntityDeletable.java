package com.easy.query.api.proxy.delete.impl;

import com.easy.query.api.proxy.delete.abstraction.AbstractProxyEntityDeletable;
import com.easy.query.core.basic.api.delete.ClientEntityDeletable;

/**
 * @author xuejiaming
 * @FileName: EasyEntityDeletable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:20
 */
public class EasyProxyEntityDeletable<T> extends AbstractProxyEntityDeletable<T> {
    public EasyProxyEntityDeletable(ClientEntityDeletable<T> entityObjectDeletable) {
        super(entityObjectDeletable);
    }
}
