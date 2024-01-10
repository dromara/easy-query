package com.easy.query.core.func.def.impl;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;

import java.util.List;

/**
 * create time 2023/10/28 15:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class BankSQLFunction  extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;

    public BankSQLFunction(List<ColumnExpression> columnExpressions) {

        this.columnExpressions = columnExpressions;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if(columnExpressions.size()!=1){
            throw new IllegalArgumentException("bank arguments != 1");
        }
        return "({0} IS NULL OR {0} = '' OR LTRIM({0}) = '')";
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
