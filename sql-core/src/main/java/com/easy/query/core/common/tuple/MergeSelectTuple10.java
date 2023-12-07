package com.easy.query.core.common.tuple;

/**
 * create time 2023/12/7 12:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class MergeSelectTuple10<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10, TR> {
    public final T1 t1;
    public final T2 t2;
    public final T3 t3;
    public final T4 t4;
    public final T5 t5;
    public final T6 t6;
    public final T7 t7;
    public final T8 t8;
    public final T9 t9;
    public final T10 t10;
    public final TR r;

    public MergeSelectTuple10(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, TR r) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.t5 = t5;
        this.t6 = t6;
        this.t7 = t7;
        this.t8 = t8;
        this.t9 = t9;
        this.t10 = t10;
        this.r = r;
    }
}