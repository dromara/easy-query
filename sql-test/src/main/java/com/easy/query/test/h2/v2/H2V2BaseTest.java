package com.easy.query.test.h2.v2;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.entity.EntityMappingRule;
import com.easy.query.core.basic.entity.PropertyFirstEntityMappingRule;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.enums.IncludeLimitModeEnum;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.h2.config.H2DatabaseConfiguration;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.h2.domain.ALLTYPE;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import org.junit.Assert;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                .setName("mytest;MODE=MySQL")//建议强制mysql模式譬如insertOrUpdate或者InsertOrIgnore并没有很好的原生支持所以默认H2的方言采用mysql的语法
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
        CodeFirstCommand dropCommand = databaseCodeFirst.dropTableIfExistsCommand(Arrays.asList(ALLTYPE.class, Topic.class, BlogEntity.class));
        dropCommand.forEach(s -> s.commit());

        CodeFirstCommand syncCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(ALLTYPE.class, Topic.class, BlogEntity.class));
        List<String> statements = new ArrayList<>();
        syncCommand.forEach(s -> {
            System.out.println(s.getSQL());
            statements.add(s.getSQL());
            s.commit();
        });

        Assert.assertEquals(3, statements.size());
        Assert.assertTrue(statements.get(0).startsWith("CREATE TABLE IF NOT EXISTS \"t_all_type\""));
        Assert.assertTrue(statements.get(0).contains("\"number_decimal\" DECIMAL(16,2) NULL"));
        Assert.assertTrue(statements.get(0).contains("\"enable_basic\" BOOLEAN NOT NULL  DEFAULT 0"));
        Assert.assertTrue(statements.get(1).startsWith("CREATE TABLE IF NOT EXISTS \"t_topic\""));
        Assert.assertTrue(statements.get(2).startsWith("CREATE TABLE IF NOT EXISTS \"t_blog\""));
        Assert.assertTrue(statements.get(2).contains("\"create_time\" TIMESTAMP(3) NULL  COMMENT '创建时间;创建时间'"));
        Assert.assertTrue(databaseCodeFirst.tableExists(ALLTYPE.class));
        Assert.assertTrue(databaseCodeFirst.tableExists(Topic.class));
        Assert.assertTrue(databaseCodeFirst.tableExists(BlogEntity.class));
    }
}
