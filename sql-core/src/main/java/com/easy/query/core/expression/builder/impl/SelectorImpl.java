package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContextImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.func.SQLFunction;

import java.util.Objects;

/**
 * create time 2023/6/22 20:34
 * 文件说明
 *
 * @author xuejiaming
 */
public class SelectorImpl extends AbstractSelector<Selector> implements Selector {


    public SelectorImpl(EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLBuilderSegment sqlBuilderSegment) {
        super(entityQueryExpressionBuilder, sqlBuilderSegment);
    }

    @Override
    protected Selector castChain() {
        return this;
    }

    @Override
    protected String getResultColumnName(String propertyAlias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Selector columnFunc(TableAvailable table, ColumnPropertyFunction columnPropertyFunction) {
        String propertyName = columnPropertyFunction.getPropertyName();
        ColumnFunction columnFunction = columnPropertyFunction.getColumnFunction();
        String columnAsName = table.getColumnName(propertyName);
        FuncColumnSegment funcColumnSegment = sqlSegmentFactory.createFuncColumnSegment(table, propertyName, runtimeContext, columnFunction, columnAsName);
        sqlBuilderSegment.append(funcColumnSegment);
        return this;
    }

    public Selector sqlNativeSegment(String sqlSegment, SQLExpression1<SQLNativeExpressionContext> contextConsume){
        Objects.requireNonNull(contextConsume,"sql native context consume cannot be null");
        SQLNativeExpressionContextImpl sqlNativeExpressionContext=new SQLNativeExpressionContextImpl(expressionContext,runtimeContext);
        contextConsume.apply(sqlNativeExpressionContext);
        SQLNativeSegment columnSegment = sqlSegmentFactory.createSQLNativeSegment(runtimeContext, sqlSegment, sqlNativeExpressionContext);
        sqlBuilderSegment.append(columnSegment);
        return castChain();
    }
}
