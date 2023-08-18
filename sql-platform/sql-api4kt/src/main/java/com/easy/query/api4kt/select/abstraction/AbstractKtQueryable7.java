package com.easy.query.api4kt.select.abstraction;

import com.easy.query.api4kt.select.KtQueryable7;
import com.easy.query.api4kt.select.extension.queryable7.override.AbstractOverrideKtQueryable7;
import com.easy.query.core.basic.api.select.ClientQueryable7;

/**
 * create time 2023/8/18 13:03
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractKtQueryable7<T1, T2, T3, T4, T5, T6, T7> extends AbstractOverrideKtQueryable7<T1, T2, T3, T4, T5, T6, T7> implements KtQueryable7<T1, T2, T3, T4, T5, T6, T7> {
    protected final ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7;

    public AbstractKtQueryable7(ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> entityQueryable7) {
        super(entityQueryable7);
        this.entityQueryable7 = entityQueryable7;
    }

    @Override
    public KtQueryable7<T1, T2, T3, T4, T5, T6, T7> getQueryable7() {
        return this;
    }

    @Override
    public ClientQueryable7<T1, T2, T3, T4, T5, T6, T7> getClientQueryable7() {
        return entityQueryable7;
    }
}
