package com.easy.query.sql.starter;

import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.plugin.encryption.EasyEncryptionStrategy;
import com.easy.query.core.basic.plugin.interceptor.EasyInterceptor;
import com.easy.query.core.basic.plugin.logicdel.EasyLogicDeleteStrategy;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.sharding.DefaultEasyQueryDataSource;
import com.easy.query.core.sharding.EasyQueryDataSource;
import com.easy.query.core.sql.dialect.Dialect;
import com.easy.query.core.api.client.EasyQuery;
import com.easy.query.core.sql.dialect.impl.MsSqlDialect;
import com.easy.query.core.sql.dialect.impl.MySqlDialect;
import com.easy.query.core.sql.dialect.impl.DefaultDialect;
import com.easy.query.core.sql.dialect.impl.PgSqlDialect;
import com.easy.query.core.sql.nameconversion.NameConversion;
import com.easy.query.core.sql.nameconversion.impl.DefaultNameConversion;
import com.easy.query.core.sql.nameconversion.impl.UnderlinedNameConversion;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.sharding.initializer.EasyShardingInitializer;
import com.easy.query.core.util.StringUtil;
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

    @Bean
    @ConditionalOnProperty(name = "easy-query.dialect",havingValue = "mysql")
    public Dialect mySqlDialect() {
        return new MySqlDialect();
    }
    @Bean
    @ConditionalOnProperty(name = "easy-query.dialect",havingValue = "mssql")
    public Dialect msSqlDialect() {
        return new MsSqlDialect();
    }
    @Bean
    @ConditionalOnProperty(name = "easy-query.dialect",havingValue = "pgsql")
    public Dialect pgSqlDialect() {
        return new PgSqlDialect();
    }
    @Bean
    @ConditionalOnProperty(name = "easy-query.dialect",havingValue = "default",matchIfMissing = true)
    public Dialect defaultDialect() {
        return new DefaultDialect();
    }

    @Bean
    @ConditionalOnProperty(name = "easy-query.name-conversion",havingValue = "underlined")
    public NameConversion underlinedNameConversion() {
        return new UnderlinedNameConversion();
    }
    @Bean
    @ConditionalOnProperty(name = "easy-query.name-conversion",havingValue = "default",matchIfMissing = true)
    public NameConversion defaultNameConversion() {
        return new DefaultNameConversion();
    }

    @Bean
    public EasyQueryDataSource easyQueryDataSource(DataSource dataSource){
        return new DefaultEasyQueryDataSource("ds0",dataSource);
    }
    @Bean
    public EasyConnectionManager easyConnectionManager(EasyQueryDataSource easyQueryDataSource){
        return new SpringConnectionManager(easyQueryDataSource);
    }






    @Bean
    public EasyQuery easyQuery(DataSource dataSource,EasyQueryDataSource easyQueryDataSource,EasyConnectionManager easyConnectionManager, Dialect dialect, NameConversion nameConversion, Map<String, EasyInterceptor> easyInterceptorMap, Map<String, EasyLogicDeleteStrategy> easyLogicDeleteStrategyMap, Map<String, EasyShardingInitializer> easyShardingInitializerMap, Map<String, EasyEncryptionStrategy> easyEncryptionStrategyMap) {
        EasyQuery easyQuery = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDataSource(dataSource)
                .setEasyQueryDataSource(easyQueryDataSource)
                .setConnectionManager(easyConnectionManager)
                .setDialect(dialect)
                .setNameConversion(nameConversion)
                .build();
        EasyQueryRuntimeContext runtimeContext = easyQuery.getRuntimeContext();
        EasyQueryConfiguration configuration = runtimeContext.getEasyQueryConfiguration();
        //拦截器注册
        for (EasyInterceptor easyInterceptor : easyInterceptorMap.values()) {
            configuration.applyEasyInterceptor(easyInterceptor);
        }
        //逻辑删除
        for (EasyLogicDeleteStrategy easyLogicDeleteStrategy : easyLogicDeleteStrategyMap.values()) {
            configuration.applyEasyLogicDeleteStrategy(easyLogicDeleteStrategy);
        }
        //分片初始化
        for (EasyShardingInitializer easyShardingInitializer : easyShardingInitializerMap.values()) {
            configuration.applyShardingInitializer(easyShardingInitializer);
        }
        //列加密
        for (EasyEncryptionStrategy easyEncryptionStrategy : easyEncryptionStrategyMap.values()) {
            configuration.applyEasyEncryptionStrategy(easyEncryptionStrategy);
        }
        return easyQuery;
    }
}
