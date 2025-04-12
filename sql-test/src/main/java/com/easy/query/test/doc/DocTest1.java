package com.easy.query.test.doc;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.partition.Part1;
import com.easy.query.core.proxy.partition.Part2;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.BaseTest;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

/**
 * create time 2023/11/21 21:48
 * 文件说明
 *
 * @author xuejiaming
 */
public class DocTest1 extends BaseTest {
    @Test
    public void test1() {
        List<Draft2<String, Boolean>> list = easyEntityQuery.queryable(SysUser.class)
                .select(s -> Select.DRAFT.of(
                        s.id(),
                        s.blogs().anyValue()
                )).toList();

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        BlogEntity blog = easyEntityQuery.queryable(BlogEntity.class)
                .findNotNull("1");
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();


    }

    @Test
    public void test2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        BlogEntity blog = easyEntityQuery.queryable(BlogEntity.class)
                .whereById("1")
                .firstNotNull();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();


    }


//    @Test
//    public void test3() {
//        MapKey<String> id = MapKeys.stringKey("id");
//        MapKey<String> phone = MapKeys.stringKey("phone");
//        MapKey<Integer> star = MapKeys.integerKey("star");
//        List<Draft3<String, Integer, LocalDateTime>> list = easyEntityQuery.queryable(SysUser.class)
//                .leftJoin(BlogEntity.class, (s, b2) -> s.phone().eq(b2.title()))
//                .where((s1, b2) -> {
//                    s1.departName().like("123");
//                    b2.star().gt(1);
//                })
//                .select((s1, b2) -> {
//                    MapTypeProxy map = new MapTypeProxy();
//                    map.put(id, s1.id());
//                    map.put(phone, s1.phone());
//                    map.put(star, b2.star());
//                    return map;
//                })
//                .leftJoin(Topic.class, (m, t) -> m.get(id).eq(t.id()))
//                .select((m, t) -> Select.DRAFT.of(
//                        m.get(phone),
//                        m.get(star),
//                        t.createTime()
//                )).toList();
//
//    }
//

    @Test
    public void testBlog1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Supplier<Exception> f = () -> {
            try {

                List<Part1<DocBlog, Long>> list = easyEntityQuery.queryable(DocBlog.class)
                        .where(d -> {
                            d.publishTime().gt(LocalDateTime.of(2024, 1, 1, 0, 0));
                        }).select(d -> Select.PARTITION.of(
                                d,
                                d.expression().rowNumberOver().partitionBy(d.topic()).orderByDescending(d.score())
                        )).toList();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`title`,t.`topic`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,(ROW_NUMBER() OVER (PARTITION BY t.`topic` ORDER BY t.`score` DESC)) AS `__part__column1` FROM `t_blog` t WHERE t.`publish_time` > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2024-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }
    @Test
    public void testBlog2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Supplier<Exception> f = () -> {
            try {


                List<DocBlog> list = easyEntityQuery.queryable(DocBlog.class)
                        .where(d -> {
                            d.publishTime().gt(LocalDateTime.of(2024, 1, 1, 0, 0));
                        }).select(d -> Select.PARTITION.of(
                                d,
                                d.expression().rowNumberOver().partitionBy(d.topic()).orderByDescending(d.score().nullOrDefault(BigDecimal.ZERO)),
                                d.expression().rowNumberOver().partitionBy(d.topic()).orderBy(d.star().nullOrDefault(0))
                        ))
                        .where(p2 -> {
                            p2.partColumn1().le(3L);
                            p2.partColumn2().le(5L);
                        }).select(p2 -> p2.entityTable()).toList();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`id`,t1.`title`,t1.`topic`,t1.`url`,t1.`star`,t1.`publish_time`,t1.`score`,t1.`status` FROM (SELECT t.`id`,t.`title`,t.`topic`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,(ROW_NUMBER() OVER (PARTITION BY t.`topic` ORDER BY IFNULL(t.`score`,?) DESC)) AS `__part__column1`,(ROW_NUMBER() OVER (PARTITION BY t.`topic` ORDER BY IFNULL(t.`star`,?) ASC)) AS `__part__column2` FROM `t_blog` t WHERE t.`publish_time` > ?) t1 WHERE t1.`__part__column1` <= ? AND t1.`__part__column2` <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("0(BigDecimal),0(Integer),2024-01-01T00:00(LocalDateTime),3(Long),5(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testBlog3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Supplier<Exception> f = () -> {
            try {

                List<Part2<DocBlog, Long, Long>> list = easyEntityQuery.queryable(DocBlog.class)
                        .where(d -> {
                            d.publishTime().gt(LocalDateTime.of(2024, 1, 1, 0, 0));
                        }).select(d -> Select.PARTITION.of(
                                d,
                                d.expression().rowNumberOver().partitionBy(d.topic()).orderByDescending(d.score().nullOrDefault(BigDecimal.ZERO)),
                                d.expression().rowNumberOver().partitionBy(d.topic()).orderBy(d.star().nullOrDefault(0))
                        ))
                        .where(p2 -> {
                            p2.partColumn1().le(3L);
                            p2.partColumn2().le(5L);
                        }).toList();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`id`,t1.`title`,t1.`topic`,t1.`url`,t1.`star`,t1.`publish_time`,t1.`score`,t1.`status`,t1.`__part__column1` AS `__part__column1`,t1.`__part__column2` AS `__part__column2` FROM (SELECT t.`id`,t.`title`,t.`topic`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,(ROW_NUMBER() OVER (PARTITION BY t.`topic` ORDER BY IFNULL(t.`score`,?) DESC)) AS `__part__column1`,(ROW_NUMBER() OVER (PARTITION BY t.`topic` ORDER BY IFNULL(t.`star`,?) ASC)) AS `__part__column2` FROM `t_blog` t WHERE t.`publish_time` > ?) t1 WHERE t1.`__part__column1` <= ? AND t1.`__part__column2` <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("0(BigDecimal),0(Integer),2024-01-01T00:00(LocalDateTime),3(Long),5(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testBlog4() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Supplier<Exception> f = () -> {
            try {

              easyEntityQuery.queryable(TestSelf.class)
                      .where(t -> {
                          t.uid().eq("1");
                      })
                      .select(t -> Select.DRAFT.of(
                              t.myUser().name(),
                              t.parent().myUser().name()
                      )).toList();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`name` AS `value1`,t3.`name` AS `value2` FROM `test_self` t LEFT JOIN `my_user` t1 ON t1.`id` = t.`uid` LEFT JOIN `test_self` t2 ON t2.`id` = t.`pid` LEFT JOIN `my_user` t3 ON t3.`id` = t2.`uid` WHERE t.`uid` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testBlog5() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Supplier<Exception> f = () -> {
            try {

              easyEntityQuery.queryable(TestSelf.class)
                      .where(t -> {
                          t.uid().eq("1");
                      })
                      .selectAutoInclude(TopicSelfVO.class).toList();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`name` AS `uname1`,t3.`name` AS `uname2`,t.`id`,t.`name` FROM `test_self` t LEFT JOIN `my_user` t1 ON t1.`id` = t.`uid` LEFT JOIN `test_self` t2 ON t2.`id` = t.`pid` LEFT JOIN `my_user` t3 ON t3.`id` = t2.`uid` WHERE t.`uid` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
}
