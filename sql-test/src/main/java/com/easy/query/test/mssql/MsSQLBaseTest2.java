package com.easy.query.test.mssql;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.bean.PropertyDescriptorMatcher;
import com.easy.query.core.configuration.bean.entity.EntityPropertyDescriptorMatcher;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.configuration.nameconversion.impl.UpperCamelCaseNameConversion;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.util.EasyDatabaseUtil;
import com.easy.query.mssql.config.MsSQLDatabaseConfiguration;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import com.easy.query.test.mssql.entity.MsSQLMyTopic;
import com.zaxxer.hikari.HikariDataSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * create time 2023/7/27 17:27
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class MsSQLBaseTest2 {
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
    }

    public static void initDatasource() {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:sqlserver://localhost:1433;database=mssql_eq");
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
                .replaceService(PropertyDescriptorMatcher.class, EntityPropertyDescriptorMatcher.class)
//                .replaceService(SQLCaseWhenBuilderFactory.class, MyMsSQLCaseWhenBuilderFactory.class)
//                .replaceService(BeanValueCaller.class, ReflectBeanValueCaller.class)
                .build();
        entityQuery = new DefaultEasyEntityQuery(easyQueryClient);



    }



}