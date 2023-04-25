package com.easy.query.mysql.expression;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.expression.sql.expression.impl.QuerySqlExpression;
import com.easy.query.core.expression.sql.factory.EasyExpressionFactory;
import com.easy.query.core.util.SqlSegmentUtil;

/**
 * create time 2023/4/23 13:05
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySqlQuerySqlExpression extends QuerySqlExpression {
    public MySqlQuerySqlExpression(EasyQueryRuntimeContext runtimeContext) {
        super(runtimeContext);
    }

    @Override
    public EasyQuerySqlExpression cloneSqlExpression() {
        EasyExpressionFactory expressionFactory = getRuntimeContext().getExpressionFactory();
        MySqlQuerySqlExpression easyQuerySqlExpression = (MySqlQuerySqlExpression)expressionFactory.createEasyQuerySqlExpression(getRuntimeContext());

        if(SqlSegmentUtil.isNotEmpty(where)){
            easyQuerySqlExpression.setWhere(where.clonePredicateSegment());
        }
        if(SqlSegmentUtil.isNotEmpty(group)){
            easyQuerySqlExpression.setGroup(group.cloneSqlBuilder());
        }
        if(SqlSegmentUtil.isNotEmpty(having)){
            easyQuerySqlExpression.setHaving(having.clonePredicateSegment());
        }
        if(SqlSegmentUtil.isNotEmpty(order)){
            easyQuerySqlExpression.setOrder(order.cloneSqlBuilder());
        }
        if(SqlSegmentUtil.isNotEmpty(projects)){
            easyQuerySqlExpression.setProjects(projects.cloneSqlBuilder());
        }
        easyQuerySqlExpression.offset = super.offset;
        easyQuerySqlExpression.rows = super.rows;
        for (EasyTableSqlExpression table : super.tables) {
            easyQuerySqlExpression.tables.add((EasyTableSqlExpression)table.cloneSqlExpression());
        }
        return easyQuerySqlExpression;
    }
}
