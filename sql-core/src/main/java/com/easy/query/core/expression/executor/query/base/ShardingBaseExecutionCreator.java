package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.internal.common.ExecutionUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.GroupByValue;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRewriteUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRewriteUnitImpl;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLRouteUnit;
import com.easy.query.core.basic.jdbc.executor.internal.common.SQLUnionAllRewriteUnitImpl;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.expression.EntitySQLExpression;
import com.easy.query.core.sharding.rewrite.DefaultRewriteRouteUnit;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        ExecutorContext executorContext = rewriteContext.getPrepareParseResult().getExecutorContext();
        if(!isCrossDataSource() && executorContext.isQuery()&&executorContext.getExpressionContext().getBehavior().hasBehavior(EasyBehaviorEnum.SHARDING_UNION_ALL)){

            List<GroupByValue<String, RewriteRouteUnit>> groupByValues = EasyCollectionUtil.groupBy(rewriteRouteUnits.stream(), r -> r.getRouteUnit().getDataSource())
                    .collect(Collectors.toList());
            for (GroupByValue<String, RewriteRouteUnit> groupByValue : groupByValues) {
                String dataSource = groupByValue.key();
                Stream<TableRouteUnit> tableRouteUnitStream = groupByValue.values().stream().flatMap(r -> r.getRouteUnit().getTableRouteUnits().stream());
                Map<TableAvailable, List<String>> tableNameRewriteMap = EasyCollectionUtil.groupBy(tableRouteUnitStream, r -> r.getTable()).collect(Collectors.toMap(s -> s.key(), s -> s.values().stream().map(x -> x.getActualTableName()).collect(Collectors.toList())));

                SQLRewriteUnit sqlRewriteUnit = new SQLUnionAllRewriteUnitImpl(tableNameRewriteMap);
                EntitySQLExpression entitySQLExpression = createEntitySQLExpression(new DefaultRewriteRouteUnit(null));
                SQLRouteUnit sqlUnit = createSQLUnit(entitySQLExpression, null, false,sqlRewriteUnit);
                ExecutionUnit executionUnit = createExecutionUnit(dataSource, sqlUnit);
                executionUnits.add(executionUnit);
            }
        }else{
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
