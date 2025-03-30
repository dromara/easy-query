package com.easy.query.test;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.subjoin.SubJoinAuthor;
import com.easy.query.test.entity.subjoin.SubJoinBook;
import com.easy.query.test.entity.subjoin.SubJoinUser;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/2/20 15:17
 * 文件说明
 *
 * @author xuejiaming
 */
public class SubJoinTest extends BaseTest {

    @Before
    public void testBefore() {
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        databaseCodeFirst.createDatabaseIfNotExists();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(SubJoinUser.class, SubJoinBook.class, SubJoinAuthor.class));
        codeFirstCommand.executeWithTransaction(c -> c.commit());
    }


    @Test
    public void testSubJoinCaseWhen1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft1<Integer>> list = easyEntityQuery.queryable(SubJoinUser.class)
                .where(s -> {
                    s.id().eq("123");
                })
                .orderBy(s -> {
                    s.expression().caseWhen(() -> {
                        s.books().flatElement().name().eq("123");
//                        s.books().any(x->x.name().eq("123"));
                    }).then(1).elseEnd(2).asc();
                })
                .select(s -> Select.DRAFT.of(
                        s.books().sum(b -> b.author().age())
                ))
                .toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL((SELECT SUM(t3.`age`) FROM `sub_join_book` t2 LEFT JOIN `sub_join_author` t3 ON t3.`id` = t2.`author_id` WHERE t2.`uid` = t.`id`),0) AS `value1` FROM `sub_join_user` t WHERE t.`id` = ? ORDER BY (CASE WHEN EXISTS (SELECT 1 FROM `sub_join_book` t1 WHERE t1.`uid` = t.`id` AND t1.`name` = ? LIMIT 1) THEN ? ELSE ? END) ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String),1(Integer),2(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
    @Test
    public void testSubJoinCaseWhen1_1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft1<Integer>> list = easyEntityQuery.queryable(SubJoinUser.class)
                .where(s -> {
                    s.id().eq("123");
                })
                .orderBy(s -> {
                    s.expression().caseWhen(() -> {
//                        s.books().flatElement().name().eq("123");
                        s.books().any(x->x.name().eq("123"));
                    }).then(1).elseEnd(2).asc();
                })
                .select(s -> Select.DRAFT.of(
                        s.books().sum(b -> b.author().age())
                ))
                .toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL((SELECT SUM(t3.`age`) FROM `sub_join_book` t2 LEFT JOIN `sub_join_author` t3 ON t3.`id` = t2.`author_id` WHERE t2.`uid` = t.`id`),0) AS `value1` FROM `sub_join_user` t WHERE t.`id` = ? ORDER BY (CASE WHEN EXISTS (SELECT 1 FROM `sub_join_book` t1 WHERE t1.`uid` = t.`id` AND t1.`name` = ? LIMIT 1) THEN ? ELSE ? END) ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String),1(Integer),2(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testSubJoinCaseWhen2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft1<Integer>> list = easyEntityQuery.queryable(SubJoinUser.class)
                .where(s -> {
                    s.id().eq("123");
                })
                .orderBy(s -> {
                    s.expression().caseWhen(() -> {
                        s.books().any(o -> o.name().eq("123"));
                    }).then(1).elseEnd(2).asc();
                })
                .select(s -> Select.DRAFT.of(
                        s.books().sum(b -> b.author().age())
                ))
                .toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL((SELECT SUM(t3.`age`) FROM `sub_join_book` t2 LEFT JOIN `sub_join_author` t3 ON t3.`id` = t2.`author_id` WHERE t2.`uid` = t.`id`),0) AS `value1` FROM `sub_join_user` t WHERE t.`id` = ? ORDER BY (CASE WHEN EXISTS (SELECT 1 FROM `sub_join_book` t1 WHERE t1.`uid` = t.`id` AND t1.`name` = ? LIMIT 1) THEN ? ELSE ? END) ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String),1(Integer),2(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testSubJoinSum() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft1<Integer>> list = easyEntityQuery.queryable(SubJoinUser.class)
                .where(s -> {
                    s.id().eq("123");
                }).select(s -> Select.DRAFT.of(
                        s.books().sum(b -> b.author().age())
                ))
                .toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL((SELECT SUM(t2.`age`) FROM `sub_join_book` t1 LEFT JOIN `sub_join_author` t2 ON t2.`id` = t1.`author_id` WHERE t1.`uid` = t.`id`),0) AS `value1` FROM `sub_join_user` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testSubJoinSum2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        easyEntityQuery.queryable(SubJoinUser.class)
                .where(s -> {
                    s.id().eq("123");
                }).selectColumn(s -> s.books().sum(b -> b.author().age()))
                .toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL((SELECT SUM(t2.`age`) FROM `sub_join_book` t1 LEFT JOIN `sub_join_author` t2 ON t2.`id` = t1.`author_id` WHERE t1.`uid` = t.`id`),0) FROM `sub_join_user` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testSubJoinSumDistinct() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft1<Integer>> list = easyEntityQuery.queryable(SubJoinUser.class)
                .where(s -> {
                    s.id().eq("123");
                }).select(s -> Select.DRAFT.of(
                        s.books().distinct().sum(b -> b.author().age())
                ))
                .toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL((SELECT SUM(DISTINCT t2.`age`) FROM `sub_join_book` t1 LEFT JOIN `sub_join_author` t2 ON t2.`id` = t1.`author_id` WHERE t1.`uid` = t.`id`),0) AS `value1` FROM `sub_join_user` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testSubJoinSumDistinct2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        easyEntityQuery.queryable(SubJoinUser.class)
                .where(s -> {
                    s.id().eq("123");
                }).selectColumn(s -> s.books().distinct().sum(b -> b.author().age()))
                .toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL((SELECT SUM(DISTINCT t2.`age`) FROM `sub_join_book` t1 LEFT JOIN `sub_join_author` t2 ON t2.`id` = t1.`author_id` WHERE t1.`uid` = t.`id`),0) FROM `sub_join_user` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }


    @Test
    public void testSubJoinSumBigDecimal() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft1<BigDecimal>> list = easyEntityQuery.queryable(SubJoinUser.class)
                .where(s -> {
                    s.id().eq("123");
                }).select(s -> Select.DRAFT.of(
                        s.books().sumBigDecimal(b -> b.author().age())
                ))
                .toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL((SELECT SUM(t2.`age`) FROM `sub_join_book` t1 LEFT JOIN `sub_join_author` t2 ON t2.`id` = t1.`author_id` WHERE t1.`uid` = t.`id`),0) AS `value1` FROM `sub_join_user` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testSubJoinSumBigDecimal2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        easyEntityQuery.queryable(SubJoinUser.class)
                .where(s -> {
                    s.id().eq("123");
                }).selectColumn(s -> s.books().sumBigDecimal(b -> b.author().age()))
                .toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL((SELECT SUM(t2.`age`) FROM `sub_join_book` t1 LEFT JOIN `sub_join_author` t2 ON t2.`id` = t1.`author_id` WHERE t1.`uid` = t.`id`),0) FROM `sub_join_user` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testSubJoinSumBigDecimalDistinct() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft1<BigDecimal>> list = easyEntityQuery.queryable(SubJoinUser.class)
                .where(s -> {
                    s.id().eq("123");
                }).select(s -> Select.DRAFT.of(
                        s.books().distinct().sumBigDecimal(b -> b.author().age())
                ))
                .toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL((SELECT SUM(DISTINCT t2.`age`) FROM `sub_join_book` t1 LEFT JOIN `sub_join_author` t2 ON t2.`id` = t1.`author_id` WHERE t1.`uid` = t.`id`),0) AS `value1` FROM `sub_join_user` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testSubJoinSumBigDecimalDistinct2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        easyEntityQuery.queryable(SubJoinUser.class)
                .where(s -> {
                    s.id().eq("123");
                }).selectColumn(s -> s.books().distinct().sumBigDecimal(b -> b.author().age()))
                .toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL((SELECT SUM(DISTINCT t2.`age`) FROM `sub_join_book` t1 LEFT JOIN `sub_join_author` t2 ON t2.`id` = t1.`author_id` WHERE t1.`uid` = t.`id`),0) FROM `sub_join_user` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }


    @Test
    public void testSubJoinAvg() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        easyEntityQuery.queryable(SubJoinUser.class)
                .where(s -> {
                    s.id().eq("123");
                }).select(s -> Select.DRAFT.of(
                        s.books().avg(b -> b.author().age())
                ))
                .toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL((SELECT AVG(t2.`age`) FROM `sub_join_book` t1 LEFT JOIN `sub_join_author` t2 ON t2.`id` = t1.`author_id` WHERE t1.`uid` = t.`id`),0) AS `value1` FROM `sub_join_user` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testSubJoinAvg2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        easyEntityQuery.queryable(SubJoinUser.class)
                .where(s -> {
                    s.id().eq("123");
                }).selectColumn(s -> s.books().avg(b -> b.author().age()))
                .toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL((SELECT AVG(t2.`age`) FROM `sub_join_book` t1 LEFT JOIN `sub_join_author` t2 ON t2.`id` = t1.`author_id` WHERE t1.`uid` = t.`id`),0) FROM `sub_join_user` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testSubJoinAvgDistinct() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        easyEntityQuery.queryable(SubJoinUser.class)
                .where(s -> {
                    s.id().eq("123");
                }).select(s -> Select.DRAFT.of(
                        s.books().distinct().avg(b -> b.author().age())
                ))
                .toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL((SELECT AVG(DISTINCT t2.`age`) FROM `sub_join_book` t1 LEFT JOIN `sub_join_author` t2 ON t2.`id` = t1.`author_id` WHERE t1.`uid` = t.`id`),0) AS `value1` FROM `sub_join_user` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testSubJoinAvgDistinct2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        easyEntityQuery.queryable(SubJoinUser.class)
                .where(s -> {
                    s.id().eq("123");
                }).selectColumn(s -> s.books().distinct().avg(b -> b.author().age()))
                .toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT IFNULL((SELECT AVG(DISTINCT t2.`age`) FROM `sub_join_book` t1 LEFT JOIN `sub_join_author` t2 ON t2.`id` = t1.`author_id` WHERE t1.`uid` = t.`id`),0) FROM `sub_join_user` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }


    @Test
    public void testSubJoinMax() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft1<Integer>> list = easyEntityQuery.queryable(SubJoinUser.class)
                .where(s -> {
                    s.id().eq("123");
                }).select(s -> Select.DRAFT.of(
                        s.books().max(b -> b.author().age())
                ))
                .toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (SELECT MAX(t2.`age`) FROM `sub_join_book` t1 LEFT JOIN `sub_join_author` t2 ON t2.`id` = t1.`author_id` WHERE t1.`uid` = t.`id`) AS `value1` FROM `sub_join_user` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testSubJoinMax2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        easyEntityQuery.queryable(SubJoinUser.class)
                .where(s -> {
                    s.id().eq("123");
                }).selectColumn(s -> s.books().max(b -> b.author().age()))
                .toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (SELECT MAX(t2.`age`) FROM `sub_join_book` t1 LEFT JOIN `sub_join_author` t2 ON t2.`id` = t1.`author_id` WHERE t1.`uid` = t.`id`) FROM `sub_join_user` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testSubJoinMin() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft1<Integer>> list = easyEntityQuery.queryable(SubJoinUser.class)
                .where(s -> {
                    s.id().eq("123");
                }).select(s -> Select.DRAFT.of(
                        s.books().min(b -> b.author().age())
                ))
                .toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (SELECT MIN(t2.`age`) FROM `sub_join_book` t1 LEFT JOIN `sub_join_author` t2 ON t2.`id` = t1.`author_id` WHERE t1.`uid` = t.`id`) AS `value1` FROM `sub_join_user` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testSubJoinMin2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        easyEntityQuery.queryable(SubJoinUser.class)
                .where(s -> {
                    s.id().eq("123");
                }).selectColumn(s -> s.books().min(b -> b.author().age()))
                .toList();


        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (SELECT MIN(t2.`age`) FROM `sub_join_book` t1 LEFT JOIN `sub_join_author` t2 ON t2.`id` = t1.`author_id` WHERE t1.`uid` = t.`id`) FROM `sub_join_user` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

}
