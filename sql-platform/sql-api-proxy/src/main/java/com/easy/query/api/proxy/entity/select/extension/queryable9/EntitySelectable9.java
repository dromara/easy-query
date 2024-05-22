package com.easy.query.api.proxy.entity.select.extension.queryable9;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.DraftResult;
import com.easy.query.core.common.tuple.MergeTuple9;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression9;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.core.draft.DraftFetcher;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Objects;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntitySelectable9<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3,
        T4Proxy extends ProxyEntity<T4Proxy, T4>, T4,
        T5Proxy extends ProxyEntity<T5Proxy, T5>, T5,
        T6Proxy extends ProxyEntity<T6Proxy, T6>, T6,
        T7Proxy extends ProxyEntity<T7Proxy, T7>, T7,
        T8Proxy extends ProxyEntity<T8Proxy, T8>, T8,
        T9Proxy extends ProxyEntity<T9Proxy, T9>, T9> extends ClientEntityQueryable9Available<T1, T2, T3, T4, T5, T6, T7, T8, T9>, EntityQueryable9Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3, T4Proxy, T4, T5Proxy, T5, T6Proxy, T6, T7Proxy, T7, T8Proxy, T8, T9Proxy, T9> {

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> select(SQLFuncExpression9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, TRProxy> selectExpression) {

        TRProxy resultProxy = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy());
        return Select.selectProxy(resultProxy, getClientQueryable9());
    }

    default <TR> Query<TR> select(Class<TR> resultClass, SQLFuncExpression9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, SQLSelectAsExpression> selectExpression) {
        SQLSelectAsExpression sqlSelectAsExpression = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy());
        return getClientQueryable9().select(resultClass, columnAsSelector -> {
            sqlSelectAsExpression.accept(columnAsSelector.getAsSelector());
        });
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> selectMerge(SQLFuncExpression1<MergeTuple9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy>, TRProxy> selectExpression) {
        return select((a, b, c, d, e, f, g, h, i) -> selectExpression.apply(new MergeTuple9<>(a, b, c, d, e, f, g, h, i)));
    }

    /**
     * 快速读取单列用于返回基本类型或者subQuery等查询
     * <blockquote><pre>
     *     {@code
     *          //如果您是枚举需要单独查询请转成integer或者具体数据库对应的值
     *          //直接返回单个列如果是Enum类型的不支持
     *         .selectColumn((t1,t2,t3,t4,t5,t6,t7,t8,t9) -> t2.enumProp().toNumber(Integer.class))
     *          //快速生成子查询
     *          Query<Enum> query = easyEntityQuery.queryable(EntityClass.class).where(o -> o.id().eq("123" )).selectColumn((t1,t2,t3,t4,t5,t6,t7,t8,t9) -> t1.enumProp());
     *         List<EntityClass> list = easyEntityQuery.queryable(EntityClass.class).where(o -> {
     *             o.enumProp().in(query);
     *         }).toList();
     *
     *
     *                 }
     * </pre></blockquote>
     *
     * @param selectExpression
     * @param <TR>
     * @return
     */
    default <TR> Query<TR> selectColumn(SQLFuncExpression9<T1Proxy, T2Proxy, T3Proxy, T4Proxy, T5Proxy, T6Proxy, T7Proxy, T8Proxy, T9Proxy, PropTypeColumn<TR>> selectExpression) {
        PropTypeColumn<TR> column = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(), get5Proxy(), get6Proxy(), get7Proxy(), get8Proxy(), get9Proxy());
        Objects.requireNonNull(column, "select column null result class");
        ClientQueryable<?> select = getClientQueryable9().select(column.getPropertyType(), (t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
            PropTypeColumn.selectColumn(t1.getAsSelector(), column);
        });
        return EasyObjectUtil.typeCastNullable(select);
    }
    default <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, SQLFuncExpression9<T1Proxy, T2Proxy, T3Proxy, T4Proxy,T5Proxy,T6Proxy,T7Proxy,T8Proxy,T9Proxy, SQLSelectAsExpression> selectExpression) {
        return selectAutoInclude(resultClass, selectExpression, false);
    }

    default <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, SQLFuncExpression9<T1Proxy, T2Proxy, T3Proxy, T4Proxy,T5Proxy,T6Proxy,T7Proxy,T8Proxy,T9Proxy, SQLSelectAsExpression> selectExpression, boolean replace) {
        SQLSelectAsExpression sqlSelectAsExpression = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy(), get4Proxy(),get5Proxy(),get6Proxy(),get7Proxy(),get8Proxy(),get9Proxy());
        return getClientQueryable9().selectAutoInclude(resultClass, (columnAsSelector1, columnAsSelector2, columnAsSelector3, columnAsSelector4, columnAsSelector5, columnAsSelector6, columnAsSelector7, columnAsSelector8, columnAsSelector9) -> {
            if (sqlSelectAsExpression != null) {
                sqlSelectAsExpression.accept(columnAsSelector1.getAsSelector());
            }
        }, replace);
    }
}
