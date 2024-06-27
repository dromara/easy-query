package com.easy.query.sql.starter.config;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.extension.generated.GeneratedKeySQLColumnGenerator;
import com.easy.query.core.basic.extension.generated.PrimaryKeyGenerator;
import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategy;
import com.easy.query.core.basic.extension.navigate.NavigateExtraFilterStrategy;
import com.easy.query.core.basic.extension.version.VersionStrategy;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.sharding.initializer.ShardingInitializer;
import com.easy.query.core.sharding.route.datasource.DataSourceRoute;
import com.easy.query.core.sharding.route.table.TableRoute;

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
    private final Map<String, PrimaryKeyGenerator> primaryKeyGeneratorMap;

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

    public Map<String, PrimaryKeyGenerator> getPrimaryKeyGeneratorMap() {
        return primaryKeyGeneratorMap;
    }

    public EasyQueryInitializeOption(Map<String, Interceptor> interceptorMap, Map<String, VersionStrategy> versionStrategyMap, Map<String, LogicDeleteStrategy> logicDeleteStrategyMap, Map<String, ShardingInitializer> shardingInitializerMap, Map<String, EncryptionStrategy> encryptionStrategyMap, Map<String, ValueConverter<?, ?>> valueConverterMap,
                                     Map<String, TableRoute<?>> tableRouteMap,
                                     Map<String, DataSourceRoute<?>> dataSourceRouteMap,
                                     Map<String, JdbcTypeHandler> jdbcTypeHandlerMap,
                                     Map<String, ColumnValueSQLConverter> columnValueSQLConverterMap,
                                     Map<String, GeneratedKeySQLColumnGenerator> generatedKeySQLColumnGeneratorMap,
                                     Map<String, NavigateExtraFilterStrategy> navigateExtraFilterStrategyMap,
                                     Map<String, PrimaryKeyGenerator> primaryKeyGeneratorMap) {

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
        this.primaryKeyGeneratorMap = primaryKeyGeneratorMap;
    }
}
