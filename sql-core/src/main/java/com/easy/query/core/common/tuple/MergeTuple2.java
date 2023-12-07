package com.easy.query.core.common.tuple;

/**
 * create time 2023/12/7 11:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class MergeTuple2<T1,T2> {
    public final T1 t1;
    public final T2 t2;

    public MergeTuple2(T1 t1, T2 t2){

        this.t1 = t1;
        this.t2 = t2;
    }
}
