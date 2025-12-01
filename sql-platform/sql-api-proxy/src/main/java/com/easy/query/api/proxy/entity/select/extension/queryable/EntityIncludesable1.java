package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLQueryable;

/**
 * create time 2023/8/17 13:34
 * 建议使用{@link EntityIncludeable1#include2(SQLActionExpression2)}
 *
 * @author xuejiaming
 */
@Deprecated
public interface EntityIncludesable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ClientEntityQueryableAvailable<T1>, EntityQueryableAvailable<T1Proxy, T1> {

    /**
     * 建议使用{@link EntityIncludeable1#include2(SQLActionExpression2)}
     * <blockquote><pre>
     * {@code
     * easyEntityQuery.queryable(SchoolStudent.class)
     *                         .include(o->o.schoolClass())
     *                    }
     * </pre></blockquote>
     *
     * @param navigateIncludeSQLExpression
     * @param <TPropertyProxy>
     * @param <TProperty>
     * @return
     */
    @Deprecated
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty > EntityQueryable<T1Proxy, T1> includes(SQLFuncExpression1<T1Proxy, SQLQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
        return includes(navigateIncludeSQLExpression, null, null);
    }

    /**
     * 建议使用{@link EntityIncludeable1#include2(SQLActionExpression2)}
     * @param condition
     * @param navigateIncludeSQLExpression
     * @return
     * @param <TPropertyProxy>
     * @param <TProperty>
     */
    @Deprecated
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty > EntityQueryable<T1Proxy, T1> includes(boolean condition, SQLFuncExpression1<T1Proxy, SQLQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
        return includes(condition, navigateIncludeSQLExpression, null, null);
    }

    /**
     * 建议使用{@link EntityIncludeable1#include2(SQLActionExpression2)}
     * <blockquote><pre>
     * {@code
     * easyEntityQuery.queryable(SchoolStudent.class)
     *                         .include(o->o.schoolClass(),100)
     *                    }
     * </pre></blockquote>
     *
     * @param navigateIncludeSQLExpression
     * @param groupSize
     * @param <TPropertyProxy>
     * @param <TProperty>
     * @return
     */
    @Deprecated
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty > EntityQueryable<T1Proxy, T1> includes(SQLFuncExpression1<T1Proxy, SQLQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression, Integer groupSize) {
        return includes(navigateIncludeSQLExpression, null, groupSize);
    }

    /**
     * 建议使用{@link EntityIncludeable1#include2(SQLActionExpression2)}
     * @param condition
     * @param navigateIncludeSQLExpression
     * @param groupSize
     * @return
     * @param <TPropertyProxy>
     * @param <TProperty>
     */
    @Deprecated
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty > EntityQueryable<T1Proxy, T1> includes(boolean condition, SQLFuncExpression1<T1Proxy, SQLQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression, Integer groupSize) {
        return includes(condition, navigateIncludeSQLExpression, null, groupSize);
    }

    /**
     * 建议使用{@link EntityIncludeable1#include2(SQLActionExpression2)}
     * <blockquote><pre>
     * {@code
     * easyEntityQuery.queryable(SchoolStudent.class)
     *                         .include(o->o.schoolClass()，q->q.where(x->x.name().like("123)))
     *                    }
     * </pre></blockquote>
     *
     * @param navigateIncludeSQLExpression
     * @param includeAdapterExpression
     * @param <TPropertyProxy>
     * @param <TProperty>
     * @return
     */
    @Deprecated
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty > EntityQueryable<T1Proxy, T1> includes(SQLFuncExpression1<T1Proxy, SQLQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression, SQLActionExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression) {
        return includes(true, navigateIncludeSQLExpression, includeAdapterExpression, null);
    }

    /**
     * 建议使用{@link EntityIncludeable1#include2(SQLActionExpression2)}
     * @param condition
     * @param navigateIncludeSQLExpression
     * @param includeAdapterExpression
     * @return
     * @param <TPropertyProxy>
     * @param <TProperty>
     */
    @Deprecated
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty > EntityQueryable<T1Proxy, T1> includes(boolean condition, SQLFuncExpression1<T1Proxy, SQLQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression, SQLActionExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression) {
        return includes(condition, navigateIncludeSQLExpression, includeAdapterExpression, null);
    }

    /**
     * 建议使用{@link EntityIncludeable1#include2(SQLActionExpression2)}
     * @param navigateIncludeSQLExpression
     * @param includeAdapterExpression
     * @param groupSize
     * @return
     * @param <TPropertyProxy>
     * @param <TProperty>
     */
    @Deprecated
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty > EntityQueryable<T1Proxy, T1> includes(SQLFuncExpression1<T1Proxy, SQLQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression, SQLActionExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression, Integer groupSize) {
        return includes(true, navigateIncludeSQLExpression, includeAdapterExpression, groupSize);
    }

    /**
     * <blockquote><pre>
     * {@code
     * easyEntityQuery.queryable(SchoolStudent.class)
     *                         .include(o->o.schoolClass()，q->q.where(x->x.name().like("123)),100)
     *                    }
     * </pre></blockquote>
     *
     * @param navigateIncludeSQLExpression
     * @param includeAdapterExpression
     * @param groupSize
     * @param <TPropertyProxy>
     * @param <TProperty>
     * @return
     */
    @Deprecated
    <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty > EntityQueryable<T1Proxy, T1> includes(boolean condition, SQLFuncExpression1<T1Proxy, SQLQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression, SQLActionExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression, Integer groupSize);


}
