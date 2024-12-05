package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;

/**
 * create time 2024/12/5 10:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class Column2NativeSegmentImpl implements Column2NativeSegment{
    private final TableAvailable table;
    private final ColumnMetadata columnMetadata;
    private final SQLNativeSegment sqlNativeSegment;
    private final ExpressionContext expressionContext;

    public Column2NativeSegmentImpl(TableAvailable table, ColumnMetadata columnMetadata,SQLNativeSegment sqlNativeSegment, ExpressionContext expressionContext){
        this.table = table;
        this.columnMetadata = columnMetadata;
        this.sqlNativeSegment = sqlNativeSegment;
        this.expressionContext = expressionContext;
    }
    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public ColumnMetadata getColumnMetadata() {
        return columnMetadata;
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return expressionContext;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return sqlNativeSegment.toSQL(toSQLContext);
    }

    @Override
    public SQLNativeSegment getSQLNativeSegment() {
        return sqlNativeSegment;
    }
}
