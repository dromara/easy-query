package com.easy.query.cache.core.impl.all;

import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.entity.select.impl.EasyEntityQueryable;
import com.easy.query.cache.core.CacheAllEntity;
import com.easy.query.cache.core.EasyCacheIndex;
import com.easy.query.cache.core.CacheRuntimeContext;
import com.easy.query.cache.core.Pair;
import com.easy.query.cache.core.base.CachePredicate;
import com.easy.query.cache.core.impl.AbstractSingleCacheQueryable;
import com.easy.query.cache.core.queryable.AllCacheQueryable;
import com.easy.query.core.api.pagination.DefaultPageResult;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.util.ArrayList;
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
 * create time 2023/5/16 12:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultAllCacheQueryable<T1Proxy extends ProxyEntity<T1Proxy, TEntity>, TEntity extends ProxyEntityAvailable<TEntity, T1Proxy> & CacheAllEntity> extends AbstractSingleCacheQueryable<TEntity> implements AllCacheQueryable<T1Proxy, TEntity> {
    private final T1Proxy t1Proxy;
    private final ClientQueryable<TEntity> indexQueryable;

    public DefaultAllCacheQueryable(CacheRuntimeContext cacheRuntimeContext, Class<TEntity> entityClass) {
        super(cacheRuntimeContext, entityClass);
        this.indexQueryable = easyQueryClient.queryable(entityClass).asNoTracking();
        this.t1Proxy = EntityQueryProxyManager.create(entityClass);
    }

    @Override
    public List<TEntity> toList(Collection<String> ids) {
        if (EasyCollectionUtil.isEmpty(ids)) {
            return new ArrayList<>(0);
        }
        return getStreamBy(ids).collect(Collectors.toList());
    }

    private Stream<TEntity> getStreamBy(Collection<String> ids) {

        List<Pair<String, TEntity>> caches = doGet(ids);

        Set<String> idSet = new HashSet<>(ids);
        Stream<TEntity> select = caches.stream().filter(o -> idSet.contains(o.getObject1()) && o.getObject2() != null)
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

    @Override
    public boolean any(String id) {
        Set<String> indexs = doGetIndex();
        if (indexs.isEmpty()) {
            return false;
        }
        if (hasFilter()) {
            List<TEntity> in = toList(indexs);
            return EasyCollectionUtil.isNotEmpty(in);
        }
        return true;
    }

    @Override
    public List<TEntity> toList() {
        List<Pair<String, TEntity>> caches = doGet(Collections.emptyList());
        Stream<TEntity> select = caches.stream().filter(o -> o.getObject2() != null).map(o -> o.getObject2());
        return filterResult(select).collect(Collectors.toList());
    }

    @Override
    public List<String> toIndexList() {
        Set<String> indexs = doGetIndex();
        return new ArrayList<>(indexs);
    }

    @Override
    public int count() {
        Set<String> indexs = doGetIndex();
        return indexs.size();
    }

    @Override
    public EasyPageResult<TEntity> toPageResult(int pageIndex, int pageSize) {
        Set<String> indexs = doGetIndex();
        if (indexs.isEmpty()) {
            return new DefaultPageResult<>(0, new ArrayList<>(0));
        }
        int take = pageSize <= 0 ? 1 : pageSize;
        int index = pageIndex <= 0 ? 1 : pageIndex;
        int skip = (index - 1) * take;
        List<Pair<String, TEntity>> caches = getCacheByIds(indexs);
        Stream<TEntity> select = caches.stream().filter(o -> o.getObject2() != null)
                .map(o -> o.getObject2());
        List<TEntity> tCacheItems = filterResult(select).skip(skip).limit(take).collect(Collectors.toList());
        return new DefaultPageResult<>(indexs.size(), tCacheItems);
    }

    @Override
    public AllCacheQueryable<T1Proxy, TEntity> where(SQLActionExpression1<T1Proxy> whereExpression) {
        this.functions.add(q -> new EasyEntityQueryable<>(t1Proxy, q).where(whereExpression).getClientQueryable());
        return this;
    }

    @Override
    public AllCacheQueryable<T1Proxy, TEntity> where(boolean condition, SQLActionExpression1<T1Proxy> whereExpression) {
        this.functions.add(q -> new EasyEntityQueryable<>(t1Proxy, q).where(condition, whereExpression).getClientQueryable());
        return this;
    }

    protected List<Pair<String, TEntity>> doGet(Collection<String> ids) {
        if (EasyCollectionUtil.isEmpty(ids)) {
            Set<String> indexs = doGetIndex();
            if (indexs.isEmpty()) {
                return Collections.emptyList();
            }
            Set<String> findIds = filterIdWhichIsQuery(indexs, ids);
            if (findIds.size() > 0) {
                return getCacheByIds(findIds);
            }
            return Collections.emptyList();
        } else {
            return getCacheByIds(new HashSet<>(ids));
        }

    }

    private Set<String> filterIdWhichIsQuery(Set<String> indexs, Collection<String> ids) {
        if (EasyCollectionUtil.isEmpty(ids)) {
            return indexs;
        }
        return ids.stream().filter(o -> EasyStringUtil.isNotBlank(o) && indexs.contains(o)).collect(Collectors.toSet());
    }

    protected Set<String> doGetIndex() {
        Set<String> fields = new HashSet<>();
        fields.add(easyCacheOption.getCacheIndex());
        ClientQueryable<TEntity> endEntityQueryable = getEndEntityQueryable(indexQueryable);
        String queryableKey = getQueryableKey(endEntityQueryable);

        List<Pair<String, EasyCacheIndex>> cache = easyCacheProvider.cache(entityClass, EasyCacheIndex.class, queryableKey, fields, ids -> {
            return getIndex(getCacheAllIndex(endEntityQueryable));
        });
        EasyCacheIndex v = cache.get(0).getObject2();
        return (v == null || v.getIndex() == null) ? new HashSet<>() : v.getIndex();
    }

    private List<Pair<String, TEntity>> getCacheByIds(Set<String> ids) {
        ClientQueryable<TEntity> entityQueryable = getEndEntityQueryable(this.queryable);
        String queryableKey = getQueryableKey(entityQueryable);
        return easyCacheProvider.cache(entityClass, entityClass, queryableKey, ids, otherIds -> {
            return toKeyAndEntity(getEntities(otherIds, entityQueryable));
        });
    }

    /**
     * 获取全部索引 mapper.where(o->o.eq(tenantID))
     *
     * @return
     */
    protected List<String> getCacheAllIndex(ClientQueryable<TEntity> indexQueryable) {
        return getCacheAllIndex0(indexQueryable);
    }

    protected List<String> getCacheAllIndex0(ClientQueryable<TEntity> indexQueryable) {
        SQLActionExpression1<ColumnAsSelector<TEntity, String>> idProperty = x -> x.column(getIdProperty());
        return indexQueryable
                .select(String.class, idProperty)
                .toList();
    }

    private List<Pair<String, EasyCacheIndex>> getIndex(Collection<String> indexs) {
        List<Pair<String, EasyCacheIndex>> ret = new ArrayList<>(1);
        EasyCacheIndex easyCacheIndex = new EasyCacheIndex();
        easyCacheIndex.setIndex(new HashSet<>(indexs));
        ret.add(new Pair<>(easyCacheOption.getCacheIndex(), easyCacheIndex));
        return ret;
    }


    protected List<Pair<String, TEntity>> toKeyAndEntity(List<TEntity> entities) {
        return entities.stream().map(this::getKeyAndEntity).collect(Collectors.toList());
    }

    @Override
    public AllCacheQueryable<T1Proxy, TEntity> noInterceptor() {
        this.functions.add(q -> q.noInterceptor());
        return this;
    }

    @Override
    public AllCacheQueryable<T1Proxy, TEntity> useInterceptor(String name) {
        this.functions.add(q -> q.useInterceptor(name));
        return this;
    }

    @Override
    public AllCacheQueryable<T1Proxy, TEntity> noInterceptor(String name) {
        this.functions.add(q -> q.noInterceptor(name));
        return this;
    }

    @Override
    public AllCacheQueryable<T1Proxy, TEntity> useInterceptor() {
        this.functions.add(q -> q.useInterceptor());
        return this;
    }

    //
//    @Override
//    public TEntity firstOrDefault(String id, TEntity def) {
//
//        if (EasyStringUtil.isBlank(id)) {
//            return def;
//        }
//        return doFirstOrDefault(id);
//    }
//
//    private TEntity doFirstOrDefault(String id) {
//        if (EasyStringUtil.isBlank(id)) {
//            return null;
//        }
//        Set<String> idSet = Collections.singleton(id);
//        List<Pair<String, TEntity>> caches = doFirstGet(idSet);
//        Stream<TEntity> select = caches.stream().filter(o -> o.getObject2() != null && idSet.contains(o.getObject1()))
//                .map(o -> o.getObject2());
//        return filterResult(select).findFirst().orElse(null);
//    }
//
//    private List<Pair<String, TEntity>> doFirstGet(Set<String> ids) {
//        return easyRedisManager.cache(entityClass, getEntityKey(), ids, easyCacheOption.getTimeoutMillisSeconds(), easyCacheOption.getValueNullTimeoutMillisSeconds(),
//                otherIds -> {
//                    return defaultSelect(otherIds);
//                });
//    }
    @Override
    public AllCacheQueryable<T1Proxy, TEntity> filter(boolean condition, CachePredicate<TEntity> predicate) {
        if (condition) {
            addFilter(predicate);
        }
        return this;
    }
}
