package com.easy.query.cache.core.impl.kv;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.cache.core.CacheKvEntity;
import com.easy.query.cache.core.CacheRuntimeContext;
import com.easy.query.cache.core.Pair;
import com.easy.query.cache.core.base.CachePredicate;
import com.easy.query.cache.core.impl.AbstractSingleCacheQueryable;
import com.easy.query.cache.core.queryable.KvCacheQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * create time 2023/5/16 10:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultKvCacheQueryable<T1Proxy extends ProxyEntity<T1Proxy, TEntity>, TEntity extends CacheKvEntity> extends AbstractSingleCacheQueryable<TEntity> implements KvCacheQueryable<T1Proxy, TEntity> {
    private final T1Proxy t1Proxy;

    public DefaultKvCacheQueryable(CacheRuntimeContext cacheRuntimeContext, Class<TEntity> entityClass,T1Proxy t1Proxy) {
        super(cacheRuntimeContext, entityClass);
        this.t1Proxy = t1Proxy;
    }

    @Override
    public List<TEntity> toList(Collection<String> ids) {
        if (EasyCollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return getStreamBy(ids).collect(Collectors.toList());
    }
    public Stream<TEntity> getStreamBy(Collection<String> ids) {
        List<Pair<String, TEntity>> caches = doGet(ids);
        Set<String> idSet = new HashSet<>(ids);
        Stream<TEntity> select = caches.stream().filter(o -> o.getObject2() != null && idSet.contains(o.getObject1()))
                .map(o -> o.getObject2());
        return filterResult(select);
    }

    @Override
    public Map<String, TEntity> toMap(Collection<String> ids) {
        if (EasyCollectionUtil.isEmpty(ids)) {
            return new HashMap<>();
        }
        return getStreamBy(ids).collect(Collectors.toMap(o -> getKey(o), o -> o, (v1, v2) -> {
            throw new EasyQueryInvalidOperationException(EasyClassUtil.getInstanceSimpleName(entityClass) + " cache have duplicates.");
        }, HashMap::new));
    }

    @Override
    public Map<String, TEntity> toLinkedMap(Collection<String> ids) {
        if (EasyCollectionUtil.isEmpty(ids)) {
            return new LinkedHashMap<>();
        }
        return getStreamBy(ids).collect(Collectors.toMap(o -> getKey(o), o -> o, (v1, v2) -> {
            throw new EasyQueryInvalidOperationException(EasyClassUtil.getInstanceSimpleName(entityClass) + " cache have duplicates.");
        }, LinkedHashMap::new));
    }

    protected List<Pair<String, TEntity>> doGet(Collection<String> ids) {
        Set<String> needFinds = new HashSet<>(ids);
        if (!needFinds.isEmpty()) {
            ClientQueryable<TEntity> entityQueryable = getEndEntityQueryable(this.queryable);
            String queryableKey = getQueryableKey(entityQueryable);

            return easyCacheProvider.cache(entityClass, entityClass, queryableKey, needFinds,
                    otherIds -> {
                        return defaultSelect(otherIds, entityQueryable);
                    });
        }
        return Collections.emptyList();
    }


    @Override
    public boolean any(String id) {
        return singleOrDefault(id, null) != null;
    }


    @Override
    public KvCacheQueryable<T1Proxy, TEntity> noInterceptor() {
        this.functions.add(q -> q.noInterceptor());
        return this;
    }

    @Override
    public KvCacheQueryable<T1Proxy, TEntity> useInterceptor(String name) {
        this.functions.add(q -> q.useInterceptor(name));
        return this;
    }

    @Override
    public KvCacheQueryable<T1Proxy, TEntity> noInterceptor(String name) {
        this.functions.add(q -> q.noInterceptor(name));
        return this;
    }

    @Override
    public KvCacheQueryable<T1Proxy, TEntity> useInterceptor() {
        this.functions.add(q -> q.useInterceptor());
        return this;
    }

    @Override
    public KvCacheQueryable<T1Proxy, TEntity> filter(boolean condition, CachePredicate<TEntity> predicate) {
        if (condition) {
            addFilter(predicate);
        }
        return this;
    }

    @Override
    public KvCacheQueryable<T1Proxy, TEntity> where(SQLActionExpression1<T1Proxy> whereExpression) {
        this.functions.add(q -> new EasyEntityQueryable<>(t1Proxy, q).where(whereExpression).getClientQueryable());
        return this;
    }

    @Override
    public KvCacheQueryable<T1Proxy, TEntity> where(boolean condition, SQLActionExpression1<T1Proxy> whereExpression) {
        this.functions.add(q -> new EasyEntityQueryable<>(t1Proxy, q).where(condition, whereExpression).getClientQueryable());
        return this;
    }
}
