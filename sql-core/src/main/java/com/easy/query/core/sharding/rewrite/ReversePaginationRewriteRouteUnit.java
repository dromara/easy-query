package com.easy.query.core.sharding.rewrite;

import com.easy.query.core.expression.segment.OrderByColumnSegment;
import com.easy.query.core.expression.segment.OrderColumnSegmentImpl;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.sql.expression.EntitySQLExpression;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.sharding.route.RouteUnit;

import java.util.List;

/**
 * create time 2023/5/15 09:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class ReversePaginationRewriteRouteUnit  extends DefaultRewriteRouteUnit{
    private final long rewriteOffset;
    private final long rewriteRows;

    public ReversePaginationRewriteRouteUnit(long rewriteOffset, long rewriteRows, RouteUnit routeUnit) {
        super(routeUnit);
        this.rewriteOffset = rewriteOffset;
        this.rewriteRows = rewriteRows;
    }

    @Override
    public EntitySQLExpression rewrite(EntitySQLExpression entitySQLExpression) {
        EntityQuerySQLExpression querySQLExpression = (EntityQuerySQLExpression)super.rewrite(entitySQLExpression);
        querySQLExpression.setOffset(rewriteOffset);
        querySQLExpression.setRows(rewriteRows);
        List<SQLSegment> sqlSegments = querySQLExpression.getOrder().cloneSQLBuilder().getSQLSegments();
        querySQLExpression.getOrder().clear();
        for (SQLSegment sqlSegment : sqlSegments) {
            if(sqlSegment instanceof OrderByColumnSegment){
                OrderByColumnSegment orderByColumnSegment = (OrderByColumnSegment) sqlSegment;
                querySQLExpression.getOrder().append(new OrderColumnSegmentImpl(orderByColumnSegment.getTable(),orderByColumnSegment.getPropertyName(), entitySQLExpression.getRuntimeContext(),!orderByColumnSegment.isAsc()));
            }
        }
        return querySQLExpression;
    }
}
