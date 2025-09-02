package com.easy.query.sql.starter;

import com.easy.query.clickhouse.config.ClickHouseDatabaseConfiguration;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.entity.ColumnEntityMappingRule;
import com.easy.query.core.basic.entity.EntityMappingRule;
import com.easy.query.core.basic.entity.PropertyEntityMappingRule;
import com.easy.query.core.basic.entity.PropertyFirstEntityMappingRule;
import com.easy.query.core.basic.entity.TryColumnAndPropertyEntityMappingRule;
import com.easy.query.core.basic.extension.formater.DefaultSQLParameterPrintFormat;
import com.easy.query.core.basic.extension.formater.MyBatisSQLParameterPrintFormat;
import com.easy.query.core.basic.extension.formater.SQLParameterPrintFormat;
import com.easy.query.core.basic.jdbc.conn.ConnectionManager;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.ShardingEntityExpressionExecutor;
import com.easy.query.core.bootstrapper.DefaultDatabaseConfiguration;
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
import com.easy.query.core.configuration.nameconversion.impl.*;
import com.easy.query.core.datasource.DataSourceUnitFactory;
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
import com.easy.query.sql.starter.option.SQLParameterPrintEnum;
import com.easy.query.sqlite.config.SQLiteDatabaseConfiguration;

import javax.sql.DataSource;

/**
 * create time 2025/3/13 19:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class SpringBootStarterBuilder {
    public static EasyQueryClient buildClient(DataSource dataSource, EasyQueryProperties easyQueryProperties, EasyQueryInitializeOption easyQueryInitializeOption, StarterConfigurer starterConfigurer) {
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .replaceService(DataSourceUnitFactory.class, SpringDataSourceUnitFactory.class)
                .replaceService(ConnectionManager.class, SpringConnectionManager.class)
                .customConfigure(s -> {
                    switch (easyQueryProperties.getNameConversion()) {
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
                        case LOWER_SNAKE_CASE:
                            s.addService(NameConversion.class, LowerSnakeCaseNameConversion.class);
                            break;
                        case UPPER_SNAKE_CASE:
                            s.addService(NameConversion.class, UpperSnakeCaseNameConversion.class);
                            break;
                        case DEFAULT:
                            s.addService(NameConversion.class, DefaultNameConversion.class);
                            break;
                    }

                    switch (easyQueryProperties.getMapKeyConversion()) {
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
                    switch (easyQueryProperties.getPropertyMode()) {
                        case FIRST_LOWER:
                            s.addService(PropertyDescriptorMatcher.class, DefaultPropertyDescriptorMatcher.class);
                            break;
                        case SAME_AS_ENTITY:
                            s.addService(PropertyDescriptorMatcher.class, EntityPropertyDescriptorMatcher.class);
                            break;
                    }
                    switch (easyQueryProperties.getMappingStrategy()) {
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
                .customConfigure(s -> {
                    if (easyQueryProperties.getSqlParameterPrint() == SQLParameterPrintEnum.MYBATIS) {
                        s.addService(SQLParameterPrintFormat.class, MyBatisSQLParameterPrintFormat.class);
                    } else {
                        s.addService(SQLParameterPrintFormat.class, DefaultSQLParameterPrintFormat.class);
                    }
                })
                .customConfigure(s -> {
                    if (Boolean.TRUE.equals(easyQueryProperties.getSharding())) {
                        s.addService(EntityExpressionExecutor.class, ShardingEntityExpressionExecutor.class);
                    }
                })
                .optionConfigure(builder -> {
                    builder.setDeleteThrowError(easyQueryProperties.getDeleteThrow());
                    builder.setInsertStrategy(easyQueryProperties.getInsertStrategy());
                    builder.setUpdateStrategy(easyQueryProperties.getUpdateStrategy());
                    builder.setMaxShardingQueryLimit(easyQueryProperties.getMaxShardingQueryLimit());
                    builder.setExecutorMaximumPoolSize(easyQueryProperties.getExecutorMaximumPoolSize());
                    builder.setExecutorCorePoolSize(easyQueryProperties.getExecutorCorePoolSize());
                    builder.setThrowIfRouteNotMatch(easyQueryProperties.isThrowIfRouteNotMatch());
                    builder.setShardingExecuteTimeoutMillis(easyQueryProperties.getShardingExecuteTimeoutMillis());
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
                    builder.setWarningColumnMiss(easyQueryProperties.isWarningColumnMiss());
                    builder.setShardingFetchSize(easyQueryProperties.getShardingFetchSize());
                    builder.setReverseOffsetThreshold(easyQueryProperties.getReverseOffsetThreshold());
                    builder.setMapToBeanStrict(easyQueryProperties.isMapToBeanStrict());
                    builder.setDefaultSchema(easyQueryProperties.getDefaultSchema());
                    builder.setResultSizeLimit(easyQueryProperties.getResultSizeLimit());
                    builder.setShardingQueryInTransaction(easyQueryProperties.getShardingQueryInTransaction());
                    builder.setMssqlMinBigDecimalScale(easyQueryProperties.getMssqlMinBigDecimalScale());
                    builder.setIncludeLimitMode(easyQueryProperties.getIncludeLimitMode());
                    builder.setSaveComment(easyQueryProperties.isSaveComment());
                    builder.setMaxInClauseSize(easyQueryProperties.getMaxInClauseSize());
                    builder.setDefaultCondition(easyQueryProperties.getDefaultCondition());
                })
                .customConfigure(s -> {
                    switch (easyQueryProperties.getDatabase()) {
                        case MYSQL:
                            new MySQLDatabaseConfiguration().configure(s);
                            break;
                        case MSSQL:
                            new MsSQLDatabaseConfiguration().configure(s);
                            break;
                        case PGSQL:
                            new PgSQLDatabaseConfiguration().configure(s);
                            break;
                        case H2:
                            new H2DatabaseConfiguration().configure(s);
                            break;
                        case DAMENG:
                            new DamengDatabaseConfiguration().configure(s);
                            break;
                        case KINGBASE_ES:
                            new KingbaseESDatabaseConfiguration().configure(s);
                            break;
                        case MSSQL_ROW_NUMBER:
                            new MsSQLRowNumberDatabaseConfiguration().configure(s);
                            break;
                        case ORACLE:
                            new OracleDatabaseConfiguration().configure(s);
                            break;
                        case SQLITE:
                            new SQLiteDatabaseConfiguration().configure(s);
                            break;
                        case CLICKHOUSE:
                            new ClickHouseDatabaseConfiguration().configure(s);
                            break;
                        case GAUSS_DB:
                            new GaussDBDatabaseConfiguration().configure(s);
                            break;
                        case DB2:
                            new DB2DatabaseConfiguration().configure(s);
                            break;
                        case SQL92:
                            new DefaultDatabaseConfiguration().configure(s);
                            break;
                        default:
                            throw new UnsupportedOperationException("Please select the correct database dialect. For Spring-related configuration, set it in the yml file, for example:[easy-query.database: mysql]");

                    }
                })
                .customConfigure(s -> {
                    if (starterConfigurer != null) {
                        starterConfigurer.configure(s);
                    }
                })
                .build();
        if (easyQueryInitializeOption != null) {
            easyQueryInitializeOption.addComponents(easyQueryClient);
        }
        return easyQueryClient;
    }
}
