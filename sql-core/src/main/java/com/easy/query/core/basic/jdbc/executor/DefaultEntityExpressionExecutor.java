package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.basic.jdbc.executor.internal.command.JdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.command.impl.DefaultExecuteBatchJdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.command.impl.DefaultExecuteUpdateJdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.command.impl.DefaultInsertJdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.command.impl.DefaultQueryJdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.DefaultJdbcResult;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.DefaultJdbcStreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcResult;
import com.easy.query.core.basic.jdbc.executor.internal.result.AffectedRowsExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.result.QueryExecuteResult;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.expression.executor.parser.EasyPrepareParser;
import com.easy.query.core.expression.executor.parser.EasyQueryPrepareParseResult;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.executor.parser.context.impl.EntityParseContextImpl;
import com.easy.query.core.expression.executor.parser.context.impl.InsertEntityParseContextImpl;
import com.easy.query.core.expression.executor.parser.context.impl.PredicateParseContextImpl;
import com.easy.query.core.expression.executor.parser.context.impl.QueryPredicateParseContextImpl;
import com.easy.query.core.expression.executor.query.ExecutionContextFactory;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityPredicateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.sharding.context.EasyStreamMergeContext;
import com.easy.query.core.sharding.context.EntityStreamMergeContext;
import com.easy.query.core.sharding.context.ShardingQueryEasyStreamMergeContext;

import java.sql.SQLException;
import java.util.List;

/**
 * create time 2023/4/16 22:47
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEntityExpressionExecutor implements EntityExpressionExecutor {
    private final EasyPrepareParser easyPrepareParser;
    private final ExecutionContextFactory executionContextFactory;

    public DefaultEntityExpressionExecutor(EasyPrepareParser easyPrepareParser, ExecutionContextFactory executionContextFactory) {

        this.easyPrepareParser = easyPrepareParser;
        this.executionContextFactory = executionContextFactory;
    }

    @Override
    public <TR> JdbcResult<TR> querySQLStreamResultSet(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, String sql, List<SQLParameter> sqlParameters) {

        ExecutionContext executionContext = executionContextFactory.createJdbcExecutionContext(sql, sqlParameters);
        JdbcCommand<QueryExecuteResult> command = getSQLQueryJdbcCommand(executorContext, executionContext);
        DefaultJdbcStreamResultSet<TR> jdbcStreamResultSet = new DefaultJdbcStreamResultSet<>(executorContext, resultMetadata, command);
        return new DefaultJdbcResult<>(jdbcStreamResultSet);
    }

    @Override
    public <TR> List<TR> query(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        JdbcResult<TR> jdbcResult = queryStreamResultSet(executorContext, resultMetadata, entityQueryExpressionBuilder);
        return jdbcResult.toList();
    }

    @Override
    public <TR> JdbcResult<TR> queryStreamResultSet(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        PrepareParseResult prepareParseResult = easyPrepareParser.parse(new QueryPredicateParseContextImpl(executorContext, entityQueryExpressionBuilder));
        ExecutionContext executionContext = executionContextFactory.createEntityExecutionContext(prepareParseResult);
        JdbcCommand<QueryExecuteResult> command = getQueryEntityJdbcCommand(executorContext, executionContext, (EasyQueryPrepareParseResult) prepareParseResult);
        DefaultJdbcStreamResultSet<TR> jdbcStreamResultSet = new DefaultJdbcStreamResultSet<>(executorContext, resultMetadata, command);
        return new DefaultJdbcResult<>(jdbcStreamResultSet);
    }

    @Override
    public <TR> List<TR> querySQL(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, String sql, List<SQLParameter> sqlParameters) {
        JdbcResult<TR> jdbcResult = querySQLStreamResultSet(executorContext, resultMetadata, sql, sqlParameters);
        return jdbcResult.toList();
    }

    @Override
    public long executeSQLRows(ExecutorContext executorContext, String sql, List<SQLParameter> sqlParameters) {

        ExecutionContext executionContext = executionContextFactory.createJdbcExecutionContext(sql, sqlParameters);

        try (JdbcCommand<AffectedRowsExecuteResult> command = getSQLExecuteUpdateJdbcCommand(executorContext, executionContext);
             AffectedRowsExecuteResult executeResult = command.execute()) {
            return executeResult.getRows();
        } catch (SQLException e) {
            throw new EasyQuerySQLCommandException(e);
        }
    }

    @Override
    public <T> long insert(ExecutorContext executorContext, List<T> entities, EntityInsertExpressionBuilder entityInsertExpressionBuilder, boolean fillAutoIncrement) {
        PrepareParseResult prepareParseResult = easyPrepareParser.parse(new InsertEntityParseContextImpl(executorContext, entityInsertExpressionBuilder, (List<Object>) entities, fillAutoIncrement));
        ExecutionContext executionContext = executionContextFactory.createEntityExecutionContext(prepareParseResult);

        try (JdbcCommand<AffectedRowsExecuteResult> command = getInsertJdbcCommand(executorContext, executionContext, prepareParseResult);
             AffectedRowsExecuteResult executeResult = command.execute()) {
            return executeResult.getRows();
        } catch (SQLException e) {
            throw new EasyQuerySQLCommandException(e);
        }
    }

    @Override
    public <T> long executeRows(ExecutorContext executorContext, EntityExpressionBuilder entityExpressionBuilder, List<T> entities) {
        PrepareParseResult prepareParseResult = easyPrepareParser.parse(new EntityParseContextImpl(executorContext, entityExpressionBuilder, (List<Object>) entities));
        ExecutionContext executionContext = executionContextFactory.createEntityExecutionContext(prepareParseResult);

        try (JdbcCommand<AffectedRowsExecuteResult> command = getExecuteBatchJdbcCommand(executorContext, executionContext, prepareParseResult);
             AffectedRowsExecuteResult executeResult = command.execute()) {
            return executeResult.getRows();
        } catch (SQLException e) {
            throw new EasyQuerySQLCommandException(e);
        }
    }

    @Override
    public <T> long executeRows(ExecutorContext executorContext, EntityPredicateExpressionBuilder entityPredicateExpressionBuilder) {
        PrepareParseResult prepareParseResult = easyPrepareParser.parse(new PredicateParseContextImpl(executorContext, entityPredicateExpressionBuilder));
        ExecutionContext executionContext = executionContextFactory.createEntityExecutionContext(prepareParseResult);

        try (JdbcCommand<AffectedRowsExecuteResult> command = getExecuteUpdateJdbcCommand(executorContext, executionContext, prepareParseResult);
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

    private JdbcCommand<QueryExecuteResult> getQueryEntityJdbcCommand(ExecutorContext executorContext, ExecutionContext executionContext, EasyQueryPrepareParseResult easyQueryPrepareParseResult) {
        return new DefaultQueryJdbcCommand(new ShardingQueryEasyStreamMergeContext(executorContext, executionContext, easyQueryPrepareParseResult));
    }

    private JdbcCommand<AffectedRowsExecuteResult> getInsertJdbcCommand(ExecutorContext executorContext, ExecutionContext executionContext, PrepareParseResult prepareParseResult) {
        return new DefaultInsertJdbcCommand(new EntityStreamMergeContext(executorContext, executionContext, prepareParseResult));
    }

    private JdbcCommand<AffectedRowsExecuteResult> getExecuteBatchJdbcCommand(ExecutorContext executorContext, ExecutionContext executionContext, PrepareParseResult prepareParseResult) {
        return new DefaultExecuteBatchJdbcCommand(new EntityStreamMergeContext(executorContext, executionContext, prepareParseResult));
    }

    private JdbcCommand<AffectedRowsExecuteResult> getExecuteUpdateJdbcCommand(ExecutorContext executorContext, ExecutionContext executionContext, PrepareParseResult prepareParseResult) {
        return new DefaultExecuteUpdateJdbcCommand(new EntityStreamMergeContext(executorContext, executionContext, prepareParseResult));
    }

}
