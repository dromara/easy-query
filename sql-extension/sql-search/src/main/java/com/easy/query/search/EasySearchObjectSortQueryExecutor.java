package com.easy.query.search;

import com.easy.query.core.api.dynamic.executor.sort.DefaultObjectSortQueryExecutor;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.search.executor.EasySearchQueryExecutor;

/**
 * create time 2025/6/25 21:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasySearchObjectSortQueryExecutor extends DefaultObjectSortQueryExecutor {
    private final EasySearchQueryExecutor easySearchQueryExecutor;

    public EasySearchObjectSortQueryExecutor(EasySearchQueryExecutor easySearchQueryExecutor) {
        this.easySearchQueryExecutor = easySearchQueryExecutor;
    }

    @Override
    public void orderByObject(ObjectSort objectSort, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        if (objectSort instanceof EasySearch) {
            easySearchQueryExecutor.orderByObject(objectSort, entityQueryExpressionBuilder);
            return;
        }

        super.orderByObject(objectSort, entityQueryExpressionBuilder);
    }
}
