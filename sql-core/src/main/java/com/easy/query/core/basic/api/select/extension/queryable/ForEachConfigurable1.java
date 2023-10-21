package com.easy.query.core.basic.api.select.extension.queryable;

import com.easy.query.core.basic.api.select.executor.MethodQuery;

import java.util.function.Consumer;

/**
 * create time 2023/10/20 22:18
 * 结果集映射配置
 *
 * @author xuejiaming
 */
public interface ForEachConfigurable1<T1> {
    MethodQuery<T1> forEach(Consumer<T1> mapConfigure);
}
