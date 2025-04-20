package com.easy.query.test.testmysql8;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import org.junit.Test;

import java.util.Arrays;

/**
 * create time 2025/4/19 20:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyTest extends BaseTest{
    @Test
    public void testaa(){
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(SysUser.class, SysRole.class, SysModule.class));
        codeFirstCommand.executeWithTransaction(s->s.commit());
    }
}
