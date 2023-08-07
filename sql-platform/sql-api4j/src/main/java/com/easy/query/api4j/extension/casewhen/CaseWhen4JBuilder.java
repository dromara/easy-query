package com.easy.query.api4j.extension.casewhen;

import com.easy.query.api4j.sql.SQLColumnAsSelector;
import com.easy.query.api4j.sql.SQLWherePredicate;
import com.easy.query.api4j.sql.impl.SQLWherePredicateImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.impl.WherePredicateImpl;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.extension.casewhen.CaseWhenBuilder;

/**
 * create time 2023/7/3 08:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhen4JBuilder<T1,TR> {
    private final SQLColumnAsSelector<T1, TR> sqlColumnAsSelector;
    private final CaseWhenBuilder caseWhenBuilder;

    public CaseWhen4JBuilder(SQLColumnAsSelector<T1, TR> sqlColumnAsSelector){
        this.sqlColumnAsSelector = sqlColumnAsSelector;
        this.caseWhenBuilder=new CaseWhenBuilder(sqlColumnAsSelector.getRuntimeContext(),sqlColumnAsSelector.getExpressionContext());
    }
    public CaseWhen4JBuilder<T1,TR> caseWhen(SQLExpression1<SQLWherePredicate<T1>> predicateExpression,Object then){
        caseWhenBuilder.caseWhen(filter->{
            predicateExpression.apply(new SQLWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector.getTable(),filter)));
        },then);
        return this;
    }
    public CloneableSQLSegment elseEnd(Object elseValue){
        return caseWhenBuilder.elseEnd(elseValue);
    }
}