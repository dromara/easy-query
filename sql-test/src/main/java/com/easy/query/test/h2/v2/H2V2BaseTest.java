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
import com.easy.query.core.common.ValueHolder;
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
import org.junit.Assert;
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
        ValueHolder<String> sqlValueHolder=new ValueHolder<>();
        codeFirstCommand1.executeWithTransaction(s -> {
            System.out.println(s.getSQL());
            sqlValueHolder.setValue(s.getSQL());
            s.commit();
        });

        String value = sqlValueHolder.getValue();

        Assert.assertEquals("\n" +
                "CREATE TABLE IF NOT EXISTS \"t_all_type\" ( \n" +
                "\"id\" VARCHAR(255) NOT NULL  PRIMARY KEY ,\n" +
                "\"number_decimal\" DECIMAL(16,2) NULL ,\n" +
                "\"number_float\" FLOAT NULL ,\n" +
                "\"number_double\" DOUBLE NULL ,\n" +
                "\"number_short\" SMALLINT NULL ,\n" +
                "\"number_integer\" INT NULL ,\n" +
                "\"number_long\" BIGINT NULL ,\n" +
                "\"number_byte\" TINYINT NULL ,\n" +
                "\"time_local_date_time\" TIMESTAMP(3) NULL ,\n" +
                "\"time_local_date\" DATE NULL ,\n" +
                "\"time_local_time\" TIME NULL ,\n" +
                "\"only_date\" TIMESTAMP(3) NULL ,\n" +
                "\"sql_date\" TIMESTAMP(3) NULL ,\n" +
                "\"only_time\" TIME NULL ,\n" +
                "\"enable\" BOOLEAN NULL ,\n" +
                "\"value\" VARCHAR(255) NULL ,\n" +
                "\"uid\" UUID NULL ,\n" +
                "\"number_float_basic\" FLOAT NOT NULL  DEFAULT 0,\n" +
                "\"number_double_basic\" DOUBLE NOT NULL  DEFAULT 0,\n" +
                "\"number_short_basic\" SMALLINT NOT NULL  DEFAULT 0,\n" +
                "\"number_integer_basic\" INT NOT NULL  DEFAULT 0,\n" +
                "\"number_long_basic\" BIGINT NOT NULL  DEFAULT 0,\n" +
                "\"number_byte_basic\" TINYINT NOT NULL  DEFAULT 0,\n" +
                "\"enable_basic\" BOOLEAN NOT NULL  DEFAULT 0\n" +
                ");\n" +
                "CREATE TABLE IF NOT EXISTS \"t_topic\" ( \n" +
                "\"id\" VARCHAR(255) NOT NULL  PRIMARY KEY ,\n" +
                "\"stars\" INT NULL ,\n" +
                "\"title\" VARCHAR(255) NULL ,\n" +
                "\"create_time\" TIMESTAMP(3) NULL ,\n" +
                "\"alias\" VARCHAR(255) NULL \n" +
                ");\n" +
                "CREATE TABLE IF NOT EXISTS \"t_blog\" ( \n" +
                "\"id\" VARCHAR(255) NOT NULL  PRIMARY KEY ,\n" +
                "\"create_time\" TIMESTAMP(3) NULL  COMMENT '创建时间;创建时间',\n" +
                "\"update_time\" TIMESTAMP(3) NULL  COMMENT '修改时间;修改时间',\n" +
                "\"create_by\" VARCHAR(255) NULL  COMMENT '创建人;创建人',\n" +
                "\"update_by\" VARCHAR(255) NULL  COMMENT '修改人;修改人',\n" +
                "\"deleted\" BOOLEAN NULL  COMMENT '是否删除;是否删除',\n" +
                "\"title\" VARCHAR(255) NULL ,\n" +
                "\"content\" VARCHAR(255) NULL ,\n" +
                "\"url\" VARCHAR(255) NULL ,\n" +
                "\"star\" INT NULL ,\n" +
                "\"publish_time\" TIMESTAMP(3) NULL ,\n" +
                "\"score\" DECIMAL(16,2) NULL ,\n" +
                "\"status\" INT NULL ,\n" +
                "\"order\" DECIMAL(16,2) NULL ,\n" +
                "\"is_top\" BOOLEAN NULL ,\n" +
                "\"top\" BOOLEAN NULL \n" +
                ");",value);
    }
}
