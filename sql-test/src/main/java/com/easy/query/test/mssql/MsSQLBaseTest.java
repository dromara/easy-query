package com.easy.query.test.mssql;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.bean.PropertyDescriptorMatcher;
import com.easy.query.core.configuration.bean.entity.EntityPropertyDescriptorMatcher;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UpperCamelCaseNameConversion;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.util.EasyDatabaseUtil;
import com.easy.query.mssql.config.MsSQLDatabaseConfiguration;
import com.easy.query.test.entity.MathTest;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import com.easy.query.test.mssql.entity.MsSQLMyTopic;
import com.zaxxer.hikari.HikariDataSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * create time 2023/7/27 17:27
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class MsSQLBaseTest {
    public static HikariDataSource dataSource;
    public static EasyEntityQuery entityQuery;
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
        dataSource.setJdbcUrl("jdbc:sqlserver://localhost:1433;database=mssql_eq;useBulkCopyForBatchInsert=true;");
        dataSource.setUsername("sa");
        dataSource.setPassword("Password.1");
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setMaximumPoolSize(20);
//        postgres://postgres:postgrespw@localhost:55000
    }

    public static void initEasyQuery() {
        listenerContextManager=new ListenerContextManager();
        MyJdbcListener myJdbcListener = new MyJdbcListener(listenerContextManager);
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                    op.setMssqlMinBigDecimalScale(19);
                })
                .useDatabaseConfigure(new MsSQLDatabaseConfiguration())
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
                .replaceService(NameConversion.class, UpperCamelCaseNameConversion.class)
                .replaceService(PropertyDescriptorMatcher.class, EntityPropertyDescriptorMatcher.class)
//                .replaceService(SQLCaseWhenBuilderFactory.class, MyMsSQLCaseWhenBuilderFactory.class)
//                .replaceService(BeanValueCaller.class, ReflectBeanValueCaller.class)
                .build();
        entityQuery = new DefaultEasyEntityQuery(easyQueryClient);


        Set<String> docBankCard = EasyDatabaseUtil.getTableForeignKeys(dataSource, "doc_bank_card");
        System.out.println(docBankCard);

    }

    public static void initData() {


        boolean topicAny = entityQuery.queryable(MsSQLMyTopic.class).any();
        if (!topicAny) {
            List<MsSQLMyTopic> topics = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                MsSQLMyTopic topic = new MsSQLMyTopic();
                topic.setId(String.valueOf(i));
                topic.setStars(i + 100);
                topic.setTitle("标题" + i);
                topic.setCreateTime(LocalDateTime.now().plusDays(i));
                topics.add(topic);
            }
            long l = entityQuery.insertable(topics).executeRows();
        }

        DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
        {

            CodeFirstCommand codeFirstCommand = databaseCodeFirst.dropTableIfExistsCommand(Arrays.asList(MathTest.class));
            codeFirstCommand.executeWithTransaction(s->s.commit());
        }
        {

            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(MathTest.class));
            codeFirstCommand.executeWithTransaction(s->s.commit());
        }

        int size = 10000;
        List<MathTest> list = new ArrayList<>(size);
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < size; i++) {
            MathTest entity = new MathTest();
            entity.setId(UUID.randomUUID().toString());

            // 随机生成 -1000.00 到 1000.00 的 BigDecimal
            double value = random.nextDouble(-1000, 1000);
            BigDecimal bigDecimalValue = BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);

            entity.setTestValue(bigDecimalValue);

            list.add(entity);
        }

        entityQuery.insertable(list).batch().executeRows();
    }


}