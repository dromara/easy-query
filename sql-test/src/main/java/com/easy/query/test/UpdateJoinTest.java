package com.easy.query.test;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.doc.entity.DocBank;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/2/22 22:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class UpdateJoinTest extends BaseTest{

//    @Before
//     public void testBefore(){
//        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
//        databaseCodeFirst.createDatabaseIfNotExists();
//        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(DocBankCard.class, DocUser.class, DocBank.class));
//        codeFirstCommand.executeWithTransaction(a->a.commit());
//    }
    @Test
    public void testUpdateJoin1(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        easyEntityQuery.updatable(DocBankCard.class)
                .setColumns(bank_card -> {
                    bank_card.type().set(bank_card.user().name());
                })
                .where(bank_card -> {
                    bank_card.user().name().like("123");
                }).executeRows();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `doc_bank_card` t INNER JOIN `doc_user` t1 ON t1.`id` = t.`uid` SET t.`type` = t1.`name` WHERE t1.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testUpdateJoin2(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        easyEntityQuery.updatable(DocBankCard.class)
                .setColumns(bank_card -> {
                    bank_card.type().set(bank_card.bank().name());
                })
                .where(bank_card -> {
                    bank_card.user().name().like("123");
                }).executeRows();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `doc_bank_card` t INNER JOIN `doc_bank` t1 ON t1.`id` = t.`bank_id` INNER JOIN `doc_user` t2 ON t2.`id` = t.`uid` SET t.`type` = t1.`name` WHERE t2.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
}
