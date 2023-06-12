package com.easy.query.core.job;

import com.easy.query.core.inject.ServiceProvider;

/**
 * create time 2023/6/12 14:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TimeJob {
    /**
     * 任务名称
     * @return
     */
    String jobName();

    /**
     * 计算下次轮询时间
     * @return
     */
    long calcNextTime();

    /**
     * 执行任务
     * @param serviceProvider
     */
    void execute(ServiceProvider serviceProvider);

}
