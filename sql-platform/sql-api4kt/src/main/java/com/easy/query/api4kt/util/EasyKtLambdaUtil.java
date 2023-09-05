package com.easy.query.api4kt.util;

import kotlin.reflect.KProperty1;

/**
 * create time 2023/6/3 14:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyKtLambdaUtil {

    private EasyKtLambdaUtil() {
    }

    public static <T> String getPropertyName(KProperty1<? super T, ?> property) {
        if (property == null) {
            return null;
        }
        return property.getName();
    }
}
