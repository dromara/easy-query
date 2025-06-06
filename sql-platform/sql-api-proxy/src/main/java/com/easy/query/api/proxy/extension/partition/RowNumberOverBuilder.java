package com.easy.query.api.proxy.extension.partition;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.type.PartitionByTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.PartitionByTypeExpressionImpl;
import com.easy.query.core.util.EasyArrayUtil;


/**
 * create time 2024/8/4 14:35
 *
 * @author xuejiaming
 */
public class RowNumberOverBuilder {
    private final EntitySQLContext entitySQLContext;

    public RowNumberOverBuilder(EntitySQLContext entitySQLContext) {
        this.entitySQLContext = entitySQLContext;
    }

    public PartitionByTypeExpression<Long> partitionBy(PropTypeColumn<?>... columns) {
        if(EasyArrayUtil.isEmpty(columns)){
            throw new EasyQueryInvalidOperationException("row_number over partition by empty");
        }
        return new PartitionByTypeExpressionImpl<>(entitySQLContext, columns[0].getTable(), null, f -> {
            return f.rowNumberOver(x -> {
                for (PropTypeColumn<?> column : columns) {
                    PropTypeColumn.columnFuncSelector(x, column);
                }
            });
        }, Long.class);
    }
}
