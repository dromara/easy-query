package com.easy.query.sql.starter;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.DefaultEasyProxyQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.api.proxy.client.EasyProxyQuery;
import com.easy.query.api4j.client.DefaultEasyQuery;
import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.clickhouse.config.ClickHouseDatabaseConfiguration;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.entity.ColumnEntityMappingRule;
import com.easy.query.core.basic.entity.EntityMappingRule;
import com.easy.query.core.basic.entity.PropertyEntityMappingRule;
import com.easy.query.core.basic.entity.PropertyFirstEntityMappingRule;
import com.easy.query.core.basic.entity.TryColumnAndPropertyEntityMappingRule;
import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.extension.formater.DefaultSQLParameterPrintFormat;
import com.easy.query.core.basic.extension.formater.MyBatisSQLParameterPrintFormat;
import com.easy.query.core.basic.extension.formater.SQLParameterPrintFormat;
import com.easy.query.core.basic.extension.generated.GeneratedKeySQLColumnGenerator;
import com.easy.query.core.basic.extension.generated.PrimaryKeyGenerator;
import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategy;
import com.easy.query.core.basic.extension.navigate.NavigateExtraFilterStrategy;
import com.easy.query.core.basic.extension.navigate.NavigateValueSetter;
import com.easy.query.core.basic.extension.version.VersionStrategy;
import com.easy.query.core.basic.jdbc.conn.ConnectionManager;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.bootstrapper.DefaultDatabaseConfiguration;
import com.easy.query.core.bootstrapper.DefaultStarterConfigurer;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.bootstrapper.StarterConfigurer;
import com.easy.query.core.configuration.bean.PropertyDescriptorMatcher;
import com.easy.query.core.configuration.bean.def.DefaultPropertyDescriptorMatcher;
import com.easy.query.core.configuration.bean.entity.EntityPropertyDescriptorMatcher;
import com.easy.query.core.configuration.column2mapkey.Column2MapKeyConversion;
import com.easy.query.core.configuration.column2mapkey.DefaultColumn2MapKeyConversion;
import com.easy.query.core.configuration.column2mapkey.LowerColumn2MapKeyConversion;
import com.easy.query.core.configuration.column2mapkey.LowerUnderlinedColumn2MapKeyConversion;
import com.easy.query.core.configuration.column2mapkey.UpperColumn2MapKeyConversion;
import com.easy.query.core.configuration.column2mapkey.UpperUnderlinedColumn2MapKeyConversion;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.configuration.nameconversion.impl.DefaultNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.LowerCamelCaseNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UnderlinedNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UpperCamelCaseNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UpperUnderlinedNameConversion;
import com.easy.query.core.datasource.DataSourceUnitFactory;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.sharding.initializer.ShardingInitializer;
import com.easy.query.core.sharding.route.datasource.DataSourceRoute;
import com.easy.query.core.sharding.route.table.TableRoute;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.dameng.config.DamengDatabaseConfiguration;
import com.easy.query.db2.config.DB2DatabaseConfiguration;
import com.easy.query.gauss.db.config.GaussDBDatabaseConfiguration;
import com.easy.query.h2.config.H2DatabaseConfiguration;
import com.easy.query.kingbase.es.config.KingbaseESDatabaseConfiguration;
import com.easy.query.mssql.config.MsSQLDatabaseConfiguration;
import com.easy.query.mssql.config.MsSQLRowNumberDatabaseConfiguration;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.oracle.config.OracleDatabaseConfiguration;
import com.easy.query.pgsql.config.PgSQLDatabaseConfiguration;
import com.easy.query.sql.starter.config.EasyQueryInitializeOption;
import com.easy.query.sql.starter.config.EasyQueryProperties;
import com.easy.query.sql.starter.conn.SpringConnectionManager;
import com.easy.query.sql.starter.conn.SpringDataSourceUnitFactory;
import com.easy.query.sql.starter.logging.Slf4jImpl;
import com.easy.query.sql.starter.option.SQLParameterPrintEnum;
import com.easy.query.sqlite.config.SQLiteDatabaseConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
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
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
@ConditionalOnProperty(
        prefix = "easy-query",
        value = {"enable"},
        matchIfMissing = true
)
public class EasyQueryStarterAutoConfiguration {
    private final DataSource dataSource;
    private final EasyQueryProperties easyQueryProperties;

    public EasyQueryStarterAutoConfiguration(DataSource dataSource, EasyQueryProperties easyQueryProperties) {
        this.dataSource = dataSource;
        this.easyQueryProperties = easyQueryProperties;
        if (EasyStringUtil.isBlank(easyQueryProperties.getLogClass())) {
            LogFactory.useCustomLogging(Slf4jImpl.class);
        } else {
            try {
                Class<?> aClass = Class.forName(easyQueryProperties.getLogClass());
                if (Log.class.isAssignableFrom(aClass)) {
                    LogFactory.useCustomLogging((Class<? extends Log>) aClass);
                } else {
                    LogFactory.useStdOutLogging();
                    System.out.println("cant found log:[" + easyQueryProperties.getLogClass() + "]!!!!!!");
                }
            } catch (ClassNotFoundException e) {
                System.err.println("cant found log:[" + easyQueryProperties.getLogClass() + "]!!!!!!");
                e.printStackTrace();
            }
        }
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "mysql")
    @ConditionalOnMissingBean
    public DatabaseConfiguration mysqlDatabaseConfiguration() {
        return new MySQLDatabaseConfiguration();
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "h2")
    @ConditionalOnMissingBean
    public DatabaseConfiguration h2DatabaseConfiguration() {
        return new H2DatabaseConfiguration();
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "mssql")
    @ConditionalOnMissingBean
    public DatabaseConfiguration mssqlDatabaseConfiguration() {
        return new MsSQLDatabaseConfiguration();
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "clickhouse")
    @ConditionalOnMissingBean
    public DatabaseConfiguration clickhouseDatabaseConfiguration() {
        return new ClickHouseDatabaseConfiguration();
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "mssql_row_number")
    @ConditionalOnMissingBean
    public DatabaseConfiguration mssqlRowNumberDatabaseConfiguration() {
        return new MsSQLRowNumberDatabaseConfiguration();
    }
    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "oracle")
    @ConditionalOnMissingBean
    public DatabaseConfiguration oracleDatabaseConfiguration() {
        return new OracleDatabaseConfiguration();
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "pgsql")
    @ConditionalOnMissingBean
    public DatabaseConfiguration pgsqlDatabaseConfiguration() {
        return new PgSQLDatabaseConfiguration();
    }
    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "gauss_db")
    @ConditionalOnMissingBean
    public DatabaseConfiguration gaussDbDatabaseConfiguration() {
        return new GaussDBDatabaseConfiguration();
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "dameng")
    @ConditionalOnMissingBean
    public DatabaseConfiguration damengDatabaseConfiguration() {
        return new DamengDatabaseConfiguration();
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "kingbase_es")
    @ConditionalOnMissingBean
    public DatabaseConfiguration kingbaseESDatabaseConfiguration() {
        return new KingbaseESDatabaseConfiguration();
    }
    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "db2")
    @ConditionalOnMissingBean
    public DatabaseConfiguration dbB2DatabaseConfiguration() {
        return new DB2DatabaseConfiguration();
    }
    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "sqlite")
    @ConditionalOnMissingBean
    public DatabaseConfiguration SQLiteDatabaseConfiguration() {
        return new SQLiteDatabaseConfiguration();
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "default", matchIfMissing = true)
    @ConditionalOnMissingBean
    public DatabaseConfiguration databaseConfiguration() {
        return new DefaultDatabaseConfiguration();
    }

    @Bean
    @ConditionalOnMissingBean
    public StarterConfigurer starterConfigurer() {
        return new DefaultStarterConfigurer();
    }

    private Class<? extends SQLParameterPrintFormat> getSQLParameterPrintFormatClass() {
        if (easyQueryProperties.getSqlParameterPrint() == SQLParameterPrintEnum.MYBATIS) {
            return MyBatisSQLParameterPrintFormat.class;
        }
        return DefaultSQLParameterPrintFormat.class;
    }
    @Bean
    @ConditionalOnMissingBean
    public EasyQueryClient easyQueryClient(DatabaseConfiguration databaseConfiguration, StarterConfigurer starterConfigurer) {
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .replaceService(DataSourceUnitFactory.class, SpringDataSourceUnitFactory.class)
                .customConfigure(s->{
                    switch (easyQueryProperties.getNameConversion()){
                        case UNDERLINED:
                            s.addService(NameConversion.class, UnderlinedNameConversion.class);
                            break;
                        case UPPER_UNDERLINED:
                            s.addService(NameConversion.class, UpperUnderlinedNameConversion.class);
                            break;
                        case LOWER_CAMEL_CASE:
                            s.addService(NameConversion.class, LowerCamelCaseNameConversion.class);
                            break;
                        case UPPER_CAMEL_CASE:
                            s.addService(NameConversion.class, UpperCamelCaseNameConversion.class);
                            break;
                        case DEFAULT:
                            s.addService(NameConversion.class, DefaultNameConversion.class);
                            break;
                    }

                    switch (easyQueryProperties.getMapKeyConversion()){
                        case LOWER:
                            s.addService(Column2MapKeyConversion.class, LowerColumn2MapKeyConversion.class);
                            break;
                        case UPPER:
                            s.addService(Column2MapKeyConversion.class, UpperColumn2MapKeyConversion.class);
                            break;
                        case LOWER_UNDERLINED:
                            s.addService(Column2MapKeyConversion.class, LowerUnderlinedColumn2MapKeyConversion.class);
                            break;
                        case UPPER_UNDERLINED:
                            s.addService(Column2MapKeyConversion.class, UpperUnderlinedColumn2MapKeyConversion.class);
                            break;
                        case DEFAULT:
                            s.addService(Column2MapKeyConversion.class, DefaultColumn2MapKeyConversion.class);
                            break;
                    }
                    switch (easyQueryProperties.getPropertyMode()){
                        case FIRST_LOWER:
                            s.addService(PropertyDescriptorMatcher.class, DefaultPropertyDescriptorMatcher.class);
                            break;
                        case SAME_AS_ENTITY:
                            s.addService(PropertyDescriptorMatcher.class, EntityPropertyDescriptorMatcher.class);
                            break;
                    }
                    switch (easyQueryProperties.getMappingStrategy()){
                        case COLUMN_ONLY:
                            s.addService(EntityMappingRule.class, ColumnEntityMappingRule.class);
                            break;
                        case PROPERTY_ONLY:
                            s.addService(EntityMappingRule.class, PropertyEntityMappingRule.class);
                            break;
                        case COLUMN_AND_PROPERTY:
                            s.addService(EntityMappingRule.class, TryColumnAndPropertyEntityMappingRule.class);
                            break;
                        case PROPERTY_FIRST:
                            s.addService(EntityMappingRule.class, PropertyFirstEntityMappingRule.class);
                            break;
                    }
                })
                .replaceService(ConnectionManager.class, SpringConnectionManager.class)
                .replaceService(SQLParameterPrintFormat.class, getSQLParameterPrintFormatClass())
                .optionConfigure(builder -> {
                    builder.setDeleteThrowError(easyQueryProperties.getDeleteThrow());
                    builder.setInsertStrategy(easyQueryProperties.getInsertStrategy());
                    builder.setUpdateStrategy(easyQueryProperties.getUpdateStrategy());
                    builder.setMaxShardingQueryLimit(easyQueryProperties.getMaxShardingQueryLimit());
                    builder.setExecutorMaximumPoolSize(easyQueryProperties.getExecutorMaximumPoolSize());
                    builder.setExecutorCorePoolSize(easyQueryProperties.getExecutorCorePoolSize());
                    builder.setThrowIfRouteNotMatch(easyQueryProperties.isThrowIfRouteNotMatch());
                    builder.setShardingExecuteTimeoutMillis(easyQueryProperties.getShardingExecuteTimeoutMillis());
                    builder.setQueryLargeColumn(easyQueryProperties.isQueryLargeColumn());
                    builder.setMaxShardingRouteCount(easyQueryProperties.getMaxShardingRouteCount());
                    builder.setExecutorQueueSize(easyQueryProperties.getExecutorQueueSize());
                    builder.setDefaultDataSourceName(easyQueryProperties.getDefaultDataSourceName());
                    builder.setDefaultDataSourceMergePoolSize(easyQueryProperties.getDefaultDataSourceMergePoolSize());
                    builder.setMultiConnWaitTimeoutMillis(easyQueryProperties.getMultiConnWaitTimeoutMillis());
                    builder.setWarningBusy(easyQueryProperties.isWarningBusy());
                    builder.setInsertBatchThreshold(easyQueryProperties.getInsertBatchThreshold());
                    builder.setUpdateBatchThreshold(easyQueryProperties.getUpdateBatchThreshold());
                    builder.setPrintSql(easyQueryProperties.isPrintSql());
                    builder.setPrintNavSql(easyQueryProperties.isPrintNavSql());
                    builder.setStartTimeJob(easyQueryProperties.isStartTimeJob());
                    builder.setDefaultTrack(easyQueryProperties.isDefaultTrack());
                    builder.setRelationGroupSize(easyQueryProperties.getRelationGroupSize());
                    builder.setKeepNativeStyle(easyQueryProperties.isKeepNativeStyle());
                    builder.setWarningColumnMiss(easyQueryProperties.isWarningColumnMiss());
                    builder.setShardingFetchSize(easyQueryProperties.getShardingFetchSize());
                    builder.setReverseOffsetThreshold(easyQueryProperties.getReverseOffsetThreshold());
                    builder.setMapToBeanStrict(easyQueryProperties.isMapToBeanStrict());
                    builder.setDefaultSchema(easyQueryProperties.getDefaultSchema());
                    builder.setResultSizeLimit(easyQueryProperties.getResultSizeLimit());
                    builder.setShardingQueryInTransaction(easyQueryProperties.getShardingQueryInTransaction());
                })
                .useDatabaseConfigure(databaseConfiguration)
                .useStarterConfigure(starterConfigurer)
                .build();

        return easyQueryClient;
    }

    @Bean
    @ConditionalOnMissingBean
    public EasyQuery easyQuery(EasyQueryClient easyQueryClient) {
        return new DefaultEasyQuery(easyQueryClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public EasyEntityQuery entityQuery(EasyQueryClient easyQueryClient) {
        return new DefaultEasyEntityQuery(easyQueryClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public EasyProxyQuery easyProxyQuery(EasyQueryClient easyQueryClient) {
        return new DefaultEasyProxyQuery(easyQueryClient);
    }
//    @Bean
//    @ConditionalOnMissingBean
//    public EntityQuery entityQuery(EasyQueryClient easyQueryClient) {
//        return new DefaultEntityQuery(easyQueryClient);
//    }

    @Bean
    @ConditionalOnMissingBean
    public EasyQueryInitializeOption easyQueryInitializeOption(Map<String, Interceptor> interceptorMap,
                                                               Map<String, VersionStrategy> versionStrategyMap,
                                                               Map<String, LogicDeleteStrategy> logicDeleteStrategyMap,
                                                               Map<String, ShardingInitializer> shardingInitializerMap,
                                                               Map<String, EncryptionStrategy> encryptionStrategyMap,
                                                               Map<String, ValueConverter<?, ?>> valueConverterMap,
                                                               Map<String, TableRoute<?>> tableRouteMap,
                                                               Map<String, DataSourceRoute<?>> dataSourceRouteMap,
                                                               Map<String, JdbcTypeHandler> jdbcTypeHandlerMap,
                                                               Map<String, ColumnValueSQLConverter> columnValueSQLConverterMap,
                                                               Map<String, GeneratedKeySQLColumnGenerator> generatedKeySQLColumnGeneratorMap,
                                                               Map<String, NavigateExtraFilterStrategy> navigateExtraFilterStrategyMap,
                                                               Map<String, NavigateValueSetter> navigateValueSetterMap,
                                                               Map<String, PrimaryKeyGenerator> primaryKeyGeneratorMap) {
        return new EasyQueryInitializeOption(interceptorMap,
                versionStrategyMap,
                logicDeleteStrategyMap,
                shardingInitializerMap,
                encryptionStrategyMap,
                valueConverterMap,
                tableRouteMap,
                dataSourceRouteMap,
                jdbcTypeHandlerMap,
                columnValueSQLConverterMap,
                generatedKeySQLColumnGeneratorMap,
                navigateExtraFilterStrategyMap,
                navigateValueSetterMap,
                primaryKeyGeneratorMap);
    }
}
