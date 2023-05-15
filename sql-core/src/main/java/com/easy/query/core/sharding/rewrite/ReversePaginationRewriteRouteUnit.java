package com.easy.query.core.sharding.rewrite;

import com.easy.query.core.expression.segment.OrderByColumnSegment;
import com.easy.query.core.expression.segment.OrderColumnSegmentImpl;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
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
    public EasyEntitySqlExpression rewrite(EasyEntitySqlExpression entitySqlExpression) {
        EasyQuerySqlExpression easyQuerySqlExpression = (EasyQuerySqlExpression)super.rewrite(entitySqlExpression);
        easyQuerySqlExpression.setOffset(rewriteOffset);
        easyQuerySqlExpression.setRows(rewriteRows);
        List<SqlSegment> sqlSegments = easyQuerySqlExpression.getOrder().cloneSqlBuilder().getSqlSegments();
        easyQuerySqlExpression.getOrder().clear();
        for (SqlSegment sqlSegment : sqlSegments) {
            if(sqlSegment instanceof OrderByColumnSegment){
                OrderByColumnSegment orderByColumnSegment = (OrderByColumnSegment) sqlSegment;
                easyQuerySqlExpression.getOrder().append(new OrderColumnSegmentImpl(orderByColumnSegment.getTable(),orderByColumnSegment.getPropertyName(),entitySqlExpression.getRuntimeContext(),!orderByColumnSegment.isAsc()));
            }
        }
        return easyQuerySqlExpression;
    }
}
