package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.expression.segment.scec.context.core.SQLNativeExpression;

/**
 * create time 2023/8/7 11:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class InsertUpdateColumnConfigureSegmentImpl extends AbstractSQLNativeSegmentImpl implements InsertUpdateSetColumnSQLSegment {
    private final InsertUpdateSetColumnSQLSegment insertUpdateSetColumnSQLSegment;

    public InsertUpdateColumnConfigureSegmentImpl(InsertUpdateSetColumnSQLSegment insertUpdateSetColumnSQLSegment, QueryRuntimeContext runtimeContext, String sqlSegment, SQLNativeExpression sqlNativeExpression) {
        super(runtimeContext, sqlSegment, sqlNativeExpression);
        this.insertUpdateSetColumnSQLSegment = insertUpdateSetColumnSQLSegment;
    }

    @Override
    public TableAvailable getTable() {
        return insertUpdateSetColumnSQLSegment.getTable();
    }

    @Override
    public String getPropertyName() {
        return insertUpdateSetColumnSQLSegment.getPropertyName();
    }

    @Override
    public String getAlias() {
        return null;
    }

    @Override
    public String getColumnNameWithOwner(ToSQLContext toSQLContext) {
        return insertUpdateSetColumnSQLSegment.getColumnNameWithOwner(toSQLContext);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new InsertUpdateColumnConfigureSegmentImpl(insertUpdateSetColumnSQLSegment,runtimeContext,sqlSegment, sqlNativeExpression);
    }
}
