package com.easy.query.test;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.common.tuple.MergeTuple10;
import com.easy.query.core.common.tuple.MergeTuple4;
import com.easy.query.core.common.tuple.MergeTuple5;
import com.easy.query.core.common.tuple.MergeTuple6;
import com.easy.query.core.common.tuple.MergeTuple7;
import com.easy.query.core.common.tuple.MergeTuple8;
import com.easy.query.core.common.tuple.MergeTuple9;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.exception.EasyQuerySingleMoreElementException;
import com.easy.query.core.proxy.columns.types.SQLStringTypeColumn;
import com.easy.query.core.proxy.grouping.Grouping1;
import com.easy.query.core.proxy.grouping.proxy.Grouping1Proxy;
import com.easy.query.core.proxy.impl.SQLSelectAsImpl;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.test.dto.BlogEntityTest;
import com.easy.query.test.dto.TopicRequest;
import com.easy.query.test.dto.TopicTypeVO;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * create time 2023/10/17 16:13
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest5 extends BaseTest {
    @Test
    public void test1() {
        Integer integer = easyQueryClient.queryable("select 1", Integer.class)
                .firstOrNull();
        Assert.assertEquals(1, (int) integer);
        String sql = easyQueryClient.queryable("select 1", Integer.class).toSQL();
        Assert.assertEquals("SELECT * FROM (select 1) t", sql);
    }

    @Test
    public void test2() {
        {
            String sql = easyEntityQuery.queryable(Topic.class)
                    .select(o -> o.FETCHER.allFields()).toSQL();

            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t", sql);
        }
        {
            String sql = easyEntityQuery.queryable(Topic.class)
                    .select(Topic.class, o -> o.FETCHER.allFields().alias()).toSQL();

            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time`,t.`alias` FROM `t_topic` t", sql);
        }
        {
            String sql = easyEntityQuery.queryable(Topic.class)
                    .select(o -> o.FETCHER.allFields().alias()).toSQL();

            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time`,t.`alias` FROM `t_topic` t", sql);
        }
        {
            String sql = easyEntityQuery.queryable(Topic.class).toSQL();

            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic`", sql);
        }
        {
            String sql = easyEntityQuery.queryable(Topic.class)
                    .select(TopicTypeVO.class, o -> Select.of(
                            o.expression().sqlSegment("ROW_NUMBER() OVER(ORDER BY {0})", c -> {
                                c.expression(o.createTime());
                            }).as(TopicTypeVO::getTitle),
                            o.FETCHER.allFieldsExclude(o.title())
                    )).toSQL();

            Assert.assertEquals("SELECT ROW_NUMBER() OVER(ORDER BY t.`create_time`) AS `title`,t.`id`,t.`stars`,t.`create_time` FROM `t_topic` t", sql);
        }
        {
            //todo
            ArrayList<String> columns = new ArrayList<>();
            columns.add("id");
            columns.add("title");
            columns.add("stars");
            String sql = easyEntityQuery.queryable(Topic.class)
                    .select(TopicTypeVO.class, o -> {
                        o.getEntitySQLContext().accept(new SQLSelectAsImpl(null, s -> {
                            for (String column : columns) {
                                s.column(o.getTable(), column);
                            }
                        }));
                        return o.getEntitySQLContext().getSelectAsExpression();

                    }).toSQL();

            Assert.assertEquals("SELECT t.`id`,t.`title`,t.`stars` FROM `t_topic` t", sql);
        }
        {

            String sql = easyEntityQuery.queryable(Topic.class)
                    .select(TopicTypeVO.class, o -> {
                        return Select.of(
                                o.id(),
                                o.title(),
                                o.stars()
                        );
                    }).toSQL();

            Assert.assertEquals("SELECT t.`id`,t.`title`,t.`stars` FROM `t_topic` t", sql);
        }
        {

            String sql = easyQueryClient.queryable(Topic.class)
                    .select(TopicTypeVO.class, o -> {
                        o.column("id");
                        o.column("title");
                        o.column("stars");

                    }).toSQL();

            Assert.assertEquals("SELECT t.`id`,t.`title`,t.`stars` FROM `t_topic` t", sql);
        }
        {
            ArrayList<String> columns = new ArrayList<>();
            columns.add("id");
            columns.add("title");
            String sql = easyEntityQuery.queryable(Topic.class)
                    .select(TopicTypeVO.class, o -> {
                        o.getEntitySQLContext().accept(new SQLSelectAsImpl(null, s -> {
                            for (String column : columns) {
                                s.column(o.getTable(), column);
                            }
                        }));
                        return o.getEntitySQLContext().getSelectAsExpression();

                    }).toSQL();

            Assert.assertEquals("SELECT t.`id`,t.`title` FROM `t_topic` t", sql);
        }
    }

    @Test
    public void groupTest5_2() {
        Supplier<Exception> x = () -> {
            try {

                BlogEntity blogEntity = easyEntityQuery.queryable(BlogEntity.class)
                        .groupBy(o -> GroupKeys.of(o.id()))
                        .select(BlogEntity.class, o -> Select.of(
                                o.key1().as(BlogEntity::getId),
                                o.distinct().sum(s -> s.score().nullOrDefault(BigDecimal.ZERO)),
                                o.distinct().count(s -> s.order().nullOrDefault(BigDecimal.ONE)),
                                o.distinct().avg(s -> s.status().nullOrDefault(3))
                        )).singleOrNull();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = x.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySingleMoreElementException);
    }

    @Test
    public void joinTestx() {
        Supplier<Exception> f = () -> {
            try {
                Long aLong = easyEntityQuery.queryable(Topic.class)
                        .asTable("a123")
                        .innerJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                        .where((t_topic, t_blog) -> {
                            t_blog.title().isNotNull();
                        })
                        .groupBy((t_topic, t_blog) -> GroupKeys.of(t_blog.id().nullOrDefault("")))
                        .select(group -> new BlogEntityProxy()
                                .id().set(group.key1())
                                .score().set(group.sum(s -> s.t2.score()))
                        ).distinct()
                        .selectColumn(s -> s.id().count())
                        .firstOrNull();
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
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        Assert.assertEquals("SELECT COUNT(t2.`id`) FROM (SELECT DISTINCT IFNULL(t1.`id`,?) AS `id`,SUM(t1.`score`) AS `score` FROM `a123` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY IFNULL(t1.`id`,?)) t2 LIMIT 1", easyQuerySQLStatementException.getSQL());
    }

    @Test
    public void joinTestx1() {
        Supplier<Exception> f = () -> {
            try {

                List<BlogEntityTest> list = easyEntityQuery.queryable(BlogEntity.class)
                        .select(BlogEntityTest.class).toList();

                List<BlogEntityTest> list1 = easyEntityQuery.queryable(BlogEntity.class)
                        .asTable("a123")
                        .select(BlogEntityTest.class).toList();
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
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        Assert.assertEquals("SELECT t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `a123` t WHERE t.`deleted` = ?", easyQuerySQLStatementException.getSQL());
    }
}
