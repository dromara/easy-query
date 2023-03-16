package com.easy.query.mysql.expression;

import com.easy.query.core.query.EasySqlUpdateExpression;
import com.easy.query.core.query.SqlExpressionContext;

/**
 * @FileName: MySQLUpdateExpression.java
 * @Description: mysql数据库更新表达式
 * @Date: 2023/3/4 17:11
 * @Created by xuejiaming
 */
public class MySqlUpdateExpression extends EasySqlUpdateExpression {

    public MySqlUpdateExpression(SqlExpressionContext queryExpressionContext, boolean isExpressionUpdate) {
        super(queryExpressionContext,isExpressionUpdate);
    }

}
