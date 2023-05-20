package com.easy.query.sql.starter.config;

import com.easy.query.core.basic.plugin.conversion.ValueConverter;
import com.easy.query.core.basic.plugin.encryption.EncryptionStrategy;
import com.easy.query.core.basic.plugin.interceptor.Interceptor;
import com.easy.query.core.basic.plugin.logicdel.LogicDeleteStrategy;
import com.easy.query.core.basic.plugin.version.VersionStrategy;
import com.easy.query.core.sharding.initializer.ShardingInitializer;

import java.util.Map;

/**
 * create time 2023/5/20 23:43
 * 文件说明
 *
 * @author xuejiaming
 */
public final class EasyQueryInitializeOption {

    private final Map<String, Interceptor> interceptorMap;
    private final Map<String, VersionStrategy> versionStrategyMap;
    private final Map<String, LogicDeleteStrategy> logicDeleteStrategyMap;
    private final Map<String, ShardingInitializer> shardingInitializerMap;
    private final Map<String, EncryptionStrategy> encryptionStrategyMap;
    private final Map<String, ValueConverter<?, ?>> valueConverterMap;

    public Map<String, Interceptor> getInterceptorMap() {
        return interceptorMap;
    }

    public Map<String, VersionStrategy> getVersionStrategyMap() {
        return versionStrategyMap;
    }

    public Map<String, LogicDeleteStrategy> getLogicDeleteStrategyMap() {
        return logicDeleteStrategyMap;
    }

    public Map<String, ShardingInitializer> getShardingInitializerMap() {
        return shardingInitializerMap;
    }

    public Map<String, EncryptionStrategy> getEncryptionStrategyMap() {
        return encryptionStrategyMap;
    }

    public Map<String, ValueConverter<?, ?>> getValueConverterMap() {
        return valueConverterMap;
    }

    public EasyQueryInitializeOption(Map<String, Interceptor> interceptorMap, Map<String, VersionStrategy> versionStrategyMap, Map<String, LogicDeleteStrategy> logicDeleteStrategyMap, Map<String, ShardingInitializer> shardingInitializerMap, Map<String, EncryptionStrategy> encryptionStrategyMap, Map<String, ValueConverter<?,?>> valueConverterMap){

        this.interceptorMap = interceptorMap;
        this.versionStrategyMap = versionStrategyMap;
        this.logicDeleteStrategyMap = logicDeleteStrategyMap;
        this.shardingInitializerMap = shardingInitializerMap;
        this.encryptionStrategyMap = encryptionStrategyMap;
        this.valueConverterMap = valueConverterMap;
    }

}
