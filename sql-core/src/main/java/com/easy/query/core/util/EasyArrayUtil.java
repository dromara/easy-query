package com.easy.query.core.util;

import java.util.Arrays;

/**
 * create time 2023/10/11 22:29
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyArrayUtil {
//    public static boolean isArray(Object value){
//        return value!=null&&value.getClass().isArray();
//    }

    /**
     * 判断数组是否为空
     *
     * @param array 数组
     * @param <T>   数组类型
     * @return {@code true} 数组为空，{@code false} 数组不为空
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }


    /**
     * 判断数组是否不为空
     *
     * @param array 数组
     * @param <T>   数组类型
     * @return {@code true} 数组不为空，{@code false} 数组为空
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return !isEmpty(array);
    }


    /**
     * 合并两个数组为一个新的数组
     *
     * @param first  第一个数组
     * @param second 第二个数组
     * @param <T> 数组类型
     * @return 新的数组
     */
    public static <T> T[] concat(T[] first, T[] second) {
        if (first == null && second == null) {
            throw new IllegalArgumentException("not allow first and second are null.");
        } else if (isEmpty(first) && second != null) {
            return second;
        } else if (isEmpty(second)) {
            return first;
        } else {
            T[] result = Arrays.copyOf(first, first.length + second.length);
            System.arraycopy(second, 0, result, first.length, second.length);
            return result;
        }
    }


//    public static <TSource, TElement> Iterable<TElement> select(TSource[] sources, Selector<TSource, TElement> selector) {
//        int size = sources.length;
//        List<TElement> result = new ArrayList<>(size);
//        int i = 0;
//        for (TSource source : sources) {
//            TElement element = selector.apply(source, i);
//            result.add(element);
//            i++;
//        }
//        return result;
//    }
}
