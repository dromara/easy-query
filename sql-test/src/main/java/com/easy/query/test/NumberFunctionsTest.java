package com.easy.query.test;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.core.draft.Draft4;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.mssql.config.MsSQLDatabaseConfiguration;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.oracle.config.OracleDatabaseConfiguration;
import com.easy.query.pgsql.config.PgSQLDatabaseConfiguration;
import com.easy.query.test.common.EmptyDataSource;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import com.easy.query.test.mysql8.entity.bank.SysUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * create time 2025/4/13 22:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class NumberFunctionsTest {

    @Before
    public void before() {
        LogFactory.useStdOutLogging();
    }

    public EasyEntityQuery create(ListenerContextManager listenerContextManager, DatabaseConfiguration databaseConfiguration) {
        MyJdbcListener myJdbcListener = new MyJdbcListener(listenerContextManager);
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(new EmptyDataSource())
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                })
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
                .useDatabaseConfigure(databaseConfiguration)
                .build();
        return new DefaultEasyEntityQuery(easyQueryClient);
    }

    @Test
    public void avgMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {

            List<Draft3<BigDecimal, BigDecimal, BigDecimal>> blogs = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().avg(),
                            t_blog.score().avg(true),
                            t_blog.score().avg(true).filter(() -> {
                                t_blog.title().like("小说");
                            })
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT AVG(t.`score`) AS `value1`,AVG(DISTINCT t.`score`) AS `value2`,AVG(DISTINCT (CASE WHEN t.`title` LIKE ? THEN t.`score` ELSE ? END)) AS `value3` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小说%(String),null(null),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void avgMSSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MsSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft3<BigDecimal, BigDecimal, BigDecimal>> blogs = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().avg(),
                            t_blog.score().avg(true),
                            t_blog.score().avg(true).filter(() -> {
                                t_blog.title().like("小说");
                            })
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT AVG(t.[score]) AS [value1],AVG(DISTINCT t.[score]) AS [value2],AVG(DISTINCT (CASE WHEN t.[title] LIKE ? THEN t.[score] ELSE ? END)) AS [value3] FROM [t_blog] t WHERE t.[deleted] = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小说%(String),null(null),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void avgPGSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft3<BigDecimal, BigDecimal, BigDecimal>> blogs = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().avg(),
                            t_blog.score().avg(true),
                            t_blog.score().avg(true).filter(() -> {
                                t_blog.title().like("小说");
                            })
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT AVG(t.\"score\") AS \"value1\",AVG(DISTINCT t.\"score\") AS \"value2\",AVG(DISTINCT (CASE WHEN t.\"title\" LIKE ? THEN t.\"score\" ELSE ? END)) AS \"value3\" FROM \"t_blog\" t WHERE t.\"deleted\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小说%(String),null(null),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void avgORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft3<BigDecimal, BigDecimal, BigDecimal>> blogs = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().avg(),
                            t_blog.score().avg(true),
                            t_blog.score().avg(true).filter(() -> {
                                t_blog.title().like("小说");
                            })
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT AVG(t.\"score\") AS \"value1\",AVG(DISTINCT t.\"score\") AS \"value2\",AVG(DISTINCT (CASE WHEN t.\"title\" LIKE ? THEN t.\"score\" ELSE ? END)) AS \"value3\" FROM \"t_blog\" t WHERE t.\"deleted\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小说%(String),null(null),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void sumMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {

            List<Draft4<Number, Integer, Long, BigDecimal>> blogs = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().sum(),
                            t_blog.score().sumInt(true),
                            t_blog.score().sumLong(true),
                            t_blog.score().sumBigDecimal(true).filter(() -> {
                                t_blog.title().like("小说");
                            })
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUM(t.`score`) AS `value1`,SUM(DISTINCT t.`score`) AS `value2`,SUM(DISTINCT t.`score`) AS `value3`,SUM(DISTINCT (CASE WHEN t.`title` LIKE ? THEN t.`score` ELSE ? END)) AS `value4` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小说%(String),0(Long),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void sumMSSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MsSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft4<Number, Integer, Long, BigDecimal>> blogs = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().sum(),
                            t_blog.score().sumInt(true),
                            t_blog.score().sumLong(true),
                            t_blog.score().sumBigDecimal(true).filter(() -> {
                                t_blog.title().like("小说");
                            })
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUM(t.[score]) AS [value1],SUM(DISTINCT t.[score]) AS [value2],SUM(DISTINCT t.[score]) AS [value3],SUM(DISTINCT (CASE WHEN t.[title] LIKE ? THEN t.[score] ELSE ? END)) AS [value4] FROM [t_blog] t WHERE t.[deleted] = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小说%(String),0(Long),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void sumPGSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft4<Number, Integer, Long, BigDecimal>> blogs = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().sum(),
                            t_blog.score().sumInt(true),
                            t_blog.score().sumLong(true),
                            t_blog.score().sumBigDecimal(true).filter(() -> {
                                t_blog.title().like("小说");
                            })
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUM(t.\"score\") AS \"value1\",SUM(DISTINCT t.\"score\") AS \"value2\",SUM(DISTINCT t.\"score\") AS \"value3\",SUM(DISTINCT (CASE WHEN t.\"title\" LIKE ? THEN t.\"score\" ELSE ? END)) AS \"value4\" FROM \"t_blog\" t WHERE t.\"deleted\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小说%(String),0(Long),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void sumORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft4<Number, Integer, Long, BigDecimal>> blogs = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().sum(),
                            t_blog.score().sumInt(true),
                            t_blog.score().sumLong(true),
                            t_blog.score().sumBigDecimal(true).filter(() -> {
                                t_blog.title().like("小说");
                            })
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUM(t.\"score\") AS \"value1\",SUM(DISTINCT t.\"score\") AS \"value2\",SUM(DISTINCT t.\"score\") AS \"value3\",SUM(DISTINCT (CASE WHEN t.\"title\" LIKE ? THEN t.\"score\" ELSE ? END)) AS \"value4\" FROM \"t_blog\" t WHERE t.\"deleted\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小说%(String),0(Long),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void addMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {
            //blogs
            List<Draft4<BigDecimal, Integer, Long, BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().add(1),
                            t_blog.score().add(1).asInteger(),
                            t_blog.score().add(1).asLong(),
                            t_blog.score().add(t_blog.score())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (t.`score` + ?) AS `value1`,(t.`score` + ?) AS `value2`,(t.`score` + ?) AS `value3`,(t.`score` + t.`score`) AS `value4` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),1(Integer),1(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void addMSSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MsSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft4<BigDecimal, Integer, Long, BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().add(1),
                            t_blog.score().add(1).asInteger(),
                            t_blog.score().add(1).asLong(),
                            t_blog.score().add(t_blog.score())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (t.[score] + ?) AS [value1],(t.[score] + ?) AS [value2],(t.[score] + ?) AS [value3],(t.[score] + t.[score]) AS [value4] FROM [t_blog] t WHERE t.[deleted] = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),1(Integer),1(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void addPGSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft4<BigDecimal, Integer, Long, BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().add(1),
                            t_blog.score().add(1).asInteger(),
                            t_blog.score().add(1).asLong(),
                            t_blog.score().add(t_blog.score())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (t.\"score\" + ?) AS \"value1\",(t.\"score\" + ?) AS \"value2\",(t.\"score\" + ?) AS \"value3\",(t.\"score\" + t.\"score\") AS \"value4\" FROM \"t_blog\" t WHERE t.\"deleted\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),1(Integer),1(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void addORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft4<BigDecimal, Integer, Long, BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().add(1),
                            t_blog.score().add(1).asInteger(),
                            t_blog.score().add(1).asLong(),
                            t_blog.score().add(t_blog.score())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (t.\"score\" + ?) AS \"value1\",(t.\"score\" + ?) AS \"value2\",(t.\"score\" + ?) AS \"value3\",(t.\"score\" + t.\"score\") AS \"value4\" FROM \"t_blog\" t WHERE t.\"deleted\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),1(Integer),1(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void subtractMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {
            //blogs
            List<Draft4<BigDecimal, Integer, Long, BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().subtract(1),
                            t_blog.score().subtract(1).asInteger(),
                            t_blog.score().subtract(1).asLong(),
                            t_blog.score().subtract(t_blog.score())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (t.`score` - ?) AS `value1`,(t.`score` - ?) AS `value2`,(t.`score` - ?) AS `value3`,(t.`score` - t.`score`) AS `value4` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),1(Integer),1(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void subtractMSSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MsSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft4<BigDecimal, Integer, Long, BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().subtract(1),
                            t_blog.score().subtract(1).asInteger(),
                            t_blog.score().subtract(1).asLong(),
                            t_blog.score().subtract(t_blog.score())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (t.[score] - ?) AS [value1],(t.[score] - ?) AS [value2],(t.[score] - ?) AS [value3],(t.[score] - t.[score]) AS [value4] FROM [t_blog] t WHERE t.[deleted] = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),1(Integer),1(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void subtractPGSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft4<BigDecimal, Integer, Long, BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().subtract(1),
                            t_blog.score().subtract(1).asInteger(),
                            t_blog.score().subtract(1).asLong(),
                            t_blog.score().subtract(t_blog.score())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (t.\"score\" - ?) AS \"value1\",(t.\"score\" - ?) AS \"value2\",(t.\"score\" - ?) AS \"value3\",(t.\"score\" - t.\"score\") AS \"value4\" FROM \"t_blog\" t WHERE t.\"deleted\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),1(Integer),1(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void subtractORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft4<BigDecimal, Integer, Long, BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().subtract(1),
                            t_blog.score().subtract(1).asInteger(),
                            t_blog.score().subtract(1).asLong(),
                            t_blog.score().subtract(t_blog.score())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (t.\"score\" - ?) AS \"value1\",(t.\"score\" - ?) AS \"value2\",(t.\"score\" - ?) AS \"value3\",(t.\"score\" - t.\"score\") AS \"value4\" FROM \"t_blog\" t WHERE t.\"deleted\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),1(Integer),1(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void divideMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {
            //blogs
            List<Draft2<BigDecimal, BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().divide(2),
                            t_blog.score().divide(t_blog.score())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (t.`score` / ?) AS `value1`,(t.`score` / t.`score`) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void divideMSSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MsSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft2<BigDecimal, BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().divide(2),
                            t_blog.score().divide(t_blog.score())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (t.[score] / ?) AS [value1],(t.[score] / t.[score]) AS [value2] FROM [t_blog] t WHERE t.[deleted] = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void dividePGSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft2<BigDecimal, BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().divide(2),
                            t_blog.score().divide(t_blog.score())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (t.\"score\" / ?) AS \"value1\",(t.\"score\" / t.\"score\") AS \"value2\" FROM \"t_blog\" t WHERE t.\"deleted\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void divideORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft2<BigDecimal, BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().divide(2),
                            t_blog.score().divide(t_blog.score())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (t.\"score\" / ?) AS \"value1\",(t.\"score\" / t.\"score\") AS \"value2\" FROM \"t_blog\" t WHERE t.\"deleted\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void multiplyMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {
            //blogs
            List<Draft2<BigDecimal, BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().multiply(2),
                            t_blog.score().multiply(t_blog.score())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (t.`score` * ?) AS `value1`,(t.`score` * t.`score`) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void multiplyMSSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MsSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft2<BigDecimal, BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().multiply(2),
                            t_blog.score().multiply(t_blog.score())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (t.[score] * ?) AS [value1],(t.[score] * t.[score]) AS [value2] FROM [t_blog] t WHERE t.[deleted] = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void multiplyPGSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft2<BigDecimal, BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().multiply(2),
                            t_blog.score().multiply(t_blog.score())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (t.\"score\" * ?) AS \"value1\",(t.\"score\" * t.\"score\") AS \"value2\" FROM \"t_blog\" t WHERE t.\"deleted\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void multiplyORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft2<BigDecimal, BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.score().multiply(2),
                            t_blog.score().multiply(t_blog.score())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (t.\"score\" * ?) AS \"value1\",(t.\"score\" * t.\"score\") AS \"value2\" FROM \"t_blog\" t WHERE t.\"deleted\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Integer),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void subQueryORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            easyEntityQuery.queryable(SysUser.class)
                    .subQueryToGroupJoin(o -> o.bankCards())
                    .where(t_blog -> {
                        t_blog.bankCards().count(s -> s.id()).gt(1L);
                        t_blog.bankCards().any();
                        t_blog.bankCards().where(bc->bc.uid().isNull()).any();
                        t_blog.bankCards().any(bc->bc.uid().isNull());
                        t_blog.bankCards().none();
                        t_blog.bankCards().where(bc->bc.uid().isNull()).none();
                        t_blog.bankCards().none(bc->bc.uid().isNull());
                    })
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.bankCards().where(u -> u.id().ne("1")).anyValue()
                    )).toList();
        } catch (Exception ignored) {
            System.out.println(ignored);
        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT NVL(t2.\"__any7__\",0) AS \"value1\" FROM \"t_sys_user\" t LEFT JOIN (SELECT t1.\"uid\" AS \"uid\",COUNT(t1.\"id\") AS \"__count2__\",(CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END) AS \"__any3__\",(CASE WHEN COUNT((CASE WHEN t1.\"uid\" IS NULL THEN ? ELSE ? END)) > 0 THEN 1 ELSE 0 END) AS \"__any4__\",(CASE WHEN COUNT(*) > 0 THEN 0 ELSE 1 END) AS \"__none5__\",(CASE WHEN COUNT((CASE WHEN t1.\"uid\" IS NULL THEN ? ELSE ? END)) > 0 THEN 0 ELSE 1 END) AS \"__none6__\",(CASE WHEN COUNT((CASE WHEN t1.\"id\" <> ? THEN ? ELSE ? END)) > 0 THEN 1 ELSE 0 END) AS \"__any7__\" FROM \"t_bank_card\" t1 GROUP BY t1.\"uid\") t2 ON t2.\"uid\" = t.\"id\" WHERE NVL(t2.\"__count2__\",0) > ? AND NVL(t2.\"__any3__\",0) = ? AND NVL(t2.\"__any4__\",0) = ? AND NVL(t2.\"__any4__\",0) = ? AND NVL(t2.\"__none5__\",1) = ? AND NVL(t2.\"__none6__\",1) = ? AND NVL(t2.\"__none6__\",1) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),null(null),1(Integer),null(null),1(String),1(Integer),null(null),1(Long),true(Boolean),true(Boolean),true(Boolean),true(Boolean),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void subQueryMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            easyEntityQuery.queryable(SysUser.class)
                    .subQueryToGroupJoin(o -> o.bankCards())
                    .where(t_blog -> {
                        t_blog.bankCards().count(s -> s.id()).gt(1L);
                        t_blog.bankCards().any();
                        t_blog.bankCards().where(bc->bc.uid().isNull()).any();
                        t_blog.bankCards().any(bc->bc.uid().isNull());
                        t_blog.bankCards().none();
                        t_blog.bankCards().where(bc->bc.uid().isNull()).none();
                        t_blog.bankCards().none(bc->bc.uid().isNull());
                    })
                    .select(t_blog -> Select.DRAFT.of(
                            t_blog.bankCards().where(u -> u.id().ne("1")).anyValue()
                    )).toList();
        } catch (Exception ignored) {
            System.out.println(ignored);
        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL(t2.`__any7__`,?) AS `value1` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,COUNT(t1.`id`) AS `__count2__`,(CASE WHEN COUNT(*) > 0 THEN ? ELSE ? END) AS `__any3__`,(CASE WHEN COUNT((CASE WHEN t1.`uid` IS NULL THEN ? ELSE ? END)) > 0 THEN ? ELSE ? END) AS `__any4__`,(CASE WHEN COUNT(*) > 0 THEN ? ELSE ? END) AS `__none5__`,(CASE WHEN COUNT((CASE WHEN t1.`uid` IS NULL THEN ? ELSE ? END)) > 0 THEN ? ELSE ? END) AS `__none6__`,(CASE WHEN COUNT((CASE WHEN t1.`id` <> ? THEN ? ELSE ? END)) > 0 THEN ? ELSE ? END) AS `__any7__` FROM `t_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE IFNULL(t2.`__count2__`,0) > ? AND IFNULL(t2.`__any3__`,?) = ? AND IFNULL(t2.`__any4__`,?) = ? AND IFNULL(t2.`__any4__`,?) = ? AND IFNULL(t2.`__none5__`,?) = ? AND IFNULL(t2.`__none6__`,?) = ? AND IFNULL(t2.`__none6__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),true(Boolean),false(Boolean),1(Integer),null(null),true(Boolean),false(Boolean),false(Boolean),true(Boolean),1(Integer),null(null),false(Boolean),true(Boolean),1(String),1(Integer),null(null),true(Boolean),false(Boolean),1(Long),false(Boolean),true(Boolean),false(Boolean),true(Boolean),false(Boolean),true(Boolean),true(Boolean),true(Boolean),true(Boolean),true(Boolean),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
}
