package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.expression.sql.expression.impl.AnonymousTableSqlExpression;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.sharding.route.RouteContext;
import com.easy.query.core.sharding.route.RouteUnit;
import com.easy.query.core.sharding.route.table.EntityTableRouteUnit;
import com.easy.query.core.sharding.route.table.TableRouteUnit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
    protected boolean sequenceQuery() {
        return routeContext.getEntityRouteResult().isSequenceQuery();
    }

    @Override
    protected boolean isCrossTable() {
        return routeContext.getEntityRouteResult().isCrossTable();
    }

    @Override
    protected boolean isCrossDataSource() {
        return routeContext.getEntityRouteResult().isCrossDataSource();
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
                EasyTableSqlExpression easyTableSqlExpression = easyEntitySqlExpression.getTable(tableRouteUnit.getTableIndex());
                if(!(easyTableSqlExpression instanceof AnonymousTableSqlExpression)){
                    easyTableSqlExpression.setTableNameAs(o->tableRouteUnit.getActualTableName());
                }
            }

            ExecutionUnit executionUnit = createExecutionUnit(dataSource, easyEntitySqlExpression, getEntitiesByTableRouteUnits(tableRouteUnits), false);
            executionUnits.add(executionUnit);
        }
        return executionUnits;
    }
    private List<Object> getEntitiesByTableRouteUnits(List<TableRouteUnit> tableRouteUnits){
        if(tableRouteUnits.size()!=1){
            return null;
        }
        TableRouteUnit tableRouteUnit = tableRouteUnits.get(0);
        if(tableRouteUnit instanceof EntityTableRouteUnit){
            return Collections.singletonList(((EntityTableRouteUnit)tableRouteUnit).getEntity());
        }
        return null;

    }
    protected List<RouteUnit> getRouteUnits(){
        return routeContext.getEntityRouteResult().getRouteUnits();
    }
    protected abstract List<Object> getEntities(RouteUnit routeUnit);
    protected abstract boolean getFillAutoIncrement(RouteUnit routeUnit);
    protected abstract  EasyEntitySqlExpression createEasyEntitySqlExpression(RouteUnit routeUnit);

}
