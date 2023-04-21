package com.easy.query.mysql.expression;

import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.expression.sql.def.EasyDeleteExpression;
import com.easy.query.core.expression.sql.ExpressionContext;
import com.easy.query.core.expression.sql.factory.EasyExpressionFactory;


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

    @Override
    public EntityExpression cloneEntityExpression() {

        ExpressionContext sqlExpressionContext = getExpressionContext();
        EasyExpressionFactory sqlExpressionFactory = getRuntimeContext().getSqlExpressionFactory();
        MySqlDeleteExpression entityDeleteExpression = (MySqlDeleteExpression) sqlExpressionFactory.createEntityDeleteExpression(sqlExpressionContext,isExpression());

        if(hasWhere()){
            getWhere().copyTo(entityDeleteExpression.getWhere());
        }
        if(hasWhereColumns()){
            getWhereColumns().copyTo(entityDeleteExpression.getWhereColumns());
        }
        for (EntityTableExpression table : super.tables) {
            entityDeleteExpression.tables.add(table.copyEntityTableExpression());
        }
        return entityDeleteExpression;
    }
}
