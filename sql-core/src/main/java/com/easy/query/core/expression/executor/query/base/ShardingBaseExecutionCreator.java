package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.expression.sql.expression.impl.AnonymousTableSqlExpression;
import com.easy.query.core.sharding.merge.executor.common.ExecutionUnit;
import com.easy.query.core.sharding.route.RouteContext;
import com.easy.query.core.sharding.route.RouteUnit;
import com.easy.query.core.sharding.route.table.TableRouteUnit;
import com.easy.query.core.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/4/25 16:44
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class ShardingBaseExecutionCreator extends BaseExecutionCreator{
    protected final PrepareParseResult prepareParseResult;
    protected final RouteContext routeContext;

    public ShardingBaseExecutionCreator(PrepareParseResult prepareParseResult, RouteContext routeContext){

        this.prepareParseResult = prepareParseResult;
        this.routeContext = routeContext;
    }

    @Override
    protected Collection<ExecutionUnit> createExecutionUnits() {
        List<RouteUnit> routeUnits = getRouteUnits();
        List<ExecutionUnit> executionUnits = new ArrayList<>(routeUnits.size());
        for (RouteUnit routeUnit : routeUnits) {
            String dataSource = routeUnit.getDataSource();
            List<TableRouteUnit> tableRouteUnits = routeUnit.getTableRouteUnits();
            EasyEntitySqlExpression easyEntitySqlExpression = createEasyEntitySqlExpression(routeUnit);
            for (TableRouteUnit tableRouteUnit : tableRouteUnits) {
                List<EasyTableSqlExpression> tables = easyEntitySqlExpression.getTables();
                EasyTableSqlExpression easyTableSqlExpression = ArrayUtil.firstOrDefault(tables, o -> Objects.equals(o.getEntityMetadata().getEntityClass(), tableRouteUnit.getEntityClass()), null);
                if(easyTableSqlExpression!=null&&!(easyTableSqlExpression instanceof AnonymousTableSqlExpression)){
                    easyTableSqlExpression.setTableNameAs(o->tableRouteUnit.getActualTableName());
                }
            }
            ExecutionUnit executionUnit = createExecutionUnit(dataSource, easyEntitySqlExpression, null, false);
            executionUnits.add(executionUnit);
        }
        return executionUnits;
    }
    protected List<RouteUnit> getRouteUnits(){
        return routeContext.getEntityRouteResult().getRouteUnits();
    }
    protected abstract List<Object> getEntities(RouteUnit routeUnit);
    protected abstract boolean getFillAutoIncrement(RouteUnit routeUnit);
    protected abstract  EasyEntitySqlExpression createEasyEntitySqlExpression(RouteUnit routeUnit);
}
