package com.easy.query.test.common;

import com.easy.query.core.basic.jdbc.executor.DefaultEntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.executor.parser.EasyPrepareParser;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.executor.query.ExecutionContextFactory;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityPredicateExpressionBuilder;

import java.util.List;

/**
 * create time 2025/4/18 10:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class MockEntityExpressionExecutor extends DefaultEntityExpressionExecutor {


    public MockEntityExpressionExecutor(EasyPrepareParser easyPrepareParser, ExecutionContextFactory executionContextFactory) {
        super(easyPrepareParser, executionContextFactory);
    }

    @Override
    public long executeRows(ExecutorContext executorContext, EntityPredicateExpressionBuilder entityPredicateExpressionBuilder) {
        return super.executeRows(executorContext, entityPredicateExpressionBuilder);
    }

    @Override
    public <T> long executeRows(ExecutorContext executorContext, EntityExpressionBuilder entityExpressionBuilder, List<T> entities) {
        return super.executeRows(executorContext, entityExpressionBuilder, entities);
    }

    @Override
    public <T> long insert(ExecutorContext executorContext, List<T> entities, EntityInsertExpressionBuilder entityInsertExpressionBuilder, boolean fillAutoIncrement) {
        return super.insert(executorContext, entities, entityInsertExpressionBuilder, fillAutoIncrement);
    }

    @Override
    public long executeSQLRows(ExecutorContext executorContext, String sql, List<SQLParameter> sqlParameters) {
        return super.executeSQLRows(executorContext, sql, sqlParameters);
    }
}
