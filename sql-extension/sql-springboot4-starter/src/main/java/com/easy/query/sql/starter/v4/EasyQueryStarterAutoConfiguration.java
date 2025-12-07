package com.easy.query.sql.starter.v4;

import com.easy.query.core.basic.extension.conversion.ColumnValueSQLConverter;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.extension.encryption.EncryptionStrategy;
import com.easy.query.core.basic.extension.generated.GeneratedKeySQLColumnGenerator;
import com.easy.query.core.basic.extension.generated.PrimaryKeyGenerator;
import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.extension.logicdel.LogicDeleteStrategy;
import com.easy.query.core.basic.extension.navigate.NavigateExtraFilterStrategy;
import com.easy.query.core.basic.extension.navigate.NavigateValueSetter;
import com.easy.query.core.basic.extension.version.VersionStrategy;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.bootstrapper.DefaultStarterConfigurer;
import com.easy.query.core.bootstrapper.StarterConfigurer;
import com.easy.query.core.expression.implicit.EntityRelationPropertyProvider;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.sharding.initializer.ShardingInitializer;
import com.easy.query.core.sharding.route.datasource.DataSourceRoute;
import com.easy.query.core.sharding.route.table.TableRoute;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.sql.starter.config.EasyQueryInitializeOption;
import com.easy.query.sql.starter.config.EasyQueryProperties;
import com.easy.query.sql.starter.logging.Slf4jImpl;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

/**
 * create time 2023/3/11 12:47
 *
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

    public EasyQueryStarterAutoConfiguration(EasyQueryProperties easyQueryProperties) {
        if (EasyStringUtil.isBlank(easyQueryProperties.getLogClass())) {
            LogFactory.useCustomLogging(Slf4jImpl.class);
        } else {
            try {
                Class<?> aClass = Class.forName(easyQueryProperties.getLogClass());
                if (Log.class.isAssignableFrom(aClass)) {
                    Class<? extends Log> logClass = EasyObjectUtil.typeCastNullable(aClass);
                    LogFactory.useCustomLogging(logClass);
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
    @ConditionalOnMissingBean
    public StarterConfigurer starterConfigurer() {
        return new DefaultStarterConfigurer();
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
                                                               Map<String, PrimaryKeyGenerator> primaryKeyGeneratorMap,
                                                               Map<String, EntityRelationPropertyProvider> entityRelationPropertyProviderMap) {
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
                primaryKeyGeneratorMap,
                entityRelationPropertyProviderMap);
    }
}
