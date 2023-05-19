package com.easy.query.core.expression.executor.query;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
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
import com.easy.query.core.sharding.EasyQueryDataSource;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRouteUnit;
import com.easy.query.core.sharding.rewrite.RewriteContext;
import com.easy.query.core.sharding.rewrite.RewriteContextFactory;
import com.easy.query.core.sharding.route.RouteContext;
import com.easy.query.core.sharding.route.RouteContextFactory;
import com.easy.query.core.util.EasyCollectionUtil;
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
    private final RouteContextFactory routeContextFactory;
    private final RewriteContextFactory rewriteContextFactory;
    private final EasyQueryDataSource easyDataSource;

    public DefaultExecutionContextFactory(RouteContextFactory routeContextFactory, RewriteContextFactory rewriteContextFactory, EasyQueryDataSource easyDataSource){
        this.routeContextFactory = routeContextFactory;
        this.rewriteContextFactory = rewriteContextFactory;

        this.easyDataSource = easyDataSource;
    }

    @Override
    public ExecutionContext createJdbcExecutionContext(String sql, List<SQLParameter> parameters) {
        ExecutionUnit executionUnit = new ExecutionUnit(easyDataSource.getDefaultDataSourceName(),new SQLRouteUnit( sql,parameters));
        return new ExecutionContext(Collections.singletonList(executionUnit),false,false,false,false);
    }
    @Override
    public ExecutionContext createEntityExecutionContext(PrepareParseResult prepareParseResult) {
//        NativeSqlQueryCompilerContext nativeSqlQueryCompilerContext = new NativeSqlQueryCompilerContext(prepareParseResult);
        //无需分片的情况下
        if(!prepareParseResult.isSharding()){
            if(prepareParseResult instanceof PredicatePrepareParseResult){
                return new PredicateExecutionCreator(easyDataSource.getDefaultDataSourceName(), prepareParseResult.getEntityExpressionBuilder().toExpression()).create();
            }
            if(prepareParseResult instanceof InsertPrepareParseResult){
                return new InsertExecutionCreator(easyDataSource.getDefaultDataSourceName(), (InsertPrepareParseResult) prepareParseResult).create();
            }
            if(prepareParseResult instanceof EntityPrepareParseResult){
                return new EntityExecutionCreator(easyDataSource.getDefaultDataSourceName(),(EntityPrepareParseResult)prepareParseResult).create();
            }
            throw new UnsupportedOperationException(EasyClassUtil.getInstanceSimpleName(prepareParseResult));
        }
        RouteContext routeContext = routeContextFactory.createRouteContext(prepareParseResult);
        RewriteContext rewriteContext = rewriteContextFactory.rewriteShardingExpression(prepareParseResult, routeContext);
        if(prepareParseResult instanceof PredicatePrepareParseResult){
            return new ShardingPredicateExecutionCreator(rewriteContext).create();
        }
        if(prepareParseResult instanceof EntityPrepareParseResult){
            return new ShardingEntityExecutionCreator(rewriteContext).create();
        }
        throw new UnsupportedOperationException();
    }
}
