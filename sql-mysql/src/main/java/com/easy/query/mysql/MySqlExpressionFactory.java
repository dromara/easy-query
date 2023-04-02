package com.easy.query.mysql;

import com.easy.query.core.expression.sql.factory.AbstractEasyExpressionFactory;
import com.easy.query.core.expression.sql.EntityDeleteExpression;
import com.easy.query.core.expression.sql.EntityInsertExpression;
import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.expression.sql.EntityUpdateExpression;
import com.easy.query.core.expression.sql.ExpressionContext;
import com.easy.query.mysql.expression.MySqlInsertExpression;
import com.easy.query.mysql.expression.MySqlDeleteExpression;
import com.easy.query.mysql.expression.MySqlQueryExpression;
import com.easy.query.mysql.expression.MySqlUpdateExpression;

/**
 * @FileName: MySQLSqlExpressionFactory.java
 * @Description: 文件说明
 * @Date: 2023/3/4 22:55
 * @author xuejiaming
 */
public class MySqlExpressionFactory extends AbstractEasyExpressionFactory {

    @Override
    public EntityQueryExpression createEntityQueryExpression(ExpressionContext sqlExpressionContext) {
        return new MySqlQueryExpression(sqlExpressionContext);
    }

    @Override
    public EntityInsertExpression createEntityInsertExpression(ExpressionContext sqlExpressionContext) {
        return new MySqlInsertExpression(sqlExpressionContext);
    }

    @Override
    public EntityUpdateExpression createEntityUpdateExpression(ExpressionContext sqlExpressionContext, boolean expression) {
        return new MySqlUpdateExpression(sqlExpressionContext,expression);
    }

    @Override
    public EntityDeleteExpression createEntityDeleteExpression(ExpressionContext sqlExpressionContext, boolean expression) {
        return new MySqlDeleteExpression(sqlExpressionContext,expression);
    }
}
