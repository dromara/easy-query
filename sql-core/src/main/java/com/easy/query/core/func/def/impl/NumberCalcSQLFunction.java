package com.easy.query.core.func.def.impl;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.column.ColumnExpression;
import com.easy.query.core.func.def.AbstractExpressionSQLFunction;
import com.easy.query.core.func.def.enums.NumberCalcEnum;

import java.util.List;

/**
 * create time 2023/10/12 15:46
 * 文件说明
 *
 * @author xuejiaming
 */
public class NumberCalcSQLFunction extends AbstractExpressionSQLFunction {
    private final List<ColumnExpression> columnExpressions;
    private final NumberCalcEnum numberCalcEnum;

    public NumberCalcSQLFunction(List<ColumnExpression> columnExpressions, NumberCalcEnum numberCalcEnum) {

        this.columnExpressions = columnExpressions;
        this.numberCalcEnum = numberCalcEnum;
    }

    @Override
    public String sqlSegment(TableAvailable defaultTable) {
        if(columnExpressions.size()<2){
            throw new EasyQueryInvalidOperationException("NumberCalcSQLFunction columnExpressions size < 2");
        }
        switch (numberCalcEnum){
            case NUMBER_ADD:
                return "({0} + {1})";
            case NUMBER_SUBTRACT:
                return "({0} - {1})";
            case NUMBER_MULTIPLY:
                return "({0} * {1})";
            case NUMBER_DEVIDE:
                return "({0} / {1})";
        }
        throw new UnsupportedOperationException("不支持当前函数NumberCalcSQLFunction:"+ numberCalcEnum);
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
