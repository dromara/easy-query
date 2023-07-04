package com.easy.query.api4kt.extension.casewhen;

import com.easy.query.api4kt.sql.SQLKtColumnAsSelector;
import com.easy.query.api4kt.sql.SQLKtWherePredicate;
import com.easy.query.api4kt.sql.impl.SQLKtWherePredicateImpl;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.base.impl.WherePredicateImpl;
import com.easy.query.core.expression.segment.SQLColumnSegment;
import com.easy.query.core.extension.casewhen.CaseWhenBuilder;

/**
 * create time 2023/7/3 08:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhen4KtBuilder2<T1,T2,TR> {
    private final SQLKtColumnAsSelector<T1, TR> sqlColumnAsSelector1;
    private final CaseWhenBuilder caseWhenBuilder;
    private final SQLKtColumnAsSelector<T2, TR> sqlColumnAsSelector2;

    public CaseWhen4KtBuilder2(SQLKtColumnAsSelector<T1, TR> sqlColumnAsSelector1,SQLKtColumnAsSelector<T2, TR> sqlColumnAsSelector2){
        this.sqlColumnAsSelector1 = sqlColumnAsSelector1;
        this.sqlColumnAsSelector2 = sqlColumnAsSelector2;
        this.caseWhenBuilder=new CaseWhenBuilder(sqlColumnAsSelector1.getRuntimeContext(),sqlColumnAsSelector1.getExpressionContext());
    }
    public CaseWhen4KtBuilder2<T1,T2,TR> caseWhen(SQLExpression2<SQLKtWherePredicate<T1>,SQLKtWherePredicate<T2>> predicateExpression, Object then){
        caseWhenBuilder.caseWhen(filter->{
            predicateExpression.apply(new SQLKtWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector1.getTable(),filter)),new SQLKtWherePredicateImpl<>(new WherePredicateImpl<>(sqlColumnAsSelector2.getTable(),filter)));
        },then);
        return this;
    }
    public SQLColumnSegment elseEnd(Object elseValue){
        return caseWhenBuilder.elseEnd(elseValue);
    }
}