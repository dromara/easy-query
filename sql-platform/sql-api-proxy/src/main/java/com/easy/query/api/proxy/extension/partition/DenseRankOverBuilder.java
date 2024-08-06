package com.easy.query.api.proxy.extension.partition;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparablePartitionByChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparablePartitionByChainExpressionImpl;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2024/8/4 14:35
 *
 * @author xuejiaming
 */
public class DenseRankOverBuilder {
    private final EntitySQLContext entitySQLContext;

    public DenseRankOverBuilder(EntitySQLContext entitySQLContext) {
        this.entitySQLContext = entitySQLContext;
    }

    public ColumnFunctionComparablePartitionByChainExpression<Long> partitionBy(PropTypeColumn<?>... columns) {
        if(EasyArrayUtil.isEmpty(columns)){
            throw new EasyQueryInvalidOperationException("dense_rank over partition by empty");
        }
        return new ColumnFunctionComparablePartitionByChainExpressionImpl<>(entitySQLContext, columns[0].getTable(), null, f -> {
            return f.denseRankNumberOver(x -> {
                for (PropTypeColumn<?> column : columns) {
                    PropTypeColumn.columnFuncSelector(x, column);
                }
            });
        }, Long.class);
    }
}
