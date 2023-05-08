package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.expression.executor.parser.PredicatePrepareParseResult;
import com.easy.query.core.expression.executor.parser.QueryPrepareParseResult;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.sharding.route.RouteContext;
import com.easy.query.core.sharding.route.RouteUnit;

import java.util.List;

/**
 * create time 2023/4/25 16:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingPredicateExecutionCreator extends ShardingBaseExecutionCreator {
    private final PredicatePrepareParseResult predicatePrepareParseResult;

    public ShardingPredicateExecutionCreator(PredicatePrepareParseResult prepareParseResult, RouteContext routeContext) {
        super(prepareParseResult, routeContext);
        predicatePrepareParseResult = prepareParseResult;
    }

    @Override
    protected List<Object> getEntities(RouteUnit routeUnit) {
        return null;
    }

    @Override
    protected boolean getFillAutoIncrement(RouteUnit routeUnit) {
        return false;
    }

    @Override
    protected EasyEntitySqlExpression createEasyEntitySqlExpression(RouteUnit routeUnit) {
        return predicatePrepareParseResult.getEasyEntityPredicateSqlExpression().cloneSqlExpression();
    }
}
