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
//    static {
//        proxyConstructorCache.computeIfAbsent(BigDecimal.class, key -> {
//            return BigDecimalProxy::createTable;
//        });
//        proxyConstructorCache.computeIfAbsent(Boolean.class, key -> {
//            return BooleanProxy::createTable;
//        });
//        proxyConstructorCache.computeIfAbsent(Byte[].class, key -> {
//            return ByteArrayProxy::createTable;
//        });
//        proxyConstructorCache.computeIfAbsent(Byte.class, key -> {
//            return ByteProxy::createTable;
//        });
//        proxyConstructorCache.computeIfAbsent(Double.class, key -> {
//            return DoubleProxy::createTable;
//        });
//        proxyConstructorCache.computeIfAbsent(Float.class, key -> {
//            return FloatProxy::createTable;
//        });
//        proxyConstructorCache.computeIfAbsent(Integer.class, key -> {
//            return IntegerProxy::createTable;
//        });
//        proxyConstructorCache.computeIfAbsent(LocalDate.class, key -> {
//            return LocalDateProxy::createTable;
//        });
//        proxyConstructorCache.computeIfAbsent(LocalDateTime.class, key -> {
//            return LocalDateTimeProxy::createTable;
//        });
//        proxyConstructorCache.computeIfAbsent(Long.class, key -> {
//            return LongProxy::createTable;
//        });
//        proxyConstructorCache.computeIfAbsent(Map.class, key -> {
//            return MapProxy::createTable;
//        });
//        proxyConstructorCache.computeIfAbsent(Short.class, key -> {
//            return ShortProxy::createTable;
//        });
//        proxyConstructorCache.computeIfAbsent(java.sql.Date.class, key -> {
//            return SQLDateProxy::createTable;
//        });
//        proxyConstructorCache.computeIfAbsent(String.class, key -> {
//            return StringProxy::createTable;
//        });
//        proxyConstructorCache.computeIfAbsent(Time.class, key -> {
//            return TimeProxy::createTable;
//        });
//        proxyConstructorCache.computeIfAbsent(Timestamp.class, key -> {
//            return TimestampProxy::createTable;
//        });
//        proxyConstructorCache.computeIfAbsent(java.util.Date.class, key -> {
//            return UtilDateProxy::createTable;
//        });
//    }

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
