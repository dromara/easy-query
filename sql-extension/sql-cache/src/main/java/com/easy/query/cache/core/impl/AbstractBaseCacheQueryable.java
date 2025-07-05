package com.easy.query.cache.core.impl;

import com.easy.query.cache.core.CacheEntity;
import com.easy.query.cache.core.EasyCacheOption;
import com.easy.query.cache.core.CacheRuntimeContext;
import com.easy.query.cache.core.provider.EasyCacheProvider;
import com.easy.query.cache.core.Pair;
import com.easy.query.cache.core.annotation.CacheEntitySchema;
import com.easy.query.cache.core.key.CacheKeyFactory;
import com.easy.query.cache.core.base.CachePredicate;
import com.easy.query.cache.core.queryable.CacheQueryable;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.parser.core.available.RuntimeContextAvailable;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * create time 2023/5/16 10:19
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractBaseCacheQueryable<TEntity extends CacheEntity> implements CacheQueryable, RuntimeContextAvailable {
    protected final EasyCacheOption easyCacheOption;
    protected final EasyCacheProvider easyCacheProvider;
    protected final EasyQueryClient easyQueryClient;
    protected final ServiceProvider serviceProvider;
    protected final CacheKeyFactory cacheKeyFactory;
    protected final Class<TEntity> entityClass;
    protected final CacheEntitySchema cacheEntitySchema;
    protected final QueryRuntimeContext runtimeContext;
    protected final EntityMetadata entityMetadata;
    protected final ClientQueryable<TEntity> queryable;
    protected List<Function<ClientQueryable<TEntity>, ClientQueryable<TEntity>>> functions;
    private List<CachePredicate<TEntity>> filters;

    public AbstractBaseCacheQueryable(CacheRuntimeContext cacheRuntimeContext, Class<TEntity> entityClass) {

        this.serviceProvider = cacheRuntimeContext.getServiceProvider();
        this.easyCacheOption = cacheRuntimeContext.getEasyCacheOption();
        this.easyCacheProvider = cacheRuntimeContext.getEasyCacheManager();
        this.easyQueryClient = cacheRuntimeContext.getEasyQueryClient();
        this.cacheKeyFactory = serviceProvider.getService(CacheKeyFactory.class);
        this.runtimeContext = this.easyQueryClient.getRuntimeContext();
        this.entityClass = entityClass;
        this.entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(entityClass);
        CacheEntitySchema cacheEntitySchema = EasyClassUtil.getAnnotation(entityClass, CacheEntitySchema.class);
        if (cacheEntitySchema == null) {
            throw new IllegalArgumentException(entityClass.getSimpleName() + " plz add annotation @CacheEntitySchema");
        }
        this.cacheEntitySchema = cacheEntitySchema;
        this.queryable = easyQueryClient.queryable(entityClass)
                .asNoTracking();
        this.functions = new ArrayList<>();
    }


    protected ClientQueryable<TEntity> getEndEntityQueryable(ClientQueryable<TEntity> queryable) {
        ClientQueryable<TEntity> cloneQueryable = queryable.cloneQueryable();
        if (EasyCollectionUtil.isNotEmpty(functions)) {
            for (Function<ClientQueryable<TEntity>, ClientQueryable<TEntity>> function : functions) {
                cloneQueryable = function.apply(cloneQueryable);
            }
        }
        return cloneQueryable;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    protected Pair<String, TEntity> getKeyAndEntity(TEntity entity) {
        return new Pair<>(getKey(entity), entity);
    }

    protected String getKey(TEntity entity) {
        Object val = entityMetadata.getColumnNotNull(cacheEntitySchema.value()).getGetterCaller().apply(entity);
        if (val == null) {
            throw new IllegalArgumentException(String.format("CacheEntitySchema.value [%s] is null", cacheEntitySchema.value()));
        }
        return val.toString();
    }

    protected String getIdProperty() {
        return cacheEntitySchema.value();
    }


    protected void addFilter(CachePredicate<TEntity> filter) {
        if (filters == null) {
            filters = new ArrayList<>();
        }
        filters.add(filter);
    }

    protected boolean hasFilter() {
        return EasyCollectionUtil.isNotEmpty(filters);
    }

    protected Stream<TEntity> filterResult(Stream<TEntity> source) {
        if (filters != null) {
            return source.filter(o -> {
                for (CachePredicate<TEntity> filter : filters) {
                    boolean ok = filter.apply(o);
                    if (!ok) {
                        return false;
                    }
                }
                return true;
            });
        }
        return source;
    }


    protected String getQueryableKey(ClientQueryable<TEntity> entityQueryable) {
        return cacheKeyFactory.getKey(entityQueryable);
    }

}
