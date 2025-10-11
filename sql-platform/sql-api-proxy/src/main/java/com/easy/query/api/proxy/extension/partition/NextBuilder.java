package com.easy.query.api.proxy.extension.partition;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.impl.OrderSelectorImpl;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.segment.builder.OrderBySQLBuilderSegmentImpl;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.extension.functions.type.AnyTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.impl.AnyTypeExpressionImpl;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * create time 2025/10/11 08:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class NextBuilder<TProxy extends ProxyEntity<TProxy, TEntity>, TEntity> {
    private final TProxy proxy;
    private final EntitySQLContext entitySQLContext;
    private final int offset;
    private final boolean isNext;
    private final List<SQLActionExpression1<TProxy>> orderBys;

    public NextBuilder(TProxy proxy, EntitySQLContext entitySQLContext, int offset, boolean isNext) {
        this.proxy = proxy;
        this.entitySQLContext = entitySQLContext;
        this.offset = offset;
        this.isNext = isNext;
        this.orderBys = new ArrayList<>();
    }

    public NextBuilder<TProxy, TEntity> orderBy(SQLActionExpression1<TProxy> orderByExpression) {
        this.orderBys.add(orderByExpression);
        return this;
    }

    public <TProperty> AnyTypeExpression<TProperty> column(SQLFuncExpression1<TProxy, PropTypeColumn<TProperty>> columnExpression) {
        PropTypeColumn<TProperty> typeColumn = columnExpression.apply(proxy);
        OrderBySQLBuilderSegmentImpl orderBySQLBuilderSegment = new OrderBySQLBuilderSegmentImpl();
        EntityExpressionBuilder entityExpressionBuilder = proxy.getEntitySQLContext().getEntityExpressionBuilder();
        OrderSelectorImpl orderSelector = new OrderSelectorImpl((EntityQueryExpressionBuilder) entityExpressionBuilder, entityExpressionBuilder.getRuntimeContext(), entityExpressionBuilder.getExpressionContext(), orderBySQLBuilderSegment);
        proxy.getEntitySQLContext()._orderBy(orderSelector, () -> {
            for (SQLActionExpression1<TProxy> orderBy : orderBys) {
                orderBy.apply(proxy);
            }
        });

        return new AnyTypeExpressionImpl<>(entitySQLContext, typeColumn.getTable(), typeColumn.getValue(), f -> {
            return f.anySQLFunction((isNext ? "LEAD" : "LAG") + "({0}, " + offset + ")  OVER (ORDER BY {1})", x -> {
                PropTypeColumn.columnFuncSelector(x, typeColumn);
                if (EasySQLSegmentUtil.isNotEmpty(orderBySQLBuilderSegment)) {
                    PropTypeColumn.acceptAnyValue(x, orderBySQLBuilderSegment);
                } else {
                    throw new EasyQueryInvalidOperationException("In a PARTITION BY clause, the ORDER BY expression must be explicitly specified; otherwise, referencing the nth expression is not supported.");
                }
            });
        }, typeColumn.getPropertyType());
    }
}
