package com.easy.query.test;

import com.easy.query.api.proxy.base.BigDecimalProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
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
import com.easy.query.test.doc.MyComUser;
import com.easy.query.test.doc.entity.DocBank;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.doc.entity.DocPart;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.doc.entity.DocUserBook;
import com.easy.query.test.doc.entity.proxy.DocUserProxy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/3/7 16:56
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest22 extends BaseTest {

     public void test1(){
         BigDecimal bigDecimal = easyEntityQuery.queryable(Topic.class)
                 .maxOrDefault(x -> x.id().toNumber(Long.class).add(1), BigDecimal.ZERO);
     }
    @Test
    public void testManyGroup() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        easyEntityQuery.queryable(DocUser.class)
                .manyJoin(o -> o.bankCards())
                .where(user -> {
                    user.bankCards().where(x -> x.type().eq("123")).
                            sum(o -> o.code().toNumber(Integer.class))
                            .eq(123);
                    user.bankCards().where(x -> x.type().eq("123")).
                            sum(o -> o.code().toNumber(Integer.class))
                            .eq(456);
                    user.bankCards().where(x -> x.type().eq("456")).
                            sum(o -> o.code().toNumber(Integer.class))
                            .eq(789);
                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,SUM((CASE WHEN t1.`type` = ? THEN CAST(t1.`code` AS SIGNED) ELSE ? END)) AS `__sum2__`,SUM((CASE WHEN t1.`type` = ? THEN CAST(t1.`code` AS SIGNED) ELSE ? END)) AS `__sum3__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__sum2__` = ? AND t2.`__sum2__` = ? AND t2.`__sum3__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),0(Integer),456(String),0(Integer),123(Integer),456(Integer),789(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testManyGroupJoin() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        easyEntityQuery.queryable(DocUser.class)
                .manyJoin(o -> o.bankCards(), bcq -> bcq.where(x -> {
                    //支持隐式join和普通属性筛选
                    x.bank().name().eq("银行");
                    x.type().like("45678");
                }))
                .where(user -> {
                    user.bankCards().where(x -> x.type().eq("123")).
                            sum(o -> o.code().toNumber(Integer.class))
                            .eq(123);
                    user.bankCards().where(x -> x.type().eq("123")).
                            sum(o -> o.code().toNumber(Integer.class))
                            .eq(456);
                    user.bankCards().where(x -> x.type().eq("456")).
                            sum(o -> o.code().toNumber(Integer.class))
                            .eq(789);
                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,SUM((CASE WHEN t1.`type` = ? THEN CAST(t1.`code` AS SIGNED) ELSE ? END)) AS `__sum2__`,SUM((CASE WHEN t1.`type` = ? THEN CAST(t1.`code` AS SIGNED) ELSE ? END)) AS `__sum3__` FROM `doc_bank_card` t1 LEFT JOIN `doc_bank` t2 ON t2.`id` = t1.`bank_id` WHERE t2.`name` = ? AND t1.`type` LIKE ? GROUP BY t1.`uid`) t3 ON t3.`uid` = t.`id` WHERE t3.`__sum2__` = ? AND t3.`__sum2__` = ? AND t3.`__sum3__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),0(Integer),456(String),0(Integer),银行(String),%45678%(String),123(Integer),456(Integer),789(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testManyGroupJoin2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft3<String, Integer, String>> 银行 = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(o -> o.bankCards(), bcq -> bcq.where(x -> {
                    //支持隐式join和普通属性筛选
                    x.bank().name().eq("银行");
                    x.type().like("45678");
                }))
                .where(user -> {
                    user.bankCards().where(x -> x.type().eq("123")).
                            sum(o -> o.code().toNumber(Integer.class))
                            .eq(123);
                    user.bankCards().where(x -> x.type().eq("123")).
                            sum(o -> o.code().toNumber(Integer.class))
                            .eq(456);
                    user.bankCards().where(x -> x.type().eq("456")).
                            sum(o -> o.code().toNumber(Integer.class))
                            .eq(789);
                })
                .orderBy(user -> {
                    user.bankCards().where(x -> x.type().eq("123")).
                            sum(o -> o.code().toNumber(Integer.class)).asc();
                    user.bankCards().where(x -> x.type().eq("123")).
                            max(o -> o.code()).desc();
                })
                .select(user -> Select.DRAFT.of(
                        user.id(),
                        user.bankCards().where(x -> x.type().eq("123")).
                                sum(o -> o.code().toNumber(Integer.class)),
                        user.bankCards().where(x -> x.type().eq("123")).
                                min(o -> o.code())
                ))
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,t3.`__sum2__` AS `value2`,t3.`__min5__` AS `value3` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,SUM((CASE WHEN t1.`type` = ? THEN CAST(t1.`code` AS SIGNED) ELSE ? END)) AS `__sum2__`,SUM((CASE WHEN t1.`type` = ? THEN CAST(t1.`code` AS SIGNED) ELSE ? END)) AS `__sum3__`,MAX((CASE WHEN t1.`type` = ? THEN t1.`code` ELSE ? END)) AS `__max4__`,MIN((CASE WHEN t1.`type` = ? THEN t1.`code` ELSE ? END)) AS `__min5__` FROM `doc_bank_card` t1 LEFT JOIN `doc_bank` t2 ON t2.`id` = t1.`bank_id` WHERE t2.`name` = ? AND t1.`type` LIKE ? GROUP BY t1.`uid`) t3 ON t3.`uid` = t.`id` WHERE t3.`__sum2__` = ? AND t3.`__sum2__` = ? AND t3.`__sum3__` = ? ORDER BY t3.`__sum2__` ASC,t3.`__max4__` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),0(Integer),456(String),0(Integer),123(String),null(null),123(String),null(null),银行(String),%45678%(String),123(Integer),456(Integer),789(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }



    @Test
    public void testManyGroupJoin3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        easyEntityQuery.queryable(DocUser.class)
                .manyJoin(o -> o.bankCards())
                .where(user -> {
                    user.bankCards().where(x -> x.code().likeMatchLeft("400")).any();
                })
                .select(user -> Select.DRAFT.of(
                        user.id(),
                        user.bankCards().where(x->x.type().eq("工商")).count(),
                        user.bankCards().where(x->x.type().eq("建设")).count(),
                        user.bankCards().where(x->x.type().eq("农业")).count()
                ))
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,t2.`__count3__` AS `value2`,t2.`__count4__` AS `value3`,t2.`__count5__` AS `value4` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,(CASE WHEN COUNT((CASE WHEN t1.`code` LIKE ? THEN ? ELSE ? END)) > 0 THEN TRUE ELSE FALSE END) AS `__any2__`,COUNT((CASE WHEN t1.`type` = ? THEN ? ELSE ? END)) AS `__count3__`,COUNT((CASE WHEN t1.`type` = ? THEN ? ELSE ? END)) AS `__count4__`,COUNT((CASE WHEN t1.`type` = ? THEN ? ELSE ? END)) AS `__count5__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__any2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("400%(String),1(Integer),null(null),工商(String),1(Integer),null(null),建设(String),1(Integer),null(null),农业(String),1(Integer),null(null),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

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
        Assert.assertEquals("SELECT t2.`__any2__` AS `value1`,t2.`__none3__` AS `value2` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,(CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END) AS `__any2__`,(CASE WHEN COUNT(*) > 0 THEN FALSE ELSE TRUE END) AS `__none3__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__any2__` = ? AND t2.`__none3__` = ? AND t2.`__any2__` = ? AND t2.`__none3__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
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
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,(CASE WHEN COUNT((CASE WHEN t1.`type` = ? THEN ? ELSE ? END)) > 0 THEN TRUE ELSE FALSE END) AS `__any2__`,(CASE WHEN COUNT((CASE WHEN t1.`type` = ? THEN ? ELSE ? END)) > 0 THEN FALSE ELSE TRUE END) AS `__none3__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__any2__` = ? AND t2.`__none3__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
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
    public void testManyGroup15() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Long c = 1L;
        List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                .manyJoin(o -> o.user().userBooks())
                .where(user -> {
                    user.user().userBooks().where(bc -> {
                        bc.name().eq("12345");
                    }).count().eq(c);
                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`uid`,t.`code`,t.`type`,t.`bank_id` FROM `doc_bank_card` t LEFT JOIN `doc_user` t1 ON t1.`id` = t.`uid` LEFT JOIN (SELECT t2.`uid`,COUNT((CASE WHEN t2.`name` = ? THEN ? ELSE ? END)) AS `__count2__` FROM `doc_user_book` t2 GROUP BY t2.`uid`) t3 ON t3.`uid` = t1.`id` WHERE t3.`__count2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("12345(String),1(Integer),null(null),1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testManyGroup16() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Long c = 1L;
        List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .leftJoin(DocUser.class, (t_blog, user) -> t_blog.id().eq(user.id()))
                .manyJoin((t_blog, user) -> user.userBooks())
                .where((t_blog, user) -> {
                    user.userBooks().where(x -> x.name().like("123")).max(x -> x.name()).eq("你好");
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN `doc_user` t1 ON t.`id` = t1.`id` LEFT JOIN (SELECT t2.`uid`,MAX((CASE WHEN t2.`name` LIKE ? THEN t2.`name` ELSE ? END)) AS `__max2__` FROM `doc_user_book` t2 GROUP BY t2.`uid`) t3 ON t3.`uid` = t1.`id` WHERE t.`deleted` = ? AND t3.`__max2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String),null(null),false(Boolean),你好(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testManyGroup17() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Long c = 1L;
        easyEntityQuery.queryable(DocUser.class)
                .manyJoin(user -> user.userBooks())
                .where(user -> {
                    user.userBooks().flatElement().name().like("123");
//                    user.userBooks().where(x->x.name().like("123")).any();
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,(CASE WHEN COUNT((CASE WHEN t1.`name` LIKE ? THEN ? ELSE ? END)) > 0 THEN TRUE ELSE FALSE END) AS `__any2__` FROM `doc_user_book` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__any2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String),1(Integer),null(null),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testManyGroup18() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Long c = 1L;
        easyEntityQuery.queryable(DocUser.class)
                .manyJoin(user -> user.userBooks())
                .where(user -> {
                    user.userBooks().flatElement().name().like("123");
                    user.userBooks().where(x -> x.name().like("123")).any();
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,(CASE WHEN COUNT((CASE WHEN t1.`name` LIKE ? THEN ? ELSE ? END)) > 0 THEN TRUE ELSE FALSE END) AS `__any2__` FROM `doc_user_book` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__any2__` = ? AND t2.`__any2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String),1(Integer),null(null),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
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

    @Test
    public void testManyOrder(){

        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(DocPart.class));
        codeFirstCommand.executeWithTransaction(s->s.commit());


//
//        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
////                .manyJoin(x->x.bankCards())
//                .orderBy(user -> {
//                    user.bankCards().where(bk -> bk.type().eq("建设"))
//                            .sum(x -> x.parts().count()).asc();
//
//                }).toList();


        List<DocUser> list2 = easyEntityQuery.queryable(DocUser.class)
//                .manyJoin(x->x.bankCards())
                .orderBy(user -> {
                    user.bankCards().where(bk -> bk.type().eq("建设"))
                            .count(x -> x.bank().id()).asc();

                }).toList();
    }

    @Test
    public void testOrderCountDistinct1(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
//                .manyJoin(x->x.bankCards())
                .orderBy(user -> {
                    user.bankCards().where(bk -> bk.type().eq("建设"))
                            .sum(x -> x.parts().distinct().count(p->p.id())).asc();

                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t ORDER BY IFNULL((SELECT SUM((SELECT COUNT(DISTINCT t2.`id`) FROM `doc_part` t2 WHERE t2.`card_id` = t1.`id`)) FROM `doc_bank_card` t1 WHERE t1.`uid` = t.`id` AND t1.`type` = ?),0) ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("建设(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testOrderCountDistinct2(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(x->x.bankCards())
                .orderBy(user -> {
                    user.bankCards().where(bk -> bk.type().eq("建设"))
                            .sum(x -> x.parts().distinct().count(p->p.id())).asc();

                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,SUM((CASE WHEN t1.`type` = ? THEN (SELECT COUNT(DISTINCT t3.`id`) FROM `doc_part` t3 WHERE t3.`card_id` = t1.`id`) ELSE ? END)) AS `__sum2__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` ORDER BY t2.`__sum2__` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("建设(String),0(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testOrderCountDistinct3(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<DocUser> list2 = easyEntityQuery.queryable(DocUser.class)
//                .manyJoin(x->x.bankCards())
                .orderBy(user -> {
                    user.bankCards().where(bk -> bk.type().eq("建设"))
                            .distinct()
                            .count(x -> x.bank().id()).asc();

                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t ORDER BY (SELECT COUNT(DISTINCT t2.`id`) FROM `doc_bank_card` t1 LEFT JOIN `doc_bank` t2 ON t2.`id` = t1.`bank_id` WHERE t1.`uid` = t.`id` AND t1.`type` = ?) ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("建设(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testOrderCountDistinct4(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<DocUser> list2 = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(x->x.bankCards())
                .orderBy(user -> {
                    user.bankCards().where(bk -> bk.type().eq("建设"))
                            .distinct()
                            .count(x -> x.bank().id()).asc();

                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,COUNT(DISTINCT (CASE WHEN t1.`type` = ? THEN t3.`id` ELSE ? END)) AS `__count2__` FROM `doc_bank_card` t1 LEFT JOIN `doc_bank` t3 ON t3.`id` = t1.`bank_id` GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` ORDER BY t2.`__count2__` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("建设(String),null(null)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testOrderCountDistinct5(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
//                .manyJoin(x->x.bankCards())
                .orderBy(user -> {
                    user.bankCards().where(bk -> bk.type().eq("建设")).distinct()
                            .sum(x -> x.parts().distinct().count(p->p.id())).asc();

                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t ORDER BY IFNULL((SELECT SUM(DISTINCT (SELECT COUNT(DISTINCT t2.`id`) FROM `doc_part` t2 WHERE t2.`card_id` = t1.`id`)) FROM `doc_bank_card` t1 WHERE t1.`uid` = t.`id` AND t1.`type` = ?),0) ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("建设(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testOrderCountDistinct6(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(x->x.bankCards())
                .orderBy(user -> {
                    user.bankCards().where(bk -> bk.type().eq("建设")).distinct()
                            .sum(x -> x.parts().distinct().count(p->p.id())).asc();

                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,SUM(DISTINCT (CASE WHEN t1.`type` = ? THEN (SELECT COUNT(DISTINCT t3.`id`) FROM `doc_part` t3 WHERE t3.`card_id` = t1.`id`) ELSE ? END)) AS `__sum2__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` ORDER BY t2.`__sum2__` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("建设(String),0(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testOrderCountDistinct7(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(x->x.bankCards())
                .orderBy(user -> {
                    user.bankCards().where(bk -> bk.type().eq("建设")).distinct()
                            .sum(x -> x.parts().distinct().count(p->p.id().nullOrDefault("x"))).asc();

                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,SUM(DISTINCT (CASE WHEN t1.`type` = ? THEN (SELECT COUNT(DISTINCT IFNULL(t3.`id`,?)) FROM `doc_part` t3 WHERE t3.`card_id` = t1.`id`) ELSE ? END)) AS `__sum2__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` ORDER BY t2.`__sum2__` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("建设(String),x(String),0(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testOrderCountDistinct8(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(x->x.bankCards())
                .orderBy(user -> {
                    user.bankCards().where(bk -> bk.type().eq("建设")).distinct()
                            .avg(x -> x.type().toNumber(long.class)).asc();

                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,AVG(DISTINCT (CASE WHEN t1.`type` = ? THEN CAST(t1.`type` AS SIGNED) ELSE ? END)) AS `__avg2__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` ORDER BY t2.`__avg2__` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("建设(String),null(null)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testOrderCountDistinct9(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(x->x.bankCards())
                .where(user -> {
                    user.bankCards().where(bk -> bk.type().eq("建设")).distinct()
                            .max(x -> x.type()).eq("maxtype");

                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,MAX((CASE WHEN t1.`type` = ? THEN t1.`type` ELSE ? END)) AS `__max2__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__max2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("建设(String),null(null),maxtype(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testOrderCountDistinct10(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(x->x.bankCards())
                .where(user -> {
                    user.bankCards().where(bk -> bk.type().eq("建设"))
                            .max(x -> x.type()).eq("maxtype");

                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,MAX((CASE WHEN t1.`type` = ? THEN t1.`type` ELSE ? END)) AS `__max2__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__max2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("建设(String),null(null),maxtype(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testOrderCountDistinct11(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
//                .manyJoin(x->x.bankCards())
                .where(user -> {
                    user.bankCards().where(bk -> bk.type().eq("建设"))
                            .max(x -> x.type()).eq("maxtype");

                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t WHERE (SELECT MAX(t1.`type`) FROM `doc_bank_card` t1 WHERE t1.`uid` = t.`id` AND t1.`type` = ?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("建设(String),maxtype(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }



    @Test
    public void testOrderCountDistinct12(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(x->x.bankCards())
                .where(user -> {
                    user.bankCards().where(bk -> bk.type().eq("建设")).distinct()
                            .min(x -> x.type()).eq("maxtype");

                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,MIN((CASE WHEN t1.`type` = ? THEN t1.`type` ELSE ? END)) AS `__min2__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__min2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("建设(String),null(null),maxtype(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testOrderCountDistinct13(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(x->x.bankCards())
                .where(user -> {
                    user.bankCards().where(bk -> bk.type().eq("建设"))
                            .min(x -> x.type()).eq("maxtype");

                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,MIN((CASE WHEN t1.`type` = ? THEN t1.`type` ELSE ? END)) AS `__min2__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__min2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("建设(String),null(null),maxtype(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testOrderCountDistinct14(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
//                .manyJoin(x->x.bankCards())
                .where(user -> {
                    user.bankCards().where(bk -> bk.type().eq("建设"))
                            .min(x -> x.type()).eq("maxtype");

                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t WHERE (SELECT MIN(t1.`type`) FROM `doc_bank_card` t1 WHERE t1.`uid` = t.`id` AND t1.`type` = ?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("建设(String),maxtype(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testOrderCountDistinct15(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(x->x.bankCards())
                .where(user -> {
                    user.bankCards().none(bk -> bk.type().eq("建设"));

                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`phone`,t.`age` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,(CASE WHEN COUNT((CASE WHEN t1.`type` = ? THEN ? ELSE ? END)) > 0 THEN FALSE ELSE TRUE END) AS `__none2__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` WHERE t2.`__none2__` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("建设(String),1(Integer),null(null),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testBigDecimal(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<BigDecimal> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(o -> new BigDecimalProxy(o.score())).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`score` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public void testAdd(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        BigDecimal bigDecimal = easyEntityQuery.queryable(BlogEntity.class)
                .maxOrDefault(o -> o.star().add(1L), BigDecimal.ZERO);
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT MAX((t.`star` + ?)) FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Long),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

}
