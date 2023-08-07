package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContextImpl;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.segment.impl.InsertUpdateColumnConfigureSegmentImpl;
import com.easy.query.core.expression.segment.impl.UpdateColumnSegmentImpl;
import com.easy.query.core.expression.segment.impl.UpdateColumnSetSegmentImpl;
import com.easy.query.core.expression.segment.impl.UpdateColumnSetSelfSegmentImpl;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContextImpl;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;

import java.util.Objects;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/25 17:39
 */
public class ColumnSetterImpl<T> implements ColumnSetter<T> {
    protected final EntityExpressionBuilder entityExpressionBuilder;
    protected final TableAvailable table;
    protected final SQLBuilderSegment sqlBuilderSegment;
    protected final QueryRuntimeContext runtimeContext;
    protected final SQLSegmentFactory sqlSegmentFactory;

    public ColumnSetterImpl(TableAvailable table, EntityExpressionBuilder entityExpressionBuilder, SQLBuilderSegment sqlBuilderSegment) {

        this.entityExpressionBuilder = entityExpressionBuilder;
        this.runtimeContext = entityExpressionBuilder.getRuntimeContext();
        this.sqlSegmentFactory = runtimeContext.getSQLSegmentFactory();
        this.table = table;
        this.sqlBuilderSegment = sqlBuilderSegment;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public ColumnSetter<T> set(boolean condition, String property, Object val) {
        if (condition) {
            sqlBuilderSegment.append(new UpdateColumnSetSegmentImpl(table, property, val, entityExpressionBuilder.getRuntimeContext()));
        }
        return this;
    }

    @Override
    public ColumnSetter<T> setWithColumn(boolean condition, String property1, String property2) {
        if (condition) {
            sqlBuilderSegment.append(new UpdateColumnSetSelfSegmentImpl(table, property1, table, property2, entityExpressionBuilder.getRuntimeContext()));
        }
        return this;
    }

    @Override
    public ColumnSetter<T> setIncrementNumber(boolean condition, String property, Number val) {

        if (condition) {
            setSelf(true, property, val);
        }
        return this;
    }

    private void setSelf(boolean increment, String property, Number val) {

        InsertUpdateSetColumnSQLSegment columnWithSelfSegment = sqlSegmentFactory.createColumnWithSelfSegment(increment, table, property, val, entityExpressionBuilder.getRuntimeContext());
        sqlBuilderSegment.append(columnWithSelfSegment);
    }

    @Override
    public ColumnSetter<T> setDecrementNumber(boolean condition, String property, Number val) {

        if (condition) {
            setSelf(false, property, val);
        }
        return this;
    }

    @Override
    public ColumnSetter<T> setSQLSegment(String property, String sqlSegment, SQLExpression1<SQLNativePropertyExpressionContext> contextConsume) {

        Objects.requireNonNull(contextConsume, "sql native context consume cannot be null");
        SQLNativeExpressionContextImpl sqlNativeExpressionContext = new SQLNativeExpressionContextImpl();
        SQLNativePropertyExpressionContextImpl sqlNativePropertyExpressionContext = new SQLNativePropertyExpressionContextImpl(table, sqlNativeExpressionContext);
        contextConsume.apply(sqlNativePropertyExpressionContext);
        InsertUpdateColumnConfigureSegmentImpl insertUpdateColumnConfigureSegment = new InsertUpdateColumnConfigureSegmentImpl(new UpdateColumnSegmentImpl(table, property, runtimeContext), runtimeContext, sqlSegment, sqlNativeExpressionContext);
        sqlBuilderSegment.append(insertUpdateColumnConfigureSegment);
        return this;
    }
}
