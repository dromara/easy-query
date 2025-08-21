package com.easy.query.test.h2.v2;

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
import com.easy.query.h2.config.H2DatabaseConfiguration;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.test.common.M8Interceptor;
import com.easy.query.test.common.MockEntityExpressionExecutor;
import com.easy.query.test.common.MyQueryConfiguration;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.h2.domain.ALLTYPE;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import com.easy.query.test.mysql8.FindInSetRelationToImplicitProvider;
import com.easy.query.test.mysql8.TreeA;
import com.easy.query.test.mysql8.TreeB;
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
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * create time 2025/8/21 21:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class H2V2BaseTest {
    public static DataSource dataSource;
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

        dataSource = new EmbeddedDatabaseBuilder()
                .setName("mytest")
                .setType(EmbeddedDatabaseType.H2)
                .build();

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
                .useDatabaseConfigure(new H2DatabaseConfiguration())
//                .replaceService(Dialect.class, DefaultDialect.class)
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
//                .replaceService(EntityMappingRule.class, PropertyEntityMappingRule.class)
                .replaceService(EntityMappingRule.class, PropertyFirstEntityMappingRule.class)
//                .replaceService(SQLKeyword.class, DefaultSQLKeyword.class)
//                .replaceService(BeanValueCaller.class, ReflectBeanValueCaller.class)
                .build();
        easyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);
        beforex();
    }

    public static void beforex() {
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
//        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(SysUser.class,SysBank.class, SysBankCard.class,  SysUserBook.class, M8Comment.class));
//        codeFirstCommand.executeWithTransaction(s -> s.commit());
        CodeFirstCommand codeFirstCommand2 = databaseCodeFirst.dropTableIfExistsCommand(Arrays.asList(ALLTYPE.class, Topic.class, BlogEntity.class));
        codeFirstCommand2.executeWithTransaction(s -> s.commit());

        CodeFirstCommand codeFirstCommand1 = databaseCodeFirst.syncTableCommand(Arrays.asList(ALLTYPE.class, Topic.class, BlogEntity.class));
        codeFirstCommand1.executeWithTransaction(s -> {
            System.out.println(s.getSQL());
            s.commit();
        });

    }
}
