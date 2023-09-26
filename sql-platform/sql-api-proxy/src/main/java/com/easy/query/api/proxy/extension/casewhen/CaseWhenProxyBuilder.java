package com.easy.query.api.proxy.extension.casewhen;

import com.easy.query.api.proxy.sql.ProxyAsSelector;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.impl.ProxyFilterImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.extension.casewhen.CaseWhenBuilder;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/7/3 08:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhenProxyBuilder<TRProxy extends ProxyEntity<TRProxy, TR>, TR> {
    private final CaseWhenBuilder caseWhenBuilder;

    public CaseWhenProxyBuilder(ProxyAsSelector<TRProxy,TR> proxyAsSelector){
        this.caseWhenBuilder=new CaseWhenBuilder(proxyAsSelector.getRuntimeContext(),proxyAsSelector.getExpressionContext());
    }
    public CaseWhenProxyBuilder<TRProxy,TR> caseWhen(SQLExpression1<ProxyFilter> predicateExpression, Object then){
        caseWhenBuilder.caseWhen(filter->{
            predicateExpression.apply(new ProxyFilterImpl(filter));
        },then);
        return this;
    }
    public CaseWhenProxyBuilder<TRProxy,TR> caseWhen(SQLExpression1<ProxyFilter> predicateExpression, SQLColumn<?,?> thenSQLColumn){
        caseWhenBuilder.caseWhenColumn(filter->{
            predicateExpression.apply(new ProxyFilterImpl(filter));
        },thenSQLColumn.getTable(),thenSQLColumn.value());
        return this;
    }
    public CloneableSQLSegment elseEnd(Object elseValue){
        return caseWhenBuilder.elseEnd(elseValue);
    }
    public CloneableSQLSegment elseEnd(SQLColumn<?, ?> elseSQLColumn){
        return caseWhenBuilder.elseEndColumn(elseSQLColumn.getTable(),elseSQLColumn.value());
    }
}