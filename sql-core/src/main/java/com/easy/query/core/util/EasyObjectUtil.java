package com.easy.query.core.util;

import com.easy.query.core.exception.EasyQueryException;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * create time 2023/5/20 22:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyObjectUtil {

    private EasyObjectUtil(){}

    public static <T,TR> TR getValueOrNull(T source, Function<T,TR> getter){
        return getValueOrDefault(source,getter,null);
    }
    public static <T,TR> TR getValueOrDefault(T source, Function<T,TR> getter,TR def){
        if(Objects.isNull(source)){
            return def;
        }
        return getter.apply(source);
    }

    /**
     * 强制类型转换
     * @param original 原始对象
     * @param <T> 原始类型
     * @param <N> 目标类型
     * @return 目标对象
     */
    @SuppressWarnings("unchecked")
    public static <T, N> N typeCast(T original) {
        try {
            return (N) original;
        } catch (Exception e) {
            throw new EasyQueryException(e.getMessage(), e);
        }
    }

    /**
     * 强制类型转换, 支持null
     * @param original 原始对象
     * @param <T> 原始类型
     * @param <N> 目标类型
     * @return 目标对象
     */
    public static <T, N> N typeCastNullable(T original) {
        if (original == null) {
            return null;
        }
        return typeCast(original);
    }
    /**
     * 判断是否为空
     * @param obj 所有类型
     * @return true 为空false不为空
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof Optional) {
            return !((Optional<?>) obj).isPresent();
        } else if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj) == 0;
        } else if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        } else {
            return obj instanceof Map && ((Map<?, ?>) obj).isEmpty();
        }
    }
}
