package com.easy.query.core.expression.lambda;

/**
 * create time 2023/3/28 16:55
 * 文件说明
 *
 * @author xuejiaming
 */
public interface PropertySetterCaller<T> {
    void call(T t,Object value);
}
