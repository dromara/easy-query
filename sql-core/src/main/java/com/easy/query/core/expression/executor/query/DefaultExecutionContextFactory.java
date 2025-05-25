package com.easy.query.core.expression.executor.query;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRouteUnit;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.exception.EasyQueryShardingRouteExecuteMoreException;
import com.easy.query.core.expression.executor.parser.EntityPrepareParseResult;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.executor.parser.InsertPrepareParseResult;
import com.easy.query.core.expression.executor.parser.PredicatePrepareParseResult;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.executor.query.base.EntityExecutionCreator;
import com.easy.query.core.expression.executor.query.base.InsertExecutionCreator;
import com.easy.query.core.expression.executor.query.base.PredicateExecutionCreator;
import com.easy.query.core.expression.executor.query.base.ShardingEntityExecutionCreator;
import com.easy.query.core.expression.executor.query.base.ShardingPredicateExecutionCreator;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.expression.sql.expression.EntitySQLExpression;
import com.easy.query.core.sharding.EasyQueryDataSource;
import com.easy.query.core.sharding.rewrite.RewriteContext;
import com.easy.query.core.sharding.rewrite.RewriteContextFactory;
import com.easy.query.core.sharding.router.RouteContext;
import com.easy.query.core.sharding.router.RouteContextFactory;
import com.easy.query.core.util.EasyClassUtil;

import java.util.Collections;
import java.util.List;

/**
 * create time 2023/4/11 12:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultExecutionContextFactory implements ExecutionContextFactory {
    private final EasyQueryOption easyQueryOption;
    private final RouteContextFactory routeContextFactory;
    private final RewriteContextFactory rewriteContextFactory;
    private final EasyQueryDataSource easyDataSource;

    public DefaultExecutionContextFactory(EasyQueryOption easyQueryOption, RouteContextFactory routeContextFactory, RewriteContextFactory rewriteContextFactory, EasyQueryDataSource easyDataSource) {
        this.easyQueryOption = easyQueryOption;
        this.routeContextFactory = routeContextFactory;
        this.rewriteContextFactory = rewriteContextFactory;

        this.easyDataSource = easyDataSource;
    }

    @Override
    public ExecutionContext createByPredicateExpression(EntitySQLExpression entitySQLExpression) {
        return new PredicateExecutionCreator(easyDataSource.getDefaultDataSourceName(), entitySQLExpression).create();
    }

    @Override
    public ExecutionContext createExecutionContextByInsert(EntityInsertExpressionBuilder entityInsertExpressionBuilder, List<Object> entities, boolean fillAutoIncrement, ExecutorContext executorContext) {
        return new InsertExecutionCreator(easyDataSource.getDefaultDataSourceName(), entityInsertExpressionBuilder, entities, fillAutoIncrement, executorContext).create();
    }

    @Override
    public ExecutionContext createExecutionContextByEntities(EntityExpressionBuilder entityExpressionBuilder, List<Object> entities, ExecutorContext executorContext) {
        return new EntityExecutionCreator(easyDataSource.getDefaultDataSourceName(), entityExpressionBuilder, entities, executorContext).create();
    }

    @Override
    public ExecutionContext createNativeJdbcExecutionContext(String sql, List<SQLParameter> parameters) {
        ExecutionUnit executionUnit = new ExecutionUnit(easyDataSource.getDefaultDataSourceName(), new SQLRouteUnit(sql, parameters));
        return new ExecutionContext(Collections.singletonList(executionUnit), false, false, false, false);
    }

    @Override
    public ExecutionContext createJdbcExecutionContext(EntityQueryExpressionBuilder entityQueryExpressionBuilder, EntityQuerySQLExpression entityQuerySQLExpression) {
        ExecutionUnit executionUnit = new ExecutionUnit(easyDataSource.getDefaultDataSourceName(), new SQLRouteUnit(entityQuerySQLExpression, null, false, null));
        return new ExecutionContext(Collections.singletonList(executionUnit), false, false, false, false);
    }

    @Override
    public ExecutionContext createShardingExecutionContext(PrepareParseResult prepareParseResult) {
//        NativeSqlQueryCompilerContext nativeSqlQueryCompilerContext = new NativeSqlQueryCompilerContext(prepareParseResult);
        //无需分片的情况下
        RouteContext routeContext = routeContextFactory.createRouteContext(prepareParseResult);
        RewriteContext rewriteContext = rewriteContextFactory.rewriteShardingExpression(prepareParseResult, routeContext);
        if (prepareParseResult instanceof PredicatePrepareParseResult) {
            if (rewriteContext.getRewriteRouteUnits().size() >= easyQueryOption.getMaxShardingRouteCount()) {
                throw new EasyQueryShardingRouteExecuteMoreException("execute route size:" + rewriteContext.getRewriteRouteUnits().size());
            }
            return new ShardingPredicateExecutionCreator(rewriteContext).create();
        }
        if (prepareParseResult instanceof EntityPrepareParseResult) {
            return new ShardingEntityExecutionCreator(rewriteContext).create();
        }
        throw new UnsupportedOperationException();
    }
}
