package com.easy.query.core.func.def;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;

import java.util.List;

/**
 * create time 2024/4/8 16:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnySQLFunction extends AbstractExpressionSQLFunction{
    private final String sqlSegment;
    private final List<ColumnExpression> columnExpressions;

    public AnySQLFunction(String sqlSegment,List<ColumnExpression> columnExpressions) {
        this.sqlSegment = sqlSegment;
        this.columnExpressions = columnExpressions;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        return sqlSegment;
    }

    @Override
    public int paramMarks() {
        return columnExpressions.size();
    }

    @Override
    protected List<ColumnExpression> getColumnExpressions() {
        return columnExpressions;
    }
}
