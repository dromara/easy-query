package com.easy.query.duckdb.func;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncValueExpression;
import com.easy.query.core.func.def.impl.AbstractLikeSQLFunction;

import java.util.List;

/**
 * create time 2024/3/11 20:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class DuckDBSQLLikeSQLFunction extends AbstractLikeSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final SQLLikeEnum sqlLikeEnum;

    public DuckDBSQLLikeSQLFunction(List<ColumnExpression> columnExpressions, SQLLikeEnum sqlLikeEnum) {

        this.columnExpressions = columnExpressions;
        this.sqlLikeEnum = sqlLikeEnum;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        DuckDBLikeFunctionTemplateGenerator templateGenerator = new DuckDBLikeFunctionTemplateGenerator(
                columnExpressions, sqlLikeEnum, o -> getColumnFuncValueExpression(o)
                , "STRPOS({0},{1}) = 1"
                , "STRPOS({0},{1}) = (CHAR_LENGTH({0}) - CHAR_LENGTH({1}) + 1)"
                , "STRPOS({0},{1}) > 0"
                , "{0} LIKE CONCAT(({1})::TEXT,'%')"
                , "{0} LIKE CONCAT('%',({1})::TEXT)"
                , "{0} LIKE CONCAT('%',({1})::TEXT,'%')"
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
