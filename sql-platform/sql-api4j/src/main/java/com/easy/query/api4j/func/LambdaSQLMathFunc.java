package com.easy.query.api4j.func;

import com.easy.query.api4j.func.column.SQLColumnFuncSelector;
import com.easy.query.api4j.func.column.SQLColumnFuncSelectorImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.func.SQLFuncAvailable;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.func.def.enums.MathMethodEnum;

/**
 * create time 2024/5/6 16:45
 * 文件说明
 *
 * @author xuejiaming
 */
public interface LambdaSQLMathFunc<T1> extends SQLFuncAvailable  {
    /**
     * 数学函数
     * @param sqlExpression 数学参数
     * @return 数学函数
     */
  default SQLFunction math(SQLExpression1<SQLColumnFuncSelector<T1>> sqlExpression, MathMethodEnum mathMethodEnum){
      return getSQLFunc().math(x->{
          sqlExpression.apply(new SQLColumnFuncSelectorImpl<>(x));
      },mathMethodEnum);
  }
}
