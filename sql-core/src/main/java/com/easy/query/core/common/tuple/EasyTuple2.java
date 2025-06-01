package com.easy.query.core.common.tuple;

/**
 * create time 2023/7/2 21:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyTuple2<T1, T2> {
    private final T1 t;
    private final T2 t1;

    public EasyTuple2(T1 t, T2 t1) {

        this.t = t;
        this.t1 = t1;
    }


    public T1 t() {
        return t;
    }

    public T2 t1() {
        return t1;
    }
}
