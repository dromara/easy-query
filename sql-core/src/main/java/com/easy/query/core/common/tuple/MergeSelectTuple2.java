package com.easy.query.core.common.tuple;

/**
 * create time 2023/12/7 11:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class MergeSelectTuple2<T1,T2,TR> {
    public final T1 t1;
    public final T2 t2;
    public final TR r;

    public MergeSelectTuple2(T1 t1, T2 t2, TR r){

        this.t1 = t1;
        this.t2 = t2;
        this.r = r;
    }
}
