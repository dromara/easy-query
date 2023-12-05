package com.easy.query.api.proxy.select.extension.queryable.join;

import com.easy.query.api.proxy.select.ProxyQueryable2;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLPredicateExpression;
import com.easy.query.core.proxy.sql.Predicate;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasySQLExpressionUtil;

/**
 * create time 2023/12/3 19:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyProxyJoinOnQueryable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1, T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> implements ProxyJoinOnQueryable2<T1Proxy,T1,T2Proxy,T2> {
    private final T1Proxy t1Proxy;
    private final T2Proxy t2Proxy;
    private final ClientQueryable2<T1, T2> clientQueryable2;

    public EasyProxyJoinOnQueryable2(T1Proxy t1Proxy, T2Proxy t2Proxy, ClientQueryable2<T1,T2> clientQueryable2){
        this.t1Proxy = t1Proxy;
        this.t2Proxy = t2Proxy;

        this.clientQueryable2 = clientQueryable2;
        this.t2Proxy.create(clientQueryable2.getSQLEntityExpressionBuilder().getRecentlyTable().getEntityTable());
    }
    @Override
    public ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> on(SQLPredicateExpression... onSQLPredicates) {
        ClientQueryable2<T1, T2> joinedQueryable2 = EasySQLExpressionUtil.executeJoinOn(clientQueryable2, (t, t1) -> {
            if (EasyArrayUtil.isEmpty(onSQLPredicates)) {
                throw new EasyQueryInvalidOperationException("left join on sql predicates is empty");
            }
            SQLPredicateExpression sqlPredicate = Predicate.and(onSQLPredicates);
            sqlPredicate.accept(t.getFilter());
        });
        return new EasyProxyQueryable2<>(t1Proxy,t2Proxy,joinedQueryable2);
    }
}
