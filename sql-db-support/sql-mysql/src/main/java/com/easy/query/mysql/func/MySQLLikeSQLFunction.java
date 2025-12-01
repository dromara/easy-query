package com.easy.query.mysql.func;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncValueExpression;
import com.easy.query.core.func.column.impl.ColumnFuncValueExpressionImpl;
import com.easy.query.core.func.def.impl.AbstractLikeSQLFunction;

import java.util.List;

/**
 * create time 2024/3/11 20:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQLLikeSQLFunction extends AbstractLikeSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final SQLLikeEnum sqlLikeEnum;

    public MySQLLikeSQLFunction(List<ColumnExpression> columnExpressions, SQLLikeEnum sqlLikeEnum) {

        this.columnExpressions = columnExpressions;
        this.sqlLikeEnum = sqlLikeEnum;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if (columnExpressions.size() != 2) {
            throw new IllegalArgumentException("like arguments != 2");
        }
        ColumnExpression columnExpression = columnExpressions.get(1);
        ColumnFuncValueExpression columnFuncValueExpression = getColumnFuncValueExpression(columnExpression);
        if (columnFuncValueExpression != null) {
            Object value = columnFuncValueExpression.getValue();
            if (value instanceof String) {
                String valueString = (String) value;
                if (valueString.contains("%") || valueString.contains("_")) {

                    String escapeValue = escape(valueString);//转义
                    ColumnFuncValueExpressionImpl columnFuncEscapeValueExpression = new ColumnFuncValueExpressionImpl(escapeValue);
                    columnExpressions.set(1, columnFuncEscapeValueExpression);
                    if (sqlLikeEnum == SQLLikeEnum.LIKE_PERCENT_RIGHT) {
                        return  "{0} LIKE CONCAT({1},'%') ESCAPE '\\\\'";
//                        return "LOCATE({1},{0}) = 1";
                    }
                    if (sqlLikeEnum == SQLLikeEnum.LIKE_PERCENT_LEFT) {
                        return "{0} LIKE CONCAT('%',{1}) ESCAPE '\\\\'";
                    }
                    return "{0} LIKE CONCAT('%',{1},'%') ESCAPE '\\\\'";
                }
            }
        }
        if (sqlLikeEnum == SQLLikeEnum.LIKE_PERCENT_RIGHT) {
            return "{0} LIKE CONCAT({1},'%')";
        }
        if (sqlLikeEnum == SQLLikeEnum.LIKE_PERCENT_LEFT) {
            return "{0} LIKE CONCAT('%',{1})";
        }
        return "{0} LIKE CONCAT('%',{1},'%')";
    }
    /**
     * 转义 LIKE 中的特殊字符：%, _, \
     * 使用 ESCAPE '\'
     */
    private String escape(String input) {
        if (input == null) return null;

        return input
                .replace("\\", "\\\\")   // 转义反斜杠
                .replace("%", "\\%")      // 转义百分号
                .replace("_", "\\_");     // 转义下划线
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
