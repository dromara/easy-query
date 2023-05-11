package com.easy.query.core.sharding.initializer;

import com.easy.query.core.sharding.enums.ConnectionModeEnum;

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
    private final Map<String,Boolean/*asc or desc*/> sequenceProperties;
    private final ConnectionModeEnum connectionMode;
    private final int connectionsLimit;
    private final ExecuteMethodBehavior executeMethodBehavior;

    public ShardingInitOption(Map<String, Collection<String>> actualTableNames, Comparator<String> defaultTableNameComparator, Map<String,Boolean/*asc or desc*/> sequenceProperties, ConnectionModeEnum connectionMode, int connectionsLimit, ExecuteMethodBehavior executeMethodBehavior) {
        this.actualTableNames = actualTableNames;
        this.defaultTableNameComparator = defaultTableNameComparator;
        this.sequenceProperties = sequenceProperties;
        this.connectionMode = connectionMode;
        this.connectionsLimit = connectionsLimit;
        this.executeMethodBehavior = executeMethodBehavior;
    }

    public Map<String, Collection<String>> getActualTableNames() {
        return actualTableNames;
    }

    public Comparator<String> getDefaultTableNameComparator() {
        return defaultTableNameComparator;
    }

    public Map<String, Boolean> getSequenceProperties() {
        return sequenceProperties;
    }

    public ConnectionModeEnum getConnectionMode() {
        return connectionMode;
    }

    public int getConnectionsLimit() {
        return connectionsLimit;
    }

    public ExecuteMethodBehavior getExecuteMethodBehavior() {
        return executeMethodBehavior;
    }
}
