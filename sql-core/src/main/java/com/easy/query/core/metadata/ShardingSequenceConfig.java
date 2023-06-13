package com.easy.query.core.metadata;

import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.sharding.ConnectionModeEnum;
import com.easy.query.core.sharding.router.table.TableUnit;
import com.easy.query.core.util.EasyBitwiseUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * create time 2023/5/13 22:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingSequenceConfig {
    private final Comparator<TableUnit> tableComparator;
    private final int sequenceCompareMethods;
    private final int sequenceCompareAscMethods;
    private final int sequenceLimitMethods;
    private final int sequenceConnectionModeMethods;
    private final ConnectionModeEnum connectionMode;
    private final int maxShardingQueryLimit;
    private final Map<String, Boolean> sequenceProperties;

    public ShardingSequenceConfig(Comparator<TableUnit> tableComparator,
                                  Map<String, Boolean/*asc or desc*/> sequenceProperties,
                                  int maxShardingQueryLimit,
                                  int sequenceCompareMethods,
                                  int sequenceCompareAscMethods,
                                  int sequenceLimitMethods,
                                  int sequenceConnectionModeMethods,
                                  ConnectionModeEnum connectionMode) {
        this.tableComparator = tableComparator;
        this.maxShardingQueryLimit = maxShardingQueryLimit;
        this.sequenceCompareMethods = sequenceCompareMethods;
        this.sequenceCompareAscMethods = sequenceCompareAscMethods;
        this.sequenceLimitMethods = sequenceLimitMethods;
        this.sequenceConnectionModeMethods = sequenceConnectionModeMethods;
        this.connectionMode = connectionMode;

        if (sequenceProperties != null) {
            this.sequenceProperties = new HashMap<>(sequenceProperties);
        } else {
            this.sequenceProperties = Collections.emptyMap();
        }
    }

    public Comparator<TableUnit> getTableComparator() {
        return tableComparator;
    }

    public ConnectionModeEnum getConnectionModeOrDefault(ExecuteMethodEnum executeMethod,ConnectionModeEnum def) {
        if(connectionMode==null){
            return def;
        }
        if(!hasConnectionModeMethods(executeMethod)){
            return def;
        }
        return connectionMode;
    }

    public int getMaxShardingQueryLimitOrDefault(ExecuteMethodEnum executeMethod,int def) {
        if(maxShardingQueryLimit<=0){
            return def;
        }
        if(!hasLimitMethods(executeMethod)){
            return def;
        }
        return maxShardingQueryLimit;
    }

    public Boolean getSequenceProperty(String propertyName){
        return sequenceProperties.get(propertyName);
    }

    public String getFirstSequencePropertyOrNull(){
        Set<Map.Entry<String, Boolean>> entries = sequenceProperties.entrySet();
        Map.Entry<String, Boolean> firstPropertyKv = EasyCollectionUtil.firstOrNull(entries);
        if(firstPropertyKv!=null){
            return firstPropertyKv.getKey();
        }
        return null;
    }

    public boolean hasCompareMethods(ExecuteMethodEnum executeMethod){
        return EasyBitwiseUtil.hasBit(sequenceCompareMethods,executeMethod.getCode());
    }
    public boolean hasCompareAscMethods(ExecuteMethodEnum executeMethod){
        return EasyBitwiseUtil.hasBit(sequenceCompareAscMethods,executeMethod.getCode());
    }
    public boolean hasLimitMethods(ExecuteMethodEnum executeMethod){
        return EasyBitwiseUtil.hasBit(sequenceLimitMethods,executeMethod.getCode());
    }
    public boolean hasConnectionModeMethods(ExecuteMethodEnum executeMethod){
        return EasyBitwiseUtil.hasBit(sequenceConnectionModeMethods,executeMethod.getCode());
    }
}
