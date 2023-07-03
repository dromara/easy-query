package com.easy.query.core.common.tuple;

/**
 * create time 2023/7/2 21:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class Tuple3<T1,T2,T3> {
    private final T1 t1;
    private final T2 t2;
    private final T3 t3;

    public Tuple3(T1 t1, T2 t2,T3 t3){

        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
    }

    public T1 getT1() {
        return t1;
    }

    public T2 getT2() {
        return t2;
    }

    public T3 getT3() {
        return t3;
    }
}
