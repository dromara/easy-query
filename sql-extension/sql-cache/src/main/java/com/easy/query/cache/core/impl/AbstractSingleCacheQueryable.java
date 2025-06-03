package com.easy.query.cache.core.impl;


import com.easy.query.cache.core.CacheEntity;
import com.easy.query.cache.core.CacheRuntimeContext;
import com.easy.query.cache.core.Pair;
import com.easy.query.cache.core.queryable.SingleCacheQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.util.EasyStringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * create time 2023/5/16 10:34
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractSingleCacheQueryable<TEntity extends CacheEntity> extends AbstractBaseCacheQueryable<TEntity> implements SingleCacheQueryable<TEntity> {


    public AbstractSingleCacheQueryable(CacheRuntimeContext cacheRuntimeContext, Class<TEntity> entityClass) {
        super(cacheRuntimeContext, entityClass);
    }

    @Override
    public TEntity singleOrNull(@NotNull String id) {
        return singleOrDefault(id, null);
    }

    @Override
    public TEntity singleOrDefault(String id, TEntity def) {
        if (EasyStringUtil.isBlank(id)) {
            return def;
        }
        List<TEntity> in = toList(Collections.singletonList(id));
        if (in.isEmpty()) {
            return null;
        }
        return in.get(0);
    }

    @Override
    public abstract List<TEntity> toList(Collection<String> ids);

    protected List<Pair<String, TEntity>> defaultSelect(Collection<String> ids, ClientQueryable<TEntity> clientQueryable) {
        return getEntities(ids, clientQueryable).stream()
                .map(this::getKeyAndEntity).collect(Collectors.toList());
    }

    protected List<TEntity> getEntities(Collection<String> ids, ClientQueryable<TEntity> clientQueryable) {
        return clientQueryable.where(o -> o.in(getIdProperty(), ids)).toList();
    }


}
