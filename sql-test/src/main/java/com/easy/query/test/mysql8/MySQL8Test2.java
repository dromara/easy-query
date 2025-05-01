package com.easy.query.test.mysql8;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.test.common.MD5Util;
import com.easy.query.test.mysql8.entity.M8TestIndex;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/4/27 15:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQL8Test2 extends BaseTest{
    @Test
    public void test(){
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(M8TestIndex.class));
        codeFirstCommand.executeWithTransaction(s->{
            s.commit();
        });
        CodeFirstCommand codeFirstCommand1 = databaseCodeFirst.dropTableCommand(Arrays.asList(M8TestIndex.class));
        codeFirstCommand1.executeWithTransaction(s->s.commit());

        CodeFirstCommand codeFirstCommand2 = databaseCodeFirst.syncTableCommand(Arrays.asList(M8TestIndex.class));
        codeFirstCommand2.executeWithTransaction(s->{
            String md5Hash = MD5Util.getMD5Hash(s.getSQL());
            System.out.println(s.getSQL());
            Assert.assertEquals("b8e72a4e35ecab317c1124a7ca2fe2f6",md5Hash);
        });
//        List<M8TestIndex> list = easyEntityQuery.queryable(M8TestIndex.class)
//                .toList();
    }

}
