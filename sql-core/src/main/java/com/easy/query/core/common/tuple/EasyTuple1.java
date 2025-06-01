package com.easy.query.core.common.tuple;

/**
 * create time 2023/7/2 21:37
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyTuple1<T1> {
    private final T1 t;

    public EasyTuple1(T1 t) {

        this.t = t;
    }

    public T1 getT() {
        return t;
    }
}
