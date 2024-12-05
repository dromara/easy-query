package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLUtil;

/**
 * create time 2024/12/5 13:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnValue2SegmentImpl implements ColumnValue2Segment{
    private final TableAvailable table;
    private final ColumnMetadata columnMetadata;
    private final ExpressionContext expressionContext;
    private final SQLParameter sqlParameter;

    public ColumnValue2SegmentImpl(TableAvailable table, ColumnMetadata columnMetadata, ExpressionContext expressionContext, SQLParameter sqlParameter){
        this.table = table;
        this.columnMetadata = columnMetadata;
        this.expressionContext = expressionContext;
        this.sqlParameter = sqlParameter;
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
        EasySQLUtil.addParameter(toSQLContext, sqlParameter);
        return "?";
    }

    @Override
    public SQLParameter getSQLParameter() {
        return sqlParameter;
    }
}
