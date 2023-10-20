package com.easy.query.api4kt.select.extension.queryable;

import com.easy.query.core.basic.api.select.executor.QueryExecutable;

import java.util.function.Consumer;

/**
 * create time 2023/10/20 23:16
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtForEachConfigurable1<T1> {
    QueryExecutable<T1> forEach(Consumer<T1> mapConfigure);
}
