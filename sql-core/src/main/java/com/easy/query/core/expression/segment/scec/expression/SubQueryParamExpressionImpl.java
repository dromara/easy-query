package com.easy.query.core.expression.segment.scec.expression;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.visitor.TableVisitor;

/**
 * create time 2023/8/16 21:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class SubQueryParamExpressionImpl implements SubQueryParamExpression{
    private final Query<?> subQuery;

    public SubQueryParamExpressionImpl(Query<?> subQuery){

        this.subQuery = subQuery;
    }
    @Override
    public String toSQL(ToSQLContext toSQLContext) {
        return "("+subQuery.cloneQueryable().toSQL(toSQLContext)+")";
    }

    @Override
    public void accept(TableVisitor visitor) {
        subQuery.getSQLEntityExpressionBuilder().accept(visitor);
    }
}
