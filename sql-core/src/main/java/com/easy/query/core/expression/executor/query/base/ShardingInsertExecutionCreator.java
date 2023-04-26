package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.expression.executor.parser.InsertPrepareParseResult;
import com.easy.query.core.sharding.route.RouteContext;
import com.easy.query.core.sharding.route.RouteUnit;

/**
 * create time 2023/4/25 17:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingInsertExecutionCreator extends ShardingEntityExecutionCreator{
    private final InsertPrepareParseResult insertPrepareParseResult;

    public ShardingInsertExecutionCreator(InsertPrepareParseResult prepareParseResult, RouteContext routeContext) {
        super(prepareParseResult, routeContext);
        insertPrepareParseResult = prepareParseResult;
    }

    @Override
    protected boolean getFillAutoIncrement(RouteUnit routeUnit) {
        return insertPrepareParseResult.isFillAutoIncrement();
    }
}
