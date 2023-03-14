package com.easy.query.mysql.expression;

import com.easy.query.core.abstraction.EasySQLExpressionFactory;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.query.EasySqlQueryExpression;
import com.easy.query.core.query.SqlEntityQueryExpression;
import com.easy.query.core.query.SqlExpressionContext;

import java.util.List;

/**
 * @FileName: MySQLEntityExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 13:04
 * @Created by xuejiaming
 */
public class MySQLQueryExpression extends EasySqlQueryExpression {
    public MySQLQueryExpression(SqlExpressionContext queryExpressionContext) {
        super(queryExpressionContext);
    }



    @Override
    public SqlEntityQueryExpression cloneSqlQueryExpression() {

        SqlExpressionContext sqlExpressionContext = getSqlExpressionContext();
        EasySQLExpressionFactory sqlExpressionFactory = getRuntimeContext().getSqlExpressionFactory();
        MySQLQueryExpression sqlEntityQueryExpression = (MySQLQueryExpression) sqlExpressionFactory.createSqlEntityQueryExpression(sqlExpressionContext);
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
        sqlEntityQueryExpression.tables.addAll(super.tables);
        return sqlEntityQueryExpression;
    }
}
