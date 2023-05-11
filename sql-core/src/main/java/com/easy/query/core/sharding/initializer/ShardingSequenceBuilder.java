package com.easy.query.core.sharding.initializer;

import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.enums.ConnectionModeEnum;
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
public class ShardingSequenceBuilder<T> {
    private final EntityMetadata entityMetadata;
    private final Comparator<String> defaultTableNameComparator;
    private final Map<String, Boolean/*asc or desc*/> sequenceProperties = new HashMap<>();
    private int maxShardingQueryLimit = 0;
    private ConnectionModeEnum connectionMode = null;
    private final ExecuteMethodBehavior executeMethodBehavior;

    public ShardingSequenceBuilder(EntityMetadata entityMetadata, Comparator<String> defaultTableNameComparator) {

        this.entityMetadata = entityMetadata;
        this.defaultTableNameComparator = defaultTableNameComparator;
        this.executeMethodBehavior = ExecuteMethodBehavior.getDefault();
    }


    /**
     * 当前属性asc的时候采用 defaultTableComparator的defaultTableNameComparator
     *
     * @param orderProperty
     * @return
     */
    public ShardingSequenceBuilder<T> addPropertyWhenAsc(Property<T, ?> orderProperty) {
        String propertyName = LambdaUtil.getPropertyName(orderProperty);
        return addPropertyWhenAsc(propertyName);
    }

    public ShardingSequenceBuilder<T> addPropertyWhenAsc(String propertyName) {
        if (entityMetadata.getColumnOrNull(propertyName) == null) {
            throw new EasyQueryInvalidOperationException("sharding initializer add asc unknown property:" + propertyName);
        }
        sequenceProperties.put(propertyName, true);
        return this;
    }

    /**
     * 当前属性desc的时候采用 defaultTableComparator的defaultTableNameComparator
     *
     * @param orderProperty
     * @return
     */
    public ShardingSequenceBuilder<T> addPropertyWhenDesc(Property<T, ?> orderProperty) {
        String propertyName = LambdaUtil.getPropertyName(orderProperty);
        return addPropertyWhenDesc(propertyName);
    }

    public ShardingSequenceBuilder<T> addPropertyWhenDesc(String propertyName) {
        if (entityMetadata.getColumnOrNull(propertyName) == null) {
            throw new EasyQueryInvalidOperationException("sharding initializer add desc unknown property:" + propertyName);
        }
        sequenceProperties.put(propertyName, false);
        return this;
    }

    /**
     * 表达式没有order的时候哪些方法需要支持顺序排序,譬如any可以设置连接数不需要那么大,first可以设置只需要2个连接查询譬如,并且不需要查询所有表
     *
     * @param asc
     * @param executeMethods
     * @return
     */
    public ShardingSequenceBuilder<T> defaultAffectedMethod(boolean asc, ExecuteMethodEnum... executeMethods) {
        for (ExecuteMethodEnum executeMethod : executeMethods) {
            executeMethodBehavior.removeMethod(executeMethod);
            executeMethodBehavior.removeMethodAsc(executeMethod);
            executeMethodBehavior.addMethod(executeMethod);
            if (asc) {
                executeMethodBehavior.addMethod(executeMethod);
            }
        }
        return this;
    }

    /**
     * 当符合顺序查询时使用的connectionsLimit
     *
     * @param maxShardingQueryLimit
     * @return
     */
    public ShardingSequenceBuilder<T> setMaxShardingQueryLimit(int maxShardingQueryLimit) {
        this.maxShardingQueryLimit = maxShardingQueryLimit;
        return this;
    }
    public ShardingSequenceBuilder<T> setConnectionMode(ConnectionModeEnum connectionMode) {
        this.connectionMode = connectionMode;
        return this;
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

    public int getMaxShardingQueryLimit() {
        return maxShardingQueryLimit;
    }

    public ExecuteMethodBehavior getExecuteMethodBehavior() {
        return executeMethodBehavior;
    }
}
