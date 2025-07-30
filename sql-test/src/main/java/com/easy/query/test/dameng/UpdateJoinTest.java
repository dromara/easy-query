package com.easy.query.test.dameng;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.dameng.entity.DamengMyTopic;
import com.easy.query.test.doc.entity.DocBank;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.pgsql.PgSQLBaseTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * create time 2025/2/22 23:43
 * 文件说明
 *
 * @author xuejiaming
 */
public class UpdateJoinTest extends DamengBaseTest {


    @Before
    public void testBefore() {
        DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        {
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(DocBank.class,DocBankCard.class, DocUser.class));
            codeFirstCommand.executeWithTransaction(a -> a.commit());
        }

        {
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.dropTableCommand(Arrays.asList(DocBankCard.class, DocUser.class, DocBank.class));
            codeFirstCommand.executeWithTransaction(a -> a.commit());

        }
        {
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(DocBank.class,DocBankCard.class, DocUser.class));
            codeFirstCommand.executeWithTransaction(a -> a.commit());
        }
//        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(DocBank.class));
    }

    @Test
    public void testUpdateJoin1() {
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
        Assert.assertEquals("UPDATE \"doc_bank_card\" t INNER JOIN \"doc_user\" t1 ON t1.\"ID\" = t.\"UID\" SET t.\"TYPE\" = t1.\"NAME\" WHERE t1.\"NAME\" LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testUpdateJoin2() {
//        entityQuery.sqlExecute("UPDATE \"doc_bank_card\" t SET \"type\" = t1.\"name\" FROM \"doc_bank\" t1 INNER JOIN \"doc_user\" t2 ON t2.\"id\" = t.\"id\" WHERE t1.\"id\" = t.\"bank_id\" AND t2.\"name\" LIKE '123'");
//        entityQuery.sqlExecute("UPDATE \"doc_bank_card\" t SET \"type\" = t1.\"name\" FROM \"doc_bank\" t1 , \"doc_user\" t2 WHERE t1.\"id\" = t.\"bank_id\" AND t2.\"id\" = t1.\"id\" AND t2.\"name\" LIKE '123'");

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
        Assert.assertEquals("UPDATE \"doc_bank_card\" t INNER JOIN \"doc_bank\" t1 ON t1.\"ID\" = t.\"BANK_ID\" INNER JOIN \"doc_user\" t2 ON t2.\"ID\" = t.\"UID\" SET t.\"TYPE\" = t1.\"NAME\" WHERE t2.\"NAME\" LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public  void testTopicInsertOrUpdate(){
        DamengMyTopic damengMyTopic = new DamengMyTopic();
        damengMyTopic.setId("-111");
        damengMyTopic.setStars(1);
        damengMyTopic.setTitle("123123");
        damengMyTopic.setCreateTime(LocalDateTime.of(2020,1,1,1,1));
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        entityQuery.insertable(damengMyTopic)
                .onConflictThen(o->o.FETCHER.allFields()).executeRows();
        listenerContextManager.clear();
    }

}
