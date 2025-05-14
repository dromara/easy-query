package com.easy.query.core.func;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.func.def.enums.NumberCalcEnum;

/**
 * create time 2023/12/21 12:00
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLNumberFunc {
    /**
     * 数字处理函数
     * @param sqlExpression 数字计算表达式
     * @return 数字处理函数
     */
    SQLFunction numberCalc(SQLActionExpression1<ColumnFuncSelector> sqlExpression, NumberCalcEnum numberCalcEnum);

}
