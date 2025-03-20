package com.easy.query.test;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.SQLMathExpression;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.school.SchoolClass;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2025/3/11 20:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest23 extends BaseTest {


    @Test
    public void testElement3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try {

            List<Draft2<String, String>> list = easyEntityQuery.queryable(DocUser.class)
                    .manyJoin(x -> x.bankCards())
                    .select(user -> Select.DRAFT.of(
                            user.bankCards().where(o -> o.type().eq("123")).max(x -> x.code()),
                            user.bankCards().where(o -> o.type().eq("123")).element(0).type()
                    )).toList();
        } catch (Exception ex) {

        }

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`__max2__` AS `value1`,t6.`type` AS `value2` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,MAX((CASE WHEN t1.`type` = ? THEN t1.`code` ELSE ? END)) AS `__max2__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` LEFT JOIN (SELECT t4.`id` AS `id`,t4.`uid` AS `uid`,t4.`code` AS `code`,t4.`type` AS `type`,t4.`bank_id` AS `bank_id` FROM (SELECT t3.`id`,t3.`uid`,t3.`code`,t3.`type`,t3.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t3.`uid` ORDER BY 1 = 1)) AS `__row__` FROM `doc_bank_card` t3 WHERE t3.`type` = ?) t4 WHERE t4.`__row__` = ?) t6 ON t6.`uid` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),null(null),123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void manyJoinMany2Many() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SchoolClass> list = easyEntityQuery.queryable(SchoolClass.class)
                .manyJoin(x -> x.schoolTeachers())
                .where(s -> {
                    s.schoolTeachers().where(x -> x.name().like("小明")).count().eq(1L);
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t LEFT JOIN (SELECT t2.`class_id` AS `class_id`,COUNT((CASE WHEN t1.`name` LIKE ? THEN ? ELSE ? END)) AS `__count2__` FROM `school_teacher` t1 INNER JOIN `school_class_teacher` t2 ON t1.`id` = t2.`teacher_id` GROUP BY t2.`class_id`) t4 ON t4.`class_id` = t.`id` WHERE IFNULL(t4.`__count2__`,0) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明%(String),1(Integer),null(null),1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void fetcherOrder() {
        List<Draft1<BigDecimal>> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .select(t_blog -> Select.DRAFT.of(
                        t_blog.expression().sqlSegment("({0}+{1})/{2}", c -> {
                            c.expression(t_blog.score()).expression(t_blog.star()).expression(t_blog.order());
                        }, BigDecimal.class)
                )).toList();


        easyEntityQuery.queryable(BlogEntity.class)
                .select(t_blog -> Select.DRAFT.of(
                        t_blog.expression().caseWhen(() -> {
                                }).then(1)
                                .elseEnd(
                                        t_blog.expression().sqlSegment("({0}+{1})/{2}", c -> {
                                            c.expression(t_blog.score()).expression(t_blog.star()).expression(t_blog.order());
                                        }, BigDecimal.class)
                                )
                )).toList();

//
//        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
//                 .where(t_blog -> t_blog.createTime().gt(LocalDateTime.of(2020, 1, 1, 0, 0)))
//                 .select(t_blog -> t_blog.FETCHER.id().score())
//                 .orderBy(t_blog -> {
//                     t_blog.score().asc();
//                 }).toList();
    }

    @Test
    public void testAddMulty() {

//        List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
//                .where(t_blog -> {
//                    t_blog.createTime().duration(LocalDateTime.now()).toDays().gt(5L);
//
//                    t_blog.expression().now()
//                            .duration(t_blog.createTime()).toDays().gt(5L);
//                })
//                .toList();
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft1<BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                .configure(o -> o.setPrintSQL(false))
                .select(t_blog -> Select.DRAFT.of(
                        t_blog.order().add(t_blog.star()).divide(t_blog.score())
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT ((t.`order` + t.`star`) / t.`score`) AS `value1` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
//     @Test
//     public void testAddMulty2(){
//
//         ListenerContext listenerContext = new ListenerContext();
//         listenerContextManager.startListen(listenerContext);
//
//         List<Draft2<BigDecimal,BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
//                 .configure(o->o.setPrintSQL(false))
//                 .select(t_blog -> Select.DRAFT.of(
//                         t_blog.expression().caseWhen(()->{
//                             t_blog.id().isNull();
//                         }).then(1).elseEnd(t_blog.order().add(t_blog.star()).divide(t_blog.score()),BigDecimal.class),
//                         t_blog.order().add(t_blog.star()).divide(t_blog.score())
//                 )).toList();
//         for (Draft2<BigDecimal,BigDecimal> bigDecimalDraft1 : list) {
//             System.out.println(bigDecimalDraft1.getValue1());
//             System.out.println(bigDecimalDraft1.getValue2());
//         }
//
//         listenerContextManager.clear();
//         Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//         JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//         Assert.assertEquals("SELECT ((t.`order` + t.`star`) / t.`score`) AS `value1` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
//         Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//
//     }


}
