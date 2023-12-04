package com.easy.query.api.proxy.entity;

import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.common.cache.Cache;
import com.easy.query.core.common.cache.DefaultMemoryCache;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.function.Supplier;

/**
 * create time 2023/12/4 11:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityQueryProxyManager {
    private static final Cache<Class<?>, Supplier<Object>> proxyConstructorCache = new DefaultMemoryCache<>();

    public static <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity extends ProxyEntityAvailable<TEntity, TEntityProxy>> TEntityProxy create(Class<TEntity> entityClass) {
//        EntityMetadataManager

        if (entityClass == null) {
            throw new IllegalArgumentException("entityClass is null");
        }
        Supplier<Object> creator = proxyConstructorCache.get(entityClass);
        if (creator != null) {
            Object proxy = creator.get();
            return EasyObjectUtil.typeCast(proxy);
        }
        TEntity tEntity = EasyClassUtil.newInstance(entityClass);
        Class<TEntityProxy> proxyTableClass = tEntity.proxyTableClass();
        FastBean fastBean = new FastBean(proxyTableClass);
        Supplier<Object> beanConstructorCreator = fastBean.getBeanConstructorCreator();
        Supplier<Object> objectSupplier = proxyConstructorCache.computeIfAbsent(entityClass, key -> {
            return beanConstructorCreator;
        });
        Object proxy = objectSupplier.get();
        return EasyObjectUtil.typeCast(proxy);
    }
}
