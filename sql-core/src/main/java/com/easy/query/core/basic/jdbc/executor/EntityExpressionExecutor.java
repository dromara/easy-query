package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityPredicateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyInsertSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;

import java.util.List;

/**
 * create time 2023/4/16 22:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityExpressionExecutor {
    <TR> List<TR> query(ExecutorContext executorContext, Class<TR> clazz, EntityQueryExpressionBuilder entityQueryExpressionBuilder);

    <TR> List<TR> query(ExecutorContext executorContext, Class<TR> clazz, String sql, List<SQLParameter> sqlParameters);

    <T> long insert(ExecutorContext executorContext, List<T> entities, EntityInsertExpressionBuilder entityInsertExpressionBuilder, boolean fillAutoIncrement);

    <T> long executeRows(ExecutorContext executorContext, EntityExpressionBuilder entityExpressionBuilder, List<T> entities);

    <T> long executeRows(ExecutorContext executorContext, EntityPredicateExpressionBuilder entityPredicateExpressionBuilder);

    long executeRows(ExecutorContext executorContext, String sql, List<SQLParameter> sqlParameters);
}
