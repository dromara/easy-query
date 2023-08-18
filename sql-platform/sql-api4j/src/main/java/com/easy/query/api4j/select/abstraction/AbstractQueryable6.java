package com.easy.query.api4j.select.abstraction;

import com.easy.query.api4j.select.Queryable6;
import com.easy.query.api4j.select.extension.queryable6.override.AbstractOverrideQueryable6;
import com.easy.query.core.basic.api.select.ClientQueryable6;

/**
 * create time 2023/8/18 13:03
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractQueryable6<T1, T2, T3, T4, T5, T6> extends AbstractOverrideQueryable6<T1, T2, T3, T4, T5, T6> implements Queryable6<T1, T2, T3, T4, T5, T6> {
    protected final ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6;

    public AbstractQueryable6(ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6) {
        super(entityQueryable6);
        this.entityQueryable6 = entityQueryable6;
    }

    @Override
    public Queryable6<T1, T2, T3, T4, T5, T6> getQueryable6() {
        return this;
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5, T6> getClientQueryable6() {
        return entityQueryable6;
    }
}