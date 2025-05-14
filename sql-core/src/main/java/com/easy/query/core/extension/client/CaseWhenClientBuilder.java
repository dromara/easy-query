package com.easy.query.core.extension.client;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
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
public class CaseWhenClientBuilder<T1,TR> {
    private final ColumnAsSelector<T1,TR> columnAsSelector;
    private final CaseWhenBuilder caseWhenBuilder;

    public CaseWhenClientBuilder(ColumnAsSelector<T1,TR> columnAsSelector){
        this.columnAsSelector = columnAsSelector;
        this.caseWhenBuilder = new CaseWhenBuilder(columnAsSelector.getRuntimeContext(),columnAsSelector.getExpressionContext());
    }
    public CaseWhenClientBuilder<T1,TR> caseWhen(SQLActionExpression1<WherePredicate<T1>> predicateExpression, Object then){
        caseWhenBuilder.caseWhen(filter->{
            predicateExpression.apply(new WherePredicateImpl<>(columnAsSelector.getTable(),new FilterContext(filter,columnAsSelector.getAsSelector().getEntityQueryExpressionBuilder())));
        },then);
        return this;
    }
    public CloneableSQLSegment elseEnd(Object elseValue){
        return caseWhenBuilder.elseEnd(elseValue);
    }
    public CloneableSQLSegment elseEndColumn(TableAvailable table, String property){
        return caseWhenBuilder.elseEndColumn(table,property);
    }
    public CloneableSQLSegment elseEndColumn(SQLTableOwner sqlTableOwner, String property){
        return caseWhenBuilder.elseEndColumn(sqlTableOwner,property);
    }
}