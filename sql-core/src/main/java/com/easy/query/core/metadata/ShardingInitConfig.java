package com.easy.query.core.metadata;

/**
 * create time 2023/5/8 08:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingInitConfig {
    /**
     * 大于0
     */
    private final double reverseFactor;
    /**
     * 大于1启用
     */
    private final long minReverseTotal;
    private final ShardingSequenceConfig shardingSequenceConfig;

    public ShardingInitConfig(
            double reverseFactor,
            long minReverseTotal, ShardingSequenceConfig shardingSequenceConfig) {
        this.reverseFactor = reverseFactor;
        this.minReverseTotal = minReverseTotal;

        this.shardingSequenceConfig = shardingSequenceConfig;
    }

    public double getReverseFactor() {
        return reverseFactor;
    }

    public long getMinReverseTotal() {
        return minReverseTotal;
    }

    public ShardingSequenceConfig getShardingSequenceConfig() {
        return shardingSequenceConfig;
    }

    public boolean isReverse(){
        return reverseFactor>0d&&minReverseTotal>1L;
    }
}
