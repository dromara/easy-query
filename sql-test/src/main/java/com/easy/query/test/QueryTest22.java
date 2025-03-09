package com.easy.query.test;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.core.draft.Draft4;
import com.easy.query.core.proxy.core.draft.proxy.Draft4Proxy;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.doc.entity.proxy.DocUserProxy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

/**
 * create time 2025/3/7 16:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest22 extends BaseTest {
    @Test
    public void testManyGroup() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        easyEntityQuery.queryable(DocUser.class)
                .manyJoin(o -> o.bankCards())
                .where(user -> {
//                    user.bankCards().count().eq(1L);
//                    user.bankCards().count(o->o.type()).ge(2L);
//                    user.bankCards().count(o->o.type()).ge(3L);
                    user.bankCards().where(x -> x.type().eq("123")).
                            sum(o -> o.code().toNumber(Integer.class))
                            .eq(123);
                    user.bankCards().where(x -> x.type().eq("123")).
                            sum(o -> o.code().toNumber(Integer.class))
                            .eq(456);
                    user.bankCards().where(x -> x.type().eq("456")).
                            sum(o -> o.code().toNumber(Integer.class))
                            .eq(789);
//                    user.bankCards().where(x->x.type().eq("456")).sum(o->o.code().toNumber(Integer.class)).eq(123);

//                    user.or(()->{
//                        user.bankCards().count().eq(1L);
//                        user.bankCards().count().ge(2L);
//                    });
                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,SUM((CASE WHEN t1.`type` = ? THEN CAST(t1.`code` AS SIGNED) ELSE ? END)) AS `__sum2__`,SUM((CASE WHEN t1.`type` = ? THEN CAST(t1.`code` AS SIGNED) ELSE ? END)) AS `__sum3__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__sum2__` = ? AND t2.`__sum2__` = ? AND t2.`__sum3__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),0(Integer),456(String),0(Integer),123(Integer),456(Integer),789(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testManyGroup2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        easyEntityQuery.queryable(DocUser.class)
                .manyJoin(o -> o.bankCards())
                .where(user -> {
                    user.bankCards().count().eq(1L);
                    user.bankCards().count(o -> o.type()).ge(2L);
                    user.bankCards().count(o -> o.type()).ge(3L);
                    user.bankCards().where(x -> x.type().eq("123"))
                            .distinct()
                            .count()
                            .eq(123L);

                    user.or(() -> {
                        user.bankCards().count().eq(1L);
                        user.bankCards().where(x -> x.type().eq("123"))
                                .distinct()
                                .count()
                                .eq(456L);
                    });
                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,COUNT(*) AS `__count2__`,COUNT(t1.`type`) AS `__count3__`,COUNT(DISTINCT (CASE WHEN t1.`type` = ? THEN ? ELSE ? END)) AS `__count4__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__count2__` = ? AND t2.`__count3__` >= ? AND t2.`__count3__` >= ? AND t2.`__count4__` = ? AND (t2.`__count2__` = ? OR t2.`__count4__` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),null(null),1(Long),2(Long),3(Long),123(Long),1(Long),456(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testManyGroup3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft3<Long, Long, Long>> list = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(o -> o.bankCards())
                .where(user -> {
                    user.bankCards().count().eq(1L);
                    user.bankCards().count(o -> o.type()).ge(2L);
                    user.bankCards().count(o -> o.type()).ge(3L);
                    user.bankCards().where(x -> x.type().eq("123"))
                            .distinct()
                            .count()
                            .eq(123L);

                    user.or(() -> {
                        user.bankCards().count().eq(1L);
                        user.bankCards().where(x -> x.type().eq("123"))
                                .distinct()
                                .count()
                                .eq(456L);
                    });
                })
                .select(user -> Select.DRAFT.of(
                        user.bankCards().count(o -> o.type()),
                        user.bankCards().where(x -> x.type().eq("123")).distinct().count(),
                        user.bankCards().where(x -> x.type().eq("124")).distinct().count()
                ))
                .toList();
        for (Draft3<Long, Long, Long> longLongLongDraft3 : list) {
            Long value1 = longLongLongDraft3.getValue1();
            Long value2 = longLongLongDraft3.getValue2();
            Long value3 = longLongLongDraft3.getValue3();
        }
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`__count3__` AS `value1`,t2.`__count4__` AS `value2`,t2.`__count5__` AS `value3` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,COUNT(*) AS `__count2__`,COUNT(t1.`type`) AS `__count3__`,COUNT(DISTINCT (CASE WHEN t1.`type` = ? THEN ? ELSE ? END)) AS `__count4__`,COUNT(DISTINCT (CASE WHEN t1.`type` = ? THEN ? ELSE ? END)) AS `__count5__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__count2__` = ? AND t2.`__count3__` >= ? AND t2.`__count3__` >= ? AND t2.`__count4__` = ? AND (t2.`__count2__` = ? OR t2.`__count4__` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(Integer),null(null),124(String),1(Integer),null(null),1(Long),2(Long),3(Long),123(Long),1(Long),456(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testManyGroup4() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft2<Boolean, Boolean>> list = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(o -> o.bankCards())
                .where(user -> {
                    user.bankCards().any();
                    user.bankCards().none();
                    user.bankCards().anyValue().eq(false);
                    user.bankCards().noneValue().eq(true);

                })
                .select(user -> Select.DRAFT.of(
                        user.bankCards().anyValue(),
                        user.bankCards().noneValue()
                ))
                .toList();
        for (Draft2<Boolean, Boolean> booleanBooleanDraft2 : list) {

            Boolean value1 = booleanBooleanDraft2.getValue1();
            Boolean value2 = booleanBooleanDraft2.getValue2();
        }
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`__any2__` AS `value1`,t2.`__none3__` AS `value2` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,(CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END) AS `__any2__`,(CASE WHEN COUNT(*) > 0 THEN TRUE ELSE TRUE END) AS `__none3__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__any2__` = ? AND t2.`__none3__` = ? AND t2.`__any2__` = ? AND t2.`__none3__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("true(Boolean),true(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testManyGroup5() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(o -> o.bankCards())
                .where(user -> {
                    user.bankCards().where(x -> x.type().eq("1")).any();
                    user.bankCards().where(x -> x.type().eq("1")).none();

                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,(CASE WHEN COUNT((CASE WHEN t1.`type` = ? THEN ? ELSE ? END)) > 0 THEN TRUE ELSE FALSE END) AS `__any2__`,(CASE WHEN COUNT((CASE WHEN t1.`type` = ? THEN ? ELSE ? END)) > 0 THEN TRUE ELSE TRUE END) AS `__none3__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__any2__` = ? AND t2.`__none3__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String),1(Integer),null(null),1(String),1(Integer),null(null),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testManyGroup6() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(o -> o.bankCards())
                .where(user -> {
                    user.bankCards().where(x -> x.type().eq("1")).sum(x -> x.code().toNumber(long.class));
                    user.bankCards().sum(x -> x.code().toNumber(long.class));
                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("1(String),1(Integer),null(null),1(String),1(Integer),null(null),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testManyGroup7() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Long c = null;
        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .manyJoin(o -> o.bankCards())
                .where(user -> {
                    user.bankCards().count().eq(c);
                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("1(String),1(Integer),null(null),1(String),1(Integer),null(null),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testManyGroup8() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Long c = 1L;
        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .manyJoin(o -> o.bankCards())
                .where(user -> {
                    user.bankCards().count().eq(c);
                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,COUNT(*) AS `__count2__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__count2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testManyGroup9() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Long c = 1L;
        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .manyJoin(false, o -> o.bankCards())
                .where(user -> {
                    user.bankCards().count().eq(c);
                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t WHERE (SELECT COUNT(*) FROM `doc_bank_card` t1 WHERE t1.`uid` = t.`id`) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testManyGroup10() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Long c = 1L;
        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .manyJoin(o -> o.bankCards())
                .where(user -> {
                    user.bankCards().count().eq(c);
                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,COUNT(*) AS `__count2__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__count2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testManyGroup11() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Long c = 1L;
        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(o -> o.bankCards())
                .where(user -> {
                    user.bankCards().where(bc -> {
                        bc.user().phone().eq("12345");
                    }).count().eq(c);
                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,COUNT((CASE WHEN t3.`phone` = ? THEN ? ELSE ? END)) AS `__count2__` FROM `doc_bank_card` t1 LEFT JOIN `doc_user` t3 ON t3.`id` = t1.`uid` GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__count2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("12345(String),1(Integer),null(null),1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testManyGroup12() {

        Long c = 1L;
        EntityQueryable<DocUserProxy, DocUser> where = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(o -> o.bankCards())
                .where(user -> {
                    user.bankCards().where(bc -> {
                        bc.user().phone().eq("12345");
                    }).count().eq(c);
                });
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<DocUser> list1 = where.cloneQueryable().toList();

            listenerContextManager.clear();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,COUNT((CASE WHEN t3.`phone` = ? THEN ? ELSE ? END)) AS `__count2__` FROM `doc_bank_card` t1 LEFT JOIN `doc_user` t3 ON t3.`id` = t1.`uid` GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__count2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("12345(String),1(Integer),null(null),1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<DocUser> list1 = where.cloneQueryable().toList();

            listenerContextManager.clear();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,COUNT((CASE WHEN t3.`phone` = ? THEN ? ELSE ? END)) AS `__count2__` FROM `doc_bank_card` t1 LEFT JOIN `doc_user` t3 ON t3.`id` = t1.`uid` GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__count2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("12345(String),1(Integer),null(null),1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
    }

    @Test
    public void testManyGroup13() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Long c = 1L;
        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(o -> o.bankCards())
                .leftJoin(BlogEntity.class, (user, t_blog) -> {
                    user.bankCards().where(bc -> {
                        bc.user().phone().eq("12345");
                    }).max(x -> x.type()).eq(t_blog.title());
                })
                .where((user, t_blog) -> {
                    t_blog.score().gt(BigDecimal.ZERO);
                    user.name().like("abc");
                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,MAX((CASE WHEN t4.`phone` = ? THEN t1.`type` ELSE ? END)) AS `__max2__` FROM `doc_bank_card` t1 LEFT JOIN `doc_user` t4 ON t4.`id` = t1.`uid` GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t2.`__max2__` = t3.`title` WHERE t3.`score` > ? AND t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("12345(String),null(null),false(Boolean),0(BigDecimal),%abc%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }


    @Test
    public void testManyGroup14() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Long c = 1L;
        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(o -> o.bankCards(), bcq -> bcq.where(o -> o.code().likeMatchRight("coder")))
                .where(user -> {
                    user.bankCards().where(bc -> {
                        bc.user().phone().eq("12345");
                    }).count().eq(c);
                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,COUNT((CASE WHEN t3.`phone` = ? THEN ? ELSE ? END)) AS `__count2__` FROM `doc_bank_card` t1 LEFT JOIN `doc_user` t3 ON t3.`id` = t1.`uid` WHERE t1.`code` LIKE ? GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__count2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("12345(String),1(Integer),null(null),%coder(String),1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }


    @Test
    public void testManyGroup29() {

        List<Draft2<Long, Long>> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().count().rangeOpenClosed(1L, 2L);
                })
                .select(user -> Select.DRAFT.of(
                        user.bankCards().where(bc -> bc.type().eq("1")).count(),
                        user.bankCards().where(bc -> bc.type().eq("2")).count()
                )).toList();
    }

    @Test
    public void testManyGroup39() {

        EntityQueryable<Draft4Proxy<String, Long, Long, Long>, Draft4<String, Long, Long, Long>> select = easyEntityQuery.queryable(DocBankCard.class)
                .groupBy(bank_card -> GroupKeys.of(bank_card.uid()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.count(),
                        group.where(g -> g.type().eq("1")).count(),
                        group.where(g -> g.type().eq("2")).count()
                ));
        List<Draft2<Long, Long>> list = easyEntityQuery.queryable(DocUser.class)
                .leftJoin(select, (user, l2) -> user.id().eq(l2.value1()))
                .where((user, l2) -> {
                    l2.value2().rangeOpenClosed(1L, 2L);
                })
                .select((user, l2) -> Select.DRAFT.of(
                        l2.value3(),
                        l2.value4()
                )).toList();
    }
}
