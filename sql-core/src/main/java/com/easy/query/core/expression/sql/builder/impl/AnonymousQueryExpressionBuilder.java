package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.impl.AnonymousQuerySqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.SqlEntityQueryExpressionBuilder;

/**
 * create time 2023/3/31 10:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousQueryExpressionBuilder extends QueryExpressionBuilder implements SqlEntityQueryExpressionBuilder {
    private final String sql;

    public AnonymousQueryExpressionBuilder(String sql, ExpressionContext queryExpressionContext) {
        super(queryExpressionContext);
        this.sql = sql;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


    @Override
    public EasyQuerySqlExpression toExpression() {
        return new AnonymousQuerySqlExpression(getRuntimeContext(), sql,sqlExpressionContext.getExecuteMethod());
    }

//    @Override
//    public QueryExpressionBuilder cloneSqlQueryExpressionBuilder() {
//        AnonymousQueryExpressionBuilder anonymousQueryExpressionBuilder = new AnonymousQueryExpressionBuilder(sql, sqlExpressionContext);
//
//        for (EntityTableExpressionBuilder table : super.tables) {
//            anonymousQueryExpressionBuilder.tables.add(table.copyEntityTableExpressionBuilder());
//        }
//        return anonymousQueryExpressionBuilder;
//    }


    @Override
    public EntityQueryExpressionBuilder cloneEntityExpressionBuilder() {
        AnonymousQueryExpressionBuilder anonymousQueryExpressionBuilder = new AnonymousQueryExpressionBuilder(sql, sqlExpressionContext);

        for (EntityTableExpressionBuilder table : super.tables) {
            anonymousQueryExpressionBuilder.tables.add(table.copyEntityTableExpressionBuilder());
        }
        return anonymousQueryExpressionBuilder;
    }
}
