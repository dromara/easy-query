package com.easy.query.api.proxy.entity.select.extension.queryable3;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.common.tuple.MergeTuple3;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression3;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Objects;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntitySelectable3<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2,
        T3Proxy extends ProxyEntity<T3Proxy, T3>, T3> extends ClientEntityQueryable3Available<T1, T2, T3>, EntityQueryable3Available<T1Proxy, T1, T2Proxy, T2, T3Proxy, T3> {

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> select(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, TRProxy> selectExpression) {

        TRProxy resultProxy = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy());
        return Select.selectProxy(resultProxy, getClientQueryable3());
    }

    default <TR> Query<TR> select(Class<TR> resultClass, SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLSelectAsExpression> selectExpression) {
        SQLSelectAsExpression sqlSelectAsExpression = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy());
        return getClientQueryable3().select(resultClass, columnAsSelector -> {
            sqlSelectAsExpression.accept(columnAsSelector.getAsSelector());
        });
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> selectMerge(SQLFuncExpression1<MergeTuple3<T1Proxy, T2Proxy, T3Proxy>, TRProxy> selectExpression) {
        return select((a, b, c) -> selectExpression.apply(new MergeTuple3<>(a, b, c)));
    }

    /**
     * 快速读取单列用于返回基本类型或者subQuery等查询
     * <blockquote><pre>
     *     {@code
     *          //如果您是枚举需要单独查询请转成integer或者具体数据库对应的值
     *          //直接返回单个列如果是Enum类型的不支持
     *         .selectColumn((t1,t2,t3) -> t2.enumProp().toNumber(Integer.class))
     *          //快速生成子查询
     *          Query<Enum> query = easyEntityQuery.queryable(EntityClass.class).where(o -> o.id().eq("123" )).selectColumn((t1,t2,t3) -> t1.enumProp());
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
    default <TR> Query<TR> selectColumn(SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, PropTypeColumn<TR>> selectExpression) {
        PropTypeColumn<TR> column = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy());
        Objects.requireNonNull(column, "select column null result class");
        ClientQueryable<?> select = getClientQueryable3().select(column.getPropertyType(), (t1, t2, t3) -> {
            PropTypeColumn.selectColumn(t1.getAsSelector(), column);
        });
        return EasyObjectUtil.typeCastNullable(select);
    }


    default <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLSelectAsExpression> selectExpression) {
        return selectAutoInclude(resultClass, selectExpression, false);
    }

    default <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, SQLFuncExpression3<T1Proxy, T2Proxy, T3Proxy, SQLSelectAsExpression> selectExpression, boolean replace) {
        SQLSelectAsExpression sqlSelectAsExpression = selectExpression.apply(get1Proxy(), get2Proxy(), get3Proxy());
        return getClientQueryable3().selectAutoInclude(resultClass, (columnAsSelector1, columnAsSelector2, columnAsSelector3) -> {
            if (sqlSelectAsExpression != null) {
                sqlSelectAsExpression.accept(columnAsSelector1.getAsSelector());
            }
        }, replace);
    }
}
