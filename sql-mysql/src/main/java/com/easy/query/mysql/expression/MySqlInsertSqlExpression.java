package com.easy.query.mysql.expression;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.expression.sql.expression.EasyInsertSqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.expression.sql.expression.impl.InsertSqlExpression;
import com.easy.query.core.expression.sql.factory.EasyExpressionFactory;
import com.easy.query.core.util.SqlSegmentUtil;

/**
 * create time 2023/4/23 15:34
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySqlInsertSqlExpression extends InsertSqlExpression {
    public MySqlInsertSqlExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSqlExpression table) {
        super(runtimeContext, table);
    }

    @Override
    public EasySqlExpression cloneSqlExpression() {
        EasyExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();

        EasyInsertSqlExpression easyInsertSqlExpression = expressionFactory.createEasyInsertSqlExpression(runtimeContext,(EasyTableSqlExpression)tables.get(0).cloneSqlExpression());
        if(SqlSegmentUtil.isNotEmpty(columns)){
            columns.copyTo(easyInsertSqlExpression.getColumns());
        }
        return easyInsertSqlExpression;
    }
}
