package com.easy.query.api.proxy.select.extension.queryable2.join;

import com.easy.query.api.proxy.select.ProxyQueryable3;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable3;
import com.easy.query.core.basic.api.select.ClientQueryable3;
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
public class EasyProxyJoinOnQueryable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1
        , T2Proxy extends ProxyEntity<T2Proxy, T2>, T2
        , T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> implements ProxyJoinOnQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {
    private final T1Proxy t1Proxy;
    private final T2Proxy t2Proxy;
    private final T3Proxy t3Proxy;
    private final ClientQueryable3<T1, T2, T3> clientQueryable;

    public EasyProxyJoinOnQueryable3(T1Proxy t1Proxy, T2Proxy t2Proxy, T3Proxy t3Proxy, ClientQueryable3<T1, T2, T3> clientQueryable) {
        this.t1Proxy = t1Proxy;
        this.t2Proxy = t2Proxy;
        this.t3Proxy = t3Proxy;

        this.clientQueryable = clientQueryable;
        this.t3Proxy.create(clientQueryable.getSQLEntityExpressionBuilder().getRecentlyTable().getEntityTable());
    }

    @Override
    public ProxyQueryable3<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> on(SQLPredicateExpression... onSQLPredicates) {
        ClientQueryable3<T1, T2, T3> joinedQueryable = EasySQLExpressionUtil.executeJoinOn(clientQueryable, (t, t1, t2) -> {
            if (EasyArrayUtil.isEmpty(onSQLPredicates)) {
                throw new EasyQueryInvalidOperationException("left join on sql predicates is empty");
            }
            SQLPredicateExpression sqlPredicate = Predicate.and(onSQLPredicates);
            sqlPredicate.accept(t.getFilter());
        });
        return new EasyProxyQueryable3<>(t1Proxy, t2Proxy, t3Proxy, joinedQueryable);
    }
}
