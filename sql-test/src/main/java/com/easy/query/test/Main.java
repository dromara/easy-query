package com.easy.query.test;


import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.easy.query.api.proxy.client.EasyProxyQuery;
import com.easy.query.api4j.client.DefaultEasyQuery;
import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.api4j.select.Queryable;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.common.bean.FastBeanProperty;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.util.EasyBeanUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.test.dto.TopicRequest;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.company.ValueCompany1Proxy;
import com.easy.query.test.mytest.SysUserLogbyMonth;
import com.easy.query.test.mytest.TestUserMysql;
import com.easy.query.test.mytest.TestUserMysqlGroup;
import com.easy.query.test.mytest.TestUserMysqlx;

import javax.sql.DataSource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;


public class Main {

    public static <TR> void action(TR a, TR b) {

    }

    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String username = "root";
    private static final String password = "root";
    //    private static final String url = "jdbc:mysql://127.0.0.1:3306/dbdbd0?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false";
    private static final String url = "jdbc:mysql://127.0.0.1:3306/dbdbd0?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true";
    private static EasyQuery easyQuery;
    private static EasyProxyQuery easyProxyQuery;

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String fullName = EasyClassUtil.getFullName(Boolean.class);
        String fullName1 = EasyClassUtil.getFullName(boolean.class);
        LocalDateTime localDateTime1 = LocalDateTime.of(1900, 1, 1, 0, 0).plusDays(45245);
        ValueCompany1Proxy table = ValueCompany1Proxy.createTable();
        int ixx=1- 1 % 1000;
        StringBuilder sqlx=new StringBuilder();
        for (int i = 10000; i < 20000; i++) {
            sqlx.append("\n insert into  sys_user values (").append(i).append(",'用户三');");
        }
        String string = sqlx.toString();
        List<String> list = new ArrayList<>(); // 假设列表为字符串类型的示例
        String name = Main.class.getName();
        Class<?> aClass = Class.forName(name);
        Object o1 = aClass.newInstance();
// 添加列表元素
        list.add("元素1");
        list.add("元素2");
        list.add("元素3");
        list.add("元素4");

// 获取数据集（排除第一个元素）
        List<String> dataSet = new ArrayList<>(list.subList(1, list.size()));

// 打印数据集
        for (String data : dataSet) {
            System.out.println(data);
        }

//        int[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
//        HashSet<String> strings = new HashSet<>(Arrays.asList("id","title","createTime","isTop","top"));
//        BlogEntity xaa=   new BlogEntity();
//        xaa.setId("123");
//        xaa.setTitle("456");
//        xaa.setCreateTime(LocalDateTime.now());
//        xaa.setIsTop(false);
//        xaa.setTop(true);
//        {
//            long start = System.currentTimeMillis();
//
//            for (int i = 0; i < 100000; i++) {
//                BlogEntity y=   new BlogEntity();
//                BeanUtil.copyProperties(xaa,y,strings);
//            }
//            long end = System.currentTimeMillis();
//            System.out.println("耗时：" + (end - start) + "ms");
//
//        }
//        {
//            long start = System.currentTimeMillis();
//
//            for (int i = 0; i < 100000; i++) {
//                BlogEntity y= (BlogEntity)BeanUtil.copyProperties1(xaa);
//            }
//            long end = System.currentTimeMillis();
//            System.out.println("耗时：" + (end - start) + "ms");
//        }

        boolean openFirst1 = SQLRangeEnum.openFirst(SQLRangeEnum.OPEN);
        boolean openFirst2 = SQLRangeEnum.openFirst(SQLRangeEnum.CLOSED);
        boolean openFirst3 = SQLRangeEnum.openFirst(SQLRangeEnum.CLOSED_OPEN);
        boolean openFirst4 = SQLRangeEnum.openFirst(SQLRangeEnum.OPEN_CLOSED);
        boolean openEnd1 = SQLRangeEnum.openEnd(SQLRangeEnum.OPEN);
        boolean openEnd2 = SQLRangeEnum.openEnd(SQLRangeEnum.CLOSED);
        boolean openEnd3 = SQLRangeEnum.openEnd(SQLRangeEnum.CLOSED_OPEN);
        boolean openEnd4 = SQLRangeEnum.openEnd(SQLRangeEnum.OPEN_CLOSED);

        LogFactory.useStdOutLogging();


        // 设置properties
        Properties properties = new Properties();
        properties.setProperty("name", "easy-query");
        properties.setProperty("driverClassName", driver);
        properties.setProperty("url", url);
        properties.setProperty("username", username);
        properties.setProperty("password", password);
        int pc = Runtime.getRuntime().availableProcessors();
        properties.setProperty("initialSize", String.valueOf(pc));
        properties.setProperty("maxActive", String.valueOf(2 * pc + 1));
        properties.setProperty("minIdle", String.valueOf(pc));
        DataSource dataSource = null;
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new EasyQueryException(e);
        }

        easyQuery = new DefaultEasyQuery(EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
                .build());
        QueryRuntimeContext runtimeContext = easyQuery.getRuntimeContext();

        EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();

        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(BlogEntity.class);
        ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull("title");
        BlogEntity blog = new BlogEntity();
        FastBean beanFastSetter = EasyBeanUtil.getFastBean(BlogEntity.class);
        PropertySetterCaller<Object> beanSetter = beanFastSetter.getBeanSetter(new FastBeanProperty(false,columnMetadata.getProperty()));
        beanSetter.call(blog,"123");
        {
            long start = System.currentTimeMillis();
            PropertySetterCaller<Object> beanSetter1 = beanFastSetter.getBeanSetter(new FastBeanProperty(false,columnMetadata.getProperty()));
            for (int i = 0; i < 10000000; i++) {
               beanSetter1.call(blog,"123");
            }
            long end = System.currentTimeMillis();
            System.out.println("lambda100000次耗时：" + (end - start) + "ms");

        }
        {

            PropertyDescriptor property = columnMetadata.getProperty();
            Method writeMethodOrNull = EasyClassUtil.getWriteMethodOrNull(new FastBeanProperty(false,property), BlogEntity.class);
            callSetter(blog,writeMethodOrNull,property,"123");
        }
        {
            long start = System.currentTimeMillis();

            PropertyDescriptor property = columnMetadata.getProperty();
            Method writeMethodOrNull = EasyClassUtil.getWriteMethodOrNull(new FastBeanProperty(false,property), BlogEntity.class);
            writeMethodOrNull.setAccessible(true);
            for (int i = 0; i < 10000000; i++) {
                callSetter(blog,writeMethodOrNull,property,"123");
            }
            long end = System.currentTimeMillis();
            System.out.println("反射100000次set耗时：" + (end - start) + "ms");
            if(true){
                throw  new RuntimeException("123");
            }
        }
        {
//            List<Topic> list1 = easyProxyQuery
//                    .queryable(TopicProxy.createTable())
//                    .where(o -> o.eq(o.t().id(), "123").like(o.t().title(), "xxx"))
//                    .where(o -> o.eq(o.t().id(), "123").like(o.t().title(), "xxx"))
//                    .select(o -> o.columns(o.t().id(), o.t().title()))
//                    .toList();
//            List<SysUser> sysUsers = easyProxyQuery
//                    .queryable(TopicProxy.createTable())
//                    .where(o -> o.eq(o.t().id, "123").like(o.t().title, "xxx"))
//                    .select(SYS_USER_PROXY, o -> o.columns(o.t().id, o.t().title).columnAs(o.t().title, o.tr().username))
//                    .toList();
//            List<SysUser> sysUsers1 = easyProxyQuery
//                    .queryable(TopicProxy.createTable())
//                    .where(o -> o.eq(o.t().id, "123").like(o.t().title, "xxx"))
//                    .select(SYS_USER_PROXY)
//                    .groupBy(g -> g.sqlNativeSegment("").column(g.t().idCard))
//                    .orderByAsc(o -> o.columns(o.t().idCard, o.t().phone))
//                    .toList();
//            TopicProxy topicProxy = TOPIC_PROXY;
//            String bigDecimal = easyProxyQuery
//                    .queryable(topicProxy)
//                    .where(o -> o.eq(o.t().id, "123").like(o.t().title, "xxx"))
//                    .maxOrNull(topicProxy.id);
//
//            List<Topic> list2 = easyProxyQuery.queryable(TOPIC_PROXY)
//                    .leftJoin(SYS_USER_PROXY, o -> o.eq(o.t().title, o.t1().idCard))
//                    .toList();
//            List<SysUser> sysUsers2 = easyProxyQuery.queryable(TOPIC_PROXY)
//                    .leftJoin(SYS_USER_PROXY, o -> o.eq(o.t().title, o.t1().phone))
//                    .innerJoin(SYS_USER_PROXY, o -> o.eq(o.t1().phone, o.t2().phone).like(o.t2().idCard, "123"))
//                    .where(filter -> filter.like(filter.t1().username, "111").eq(filter.t2().idCard, "111"))
//                    .select(SYS_USER_PROXY, o -> o.columns(o.t1().idCard, o.t2().username, o.t().id).columnAs(o.t2().phone, o.tr().phone))
//                    .toList();
//            List<Topic> list2 = easyProxyQuery
//                    .queryable(TopicSQL.DEFAULT)
//                    .where(t -> t.id().eq("123").name().like("111").id().isNull())
//                    .select(TopicSQL.DEFAULT, (t, tr) -> t.columns(t.ID,t.NAME).as(t.ID,tr.NAME))
//                    .toList();


            Queryable<String> nameSubQuery = easyQuery.queryable(TestUserMysql.class).where(o -> o.eq(TestUserMysql::getName, "xhn")).select(String.class, o -> o.column(TestUserMysql::getName));
            TestUserMysql testUserMysql = easyQuery.queryable(TestUserMysql.class)
                    .where(o -> o.eq(TestUserMysql::getName, 12).in(TestUserMysql::getName, nameSubQuery))
                    .firstOrNull();

            Queryable<TestUserMysql> sql = easyQuery.queryable(TestUserMysql.class)
                    .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                    .where(o -> o.eq(TestUserMysql::getId, "102")
                            .like(TestUserMysql::getName, "1%")
                            .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                            ));
//            sql.where(o->o.isNotNull(TestUserMysql::getId));
            long count31 = easyQuery.queryable(SysUserLogbyMonth.class)
                    .innerJoin(sql, (a, b) -> a.eq(b, SysUserLogbyMonth::getId, TestUserMysql::getId))
                    .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).select(o -> o.column(SysUserLogbyMonth::getId)).count();


        }

        TopicRequest topicRequest = new TopicRequest();
        LocalDateTime now = LocalDateTime.now();
        topicRequest.setCreateTimeBegin(now);

        {
            long start = System.currentTimeMillis();
            String s1 = easyQuery
                    .queryable(Topic.class).where(o -> o.gt(Topic::getCreateTime, now)).toSQL();

            for (int i = 0; i < 100000; i++) {

                String s = easyQuery
                        .queryable(Topic.class).where(o -> o.gt(Topic::getCreateTime, now)).toSQL();
            }
            long end = System.currentTimeMillis();
            System.out.println("耗时：" + (end - start) + "ms");

        }
        {
            long start = System.currentTimeMillis();
            String s1 = easyQuery
                    .queryable(Topic.class).whereObject(topicRequest).toSQL();

            for (int i = 0; i < 100000; i++) {
                String s = easyQuery
                        .queryable(Topic.class).whereObject(topicRequest).toSQL();
            }
            long end = System.currentTimeMillis();
            System.out.println("耗时：" + (end - start) + "ms");

        }
        {
//            easyQuery.updatable(Topic.class)
//                    .set(Topic::getStars, 14)
//                    .where(o -> o.eq(Topic::getId, "94d16a2883a045cbbe8906d916ef234d")).executeRows(2,"更新失败");
//            long rows = easyQuery.updatable(Topic.class)
//                    .set(Topic::getStars, 12)
//                    .where(o -> o.eq(Topic::getId, "94d16a2883a045cbbe8906d916ef234d")).executeRows();


//            long rows = easyQuery.updatable(Topic.class)
//                    .set(Topic::getTitle, Topic::getStars)
//                    .where(o -> o.eq(Topic::getId, "94d16a2883a045cbbe8906d916ef234d")).executeRows();

//            long rows1 = easyQuery.updatable(Topic.class)
//                    .setIncrement(Topic::getStars)
//                    .where(o -> o.eq(Topic::getId, "94d16a2883a045cbbe8906d916ef234d")).executeRows();
//            long rows2 = easyQuery.updatable(Topic.class)
//                    .setIncrement(Topic::getStars,2)
//                    .where(o -> o.eq(Topic::getId, "94d16a2883a045cbbe8906d916ef234d")).executeRows();
//            long rows3 = easyQuery.updatable(Topic.class)
//                    .setDecrement(Topic::getStars)
//                    .where(o -> o.eq(Topic::getId, "94d16a2883a045cbbe8906d916ef234d")).executeRows();
//            long rows4 = easyQuery.updatable(Topic.class)
//                    .setDecrement(Topic::getStars,2)
//                    .where(o -> o.eq(Topic::getId, "94d16a2883a045cbbe8906d916ef234d")).executeRows();

//            List<Topic> topics = new ArrayList<>();
//            for (int i = 0; i < 10; i++) {
//                Topic topic = new Topic();
//                topic.setId(UUID.randomUUID().toString().replaceAll("-",""));
//                topic.setStars(i+100);
//                topic.setTitle("标题"+i);
//                topic.setCreateTime(LocalDateTime.now().plusDays(i));
//                topics.add(topic);
//            }
//            long rows = easyQuery.insertable(topics.get(0)).insert(topics.get(1)).executeRows();
            System.out.println(1);
        }

        {
            runtimeContext.getTrackManager().begin();
            TestUserMysql testUserMysql = easyQuery.queryable(TestUserMysql.class).asTracking().firstOrNull();
            testUserMysql.setName("444444" + new Random().nextInt(100));
            long l = easyQuery.updatable(testUserMysql).executeRows();
            runtimeContext.getTrackManager().release();


            List<TestUserMysql> updates = new ArrayList<>();
            TestUserMysql test1 = new TestUserMysql();
            test1.setId("102");
            test1.setName("ds01");
            updates.add(test1);
            TestUserMysql test2 = new TestUserMysql();
            test2.setId("105");
            test2.setAge(102);
            test2.setName("111");
            updates.add(test2);
            long l12xx = easyQuery.updatable(updates).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS).executeRows();
        }
        {
            TestUserMysql testUserMysqlx11 = easyQuery.queryable(TestUserMysql.class)
                    .where(o -> o.eq(TestUserMysql::getId, "102").like(TestUserMysql::getName, "1").and(x ->
                            x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                    )).orderByAsc(o -> o.column(TestUserMysql::getName).column(TestUserMysql::getAge))
                    .orderByDesc(o -> o.column(TestUserMysql::getName)).firstOrNull();
            TestUserMysqlx testUserMysqlx = easyQuery.queryable(SysUserLogbyMonth.class).select(TestUserMysqlx.class).leftJoin(TestUserMysql.class, (a, b) -> a.eq(b, TestUserMysqlx::getId, TestUserMysql::getId))
                    .where(o -> o.eq(TestUserMysqlx::getId, "123")).firstOrNull();
            long testUserMysqlxascaa = easyQuery.queryable(TestUserMysql.class)
                    .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                    .where(o -> o.eq(TestUserMysql::getId, "102")
                            .like(TestUserMysql::getName, "1%")
                            .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                            )).select(o -> o.column(TestUserMysql::getName)).count();
            long xhn = easyQuery.updatable(TestUserMysql.class).set(TestUserMysql::getAge, 12)
                    .set(TestUserMysql::getName, "xhn").where(o -> o.like(TestUserMysql::getId, "123")).executeRows();
            List<SysUserLogbyMonth> sysUserLogbyMonths = easyQuery.sqlQuery("select * from sys_user_logby_month_202103 limit 1", SysUserLogbyMonth.class);
//            SysUserLogbyMonth sysUserLogbyMonth = easyQuery.queryable("select * from sys_user_logby_month_202103", SysUserLogbyMonth.class)
//                    .firstOrNull();
//            TestUserMysqlx sysUserLogbyMonth1 = easyQuery.queryable("select * from sys_user_logby_month_202103", SysUserLogbyMonth.class).select(TestUserMysqlx.class).leftJoin(TestUserMysql.class, (a, b) -> a.eq(b, TestUserMysqlx::getId, TestUserMysql::getId))
//                    .where(o -> o.eq(TestUserMysqlx::getId, "123")).firstOrNull();

            List<TestUserMysqlx> testUserMysqls = easyQuery.queryable(SysUserLogbyMonth.class).toList(TestUserMysqlx.class);
            List<TestUserMysqlx> testUserMysqlsa = easyQuery.queryable(TestUserMysql.class).toList(TestUserMysqlx.class);
            List<TestUserMysqlx> testUserMysqls0 = easyQuery.queryable(SysUserLogbyMonth.class).select(TestUserMysqlx.class).toList();
            List<TestUserMysqlx> testUserMysqls1 = easyQuery.queryable(SysUserLogbyMonth.class).select(o -> o.column(SysUserLogbyMonth::getId)).select(TestUserMysqlx.class).toList();
            List<TestUserMysqlx> testUserMysqls2 = easyQuery.queryable(SysUserLogbyMonth.class).select(TestUserMysqlx.class, o -> o.column(SysUserLogbyMonth::getId)).toList();


            String s = easyQuery.queryable(TestUserMysql.class)
                    .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                    .where(o -> o.eq(TestUserMysql::getId, "102")
                            .like(TestUserMysql::getName, "1%")
                            .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                            )).toSQL();
            System.out.println("---------:" + s);

            TestUserMysql testUserMysqlxa = easyQuery.queryable(TestUserMysql.class)
                    .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                    .where(o -> o.eq(TestUserMysql::getId, "102")
                            .like(TestUserMysql::getName, "1%")
                            .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                            )).firstOrNull();
            long testUserMysqlxac = easyQuery.queryable(TestUserMysql.class)
                    .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                    .where(o -> o.eq(TestUserMysql::getId, "102")
                            .like(TestUserMysql::getName, "1%")
                            .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                            )).count();
            long testUserMysqlxasc = easyQuery.queryable(TestUserMysql.class)
                    .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                    .where(o -> o.eq(TestUserMysql::getId, "102")
                            .like(TestUserMysql::getName, "1%")
                            .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                            )).select(o -> o.column(TestUserMysql::getName)).count();
            TestUserMysql testUserMysql = easyQuery.queryable(TestUserMysql.class)
                    .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                    .where(o -> o.eq(TestUserMysql::getId, "102")
                            .like(TestUserMysql::getName, "1%")
                            .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                            )).select(o -> o.column(TestUserMysql::getName)).firstOrNull();


            Queryable<TestUserMysql> sql = easyQuery.queryable(TestUserMysql.class)
                    .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                    .where(o -> o.eq(TestUserMysql::getId, "102")
                            .like(TestUserMysql::getName, "1%")
                            .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                            ));
//            sql.where(o->o.isNotNull(TestUserMysql::getId));
            long count31 = easyQuery.queryable(SysUserLogbyMonth.class)
                    .innerJoin(sql, (a, b) -> a.eq(b, SysUserLogbyMonth::getId, TestUserMysql::getId))
                    .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).select(o -> o.column(SysUserLogbyMonth::getId)).count();

        }
        {
            Queryable<TestUserMysqlx> select = easyQuery.queryable(TestUserMysql.class)
                    .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                    .where(o -> o.eq(TestUserMysql::getId, "102")
                            .like(TestUserMysql::getName, "1%")
                            .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                            ))
                    .select(TestUserMysqlx.class, x -> x.columnAll().columnAs(TestUserMysql::getName, TestUserMysqlx::getName1));

            long count3 = easyQuery.queryable(SysUserLogbyMonth.class)
                    .leftJoin(select, (a, b) -> a.eq(b, SysUserLogbyMonth::getId, TestUserMysqlx::getId))
                    .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).select(o -> o.column(SysUserLogbyMonth::getId)).count();


            {

                Queryable<SysUserLogbyMonth> queryable = easyQuery.queryable(SysUserLogbyMonth.class)
                        .where(o -> o.eq(SysUserLogbyMonth::getId, "119"));
                long count2 = queryable.count();
                List<SysUserLogbyMonth> sysUserLogbyMonths = queryable.limit(1, 10).toList();

            }
            TestUserMysqlx testUserMysql23 = easyQuery.queryable(TestUserMysql.class)
                    .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                    .where(o -> o.eq(TestUserMysql::getId, "102")
                            .like(TestUserMysql::getName, "1%")
                            .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                            ))
                    .select(TestUserMysqlx.class, x -> x.columnAll().columnAs(TestUserMysql::getName, TestUserMysqlx::getName1)).firstOrNull();


            easyQuery.queryable(TestUserMysql.class)
                    .where(o -> o.eq(TestUserMysql::getName, "ds0"))
                    .groupBy(o -> o.column(TestUserMysql::getAge))
                    .having(o -> o.count(TestUserMysql::getId, AggregatePredicateCompare.GE, 0))
                    .select(TestUserMysqlGroup.class, o -> o.column(TestUserMysql::getAge).columnCountAs(TestUserMysql::getId, TestUserMysqlGroup::getAcount))
                    .firstOrNull();

            String ds0z = easyQuery.queryable(TestUserMysql.class)
                    .where(o -> o.eq(TestUserMysql::getName, "ds0"))
                    .groupBy(o -> o.column(TestUserMysql::getAge))
                    .having(o -> o.count(TestUserMysql::getId, AggregatePredicateCompare.GE, 0))
                    .select(TestUserMysqlGroup.class, o -> o.column(TestUserMysql::getAge).columnCountAs(TestUserMysql::getId, TestUserMysqlGroup::getAcount))
                    .toSQL();
            System.out.println(ds0z);

            try (Transaction transaction = easyQuery.beginTransaction()) {

                transaction.commit();
            }
            {
                List<TestUserMysql> updates = new ArrayList<>();
                TestUserMysql test1 = new TestUserMysql();
                test1.setId("102");
                test1.setAge(102);
                test1.setName("ds01");
                updates.add(test1);
                TestUserMysql test2 = new TestUserMysql();
                test2.setId("105");
                test2.setAge(102);
                test2.setName("ds01");
                updates.add(test2);
                long l12xx = easyQuery.updatable(updates).setColumns(o -> o.column(TestUserMysql::getName)).whereColumns(o -> o.column(TestUserMysql::getAge)).executeRows();
                long l2 = easyQuery.deletable(updates).executeRows();
            }

            long l4 = easyQuery.deletable(SysUserLogbyMonth.class).whereById("00010728-2b86-426a-a81c-001a67ff409a").executeRows();

            ArrayList<SysUserLogbyMonth> sysUserLogbyMonths1 = new ArrayList<>();
//        long execute = client.insert(LocalDateTime.now()).execute();
            SysUserLogbyMonth sysUserLogbyMonth2 = new SysUserLogbyMonth();
            sysUserLogbyMonth2.setId(UUID.randomUUID().toString());
            sysUserLogbyMonth2.setTime(LocalDateTime.now());
            sysUserLogbyMonths1.add(sysUserLogbyMonth2);
            long execute1 = easyQuery.insertable(sysUserLogbyMonths1).executeRows();
            long l = easyQuery.updatable(sysUserLogbyMonth2).executeRows();
            System.out.println(l);
            long l23 = easyQuery.deletable(sysUserLogbyMonths1).executeRows();
            long l3 = easyQuery.deletable(TestUserMysql.class).whereById("123").executeRows();
            long l31 = easyQuery.deletable(SysUserLogbyMonth.class).whereById("123").executeRows();
            System.out.println("-------------------1");
            long l34 = easyQuery.deletable(TestUserMysql.class).where(o -> o.like(TestUserMysql::getName, "1233")).executeRows();
            long l345 = easyQuery.deletable(TestUserMysql.class).where(o -> o.like(TestUserMysql::getName, "1233")).whereById("123").executeRows();
            SysUserLogbyMonth sysUserLogbyMonth23 = new SysUserLogbyMonth();
            sysUserLogbyMonth23.setId(UUID.randomUUID().toString());
            sysUserLogbyMonth23.setTime(LocalDateTime.now());
            long l13 = easyQuery.updatable(sysUserLogbyMonth23).executeRows();
            TestUserMysql testUserMysql3 = new TestUserMysql();
            System.out.println("-------------------2");
            long l1 = easyQuery.updatable(testUserMysql3).setColumns(o -> o.column(TestUserMysql::getName)).executeRows();
            long l12 = easyQuery.updatable(testUserMysql3).setColumns(o -> o.column(TestUserMysql::getName)).whereColumns(o -> o.column(TestUserMysql::getAge)).executeRows();
            long xhn = easyQuery.updatable(TestUserMysql.class).set(TestUserMysql::getAge, 12).set(TestUserMysql::getName, "xhn").where(o -> o.like(TestUserMysql::getId, "123")).executeRows();
//
//        ArrayList<SysUserLogbyMonth> sysUserLogbyMonthsxx = new ArrayList<>();
//        for (int j = 0; j < 10000; j++) {
//
//            SysUserLogbyMonth sysUserLogbyMonth2x = new SysUserLogbyMonth();
//            sysUserLogbyMonth2x.setId(UUID.randomUUID().toString());
//            sysUserLogbyMonth2x.setTime(LocalDateTime.now());
//            sysUserLogbyMonthsxx.add(sysUserLogbyMonth2x);
//        }
//        long start = System.currentTimeMillis();
//        long execute2 = client.insert(sysUserLogbyMonthsxx).executeRows();
//        System.out.println("返回行数:" + execute2);
//        long end = System.currentTimeMillis();
//        System.out.println("耗时：" + (end - start) + "ms");
            SysUserLogbyMonth sysUserLogbyMonth1 = easyQuery.queryable(SysUserLogbyMonth.class)
                    .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).firstOrNull();
            SysUserLogbyMonth sysUserLogbyMonth1x = easyQuery.queryable(SysUserLogbyMonth.class)
                    .where(o -> o.in(SysUserLogbyMonth::getId, Arrays.asList("119", "111")).notIn(SysUserLogbyMonth::getId, Arrays.asList("1", "2"))).firstOrNull();
            SysUserLogbyMonth sysUserLogbyMonth2x = easyQuery.queryable(SysUserLogbyMonth.class)
                    .where(o -> o.in(SysUserLogbyMonth::getId, Collections.emptyList())).firstOrNull();
            SysUserLogbyMonth sysUserLogbyMonth2xx = easyQuery.queryable(SysUserLogbyMonth.class)
                    .where(o -> o.notIn(SysUserLogbyMonth::getId, Collections.emptyList())).firstOrNull();
            EasyPageResult<SysUserLogbyMonth> page = easyQuery.queryable(SysUserLogbyMonth.class)
                    .where(o -> o.notIn(SysUserLogbyMonth::getId, Collections.emptyList())).select(SysUserLogbyMonth.class).toPageResult(2, 20);
//        long start = System.currentTimeMillis();
//        for (int j = 0; j < 1000; j++) {
//
//            SysUserLogbyMonth sysUserLogbyMonth2 = client.select(SysUserLogbyMonth.class)
//                    .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).firstOrNull();
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("耗时："+(end-start)+"ms");

            Queryable<SysUserLogbyMonth> queryable = easyQuery.queryable(SysUserLogbyMonth.class)
                    .where(o -> o.eq(SysUserLogbyMonth::getId, "119"));
            long count2 = queryable.count();
            List<SysUserLogbyMonth> sysUserLogbyMonths = queryable.limit(1, 10).toList();

            long count = easyQuery.queryable(SysUserLogbyMonth.class)
                    .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).count();
            long count1 = easyQuery.queryable(SysUserLogbyMonth.class)
                    .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).countDistinct(o -> o.column(SysUserLogbyMonth::getId).column(SysUserLogbyMonth::getTime));

            boolean any = easyQuery.queryable(SysUserLogbyMonth.class)
                    .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).any();
            LocalDateTime localDateTime = easyQuery.queryable(SysUserLogbyMonth.class).maxOrNull(SysUserLogbyMonth::getTime);
            Integer totalAge = easyQuery.queryable(TestUserMysql.class).sumOrDefault(TestUserMysql::getAge, 0);
            Integer max = easyQuery.queryable(TestUserMysql.class).maxOrNull(TestUserMysql::getAge);

            String ds01 = easyQuery.queryable(TestUserMysql.class)
                    .where(o -> o.eq(TestUserMysql::getName, "ds0"))
                    .groupBy(o -> o.column(TestUserMysql::getAge))
                    .having(o -> o.count(TestUserMysql::getId, AggregatePredicateCompare.GE, 0))
                    .select(TestUserMysqlGroup.class, o -> o.column(TestUserMysql::getAge).columnCountAs(TestUserMysql::getId, TestUserMysqlGroup::getAcount))
                    .toSQL();
            String ds02 = easyQuery.queryable(TestUserMysql.class)
                    .where(o -> o.eq(TestUserMysql::getName, "ds0"))
                    .groupBy(o -> o.column(TestUserMysql::getAge))
                    .having(o -> o.count(TestUserMysql::getId, AggregatePredicateCompare.GE, 0))
                    .select(TestUserMysqlGroup.class, o -> o.column(TestUserMysql::getAge).columnCountAs(TestUserMysql::getId, TestUserMysqlGroup::getAcount))
                    .toSQL();
            System.out.println("ds01==ds02:" + ds01.equals(ds02));
            long start = System.currentTimeMillis();
            for (int ii = 0; ii < 10000; ii++) {
//            SysUserLogbyMonth sysUserLogbyMonth = client.select(SysUserLogbyMonth.class)
//                    .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).firstOrNull();
                String ds0 = easyQuery.queryable(TestUserMysql.class)
                        .where(o -> o.eq(TestUserMysql::getName, "ds0"))
                        .groupBy(o -> o.column(TestUserMysql::getAge))
                        .having(o -> o.count(TestUserMysql::getId, AggregatePredicateCompare.GE, 0))
                        .select(TestUserMysqlGroup.class, o -> o.column(TestUserMysql::getAge).columnCountAs(TestUserMysql::getId, TestUserMysqlGroup::getAcount))
                        .toSQL();
            }
            long end = System.currentTimeMillis();
            System.out.println("耗时：" + (end - start) + "ms");


//        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(TestUserMysql.class);
//        SqlExpression<SqlPredicate<TestUserMysql>> xx=xxx->xxx.and(x->x.eq(TestUserMysql::getId,12));
//        entityMetadata.addLogicDeleteExpression(xx);

            List<TestUserMysqlGroup> testUserMysqlsy = easyQuery.queryable(TestUserMysql.class)
                    .where(o -> o.eq(TestUserMysql::getName, "ds0"))
                    .groupBy(o -> o.column(TestUserMysql::getAge))
                    .having(o -> o.count(TestUserMysql::getId, AggregatePredicateCompare.GE, 0))
                    .select(TestUserMysqlGroup.class, o -> o.column(TestUserMysql::getAge).columnCountAs(TestUserMysql::getId, TestUserMysqlGroup::getAcount))
                    .toList();
            List<TestUserMysql> testUserMysqls1 = easyQuery.queryable(TestUserMysql.class)
                    .where(o -> o.eq(TestUserMysql::getName, "123"))
                    .groupBy(o -> o.column(TestUserMysql::getAge))
                    .having(o -> o.countDistinct(TestUserMysql::getId, AggregatePredicateCompare.GT, 5).and(x -> x.countDistinct(TestUserMysql::getId, AggregatePredicateCompare.GT, 5).or().countDistinct(TestUserMysql::getId, AggregatePredicateCompare.GT, 5)))
                    .toList();
            TestUserMysql testUserMysql = easyQuery.queryable(TestUserMysql.class)
                    .where(o -> o.eq(TestUserMysql::getId, "102").like(TestUserMysql::getName, "1").and(x ->
                            x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                    )).orderByAsc(o -> o.column(TestUserMysql::getName).column(TestUserMysql::getAge))
                    .orderByDesc(o -> o.column(TestUserMysql::getName)).firstOrNull();


            TestUserMysql testUserMysql1 = easyQuery.queryable(TestUserMysql.class)
                    .where(o -> o.eq(TestUserMysql::getId, "102").like(TestUserMysql::getName, "1%").and(x ->
                            x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                    ).or(x -> x.eq(TestUserMysql::getName, 456).eq(TestUserMysql::getId, 8989))).firstOrNull();

            SysUserLogbyMonth sysUserLogbyMonth = easyQuery.queryable(SysUserLogbyMonth.class)
                    .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).firstOrNull();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String format = dateTimeFormatter.format(sysUserLogbyMonth.getTime());
            System.out.println(sysUserLogbyMonth);
            TestUserMysqlx testUserMysql2 = easyQuery.queryable(TestUserMysql.class)
                    .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                    .where(o -> o.eq(TestUserMysql::getId, "102")
                            .like(TestUserMysql::getName, "1%")
                            .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                            ))
                    .select(TestUserMysqlx.class, x -> x.columnAll().columnAs(TestUserMysql::getName, TestUserMysqlx::getName1)).firstOrNull();

            TestUserMysqlx testUserMysqlx = easyQuery.queryable(SysUserLogbyMonth.class)
                    .leftJoin(TestUserMysql.class, (a, b) -> a.eq(b, SysUserLogbyMonth::getId, TestUserMysql::getName).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                    .where(o -> o.eq(SysUserLogbyMonth::getId, "102")
                            .like(SysUserLogbyMonth::getTime, LocalDateTime.now())
                            .and(x -> x.like(SysUserLogbyMonth::getId, "123").or().eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                    ).select(TestUserMysqlx.class, x -> x.columnAll().columnAs(SysUserLogbyMonth::getId, TestUserMysqlx::getName1)).firstOrNull();
            System.out.println("Hello world!");
        }
    }

    public static void callSetter(Object target, Method setter, PropertyDescriptor prop, Object value) throws SQLException, InvocationTargetException, IllegalAccessException {
//        try {
            setter.invoke(target, value);
//        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
//            throw new SQLException("Cannot set " + prop.getName() + ",value: " + value + ".: " + e.getMessage(), e);
//        }

    }
}