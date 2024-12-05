package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.ColumnValue2Segment;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/8/7 10:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class UpdateColumnSetSegmentImpl extends AbstractInsertUpdateSetColumnSQLSegmentImpl implements InsertUpdateSetColumnSQLSegment {


    public UpdateColumnSetSegmentImpl(Column2Segment column2Segment, ColumnValue2Segment columnValue2Segment){
        super(column2Segment,columnValue2Segment);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new UpdateColumnSetSegmentImpl(column2Segment,columnValue2Segment);
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return columnValue2Segment.toSQL(toSQLContext);
    }

    @Override
    public String getPropertyName() {
        return column2Segment.getColumnMetadata().getPropertyName();
    }

    @Override
    public String getColumnNameWithOwner(ToSQLContext toSQLContext) {
        return column2Segment.toSQL(toSQLContext);
    }

    @Override
    public TableAvailable getTable() {
        return column2Segment.getTable();
    }
}
