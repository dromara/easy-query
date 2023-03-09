package org.easy.query.mysql.base;

import org.easy.query.core.exception.EasyQueryException;
import org.easy.query.core.query.EasySqlUpdateExpression;
import org.easy.query.core.query.SqlEntityTableExpression;
import org.easy.query.core.query.SqlExpressionContext;

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
