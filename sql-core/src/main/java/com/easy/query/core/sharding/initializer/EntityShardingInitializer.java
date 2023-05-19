package com.easy.query.core.sharding.initializer;

/**
 * create time 2023/5/15 12:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityShardingInitializer<T> extends ShardingInitializer {
    @Override
    default void initialize(ShardingEntityBuilder<?> builder) {
        configure((ShardingEntityBuilder<T>) builder);
    }

    /**
     * <blockquote><pre>
     *     {@code
     *
     * EntityMetadata entityMetadata = builder.getEntityMetadata();
     * String tableName = entityMetadata.getTableName();
     * ArrayList<String> actualTableNames = new ArrayList<>(3);
     * for (int i = 0; i < 3; i++) {
     * actualTableNames.add(tableName+"_"+i);
     * }
     * LinkedHashMap<String, Collection<String>> initTables = new LinkedHashMap<String, Collection<String>>() {{
     *      put("ds2020", actualTableNames);
     * }};
     *
     * builder.actualTableNameInit(initTables)
     *                 .paginationReverse(0.5,100L)
     *                 .ascSequenceConfigure(new DataSourceAndTableComparator())
     *                 .addPropertyDefaultUseDesc(TopicShardingDataSourceTime::getCreateTime)
     *                 .defaultAffectedMethod(false,
     *                 ExecuteMethodEnum.LIST,
     *                 ExecuteMethodEnum.ANY,
     *                 ExecuteMethodEnum.FIRST,
     *                 ExecuteMethodEnum.COUNT)
     *                 .useMaxShardingQueryLimit(1,ExecuteMethodEnum.FIRST);
     *     }
     * </pre></blockquote>
     *
     * @param builder
     */
    void configure(ShardingEntityBuilder<T> builder);
}
