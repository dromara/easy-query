package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.builder.core.ResultColumnInfo;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.impl.SQLFunctionColumnSegmentImpl;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContextImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.SQLFunctionTranslateImpl;
import com.easy.query.core.metadata.ColumnMetadata;

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
    protected ResultColumnInfo getResultColumnName(String propertyAlias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Selector columnFunc(TableAvailable table,SQLFunction sqlFunction,String propertyAlias) {
        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(propertyAlias);
        String columnAsName = columnMetadata.getName();
        SQLSegment sqlSegment = new SQLFunctionTranslateImpl(sqlFunction)
                .toSQLSegment(expressionContext, table, runtimeContext, columnAsName);
        FuncColumnSegment funcColumnSegment = new SQLFunctionColumnSegmentImpl(table, columnMetadata, runtimeContext, sqlSegment, sqlFunction.getAggregationType(), columnAsName);
        sqlBuilderSegment.append(funcColumnSegment);
//        columnAppendSQLFunction(table,property,sqlFunction,propertyAlias);
//        sqlActionExpression.apply();
        return this;
    }

    public Selector sqlNativeSegment(String sqlSegment, SQLActionExpression1<SQLNativeExpressionContext> contextConsume){
        Objects.requireNonNull(contextConsume,"sql native context consume cannot be null");
        SQLNativeExpressionContextImpl sqlNativeExpressionContext=new SQLNativeExpressionContextImpl(expressionContext,runtimeContext);
        contextConsume.apply(sqlNativeExpressionContext);
        SQLNativeSegment columnSegment = sqlSegmentFactory.createSQLNativeSegment(expressionContext, sqlSegment, sqlNativeExpressionContext);
        sqlBuilderSegment.append(columnSegment);
        return castChain();
    }
}
