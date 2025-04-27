package com.easy.query.test;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.api4j.client.DefaultEasyQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.configuration.bean.PropertyDescriptorMatcher;
import com.easy.query.core.configuration.bean.entity.EntityPropertyDescriptorMatcher;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.mssql.config.MsSQLDatabaseConfiguration;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.oracle.config.OracleDatabaseConfiguration;
import com.easy.query.pgsql.config.PgSQLDatabaseConfiguration;
import com.easy.query.sqlite.config.SQLiteDatabaseConfiguration;
import com.easy.query.test.common.EmptyDataSource;
import com.easy.query.test.common.LowerUnderlinedNameConversion;
import com.easy.query.test.common.MyQueryConfiguration;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.blogtest.SysUser;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * create time 2025/4/13 10:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class GeneralFunctionsTest {
    @Before
    public void before(){
        LogFactory.useStdOutLogging();
    }

    public EasyEntityQuery create(ListenerContextManager listenerContextManager, DatabaseConfiguration databaseConfiguration){
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
    public void nullOrDefaultMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {

            List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().nullOrDefault("noCode").eq("123");
                    }).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`uid`,`code`,`type`,`bank_id` FROM `doc_bank_card` WHERE IFNULL(`code`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void nullOrDefaultMSSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MsSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().nullOrDefault("noCode").eq("123");
                    }).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT [id],[uid],[code],[type],[bank_id] FROM [doc_bank_card] WHERE ISNULL([code],?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void nullOrDefaultPGSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().nullOrDefault("noCode").eq("123");
                    }).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT \"id\",\"uid\",\"code\",\"type\",\"bank_id\" FROM \"doc_bank_card\" WHERE COALESCE(\"code\",?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void nullOrDefaultORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().nullOrDefault("noCode").eq("123");
                    }).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT \"id\",\"uid\",\"code\",\"type\",\"bank_id\" FROM \"doc_bank_card\" WHERE NVL(\"code\",?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }




    @Test
    public void countMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {

            List<Draft2<Long, Long>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().count(),
                            bank_card.id().count(true)
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT COUNT(t.`id`) AS `value1`,COUNT(DISTINCT t.`id`) AS `value2` FROM `doc_bank_card` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void countMSSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MsSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft2<Long, Long>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().count(),
                            bank_card.id().count(true)
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT COUNT(t.[id]) AS [value1],COUNT(DISTINCT t.[id]) AS [value2] FROM [doc_bank_card] t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void countPGSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft2<Long, Long>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().count(),
                            bank_card.id().count(true)
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT COUNT(t.\"id\") AS \"value1\",COUNT(DISTINCT t.\"id\") AS \"value2\" FROM \"doc_bank_card\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void countORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft2<Long, Long>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().count(),
                            bank_card.id().count(true)
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT COUNT(t.\"id\") AS \"value1\",COUNT(DISTINCT t.\"id\") AS \"value2\" FROM \"doc_bank_card\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }








    @Test
    public void maxMinMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {

            List<Draft2<String, String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().max(),
                            bank_card.id().min()
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT MAX(t.`id`) AS `value1`,MIN(t.`id`) AS `value2` FROM `doc_bank_card` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void maxMinMSSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MsSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft2<String, String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().max(),
                            bank_card.id().min()
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT MAX(t.[id]) AS [value1],MIN(t.[id]) AS [value2] FROM [doc_bank_card] t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void maxMinPGSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft2<String, String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().max(),
                            bank_card.id().min()
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT MAX(t.\"id\") AS \"value1\",MIN(t.\"id\") AS \"value2\" FROM \"doc_bank_card\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void maxMinORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft2<String, String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().max(),
                            bank_card.id().min()
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT MAX(t.\"id\") AS \"value1\",MIN(t.\"id\") AS \"value2\" FROM \"doc_bank_card\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }





    @Test
    public void equalsWithMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {

            List<Draft1<Boolean>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().equalsWith("myCode").eq(false);
                    })
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().equalsWith("123")
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (CASE t.`id` WHEN ? THEN 1 ELSE 0 END) AS `value1` FROM `doc_bank_card` t WHERE (CASE t.`code` WHEN ? THEN 1 ELSE 0 END) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),myCode(String),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void equalsWithMSSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MsSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft1<Boolean>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().equalsWith("myCode").eq(false);
                    })
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().equalsWith("123")
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (CASE t.[id] WHEN ? THEN 1 ELSE 0 END) AS [value1] FROM [doc_bank_card] t WHERE (CASE t.[code] WHEN ? THEN 1 ELSE 0 END) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),myCode(String),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void equalsWithPGSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft1<Boolean>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().equalsWith("myCode").eq(false);
                    })
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().equalsWith("123")
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (CASE t.\"id\" WHEN ? THEN 1 ELSE 0 END) AS \"value1\" FROM \"doc_bank_card\" t WHERE (CASE t.\"code\" WHEN ? THEN 1 ELSE 0 END) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),myCode(String),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void equalsWithORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft1<Boolean>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().equalsWith("myCode").eq(false);
                    })
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().equalsWith("123")
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (CASE t.\"id\" WHEN ? THEN 1 ELSE 0 END) AS \"value1\" FROM \"doc_bank_card\" t WHERE (CASE t.\"code\" WHEN ? THEN 1 ELSE 0 END) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),myCode(String),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void countFilterMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {
            List<Draft3<Long, Long, Long>> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(t_blog -> {
                        t_blog.content().like("abc");
                    }).select(t_blog -> Select.DRAFT.of(
                            t_blog.id().count(),//count(id)
                            t_blog.title().count(true),//count(distinct title)
                            t_blog.title().count().filter(() -> {//title求不是恐怖的标题的数量
                                t_blog.title().ne("恐怖");
                            })
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT COUNT(t.`id`) AS `value1`,COUNT(DISTINCT t.`title`) AS `value2`,COUNT((CASE WHEN t.`title` <> ? THEN t.`title` ELSE NULL END)) AS `value3` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`content` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("恐怖(String),false(Boolean),%abc%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void minMaxFilterMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {
            easyEntityQuery.queryable(BlogEntity.class)
                    .where(t_blog -> {
                        t_blog.content().like("abc");
                    }).select(t_blog -> Select.DRAFT.of(
                            t_blog.score().max(),//max(id)
                            t_blog.score().max().filter(() -> {//title求不是恐怖的标题的最大分数
                                t_blog.title().ne("恐怖");
                            })
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT MAX(t.`score`) AS `value1`,MAX((CASE WHEN t.`title` <> ? THEN t.`score` ELSE NULL END)) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`content` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("恐怖(String),false(Boolean),%abc%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void likeSQLite() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new SQLiteDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.type().like(bank_card.expression().constant("30%"));
                    }).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT \"id\",\"uid\",\"code\",\"type\",\"bank_id\" FROM \"doc_bank_card\" WHERE INSTR(\"type\",?) > 0", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("30%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void likeSQLite2() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new SQLiteDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.type().likeMatchLeft(bank_card.expression().constant("30%"));
                    }).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT \"id\",\"uid\",\"code\",\"type\",\"bank_id\" FROM \"doc_bank_card\" WHERE INSTR(\"type\",?) = 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("30%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void likeSQLite3() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new SQLiteDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.type().likeMatchRight(bank_card.expression().constant("30%"));
                    }).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT \"id\",\"uid\",\"code\",\"type\",\"bank_id\" FROM \"doc_bank_card\" WHERE INSTR(\"type\",?) = (LENGTH(\"type\") - LENGTH(?) + 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("30%(String),30%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    
    
    
    
    
    
    //...............

    @Test
    public void likePgSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.type().like(bank_card.expression().constant("30%"));
                    }).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT \"id\",\"uid\",\"code\",\"type\",\"bank_id\" FROM \"doc_bank_card\" WHERE STRPOS(\"type\",?) > 0", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("30%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void likePgSQL2() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.type().likeMatchLeft(bank_card.expression().constant("30%"));
                    }).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT \"id\",\"uid\",\"code\",\"type\",\"bank_id\" FROM \"doc_bank_card\" WHERE STRPOS(\"type\",?) = 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("30%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void likePgSQL3() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.type().likeMatchRight(bank_card.expression().constant("30%"));
                    }).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT \"id\",\"uid\",\"code\",\"type\",\"bank_id\" FROM \"doc_bank_card\" WHERE STRPOS(\"type\",?) = (CHAR_LENGTH(\"type\") - CHAR_LENGTH(?) + 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("30%(String),30%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }




    //...............

    @Test
    public void likeOracle() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.type().like(bank_card.expression().constant("30%"));
                    }).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT \"id\",\"uid\",\"code\",\"type\",\"bank_id\" FROM \"doc_bank_card\" WHERE INSTR(\"type\",?,1,1) > 0", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("30%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void likeOracle2() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.type().likeMatchLeft(bank_card.expression().constant("30%"));
                    }).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT \"id\",\"uid\",\"code\",\"type\",\"bank_id\" FROM \"doc_bank_card\" WHERE INSTR(\"type\",?,1,1) = 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("30%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void likeOracle3() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.type().likeMatchRight(bank_card.expression().constant("30%"));
                    }).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT \"id\",\"uid\",\"code\",\"type\",\"bank_id\" FROM \"doc_bank_card\" WHERE INSTR(\"type\",?,1,1) = (LENGTH(\"type\") - LENGTH(?) + 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("30%(String),30%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
}
