package com.easy.query.mysql.base;

import com.easy.query.core.abstraction.EasySqlExpressionFactory;
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
        EasySqlExpressionFactory sqlExpressionFactory = getRuntimeContext().getSqlExpressionFactory();
        MySQLQueryExpression sqlEntityQueryExpression = (MySQLQueryExpression) sqlExpressionFactory.createSqlEntityQueryExpression(sqlExpressionContext);
        sqlEntityQueryExpression.where = super.where;
        sqlEntityQueryExpression.group = super.group;
        sqlEntityQueryExpression.having = super.having;
        sqlEntityQueryExpression.order = super.order;
        sqlEntityQueryExpression.offset = super.offset;
        sqlEntityQueryExpression.rows = super.rows;
        List<SqlSegment> sqlSegments = super.projects.getSqlSegments();
        sqlEntityQueryExpression.projects.getSqlSegments().addAll(sqlSegments);
        sqlEntityQueryExpression.tables.addAll(super.tables);
        return sqlEntityQueryExpression;
    }
}
