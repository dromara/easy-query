package com.easy.query.api4j.func;

import com.easy.query.api4j.func.column.SQLColumnFuncSelector;
import com.easy.query.api4j.func.column.SQLColumnFuncSelectorImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.func.SQLFuncAvailable;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.NumberCalcEnum;

/**
 * create time 2024/5/6 16:44
 * 文件说明
 *
 * @author xuejiaming
 */
public interface LambdaSQLNumberFunc<T1> extends SQLFuncAvailable {
    /**
     * 数字处理函数
     * @param sqlExpression 数字计算表达式
     * @return 数字处理函数
     */
   default SQLFunction numberCalc(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression, NumberCalcEnum numberCalcEnum){
       return getSQLFunc().numberCalc(x->{
           sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
       },numberCalcEnum);
   }
}
