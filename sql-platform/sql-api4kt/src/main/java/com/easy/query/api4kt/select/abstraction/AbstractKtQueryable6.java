package com.easy.query.api4kt.select.abstraction;

import com.easy.query.api4kt.select.KtQueryable6;
import com.easy.query.api4kt.select.extension.queryable6.override.AbstractOverrideKtQueryable6;
import com.easy.query.core.basic.api.select.ClientQueryable6;

/**
 * create time 2023/8/18 13:03
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractKtQueryable6<T1, T2, T3, T4, T5, T6> extends AbstractOverrideKtQueryable6<T1, T2, T3, T4, T5, T6> implements KtQueryable6<T1, T2, T3, T4, T5, T6> {
    protected final ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6;

    public AbstractKtQueryable6(ClientQueryable6<T1, T2, T3, T4, T5, T6> entityQueryable6) {
        super(entityQueryable6);
        this.entityQueryable6 = entityQueryable6;
    }

    @Override
    public KtQueryable6<T1, T2, T3, T4, T5, T6> getQueryable6() {
        return this;
    }

    @Override
    public ClientQueryable6<T1, T2, T3, T4, T5, T6> getClientQueryable6() {
        return entityQueryable6;
    }
}