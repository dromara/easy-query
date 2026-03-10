package com.easy.query.test.pgsql;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * create time 2026/3/10
 * PostgreSQL FOR UPDATE integration tests
 *
 * @author WCPE
 */
public class PgSQLForUpdateTest extends PgSQLBaseTest {

    @Test
    public void testForUpdateSqlAppendInTransaction() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try (Transaction transaction = entityQuery.beginTransaction()) {
            entityQuery.queryable(DocUser.class)
                    .where(user -> user.id().eq("小明id"))
                    .forUpdate()
                    .firstOrNull();
            transaction.commit();
        } finally {
            listenerContextManager.clear();
        }
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertNotNull(jdbcExecuteAfterArg);
        Assert.assertTrue(jdbcExecuteAfterArg.getBeforeArg().getSql().endsWith(" FOR UPDATE"));
    }

    @Test
    public void testForUpdateWithoutTransactionThrows() {
        try {
            entityQuery.queryable(DocUser.class)
                    .where(user -> user.id().eq("小明id"))
                    .forUpdate();
            Assert.fail("forUpdate should require active transaction");
        } catch (IllegalStateException ex) {
            Assert.assertTrue(ex.getMessage().contains("beginTransaction"));
        }
    }

    @Test
    public void testForUpdateJoinNotSupported() {
        try (Transaction transaction = entityQuery.beginTransaction()) {
            try {
                entityQuery.queryable(DocUser.class)
                        .leftJoin(DocBankCard.class, (user, card) -> user.id().eq(card.uid()))
                        .forUpdate();
                Assert.fail("forUpdate should reject join query");
            } catch (IllegalStateException ex) {
                Assert.assertTrue(ex.getMessage().contains("single-table"));
            }
            transaction.commit();
        }
    }

    @Test
    public void testForUpdateDuplicateCallThrows() {
        try (Transaction transaction = entityQuery.beginTransaction()) {
            try {
                entityQuery.queryable(DocUser.class)
                        .where(user -> user.id().eq("小明id"))
                        .forUpdate()
                        .forUpdate();
                Assert.fail("repeated forUpdate should be rejected");
            } catch (IllegalStateException ex) {
                Assert.assertTrue(ex.getMessage().contains("repeated"));
            }
            transaction.commit();
        }
    }

    @Test
    public void testForUpdateBlocksConcurrentTransaction() throws InterruptedException {
        CountDownLatch firstLocked = new CountDownLatch(1);
        CountDownLatch releaseFirst = new CountDownLatch(1);
        CountDownLatch secondFinished = new CountDownLatch(1);
        AtomicLong secondWaitMillis = new AtomicLong(0L);
        AtomicReference<Throwable> error = new AtomicReference<>();

        Thread firstThread = new Thread(() -> {
            try (Transaction transaction = entityQuery.beginTransaction()) {
                entityQuery.queryable(DocUser.class)
                        .where(user -> user.id().eq("小明id"))
                        .forUpdate()
                        .firstOrNull();
                firstLocked.countDown();
                if (!releaseFirst.await(5, TimeUnit.SECONDS)) {
                    throw new IllegalStateException("first transaction wait timeout");
                }
                transaction.commit();
            } catch (Throwable ex) {
                error.compareAndSet(null, ex);
                releaseFirst.countDown();
            }
        }, "pg-for-update-first-thread");

        Thread secondThread = new Thread(() -> {
            try {
                if (!firstLocked.await(5, TimeUnit.SECONDS)) {
                    throw new IllegalStateException("second transaction did not observe first lock");
                }
                long start = System.currentTimeMillis();
                try (Transaction transaction = entityQuery.beginTransaction()) {
                    entityQuery.queryable(DocUser.class)
                            .where(user -> user.id().eq("小明id"))
                            .forUpdate()
                            .firstOrNull();
                    transaction.commit();
                }
                secondWaitMillis.set(System.currentTimeMillis() - start);
            } catch (Throwable ex) {
                error.compareAndSet(null, ex);
            } finally {
                secondFinished.countDown();
            }
        }, "pg-for-update-second-thread");

        firstThread.start();
        secondThread.start();
        Assert.assertTrue("first transaction failed to lock row in time", firstLocked.await(5, TimeUnit.SECONDS));
        Thread.sleep(300);
        Assert.assertEquals("second transaction should still be blocked by row lock", 1L, secondFinished.getCount());
        releaseFirst.countDown();
        Assert.assertTrue("second transaction did not finish in time", secondFinished.await(5, TimeUnit.SECONDS));
        firstThread.join();
        secondThread.join();
        if (error.get() != null) {
            throw new AssertionError(error.get());
        }
        Assert.assertTrue("second transaction should wait for lock release", secondWaitMillis.get() >= 250L);
    }

    @Test
    public void testForUpdateAppliesOnlyOnOuterQueryForExistsAndInSubQuery() {
        try (Transaction transaction = entityQuery.beginTransaction()) {
            String existsSql = entityQuery.queryable(DocUser.class)
                    .where(user -> {
                        user.expression().exists(
                                user.expression().subQueryable(DocBankCard.class)
                                        .where(bankCard -> bankCard.uid().eq(user.id()))
                        );
                    })
                    .forUpdate()
                    .toSQL();
            Assert.assertTrue(existsSql.endsWith(" FOR UPDATE"));
            Assert.assertEquals(1, countMatches(existsSql, "FOR UPDATE"));

            Query<String> uidSubQuery = entityQuery.queryable(DocBankCard.class)
                    .selectColumn(bankCard -> bankCard.uid());
            String inSql = entityQuery.queryable(DocUser.class)
                    .where(user -> user.id().in(uidSubQuery))
                    .forUpdate()
                    .toSQL();
            Assert.assertTrue(inSql.endsWith(" FOR UPDATE"));
            Assert.assertEquals(1, countMatches(inSql, "FOR UPDATE"));
            transaction.commit();
        }
    }

    @Test
    public void testForUpdateDerivedCountAndExistsSqlBehaviorConsistent() {
        try (Transaction transaction = entityQuery.beginTransaction()) {
            EntityQueryable<?, DocUser> forUpdateQueryable = entityQuery.queryable(DocUser.class)
                    .where(user -> user.id().eq("小明id"))
                    .forUpdate();

            String countSql = forUpdateQueryable.cloneQueryable()
                    .selectCount()
                    .toSQL();
            String existsSql = forUpdateQueryable.cloneQueryable()
                    .limit(1)
                    .select(" 1 ")
                    .toSQL();

            Assert.assertTrue(countSql.endsWith(" FOR UPDATE"));
            Assert.assertTrue(existsSql.endsWith(" FOR UPDATE"));
            Assert.assertEquals(1, countMatches(countSql, "FOR UPDATE"));
            Assert.assertEquals(1, countMatches(existsSql, "FOR UPDATE"));
            transaction.commit();
        }
    }

    private static int countMatches(String text, String part) {
        int count = 0;
        int index = 0;
        while (index >= 0) {
            index = text.indexOf(part, index);
            if (index >= 0) {
                count++;
                index += part.length();
            }
        }
        return count;
    }
}
