package com.easy.query.cache.core.impl;


import com.easy.query.cache.core.CacheEntity;
import com.easy.query.cache.core.EasyCacheStorageOption;
import com.easy.query.cache.core.Pair;
import com.easy.query.cache.core.queryable.SingleCacheQueryable;
import com.easy.query.core.util.EasyStringUtil;

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


    public AbstractSingleCacheQueryable(EasyCacheStorageOption easyCacheStorageOption, Class<TEntity> entityClass) {
        super(easyCacheStorageOption, entityClass);
    }

    @Override
    public TEntity firstOrNull(String id) {
        return firstOrDefault(id,null);
    }

    @Override
    public TEntity firstOrDefault(String id, TEntity def) {
        if(EasyStringUtil.isBlank(id)){
            return def;
        }
        List<TEntity> in = getIn(Collections.singletonList(id));
        if(in.isEmpty()){
            return null;
        }
        return filterResult(in.stream()).findFirst().orElse(null);
    }

    @Override
    public abstract List<TEntity> getIn(Collection<String> ids);
    protected List<Pair<String,TEntity>> defaultSelect(Collection<String> ids){
        return getEntities(ids).stream()
                .map(this::getKeyAndEntity).collect(Collectors.toList());
    }
    protected  List<TEntity> getEntities(Collection<String> ids){
        return easyQueryClient.queryable(entityClass)
                .asNoTracking()
                .where(o->o.in(getIdProperty(),ids)).toList();
    }

}
