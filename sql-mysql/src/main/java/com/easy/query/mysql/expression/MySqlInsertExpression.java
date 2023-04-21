package com.easy.query.mysql.expression;

import com.easy.query.core.expression.sql.EntityExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.expression.sql.def.EasyInsertExpression;
import com.easy.query.core.expression.sql.ExpressionContext;
import com.easy.query.core.expression.sql.factory.EasyExpressionFactory;

/**
 * @FileName: MySQLInsertExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:51
 * @author xuejiaming
 */
public class MySqlInsertExpression extends EasyInsertExpression {
    public MySqlInsertExpression(ExpressionContext queryExpressionContext) {
        super(queryExpressionContext);
    }


    @Override
    public EntityExpression cloneEntityExpression() {

        ExpressionContext sqlExpressionContext = getExpressionContext();
        EasyExpressionFactory sqlExpressionFactory = getRuntimeContext().getSqlExpressionFactory();
        MySqlInsertExpression entityInsertExpression = (MySqlInsertExpression) sqlExpressionFactory.createEntityInsertExpression(sqlExpressionContext);
        if(getColumns().isNotEmpty()){
            getColumns().copyTo(entityInsertExpression.getColumns());
        }
        for (EntityTableExpression table : super.tables) {
            entityInsertExpression.tables.add(table.copyEntityTableExpression());
        }
        return entityInsertExpression;
    }
}
