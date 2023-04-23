package com.easy.query.mysql.expression;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyUpdateSqlExpression;
import com.easy.query.core.expression.sql.expression.impl.UpdateSqlExpression;
import com.easy.query.core.expression.sql.factory.EasyExpressionFactory;
import com.easy.query.core.util.SqlSegmentUtil;

/**
 * create time 2023/4/23 15:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySqlUpdateSqlExpression extends UpdateSqlExpression {
    public MySqlUpdateSqlExpression(EasyQueryRuntimeContext runtimeContext, EasyTableSqlExpression table) {
        super(runtimeContext, table);
    }

    @Override
    public EasySqlExpression cloneSqlExpression() {
        EasyExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EasyUpdateSqlExpression easyUpdateSqlExpression = expressionFactory.createEasyUpdateSqlExpression(runtimeContext, (EasyTableSqlExpression) tables.get(0).cloneSqlExpression());
        if(SqlSegmentUtil.isNotEmpty(where)){
            where.copyTo(easyUpdateSqlExpression.getWhere());
        }if(SqlSegmentUtil.isNotEmpty(setColumns)){
            setColumns.copyTo(easyUpdateSqlExpression.getSetColumns());
        }
        return easyUpdateSqlExpression;
    }
}
