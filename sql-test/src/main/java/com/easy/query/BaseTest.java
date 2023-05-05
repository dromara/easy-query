package com.easy.query;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.api.client.EasyQuery;
import com.easy.query.core.sql.dialect.Dialect;
import com.easy.query.core.sql.dialect.impl.MySqlDialect;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.sharding.route.abstraction.TableRouteManager;
import com.easy.query.encryption.Base64EncryptionStrategy;
import com.easy.query.encryption.DefaultAesEasyEncryptionStrategy;
import com.easy.query.encryption.MyEncryptionStrategy;
import com.easy.query.entity.BlogEntity;
import com.easy.query.entity.LogicDelTopic;
import com.easy.query.entity.LogicDelTopicCustom;
import com.easy.query.entity.SysUser;
import com.easy.query.entity.Topic;
import com.easy.query.entity.TopicAuto;
import com.easy.query.entity.TopicInterceptor;
import com.easy.query.entity.TopicSharding;
import com.easy.query.interceptor.MyEntityInterceptor;
import com.easy.query.interceptor.MyTenantInterceptor;
import com.easy.query.logicdel.MyLogicDelStrategy;
import com.easy.query.sharding.FixShardingInitializer;
import com.easy.query.sharding.TopicShardingTableRule;
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
        easyQuery = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDataSource(dataSource)
                .replaceService(Dialect.class,MySqlDialect.class)
                .build();
        EasyQueryRuntimeContext runtimeContext = easyQuery.getRuntimeContext();
        EasyQueryConfiguration configuration = runtimeContext.getEasyQueryConfiguration();
        configuration.applyEasyEncryptionStrategy(new DefaultAesEasyEncryptionStrategy());
        configuration.applyEasyEncryptionStrategy(new Base64EncryptionStrategy());
        configuration.applyEasyEncryptionStrategy(new MyEncryptionStrategy());
        configuration.applyEasyLogicDeleteStrategy(new MyLogicDelStrategy());
        configuration.applyEasyInterceptor(new MyEntityInterceptor());
        configuration.applyEasyInterceptor(new MyTenantInterceptor());
        configuration.applyShardingInitializer(new FixShardingInitializer());

        TableRouteManager tableRouteManager = runtimeContext.getTableRouteManager();
        tableRouteManager.addRouteRule(new TopicShardingTableRule());

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
        boolean topicInterceptorAny = easyQuery.queryable(TopicInterceptor.class).any();
        if(!topicInterceptorAny){
            List<TopicInterceptor> topicInterceptors = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                TopicInterceptor topicInterceptor = new TopicInterceptor();
                topicInterceptor.setId(String.valueOf(i));
                topicInterceptor.setStars(i + 100);
                topicInterceptor.setTitle("标题" + i);
                topicInterceptor.setCreateTime(LocalDateTime.now().plusDays(i));
                topicInterceptor.setUpdateTime(LocalDateTime.now().plusDays(i));
                topicInterceptor.setCreateBy(i+"");
                topicInterceptor.setUpdateBy(i+"");
                topicInterceptor.setTenantId(i+"");
                topicInterceptors.add(topicInterceptor);
            }
            long l = easyQuery.insertable(topicInterceptors).executeRows();
        }
        boolean topicShardingAny = easyQuery.queryable(TopicSharding.class).where(o->o.le(TopicSharding::getStars,1000)).any();
       if(!topicShardingAny){

           ArrayList<TopicSharding> topicShardings = new ArrayList<>(500);
           for (int i = 0; i < 500; i++) {
               TopicSharding topicSharding = new TopicSharding();
               topicSharding.setId(String.valueOf(i));
               topicSharding.setTitle("title" + i);
               topicSharding.setStars(i);
               topicSharding.setCreateTime(LocalDateTime.now().plusMinutes(i));
               topicShardings.add(topicSharding);
           }

           long l = easyQuery.insertable(topicShardings).executeRows();
       }
    }


}
