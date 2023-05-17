package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.SQLAnonymousEntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.expression.QuerySQLExpression;
import com.easy.query.core.expression.sql.expression.impl.AnonymousQuerySQLExpressionImpl;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

/**
 * create time 2023/3/31 10:59
 * 文件说明
 *
 * @author xuejiaming
 */
public class AnonymousQueryExpressionBuilder extends QueryExpressionBuilder implements SQLAnonymousEntityQueryExpressionBuilder {
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
    public QuerySQLExpression toExpression() {
        return new AnonymousQuerySQLExpressionImpl(getRuntimeContext(), sql);
    }



    @Override
    public EntityQueryExpressionBuilder cloneEntityExpressionBuilder() {
        AnonymousQueryExpressionBuilder anonymousQueryExpressionBuilder = new AnonymousQueryExpressionBuilder(sql, sqlExpressionContext);

        for (EntityTableExpressionBuilder table : super.tables) {
            anonymousQueryExpressionBuilder.tables.add(table.copyEntityTableExpressionBuilder());
        }
        return anonymousQueryExpressionBuilder;
    }
}
