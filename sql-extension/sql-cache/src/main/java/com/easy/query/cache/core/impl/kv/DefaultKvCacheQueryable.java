package com.easy.query.cache.core.impl.kv;

import com.easy.query.cache.core.CacheKvEntity;
import com.easy.query.cache.core.EasyCacheStorageOption;
import com.easy.query.cache.core.Pair;
import com.easy.query.cache.core.base.CachePredicate;
import com.easy.query.cache.core.impl.AbstractSingleCacheQueryable;
import com.easy.query.cache.core.queryable.KvCacheQueryable;
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

    public DefaultKvCacheQueryable(EasyCacheStorageOption easyCacheStorageOption, Class<TEntity> entityClass) {
        super(easyCacheStorageOption, entityClass);
    }

    @Override
    public List<TEntity> getIn(Collection<String> ids) {
        if(EasyCollectionUtil.isEmpty(ids)){
            return Collections.emptyList();
        }
        List<Pair<String, TEntity>> caches=doGet(ids);
        Set<String> idSet = new HashSet<>(ids);
        Stream<TEntity> select = caches.stream().filter(o -> o.getObject2() != null && idSet.contains(o.getObject1()))
                .map(o -> o.getObject2());
        return filterResult(select).collect(Collectors.toList());
    }
    protected List<Pair<String, TEntity>> doGet(Collection<String> ids){
        Set<String> needFinds = new HashSet<>(ids);
        if(!needFinds.isEmpty()){
            return easyRedisManager.cache(entityClass, getEntityKey(), needFinds, easyCacheOption.getTimeoutMillisSeconds(), easyCacheOption.getValueNullTimeoutMillisSeconds(),
                    otherIds -> {
                        return defaultSelect(otherIds);
                    });
        }
        return Collections.emptyList();
    }


    @Override
    public boolean any(String id) {
        return firstOrDefault(id,null)!=null;
    }

    @Override
    public KvCacheQueryable<TEntity> filter(boolean condition, CachePredicate<TEntity> predicate) {
        if(condition){
            addFilter(predicate);
        }
        return this;
    }
}
