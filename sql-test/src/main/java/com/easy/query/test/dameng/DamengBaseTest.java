package com.easy.query.test.dameng;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UpperUnderlinedNameConversion;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.dameng.config.DamengDatabaseConfiguration;
import com.easy.query.test.dameng.entity.DamengMyTopic;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import com.easy.query.test.mysql8.TreeA;
import com.easy.query.test.mysql8.TreeB;
import com.zaxxer.hikari.HikariDataSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2023/7/27 17:27
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class DamengBaseTest {
    public static HikariDataSource dataSource;
    public static EasyEntityQuery entityQuery;
    public static EasyQueryClient easyQueryClient;
    public static ListenerContextManager listenerContextManager;

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
        listenerContextManager = new ListenerContextManager();
        MyJdbcListener myJdbcListener = new MyJdbcListener(listenerContextManager);
        easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
//                    op.setPrintSql(false);
                })
                .useDatabaseConfigure(new DamengDatabaseConfiguration())
                .replaceService(NameConversion.class, UpperUnderlinedNameConversion.class)
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
//                .replaceService(BeanValueCaller.class, ReflectBeanValueCaller.class)
                .build();
        entityQuery = new DefaultEasyEntityQuery(easyQueryClient);
    }

    public static void initData() {
        DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(DamengMyTopic.class));
        codeFirstCommand.executeWithTransaction(s->s.commit());

        CodeFirstCommand codeFirstCommand1 = databaseCodeFirst.dropTableIfExistsCommand(Arrays.asList(TreeA.class, TreeB.class));
        codeFirstCommand1.executeWithTransaction(e->e.commit());

        CodeFirstCommand codeFirstCommand2 = databaseCodeFirst.syncTableCommand(Arrays.asList(TreeA.class, TreeB.class));
        codeFirstCommand2.executeWithTransaction(s->s.commit());

        entityQuery.deletable(DamengMyTopic.class).where(d -> d.id().isNotNull()).allowDeleteStatement(true).disableLogicDelete().executeRows();
        boolean topicAny = entityQuery.queryable(DamengMyTopic.class).any();
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
            long l = entityQuery.insertable(topics).executeRows();
        }

    }


}