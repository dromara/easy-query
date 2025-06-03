package com.easy.query.cache.core.impl.kv;

import com.easy.query.cache.core.CacheKvEntity;
import com.easy.query.cache.core.CacheRuntimeContext;
import com.easy.query.cache.core.Pair;
import com.easy.query.cache.core.base.CachePredicate;
import com.easy.query.cache.core.impl.AbstractSingleCacheQueryable;
import com.easy.query.cache.core.queryable.AllCacheQueryable;
import com.easy.query.cache.core.queryable.KvCacheQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * create time 2023/5/16 10:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultKvCacheQueryable<TEntity extends CacheKvEntity> extends AbstractSingleCacheQueryable<TEntity> implements KvCacheQueryable<TEntity> {

    public DefaultKvCacheQueryable(CacheRuntimeContext cacheRuntimeContext, Class<TEntity> entityClass) {
        super(cacheRuntimeContext, entityClass);
    }

    @Override
    public List<TEntity> toList(Collection<String> ids) {
        if (EasyCollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        List<Pair<String, TEntity>> caches = doGet(ids);
        Set<String> idSet = new HashSet<>(ids);
        Stream<TEntity> select = caches.stream().filter(o -> o.getObject2() != null && idSet.contains(o.getObject1()))
                .map(o -> o.getObject2());
        return filterResult(select).collect(Collectors.toList());
    }

    protected List<Pair<String, TEntity>> doGet(Collection<String> ids) {
        Set<String> needFinds = new HashSet<>(ids);
        if (!needFinds.isEmpty()) {
            ClientQueryable<TEntity> entityQueryable = getEndEntityQueryable(this.queryable);
            String queryableKey = getQueryableKey(entityQueryable);

            return easyCacheManager.cache(entityClass,entityClass, getEntityKey(), queryableKey, needFinds,
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
    public KvCacheQueryable<TEntity> noInterceptor() {
        this.functions.add(q -> q.noInterceptor());
        return this;
    }

    @Override
    public KvCacheQueryable<TEntity> useInterceptor(String name) {
        this.functions.add(q -> q.useInterceptor(name));
        return this;
    }

    @Override
    public KvCacheQueryable<TEntity> noInterceptor(String name) {
        this.functions.add(q -> q.noInterceptor(name));
        return this;
    }

    @Override
    public KvCacheQueryable<TEntity> useInterceptor() {
        this.functions.add(q -> q.useInterceptor());
        return this;
    }
}
