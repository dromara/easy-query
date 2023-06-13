package com.easy.query.core.sharding.rewrite;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.segment.OrderByColumnSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.sql.expression.EntitySQLExpression;
import com.easy.query.core.expression.sql.expression.EntityQuerySQLExpression;
import com.easy.query.core.sharding.router.RouteUnit;

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
        QueryRuntimeContext runtimeContext = entitySQLExpression.getRuntimeContext();
        SQLSegmentFactory sqlSegmentFactory = runtimeContext.getSQLSegmentFactory();

        for (SQLSegment sqlSegment : sqlSegments) {
            if(sqlSegment instanceof OrderByColumnSegment){
                OrderByColumnSegment orderByColumnSegment = (OrderByColumnSegment) sqlSegment;
                OrderByColumnSegment reverseOrderByColumnSegment = sqlSegmentFactory.createOrderByColumnSegment(orderByColumnSegment.getTable(), orderByColumnSegment.getPropertyName(), runtimeContext, !orderByColumnSegment.isAsc());
                querySQLExpression.getOrder().append(reverseOrderByColumnSegment);
            }
        }
        return querySQLExpression;
    }
}
