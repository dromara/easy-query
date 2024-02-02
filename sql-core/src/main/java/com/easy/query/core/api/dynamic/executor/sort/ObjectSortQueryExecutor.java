package com.easy.query.core.api.dynamic.executor.sort;

import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

/**
 * create time 2023/9/26 21:23
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ObjectSortQueryExecutor {
    void orderByObject(ObjectSort objectSort, EntityQueryExpressionBuilder entityQueryExpressionBuilder);
}
