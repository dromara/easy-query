package com.easy.query.core.common.tuple;

/**
 * create time 2023/7/2 21:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class Tuple2<T,T1> {
    private final T t;
    private final T1 t1;

    public Tuple2(T t, T1 t1){

        this.t = t;
        this.t1 = t1;
    }

    public T t() {
        return t;
    }

    public T1 t1() {
        return t1;
    }
}
