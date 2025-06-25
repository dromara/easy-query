package com.easy.query.test.search;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.CodeFirstCommandTxArg;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.search.EasySearch;
import com.easy.query.search.EasySearchConfiguration;
import com.easy.query.search.SearchInjectConfiguration;
import com.easy.query.search.meta.EasySearchMetaDataManager;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * EasySearch单元测试基类
 *
 * @author bkbits
 */
@Slf4j
public abstract class EasySearchBaseTest {
    protected static final EasyQueryClient easyQueryClient;
    protected static final EasyEntityQuery easyEntityQuery;
    protected static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    protected static final Date currentDate;
    protected static final HikariDataSource dataSource;

    static {
        try {
            currentDate = dateFormat.parse("2025-06-15 12:00:00");

            LogFactory.useStdOutLogging();
            dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(
                    "jdbc:mysql://127.0.0.1:3306/easy-query-test?serverTimezone=GMT%2B8" +
                            "&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true" +
                            "&rewriteBatchedStatements=true");
            dataSource.setUsername("root");
            dataSource.setPassword("root");
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            dataSource.setMaximumPoolSize(20);

            easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                    .setDefaultDataSource(dataSource)
                    .optionConfigure(op -> {
                        op.setDeleteThrowError(true);
                        op.setDefaultDataSourceName("dt");
                        op.setReverseOffsetThreshold(10);
                        op.setPrintSql(true);
                    })
                    .useDatabaseConfigure(new MySQLDatabaseConfiguration())
                    .customConfigure(s -> {
                        SearchInjectConfiguration.configure(s);
                    })
                    .build();
            easyEntityQuery = new DefaultEasyEntityQuery(EasySearchBaseTest.easyQueryClient);
            EasySearchConfiguration configuration =
                    EasySearchBaseTest.easyEntityQuery.getRuntimeContext()
                            .getService(
                                    EasySearchMetaDataManager.class)
                            .getConfiguration();

            StringBuilder opBuilder = new StringBuilder();
            configuration.getAllOp().forEach((k, v) -> {
                opBuilder.append(k).append("=").append(v.getClass().getSimpleName()).append("\n");
            });
            log.info("ops: \n{}", opBuilder.toString());

            DatabaseCodeFirst codeFirst = EasySearchBaseTest.easyQueryClient.getDatabaseCodeFirst();
            List<Class<?>> tableClasses = Arrays.asList(SysUser.class, SysUserExt.class);
            codeFirst.dropTableIfExistsCommand(tableClasses).executeWithTransaction(
                    CodeFirstCommandTxArg::commit);
            codeFirst.createTableCommand(tableClasses)
                    .executeWithTransaction(CodeFirstCommandTxArg::commit);

            List<SysUser> users = new ArrayList<>();
            List<SysUserExt> userExts = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                SysUser user = new SysUser();
                user.setUserId(String.valueOf(100 + i));
                user.setUserName("user_" + (100 + i));
                user.setCreateTime(EasySearchBaseTest.dateFormat.parse(EasySearchBaseTest.getDate((i / 10) - 5)));
                SysUserExt sysUserExt = new SysUserExt();
                sysUserExt.setUserId(user.getUserId());
                sysUserExt.setMobile("mobile_" + i);
                if (i % 5 == 0) {
                    user.setDeleteTime(
                            EasySearchBaseTest.dateFormat.parse(EasySearchBaseTest.getDate((i / 10) - 2))
                    );
                }

                users.add(user);
                userExts.add(sysUserExt);
            }
            try (Transaction transaction = EasySearchBaseTest.easyQueryClient.beginTransaction()) {
                EasySearchBaseTest.easyQueryClient.insertable(users).batch().executeRows();
                EasySearchBaseTest.easyQueryClient.insertable(userExts).batch().executeRows();
                transaction.commit();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取日期参数值
     *
     * @param day 参数值+-天数
     * @return 日期字符串
     */
    protected static String getDate(int day) {
        if (day == 0) {
            return dateFormat.format(new Date());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, day);
        return dateFormat.format(calendar.getTime());
    }


    /**
     * 测试运算符
     *
     * @param property      属性名
     * @param op            运算符
     * @param expectedCount 期望结果数
     * @param values        参数值
     */
    protected void testOp(
            String property, String op, long expectedCount, Object first,
            Object... values
    ) {
        EasySearch easySearch = EasySearch.of(SysUser.class)
                .param(property, first, values)
                .param(property + "-op", op);

        long count = easyEntityQuery.queryable(SysUser.class)
                .whereObject(easySearch)
                .orderByObject(easySearch)
                .count();
        Assert.assertEquals(expectedCount, count);
    }
}
