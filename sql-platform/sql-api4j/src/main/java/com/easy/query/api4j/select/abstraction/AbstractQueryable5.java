package com.easy.query.api4j.select.abstraction;

import com.easy.query.api4j.select.Queryable5;
import com.easy.query.api4j.select.extension.queryable5.override.AbstractOverrideQueryable5;
import com.easy.query.core.basic.api.select.ClientQueryable5;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:38
 */
public abstract class AbstractQueryable5<T1, T2, T3, T4, T5> extends AbstractOverrideQueryable5<T1, T2, T3,T4, T5> implements Queryable5<T1,T2,T3,T4, T5> {
    protected final ClientQueryable5<T1, T2, T3, T4,T5> entityQueryable5;


    public AbstractQueryable5(ClientQueryable5<T1, T2, T3, T4,T5> entityQueryable5) {
        super(entityQueryable5);
        this.entityQueryable5 = entityQueryable5;
    }

    @Override
    public Queryable5<T1, T2, T3,T4,T5> getQueryable5() {
        return this;
    }

    @Override
    public ClientQueryable5<T1, T2, T3,T4,T5> getClientQueryable5() {
        return entityQueryable5;
    }
}
