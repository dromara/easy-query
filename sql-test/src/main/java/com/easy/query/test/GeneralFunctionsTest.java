package com.easy.query.test;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.DatabaseConfiguration;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.kingbase.es.config.KingbaseESDatabaseConfiguration;
import com.easy.query.mssql.config.MsSQLDatabaseConfiguration;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.oracle.config.OracleDatabaseConfiguration;
import com.easy.query.pgsql.config.PgSQLDatabaseConfiguration;
import com.easy.query.sqlite.config.SQLiteDatabaseConfiguration;
import com.easy.query.test.common.EmptyDataSource;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import com.easy.query.test.mysql8.dto.MyComment3;
import com.easy.query.test.mysql8.entity.Comment;
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
    public void unionSQLite() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new SQLiteDatabaseConfiguration());


        EasyQueryClient easyQueryClient = easyEntityQuery.getEasyQueryClient();

        ClientQueryable<Topic> q1 = easyQueryClient
                .queryable(Topic.class)
                .where(o -> o.isNotNull("id"));
        ClientQueryable<Topic> q2 = easyQueryClient
                .queryable(Topic.class)
                .where(o -> o.isNull("id"));
        ClientQueryable<Topic> q3 = easyQueryClient
                .queryable(Topic.class)
                .where(o -> o.eq("id", "123"));

        String sql = q1.union(q2, q3).toSQL();
        System.out.println(sql);
        Assert.assertEquals("SELECT t3.\"id\",t3.\"stars\",t3.\"title\",t3.\"create_time\" FROM ( SELECT t.\"id\",t.\"stars\",t.\"title\",t.\"create_time\" FROM \"t_topic\" t WHERE t.\"id\" IS NOT NULL  UNION  SELECT t1.\"id\",t1.\"stars\",t1.\"title\",t1.\"create_time\" FROM \"t_topic\" t1 WHERE t1.\"id\" IS NULL  UNION  SELECT t2.\"id\",t2.\"stars\",t2.\"title\",t2.\"create_time\" FROM \"t_topic\" t2 WHERE t2.\"id\" = ? ) t3", sql);

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
        Assert.assertEquals("SELECT \"id\",\"uid\",\"code\",\"type\",\"bank_id\" FROM \"doc_bank_card\" WHERE \"type\" LIKE CONCAT('%',(?)::TEXT,'%') ESCAPE '\\'", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("30\\%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

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
        Assert.assertEquals("SELECT \"id\",\"uid\",\"code\",\"type\",\"bank_id\" FROM \"doc_bank_card\" WHERE \"type\" LIKE CONCAT((?)::TEXT,'%') ESCAPE '\\'", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("30\\%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

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
        Assert.assertEquals("SELECT \"id\",\"uid\",\"code\",\"type\",\"bank_id\" FROM \"doc_bank_card\" WHERE \"type\" LIKE CONCAT('%',(?)::TEXT) ESCAPE '\\'", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("30\\%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

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


    @Test
    public void testDoubleConcat() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new KingbaseESDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(t_blog -> {
                        t_blog.expression().stringFormat("你好:{0}我叫{1}你好吗!我今年{2}岁了,{3}你呢", t_blog.title(), t_blog.star(), t_blog.content(), 12)
                                .eq("比较一下");
                    }).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT \"id\",\"create_time\",\"update_time\",\"create_by\",\"update_by\",\"deleted\",\"title\",\"content\",\"url\",\"star\",\"publish_time\",\"score\",\"status\",\"order\",\"is_top\",\"top\" FROM \"t_blog\" WHERE \"deleted\" = ? AND CONCAT(CONCAT(CONCAT(CONCAT(CONCAT(CONCAT(CONCAT(CONCAT(?,\"title\"),?),\"star\"),?),\"content\"),?),?),?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),你好:(String),我叫(String),你好吗!我今年(String),岁了,(String),12(Integer),你呢(String),比较一下(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testKingbaseCte() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new KingbaseESDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {

            List<MyComment3> treeList = easyEntityQuery.queryable(Comment.class)
                    .asTreeCTE(o -> {
                        o.setUp(true);
                        o.setDeepColumnName("deep");
                    })
                    .selectAutoInclude(MyComment3.class)
                    .toTreeList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH RECURSIVE \"as_tree_cte\" AS ( (SELECT 0+0 AS \"deep\",t1.\"id\",t1.\"parent_id\",t1.\"content\",t1.\"user_id\",t1.\"post_id\",t1.\"create_at\" FROM \"t_comment\" t1)  UNION ALL  (SELECT t2.\"deep\" + 1 AS \"deep\",t3.\"id\",t3.\"parent_id\",t3.\"content\",t3.\"user_id\",t3.\"post_id\",t3.\"create_at\" FROM \"as_tree_cte\" t2 INNER JOIN \"t_comment\" t3 ON t3.\"id\" = t2.\"parent_id\") ) SELECT t.\"id\",t.\"parent_id\",t.\"content\",t.\"user_id\",t.\"post_id\",t.\"create_at\",t.\"deep\" FROM \"as_tree_cte\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());

    }


    @Test
    public void limitOrderTest1() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft2<String, Long>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.type().eq("123");
                    }).groupBy(bank_card -> GroupKeys.of(bank_card.id()))
                    .select(group -> Select.DRAFT.of(
                            group.key1(),
                            group.count()
                    ))
                    .limit(2, 3)
                    .orderBy(draft2 -> draft2.value2().asc())
                    .toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"value1\" AS \"value1\",t1.\"value2\" AS \"value2\" FROM (SELECT rt1.* FROM (SELECT rt.*, ROWNUM AS \"__rownum__\" FROM (SELECT t.\"id\" AS \"value1\",COUNT(*) AS \"value2\" FROM \"doc_bank_card\" t WHERE t.\"type\" = ? GROUP BY t.\"id\") rt WHERE ROWNUM < 6) rt1 WHERE rt1.\"__rownum__\" > 2) t1 ORDER BY t1.\"value2\" ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void limitOrderTest2() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft2<String, Long>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.type().eq("123");
                    }).groupBy(bank_card -> GroupKeys.of(bank_card.id()))
                    .select(group -> Select.DRAFT.of(
                            group.key1(),
                            group.count()
                    ))
                    .orderBy(draft2 -> draft2.value2().asc())
                    .limit(2, 3)
                    .toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT rt1.* FROM (SELECT rt.*, ROWNUM AS \"__rownum__\" FROM (SELECT t1.\"value1\" AS \"value1\",t1.\"value2\" AS \"value2\" FROM (SELECT t.\"id\" AS \"value1\",COUNT(*) AS \"value2\" FROM \"doc_bank_card\" t WHERE t.\"type\" = ? GROUP BY t.\"id\") t1 ORDER BY t1.\"value2\" ASC) rt WHERE ROWNUM < 6) rt1 WHERE rt1.\"__rownum__\" > 2", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
}
