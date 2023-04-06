package com.easy.query.core.sharding;

/**
 * create time 2023/4/5 13:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyShardingOption {
    private final int maxQueryConnectionsLimit;

    public  EasyShardingOption(int maxQueryConnectionsLimit){

        this.maxQueryConnectionsLimit = maxQueryConnectionsLimit;
    }

    public int getMaxQueryConnectionsLimit() {
        return maxQueryConnectionsLimit;
    }
}
