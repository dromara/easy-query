package com.easy.query.core.proxy.core;

import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.SQLSelectAsExpression;

/**
 * create time 2024/5/13 16:27
 * 文件说明
 *
 * @author xuejiaming
 */
public interface FlatEntitySQLContext extends EntitySQLContext{
    SQLFuncExpression1<?, SQLSelectAsExpression> getSelectAsExpressionFunction();
}
