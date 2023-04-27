package com.easy.query.sql.starter;

import com.easy.query.core.basic.plugin.encryption.EasyEncryptionStrategy;
import com.easy.query.core.basic.plugin.interceptor.EasyInterceptor;
import com.easy.query.core.basic.plugin.logicdel.EasyLogicDeleteStrategy;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.config.IDialect;
import com.easy.query.core.api.client.DefaultEasyQuery;
import com.easy.query.core.api.client.EasyQuery;
import com.easy.query.core.config.MySqlDialect;
import com.easy.query.core.config.NameConversion;
import com.easy.query.core.config.UnderlinedNameConversion;
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
                    System.out.println("cant found log:["+easyQueryProperties.getLogClass()+"]!!!!!!");
                }
            } catch (ClassNotFoundException e) {
                System.err.println("cant found log:["+easyQueryProperties.getLogClass()+"]!!!!!!");
                e.printStackTrace();
            }
        }
    }
     @Bean
     public NameConversion nameConversion(){
        return new UnderlinedNameConversion();
     }
     @Bean
     public IDialect dialect(){
        return new MySqlDialect();
     }
    @Bean
    public EasyQuery easyQuery(DataSource dataSource, IDialect dialect, NameConversion nameConversion, Map<String, EasyInterceptor> easyInterceptorMap, Map<String, EasyLogicDeleteStrategy> easyLogicDeleteStrategyMap,Map<String, EasyShardingInitializer> easyShardingInitializerMap,Map<String, EasyEncryptionStrategy> easyEncryptionStrategyMap) {
        EasyQuery easyQuery = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDataSource(dataSource)
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
