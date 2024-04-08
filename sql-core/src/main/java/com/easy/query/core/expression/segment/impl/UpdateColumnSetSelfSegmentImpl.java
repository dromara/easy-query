package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
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
    private final ExpressionContext expressionContext;

    public UpdateColumnSetSelfSegmentImpl(TableAvailable leftTable, String leftPropertyName, TableAvailable rightTable, String rightPropertyName, ExpressionContext expressionContext) {
        this.leftTable = leftTable;
        this.leftPropertyName = leftPropertyName;
        this.rightTable = rightTable;
        this.rightPropertyName = rightPropertyName;
        this.expressionContext = expressionContext;
    }
    @Override
    public String getColumnNameWithOwner(ToSQLContext toSQLContext) {
        ColumnMetadata columnMetadata = leftTable.getEntityMetadata().getColumnNotNull(leftPropertyName);
        return EasySQLExpressionUtil.getSQLOwnerColumnMetadata(expressionContext, leftTable, columnMetadata, toSQLContext,true,false);
//        return EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,leftTable,leftPropertyName,toSQLContext);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new UpdateColumnSetSelfSegmentImpl(leftTable,leftPropertyName,rightTable,rightPropertyName,expressionContext);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        ColumnMetadata columnMetadata = rightTable.getEntityMetadata().getColumnNotNull(rightPropertyName);
        return EasySQLExpressionUtil.getSQLOwnerColumnMetadata(expressionContext, rightTable, columnMetadata, toSQLContext,true,false);
//        return EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,rightTable,rightPropertyName,toSQLContext);
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
