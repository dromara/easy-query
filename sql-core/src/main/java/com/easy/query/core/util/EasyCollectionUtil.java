package com.easy.query.core.util;

import com.easy.query.core.expression.lambda.Selector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author xuejiaming
 * @FileName: ArrayUtil.java
 * @Description: 文件说明
 * @Date: 2023/2/26 14:07
 */
public class EasyCollectionUtil {
    public static <TSource> boolean isNotSingle(Collection<TSource> sources) {
        return !isSingle(sources);
    }

    public static <TSource> boolean isSingle(Collection<TSource> sources) {
        if (sources == null) {
            return false;
        }
        return sources.size() == 1;
    }

    public static <TSource, TElement> List<TElement> select(Collection<TSource> sources, Selector<TSource, TElement> selector) {
        int size = sources.size();
        List<TElement> result = new ArrayList<>(size);
        int i = 0;
        for (TSource source : sources) {
            TElement element = selector.apply(source, i);
            result.add(element);
            i++;
        }
        return result;
    }

    public static <TSource> boolean any(Collection<TSource> sources, Predicate<TSource> predicate) {
        if (isEmpty(sources)) {
            return false;
        }
        for (TSource source : sources) {
            if (predicate.test(source)) {
                return true;
            }
        }
        return false;
    }

    public static <TSource> boolean all(Collection<TSource> sources, Predicate<TSource> predicate) {
        if (isEmpty(sources)) {
            return false;
        }
        for (TSource source : sources) {
            if (!predicate.test(source)) {
                return false;
            }
        }
        return true;
    }

    //    public static <TSource,TElement> List<TElement> select(List<TSource> sources, Selector<TSource,TElement> selector){
//        int size = sources.size();
//        List<TElement> result = new ArrayList<>(size);
//        for (int i = 0; i < size; i++) {
//            TElement element = selector.apply(sources.get(i), i);
//            result.add(element);
//        }
//        return result;
//    }
    public static <T> List<List<T>> partition(List<T> list, int size) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List cannot be null or empty.");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be positive.");
        }
        List<List<T>> partitions = new ArrayList<>();
        int numberOfPartitions = (int) Math.ceil((double) list.size() / size);
        for (int i = 0; i < numberOfPartitions; i++) {
            int start = i * size;
            int end = Math.min(start + size, list.size());
            partitions.add(new ArrayList<>(list.subList(start, end)));
        }
        return partitions;
    }

    public static <TSource> int sum(Collection<TSource> arrays, Function<TSource, Integer> selector) {
        int length = arrays.size();
        if (length > 0) {
            int sum = 0;
            for (TSource array : arrays) {
                sum += selector.apply(array);
            }
            return sum;
        }
        return 0;
    }

    public static <TSource> long sumLong(Collection<TSource> arrays, Function<TSource, Long> selector) {
        int length = arrays.size();
        if (length > 0) {
            long sum = 0;
            for (TSource array : arrays) {
                sum += selector.apply(array);
            }
            return sum;
        }
        return 0L;
    }

    public static long sum(Collection<Long> arrays) {
        int length = arrays.size();
        if (length > 0) {
            long sum = 0;
            for (Long array : arrays) {
                sum += array;
            }
            return sum;
        }
        return 0;
    }

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
     *
     * @param collection
     * @param keySelector
     * @param <T>
     * @param <K>
     * @return false表示都是同一个, true表示存在不一样的
     */
    public static <T, K> boolean hasDifferent(Collection<T> collection, Function<T, K> keySelector) {
        if (isEmpty(collection)) {
            return false;
        }
        if (collection.size() <= 1) {
            return false;
        }
        Iterator<T> iterator = collection.iterator();
        T first = iterator.next();
        K firstKey = keySelector.apply(first);
        while (iterator.hasNext()) {
            T next = iterator.next();
            K nextKey = keySelector.apply(next);
            if (!Objects.equals(firstKey, nextKey)) {
                return true;
            }
        }
        return false;
    }

    public static <TSource> List<TSource> filter(List<TSource> source, java.util.function.Predicate<TSource> predicate) {
        if (isEmpty(source)) {
            return Collections.emptyList();
        }
        ArrayList<TSource> result = new ArrayList<>(source.size());
        for (TSource tSource : source) {
            if (predicate.test(tSource)) {
                result.add(tSource);
            }
        }
        return result;
    }

    public static <TSource> TSource firstOrDefault(List<TSource> source, java.util.function.Predicate<TSource> predicate, TSource def) {
        if (source == null) {
            return def;
        }
        if (source.isEmpty()) {
            return def;
        }
        for (TSource tSource : source) {
            if (predicate.test(tSource)) {
                return tSource;
            }
        }
        return def;
    }

    public static <TSource> TSource firstOrDefault(List<TSource> source, TSource def) {
        TSource result = firstOrNull(source);
        if (result == null) {
            return def;
        }
        return result;
    }

    public static <TSource> TSource firstOrNull(Collection<TSource> source) {
        if (source == null) {
            return null;
        }
        if (source.isEmpty()) {
            return null;
        }
        if (source instanceof List) {
            return ((List<TSource>) source).get(0);
        }
        return source.iterator().next();
    }

    public static <TSource> TSource first(Collection<TSource> source) {
        if (source == null) {
            throw new NoSuchElementException();
        }
        if (source.isEmpty()) {
            throw new NoSuchElementException();
        }
        if (source instanceof List) {
            return ((List<TSource>) source).get(0);
        }
        return source.iterator().next();
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
        if (isEmpty(list)) {
            return Collections.emptySet();
        }
        // 创建一个新的Set集合来存储交集
        Iterator<Set<String>> iterator = list.iterator();
        Set<String> intersection = new HashSet<>(iterator.next());

        while (iterator.hasNext()) {
            intersection.retainAll(iterator.next());
        }

        // 返回交集
        return intersection;
    }

    public static <T> Collection<Collection<T>> getCartesian(Collection<Set<T>> collections) {
        Collection<Collection<T>> result = new ArrayList<>();
        if (collections == null || collections.isEmpty()) {
            result.add(new ArrayList<>());
            return result;
        } else {
            Collection<T> first = collections.iterator().next();
            Collection<Collection<T>> remaining = getCartesian(new ArrayList<>(collections).subList(1, collections.size()));
            for (T element : first) {
                for (Collection<T> product : remaining) {
                    Collection<T> newProduct = new ArrayList<>();
                    newProduct.add(element);
                    newProduct.addAll(product);
                    result.add(newProduct);
                }
            }
            return result;
        }
    }

    public static <K, V, R> Map<K, R> listToMap(List<V> list, Function<V, K> keyExtractor, Function<V, R> valueExtractor) {
        Map<K, R> map = new HashMap<>();
        for (V element : list) {
            K key = keyExtractor.apply(element);
            R value = valueExtractor.apply(element);
            map.put(key, value);
        }
        return map;
    }
}
