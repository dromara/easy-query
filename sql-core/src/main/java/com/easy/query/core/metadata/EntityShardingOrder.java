package com.easy.query.core.metadata;

import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.sharding.initializer.ExecuteMethodBehavior;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * create time 2023/5/8 08:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityShardingOrder {
    private final Comparator<String> tableComparator;
    private final ConnectionModeEnum connectionMode;
    private final int connectionsLimit;
    private final Map<String, Boolean> sequenceProperties;
    private final ExecuteMethodBehavior executeMethodBehavior;

    public EntityShardingOrder(Comparator<String> tableComparator, ConnectionModeEnum connectionMode, int connectionsLimit, Map<String, Boolean> sequenceProperties, ExecuteMethodBehavior executeMethodBehavior) {
        this.tableComparator = tableComparator;
        this.connectionMode = connectionMode;

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

    public ConnectionModeEnum getConnectionMode() {
        return connectionMode;
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

    public String getFirstSequencePropertyOrNull(){
        Set<Map.Entry<String, Boolean>> entries = sequenceProperties.entrySet();
        Map.Entry<String, Boolean> firstPropertyKv = EasyCollectionUtil.firstOrNull(entries);
        if(firstPropertyKv!=null){
            return firstPropertyKv.getKey();
        }
        return null;
    }
}
