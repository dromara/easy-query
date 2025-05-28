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
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.core.draft.Draft4;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.mssql.config.MsSQLDatabaseConfiguration;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.oracle.config.OracleDatabaseConfiguration;
import com.easy.query.pgsql.config.PgSQLDatabaseConfiguration;
import com.easy.query.test.common.EmptyDataSource;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.entity.Topic;
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
public class StringFunctionsTest {
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
    public void concatMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {

            List<String> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        Expression expression = bank_card.expression();
                        //列+常量
                        bank_card.code().concat("nextCode").eq("aaaa");
                        //列+列
                        bank_card.code().concat(bank_card.type()).eq("123");

//                        bank_card.code().appendFormat("是银行卡编号").eq("123");
                        //常量+列
                        expression.constant("这张银行卡编号:").concat(bank_card.code()).eq("123");
                        //自定义格式化
                        expression.stringFormat("这张银行卡编号:{0},类型为:{1}", bank_card.code(), bank_card.type()).eq("这张银行卡编号:123,类型为:456");
                    })
                    .selectColumn(bank_card -> bank_card.expression().stringFormat("这张银行卡编号:{0},类型为:{1}", bank_card.code(), bank_card.type()))
                    .toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT CONCAT(?,t.`code`,?,t.`type`) FROM `doc_bank_card` t WHERE CONCAT(t.`code`,?) = ? AND CONCAT(t.`code`,t.`type`) = ? AND CONCAT(?,t.`code`) = ? AND CONCAT(?,t.`code`,?,t.`type`) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("这张银行卡编号:(String),,类型为:(String),nextCode(String),aaaa(String),123(String),这张银行卡编号:(String),123(String),这张银行卡编号:(String),,类型为:(String),这张银行卡编号:123,类型为:456(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void concatMSSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MsSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<String> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        Expression expression = bank_card.expression();
                        //列+常量
                        bank_card.code().concat("nextCode").eq("aaaa");
                        //列+列
                        bank_card.code().concat(bank_card.type()).eq("123");
                        //常量+列
                        expression.constant("这张银行卡编号:").concat(bank_card.code()).eq("123");
                        //自定义格式化
                        expression.stringFormat("这张银行卡编号:{0},类型为:{1}", bank_card.code(), bank_card.type()).eq("这张银行卡编号:123,类型为:456");
                    })
                    .selectColumn(bank_card -> bank_card.expression().stringFormat("这张银行卡编号:{0},类型为:{1}", bank_card.code(), bank_card.type()))
                    .toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT CONCAT(?,t.[code],?,t.[type]) FROM [doc_bank_card] t WHERE CONCAT(t.[code],?) = ? AND CONCAT(t.[code],t.[type]) = ? AND CONCAT(?,t.[code]) = ? AND CONCAT(?,t.[code],?,t.[type]) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("这张银行卡编号:(String),,类型为:(String),nextCode(String),aaaa(String),123(String),这张银行卡编号:(String),123(String),这张银行卡编号:(String),,类型为:(String),这张银行卡编号:123,类型为:456(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void concatPGSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<String> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        Expression expression = bank_card.expression();
                        //列+常量
                        bank_card.code().concat("nextCode").eq("aaaa");
                        //列+列
                        bank_card.code().concat(bank_card.type()).eq("123");
                        //常量+列
                        expression.constant("这张银行卡编号:").concat(bank_card.code()).eq("123");
                        //自定义格式化
                        expression.stringFormat("这张银行卡编号:{0},类型为:{1}", bank_card.code(), bank_card.type()).eq("这张银行卡编号:123,类型为:456");
                    })
                    .selectColumn(bank_card -> bank_card.expression().stringFormat("这张银行卡编号:{0},类型为:{1}", bank_card.code(), bank_card.type()))
                    .toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT CONCAT(?,t.\"code\",?,t.\"type\") FROM \"doc_bank_card\" t WHERE CONCAT(t.\"code\",?) = ? AND CONCAT(t.\"code\",t.\"type\") = ? AND CONCAT(?,t.\"code\") = ? AND CONCAT(?,t.\"code\",?,t.\"type\") = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("这张银行卡编号:(String),,类型为:(String),nextCode(String),aaaa(String),123(String),这张银行卡编号:(String),123(String),这张银行卡编号:(String),,类型为:(String),这张银行卡编号:123,类型为:456(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void concatORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<String> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        Expression expression = bank_card.expression();
                        //列+常量
                        bank_card.code().concat("nextCode").eq("aaaa");
                        //列+列
                        bank_card.code().concat(bank_card.type()).eq("123");
                        //常量+列
                        expression.constant("这张银行卡编号:").concat(bank_card.code()).eq("123");
                        //自定义格式化
                        expression.stringFormat("这张银行卡编号:{0},类型为:{1}", bank_card.code(), bank_card.type()).eq("这张银行卡编号:123,类型为:456");
                    })
                    .selectColumn(bank_card -> bank_card.expression().stringFormat("这张银行卡编号:{0},类型为:{1}", bank_card.code(), bank_card.type()))
                    .toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT ? || t.\"code\" || ? || t.\"type\" FROM \"doc_bank_card\" t WHERE t.\"code\" || ? = ? AND t.\"code\" || t.\"type\" = ? AND ? || t.\"code\" = ? AND ? || t.\"code\" || ? || t.\"type\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("这张银行卡编号:(String),,类型为:(String),nextCode(String),aaaa(String),123(String),这张银行卡编号:(String),123(String),这张银行卡编号:(String),,类型为:(String),这张银行卡编号:123,类型为:456(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }







    @Test
    public void lowerUpperMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {

            List<Draft2<String, String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().toLower(),
                            bank_card.id().toUpper()
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT LOWER(t.`id`) AS `value1`,UPPER(t.`id`) AS `value2` FROM `doc_bank_card` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void lowerUpperMSSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MsSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft2<String, String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().toLower(),
                            bank_card.id().toUpper()
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT LOWER(t.[id]) AS [value1],UPPER(t.[id]) AS [value2] FROM [doc_bank_card] t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void lowerUpperPGSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft2<String, String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().toLower(),
                            bank_card.id().toUpper()
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT LOWER(t.\"id\") AS \"value1\",UPPER(t.\"id\") AS \"value2\" FROM \"doc_bank_card\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void lowerUpperORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft2<String, String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().toLower(),
                            bank_card.id().toUpper()
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT LOWER(t.\"id\") AS \"value1\",UPPER(t.\"id\") AS \"value2\" FROM \"doc_bank_card\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }



    @Test
    public void subStringMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {

            List<Draft2<String, String>> list = easyEntityQuery.queryable(Topic.class)
                    .select(topic -> Select.DRAFT.of(
                            topic.id().subString(0,2),
                            topic.id().subString(0,topic.stars())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUBSTR(t.`id`,1,2) AS `value1`,SUBSTR(t.`id`,1,t.`stars`) AS `value2` FROM `t_topic` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void subStringMSSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MsSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft2<String, String>> list = easyEntityQuery.queryable(Topic.class)
                    .select(topic -> Select.DRAFT.of(
                            topic.id().subString(0,2),
                            topic.id().subString(0,topic.stars())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUBSTRING(t.[id],1,2) AS [value1],SUBSTRING(t.[id],1,t.[stars]) AS [value2] FROM [t_topic] t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void subStringPGSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {


            List<Draft2<String, String>> list = easyEntityQuery.queryable(Topic.class)
                    .select(topic -> Select.DRAFT.of(
                            topic.id().subString(0,2),
                            topic.id().subString(0,topic.stars())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUBSTR(t.\"id\",1,2) AS \"value1\",SUBSTR(t.\"id\",1,t.\"stars\") AS \"value2\" FROM \"t_topic\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void subStringORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {


            List<Draft2<String, String>> list = easyEntityQuery.queryable(Topic.class)
                    .select(topic -> Select.DRAFT.of(
                            topic.id().subString(0,2),
                            topic.id().subString(0,topic.stars())
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT SUBSTR(t.\"id\",1,2) AS \"value1\",SUBSTR(t.\"id\",1,t.\"stars\") AS \"value2\" FROM \"t_topic\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("noCode(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }







    @Test
    public void trimMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {

            List<Draft1<String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().trim().eq("123");
                        bank_card.code().ltrim().eq("123");
                        bank_card.code().rtrim().eq("123");
                    })
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().trim()
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT TRIM(t.`id`) AS `value1` FROM `doc_bank_card` t WHERE TRIM(t.`code`) = ? AND LTRIM(t.`code`) = ? AND RTRIM(t.`code`) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void trimMSSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MsSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft1<String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().trim().eq("123");
                        bank_card.code().ltrim().eq("123");
                        bank_card.code().rtrim().eq("123");
                    })
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().trim()
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT LTRIM(RTRIM(t.[id])) AS [value1] FROM [doc_bank_card] t WHERE LTRIM(RTRIM(t.[code])) = ? AND LTRIM(t.[code]) = ? AND RTRIM(t.[code]) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void trimPGSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft1<String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().trim().eq("123");
                        bank_card.code().ltrim().eq("123");
                        bank_card.code().rtrim().eq("123");
                    })
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().trim()
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT TRIM(t.\"id\") AS \"value1\" FROM \"doc_bank_card\" t WHERE TRIM(t.\"code\") = ? AND LTRIM(t.\"code\") = ? AND RTRIM(t.\"code\") = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void trimORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft1<String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().trim().eq("123");
                        bank_card.code().ltrim().eq("123");
                        bank_card.code().rtrim().eq("123");
                    })
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().trim()
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT TRIM(t.\"id\") AS \"value1\" FROM \"doc_bank_card\" t WHERE TRIM(t.\"code\") = ? AND LTRIM(t.\"code\") = ? AND RTRIM(t.\"code\") = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }





    @Test
    public void replaceMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {

            List<Draft1<String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().replace("旧值","新值").eq("123");
                    })
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().replace("oldValue","newValue")
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT REPLACE(t.`id`,?,?) AS `value1` FROM `doc_bank_card` t WHERE REPLACE(t.`code`,?,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("oldValue(String),newValue(String),旧值(String),新值(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void replaceMSSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MsSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft1<String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().replace("旧值","新值").eq("123");
                    })
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().replace("oldValue","newValue")
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT REPLACE(t.[id],?,?) AS [value1] FROM [doc_bank_card] t WHERE REPLACE(t.[code],?,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("oldValue(String),newValue(String),旧值(String),新值(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void replacePGSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft1<String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().replace("旧值","新值").eq("123");
                    })
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().replace("oldValue","newValue")
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT REPLACE(t.\"id\",?,?) AS \"value1\" FROM \"doc_bank_card\" t WHERE REPLACE(t.\"code\",?,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("oldValue(String),newValue(String),旧值(String),新值(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void replaceORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Draft1<String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().replace("旧值","新值").eq("123");
                    })
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.id().replace("oldValue","newValue")
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT REPLACE(t.\"id\",?,?) AS \"value1\" FROM \"doc_bank_card\" t WHERE REPLACE(t.\"code\",?,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("oldValue(String),newValue(String),旧值(String),新值(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }




    @Test
    public void leftRightPadMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {

            List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().leftPad(4).eq("123");
                        bank_card.code().leftPad(4, ' ').eq("123");
                        bank_card.code().rightPad(4).eq("123");
                        bank_card.code().rightPad(4, ' ').eq("123");
                    }).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`uid`,`code`,`type`,`bank_id` FROM `doc_bank_card` WHERE LPAD(`code`, 4) = ? AND LPAD(`code`, 4, ?) = ? AND RPAD(`code`, 4) = ? AND RPAD(`code`, 4, ?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String), (String),123(String),123(String), (String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void leftRightPadMSSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MsSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().leftPad(4).eq("123");
                        bank_card.code().leftPad(4, ' ').eq("123");
                        bank_card.code().rightPad(4).eq("123");
                        bank_card.code().rightPad(4, ' ').eq("123");
                    }).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT [id],[uid],[code],[type],[bank_id] FROM [doc_bank_card] WHERE LPAD([code], 4) = ? AND LPAD([code], 4, ?) = ? AND RPAD([code], 4) = ? AND RPAD([code], 4, ?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String), (String),123(String),123(String), (String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void leftRightPadPGSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().leftPad(4).eq("123");
                        bank_card.code().leftPad(4, ' ').eq("123");
                        bank_card.code().rightPad(4).eq("123");
                        bank_card.code().rightPad(4, ' ').eq("123");
                    }).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT \"id\",\"uid\",\"code\",\"type\",\"bank_id\" FROM \"doc_bank_card\" WHERE LPAD(\"code\", 4) = ? AND LPAD(\"code\", 4, ?) = ? AND RPAD(\"code\", 4) = ? AND RPAD(\"code\", 4, ?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String), (String),123(String),123(String), (String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void leftRightPadORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().leftPad(4).eq("123");
                        bank_card.code().leftPad(4, ' ').eq("123");
                        bank_card.code().rightPad(4).eq("123");
                        bank_card.code().rightPad(4, ' ').eq("123");
                    }).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT \"id\",\"uid\",\"code\",\"type\",\"bank_id\" FROM \"doc_bank_card\" WHERE LPAD(\"code\", 4, ' ') = ? AND LPAD(\"code\", 4, ?) = ? AND RPAD(\"code\", 4, ' ') = ? AND RPAD(\"code\", 4, ?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String), (String),123(String),123(String), (String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void joining2MySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {

            List<Draft4<String, String, String, String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .groupBy(bank_card -> GroupKeys.of(bank_card.code()))
                    .select(group -> Select.DRAFT.of(
                            group.key1(),
                            //银行卡按编号分组，并且分组后将类型是是储蓄卡，用户姓名，用逗号分隔
                            group.where(o -> o.type().eq("储蓄卡")).distinct().joining(x -> x.user().name(), ","),
                            //简单暴力将银行id用逗号分隔
                            group.groupTable().bankId().joining(",",true),
                            group.groupTable().bankId().joining(",",true).filter(() -> group.groupTable().type().eq("储蓄卡"))
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`code` AS `value1`,GROUP_CONCAT(DISTINCT (CASE WHEN t.`type` = ? THEN t1.`name` ELSE NULL END) SEPARATOR ?) AS `value2`,GROUP_CONCAT(DISTINCT t.`bank_id` SEPARATOR ?) AS `value3`,GROUP_CONCAT(DISTINCT (CASE WHEN t.`type` = ? THEN t.`bank_id` ELSE NULL END) SEPARATOR ?) AS `value4` FROM `doc_bank_card` t LEFT JOIN `doc_user` t1 ON t1.`id` = t.`uid` GROUP BY t.`code`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("储蓄卡(String),,(String),,(String),储蓄卡(String),,(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void joiningMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {

            List<Draft3<String, String, String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .groupBy(bank_card -> GroupKeys.of(bank_card.code()))
                    .select(group -> Select.DRAFT.of(
                            group.key1(),
                            //银行卡按编号分组，并且分组后将类型是是储蓄卡，用户姓名，用逗号分隔
                            group.where(o -> o.type().eq("储蓄卡")).distinct().joining(x -> x.user().name(), ","),
                            //简单暴力将银行id用逗号分隔
                            group.groupTable().bankId().joining(",",true)
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`code` AS `value1`,GROUP_CONCAT(DISTINCT (CASE WHEN t.`type` = ? THEN t1.`name` ELSE NULL END) SEPARATOR ?) AS `value2`,GROUP_CONCAT(DISTINCT t.`bank_id` SEPARATOR ?) AS `value3` FROM `doc_bank_card` t LEFT JOIN `doc_user` t1 ON t1.`id` = t.`uid` GROUP BY t.`code`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("储蓄卡(String),,(String),,(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void joiningMSSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MsSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft3<String, String, String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .groupBy(bank_card -> GroupKeys.of(bank_card.code()))
                    .select(group -> Select.DRAFT.of(
                            group.key1(),
                            //银行卡按编号分组，并且分组后将类型是是储蓄卡，用户姓名，用逗号分隔
                            group.where(o -> o.type().eq("储蓄卡")).distinct().joining(x -> x.user().name(), ","),
                            //简单暴力将银行id用逗号分隔
                            group.groupTable().bankId().joining(",",true)
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.[code] AS [value1],STRING_AGG(DISTINCT (CASE WHEN t.[type] = ? THEN t1.[name] ELSE NULL END), ?) AS [value2],STRING_AGG(DISTINCT t.[bank_id], ?) AS [value3] FROM [doc_bank_card] t LEFT JOIN [doc_user] t1 ON t1.[id] = t.[uid] GROUP BY t.[code]", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("储蓄卡(String),,(String),,(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void joiningPGSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft3<String, String, String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .groupBy(bank_card -> GroupKeys.of(bank_card.code()))
                    .select(group -> Select.DRAFT.of(
                            group.key1(),
                            //银行卡按编号分组，并且分组后将类型是是储蓄卡，用户姓名，用逗号分隔
                            group.where(o -> o.type().eq("储蓄卡")).distinct().joining(x -> x.user().name(), ","),
                            //简单暴力将银行id用逗号分隔
                            group.groupTable().bankId().joining(",",true)
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.\"code\" AS \"value1\",STRING_AGG(DISTINCT ((CASE WHEN t.\"type\" = ? THEN t1.\"name\" ELSE NULL END))::TEXT, ?) AS \"value2\",STRING_AGG(DISTINCT (t.\"bank_id\")::TEXT, ?) AS \"value3\" FROM \"doc_bank_card\" t LEFT JOIN \"doc_user\" t1 ON t1.\"id\" = t.\"uid\" GROUP BY t.\"code\"", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("储蓄卡(String),,(String),,(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void joiningORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Draft3<String, String, String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .groupBy(bank_card -> GroupKeys.of(bank_card.code()))
                    .select(group -> Select.DRAFT.of(
                            group.key1(),
                            //银行卡按编号分组，并且分组后将类型是是储蓄卡，用户姓名，用逗号分隔
                            group.where(o -> o.type().eq("储蓄卡")).distinct().joining(x -> x.user().name(), ","),
                            //简单暴力将银行id用逗号分隔
                            group.groupTable().bankId().joining(",",true)
                    )).toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.\"code\" AS \"value1\",LISTAGG(DISTINCT TO_CHAR((CASE WHEN t.\"type\" = ? THEN t1.\"name\" ELSE NULL END)), ?) WITHIN GROUP(ORDER BY 1) AS \"value2\",LISTAGG(DISTINCT TO_CHAR(t.\"bank_id\"), ?) WITHIN GROUP(ORDER BY t.\"id\") AS \"value3\" FROM \"doc_bank_card\" t LEFT JOIN \"doc_user\" t1 ON t1.\"id\" = t.\"uid\" GROUP BY t.\"code\"", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("储蓄卡(String),,(String),,(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }





    @Test
    public void lengthMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {

            List<Integer> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().length().eq(2);
                    }).selectColumn(bank_card -> bank_card.code().length())
                    .toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT CHAR_LENGTH(t.`code`) FROM `doc_bank_card` t WHERE CHAR_LENGTH(t.`code`) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void lengthMSSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MsSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Integer> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().length().eq(2);
                    }).selectColumn(bank_card -> bank_card.code().length())
                    .toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT LEN(t.[code]) FROM [doc_bank_card] t WHERE LEN(t.[code]) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void lengthPGSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Integer> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().length().eq(2);
                    }).selectColumn(bank_card -> bank_card.code().length())
                    .toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT CHAR_LENGTH(t.\"code\") FROM \"doc_bank_card\" t WHERE CHAR_LENGTH(t.\"code\") = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void lengthORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Integer> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().length().eq(2);
                    }).selectColumn(bank_card -> bank_card.code().length())
                    .toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT LENGTH(t.\"code\") FROM \"doc_bank_card\" t WHERE LENGTH(t.\"code\") = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }



    @Test
    public void compareToMySQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MySQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();

        try {

            List<Integer> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().compareTo("1").eq(1);
                    }).selectColumn(bank_card -> bank_card.code().compareTo(bank_card.type()))
                    .toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT STRCMP(t.`code`,t.`type`) FROM `doc_bank_card` t WHERE STRCMP(t.`code`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void compareToMSSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new MsSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Integer> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().compareTo("1").eq(1);
                    }).selectColumn(bank_card -> bank_card.code().compareTo(bank_card.type()))
                    .toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (CASE WHEN t.[code] = t.[type] THEN 0 WHEN t.[code] > t.[type] THEN 1 ELSE -1 END) FROM [doc_bank_card] t WHERE (CASE WHEN t.[code] = ? THEN 0 WHEN t.[code] > ? THEN 1 ELSE -1 END) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String),1(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void compareToPGSQL() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new PgSQLDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {
            List<Integer> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().compareTo("1").eq(1);
                    }).selectColumn(bank_card -> bank_card.code().compareTo(bank_card.type()))
                    .toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (CASE WHEN t.\"code\" = t.\"type\" THEN 0 WHEN t.\"code\" > t.\"type\" THEN 1 ELSE -1 END) FROM \"doc_bank_card\" t WHERE (CASE WHEN t.\"code\" = ? THEN 0 WHEN t.\"code\" > ? THEN 1 ELSE -1 END) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String),1(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void compareToORACLE() {
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        EasyEntityQuery easyEntityQuery = create(listenerContextManager, new OracleDatabaseConfiguration());
        listenerContextManager.startCreateListen();


        try {

            List<Integer> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.code().compareTo("1").eq(1);
                    }).selectColumn(bank_card -> bank_card.code().compareTo(bank_card.type()))
                    .toList();
        } catch (Exception ignored) {

        }
        ListenerContext listenerContext = listenerContextManager.getListenContext();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (CASE WHEN t.\"code\" = t.\"type\" THEN 0 WHEN t.\"code\" > t.\"type\" THEN 1 ELSE -1 END) FROM \"doc_bank_card\" t WHERE (CASE WHEN t.\"code\" = ? THEN 0 WHEN t.\"code\" > ? THEN 1 ELSE -1 END) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String),1(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
}
