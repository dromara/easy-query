package com.easy.query.core.util;

import java.util.*;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: ArrayUtil.java
 * @Description: 文件说明
 * @Date: 2023/2/26 14:07
 */
public class ArrayUtil {
    public static int sum(int[] arrays) {
        int length = arrays.length;
        if (length > 0) {
            int sum = 0;
            for (int array : arrays) {
                sum += array;
            }
            return sum;
        }
        return 0;
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }

    /**
     * 是否存在不一样的元素,当元素个数大于等于2个的时候
     * @param collection
     * @param keySelector
     * @return false表示都是同一个,true表示存在不一样的
     * @param <T>
     * @param <K>
     */
    public static <T,K> boolean hasDifferent(Collection<T> collection,Function<T,K> keySelector) {
         if(isEmpty(collection)){
             return false;
         }
         if(collection.size()<=1){
             return false;
         }
        Iterator<T> iterator = collection.iterator();
        T first = iterator.next();
        K firstKey = keySelector.apply(first);
        while(iterator.hasNext()){
            T next = iterator.next();
            K nextKey = keySelector.apply(next);
            if(!Objects.equals(firstKey,nextKey))
            {
                return true;
            }
        }
        return false;
    }


    public static <TSource> TSource firstOrDefault(List<TSource> source, TSource def) {
        TSource result = firstOrNull(source);
        if (result == null) {
            return def;
        }
        return result;
    }

    public static <TSource> TSource firstOrNull(List<TSource> source) {
        if (source == null) {
            return null;
        }
        if (source.isEmpty()) {
            return null;
        }
        return source.get(0);
    }

    public static <TResult, TSource> List<TResult> map(List<TSource> source, Function<? super TSource, ? extends TResult> mapper) {
        if (isEmpty(source)) {
            return Collections.emptyList();
        }
        ArrayList<TResult> r = new ArrayList<>(source.size());
        for (TSource tSource : source) {
            r.add(mapper.apply(tSource));
        }
        return r;
    }

    public static byte[] mergeByteArrays(byte[]... arrays) {
        int length = 0;
        for (byte[] array : arrays) {
            length += array.length;
        }
        byte[] mergedArray = new byte[length];
        int destPos = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, mergedArray, destPos, array.length);
            destPos += array.length;
        }
        return mergedArray;
    }

    public static byte[][] splitByteArray(byte[] array, int chunkSize) {
        if (array == null || array.length == 0 || chunkSize <= 0) {
            return new byte[0][];
        }
        int size = (array.length + chunkSize - 1) / chunkSize;
        byte[][] chunks = new byte[size][];
        for (int i = 0; i < size; i++) {
            int start = i * chunkSize;
            int end = Math.min(array.length, start + chunkSize);
            chunks[i] = Arrays.copyOfRange(array, start, end);
        }
        return chunks;
    }

    public static Set<String> getIntersection(Collection<Set<String>> list) {
        if(isEmpty(list)){
            return Collections.emptySet();
        }
        // 创建一个新的Set集合来存储交集
        Iterator<Set<String>> iterator = list.iterator();
        Set<String> intersection = new HashSet<>(iterator.next());

        while(iterator.hasNext()){
            intersection.retainAll(iterator.next());
        }

        // 返回交集
        return intersection;
    }
}
