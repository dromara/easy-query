package org.easy.query.mysql;

import org.easy.query.core.abstraction.EasySqlExpressionFactory;
import org.easy.query.core.query.*;
import org.easy.query.mysql.base.MySQLDeleteExpression;
import org.easy.query.mysql.base.MySQLInsertExpression;
import org.easy.query.mysql.base.MySQLQueryExpression;
import org.easy.query.mysql.base.MySQLUpdateExpression;

/**
 * @FileName: MySQLSqlExpressionFactory.java
 * @Description: 文件说明
 * @Date: 2023/3/4 22:55
 * @Created by xuejiaming
 */
public class MySQLSqlExpressionFactory implements EasySqlExpressionFactory {
    @Override
    public SqlEntityQueryExpression createSqlEntityQueryExpression(SqlExpressionContext sqlExpressionContext) {
        return new MySQLQueryExpression(sqlExpressionContext);
    }

    @Override
    public SqlEntityInsertExpression createSqlEntityInsertExpression(SqlExpressionContext sqlExpressionContext) {
        return new MySQLInsertExpression(sqlExpressionContext);
    }

    @Override
    public SqlEntityUpdateExpression createSqlEntityUpdateExpression(SqlExpressionContext sqlExpressionContext,boolean expression) {
        return new MySQLUpdateExpression(sqlExpressionContext,expression);
    }

    @Override
    public SqlEntityDeleteExpression createSqlEntityDeleteExpression(SqlExpressionContext sqlExpressionContext,boolean expression) {
        return new MySQLDeleteExpression(sqlExpressionContext,expression);
    }
}
