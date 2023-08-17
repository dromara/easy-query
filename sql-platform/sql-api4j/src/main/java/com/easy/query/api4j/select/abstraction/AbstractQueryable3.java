package com.easy.query.api4j.select.abstraction;

import com.easy.query.api4j.select.Queryable3;
import com.easy.query.api4j.select.extension.queryable3.override.AbstractOverrideQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable3;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable3.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:43
 */
public abstract class AbstractQueryable3<T1, T2, T3> extends AbstractOverrideQueryable3<T1, T2, T3> implements Queryable3<T1,T2,T3>{


    protected final ClientQueryable3<T1, T2, T3> entityQueryable3;

    public AbstractQueryable3(ClientQueryable3<T1, T2, T3> entityQueryable3) {
        super(entityQueryable3);
        this.entityQueryable3 = entityQueryable3;
    }

    @Override
    public Queryable3<T1, T2, T3> getQueryable3() {
        return this;
    }

    @Override
    public ClientQueryable3<T1, T2, T3> getClientQueryable3() {
        return entityQueryable3;
    }

}
