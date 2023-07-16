package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRewriteUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRewriteUnitImpl;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRouteUnit;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.expression.EntitySQLExpression;
import com.easy.query.core.sharding.rewrite.RewriteContext;
import com.easy.query.core.sharding.rewrite.RewriteRouteUnit;
import com.easy.query.core.sharding.router.RouteUnit;
import com.easy.query.core.sharding.router.table.EntityTableRouteUnit;
import com.easy.query.core.sharding.router.table.TableRouteUnit;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
            Map<TableAvailable, String> tableNameRewriteMap = EasyCollectionUtil.collectionToMap(tableRouteUnits, TableRouteUnit::getTable,TableRouteUnit::getActualTableName);
            SQLRewriteUnit sqlRewriteUnit = new SQLRewriteUnitImpl(tableNameRewriteMap);
            EntitySQLExpression entitySQLExpression = createEntitySQLExpression(rewriteRouteUnit);
            List<Object> entities = getEntitiesByTableRouteUnits(tableRouteUnits);
            boolean fillAutoIncrement = getFillAutoIncrement(routeUnit);
            SQLRouteUnit sqlUnit = createSQLUnit(entitySQLExpression, entities, fillAutoIncrement,sqlRewriteUnit);
            ExecutionUnit executionUnit = createExecutionUnit(dataSource, sqlUnit);
            executionUnits.add(executionUnit);
        }
        return executionUnits;
    }

    private List<Object> getEntitiesByTableRouteUnits(List<TableRouteUnit> tableRouteUnits){
        if(EasyCollectionUtil.isSingle(tableRouteUnits)){
            TableRouteUnit tableRouteUnit =EasyCollectionUtil.first(tableRouteUnits);
            if(tableRouteUnit instanceof EntityTableRouteUnit){
                return Collections.singletonList(((EntityTableRouteUnit)tableRouteUnit).getEntity());
            }
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
