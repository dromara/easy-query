package com.easy.query.test.pgsql;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

/**
 * create time 2025/2/22 23:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class DeleteUpdateJoinTest extends PgSQLBaseTest{


    @Test
    public void testUpdateJoin1(){
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        entityQuery.updatable(DocBankCard.class)
                .setColumns(bank_card -> {
                    bank_card.type().set(bank_card.user().name());
                })
                .where(bank_card -> {
                    bank_card.user().name().like("123");
                }).executeRows();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE \"doc_bank_card\" t SET \"type\" = t1.\"name\" FROM \"doc_user\" t1 WHERE t1.\"id\" = t.\"uid\" AND t1.\"name\" LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testUpdateJoin2(){
//        entityQuery.sqlExecute("UPDATE \"doc_bank_card\" t SET \"type\" = t1.\"name\" FROM \"doc_bank\" t1 INNER JOIN \"doc_user\" t2 ON t2.\"id\" = t.\"id\" WHERE t1.\"id\" = t.\"bank_id\" AND t2.\"name\" LIKE '123'");
        entityQuery.sqlExecute("UPDATE \"doc_bank_card\" t SET \"type\" = t1.\"name\" FROM \"doc_bank\" t1 , \"doc_user\" t2 WHERE t1.\"id\" = t.\"bank_id\" AND t2.\"id\" = t1.\"id\" AND t2.\"name\" LIKE '123'");

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        entityQuery.updatable(DocBankCard.class)
                .setColumns(bank_card -> {
                    bank_card.type().set(bank_card.bank().name());
                })
                .where(bank_card -> {
                    bank_card.user().name().like("123");
                }).executeRows();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE \"doc_bank_card\" t SET \"type\" = t1.\"name\" FROM \"doc_bank\" t1 , \"doc_user\" t2 WHERE t1.\"id\" = t.\"bank_id\" AND t2.\"id\" = t.\"uid\" AND t2.\"name\" LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void testUpdateJoin3(){
        {

            long count = entityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.type().eq("小明姓名");
                    }).count();
            Assert.assertEquals(0,count);
        }

        DocBankCard docBankCard = entityQuery.queryable(DocBankCard.class)
                .where(bank_card -> {
                    bank_card.user().name().like("小明姓名");
                }).singleNotNull();
        Assert.assertEquals("储蓄卡",docBankCard.getType());

        entityQuery.updatable(DocBankCard.class)
                .setColumns(bank_card -> {
                    bank_card.type().set(bank_card.user().name());
                })
                .where(bank_card -> {
                    bank_card.user().name().like("小明姓名");
                }).executeRows();

        DocBankCard docBankCard1 = entityQuery.queryable(DocBankCard.class)
                .where(bank_card -> {
                    bank_card.user().name().like("小明姓名");
                }).singleNotNull();
        Assert.assertEquals("小明姓名",docBankCard1.getType());

        long count = entityQuery.queryable(DocBankCard.class)
                .where(bank_card -> {
                    bank_card.type().eq("小明姓名");
                }).count();
        Assert.assertEquals(1,count);
    }
    @Test
    public void testUpdateJoin4(){
        {

            long count = entityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.type().eq("建设银行");
                    }).count();
            Assert.assertEquals(0,count);
        }

        DocBankCard docBankCard = entityQuery.queryable(DocBankCard.class)
                .where(bank_card -> {
                    bank_card.user().name().like("小红姓名");
                }).singleNotNull();
        Assert.assertEquals("储蓄卡",docBankCard.getType());


        entityQuery.updatable(DocBankCard.class)
                .setColumns(bank_card -> {
                    bank_card.type().set(bank_card.bank().name());
                })
                .where(bank_card -> {
                    bank_card.user().name().like("小红姓名");
                }).executeRows();

        DocBankCard docBankCard1 = entityQuery.queryable(DocBankCard.class)
                .where(bank_card -> {
                    bank_card.user().name().like("小红姓名");
                }).singleNotNull();
        Assert.assertEquals("建设银行",docBankCard1.getType());

        long count = entityQuery.queryable(DocBankCard.class)
                .where(bank_card -> {
                    bank_card.type().eq("建设银行");
                }).count();
        Assert.assertEquals(1,count);
    }




    @Test
    public void testDeleteJoin1(){
//        easyEntityQuery.sqlExecute("DELETE FROM `doc_bank_card` t INNER JOIN `doc_user` t1 ON t1.`id` = t.`uid` WHERE t1.`name` LIKE '123'");

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        entityQuery.deletable(DocBankCard.class)
                .allowDeleteStatement(true)
                .where(bank_card -> {
                    bank_card.user().name().like("123");
                }).executeRows();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("DELETE FROM \"doc_bank_card\" t USING \"doc_user\" t1 WHERE t1.\"id\" = t.\"uid\" AND t1.\"name\" LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testDeleteJoin2(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        entityQuery.deletable(DocBankCard.class)
                .allowDeleteStatement(true)
                .where(bank_card -> {
                    bank_card.user().name().like("123");
                }).executeRows();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("DELETE FROM \"doc_bank_card\" t USING \"doc_user\" t1 WHERE t1.\"id\" = t.\"uid\" AND t1.\"name\" LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
}
