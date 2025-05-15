package com.easy.query.test.mssqlrownumber;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UpperCamelCaseNameConversion;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.mssql.config.MsSQLRowNumberDatabaseConfiguration;
import com.easy.query.test.mssql.entity.MsSQLMyTopic;
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
public abstract class MsSQLRowNumberBaseTest {
    public static HikariDataSource dataSource;
    public static EasyEntityQuery entityQuery;

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
        dataSource.setJdbcUrl("jdbc:sqlserver://localhost:1433;database=mssql_eq");
        dataSource.setUsername("sa");
        dataSource.setPassword("Password.1");
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setMaximumPoolSize(20);
//        postgres://postgres:postgrespw@localhost:55000
    }

    public static void initEasyQuery() {
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                })
                .useDatabaseConfigure(new MsSQLRowNumberDatabaseConfiguration())
                .replaceService(NameConversion.class, UpperCamelCaseNameConversion.class)
//                .replaceService(BeanValueCaller.class, ReflectBeanValueCaller.class)
                .build();
        entityQuery = new DefaultEasyEntityQuery(easyQueryClient);
    }

    public static void initData() {


        boolean topicAny = entityQuery.queryable(MsSQLMyTopic.class).any();
        if (!topicAny) {
            List<MsSQLMyTopic> topics = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                MsSQLMyTopic topic = new MsSQLMyTopic();
                topic.setId(String.valueOf(i));
                topic.setStars(i + 100);
                topic.setTitle("标题" + i);
                topic.setCreateTime(LocalDateTime.now().plusDays(i));
                topics.add(topic);
            }
            long l = entityQuery.insertable(topics).executeRows();
        }

    }


}