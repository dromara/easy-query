package com.easy.query.api4j.select.extension.queryable;

import com.easy.query.core.basic.api.select.executor.MethodQuery;

import java.util.function.Consumer;

/**
 * create time 2023/10/20 23:16
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLForEachConfigurable1<T1> {
    MethodQuery<T1> forEach(Consumer<T1> mapConfigure);
}
