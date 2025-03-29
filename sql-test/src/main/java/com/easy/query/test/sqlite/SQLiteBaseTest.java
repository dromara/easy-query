package com.easy.query.test.sqlite;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UpperCamelCaseNameConversion;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.sqlite.config.SQLiteDatabaseConfiguration;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import com.zaxxer.hikari.HikariDataSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/3/25 09:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLiteBaseTest {
    public static HikariDataSource dataSource;
    public static EasyEntityQuery entityQuery;
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
        dataSource.setJdbcUrl("jdbc:sqlite:/Users/xuejiaming/Desktop/dromara/easy-query/sql-test/src/main/resources/sqlite_test.db");
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setMaximumPoolSize(20);
//        postgres://postgres:postgrespw@localhost:55000
    }

    public static void initEasyQuery() {
        listenerContextManager=new ListenerContextManager();
        MyJdbcListener myJdbcListener = new MyJdbcListener(listenerContextManager);
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                })
                .useDatabaseConfigure(new SQLiteDatabaseConfiguration())
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
                .replaceService(NameConversion.class, UpperCamelCaseNameConversion.class)
//                .replaceService(BeanValueCaller.class, ReflectBeanValueCaller.class)
                .build();
        entityQuery = new DefaultEasyEntityQuery(easyQueryClient);
    }

    public static void initData() {

        DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(BlogEntity.class));
        codeFirstCommand.executeWithTransaction(s->s.commit());

        boolean topicAny = entityQuery.queryable(BlogEntity.class).any();
        if (!topicAny) {

            LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
            List<BlogEntity> blogs = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                String indexStr = String.valueOf(i);
                BlogEntity blog = new BlogEntity();
                blog.setId(indexStr);
                blog.setCreateBy(indexStr);
                blog.setCreateTime(begin.plusDays(i));
                blog.setUpdateBy(indexStr);
                blog.setUpdateTime(begin.plusDays(i));
                blog.setTitle("title" + indexStr);
                blog.setContent("content" + indexStr);
                blog.setUrl("http://blog.easy-query.com/" + indexStr);
                blog.setStar(i);
                blog.setScore(new BigDecimal("1.2"));
                blog.setStatus(i % 3 == 0 ? 0 : 1);
                blog.setOrder(new BigDecimal("1.2").multiply(BigDecimal.valueOf(i)));
                blog.setIsTop(i % 2 == 0);
                blog.setTop(i % 2 == 0);
                blog.setDeleted(false);
                blogs.add(blog);
            }
            long l = entityQuery.insertable(blogs).executeRows();
        }

    }

}
