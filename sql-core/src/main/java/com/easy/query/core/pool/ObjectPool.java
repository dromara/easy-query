package com.easy.query.core.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Supplier;

/**
 * create time 2023/6/2 19:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class ObjectPool<T extends Resettable> {

    public static <V extends Resettable> ObjectPool<V> create(int capacity, Supplier<V> factory) {
        return new ObjectPool<>(capacity, factory);
    }

    public final ArrayBlockingQueue<T> pool;
    private final Supplier<T> factory;

    public ObjectPool(int capacity, Supplier<T> factory) {
        pool = new ArrayBlockingQueue<>(capacity);
        this.factory = factory;
    }

    public T get() {
        T item = pool.poll();
        if (item == null) {
            item = factory.get();
        }
        return item;
    }

    public void release(T item) {
        item.reset();
        pool.offer(item);
    }

}
