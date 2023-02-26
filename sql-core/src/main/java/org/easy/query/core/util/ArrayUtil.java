package org.easy.query.core.util;

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
}
