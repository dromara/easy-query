package com.easy.query.mysql.expression;

import com.easy.query.core.query.EasySqlDeleteExpression;
import com.easy.query.core.query.SqlExpressionContext;


/**
 * @FileName: MySQLDeleteExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:36
 * @Created by xuejiaming
 */
public class MySQLDeleteExpression extends EasySqlDeleteExpression {


    public MySQLDeleteExpression(SqlExpressionContext sqlExpressionContext, boolean expressionDelete) {
        super(sqlExpressionContext,expressionDelete);
    }

}
