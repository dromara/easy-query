package com.easy.query.core.api.dynamic.executor.search.meta;

import com.easy.query.core.api.dynamic.executor.search.EasySearchConfiguration;
import com.easy.query.core.api.dynamic.executor.search.EasySearchConfigurationBuilder;
import com.easy.query.core.api.dynamic.executor.search.EasySearchConfigurationProvider;
import com.easy.query.core.metadata.EntityMetadataManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 搜索元数据管理器，用于管理和缓存搜索条件元数据实例，线程安全
 *
 * @author bkbits
 */
public class EasySearchMetaDataManager {
    //元数据缓存
    private final Map<Class<?>, EasySearchMetaData> metaDataCache = new ConcurrentHashMap<>();
    //实体数据管理器
    private final EntityMetadataManager entityMetadataManager;

    //配置
    private final EasySearchConfiguration configuration;

    public EasySearchMetaDataManager(
            EntityMetadataManager entityMetadataManager,
            EasySearchConfigurationProvider configurationProvider
    ) {
        this.entityMetadataManager = entityMetadataManager;
        this.configuration =
                configurationProvider.getConfiguration(new EasySearchConfigurationBuilder());
    }


    /**
     * 获取搜索元数据
     *
     * @param clazz 搜索类
     * @return 元数据
     */
    public EasySearchMetaData getSearchMetaData(Class<?> clazz) {
        return metaDataCache.computeIfAbsent(
                clazz,
                tableClass -> EasySearchMetaData.of(
                        configuration,
                        entityMetadataManager,
                        tableClass
                )
        );
    }

    /**
     * 获取easy-search配置
     *
     * @return 配置实例
     */
    public EasySearchConfiguration getConfiguration() {
        return configuration;
    }
}
