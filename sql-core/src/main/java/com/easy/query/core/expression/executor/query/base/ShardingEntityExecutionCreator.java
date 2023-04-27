package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.expression.executor.parser.EntityPrepareParseResult;
import com.easy.query.core.expression.executor.parser.InsertPrepareParseResult;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityToExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.sharding.route.RouteContext;
import com.easy.query.core.sharding.route.RouteUnit;
import com.easy.query.core.sharding.route.table.EntityTableRouteUnit;

import java.util.Collections;
import java.util.List;

/**
 * create time 2023/4/26 09:34
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingEntityExecutionCreator extends ShardingBaseExecutionCreator{
    private final EntityPrepareParseResult entityPrepareParseResult;

    public ShardingEntityExecutionCreator(EntityPrepareParseResult entityPrepareParseResult, RouteContext routeContext) {
        super(entityPrepareParseResult, routeContext);
        this.entityPrepareParseResult = entityPrepareParseResult;
    }

    @Override
    protected List<Object> getEntities(RouteUnit routeUnit) {
        return Collections.singletonList(getEntity(routeUnit));
    }
    private Object getEntity(RouteUnit routeUnit){
        EntityTableRouteUnit tableRouteUnit = (EntityTableRouteUnit)routeUnit.getTableRouteUnits().get(0);
        return tableRouteUnit.getEntity();
    }

    @Override
    protected boolean getFillAutoIncrement(RouteUnit routeUnit) {
        if(entityPrepareParseResult instanceof InsertPrepareParseResult){
            return ((InsertPrepareParseResult)entityPrepareParseResult).isFillAutoIncrement();
        }
        return false;
    }

    @Override
    protected EasyEntitySqlExpression createEasyEntitySqlExpression(RouteUnit routeUnit) {
        EntityExpressionBuilder entityExpressionBuilder = entityPrepareParseResult.getEntityExpressionBuilder();
        if(entityExpressionBuilder instanceof EntityToExpressionBuilder){
            EntityToExpressionBuilder entityToExpressionBuilder = (EntityToExpressionBuilder) entityExpressionBuilder;
            Object entity = getEntity(routeUnit);
            return entityToExpressionBuilder.toExpression(entity);
        }
        return entityExpressionBuilder.toExpression();
    }
}
