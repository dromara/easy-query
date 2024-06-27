package com.easy.query.sql.starter;

import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.extension.generated.GeneratedKeySQLColumnGenerator;
import com.easy.query.core.basic.extension.generated.PrimaryKeyGenerator;
import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategy;
import com.easy.query.core.basic.extension.navigate.NavigateExtraFilterStrategy;
import com.easy.query.core.basic.extension.version.VersionStrategy;
import com.easy.query.sql.starter.config.JdbcTypeHandlerReplaceConfigurer;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.sharding.initializer.ShardingInitializer;
import com.easy.query.core.sharding.router.manager.DataSourceRouteManager;
import com.easy.query.core.sharding.router.manager.TableRouteManager;
import com.easy.query.core.sharding.route.datasource.DataSourceRoute;
import com.easy.query.core.sharding.route.table.TableRoute;
import com.easy.query.sql.starter.config.EasyQueryInitializeOption;
import com.easy.query.sql.starter.config.EasyQueryProperties;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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

    private final EasyQueryClient easyQuery;
    private final EasyQueryInitializeOption easyQueryInitializeOption;

    public EasyQueryStarterInitializeAutoConfiguration(EasyQueryClient easyQuery, EasyQueryInitializeOption easyQueryInitializeOption) {

        this.easyQuery = easyQuery;
        this.easyQueryInitializeOption = easyQueryInitializeOption;
        initialize();
    }

    public void initialize() {
        QueryRuntimeContext runtimeContext = easyQuery.getRuntimeContext();
        QueryConfiguration configuration = runtimeContext.getQueryConfiguration();
        JdbcTypeHandlerManager jdbcTypeHandlerManager = runtimeContext.getJdbcTypeHandlerManager();
        for (Map.Entry<String, JdbcTypeHandler> jdbcTypeHandlerEntry : easyQueryInitializeOption.getJdbcTypeHandlerMap().entrySet()) {
            JdbcTypeHandler jdbcTypeHandler = jdbcTypeHandlerEntry.getValue();
            if(jdbcTypeHandler instanceof JdbcTypeHandlerReplaceConfigurer){
                JdbcTypeHandlerReplaceConfigurer jdbcTypeHandlerReplaceConfiguration = (JdbcTypeHandlerReplaceConfigurer) jdbcTypeHandler;
                if(jdbcTypeHandlerReplaceConfiguration.allowTypes()!=null&&!jdbcTypeHandlerReplaceConfiguration.allowTypes().isEmpty()){
                    boolean replace = jdbcTypeHandlerReplaceConfiguration.replace();
                    for (Class<?> allowType : jdbcTypeHandlerReplaceConfiguration.allowTypes()) {
                        jdbcTypeHandlerManager.appendHandler(allowType,jdbcTypeHandler,replace);
                    }
                }
            }
        }
        //拦截器注册
        for (Map.Entry<String, Interceptor> easyInterceptorEntry : easyQueryInitializeOption.getInterceptorMap().entrySet()) {
            configuration.applyInterceptor(easyInterceptorEntry.getValue());
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
            configuration.applyEncryptionStrategy(easyEncryptionStrategyEntry.getValue());
        }
        //数据行版本
        for (Map.Entry<String, VersionStrategy> easyVersionStrategyEntry : easyQueryInitializeOption.getVersionStrategyMap().entrySet()) {
            configuration.applyEasyVersionStrategy(easyVersionStrategyEntry.getValue());
        }
        //列转化
        for (Map.Entry<String, ValueConverter<?, ?>> valueConverterEntry : easyQueryInitializeOption.getValueConverterMap().entrySet()) {
            configuration.applyValueConverter(valueConverterEntry.getValue());
        }

        for (Map.Entry<String, ColumnValueSQLConverter> columnValueSQLConverterEntry : easyQueryInitializeOption.getColumnValueSQLConverterMap().entrySet()) {
            configuration.applyColumnValueSQLConverter(columnValueSQLConverterEntry.getValue());
        }
        for (Map.Entry<String, GeneratedKeySQLColumnGenerator> incrementSQLColumnGeneratorEntry : easyQueryInitializeOption.getGeneratedKeySQLColumnGeneratorMap().entrySet()) {
            configuration.applyGeneratedKeySQLColumnGenerator(incrementSQLColumnGeneratorEntry.getValue());
        }
        for (Map.Entry<String, NavigateExtraFilterStrategy> navigateExtraFilterStrategyEntry : easyQueryInitializeOption.getNavigateExtraFilterStrategyMap().entrySet()) {
            configuration.applyNavigateExtraFilterStrategy(navigateExtraFilterStrategyEntry.getValue());
        }
        for (Map.Entry<String, PrimaryKeyGenerator> primaryKeyGeneratorEntry : easyQueryInitializeOption.getPrimaryKeyGeneratorMap().entrySet()) {
            configuration.applyPrimaryKeyGenerator(primaryKeyGeneratorEntry.getValue());
        }
        TableRouteManager tableRouteManager = runtimeContext.getTableRouteManager();
        for (Map.Entry<String, TableRoute<?>> tableRouteEntry : easyQueryInitializeOption.getTableRouteMap().entrySet()) {
            tableRouteManager.addRoute(tableRouteEntry.getValue());
        }
        DataSourceRouteManager dataSourceRouteManager = runtimeContext.getDataSourceRouteManager();
        for (Map.Entry<String, DataSourceRoute<?>> dataSourceRouteEntry : easyQueryInitializeOption.getDataSourceRouteMap().entrySet()) {
            dataSourceRouteManager.addRoute(dataSourceRouteEntry.getValue());
        }
        
    }
}
