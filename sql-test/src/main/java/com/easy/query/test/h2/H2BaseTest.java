package com.easy.query.test.h2;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.ShardingEntityExpressionExecutor;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.EasyQueryShardingOption;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.configuration.dialect.DefaultSQLKeyword;
import com.easy.query.core.configuration.dialect.SQLKeyword;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.sharding.router.manager.TableRouteManager;
import com.easy.query.h2.config.H2DatabaseConfiguration;
import com.easy.query.h2.config.H2SQLKeyword;
import com.easy.query.test.h2.domain.ALLTYPE;
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
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

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
    public static EasyEntityQuery easyEntityQuery;
    public static EasyEntityQuery easyEntityQuery2;
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
                .replaceService(EntityExpressionExecutor.class, ShardingEntityExpressionExecutor.class)
                .useDatabaseConfigure(new H2DatabaseConfiguration())
                .replaceService(SQLKeyword.class, DefaultSQLKeyword.class)
                .build();
        easyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);
        QueryRuntimeContext runtimeContext1 = easyEntityQuery.getRuntimeContext();
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
                }).replaceService(EntityExpressionExecutor.class, ShardingEntityExpressionExecutor.class)
                .build();
        QueryRuntimeContext runtimeContext = easyQueryClientOrder.getRuntimeContext();
        QueryConfiguration queryConfiguration = runtimeContext.getQueryConfiguration();
        easyEntityQuery2 = new DefaultEasyEntityQuery(easyQueryClientOrder);
        queryConfiguration.applyShardingInitializer(new H2OrderShardingInitializer());
        TableRouteManager tableRouteManager = runtimeContext.getTableRouteManager();
        tableRouteManager.addRoute(new H2OrderRoute());
    }

    public static void initData() {
        boolean any = easyEntityQuery.queryable(DefTable.class)
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
            easyEntityQuery.insertable(defTables).executeRows();
            easyEntityQuery.insertable(defTable1s).executeRows();
            easyEntityQuery.insertable(defTable2s).executeRows();
            easyEntityQuery.insertable(defTable3s).executeRows();
        }


        boolean orderExists = easyEntityQuery2.queryable(H2Order.class)
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
            easyEntityQuery2.insertable(h2Orders).executeRows();
        }


        ALLTYPE alltype = new ALLTYPE();
        alltype.setId("123xxxxxxx1qq");

        alltype.setNumberDecimal(new BigDecimal("12.33"));
        alltype.setNumberFloat(12.3f);
        alltype.setNumberDouble(22.1d);
        alltype.setNumberShort(new Short("12"));
        alltype.setNumberInteger(33);
        alltype.setNumberLong(12345678911L);
        alltype.setNumberByte(new Byte("-1"));
        alltype.setEnable(true);
        alltype.setTimeLocalDateTime(LocalDateTime.of(2021, 1, 1, 0, 0));
        alltype.setTimeLocalDate(LocalDate.of(2121, 1, 2));
        alltype.setTimeLocalTime(LocalTime.of(21, 1, 9));
        alltype.setOnlyDate(new Date());
        long epochMilli = LocalDateTime.now().toLocalDate().atStartOfDay()
                .toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        java.sql.Date date = new java.sql.Date(epochMilli);
        alltype.setSqlDate(date);
        alltype.setOnlyTime(Time.valueOf("12:09:10"));
        alltype.setValue("3322");
        alltype.setUid(UUID.randomUUID());
        alltype.setNumberFloatBasic(12.3f);
        alltype.setNumberDoubleBasic(22.1d);
        alltype.setNumberShortBasic(new Short("12"));
        alltype.setNumberIntegerBasic(33);
        alltype.setNumberLongBasic(12345678911L);
        alltype.setNumberByteBasic(new Byte("-1"));
        alltype.setEnableBasic(true);
        long l = easyEntityQuery.insertable(alltype).executeRows();
    }

}
