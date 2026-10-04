package com.easy.query.clickhouse.func;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.func.def.impl.AbstractLikeSQLFunction;

import java.util.List;

/**
 * create time 2024/3/11 20:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class ClickHouseLikeSQLFunction extends AbstractLikeSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final SQLLikeEnum sqlLikeEnum;

    public ClickHouseLikeSQLFunction(List<ColumnExpression> columnExpressions, SQLLikeEnum sqlLikeEnum) {

        this.columnExpressions = columnExpressions;
        this.sqlLikeEnum = sqlLikeEnum;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        ClickHouseLikeFunctionTemplateGenerator templateGenerator = new ClickHouseLikeFunctionTemplateGenerator(
                columnExpressions, sqlLikeEnum, o -> getColumnFuncValueExpression(o)
                , "positionCaseInsensitive({0}, {1}) = 1"
                , "{0} LIKE CONCAT('%', {1})"
                , "positionCaseInsensitive({0}, {1}) > 0"
                , "positionCaseInsensitive({0}, {1}) = 1"
                , "{0} LIKE CONCAT('%', {1})"
                , "positionCaseInsensitive({0}, {1}) > 0"
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
