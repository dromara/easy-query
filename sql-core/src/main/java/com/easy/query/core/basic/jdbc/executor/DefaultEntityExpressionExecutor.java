package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.basic.jdbc.executor.internal.command.JdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.command.impl.DefaultExecuteBatchJdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.command.impl.DefaultExecuteUpdateJdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.command.impl.DefaultInsertJdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.command.impl.DefaultQueryJdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.DefaultJdbcResult;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.DefaultJdbcStreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcResult;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.ResultSizeLimitJdbcResult;
import com.easy.query.core.basic.jdbc.executor.internal.result.AffectedRowsExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.result.QueryExecuteResult;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.expression.executor.parser.EasyPrepareParser;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.executor.query.ExecutionContextFactory;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityPredicateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityPredicateSQLExpression;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.sharding.context.EasyStreamMergeContext;
import com.easy.query.core.sharding.context.EntityStreamMergeContext;

import java.sql.SQLException;
import java.util.List;

/**
 * create time 2023/8/10 21:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEntityExpressionExecutor implements EntityExpressionExecutor {
    protected final ExecutionContextFactory executionContextFactory;

    public DefaultEntityExpressionExecutor(ExecutionContextFactory executionContextFactory) {
        this.executionContextFactory = executionContextFactory;
    }

    @Override
    public <TR> List<TR> query(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        JdbcResult<TR> jdbcResult = queryStreamResultSet(executorContext, resultMetadata, entityQueryExpressionBuilder);
        return jdbcResult.toList();
    }

    @Override
    public <TR> JdbcResult<TR> queryStreamResultSet(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        JdbcCommand<QueryExecuteResult> command = getJdbcCommand(executorContext, entityQueryExpressionBuilder);
        DefaultJdbcStreamResultSet<TR> jdbcStreamResultSet = new DefaultJdbcStreamResultSet<>(executorContext, resultMetadata, command);
        long resultSizeLimit = executorContext.getExpressionContext().getResultSizeLimit();
        return getJdbcResult(resultSizeLimit, jdbcStreamResultSet);
    }

    protected <TR> JdbcResult<TR> getJdbcResult(long resultSizeLimit, JdbcStreamResult<TR> jdbcStreamResult) {
        if (resultSizeLimit > 0) {
            return new ResultSizeLimitJdbcResult<>(resultSizeLimit, jdbcStreamResult);
        }
        return new DefaultJdbcResult<>(jdbcStreamResult);
    }

    private JdbcCommand<QueryExecuteResult> getJdbcCommand(ExecutorContext executorContext, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        EntityQuerySQLExpression entityQuerySQLExpression = entityQueryExpressionBuilder.toExpression();
        ExecutionContext executionContext = executionContextFactory.createJdbcExecutionContext(entityQueryExpressionBuilder, entityQuerySQLExpression);
        return getSQLQueryJdbcCommand(executorContext, executionContext);
    }

    @Override
    public <TR> List<TR> querySQL(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, String sql, List<SQLParameter> sqlParameters) {
        JdbcResult<TR> jdbcResult = querySQLStreamResultSet(executorContext, resultMetadata, sql, sqlParameters);
        return jdbcResult.toList();
    }

    @Override
    public <TR> JdbcResult<TR> querySQLStreamResultSet(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, String sql, List<SQLParameter> sqlParameters) {
        ExecutionContext executionContext = executionContextFactory.createNativeJdbcExecutionContext(sql, sqlParameters);
        JdbcCommand<QueryExecuteResult> command = getSQLQueryJdbcCommand(executorContext, executionContext);
        DefaultJdbcStreamResultSet<TR> jdbcStreamResultSet = new DefaultJdbcStreamResultSet<>(executorContext, resultMetadata, command);
        long resultSizeLimit = executorContext.getExpressionContext().getResultSizeLimit();
        return getJdbcResult(resultSizeLimit, jdbcStreamResultSet);
    }

    @Override
    public long executeSQLRows(ExecutorContext executorContext, String sql, List<SQLParameter> sqlParameters) {

        ExecutionContext executionContext = executionContextFactory.createNativeJdbcExecutionContext(sql, sqlParameters);
        return executeSQLCommand(executorContext, executionContext);
    }

    protected long executeSQLCommand(ExecutorContext executorContext, ExecutionContext executionContext) {

        try (JdbcCommand<AffectedRowsExecuteResult> command = getSQLExecuteUpdateJdbcCommand(executorContext, executionContext);
             AffectedRowsExecuteResult executeResult = command.execute()) {
            return executeResult.getRows();
        } catch (SQLException e) {
            throw new EasyQuerySQLCommandException(e);
        }
    }


    @Override
    public <T> long insert(ExecutorContext executorContext, List<T> entities, EntityInsertExpressionBuilder entityInsertExpressionBuilder, boolean fillAutoIncrement) {
        ExecutionContext executionContext = executionContextFactory.createExecutionContextByInsert(entityInsertExpressionBuilder, (List<Object>) entities, fillAutoIncrement, executorContext);
        return executeInsertCommand(executorContext, executionContext, false);
    }

    protected long executeInsertCommand(ExecutorContext executorContext, ExecutionContext executionContext, boolean sharding) {
        try (JdbcCommand<AffectedRowsExecuteResult> command = getInsertJdbcCommand(executorContext, executionContext, sharding);
             AffectedRowsExecuteResult executeResult = command.execute()) {
            return executeResult.getRows();
        } catch (SQLException e) {
            throw new EasyQuerySQLCommandException(e);
        }
    }

    @Override
    public <T> long executeRows(ExecutorContext executorContext, EntityExpressionBuilder entityExpressionBuilder, List<T> entities) {
        ExecutionContext executionContext = executionContextFactory.createExecutionContextByEntities(entityExpressionBuilder, (List<Object>) entities, executorContext);
        return executeEntitiesCommand(executorContext, executionContext, false);
    }

    protected long executeEntitiesCommand(ExecutorContext executorContext, ExecutionContext executionContext, boolean sharding) {
        try (JdbcCommand<AffectedRowsExecuteResult> command = getExecuteBatchJdbcCommand(executorContext, executionContext, sharding);
             AffectedRowsExecuteResult executeResult = command.execute()) {
            return executeResult.getRows();
        } catch (SQLException e) {
            throw new EasyQuerySQLCommandException(e);
        }
    }


    @Override
    public long executeRows(ExecutorContext executorContext, EntityPredicateExpressionBuilder entityPredicateExpressionBuilder,EntityPredicateSQLExpression entityPredicateSQLExpression) {
        ExecutionContext executionContext = executionContextFactory.createByPredicateExpression(entityPredicateSQLExpression);
        return executeExpressionCommand(executorContext, executionContext, false);
    }
    protected long executeExpressionCommand(ExecutorContext executorContext, ExecutionContext executionContext, boolean sharding) {

        try (JdbcCommand<AffectedRowsExecuteResult> command = getExecuteExpressionJdbcCommand(executorContext, executionContext, sharding);
             AffectedRowsExecuteResult executeResult = command.execute()) {
            return executeResult.getRows();
        } catch (SQLException e) {
            throw new EasyQuerySQLCommandException(e);
        }
    }


    private JdbcCommand<QueryExecuteResult> getSQLQueryJdbcCommand(ExecutorContext executorContext, ExecutionContext executionContext) {
        return new DefaultQueryJdbcCommand(new EasyStreamMergeContext(executorContext, executionContext));
    }

    private JdbcCommand<AffectedRowsExecuteResult> getSQLExecuteUpdateJdbcCommand(ExecutorContext executorContext, ExecutionContext executionContext) {
        return new DefaultExecuteUpdateJdbcCommand(new EasyStreamMergeContext(executorContext, executionContext));
    }

    private JdbcCommand<AffectedRowsExecuteResult> getInsertJdbcCommand(ExecutorContext executorContext, ExecutionContext executionContext, boolean sharding) {
        return new DefaultInsertJdbcCommand(new EntityStreamMergeContext(executorContext, executionContext, sharding));
    }

    private JdbcCommand<AffectedRowsExecuteResult> getExecuteBatchJdbcCommand(ExecutorContext executorContext, ExecutionContext executionContext, boolean sharding) {
        return new DefaultExecuteBatchJdbcCommand(new EntityStreamMergeContext(executorContext, executionContext, sharding));
    }

    private JdbcCommand<AffectedRowsExecuteResult> getExecuteExpressionJdbcCommand(ExecutorContext executorContext, ExecutionContext executionContext, boolean sharding) {
        return new DefaultExecuteUpdateJdbcCommand(new EntityStreamMergeContext(executorContext, executionContext, sharding));
    }
}
