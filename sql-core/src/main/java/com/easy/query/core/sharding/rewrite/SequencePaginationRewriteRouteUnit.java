package com.easy.query.core.sharding.rewrite;

import com.easy.query.core.expression.sql.expression.EntitySQLExpression;
import com.easy.query.core.expression.sql.expression.QuerySQLExpression;
import com.easy.query.core.sharding.route.RouteUnit;

/**
 * create time 2023/5/15 08:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class SequencePaginationRewriteRouteUnit extends DefaultRewriteRouteUnit{
    private final long rewriteOffset;
    private final long rewriteRows;

    public SequencePaginationRewriteRouteUnit(long rewriteOffset, long rewriteRows, RouteUnit routeUnit) {
        super(routeUnit);
        this.rewriteOffset = rewriteOffset;
        this.rewriteRows = rewriteRows;
    }

    @Override
    public EntitySQLExpression rewrite(EntitySQLExpression entitySQLExpression) {
        QuerySQLExpression querySQLExpression = (QuerySQLExpression)super.rewrite(entitySQLExpression);
        querySQLExpression.setOffset(rewriteOffset);
        querySQLExpression.setRows(rewriteRows);
        return querySQLExpression;
    }
}
