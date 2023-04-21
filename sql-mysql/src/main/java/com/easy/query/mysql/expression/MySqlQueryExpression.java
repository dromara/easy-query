package com.easy.query.mysql.expression;

import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.expression.sql.factory.EasyExpressionFactory;
import com.easy.query.core.expression.sql.def.EasyQueryExpression;
import com.easy.query.core.expression.sql.EntityQueryExpression;
import com.easy.query.core.expression.sql.ExpressionContext;

/**
 * @FileName: MySQLEntityExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 13:04
 * @author xuejiaming
 */
public class MySqlQueryExpression extends EasyQueryExpression {
    public MySqlQueryExpression(ExpressionContext queryExpressionContext) {
        super(queryExpressionContext);
    }


    @Override
    public EntityExpression cloneEntityExpression() {

        ExpressionContext sqlExpressionContext = getExpressionContext();
        EasyExpressionFactory sqlExpressionFactory = getRuntimeContext().getSqlExpressionFactory();
        MySqlQueryExpression entityQueryExpression = (MySqlQueryExpression) sqlExpressionFactory.createEntityQueryExpression(sqlExpressionContext);
        if(hasWhere()){
            getWhere().copyTo(entityQueryExpression.getWhere());
        }
        if(hasGroup()){
            getGroup().copyTo(entityQueryExpression.getGroup());
        }
        if(hasHaving()){
            getHaving().copyTo(entityQueryExpression.getHaving());
        }
        if(hasOrder()){
            getOrder().copyTo(entityQueryExpression.getOrder());
        }
        getProjects().copyTo(entityQueryExpression.getProjects());
        entityQueryExpression.offset = super.offset;
        entityQueryExpression.rows = super.rows;
        for (EntityTableExpression table : super.tables) {
            entityQueryExpression.tables.add(table.copyEntityTableExpression());
        }
        return entityQueryExpression;
    }
}
