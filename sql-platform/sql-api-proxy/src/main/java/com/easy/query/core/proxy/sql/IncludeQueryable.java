package com.easy.query.core.proxy.sql;

import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.IncludeAvailable;
import com.easy.query.core.proxy.ProxyEntity;


/**
 * create time 2025/8/14 19:38
 * 文件说明
 *
 * @author xuejiaming
 */
public interface IncludeQueryable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends IncludeAvailable {
    T1Proxy getProxy();

    IncludeQueryable<T1Proxy, T1> where(SQLActionExpression1<T1Proxy> filter);

    IncludeQueryable<T1Proxy, T1> orderBy(SQLActionExpression1<T1Proxy> orderBy);

    IncludeQueryable<T1Proxy, T1> limit(long offset, long rows);

    <TRProxy extends ProxyEntity<TRProxy, TR>, TR> IncludeQueryable<T1Proxy, T1> select(SQLFuncExpression1<T1Proxy, TRProxy> selector);

}
