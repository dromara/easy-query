package com.easy.query;

import com.easy.query.core.abstraction.DefaultEasyQueryLambdaFactory;
import com.easy.query.core.abstraction.DefaultEasyQueryRuntimeContext;
import com.easy.query.core.abstraction.EasyQueryLambdaFactory;
import com.easy.query.core.abstraction.EasySqlApiFactory;
import com.easy.query.core.basic.pagination.DefaultEasyPageResultProvider;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.api.client.DefaultEasyQuery;
import com.easy.query.core.api.client.EasyQuery;
import com.easy.query.core.api.def.DefaultEasySqlApiFactory;
import com.easy.query.core.basic.jdbc.con.DefaultConnectionManager;
import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.executor.DefaultEasyExecutor;
import com.easy.query.core.basic.jdbc.types.DefaultJdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.EasyJdbcTypeHandlerManager;
import com.easy.query.core.config.UnderlinedNameConversion;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.metadata.DefaultEntityMetadataManager;
import com.easy.query.core.basic.plugin.track.DefaultTrackManager;
import com.easy.query.encryption.DefaultAesEasyEncryptionStrategy;
import com.easy.query.entity.BlogEntity;
import com.easy.query.entity.LogicDelTopic;
import com.easy.query.entity.LogicDelTopicCustom;
import com.easy.query.entity.SysUser;
import com.easy.query.entity.Topic;
import com.easy.query.entity.TopicAuto;
import com.easy.query.logicdel.MyLogicDelStrategy;
import com.easy.query.mysql.MySqlExpressionFactory;
import com.easy.query.mysql.config.MySqlDialect;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.BeforeClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @FileName: BaseTest.java
 * @Description: 基础单元测试类用于构建easy-query
 * @Date: 2023/3/16 16:47
 * @author xuejiaming
 */
public abstract class BaseTest {
    public static HikariDataSource dataSource;
    public static EasyQuery easyQuery;

    static {
        LogFactory.useStdOutLogging();
    }


    @BeforeClass
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

    }

    public static void initEasyQuery() {
        EasyConnectionManager connectionManager = new DefaultConnectionManager(dataSource);
        DefaultEasyExecutor defaultExecutor = new DefaultEasyExecutor();
        EasyJdbcTypeHandlerManager jdbcTypeHandler = new DefaultJdbcTypeHandlerManager();
        EasyQueryConfiguration configuration = new EasyQueryConfiguration(false);
        configuration.setNameConversion(new UnderlinedNameConversion());
        configuration.setDialect(new MySqlDialect());
        configuration.applyEasyEncryptionStrategy(new DefaultAesEasyEncryptionStrategy());
        configuration.applyEasyLogicDeleteStrategy(new MyLogicDelStrategy());
        EntityMetadataManager entityMetadataManager = new DefaultEntityMetadataManager(configuration);
        EasyQueryLambdaFactory easyQueryLambdaFactory = new DefaultEasyQueryLambdaFactory();
        MySqlExpressionFactory mySQLSqlExpressionFactory = new MySqlExpressionFactory();
        EasySqlApiFactory easyQueryableFactory = new DefaultEasySqlApiFactory(mySQLSqlExpressionFactory);
        DefaultTrackManager defaultTrackManager = new DefaultTrackManager(entityMetadataManager);
        DefaultEasyPageResultProvider defaultEasyPageResultProvider = new DefaultEasyPageResultProvider();
        DefaultEasyQueryRuntimeContext jqdcRuntimeContext = new DefaultEasyQueryRuntimeContext(configuration, entityMetadataManager, easyQueryLambdaFactory, connectionManager, defaultExecutor, jdbcTypeHandler, easyQueryableFactory, mySQLSqlExpressionFactory, defaultTrackManager,defaultEasyPageResultProvider);
////        jqdcRuntimeContext.getEasyQueryConfiguration().applyEntityTypeConfiguration(new TestUserMySqlConfiguration());
//        configuration.applyGlobalInterceptor(new NameQueryFilter());

        easyQuery = new DefaultEasyQuery(jqdcRuntimeContext);
    }

    public static void initData() {

        boolean any = easyQuery.queryable(BlogEntity.class).any();
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
            easyQuery.insertable(blogs).executeRows();
        }


        boolean topicAutoAny = easyQuery.queryable(TopicAuto.class).any();
        if (!topicAutoAny) {
            List<TopicAuto> topicAutos = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                TopicAuto topicAuto = new TopicAuto();
                topicAuto.setStars(i);
                topicAuto.setTitle("title" + i);
                topicAuto.setCreateTime(LocalDateTime.now().plusDays(i));
                topicAutos.add(topicAuto);
            }
            long l = easyQuery.insertable(topicAutos).executeRows(true);
        }


        boolean topicAny = easyQuery.queryable(Topic.class).any();
        if(!topicAny){
            List<Topic> topics = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                Topic topic = new Topic();
                topic.setId(String.valueOf(i));
                topic.setStars(i + 100);
                topic.setTitle("标题" + i);
                topic.setCreateTime(LocalDateTime.now().plusDays(i));
                topics.add(topic);
            }
            long l = easyQuery.insertable(topics).executeRows();
        }

        boolean sysUserAny = easyQuery.queryable(SysUser.class).any();
        if(!sysUserAny){
            List<SysUser> sysUsers = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                SysUser sysUser = new SysUser();
                sysUser.setId(String.valueOf(i));
                sysUser.setUsername("username"+String.valueOf(i));
                sysUser.setCreateTime(LocalDateTime.now().plusDays(i));
                sysUser.setPhone("18888888"+String.valueOf(i)+String.valueOf(i));
                sysUser.setIdCard("333333333333333"+String.valueOf(i)+String.valueOf(i));
                sysUser.setAddress(sysUser.getPhone()+sysUser.getIdCard());
                sysUsers.add(sysUser);
            }
            long l = easyQuery.insertable(sysUsers).executeRows();
        }
        boolean logicDeleteAny = easyQuery.queryable(LogicDelTopic.class).any();
        if(!logicDeleteAny){
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
            long l = easyQuery.insertable(logicDelTopics).executeRows();
        }
        boolean logicDeleteCusAny = easyQuery.queryable(LogicDelTopicCustom.class).any();
        if(!logicDeleteCusAny){
            List<LogicDelTopicCustom> logicDelTopics = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                LogicDelTopicCustom logicDelTopic = new LogicDelTopicCustom();
                logicDelTopic.setId(String.valueOf(i));
                logicDelTopic.setStars(i + 100);
                logicDelTopic.setTitle("标题" + i);
                logicDelTopic.setCreateTime(LocalDateTime.now().plusDays(i));
                logicDelTopics.add(logicDelTopic);
            }
            long l = easyQuery.insertable(logicDelTopics).executeRows();
        }
    }


}
