package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.Column2Segment;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * @author xuejiaming
 */
public class ColumnSegmentImpl implements ColumnSegment {


    protected final Column2Segment column2Segment;
    protected String alias;

    public ColumnSegmentImpl(Column2Segment column2Segment,String alias) {
        this.column2Segment = column2Segment;
        this.alias = alias;
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
    public String getAlias() {
        return alias;
    }

    @Override
    public void setAlias(String alias) {
        this.alias=alias;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        String sqlOwnerColumn = column2Segment.toSQL(toSQLContext);
        if (getAlias() == null) {
            return sqlOwnerColumn;
        }
        return sqlOwnerColumn + " AS " + EasySQLExpressionUtil.getQuoteName(column2Segment.getExpressionContext().getRuntimeContext(), getAlias());
    }


    @Override
    public ColumnMetadata getColumnMetadata() {
        return column2Segment.getColumnMetadata();
    }

    @Override
    public ColumnSegment cloneSQLColumnSegment() {
        return new ColumnSegmentImpl(column2Segment, alias);
    }

}
