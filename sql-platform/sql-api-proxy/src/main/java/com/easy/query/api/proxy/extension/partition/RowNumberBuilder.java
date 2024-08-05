package com.easy.query.api.proxy.extension.partition;

import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparablePartitionByChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparablePartitionByChainExpressionImpl;


/**
 * create time 2024/8/4 14:35
 * RowNumberBuilderExpression
 *
 * @author xuejiaming
 */
public class RowNumberBuilder {
    private final EntitySQLContext entitySQLContext;

    public RowNumberBuilder(EntitySQLContext entitySQLContext) {
        this.entitySQLContext = entitySQLContext;
    }

    public <TProperty> ColumnFunctionComparablePartitionByChainExpression<Long> partitionBy(PropTypeColumn<TProperty> column){
        return partitionBy(column, Long.class);
    }
    public <TNumber,TProperty> ColumnFunctionComparablePartitionByChainExpression<TNumber> partitionBy(PropTypeColumn<TProperty> column,Class<TNumber> clazz){
        return new ColumnFunctionComparablePartitionByChainExpressionImpl<>(entitySQLContext, null, null, f->{
            return f.rowNumberOver(x->{
                PropTypeColumn.columnFuncSelector(x,column);
            });
        } , clazz);
    }
}
