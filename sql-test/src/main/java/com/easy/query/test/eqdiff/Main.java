package com.easy.query.test.eqdiff;

import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.test.common.EmptyDataSource;
import com.easy.query.test.common.MyQueryConfiguration;

/**
 * create time 2024/8/27 10:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class Main {
    public static void main(String[] args) {

        EasyQueryClient c1 = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(new EmptyDataSource())
                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
                .replaceService(JdbcExecutorListener.class, LogSlowJdbcListener.class)
                .replaceService(new NamedConfig("A"))
                .build();
        EasyQueryClient c2 = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(new EmptyDataSource())
                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
                .replaceService(new NamedConfig("B"))
                .replaceService(JdbcExecutorListener.class, LogSlowJdbcListener.class)
                .build();
        NamedConfig aName = c1.getRuntimeContext().getService(NamedConfig.class);
        System.out.println(aName.getName());
        NamedConfig bName = c2.getRuntimeContext().getService(NamedConfig.class);
        System.out.println(bName.getName());
    }
}
