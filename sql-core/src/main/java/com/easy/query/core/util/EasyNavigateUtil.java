package com.easy.query.core.util;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;

/**
 * create time 2024/10/14 11:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyNavigateUtil {
    public static void checkProperties(String[] selfProperties, String[] targetProperties) {
        if (selfProperties == null) {
            throw new IllegalArgumentException("selfProperties is null");
        }
        if (targetProperties == null) {
            throw new IllegalArgumentException("targetProperties is null");
        }
        if (selfProperties.length > 1 || targetProperties.length > 1) {
            if (selfProperties.length != targetProperties.length) {
                throw new EasyQueryInvalidOperationException("selfProperties.length != targetProperties.length");
            }
        }
    }
}
