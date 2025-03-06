package com.easy.query.core.basic.api.flat.provider.impl;

import com.easy.query.core.basic.api.flat.provider.AbstractMapTable;
import com.easy.query.core.basic.api.flat.provider.MapOrderBy;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.builder.impl.OrderSelectorImpl;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import com.easy.query.core.expression.parser.core.base.impl.ColumnOrderSelectorImpl;

/**
 * create time 2024/3/26 17:09
 * 文件说明
 *
 * @author xuejiaming
 */
public class MapOrderByImpl extends AbstractMapTable implements MapOrderBy {

    private final boolean asc;

    public MapOrderByImpl(ClientQueryable<?> queryable, boolean asc) {
        super(queryable);
        this.asc = asc;
    }

    @Override
    public ColumnOrderSelector<?> getOrderBy(int tableIndex) {
        ColumnOrderSelectorImpl<?> order = new ColumnOrderSelectorImpl<>(getTable(tableIndex), new OrderSelectorImpl(entityQueryExpressionBuilder,entityQueryExpressionBuilder.getRuntimeContext(), entityQueryExpressionBuilder.getExpressionContext(), entityQueryExpressionBuilder.getOrder()));
        order.setAsc(asc);
        return order;
    }
}
