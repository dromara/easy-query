package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.expression.executor.parser.QueryPrepareParseResult;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
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
 * create time 2023/4/25 16:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingQueryExecutionCreator extends ShardingBaseExecutionCreator{
    private final QueryPrepareParseResult queryPrepareParseResult;

    public ShardingQueryExecutionCreator(QueryPrepareParseResult prepareParseResult, RouteContext routeContext) {
        super(prepareParseResult, routeContext);
        queryPrepareParseResult = prepareParseResult;
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
        return queryPrepareParseResult.getEasyQuerySqlExpression().cloneSqlExpression();
    }

}
