package com.easy.query.test.mysql8;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.DefaultEasyProxyQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.api.proxy.client.EasyProxyQuery;
import com.easy.query.api4j.client.DefaultEasyQuery;
import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.entity.EntityMappingRule;
import com.easy.query.core.basic.entity.PropertyFirstEntityMappingRule;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.EasyQueryShardingOption;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.configuration.ShardingDataSource;
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
import com.easy.query.test.parser.MyLambdaParser;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * create time 2025/3/4 08:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class BaseTest {
    public static HikariDataSource dataSource;
    public static EasyQueryClient easyQueryClient;
    public static EasyQuery easyQuery;
    public static EasyEntityQuery easyEntityQuery;
    public static ListenerContextManager listenerContextManager;

    static {
//        EasyBeanUtil.FAST_BEAN_FUNCTION = ReflectBean::new;
        LogFactory.useStdOutLogging();
        init();


    }


    public static void init() {
        EasyLambdaUtil.replaceParser(new MyLambdaParser());
        initDatasource();
        initEasyQuery();
    }

    public static void initDatasource() {

        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3316/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
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
                })
//                .replaceService(Column2MapKeyConversion.class, UpperColumn2MapKeyConversion.class)
                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
//                .replaceService(Dialect.class, DefaultDialect.class)
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
                .replaceService(QueryConfiguration.class, MyQueryConfiguration.class)
//                .replaceService(EntityMappingRule.class, PropertyEntityMappingRule.class)
                .replaceService(EntityMappingRule.class, PropertyFirstEntityMappingRule.class)
//                .replaceService(EasyPageResultProvider.class,MyEasyPageResultProvider.class)
//                .replaceService(SQLKeyword.class, DefaultSQLKeyword.class)
//                .replaceService(BeanValueCaller.class, ReflectBeanValueCaller.class)
                .build();
        easyQuery = new DefaultEasyQuery(easyQueryClient);
        easyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);

    }



}
