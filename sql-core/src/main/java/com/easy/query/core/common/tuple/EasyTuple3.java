package com.easy.query.core.common.tuple;

/**
 * create time 2023/7/2 21:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyTuple3<T1, T2, T3> {
    private final T1 t;
    private final T2 t1;
    private final T3 t2;

    public EasyTuple3(T1 t, T2 t1, T3 t2) {
        this.t = t;
        this.t1 = t1;
        this.t2 = t2;
    }


    public T1 t() {
        return t;
    }

    public T2 t1() {
        return t1;
    }

    public T3 t2() {
        return t2;
    }
}
