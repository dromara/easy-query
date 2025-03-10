package com.easy.query.test;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.test.entity.one.One1;
import com.easy.query.test.entity.one.One2;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/3/10 15:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class RelationNullValueTest extends BaseTest{

    @Before
    public void initData1(){
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(One1.class, One2.class));
        codeFirstCommand.executeWithTransaction(s->s.commit());
        easyEntityQuery.deletable(One1.class).allowDeleteStatement(true).where(one1 -> one1.isNotNull()).executeRows();
        easyEntityQuery.deletable(One2.class).allowDeleteStatement(true).where(one1 -> one1.isNotNull()).executeRows();

        One1 one1 = new One1();
        one1.setId("1");
        one1.setOid("");
        one1.setOname("one1");
        One1 one11 = new One1();
        one11.setId("11");
        one11.setOid("");
        one11.setOname("one11");
        One2 one2 = new One2();
        one2.setId("2");
        one2.setOid("");
        one2.setOname("one2");
        One2 one22 = new One2();
        one22.setId("22");
        one22.setOid("");
        one22.setOname("one22");
        try(Transaction transaction = easyEntityQuery.beginTransaction()){
            easyEntityQuery.insertable(one1).executeRows();
            easyEntityQuery.insertable(one11).executeRows();
            easyEntityQuery.insertable(one2).executeRows();
            easyEntityQuery.insertable(one22).executeRows();
            transaction.commit();
        }
    }
    @Test
    public void one2oneTest(){
        List<One1> list = easyEntityQuery.queryable(One1.class)
                .include(one1 -> one1.one2())
                .toList();
    }
}
