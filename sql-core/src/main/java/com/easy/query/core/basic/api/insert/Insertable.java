package com.easy.query.core.basic.api.insert;

import com.easy.query.core.basic.api.internal.BehaviorConfigure;
import com.easy.query.core.basic.api.internal.Interceptable;
import com.easy.query.core.basic.api.internal.SQLBatchExecute;
import com.easy.query.core.basic.api.internal.SQLExecuteRows;
import com.easy.query.core.basic.api.internal.SQLExecuteStrategy;
import com.easy.query.core.basic.api.internal.SQLOnDuplicateKeyIgnore;
import com.easy.query.core.basic.api.internal.TableReNameable;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.common.ToSQLResult;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: Insertable.java
 * @Description: 文件说明
 * @Date: 2023/2/20 08:48
 */
public interface Insertable<T, TChain> extends SQLExecuteRows,
        Interceptable<TChain>,
        TableReNameable<TChain>,
        SQLExecuteStrategy<TChain>,
        SQLOnDuplicateKeyIgnore<TChain>,
        BehaviorConfigure<TChain>,
        SQLBatchExecute<TChain> {
    TChain insert(T entity);

    TChain insert(Collection<T> entities);

    EntityInsertExpressionBuilder getEntityInsertExpressionBuilder();

    /**
     * @param fillAutoIncrement
     * @return
     */
    long executeRows(boolean fillAutoIncrement);

    @Override
    default long executeRows() {
        return executeRows(false);
    }

    String toSQL(T entity);

    String toSQL(T entity, ToSQLContext toSQLContext);

    /**
     * 传入生成sql的上下文用来获取生成sql后的表达式内部的参数
     *
     * @return 包含sql和sql结果比如参数
     */

    default ToSQLResult toSQLResult(T entity) {
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(getEntityInsertExpressionBuilder().getExpressionContext().getTableContext(), true);
        String sql = toSQL(entity, toSQLContext);
        return new ToSQLResult(sql, toSQLContext);
    }
}
