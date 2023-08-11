package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContextImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;

import java.util.Objects;

/**
 * create time 2023/6/23 13:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupSelectorImpl implements GroupSelector {
    protected final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    protected final SQLBuilderSegment sqlSegmentBuilder;
    protected final SQLSegmentFactory sqlSegmentFactory;

    public GroupSelectorImpl(EntityQueryExpressionBuilder entityQueryExpressionBuilder){

        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        sqlSegmentBuilder=entityQueryExpressionBuilder.getGroup();
        sqlSegmentFactory=entityQueryExpressionBuilder.getRuntimeContext().getSQLSegmentFactory();
    }
    @Override
    public GroupSelector column(TableAvailable table, String property) {
        GroupByColumnSegment groupByColumnSegment = sqlSegmentFactory.createGroupByColumnSegment(table, property, entityQueryExpressionBuilder.getRuntimeContext());
        sqlSegmentBuilder.append(groupByColumnSegment);
        return this;
    }

    @Override
    public GroupSelector sqlNativeSegment(String sqlSegment, SQLExpression1<SQLNativeExpressionContext> contextConsume) {
        Objects.requireNonNull(contextConsume,"sql native context consume cannot be null");
        SQLNativeExpressionContextImpl sqlConstExpressionContext=new SQLNativeExpressionContextImpl();
        contextConsume.apply(sqlConstExpressionContext);
        GroupByColumnSegment groupByColumnSegment = sqlSegmentFactory.createGroupBySQLNativeSegment(entityQueryExpressionBuilder.getRuntimeContext(), sqlSegment,sqlConstExpressionContext);
        sqlSegmentBuilder.append(groupByColumnSegment);
        return this;
    }

//    @Override
//    public GroupSelector columnConst(String columnConst) {
//        GroupByColumnSegment groupByColumnSegment = sqlSegmentFactory.createGroupByConstSegment(null, entityQueryExpressionBuilder.getRuntimeContext(),columnConst);
//        sqlSegmentBuilder.append(groupByColumnSegment);
//        return this;
//    }

    @Override
    public GroupSelector columnFunc(TableAvailable table, ColumnPropertyFunction columnPropertyFunction) {
        String propertyName = columnPropertyFunction.getPropertyName();
        ColumnFunction columnFunction = columnPropertyFunction.getColumnFunction();
        FuncColumnSegment funcColumnSegment = sqlSegmentFactory.createFuncColumnSegment(table, propertyName, entityQueryExpressionBuilder.getRuntimeContext(), columnFunction, null);
        sqlSegmentBuilder.append(funcColumnSegment);
        return this;
    }
}
