package com.easy.query.mysql.expression;

import com.easy.query.core.expression.sql.def.EasyUpdateExpression;
import com.easy.query.core.expression.sql.ExpressionContext;

/**
 * @FileName: MySQLUpdateExpression.java
 * @Description: mysql数据库更新表达式
 * @Date: 2023/3/4 17:11
 * @author xuejiaming
 */
public class MySqlUpdateExpression extends EasyUpdateExpression {

    public MySqlUpdateExpression(ExpressionContext queryExpressionContext, boolean isExpressionUpdate) {
        super(queryExpressionContext,isExpressionUpdate);
    }

}
