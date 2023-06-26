package com.easy.query.api.proxy.delete.impl;

import com.easy.query.api.proxy.delete.abstraction.AbstractProxyExpressionDeletable;
import com.easy.query.core.basic.api.delete.ClientExpressionDeletable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * @author xuejiaming
 * @FileName: EasyExpressionDeletable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:21
 */
public class EasyProxyExpressionDeletable<TProxy extends ProxyEntity<TProxy, T>, T> extends AbstractProxyExpressionDeletable<TProxy,T> {
    public EasyProxyExpressionDeletable(TProxy proxy,ClientExpressionDeletable<T> expressionObjectDeletable) {
        super(proxy,expressionObjectDeletable);
    }
}
