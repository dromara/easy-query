package com.easy.query.sql.starter;

import com.easy.query.core.api.client.EasyQuery;
import com.easy.query.core.basic.plugin.conversion.ValueConverter;
import com.easy.query.core.basic.plugin.encryption.EncryptionStrategy;
import com.easy.query.core.basic.plugin.interceptor.Interceptor;
import com.easy.query.core.basic.plugin.logicdel.LogicDeleteStrategy;
import com.easy.query.core.basic.plugin.version.VersionStrategy;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.sharding.initializer.ShardingInitializer;
import com.easy.query.sql.starter.config.EasyQueryInitializeOption;
import com.easy.query.sql.starter.config.EasyQueryProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @author xuejiaming
 * @FileName: EasyQueryStarter.java
 * @Description: 文件说明
 * @Date: 2023/3/11 12:47
 */
@Configuration
@EnableConfigurationProperties(EasyQueryProperties.class)
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter({DataSourceAutoConfiguration.class,EasyQueryStarterAutoConfiguration.class})
@ConditionalOnProperty(
        prefix = "easy-query",
        value = {"enable"},
        matchIfMissing = true
)
public class EasyQueryStarterInitializeAutoConfiguration {

    private final EasyQuery easyQuery;
    private final EasyQueryInitializeOption easyQueryInitializeOption;

    public EasyQueryStarterInitializeAutoConfiguration(EasyQuery easyQuery, EasyQueryInitializeOption easyQueryInitializeOption){

        this.easyQuery = easyQuery;
        this.easyQueryInitializeOption = easyQueryInitializeOption;
    };
    @PostConstruct
    public void initialize() {
        QueryRuntimeContext runtimeContext = easyQuery.getRuntimeContext();
        QueryConfiguration configuration = runtimeContext.getQueryConfiguration();
        //拦截器注册
        for (Map.Entry<String, Interceptor> easyInterceptorEntry : easyQueryInitializeOption.getInterceptorMap().entrySet()) {
            configuration.applyEasyInterceptor(easyInterceptorEntry.getValue());
        }
        //逻辑删除
        for (Map.Entry<String, LogicDeleteStrategy> logicDeleteStrategyEntry : easyQueryInitializeOption.getLogicDeleteStrategyMap().entrySet()) {
            configuration.applyLogicDeleteStrategy(logicDeleteStrategyEntry.getValue());
        }
        //分片初始化
        for (Map.Entry<String, ShardingInitializer> shardingInitializerEntry : easyQueryInitializeOption.getShardingInitializerMap().entrySet()) {
            configuration.applyShardingInitializer(shardingInitializerEntry.getValue());
        }
        //列加密
        for (Map.Entry<String, EncryptionStrategy> easyEncryptionStrategyEntry : easyQueryInitializeOption.getEncryptionStrategyMap().entrySet()) {
            configuration.applyEasyEncryptionStrategy(easyEncryptionStrategyEntry.getValue());
        }
        //数据行版本
        for (Map.Entry<String, VersionStrategy> easyVersionStrategyEntry : easyQueryInitializeOption.getVersionStrategyMap().entrySet()) {
            configuration.applyEasyVersionStrategy(easyVersionStrategyEntry.getValue());
        }
        //列转化
        for (Map.Entry<String, ValueConverter<?, ?>> valueConverterEntry : easyQueryInitializeOption.getValueConverterMap().entrySet()) {
            configuration.applyValueConverter(valueConverterEntry.getValue());
        }
    }
}
