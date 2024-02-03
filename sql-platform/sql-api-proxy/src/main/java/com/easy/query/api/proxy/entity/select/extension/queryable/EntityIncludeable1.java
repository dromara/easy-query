package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.api.proxy.sql.ProxyEntityNavigateInclude;
import com.easy.query.api.proxy.sql.impl.ProxyEntityNavigateIncludeImpl;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.columns.SQLNavigateColumn;

/**
 * create time 2023/8/17 13:34
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityIncludeable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ClientEntityQueryableAvailable<T1>, EntityQueryableAvailable<T1Proxy, T1> {

    /**
     * 请使用
     * {@link #include(SQLFuncExpression1)}
     * {@link #include(SQLFuncExpression1, Integer)}
     * {@link #include(SQLFuncExpression1, SQLExpression1, Integer)}
     *
     * @param navigateIncludeSQLExpression
     * @return
     * @param <TPropertyProxy>
     * @param <TProperty>
     */
    @Deprecated
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression2<ProxyEntityNavigateInclude<T1, T1Proxy>, T1Proxy, EntityQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
        return include(true, navigateIncludeSQLExpression);
    }

    /**
     * 请使用
     * {@link #include(SQLFuncExpression1)}
     * {@link #include(SQLFuncExpression1, Integer)}
     * {@link #include(SQLFuncExpression1, SQLExpression1, Integer)}
     *
     * @param condition
     * @param navigateIncludeSQLExpression
     * @return
     * @param <TPropertyProxy>
     * @param <TProperty>
     */
    @Deprecated
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(boolean condition, SQLFuncExpression2<ProxyEntityNavigateInclude<T1, T1Proxy>, T1Proxy, EntityQueryable<TPropertyProxy, TProperty>> navigateIncludeSQLExpression) {
        if (condition) {
            T1Proxy proxy = getQueryable().get1Proxy();
            getClientQueryable().<TProperty>include(navigateInclude -> navigateIncludeSQLExpression.apply(new ProxyEntityNavigateIncludeImpl<>(proxy, navigateInclude), proxy).getClientQueryable());
        }
        return getQueryable();
    }

    /**
     * <blockquote><pre>
     * {@code
     * easyEntityQuery.queryable(SchoolStudent.class)
     *                         .include(o->o.schoolClass())
     *                    }
     * </pre></blockquote>
     * @param navigateIncludeSQLExpression
     * @return
     * @param <TPropertyProxy>
     * @param <TProperty>
     */
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression1<T1Proxy, SQLNavigateColumn<T1Proxy, TProperty>> navigateIncludeSQLExpression) {
        return include(navigateIncludeSQLExpression, o -> {
        }, null);
    }

    /**
     * <blockquote><pre>
     * {@code
     * easyEntityQuery.queryable(SchoolStudent.class)
     *                         .include(o->o.schoolClass(),100)
     *                    }
     * </pre></blockquote>
     * @param navigateIncludeSQLExpression
     * @param groupSize
     * @return
     * @param <TPropertyProxy>
     * @param <TProperty>
     */
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression1<T1Proxy, SQLNavigateColumn<T1Proxy, TProperty>> navigateIncludeSQLExpression, Integer groupSize) {
        return include(navigateIncludeSQLExpression, o -> {
        }, groupSize);
    }

    /**
     * <blockquote><pre>
     * {@code
     * easyEntityQuery.queryable(SchoolStudent.class)
     *                         .include(o->o.schoolClass()，q->q.where(x->x.name().like("123)))
     *                    }
     * </pre></blockquote>
     * @param navigateIncludeSQLExpression
     * @param includeAdapterExpression
     * @return
     * @param <TPropertyProxy>
     * @param <TProperty>
     */
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression1<T1Proxy, SQLNavigateColumn<T1Proxy, TProperty>> navigateIncludeSQLExpression, SQLExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression) {
        return include(navigateIncludeSQLExpression, includeAdapterExpression, null);
    }

    /**
     * <blockquote><pre>
     * {@code
     * easyEntityQuery.queryable(SchoolStudent.class)
     *                         .include(o->o.schoolClass()，q->q.where(x->x.name().like("123)),100)
     *                    }
     * </pre></blockquote>
     * @param navigateIncludeSQLExpression
     * @param includeAdapterExpression
     * @param groupSize
     * @return
     * @param <TPropertyProxy>
     * @param <TProperty>
     */
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression1<T1Proxy, SQLNavigateColumn<T1Proxy, TProperty>> navigateIncludeSQLExpression, SQLExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression, Integer groupSize) {
        T1Proxy proxy = getQueryable().get1Proxy();
        SQLNavigateColumn<T1Proxy, TProperty> navigateColumn = navigateIncludeSQLExpression.apply(proxy);

        getClientQueryable().<TProperty>include(navigateInclude -> {
            ClientQueryable<TProperty> clientQueryable = navigateInclude.with(navigateColumn.getValue(), groupSize);
            TPropertyProxy tPropertyProxy = EntityQueryProxyManager.create(clientQueryable.queryClass());
            EasyEntityQueryable<TPropertyProxy, TProperty> entityQueryable = new EasyEntityQueryable<>(tPropertyProxy, clientQueryable);
            includeAdapterExpression.apply(entityQueryable);
            return entityQueryable.getClientQueryable();
        });

        return getQueryable();
    }
}
