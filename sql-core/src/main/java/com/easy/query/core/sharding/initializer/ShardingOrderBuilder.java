package com.easy.query.core.sharding.initializer;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.LambdaUtil;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * create time 2023/5/7 23:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingOrderBuilder<T> {
    private final EntityMetadata entityMetadata;
    private final Comparator<String> defaultTableNameComparator;
    private final boolean defaultReverse;
    private final Map<String,Boolean/*asc or desc*/> sequenceProperties=new HashMap<>();
    private int connectionsLimit =0;
    public ShardingOrderBuilder(EntityMetadata entityMetadata,Comparator<String> defaultTableNameComparator, boolean defaultReverse){

        this.entityMetadata = entityMetadata;
        this.defaultTableNameComparator = defaultTableNameComparator;
        this.defaultReverse = defaultReverse;
    }


    /**
     * 当前属性asc的时候采用 defaultTableComparator的defaultTableNameComparator
     * @param orderProperty
     * @return
     */
    public  ShardingOrderBuilder<T> addPropertyWhenAsc(Property<T,?> orderProperty){
        String propertyName = LambdaUtil.getPropertyName(orderProperty);
        return addPropertyWhenAsc(propertyName);
    }
    public ShardingOrderBuilder<T> addPropertyWhenAsc(String propertyName){
        if(entityMetadata.getColumnOrNull(propertyName)==null){
            throw new EasyQueryInvalidOperationException("sharding initializer add asc unknown property:"+propertyName);
        }
        sequenceProperties.put(propertyName,true);
        return this;
    }
    /**
     * 当前属性desc的时候采用 defaultTableComparator的defaultTableNameComparator
     * @param orderProperty
     * @return
     */
    public ShardingOrderBuilder<T> addPropertyWhenDesc(Property<T,?> orderProperty){
        String propertyName = LambdaUtil.getPropertyName(orderProperty);
        return addPropertyWhenDesc(propertyName);
    }
    public ShardingOrderBuilder<T> addPropertyWhenDesc(String propertyName){
        if(entityMetadata.getColumnOrNull(propertyName)==null){
            throw new EasyQueryInvalidOperationException("sharding initializer add desc unknown property:"+propertyName);
        }
        sequenceProperties.put(propertyName,false);
        return this;
    }

    /**
     * 当符合顺序查询时使用的connectionsLimit
     * @param connectionsLimit
     * @return
     */
    public ShardingOrderBuilder<T> addConnectionsLimit(int connectionsLimit){
        this.connectionsLimit =connectionsLimit;
        return this;
    }

    public Comparator<String> getDefaultTableNameComparator() {
        return defaultTableNameComparator;
    }

    public boolean isDefaultReverse() {
        return defaultReverse;
    }

    public Map<String, Boolean> getSequenceProperties() {
        return sequenceProperties;
    }

    public int getConnectionsLimit() {
        return connectionsLimit;
    }
}
