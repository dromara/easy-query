package com.easy.query.api.proxy.entity;

import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.common.cache.Cache;
import com.easy.query.core.common.cache.DefaultMemoryCache;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
        if(!ProxyEntityAvailable.class.isAssignableFrom(entityClass)){
            throw new EasyQueryInvalidOperationException(String.format("%s is not implements ProxyEntityAvailable",EasyClassUtil.getSimpleName(entityClass)));
        }

        Class<?> proxyTableClass = Arrays.stream(entityClass.getGenericInterfaces()).map(t -> {

            if (t instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) t;
                Type rawType = parameterizedType.getRawType();

                if (rawType instanceof Class && ProxyEntityAvailable.class.isAssignableFrom((Class<?>) rawType)) {

                    // 如果你需要进一步检查类型参数
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    if (actualTypeArguments.length == 2) {
                        Type actualTypeArgument = actualTypeArguments[1];
                        if (actualTypeArgument instanceof Class<?>) {
                            return (Class<?>) actualTypeArgument;
                        }
                    }
//                    for (Type actualType : actualTypeArguments) {
//                        if()
//                        System.out.println("Type argument: " + actualType);
//                    }
                }
            }
            return null;
        }).filter(t -> t != null).findFirst().orElse(null);

        if(proxyTableClass==null){
            proxyTableClass=getProxyGenericType(entityClass, ProxyEntityAvailable.class);
            if(proxyTableClass==null){
                throw new EasyQueryInvalidOperationException(String.format("Cannot find the ProxyEntityAvailable proxy type for %s",EasyClassUtil.getSimpleName(entityClass)));
            }

        }

        FastBean fastBean = new FastBean(proxyTableClass);
        Supplier<Object> beanConstructorCreator = fastBean.getBeanConstructorCreator();
        Supplier<Object> objectSupplier = proxyConstructorCache.computeIfAbsent(entityClass, key -> {
            return beanConstructorCreator;
        });
        Object proxy = objectSupplier.get();
        return EasyObjectUtil.typeCast(proxy);
    }



    public static Class<?> getProxyGenericType(Class<?> clazz, Class<?> targetInterface) {
        Map<TypeVariable<?>, Type> typeMap = new HashMap<>();
        return getGenericType(clazz, targetInterface, typeMap);
    }

    private static Class<?> getGenericType(Class<?> clazz, Class<?> targetInterface, Map<TypeVariable<?>, Type> typeMap){
        // 解析当前类的父类
        Type superClass = clazz.getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            resolveTypeArguments((ParameterizedType) superClass, typeMap);
        }

        // 解析当前类实现的接口
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                if (parameterizedType.getRawType().equals(targetInterface)) {
                    return getClassForType(parameterizedType.getActualTypeArguments()[1], typeMap);
                } else {
                    resolveTypeArguments(parameterizedType, typeMap);
                }
            }
        }

        // 递归解析父类
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && !superclass.equals(Object.class)) {
            return getGenericType(superclass, targetInterface,typeMap);
        }

        return null;
    }

    private static void resolveTypeArguments(ParameterizedType parameterizedType, Map<TypeVariable<?>, Type> typeMap) {
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        TypeVariable<?>[] typeVariables = ((Class<?>) parameterizedType.getRawType()).getTypeParameters();
        for (int i = 0; i < typeVariables.length; i++) {
            typeMap.put(typeVariables[i], actualTypeArguments[i]);
        }
    }

    private static Class<?> getClassForType(Type type, Map<TypeVariable<?>, Type> typeMap) {
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        } else if (type instanceof TypeVariable<?>) {
            Type resolvedType = typeMap.get(type);
            if (resolvedType instanceof Class<?>) {
                return (Class<?>) resolvedType;
            } else {
                return getClassForType(resolvedType, typeMap);
            }
        } else if (type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) type).getRawType();
        }
        return null;
    }
}
