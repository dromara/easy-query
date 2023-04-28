package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.basic.jdbc.executor.internal.AffectedRowsExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.DefaultExecuteBatchEasyQueryJdbcExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.DefaultExecuteUpdateEasyQueryJdbcExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.DefaultInsertEasyQueryJdbcExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.DefaultQueryEasyQueryJdbcExecutor;
import com.easy.query.core.basic.jdbc.executor.internal.QueryExecuteResult;
import com.easy.query.core.basic.jdbc.executor.internal.abstraction.EasyQueryJdbcExecutor;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.executor.parser.EasyEntityPrepareParseResult;
import com.easy.query.core.expression.executor.parser.EasyInsertPrepareParseResult;
import com.easy.query.core.expression.executor.parser.EasyPredicatePrepareParseResult;
import com.easy.query.core.expression.executor.parser.EasyQueryPrepareParseResult;
import com.easy.query.core.expression.executor.parser.EasyPrepareParser;
import com.easy.query.core.expression.executor.parser.EntityPrepareParseResult;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.executor.parser.InsertPrepareParseResult;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.executor.query.ExecutionContextFactory;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityPredicateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.sharding.merge.EasyStreamMergeContext;
import com.easy.query.core.sharding.merge.EntityStreamMergeContext;
import com.easy.query.core.sharding.merge.ShardingQueryEasyStreamMergeContext;
import com.easy.query.core.util.StreamResultUtil;

import java.util.List;
import java.util.Set;

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
    public <TR> List<TR> querySql(ExecutorContext executorContext, Class<TR> clazz, String sql, List<SQLParameter> sqlParameters) {

        ExecutionContext executionContext = executionContextFactory.createJdbcExecutionContext(sql, sqlParameters);

        try (EasyQueryJdbcExecutor<QueryExecuteResult> easyQueryJDBCExecutor = getSqlQueryJdbcExecuteResult(executorContext, executionContext);
             QueryExecuteResult executeResult = easyQueryJDBCExecutor.execute()) {
            return StreamResultUtil.mapTo(executorContext, executeResult.getStreamResult(), clazz);
        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
    }
    @Override
    public long executeSqlRows(ExecutorContext executorContext, String sql, List<SQLParameter> sqlParameters) {

        ExecutionContext executionContext = executionContextFactory.createJdbcExecutionContext(sql, sqlParameters);

        try (EasyQueryJdbcExecutor<AffectedRowsExecuteResult> easyQueryJDBCExecutor = getSqlExecuteUpdateJdbcExecuteResult(executorContext, executionContext);
             AffectedRowsExecuteResult executeResult = easyQueryJDBCExecutor.execute()) {
            return executeResult.getRows();
        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
    }

    @Override
    public <TR> List<TR> query(ExecutorContext executorContext, Class<TR> clazz, EntityQueryExpressionBuilder entityQueryExpressionBuilder) {
        PrepareParseResult prepareParseResult = easyPrepareParser.parse(entityQueryExpressionBuilder);
        ExecutionContext executionContext = executionContextFactory.createEntityExecutionContext(prepareParseResult);

        try (EasyQueryJdbcExecutor<QueryExecuteResult> easyQueryJDBCExecutor = getQueryEntityJdbcExecuteResult(executorContext, executionContext,(EasyQueryPrepareParseResult)prepareParseResult);
             QueryExecuteResult executeResult = easyQueryJDBCExecutor.execute()) {
            return StreamResultUtil.mapTo(executorContext, executeResult.getStreamResult(), clazz);
        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
    }

    @Override
    public <T> long insert(ExecutorContext executorContext, List<T> entities, EntityInsertExpressionBuilder entityInsertExpressionBuilder, boolean fillAutoIncrement) {
        PrepareParseResult prepareParseResult = easyPrepareParser.parse(entityInsertExpressionBuilder,(List<Object>) entities,fillAutoIncrement);
        ExecutionContext executionContext = executionContextFactory.createEntityExecutionContext(prepareParseResult);

        try (EasyQueryJdbcExecutor<AffectedRowsExecuteResult> easyQueryJDBCExecutor = getInsertJdbcExecuteResult(executorContext, executionContext,prepareParseResult);
             AffectedRowsExecuteResult executeResult = easyQueryJDBCExecutor.execute()) {
            return executeResult.getRows();
        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
    }

    @Override
    public <T> long executeRows(ExecutorContext executorContext, EntityExpressionBuilder entityExpressionBuilder, List<T> entities) {
        PrepareParseResult prepareParseResult =  easyPrepareParser.parse(entityExpressionBuilder,(List<Object>) entities,false);
        ExecutionContext executionContext = executionContextFactory.createEntityExecutionContext(prepareParseResult);

        try (EasyQueryJdbcExecutor<AffectedRowsExecuteResult> easyQueryJDBCExecutor = getExecuteBatchJdbcExecuteResult(executorContext, executionContext,prepareParseResult);
             AffectedRowsExecuteResult executeResult = easyQueryJDBCExecutor.execute()) {
            return executeResult.getRows();
        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
    }

    @Override
    public <T> long executeRows(ExecutorContext executorContext, EntityPredicateExpressionBuilder entityPredicateExpressionBuilder) {
        PrepareParseResult prepareParseResult =   easyPrepareParser.parse(entityPredicateExpressionBuilder);
        ExecutionContext executionContext = executionContextFactory.createEntityExecutionContext(prepareParseResult);

        try (EasyQueryJdbcExecutor<AffectedRowsExecuteResult> easyQueryJDBCExecutor = getExecuteUpdateJdbcExecuteResult(executorContext, executionContext,prepareParseResult);
             AffectedRowsExecuteResult executeResult = easyQueryJDBCExecutor.execute()) {
            return executeResult.getRows();
        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
    }


    private EasyQueryJdbcExecutor<QueryExecuteResult> getSqlQueryJdbcExecuteResult(ExecutorContext executorContext, ExecutionContext executionContext) {
        return new DefaultQueryEasyQueryJdbcExecutor(new EasyStreamMergeContext(executorContext, executionContext));
    }
    private EasyQueryJdbcExecutor<AffectedRowsExecuteResult> getSqlExecuteUpdateJdbcExecuteResult(ExecutorContext executorContext, ExecutionContext executionContext) {
        return new DefaultExecuteUpdateEasyQueryJdbcExecutor(new EasyStreamMergeContext(executorContext, executionContext));
    }
    private EasyQueryJdbcExecutor<QueryExecuteResult> getQueryEntityJdbcExecuteResult(ExecutorContext executorContext, ExecutionContext executionContext,EasyQueryPrepareParseResult easyQueryPrepareParseResult) {
        return new DefaultQueryEasyQueryJdbcExecutor(new ShardingQueryEasyStreamMergeContext(executorContext, executionContext,easyQueryPrepareParseResult));
    }

    private EasyQueryJdbcExecutor<AffectedRowsExecuteResult> getInsertJdbcExecuteResult(ExecutorContext executorContext, ExecutionContext executionContext, PrepareParseResult prepareParseResult) {
        return new DefaultInsertEasyQueryJdbcExecutor(new EntityStreamMergeContext(executorContext, executionContext,prepareParseResult));
    }

    private EasyQueryJdbcExecutor<AffectedRowsExecuteResult> getExecuteBatchJdbcExecuteResult(ExecutorContext executorContext, ExecutionContext executionContext, PrepareParseResult prepareParseResult) {
        return new DefaultExecuteBatchEasyQueryJdbcExecutor(new EntityStreamMergeContext(executorContext, executionContext,prepareParseResult));
    }

    private EasyQueryJdbcExecutor<AffectedRowsExecuteResult> getExecuteUpdateJdbcExecuteResult(ExecutorContext executorContext, ExecutionContext executionContext,PrepareParseResult prepareParseResult) {
        return new DefaultExecuteUpdateEasyQueryJdbcExecutor(new EntityStreamMergeContext(executorContext, executionContext,prepareParseResult));
    }

}
