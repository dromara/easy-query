package com.easy.query.sql.starter.config;

import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.extension.generated.GeneratedKeySQLColumnGenerator;
import com.easy.query.core.basic.extension.generated.PrimaryKeyGenerator;
import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategy;
import com.easy.query.core.basic.extension.navigate.NavigateExtraFilterStrategy;
import com.easy.query.core.basic.extension.navigate.NavigateValueSetter;
import com.easy.query.core.basic.extension.version.VersionStrategy;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.implicit.EntityRelationPropertyProvider;
import com.easy.query.core.sharding.initializer.ShardingInitializer;
import com.easy.query.core.sharding.route.datasource.DataSourceRoute;
import com.easy.query.core.sharding.route.table.TableRoute;
import com.easy.query.core.sharding.router.manager.DataSourceRouteManager;
import com.easy.query.core.sharding.router.manager.TableRouteManager;

import java.util.HashMap;
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
    private final Map<String, TableRoute<?>> tableRouteMap;
    private final Map<String, DataSourceRoute<?>> dataSourceRouteMap;
    private final Map<String, JdbcTypeHandler> jdbcTypeHandlerMap;
    private final Map<String, ColumnValueSQLConverter> columnValueSQLConverterMap;
    private final Map<String, GeneratedKeySQLColumnGenerator> generatedKeySQLColumnGeneratorMap;
    private final Map<String, NavigateExtraFilterStrategy> navigateExtraFilterStrategyMap;
    private final Map<String, NavigateValueSetter> navigateValueSetterMap;
    private final Map<String, PrimaryKeyGenerator> primaryKeyGeneratorMap;
    private final Map<String, EntityRelationPropertyProvider> entityRelationPropertyProviderMap;

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

    public Map<String, TableRoute<?>> getTableRouteMap() {
        return tableRouteMap;
    }

    public Map<String, DataSourceRoute<?>> getDataSourceRouteMap() {
        return dataSourceRouteMap;
    }

    public Map<String, JdbcTypeHandler> getJdbcTypeHandlerMap() {
        return jdbcTypeHandlerMap;
    }

    public Map<String, ColumnValueSQLConverter> getColumnValueSQLConverterMap() {
        return columnValueSQLConverterMap;
    }

    public Map<String, GeneratedKeySQLColumnGenerator> getGeneratedKeySQLColumnGeneratorMap() {
        return generatedKeySQLColumnGeneratorMap;
    }

    public Map<String, NavigateExtraFilterStrategy> getNavigateExtraFilterStrategyMap() {
        return navigateExtraFilterStrategyMap;
    }

    public Map<String, NavigateValueSetter> getNavigateValueSetterMap() {
        return navigateValueSetterMap;
    }

    public Map<String, PrimaryKeyGenerator> getPrimaryKeyGeneratorMap() {
        return primaryKeyGeneratorMap;
    }

    public Map<String, EntityRelationPropertyProvider> getEntityRelationPropertyProviderMap() {
        return entityRelationPropertyProviderMap;
    }

    public EasyQueryInitializeOption(Map<String, Interceptor> interceptorMap
            , Map<String, VersionStrategy> versionStrategyMap
            , Map<String, LogicDeleteStrategy> logicDeleteStrategyMap
            , Map<String, ShardingInitializer> shardingInitializerMap
            , Map<String, EncryptionStrategy> encryptionStrategyMap
            , Map<String, ValueConverter<?, ?>> valueConverterMap,
                                     Map<String, TableRoute<?>> tableRouteMap,
                                     Map<String, DataSourceRoute<?>> dataSourceRouteMap,
                                     Map<String, JdbcTypeHandler> jdbcTypeHandlerMap,
                                     Map<String, ColumnValueSQLConverter> columnValueSQLConverterMap,
                                     Map<String, GeneratedKeySQLColumnGenerator> generatedKeySQLColumnGeneratorMap,
                                     Map<String, NavigateExtraFilterStrategy> navigateExtraFilterStrategyMap,
                                     Map<String, NavigateValueSetter> navigateValueSetterMap,
                                     Map<String, PrimaryKeyGenerator> primaryKeyGeneratorMap,
                                     Map<String, EntityRelationPropertyProvider> entityRelationPropertyProviderMap) {

        this.interceptorMap = interceptorMap;
        this.versionStrategyMap = versionStrategyMap;
        this.logicDeleteStrategyMap = logicDeleteStrategyMap;
        this.shardingInitializerMap = shardingInitializerMap;
        this.encryptionStrategyMap = encryptionStrategyMap;
        this.valueConverterMap = valueConverterMap;
        this.tableRouteMap = tableRouteMap;
        this.dataSourceRouteMap = dataSourceRouteMap;
        this.jdbcTypeHandlerMap = jdbcTypeHandlerMap;
        this.columnValueSQLConverterMap = columnValueSQLConverterMap;
        this.generatedKeySQLColumnGeneratorMap = generatedKeySQLColumnGeneratorMap;
        this.navigateExtraFilterStrategyMap = navigateExtraFilterStrategyMap;
        this.navigateValueSetterMap = navigateValueSetterMap;
        this.primaryKeyGeneratorMap = primaryKeyGeneratorMap;
        this.entityRelationPropertyProviderMap = entityRelationPropertyProviderMap;
    }

    public EasyQueryInitializeOption() {
        this(new HashMap<>()
                , new HashMap<>()
                , new HashMap<>()
                , new HashMap<>()
                , new HashMap<>()
                , new HashMap<>()
                , new HashMap<>()
                , new HashMap<>()
                , new HashMap<>()
                , new HashMap<>()
                , new HashMap<>()
                , new HashMap<>()
                , new HashMap<>()
                , new HashMap<>()
                , new HashMap<>());
    }

    public void addComponents(EasyQueryClient easyQueryClient) {

        QueryRuntimeContext runtimeContext = easyQueryClient.getRuntimeContext();
        QueryConfiguration configuration = runtimeContext.getQueryConfiguration();
        JdbcTypeHandlerManager jdbcTypeHandlerManager = runtimeContext.getJdbcTypeHandlerManager();
        for (Map.Entry<String, JdbcTypeHandler> jdbcTypeHandlerEntry : this.getJdbcTypeHandlerMap().entrySet()) {
            JdbcTypeHandler jdbcTypeHandler = jdbcTypeHandlerEntry.getValue();
            if (jdbcTypeHandler instanceof JdbcTypeHandlerReplaceConfigurer) {
                JdbcTypeHandlerReplaceConfigurer jdbcTypeHandlerReplaceConfiguration = (JdbcTypeHandlerReplaceConfigurer) jdbcTypeHandler;
                if (jdbcTypeHandlerReplaceConfiguration.allowTypes() != null && !jdbcTypeHandlerReplaceConfiguration.allowTypes().isEmpty()) {
                    boolean replace = jdbcTypeHandlerReplaceConfiguration.replace();
                    for (Class<?> allowType : jdbcTypeHandlerReplaceConfiguration.allowTypes()) {
                        jdbcTypeHandlerManager.appendHandler(allowType, jdbcTypeHandler, replace);
                    }
                }
            }
        }
        //拦截器注册
        for (Map.Entry<String, Interceptor> easyInterceptorEntry : this.getInterceptorMap().entrySet()) {
            configuration.applyInterceptor(easyInterceptorEntry.getValue());
        }
        //逻辑删除
        for (Map.Entry<String, LogicDeleteStrategy> logicDeleteStrategyEntry : this.getLogicDeleteStrategyMap().entrySet()) {
            configuration.applyLogicDeleteStrategy(logicDeleteStrategyEntry.getValue());
        }
        //分片初始化
        for (Map.Entry<String, ShardingInitializer> shardingInitializerEntry : this.getShardingInitializerMap().entrySet()) {
            configuration.applyShardingInitializer(shardingInitializerEntry.getValue());
        }
        //列加密
        for (Map.Entry<String, EncryptionStrategy> easyEncryptionStrategyEntry : this.getEncryptionStrategyMap().entrySet()) {
            configuration.applyEncryptionStrategy(easyEncryptionStrategyEntry.getValue());
        }
        //数据行版本
        for (Map.Entry<String, VersionStrategy> easyVersionStrategyEntry : this.getVersionStrategyMap().entrySet()) {
            configuration.applyEasyVersionStrategy(easyVersionStrategyEntry.getValue());
        }
        //列转化
        for (Map.Entry<String, ValueConverter<?, ?>> valueConverterEntry : this.getValueConverterMap().entrySet()) {
            configuration.applyValueConverter(valueConverterEntry.getValue());
        }

        for (Map.Entry<String, ColumnValueSQLConverter> columnValueSQLConverterEntry : this.getColumnValueSQLConverterMap().entrySet()) {
            configuration.applyColumnValueSQLConverter(columnValueSQLConverterEntry.getValue());
        }
        for (Map.Entry<String, GeneratedKeySQLColumnGenerator> incrementSQLColumnGeneratorEntry : this.getGeneratedKeySQLColumnGeneratorMap().entrySet()) {
            configuration.applyGeneratedKeySQLColumnGenerator(incrementSQLColumnGeneratorEntry.getValue());
        }
        for (Map.Entry<String, NavigateExtraFilterStrategy> navigateExtraFilterStrategyEntry : this.getNavigateExtraFilterStrategyMap().entrySet()) {
            configuration.applyNavigateExtraFilterStrategy(navigateExtraFilterStrategyEntry.getValue());
        }
        for (Map.Entry<String, NavigateValueSetter> navigateValueSetterEntry : this.getNavigateValueSetterMap().entrySet()) {
            configuration.applyNavigateValueSetter(navigateValueSetterEntry.getValue());
        }
        for (Map.Entry<String, PrimaryKeyGenerator> primaryKeyGeneratorEntry : this.getPrimaryKeyGeneratorMap().entrySet()) {
            configuration.applyPrimaryKeyGenerator(primaryKeyGeneratorEntry.getValue());
        }
        for (Map.Entry<String, EntityRelationPropertyProvider> entityRelationPropertyProviderEntry : this.getEntityRelationPropertyProviderMap().entrySet()) {
            configuration.applyRelationPropertyProvider();
        }
        TableRouteManager tableRouteManager = runtimeContext.getTableRouteManager();
        for (Map.Entry<String, TableRoute<?>> tableRouteEntry : this.getTableRouteMap().entrySet()) {
            tableRouteManager.addRoute(tableRouteEntry.getValue());
        }
        DataSourceRouteManager dataSourceRouteManager = runtimeContext.getDataSourceRouteManager();
        for (Map.Entry<String, DataSourceRoute<?>> dataSourceRouteEntry : this.getDataSourceRouteMap().entrySet()) {
            dataSourceRouteManager.addRoute(dataSourceRouteEntry.getValue());
        }

    }
}
