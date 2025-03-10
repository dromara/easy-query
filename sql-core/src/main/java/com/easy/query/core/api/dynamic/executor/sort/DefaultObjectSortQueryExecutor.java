package com.easy.query.core.api.dynamic.executor.sort;

import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.dynamic.sort.internal.ObjectSortBuilderImpl;
import com.easy.query.core.api.dynamic.sort.internal.ObjectSortEntry;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.expression.builder.impl.OrderSelectorImpl;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.util.EasySQLUtil;

import java.util.Map;

/**
 * create time 2023/9/26 21:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultObjectSortQueryExecutor implements ObjectSortQueryExecutor {
    @Override
    public void orderByObject(ObjectSort objectSort, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        boolean strictMode = objectSort.useStrictMode();

        ObjectSortBuilderImpl orderByBuilder = new ObjectSortBuilderImpl();
        objectSort.configure(orderByBuilder);
        Map<String, ObjectSortEntry> orderProperties = orderByBuilder.build();
        if (!orderProperties.isEmpty()) {

            OrderSelectorImpl orderSelector = new OrderSelectorImpl(entityQueryExpressionBuilder,entityQueryExpressionBuilder.getRuntimeContext(), entityQueryExpressionBuilder.getExpressionContext(), entityQueryExpressionBuilder.getOrder());

            for (Map.Entry<String, ObjectSortEntry> sortKv : orderProperties.entrySet()) {
                String property = sortKv.getKey();
                ObjectSortEntry objectSortEntry = sortKv.getValue();
                int tableIndex = objectSortEntry.getTableIndex();
                if (tableIndex < 0 || tableIndex > entityQueryExpressionBuilder.getTables().size() - 1) {
                    if (strictMode) {
                        throw new EasyQueryOrderByInvalidOperationException(property, "table index:[" + tableIndex + "] not found in query context");
                    }
                    continue;
                }
                TableAvailable entityTable = entityQueryExpressionBuilder.getTable(tableIndex).getEntityTable();
                EasySQLUtil.dynamicOrderBy(orderSelector, entityTable, property, objectSortEntry.isAsc(), objectSortEntry.getOrderByMode(), strictMode);
            }

        }
        orderByBuilder.clear();
    }

}
