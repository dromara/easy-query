package com.easy.query.test.pgsql;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.sharding.router.manager.TableRouteManager;
import com.easy.query.test.doc.entity.DocBank;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.encryption.Base64EncryptionStrategy;
import com.easy.query.test.encryption.DefaultAesEasyEncryptionStrategy;
import com.easy.query.test.encryption.MyEncryptionStrategy;
import com.easy.query.pgsql.config.PgSQLDatabaseConfiguration;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.MyCategoryInterceptor;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.UUIDEntity;
import com.easy.query.test.interceptor.MyEntityInterceptor;
import com.easy.query.test.interceptor.MyTenantInterceptor;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import com.easy.query.test.logicdel.MyLogicDelStrategy;
import com.easy.query.test.mysql8.TreeA;
import com.easy.query.test.mysql8.TreeB;
import com.easy.query.test.sharding.FixShardingInitializer;
import com.easy.query.test.sharding.TopicShardingTableRoute;
import com.easy.query.test.sharding.TopicShardingTimeTableRoute;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Before;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2023/5/10 16:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class PgSQLBaseTest {
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
        dataSource.setJdbcUrl("jdbc:postgresql://127.0.0.1:55000/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgrespw");
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
//        configuration.applyInterceptor(new TopicInterceptor());
        configuration.applyShardingInitializer(new FixShardingInitializer());

        TableRouteManager tableRouteManager = runtimeContext.getTableRouteManager();
        tableRouteManager.addRoute(new TopicShardingTableRoute());
        tableRouteManager.addRoute(new TopicShardingTimeTableRoute());
        testBeforex();
    }

    public static void initData() {

        boolean any = entityQuery.queryable(BlogEntity.class).any();
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
            entityQuery.insertable(blogs).executeRows();
        }

    }

    public static void testBeforex(){
        DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        {


            CodeFirstCommand codeFirstCommand = databaseCodeFirst.dropTableIfExistsCommand(Arrays.asList(DocBankCard.class,DocBank.class,  DocUser.class, SysUser.class, UUIDEntity.class, TreeA.class, TreeB.class));
            codeFirstCommand.executeWithTransaction(a->a.commit());
        }
        {

            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(DocBank.class,DocBankCard.class, DocUser.class, SysUser.class, UUIDEntity.class, TreeA.class, TreeB.class));
            codeFirstCommand.executeWithTransaction(a->a.commit());
        }

        ArrayList<DocBankCard> docBankCards = new ArrayList<>();
        ArrayList<DocUser> docUsers = new ArrayList<>();
        ArrayList<DocBank> docBanks = new ArrayList<>();

        {
            {
                DocUser docUser = new DocUser();
                docUser.setId("小明id");
                docUser.setName("小明姓名");
                docUser.setPhone("小明手机");
                docUser.setAge(1);
                docUsers.add(docUser);
            }
            {
                DocUser docUser = new DocUser();
                docUser.setId("小红id");
                docUser.setName("小红姓名");
                docUser.setPhone("小红手机");
                docUser.setAge(1);
                docUsers.add(docUser);
            }
            {
                DocUser docUser = new DocUser();
                docUser.setId("小黄id");
                docUser.setName("小黄姓名");
                docUser.setPhone("小黄手机");
                docUser.setAge(1);
                docUsers.add(docUser);
            }
        }

        {
            {
                DocBank docBank = new DocBank();
                docBank.setId("建设银行");
                docBank.setName("建设银行");
                docBanks.add(docBank);
            }
            {
                DocBank docBank = new DocBank();
                docBank.setId("中国银行");
                docBank.setName("中国银行");
                docBanks.add(docBank);
            }
            {
                DocBank docBank = new DocBank();
                docBank.setId("工商银行");
                docBank.setName("工商银行");
                docBanks.add(docBank);
            }
        }
        {
            {
                DocBankCard docBankCard = new DocBankCard();
                docBankCard.setId("小明工商");
                docBankCard.setType("储蓄卡");
                docBankCard.setUid("小明id");
                docBankCard.setBankId("工商银行");
                docBankCards.add(docBankCard);
            }
            {
                DocBankCard docBankCard = new DocBankCard();
                docBankCard.setId("小红建设");
                docBankCard.setType("储蓄卡");
                docBankCard.setUid("小红id");
                docBankCard.setBankId("建设银行");
                docBankCards.add(docBankCard);
            }
        }
        SysUser sysUser = new SysUser();
        sysUser.setId("title0");
        entityQuery.insertable(sysUser).executeRows();
        entityQuery.insertable(docUsers).executeRows();
        entityQuery.insertable(docBanks).executeRows();
        entityQuery.insertable(docBankCards).executeRows();
    }

}
