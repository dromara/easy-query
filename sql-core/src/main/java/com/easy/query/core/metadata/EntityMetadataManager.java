package com.easy.query.core.metadata;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 类元信息管理
 *
 * @author xuejiaming
 * @FileName: ClassMetadataManager.java
 * @Description: 文件说明
 * create time 2023/2/11 10:07
 */
public interface EntityMetadataManager {

    /**
     * 根据字节获取对象元信息
     *
     * @param entityClass
     * @return 如果对象不包含Table注解那么返回为为null
     */
    EntityMetadata getEntityMetadata(Class<?> entityClass);

    boolean isSharding(Class<?> entityClass);

    boolean isShardingTable(Class<?> entityClass);

    boolean isOnlyShardingTable(Class<?> entityClass);

    boolean isShardingDataSource(Class<?> entityClass);

    boolean isOnlyShardingDataSource(Class<?> entityClass);

    /**
     * 根据表名获取已加载对象信息
     * 因为多个对象可以同时指向一张表
     * @param tableName
     * @return
     */
    @Nullable
    List<EntityMetadata> getEntityMetadataList(String tableName);

    /**
     * 获取所有已加载的对象元信息
     * @return
     */
    List<EntityMetadata> getAllLoadedEntityMetadata();

}
