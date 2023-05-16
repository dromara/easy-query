package com.easy.query.core.sharding.initializer;


import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.sharding.route.table.TableUnit;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * create time 2023/5/13 23:53
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingInitOptionBuilder {
    private Map<String, Collection<String>> actualTableNames;
    private double reverseFactor = 0;
    private long minReverseTotal = 0;
    private Comparator<TableUnit> defaultTableNameComparator;
    private Map<String, Boolean/*asc or desc*/> sequenceProperties = new HashMap<>();
    private int maxShardingQueryLimit = 0;
    private int sequenceCompareMethods = ExecuteMethodEnum.UNKNOWN.getCode();
    private int sequenceCompareAscMethods = ExecuteMethodEnum.UNKNOWN.getCode();
    private int sequenceLimitMethods = ExecuteMethodEnum.UNKNOWN.getCode();
    private int sequenceConnectionModeMethods = ExecuteMethodEnum.UNKNOWN.getCode();
    private ConnectionModeEnum connectionMode = null;

    public Map<String, Collection<String>> getActualTableNames() {
        return actualTableNames;
    }

    public void setActualTableNames(Map<String, Collection<String>> actualTableNames) {
        this.actualTableNames = actualTableNames;
    }

    public double getReverseFactor() {
        return reverseFactor;
    }

    public void setReverseFactor(double reverseFactor) {
        this.reverseFactor = reverseFactor;
    }

    public long getMinReverseTotal() {
        return minReverseTotal;
    }

    public void setMinReverseTotal(long minReverseTotal) {
        this.minReverseTotal = minReverseTotal;
    }

    public Comparator<TableUnit> getDefaultTableNameComparator() {
        return defaultTableNameComparator;
    }

    public void setDefaultTableNameComparator(Comparator<TableUnit> defaultTableNameComparator) {
        this.defaultTableNameComparator = defaultTableNameComparator;
    }

    public Map<String, Boolean> getSequenceProperties() {
        return sequenceProperties;
    }

    public void setSequenceProperties(Map<String, Boolean> sequenceProperties) {
        this.sequenceProperties = sequenceProperties;
    }

    public int getMaxShardingQueryLimit() {
        return maxShardingQueryLimit;
    }

    public void setMaxShardingQueryLimit(int maxShardingQueryLimit) {
        this.maxShardingQueryLimit = maxShardingQueryLimit;
    }

    public int getSequenceCompareMethods() {
        return sequenceCompareMethods;
    }

    public void setSequenceCompareMethods(int sequenceCompareMethods) {
        this.sequenceCompareMethods = sequenceCompareMethods;
    }

    public int getSequenceCompareAscMethods() {
        return sequenceCompareAscMethods;
    }

    public void setSequenceCompareAscMethods(int sequenceCompareAscMethods) {
        this.sequenceCompareAscMethods = sequenceCompareAscMethods;
    }

    public int getSequenceLimitMethods() {
        return sequenceLimitMethods;
    }

    public void setSequenceLimitMethods(int sequenceLimitMethods) {
        this.sequenceLimitMethods = sequenceLimitMethods;
    }

    public int getSequenceConnectionModeMethods() {
        return sequenceConnectionModeMethods;
    }

    public void setSequenceConnectionModeMethods(int sequenceConnectionModeMethods) {
        this.sequenceConnectionModeMethods = sequenceConnectionModeMethods;
    }

    public ConnectionModeEnum getConnectionMode() {
        return connectionMode;
    }

    public void setConnectionMode(ConnectionModeEnum connectionMode) {
        this.connectionMode = connectionMode;
    }


    public ShardingInitOption build(){

        return new ShardingInitOption(actualTableNames,
                reverseFactor,
                minReverseTotal,
                defaultTableNameComparator,
                sequenceProperties,
                maxShardingQueryLimit,
                sequenceCompareMethods,
                sequenceCompareAscMethods,
                sequenceLimitMethods,
                sequenceConnectionModeMethods,
                connectionMode);

    }
}
