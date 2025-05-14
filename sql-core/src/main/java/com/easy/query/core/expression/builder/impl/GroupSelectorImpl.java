package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.GroupByColumnSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.segment.impl.SQLFunctionColumnSegmentImpl;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContextImpl;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.SQLFunctionTranslateImpl;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;
import java.util.Objects;

/**
 * create time 2023/6/23 13:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupSelectorImpl implements GroupSelector {
    protected final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    protected final QueryRuntimeContext runtimeContext;
    protected final SQLBuilderSegment sqlSegmentBuilder;
    protected final SQLSegmentFactory sqlSegmentFactory;

    public GroupSelectorImpl(EntityQueryExpressionBuilder entityQueryExpressionBuilder){

        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.sqlSegmentBuilder=entityQueryExpressionBuilder.getGroup();
        this.runtimeContext=entityQueryExpressionBuilder.getRuntimeContext();
        this.sqlSegmentFactory=runtimeContext.getSQLSegmentFactory();
    }

    @Override
    public GroupSelector columnKeys(TableAvailable table) {
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
    public GroupSelector column(TableAvailable table, String property) {
        GroupByColumnSegment groupByColumnSegment = sqlSegmentFactory.createGroupByColumnSegment(table, property, entityQueryExpressionBuilder.getExpressionContext());
        sqlSegmentBuilder.append(groupByColumnSegment);
        return this;
    }

    @Override
    public GroupSelector columnIgnore(TableAvailable table, String property) {
        sqlSegmentBuilder.getSQLSegments().removeIf(sqlSegment -> {
            if (sqlSegment instanceof SQLEntitySegment) {
                SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) sqlSegment;
                return Objects.equals(sqlEntitySegment.getTable(), table) &&
                        (
                                Objects.equals(sqlEntitySegment.getPropertyName(), property)
                                        ||
                                        (sqlEntitySegment.getPropertyName().contains("." ) && sqlEntitySegment.getPropertyName().startsWith(property + "." ))
                        );
            }
            return false;
        });
        return this;
    }

    @Override
    public GroupSelector sqlNativeSegment(String sqlSegment, SQLActionExpression1<SQLNativeExpressionContext> contextConsume) {
        Objects.requireNonNull(contextConsume,"sql native context consume cannot be null");
        SQLNativeExpressionContextImpl sqlConstExpressionContext=new SQLNativeExpressionContextImpl(entityQueryExpressionBuilder.getExpressionContext(),entityQueryExpressionBuilder.getRuntimeContext());
        contextConsume.apply(sqlConstExpressionContext);
        GroupByColumnSegment groupByColumnSegment = sqlSegmentFactory.createGroupBySQLNativeSegment(entityQueryExpressionBuilder.getExpressionContext(), sqlSegment,sqlConstExpressionContext);
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
        FuncColumnSegment funcColumnSegment = sqlSegmentFactory.createFuncColumnSegment(table, propertyName, entityQueryExpressionBuilder.getExpressionContext(), columnFunction, null);
        sqlSegmentBuilder.append(funcColumnSegment);
        return this;
    }

    @Override
    public GroupSelector columnFunc(TableAvailable table, SQLFunction sqlFunction) {

        SQLSegment sqlSegment = new SQLFunctionTranslateImpl(sqlFunction)
                .toSQLSegment(entityQueryExpressionBuilder.getExpressionContext(), table, runtimeContext, null);
        FuncColumnSegment funcColumnSegment = new SQLFunctionColumnSegmentImpl(table, null, runtimeContext, sqlSegment, sqlFunction.getAggregationType(), null);
        sqlSegmentBuilder.append(funcColumnSegment);
        return this;
    }

    @Override
    public GroupSelector sqlSegmentAs(CloneableSQLSegment sqlColumnSegment) {
        CloneableSQLSegment sqlColumnAsSegment = sqlSegmentFactory.createSQLColumnAsSegment(sqlColumnSegment, null, runtimeContext);
        sqlSegmentBuilder.append(sqlColumnAsSegment);
        return this;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }
}
