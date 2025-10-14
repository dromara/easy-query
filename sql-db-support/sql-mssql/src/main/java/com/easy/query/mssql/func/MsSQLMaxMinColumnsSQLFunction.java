package com.easy.query.mssql.func;

import com.easy.query.core.common.KeywordTool;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;

/**
 * create time 2025/10/14 14:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLMaxMinColumnsSQLFunction extends AbstractExpressionSQLFunction {
    private final boolean isMax;
    private final List<ColumnExpression> columnExpressions;

    public MsSQLMaxMinColumnsSQLFunction(boolean isMax, List<ColumnExpression> columnExpressions) {
        this.isMax = isMax;

        this.columnExpressions = columnExpressions;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        return buildMaxUnionSql(columnExpressions);
    }

    public String buildMaxUnionSql(List<ColumnExpression> columns) {
        StringBuilder sb = new StringBuilder();
        sb.append("(SELECT ").append(isMax ? "MAX" : "MIN").append("(").append(KeywordTool.MAX_COLUMN).append(") FROM (VALUES ");

        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append("({").append(i).append("})");
        }

        sb.append(") AS t("+KeywordTool.MAX_COLUMN+"))");
        return sb.toString();
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