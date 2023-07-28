package com.easy.query.test.dameng;

import com.easy.query.api4j.client.DefaultEasyQuery;
import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UpperUnderlinedNameConversion;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.dameng.config.DamengDatabaseConfiguration;
import com.easy.query.test.dameng.entity.DamengMyTopic;
import com.zaxxer.hikari.HikariDataSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * create time 2023/7/27 17:27
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class DamengBaseTest {
    public static HikariDataSource dataSource;
    public static EasyQuery easyQuery;

    static {
        LogFactory.useStdOutLogging();
        init();
    }


    public static void init() {
        initDatasource();
        initEasyQuery();
        initData();
    }

    public static void initDatasource() {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:dm://localhost:5236/");
        dataSource.setUsername("SYSDBA");
        dataSource.setPassword("SYSDBA");
        dataSource.setDriverClassName("dm.jdbc.driver.DmDriver");
        dataSource.setMaximumPoolSize(20);
//        postgres://postgres:postgrespw@localhost:55000
    }

    public static void initEasyQuery() {
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                })
                .useDatabaseConfigure(new DamengDatabaseConfiguration())
                .replaceService(NameConversion.class, UpperUnderlinedNameConversion.class)
//                .replaceService(BeanValueCaller.class, ReflectBeanValueCaller.class)
                .build();
        easyQuery = new DefaultEasyQuery(easyQueryClient);
    }

    public static void initData() {


        boolean topicAny = easyQuery.queryable(DamengMyTopic.class).any();
        if (!topicAny) {
            List<DamengMyTopic> topics = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                DamengMyTopic topic = new DamengMyTopic();
                topic.setId(String.valueOf(i));
                topic.setStars(i + 100);
                topic.setTitle("标题" + i);
                topic.setCreateTime(LocalDateTime.now().plusDays(i));
                topics.add(topic);
            }
            long l = easyQuery.insertable(topics).executeRows();
        }

    }


}