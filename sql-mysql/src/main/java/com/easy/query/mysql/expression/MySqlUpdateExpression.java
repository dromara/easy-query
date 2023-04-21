package com.easy.query.mysql.expression;

import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.expression.sql.def.EasyUpdateExpression;
import com.easy.query.core.expression.sql.ExpressionContext;
import com.easy.query.core.expression.sql.factory.EasyExpressionFactory;

/**
 * @FileName: MySQLUpdateExpression.java
 * @Description: mysql数据库更新表达式
 * @Date: 2023/3/4 17:11
 * @author xuejiaming
 */
public class MySqlUpdateExpression extends EasyUpdateExpression {

    public MySqlUpdateExpression(ExpressionContext queryExpressionContext, boolean isExpressionUpdate) {
        super(queryExpressionContext,isExpressionUpdate);
    }

    @Override
    public EntityExpression cloneEntityExpression() {

        ExpressionContext sqlExpressionContext = getExpressionContext();
        EasyExpressionFactory sqlExpressionFactory = getRuntimeContext().getSqlExpressionFactory();
        MySqlUpdateExpression entityUpdateExpression = (MySqlUpdateExpression) sqlExpressionFactory.createEntityUpdateExpression(sqlExpressionContext,isExpression());

        if(hasSetColumns()){
            getSetColumns().copyTo(entityUpdateExpression.getSetColumns());
        }
        if(hasWhere()){
            getWhere().copyTo(entityUpdateExpression.getWhere());
        }
        if(hasSetIgnoreColumns()){
            getSetIgnoreColumns().copyTo(entityUpdateExpression.getSetIgnoreColumns());
        }
        if(hasWhereColumns()){
            getWhereColumns().copyTo(entityUpdateExpression.getWhereColumns());
        }
        for (EntityTableExpression table : super.tables) {
            entityUpdateExpression.tables.add(table.copyEntityTableExpression());
        }
        return entityUpdateExpression;
    }
}
