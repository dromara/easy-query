package com.easy.query.core.expression.executor.query.base;

import com.easy.query.core.expression.executor.parser.PredicatePrepareParseResult;
import com.easy.query.core.expression.sql.expression.EntitySQLExpression;
import com.easy.query.core.sharding.rewrite.RewriteContext;
import com.easy.query.core.sharding.rewrite.RewriteRouteUnit;
import com.easy.query.core.sharding.router.RouteUnit;

import java.util.List;

/**
 * create time 2023/4/25 16:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingPredicateExecutionCreator extends ShardingBaseExecutionCreator {
    private final PredicatePrepareParseResult predicatePrepareParseResult;

    public ShardingPredicateExecutionCreator(RewriteContext rewriteContext) {
        super(rewriteContext);
        predicatePrepareParseResult = (PredicatePrepareParseResult)rewriteContext.getPrepareParseResult();
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
    protected EntitySQLExpression createEntitySQLExpression(RewriteRouteUnit rewriteRouteUnit) {
        EntitySQLExpression entitySQLExpression = predicatePrepareParseResult.getEntityPredicateSQLExpression().cloneSQLExpression();
        return rewriteRouteUnit.rewrite(entitySQLExpression);
    }
}
