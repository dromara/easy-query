package com.easy.query.api.proxy.extension.casewhen;

import com.easy.query.api.proxy.sql.ProxyAsSelector;
import com.easy.query.api.proxy.sql.ProxyFilter;
import com.easy.query.api.proxy.sql.impl.ProxyFilterImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.segment.SQLColumnSegment;
import com.easy.query.core.extension.casewhen.CaseWhenBuilder;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/7/3 08:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class CaseWhenProxyBuilder<TRProxy extends ProxyEntity<TRProxy, TR>, TR> {
    private final CaseWhenBuilder caseWhenBuilder;
    private final ProxyAsSelector<TRProxy, TR> proxyAsSelector;

    public CaseWhenProxyBuilder(ProxyAsSelector<TRProxy,TR> proxyAsSelector){
        this.proxyAsSelector = proxyAsSelector;
        this.caseWhenBuilder=new CaseWhenBuilder(proxyAsSelector.getRuntimeContext(),proxyAsSelector.getExpressionContext());
    }
    public CaseWhenProxyBuilder<TRProxy,TR> caseWhen(SQLExpression1<ProxyFilter> predicateExpression, Object then){
        caseWhenBuilder.caseWhen(filter->{
            predicateExpression.apply(new ProxyFilterImpl(filter));
        },then);
        return this;
    }
    public SQLColumnSegment elseEnd(Object elseValue){
        return caseWhenBuilder.elseEnd(elseValue);
    }
}