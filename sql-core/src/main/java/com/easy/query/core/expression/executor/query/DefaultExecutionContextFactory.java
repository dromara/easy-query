package com.easy.query.core.expression.executor.query;

import com.easy.query.core.basic.jdbc.parameter.DefaultSqlParameterCollector;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.expression.sql.expression.impl.QuerySqlExpression;
import com.easy.query.core.sharding.EasyDataSource;
import com.easy.query.core.sharding.common.RouteMapper;
import com.easy.query.core.sharding.common.RouteUnit;
import com.easy.query.core.sharding.merge.executor.common.ExecutionUnit;
import com.easy.query.core.sharding.merge.executor.common.SqlUnit;
import com.easy.query.core.sharding.rewrite.RewriteContext;
import com.easy.query.core.sharding.rewrite.RewriteContextFactory;
import com.easy.query.core.sharding.route.RouteContext;
import com.easy.query.core.sharding.route.RouteContextFactory;
import com.easy.query.core.util.ArrayUtil;

import java.util.ArrayList;
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

    @Override
    public ExecutionContext createExecutionContext(PrepareParseResult prepareParseResult) {
        EasyEntitySqlExpression entitySqlExpression = prepareParseResult.getEntitySqlExpression();
//        NativeSqlQueryCompilerContext nativeSqlQueryCompilerContext = new NativeSqlQueryCompilerContext(prepareParseResult);
        if(ArrayUtil.isEmpty(prepareParseResult.getShardingEntities())){
            ExecutionUnit executionUnit = new ExecutionUnit(easyDataSource.getDefaultDataSourceName(), createSqlUnit(entitySqlExpression));
            return new ExecutionContext(Collections.singletonList(executionUnit));
        }
        RouteContext routeContext = routeContextFactory.createRouteContext(prepareParseResult);
        RewriteContext rewriteContext = rewriteContextFactory.createRewriteContext(routeContext);
        EasyEntitySqlExpression rewriteEntitySqlExpression = rewriteContext.getEntitySqlExpression();
        List<RouteUnit> routeUnits = routeContext.getEntityRouteResult().getRouteUnits();
        List<ExecutionUnit> executionUnits = new ArrayList<>(routeUnits.size());
        for (RouteUnit routeUnit : routeUnits) {
            String dataSource = routeUnit.getDataSource();
            List<RouteMapper> routeMappers = routeUnit.getRouteMappers();
            EasyEntitySqlExpression easySqlExpression = (EasyEntitySqlExpression)rewriteEntitySqlExpression.cloneSqlExpression();
            for (EasyTableSqlExpression table : easySqlExpression.getTables()) {
                if(!table.tableNameIsAs()&&!(table instanceof AnonymousEntityTableExpressionBuilder)){
                    RouteMapper routeMapper = ArrayUtil.firstOrDefault(routeMappers, o -> Objects.equals(o.getEntityClass(), table.getEntityMetadata().getEntityClass()), null);
                    if(routeMapper!=null){
                        table.setTableNameAs(o->routeMapper.getActualName());
                    }
                }
            }
            SqlUnit sqlUnit = createSqlUnit(easySqlExpression);
            ExecutionUnit executionUnit = new ExecutionUnit(dataSource, sqlUnit);
            executionUnits.add(executionUnit);
        }

        return new ExecutionContext(executionUnits);
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
