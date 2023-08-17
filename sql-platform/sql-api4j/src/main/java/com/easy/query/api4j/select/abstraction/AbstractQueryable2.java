package com.easy.query.api4j.select.abstraction;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.select.Queryable2;
import com.easy.query.api4j.select.Queryable3;
import com.easy.query.api4j.select.extension.queryable2.override.AbstractOverrideQueryable2;
import com.easy.query.api4j.select.impl.EasyQueryable;
import com.easy.query.api4j.select.impl.EasyQueryable3;
import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLColumnAsSelectorImpl;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable3;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLExpression3;

/**
 * @author xuejiaming
 * @FileName: AbstractQueryable2.java
 * @Description: 文件说明
 * @Date: 2023/2/6 23:43
 */
public abstract class AbstractQueryable2<T1, T2> extends AbstractOverrideQueryable2<T1,T2> implements Queryable2<T1, T2> {


    protected final ClientQueryable2<T1, T2> entityQueryable2;

    public AbstractQueryable2(ClientQueryable2<T1, T2> entityQueryable2) {
        super(entityQueryable2);
        this.entityQueryable2 = entityQueryable2;
    }

    @Override
    public ClientQueryable2<T1, T2> getClientQueryable2() {
        return entityQueryable2;
    }

    @Override
    public Queryable2<T1, T2> getQueryable2() {
        return this;
    }

}
