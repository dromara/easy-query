package com.easy.query.core.basic.api.select.executor;

import com.easy.query.core.basic.api.select.QueryAvailable;

/**
 * create time 2023/10/21 09:40
 * 获取方法结果查询自定义
 *
 * @author xuejiaming
 */
public interface MethodResultQuery<T> extends QueryAvailable<T>,FirstResultAble<T>,SingleResultAble<T>,ListResultAble<T>,TreeListResultAble<T>,PageAble<T>,StreamResultAble<T> {
}
