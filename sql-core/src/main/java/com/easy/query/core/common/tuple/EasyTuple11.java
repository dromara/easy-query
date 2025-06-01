package com.easy.query.core.common.tuple;

/**
 * create time 2023/8/15 21:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyTuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9,T10,T11> {
    private final T1  t;
    private final T2  t1;
    private final T3  t2;
    private final T4  t3;
    private final T5  t4;
    private final T6  t5;
    private final T7  t6;
    private final T8  t7;
    private final T9  t8;
    private final T10 t9;
    private final T11 t11;

    public EasyTuple11(T1 t, T2 t1, T3 t2, T4 t3, T5 t4, T6 t5, T7 t6, T8 t7, T9 t8, T10 t9, T11 t11) {
        this.t=  t;
        this.t1= t1;
        this.t2= t2;
        this.t3= t3;
        this.t4= t4;
        this.t5= t5;
        this.t6= t6;
        this.t7= t7;
        this.t8= t8;
        this.t9= t9;
        this.t11 = t11;
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

    public T4 t3() {
        return t3;
    }

    public T5 t4() {
        return t4;
    }

    public T6 t5() {
        return t5;
    }

    public T7 t6() {
        return t6;
    }

    public T8 t7() {
        return t7;
    }

    public T9 t8() {
        return t8;
    }
    public T10 t9() {
        return t9;
    }
    public T11 t11() {
        return t11;
    }
}