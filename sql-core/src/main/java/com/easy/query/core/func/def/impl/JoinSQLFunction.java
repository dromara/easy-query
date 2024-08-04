package com.easy.query.core.func.def.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;

import java.util.List;

/**
 * create time 2023/12/21 11:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class JoinSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final boolean distinct;

    public JoinSQLFunction(List<ColumnExpression> columnExpressions,boolean distinct) {

        this.columnExpressions = columnExpressions;
        this.distinct = distinct;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if(columnExpressions.size()!=2){
            throw new IllegalArgumentException("join arguments != 2");
        }
        if(distinct){
            return "GROUP_CONCAT(DISTINCT {1} SEPARATOR {0})";
        }
        return "GROUP_CONCAT({1} SEPARATOR {0})";
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
