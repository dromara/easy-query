package com.easy.query.api4j.select.abstraction;

import com.easy.query.api4j.select.Queryable8;
import com.easy.query.api4j.select.extension.queryable8.override.AbstractOverrideQueryable8;
import com.easy.query.core.basic.api.select.ClientQueryable8;

/**
 * create time 2023/8/18 13:03
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> extends AbstractOverrideQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> implements Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> {
    protected final ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8;

    public AbstractQueryable8(ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> entityQueryable8) {
        super(entityQueryable8);
        this.entityQueryable8 = entityQueryable8;
    }

    @Override
    public Queryable8<T1, T2, T3, T4, T5, T6, T7, T8> getQueryable8() {
        return this;
    }

    @Override
    public ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> getClientQueryable8() {
        return entityQueryable8;
    }
}
