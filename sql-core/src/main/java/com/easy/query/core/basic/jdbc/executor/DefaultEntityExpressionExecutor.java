package com.easy.query.core.basic.jdbc.executor;

import com.easy.query.core.basic.jdbc.executor.query.DefaultEasyQueryJDBCExecutor;
import com.easy.query.core.basic.jdbc.executor.query.DefaultStreamIterable;
import com.easy.query.core.basic.jdbc.executor.query.EasyQueryJDBCExecutor;
import com.easy.query.core.basic.jdbc.executor.query.StreamIterable;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.EasyPrepareParser;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.executor.query.ExecutionContextFactory;
import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.sharding.merge.DefaultStreamMergeContext;
import com.easy.query.core.sharding.merge.executor.internal.AffectedRowsExecuteResult;
import com.easy.query.core.sharding.merge.executor.internal.ExecuteResult;
import com.easy.query.core.sharding.merge.executor.internal.QueryExecuteResult;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.StreamResultUtil;

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
    public <TR> List<TR> query(ExecutorContext executorContext, Class<TR> clazz, String sql, List<SQLParameter> sqlParameters) {

        ExecutionContext executionContext = executionContextFactory.createQueryExecutionContext(sql, sqlParameters);

        try (EasyQueryJDBCExecutor easyQueryJDBCExecutor = getJDBCExecuteResult(executorContext, executionContext);
             ExecuteResult executeResult = easyQueryJDBCExecutor.execute()) {
            if (executeResult instanceof QueryExecuteResult) {
                QueryExecuteResult queryExecuteResult = (QueryExecuteResult) executeResult;
                return StreamResultUtil.mapTo(executorContext, queryExecuteResult.getStreamResult(), clazz);
            }
            throw new EasyQueryInvalidOperationException("jdbc executor execute error result type :"+ ClassUtil.getInstanceSimpleName(executeResult));
        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
//        try (StreamIterable queryIterable = getQueryStreamIterable(executorContext, executionContext)) {
//            StreamResult streamResult = queryIterable.getStreamResult();
//            return StreamResultUtil.mapTo(executorContext, streamResult, clazz);
//        } catch (Exception e) {
//            throw new EasyQueryException(e);
//        }
    }

    @Override
    public <TR> List<TR> query(ExecutorContext executorContext, Class<TR> clazz, EntityQueryExpression entityQueryExpression) {
        PrepareParseResult prepareParseResult = easyPrepareParser.parse(entityQueryExpression);
        ExecutionContext executionContext = executionContextFactory.createExecutionContext(prepareParseResult);

        try (EasyQueryJDBCExecutor easyQueryJDBCExecutor = getJDBCExecuteResult(executorContext, executionContext);
             ExecuteResult executeResult = easyQueryJDBCExecutor.execute()) {
            if (executeResult instanceof QueryExecuteResult) {
                QueryExecuteResult queryExecuteResult = (QueryExecuteResult) executeResult;
                return StreamResultUtil.mapTo(executorContext, queryExecuteResult.getStreamResult(), clazz);
            }
            if (executeResult instanceof AffectedRowsExecuteResult) {
            }
            throw new UnsupportedOperationException();

        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
//        try (StreamIterable queryIterable = getQueryStreamIterable(executorContext,executionContext)) {
//            StreamResult streamResult = queryIterable.getStreamResult();
//            return StreamResultUtil.mapTo(executorContext, streamResult, clazz);
//        } catch (Exception e) {
//            throw new EasyQueryException(e);
//        }
//        //需要分片
//        if (queryCompilerContext.isShardingQuery()) {//queryCompilerContext.isSingleShardingQuery()
//            MergeQueryCompilerContext mergeQueryCompilerContext = (MergeQueryCompilerContext) queryCompilerContext;
//            StreamResult streamResult = ShardingExecutor.<StreamResult>execute(null, null, mergeQueryCompilerContext.GetShardingRouteResult().getRouteUnits().stream());
//            throw new NotImplementedException();
//        } else {
//            //非分片处理
//            boolean tracking = entityQueryExpression.getExpressionContext().getBehavior().hasBehavior(EasyBehaviorEnum.USE_TRACKING);
//            String sql = entityQueryExpression.toSql();
//            return easyExecutor.query(ExecutorContext.create(entityQueryExpression.getRuntimeContext(), tracking), resultClass, sql, entityQueryExpression.getParameters());
//
//        }
    }

    private EasyQueryJDBCExecutor getJDBCExecuteResult(ExecutorContext executorContext, ExecutionContext executionContext) {
        return new DefaultEasyQueryJDBCExecutor(new DefaultStreamMergeContext(executorContext, executionContext));
    }

    private StreamIterable getQueryStreamIterable(ExecutorContext executorContext, ExecutionContext executionContext) {
//        if (queryCompilerContext.isShardingQuery()) {//queryCompilerContext.isSingleShardingQuery()
//            MergeQueryCompilerContext mergeQueryCompilerContext = (MergeQueryCompilerContext) queryCompilerContext;
//            StreamResult streamResult = ShardingExecutor.<StreamResult>execute(null, null, mergeQueryCompilerContext.GetShardingRouteResult().getRouteUnits().stream());
//            throw new NotImplementedException();
//        } else {
//            //非分片处理
//            queryCompilerContext
//
//        }

        return new DefaultStreamIterable(new DefaultStreamMergeContext(executorContext, executionContext));
    }
}
