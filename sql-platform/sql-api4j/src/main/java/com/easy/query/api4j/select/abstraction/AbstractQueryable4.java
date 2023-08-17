package com.easy.query.api4j.select.abstraction;

import com.easy.query.api4j.select.Queryable4;
import com.easy.query.api4j.select.extension.queryable4.override.AbstractOverrideQueryable4;
import com.easy.query.core.basic.api.select.ClientQueryable4;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable4.java
 * @Description: 文件说明
 * @Date: 2023/3/9 12:38
 */
public abstract class AbstractQueryable4<T1, T2, T3, T4> extends AbstractOverrideQueryable4<T1, T2, T3,T4> implements Queryable4<T1,T2,T3,T4> {
    protected final ClientQueryable4<T1, T2, T3, T4> entityQueryable4;


    public AbstractQueryable4(ClientQueryable4<T1, T2, T3, T4> entityQueryable4) {
        super(entityQueryable4);
        this.entityQueryable4 = entityQueryable4;
    }

    @Override
    public Queryable4<T1, T2, T3,T4> getQueryable4() {
        return this;
    }

    @Override
    public ClientQueryable4<T1, T2, T3,T4> getClientQueryable4() {
        return entityQueryable4;
    }
}
