package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.expression.executor.parser.InsertPrepareParseResult;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyInsertSqlExpression;
import com.easy.query.core.sharding.route.RouteContext;
import com.easy.query.core.sharding.route.RouteUnit;
import com.easy.query.core.sharding.route.table.InsertTableRouteUnit;
import com.easy.query.core.sharding.route.table.TableRouteUnit;

import java.util.Collections;
import java.util.List;

/**
 * create time 2023/4/25 17:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingInsertExecutionCreator extends ShardingBaseExecutionCreator{
    private final InsertPrepareParseResult insertPrepareParseResult;

    public ShardingInsertExecutionCreator(InsertPrepareParseResult prepareParseResult, RouteContext routeContext) {
        super(prepareParseResult, routeContext);
        insertPrepareParseResult = prepareParseResult;
    }

    @Override
    protected List<Object> getEntities(RouteUnit routeUnit) {
        return Collections.singletonList(getEntity(routeUnit));
    }
    private Object getEntity(RouteUnit routeUnit){
        InsertTableRouteUnit tableRouteUnit = (InsertTableRouteUnit)routeUnit.getTableRouteUnits().get(0);
        return tableRouteUnit.getEntity();
    }

    @Override
    protected boolean getFillAutoIncrement(RouteUnit routeUnit) {
        return insertPrepareParseResult.isFillAutoIncrement();
    }

    @Override
    protected EasyEntitySqlExpression createEasyEntitySqlExpression(RouteUnit routeUnit) {
        Object entity = getEntity(routeUnit);
        return insertPrepareParseResult.getEntityExpressionBuilder().toExpression(entity);
    }
}
