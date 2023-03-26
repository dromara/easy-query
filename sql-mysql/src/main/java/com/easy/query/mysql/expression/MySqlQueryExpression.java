package com.easy.query.mysql.expression;

import com.easy.query.core.abstraction.EasyExpressionFactory;
import com.easy.query.core.expression.sql.def.EasySqlQueryExpression;
import com.easy.query.core.expression.sql.SqlEntityQueryExpression;
import com.easy.query.core.expression.sql.SqlExpressionContext;

/**
 * @FileName: MySQLEntityExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 13:04
 * @author xuejiaming
 */
public class MySqlQueryExpression extends EasySqlQueryExpression {
    public MySqlQueryExpression(SqlExpressionContext queryExpressionContext) {
        super(queryExpressionContext);
    }



    @Override
    public SqlEntityQueryExpression cloneSqlQueryExpression() {

        SqlExpressionContext sqlExpressionContext = getSqlExpressionContext();
        EasyExpressionFactory sqlExpressionFactory = getRuntimeContext().getSqlExpressionFactory();
        MySqlQueryExpression sqlEntityQueryExpression = (MySqlQueryExpression) sqlExpressionFactory.createSqlEntityQueryExpression(sqlExpressionContext);
        if(hasWhere()){
            getWhere().copyTo(sqlEntityQueryExpression.getWhere());
        }
        if(hasGroup()){
            getGroup().copyTo(sqlEntityQueryExpression.getGroup());
        }
        if(hasHaving()){
            getHaving().copyTo(sqlEntityQueryExpression.getHaving());
        }
        if(hasOrder()){
            getOrder().copyTo(sqlEntityQueryExpression.getOrder());
        }
        getProjects().copyTo(sqlEntityQueryExpression.getProjects());
        sqlEntityQueryExpression.offset = super.offset;
        sqlEntityQueryExpression.rows = super.rows;
        sqlEntityQueryExpression.logicDelete=super.logicDelete;
        sqlEntityQueryExpression.tables.addAll(super.tables);
        return sqlEntityQueryExpression;
    }
}
