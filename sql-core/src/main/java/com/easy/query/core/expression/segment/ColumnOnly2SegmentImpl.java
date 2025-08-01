package com.easy.query.core.expression.segment;

import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2024/12/5 10:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnOnly2SegmentImpl implements Column2Segment{
    private final TableAvailable table;
    private final String columnName;
    private final ExpressionContext expressionContext;

    public ColumnOnly2SegmentImpl(TableAvailable table, String columnName, ExpressionContext expressionContext){
        this.table = table;
        this.columnName = columnName;
        this.expressionContext = expressionContext;
    }
    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public ColumnMetadata getColumnMetadata() {
        return null;
    }

    @Override
    public ExpressionContext getExpressionContext() {
        return expressionContext;
    }

    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return EasySQLExpressionUtil.getSQLOwnerColumn(expressionContext.getRuntimeContext(), table, columnName, toSQLContext);
    }
}
