package com.easy.query.sql.starter;

import com.easy.query.core.basic.jdbc.executor.DefaultEntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.thread.DefaultEasyShardingExecutorService;
import com.easy.query.core.basic.thread.EasyShardingExecutorService;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.expression.executor.parser.DefaultEasyPrepareParser;
import com.easy.query.core.expression.executor.query.DefaultExecutionContextFactory;
import com.easy.query.core.expression.parser.factory.DefaultEasyQueryLambdaFactory;
import com.easy.query.core.abstraction.DefaultEasyQueryRuntimeContext;
import com.easy.query.core.expression.sql.factory.DefaultEasyExpressionBuilderFactory;
import com.easy.query.core.expression.sql.factory.EasyExpressionBuilderFactory;
import com.easy.query.core.expression.parser.factory.EasyQueryLambdaFactory;
import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.abstraction.EasySqlApiFactory;
import com.easy.query.core.basic.pagination.DefaultEasyPageResultProvider;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.config.EasyQueryDialect;
import com.easy.query.core.expression.sql.factory.EasyExpressionFactory;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.api.client.DefaultEasyQuery;
import com.easy.query.core.api.client.EasyQuery;
import com.easy.query.core.api.def.DefaultEasySqlApiFactory;
import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
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
import com.easy.query.core.sharding.DefaultEasyDataSource;
import com.easy.query.core.sharding.EasyDataSource;
import com.easy.query.core.sharding.EasyShardingOption;
import com.easy.query.core.sharding.rewrite.DefaultRewriteContextFactory;
import com.easy.query.core.sharding.route.DefaultRouteContextFactory;
import com.easy.query.core.sharding.route.abstraction.DefaultDataSourceRouteManager;
import com.easy.query.core.sharding.route.abstraction.DefaultTableRouteManager;
import com.easy.query.core.sharding.route.datasource.engine.DefaultDataSourceRouteEngine;
import com.easy.query.core.sharding.route.table.engine.DefaultTableRouteEngine;
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
                    System.out.println("cant found log:["+easyQueryProperties.getLogClass()+"]!!!!!!");
                }
            } catch (ClassNotFoundException e) {
                System.err.println("cant found log:["+easyQueryProperties.getLogClass()+"]!!!!!!");
                e.printStackTrace();
            }
        }
    }

    @Bean
    public EasyConnectionManager easyConnectionManager(EasyDataSource easyDataSource) {
        return new SpringConnectionManager(easyDataSource);
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
     public NameConversion nameConversion(){
        return new UnderlinedNameConversion();
     }
     @Bean
     public EasyQueryDialect easyQueryDialect(){
        return new MySqlDialect();
     }

    @Bean
    public EasyQueryConfiguration easyQueryConfiguration(Map<String, EasyInterceptor> easyInterceptorMap, Map<String, EasyLogicDeleteStrategy> easyLogicDeleteStrategyMap,
                                                         NameConversion nameConversion,EasyQueryDialect sqlDialect) {
        //只有当不是false的时候才不是false,比如null那么也是true,说明也是不允许删除命令
        EasyQueryConfiguration configuration = new EasyQueryConfiguration(new EasyQueryOption(!Boolean.FALSE.equals(easyQueryProperties.getDeleteThrow())));

        configuration.setNameConversion(nameConversion);
        configuration.setDialect(sqlDialect);
        for (EasyInterceptor easyInterceptor : easyInterceptorMap.values()) {
            configuration.applyEasyInterceptor(easyInterceptor);
        }
        for (EasyLogicDeleteStrategy easyLogicDeleteStrategy : easyLogicDeleteStrategyMap.values()) {
            configuration.applyEasyLogicDeleteStrategy(easyLogicDeleteStrategy);
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
    public EasyExpressionBuilderFactory easyExpressionBuilderFactory() {
        return new DefaultEasyExpressionBuilderFactory();
    }

    @Bean
    public EasySqlApiFactory easySqlApiFactory(EasyExpressionBuilderFactory easySqlExpressionFactory) {
        return new DefaultEasySqlApiFactory(easySqlExpressionFactory);
    }
    @Bean
    public EasyPageResultProvider easyPageResultProvider(){
        return new DefaultEasyPageResultProvider();
    }
    @Bean
    public EasyDataSource easyDataSource(DataSource dataSource){
        return new DefaultEasyDataSource("ds0",dataSource);
    }
    @Bean
    public EntityExpressionExecutor entityExpressionExecutor(EasyDataSource easyDataSource, EntityMetadataManager entityMetadataManager,DataSource dataSource){

        DefaultEasyPrepareParser prepareParser = new DefaultEasyPrepareParser();
        DefaultDataSourceRouteManager defaultDataSourceRouteManager = new DefaultDataSourceRouteManager(entityMetadataManager,easyDataSource);
        DefaultDataSourceRouteEngine defaultDataSourceRouteEngine = new DefaultDataSourceRouteEngine(easyDataSource,entityMetadataManager,defaultDataSourceRouteManager);
        DefaultTableRouteManager defaultTableRouteManager = new DefaultTableRouteManager(entityMetadataManager);
        DefaultTableRouteEngine defaultTableRouteEngine = new DefaultTableRouteEngine(entityMetadataManager,defaultTableRouteManager);
        DefaultRouteContextFactory defaultRouteContextFactory = new DefaultRouteContextFactory(defaultDataSourceRouteEngine,defaultTableRouteEngine);
        DefaultRewriteContextFactory defaultRewriteContextFactory = new DefaultRewriteContextFactory();
        DefaultExecutionContextFactory defaultQueryCompilerContextFactory = new DefaultExecutionContextFactory(defaultRouteContextFactory,defaultRewriteContextFactory,easyDataSource);
        return new DefaultEntityExpressionExecutor(prepareParser, defaultQueryCompilerContextFactory);

    }
    @Bean
    public EasyShardingOption easyShardingOption(){

       return new EasyShardingOption(10, 20);

    }
    @Bean
    public EasyShardingExecutorService easyShardingExecutorService(EasyShardingOption easyShardingOption){
        return new DefaultEasyShardingExecutorService(easyShardingOption);
    }
    @Bean
    public EasyExpressionFactory easyExpressionFactory(){
        return new MySqlExpressionFactory();
    }

    @Bean
    public EasyQueryRuntimeContext easyQueryRuntimeContext(EasyQueryConfiguration easyQueryConfiguration,
                                                           EntityMetadataManager entityMetadataManager,
                                                           EasyQueryLambdaFactory easyQueryLambdaFactory,
                                                           EasyConnectionManager easyConnectionManager,
                                                           EntityExpressionExecutor entityExpressionExecutor,
                                                           EasyJdbcTypeHandlerManager easyJdbcTypeHandler,
                                                           EasySqlApiFactory easyQueryableFactory,
                                                           EasyExpressionBuilderFactory easySqlExpressionFactory,
                                                           TrackManager trackManager,
                                                           EasyPageResultProvider easyPageResultProvider,
                                                           EasyShardingOption easyShardingOption,
                                                           EasyShardingExecutorService easyShardingExecutorService,
                                                           EasyExpressionFactory easyExpressionFactory) {
        return new DefaultEasyQueryRuntimeContext(
                easyQueryConfiguration,
                entityMetadataManager,
                easyQueryLambdaFactory,
                easyConnectionManager,
                entityExpressionExecutor,
                easyJdbcTypeHandler,
                easyQueryableFactory,
                easySqlExpressionFactory,
                trackManager,
                easyPageResultProvider,
                easyShardingOption,
                easyShardingExecutorService,
                easyExpressionFactory
        );
    }

    @Bean
    public EasyQuery easyQuery(EasyQueryRuntimeContext easyQueryRuntimeContext) {
        return new DefaultEasyQuery(easyQueryRuntimeContext);
    }
}
