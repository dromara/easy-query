package com.easy.query.solon.integration;

import com.easy.query.clickhouse.config.ClickHouseDatabaseConfiguration;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.entity.ColumnEntityMappingRule;
import com.easy.query.core.basic.entity.EntityMappingRule;
import com.easy.query.core.basic.entity.PropertyEntityMappingRule;
import com.easy.query.core.basic.entity.PropertyFirstEntityMappingRule;
import com.easy.query.core.basic.entity.TryColumnAndPropertyEntityMappingRule;
import com.easy.query.core.basic.extension.formater.MyBatisSQLParameterPrintFormat;
import com.easy.query.core.basic.extension.formater.SQLParameterPrintFormat;
import com.easy.query.core.basic.extension.track.InvokeTryFinally;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.conn.ConnectionManager;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.ShardingEntityExpressionExecutor;
import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.bootstrapper.DefaultDatabaseConfiguration;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.bootstrapper.EasyQueryBuilderConfiguration;
import com.easy.query.core.common.EasyQueryTrackInvoker;
import com.easy.query.core.common.EmptyInvokeTryFinally;
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
import com.easy.query.core.enums.EntityMappingStrategyEnum;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
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
import com.easy.query.solon.integration.conn.SolonConnectionManager;
import com.easy.query.solon.integration.conn.SolonDataSourceUnitFactory;
import com.easy.query.solon.integration.holder.DefaultHolderFactory;
import com.easy.query.solon.integration.holder.EasyQueryHolder;
import com.easy.query.solon.integration.holder.HolderFactory;
import com.easy.query.solon.integration.option.DatabaseEnum;
import com.easy.query.solon.integration.option.MapKeyConversionEnum;
import com.easy.query.solon.integration.option.NameConversionEnum;
import com.easy.query.solon.integration.option.PropertyModeEnum;
import com.easy.query.solon.integration.option.SQLParameterPrintEnum;
import com.easy.query.sqlite.config.SQLiteDatabaseConfiguration;
import org.noear.solon.Utils;
import org.noear.solon.core.BeanWrap;
import org.noear.solon.core.Props;
import org.noear.solon.core.event.EventBus;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * create time 2023/7/19 21:53
 * 文件说明
 *
 * @author xuejiaming
 */

public class DbManager {
    private static final Log log = LogFactory.getLog(DbManager.class);
    private static DbManager _global = new DbManager();
    public static String DEFAULT_BEAN_NAME = "db1";
    public InvokeTryFinally allInvokeTryFinally = null;


    public static Supplier<HolderFactory> injectHolderFactory = () -> DefaultHolderFactory.DEFAULT;

    public static void replace(Supplier<HolderFactory> injectHolderFactory) {
        DbManager.injectHolderFactory = injectHolderFactory;
    }

    public static DbManager global() {
        return _global;
    }


    private static Map<String, EasyQueryHolder> dbMap = new ConcurrentHashMap<>();

    public static EasyQueryHolder get(BeanWrap bw) {
        EasyQueryHolder holder = dbMap.get(bw.name());

        if (holder == null) {
            synchronized (bw.name().intern()) {
                holder = dbMap.get(bw.name());
                if (holder == null) {
                    holder = build(bw);
//                    bw.context().getBeansOfType()
                    dbMap.put(bw.name(), holder);
//                    if(Utils.isBlank(DEFAULT_BEAN_NAME)){
//                        DEFAULT_BEAN_NAME=bw.name();
//                    }
                }
            }

        }

        return holder;
    }

    public static EasyQueryHolder getByName(String name) {
        return dbMap.get(name);
    }

    public static EasyQueryHolder removeByName(String name) {
        return dbMap.remove(name);
    }

    /**
     * 构建
     */
    private static EasyQueryHolder build(BeanWrap bw) {
        DataSource dataSource = bw.raw();
        Props dsProps;

        if (Utils.isNotEmpty(bw.name())) {
            String cfgPropPrefix = CommonConstant.TAG + "." + bw.name();
            dsProps = bw.context().cfg().getProp(cfgPropPrefix);
            if(dsProps.isEmpty() || !dsProps.containsKey("database")) {
                throw new UnsupportedOperationException("Please set the configuration for the data source "+bw.name()+" dialect in the yml file with specific key " + cfgPropPrefix + ".database");
            }
        } else {
            // all the data source bean must has a name
            throw new UnsupportedOperationException("The data source bean must has a name, please set the name for the data source bean.");
        }
        String configName = "ds-" + (bw.name() == null ? "" : bw.name());
        SolonEasyQueryProperties solonEasyQueryProperties = new SolonEasyQueryProperties(dsProps);
        EasyQueryBuilderConfiguration easyQueryBuilderConfiguration = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setName(configName)
                .setDefaultDataSource(dataSource)
                .optionConfigure(builder -> {
                    builder.setDeleteThrowError(solonEasyQueryProperties.getDeleteThrow());
                    builder.setInsertStrategy(solonEasyQueryProperties.getInsertStrategy());
                    builder.setUpdateStrategy(solonEasyQueryProperties.getUpdateStrategy());
                    builder.setMaxShardingQueryLimit(solonEasyQueryProperties.getMaxShardingQueryLimit());
                    builder.setExecutorMaximumPoolSize(solonEasyQueryProperties.getExecutorMaximumPoolSize());
                    builder.setExecutorCorePoolSize(solonEasyQueryProperties.getExecutorCorePoolSize());
                    builder.setThrowIfRouteNotMatch(solonEasyQueryProperties.isThrowIfRouteNotMatch());
                    builder.setShardingExecuteTimeoutMillis(solonEasyQueryProperties.getShardingExecuteTimeoutMillis());
                    builder.setMaxShardingRouteCount(solonEasyQueryProperties.getMaxShardingRouteCount());
                    builder.setExecutorQueueSize(solonEasyQueryProperties.getExecutorQueueSize());
                    builder.setDefaultDataSourceName(solonEasyQueryProperties.getDefaultDataSourceName());
                    builder.setDefaultDataSourceMergePoolSize(solonEasyQueryProperties.getDefaultDataSourceMergePoolSize());
                    builder.setMultiConnWaitTimeoutMillis(solonEasyQueryProperties.getMultiConnWaitTimeoutMillis());
                    builder.setWarningBusy(solonEasyQueryProperties.isWarningBusy());
                    builder.setInsertBatchThreshold(solonEasyQueryProperties.getInsertBatchThreshold());
                    builder.setUpdateBatchThreshold(solonEasyQueryProperties.getUpdateBatchThreshold());
                    builder.setPrintSql(solonEasyQueryProperties.isPrintSql());
                    builder.setPrintNavSql(solonEasyQueryProperties.isPrintNavSql());
                    builder.setStartTimeJob(solonEasyQueryProperties.isStartTimeJob());
                    builder.setDefaultTrack(solonEasyQueryProperties.isDefaultTrack());
                    builder.setRelationGroupSize(solonEasyQueryProperties.getRelationGroupSize());
                    builder.setWarningColumnMiss(solonEasyQueryProperties.isWarningColumnMiss());
                    builder.setShardingFetchSize(solonEasyQueryProperties.getShardingFetchSize());
                    builder.setConnectionMode(solonEasyQueryProperties.getConnectionMode());
                    builder.setReverseOffsetThreshold(solonEasyQueryProperties.getReverseOffsetThreshold());
                    builder.setMapToBeanStrict(solonEasyQueryProperties.getMapToBeanStrict());
                    builder.setDefaultSchema(solonEasyQueryProperties.getDefaultSchema());
                    builder.setResultSizeLimit(solonEasyQueryProperties.getResultSizeLimit());
                    builder.setShardingQueryInTransaction(solonEasyQueryProperties.getShardingQueryInTransaction());
                    builder.setMssqlMinBigDecimalScale(solonEasyQueryProperties.getMssqlMinBigDecimalScale());
                    builder.setIncludeLimitMode(solonEasyQueryProperties.getIncludeLimitMode());
                    builder.setSaveComment(solonEasyQueryProperties.getSaveComment());
                    builder.setMaxInClauseSize(solonEasyQueryProperties.getMaxInClauseSize());
                    builder.setGroupJoinMode(solonEasyQueryProperties.getGroupJoinMode());
                });


        DatabaseConfiguration databaseConfigure = getDatabaseConfigure(solonEasyQueryProperties);
        if (databaseConfigure == null) {
            throw new UnsupportedOperationException("Please select the correct database dialect. For solon-related configuration, set it in the yml file, for example:[easy-query.database: mysql]");
        }
        easyQueryBuilderConfiguration.useDatabaseConfigure(databaseConfigure);
        useNameConversion(solonEasyQueryProperties, easyQueryBuilderConfiguration);
        useMapKeyConversion(solonEasyQueryProperties, easyQueryBuilderConfiguration);
        usePropertyMode(solonEasyQueryProperties, easyQueryBuilderConfiguration);
        useMappingStrategy(solonEasyQueryProperties, easyQueryBuilderConfiguration);
        SQLParameterPrintEnum sqlParameterPrint = solonEasyQueryProperties.getSQLParameterPrint();
        if (sqlParameterPrint == SQLParameterPrintEnum.MYBATIS) {
            easyQueryBuilderConfiguration
                    .replaceService(SQLParameterPrintFormat.class, MyBatisSQLParameterPrintFormat.class);
        }
        if (Boolean.TRUE.equals(solonEasyQueryProperties.getSharding())) {
            easyQueryBuilderConfiguration
                    .replaceService(EntityExpressionExecutor.class, ShardingEntityExpressionExecutor.class);
        }
        easyQueryBuilderConfiguration
                .replaceService(DataSourceUnitFactory.class, SolonDataSourceUnitFactory.class)
                .replaceService(ConnectionManager.class, SolonConnectionManager.class);
//                .useStarterConfigure()

        //推到事件中心，用于扩展
        EventBus.publish(easyQueryBuilderConfiguration);

        EasyQueryClient easyQueryClient = easyQueryBuilderConfiguration.build();
        //扩展
        EventBus.publish(easyQueryClient.getRuntimeContext());
        return injectHolderFactory.get().getHolder(easyQueryClient);
    }


    private static void useNameConversion(SolonEasyQueryProperties solonEasyQueryProperties, EasyQueryBuilderConfiguration easyQueryBuilderConfiguration) {
        NameConversionEnum nameConversion = solonEasyQueryProperties.getNameConversion();
        switch (nameConversion) {
            case DEFAULT:
                easyQueryBuilderConfiguration.replaceService(NameConversion.class, new DefaultNameConversion());
                break;
            case UNDERLINED:
                easyQueryBuilderConfiguration.replaceService(NameConversion.class, new UnderlinedNameConversion());
                break;
            case LOWER_CAMEL_CASE:
                easyQueryBuilderConfiguration.replaceService(NameConversion.class, new LowerCamelCaseNameConversion());
                break;
            case UPPER_CAMEL_CASE:
                easyQueryBuilderConfiguration.replaceService(NameConversion.class, new UpperCamelCaseNameConversion());
                break;
            case UPPER_UNDERLINED:
                easyQueryBuilderConfiguration.replaceService(NameConversion.class, new UpperUnderlinedNameConversion());
                break;
        }
    }

    private static void usePropertyMode(SolonEasyQueryProperties solonEasyQueryProperties, EasyQueryBuilderConfiguration easyQueryBuilderConfiguration) {
        PropertyModeEnum propertyMode = solonEasyQueryProperties.getPropertyMode();
        switch (propertyMode) {
            case FIRST_LOWER:
                easyQueryBuilderConfiguration.replaceService(PropertyDescriptorMatcher.class, new DefaultPropertyDescriptorMatcher());
                break;
            case SAME_AS_ENTITY:
                easyQueryBuilderConfiguration.replaceService(PropertyDescriptorMatcher.class, new EntityPropertyDescriptorMatcher());
                break;
        }
    }

    private static void useMappingStrategy(SolonEasyQueryProperties solonEasyQueryProperties, EasyQueryBuilderConfiguration easyQueryBuilderConfiguration) {
        EntityMappingStrategyEnum mappingStrategy = solonEasyQueryProperties.getMappingStrategy();
        switch (mappingStrategy) {
            case COLUMN_ONLY:
                easyQueryBuilderConfiguration.replaceService(EntityMappingRule.class, ColumnEntityMappingRule.class);
                break;
            case PROPERTY_ONLY:
                easyQueryBuilderConfiguration.replaceService(EntityMappingRule.class, PropertyEntityMappingRule.class);
                break;
            case COLUMN_AND_PROPERTY:
                easyQueryBuilderConfiguration.replaceService(EntityMappingRule.class, TryColumnAndPropertyEntityMappingRule.class);
                break;
            case PROPERTY_FIRST:
                easyQueryBuilderConfiguration.replaceService(EntityMappingRule.class, PropertyFirstEntityMappingRule.class);
                break;
        }
    }

    private static void useMapKeyConversion(SolonEasyQueryProperties solonEasyQueryProperties, EasyQueryBuilderConfiguration easyQueryBuilderConfiguration) {
        MapKeyConversionEnum mapKeyConversionEnum = solonEasyQueryProperties.getMapKeyConversionEnum();
        switch (mapKeyConversionEnum) {
            case LOWER:
                easyQueryBuilderConfiguration.replaceService(Column2MapKeyConversion.class, LowerColumn2MapKeyConversion.class);
                break;
            case UPPER:
                easyQueryBuilderConfiguration.replaceService(Column2MapKeyConversion.class, UpperColumn2MapKeyConversion.class);
                break;
            case LOWER_UNDERLINED:
                easyQueryBuilderConfiguration.replaceService(Column2MapKeyConversion.class, LowerUnderlinedColumn2MapKeyConversion.class);
                break;
            case UPPER_UNDERLINED:
                easyQueryBuilderConfiguration.replaceService(Column2MapKeyConversion.class, UpperUnderlinedColumn2MapKeyConversion.class);
                break;
            case DEFAULT:
                easyQueryBuilderConfiguration.replaceService(Column2MapKeyConversion.class, DefaultColumn2MapKeyConversion.class);
                break;
        }
    }

    private static DatabaseConfiguration getDatabaseConfigure(SolonEasyQueryProperties solonEasyQueryProperties) {
        DatabaseEnum database = solonEasyQueryProperties.getDatabase();
        switch (database) {
            case MYSQL:
                return new MySQLDatabaseConfiguration();
            case PGSQL:
                return new PgSQLDatabaseConfiguration();
            case MSSQL:
                return new MsSQLDatabaseConfiguration();
            case H2:
                return new H2DatabaseConfiguration();
            case DAMENG:
                return new DamengDatabaseConfiguration();
            case KINGBASE_ES:
                return new KingbaseESDatabaseConfiguration();
            case MSSQL_ROW_NUMBER:
                return new MsSQLRowNumberDatabaseConfiguration();
            case ORACLE:
                return new OracleDatabaseConfiguration();
            case SQLITE:
                return new SQLiteDatabaseConfiguration();
            case CLICKHOUSE:
                return new ClickHouseDatabaseConfiguration();
            case GAUSS_DB:
                return new GaussDBDatabaseConfiguration();
            case DB2:
                return new DB2DatabaseConfiguration();
            case SQL92:
                return new DefaultDatabaseConfiguration();
        }
        return null;
    }

    public void reg(BeanWrap bw) {
        get(bw);
    }

    private synchronized InvokeTryFinally getAllInvokeTryFinally() {

        if (allInvokeTryFinally != null) {
            return allInvokeTryFinally;
        }
        InvokeTryFinally invokeTryFinally = EmptyInvokeTryFinally.EMPTY;
        Collection<EasyQueryHolder> values = dbMap.values();
        for (EasyQueryHolder holder : values) {
            TrackManager trackManager = holder.getEasyQueryClient().getRuntimeContext().getTrackManager();
            invokeTryFinally = new EasyQueryTrackInvoker(invokeTryFinally, trackManager);
        }
        allInvokeTryFinally = invokeTryFinally;
        return allInvokeTryFinally;
    }

    public InvokeTryFinally getTrackInvokeTryFinally(String tag) {
        InvokeTryFinally invokeTryFinally = EmptyInvokeTryFinally.EMPTY;
        if (EasyStringUtil.isBlank(tag)) {
            //如果全部的已经设置了
            if (allInvokeTryFinally != null) {
                return allInvokeTryFinally;
            }
            return getAllInvokeTryFinally();
        }
        if (tag.contains(",")) {
            String[] names = tag.split(",");
            for (String name : names) {
                EasyQueryHolder holder = dbMap.get(name);
                if (holder == null) {
                    log.warn("can not get track managers name:[" + name + "]");
                    continue;
                }
                TrackManager trackManager = holder.getEasyQueryClient().getRuntimeContext().getTrackManager();
                invokeTryFinally = new EasyQueryTrackInvoker(invokeTryFinally, trackManager);
            }
            return invokeTryFinally;

        } else {
            EasyQueryHolder holder = dbMap.get(tag);
            if (holder == null) {
                log.warn("can not get track managers name:[" + tag + "]");
                return EmptyInvokeTryFinally.EMPTY;
            }
            TrackManager trackManager = holder.getEasyQueryClient().getRuntimeContext().getTrackManager();
            return new EasyQueryTrackInvoker(invokeTryFinally, trackManager);
        }

    }

}

