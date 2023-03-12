package com.easy.query;


import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.easy.query.core.abstraction.DefaultEasyQueryLambdaFactory;
import com.easy.query.core.abstraction.DefaultEasyQueryRuntimeContext;
import com.easy.query.core.abstraction.EasyQueryLambdaFactory;
import com.easy.query.core.abstraction.EasySqlApiFactory;
import com.easy.query.test.*;
import com.easy.query.core.abstraction.metadata.EntityMetadataManager;
import com.easy.query.core.api.pagination.PageResult;
import com.easy.query.core.api.client.DefaultEasyQuery;
import com.easy.query.core.api.client.EasyQuery;
import com.easy.query.core.api.def.DefaultEasySqlApiFactory;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.jdbc.con.DefaultConnectionManager;
import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.executor.DefaultEasyExecutor;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.basic.jdbc.types.DefaultJdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.EasyJdbcTypeHandlerManager;
import com.easy.query.core.config.DefaultConfig;
import com.easy.query.core.config.NameConversion;
import com.easy.query.core.config.UnderlinedNameConversion;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.metadata.DefaultEntityMetadataManager;
import com.easy.query.mysql.MySQLSqlExpressionFactory;
import com.easy.query.mysql.config.MySQLDialect;
import com.easy.query.test.*;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class Main {
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String username = "root";
    private static final String password = "root";
//    private static final String url = "jdbc:mysql://127.0.0.1:3306/dbdbd0?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false";
    private static final String url = "jdbc:mysql://127.0.0.1:3306/dbdbd0?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true";
    private static EasyQuery easyQuery;

    public static void main(String[] args) {

//        int[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
//        {
//            long start = System.currentTimeMillis();
//
//            for (int i = 0; i < 100000; i++) {
//                ArrayUtil.sum(ints);
//            }
//            long end = System.currentTimeMillis();
//            System.out.println("耗时：" + (end - start) + "ms");
//
//        }
//        {
//            long start = System.currentTimeMillis();
//
//            for (int i = 0; i < 100000; i++) {
//                Arrays.stream(ints).sum();
//            }
//            long end = System.currentTimeMillis();
//            System.out.println("耗时：" + (end - start) + "ms");
//
//        }
//        {
//            long start = System.currentTimeMillis();
//
//            for (int i = 0; i < 100000; i++) {
//                ArrayUtil.sum(ints);
//            }
//            long end = System.currentTimeMillis();
//            System.out.println("耗时：" + (end - start) + "ms");
//
//        }
//        {
//            long start = System.currentTimeMillis();
//
//            for (int i = 0; i < 100000; i++) {
//                Arrays.stream(ints).sum();
//            }
//            long end = System.currentTimeMillis();
//            System.out.println("耗时：" + (end - start) + "ms");
//
//        }

        LogFactory.useStdOutLogging();

        DefaultConfig easyConfig = new DefaultConfig("easy-query", driver, username, password, url);

        // 设置properties
        Properties properties = new Properties();
        properties.setProperty("name", easyConfig.getName());
        properties.setProperty("driverClassName", easyConfig.getDriver());
        properties.setProperty("url", easyConfig.getUrl());
        properties.setProperty("username", easyConfig.getUsername());
        properties.setProperty("password", easyConfig.getPassword());
        int i = Runtime.getRuntime().availableProcessors();
        properties.setProperty("initialSize", String.valueOf(i));
        properties.setProperty("maxActive", String.valueOf(2 * i + 1));
        properties.setProperty("minIdle", String.valueOf(i));
        DataSource dataSource = null;
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new EasyQueryException(e);
        }
        EasyConnectionManager connectionManager = new DefaultConnectionManager(dataSource);
        DefaultEasyExecutor defaultExecutor = new DefaultEasyExecutor();
        EasyJdbcTypeHandlerManager jdbcTypeHandler = new DefaultJdbcTypeHandlerManager();
        NameConversion nameConversion = new UnderlinedNameConversion();
        EasyQueryConfiguration configuration = new EasyQueryConfiguration();
        configuration.setNameConversion(nameConversion);
        configuration.setDialect(new MySQLDialect());

        EntityMetadataManager entityMetadataManager = new DefaultEntityMetadataManager(configuration);
        EasyQueryLambdaFactory easyQueryLambdaFactory = new DefaultEasyQueryLambdaFactory();
        MySQLSqlExpressionFactory mySQLSqlExpressionFactory = new MySQLSqlExpressionFactory();
        EasySqlApiFactory easyQueryableFactory = new DefaultEasySqlApiFactory(mySQLSqlExpressionFactory);
        DefaultEasyQueryRuntimeContext jqdcRuntimeContext = new DefaultEasyQueryRuntimeContext(configuration, entityMetadataManager, easyQueryLambdaFactory, connectionManager, defaultExecutor, jdbcTypeHandler, easyQueryableFactory, mySQLSqlExpressionFactory);

//        jqdcRuntimeContext.getEasyQueryConfiguration().applyEntityTypeConfiguration(new TestUserMySqlConfiguration());
configuration.applyGlobalInterceptorStrategy(new NameQueryFilter());

        easyQuery = new DefaultEasyQuery(jqdcRuntimeContext);

        {
            TestUserMysqlx testUserMysqlx = easyQuery.query(SysUserLogbyMonth.class).select(TestUserMysqlx.class).leftJoin(TestUserMysql.class, (a, b) -> a.eq(b, TestUserMysqlx::getId, TestUserMysql::getId))
                    .where(o -> o.eq(TestUserMysqlx::getId, "123")).firstOrNull();

            List<TestUserMysqlx> testUserMysqls = easyQuery.query(SysUserLogbyMonth.class).toList(TestUserMysqlx.class);
            List<TestUserMysqlx> testUserMysqlsa = easyQuery.query(TestUserMysql.class).toList(TestUserMysqlx.class);
            List<TestUserMysqlx> testUserMysqls0 = easyQuery.query(SysUserLogbyMonth.class).select(TestUserMysqlx.class).toList();
            List<TestUserMysqlx> testUserMysqls1 = easyQuery.query(SysUserLogbyMonth.class).select(o->o.column(SysUserLogbyMonth::getId)).select(TestUserMysqlx.class).toList();
            List<TestUserMysqlx> testUserMysqls2 = easyQuery.query(SysUserLogbyMonth.class).select(TestUserMysqlx.class, o->o.column(SysUserLogbyMonth::getId)).toList();


            String s = easyQuery.query(TestUserMysql.class)
                    .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                    .where(o -> o.eq(TestUserMysql::getId, "102")
                            .like(TestUserMysql::getName, "1%")
                            .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                            )).toSql();
            System.out.println("---------:"+s);

            TestUserMysql testUserMysqlxa = easyQuery.query(TestUserMysql.class)
                    .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                    .where(o -> o.eq(TestUserMysql::getId, "102")
                            .like(TestUserMysql::getName, "1%")
                            .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                            )).firstOrNull();
            long testUserMysqlxac= easyQuery.query(TestUserMysql.class)
                    .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                    .where(o -> o.eq(TestUserMysql::getId, "102")
                            .like(TestUserMysql::getName, "1%")
                            .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                            )).count();
            long testUserMysqlxasc= easyQuery.query(TestUserMysql.class)
                    .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                    .where(o -> o.eq(TestUserMysql::getId, "102")
                            .like(TestUserMysql::getName, "1%")
                            .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                            )).select(o->o.column(TestUserMysql::getName)).count();
            TestUserMysql testUserMysql = easyQuery.query(TestUserMysql.class)
                    .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                    .where(o -> o.eq(TestUserMysql::getId, "102")
                            .like(TestUserMysql::getName, "1%")
                            .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                            )).select(o->o.column(TestUserMysql::getName)).firstOrNull();



            Queryable<TestUserMysql> sql = easyQuery.query(TestUserMysql.class)
                    .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                    .where(o -> o.eq(TestUserMysql::getId, "102")
                            .like(TestUserMysql::getName, "1%")
                            .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                            ));
            long count31 = easyQuery.query(SysUserLogbyMonth.class)
                    .innerJoin(sql,(a,b)->a.eq(b,SysUserLogbyMonth::getId,TestUserMysql::getId))
                    .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).select(o -> o.column(SysUserLogbyMonth::getId)).count();

        }

        Queryable<TestUserMysqlx> select = easyQuery.query(TestUserMysql.class)
                .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                .where(o -> o.eq(TestUserMysql::getId, "102")
                        .like(TestUserMysql::getName, "1%")
                        .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                        ))
                .select(TestUserMysqlx.class, x -> x.columnAll().columnAs(TestUserMysql::getName, TestUserMysqlx::getName1));

        long count3 = easyQuery.query(SysUserLogbyMonth.class)
                .leftJoin(select,(a,b)->a.eq(b,SysUserLogbyMonth::getId,TestUserMysqlx::getId))
                .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).select(o -> o.column(SysUserLogbyMonth::getId)).count();


        {

            Queryable<SysUserLogbyMonth> queryable = easyQuery.query(SysUserLogbyMonth.class)
                    .where(o -> o.eq(SysUserLogbyMonth::getId, "119"));
            long count2 = queryable.count();
            List<SysUserLogbyMonth> sysUserLogbyMonths = queryable.limit(1, 10).toList();

        }
        TestUserMysqlx testUserMysql23 = easyQuery.query(TestUserMysql.class)
                .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                .where(o -> o.eq(TestUserMysql::getId, "102")
                        .like(TestUserMysql::getName, "1%")
                        .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                        ))
                .select(TestUserMysqlx.class, x -> x.columnAll().columnAs(TestUserMysql::getName, TestUserMysqlx::getName1)).firstOrNull();



        easyQuery.query(TestUserMysql.class)
                .where(o -> o.eq(TestUserMysql::getName, "ds0"))
                .groupBy(o -> o.column(TestUserMysql::getAge))
                .having(o -> o.count(TestUserMysql::getId, AggregatePredicateCompare.GE, 0))
                .select(TestUserMysqlGroup.class, o -> o.column(TestUserMysql::getAge).columnCount(TestUserMysql::getId, TestUserMysqlGroup::getAcount))
                .firstOrNull();

        String ds0z = easyQuery.query(TestUserMysql.class)
                .where(o -> o.eq(TestUserMysql::getName, "ds0"))
                .groupBy(o -> o.column(TestUserMysql::getAge))
                .having(o -> o.count(TestUserMysql::getId, AggregatePredicateCompare.GE, 0))
                .select(TestUserMysqlGroup.class, o -> o.column(TestUserMysql::getAge).columnCount(TestUserMysql::getId, TestUserMysqlGroup::getAcount))
                .toSql();
        System.out.println(ds0z);

        try (Transaction transaction = easyQuery.beginTransaction()) {

            transaction.commit();
        }
        List<TestUserMysql> updates=new ArrayList<>();
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
        long l12xx = easyQuery.update(updates).setColumns(o -> o.column(TestUserMysql::getName)).whereColumns(o -> o.column(TestUserMysql::getAge)).executeRows();
        long l2 = easyQuery.delete(updates).executeRows();

        long l4 = easyQuery.delete(SysUserLogbyMonth.class).deleteById("00010728-2b86-426a-a81c-001a67ff409a").executeRows();

        ArrayList<SysUserLogbyMonth> sysUserLogbyMonths1 = new ArrayList<>();
//        long execute = client.insert(LocalDateTime.now()).execute();
        SysUserLogbyMonth sysUserLogbyMonth2 = new SysUserLogbyMonth();
        sysUserLogbyMonth2.setId(UUID.randomUUID().toString());
        sysUserLogbyMonth2.setTime(LocalDateTime.now());
        sysUserLogbyMonths1.add(sysUserLogbyMonth2);
        long execute1 = easyQuery.insert(sysUserLogbyMonths1).executeRows();
        long l = easyQuery.update(sysUserLogbyMonth2).executeRows();
        System.out.println(l);
        long l23 = easyQuery.delete(sysUserLogbyMonths1).executeRows();
        long l3 = easyQuery.delete(TestUserMysql.class).deleteById("123").executeRows();
        long l31 = easyQuery.delete(SysUserLogbyMonth.class).deleteById("123").executeRows();
        long l34 = easyQuery.delete(TestUserMysql.class).where(o->o.like(TestUserMysql::getName,"1233")).executeRows();
        long l345 = easyQuery.delete(TestUserMysql.class).where(o->o.like(TestUserMysql::getName,"1233")).deleteById("123").executeRows();
        SysUserLogbyMonth sysUserLogbyMonth23 = new SysUserLogbyMonth();
        sysUserLogbyMonth23.setId(UUID.randomUUID().toString());
        sysUserLogbyMonth23.setTime(LocalDateTime.now());
        long l13 = easyQuery.update(sysUserLogbyMonth23).executeRows();
        TestUserMysql testUserMysql3 = new TestUserMysql();
        long l1 = easyQuery.update(testUserMysql3).setColumns(o -> o.column(TestUserMysql::getName)).executeRows();
        long l12 = easyQuery.update(testUserMysql3).setColumns(o -> o.column(TestUserMysql::getName)).whereColumns(o -> o.column(TestUserMysql::getAge)).executeRows();
        long xhn = easyQuery.update(TestUserMysql.class).set(o -> o.set(TestUserMysql::getAge, 12).set(TestUserMysql::getName, "xhn")).where(o -> o.like(TestUserMysql::getId, "123")).executeRows();
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
        SysUserLogbyMonth sysUserLogbyMonth1 = easyQuery.query(SysUserLogbyMonth.class)
                .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).firstOrNull();
        SysUserLogbyMonth sysUserLogbyMonth1x = easyQuery.query(SysUserLogbyMonth.class)
                .where(o -> o.in(SysUserLogbyMonth::getId, Arrays.asList("119", "111")).notIn(SysUserLogbyMonth::getId, Arrays.asList("1", "2"))).firstOrNull();
        SysUserLogbyMonth sysUserLogbyMonth2x = easyQuery.query(SysUserLogbyMonth.class)
                .where(o -> o.in(SysUserLogbyMonth::getId, Collections.emptyList())).firstOrNull();
        SysUserLogbyMonth sysUserLogbyMonth2xx = easyQuery.query(SysUserLogbyMonth.class)
                .where(o -> o.notIn(SysUserLogbyMonth::getId, Collections.emptyList())).firstOrNull();
        PageResult<SysUserLogbyMonth> page = easyQuery.query(SysUserLogbyMonth.class)
                .where(o -> o.notIn(SysUserLogbyMonth::getId, Collections.emptyList())).toPageResult(2, 20, SysUserLogbyMonth.class);
//        long start = System.currentTimeMillis();
//        for (int j = 0; j < 1000; j++) {
//
//            SysUserLogbyMonth sysUserLogbyMonth2 = client.select(SysUserLogbyMonth.class)
//                    .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).firstOrNull();
//        }
//        long end = System.currentTimeMillis();
//        System.out.println("耗时："+(end-start)+"ms");

        Queryable<SysUserLogbyMonth> queryable = easyQuery.query(SysUserLogbyMonth.class)
                .where(o -> o.eq(SysUserLogbyMonth::getId, "119"));
        long count2 = queryable.count();
        List<SysUserLogbyMonth> sysUserLogbyMonths = queryable.limit(1, 10).toList();

        long count = easyQuery.query(SysUserLogbyMonth.class)
                .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).count();
        long count1 = easyQuery.query(SysUserLogbyMonth.class)
                .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).countDistinct(o -> o.column(SysUserLogbyMonth::getId).column(SysUserLogbyMonth::getTime));

        boolean any = easyQuery.query(SysUserLogbyMonth.class)
                .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).any();
        LocalDateTime localDateTime = easyQuery.query(SysUserLogbyMonth.class).maxOrNull(SysUserLogbyMonth::getTime);
        Integer totalAge = easyQuery.query(TestUserMysql.class).sumOrDefault(TestUserMysql::getAge, 0);
        Integer max = easyQuery.query(TestUserMysql.class).maxOrNull(TestUserMysql::getAge);

        String ds01 = easyQuery.query(TestUserMysql.class)
                .where(o -> o.eq(TestUserMysql::getName, "ds0"))
                .groupBy(o -> o.column(TestUserMysql::getAge))
                .having(o -> o.count(TestUserMysql::getId, AggregatePredicateCompare.GE, 0))
                .select(TestUserMysqlGroup.class, o -> o.column(TestUserMysql::getAge).columnCount(TestUserMysql::getId, TestUserMysqlGroup::getAcount))
                .toSql();
        String ds02 = easyQuery.query(TestUserMysql.class)
                .where(o -> o.eq(TestUserMysql::getName, "ds0"))
                .groupBy(o -> o.column(TestUserMysql::getAge))
                .having(o -> o.count(TestUserMysql::getId, AggregatePredicateCompare.GE, 0))
                .select(TestUserMysqlGroup.class, o -> o.column(TestUserMysql::getAge).columnCount(TestUserMysql::getId, TestUserMysqlGroup::getAcount))
                .toSql();
        System.out.println("ds01==ds02:"+ds01.equals(ds02));
        long start = System.currentTimeMillis();
        for (int ii = 0; ii < 10000; ii++) {
//            SysUserLogbyMonth sysUserLogbyMonth = client.select(SysUserLogbyMonth.class)
//                    .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).firstOrNull();
            String ds0 = easyQuery.query(TestUserMysql.class)
                    .where(o -> o.eq(TestUserMysql::getName, "ds0"))
                    .groupBy(o -> o.column(TestUserMysql::getAge))
                    .having(o -> o.count(TestUserMysql::getId, AggregatePredicateCompare.GE, 0))
                    .select(TestUserMysqlGroup.class, o -> o.column(TestUserMysql::getAge).columnCount(TestUserMysql::getId, TestUserMysqlGroup::getAcount))
                    .toSql();
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时："+(end-start)+"ms");


//        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(TestUserMysql.class);
//        SqlExpression<SqlPredicate<TestUserMysql>> xx=xxx->xxx.and(x->x.eq(TestUserMysql::getId,12));
//        entityMetadata.addLogicDeleteExpression(xx);

        List<TestUserMysqlGroup> testUserMysqlsy = easyQuery.query(TestUserMysql.class)
                .where(o -> o.eq(TestUserMysql::getName, "ds0"))
                .groupBy(o -> o.column(TestUserMysql::getAge))
                .having(o -> o.count(TestUserMysql::getId, AggregatePredicateCompare.GE, 0))
                .select(TestUserMysqlGroup.class, o -> o.column(TestUserMysql::getAge).columnCount(TestUserMysql::getId, TestUserMysqlGroup::getAcount))
                .toList();
        List<TestUserMysql> testUserMysqls1 = easyQuery.query(TestUserMysql.class)
                .where(o -> o.eq(TestUserMysql::getName, "123"))
                .groupBy(o -> o.column(TestUserMysql::getAge))
                .having(o -> o.countDistinct(TestUserMysql::getId, AggregatePredicateCompare.GT, 5).and(x -> x.countDistinct(TestUserMysql::getId, AggregatePredicateCompare.GT, 5).or().countDistinct(TestUserMysql::getId, AggregatePredicateCompare.GT, 5)))
                .toList();
        TestUserMysql testUserMysql = easyQuery.query(TestUserMysql.class, "y")
                .where(o -> o.eq(TestUserMysql::getId, "102").like(TestUserMysql::getName, "1%").and(x ->
                        x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                )).orderByAsc(o -> o.column(TestUserMysql::getName).column(TestUserMysql::getAge))
                .orderByDesc(o -> o.column(TestUserMysql::getName)).firstOrNull();


        TestUserMysql testUserMysql1 = easyQuery.query(TestUserMysql.class, "y")
                .where(o -> o.eq(TestUserMysql::getId, "102").like(TestUserMysql::getName, "1%").and(x ->
                        x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                ).or(x -> x.eq(TestUserMysql::getName, 456).eq(TestUserMysql::getId, 8989))).firstOrNull();

        SysUserLogbyMonth sysUserLogbyMonth = easyQuery.query(SysUserLogbyMonth.class)
                .where(o -> o.eq(SysUserLogbyMonth::getId, "119")).firstOrNull();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = dateTimeFormatter.format(sysUserLogbyMonth.getTime());
        System.out.println(sysUserLogbyMonth);
        TestUserMysqlx testUserMysql2 = easyQuery.query(TestUserMysql.class)
                .leftJoin(SysUserLogbyMonth.class, (a, b) -> a.eq(b, TestUserMysql::getName, SysUserLogbyMonth::getId).then(b).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                .where(o -> o.eq(TestUserMysql::getId, "102")
                        .like(TestUserMysql::getName, "1%")
                        .and(x -> x.like(TestUserMysql::getName, "123").or().eq(TestUserMysql::getAge, 1)
                        ))
                .select(TestUserMysqlx.class, x -> x.columnAll().columnAs(TestUserMysql::getName, TestUserMysqlx::getName1)).firstOrNull();

        TestUserMysqlx testUserMysqlx = easyQuery.query(SysUserLogbyMonth.class)
                .leftJoin(TestUserMysql.class, (a, b) -> a.eq(b, SysUserLogbyMonth::getId, TestUserMysql::getName).eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                .where(o -> o.eq(SysUserLogbyMonth::getId, "102")
                        .like(SysUserLogbyMonth::getTime, LocalDateTime.now())
                        .and(x -> x.like(SysUserLogbyMonth::getId, "123").or().eq(SysUserLogbyMonth::getTime, LocalDateTime.now()))
                ).select(TestUserMysqlx.class, x -> x.columnAll().columnAs(SysUserLogbyMonth::getId, TestUserMysqlx::getName1)).firstOrNull();
        System.out.println("Hello world!");

    }
}