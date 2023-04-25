package com.easy.query.core.expression.executor.query;

import com.easy.query.core.basic.jdbc.parameter.DefaultSqlParameterCollector;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.executor.parser.InsertPrepareParseResult;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.executor.parser.QueryPrepareParseResult;
import com.easy.query.core.expression.executor.query.base.InsertExecutionCreator;
import com.easy.query.core.expression.executor.query.base.QueryExecutionCreator;
import com.easy.query.core.expression.executor.query.base.ShardingInsertExecutionCreator;
import com.easy.query.core.expression.executor.query.base.ShardingQueryExecutionCreator;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyInsertSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.sharding.EasyDataSource;
import com.easy.query.core.sharding.route.RouteUnit;
import com.easy.query.core.sharding.merge.executor.common.ExecutionUnit;
import com.easy.query.core.sharding.merge.executor.common.SqlUnit;
import com.easy.query.core.sharding.rewrite.RewriteContextFactory;
import com.easy.query.core.sharding.route.RouteContext;
import com.easy.query.core.sharding.route.RouteContextFactory;
import com.easy.query.core.sharding.route.table.InsertTableRouteUnit;
import com.easy.query.core.sharding.route.table.TableRouteUnit;
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.core.util.ClassUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    public ExecutionContext createQueryExecutionContext(String sql, List<SQLParameter> parameters) {
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
    public ExecutionContext createExecutionContext(PrepareParseResult prepareParseResult) {
//        NativeSqlQueryCompilerContext nativeSqlQueryCompilerContext = new NativeSqlQueryCompilerContext(prepareParseResult);
        //无需分片的情况下
        if(ArrayUtil.isEmpty(prepareParseResult.getShardingEntities())){
            if(prepareParseResult instanceof QueryPrepareParseResult){
                return new QueryExecutionCreator(easyDataSource.getDefaultDataSourceName(), prepareParseResult.getEntityExpressionBuilder().toExpression()).create();
            }
            if(prepareParseResult instanceof InsertPrepareParseResult){
                InsertPrepareParseResult insertPrepareParseResult = (InsertPrepareParseResult) prepareParseResult;
                EntityInsertExpressionBuilder entityInsertExpressionBuilder = insertPrepareParseResult.getEntityExpressionBuilder();
                boolean fillAutoIncrement = insertPrepareParseResult.isFillAutoIncrement();
                List<Object> entities = insertPrepareParseResult.getEntities();
                return new InsertExecutionCreator(easyDataSource.getDefaultDataSourceName(), entityInsertExpressionBuilder,entities,fillAutoIncrement).create();
            }
            throw new UnsupportedOperationException(ClassUtil.getInstanceSimpleName(prepareParseResult));
        }
        RouteContext routeContext = routeContextFactory.createRouteContext(prepareParseResult);
        rewriteContextFactory.rewriteExpression(prepareParseResult);
        if(prepareParseResult instanceof QueryPrepareParseResult){
            return new ShardingQueryExecutionCreator((QueryPrepareParseResult)prepareParseResult,routeContext).create();
        }
        if(prepareParseResult instanceof  InsertPrepareParseResult){
            return new ShardingInsertExecutionCreator((InsertPrepareParseResult) prepareParseResult,routeContext).create();
        }
        throw new UnsupportedOperationException();
    }


    private SqlUnit createSqlUnit(EasyEntitySqlExpression easyEntitySqlExpression){
        if(easyEntitySqlExpression instanceof EasyQuerySqlExpression){
            return createQuerySqlUnit((EasyQuerySqlExpression) easyEntitySqlExpression);
        }
        throw new UnsupportedOperationException();
    }
    private SqlUnit createQuerySqlUnit(EasyQuerySqlExpression easyQuerySqlExpression){
        SqlParameterCollector sqlParameterCollector = DefaultSqlParameterCollector.defaultCollector();
        String sql = easyQuerySqlExpression.toSql(sqlParameterCollector);
        List<SQLParameter> parameters = sqlParameterCollector.getParameters();
        return new SqlUnit(sql,parameters);
    }
}
