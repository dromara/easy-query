package com.easy.query.core.common;

/**
 * create time 2024/2/23 11:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class ValueHolder2<T1,T2> {
    private T1 value1;
    private T2 value2;
    public ValueHolder2() {
    }
    public ValueHolder2(T1 value1,T2 value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public T1 getValue1() {
        return value1;
    }

    public void setValue1(T1 value1) {
        this.value1 = value1;
    }

    public T2 getValue2() {
        return value2;
    }

    public void setValue2(T2 value2) {
        this.value2 = value2;
    }
}
