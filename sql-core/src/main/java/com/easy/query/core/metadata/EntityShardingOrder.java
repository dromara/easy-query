package com.easy.query.core.metadata;

import com.easy.query.core.sharding.initializer.ExecuteMethodBehavior;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * create time 2023/5/8 08:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityShardingOrder {
    private final Comparator<String> tableComparator;
    private final boolean reverse;
    private final int connectionsLimit;
    private final Map<String, Boolean> sequenceProperties;
    private final ExecuteMethodBehavior executeMethodBehavior;

    public EntityShardingOrder(Comparator<String> tableComparator, boolean reverse, int connectionsLimit, Map<String, Boolean> sequenceProperties,ExecuteMethodBehavior executeMethodBehavior) {
        this.tableComparator = tableComparator;
        this.reverse = reverse;

        this.connectionsLimit = connectionsLimit;
        if (sequenceProperties != null) {
            this.sequenceProperties = new HashMap<>(sequenceProperties);
        } else {
            this.sequenceProperties = Collections.emptyMap();
        }
        this.executeMethodBehavior = executeMethodBehavior;
    }

    public Comparator<String> getTableComparator() {
        return tableComparator;
    }

    public boolean isReverse() {
        return reverse;
    }

    public int getConnectionsLimit() {
        return connectionsLimit;
    }

    public Boolean getSequenceProperty(String propertyName){
        return sequenceProperties.get(propertyName);
    }

    public ExecuteMethodBehavior getExecuteMethodBehavior() {
        return executeMethodBehavior;
    }
}
