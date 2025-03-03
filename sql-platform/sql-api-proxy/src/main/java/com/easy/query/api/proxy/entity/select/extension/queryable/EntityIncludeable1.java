package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.sql.include.IncludeExpression;

/**
 * create time 2023/8/17 13:34
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityIncludeable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ClientEntityQueryableAvailable<T1>, EntityQueryableAvailable<T1Proxy, T1> {

//   <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> includeMany(SQLExpression1<T1Proxy> navigateIncludeSQLExpression);
    /**
     * <blockquote><pre>
     * easyEntityQuery.queryable(SchoolStudent.class)
     *                         .include(o->o.schoolClass())
     * </pre></blockquote>
     *
     *
     * @param navigateIncludeSQLExpression
     * @param <TPropertyProxy>
     * @param <TProperty>
     * @return
     */
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression1<T1Proxy, TPropertyProxy> navigateIncludeSQLExpression) {
        return include(navigateIncludeSQLExpression, o -> {
        }, null);
    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(boolean condition, SQLFuncExpression1<T1Proxy, TPropertyProxy> navigateIncludeSQLExpression) {
        return include(condition, navigateIncludeSQLExpression, o -> {
        }, null);
    }

    /**
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
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression1<T1Proxy, TPropertyProxy> navigateIncludeSQLExpression, Integer groupSize) {
        return include(navigateIncludeSQLExpression, o -> {
        }, groupSize);
    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(boolean condition, SQLFuncExpression1<T1Proxy, TPropertyProxy> navigateIncludeSQLExpression, Integer groupSize) {
        return include(condition, navigateIncludeSQLExpression, o -> {
        }, groupSize);
    }

    /**
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
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression1<T1Proxy, TPropertyProxy> navigateIncludeSQLExpression, SQLExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression) {
        return include(navigateIncludeSQLExpression, includeAdapterExpression, null);
    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(boolean condition, SQLFuncExpression1<T1Proxy, TPropertyProxy> navigateIncludeSQLExpression, SQLExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression) {
        return include(condition, navigateIncludeSQLExpression, includeAdapterExpression, null);
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
    <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(boolean condition, SQLFuncExpression1<T1Proxy, TPropertyProxy> navigateIncludeSQLExpression, SQLExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression, Integer groupSize);

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression1<T1Proxy, TPropertyProxy> navigateIncludeSQLExpression, SQLExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression, Integer groupSize) {
        return include(true, navigateIncludeSQLExpression, includeAdapterExpression, groupSize);
    }
}
