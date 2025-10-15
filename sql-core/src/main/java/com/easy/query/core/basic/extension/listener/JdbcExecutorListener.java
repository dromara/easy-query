package com.easy.query.core.basic.extension.listener;

import java.util.UUID;

/**
 * create time 2023/11/10 22:53
 * 文件说明
 *
 * @author xuejiaming
 */
public interface JdbcExecutorListener {
    /**
     * 是否启用监听
     * @return
     */
    boolean enable();
    default String createTraceId(){
        return UUID.randomUUID().toString();
    }
    void onExecuteBefore(JdbcExecuteBeforeArg arg);

    void onExecuteAfter(JdbcExecuteAfterArg afterArg);
    default void onQueryRows(String traceId,long total){

    }
}
