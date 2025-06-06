package com.easy.query.api.proxy.entity.select.extension.queryable2;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.common.tuple.MergeTuple2;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.fetcher.EntityFetcher;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Objects;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntitySelectable2<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1,
        T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> extends ClientEntityQueryable2Available<T1, T2>, EntityQueryable2Available<T1Proxy, T1, T2Proxy, T2> {


//    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> selectAny(SQLFuncExpression2<T1Proxy, T2Proxy, TRProxy> selectExpression) {
//        return select(selectExpression);
//    }

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> select(SQLFuncExpression2<T1Proxy, T2Proxy, TRProxy> selectExpression) {
//        TRProxy resultProxy = selectExpression.apply(get1Proxy(), get2Proxy());
//        return Select.selectProxy(resultProxy, getClientQueryable2());

        TRProxy resultProxy = selectExpression.apply(get1Proxy(), get2Proxy());
        if (resultProxy instanceof EntityFetcher) {
            EntityFetcher resultProxy1 = (EntityFetcher) resultProxy;
            return Select.selectProxy(EasyObjectUtil.typeCastNullable(resultProxy1.fetchProxy()), getClientQueryable2());
        }
        EntityQueryable<TRProxy, TR> trProxyTREntityQueryable = Select.selectProxy(resultProxy, getClientQueryable2());
        trProxyTREntityQueryable.get1Proxy().getEntitySQLContext().setContextHolder(get1Proxy().getEntitySQLContext().getContextHolder());
        return trProxyTREntityQueryable;
    }

    default <TR> Query<TR> select(Class<TR> resultClass, SQLFuncExpression2<T1Proxy, T2Proxy, SQLSelectAsExpression> selectExpression) {
        SQLSelectAsExpression sqlSelectAsExpression = selectExpression.apply(get1Proxy(), get2Proxy());
        return getClientQueryable2().select(resultClass, columnAsSelector -> {
            sqlSelectAsExpression.accept(columnAsSelector.getAsSelector());
        });
    }


    /**
     * 自动查询结构化DTO
     * <blockquote><pre>
     *     {@code
     *      List<SchoolClassAOProp6> list = easyEntityQuery.queryable(SchoolClass.class)
     *                         .leftJoin(Topic.class,(s, t2) -> s.id().eq(t2.id()))
     *                         .selectAutoInclude(SchoolClassAOProp6.class,(s,t2)->Select.of(
     *                                 //s.FETCHER.allFields(), //不需要执行
     *                                 t2.stars().nullOrDefault(1).as(SchoolClassAOProp6::getName1)
     *                         ))
     *                         .toList();
     *
     *                 }
     * </pre></blockquote>
     * @param resultClass 返回的结构化dto
     * @param extraSelectExpression 额外select表达式增强,默认会执行映射allFields所以不再需要执行主表得allFields
     * @return
     * @param <TR>
     */
    default <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, SQLFuncExpression2<T1Proxy, T2Proxy, SQLSelectAsExpression> extraSelectExpression) {
        return selectAutoInclude(resultClass, extraSelectExpression, false);
    }

    /**
     * 自动查询结构化DTO
     * <blockquote><pre>
     *     {@code
     *      List<SchoolClassAOProp6> list = easyEntityQuery.queryable(SchoolClass.class)
     *                         .leftJoin(Topic.class,(s, t2) -> s.id().eq(t2.id()))
     *                         .selectAutoInclude(SchoolClassAOProp6.class,(s,t2)->Select.of(
     *                                 //s.FETCHER.allFields(), //不需要执行
     *                                 t2.stars().nullOrDefault(1).as(SchoolClassAOProp6::getName1)
     *                         ),false)
     *                         .toList();
     *
     *                 }
     * </pre></blockquote>
     * @param resultClass 返回的结构化dto
     * @param extraSelectExpression 额外select表达式增强,默认会执行映射allFields所以不再需要执行主表得allFields
     * @param replace false:表示当前的selectAutoInclude里面的东西不去替换前面的include,如果false表示以表达式前面的include为准,true:表示以selectAutoInclude里面的为准
     * @return
     * @param <TR>
     */
    default <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, SQLFuncExpression2<T1Proxy, T2Proxy, SQLSelectAsExpression> extraSelectExpression, boolean replace) {
        SQLSelectAsExpression sqlSelectAsExpression = extraSelectExpression.apply(get1Proxy(), get2Proxy());
        return getClientQueryable2().selectAutoInclude(resultClass, (columnAsSelector1, columnAsSelector2) -> {
            if (sqlSelectAsExpression != null) {
                sqlSelectAsExpression.accept(columnAsSelector1.getAsSelector());
            }
        }, replace);
    }

    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> selectMerge(SQLFuncExpression1<MergeTuple2<T1Proxy, T2Proxy>, TRProxy> selectExpression) {
        return select((a, b) -> selectExpression.apply(new MergeTuple2<>(a, b)));
    }


    /**
     * 快速读取单列用于返回基本类型或者subQuery等查询
     * <blockquote><pre>
     *     {@code
     *          //如果您是枚举需要单独查询请转成integer或者具体数据库对应的值
     *          //直接返回单个列如果是Enum类型的不支持
     *         .selectColumn((t1,t2) -> t2.enumProp().toNumber(Integer.class))
     *          //快速生成子查询
     *          Query<Enum> query = easyEntityQuery.queryable(EntityClass.class).where(o -> o.id().eq("123" )).selectColumn((t1,t2) -> t1.enumProp());
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
    default <TR> Query<TR> selectColumn(SQLFuncExpression2<T1Proxy, T2Proxy, PropTypeColumn<TR>> selectExpression) {
        PropTypeColumn<TR> column = selectExpression.apply(get1Proxy(), get2Proxy());
        Objects.requireNonNull(column, "select column null result class");
        ClientQueryable<?> select = getClientQueryable2().select(column.getPropertyType(), (t1, t2) -> {
            PropTypeColumn.selectColumn(t1.getAsSelector(), column);
        });
        return EasyObjectUtil.typeCastNullable(select);
    }
}
