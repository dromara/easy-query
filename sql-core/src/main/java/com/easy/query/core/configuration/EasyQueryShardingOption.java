package com.easy.query.core.configuration;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Set;

/**
 * create time 2023/5/12 16:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryShardingOption {

    private final Set<ShardingDataSource> shardingDataSources;

    public EasyQueryShardingOption(Set<ShardingDataSource> shardingDataSources){

        this.shardingDataSources = shardingDataSources;
    }

    public Set<ShardingDataSource> getShardingDataSources() {
        return shardingDataSources;
    }
}
