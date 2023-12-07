package com.easy.query.api.proxy.entity.delete.impl;

import com.easy.query.api.proxy.entity.delete.abstraction.AbstractExpressionDeletable;
import com.easy.query.core.basic.api.delete.ClientExpressionDeletable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * @author xuejiaming
 * @FileName: EasyExpressionDeletable.java
 * @Description: 文件说明
 * @Date: 2023/3/6 13:21
 */
public class EasyExpressionDeletable<TProxy extends ProxyEntity<TProxy, T>, T> extends AbstractExpressionDeletable<TProxy,T> {
    public EasyExpressionDeletable(TProxy tProxy,ClientExpressionDeletable<T> expressionObjectDeletable) {
        super(tProxy,expressionObjectDeletable);
    }
}
