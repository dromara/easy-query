package com.easy.query.sql.starter;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.bootstrapper.StarterConfigurer;
import com.easy.query.sql.starter.config.EasyQueryInitializeOption;
import com.easy.query.sql.starter.config.EasyQueryProperties;
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
 * create time 2023/3/11 12:47
 */
@Configuration
@EnableConfigurationProperties(EasyQueryProperties.class)
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter({DataSourceAutoConfiguration.class,EasyQueryStarterAutoConfiguration.class})
@ConditionalOnProperty(
        prefix = "easy-query",
        value = {"build"},
        matchIfMissing = true
)
public class EasyQueryStarterBuildAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public EasyQueryClient easyQueryClient(DataSource dataSource,EasyQueryProperties easyQueryProperties,EasyQueryInitializeOption easyQueryInitializeOption, StarterConfigurer starterConfigurer) {

        return SpringBootStarterBuilder.buildClient(dataSource, easyQueryProperties, easyQueryInitializeOption, starterConfigurer);
    }
    @Bean
    @ConditionalOnMissingBean
    public EasyEntityQuery entityQuery(EasyQueryClient easyQueryClient) {
        return new DefaultEasyEntityQuery(easyQueryClient);
    }
}
