package com.easy.query.core.util;

import java.util.Collection;
import java.util.List;

/**
 * @FileName: ArrayUtil.java
 * @Description: 文件说明
 * @Date: 2023/2/26 14:07
 * @Created by xuejiaming
 */
public class ArrayUtil {
    public static int sum(int[] arrays){
        int length = arrays.length;
        if(length>0){
            int sum=0;
            for (int array : arrays) {
                sum += array;
            }
            return  sum;
        }
        return 0;
    }
    public static <T> boolean isEmpty(Collection<T> collection){
        return collection==null || collection.isEmpty();
    }
    public static  <T> boolean isNotEmpty(Collection<T> collection){
        return !isEmpty(collection);
    }

    public static <TSource> TSource firstOrDefault(List<TSource> source, TSource def){
        TSource result = firstOrNull(source);
        if (result == null) {
            return def;
        }
        return result;
    }
    public static <TSource> TSource firstOrNull(List<TSource> source){
        if(source==null){
            return null;
        }
        if (source.isEmpty()) {
            return null;
        }
        return source.get(0);
    }
}
