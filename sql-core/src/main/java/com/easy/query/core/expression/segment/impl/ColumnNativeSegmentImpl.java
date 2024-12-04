package com.easy.query.core.expression.segment.impl;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.SQLNativeSegment;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * @author xuejiaming
 * @FileName: ColumnSegment.java
 * @Description: 文件说明
 * @Date: 2023/2/13 15:18
 */
public class ColumnNativeSegmentImpl implements ColumnSegment {


    protected final TableAvailable table;


    protected final ColumnMetadata columnMetadata;
    protected final ExpressionContext expressionContext;
    protected final SQLNativeSegment sqlNativeSegment;
    protected String alias;

    public ColumnNativeSegmentImpl(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, SQLNativeSegment sqlNativeSegment, String alias) {
        this.table = table;
        this.columnMetadata = columnMetadata;
        this.expressionContext = expressionContext;
        this.sqlNativeSegment = sqlNativeSegment;
        this.alias = alias;
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public String getPropertyName() {
        return columnMetadata.getPropertyName();
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        String sqlOwnerColumn = sqlNativeSegment.toSQL(toSQLContext);
        if (getAlias() == null) {
            return sqlOwnerColumn;
        }
        return sqlOwnerColumn + " AS " + EasySQLExpressionUtil.getQuoteName(expressionContext.getRuntimeContext(), getAlias());
    }

    @Override
    public ColumnMetadata getColumnMetadata() {
        return columnMetadata;
    }

    @Override
    public ColumnSegment cloneSQLColumnSegment() {
        return new ColumnNativeSegmentImpl(table, columnMetadata, expressionContext, sqlNativeSegment, alias);
    }

}
