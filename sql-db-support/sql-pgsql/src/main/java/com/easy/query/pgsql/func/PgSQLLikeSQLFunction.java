package com.easy.query.pgsql.func;

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
public class PgSQLLikeSQLFunction extends AbstractLikeSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final SQLLikeEnum sqlLikeEnum;

    public PgSQLLikeSQLFunction(List<ColumnExpression> columnExpressions, SQLLikeEnum sqlLikeEnum) {

        this.columnExpressions = columnExpressions;
        this.sqlLikeEnum = sqlLikeEnum;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if(columnExpressions.size()!=2){
            throw new IllegalArgumentException("bank arguments != 1");
        }
        ColumnExpression columnExpression = columnExpressions.get(1);
        ColumnFuncValueExpression columnFuncValueExpression = getColumnFuncValueExpression(columnExpression);
        if (columnFuncValueExpression != null) {
            Object value = columnFuncValueExpression.getValue();
            if (value instanceof String) {
                String valueString = (String) value;
                if(valueString.contains("%")){
                    if(sqlLikeEnum==SQLLikeEnum.LIKE_PERCENT_RIGHT){
                        return "STRPOS({0},{1}) = 1";
                    }
                    if(sqlLikeEnum==SQLLikeEnum.LIKE_PERCENT_LEFT){
                        return "STRPOS({0},{1}) = (CHAR_LENGTH({0}) - CHAR_LENGTH({1}) + 1)";
                    }
                    return "STRPOS({0},{1}) > 0";
                }
            }
        }
        if(sqlLikeEnum==SQLLikeEnum.LIKE_PERCENT_RIGHT){
            return "{0} LIKE (CONCAT(({1})::TEXT , '%'))";
        }
        if(sqlLikeEnum==SQLLikeEnum.LIKE_PERCENT_LEFT){
            return "{0} LIKE (CONCAT('%' , ({1})::TEXT))";
        }
        return "{0} LIKE (CONCAT('%' , ({1})::TEXT , '%'))";
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
