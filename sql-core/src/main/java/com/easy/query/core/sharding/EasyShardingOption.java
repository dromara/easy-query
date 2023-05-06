package com.easy.query.core.sharding;

/**
 * create time 2023/4/5 13:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyShardingOption {
    private final int maxQueryConnectionsLimit;
    private final int executorSize;

    public  EasyShardingOption(int maxQueryConnectionsLimit,int executorSize){
        if(executorSize<0){
            throw new IllegalArgumentException("executor size less than zero");
        }
        if(executorSize>0){
            if(maxQueryConnectionsLimit>executorSize){
                throw new IllegalArgumentException("maxQueryConnectionsLimit:"+maxQueryConnectionsLimit+" less than executorSize:"+executorSize);
            }
        }

        this.maxQueryConnectionsLimit = maxQueryConnectionsLimit;
        this.executorSize = executorSize;
    }

    public int getMaxQueryConnectionsLimit() {
        return maxQueryConnectionsLimit;
    }

    public int getExecutorSize() {
        return executorSize;
    }
}
