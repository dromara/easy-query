package com.easy.query.test;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.cache.core.EasyCacheClient;
import com.easy.query.cache.core.bootstrapper.EasyCacheBootstrapper;
import com.easy.query.cache.core.manager.EasyCacheManager;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.entity.EntityMappingRule;
import com.easy.query.core.basic.entity.PropertyFirstEntityMappingRule;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.EasyQueryShardingOption;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.configuration.ShardingDataSource;
import com.easy.query.core.configuration.bean.PropertyDescriptorMatcher;
import com.easy.query.core.configuration.bean.entity.EntityPropertyDescriptorMatcher;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.test.cache.BlogPredicateInterceptor;
import com.easy.query.test.cache.CacheMultiOption;
import com.easy.query.test.cache.DefaultCacheManager;
import com.easy.query.test.common.MyQueryConfiguration;
import com.easy.query.test.common.TopicUpdateInterceptor;
import com.easy.query.test.conversion.Blog2StarToStringColumnValueSQLConverter;
import com.easy.query.test.conversion.CertStatusColumnValueSQLConverter;
import com.easy.query.test.conversion.EnumConverter;
import com.easy.query.test.conversion.EnumValueConverter;
import com.easy.query.test.conversion.FullNameColumnValueSQLConverter;
import com.easy.query.test.conversion.JavaEncryptionStrategy;
import com.easy.query.test.conversion.JsonConverter;
import com.easy.query.test.conversion.MySQLAesEncrypt2ColumnValueSQLConverter;
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
import com.easy.query.test.entity.SysUserEncrypt;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicAuto;
import com.easy.query.test.entity.TopicInterceptor;
import com.easy.query.test.entity.m2m.UserAccount;
import com.easy.query.test.entity.m2m.UserBook;
import com.easy.query.test.entity.onrelation.OnRelationA;
import com.easy.query.test.entity.onrelation.OnRelationB;
import com.easy.query.test.entity.onrelation.OnRelationC;
import com.easy.query.test.entity.onrelation.OnRelationD;
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
import com.zaxxer.hikari.HikariDataSource;
import org.redisson.Redisson;
import org.redisson.api.NameMapper;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xuejiaming
 * @FileName: BaseTest.java
 * @Description: 基础单元测试类用于构建easy-query
 * create time 2023/3/16 16:47
 */
public abstract class BaseTest {
    public static HikariDataSource dataSource;
    public static EasyQueryClient easyQueryClient;
    public static EasyCacheClient easyCacheClient;
    public static EasyQueryShardingOption easyQueryShardingOption;
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
                    op.setDefaultDataSourceName("ds2020");
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
        configuration.applyInterceptor(new BlogPredicateInterceptor());
        configuration.applyNavigateExtraFilterStrategy(new BookNavigateExtraFilterStrategy());
        configuration.applyNavigateExtraFilterStrategy(new JoinType());
        configuration.applyNavigateExtraFilterStrategy(new RoleJoin.RoleJoinType());
        configuration.applyPrimaryKeyGenerator(new MyTestPrimaryKeyGenerator());
//        configuration.applyShardingInitializer(new FixShardingInitializer());
        configuration.applyValueConverter(new EnumConverter());
        configuration.applyValueConverter(new JsonConverter());
        configuration.applyValueConverter(new EnumValueConverter());
//        configuration.applyValueUpdateAtomicTrack(new IntegerNotNullValueUpdateAtomicTrack());
        configuration.applyColumnValueSQLConverter(new CertStatusColumnValueSQLConverter());
        configuration.applyColumnValueSQLConverter(new MySQLAesEncryptColumnValueSQLConverter());
        configuration.applyColumnValueSQLConverter(new MySQLAesEncrypt2ColumnValueSQLConverter());
        configuration.applyColumnValueSQLConverter(new FullNameColumnValueSQLConverter());
        configuration.applyColumnValueSQLConverter(new UserAgeColumnValueSQLConverter());
        configuration.applyColumnValueSQLConverter(new StudentSizeColumnValueSQLConverter());
        configuration.applyColumnValueSQLConverter(new Blog2StarToStringColumnValueSQLConverter());
        configuration.applyGeneratedKeySQLColumnGenerator(new MyDatabaseIncrementSQLColumnGenerator());
        configuration.applyNavigateValueSetter(new MyNavigateValueSetter());
        configuration.applyNavigateExtraFilterStrategy(new com.easy.query.test.entity.navf.RoleJoin.RoleJoinType());


        easyCacheClient = EasyCacheBootstrapper.defaultBuilderConfiguration()
                .optionConfigure(op -> {
                })
                .replaceService(new CacheMultiOption(1000 * 60 * 60, 1000, 10000))
                .replaceService(EasyCacheManager.class, DefaultCacheManager.class)
                .replaceService(EasyQueryClient.class, easyQueryClient)
                .replaceService(RedissonClient.class, cacheRedissonClient()).build();
        beforex();


        RedissonClient redissonClient = easyCacheClient.getService(RedissonClient.class);
        RKeys keys = redissonClient.getKeys();
        Iterable<String> keysByPattern = keys.getKeysByPattern("CACHE:*");
        for (String s : keysByPattern) {
            redissonClient.getBucket(s).delete();
        }

    }

    public static RedissonClient cacheRedissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setConnectionMinimumIdleSize(1)
                .setDatabase(1)
                .setAddress("redis://127.0.0.1:55001")
                .setNameMapper(createNameMapper("CACHE"));
        config.useSingleServer().setPassword("redispw");
        StringCodec codec = new StringCodec();
        config.setCodec(codec);
        return Redisson.create(config);
    }

    private static NameMapper createNameMapper(String prefix) {
        return new NameMapper() {
            public final String nameSpace = prefix + ":";

            @Override
            public String map(String name) {
                return nameSpace + name;
            }

            @Override
            public String unmap(String name) {
                return name.substring(nameSpace.length());
            }
        };
    }

    public static void initData() {
        easyEntityQuery.deletable(BlogEntity.class).where(o -> o.id().isNotBlank()).disableLogicDelete().allowDeleteStatement(true).executeRows();
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


        boolean topicAutoAny = easyEntityQuery.queryable(TopicAuto.class).any();
        if (!topicAutoAny) {
            List<TopicAuto> topicAutos = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                TopicAuto topicAuto = new TopicAuto();
                topicAuto.setStars(i);
                topicAuto.setTitle("title" + i);
                topicAuto.setCreateTime(LocalDateTime.now().plusDays(i));
                topicAutos.add(topicAuto);
            }
            long l = easyEntityQuery.insertable(topicAutos).executeRows(true);
        }


        boolean topicAny = easyEntityQuery.queryable(Topic.class).any();
        if (!topicAny) {
            List<Topic> topics = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                Topic topic = new Topic();
                topic.setId(String.valueOf(i));
                topic.setStars(i + 100);
                topic.setTitle("标题" + i);
                topic.setCreateTime(LocalDateTime.now().plusDays(i));
                topics.add(topic);
            }
            long l = easyEntityQuery.insertable(topics).executeRows();
        }

        boolean sysUserAny = easyEntityQuery.queryable(SysUser.class).any();
        if (!sysUserAny) {
            List<SysUser> sysUsers = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                SysUser sysUser = new SysUser();
                sysUser.setId(String.valueOf(i));
                sysUser.setUsername("username" + String.valueOf(i));
                sysUser.setCreateTime(LocalDateTime.now().plusDays(i));
                sysUser.setPhone("18888888" + String.valueOf(i) + String.valueOf(i));
                sysUser.setIdCard("333333333333333" + String.valueOf(i) + String.valueOf(i));
                sysUser.setAddress(sysUser.getPhone() + sysUser.getIdCard());
                sysUsers.add(sysUser);
            }
            long l = easyEntityQuery.insertable(sysUsers).executeRows();
        }
        boolean logicDeleteAny = easyEntityQuery.queryable(LogicDelTopic.class).any();
        if (!logicDeleteAny) {
            List<LogicDelTopic> logicDelTopics = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                LogicDelTopic logicDelTopic = new LogicDelTopic();
                logicDelTopic.setId(String.valueOf(i));
                logicDelTopic.setStars(i + 100);
                logicDelTopic.setTitle("标题" + i);
                logicDelTopic.setCreateTime(LocalDateTime.now().plusDays(i));
                logicDelTopic.setDeleted(false);
                logicDelTopics.add(logicDelTopic);
            }
            long l = easyEntityQuery.insertable(logicDelTopics).executeRows();
        }
        boolean logicDeleteCusAny = easyEntityQuery.queryable(LogicDelTopicCustom.class).any();
        if (!logicDeleteCusAny) {
            List<LogicDelTopicCustom> logicDelTopics = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                LogicDelTopicCustom logicDelTopic = new LogicDelTopicCustom();
                logicDelTopic.setId(String.valueOf(i));
                logicDelTopic.setStars(i + 100);
                logicDelTopic.setTitle("标题" + i);
                logicDelTopic.setCreateTime(LocalDateTime.now().plusDays(i));
                logicDelTopics.add(logicDelTopic);
            }
            long l = easyEntityQuery.insertable(logicDelTopics).executeRows();
        }
        boolean topicInterceptorAny = easyEntityQuery.queryable(TopicInterceptor.class).any();
        if (!topicInterceptorAny) {
            List<TopicInterceptor> topicInterceptors = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                TopicInterceptor topicInterceptor = new TopicInterceptor();
                topicInterceptor.setId(String.valueOf(i));
                topicInterceptor.setStars(i + 100);
                topicInterceptor.setTitle("标题" + i);
                topicInterceptor.setCreateTime(LocalDateTime.now().plusDays(i));
                topicInterceptor.setUpdateTime(LocalDateTime.now().plusDays(i));
                topicInterceptor.setCreateBy(String.valueOf(i));
                topicInterceptor.setUpdateBy(String.valueOf(i));
                topicInterceptor.setTenantId(String.valueOf(i));
                topicInterceptors.add(topicInterceptor);
            }
            long l = easyEntityQuery.insertable(topicInterceptors).executeRows();
        }
    }

    public static void beforex() {
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        {
            try {

                CodeFirstCommand codeFirstCommand = databaseCodeFirst.dropTableCommand(Arrays.asList(SysUserEncrypt.class,DocBankCard.class, DocBank.class, UserAccount.class, UserBook.class, DocUser.class, OnRelationA.class, OnRelationB.class, OnRelationC.class, OnRelationD.class));
                codeFirstCommand.executeWithTransaction(a -> a.commit());
            } catch (Exception ignored) {

            }

        }
        {
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(SysUserEncrypt.class,DocBank.class, UserAccount.class, UserBook.class, DocBankCard.class, DocUser.class, OnRelationA.class, OnRelationB.class, OnRelationC.class, OnRelationD.class));
            codeFirstCommand.executeWithTransaction(a -> a.commit());
        }
    }

}
