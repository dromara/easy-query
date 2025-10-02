package com.easy.query.test.duckdb;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.duckdb.config.DuckDBSQLDatabaseConfiguration;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.h2.domain.ALLTYPE;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import com.zaxxer.hikari.HikariDataSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/10/2 15:12
 * 文件说明
 *
 * @author xuejiaming
 */
public class DuckDBBaseTest {
    public static HikariDataSource dataSource;
    public static EasyEntityQuery easyEntityQuery;
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
//        dataSource.setJdbcUrl("jdbc:duckdb:/data/db/mydb.db");
        dataSource.setJdbcUrl("jdbc:duckdb:/Users/xuejiaming/Desktop/dromara/easy-query/sql-test/src/main/resources/duckdb.db");
        dataSource.setUsername("duckdb");
        dataSource.setPassword("duckdb");
        dataSource.setDriverClassName("org.duckdb.DuckDBDriver");
        dataSource.setMaximumPoolSize(50);
//        postgres://postgres:postgrespw@localhost:55000
    }

    public static void initEasyQuery() {
        listenerContextManager = new ListenerContextManager();
        MyJdbcListener myJdbcListener = new MyJdbcListener(listenerContextManager);
        easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                    op.setExecutorCorePoolSize(1);
                    op.setExecutorMaximumPoolSize(2);
                    op.setMaxShardingQueryLimit(1);
                })
                .useDatabaseConfigure(new DuckDBSQLDatabaseConfiguration())
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
                .build();
        easyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);
    }

    public static void initData() {

        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.dropTableIfExistsCommand(Arrays.asList(BlogEntity.class, ALLTYPE.class)).executeWithTransaction(s->s.commit());
        databaseCodeFirst.syncTableCommand(Arrays.asList(BlogEntity.class, ALLTYPE.class)).executeWithTransaction(s->s.commit());


        boolean any = easyEntityQuery.queryable(BlogEntity.class).any();
        if (!any) {

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
            easyEntityQuery.insertable(blogs).executeRows();
        }

    }
}
