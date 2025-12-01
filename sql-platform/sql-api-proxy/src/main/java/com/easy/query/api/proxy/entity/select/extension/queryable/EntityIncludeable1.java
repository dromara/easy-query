package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.IncludeAvailable;
import com.easy.query.core.metadata.IncludePathTreeNode;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.sql.include.DefaultIncludeContext;
import com.easy.query.core.proxy.sql.include.IncludeContext;
import com.easy.query.core.proxy.sql.include.NavigatePathAvailable;
import com.easy.query.core.util.EasyUtil;

import java.util.List;

/**
 * create time 2023/8/17 13:34
 * 建议使用includeBy
 *
 * @author xuejiaming
 */
public interface EntityIncludeable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ClientEntityQueryableAvailable<T1>, EntityQueryableAvailable<T1Proxy, T1> {

//   <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty > EntityQueryable<T1Proxy, T1> includeMany(SQLExpression1<T1Proxy> navigatePathExpression);

    /**
     * <blockquote><pre>
     * easyEntityQuery.queryable(SchoolStudent.class)
     *                         .include(o->o.schoolClass())
     * </pre></blockquote>
     *
     * @param navigatePathExpression
     * @param <TPropertyProxy>
     * @param <TProperty>
     * @return
     */
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression1<T1Proxy, NavigatePathAvailable<TPropertyProxy, TProperty>> navigatePathExpression) {
        return include(navigatePathExpression, null, null);
    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> EntityQueryable<T1Proxy, T1> include(boolean condition, SQLFuncExpression1<T1Proxy, NavigatePathAvailable<TPropertyProxy, TProperty>> navigatePathExpression) {
        return include(condition, navigatePathExpression, null, null);
    }

    /**
     * <blockquote><pre>
     * {@code
     * easyEntityQuery.queryable(SchoolStudent.class)
     *                         .include(o->o.schoolClass(),100)
     *                    }
     * </pre></blockquote>
     *
     * @param navigatePathExpression
     * @param groupSize
     * @param <TPropertyProxy>
     * @param <TProperty>
     * @return
     */
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression1<T1Proxy, NavigatePathAvailable<TPropertyProxy, TProperty>> navigatePathExpression, Integer groupSize) {
        return include(navigatePathExpression, null, groupSize);
    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> EntityQueryable<T1Proxy, T1> include(boolean condition, SQLFuncExpression1<T1Proxy, NavigatePathAvailable<TPropertyProxy, TProperty>> navigatePathExpression, Integer groupSize) {
        return include(condition, navigatePathExpression, null, groupSize);
    }

    /**
     * <blockquote><pre>
     * {@code
     * easyEntityQuery.queryable(SchoolStudent.class)
     *                         .include(o->o.schoolClass()，q->q.where(x->x.name().like("123)))
     *                    }
     * </pre></blockquote>
     *
     * @param navigatePathExpression
     * @param includeAdapterExpression
     * @param <TPropertyProxy>
     * @param <TProperty>
     * @return
     */
    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression1<T1Proxy, NavigatePathAvailable<TPropertyProxy, TProperty>> navigatePathExpression, SQLActionExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression) {
        return include(navigatePathExpression, includeAdapterExpression, null);
    }

    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> EntityQueryable<T1Proxy, T1> include(boolean condition, SQLFuncExpression1<T1Proxy, NavigatePathAvailable<TPropertyProxy, TProperty>> navigatePathExpression, SQLActionExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression) {
        return include(condition, navigatePathExpression, includeAdapterExpression, null);
    }

    /**
     * <blockquote><pre>
     * {@code
     * easyEntityQuery.queryable(SchoolStudent.class)
     *                         .include(o->o.schoolClass()，q->q.where(x->x.name().like("123)),100)
     *                    }
     * </pre></blockquote>
     *
     * @param navigatePathExpression
     * @param includeAdapterExpression
     * @param groupSize
     * @param <TPropertyProxy>
     * @param <TProperty>
     * @return
     */
    <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> EntityQueryable<T1Proxy, T1> include(boolean condition, SQLFuncExpression1<T1Proxy, NavigatePathAvailable<TPropertyProxy, TProperty>> navigatePathExpression, SQLActionExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression, Integer groupSize);


    default <TPropertyProxy extends ProxyEntity<TPropertyProxy, TProperty>, TProperty> EntityQueryable<T1Proxy, T1> include(SQLFuncExpression1<T1Proxy, NavigatePathAvailable<TPropertyProxy, TProperty>> navigatePathExpression, SQLActionExpression1<EntityQueryable<TPropertyProxy, TProperty>> includeAdapterExpression, Integer groupSize) {
        return include(true, navigatePathExpression, includeAdapterExpression, groupSize);
    }

    /**
     *
     * <blockquote><pre>
     *     {@code
     *
     *      }
     * </pre></blockquote>
     *
     * @param navigatePathExpression
     * @return
     */
    default EntityQueryable<T1Proxy, T1> include2(SQLActionExpression2<IncludeContext, T1Proxy> navigatePathExpression) {
        T1Proxy t1Proxy = getQueryable().get1Proxy();
        DefaultIncludeContext defaultIncludeCollector = new DefaultIncludeContext();
        t1Proxy.getEntitySQLContext()._include(() -> {
            navigatePathExpression.apply(defaultIncludeCollector, t1Proxy);
        });
        List<IncludeAvailable> values = defaultIncludeCollector.getIncludes();
        if (values != null) {
            IncludePathTreeNode includePathTreeRoot = EasyUtil.getIncludePathTreeRoot(values);
            EasyUtil.includeMany(this.getClientQueryable(), includePathTreeRoot);
        }
        return getQueryable();
    }
}