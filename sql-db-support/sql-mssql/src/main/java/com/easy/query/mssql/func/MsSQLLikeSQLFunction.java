package com.easy.query.mssql.func;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncValueExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.func.def.impl.AbstractLikeSQLFunction;

import java.util.List;

/**
 * create time 2024/3/11 20:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLLikeSQLFunction extends AbstractLikeSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final SQLLikeEnum sqlLikeEnum;

    public MsSQLLikeSQLFunction(List<ColumnExpression> columnExpressions, SQLLikeEnum sqlLikeEnum) {

        this.columnExpressions = columnExpressions;
        this.sqlLikeEnum = sqlLikeEnum;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        MsSQLLikeFunctionTemplateGenerator templateGenerator = new MsSQLLikeFunctionTemplateGenerator(
                columnExpressions, sqlLikeEnum, o -> getColumnFuncValueExpression(o)
                , "CHARINDEX({1},{0}) = 1"
                , "CHARINDEX({1},{0}) = (LEN({0}) - LEN({1}) +1)"
                , "CHARINDEX({1},{0}) > 0"
                , "{0} LIKE (CAST({1} AS NVARCHAR(MAX))+'%')"
                , "{0} LIKE ('%'+CAST({1} AS NVARCHAR(MAX)))"
                , "{0} LIKE ('%'+CAST({1} AS NVARCHAR(MAX))+'%')"
        );
        return templateGenerator.sqlSegment(defaultTable);
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
