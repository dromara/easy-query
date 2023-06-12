package com.easy.query.core.job;

import com.easy.query.core.inject.ServiceProvider;

/**
 * create time 2023/6/12 15:47
 * 文件说明
 *
 * @author xuejiaming
 */
public class TimeJobEntry {
    private final TimeJob timeJob;
    private long nextTime = 0L;

    public TimeJobEntry(TimeJob timeJob) {

        this.timeJob = timeJob;
        nextTime = timeJob.calcNextTime();
    }

    public void calcNextTime() {
        this.nextTime = timeJob.calcNextTime()-120*1000; //提前两分钟
    }
    public long getNextTime(){
        return this.nextTime;
    }
    public void execute(ServiceProvider serviceProvider){
        timeJob.execute(serviceProvider);
    }
    public String jobName(){
        return timeJob.jobName();
    }

}
