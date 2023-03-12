package com.easy.query.mysql.base;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.query.EasySqlUpdateExpression;
import com.easy.query.core.query.SqlEntityTableExpression;
import com.easy.query.core.query.SqlExpressionContext;

/**
 * @FileName: MySQLUpdateExpression.java
 * @Description: mysql数据库更新表达式
 * @Date: 2023/3/4 17:11
 * @Created by xuejiaming
 */
public class MySQLUpdateExpression extends EasySqlUpdateExpression {

    public MySQLUpdateExpression(SqlExpressionContext queryExpressionContext, boolean isExpressionUpdate) {
        super(queryExpressionContext,isExpressionUpdate);
    }

}
