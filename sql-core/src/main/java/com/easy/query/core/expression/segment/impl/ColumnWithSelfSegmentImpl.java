package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLUtil;

/**
 * create time 2023/4/30 21:45
 * 文件说明
 * @author xuejiaming
 */
public class ColumnWithSelfSegmentImpl implements InsertUpdateSetColumnSQLSegment {
    private static final String INCREMENT = " + ?";
    private static final String DECREMENT = " - ?";
    private final boolean increment;
    private final Column2Segment column2Segment;
    private final Object val;

    public ColumnWithSelfSegmentImpl(boolean increment, Column2Segment column2Segment, Object val) {
        this.increment = increment;
        this.column2Segment = column2Segment;
        this.val = val;
    }

    private String getOperator() {
        return increment ? INCREMENT : DECREMENT;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        EasySQLUtil.addParameter(toSQLContext, new EasyConstSQLParameter(column2Segment.getTable(), column2Segment.getColumnMetadata().getPropertyName(), val));
        String sqlColumnSegment = column2Segment.toSQL(toSQLContext);
        return sqlColumnSegment + getOperator();
    }

    @Override
    public TableAvailable getTable() {
        return column2Segment.getTable();
    }

    @Override
    public String getPropertyName() {
        return column2Segment.getColumnMetadata().getPropertyName();
    }

    @Override
    public String getColumnNameWithOwner(ToSQLContext toSQLContext) {
        return column2Segment.toSQL(toSQLContext);
//        return EasySQLExpressionUtil.getSQLOwnerColumnByProperty(runtimeContext, entityTable, propertyName, toSQLContext);
    }

    @Override
    public InsertUpdateSetColumnSQLSegment cloneSQLColumnSegment() {
        return new ColumnWithSelfSegmentImpl(increment, column2Segment, val);
    }

    public Object getVal() {
        return val;
    }

    public Column2Segment getColumn2Segment() {
        return column2Segment;
    }

    public boolean isIncrement() {
        return increment;
    }
}
