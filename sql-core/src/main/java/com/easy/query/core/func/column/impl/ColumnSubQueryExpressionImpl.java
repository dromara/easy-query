package com.easy.query.core.func.column.impl;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.func.column.ColumnSubQueryExpression;

/**
 * create time 2023/12/21 23:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnSubQueryExpressionImpl implements ColumnSubQueryExpression {
    private final Query<?> query;

    public ColumnSubQueryExpressionImpl(Query<?> query){

        this.query = query;
    }
    @Override
    public Query<?> getQuery() {
        return query;
    }
}
