package com.easy.query.test.mysql8;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.test.mysql8.entity.M8User2;
import com.easy.query.test.mysql8.entity.M8UserBook2;
import com.easy.query.test.mysql8.entity.M8UserBookIds;
import com.easy.query.test.mysql8.entity.TableNoKey;
import com.easy.query.test.mysql8.entity.proxy.M8User2Proxy;
import org.junit.Before;
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
    @Before
    public void before(){
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(M8UserBookIds.class, M8UserBook2.class));
        codeFirstCommand.executeWithTransaction(s->s.commit());
    }
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

    @Test
    public  void testRelationColumns(){

        List<M8User2> list1 = easyEntityQuery.queryable(M8User2.class)
                .where(m -> {
                    EntitySQLContext entitySQLContext = m.getEntitySQLContext();
                    m.expression().exists(() -> {
                        return easyEntityQuery.queryable(M8UserBook2.class)
                                .where(mb -> {
                                    M8User2Proxy m8User2Proxy = m.create(m.getTable(), mb.getEntitySQLContext());
                                    m8User2Proxy.userId().eq(mb.bookId());
                                });
                    });

                    M8User2Proxy m8User2Proxy = m.create(m.getTable(), entitySQLContext);
                    m8User2Proxy.userName().like("123");
                }).toList();

        List<M8UserBookIds> list = easyEntityQuery.queryable(M8UserBookIds.class)
                .where(m -> {
                    m.books().any(x -> x.bookPrice().ge(BigDecimal.ZERO));
                })
//                .orderBy(m -> {
//                    m.expression().caseWhen(()->{}).then(1).elseEnd(1).asc();
//                })
                .toList();
    }
}
