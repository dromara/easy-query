package com.easy.query.core.util;

import java.util.Map;
import java.util.function.Function;

/**
 * create time 2023/6/15 12:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyMapUtil {
    /**
     * 防止jdk8嵌套computeIfAbsent死循环
     * @param map
     * @param key
     * @param mappingFunction
     * @return
     * @param <K>
     * @param <V>
     */
    public static <K,V> V computeIfAbsent(Map<K,V> map, K key, Function<? super K, ? extends V> mappingFunction){
        V v=null;
        if(null==(v=map.get(key))){
            v=map.computeIfAbsent(key,mappingFunction);
        }
        return v;
    }
}
