package com.easy.query;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.easy.query.core.abstraction.DefaultEasyQueryLambdaFactory;
import com.easy.query.core.abstraction.DefaultEasyQueryRuntimeContext;
import com.easy.query.core.abstraction.EasyQueryLambdaFactory;
import com.easy.query.core.abstraction.EasySqlApiFactory;
import com.easy.query.core.abstraction.metadata.EntityMetadataManager;
import com.easy.query.core.api.client.DefaultEasyQuery;
import com.easy.query.core.api.client.EasyQuery;
import com.easy.query.core.api.def.DefaultEasySqlApiFactory;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.basic.jdbc.con.DefaultConnectionManager;
import com.easy.query.core.basic.jdbc.con.EasyConnectionManager;
import com.easy.query.core.basic.jdbc.executor.DefaultEasyExecutor;
import com.easy.query.core.basic.jdbc.types.DefaultJdbcTypeHandlerManager;
import com.easy.query.core.basic.jdbc.types.EasyJdbcTypeHandlerManager;
import com.easy.query.core.config.DefaultConfig;
import com.easy.query.core.config.NameConversion;
import com.easy.query.core.config.UnderlinedNameConversion;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.metadata.DefaultEntityMetadataManager;
import com.easy.query.entity.BlogEntity;
import com.easy.query.mysql.MySqlExpressionFactory;
import com.easy.query.mysql.config.MySqlDialect;
import com.easy.query.test.NameQueryFilter;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @FileName: BaseTest.java
 * @Description: 基础单元测试类用于构建easy-query
 * @Date: 2023/3/16 16:47
 * @Created by xuejiaming
 */
public abstract class BaseTest {
    public static HikariDataSource dataSource;
    public static EasyQuery easyQuery;

    static {
        LogFactory.useStdOutLogging();
    }

    public BaseTest() {
        customInit();
    }

    @BeforeClass
    public static void init() {
        initDatasource();
        initEasyQuery();
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
        EasyQueryConfiguration configuration = new EasyQueryConfiguration();
        configuration.setNameConversion(new UnderlinedNameConversion());
        configuration.setDialect(new MySqlDialect());

        EntityMetadataManager entityMetadataManager = new DefaultEntityMetadataManager(configuration);
        EasyQueryLambdaFactory easyQueryLambdaFactory = new DefaultEasyQueryLambdaFactory();
        MySqlExpressionFactory mySQLSqlExpressionFactory = new MySqlExpressionFactory();
        EasySqlApiFactory easyQueryableFactory = new DefaultEasySqlApiFactory(mySQLSqlExpressionFactory);
        DefaultEasyQueryRuntimeContext jqdcRuntimeContext = new DefaultEasyQueryRuntimeContext(configuration, entityMetadataManager, easyQueryLambdaFactory, connectionManager, defaultExecutor, jdbcTypeHandler, easyQueryableFactory, mySQLSqlExpressionFactory);
////        jqdcRuntimeContext.getEasyQueryConfiguration().applyEntityTypeConfiguration(new TestUserMySqlConfiguration());
//        configuration.applyGlobalInterceptor(new NameQueryFilter());

        easyQuery = new DefaultEasyQuery(jqdcRuntimeContext);
    }

    public abstract void customInit();

}
