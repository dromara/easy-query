package com.easy.query.mysql.expression;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.expression.sql.expression.EasyDeleteSqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.expression.sql.expression.impl.DeleteSqlExpression;
import com.easy.query.core.expression.sql.factory.EasyExpressionFactory;
import com.easy.query.core.util.SqlSegmentUtil;

/**
 * create time 2023/4/23 15:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySqlDeleteSqlExpression extends DeleteSqlExpression {
    public MySqlDeleteSqlExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSqlExpression table) {
        super(runtimeContext, table);
    }

    @Override
    public EasySqlExpression cloneSqlExpression() {
        EasyExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EasyDeleteSqlExpression easyDeleteSqlExpression = expressionFactory.createEasyDeleteSqlExpression(runtimeContext, (EasyTableSqlExpression) tables.get(0).cloneSqlExpression());
        if(SqlSegmentUtil.isNotEmpty(where)){
            where.copyTo(easyDeleteSqlExpression.getWhere());
        }
        return easyDeleteSqlExpression;
    }
}
