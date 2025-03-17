package com.easy.query.test.mysql8;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.test.mysql8.entity.M8User2;
import com.easy.query.test.mysql8.entity.TableNoKey;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/3/17 21:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class M8Test1 extends BaseTest{
    @Test
    public void testNoKeyCodeFirst(){
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        {

            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(TableNoKey.class));
            codeFirstCommand.executeWithTransaction(s->s.commit());
        }{

            CodeFirstCommand codeFirstCommand1 = databaseCodeFirst.dropTableCommand(Arrays.asList(TableNoKey.class));
            codeFirstCommand1.executeWithTransaction(s->s.commit());
        }{

            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(TableNoKey.class));
            codeFirstCommand.executeWithTransaction(s->s.commit());
        }
    }

    @Test
    public void testMany(){
        List<M8User2> list = easyEntityQuery.queryable(M8User2.class)
                .where(m -> {
                    m.books().any(x -> x.bookPrice().gt(BigDecimal.ONE));
                }).toList();
    }
}
