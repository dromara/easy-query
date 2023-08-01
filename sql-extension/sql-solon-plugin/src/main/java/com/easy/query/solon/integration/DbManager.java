package com.easy.query.solon.integration;

import com.easy.query.api.proxy.client.DefaultEasyProxyQuery;
import com.easy.query.api.proxy.client.EasyProxyQuery;
import com.easy.query.api4j.client.DefaultEasyQuery;
import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.jdbc.conn.ConnectionManager;
import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.bootstrapper.EasyQueryBuilderConfiguration;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.configuration.nameconversion.impl.DefaultNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.LowerCamelCaseNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UnderlinedNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UpperCamelCaseNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UpperUnderlinedNameConversion;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.datasource.DataSourceUnitFactory;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.dameng.config.DamengDatabaseConfiguration;
import com.easy.query.h2.config.H2DatabaseConfiguration;
import com.easy.query.kingbase.es.config.KingbaseESDatabaseConfiguration;
import com.easy.query.mssql.config.MsSQLDatabaseConfiguration;
import com.easy.query.mssql.config.MsSQLRowNumberDatabaseConfiguration;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.pgsql.config.PgSQLDatabaseConfiguration;
import com.easy.query.solon.integration.conn.SolonConnectionManager;
import com.easy.query.solon.integration.conn.SolonDataSourceUnitFactory;
import com.easy.query.solon.integration.option.DatabaseEnum;
import com.easy.query.solon.integration.option.NameConversionEnum;
import org.noear.solon.Utils;
import org.noear.solon.core.BeanWrap;
import org.noear.solon.core.Props;
import org.noear.solon.core.event.EventBus;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time 2023/7/19 21:53
 * 文件说明
 *
 * @author xuejiaming
 */

public class DbManager {
    public static final String TAG = "easy-query";
    private static DbManager _global = new DbManager();
    private static String DEFAULT_BEAN_NAME;

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
                    holder=build(bw);
//                    bw.context().getBeansOfType()
                    dbMap.put(bw.name(), holder);
                    if(Utils.isBlank(DEFAULT_BEAN_NAME)){
                        DEFAULT_BEAN_NAME=bw.name();
                    }

                }
            }

        }

        return holder;
    }

    /**
     * 构建
     */
    private static EasyQueryHolder build(BeanWrap bw) {
        DataSource dataSource = bw.raw();
        Props dsProps;

        if (Utils.isNotEmpty(bw.name())) {
            dsProps = bw.context().cfg().getProp(TAG + "." + bw.name());
        } else {
            dsProps = new Props();
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
                    builder.setQueryLargeColumn(solonEasyQueryProperties.isQueryLargeColumn());
                    builder.setMaxShardingRouteCount(solonEasyQueryProperties.getMaxShardingRouteCount());
                    builder.setExecutorQueueSize(solonEasyQueryProperties.getExecutorQueueSize());
                    builder.setDefaultDataSourceName(solonEasyQueryProperties.getDefaultDataSourceName());
                    builder.setDefaultDataSourceMergePoolSize(solonEasyQueryProperties.getDefaultDataSourceMergePoolSize());
                    builder.setMultiConnWaitTimeoutMillis(solonEasyQueryProperties.getMultiConnWaitTimeoutMillis());
                    builder.setWarningBusy(solonEasyQueryProperties.isWarningBusy());
                    builder.setInsertBatchThreshold(solonEasyQueryProperties.getInsertBatchThreshold());
                    builder.setUpdateBatchThreshold(solonEasyQueryProperties.getUpdateBatchThreshold());
                    builder.setPrintSql(solonEasyQueryProperties.isPrintSql());
                    builder.setStartTimeJob(solonEasyQueryProperties.isStartTimeJob());
                    builder.setDefaultTrack(solonEasyQueryProperties.isDefaultTrack());
                    builder.setRelationGroupSize(solonEasyQueryProperties.getRelationGroupSize());
                    builder.setConnectionMode(solonEasyQueryProperties.getConnectionMode());
                    builder.setNoVersionError(solonEasyQueryProperties.isNoVersionError());
                });
        DatabaseConfiguration databaseConfigure = getDatabaseConfigure(solonEasyQueryProperties);
        if(databaseConfigure!=null){
            easyQueryBuilderConfiguration.useDatabaseConfigure(databaseConfigure);
        }
        useNameConversion(solonEasyQueryProperties,easyQueryBuilderConfiguration);
        easyQueryBuilderConfiguration
                .replaceService(DataSourceUnitFactory.class, SolonDataSourceUnitFactory.class)
                .replaceService(ConnectionManager.class, SolonConnectionManager.class);
//                .useStarterConfigure()

        //推到事件中心，用于扩展
        EventBus.push(easyQueryBuilderConfiguration);
        EasyQueryClient easyQueryClient =easyQueryBuilderConfiguration.build();
        DefaultEasyQuery easyQuery = new DefaultEasyQuery(easyQueryClient);
        DefaultEasyProxyQuery easyProxyQuery = new DefaultEasyProxyQuery(easyQueryClient);

        return new DefaultEasyQueryHolder(easyQueryClient,easyQuery,easyProxyQuery);
    }

    private static void useNameConversion(SolonEasyQueryProperties solonEasyQueryProperties,EasyQueryBuilderConfiguration easyQueryBuilderConfiguration){
        NameConversionEnum nameConversion = solonEasyQueryProperties.getNameConversion();
        switch (nameConversion){
            case DEFAULT:easyQueryBuilderConfiguration.replaceService(NameConversion.class, new DefaultNameConversion());break;
            case UNDERLINED:easyQueryBuilderConfiguration.replaceService(NameConversion.class, new UnderlinedNameConversion());break;
            case LOWER_CAMEL_CASE:easyQueryBuilderConfiguration.replaceService(NameConversion.class, new LowerCamelCaseNameConversion());break;
            case UPPER_CAMEL_CASE:easyQueryBuilderConfiguration.replaceService(NameConversion.class, new UpperCamelCaseNameConversion());break;
            case UPPER_UNDERLINED:easyQueryBuilderConfiguration.replaceService(NameConversion.class, new UpperUnderlinedNameConversion());break;
        }
    }
    private static DatabaseConfiguration getDatabaseConfigure(SolonEasyQueryProperties solonEasyQueryProperties){
        DatabaseEnum database = solonEasyQueryProperties.getDatabase();
        switch (database){
            case MYSQL:return new MySQLDatabaseConfiguration();
            case PGSQL:return new PgSQLDatabaseConfiguration();
            case MSSQL:return new MsSQLDatabaseConfiguration();
            case H2:return new H2DatabaseConfiguration();
            case DAMENG:return new DamengDatabaseConfiguration();
            case KINGBASE_ES:return new KingbaseESDatabaseConfiguration();
            case MSSQL_ROW_NUMBER:return new MsSQLRowNumberDatabaseConfiguration();
        }
        return null;
    }
    public void reg(BeanWrap bw) {
        get(bw);
    }

    public <T> T getInstance(String name,Class<T> clazz){
        String beanName = Utils.isBlank(name) ? DEFAULT_BEAN_NAME : name;
        EasyQueryHolder holder = dbMap.get(beanName);
        if(holder==null){
            return null;
        }
        if(EasyQuery.class.isAssignableFrom(clazz)){
            return EasyObjectUtil.typeCastNullable(holder.getEasyQuery());
        }
        if(EasyQueryClient.class.isAssignableFrom(clazz)){
            return EasyObjectUtil.typeCastNullable(holder.getEasyQueryClient());
        }
        if(EasyProxyQuery.class.isAssignableFrom(clazz)){
            return EasyObjectUtil.typeCastNullable(holder.getEasyProxyQuery());
        }
        if(QueryConfiguration.class.isAssignableFrom(clazz)){
            return EasyObjectUtil.typeCastNullable(holder.getEasyQueryClient().getRuntimeContext().getQueryConfiguration());
        }
        if(QueryRuntimeContext.class.isAssignableFrom(clazz)){
            return EasyObjectUtil.typeCastNullable(holder.getEasyQueryClient().getRuntimeContext());
        }
        return null;
    }
}

