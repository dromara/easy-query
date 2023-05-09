package com.easy.query.core.sharding.initializer;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * create time 2023/5/8 08:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingInitOption {
    private final Map<String, Collection<String>> actualTableNames;
    private final Comparator<String> defaultTableNameComparator;
    private final boolean reverse;
    private final Map<String,Boolean/*asc or desc*/> sequenceProperties;
    private final int connectionsLimit;
    private final ExecuteMethodBehavior executeMethodBehavior;

    public ShardingInitOption(Map<String, Collection<String>> actualTableNames, Comparator<String> defaultTableNameComparator,Map<String,Boolean/*asc or desc*/> sequenceProperties, boolean reverse, int connectionsLimit,ExecuteMethodBehavior executeMethodBehavior) {
        this.actualTableNames = actualTableNames;
        this.defaultTableNameComparator = defaultTableNameComparator;
        this.sequenceProperties = sequenceProperties;
        this.reverse = reverse;
        this.connectionsLimit = connectionsLimit;
        this.executeMethodBehavior = executeMethodBehavior;
    }

    public Map<String, Collection<String>> getActualTableNames() {
        return actualTableNames;
    }

    public Comparator<String> getDefaultTableNameComparator() {
        return defaultTableNameComparator;
    }

    public boolean isReverse() {
        return reverse;
    }

    public Map<String, Boolean> getSequenceProperties() {
        return sequenceProperties;
    }

    public int getConnectionsLimit() {
        return connectionsLimit;
    }

    public ExecuteMethodBehavior getExecuteMethodBehavior() {
        return executeMethodBehavior;
    }
}
