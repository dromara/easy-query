package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.common.ValueHolder;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.metadata.IncludePathTreeNode;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.expression.parser.core.available.IncludeAvailable;
import com.easy.query.core.util.EasyUtil;

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
     * @param navigateIncludeSQLExpression
     * @param <TPropertyProxy>
     * @param <TProperty>
     * @return
     */
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression1<T1Proxy, TPropertyProxy> navigateIncludeSQLExpression) {
        return include(navigateIncludeSQLExpression, null, null);
    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(boolean condition, SQLFuncExpression1<T1Proxy, TPropertyProxy> navigateIncludeSQLExpression) {
        return include(condition, navigateIncludeSQLExpression, null, null);
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
        return include(navigateIncludeSQLExpression, null, groupSize);
    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(boolean condition, SQLFuncExpression1<T1Proxy, TPropertyProxy> navigateIncludeSQLExpression, Integer groupSize) {
        return include(condition, navigateIncludeSQLExpression, null, groupSize);
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
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression1<T1Proxy, TPropertyProxy> navigateIncludeSQLExpression, SQLActionExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression) {
        return include(navigateIncludeSQLExpression, includeAdapterExpression, null);
    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(boolean condition, SQLFuncExpression1<T1Proxy, TPropertyProxy> navigateIncludeSQLExpression, SQLActionExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression) {
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
    <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(boolean condition, SQLFuncExpression1<T1Proxy, TPropertyProxy> navigateIncludeSQLExpression, SQLActionExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression, Integer groupSize);

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty extends ProxyEntityAvailable<TProperty, TPropertyProxy>> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression1<T1Proxy, TPropertyProxy> navigateIncludeSQLExpression, SQLActionExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression, Integer groupSize) {
        return include(true, navigateIncludeSQLExpression, includeAdapterExpression, groupSize);
    }

    default EntityQueryable<T1Proxy, T1> includeMany(SQLFuncExpression1<T1Proxy, IncludeAvailable> navigateIncludeSQLExpression) {
        T1Proxy t1Proxy = getQueryable().get1Proxy();
        ValueHolder<IncludeAvailable> includeAvailableValueHolder=new ValueHolder<>();
        t1Proxy.getEntitySQLContext()._include(()->{
            IncludeAvailable includeAvailable = navigateIncludeSQLExpression.apply(t1Proxy);
            includeAvailableValueHolder.setValue(includeAvailable);
        });
        IncludeAvailable value = includeAvailableValueHolder.getValue();
        if(value!=null){
            IncludePathTreeNode includePathTreeRoot = EasyUtil.getIncludePathTreeRoot(value);
            EasyUtil.includeMany(this.getClientQueryable(), includePathTreeRoot);
            value.getIncludes().clear();
            value.getFunctions().clear();
        }
        return getQueryable();
    }
}