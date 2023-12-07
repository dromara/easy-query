package com.easy.query.core.common.tuple;

/**
 * create time 2023/12/7 11:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class MergeSelectTuple4<T1,T2,T3,T4, TR> {
    public final T1 t1;
    public final T2 t2;
    public final T3 t3;
    public final T4 t4;
    public final TR r;

    public MergeSelectTuple4(T1 t1, T2 t2, T3 t3, T4 t4, TR r) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
        this.r = r;
    }
}