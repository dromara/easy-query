package com.easy.query.mysql.expression;

import com.easy.query.core.expression.sql.def.EasyDeleteExpression;
import com.easy.query.core.expression.sql.ExpressionContext;


/**
 * @FileName: MySQLDeleteExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:36
 * @author xuejiaming
 */
public class MySqlDeleteExpression extends EasyDeleteExpression {


    public MySqlDeleteExpression(ExpressionContext sqlExpressionContext, boolean expressionDelete) {
        super(sqlExpressionContext,expressionDelete);
    }

}
