package com.easy.query.core.sharding.initializer;

import com.easy.query.core.enums.sharding.ConnectionModeEnum;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

/**
 * create time 2023/5/8 08:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingInitOption {
    private final Map<String, Collection<String>> actualTableNames;
    private final double reverseFactor;
    private final long minReverseTotal;
    private final Comparator<String> defaultTableNameComparator;
    private final Map<String, Boolean> sequenceProperties;
    private final int maxShardingQueryLimit;
    private final int sequenceCompareMethods;
    private final int sequenceCompareAscMethods;
    private final int sequenceLimitMethods;
    private final int sequenceConnectionModeMethods;
    private final ConnectionModeEnum connectionMode;

    public ShardingInitOption(Map<String, Collection<String>> actualTableNames,
                              double reverseFactor,
                              long minReverseTotal,
                              Comparator<String> defaultTableNameComparator,
                              Map<String, Boolean/*asc or desc*/> sequenceProperties,
                              int maxShardingQueryLimit,
                              int sequenceCompareMethods,
                              int sequenceCompareAscMethods,
                              int sequenceLimitMethods,
                              int sequenceConnectionModeMethods,
                              ConnectionModeEnum connectionMode){

        this.actualTableNames = actualTableNames;
        this.reverseFactor = reverseFactor;
        this.minReverseTotal = minReverseTotal;
        this.defaultTableNameComparator = defaultTableNameComparator;
        this.sequenceProperties = sequenceProperties;
        this.maxShardingQueryLimit = maxShardingQueryLimit;
        this.sequenceCompareMethods = sequenceCompareMethods;
        this.sequenceCompareAscMethods = sequenceCompareAscMethods;
        this.sequenceLimitMethods = sequenceLimitMethods;
        this.sequenceConnectionModeMethods = sequenceConnectionModeMethods;
        this.connectionMode = connectionMode;
    }

    public Map<String, Collection<String>> getActualTableNames() {
        return actualTableNames;
    }

    public double getReverseFactor() {
        return reverseFactor;
    }

    public long getMinReverseTotal() {
        return minReverseTotal;
    }

    public Comparator<String> getDefaultTableNameComparator() {
        return defaultTableNameComparator;
    }

    public Map<String, Boolean> getSequenceProperties() {
        return sequenceProperties;
    }

    public int getMaxShardingQueryLimit() {
        return maxShardingQueryLimit;
    }

    public int getSequenceCompareMethods() {
        return sequenceCompareMethods;
    }

    public int getSequenceCompareAscMethods() {
        return sequenceCompareAscMethods;
    }

    public int getSequenceLimitMethods() {
        return sequenceLimitMethods;
    }

    public int getSequenceConnectionModeMethods() {
        return sequenceConnectionModeMethods;
    }

    public ConnectionModeEnum getConnectionMode() {
        return connectionMode;
    }
}
