package com.easy.query.search;

/**
 * easy-search配置构建器
 *
 * @author bkbits
 */
public class DefaultEasySearchConfigurationProvider
        implements EasySearchConfigurationProvider {
    /**
     * 构建配置
     *
     * @return 配置对象
     */
    public EasySearchConfiguration getConfiguration(EasySearchConfigurationBuilder builder) {
        return builder.build();
    }
}
