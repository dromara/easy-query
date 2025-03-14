package com.easy.query.test.mysql8;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.doc.entity.DocBank;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.doc.entity.DocUserBook;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/3/6 11:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class ManyJoinTest extends BaseTest{
    @Before
    public void before(){
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(DocBankCard.class, DocBank.class, DocUser.class, DocUserBook.class));
        codeFirstCommand.executeWithTransaction(s->s.commit());
        {
            easyEntityQuery.deletable(DocBankCard.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(DocBank.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(DocUser.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(DocUserBook.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
        }
    }

    @Test
    public void manyGroupTest1(){
        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().any();
                }).toList();
    }


    @Test
    public void testElement4(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().where(o -> o.type().eq("123")).element(0).bankId().eq("123");
                    user.bankCards().where(o -> o.type().eq("123")).element(0).type().eq("123");
                    user.bankCards().element(2).type().eq("123");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t2.`id` AS `id`,t2.`uid` AS `uid`,t2.`code` AS `code`,t2.`type` AS `type`,t2.`bank_id` AS `bank_id` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY 1 = 1)) AS `__row__` FROM `doc_bank_card` t1 WHERE t1.`type` = ?) t2 WHERE t2.`__row__` = ?) t4 ON t4.`uid` = t.`id` LEFT JOIN (SELECT t6.`id` AS `id`,t6.`uid` AS `uid`,t6.`code` AS `code`,t6.`type` AS `type`,t6.`bank_id` AS `bank_id` FROM (SELECT t5.`id`,t5.`uid`,t5.`code`,t5.`type`,t5.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t5.`uid` ORDER BY 1 = 1)) AS `__row__` FROM `doc_bank_card` t5) t6 WHERE t6.`__row__` = ?) t8 ON t8.`uid` = t.`id` WHERE t4.`bank_id` = ? AND t4.`type` = ? AND t8.`type` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),3(Integer),123(String),123(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testElement5(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().orderBy(x->{
                        x.code().nullOrDefault("x").desc();
                    }).element(2).type().eq("123");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t2.`id` AS `id`,t2.`uid` AS `uid`,t2.`code` AS `code`,t2.`type` AS `type`,t2.`bank_id` AS `bank_id` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY IFNULL(t1.`code`,?) DESC)) AS `__row__` FROM `doc_bank_card` t1) t2 WHERE t2.`__row__` = ?) t4 ON t4.`uid` = t.`id` WHERE t4.`type` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("x(String),3(Integer),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testElement6(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().orderBy(x->{
                        x.code().nullOrDefault("x").desc();
                    }).element(2).type().eq("123");
                }).orderBy(user -> {
                    user.bankCards().orderBy(x->{
                        x.code().nullOrDefault("x").desc();
                    }).element(2).type().asc();
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t2.`id` AS `id`,t2.`uid` AS `uid`,t2.`code` AS `code`,t2.`type` AS `type`,t2.`bank_id` AS `bank_id` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY IFNULL(t1.`code`,?) DESC)) AS `__row__` FROM `doc_bank_card` t1) t2 WHERE t2.`__row__` = ?) t4 ON t4.`uid` = t.`id` WHERE t4.`type` = ? ORDER BY t4.`type` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("x(String),3(Integer),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testElement7(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, String>> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().orderBy(x -> {
                        x.code().nullOrDefault("x").desc();
                    }).element(2).type().eq("123");
                }).orderBy(user -> {
                    user.bankCards().orderBy(x -> {
                        x.code().nullOrDefault("x").desc();
                    }).element(2).type().asc();
                }).select(user -> Select.DRAFT.of(
                        user.bankCards().orderBy(x -> {
                            x.code().nullOrDefault("x").desc();
                        }).element(2).type(),
                        user.bankCards().orderBy(x -> {
                            x.code().nullOrDefault("x").desc();
                        }).element(2).code()
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t4.`type` AS `value1`,t4.`code` AS `value2` FROM `doc_user` t LEFT JOIN (SELECT t2.`id` AS `id`,t2.`uid` AS `uid`,t2.`code` AS `code`,t2.`type` AS `type`,t2.`bank_id` AS `bank_id` FROM (SELECT t1.`id`,t1.`uid`,t1.`code`,t1.`type`,t1.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t1.`uid` ORDER BY IFNULL(t1.`code`,?) DESC)) AS `__row__` FROM `doc_bank_card` t1) t2 WHERE t2.`__row__` = ?) t4 ON t4.`uid` = t.`id` WHERE t4.`type` = ? ORDER BY t4.`type` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("x(String),3(Integer),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void testElement3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

            List<Draft2<String, String>> list = easyEntityQuery.queryable(DocUser.class)
                    .manyJoin(x -> x.bankCards())
                    .select(user -> Select.DRAFT.of(
                            user.bankCards().where(o -> o.type().eq("123")).max(x -> x.code()),
                            user.bankCards().where(o -> o.type().eq("123")).element(0).type()
                    )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`__max2__` AS `value1`,t6.`type` AS `value2` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,MAX((CASE WHEN t1.`type` = ? THEN t1.`code` ELSE ? END)) AS `__max2__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` LEFT JOIN (SELECT t4.`id` AS `id`,t4.`uid` AS `uid`,t4.`code` AS `code`,t4.`type` AS `type`,t4.`bank_id` AS `bank_id` FROM (SELECT t3.`id`,t3.`uid`,t3.`code`,t3.`type`,t3.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t3.`uid` ORDER BY 1 = 1)) AS `__row__` FROM `doc_bank_card` t3 WHERE t3.`type` = ?) t4 WHERE t4.`__row__` = ?) t6 ON t6.`uid` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),null(null),123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
}
