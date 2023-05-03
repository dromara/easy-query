package com.easy.query.core.expression.executor.query;

import com.easy.query.core.basic.jdbc.parameter.DefaultSqlParameterCollector;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
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
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.sharding.EasyDataSource;
import com.easy.query.core.sharding.merge.executor.common.ExecutionUnit;
import com.easy.query.core.sharding.merge.executor.common.SqlUnit;
import com.easy.query.core.sharding.rewrite.RewriteContextFactory;
import com.easy.query.core.sharding.route.RouteContext;
import com.easy.query.core.sharding.route.RouteContextFactory;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.ClassUtil;

import java.util.Collection;
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
    private final EasyDataSource easyDataSource;

    public DefaultExecutionContextFactory(RouteContextFactory routeContextFactory, RewriteContextFactory rewriteContextFactory, EasyDataSource easyDataSource){
        this.routeContextFactory = routeContextFactory;
        this.rewriteContextFactory = rewriteContextFactory;

        this.easyDataSource = easyDataSource;
    }

    @Override
    public ExecutionContext createJdbcExecutionContext(String sql, List<SQLParameter> parameters) {
        ExecutionUnit executionUnit = new ExecutionUnit(easyDataSource.getDefaultDataSourceName(), new SqlUnit(sql,parameters));
        return new ExecutionContext(Collections.singletonList(executionUnit));
    }

    private SqlUnit createSqlUnit(String sql, List<SQLParameter> parameters, List<Object> entities, boolean fillAutoIncrement){
        return new SqlUnit(sql, parameters,entities, fillAutoIncrement);
    }
    private ExecutionUnit createExecutionUnit(String dataSource,EasySqlExpression expression, List<Object> entities, boolean fillAutoIncrement){
        SqlUnit sqlUnit = createSqlUnit(expression, entities, fillAutoIncrement);
        return createExecutionUnit(dataSource, sqlUnit);
    }
    private SqlUnit createSqlUnit(EasySqlExpression expression, List<Object> entities, boolean fillAutoIncrement){
        SqlParameterCollector sqlParameterCollector = DefaultSqlParameterCollector.defaultCollector();
        String sql = expression.toSql(sqlParameterCollector);
        return new SqlUnit(sql, sqlParameterCollector.getParameters(),entities, fillAutoIncrement);
    }
    private ExecutionUnit createExecutionUnit(String dataSource,SqlUnit sqlUnit){
        return new  ExecutionUnit(dataSource, sqlUnit);
    }
    private ExecutionContext createExecutionContext(Collection<ExecutionUnit> executionUnits){
        return new ExecutionContext(executionUnits);
    }
    @Override
    public ExecutionContext createEntityExecutionContext(PrepareParseResult prepareParseResult) {
//        NativeSqlQueryCompilerContext nativeSqlQueryCompilerContext = new NativeSqlQueryCompilerContext(prepareParseResult);
        //无需分片的情况下
        if(EasyCollectionUtil.isEmpty(prepareParseResult.getShardingEntities())){
            if(prepareParseResult instanceof PredicatePrepareParseResult){
                return new PredicateExecutionCreator(easyDataSource.getDefaultDataSourceName(), prepareParseResult.getEntityExpressionBuilder().toExpression()).create();
            }
            if(prepareParseResult instanceof InsertPrepareParseResult){
                return new InsertExecutionCreator(easyDataSource.getDefaultDataSourceName(), (InsertPrepareParseResult) prepareParseResult).create();
            }
            if(prepareParseResult instanceof EntityPrepareParseResult){
                return new EntityExecutionCreator(easyDataSource.getDefaultDataSourceName(),(EntityPrepareParseResult)prepareParseResult).create();
            }
            throw new UnsupportedOperationException(ClassUtil.getInstanceSimpleName(prepareParseResult));
        }
        RouteContext routeContext = routeContextFactory.createRouteContext(prepareParseResult);
        rewriteContextFactory.rewriteShardingExpression(prepareParseResult);
        if(prepareParseResult instanceof PredicatePrepareParseResult){
            return new ShardingPredicateExecutionCreator((PredicatePrepareParseResult)prepareParseResult,routeContext).create();
        }
        if(prepareParseResult instanceof EntityPrepareParseResult){
            return new ShardingEntityExecutionCreator((EntityPrepareParseResult) prepareParseResult,routeContext).create();
        }
        throw new UnsupportedOperationException();
    }
}
