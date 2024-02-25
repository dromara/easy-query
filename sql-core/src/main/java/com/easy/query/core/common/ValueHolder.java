package com.easy.query.core.common;

/**
 * create time 2024/2/23 11:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class ValueHolder<T> {
    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
