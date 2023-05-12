package com.easy.query.core.configuration;

import javax.sql.DataSource;
import java.util.Map;

/**
 * create time 2023/5/12 16:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryShardingOption {
    private final Map<String, DataSource> shardingConfig;

    public EasyQueryShardingOption(Map<String, DataSource> shardingConfig){

        this.shardingConfig = shardingConfig;
    }

    public Map<String, DataSource> getShardingConfig() {
        return shardingConfig;
    }
}
