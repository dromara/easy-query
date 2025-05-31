package com.easy.query.core.basic.api.select.impl;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.cte.CteTableAvailable;
import com.easy.query.core.basic.api.select.abstraction.AbstractClientQueryable1;
import org.jetbrains.annotations.NotNull;

/**
 * create time 2025/2/4 15:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyCteClientQueryable<T> extends AbstractClientQueryable1<T> implements CteTableAvailable {
    private final String cteTableName;

    public EasyCteClientQueryable(ClientQueryable<T> clientQueryable, String cteTableName) {
        super(clientQueryable.queryClass(), clientQueryable.getSQLEntityExpressionBuilder());
        this.cteTableName = cteTableName;
    }

    @NotNull
    @Override
    public ClientQueryable<T> cloneQueryable() {
        ClientQueryable<T> tClientQueryable = entityQueryExpressionBuilder.getRuntimeContext().getSQLClientApiFactory().cloneQueryable(this);
        return new EasyCteClientQueryable<>(tClientQueryable, cteTableName);
    }

    @Override
    public String getCteTableName() {
        return cteTableName;
    }

    @Override
    public Class<?> cteTableClass() {
        return queryClass();
    }
}
