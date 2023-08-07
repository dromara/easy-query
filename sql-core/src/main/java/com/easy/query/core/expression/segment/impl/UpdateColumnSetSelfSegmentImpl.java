package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/8/7 13:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class UpdateColumnSetSelfSegmentImpl implements InsertUpdateSetColumnSQLSegment {
    private final TableAvailable leftTable;
    private final String leftPropertyName;
    private final TableAvailable rightTable;
    private final String rightPropertyName;
    private final QueryRuntimeContext runtimeContext;

    public UpdateColumnSetSelfSegmentImpl(TableAvailable leftTable, String leftPropertyName, TableAvailable rightTable, String rightPropertyName,QueryRuntimeContext runtimeContext) {
        this.leftTable = leftTable;
        this.leftPropertyName = leftPropertyName;
        this.rightTable = rightTable;
        this.rightPropertyName = rightPropertyName;
        this.runtimeContext = runtimeContext;
    }
    @Override
    public String getColumnNameWithOwner(ToSQLContext toSQLContext) {
        return EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,leftTable,leftPropertyName,toSQLContext);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new UpdateColumnSetSelfSegmentImpl(leftTable,leftPropertyName,rightTable,rightPropertyName,runtimeContext);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,rightTable,rightPropertyName,toSQLContext);
    }

    @Override
    public String getPropertyName() {
        return leftPropertyName;
    }

    @Override
    public TableAvailable getTable() {
        return leftTable;
    }
}
