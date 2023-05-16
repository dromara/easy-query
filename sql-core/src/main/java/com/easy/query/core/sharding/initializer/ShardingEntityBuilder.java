package com.easy.query.core.sharding.initializer;

import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.common.DataSourceThenTableNameStringComparator;
import com.easy.query.core.sharding.common.TableNameStringComparator;
import com.easy.query.core.sharding.route.table.TableUnit;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

/**
 * create time 2023/5/7 23:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingEntityBuilder<T> {
    private final EntityMetadata entityMetadata;
    private final ShardingInitOptionBuilder shardingInitOptionBuilder;

    public ShardingEntityBuilder(EntityMetadata entityMetadata) {

        this.entityMetadata = entityMetadata;
        this.shardingInitOptionBuilder = new ShardingInitOptionBuilder();
    }

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    public ShardingInitOption build() {
        return shardingInitOptionBuilder.build();
    }

    public ShardingEntityBuilder<T> actualTableNameInit(Map<String, Collection<String>> actualTableNames) {
        shardingInitOptionBuilder.setActualTableNames(actualTableNames);
        return this;
    }

    /**
     * 实际表asc下的排序
     * 默认提供{@link TableNameStringComparator} 忽略数据源仅以字符串形式比较表名
     * 默认提供{@link DataSourceThenTableNameStringComparator} 先比较数据源如果一样在比较表名仅以字符串形式比较
     * @param defaultTableNameComparator 默认没有匹配orderby的时候也会将表进行当前排序器进行排序后再分批处理
     * @return
     */
    public ShardingSequenceBuilder<T> ascSequenceConfigure(Comparator<TableUnit> defaultTableNameComparator) {
        shardingInitOptionBuilder
                .setDefaultTableNameComparator(defaultTableNameComparator);
        return new ShardingSequenceBuilder<>(this, shardingInitOptionBuilder, entityMetadata);
    }

    /**
     * 分页反排序
     * 比如 reverseFactor=0.5，minReverseTotal=100000，当0.5*total>100000时分片分页会启用反向排序
     *
     * @param reverseFactor
     * @param minReverseTotal
     * @return
     */
    public ShardingEntityBuilder<T> paginationReverse(double reverseFactor, long minReverseTotal) {
        if (reverseFactor <= 0) {
            throw new IllegalArgumentException("reverseFactor less than zero:" + reverseFactor);
        }
        if (minReverseTotal <= 1) {
            throw new IllegalArgumentException("minReverseTotal less than one:" + minReverseTotal);
        }
        shardingInitOptionBuilder.setReverseFactor(reverseFactor);
        shardingInitOptionBuilder.setMinReverseTotal(minReverseTotal);
        return this;
    }

}
