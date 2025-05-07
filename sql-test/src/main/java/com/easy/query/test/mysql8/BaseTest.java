package com.easy.query.test.mysql8;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.api4j.client.DefaultEasyQuery;
import com.easy.query.api4j.client.EasyQuery;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.entity.EntityMappingRule;
import com.easy.query.core.basic.entity.PropertyFirstEntityMappingRule;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.basic.jdbc.executor.DefaultEntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.pagination.EasyPageResultProvider;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.enums.IncludeLimitModeEnum;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.test.common.M8Interceptor;
import com.easy.query.test.common.MockEntityExpressionExecutor;
import com.easy.query.test.common.MyQueryConfiguration;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import com.easy.query.test.mypage.MyEasyPageResultProvider;
import com.easy.query.test.mysql8.entity.M8Comment;
import com.easy.query.test.mysql8.entity.bank.SysBank;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import com.easy.query.test.mysql8.entity.bank.SysUser;
import com.easy.query.test.mysql8.entity.bank.SysUserBook;
import com.easy.query.test.parser.MyLambdaParser;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

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
                    op.setDefaultTrack(true);
                    op.setIncludeLimitMode(IncludeLimitModeEnum.PARTITION);
                })
//                .replaceService(Column2MapKeyConversion.class, UpperColumn2MapKeyConversion.class)
                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
//                .replaceService(Dialect.class, DefaultDialect.class)
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
                .replaceService(QueryConfiguration.class, MyQueryConfiguration.class)
//                .replaceService(EntityMappingRule.class, PropertyEntityMappingRule.class)
                .replaceService(EntityMappingRule.class, PropertyFirstEntityMappingRule.class)
                .replaceService(EntityExpressionExecutor.class, MockEntityExpressionExecutor.class)
//                .replaceService(SQLKeyword.class, DefaultSQLKeyword.class)
//                .replaceService(BeanValueCaller.class, ReflectBeanValueCaller.class)
                .build();
        QueryConfiguration queryConfiguration = easyQueryClient.getRuntimeContext().getQueryConfiguration();
        queryConfiguration.applyRelationPropertyProvider(FindInSetRelationToImplicitProvider.INSTANCE);
        queryConfiguration.applyInterceptor(new M8Interceptor());
        easyQuery = new DefaultEasyQuery(easyQueryClient);
        easyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);
        beforex();
    }

    public static void beforex() {
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(SysUser.class,SysBank.class, SysBankCard.class,  SysUserBook.class, M8Comment.class));
        codeFirstCommand.executeWithTransaction(s -> s.commit());
        CodeFirstCommand codeFirstCommand2 = databaseCodeFirst.dropTableCommand(Arrays.asList(SysUser.class, SysBankCard.class, SysBank.class, SysUserBook.class, M8Comment.class));
        codeFirstCommand2.executeWithTransaction(s -> s.commit());

        CodeFirstCommand codeFirstCommand1 = databaseCodeFirst.syncTableCommand(Arrays.asList(SysUser.class, SysBank.class,  SysBankCard.class,SysUserBook.class, M8Comment.class));
        codeFirstCommand1.executeWithTransaction(s -> {
            System.out.println(s.getSQL());
            s.commit();
        });
        easyEntityQuery.deletable(SysBankCard.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(SysBank.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(SysUser.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8Comment.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        ArrayList<SysBank> banks = new ArrayList<>();
        ArrayList<SysBankCard> bankCards = new ArrayList<>();
        ArrayList<SysUser> users = new ArrayList<>();
        ArrayList<SysUserBook> userBooks = new ArrayList<>();
        ArrayList<M8Comment> comments = new ArrayList<>();
        {
            SysBank sysBank = new SysBank();
            sysBank.setId("1");
            sysBank.setName("工商银行");
            sysBank.setCreateTime(LocalDateTime.of(2000, 1, 1, 0, 0));
            banks.add(sysBank);

            {
                SysBankCard sysBankCard = new SysBankCard();
                sysBankCard.setId("bc1");
                sysBankCard.setUid("u1");
                sysBankCard.setCode("123");
                sysBankCard.setType("储蓄卡");
                sysBankCard.setBankId(sysBank.getId());
                sysBankCard.setOpenTime(LocalDateTime.of(2000, 1, 2, 0, 0));
                bankCards.add(sysBankCard);
            }
            {
                SysBankCard sysBankCard = new SysBankCard();
                sysBankCard.setId("bc2");
                sysBankCard.setUid("u1");
                sysBankCard.setCode("1234");
                sysBankCard.setType("信用卡");
                sysBankCard.setBankId(sysBank.getId());
                sysBankCard.setOpenTime(LocalDateTime.of(2000, 1, 2, 0, 0));
                bankCards.add(sysBankCard);
            }
            {
                SysBankCard sysBankCard = new SysBankCard();
                sysBankCard.setId("bc3");
                sysBankCard.setUid("u2");
                sysBankCard.setCode("1235");
                sysBankCard.setType("信用卡");
                sysBankCard.setBankId(sysBank.getId());
                sysBankCard.setOpenTime(LocalDateTime.of(2000, 1, 2, 0, 0));
                bankCards.add(sysBankCard);
            }
        }
        {
            SysBank sysBank = new SysBank();
            sysBank.setId("2");
            sysBank.setName("建设银行");
            sysBank.setCreateTime(LocalDateTime.of(2001, 1, 1, 0, 0));
            banks.add(sysBank);
            {
                SysBankCard sysBankCard = new SysBankCard();
                sysBankCard.setId("bc4");
                sysBankCard.setUid("u1");
                sysBankCard.setCode("1236");
                sysBankCard.setType("储蓄卡");
                sysBankCard.setBankId(sysBank.getId());
                sysBankCard.setOpenTime(LocalDateTime.of(2001, 1, 2, 0, 0));
                bankCards.add(sysBankCard);
            }
            {
                SysBankCard sysBankCard = new SysBankCard();
                sysBankCard.setId("bc5");
                sysBankCard.setCode("1237");
                sysBankCard.setType("储蓄卡");
                sysBankCard.setBankId(sysBank.getId());
                sysBankCard.setOpenTime(LocalDateTime.of(2001, 1, 2, 0, 0));
                bankCards.add(sysBankCard);
            }
        }
        {
            SysBank sysBank = new SysBank();
            sysBank.setId("3");
            sysBank.setName("招商银行");
            sysBank.setCreateTime(LocalDateTime.of(2002, 1, 1, 0, 0));
            banks.add(sysBank);
        }
        {
            SysUser sysUser = new SysUser();
            sysUser.setId("u1");
            sysUser.setName("用户1");
            sysUser.setPhone("123");
            sysUser.setAge(22);
            sysUser.setCreateTime(LocalDateTime.of(2012, 1, 1, 0, 0));
            users.add(sysUser);
        }
        {
            SysUser sysUser = new SysUser();
            sysUser.setId("u2");
            sysUser.setName("用户2");
            sysUser.setPhone("1234");
            sysUser.setAge(23);
            sysUser.setCreateTime(LocalDateTime.of(2012, 1, 1, 0, 0));
            users.add(sysUser);
        }
        {
            SysUserBook sysUserBook = new SysUserBook();
            sysUserBook.setId("b1");
            sysUserBook.setName("b1book");
            sysUserBook.setUid("u1");
            sysUserBook.setPrice(BigDecimal.valueOf(10));
            userBooks.add(sysUserBook);
        }
        {
            SysUserBook sysUserBook = new SysUserBook();
            sysUserBook.setId("b2");
            sysUserBook.setName("b2book");
            sysUserBook.setUid("u1");
            sysUserBook.setPrice(BigDecimal.valueOf(11));
            userBooks.add(sysUserBook);
        }
        {
            SysUserBook sysUserBook = new SysUserBook();
            sysUserBook.setId("b3");
            sysUserBook.setName("b3book");
            sysUserBook.setUid("u2");
            sysUserBook.setPrice(BigDecimal.valueOf(9.9));
            userBooks.add(sysUserBook);
        }

        {
            M8Comment m8Comment = new M8Comment();
            m8Comment.setId("1");
            m8Comment.setContent("我是主评论");
            m8Comment.setParentId(null);
            m8Comment.setUsername("小明");
            m8Comment.setTime(LocalDateTime.of(2020, 1, 1, 0, 0));
            comments.add(m8Comment);
            {


                M8Comment m8Comment1 = new M8Comment();
                m8Comment1.setId("2");
                m8Comment1.setContent("我是子评论");
                m8Comment1.setParentId("1");
                m8Comment1.setUsername("小明1");
                m8Comment1.setTime(LocalDateTime.of(2020, 1, 1, 1, 0));
                comments.add(m8Comment1);
            }
            {


                M8Comment m8Comment1 = new M8Comment();
                m8Comment1.setId("3");
                m8Comment1.setContent("我是子评论");
                m8Comment1.setParentId("1");
                m8Comment1.setUsername("小明2");
                m8Comment1.setTime(LocalDateTime.of(2020, 1, 1, 2, 0));
                comments.add(m8Comment1);
            }
            {


                M8Comment m8Comment1 = new M8Comment();
                m8Comment1.setId("4");
                m8Comment1.setContent("我是子评论");
                m8Comment1.setParentId("1");
                m8Comment1.setUsername("小明4");
                m8Comment1.setTime(LocalDateTime.of(2020, 1, 1, 3, 0));
                comments.add(m8Comment1);
            }
            {


                M8Comment m8Comment1 = new M8Comment();
                m8Comment1.setId("5");
                m8Comment1.setContent("我是子评论");
                m8Comment1.setParentId("1");
                m8Comment1.setUsername("小明5");
                m8Comment1.setTime(LocalDateTime.of(2020, 1, 1, 7, 0));
                comments.add(m8Comment1);
            }
        }

        easyEntityQuery.insertable(banks).executeRows();
        easyEntityQuery.insertable(bankCards).executeRows();
        easyEntityQuery.insertable(users).executeRows();
        easyEntityQuery.insertable(userBooks).executeRows();
        easyEntityQuery.insertable(comments).executeRows();

    }

}
