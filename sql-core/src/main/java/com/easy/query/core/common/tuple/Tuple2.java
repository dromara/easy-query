package com.easy.query.core.common.tuple;

/**
 * create time 2023/7/2 21:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class Tuple2<T1, T2> {
    private final T1 t1;
    private final T2 t2;

    public Tuple2(T1 t1,T2 t2) {

        this.t1 = t1;
        this.t2 = t2;
    }


    public T1 t1() {
        return t1;
    }

    public T2 t2() {
        return t2;
    }
}
