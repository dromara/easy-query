package com.easy.query.test.mysql8;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.dto.autodto.SchoolClassAO;
import com.easy.query.test.entity.school.SchoolClass;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.M8User2;
import com.easy.query.test.mysql8.entity.M8UserBook2;
import com.easy.query.test.mysql8.entity.M8UserBookIds;
import com.easy.query.test.mysql8.entity.TableNoKey;
import com.easy.query.test.mysql8.entity.proxy.M8User2Proxy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/3/17 21:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class M8Test1 extends BaseTest {
    @Before
    public void before() {
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(M8UserBookIds.class, M8UserBook2.class));
        codeFirstCommand.executeWithTransaction(s -> s.commit());
        easyEntityQuery.deletable(M8UserBookIds.class).allowDeleteStatement(true)
                .where(m -> m.userId().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8UserBook2.class).allowDeleteStatement(true)
                .where(m -> m.bookId().isNotNull()).executeRows();
        ArrayList<M8UserBookIds> m8UserBookIds = new ArrayList<>();
        ArrayList<M8UserBook2> m8UserBook2s = new ArrayList<>();
        {
            M8UserBookIds m8UserBookIds1 = new M8UserBookIds();
            m8UserBookIds1.setUserId("1");
            m8UserBookIds1.setUserName("1");
            m8UserBookIds1.setUserAge(1);
            m8UserBookIds1.setCreateTime(LocalDateTime.of(202, 1, 1, 0, 0));
            m8UserBookIds1.setBookIds("1,2");
            m8UserBookIds.add(m8UserBookIds1);
        }
        {
            M8UserBookIds m8UserBookIds1 = new M8UserBookIds();
            m8UserBookIds1.setUserId("2");
            m8UserBookIds1.setUserName("2");
            m8UserBookIds1.setUserAge(2);
            m8UserBookIds1.setCreateTime(LocalDateTime.of(202, 1, 1, 0, 0));
            m8UserBookIds1.setBookIds("3,2");
            m8UserBookIds.add(m8UserBookIds1);
        }
        {
            M8UserBookIds m8UserBookIds1 = new M8UserBookIds();
            m8UserBookIds1.setUserId("3");
            m8UserBookIds1.setUserName("3");
            m8UserBookIds1.setUserAge(3);
            m8UserBookIds1.setCreateTime(LocalDateTime.of(202, 1, 1, 0, 0));
            m8UserBookIds1.setBookIds("2");
            m8UserBookIds.add(m8UserBookIds1);
        }
        {
            M8UserBookIds m8UserBookIds1 = new M8UserBookIds();
            m8UserBookIds1.setUserId("4");
            m8UserBookIds1.setUserName("4");
            m8UserBookIds1.setUserAge(4);
            m8UserBookIds1.setCreateTime(LocalDateTime.of(202, 1, 1, 0, 0));
            m8UserBookIds1.setBookIds("2");
            m8UserBookIds.add(m8UserBookIds1);
        }
        {
            M8UserBookIds m8UserBookIds1 = new M8UserBookIds();
            m8UserBookIds1.setUserId("5");
            m8UserBookIds1.setUserName("5");
            m8UserBookIds1.setUserAge(5);
            m8UserBookIds1.setCreateTime(LocalDateTime.of(202, 1, 1, 0, 0));
//            m8UserBookIds1.setBookIds();
            m8UserBookIds.add(m8UserBookIds1);
        }
        {
            M8UserBook2 m8UserBook2 = new M8UserBook2();
            m8UserBook2.setBookId("1");
            m8UserBook2.setBookName("1");
            m8UserBook2.setBookPrice(BigDecimal.valueOf(1));
            m8UserBook2s.add(m8UserBook2);
        }
        {
            M8UserBook2 m8UserBook2 = new M8UserBook2();
            m8UserBook2.setBookId("2");
            m8UserBook2.setBookName("2");
            m8UserBook2.setBookPrice(BigDecimal.valueOf(2));
            m8UserBook2s.add(m8UserBook2);
        }
        {
            M8UserBook2 m8UserBook2 = new M8UserBook2();
            m8UserBook2.setBookId("3");
            m8UserBook2.setBookName("3");
            m8UserBook2.setBookPrice(BigDecimal.valueOf(3));
            m8UserBook2s.add(m8UserBook2);
        }
        {
            M8UserBook2 m8UserBook2 = new M8UserBook2();
            m8UserBook2.setBookId("4");
            m8UserBook2.setBookName("4");
            m8UserBook2.setBookPrice(BigDecimal.valueOf(4));
            m8UserBook2s.add(m8UserBook2);
        }
        easyEntityQuery.insertable(m8UserBookIds).executeRows();
        easyEntityQuery.insertable(m8UserBook2s).executeRows();
    }

    @Test
    public void testNoKeyCodeFirst() {
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        {

            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(TableNoKey.class));
            codeFirstCommand.executeWithTransaction(s -> s.commit());
        }
        {

            CodeFirstCommand codeFirstCommand1 = databaseCodeFirst.dropTableCommand(Arrays.asList(TableNoKey.class));
            codeFirstCommand1.executeWithTransaction(s -> s.commit());
        }
        {

            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(TableNoKey.class));
            codeFirstCommand.executeWithTransaction(s -> s.commit());
        }
    }

    @Test
    public void testMany() {
        List<M8User2> list = easyEntityQuery.queryable(M8User2.class)
                .where(m -> {
                    m.books().any(x -> x.bookPrice().gt(BigDecimal.ONE));
                }).toList();
    }

    @Test
    public void testRelationColumns() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<M8UserBookIds> list = easyEntityQuery.queryable(M8UserBookIds.class)
                .where(m -> {
                    m.books().any(x -> x.bookPrice().ge(BigDecimal.ZERO));
                })
                .toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`user_id`,t.`user_name`,t.`user_age`,t.`create_time`,t.`book_ids` FROM `m8_user_book_ids` t WHERE EXISTS (SELECT 1 FROM `m8_user_book2` t1 WHERE FIND_IN_SET(t1.`book_id`,t.`book_ids`) AND t1.`book_price` >= ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("0(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testRelationColumns1() {


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<M8UserBookIds> list1 = easyEntityQuery.queryable(M8UserBookIds.class)
                .includes(m -> m.books())
                .toList();
        for (M8UserBookIds m8UserBookIds : list1) {

            if ("1".equals(m8UserBookIds.getUserId())) {
                Assert.assertEquals(2, m8UserBookIds.getBooks().size());
                for (M8UserBook2 book : m8UserBookIds.getBooks()) {
                    Assert.assertTrue("1".equals(book.getBookId()) || "2".equals(book.getBookId()));
                }
            }
            if ("2".equals(m8UserBookIds.getUserId())) {
                Assert.assertEquals(2, m8UserBookIds.getBooks().size());
                for (M8UserBook2 book : m8UserBookIds.getBooks()) {
                    Assert.assertTrue("3".equals(book.getBookId()) || "2".equals(book.getBookId()));
                }
            }
            if ("3".equals(m8UserBookIds.getUserId())||"4".equals(m8UserBookIds.getUserId())) {
                Assert.assertEquals(1, m8UserBookIds.getBooks().size());
                for (M8UserBook2 book : m8UserBookIds.getBooks()) {
                    Assert.assertEquals("2",book.getBookId());
                }
            }
            if ("5".equals(m8UserBookIds.getUserId())) {
                Assert.assertEquals(0, m8UserBookIds.getBooks().size());
            }
        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT `user_id`,`user_name`,`user_age`,`create_time`,`book_ids` FROM `m8_user_book_ids`", jdbcExecuteAfterArg.getBeforeArg().getSql());


        }
        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`book_id`,t.`book_name`,t.`book_price` FROM `m8_user_book2` t WHERE FIND_IN_SET(t.`book_id`,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1,2,3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
    }
}
