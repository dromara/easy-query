package com.easy.query.core.basic.extension.navigate;

/**
 * create time 2024/9/2 09:12
 * 文件说明
 *
 * @author xuejiaming
 */
public interface NavigateValueSetter<TValue> {
   TValue beforeSet(TValue value);
}
