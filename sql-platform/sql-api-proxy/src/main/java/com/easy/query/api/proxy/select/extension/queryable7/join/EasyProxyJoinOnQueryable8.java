package com.easy.query.api.proxy.select.extension.queryable7.join;

import com.easy.query.api.proxy.select.ProxyQueryable8;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable8;
import com.easy.query.core.basic.api.select.ClientQueryable8;
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
public class EasyProxyJoinOnQueryable8<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1
        , T2Proxy extends ProxyEntity<T2Proxy, T2>, T2
        , T3Proxy extends ProxyEntity<T3Proxy, T3>, T3
        , T4Proxy extends ProxyEntity<T4Proxy, T4>, T4
        , T5Proxy extends ProxyEntity<T5Proxy, T5>, T5
        , T6Proxy extends ProxyEntity<T6Proxy, T6>, T6
        , T7Proxy extends ProxyEntity<T7Proxy, T7>, T7
        , T8Proxy extends ProxyEntity<T8Proxy, T8>, T8> implements ProxyJoinOnQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> {
    private final T1Proxy t1Proxy;
    private final T2Proxy t2Proxy;
    private final T3Proxy t3Proxy;
    private final T4Proxy t4Proxy;
    private final T5Proxy t5Proxy;
    private final T6Proxy t6Proxy;
    private final T7Proxy t7Proxy;
    private final T8Proxy t8Proxy;
    private final ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> clientQueryable;

    public EasyProxyJoinOnQueryable8(T1Proxy t1Proxy, T2Proxy t2Proxy, T3Proxy t3Proxy, T4Proxy t4Proxy, T5Proxy t5Proxy, T6Proxy t6Proxy, T7Proxy t7Proxy,T8Proxy t8Proxy, ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> clientQueryable) {
        this.t1Proxy = t1Proxy;
        this.t2Proxy = t2Proxy;
        this.t3Proxy = t3Proxy;
        this.t4Proxy = t4Proxy;
        this.t5Proxy = t5Proxy;
        this.t6Proxy = t6Proxy;
        this.t7Proxy = t7Proxy;
        this.t8Proxy = t8Proxy;

        this.clientQueryable = clientQueryable;
        this.t8Proxy.create(clientQueryable.getSQLEntityExpressionBuilder().getRecentlyTable().getEntityTable());
    }

    @Override
    public ProxyQueryable8<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8> on(SQLPredicateExpression... onSQLPredicates) {
        ClientQueryable8<T1, T2, T3, T4, T5, T6, T7, T8> joinedQueryable = EasySQLExpressionUtil.executeJoinOn(clientQueryable, (t, t1, t2, t3, t4, t5, t6, t7) -> {
            if (EasyArrayUtil.isEmpty(onSQLPredicates)) {
                throw new EasyQueryInvalidOperationException("left join on sql predicates is empty");
            }
            SQLPredicateExpression sqlPredicate = Predicate.and(onSQLPredicates);
            sqlPredicate.accept(t.getFilter());
        });
        return new EasyProxyQueryable8<>(t1Proxy, t2Proxy, t3Proxy, t4Proxy, t5Proxy, t6Proxy, t7Proxy,t8Proxy, joinedQueryable);
    }
}
