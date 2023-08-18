package com.easy.query.api4kt.select.abstraction;

import com.easy.query.api4kt.select.KtQueryable10;
import com.easy.query.api4kt.select.extension.queryable10.override.AbstractOverrideKtQueryable10;
import com.easy.query.core.basic.api.select.ClientQueryable10;

/**
 * create time 2023/8/18 13:04
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractKtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> extends AbstractOverrideKtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> implements KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> {
    protected final ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10;

    public AbstractKtQueryable10(ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> entityQueryable10) {
        super(entityQueryable10);
        this.entityQueryable10 = entityQueryable10;
    }

    @Override
    public KtQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> getQueryable10() {
        return this;
    }

    @Override
    public ClientQueryable10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> getClientQueryable10() {
        return entityQueryable10;
    }
}