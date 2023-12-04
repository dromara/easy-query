package com.easy.query.api.proxy.select.extension.queryable3.join;

import com.easy.query.api.proxy.select.ProxyQueryable4;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLPredicate;

/**
 * create time 2023/12/3 19:15
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyJoinOnQueryable4<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1
        , T2Proxy extends ProxyEntity<T2Proxy, T2>, T2
        , T3Proxy extends ProxyEntity<T3Proxy, T3>, T3
        , T4Proxy extends ProxyEntity<T4Proxy, T4>, T4>{
    ProxyQueryable4<T1Proxy,T1,T2Proxy,T2,T3Proxy,T3,T4Proxy,T4> on(SQLPredicate... onSQLPredicates);
}
