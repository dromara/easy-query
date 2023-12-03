package com.easy.query.api.proxy.select.extension.queryable.join;

import com.easy.query.api.proxy.select.ProxyQueryable2;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLPredicate;

/**
 * create time 2023/12/3 19:15
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyJoinOnQueryable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2>{
    ProxyQueryable2<T1Proxy,T1,T2Proxy,T2> on(SQLPredicate... onSQLPredicates);
}
