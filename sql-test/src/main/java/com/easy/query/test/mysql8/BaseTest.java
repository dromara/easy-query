package com.easy.query.test.mysql8;

import com.bestvike.linq.Linq;
import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.entity.EntityMappingRule;
import com.easy.query.core.basic.entity.PropertyFirstEntityMappingRule;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
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
import com.easy.query.test.mysql8.entity.M8Child;
import com.easy.query.test.mysql8.entity.M8Comment;
import com.easy.query.test.mysql8.entity.M8Parent;
import com.easy.query.test.mysql8.entity.M8ParentChild;
import com.easy.query.test.mysql8.entity.QueryInterceptor;
import com.easy.query.test.mysql8.entity.bank.SysBank;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import com.easy.query.test.mysql8.entity.bank.SysUser;
import com.easy.query.test.mysql8.entity.bank.SysUserBook;
import com.easy.query.test.mysql8.entity.many.M8Area;
import com.easy.query.test.mysql8.entity.many.M8AreaBuild;
import com.easy.query.test.mysql8.entity.many.M8AreaBuildLicense;
import com.easy.query.test.mysql8.entity.many.M8City;
import com.easy.query.test.mysql8.entity.many.M8Province;
import com.zaxxer.hikari.HikariDataSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
                    op.setRelationGroupSize(50);
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
        queryConfiguration.applyInterceptor(new QueryInterceptor());
        easyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);
        beforex();
    }

    public static void beforex() {
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
//        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(SysUser.class,SysBank.class, SysBankCard.class,  SysUserBook.class, M8Comment.class));
//        codeFirstCommand.executeWithTransaction(s -> s.commit());
        CodeFirstCommand codeFirstCommand2 = databaseCodeFirst.dropTableIfExistsCommand(Arrays.asList(SysUser.class, SysBankCard.class, SysBank.class, SysUserBook.class, M8Comment.class, M8Parent.class, M8Child.class, M8ParentChild.class,
                M8Province.class, M8City.class, M8Area.class, M8AreaBuild.class, TreeA.class, TreeB.class));
        codeFirstCommand2.executeWithTransaction(s -> s.commit());

        CodeFirstCommand codeFirstCommand1 = databaseCodeFirst.syncTableCommand(Arrays.asList(SysUser.class, SysBank.class, SysBankCard.class, SysUserBook.class, M8Comment.class, M8Parent.class, M8Child.class, M8ParentChild.class,
                M8Province.class, M8City.class, M8Area.class, M8AreaBuild.class,M8AreaBuildLicense.class, TreeA.class, TreeB.class));
        codeFirstCommand1.executeWithTransaction(s -> {
            System.out.println(s.getSQL());
            s.commit();
        });
        easyEntityQuery.deletable(SysBankCard.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(SysBank.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(SysUser.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8Comment.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8Parent.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8Child.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8ParentChild.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8Province.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8City.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8Area.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8AreaBuild.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8AreaBuildLicense.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        ArrayList<SysBank> banks = new ArrayList<>();
        ArrayList<SysBankCard> bankCards = new ArrayList<>();
        ArrayList<SysUser> users = new ArrayList<>();
        ArrayList<SysUserBook> userBooks = new ArrayList<>();
        ArrayList<M8Comment> comments = new ArrayList<>();
        ArrayList<M8Province> provinces = new ArrayList<>();
        ArrayList<M8City> cities = new ArrayList<>();
        ArrayList<M8Area> areas = new ArrayList<>();
        ArrayList<M8AreaBuild> builds = new ArrayList<>();
        ArrayList<M8AreaBuildLicense> buildLicenses = new ArrayList<>();
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

        ArrayList<M8Parent> parents = new ArrayList<>();
        ArrayList<M8Child> children = new ArrayList<>();
        ArrayList<M8ParentChild> parentChildren = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            M8Parent m8Parent = new M8Parent();
            m8Parent.setId(UUID.randomUUID().toString());
            m8Parent.setName("name" + i);
            m8Parent.setOrder(i);
            parents.add(m8Parent);
            M8ParentChild m8ParentChild = new M8ParentChild();
            m8ParentChild.setId(UUID.randomUUID().toString());
            m8ParentChild.setParentId(m8Parent.getId());
            m8ParentChild.setChildId(m8Parent.getId());
            parentChildren.add(m8ParentChild);
            for (int j = 0; j < 300; j++) {
                M8Child m8Child = new M8Child();
                m8Child.setId(UUID.randomUUID().toString());
                m8Child.setParentId(m8Parent.getId());
                m8Child.setName("name" + i);
                m8Child.setOrder(i);
                children.add(m8Child);

            }
        }
        {
            for (int i = 15; i < 30; i++) {

                M8Parent m8Parent = parents.get(i);
                for (int j = 2; j < 213; j++) {
                    M8Child child = children.get(j);
                    M8ParentChild m8ParentChild = new M8ParentChild();
                    m8ParentChild.setId(UUID.randomUUID().toString());
                    m8ParentChild.setParentId(m8Parent.getId());
                    m8ParentChild.setChildId(child.getId());
                    parentChildren.add(m8ParentChild);
                }
            }
        }
        {

            M8Province m8Province = new M8Province();
            m8Province.setId("p1");
            m8Province.setName("浙江");
            provinces.add(m8Province);
            {

                M8City m8City = new M8City();
                m8City.setId("c1");
                m8City.setPid(m8Province.getId());
                m8City.setName("杭州");
                cities.add(m8City);
                {

                    M8Area m8Area = new M8Area();
                    m8Area.setId("a1");
                    m8Area.setCid(m8City.getId());
                    m8Area.setName("上城区");
                    areas.add(m8Area);
                    {
                        M8AreaBuild m8AreaBuild = new M8AreaBuild();
                        m8AreaBuild.setId("b1");
                        m8AreaBuild.setAid(m8Area.getId());
                        m8AreaBuild.setName("科创楼");
                        builds.add(m8AreaBuild);
                    }
                    {
                        M8AreaBuild m8AreaBuild = new M8AreaBuild();
                        m8AreaBuild.setId("b2");
                        m8AreaBuild.setAid(m8Area.getId());
                        m8AreaBuild.setName("创新楼");
                        builds.add(m8AreaBuild);
                        M8AreaBuildLicense m8AreaBuildLicense = new M8AreaBuildLicense();
                        m8AreaBuildLicense.setId("l1");
                        m8AreaBuildLicense.setBuildId(m8AreaBuild.getId());
                        m8AreaBuildLicense.setNo("license123");
                        buildLicenses.add(m8AreaBuildLicense);
                    }
                }
                {

                    M8Area m8Area = new M8Area();
                    m8Area.setId("a2");
                    m8Area.setCid(m8City.getId());
                    m8Area.setName("下城区");
                    areas.add(m8Area);
                    {
                        M8AreaBuild m8AreaBuild = new M8AreaBuild();
                        m8AreaBuild.setId("b3");
                        m8AreaBuild.setAid(m8Area.getId());
                        m8AreaBuild.setName("创业楼");
                        builds.add(m8AreaBuild);
                    }
                }
            }
            {

                M8City m8City = new M8City();
                m8City.setId("c2");
                m8City.setPid(m8Province.getId());
                m8City.setName("宁波");
                cities.add(m8City);
                {

                    M8Area m8Area = new M8Area();
                    m8Area.setId("a3");
                    m8Area.setCid(m8City.getId());
                    m8Area.setName("宁波A");
                    areas.add(m8Area);
                }
                {

                    M8Area m8Area = new M8Area();
                    m8Area.setId("a4");
                    m8Area.setCid(m8City.getId());
                    m8Area.setName("宁波B");
                    areas.add(m8Area);
                }
            }
        }


        easyEntityQuery.insertable(banks).executeRows();
        easyEntityQuery.insertable(bankCards).executeRows();
        easyEntityQuery.insertable(users).executeRows();
        easyEntityQuery.insertable(userBooks).executeRows();
        easyEntityQuery.insertable(comments).executeRows();
        easyEntityQuery.insertable(parents).batch().executeRows();
        easyEntityQuery.insertable(provinces).batch().executeRows();
        easyEntityQuery.insertable(cities).batch().executeRows();
        easyEntityQuery.insertable(areas).batch().executeRows();
        easyEntityQuery.insertable(builds).batch().executeRows();
        easyEntityQuery.insertable(buildLicenses).batch().executeRows();
        easyEntityQuery.insertable(Linq.of(parentChildren).orderBy(s -> s.getId()).toList()).batch().executeRows();
        easyEntityQuery.insertable(Linq.of(children).orderBy(s -> s.getId()).toList()).batch().executeRows();

    }

}
