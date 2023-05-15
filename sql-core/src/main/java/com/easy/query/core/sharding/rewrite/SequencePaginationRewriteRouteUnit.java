package com.easy.query.core.sharding.rewrite;

import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
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
    public EasyEntitySqlExpression rewrite(EasyEntitySqlExpression entitySqlExpression) {
        EasyQuerySqlExpression easyQuerySqlExpression = (EasyQuerySqlExpression)super.rewrite(entitySqlExpression);
        easyQuerySqlExpression.setOffset(rewriteOffset);
        easyQuerySqlExpression.setRows(rewriteRows);
        return easyQuerySqlExpression;
    }
}
