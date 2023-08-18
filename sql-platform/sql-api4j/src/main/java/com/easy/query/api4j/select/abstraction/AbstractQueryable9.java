package com.easy.query.api4j.select.abstraction;

import com.easy.query.api4j.select.Queryable9;
import com.easy.query.api4j.select.extension.queryable9.override.AbstractOverrideQueryable9;
import com.easy.query.core.basic.api.select.ClientQueryable9;

/**
 * create time 2023/8/18 13:04
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends AbstractOverrideQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> implements Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> {
    protected final ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9;

    public AbstractQueryable9(ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> entityQueryable9) {
        super(entityQueryable9);
        this.entityQueryable9 = entityQueryable9;
    }

    @Override
    public Queryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> getQueryable9() {
        return this;
    }

    @Override
    public ClientQueryable9<T1, T2, T3, T4, T5, T6, T7, T8, T9> getClientQueryable9() {
        return entityQueryable9;
    }
}
