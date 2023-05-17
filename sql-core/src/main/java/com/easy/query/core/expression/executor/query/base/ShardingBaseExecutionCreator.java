package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.expression.sql.expression.EntitySQLExpression;
import com.easy.query.core.expression.sql.expression.TableSQLExpression;
import com.easy.query.core.expression.sql.expression.impl.AnonymousTableSQLExpressionImpl;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.sharding.rewrite.RewriteContext;
import com.easy.query.core.sharding.rewrite.RewriteRouteUnit;
import com.easy.query.core.sharding.route.RouteUnit;
import com.easy.query.core.sharding.route.table.EntityTableRouteUnit;
import com.easy.query.core.sharding.route.table.TableRouteUnit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * create time 2023/4/25 16:44
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class ShardingBaseExecutionCreator extends BaseExecutionCreator{

    private final RewriteContext rewriteContext;

    public ShardingBaseExecutionCreator(RewriteContext rewriteContext){

        this.rewriteContext = rewriteContext;
    }

    @Override
    protected int mergeBehavior() {
        return rewriteContext.getMergeBehavior();
    }

    @Override
    protected boolean sequenceQuery() {
        return rewriteContext.isSequenceQuery();
    }

    @Override
    protected boolean isCrossTable() {
        return rewriteContext.isCrossTable();
    }

    @Override
    protected boolean isCrossDataSource() {
        return rewriteContext.isCrossDataSource();
    }

    @Override
    protected boolean isReverseMerge() {
        return rewriteContext.isReverseMerge();
    }

    @Override
    protected List<ExecutionUnit> createExecutionUnits() {
        List<RewriteRouteUnit> rewriteRouteUnits = getRewriteRouteUnits();
        List<ExecutionUnit> executionUnits = new ArrayList<>(rewriteRouteUnits.size());
        for (RewriteRouteUnit rewriteRouteUnit : rewriteRouteUnits) {
            RouteUnit routeUnit = rewriteRouteUnit.getRouteUnit();
            String dataSource = routeUnit.getDataSource();
            List<TableRouteUnit> tableRouteUnits = routeUnit.getTableRouteUnits();
            EntitySQLExpression entitySQLExpression = createEntitySQLExpression(rewriteRouteUnit);
            for (TableRouteUnit tableRouteUnit : tableRouteUnits) {
                TableSQLExpression tableSQLExpression = entitySQLExpression.getTable(tableRouteUnit.getTableIndex());
                if(!(tableSQLExpression instanceof AnonymousTableSQLExpressionImpl)){
                    tableSQLExpression.setTableNameAs(o->tableRouteUnit.getActualTableName());
                }
            }
            ExecutionUnit executionUnit = createExecutionUnit(dataSource,entitySQLExpression, getEntitiesByTableRouteUnits(tableRouteUnits), false);
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
    protected List<RewriteRouteUnit> getRewriteRouteUnits(){
        return rewriteContext.getRewriteRouteUnits();
    }
    protected abstract List<Object> getEntities(RouteUnit routeUnit);
    protected abstract boolean getFillAutoIncrement(RouteUnit routeUnit);
    protected abstract EntitySQLExpression createEntitySQLExpression(RewriteRouteUnit routeUnit);

}
