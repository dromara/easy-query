package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcResult;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityPredicateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

import java.util.List;

/**
 * create time 2023/4/16 22:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityExpressionExecutor {

   <TR> List<TR> query(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

   <TR> JdbcResult<TR> queryStreamResultSet(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, EntityQueryExpressionBuilder entityQueryExpressionBuilder);
   <TR> List<TR> querySQL(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, String sql, List<SQLParameter> sqlParameters);
    <TR> JdbcResult<TR> querySQLStreamResultSet(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, String sql, List<SQLParameter> sqlParameters);
    long executeSQLRows(ExecutorContext executorContext, String sql, List<SQLParameter> sqlParameters);

    <T> long insert(ExecutorContext executorContext, List<T> entities, EntityInsertExpressionBuilder entityInsertExpressionBuilder, boolean fillAutoIncrement);

    /**
     * update或者delete对象
     * @param executorContext
     * @param entityExpressionBuilder
     * @param entities
     * @return
     * @param <T>
     */
    <T> long executeRows(ExecutorContext executorContext, EntityExpressionBuilder entityExpressionBuilder, List<T> entities);

    /**
     * update或者delete表达式
     * @param executorContext
     * @param entityPredicateExpressionBuilder
     * @return
     * @param <T>
     */
    <T> long executeRows(ExecutorContext executorContext, EntityPredicateExpressionBuilder entityPredicateExpressionBuilder);

}
