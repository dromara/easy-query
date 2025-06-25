package com.easy.query.search.meta;

import com.easy.query.search.EasySearchConfiguration;
import com.easy.query.core.metadata.EntityMetadataManager;

/**
 * 搜索元数据管理器，用于管理和缓存搜索条件元数据实例，线程安全
 *
 * @author bkbits
 */
public interface EasySearchMetaDataManager {


    /**
     * 获取搜索元数据
     *
     * @param clazz 搜索类
     * @return 元数据
     */
    EasySearchMetaData getSearchMetaData(Class<?> clazz);

    /**
     * 获取easy-search配置
     *
     * @return 配置实例
     */
    EasySearchConfiguration getConfiguration();
}
