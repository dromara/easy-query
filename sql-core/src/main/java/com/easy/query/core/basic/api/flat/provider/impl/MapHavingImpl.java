package com.easy.query.core.basic.api.flat.provider.impl;

import com.easy.query.core.basic.api.flat.provider.AbstractMapTable;
import com.easy.query.core.basic.api.flat.provider.MapHaving;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.builder.impl.AggregateFilterImpl;
import com.easy.query.core.expression.parser.core.base.WhereAggregatePredicate;
import com.easy.query.core.expression.parser.core.base.impl.WhereAggregatePredicateImpl;

/**
 * create time 2024/3/26 17:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class MapHavingImpl extends AbstractMapTable implements MapHaving {
    public MapHavingImpl(ClientQueryable<?> queryable) {
        super(queryable);
    }

    @Override
    public WhereAggregatePredicate<?> getHavingPredicate(int tableIndex) {
        return new WhereAggregatePredicateImpl<>(getTable(tableIndex), new AggregateFilterImpl(entityQueryExpressionBuilder.getExpressionContext(), entityQueryExpressionBuilder.getHaving()));
    }
}
