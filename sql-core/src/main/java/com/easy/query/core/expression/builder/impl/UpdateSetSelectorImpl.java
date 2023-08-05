package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.basic.jdbc.parameter.BeanSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.builder.UpdateSetSelector;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPropertyPredicate;
import com.easy.query.core.expression.segment.condition.predicate.SQLNativeColumnSetPredicate;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContextImpl;

import java.util.Collection;
import java.util.Objects;

/**
 * create time 2023/6/25 17:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class UpdateSetSelectorImpl implements UpdateSetSelector {

    private final QueryRuntimeContext runtimeContext;
    private final SQLBuilderSegment sqlSegmentBuilder;
    private final SQLSegmentFactory sqlSegmentFactory;

    public UpdateSetSelectorImpl(QueryRuntimeContext runtimeContext, SQLBuilderSegment sqlSegmentBuilder){

        this.runtimeContext = runtimeContext;
        this.sqlSegmentBuilder = sqlSegmentBuilder;
        this.sqlSegmentFactory = runtimeContext.getSQLSegmentFactory();
    }
    @Override
    public UpdateSetSelector column(TableAvailable table, String property) {
        sqlSegmentBuilder.append(new ColumnPropertyPredicate(table, property, runtimeContext));
        return this;
    }

    @Override
    public UpdateSetSelector columnAll(TableAvailable table) {
        Collection<String> properties = table.getEntityMetadata().getProperties();
        for (String property : properties) {
            sqlSegmentBuilder.append(new ColumnPropertyPredicate(table, property, runtimeContext));
        }
        return this;
    }

    @Override
    public UpdateSetSelector sqlNativeSegment(String sqlSegment, SQLExpression1<SQLNativeExpressionContext> contextConsume) {
        Objects.requireNonNull(contextConsume,"sql native context consume cannot be null");
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl();
        contextConsume.apply(sqlNativeExpressionContext);
        SQLNativeSegment columnSegment = sqlSegmentFactory.createSQLNativeSegment(runtimeContext, sqlSegment, sqlNativeExpressionContext);
        sqlSegmentBuilder.append(columnSegment);
        return this;
    }

    @Override
    public UpdateSetSelector columnIgnore(TableAvailable table, String property) {
        sqlSegmentBuilder.getSQLSegments().removeIf(sqlSegment -> {
            if (sqlSegment instanceof SQLEntitySegment) {
                SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                return Objects.equals(sqlEntitySegment.getTable(), table) && Objects.equals(sqlEntitySegment.getPropertyName(), property);
            }
            return false;
        });
        return this;
    }

    @Override
    public UpdateSetSelector columnSQL(TableAvailable table,String property, String sqlSegment, SQLExpression2<SQLNativeExpressionContext, SQLParameter> contextConsume) {

        Objects.requireNonNull(contextConsume,"sql native context consume cannot be null");
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl();
        PropertySQLParameter propertySQLParameter = new PropertySQLParameter(table, property);
        contextConsume.apply(sqlNativeExpressionContext,propertySQLParameter);
        SQLNativeColumnSetPredicate sqlNativeColumnSetPredicate = new SQLNativeColumnSetPredicate(table, property, runtimeContext, sqlSegment, sqlNativeExpressionContext);
//        SQLNativeSegment columnSegment = sqlSegmentFactory.createSQLNativeSegment(runtimeContext, sqlSegment, sqlNativeExpressionContext);
        sqlSegmentBuilder.append(sqlNativeColumnSetPredicate);
        return this;
    }
}
