package com.easy.query.core.basic.api.select.executor;

import com.easy.query.core.basic.api.select.QueryAvailable;

/**
 * create time 2023/10/21 09:40
 * 获取当前类的方法结果
 *
 * @author xuejiaming
 */
public interface MethodQuery<T> extends QueryAvailable<T>,FirstAble<T>,SingleAble<T>,ListAble<T>,PageAble<T>,StreamAble<T> {
}
