package com.easy.query.test.pgsql;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.basic.jdbc.types.JdbcTypeHandlerManager;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.sharding.router.manager.TableRouteManager;
import com.easy.query.pgsql.config.PgSQLDatabaseConfiguration;
import com.easy.query.test.doc.entity.DocBank;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.encryption.Base64EncryptionStrategy;
import com.easy.query.test.encryption.DefaultAesEasyEncryptionStrategy;
import com.easy.query.test.encryption.MyEncryptionStrategy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.MathTest;
import com.easy.query.test.entity.MyCategory;
import com.easy.query.test.entity.MyCategoryInterceptor;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.UUIDEntity;
import com.easy.query.test.enums.NamedEnum;
import com.easy.query.test.interceptor.MyEntityInterceptor;
import com.easy.query.test.interceptor.MyTenantInterceptor;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import com.easy.query.test.logicdel.MyLogicDelStrategy;
import com.easy.query.test.mysql8.TreeA;
import com.easy.query.test.mysql8.TreeB;
import com.easy.query.test.mysql8.entity.TableNoKey;
import com.easy.query.test.sharding.FixShardingInitializer;
import com.easy.query.test.sharding.TopicShardingTableRoute;
import com.easy.query.test.sharding.TopicShardingTimeTableRoute;
import com.zaxxer.hikari.HikariDataSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * create time 2023/5/10 16:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class PgSQLCTEBaseTest {
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
        dataSource.setJdbcUrl("jdbc:postgresql://127.0.0.1:54321/easy-query-test");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        dataSource.setDriverClassName("org.postgresql.Driver");
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
                    op.setDefaultSchema("public");
//                    op.setSelectAutoIncludeTable(SelectAutoIncludeTableEnum.WARNING);
                })
                .useDatabaseConfigure(new PgSQLDatabaseConfiguration())
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
                .build();
        entityQuery = new DefaultEasyEntityQuery(easyQueryClient);
        QueryRuntimeContext runtimeContext = entityQuery.getRuntimeContext();
        QueryConfiguration configuration = runtimeContext.getQueryConfiguration();
        configuration.applyEncryptionStrategy(new DefaultAesEasyEncryptionStrategy());
        configuration.applyEncryptionStrategy(new Base64EncryptionStrategy());
        configuration.applyEncryptionStrategy(new MyEncryptionStrategy());
        configuration.applyLogicDeleteStrategy(new MyLogicDelStrategy());
        configuration.applyInterceptor(new MyEntityInterceptor());
        configuration.applyInterceptor(new MyTenantInterceptor());
        configuration.applyInterceptor(new MyCategoryInterceptor());
        configuration.applyValueConverter(new JsonObjectAutoConverter());
//        configuration.applyInterceptor(new TopicInterceptor());
        configuration.applyShardingInitializer(new FixShardingInitializer());
        JdbcTypeHandlerManager jdbcTypeHandlerManager = runtimeContext.getJdbcTypeHandlerManager();
        jdbcTypeHandlerManager.appendHandler(String.class,PgSQLStringSupportJsonbTypeHandler.INSTANCE,true);

        TableRouteManager tableRouteManager = runtimeContext.getTableRouteManager();
        tableRouteManager.addRoute(new TopicShardingTableRoute());
        tableRouteManager.addRoute(new TopicShardingTimeTableRoute());
        testBeforex();
    }

    public static void initData() {


    }

    public static void testBeforex(){
        DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        {


            CodeFirstCommand codeFirstCommand = databaseCodeFirst.dropTableIfExistsCommand(Arrays.asList(MyCategory.class));
            codeFirstCommand.executeWithTransaction(a->a.commit());
        }
        {

            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(MyCategory.class));
            codeFirstCommand.executeWithTransaction(a->a.commit());
        }

    }

}
