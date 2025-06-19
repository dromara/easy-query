package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.util.EasySQLUtil;

/**
 * create time 2023/4/30 21:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnWithSelfSegmentImpl implements InsertUpdateSetColumnSQLSegment {
    private static final String INCREMENT = " + ?";
    private static final String DECREMENT = " - ?";
    private final boolean increment;
    private final Column2Segment columnLeftSegment;
    private final Column2Segment columnRightSegment;
    private final Object val;

    public ColumnWithSelfSegmentImpl(boolean increment, Column2Segment columnLeftSegment, Column2Segment columnRightSegment, Object val) {
        this.increment = increment;
        this.columnLeftSegment = columnLeftSegment;
        this.columnRightSegment = columnRightSegment;
        this.val = val;
    }

    private String getOperator() {
        return increment ? INCREMENT : DECREMENT;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLUtil.addParameter(toSQLContext, new EasyConstSQLParameter(columnRightSegment.getTable(), columnRightSegment.getColumnMetadata().getPropertyName(), val));
        String sqlColumnSegment = columnRightSegment.toSQL(toSQLContext);
        return sqlColumnSegment + getOperator();
    }

    @Override
    public TableAvailable getTable() {
        return columnLeftSegment.getTable();
    }

    @Override
    public String getPropertyName() {
        return columnLeftSegment.getColumnMetadata().getPropertyName();
    }

    @Override
    public String getColumnNameWithOwner(ToSQLContext toSQLContext) {
        return columnLeftSegment.toSQL(toSQLContext);
//        return EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext, entityTable, propertyName, toSQLContext);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new ColumnWithSelfSegmentImpl(increment, columnLeftSegment, columnRightSegment, val);
    }

    public Object getVal() {
        return val;
    }

    public Column2Segment getColumn2Segment() {
        return columnRightSegment;
    }

    public boolean isIncrement() {
        return increment;
    }
}
