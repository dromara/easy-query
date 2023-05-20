package com.easy.query.core.util;

import com.easy.query.core.exception.EasyQueryException;

/**
 * create time 2023/5/20 22:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyObjectUtil {

    private EasyObjectUtil(){}

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
}
