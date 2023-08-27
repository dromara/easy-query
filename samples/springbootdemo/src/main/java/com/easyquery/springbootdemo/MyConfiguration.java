package com.easyquery.springbootdemo;

import com.easy.query.api4j.client.DefaultEasyQuery;
import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * create time 2023/7/4 22:32
 * 文件说明
 *
 * @author xuejiaming
 */
@Configuration
public class MyConfiguration {
    @Bean("myEasyQuery")
    @Primary
    public EasyQuery easyQuery(DataSource dataSource){
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .build();

        return new DefaultEasyQuery(easyQueryClient);
    }
}
