package com.easy.query.mysql.expression;

import com.easy.query.core.query.EasySqlDeleteExpression;
import com.easy.query.core.query.SqlExpressionContext;


/**
 * @FileName: MySQLDeleteExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:36
 * @Created by xuejiaming
 */
public class MySqlDeleteExpression extends EasySqlDeleteExpression {


    public MySqlDeleteExpression(SqlExpressionContext sqlExpressionContext, boolean expressionDelete) {
        super(sqlExpressionContext,expressionDelete);
    }

}
