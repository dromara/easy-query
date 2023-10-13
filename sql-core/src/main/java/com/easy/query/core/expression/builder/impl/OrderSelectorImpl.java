package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.OrderSelector;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.OrderBySegment;
import com.easy.query.core.expression.segment.OrderFuncColumnSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContextImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;

import java.util.Objects;

/**
 * create time 2023/6/23 14:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class OrderSelectorImpl implements OrderSelector {
    private final QueryRuntimeContext runtimeContext;
    private final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    private final SQLSegmentFactory sqlSegmentFactory;
    private final SQLBuilderSegment order;
    protected boolean asc;

    public OrderSelectorImpl(EntityQueryExpressionBuilder entityQueryExpressionBuilder,SQLBuilderSegment order) {
        this.runtimeContext = entityQueryExpressionBuilder.getRuntimeContext();
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.sqlSegmentFactory = runtimeContext.getSQLSegmentFactory();
        this.order = order;
        this.asc=true;
    }

    @Override
    public OrderSelector column(TableAvailable table, String property) {
        OrderBySegment orderByColumnSegment = sqlSegmentFactory.createOrderByColumnSegment(table, property, runtimeContext, asc);
        order.append(orderByColumnSegment);
        return this;
    }

    @Override
    public OrderSelector sqlNativeSegment(String columnConst, SQLExpression1<SQLNativeExpressionContext> contextConsume) {
        Objects.requireNonNull(contextConsume,"sql native context consume cannot be null");
        SQLNativeExpressionContextImpl sqlConstExpressionContext=new SQLNativeExpressionContextImpl(entityQueryExpressionBuilder.getExpressionContext(),runtimeContext);
        contextConsume.apply(sqlConstExpressionContext);
        OrderBySegment orderByColumnSegment = sqlSegmentFactory.createOrderBySQLNativeSegment(runtimeContext,columnConst,sqlConstExpressionContext, asc);
        order.append(orderByColumnSegment);
        return this;
    }

    @Override
    public OrderSelector columnFunc(TableAvailable table, ColumnPropertyFunction columnPropertyFunction) {
        String propertyName = columnPropertyFunction.getPropertyName();
        ColumnFunction columnFunction = columnPropertyFunction.getColumnFunction();
        OrderFuncColumnSegment orderFuncColumnSegment = sqlSegmentFactory.createOrderFuncColumnSegment(table, propertyName, entityQueryExpressionBuilder.getRuntimeContext(), columnFunction, asc);
        order.append(orderFuncColumnSegment);
        return this;
    }

//    @Override
//    public OrderSelector columnConst(String columnConst) {
//        OrderBySegment orderFuncColumnSegment = sqlSegmentFactory.createOrderByConstSegment(null, entityQueryExpressionBuilder.getRuntimeContext(), columnConst, asc);
//        order.append(orderFuncColumnSegment);
//        return this;
//    }

    @Override
    public void setAsc(boolean asc) {
        this.asc=asc;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }
}
