package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
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

    protected final Column2Segment leftColumn2Segment;
    protected final Column2Segment rightColumn2Segment;

    public UpdateColumnSetSelfSegmentImpl(Column2Segment leftColumn2Segment, Column2Segment rightColumn2Segment) {
        this.leftColumn2Segment = leftColumn2Segment;
        this.rightColumn2Segment = rightColumn2Segment;
    }
    @Override
    public String getColumnNameWithOwner(ToSQLContext toSQLContext) {
        return leftColumn2Segment.toSQL(toSQLContext);
//        return EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext,leftTable,leftPropertyName,toSQLContext);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new UpdateColumnSetSelfSegmentImpl(leftColumn2Segment,rightColumn2Segment);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return rightColumn2Segment.toSQL(toSQLContext);
    }

    @Override
    public String getPropertyName() {
        return leftColumn2Segment.getColumnMetadata().getPropertyName();
    }

    @Override
    public TableAvailable getTable() {
        return leftColumn2Segment.getTable();
    }

    public Column2Segment getLeftColumn2Segment() {
        return leftColumn2Segment;
    }

    public Column2Segment getRightColumn2Segment() {
        return rightColumn2Segment;
    }
}
