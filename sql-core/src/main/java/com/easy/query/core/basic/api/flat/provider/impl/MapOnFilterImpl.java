package com.easy.query.core.basic.api.flat.provider.impl;

import com.easy.query.core.basic.api.flat.provider.AbstractMapTable;
import com.easy.query.core.basic.api.flat.provider.MapFilter;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.impl.WherePredicateImpl;

/**
 * create time 2024/3/26 16:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class MapOnFilterImpl extends AbstractMapTable implements MapFilter {

    public MapOnFilterImpl(ClientQueryable<?> queryable){
        super(queryable);
    }

    @Override
    public WherePredicate<?> getWherePredicate(int tableIndex) {
        return new WherePredicateImpl<>(getTable(tableIndex), sqlExpressionProvider.getOnWhereFilterContext());
    }
}
