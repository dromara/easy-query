package com.easy.query.test;

import com.alibaba.fastjson2.annotation.JSONField;
import com.easy.query.api.proxy.base.StringProxy;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.entity.EntityMappingRule;
import com.easy.query.core.basic.entity.PropertyFirstEntityMappingRule;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.executor.internal.command.JdbcCommand;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.StreamIterable;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.result.QueryExecuteResult;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.configuration.bean.PropertyDescriptorMatcher;
import com.easy.query.core.configuration.bean.entity.EntityPropertyDescriptorMatcher;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.func.def.enums.TimeUnitEnum;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.SQLMathExpression;
import com.easy.query.core.proxy.columns.types.SQLIntegerTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLStringTypeColumn;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.core.draft.Draft4;
import com.easy.query.core.proxy.core.draft.Draft6;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionCompareComparableAnyChainExpression;
import com.easy.query.core.proxy.grouping.Grouping1;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.grouping.proxy.Grouping2Proxy;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.test.common.MyQueryConfiguration;
import com.easy.query.test.doc.entity.DocBankCard;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.entity.BaseEntity;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.MyOrderDetail;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.m2m.UserAccount;
import com.easy.query.test.entity.m2m.UserBook;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.school.SchoolClass;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.mssql.entity.MsSQLMyTopic;
import lombok.Data;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * create time 2025/3/11 20:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest23 extends BaseTest {

    @Before
    public void before() {
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(UserAccount.class, UserBook.class));
        codeFirstCommand.executeWithTransaction(s -> s.commit());
    }

    @Test
    public void testElement3() {
        List<DocUser> list1 = easyEntityQuery.queryable(DocUser.class)
                .orderBy(user -> user.age())
                .toList();


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try {

            List<Draft2<String, String>> list = easyEntityQuery.queryable(DocUser.class)
                    .subQueryToGroupJoin(x -> x.bankCards())
                    .select(user -> Select.DRAFT.of(
                            user.bankCards().where(o -> o.type().eq("123")).max(x -> x.code()),
                            user.bankCards().where(o -> o.type().eq("123")).element(0).type()
                    )).toList();
        } catch (Exception ex) {

        }

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`__max2__` AS `value1`,t6.`type` AS `value2` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,MAX((CASE WHEN t1.`type` = ? THEN t1.`code` ELSE NULL END)) AS `__max2__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` LEFT JOIN (SELECT t4.`id` AS `id`,t4.`uid` AS `uid`,t4.`code` AS `code`,t4.`type` AS `type`,t4.`bank_id` AS `bank_id` FROM (SELECT t3.`id`,t3.`uid`,t3.`code`,t3.`type`,t3.`bank_id`,(ROW_NUMBER() OVER (PARTITION BY t3.`uid`)) AS `__row__` FROM `doc_bank_card` t3 WHERE t3.`type` = ?) t4 WHERE t4.`__row__` = ?) t6 ON t6.`uid` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void manyJoinMany2Many() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SchoolClass> list = easyEntityQuery.queryable(SchoolClass.class)
                .subQueryToGroupJoin(x -> x.schoolTeachers())
                .where(s -> {
                    s.schoolTeachers().where(x -> x.name().like("小明")).count().eq(1L);
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `school_class` t LEFT JOIN (SELECT t2.`class_id` AS `class_id`,COUNT((CASE WHEN t1.`name` LIKE ? THEN ? ELSE NULL END)) AS `__count2__` FROM `school_teacher` t1 INNER JOIN `school_class_teacher` t2 ON t1.`id` = t2.`teacher_id` GROUP BY t2.`class_id`) t4 ON t4.`class_id` = t.`id` WHERE IFNULL(t4.`__count2__`,0) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%小明%(String),1(Integer),1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

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


    @Test
    public void testPageDistinct() {


        {
            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);

            EasyPageResult<Draft2<String, String>> pageResult = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.or(() -> {
                            bank_card.id().eq("123");
                            bank_card.id().isNotNull();
                        });
                    })
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.code(),
                            bank_card.user().name()
                    )).distinct().toPageResult(1, 2);

            {

                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT COUNT(*) FROM (SELECT DISTINCT t.`code` AS `value1`,t1.`name` AS `value2` FROM `doc_bank_card` t LEFT JOIN `doc_user` t1 ON t1.`id` = t.`uid` WHERE (t.`id` = ? OR t.`id` IS NOT NULL)) t2", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
//                Assert.assertEquals("SELECT t.`name`,t.`class_id` AS `__relation__classId` FROM `school_student` t WHERE t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            }
        }
    }

    @Test
    public void testPageDistinct2() {


        {
            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);

            List<Draft2<String, String>> list = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.or(() -> {
                            bank_card.id().eq("123");
                            bank_card.id().isNotNull();
                        });
                    })
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.code(),
                            bank_card.user().name()
                    )).distinct().toList();

            {

                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT DISTINCT t.`code` AS `value1`,t1.`name` AS `value2` FROM `doc_bank_card` t LEFT JOIN `doc_user` t1 ON t1.`id` = t.`uid` WHERE (t.`id` = ? OR t.`id` IS NOT NULL)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
//                Assert.assertEquals("SELECT t.`name`,t.`class_id` AS `__relation__classId` FROM `school_student` t WHERE t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            }
        }
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


    @Test
    public void testPageDistinct3() {


        {
            ListenerContext listenerContext = new ListenerContext(true);
            listenerContextManager.startListen(listenerContext);

            EasyPageResult<Draft1<String>> pageResult = easyEntityQuery.queryable(DocBankCard.class)
                    .where(bank_card -> {
                        bank_card.or(() -> {
                            bank_card.id().eq("123");
                            bank_card.id().isNotNull();
                        });
                    })
                    .select(bank_card -> Select.DRAFT.of(
                            bank_card.user().name()
                    )).distinct().toPageResult(1, 2);

            {

                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                Assert.assertEquals("SELECT COUNT(DISTINCT t1.`name`) FROM `doc_bank_card` t LEFT JOIN `doc_user` t1 ON t1.`id` = t.`uid` WHERE (t.`id` = ? OR t.`id` IS NOT NULL)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            }
//            {
//                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
//                Assert.assertEquals("SELECT t.`name`,t.`class_id` AS `__relation__classId` FROM `school_student` t WHERE t.`class_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            }
        }
//        SQLFunc fx = easyEntityQuery.getRuntimeContext().fx();
//        SQLFunction caseWhenXxxxxx = fx.anySQLFunction("case when xxxxxx", c -> {
//        });
//        List<Draft1<BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
//                .select(t_blog -> Select.DRAFT.of(
//                        t_blog.expression().sqlSegment("({0} - {1})", c -> {
//                            c.expression(t_blog.star()).subQuery(
//                                    easyEntityQuery.queryable(Topic.class)
//                                            .where(t_topic -> {
//                                                t_topic.id().eq(t_blog.id());
//                                            }).selectColumn(t_topic -> t_topic.stars().sum())
//                            );
//                        }, BigDecimal.class)
//                )).toList();
//
//
//        List<Draft1<BigDecimal>> list1 = easyEntityQuery.queryable(DocUser.class)
//                .manyJoin(x->x.bankCards())
//                .select(user -> Select.DRAFT.of(
//                        user.bankCards().sum(x -> x.code().toNumber(BigDecimal.class))
//                )).toList();


//        easyEntityQuery.queryable(BlogEntity.class)
//                .where(t_blog -> {
//                    t_blog.expression().sql("id > 1");
//                })

    }


    @Test
    public void subquery() {

//        try (JdbcStreamResult<BlogEntity> streamResult = easyEntityQuery.queryable(BlogEntity.class)
//                .toStreamResult(100)) {
//            StreamIterable<BlogEntity> streamIterable = streamResult.getStreamIterable();
//            Iterator<BlogEntity> iterator = streamIterable.iterator();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        try (JdbcStreamResult<Map> streamResult = easyEntityQuery.getEasyQueryClient()
                .queryable("select * from t_blog where id <> ?", Map.class, Arrays.asList("1"))
                .toStreamResult(100)) {
            JdbcCommand<QueryExecuteResult> jdbcCommand = streamResult.getJdbcCommand();
            QueryExecuteResult execute = jdbcCommand.execute();
            StreamResultSet streamResultSet = execute.getStreamResultSet();
            ResultSetMetaData metaData = streamResultSet.getMetaData();
            Assert.assertNotNull(metaData);

            StreamIterable<Map> streamIterable = streamResult.getStreamIterable();
            for (Map map : streamIterable) {
                System.out.println(map);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testAdd() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    Expression expression = t_blog.expression();
                    t_blog.id().eq("123");
                    expression.exists(() -> {
                        return expression.subQueryable(Topic.class)
                                .where(t_topic -> {
                                    t_blog.id().eq(t_topic.id().nullOrDefault("1"));
                                }).groupBy(t_topic -> GroupKeys.of(t_topic.id()))
                                .having(group -> t_blog.id().count().gt(1L)).orderBy(t_topic -> {
                                    t_blog.id().asc();
                                });
                    });

                })
                .toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? AND EXISTS (SELECT 1 FROM `t_topic` t1 WHERE  t.`id` = IFNULL(t1.`id`,?) GROUP BY t1.`id` HAVING COUNT(t.`id`) > ? ORDER BY t.`id` ASC)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String),1(String),1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

//        easyEntityQuery.queryable(DocBankCard.class)
//                .include(s->s.bank())
//                .where(bank_card -> {
//                    bank_card.bank()
//                })

    }

    @Test
    public void anyTest() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft1<String>> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.phone().eq("123");
                    user.userBooks().any(x -> {
                        user.name().eq(x.name());
                        user.phone().eq("123");
                    });
                    user.phone().eq("123");
                }).select(user -> Select.DRAFT.of(
                        user.bankCards().where(x -> {
                            user.name().eq(x.code());
                            user.phone().eq("123");
                            x.code().eq("123");
                        }).max(x -> x.type())
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (SELECT MAX(t2.`type`) FROM `doc_bank_card` t2 WHERE t2.`uid` = t.`id` AND t.`name` = t2.`code` AND t.`phone` = ? AND t2.`code` = ?) AS `value1` FROM `doc_user` t WHERE t.`phone` = ? AND EXISTS (SELECT 1 FROM `doc_user_book` t1 WHERE t1.`uid` = t.`id` AND t.`name` = t1.`name` AND t.`phone` = ? LIMIT 1) AND t.`phone` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String),123(String),123(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testaaa() {
//        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
//                .where(t_blog -> {
//                    t_blog.createTime().plusYears(1).plusMonths(6).plus(10, TimeUnitEnum.DAYS).eq(LocalDateTime.now());
//                }).toList();

//        List<Topic> list = easyEntityQuery.queryable(Topic.class)
//                .where(o -> {
//                    Expression expression = o.expression();
//
//                    expression.exists(() -> {
//                        return expression.subQueryable(BlogEntity.class).where(q -> {
//                            q.id().eq("1" );
//                            q.id().eq(o.id());
//                        });
//                    });
//                }).toList();

//
//        EntityQueryable<BlogEntityProxy, BlogEntity> subQueryable = easyEntityQuery.queryable(BlogEntity.class)
//                .where(o -> q.id().eq("1"));
//
//        List<Topic> list = easyEntityQuery.queryable(Topic.class)
//                .where(o -> {
//                    Expression expression = o.expression();
//                    expression.notExists(() -> {
//                        return expression.subQueryable(BlogEntity.class).where(q -> {
//                            q.id().eq("1");
//                            q.id().eq(o.id());
//                        });
//                    });
//                }).toList();


        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> {
                    Query<String> stringQuery = o.expression().subQueryable(BlogEntity.class)
                            .where(x -> o.id().eq("123"))
                            .selectColumn(x -> x.id());

                    o.id().in(
                            stringQuery
                    );
                }).toList();
    }
//
//    @Data
//    public static class AA{
//        private String firstName;
//        private String lastName;
//
//        public String getFullName(){
//            return firstName+lastName;
//        }
//    }

    @Test
    public void testJoin() {
        //查询银行卡
        List<DocBankCard> list = easyEntityQuery.queryable(DocBankCard.class)
                .where(bank_card -> {
                    //条件银行卡的所属用户姓名叫小明
                    bank_card.user().name().eq("小明");
                }).toList();

        List<DocBankCard> list1 = easyEntityQuery.queryable(DocBankCard.class)
                .where(bank_card -> {
                    bank_card.user().phone().like("1234");
                    bank_card.bank().name().eq("工商银行");
                }).toList();

        List<Draft3<String, String, String>> list2 = easyEntityQuery.queryable(DocBankCard.class)
                .where(bank_card -> {
                    bank_card.user().name().eq("小明");
                    bank_card.bank().name().eq("工商银行");
                })
                .orderBy(bank_card -> bank_card.code().asc())
                .select(bank_card -> Select.DRAFT.of(
                        bank_card.user().name(),
                        bank_card.bank().name(),
                        bank_card.code()
                )).toList();
    }

    @Test
    public void testJoin1() {

        List<Draft2<String, String>> list2 = easyEntityQuery.queryable(DocBankCard.class)
                .where(bank_card -> {
                    bank_card.bank().name().eq("工商银行");
                })
                .orderBy(bank_card -> bank_card.code().asc())
                .select(bank_card -> Select.DRAFT.of(
                        bank_card.bank().name(),
                        bank_card.code()
                )).toList();
    }

    @Test
    public void testJoin2() {
        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .where(user -> {
                    user.bankCards().where(card -> {
                        card.bank().name().eq("工商银行");
                    }).count().gt(2L);

                    user.bankCards().none(card -> {
                        card.bank().name().eq("建设银行");
                    });
                }).toList();

    }

    public static class MyParameter {
        public Map<String, String> getParameters() {
            return null;
        }
    }

    @Test
    public void testJoin3() {
        MyParameter myParameter = new MyParameter();
        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
                .subQueryToGroupJoin(u -> u.bankCards())
                .where(user -> {
                    user.bankCards().where(card -> {
                        card.bank().name().eq("工商银行");
                    }).count().gt(2L);

                    user.bankCards().none(card -> {
                        card.bank().name().eq("建设银行");
                    });
                }).orderBy(myParameter.getParameters() != null, user -> {
                    user.name().asc();
                }).toList();

    }
//    @Test
//    public void testJoin5() {
//        List<DocUser> list = easyEntityQuery.queryable(DocUser.class)
//                .where(user -> {
//                    //筛选用户银行卡第二张开户的是工商银行的
//                    user.bankCards().orderBy(s->s.createTime().asc()).element(1).bank().name().eq("工商银行");
//                }).toList();
//
//    }

    @Test
    public void testJoin4() {
        EasyQueryClient build = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                    op.setExecutorCorePoolSize(1);
                    op.setExecutorMaximumPoolSize(0);
                    op.setMaxShardingQueryLimit(10);
                    op.setDefaultDataSourceName("ds2020");
                    op.setThrowIfRouteNotMatch(false);
                    op.setMaxShardingRouteCount(512);
                    op.setDefaultDataSourceMergePoolSize(20);
                    op.setStartTimeJob(true);
                    op.setReverseOffsetThreshold(10);
                })
//                .replaceService(Column2MapKeyConversion.class, UpperColumn2MapKeyConversion.class)
                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
//                .replaceService(Dialect.class, DefaultDialect.class)
//                .replaceService(EntityMappingRule.class, PropertyEntityMappingRule.class)
                .replaceService(EntityMappingRule.class, PropertyFirstEntityMappingRule.class)
                .replaceService(PropertyDescriptorMatcher.class, EntityPropertyDescriptorMatcher.class)
//                .replaceService(EasyPageResultProvider.class,MyEasyPageResultProvider.class)
//                .replaceService(SQLKeyword.class, DefaultSQLKeyword.class)
//                .replaceService(BeanValueCaller.class, ReflectBeanValueCaller.class)
                .build();
        EntityMetadata entityMetadata = build.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(SpecialClass.class);
        ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull("pi_m_id");

    }

    @Data
    @Table("special_class")
    public static class SpecialClass {
        private BigDecimal pi_m_id;
        private BigDecimal pi_m_no;
        private BigDecimal pi_m_h_id;
    }

    @Test
    public void testConcat() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.expression().stringFormat("你好:{0}我叫{1}你好吗?我今年{2}岁了,{0}你呢", t_blog.title(), t_blog.star(), 12)
                            .eq("比较一下");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND CONCAT(?,`title`,?,`star`,?,?,?,`title`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),你好:(String),我叫(String),你好吗?我今年(String),12(Integer),岁了,(String),你呢(String),比较一下(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testConcat2() {
        Exception ex = null;
        try {

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(t_blog -> {
                        t_blog.expression().stringFormat("你好:{0}我叫{1}你好吗?我今年{2}岁了,{3}你呢", t_blog.title(), t_blog.star(), 12)
                                .eq("比较一下");
                    }).toList();
        } catch (Exception e) {
            ex = e;
        }
        Assert.assertNotNull(ex);
        Assert.assertTrue(ex instanceof EasyQueryInvalidOperationException);
        Assert.assertEquals("Mismatch: provided 3 arguments, but the format string expects a different number.", ex.getMessage());
    }

    @Test
    public void testConcat3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.expression().stringFormat("你好:{0}我叫{1}你好吗?我今年{1}岁了", t_blog.title(), t_blog.star())
                            .eq("比较一下");
                }).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND CONCAT(?,`title`,?,`star`,?,`star`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),你好:(String),我叫(String),你好吗?我今年(String),岁了(String),比较一下(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void datetimeformat() {

//        List<BigDecimal> list1 = easyEntityQuery.queryable(BlogEntity.class)
//                .innerJoin(Topic.class, (t_blog, t_topic) -> t_blog.id().eq(t_topic.id()))
//                .where((t_blog, t_topic) -> {
//
//                })
//                .selectColumn((t_blog, t_topic) -> t_topic.stars().sumBigDecimal()).toList();
//
//
//        BigDecimal bigDecimal = easyEntityQuery.queryable(BlogEntity.class)
//                .innerJoin(Topic.class, (t_blog, t_topic) -> t_blog.id().eq(t_topic.id()))
//                .where((t_blog, t_topic) -> {
//
//                })
//                .sumBigDecimalOrDefault((t_blog, t_topic) -> t_topic.stars(), BigDecimal.ZERO);
//

//        Long l = easyEntityQuery.queryable(MyOrderDetail.class)
//                .where(m -> {
//                    m.productId().eq("123");
//                    m.order().status().eq(2);
//                }).sumOrDefault(m -> m.orderNum(), 0L);


        String formater = "yyyy年MM-01 HH:mm分ss秒";
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<LocalDateTime, String>> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(d -> Select.DRAFT.of(
                        d.createTime(),
                        d.createTime().format(formater)
                )).toList();
        Assert.assertFalse(list.isEmpty());
        for (Draft2<LocalDateTime, String> timeAndFormat : list) {
            LocalDateTime value1 = timeAndFormat.getValue1();
            String format = value1.format(DateTimeFormatter.ofPattern(formater));
            Assert.assertEquals(format, timeAndFormat.getValue2());
        }

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`create_time` AS `value1`,DATE_FORMAT(t.`create_time`,'%Y年%m-01 %H:%i分%s秒') AS `value2` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testOrderSQL() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .orderBy(t_blog -> {
                    t_blog.expression().sql("{0} ASC", c -> {
                        c.expression(t_blog.order());
                    });
                })
                .toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? ORDER BY `order` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testOrderSQL1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .orderBy(t_blog -> {
                    t_blog.expression().caseWhen(() -> {
                                t_blog.title().eq("1");
                            }).then(1).caseWhen(() -> {
                                t_blog.title().eq("2");
                            }).then(2)
                            .elseEnd(3).asc();
                })
                .toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? ORDER BY (CASE WHEN `title` = ? THEN ? WHEN `title` = ? THEN ? ELSE ? END) ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(String),1(Integer),2(String),2(Integer),3(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testMany2Many() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft2<String, Long>> list = easyEntityQuery.queryable(UserAccount.class)
                .where(uc -> {
                    uc.books().any(ub -> ub.name().eq("JAVA开发"));
                }).select(uc -> Select.DRAFT.of(
                        uc.name(),
                        uc.books().count()
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`name` AS `value1`,(SELECT COUNT(*) FROM `t_user_book` t2 WHERE t2.`uid` = t.`uid`) AS `value2` FROM `t_user_account` t WHERE EXISTS (SELECT 1 FROM `t_user_book` t1 WHERE t1.`uid` = t.`uid` AND t1.`name` = ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("JAVA开发(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testMany2Many2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft2<String, Long>> list = easyEntityQuery.queryable(UserAccount.class)
                .subQueryToGroupJoin(uc -> uc.books())
                .where(uc -> {
                    uc.books().any(ub -> ub.name().eq("JAVA开发"));
                }).select(uc -> Select.DRAFT.of(
                        uc.name(),
                        uc.books().count()
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`name` AS `value1`,IFNULL(t2.`__count3__`,0) AS `value2` FROM `t_user_account` t LEFT JOIN (SELECT t1.`uid` AS `uid`,(CASE WHEN COUNT((CASE WHEN t1.`name` = ? THEN ? ELSE NULL END)) > 0 THEN ? ELSE ? END) AS `__any2__`,COUNT(*) AS `__count3__` FROM `t_user_book` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`uid` WHERE IFNULL(t2.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("JAVA开发(String),1(Integer),true(Boolean),false(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testQueryConcat() {
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    Expression expression = t_blog.expression();
                    t_blog.title().eq(
                            expression.concat(s -> {
                                s.expression(t_blog.id());
                                s.value("*");
                                s.expression(t_blog.content());
                            })
                    );

                    t_blog.title().eq(
                            expression.stringFormat("{0}*{1}", t_blog.id(), t_blog.content())
                    );
                }).toList();

    }


    @Test
    public void formatMySQL() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        String format1 = "yyyy年MM-01 HH:mm分ss秒";
        String format2 = "yyyy-MM-dd HH:mm:ss";
        String format3 = "yyyy/MM-/01 HH时mm分ss秒";
        List<Draft4<LocalDateTime, String, String, String>> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(t_blog -> Select.DRAFT.of(
                        t_blog.createTime(),
                        t_blog.createTime().format(format1),
                        t_blog.createTime().format(format2),
                        t_blog.createTime().format(format3)
                )).toList();
        Assert.assertFalse(list.isEmpty());
        for (Draft4<LocalDateTime, String, String, String> timeAndFormat : list) {
            LocalDateTime value1 = timeAndFormat.getValue1();
            String formatv1 = value1.format(DateTimeFormatter.ofPattern(format1));
            Assert.assertEquals(formatv1, timeAndFormat.getValue2());
            String formatv2 = value1.format(DateTimeFormatter.ofPattern(format2));
            Assert.assertEquals(formatv2, timeAndFormat.getValue3());
            String formatv3 = value1.format(DateTimeFormatter.ofPattern(format3));
            Assert.assertEquals(formatv3, timeAndFormat.getValue4());
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`create_time` AS `value1`,DATE_FORMAT(t.`create_time`,'%Y年%m-01 %H:%i分%s秒') AS `value2`,DATE_FORMAT(t.`create_time`,'%Y-%m-%d %H:%i:%s') AS `value3`,DATE_FORMAT(t.`create_time`,'%Y/%m-/01 %H时%i分%s秒') AS `value4` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void testMath() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft6<BigDecimal, BigDecimal, Integer, BigDecimal, BigDecimal, BigDecimal>> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(t_blog -> Select.DRAFT.of(
                        t_blog.score(),
                        t_blog.score().abs(),
                        t_blog.score().sign(),
                        t_blog.score().floor(),
                        t_blog.score().ceiling(),
                        t_blog.score().log()
                )).toList();
        Assert.assertFalse(list.isEmpty());
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`score` AS `value1`,ABS(t.`score`) AS `value2`,SIN(t.`score`) AS `value3`,FLOOR(t.`score`) AS `value4`,CEILING(t.`score`) AS `value5`,LOG(t.`score`) AS `value6` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }


    @Test
    public void testaaa1() {

//        long count = easyEntityQuery.queryable(BlogEntity.class)
//                .select(t_blog -> t_blog.score())
//                .distinct().count();


        List<Grouping1<String>> list = easyEntityQuery.queryable(BlogEntity.class)
                .groupBy(t_blog -> GroupKeys.of(t_blog.title()))
//                .having(group -> group.groupTable().star().count(true).gt(1L))
                .having(group -> group.distinct().count(s -> s.star()).gt(1L))
                .toList();


        List<Draft2<String, Long>> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .leftJoin(Topic.class, (t_blog, t_topic) -> t_topic.id().eq(t_topic.id()))
                .groupBy((t_blog, t_topic) -> GroupKeys.of(t_blog.title()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.groupTable().t2.title().count(true)
                )).toList();

        easyEntityQuery.queryable(BlogEntity.class)
                .leftJoin(Topic.class, (t_blog, t_topic) -> t_topic.id().eq(t_topic.id()))
                .groupBy((t_blog, t_topic) -> GroupKeys.of(t_blog.title()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.distinct().count(s -> s.t1.star())
                )).toList();

//        List<Grouping1<String>> list2 = easyEntityQuery.queryable(BlogEntity.class)
//                .groupBy(t_blog -> GroupKeys.of(t_blog.title()))
//                .having(group -> group.where().distinct())
//                .toList();


    }

    @Test
    public void testLt() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().like(t_blog.expression().constant("30%"));
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND LOCATE(?,`title`) > 0", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),30%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void testIndexOf() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().indexOf("30%").gt(-1);
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (LOCATE(?,`title`) - 1) > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),30%(String),-1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }
    @Test
    public void testContains() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.title().contains("30%");
                    t_blog.title().contains(t_blog.expression().constant("30%"));
                    t_blog.title().contains(t_blog.content());
                    t_blog.title().contains(t_blog.content().nullOrDefault(""));
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND LOCATE(?,`title`) > 0 AND LOCATE(?,`title`) > 0 AND `title` LIKE CONCAT('%',`content`,'%') AND `title` LIKE CONCAT('%',IFNULL(`content`,?),'%')", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),30%(String),30%(String),(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }


}
