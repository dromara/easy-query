package com.easy.query.core.basic.thread;

import java.util.concurrent.ExecutorService;

/**
 * create time 2023/4/14 21:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyShardingExecutorService {
    ExecutorService getExecutorService();
}
