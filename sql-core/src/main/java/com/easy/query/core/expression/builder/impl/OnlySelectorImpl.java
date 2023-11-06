package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.OnlySelector;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.segment.impl.UpdateColumnSegmentImpl;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContextImpl;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;
import java.util.Objects;

/**
 * create time 2023/6/25 17:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class OnlySelectorImpl implements OnlySelector {

    private final QueryRuntimeContext runtimeContext;
    private final ExpressionContext expressionContext;
    private final SQLBuilderSegment sqlSegmentBuilder;
    private final SQLSegmentFactory sqlSegmentFactory;

    public OnlySelectorImpl(QueryRuntimeContext runtimeContext, ExpressionContext expressionContext, SQLBuilderSegment sqlSegmentBuilder){

        this.runtimeContext = runtimeContext;
        this.expressionContext = expressionContext;
        this.sqlSegmentBuilder = sqlSegmentBuilder;
        this.sqlSegmentFactory = runtimeContext.getSQLSegmentFactory();
    }

    @Override
    public OnlySelector columnKeys(TableAvailable table) {
        Collection<String> keyProperties = table.getEntityMetadata().getKeyProperties();
        if(EasyCollectionUtil.isEmpty(keyProperties)){
            throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(table.getEntityClass()) +" not found keys");
        }
        for (String keyProperty : keyProperties) {
            column(table,keyProperty);
        }
        return this;
    }

    @Override
    public OnlySelector column(TableAvailable table, String property) {

        ColumnMetadata columnMetadata = table.getEntityMetadata().getColumnNotNull(property);
        if (columnMetadata.isValueObject()) {
            for (ColumnMetadata metadata : columnMetadata.getValueObjectColumnMetadataList()) {
                sqlSegmentBuilder.append(new UpdateColumnSegmentImpl(table, metadata, runtimeContext));
            }
        } else {
            sqlSegmentBuilder.append(new UpdateColumnSegmentImpl(table, property, runtimeContext));
        }
        return this;
    }

    @Override
    public OnlySelector columnAll(TableAvailable table) {
        Collection<String> properties = table.getEntityMetadata().getProperties();
        for (String property : properties) {
            column(table,property);
        }
        return this;
    }

    @Override
    public OnlySelector columnIgnore(TableAvailable table, String property) {
        sqlSegmentBuilder.getSQLSegments().removeIf(sqlSegment -> {
            if (sqlSegment instanceof SQLEntitySegment) {
                SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                return Objects.equals(sqlEntitySegment.getTable(), table) && Objects.equals(sqlEntitySegment.getPropertyName(), property);
            }
            return false;
        });
        return this;
    }

    public OnlySelector sqlNativeSegment(String sqlSegment, SQLExpression1<SQLNativeExpressionContext> contextConsume){
        Objects.requireNonNull(contextConsume,"sql native context consume cannot be null");
        SQLNativeExpressionContextImpl sqlNativeExpressionContext=new SQLNativeExpressionContextImpl(expressionContext,runtimeContext);
        contextConsume.apply(sqlNativeExpressionContext);
        SQLNativeSegment columnSegment = sqlSegmentFactory.createSQLNativeSegment(runtimeContext, sqlSegment, sqlNativeExpressionContext);
        sqlSegmentBuilder.append(columnSegment);
        return this;
    }

//    @Override
//    public UpdateSetSelector columnSQL(TableAvailable table,String property, String sqlSegment, SQLExpression2<SQLNativeExpressionContext, SQLParameter> contextConsume) {
//
//        Objects.requireNonNull(contextConsume,"sql native context consume cannot be null");
//        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl();
//        PropertySQLParameter propertySQLParameter = new PropertySQLParameter(table, property);
//        contextConsume.apply(sqlNativeExpressionContext,propertySQLParameter);
//        SQLNativeColumnSetPredicate sqlNativeColumnSetPredicate = new SQLNativeColumnSetPredicate(table, property, runtimeContext, sqlSegment, sqlNativeExpressionContext);
////        SQLNativeSegment columnSegment = sqlSegmentFactory.createSQLNativeSegment(runtimeContext, sqlSegment, sqlNativeExpressionContext);
//        sqlSegmentBuilder.append(sqlNativeColumnSetPredicate);
//        return this;
//    }
}
