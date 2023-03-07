package org.easy.query.core.util;

import java.util.Collection;

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
}
