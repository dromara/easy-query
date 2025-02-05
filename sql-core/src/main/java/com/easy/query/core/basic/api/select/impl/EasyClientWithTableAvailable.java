package com.easy.query.core.basic.api.select.impl;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.WithTableAvailable;
import com.easy.query.core.basic.api.select.abstraction.AbstractClientQueryable1;

/**
 * create time 2025/2/4 15:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyClientWithTableAvailable<T> extends AbstractClientQueryable1<T> implements WithTableAvailable {
    private final String withTableName;

    public EasyClientWithTableAvailable(ClientQueryable<T> clientQueryable, String withTableName) {
        super(clientQueryable.queryClass(), clientQueryable.getSQLEntityExpressionBuilder());
        this.withTableName = withTableName;
    }

    @Override
    public ClientQueryable<T> cloneQueryable() {
        ClientQueryable<T> tClientQueryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().cloneQueryable(this);
        return new EasyClientWithTableAvailable<>(tClientQueryable, withTableName);
    }

    @Override
    public String getWithTableName() {
        return withTableName;
    }
}
