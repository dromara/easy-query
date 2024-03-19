package com.test.mutlidatasource;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.jdbc.conn.ConnectionManager;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UnderlinedNameConversion;
import com.easy.query.core.datasource.DataSourceUnitFactory;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.sql.starter.conn.SpringConnectionManager;
import com.easy.query.sql.starter.conn.SpringDataSourceUnitFactory;
import com.test.mutlidatasource.core.DefaultEasyMultiEntityQuery;
import com.test.mutlidatasource.core.EasyMultiEntityQuery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * create time 2024/3/13 15:53
 * 文件说明
 *
 * @author xuejiaming
 */
@Configuration
public class MultiDataSourceConfiguration {
    @Bean
    public EasyMultiEntityQuery easyMultiEntityQuery(EasyEntityQuery easyEntityQuery, @Qualifier("ds2") EasyEntityQuery easyEntityQuery2){
        HashMap<String, EasyEntityQuery> extra = new HashMap<>();
        extra.put("ds2",easyEntityQuery2);
        return new DefaultEasyMultiEntityQuery(easyEntityQuery,extra);
    }

    /**
     * 不是spring接管的datasource那么事务将不会生效
     * @return
     */
    @Bean("xxx")
    public DataSource dataSource2(){
        return DataSourceBuilder.create().driverClassName("")
                .url("")
                .username("")
                .password("").build();
    }
    @Bean("ds2")
    public EasyEntityQuery easyQuery(/*注入您自己的多数据源datasource*/@Qualifier("xxx")DataSource dataSource) {
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .replaceService(DataSourceUnitFactory.class, SpringDataSourceUnitFactory.class)//springboot下必须用来支持事务
                .replaceService(ConnectionManager.class, SpringConnectionManager.class)//springboot下必须用来支持事务
                .replaceService(NameConversion.class, new UnderlinedNameConversion())
                .optionConfigure(builder -> {
                    builder.setPrintSql(true);
                    builder.setKeepNativeStyle(true);
                })
                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
                .build();
//         QueryConfiguration queryConfiguration = easyQueryClient.getRuntimeContext().getQueryConfiguration();

//         configuration.applyEncryptionStrategy(new DefaultAesEasyEncryptionStrategy());
//         configuration.applyEncryptionStrategy(new Base64EncryptionStrategy());
//         configuration.applyEncryptionStrategy(new MyEncryptionStrategy());
//         configuration.applyEncryptionStrategy(new JavaEncryptionStrategy());
//         configuration.applyLogicDeleteStrategy(new MyLogicDelStrategy());
//         configuration.applyInterceptor(new MyEntityInterceptor());
//         configuration.applyInterceptor(new Topic1Interceptor());
//         configuration.applyInterceptor(new MyTenantInterceptor());
// //        configuration.applyShardingInitializer(new FixShardingInitializer());
//         configuration.applyShardingInitializer(new DataSourceAndTableShardingInitializer());
//         configuration.applyShardingInitializer(new TopicShardingShardingInitializer());
//         configuration.applyShardingInitializer(new TopicShardingTimeShardingInitializer());
//         configuration.applyShardingInitializer(new DataSourceShardingInitializer());
//         configuration.applyValueConverter(new EnumConverter());
//         configuration.applyValueConverter(new JsonConverter());
//         configuration.applyValueUpdateAtomicTrack(new IntegerNotValueUpdateAtomicTrack());
//         configuration.applyColumnValueSQLConverter(new MySQLAesEncryptColumnValueSQLConverter());
//         configuration.applyGeneratedKeySQLColumnGenerator(new MyDatabaseIncrementSQLColumnGenerator());
        return new DefaultEasyEntityQuery(easyQueryClient);
    }
}
