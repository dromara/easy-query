package com.easy.query.sql.starter;

import com.easy.query.core.basic.jdbc.con.EasyConnectionFactory;
import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.plugin.encryption.EasyEncryptionStrategy;
import com.easy.query.core.basic.plugin.interceptor.EasyInterceptor;
import com.easy.query.core.basic.plugin.logicdel.EasyLogicDeleteStrategy;
import com.easy.query.core.basic.plugin.version.EasyVersionStrategy;
import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.bootstrapper.DefaultDatabaseConfiguration;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.sharding.DefaultEasyQueryDataSource;
import com.easy.query.core.sharding.EasyQueryDataSource;
import com.easy.query.core.api.client.EasyQuery;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.configuration.nameconversion.impl.DefaultNameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UnderlinedNameConversion;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.sharding.initializer.EasyShardingInitializer;
import com.easy.query.core.util.StringUtil;
import com.easy.query.mssql.MsSqlDatabaseConfiguration;
import com.easy.query.mysql.config.MySqlDatabaseConfiguration;
import com.easy.query.pgsql.PgSqlDatabaseConfiguration;
import com.easy.query.sql.starter.config.EasyQueryProperties;
import com.easy.query.sql.starter.logging.Slf4jImpl;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
    private final EasyQueryProperties easyQueryProperties;

    public EasyQueryStarterAutoConfiguration(EasyQueryProperties easyQueryProperties) {
        this.easyQueryProperties = easyQueryProperties;
        if (StringUtil.isBlank(easyQueryProperties.getLogClass())) {
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

    //    @Bean
//    @ConditionalOnProperty(name = "setDatabase",havingValue = "mssql")
//    public Dialect msSqlDialect() {
//        return new MsSqlDialect();
//    }
//    @Bean
//    @ConditionalOnProperty(name = "setDatabase",havingValue = "pgsql")
//    public Dialect pgSqlDialect() {
//        return new PgSqlDialect();
//    }
//    @Bean
//    @ConditionalOnProperty(name = "setDatabase",havingValue = "default",matchIfMissing = true)
//    public Dialect defaultDialect() {
//        return new DefaultDialect();
//    }
    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "mysql")
    public DatabaseConfiguration mysqlDatabaseConfiguration() {
        return new MySqlDatabaseConfiguration();
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "mssql")
    public DatabaseConfiguration mssqlDatabaseConfiguration() {
        return new MsSqlDatabaseConfiguration();
    }
    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "pgsql")
    public DatabaseConfiguration pgsqlDatabaseConfiguration() {
        return new PgSqlDatabaseConfiguration();
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.database", havingValue = "default", matchIfMissing = true)
    public DatabaseConfiguration databaseConfiguration() {
        return new DefaultDatabaseConfiguration();
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.name-conversion", havingValue = "underlined", matchIfMissing = true)
    public NameConversion underlinedNameConversion() {
        return new UnderlinedNameConversion();
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.name-conversion", havingValue = "default")
    public NameConversion defaultNameConversion() {
        return new DefaultNameConversion();
    }


    @Bean
    public EasyQuery easyQuery(DataSource dataSource, DatabaseConfiguration databaseConfiguration, NameConversion nameConversion, Map<String, EasyInterceptor> easyInterceptorMap, Map<String, EasyVersionStrategy> easyVersionStrategyMap, Map<String, EasyLogicDeleteStrategy> easyLogicDeleteStrategyMap, Map<String, EasyShardingInitializer> easyShardingInitializerMap, Map<String, EasyEncryptionStrategy> easyEncryptionStrategyMap) {
        EasyQuery easyQuery = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDataSource(dataSource)
                .replaceService(EasyConnectionFactory.class,SpringEasyConnectionFactory.class)
                .optionConfigure(builder->{
                    builder.setDeleteThrowError(easyQueryProperties.getDeleteThrow());
                    builder.setInsertStrategy(easyQueryProperties.getInsertStrategy());
                    builder.setUpdateStrategy(easyQueryProperties.getUpdateStrategy());
                    builder.setMaxShardingQueryLimit(easyQueryProperties.getMaxShardingQueryLimit());
                    builder.setExecutorMaximumPoolSize(easyQueryProperties.getExecutorMaximumPoolSize());
                    builder.setExecutorCorePoolSize(easyQueryProperties.getExecutorCorePoolSize());
                    builder.setThrowIfRouteNotMatch(easyQueryProperties.isThrowIfRouteNotMatch());
                })
                .replaceService(NameConversion.class, nameConversion)
                .replaceServiceFactory(EasyQueryDataSource.class, sp -> new DefaultEasyQueryDataSource("ds0", sp.getService(DataSource.class)))
                .replaceService(EasyConnectionManager.class, SpringConnectionManager.class)
                .useDatabaseConfigure(databaseConfiguration)
                .build();
        EasyQueryRuntimeContext runtimeContext = easyQuery.getRuntimeContext();
        EasyQueryConfiguration configuration = runtimeContext.getEasyQueryConfiguration();
        //拦截器注册
        for (Map.Entry<String, EasyInterceptor> easyInterceptorEntry : easyInterceptorMap.entrySet()) {
            configuration.applyEasyInterceptor(easyInterceptorEntry.getValue());
        }
        //逻辑删除
        for (Map.Entry<String, EasyLogicDeleteStrategy> easyLogicDeleteStrategyEntry : easyLogicDeleteStrategyMap.entrySet()) {
            configuration.applyEasyLogicDeleteStrategy(easyLogicDeleteStrategyEntry.getValue());
        }
        //分片初始化
        for (Map.Entry<String, EasyShardingInitializer> easyShardingInitializerEntry : easyShardingInitializerMap.entrySet()) {
            configuration.applyShardingInitializer(easyShardingInitializerEntry.getValue());
        }
        //列加密
        for (Map.Entry<String, EasyEncryptionStrategy> easyEncryptionStrategyEntry : easyEncryptionStrategyMap.entrySet()) {
            configuration.applyEasyEncryptionStrategy(easyEncryptionStrategyEntry.getValue());
        }
        for (Map.Entry<String, EasyVersionStrategy> easyVersionStrategyEntry : easyVersionStrategyMap.entrySet()) {
            configuration.applyEasyVersionStrategy(easyVersionStrategyEntry.getValue());
        }
        return easyQuery;
    }
}
