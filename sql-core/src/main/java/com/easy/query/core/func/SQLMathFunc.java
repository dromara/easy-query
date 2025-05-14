package com.easy.query.core.func;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.func.column.ColumnFuncSelector;
import com.easy.query.core.func.def.enums.MathMethodEnum;

/**
 * create time 2023/12/21 12:00
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLMathFunc {
    /**
     * 数学函数
     * @param sqlExpression 数学参数
     * @return 数学函数
     */
    SQLFunction math(SQLActionExpression1<ColumnFuncSelector> sqlExpression, MathMethodEnum mathMethodEnum);

}
