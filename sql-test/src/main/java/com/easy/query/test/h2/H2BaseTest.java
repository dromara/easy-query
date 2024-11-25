package com.easy.query.test.h2;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.DefaultEasyProxyQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.api.proxy.client.EasyProxyQuery;
import com.easy.query.api4j.client.DefaultEasyQuery;
import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.EasyQueryShardingOption;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.sharding.router.manager.TableRouteManager;
import com.easy.query.h2.config.H2DatabaseConfiguration;
import com.easy.query.test.h2.domain.DefTable;
import com.easy.query.test.h2.domain.DefTableLeft1;
import com.easy.query.test.h2.domain.DefTableLeft2;
import com.easy.query.test.h2.domain.DefTableLeft3;
import com.easy.query.test.h2.domain.H2Order;
import com.easy.query.test.h2.domain.TbOrder;
import com.easy.query.test.h2.sharding.AllTYPEShardingInitializer;
import com.easy.query.test.h2.sharding.AllTypeRoute;
import com.easy.query.test.h2.sharding.H2OrderRoute;
import com.easy.query.test.h2.sharding.H2OrderShardingInitializer;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * create time 2023/5/22 21:46
 * 文件说明
 *
 * @author xuejiaming
 */
public class H2BaseTest {
    public static DataSource defDataSource;
    public static DataSource orderShardingDataSource;
    public static EasyQueryShardingOption easyQueryShardingOption;
    public static EasyQuery easyQuery;
    public static EasyEntityQuery easyEntityQuery;
    public static EasyProxyQuery easyProxyQuery;
    public static EasyQuery easyQueryOrder;
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
        defDataSource = DataSourceFactory.getDataSource("ds", "h2-ds2020.sql");
        orderShardingDataSource = DataSourceFactory.getDataSource("dsorder", "h2-dsorder.sql");
//        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
//        dataSource.setUsername("root");
//        dataSource.setPassword("root");
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setMaximumPoolSize(20);
//        Set<ShardingDataSource> shardingDataSources = new HashSet<>();
//        {
//            HikariDataSource dataSource = new HikariDataSource();
//            dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/easy-query-test2021?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
//            dataSource.setUsername("root");
//            dataSource.setPassword("root");
//            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//            dataSource.setMaximumPoolSize(20);
//            shardingDataSources.add(new ShardingDataSource("ds2021", dataSource, 20));
//        }
//        {
//            HikariDataSource dataSource = new HikariDataSource();
//            dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/easy-query-test2022?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
//            dataSource.setUsername("root");
//            dataSource.setPassword("root");
//            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//            dataSource.setMaximumPoolSize(20);
//            shardingDataSources.add(new ShardingDataSource("ds2022", dataSource, 20));
//        }
//        {
//            HikariDataSource dataSource = new HikariDataSource();
//            dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/easy-query-test2023?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
//            dataSource.setUsername("root");
//            dataSource.setPassword("root");
//            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//            dataSource.setMaximumPoolSize(20);
//            shardingDataSources.add(new ShardingDataSource("ds2023", dataSource, 20));
//        }
//        easyQueryShardingOption = new EasyQueryShardingOption(shardingDataSources);
//        postgres://postgres:postgrespw@localhost:55000
    }

    public static void initEasyQuery() {
        listenerContextManager = new ListenerContextManager();
        MyJdbcListener myJdbcListener = new MyJdbcListener(listenerContextManager);
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(defDataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                    op.setExecutorCorePoolSize(1);
                    op.setExecutorMaximumPoolSize(0);
                    op.setMaxShardingQueryLimit(10);
                    op.setShardingOption(easyQueryShardingOption);
                    op.setDefaultDataSourceName("ds2020");
                    op.setThrowIfRouteNotMatch(false);
                    op.setMaxShardingRouteCount(512);
                    op.setDefaultDataSourceMergePoolSize(20);
                })
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
                .useDatabaseConfigure(new H2DatabaseConfiguration())
                .build();
        easyQuery = new DefaultEasyQuery(easyQueryClient);
        easyProxyQuery=new DefaultEasyProxyQuery(easyQueryClient);
        easyEntityQuery=new DefaultEasyEntityQuery(easyQueryClient);
        QueryRuntimeContext runtimeContext1 = easyQuery.getRuntimeContext();
        QueryConfiguration queryConfiguration1 = runtimeContext1.getQueryConfiguration();
        queryConfiguration1.applyShardingInitializer(new AllTYPEShardingInitializer());
        queryConfiguration1.applyNavigateExtraFilterStrategy(new TbOrder.NameXMTbAccountExtraFilter());

        TableRouteManager tableRouteManager1 = runtimeContext1.getTableRouteManager();
        tableRouteManager1.addRoute(new AllTypeRoute());
        EasyQueryClient easyQueryClientOrder = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(orderShardingDataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                    op.setExecutorCorePoolSize(1);
                    op.setExecutorMaximumPoolSize(0);
                    op.setMaxShardingQueryLimit(10);
                    op.setShardingOption(easyQueryShardingOption);
                    op.setDefaultDataSourceName("ds2020");
                    op.setThrowIfRouteNotMatch(false);
                    op.setMaxShardingRouteCount(512);
                    op.setDefaultDataSourceMergePoolSize(20);
                })
                .build();
        easyQueryOrder = new DefaultEasyQuery(easyQueryClientOrder);
        QueryRuntimeContext runtimeContext = easyQueryOrder.getRuntimeContext();
        QueryConfiguration queryConfiguration = runtimeContext.getQueryConfiguration();
        queryConfiguration.applyShardingInitializer(new H2OrderShardingInitializer());
        TableRouteManager tableRouteManager = runtimeContext.getTableRouteManager();
        tableRouteManager.addRoute(new H2OrderRoute());
    }

    public static void initData() {
        boolean any = easyQuery.queryable(DefTable.class)
                .any();
        if (!any) {
            LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
            ArrayList<DefTable> defTables = new ArrayList<>(1000);
            ArrayList<DefTableLeft1> defTable1s = new ArrayList<>(1000);
            ArrayList<DefTableLeft2> defTable2s = new ArrayList<>(1000);
            ArrayList<DefTableLeft3> defTable3s = new ArrayList<>(1000);
            for (int i = 0; i < 1000; i++) {
                DefTable defTable = new DefTable();
                defTable.setId(String.valueOf(i));
                defTable.setUserName("username" + i);
                defTable.setNickname("nickname" + i);
                defTable.setEnable(i % 2 == 0);
                defTable.setScore(BigDecimal.valueOf(i + 0.1d));
                defTable.setMobile("133" + i);
                defTable.setAvatar("http://www." + i + ".com");
                defTable.setNumber(i);
                defTable.setStatus(i);
                defTable.setCreated(begin.plusDays(i));
                defTables.add(defTable);
                DefTableLeft1 defTableLeft1 = new DefTableLeft1();
                defTableLeft1.setId(String.valueOf(i));
                defTableLeft1.setDefId(defTable.getId());
                defTableLeft1.setUserName("username" + i);
                defTableLeft1.setNickname("nickname" + i);
                defTableLeft1.setEnable(i % 2 == 0);
                defTableLeft1.setScore(BigDecimal.valueOf(i + 0.1d));
                defTableLeft1.setMobile("133" + i);
                defTableLeft1.setAvatar("http://www." + i + ".com");
                defTableLeft1.setNumber(i);
                defTableLeft1.setStatus(i);
                defTableLeft1.setCreated(begin.plusDays(i));
                defTable1s.add(defTableLeft1);
                DefTableLeft2 defTableLeft2 = new DefTableLeft2();
                defTableLeft2.setId(String.valueOf(i));
                defTableLeft2.setDefId(defTable.getId());
                defTableLeft2.setDef1Id(defTableLeft1.getId());
                defTableLeft2.setUserName("username" + i);
                defTableLeft2.setNickname("nickname" + i);
                defTableLeft2.setEnable(i % 2 == 0);
                defTableLeft2.setScore(BigDecimal.valueOf(i + 0.1d));
                defTableLeft2.setMobile("133" + i);
                defTableLeft2.setAvatar("http://www." + i + ".com");
                defTableLeft2.setNumber(i);
                defTableLeft2.setStatus(i);
                defTableLeft2.setCreated(begin.plusDays(i));
                defTable2s.add(defTableLeft2);
                DefTableLeft3 defTableLeft3 = new DefTableLeft3();
                defTableLeft3.setId(String.valueOf(i));
                defTableLeft3.setDefId(defTable.getId());
                defTableLeft3.setDef1Id(defTableLeft1.getId());
                defTableLeft3.setDef2Id(defTableLeft2.getId());
                defTableLeft3.setUserName("username" + i);
                defTableLeft3.setNickname("nickname" + i);
                defTableLeft3.setEnable(i % 2 == 0);
                defTableLeft3.setScore(BigDecimal.valueOf(i + 0.1d));
                defTableLeft3.setMobile("133" + i);
                defTableLeft3.setAvatar("http://www." + i + ".com");
                defTableLeft3.setNumber(i);
                defTableLeft3.setStatus(i);
                defTableLeft3.setCreated(begin.plusDays(i));
                defTable3s.add(defTableLeft3);
            }
            easyQuery.insertable(defTables).executeRows();
            easyQuery.insertable(defTable1s).executeRows();
            easyQuery.insertable(defTable2s).executeRows();
            easyQuery.insertable(defTable3s).executeRows();
        }


        boolean orderExists = easyQueryOrder.queryable(H2Order.class)
                .any();
        if (!orderExists) {

            ArrayList<H2Order> h2Orders = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                H2Order h2Order = new H2Order();
                h2Order.setId(i);
                h2Order.setStatus(i % 3);
                h2Order.setCreated(String.valueOf(i));
                h2Orders.add(h2Order);
            }
            easyQueryOrder.insertable(h2Orders).executeRows();
        }

    }

}
