package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.base.FillSelector;
import com.easy.query.core.metadata.FillParams;

/**
 * create time 2023/7/18 21:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class FillSelectorImpl implements FillSelector {
    private final QueryRuntimeContext runtimeContext;
    private final FillParams fillParams;

    public FillSelectorImpl(QueryRuntimeContext runtimeContext, FillParams fillParams){

        this.runtimeContext = runtimeContext;
        this.fillParams = fillParams;
    }
    @Override
    public <TREntity> ClientQueryable<TREntity> with(Class<TREntity> entityClass) {
        fillParams.setOriginalEntityClass(entityClass);
        ClientQueryable<TREntity> queryable = runtimeContext.getSQLClientApiFactory().createQueryable(entityClass, runtimeContext);

        return queryable.where(o->o.in(fillParams.getTargetProperty(),fillParams.getRelationIds()));

    }

    @Override
    public <TREntity> ClientQueryable<TREntity> adapter(Class<TREntity> entityClass, ClientQueryable<TREntity> queryable) {
        fillParams.setOriginalEntityClass(entityClass);
        return queryable.where(o->o.in(fillParams.getTargetProperty(),fillParams.getRelationIds()));
    }

    @Override
    public FillSelector consumeNull(boolean consumeNull) {
        fillParams.setConsumeNull(consumeNull);
        return this;
    }

}
