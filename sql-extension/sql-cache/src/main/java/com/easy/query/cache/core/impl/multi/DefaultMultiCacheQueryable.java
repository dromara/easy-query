package com.easy.query.cache.core.impl.multi;

import com.easy.query.cache.core.CacheMultiEntity;
import com.easy.query.cache.core.EasyCacheIndex;
import com.easy.query.cache.core.EasyCacheStorageOption;
import com.easy.query.cache.core.Pair;
import com.easy.query.cache.core.base.CachePredicate;
import com.easy.query.cache.core.impl.AbstractMultiCacheQueryable;
import com.easy.query.cache.core.queryable.MultiCacheQueryable;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * create time 2023/5/16 12:29
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultMultiCacheQueryable<TEntity extends CacheMultiEntity> extends AbstractMultiCacheQueryable<TEntity> implements MultiCacheQueryable<TEntity> {

    public DefaultMultiCacheQueryable(EasyCacheStorageOption cacheStorageOption, Class<TEntity> entityClass) {
        super(cacheStorageOption, entityClass);
    }

    @Override
    public boolean any(String aggregateId, String id) {
        Set<String> indexs = doGetIndex(aggregateId);
        if (indexs.isEmpty()) {
            return false;
        }
        if(!indexs.contains(id)){
            return false;
        }
        if(hasFilter()){
            List<TEntity> in = toList(aggregateId, Collections.singletonList(id));
            return EasyCollectionUtil.isNotEmpty(in);
        }
        return true;
    }

    @Override
    public boolean any(String aggregateId) {
        Set<String> indexs = doGetIndex(aggregateId);
        if(indexs.isEmpty()){
            return false;
        }
        if(hasFilter()){
            List<TEntity> in = toList(aggregateId, indexs);
            return EasyCollectionUtil.isNotEmpty(in);
        }
        return true;
    }

    @Override
    public List<TEntity> toList(String aggregateId, Collection<String> ids) {
        if (EasyCollectionUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        List<Pair<String, TEntity>> cache = doGet(aggregateId, ids);
        Stream<TEntity> select = cache.stream().filter(o -> o.getObject2() != null)
                .map(o -> o.getObject2());
        return filterResult(select).collect(Collectors.toList());
    }

    @Override
    public List<TEntity> toList(String aggregateId) {
        List<Pair<String, TEntity>> cache = doGet(aggregateId, Collections.emptyList());
        Stream<TEntity> select = cache.stream().filter(o -> o.getObject2() != null)
                .map(o -> o.getObject2());
        return filterResult(select).collect(Collectors.toList());
    }

    @Override
    public List<String> toIndexList(String aggregateId) {
        Set<String> indexs = doGetIndex(aggregateId);
        return new ArrayList<>(indexs);
    }

    private Set<String> filterIdWhichIsQuery(Set<String> indexs, Collection<String> ids) {
        if (EasyCollectionUtil.isEmpty(ids)) {
            return indexs;
        }
        return ids.stream().filter(o-> EasyStringUtil.isNotBlank(o)&&indexs.contains(o)).collect(Collectors.toSet());
    }

    protected List<Pair<String, TEntity>> doGet(String aggregateId,  Collection<String> ids) {
        Set<String> indexs = doGetIndex(aggregateId);
        if (indexs.isEmpty()) {
            return Collections.emptyList();
        }
        Set<String> findIds = filterIdWhichIsQuery(indexs, ids);
        if (findIds.size() > 0) {
            return getCacheByIds(aggregateId, findIds);
        }
        return Collections.emptyList();

    }

    private List<Pair<String, TEntity>> getCacheByIds(String aggregateId, Set<String> ids) {
        return easyCacheManager.cache(entityClass, getEntityKey(), ids, easyCacheOption.getTimeoutMillisSeconds(), easyCacheOption.getValueNullTimeoutMillisSeconds(), otherIds -> {
            return toKeyAndEntity(getEntities(aggregateId, otherIds));
        });
    }

    protected List<Pair<String, TEntity>> toKeyAndEntity(List<TEntity> entities) {
        return entities.stream().map(this::getKeyAndEntity).collect(Collectors.toList());
    }

    protected Set<String> doGetIndex(String aggregateId) {
        Set<String> fields = new HashSet<>();
        fields.add(getMultiKey(aggregateId));

        List<Pair<String, EasyCacheIndex>> cache = easyCacheManager.cache(EasyCacheIndex.class, getEntityKey(), fields, easyCacheOption.getTimeoutMillisSeconds(), easyCacheOption.getValueNullTimeoutMillisSeconds(), ids -> {
            return getIndex(aggregateId, getCacheAllIndex(aggregateId));
        });
        EasyCacheIndex v = cache.get(0).getObject2();
        return (v == null || v.getIndex() == null) ? new HashSet<>() : v.getIndex();
    }

    /**
     * 获取全部索引 mapper.where(o->o.eq(tenantID))
     *
     * @return
     */
    protected List<String> getCacheAllIndex(String aggregateId) {
        return getCacheAllIndex0(aggregateId);
    }

    protected List<String> getCacheAllIndex0(String aggregateId){
        return easyQueryClient.queryable(entityClass)
                .noInterceptor().asNoTracking().where(o->o
                .eq(getAggregateIdProperty(),aggregateId)
        ).select(String.class,o->o.column(getIdProperty()))
                .toList();
    }

    /**
     * 考虑租户问题
     *
     * @param ids
     * @return
     */
    protected List<TEntity> getEntities(String aggregateId, Collection<String> ids) {
        return easyQueryClient.queryable(entityClass)
                .noInterceptor()
                .asNoTracking()
                .where(o->o.in(getIdProperty(),ids)
                        .eq(getAggregateIdProperty(),aggregateId)
                ).toList();
    }
    protected String getAggregateIdProperty(){
        return cacheMultiEntitySchema.value();
    }


    protected List<Pair<String, EasyCacheIndex>> getIndex(String aggregateId, Collection<String> indexs) {
        List<Pair<String, EasyCacheIndex>> ret = new ArrayList<>(1);
        EasyCacheIndex easyCacheIndex = new EasyCacheIndex();
        easyCacheIndex.setIndex(new HashSet<>(indexs));
        ret.add(new Pair<>(getMultiKey(aggregateId), easyCacheIndex));
        return ret;
    }

    protected String getMultiKey(String aggregateId) {
        return easyCacheOption.getCacheIndex() + ":" + aggregateId;
    }

    @Override
    public MultiCacheQueryable<TEntity> filter(boolean condition, CachePredicate<TEntity> predicate) {
        if(condition){
            addFilter(predicate);
        }
        return this;
    }
}
