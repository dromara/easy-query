package com.easy.query.api4j.select.abstraction;

import com.easy.query.api4j.select.Queryable7;
import com.easy.query.api4j.select.extension.queryable7.override.AbstractOverrideQueryable7;
import com.easy.query.core.basic.api.select.ClientQueryable7;

/**
 * create time 2023/8/18 13:03
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractQueryable7<T1, T2, T3, T4, T5, T6, T7> extends AbstractOverrideQueryable7<T1, T2, T3, T4, T5, T6, T7> implements Queryable7<T1, T2, T3, T4, T5, T6, T7> {
    protected final ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7;

    public AbstractQueryable7(ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7) {
        super(entityQueryable7);
        this.entityQueryable7 = entityQueryable7;
    }

    @Override
    public Queryable7<T1, T2, T3, T4, T5, T6, T7> getQueryable7() {
        return this;
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> getClientQueryable7() {
        return entityQueryable7;
    }
}
