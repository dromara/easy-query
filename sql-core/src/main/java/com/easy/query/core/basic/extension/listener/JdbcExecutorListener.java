package com.easy.query.core.basic.extension.listener;

import java.util.UUID;

/**
 * create time 2023/11/10 22:53
 * 文件说明
 *
 * @author xuejiaming
 */
public interface JdbcExecutorListener {
    boolean enable();
    default String createTraceId(){
        return UUID.randomUUID().toString();
    }
    void listenBefore(JdbcListenBeforeArg arg);

    void listenAfter(JdbcListenAfterArg afterArg);

}
