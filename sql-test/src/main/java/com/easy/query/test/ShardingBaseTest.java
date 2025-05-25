package com.easy.query.test;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.entity.EntityMappingRule;
import com.easy.query.core.basic.entity.PropertyFirstEntityMappingRule;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.ShardingEntityExpressionExecutor;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.EasyQueryShardingOption;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.configuration.ShardingDataSource;
import com.easy.query.core.configuration.bean.PropertyDescriptorMatcher;
import com.easy.query.core.configuration.bean.entity.EntityPropertyDescriptorMatcher;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.sharding.router.manager.DataSourceRouteManager;
import com.easy.query.core.sharding.router.manager.TableRouteManager;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.test.common.MyQueryConfiguration;
import com.easy.query.test.common.TopicUpdateInterceptor;
import com.easy.query.test.conversion.Blog2StarToStringColumnValueSQLConverter;
import com.easy.query.test.conversion.CertStatusColumnValueSQLConverter;
import com.easy.query.test.conversion.EnumConverter;
import com.easy.query.test.conversion.EnumValueConverter;
import com.easy.query.test.conversion.FullNameColumnValueSQLConverter;
import com.easy.query.test.conversion.JavaEncryptionStrategy;
import com.easy.query.test.conversion.JsonConverter;
import com.easy.query.test.conversion.MySQLAesEncryptColumnValueSQLConverter;
import com.easy.query.test.conversion.StudentSizeColumnValueSQLConverter;
import com.easy.query.test.conversion.UserAgeColumnValueSQLConverter;
import com.easy.query.test.doc.entity.DocBank;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.dto.autodto.MyNavigateValueSetter;
import com.easy.query.test.dto.autotest.RoleJoin;
import com.easy.query.test.encryption.Base64EncryptionStrategy;
import com.easy.query.test.encryption.DefaultAesEasyEncryptionStrategy;
import com.easy.query.test.encryption.MyEncryptionStrategy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.LogicDelTopic;
import com.easy.query.test.entity.LogicDelTopicCustom;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicAuto;
import com.easy.query.test.entity.TopicInterceptor;
import com.easy.query.test.entity.TopicSharding;
import com.easy.query.test.entity.TopicShardingDataSource;
import com.easy.query.test.entity.TopicShardingDataSourceTime;
import com.easy.query.test.entity.TopicShardingTime;
import com.easy.query.test.entity.m2m.UserAccount;
import com.easy.query.test.entity.m2m.UserBook;
import com.easy.query.test.entity.relation.BookNavigateExtraFilterStrategy;
import com.easy.query.test.entity.testrelation.JoinType;
import com.easy.query.test.increment.MyDatabaseIncrementSQLColumnGenerator;
import com.easy.query.test.interceptor.MyEntityInterceptor;
import com.easy.query.test.interceptor.MyTenantInterceptor;
import com.easy.query.test.interceptor.Topic1Interceptor;
import com.easy.query.test.keytest.MyTestPrimaryKeyGenerator;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import com.easy.query.test.logicdel.MyLogicDelStrategy;
import com.easy.query.test.sharding.DataSourceAndTableShardingInitializer;
import com.easy.query.test.sharding.DataSourceShardingInitializer;
import com.easy.query.test.sharding.TopicShardingDataSourceRoute;
import com.easy.query.test.sharding.TopicShardingDataSourceTimeDataSourceRoute;
import com.easy.query.test.sharding.TopicShardingDataSourceTimeTableRoute;
import com.easy.query.test.sharding.TopicShardingShardingInitializer;
import com.easy.query.test.sharding.TopicShardingTableRoute;
import com.easy.query.test.sharding.TopicShardingTimeShardingInitializer;
import com.easy.query.test.sharding.TopicShardingTimeTableRoute;
import com.zaxxer.hikari.HikariDataSource;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author xuejiaming
 * @FileName: BaseTest.java
 * @Description: 基础单元测试类用于构建easy-query
 * create time 2023/3/16 16:47
 */
public abstract class ShardingBaseTest {
    public static HikariDataSource dataSource;
    public static EasyQueryShardingOption easyQueryShardingOption;
    public static EasyQueryClient easyQueryClient;
    public static EasyEntityQuery easyEntityQuery;
    public static ListenerContextManager listenerContextManager;

    static {
//        EasyBeanUtil.FAST_BEAN_FUNCTION = ReflectBean::new;
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
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setMaximumPoolSize(20);

        Set<ShardingDataSource> shardingDataSources = new HashSet<>();
        {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/easy-query-test2021?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
            dataSource.setUsername("root");
            dataSource.setPassword("root");
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setMaximumPoolSize(20);
            shardingDataSources.add(new ShardingDataSource("ds2021", dataSource, 20));
        }
        {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/easy-query-test2022?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
            dataSource.setUsername("root");
            dataSource.setPassword("root");
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setMaximumPoolSize(20);
            shardingDataSources.add(new ShardingDataSource("ds2022", dataSource, 20));
        }
        {
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/easy-query-test2023?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
            dataSource.setUsername("root");
            dataSource.setPassword("root");
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setMaximumPoolSize(20);
            shardingDataSources.add(new ShardingDataSource("ds2023", dataSource, 20));
        }
        easyQueryShardingOption = new EasyQueryShardingOption(shardingDataSources);
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
                    op.setExecutorMaximumPoolSize(0);
                    op.setMaxShardingQueryLimit(10);
                    op.setShardingOption(easyQueryShardingOption);
                    op.setDefaultDataSourceName("ds2020");
                    op.setThrowIfRouteNotMatch(false);
                    op.setMaxShardingRouteCount(512);
                    op.setDefaultDataSourceMergePoolSize(20);
                    op.setStartTimeJob(true);
                    op.setReverseOffsetThreshold(10);
                })
//                .replaceService(Column2MapKeyConversion.class, UpperColumn2MapKeyConversion.class)
                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
//                .replaceService(Dialect.class, DefaultDialect.class)
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
                .replaceService(QueryConfiguration.class, MyQueryConfiguration.class)
//                .replaceService(EntityMappingRule.class, PropertyEntityMappingRule.class)
                .replaceService(EntityMappingRule.class, PropertyFirstEntityMappingRule.class)
                .replaceService(PropertyDescriptorMatcher.class, EntityPropertyDescriptorMatcher.class)
                .replaceService(EntityExpressionExecutor.class, ShardingEntityExpressionExecutor.class)
//                .replaceService(EasyPageResultProvider.class,MyEasyPageResultProvider.class)
//                .replaceService(SQLKeyword.class, DefaultSQLKeyword.class)
//                .replaceService(BeanValueCaller.class, ReflectBeanValueCaller.class)
                .build();
        easyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);
        QueryRuntimeContext runtimeContext = easyEntityQuery.getRuntimeContext();
        QueryConfiguration configuration = runtimeContext.getQueryConfiguration();
        configuration.applyEncryptionStrategy(new DefaultAesEasyEncryptionStrategy());
        configuration.applyEncryptionStrategy(new Base64EncryptionStrategy());
        configuration.applyEncryptionStrategy(new MyEncryptionStrategy());
        configuration.applyEncryptionStrategy(new JavaEncryptionStrategy());
        configuration.applyLogicDeleteStrategy(new MyLogicDelStrategy());
        configuration.applyInterceptor(new MyEntityInterceptor());
        configuration.applyInterceptor(new Topic1Interceptor());
        configuration.applyInterceptor(new MyTenantInterceptor());
        configuration.applyInterceptor(new TopicUpdateInterceptor());
        configuration.applyNavigateExtraFilterStrategy(new BookNavigateExtraFilterStrategy());
        configuration.applyNavigateExtraFilterStrategy(new JoinType());
        configuration.applyNavigateExtraFilterStrategy(new RoleJoin.RoleJoinType());
        configuration.applyPrimaryKeyGenerator(new MyTestPrimaryKeyGenerator());
//        configuration.applyShardingInitializer(new FixShardingInitializer());
        configuration.applyShardingInitializer(new DataSourceAndTableShardingInitializer());
        configuration.applyShardingInitializer(new TopicShardingShardingInitializer());
        configuration.applyShardingInitializer(new TopicShardingTimeShardingInitializer());
        configuration.applyShardingInitializer(new DataSourceShardingInitializer());
        configuration.applyValueConverter(new EnumConverter());
        configuration.applyValueConverter(new JsonConverter());
        configuration.applyValueConverter(new EnumValueConverter());
//        configuration.applyValueUpdateAtomicTrack(new IntegerNotNullValueUpdateAtomicTrack());
        configuration.applyColumnValueSQLConverter(new CertStatusColumnValueSQLConverter());
        configuration.applyColumnValueSQLConverter(new MySQLAesEncryptColumnValueSQLConverter());
        configuration.applyColumnValueSQLConverter(new FullNameColumnValueSQLConverter());
        configuration.applyColumnValueSQLConverter(new UserAgeColumnValueSQLConverter());
        configuration.applyColumnValueSQLConverter(new StudentSizeColumnValueSQLConverter());
        configuration.applyColumnValueSQLConverter(new Blog2StarToStringColumnValueSQLConverter());
        configuration.applyGeneratedKeySQLColumnGenerator(new MyDatabaseIncrementSQLColumnGenerator());
        configuration.applyNavigateValueSetter(new MyNavigateValueSetter());
        configuration.applyNavigateExtraFilterStrategy(new com.easy.query.test.entity.navf.RoleJoin.RoleJoinType());
        TableRouteManager tableRouteManager = runtimeContext.getTableRouteManager();
        tableRouteManager.addRoute(new TopicShardingTableRoute());
        tableRouteManager.addRoute(new TopicShardingTimeTableRoute());
        tableRouteManager.addRoute(new TopicShardingDataSourceTimeTableRoute());
        DataSourceRouteManager dataSourceRouteManager = runtimeContext.getDataSourceRouteManager();
        dataSourceRouteManager.addRoute(new TopicShardingDataSourceTimeDataSourceRoute());
        dataSourceRouteManager.addRoute(new TopicShardingDataSourceRoute());

        beforex();

    }

    public static void initData() {

        boolean topicShardingAny = easyEntityQuery.queryable(TopicSharding.class).where(o -> o.stars().le(1000)).any();
        if (!topicShardingAny) {

            ArrayList<TopicSharding> topicShardings = new ArrayList<>(500);
            for (int i = 0; i < 500; i++) {
                TopicSharding topicSharding = new TopicSharding();
                topicSharding.setId(String.valueOf(i));
                topicSharding.setTitle("title" + i);
                topicSharding.setStars(i);
                topicSharding.setCreateTime(LocalDateTime.now().plusMinutes(i));
                topicShardings.add(topicSharding);
            }

            long l = easyEntityQuery.insertable(topicShardings).executeRows();
        }
        boolean shardingTimeExists = easyEntityQuery.queryable(TopicShardingTime.class).any();
        if (!shardingTimeExists) {

            LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
            LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
            Duration between = Duration.between(beginTime, endTime);
            long days = between.toDays();
            ArrayList<TopicShardingTime> topicShardingTimes = new ArrayList<>(500);
            for (int i = 0; i < days; i++) {
                LocalDateTime now = beginTime.plusDays(i);
                String month = now.format(DateTimeFormatter.ofPattern("yyyyMM"));
                TopicShardingTime topicShardingTime = new TopicShardingTime();
                topicShardingTime.setId(UUID.randomUUID().toString().replaceAll("-", "") + month);
                topicShardingTime.setTitle("title" + month);
                topicShardingTime.setStars(i);
                topicShardingTime.setCreateTime(now);
                topicShardingTimes.add(topicShardingTime);
            }

            long l = easyEntityQuery.insertable(topicShardingTimes).executeRows();
            System.out.println("插入时间条数:" + l);
        }
        boolean shardingDataSourceTimeExists = easyEntityQuery.queryable(TopicShardingDataSourceTime.class).any();
        System.out.println(shardingDataSourceTimeExists);
        if (!shardingDataSourceTimeExists) {

            LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
            LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
            Duration between = Duration.between(beginTime, endTime);
            long days = between.toDays();
            ArrayList<TopicShardingDataSourceTime> topicShardingDataSourceTimes = new ArrayList<>(500);
            for (int i = 0; i < days; i++) {
                LocalDateTime now = beginTime.plusDays(i);
                String month = now.format(DateTimeFormatter.ofPattern("yyyyMM"));
                TopicShardingDataSourceTime topicShardingDataSourceTime = new TopicShardingDataSourceTime();
                topicShardingDataSourceTime.setId(UUID.randomUUID().toString().replaceAll("-", "") + month);
                topicShardingDataSourceTime.setTitle("title" + month);
                topicShardingDataSourceTime.setStars(i);
                topicShardingDataSourceTime.setCreateTime(now);
                topicShardingDataSourceTimes.add(topicShardingDataSourceTime);
            }

            long l = easyEntityQuery.insertable(topicShardingDataSourceTimes).executeRows();
            System.out.println("插入时间条数:" + l);
        }

        boolean any1 = easyEntityQuery.queryable(TopicShardingDataSource.class)
                .any();
        if (!any1) {

            LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
            LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);
            Duration between = Duration.between(beginTime, endTime);
            long days = between.toDays();
            ArrayList<TopicShardingDataSource> topicShardingDataSources = new ArrayList<>(500);
            for (int i = 0; i < days; i++) {
                LocalDateTime now = beginTime.plusDays(i);
                String month = now.format(DateTimeFormatter.ofPattern("yyyyMM"));
                TopicShardingDataSource topicShardingDataSource = new TopicShardingDataSource();
                topicShardingDataSource.setId(UUID.randomUUID().toString().replaceAll("-", "") + month);
                topicShardingDataSource.setTitle("title" + month);
                topicShardingDataSource.setStars(i);
                topicShardingDataSource.setCreateTime(now);
                topicShardingDataSources.add(topicShardingDataSource);
            }

            long l = easyEntityQuery.insertable(topicShardingDataSources).executeRows();
        }
    }
    public static void beforex(){
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        {
            try {

                CodeFirstCommand codeFirstCommand = databaseCodeFirst.dropTableCommand(Arrays.asList(DocBankCard.class,DocBank.class,UserAccount.class, UserBook.class, DocUser.class));
                codeFirstCommand.executeWithTransaction(a->a.commit());
            }catch (Exception ignored){

            }

        }
        {
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(DocBank.class,UserAccount.class, UserBook.class,DocBankCard.class, DocUser.class));
            codeFirstCommand.executeWithTransaction(a->a.commit());
        }
    }

}
