package com.easy.query.api.proxy.extension.partition;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparablePartitionByChainExpression;
import com.easy.query.core.proxy.extension.functions.executor.impl.ColumnFunctionComparablePartitionByChainExpressionImpl;
import com.easy.query.core.util.EasyArrayUtil;

import java.math.BigDecimal;

/**
 * create time 2024/8/5 14:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class AvgOverBuilder {
    private final PropTypeColumn<?> overColumn;
    private final EntitySQLContext entitySQLContext;

    public AvgOverBuilder(PropTypeColumn<?> overColumn, EntitySQLContext entitySQLContext) {
        this.overColumn = overColumn;
        this.entitySQLContext = entitySQLContext;
    }

    public  ColumnFunctionComparablePartitionByChainExpression<BigDecimal> partitionBy(PropTypeColumn<?>... columns) {
        if(EasyArrayUtil.isEmpty(columns)){
            throw new EasyQueryInvalidOperationException("avg over partition by empty");
        }
        return new ColumnFunctionComparablePartitionByChainExpressionImpl<>(entitySQLContext, columns[0].getTable(), null, f -> {
            return f.avgOver(x -> {
                PropTypeColumn.columnFuncSelector(x, this.overColumn);
                for (PropTypeColumn<?> column : columns) {
                    PropTypeColumn.columnFuncSelector(x, column);
                }
            });
        }, BigDecimal.class);
    }
}
