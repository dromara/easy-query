package com.easy.query.test.mysql8;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.test.doc.entity.DocBank;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.mysql8.entity.TopicJson;
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
public class ManyGroupTest extends BaseTest{
    @Before
    public void before(){
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(DocBankCard.class, DocBank.class, DocUser.class));
        codeFirstCommand.executeWithTransaction(s->s.commit());
        {
            easyEntityQuery.deletable(DocBankCard.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(DocBank.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
            easyEntityQuery.deletable(DocUser.class).allowDeleteStatement(true).where(t -> t.isNotNull()).executeRows();
        }
    }

    @Test
    public void manyGroupTest1(){
        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().any();
                }).toList();
    }
}
