package com.easy.query.core.extension.client;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.impl.WherePredicateImpl;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.extension.casewhen.CaseWhenBuilder;

/**
 * create time 2023/7/3 09:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhenClientBuilder<T1,TR> {
    private final ColumnAsSelector<T1,TR> columnAsSelector;
    private final CaseWhenBuilder caseWhenBuilder;

    public CaseWhenClientBuilder(ColumnAsSelector<T1,TR> columnAsSelector){
        this.columnAsSelector = columnAsSelector;
        this.caseWhenBuilder = new CaseWhenBuilder(columnAsSelector.getRuntimeContext(),columnAsSelector.getExpressionContext());
    }
    public CaseWhenClientBuilder<T1,TR> caseWhen(SQLExpression1<WherePredicate<T1>> predicateExpression, Object then){
        caseWhenBuilder.caseWhen(filter->{
            predicateExpression.apply(new WherePredicateImpl<>(columnAsSelector.getTable(),filter));
        },then);
        return this;
    }
    public CloneableSQLSegment elseEnd(Object elseValue){
        return caseWhenBuilder.elseEnd(elseValue);
    }
}