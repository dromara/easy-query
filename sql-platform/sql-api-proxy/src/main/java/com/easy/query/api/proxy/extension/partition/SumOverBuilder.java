package com.easy.query.api.proxy.extension.partition;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.type.PartitionByTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.PartitionByTypeExpressionImpl;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2024/8/5 14:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class SumOverBuilder<TProperty> {
    private final PropTypeColumn<TProperty> overColumn;
    private final EntitySQLContext entitySQLContext;

    public SumOverBuilder(PropTypeColumn<TProperty> overColumn, EntitySQLContext entitySQLContext) {
        this.overColumn = overColumn;
        this.entitySQLContext = entitySQLContext;
    }

    public PartitionByTypeExpression<TProperty> partitionBy(PropTypeColumn<?>... columns) {
        if(EasyArrayUtil.isEmpty(columns)){
            throw new EasyQueryInvalidOperationException("sum over partition by empty");
        }
        return new PartitionByTypeExpressionImpl<>(entitySQLContext, columns[0].getTable(), null, f -> {
            return f.sumOver(x -> {
                PropTypeColumn.columnFuncSelector(x, this.overColumn);
                for (PropTypeColumn<?> column : columns) {
                    PropTypeColumn.columnFuncSelector(x, column);
                }
            });
        }, overColumn.getPropertyType());
    }
}
