package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.basic.jdbc.executor.internal.command.JdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.command.impl.DefaultQueryJdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.DefaultJdbcStreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcResult;
import com.easy.query.core.basic.jdbc.executor.internal.result.QueryExecuteResult;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.executor.parser.EasyPrepareParser;
import com.easy.query.core.expression.executor.parser.EasyQueryPrepareParseResult;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.executor.parser.context.impl.EntityParseContextImpl;
import com.easy.query.core.expression.executor.parser.context.impl.InsertEntityParseContextImpl;
import com.easy.query.core.expression.executor.parser.context.impl.PredicateParseContextImpl;
import com.easy.query.core.expression.executor.parser.context.impl.QueryPredicateParseContextImpl;
import com.easy.query.core.expression.executor.query.ExecutionContextFactory;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityPredicateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityPredicateSQLExpression;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.expression.visitor.ExpressionTableVisitor;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.sharding.context.ShardingQueryEasyStreamMergeContext;

import java.util.List;
import java.util.Set;

/**
 * create time 2023/8/10 21:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingEntityExpressionExecutor extends DefaultEntityExpressionExecutor {
    private final EasyPrepareParser easyPrepareParser;

    public ShardingEntityExpressionExecutor(EasyPrepareParser easyPrepareParser, ExecutionContextFactory executionContextFactory) {
        super(executionContextFactory);
        this.easyPrepareParser = easyPrepareParser;
    }

    @Override
    public <TR> JdbcResult<TR> queryStreamResultSet(ExecutorContext executorContext, ResultMetadata<TR> resultMetadata, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        boolean sharding = isSharding(entityQueryExpressionBuilder);
        if (!sharding) {
            return super.queryStreamResultSet(executorContext, resultMetadata, entityQueryExpressionBuilder);
        }
        JdbcCommand<QueryExecuteResult> command = getJdbcCommand(executorContext, entityQueryExpressionBuilder);
        DefaultJdbcStreamResultSet<TR> jdbcStreamResultSet = new DefaultJdbcStreamResultSet<>(executorContext, resultMetadata, command);
        long resultSizeLimit = executorContext.getExpressionContext().getResultSizeLimit();
        return getJdbcResult(resultSizeLimit, jdbcStreamResultSet);
    }

    protected boolean isSharding(EntityExpressionBuilder entityExpressionBuilder) {
        QueryRuntimeContext runtimeContext = entityExpressionBuilder.getRuntimeContext();
        EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
        ExpressionTableVisitor expressionTableVisitor = new ExpressionTableVisitor();
        entityExpressionBuilder.accept(expressionTableVisitor);
        Set<TableAvailable> tables = expressionTableVisitor.getTables();
        return tables.stream().anyMatch(table -> {
            return entityMetadataManager.isSharding(table.getEntityClass());
        });
    }

    private JdbcCommand<QueryExecuteResult> getJdbcCommand(ExecutorContext executorContext, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {

        EntityQuerySQLExpression entityQuerySQLExpression = entityQueryExpressionBuilder.toExpression();
        PrepareParseResult prepareParseResult = easyPrepareParser.parse(new QueryPredicateParseContextImpl(executorContext, entityQueryExpressionBuilder, entityQuerySQLExpression));
        ExecutionContext executionContext = executionContextFactory.createShardingExecutionContext(prepareParseResult);
        return getQueryEntityJdbcCommand(executorContext, executionContext, (EasyQueryPrepareParseResult) prepareParseResult);
    }


    @Override
    public <T> long insert(ExecutorContext executorContext, List<T> entities, EntityInsertExpressionBuilder entityInsertExpressionBuilder, boolean fillAutoIncrement) {
        boolean sharding = isSharding(entityInsertExpressionBuilder);
        if (!sharding) {
            return super.insert(executorContext, entities, entityInsertExpressionBuilder, fillAutoIncrement);
        }
        PrepareParseResult prepareParseResult = easyPrepareParser.parse(new InsertEntityParseContextImpl(executorContext, entityInsertExpressionBuilder, (List<Object>) entities, fillAutoIncrement));
        ExecutionContext executionContext = executionContextFactory.createShardingExecutionContext(prepareParseResult);
        return executeInsertCommand(executorContext, executionContext, true);
    }

    @Override
    public <T> long executeRows(ExecutorContext executorContext, EntityExpressionBuilder entityExpressionBuilder, List<T> entities) {
        boolean sharding = isSharding(entityExpressionBuilder);
        if (!sharding) {
            return super.executeRows(executorContext, entityExpressionBuilder, entities);
        }
        PrepareParseResult prepareParseResult = easyPrepareParser.parse(new EntityParseContextImpl(executorContext, entityExpressionBuilder, (List<Object>) entities));
        ExecutionContext executionContext = executionContextFactory.createShardingExecutionContext(prepareParseResult);
        return executeEntitiesCommand(executorContext, executionContext, true);
    }


    @Override
    public long executeRows(ExecutorContext executorContext, EntityPredicateExpressionBuilder entityPredicateExpressionBuilder) {
        boolean sharding = isSharding(entityPredicateExpressionBuilder);
        if (!sharding) {
            return super.executeRows(executorContext, entityPredicateExpressionBuilder);
        }
        EntityPredicateSQLExpression entityPredicateSQLExpression = entityPredicateExpressionBuilder.toExpression();
        PrepareParseResult prepareParseResult = easyPrepareParser.parse(new PredicateParseContextImpl(executorContext, entityPredicateExpressionBuilder, entityPredicateSQLExpression));
        ExecutionContext executionContext = executionContextFactory.createShardingExecutionContext(prepareParseResult);
        return executeExpressionCommand(executorContext, executionContext, true);
    }

    private JdbcCommand<QueryExecuteResult> getQueryEntityJdbcCommand(ExecutorContext executorContext, ExecutionContext executionContext, EasyQueryPrepareParseResult easyQueryPrepareParseResult) {
        return new DefaultQueryJdbcCommand(new ShardingQueryEasyStreamMergeContext(executorContext, executionContext, easyQueryPrepareParseResult));
    }
}
