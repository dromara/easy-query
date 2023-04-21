package com.easy.query.core.expression.executor.query;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.sql.AnonymousEntityTableExpression;
import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.expression.sql.ExpressionContext;
import com.easy.query.core.expression.sql.factory.EasyExpressionFactory;
import com.easy.query.core.sharding.EasyDataSource;
import com.easy.query.core.sharding.common.RouteMapper;
import com.easy.query.core.sharding.common.RouteUnit;
import com.easy.query.core.sharding.merge.executor.common.ExecutionUnit;
import com.easy.query.core.sharding.merge.executor.common.SqlUnit;
import com.easy.query.core.sharding.merge.executor.internal.CommandTypeEnum;
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
        ExecutionUnit executionUnit = new ExecutionUnit(easyDataSource.getDefaultDataSourceName(), new SqlUnit(sql,parameters,CommandTypeEnum.QUERY));
        return new ExecutionContext(CommandTypeEnum.QUERY,Collections.singletonList(executionUnit));
    }

    @Override
    public ExecutionContext createExecutionContext(PrepareParseResult prepareParseResult) {
        EntityExpression entityExpression = prepareParseResult.getEntityExpression();
//        NativeSqlQueryCompilerContext nativeSqlQueryCompilerContext = new NativeSqlQueryCompilerContext(prepareParseResult);
        if(ArrayUtil.isEmpty(prepareParseResult.getShardingEntities())){
            ExecutionUnit executionUnit = new ExecutionUnit(easyDataSource.getDefaultDataSourceName(), createSqlUnit(entityExpression));
            return new ExecutionContext(CommandTypeEnum.QUERY,Collections.singletonList(executionUnit));
        }
        RouteContext routeContext = routeContextFactory.createRouteContext(prepareParseResult);
        RewriteContext rewriteContext = rewriteContextFactory.createRewriteContext(routeContext);
        EntityExpression rewriteEntityExpression = rewriteContext.getEntityExpression();
        List<RouteUnit> routeUnits = routeContext.getEntityRouteResult().getRouteUnits();
        List<ExecutionUnit> executionUnits = new ArrayList<>(routeUnits.size());
        for (RouteUnit routeUnit : routeUnits) {
            String dataSource = routeUnit.getDataSource();
            List<RouteMapper> routeMappers = routeUnit.getRouteMappers();
            for (EntityTableExpression table : rewriteEntityExpression.getTables()) {
                if(!table.tableNameIsAs()&&!(table instanceof AnonymousEntityTableExpression)){
                    RouteMapper routeMapper = ArrayUtil.firstOrDefault(routeMappers, o -> Objects.equals(o.getEntityClass(), table.getEntityClass()), null);
                    if(routeMapper!=null){
                        table.setTableNameAs(o->routeMapper.getActualName());
                    }
                }
            }
            SqlUnit sqlUnit = createSqlUnit(rewriteEntityExpression);
            ExecutionUnit executionUnit = new ExecutionUnit(dataSource, sqlUnit);
            executionUnits.add(executionUnit);
        }

        return new ExecutionContext(CommandTypeEnum.QUERY,executionUnits);
    }


    private SqlUnit createSqlUnit(EntityExpression entityExpression){
        if(entityExpression instanceof EntityQueryExpression){
            return createQuerySqlUnit((EntityQueryExpression) entityExpression);
        }
        throw new UnsupportedOperationException();
    }
    private SqlUnit createQuerySqlUnit(EntityQueryExpression entityQueryExpression){
        String sql = entityQueryExpression.toSql();
        List<SQLParameter> parameters = entityQueryExpression.getParameters();
        return new SqlUnit(sql,parameters, CommandTypeEnum.QUERY);
    }
}
