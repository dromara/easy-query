package com.easy.query.test.kingbase;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.kingbase.es.config.KingbaseESDatabaseConfiguration;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.test.mysql8.entity.M8Child;
import com.easy.query.test.mysql8.entity.M8Parent;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/5/7 16:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyKingbaseESTest {
    @Test
    public void testaa(){
        LogFactory.useStdOutLogging();
        {

            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3316/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true");
            dataSource.setUsername("root");
            dataSource.setPassword("root");
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setMaximumPoolSize(20);

            EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                    .setDefaultDataSource(dataSource)
                    .optionConfigure(op -> {
                        op.setPrintSql(true);
                        op.setRelationGroupSize(1512);
                    })
                    .useDatabaseConfigure(new MySQLDatabaseConfiguration())
//                .replaceService(BeanValueCaller.class, ReflectBeanValueCaller.class)
                    .build();
            DefaultEasyEntityQuery entityQuery = new DefaultEasyEntityQuery(easyQueryClient);
            DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
            databaseCodeFirst.createDatabaseIfNotExists();
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(HelpEduSubjectEntity.class, HelpEduVideoEntity.class, HelpCategoryAndSubjectEntity.class));
            codeFirstCommand.executeWithTransaction(s->s.commit());
//
           List<HelpEduSubjectEntity> list = entityQuery.queryable(HelpEduSubjectEntity.class).includes(h -> h.helpEduVideoList())
                    .toList();
            for (HelpEduSubjectEntity helpEduSubjectEntity : list) {
                Integer firstOrder = null;
                for (HelpEduVideoEntity helpEduVideoEntity : helpEduSubjectEntity.getHelpEduVideoList()) {
                    if (firstOrder == null) {
                        firstOrder = helpEduVideoEntity.getOrderBy();
                    }
                    Assert.assertTrue(firstOrder <= helpEduVideoEntity.getOrderBy());
                    firstOrder = helpEduVideoEntity.getOrderBy();
                }
            }
        }
    }


    @Test
    public void testaa1(){
        LogFactory.useStdOutLogging();
        {

            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3316/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true");
            dataSource.setUsername("root");
            dataSource.setPassword("root");
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setMaximumPoolSize(20);

            EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                    .setDefaultDataSource(dataSource)
                    .optionConfigure(op -> {
                        op.setPrintSql(true);
                        op.setRelationGroupSize(512);
                    })
                    .useDatabaseConfigure(new MySQLDatabaseConfiguration())
//                .replaceService(BeanValueCaller.class, ReflectBeanValueCaller.class)
                    .build();
            DefaultEasyEntityQuery entityQuery = new DefaultEasyEntityQuery(easyQueryClient);
            DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
            databaseCodeFirst.createDatabaseIfNotExists();
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(HelpEduSubjectEntity.class, HelpEduVideoEntity.class, HelpCategoryAndSubjectEntity.class));
            codeFirstCommand.executeWithTransaction(s->s.commit());
//
            List<HelpEduSubjectEntity> list = entityQuery.queryable(HelpEduSubjectEntity.class).includes(h -> h.helpEduVideoList())
                    .toList();
            for (HelpEduSubjectEntity helpEduSubjectEntity : list) {
                Integer firstOrder = null;
                for (HelpEduVideoEntity helpEduVideoEntity : helpEduSubjectEntity.getHelpEduVideoList()) {
                    if (firstOrder == null) {
                        firstOrder = helpEduVideoEntity.getOrderBy();
                    }
                    Assert.assertTrue(firstOrder <= helpEduVideoEntity.getOrderBy());
                    firstOrder = helpEduVideoEntity.getOrderBy();
                }
            }
        }
    }
}
