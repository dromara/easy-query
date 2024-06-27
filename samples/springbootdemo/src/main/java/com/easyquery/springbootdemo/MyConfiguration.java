package com.easyquery.springbootdemo;

import com.easy.query.api4j.client.DefaultEasyQuery;
import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.cache.core.EasyCacheClient;
import com.easy.query.cache.core.EasyCacheManager;
import com.easy.query.cache.core.bootstrapper.EasyCacheBootstrapper;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.generated.PrimaryKeyGenerator;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.util.EasyStringUtil;
import com.easyquery.springbootdemo.cache.DefaultEasyCacheManager;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.Serializable;

/**
 * create time 2023/7/4 22:32
 * 文件说明
 *
 * @author xuejiaming
 */
@Configuration
public class MyConfiguration {



//    @PostConstruct
//    public  void init(EasyQuery easyQuery){
//        QueryConfiguration queryConfiguration = easyQuery.getRuntimeContext().getQueryConfiguration();
//        queryConfiguration.applyPrimaryKeyGenerator(new UUIDPrimaryKeyGenerator());
//    }



    @Bean("myEasyQuery")
    @Primary
    public EasyQuery easyQuery(DataSource dataSource){
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .build();

        return new DefaultEasyQuery(easyQueryClient);
    }
    @Bean
    public RedissonClient redissonClient() {
        String address = "redis://127.0.0.1:55001";
        Integer database = 1;
        Integer minimumIdleSize = 1;
        Config config = new Config();
        config.useSingleServer().setConnectionMinimumIdleSize(minimumIdleSize).setDatabase(database).setAddress(address);
        String password ="redispw";
        if (EasyStringUtil.isNotBlank(password))
            config.useSingleServer().setPassword(password);
        Codec codec = new StringCodec();
        config.setCodec(codec);
        return Redisson.create(config);
    }


    @Bean
    public EasyCacheClient easyCacheClient(EasyQueryClient easyQueryClient,RedissonClient redissonClient){
        EasyCacheClient easyCacheClient = EasyCacheBootstrapper.defaultBuilderConfiguration()
                .replaceService(EasyQueryClient.class,easyQueryClient)
                .replaceService(RedissonClient.class,redissonClient)
                .replaceService(EasyCacheManager.class, DefaultEasyCacheManager.class).build();
        return easyCacheClient;
    }
}
