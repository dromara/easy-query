package com.easy.query.core.proxy.columns;

import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2024/6/5 08:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLManyQueryable<TProxy,T1Proxy extends ProxyEntity<T1Proxy, T1>, T1>  extends SQLQueryable<T1Proxy,T1>{

    void _setProxy(TProxy tProxy);

    @Override
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TProxy set(SQLQueryable<TPropertyProxy, TProperty> columnProxy){
        return set(columnProxy,null);
    }

    @Override
    <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> TProxy set(SQLQueryable<TPropertyProxy, TProperty> columnProxy, SQLFuncExpression1<TPropertyProxy, ProxyEntity<T1Proxy, T1>> navigateSelectExpression);
}
