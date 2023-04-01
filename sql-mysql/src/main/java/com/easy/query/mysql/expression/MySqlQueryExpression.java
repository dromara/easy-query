package com.easy.query.mysql.expression;

import com.easy.query.core.abstraction.EasyExpressionFactory;
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
    public EntityQueryExpression cloneSqlQueryExpression() {

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
        entityQueryExpression.tables.addAll(super.tables);
        return entityQueryExpression;
    }
}
