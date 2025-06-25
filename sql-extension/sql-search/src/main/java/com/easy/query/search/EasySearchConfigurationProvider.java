package com.easy.query.search;

/**
 * 配置提供接口
 *
 * @author bkbits
 */
public interface EasySearchConfigurationProvider {
    /**
     * @return easy-search配置对象
     */
    EasySearchConfiguration getConfiguration(EasySearchConfigurationBuilder builder);
}
