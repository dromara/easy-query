package com.easy.query.search.meta;

import com.easy.query.search.EasySearchConfiguration;
import com.easy.query.search.EasySearchConfigurationBuilder;
import com.easy.query.search.EasySearchConfigurationProvider;
import com.easy.query.core.metadata.EntityMetadataManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 搜索元数据管理器，用于管理和缓存搜索条件元数据实例，线程安全
 *
 * @author bkbits
 */
public class DefaultEasySearchMetaDataManager implements EasySearchMetaDataManager {
    //元数据缓存
    private final Map<Class<?>, EasySearchMetaData> metaDataCache = new ConcurrentHashMap<>();
    //实体数据管理器
    private final EntityMetadataManager entityMetadataManager;

    //配置
    private final EasySearchConfiguration configuration;

    public DefaultEasySearchMetaDataManager(
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
    @Override
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
    @Override
    public EasySearchConfiguration getConfiguration() {
        return configuration;
    }
}
