package com.easy.query.core.api.dynamic.executor.search;

import com.easy.query.core.api.dynamic.executor.search.op.EasySearchOpBuilder;
import com.easy.query.core.api.dynamic.executor.search.option.EasySearchOptionBuilder;
import com.easy.query.core.api.dynamic.executor.search.param.EasySearchParamMapProvider;

import java.util.function.Consumer;

/**
 * 搜索配置构造器
 *
 * @author bkbits
 */
public class EasySearchConfigurationBuilder {
    private final EasySearchOptionBuilder builder = new EasySearchOptionBuilder();
    private final EasySearchOpBuilder opBuilder = new EasySearchOpBuilder();
    private EasySearchParamMapProvider paramMapProvider = () -> null;

    /**
     * 配置easy-search设置
     *
     * @param configure 配置函数
     * @return this
     */
    public EasySearchConfigurationBuilder configure(Consumer<EasySearchOptionBuilder> configure) {
        configure.accept(builder);
        return this;
    }

    /**
     * 配置运算符
     *
     * @param configure 配置函数
     * @return this
     */
    public EasySearchConfigurationBuilder configureOp(Consumer<EasySearchOpBuilder> configure) {
        configure.accept(opBuilder);
        return this;
    }

    /**
     * 配置参数提供器
     *
     * @param paramMapProvider 参数提供器
     * @return this
     */
    public EasySearchConfigurationBuilder configureParamMapProvider(EasySearchParamMapProvider paramMapProvider) {
        this.paramMapProvider = paramMapProvider;
        return this;
    }

    /**
     * 构建configuration
     *
     * @return configuration对象
     */
    public EasySearchConfiguration build() {
        return new EasySearchConfiguration(builder.build(), opBuilder.build(), paramMapProvider);
    }
}
