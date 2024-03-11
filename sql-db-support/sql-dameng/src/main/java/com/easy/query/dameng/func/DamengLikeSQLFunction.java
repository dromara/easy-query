package com.easy.query.dameng.func;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.column.ColumnFuncValueExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;

import java.util.List;

/**
 * create time 2024/3/11 20:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class DamengLikeSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final SQLLikeEnum sqlLikeEnum;

    public DamengLikeSQLFunction(List<ColumnExpression> columnExpressions, SQLLikeEnum sqlLikeEnum) {

        this.columnExpressions = columnExpressions;
        this.sqlLikeEnum = sqlLikeEnum;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if(columnExpressions.size()!=2){
            throw new IllegalArgumentException("bank arguments != 1");
        }
        ColumnExpression columnExpression = columnExpressions.get(1);
        if(columnExpression instanceof ColumnFuncValueExpression){
            ColumnFuncValueExpression columnFuncValueExpression = (ColumnFuncValueExpression) columnExpression;
            Object value = columnFuncValueExpression.getValue();
            if(value!=null){
                String valueString = value.toString();
                if(valueString.contains("%")){
                    if(sqlLikeEnum==SQLLikeEnum.LIKE_PERCENT_RIGHT){
                        return "INSTR({1},{0}) = 1";
                    }
                    if(sqlLikeEnum==SQLLikeEnum.LIKE_PERCENT_LEFT){
                        return "INSTR({1},{0}) = LENGTH({0})";
                    }
                    return "INSTR({1},{0}) > 0";
                }
            }
        }
        if(sqlLikeEnum==SQLLikeEnum.LIKE_PERCENT_RIGHT){
            return "{0} LIKE (TO_CHAR({1})||'%')";
        }
        if(sqlLikeEnum==SQLLikeEnum.LIKE_PERCENT_LEFT){
            return "{0} LIKE ('%'||TO_CHAR({1}))";
        }
        return "{0} LIKE ('%'||TO_CHAR({1})||'%')";
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
