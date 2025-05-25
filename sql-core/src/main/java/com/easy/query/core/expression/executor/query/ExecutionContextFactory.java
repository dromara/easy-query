package com.easy.query.core.expression.executor.query;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EntitySQLExpression;

import java.util.List;

/**
 * create time 2023/4/11 08:23
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ExecutionContextFactory {
    ExecutionContext createByPredicateExpression(EntitySQLExpression entitySQLExpression);
    ExecutionContext createExecutionContextByInsert(EntityInsertExpressionBuilder entityInsertExpressionBuilder, List<Object> entities, boolean fillAutoIncrement, ExecutorContext executorContext);
    ExecutionContext createExecutionContextByEntities(EntityExpressionBuilder entityExpressionBuilder, List<Object> entities, ExecutorContext executorContext);

    ExecutionContext createNativeJdbcExecutionContext(String sql, List<SQLParameter> parameters);
    ExecutionContext createJdbcExecutionContext(EntityQueryExpressionBuilder entityQueryExpressionBuilder, EntityQuerySQLExpression entityQuerySQLExpression);

    ExecutionContext createShardingExecutionContext(PrepareParseResult prepareParseResult);
}
