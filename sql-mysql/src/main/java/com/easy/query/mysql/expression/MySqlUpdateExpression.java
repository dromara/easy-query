package com.easy.query.mysql.expression;

import com.easy.query.core.expression.sql.def.EasySqlUpdateExpression;
import com.easy.query.core.expression.sql.SqlExpressionContext;

/**
 * @FileName: MySQLUpdateExpression.java
 * @Description: mysql数据库更新表达式
 * @Date: 2023/3/4 17:11
 * @author xuejiaming
 */
public class MySqlUpdateExpression extends EasySqlUpdateExpression {

    public MySqlUpdateExpression(SqlExpressionContext queryExpressionContext, boolean isExpressionUpdate) {
        super(queryExpressionContext,isExpressionUpdate);
    }

}
