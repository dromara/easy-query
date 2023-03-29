package com.easy.query.sql.starter;

import com.easy.query.core.abstraction.DefaultEasyQueryLambdaFactory;
import com.easy.query.core.abstraction.DefaultEasyQueryRuntimeContext;
import com.easy.query.core.abstraction.EasyExpressionFactory;
import com.easy.query.core.abstraction.EasyQueryLambdaFactory;
import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.abstraction.EasySqlApiFactory;
import com.easy.query.core.basic.pagination.DefaultEasyPageResultProvider;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.api.client.DefaultEasyQuery;
import com.easy.query.core.api.client.EasyQuery;
import com.easy.query.core.api.def.DefaultEasySqlApiFactory;
import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.executor.DefaultEasyExecutor;
import com.easy.query.core.basic.jdbc.executor.EasyExecutor;
import com.easy.query.core.basic.jdbc.types.DefaultJdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.EasyJdbcTypeHandlerManager;
import com.easy.query.core.config.NameConversion;
import com.easy.query.core.config.UnderlinedNameConversion;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.basic.plugin.interceptor.EasyInterceptor;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.basic.plugin.logicdel.EasyLogicDeleteStrategy;
import com.easy.query.core.metadata.DefaultEntityMetadataManager;
import com.easy.query.core.basic.plugin.track.DefaultTrackManager;
import com.easy.query.core.basic.plugin.track.TrackManager;
import com.easy.query.core.util.StringUtil;
import com.easy.query.mysql.MySqlExpressionFactory;
import com.easy.query.mysql.config.MySqlDialect;
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
 * @FileName: EasyQueryStarter.java
 * @Description: 文件说明
 * @Date: 2023/3/11 12:47
 * @author xuejiaming
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
        if(StringUtil.isBlank(easyQueryProperties.getLogClass())){
            LogFactory.useCustomLogging(Slf4jImpl.class);
        }else {
            try {
                Class<?> aClass = Class.forName(easyQueryProperties.getLogClass());
                if(Log.class.isAssignableFrom(aClass)){
                    LogFactory.useCustomLogging((Class<? extends Log>)aClass);
                }else{
                    LogFactory.useStdOutLogging();
                    System.out.println("cant found log:["+easyQueryProperties.getLogClass()+"]");
                }
            } catch (ClassNotFoundException e) {
                System.out.println("cant found log:["+easyQueryProperties.getLogClass()+"]");
                e.printStackTrace();
            }
        }
    }

    @Bean
    public EasyConnectionManager easyConnectionManager(DataSource dataSource) {
        return new SpringConnectionManager(dataSource);
    }

    @Bean
    public EasyExecutor easyExecutor() {
        return new DefaultEasyExecutor();
    }
    @Bean
    public TrackManager trackManager(EntityMetadataManager entityMetadataManager){
        return new DefaultTrackManager(entityMetadataManager);
    }

    @Bean
    public EasyJdbcTypeHandlerManager easyJdbcTypeHandlerManager() {
        return new DefaultJdbcTypeHandlerManager();
    }

    @Bean
    public EasyQueryConfiguration easyQueryConfiguration(Map<String, EasyInterceptor> easyInterceptorMap, Map<String, EasyLogicDeleteStrategy> easyLogicDeleteStrategyMap) {
        EasyQueryConfiguration configuration = new EasyQueryConfiguration();

        NameConversion nameConversion = new UnderlinedNameConversion();
        MySqlDialect sqlDialect = new MySqlDialect();
        configuration.setNameConversion(nameConversion);
        configuration.setDialect(sqlDialect);
        for (EasyInterceptor easyInterceptor : easyInterceptorMap.values()) {
            configuration.applyGlobalInterceptor(easyInterceptor);
        }
        for (EasyLogicDeleteStrategy easyLogicDeleteStrategy : easyLogicDeleteStrategyMap.values()) {
            configuration.applyGlobalLogicDeleteStrategy(easyLogicDeleteStrategy);
        }
        return configuration;
    }

    @Bean
    public EntityMetadataManager entityMetadataManager(EasyQueryConfiguration configuration) {
        return new DefaultEntityMetadataManager(configuration);
    }

    @Bean
    public EasyQueryLambdaFactory easyQueryLambdaFactory() {
        return new DefaultEasyQueryLambdaFactory();
    }

    @Bean
    public EasyExpressionFactory easySqlExpressionFactory() {
        return new MySqlExpressionFactory();
    }

    @Bean
    public EasySqlApiFactory easySqlApiFactory(EasyExpressionFactory easySqlExpressionFactory) {
        return new DefaultEasySqlApiFactory(easySqlExpressionFactory);
    }
    @Bean
    public EasyPageResultProvider easyPageResultProvider(){
        return new DefaultEasyPageResultProvider();
    }

    @Bean
    public EasyQueryRuntimeContext easyQueryRuntimeContext(EasyQueryConfiguration easyQueryConfiguration,
                                                           EntityMetadataManager entityMetadataManager,
                                                           EasyQueryLambdaFactory easyQueryLambdaFactory,
                                                           EasyConnectionManager easyConnectionManager,
                                                           EasyExecutor easyExecutor,
                                                           EasyJdbcTypeHandlerManager easyJdbcTypeHandler,
                                                           EasySqlApiFactory easyQueryableFactory,
                                                           EasyExpressionFactory easySqlExpressionFactory,
                                                           TrackManager trackManager,
                                                           EasyPageResultProvider easyPageResultProvider) {
        return new DefaultEasyQueryRuntimeContext(
                easyQueryConfiguration,
                entityMetadataManager,
                easyQueryLambdaFactory,
                easyConnectionManager,
                easyExecutor,
                easyJdbcTypeHandler,
                easyQueryableFactory,
                easySqlExpressionFactory,
                trackManager,
                easyPageResultProvider
        );
    }

    @Bean
    public EasyQuery easyQuery(EasyQueryRuntimeContext easyQueryRuntimeContext) {
        return new DefaultEasyQuery(easyQueryRuntimeContext);
    }
}
