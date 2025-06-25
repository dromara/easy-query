package com.easy.query.search.executor;

import com.easy.query.core.api.dynamic.executor.query.WhereObjectQueryExecutor;
import com.easy.query.core.api.dynamic.executor.sort.ObjectSortQueryExecutor;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.core.AnyValueFilter;
import com.easy.query.core.expression.builder.impl.OrderSelectorImpl;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.util.EasySQLUtil;

/**
 * OrderSort执行器
 *
 * @author bkbits
 */
public interface EasySearchQueryExecutor {
    void whereObject(Object object,EntityQueryExpressionBuilder entityQueryExpressionBuilder);
    void orderByObject(ObjectSort objectSort, EntityQueryExpressionBuilder entityQueryExpressionBuilder);
}
