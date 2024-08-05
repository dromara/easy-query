package com.easy.query.api.proxy.extension.partition;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparablePartitionByChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparablePartitionByChainExpressionImpl;

/**
 * create time 2024/8/4 14:35
 *
 * @author xuejiaming
 */
public class RankOverBuilder {
    private final EntitySQLContext entitySQLContext;

    public RankOverBuilder(EntitySQLContext entitySQLContext) {
        this.entitySQLContext = entitySQLContext;
    }

    public <TProperty> ColumnFunctionComparablePartitionByChainExpression<Long> partitionBy(PropTypeColumn<TProperty> column){
        return new ColumnFunctionComparablePartitionByChainExpressionImpl<>(entitySQLContext, column.getTable(), null, f->{
            return f.rankNumberOver(x->{
                PropTypeColumn.columnFuncSelector(x,column);
            });
        } , Long.class);
    }
}
