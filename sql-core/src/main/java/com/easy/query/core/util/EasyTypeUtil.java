package com.easy.query.core.util;

import com.easy.query.core.exception.EasyQueryException;

/**
 * create time 2023/10/11 15:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyTypeUtil {

    @SuppressWarnings("unchecked")
    public static <T, N> Class<N> cast(Class<T>  original) {
        if (original == null) {
            return null;
        }
        try {
            return (Class<N>) original;
        } catch (Exception e) {
            throw new EasyQueryException(e.getMessage(), e);
        }
    }
}
