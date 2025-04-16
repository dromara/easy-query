package com.easy.query.core.extension.client;

import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.core.FilterContext;
import com.easy.query.core.expression.parser.core.base.impl.WherePredicateImpl;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.extension.casewhen.CaseWhenBuilder;

/**
 * create time 2023/7/3 09:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhenClientBuilder2<T1, T2, TR> {
    private final ColumnAsSelector<T1, TR> columnAsSelector1;
    private final ColumnAsSelector<T2, TR> columnAsSelector2;
    private final CaseWhenBuilder caseWhenBuilder;

    public CaseWhenClientBuilder2(ColumnAsSelector<T1, TR> columnAsSelector1, ColumnAsSelector<T2, TR> columnAsSelector2) {
        this.columnAsSelector1 = columnAsSelector1;
        this.columnAsSelector2 = columnAsSelector2;
        this.caseWhenBuilder = new CaseWhenBuilder(columnAsSelector1.getRuntimeContext(), columnAsSelector1.getExpressionContext());
    }

    public CaseWhenClientBuilder2<T1, T2, TR> caseWhen(SQLExpression2<WherePredicate<T1>,WherePredicate<T2>> predicateExpression, Object then) {
        caseWhenBuilder.caseWhen(filter -> {
            FilterContext filterContext = new FilterContext(filter,columnAsSelector1.getAsSelector().getEntityQueryExpressionBuilder());
            predicateExpression.apply(new WherePredicateImpl<>(columnAsSelector1.getTable(), filterContext),new WherePredicateImpl<>(columnAsSelector2.getTable(), filterContext));
        }, then);
        return this;
    }

    public CloneableSQLSegment elseEnd(Object elseValue) {
        return caseWhenBuilder.elseEnd(elseValue);
    }
}