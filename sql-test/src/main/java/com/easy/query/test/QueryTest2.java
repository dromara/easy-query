package com.easy.query.test;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.jdbc.parameter.ConstLikeSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.common.ToSQLResult;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.dto.BlogEntityGroup;
import com.easy.query.test.dto.BlogQueryRequest;
import com.easy.query.test.dto.BlogSortJoinRequest;
import com.easy.query.test.dto.BlogSortMultiRequest;
import com.easy.query.test.dto.BlogSortRequest;
import com.easy.query.test.dto.TopicGroupTestDTO;
import com.easy.query.test.dto.proxy.TopicGroupTestDTOProxy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicY;
import com.easy.query.test.entity.Topicx;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * create time 2023/6/8 21:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest2 extends BaseTest {
    @Test
    public void query1231() {

        String sql = easyQueryClient.queryable(Topic.class)
                .where(o -> {
                    o.sqlNativeSegment("{0} = {1}", c -> {
                        ClientQueryable<LocalDateTime> maxCreateTimeQuery = easyQueryClient.queryable(Topic.class)
                                .select(LocalDateTime.class, x -> x.columnMax("createTime"));
                        c.expression(o, "createTime")
                                .expression(maxCreateTimeQuery);
                    });
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` = (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1)", sql);
    }

    @Test
    public void query1231_1() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Topic> list = easyQueryClient.queryable(Topic.class)
                .where(o -> {
                    o.sqlNativeSegment("{0} = {1}", c -> {
                        ClientQueryable<LocalDateTime> maxCreateTimeQuery = easyQueryClient.queryable(Topic.class)
                                .select(LocalDateTime.class, x -> x.columnMax("createTime"));
                        c.expression(o, "createTime")
                                .expression(maxCreateTimeQuery);
                    });
                }).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` = (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(1, jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().size());
        Assert.assertEquals("", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void query1232() {

        ClientQueryable<LocalDateTime> maxCreateTimeQuery = easyQueryClient.queryable(Topic.class)
                .select(LocalDateTime.class, x -> x.columnMax("createTime"));
        String sql = easyQueryClient.queryable(Topic.class)
                .where(o -> {
                    o.eq("createTime", maxCreateTimeQuery);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` = (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1)", sql);
    }

    @Test
    public void query124() {

        String toSql = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .where(o -> o.id().eq("3"))
                .limit(1, 2)
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` = ? LIMIT 2 OFFSET 1", toSql);
    }

    @Test
    public void query124_1() {
        String toSql = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .where(o -> o.id().eq("3"))
                .limit(1, 2)
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` = ? LIMIT 2 OFFSET 1", toSql);
    }

    @Test
    public void query125() {
        String toSql = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .where(o -> o.id().eq("3"))
                .select(BlogEntity.class, (t, t1, t2) -> Select.of(
                        t.id(),
                        t1.title(),
                        t2.star()
                ))
                .limit(1, 2)
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t1.`title`,t2.`star` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` INNER JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` = ? LIMIT 2 OFFSET 1", toSql);
    }

    @Test
    public void query126() {
        {

            List<BlogEntity> list = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("3"))
                    .select(BlogEntity.class, (t, t1, t2) -> Select.of(
                            t.id(),
                            t1.title(),
                            t2.star()
                    ))
                    .limit(1, 2)
                    .toList();
            Assert.assertEquals(0, list.size());
        }
        {

            List<BlogEntity> list = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("3"))
                    .select(BlogEntity.class, (t, t1, t2) -> Select.of(
                            t.id(),
                            t1.title(),
                            t2.star()
                    ))
                    .limit(1)
                    .toList();
            Assert.assertEquals(1, list.size());
        }
        {

            List<BlogEntity> list = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("3"))
                    .select(BlogEntity.class, (t, t1, t2) -> Select.of(
                            t.id(),
                            t1.title(),
                            t2.star()
                    ))
                    .limit(1)
                    .toList();
            Assert.assertEquals(1, list.size());
        }
    }

    @Test
    public void query112() {
        {

            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .where(o -> o.id().eq("3"))
                    .sumOrDefault((t, t1) -> t1.score(), BigDecimal.ZERO);
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {

            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .where(o -> o.id().eq("3"))
                    .sumOrDefaultMerge(c -> c.t2.score(), BigDecimal.ZERO);
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
    }

    @Test
    public void query113() {
        {

            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("3x"))
                    .sumOrNull((t, t1, t2) -> t1.score());
            Assert.assertTrue(bigDecimal == null);
        }
        {

            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("3x"))
                    .sumOrNullMerge(c -> c.t2.score());
            Assert.assertTrue(bigDecimal == null);
        }
    }

    @Test
    public void query114() {
        {

            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("3"))
                    .sumBigDecimalOrNull((t, t1, t2) -> t2.score());
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {

            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("3"))
                    .sumBigDecimalOrNullMerge(c -> c.t3.score());
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
    }

    @Test
    public void query115() {
        {

            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("3x"))
                    .sumBigDecimalOrDefault((t, t1, t2) -> t2.score(), null);
            Assert.assertTrue(bigDecimal == null);
            BigDecimal bigDecimal1 = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("3x"))
                    .sumBigDecimalOrDefault((t, t1, t2) -> t1.score(), BigDecimal.ZERO);
            Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
        }
        {

            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("3x"))
                    .sumBigDecimalOrDefaultMerge(c -> c.t3.score(), null);
            Assert.assertTrue(bigDecimal == null);
            BigDecimal bigDecimal1 = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("3x"))
                    .sumBigDecimalOrDefaultMerge(c -> c.t2.score(), BigDecimal.ZERO);
            Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
        }
    }

    @Test
    public void query116() {
        {

            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("3x"))
                    .maxOrNull((t, t1, t2) -> t2.score());
            Assert.assertTrue(bigDecimal == null);
            BigDecimal bigDecimal1 = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("3x"))
                    .maxOrDefault((t, t1, t2) -> t1.score(), BigDecimal.ZERO);
            Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
        }
        {

            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("3x"))
                    .maxOrNullMerge(c -> c.t3.score());
            Assert.assertTrue(bigDecimal == null);
            BigDecimal bigDecimal1 = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("3x"))
                    .maxOrDefaultMerge(c -> c.t2.score(), BigDecimal.ZERO);
            Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
        }
    }

    @Test
    public void query117() {
        {

            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "3")))
                    .maxOrNull((t, t1, t2) -> t2.score());
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {

            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "3")))
                    .maxOrNullMerge(c -> c.t3.score());
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
    }

    @Test
    public void query118() {
        {

            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("3x"))
                    .minOrNull((t, t1, t2) -> t1.score());
            Assert.assertTrue(bigDecimal == null);
            BigDecimal bigDecimal1 = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("3x"))
                    .minOrDefault((t, t1, t2) -> t2.score(), BigDecimal.ZERO);
            Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
        }
        {

            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("3x"))
                    .minOrNullMerge(c -> c.t2.score());
            Assert.assertTrue(bigDecimal == null);
            BigDecimal bigDecimal1 = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().eq("3x"))
                    .minOrDefaultMerge(c -> c.t3.score(), BigDecimal.ZERO);
            Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
        }
    }

    @Test
    public void query119() {
        {

            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "3")))
                    .minOrNull((t, t1, t2) -> t2.score());
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {

            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "3")))
                    .minOrNullMerge(c -> c.t3.score());
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
    }

    @Test
    public void query120() {
        {
            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .avgBigDecimalOrNull((t, t1, t2) -> t1.score());
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {
            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .avgBigDecimalOrNullMerge(c -> c.t2.score());
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {
            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "2x", "5x")))
                    .avgBigDecimalOrNull((t, t1, t2) -> t2.score());
            Assert.assertTrue(bigDecimal == null);
        }
        {
            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "2x", "5x")))
                    .avgBigDecimalOrNullMerge(c -> c.t3.score());
            Assert.assertTrue(bigDecimal == null);
        }
        {
            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "2x", "5")))
                    .avgBigDecimalOrDefault((t, t1, t2) -> t2.score(), BigDecimal.ZERO);
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {
            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "2x", "5")))
                    .avgBigDecimalOrDefaultMerge(c -> c.t3.score(), BigDecimal.ZERO);
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {
            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "2x", "5x")))
                    .avgBigDecimalOrDefault((t, t1) -> t1.score(), BigDecimal.ZERO);
            Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal) == 0);
        }
        {
            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "2x", "5x")))
                    .avgBigDecimalOrDefaultMerge(c -> c.t2.score(), BigDecimal.ZERO);
            Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal) == 0);
        }
        {
            Double bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .avgOrNull((t, t1) -> t1.star());
            Assert.assertTrue(3.3333d == bigDecimal);
        }
        {
            Double bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .avgOrNullMerge(c -> c.t2.star());
            Assert.assertTrue(3.3333d == bigDecimal);
        }
        {
            Double bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "2x", "5x")))
                    .avgOrDefault((t, t1) -> t1.star(), null);
            Assert.assertTrue(null == bigDecimal);
        }
        {
            Double bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "2x", "5x")))
                    .avgOrDefaultMerge(c -> c.t2.star(), null);
            Assert.assertTrue(null == bigDecimal);
        }
        {
            Double bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "2x", "5")))
                    .avgOrDefault((t, t1, t2) -> t2.star(), null);
            Assert.assertTrue(5d == bigDecimal);
        }
        {
            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "2x", "5")))
                    .avgOrDefault((t, t1, t2) -> t2.star(), null, BigDecimal.class);
            Assert.assertTrue(new BigDecimal("5").compareTo(bigDecimal) == 0);
        }
        {
            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "2x", "5")))
                    .avgOrDefaultMerge(c -> c.t3.star(), null, BigDecimal.class);
            Assert.assertTrue(new BigDecimal("5").compareTo(bigDecimal) == 0);
        }
        {
            Float bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "2x", "5")))
                    .avgOrDefault((t, t1, t2) -> t1.star(), null, Float.class);
            Assert.assertTrue(5f == bigDecimal);
        }
        {
            Float bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "2x", "5")))
                    .avgOrDefaultMerge(c -> c.t2.star(), null, Float.class);
            Assert.assertTrue(5f == bigDecimal);
        }
    }

    @Test
    public void query121() {
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .orderBy((t, t1, t2) -> t1.order().asc()).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) ORDER BY t1.`order` ASC", sql);
        }
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .orderBy(false, (t, t1, t2) -> t1.order().asc())
                    .orderBy(true, (t, t1, t2) -> t2.id().asc()).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) ORDER BY t2.`id` ASC", sql);
        }
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .orderBy((t, t1, t2) -> t2.order().desc()).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) ORDER BY t2.`order` DESC", sql);
        }
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .orderBy(false, (t, t1, t2) -> t2.order().desc())
                    .orderBy(true, (t, t1, t2) -> {
                        t2.id().desc();
                        t2.order().desc();
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) ORDER BY t2.`id` DESC,t2.`order` DESC", sql);
        }
    }

    @Test
    public void query122() {
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1, t2) -> GroupKeys.of(t.id()))
                    .orderBy(group -> group.groupTable().t2.order().asc()).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t1.`order` ASC", sql);
        }
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1, t2) -> GroupKeys.of(t2.id()))
                    .orderBy(group -> group.groupTable().t3.order().asc()).toSQL();
            Assert.assertEquals("SELECT t2.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) GROUP BY t2.`id` ORDER BY t2.`order` ASC", sql);
        }
    }

    @Test
    public void query123() {
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1, t2) -> GroupKeys.of(t.id()))
                    .distinct()
                    .orderBy(group -> group.groupTable().t3.order().asc())
                    .limit(10).toSQL();
            Assert.assertEquals("SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` INNER JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t2.`order` ASC LIMIT 10", sql);
        }
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1, t2) -> GroupKeys.of(t.id()))
                    .orderBy(group -> group.groupTable().t3.order().asc())
                    .distinct()
                    .select(o -> new TopicProxy().id().set(o.key1()))
                    .selectColumn(o -> o.id().count()).toSQL();
            Assert.assertEquals("SELECT COUNT(t3.`id`) FROM (SELECT DISTINCT t.`id` AS `id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` INNER JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t2.`order` ASC) t3", sql);
        }
    }

    @Test
    public void groupTest() {
        String sql = easyEntityQuery.queryable(BlogEntity.class)
                .groupBy(o -> GroupKeys.of(o.id()))
                .select(BlogEntityGroup.class, o -> Select.of(
                        o.key1().as(BlogEntityGroup::getId),
                        o.count(s -> s.id()).as(BlogEntityGroup::getIdCount),
                        o.sum(s -> s.score()).as(BlogEntityGroup::getScoreSum),
                        o.max(s -> s.publishTime()).as(BlogEntityGroup::getPublishTimeMax),
                        o.min(s -> s.order()).as(BlogEntityGroup::getOrderMin),
                        o.avg(s -> s.status()).as(BlogEntityGroup::getStatusAvg)
                )).toSQL();
        Assert.assertEquals("SELECT t.`id` AS `id`,COUNT(t.`id`) AS `id_count`,SUM(t.`score`) AS `score_sum`,MAX(t.`publish_time`) AS `publish_time_max`,MIN(t.`order`) AS `order_min`,AVG(t.`status`) AS `status_avg` FROM `t_blog` t WHERE t.`deleted` = ? GROUP BY t.`id`", sql);
    }

    @Test
    public void groupTest1() {
        String sql = easyEntityQuery.queryable(BlogEntity.class)
                .groupBy(o -> GroupKeys.of(o.id()))
                .select(BlogEntity.class, o -> Select.of(
                        o.key1().as(BlogEntity::getId),
                        o.sum(s -> s.score()).as(BlogEntity::getScore),
                        o.max(s -> s.publishTime()).as(BlogEntity::getPublishTime),
                        o.min(s -> s.order()).as(BlogEntity::getOrder),
                        o.avg(s -> s.status()).as(BlogEntity::getStatus)
                )).toSQL();
        Assert.assertEquals("SELECT t.`id` AS `id`,SUM(t.`score`) AS `score`,MAX(t.`publish_time`) AS `publish_time`,MIN(t.`order`) AS `order`,AVG(t.`status`) AS `status` FROM `t_blog` t WHERE t.`deleted` = ? GROUP BY t.`id`", sql);
    }

    @Test
    public void groupTest2() {
        String sql = easyEntityQuery.queryable(BlogEntity.class)
                .groupBy(o -> GroupKeys.of(o.id()))
                .select(BlogEntityGroup.class, o -> Select.of(
                        o.key1().as(BlogEntityGroup::getId),
                        o.distinct().sum(s -> s.score()).as(BlogEntityGroup::getScoreSum),
                        o.distinct().count(s -> s.id()).as(BlogEntityGroup::getIdCount),
                        o.distinct().avg(s -> s.status()).as(BlogEntityGroup::getStatusAvg)
                )).toSQL();
        Assert.assertEquals("SELECT t.`id` AS `id`,SUM(DISTINCT t.`score`) AS `score_sum`,COUNT(DISTINCT t.`id`) AS `id_count`,AVG(DISTINCT t.`status`) AS `status_avg` FROM `t_blog` t WHERE t.`deleted` = ? GROUP BY t.`id`", sql);
    }

    @Test
    public void groupTest3() {
        String sql = easyEntityQuery.queryable(BlogEntity.class)
                .groupBy(o -> GroupKeys.of(o.id()))
                .select(BlogEntity.class, o -> Select.of(
                        o.key1().as(BlogEntity::getId),
                        o.distinct().sum(s -> s.score()),
                        o.distinct().count(s -> s.order()),
                        o.distinct().avg(s -> s.status())
                )).toSQL();
        Assert.assertEquals("SELECT t.`id` AS `id`,SUM(DISTINCT t.`score`),COUNT(DISTINCT t.`order`),AVG(DISTINCT t.`status`) FROM `t_blog` t WHERE t.`deleted` = ? GROUP BY t.`id`", sql);
    }

    @Test
    public void groupTest4() {
        String sql = easyEntityQuery.queryable(BlogEntity.class)
                .groupBy(o -> GroupKeys.of(o.id()))
                .select(BlogEntity.class, group -> Select.of(
                        group.key1().as(BlogEntity::getId),
                        group.distinct().sum(s -> s.score()).as(BlogEntity::getScore),
                        group.distinct().count(s -> s.order()).as(BlogEntity::getOrder),
                        group.distinct().avg(s -> s.status()).as(BlogEntity::getStatus)
                )).toSQL();
        Assert.assertEquals("SELECT t.`id` AS `id`,SUM(DISTINCT t.`score`) AS `score`,COUNT(DISTINCT t.`order`) AS `order`,AVG(DISTINCT t.`status`) AS `status` FROM `t_blog` t WHERE t.`deleted` = ? GROUP BY t.`id`", sql);
    }

    @Test
    public void groupTest5() {
        String sql = easyEntityQuery.queryable(BlogEntity.class)
                .groupBy(o -> GroupKeys.of(o.id()))
                .select(BlogEntity.class, group -> Select.of(
                        group.key1().as(BlogEntity::getId),
                        group.distinct().sum(s -> s.score()).nullOrDefault(BigDecimal.ZERO).as(BlogEntity::getScore),
                        group.distinct().count(s -> s.order()).asBigDecimal().nullOrDefault(BigDecimal.ZERO).as(BlogEntity::getOrder),
                        group.distinct().avg(s -> s.status()).nullOrDefault(BigDecimal.ZERO).as(BlogEntity::getStatus)
                )).toSQL();
        Assert.assertEquals("SELECT t.`id` AS `id`,IFNULL(SUM(DISTINCT t.`score`),?) AS `score`,IFNULL(COUNT(DISTINCT t.`order`),?) AS `order`,IFNULL(AVG(DISTINCT t.`status`),?) AS `status` FROM `t_blog` t WHERE t.`deleted` = ? GROUP BY t.`id`", sql);
    }

    @Test
    public void groupTest5_1() {
        BlogEntity blogEntity = easyEntityQuery.queryable(BlogEntity.class)
                .groupBy(o -> GroupKeys.of(o.id()))
                .select(BlogEntity.class, group -> Select.of(
                        group.key1().as(BlogEntity::getId),
                        group.distinct().sum(s -> s.score()).nullOrDefault(BigDecimal.ZERO).as(BlogEntity::getScore),
                        group.distinct().count(s -> s.order()).asBigDecimal().nullOrDefault(BigDecimal.ONE).as(BlogEntity::getOrder),
                        group.distinct().avg(s -> s.status()).nullOrDefault(BigDecimal.valueOf(3)).as(BlogEntity::getStatus)
                )).firstOrNull();
        Assert.assertNotNull(blogEntity);
    }

    @Test
    public void groupTest9() {
        String sql = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.id().ne("1");
                    t_blog.star().ne(true, 1);
                    t_blog.title().ne(false, "x");
                }).toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` <> ? AND `star` <> ?", sql);
    }

    @Test
    public void groupTest10() {
        Query<BlogEntity> queryable = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.id().notLikeMatchLeft("id");
                    t_blog.content().notLikeMatchLeft(true, "content");
                    t_blog.title().notLikeMatchLeft(false, "title");
                });
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(queryable.getSQLEntityExpressionBuilder().getExpressionContext().getTableContext());
        String sql = queryable.toSQL(toSQLContext);
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` NOT LIKE ? AND `content` NOT LIKE ?", sql);
        List<SQLParameter> parameters = toSQLContext.getParameters();
        Assert.assertEquals(3, parameters.size());
        {

            SQLParameter sqlParameter1 = parameters.get(0);
            Assert.assertTrue(sqlParameter1 instanceof EasyConstSQLParameter);
            EasyConstSQLParameter sqlParameter11 = (EasyConstSQLParameter) sqlParameter1;
            Assert.assertEquals("deleted", sqlParameter11.getPropertyNameOrNull());
            Assert.assertEquals(false, sqlParameter11.getValue());
        }
        {

            SQLParameter sqlParameter1 = parameters.get(1);
            Assert.assertTrue(sqlParameter1 instanceof ConstLikeSQLParameter);
            ConstLikeSQLParameter sqlParameter11 = (ConstLikeSQLParameter) sqlParameter1;
            Assert.assertEquals("id", sqlParameter11.getPropertyNameOrNull());
            Assert.assertEquals("id%", sqlParameter11.getValue());
        }
        {

            SQLParameter sqlParameter1 = parameters.get(2);
            Assert.assertTrue(sqlParameter1 instanceof ConstLikeSQLParameter);
            ConstLikeSQLParameter sqlParameter11 = (ConstLikeSQLParameter) sqlParameter1;
            Assert.assertEquals("content", sqlParameter11.getPropertyNameOrNull());
            Assert.assertEquals("content%", sqlParameter11.getValue());
        }
    }

    @Test
    public void groupTest10_1() {
        Query<BlogEntity> queryable = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.id().notLikeMatchLeft("id");
                    t_blog.content().notLikeMatchLeft(true, "content");
                    t_blog.title().notLikeMatchLeft(false, "title");
                });
        ToSQLResult sqlResult = queryable.toSQLResult();
        String sql = sqlResult.getSQL();
        ToSQLContext toSQLContext = sqlResult.getSqlContext();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` NOT LIKE ? AND `content` NOT LIKE ?", sql);
        List<SQLParameter> parameters = toSQLContext.getParameters();
        Assert.assertEquals(3, parameters.size());
        {

            SQLParameter sqlParameter1 = parameters.get(0);
            Assert.assertTrue(sqlParameter1 instanceof EasyConstSQLParameter);
            EasyConstSQLParameter sqlParameter11 = (EasyConstSQLParameter) sqlParameter1;
            Assert.assertEquals("deleted", sqlParameter11.getPropertyNameOrNull());
            Assert.assertEquals(false, sqlParameter11.getValue());
        }
        {

            SQLParameter sqlParameter1 = parameters.get(1);
            Assert.assertTrue(sqlParameter1 instanceof ConstLikeSQLParameter);
            ConstLikeSQLParameter sqlParameter11 = (ConstLikeSQLParameter) sqlParameter1;
            Assert.assertEquals("id", sqlParameter11.getPropertyNameOrNull());
            Assert.assertEquals("id%", sqlParameter11.getValue());
        }
        {

            SQLParameter sqlParameter1 = parameters.get(2);
            Assert.assertTrue(sqlParameter1 instanceof ConstLikeSQLParameter);
            ConstLikeSQLParameter sqlParameter11 = (ConstLikeSQLParameter) sqlParameter1;
            Assert.assertEquals("content", sqlParameter11.getPropertyNameOrNull());
            Assert.assertEquals("content%", sqlParameter11.getValue());
        }
    }

    @Test
    public void groupTest11() {
        Query<BlogEntity> queryable = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.id().like("id");
                    t_blog.content().like(true, "content");
                    t_blog.title().like(false, "title");
                });
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(queryable.getSQLEntityExpressionBuilder().getExpressionContext().getTableContext());
        String sql = queryable.toSQL(toSQLContext);
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` LIKE ? AND `content` LIKE ?", sql);
        List<SQLParameter> parameters = toSQLContext.getParameters();
        Assert.assertEquals(3, parameters.size());
        {

            SQLParameter sqlParameter1 = parameters.get(0);
            Assert.assertTrue(sqlParameter1 instanceof EasyConstSQLParameter);
            EasyConstSQLParameter sqlParameter11 = (EasyConstSQLParameter) sqlParameter1;
            Assert.assertEquals("deleted", sqlParameter11.getPropertyNameOrNull());
            Assert.assertEquals(false, sqlParameter11.getValue());
        }
        {

            SQLParameter sqlParameter1 = parameters.get(1);
            Assert.assertTrue(sqlParameter1 instanceof ConstLikeSQLParameter);
            ConstLikeSQLParameter sqlParameter11 = (ConstLikeSQLParameter) sqlParameter1;
            Assert.assertEquals("id", sqlParameter11.getPropertyNameOrNull());
            Assert.assertEquals("%id%", sqlParameter11.getValue());
        }
        {

            SQLParameter sqlParameter1 = parameters.get(2);
            Assert.assertTrue(sqlParameter1 instanceof ConstLikeSQLParameter);
            ConstLikeSQLParameter sqlParameter11 = (ConstLikeSQLParameter) sqlParameter1;
            Assert.assertEquals("content", sqlParameter11.getPropertyNameOrNull());
            Assert.assertEquals("%content%", sqlParameter11.getValue());
        }
    }

    @Test
    public void groupTest12() {
        Query<BlogEntity> queryable = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.id().notLikeMatchRight("id");
                    t_blog.content().notLikeMatchRight(true, "content");
                    t_blog.title().notLikeMatchRight(false, "title");
                });
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(queryable.getSQLEntityExpressionBuilder().getExpressionContext().getTableContext());
        String sql = queryable.toSQL(toSQLContext);
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` NOT LIKE ? AND `content` NOT LIKE ?", sql);
        List<SQLParameter> parameters = toSQLContext.getParameters();
        Assert.assertEquals(3, parameters.size());
        {

            SQLParameter sqlParameter1 = parameters.get(0);
            Assert.assertTrue(sqlParameter1 instanceof EasyConstSQLParameter);
            EasyConstSQLParameter sqlParameter11 = (EasyConstSQLParameter) sqlParameter1;
            Assert.assertEquals("deleted", sqlParameter11.getPropertyNameOrNull());
            Assert.assertEquals(false, sqlParameter11.getValue());
        }
        {

            SQLParameter sqlParameter1 = parameters.get(1);
            Assert.assertTrue(sqlParameter1 instanceof ConstLikeSQLParameter);
            ConstLikeSQLParameter sqlParameter11 = (ConstLikeSQLParameter) sqlParameter1;
            Assert.assertEquals("id", sqlParameter11.getPropertyNameOrNull());
            Assert.assertEquals("%id", sqlParameter11.getValue());
        }
        {

            SQLParameter sqlParameter1 = parameters.get(2);
            Assert.assertTrue(sqlParameter1 instanceof ConstLikeSQLParameter);
            ConstLikeSQLParameter sqlParameter11 = (ConstLikeSQLParameter) sqlParameter1;
            Assert.assertEquals("content", sqlParameter11.getPropertyNameOrNull());
            Assert.assertEquals("%content", sqlParameter11.getValue());
        }
    }

    @Test
    public void groupTest13() {
        EntityQueryable<BlogEntityProxy, BlogEntity> queryable = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.id().notLike("id");
                    t_blog.content().notLike(true, "content");
                    t_blog.title().notLike(false, "title");
                });
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(queryable.getSQLEntityExpressionBuilder().getExpressionContext().getTableContext());
        String sql = queryable.toSQL(toSQLContext);
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` NOT LIKE ? AND `content` NOT LIKE ?", sql);
        List<SQLParameter> parameters = toSQLContext.getParameters();
        Assert.assertEquals(3, parameters.size());
        {

            SQLParameter sqlParameter1 = parameters.get(0);
            Assert.assertTrue(sqlParameter1 instanceof EasyConstSQLParameter);
            EasyConstSQLParameter sqlParameter11 = (EasyConstSQLParameter) sqlParameter1;
            Assert.assertEquals("deleted", sqlParameter11.getPropertyNameOrNull());
            Assert.assertEquals(false, sqlParameter11.getValue());
        }
        {

            SQLParameter sqlParameter1 = parameters.get(1);
            Assert.assertTrue(sqlParameter1 instanceof ConstLikeSQLParameter);
            ConstLikeSQLParameter sqlParameter11 = (ConstLikeSQLParameter) sqlParameter1;
            Assert.assertEquals("id", sqlParameter11.getPropertyNameOrNull());
            Assert.assertEquals("%id%", sqlParameter11.getValue());
        }
        {

            SQLParameter sqlParameter1 = parameters.get(2);
            Assert.assertTrue(sqlParameter1 instanceof ConstLikeSQLParameter);
            ConstLikeSQLParameter sqlParameter11 = (ConstLikeSQLParameter) sqlParameter1;
            Assert.assertEquals("content", sqlParameter11.getPropertyNameOrNull());
            Assert.assertEquals("%content%", sqlParameter11.getValue());
        }
    }

    @Test
    public void queryTest16() {
        LocalDateTime now = LocalDateTime.now();
        Query<BlogEntity> queryable = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.star().lt(9);
                    t_blog.publishTime().lt(true, now);
                    t_blog.order().lt(false, BigDecimal.valueOf(4));
                });

        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(queryable.getSQLEntityExpressionBuilder().getExpressionContext().getTableContext());
        String sql = queryable.toSQL(toSQLContext);
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `star` < ? AND `publish_time` < ?", sql);
        List<SQLParameter> parameters = toSQLContext.getParameters();
        Assert.assertEquals(3, parameters.size());

        Assert.assertEquals(false, parameters.get(0).getValue());
        Assert.assertEquals("deleted", parameters.get(0).getPropertyNameOrNull());
        Assert.assertEquals(9, parameters.get(1).getValue());
        Assert.assertEquals(now, parameters.get(2).getValue());
    }

    @Test
    public void queryTest17() {
        BlogQueryRequest blogQueryRequest = new BlogQueryRequest();
        blogQueryRequest.setTitle("123");
        blogQueryRequest.setContent("123");
        blogQueryRequest.setStar(123);
        blogQueryRequest.setPublishTimeBegin(LocalDateTime.of(2000,2,1,0,0));
        blogQueryRequest.setPublishTimeEnd(LocalDateTime.of(2000,3,1,0,0));
        LocalDateTime[] array = {LocalDateTime.of(2000,1,1,0,0), LocalDateTime.of(2001,1,1,0,0)};
        blogQueryRequest.setPublishTime(array);
        {

            List<LocalDateTime> list=Arrays.asList(LocalDateTime.of(2003,1,1,0,0), LocalDateTime.of(2004,1,1,0,0));
            blogQueryRequest.setPublishTimes(list);
        }
        {

            List<LocalDateTime> list=Arrays.asList(LocalDateTime.of(2005,1,1,0,0), LocalDateTime.of(2006,1,1,0,0));
            blogQueryRequest.setPublishTimeOpen(list);
        }
        {

            List<LocalDateTime> list=Arrays.asList(LocalDateTime.of(2007,1,1,0,0), LocalDateTime.of(2008,1,1,0,0));
            blogQueryRequest.setPublishTimeOpenClosed(list);
        }
        {

            List<LocalDateTime> list=Arrays.asList(LocalDateTime.of(2009,1,1,0,0), LocalDateTime.of(2010,1,1,0,0));
            blogQueryRequest.setPublishTimeClosedOpen(list);
        }
        blogQueryRequest.setScore(new BigDecimal("123"));
        blogQueryRequest.setStatus(1);
        blogQueryRequest.setOrder(new BigDecimal("12"));
        blogQueryRequest.setIsTop(false);
        blogQueryRequest.getOrders().add("status");
        blogQueryRequest.getOrders().add("score");

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<BlogEntity> list1 = easyEntityQuery.queryable(BlogEntity.class)
                    .whereObject(blogQueryRequest)
                    .orderByObject(blogQueryRequest).toList();
//        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `title` LIKE ? AND `url` LIKE ? AND `star` = ? AND `publish_time` >= ? AND `publish_time` <= ? AND `score` >= ? AND `status` <= ? AND `order` > ? AND `is_top` <> ? ORDER BY `status` ASC,`score` ASC", sql);

        } catch (Exception e) {

        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `title` LIKE ? AND `url` LIKE ? AND `star` = ? AND `publish_time` >= ? AND `publish_time` <= ? AND (`publish_time` >= ? AND `publish_time` <= ?) AND (`publish_time` >= ? AND `publish_time` <= ?) AND (`publish_time` > ? AND `publish_time` < ?) AND (`publish_time` > ? AND `publish_time` <= ?) AND (`publish_time` >= ? AND `publish_time` < ?) AND `score` >= ? AND `status` <= ? AND `order` > ? AND `is_top` <> ? ORDER BY `status` ASC,`score` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),%123%(String),%123%(String),123(Integer),2000-02-01T00:00(LocalDateTime),2000-03-01T00:00(LocalDateTime),2000-01-01T00:00(LocalDateTime),2001-01-01T00:00(LocalDateTime),2003-01-01T00:00(LocalDateTime),2004-01-01T00:00(LocalDateTime),2005-01-01T00:00(LocalDateTime),2006-01-01T00:00(LocalDateTime),2007-01-01T00:00(LocalDateTime),2008-01-01T00:00(LocalDateTime),2009-01-01T00:00(LocalDateTime),2010-01-01T00:00(LocalDateTime),123(BigDecimal),1(Integer),12(BigDecimal),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void queryTest18() {
        BlogQueryRequest blogQueryRequest = new BlogQueryRequest();
        blogQueryRequest.setTitle("123");
        blogQueryRequest.setContent("123");
        blogQueryRequest.setStar(123);
        blogQueryRequest.setPublishTimeBegin(LocalDateTime.now());
        blogQueryRequest.setPublishTimeEnd(LocalDateTime.now());
        blogQueryRequest.setScore(new BigDecimal("123"));
        blogQueryRequest.setStatus(1);
        blogQueryRequest.setOrder(new BigDecimal("12"));
        blogQueryRequest.setIsTop(false);
        blogQueryRequest.getOrders().add("status");
        blogQueryRequest.getOrders().add("score");
        String sql = easyEntityQuery.queryable(BlogEntity.class)
                .whereObject(true, blogQueryRequest)
                .orderByObject(true, blogQueryRequest)
                .toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `title` LIKE ? AND `url` LIKE ? AND `star` = ? AND `publish_time` >= ? AND `publish_time` <= ? AND `score` >= ? AND `status` <= ? AND `order` > ? AND `is_top` <> ? ORDER BY `status` ASC,`score` ASC", sql);
    }

    @Test
    public void queryTest18_1() {
        BlogQueryRequest blogQueryRequest = new BlogQueryRequest();
        blogQueryRequest.setTitle("123");
        blogQueryRequest.setTitle2("123");
        blogQueryRequest.setContent("123");
        blogQueryRequest.setStar(123);
        blogQueryRequest.setPublishTimeBegin(LocalDateTime.now());
        blogQueryRequest.setPublishTimeEnd(LocalDateTime.now());
        blogQueryRequest.setScore(new BigDecimal("123"));
        blogQueryRequest.setStatus(1);
        blogQueryRequest.setOrder(new BigDecimal("12"));
        blogQueryRequest.setIsTop(false);
        blogQueryRequest.getOrders().add("status");
        blogQueryRequest.getOrders().add("score");
        String sql = easyEntityQuery.queryable(BlogEntity.class)
                .whereObject(true, blogQueryRequest)
                .orderByObject(true, blogQueryRequest)
                .toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `title` LIKE ? AND (`title` LIKE ? OR `content` LIKE ?) AND `url` LIKE ? AND `star` = ? AND `publish_time` >= ? AND `publish_time` <= ? AND `score` >= ? AND `status` <= ? AND `order` > ? AND `is_top` <> ? ORDER BY `status` ASC,`score` ASC", sql);
    }

    @Test
    public void queryTest18_2() {
        BlogQueryRequest blogQueryRequest = new BlogQueryRequest();
        blogQueryRequest.setTitle("123");
        blogQueryRequest.setTitle2("123");
        blogQueryRequest.setTitle3("123");
        blogQueryRequest.setContent("123");
        blogQueryRequest.setStar(123);
        blogQueryRequest.setPublishTimeBegin(LocalDateTime.now());
        blogQueryRequest.setPublishTimeEnd(LocalDateTime.now());
        blogQueryRequest.setScore(new BigDecimal("123"));
        blogQueryRequest.setStatus(1);
        blogQueryRequest.setOrder(new BigDecimal("12"));
        blogQueryRequest.setIsTop(false);
        blogQueryRequest.getOrders().add("status");
        blogQueryRequest.getOrders().add("score");
        String sql = easyEntityQuery.queryable(BlogEntity.class)
                .whereObject(true, blogQueryRequest)
                .orderByObject(true, blogQueryRequest)
                .toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `title` LIKE ? AND (`title` LIKE ? OR `content` LIKE ?) AND (`id` = ? OR `content` = ?) AND `url` LIKE ? AND `star` = ? AND `publish_time` >= ? AND `publish_time` <= ? AND `score` >= ? AND `status` <= ? AND `order` > ? AND `is_top` <> ? ORDER BY `status` ASC,`score` ASC", sql);
    }

    @Test
    public void queryTest19() {
        BlogQueryRequest blogQueryRequest = new BlogQueryRequest();
        blogQueryRequest.setTitle("123");
        blogQueryRequest.setContent("123");
        blogQueryRequest.setStar(123);
        blogQueryRequest.setPublishTimeBegin(LocalDateTime.now());
        blogQueryRequest.setPublishTimeEnd(LocalDateTime.now());
        blogQueryRequest.setScore(new BigDecimal("123"));
        blogQueryRequest.setStatus(1);
        blogQueryRequest.setOrder(new BigDecimal("12"));
        blogQueryRequest.setIsTop(false);
        blogQueryRequest.getOrders().add("status");
        blogQueryRequest.getOrders().add("score");
        String sql = easyEntityQuery.queryable(BlogEntity.class)
                .whereObject(false, blogQueryRequest)
                .orderByObject(false, blogQueryRequest)
                .toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ?", sql);
    }

    @Test
    public void queryTest20() {
        BlogQueryRequest blogQueryRequest = new BlogQueryRequest();
        blogQueryRequest.setTitle("123");
        blogQueryRequest.setContent("123");
        blogQueryRequest.setStar(123);
        blogQueryRequest.setPublishTimeBegin(LocalDateTime.now());
        blogQueryRequest.setPublishTimeEnd(LocalDateTime.now());
        blogQueryRequest.setScore(new BigDecimal("123"));
        blogQueryRequest.setStatus(1);
        blogQueryRequest.setOrder(new BigDecimal("12"));
        blogQueryRequest.setIsTop(false);
        blogQueryRequest.getStatusList().add(1);
        blogQueryRequest.getStatusNotList().add(2);
        blogQueryRequest.getStatusNotList().add(3);
        blogQueryRequest.getOrders().add("status");
        blogQueryRequest.getOrders().add("score");
        String sql = easyEntityQuery.queryable(BlogEntity.class)
                .whereObject(blogQueryRequest)
                .orderByObject(blogQueryRequest)
                .toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `title` LIKE ? AND `url` LIKE ? AND `star` = ? AND `publish_time` >= ? AND `publish_time` <= ? AND `score` >= ? AND `status` <= ? AND `order` > ? AND `is_top` <> ? AND `status` IN (?) AND `status` NOT IN (?,?) ORDER BY `status` ASC,`score` ASC", sql);
    }


    @Test
    public void query24() {
        Query<TopicGroupTestDTO> topicGroupTestDTOQueryable = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq("1"))
                .groupBy(o -> GroupKeys.of(o.expression().sqlSegment("RAND()").asStr()))
                .orderBy(group -> group.expression().sqlSegment("RAND()").asc())
                .select(group -> new TopicGroupTestDTOProxy()
                        .id().set(group.key1())
                        .idCount().set(group.groupTable().id().intCount()));
        String sql = topicGroupTestDTOQueryable.toSQL();
        Assert.assertEquals("SELECT RAND() AS `id`,COUNT(t.`id`) AS `id_count` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? GROUP BY RAND() ORDER BY RAND() ASC", sql);
        List<TopicGroupTestDTO> list = topicGroupTestDTOQueryable.toList();
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void query122x() {
        {
            List<Topic> list = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1) -> GroupKeys.of(t.id()))
                    .orderBy(group -> group.groupTable().t2.order().asc())
                    .select(group -> new TopicProxy()
                            .id().set(group.key1())
                    )
                    .toList();
            Assert.assertEquals(3, list.size());
            for (Topic topic : list) {
                Assert.assertNotNull(topic.getId());
                Assert.assertNull(topic.getStars());
                Assert.assertNull(topic.getTitle());
            }
        }
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1) -> GroupKeys.of(t.id()))
                    .having(false, group -> group.groupTable().t2.id().count().ge(1L))
                    .having(true, group -> group.groupTable().t1.id().count().ge(1L))
                    .orderBy(group -> group.groupTable().t2.order().asc()).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t1.`order` ASC", sql);
        }
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1) -> GroupKeys.of(t.id()))
                    .having(false, group -> group.groupTable().t2.id().count().ge(1L))
                    .having(group -> group.groupTable().t1.id().count().ge(1L))
                    .orderBy(group -> group.groupTable().t2.order().asc()).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t1.`order` ASC", sql);
        }
    }

    @Test
    public void query123x() {
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1) -> GroupKeys.of(t.id()))
                    .distinct()
                    .orderBy(group -> group.groupTable().t2.order().asc())
                    .limit(10).toSQL();
            Assert.assertEquals("SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t1.`order` ASC LIMIT 10", sql);
        }
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1) -> GroupKeys.of(t.id()))
                    .orderBy(group -> group.groupTable().t2.order().asc())
                    .distinct()
                    .select(Topic.class, o -> Select.of(o.key1()))
                    .select("count(1)").toSQL();
            Assert.assertEquals("SELECT count(1) FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t1.`order` ASC) t2", sql);
        }
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1) -> GroupKeys.of(t.id()))
                    .orderBy(group -> group.groupTable().t1.stars().desc())
                    .distinct()
                    .select(Topic.class, o -> Select.of(o.key1()))
                    .select("count(1)").toSQL();
            Assert.assertEquals("SELECT count(1) FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t.`stars` DESC) t2", sql);
        }
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1) -> GroupKeys.of(t.id()))
                    .orderBy(group -> group.groupTable().t1.stars().asc())
                    .distinct()
                    .select(Topic.class, o -> Select.of(o.key1()))
                    .select("count(1)").toSQL();
            Assert.assertEquals("SELECT count(1) FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t.`stars` ASC) t2", sql);
        }
    }

    @Test
    public void query12x() {
        EasyPageResult<Topicx> pageResult = easyEntityQuery.queryable(Topic.class).where(o -> o.createTime().isNotNull())
                .select(Topicx.class).toPageResult(1, 20);
        System.out.println(pageResult);
    }

    @Test
    public void query13x() {
        Query<SysUser> queryable = easyEntityQuery.queryable(SysUser.class)
                .where(o -> {
                    o.id().eq("123xxx");
                    o.phone().like(false, "133");
                });
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE `id` = ?", sql);
        SysUser sysUser = queryable.firstOrNull();
        Assert.assertNull(sysUser);
    }

    @Test
    public void query14x() {
        try {

            Map<String, String> phone = null;
            Query<SysUser> queryable = easyEntityQuery.queryable(SysUser.class)
                    .where(o -> {
                        o.id().eq("123xxx");
                        o.phone().like(phone != null && phone.containsKey("phone"), phone.get("phone"));
                    });
            String sql = queryable.toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE `id` = ?", sql);
            SysUser sysUser = queryable.firstOrNull();
            Assert.assertNull(sysUser);
        } catch (Exception exception) {
            Assert.assertTrue(exception instanceof NullPointerException);
        }
    }

    @Test
    public void query143x() {

        Map<String, String> phone = null;
        Query<SysUser> queryable = easyEntityQuery.queryable(SysUser.class)
                .where(o -> o.id().eq("123xxx"))
                .where(phone != null && phone.containsKey("phone"), o -> o.phone().like(phone.get("phone")));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE `id` = ?", sql);
        SysUser sysUser = queryable.firstOrNull();
        Assert.assertNull(sysUser);

    }

    @Test
    public void query26() {

//        List<TopicY> list = easyEntityQuery.queryable(TopicY.class)
//                .include(t->t.many(TopicY::getTopicxList))
//                .toList();
        List<TopicY> list1 = easyEntityQuery.queryable(TopicY.class)
                .include(t -> t.topic())
                .toList();
        Assert.assertTrue(list1.size() > 0);
        for (TopicY topicY : list1) {
            Topic topic = topicY.getTopic();
            Assert.assertEquals(topicY.getId(), topic.getId());
        }
//                .include(TopicY::getTopic,q->q.where(x->x.eq(Topic::getId,1)));
    }

    @Test
    public void dynamicSort() {
        {

            BlogSortRequest blogSortRequest = new BlogSortRequest();
            blogSortRequest.setSort("title");
            blogSortRequest.setAsc(true);
            String sql = easyEntityQuery.queryable(BlogEntity.class)
                    .orderByObject(blogSortRequest)
                    .select(o -> o.FETCHER.id().title().content())
                    .toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`title`,t.`content` FROM `t_blog` t WHERE t.`deleted` = ? ORDER BY t.`title` ASC", sql);
        }
        {

            BlogSortRequest blogSortRequest = new BlogSortRequest();
            blogSortRequest.setSort("title");
            blogSortRequest.setAsc(false);
            String sql = easyEntityQuery.queryable(BlogEntity.class)
                    .orderByObject(blogSortRequest)
                    .select(o -> o.FETCHER.id().title().content())
                    .toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`title`,t.`content` FROM `t_blog` t WHERE t.`deleted` = ? ORDER BY t.`title` DESC", sql);
        }
        {

            BlogSortRequest blogSortRequest = new BlogSortRequest();
            blogSortRequest.setSort("title");
            String sql = easyEntityQuery.queryable(BlogEntity.class)
                    .orderByObject(blogSortRequest)
                    .select(o -> o.FETCHER.id().title().content())
                    .toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`title`,t.`content` FROM `t_blog` t WHERE t.`deleted` = ?", sql);
        }
        {

            BlogSortRequest blogSortRequest = new BlogSortRequest();
            blogSortRequest.setAsc(true);
            String sql = easyEntityQuery.queryable(BlogEntity.class)
                    .orderByObject(blogSortRequest)
                    .select(o -> o.FETCHER.id().title().content())
                    .toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`title`,t.`content` FROM `t_blog` t WHERE t.`deleted` = ?", sql);
        }
    }

    @Test
    public void dynamicSort2() {

        {

            BlogSortMultiRequest blogSortRequest = new BlogSortMultiRequest();
            BlogSortMultiRequest.SortConfig sortConfig = new BlogSortMultiRequest.SortConfig();
            sortConfig.setProperty("title");
            sortConfig.setAsc(true);
            blogSortRequest.getOrders().add(sortConfig);
            BlogSortMultiRequest.SortConfig sortConfig1 = new BlogSortMultiRequest.SortConfig();
            sortConfig1.setProperty("star");
            sortConfig1.setAsc(false);
            blogSortRequest.getOrders().add(sortConfig1);
            String sql = easyEntityQuery.queryable(BlogEntity.class)
                    .orderByObject(blogSortRequest)
                    .select(o -> o.FETCHER.id().title().content())
                    .toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`title`,t.`content` FROM `t_blog` t WHERE t.`deleted` = ? ORDER BY t.`title` ASC,t.`star` DESC", sql);
        }
    }

    @Test
    public void dynamicSort3() {

        {

            BlogSortJoinRequest blogSortRequest = new BlogSortJoinRequest();
            BlogSortJoinRequest.SortConfig sortConfig = new BlogSortJoinRequest.SortConfig();
            sortConfig.setProperty("title");
            sortConfig.setAsc(true);
            blogSortRequest.getOrders().add(sortConfig);
            BlogSortJoinRequest.SortConfig sortConfig1 = new BlogSortJoinRequest.SortConfig();
            sortConfig1.setProperty("createTime");
            sortConfig1.setAsc(false);
            blogSortRequest.getOrders().add(sortConfig1);
            String sql = easyEntityQuery.queryable(BlogEntity.class)
                    .innerJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                    .orderByObject(blogSortRequest)
                    .select(o -> o.FETCHER.id().title().content())
                    .toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`title`,t.`content` FROM `t_blog` t INNER JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ? ORDER BY t.`title` ASC,t1.`create_time` DESC", sql);
        }
    }

    @Test
    public void dynamicSort4() {

        {

            BlogSortJoinRequest blogSortRequest = new BlogSortJoinRequest();
            BlogSortJoinRequest.SortConfig sortConfig = new BlogSortJoinRequest.SortConfig();
            sortConfig.setProperty("title");
            sortConfig.setAsc(true);
            blogSortRequest.getOrders().add(sortConfig);
            BlogSortJoinRequest.SortConfig sortConfig1 = new BlogSortJoinRequest.SortConfig();
            sortConfig1.setProperty("createTime");
            sortConfig1.setAsc(false);
            blogSortRequest.getOrders().add(sortConfig1);
            String sql = easyEntityQuery.queryable(BlogEntity.class)
                    .innerJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                    .orderByObject(blogSortRequest)
                    .select(o -> o.FETCHER.id().title().content())
                    .toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`title`,t.`content` FROM `t_blog` t INNER JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ? ORDER BY t.`title` ASC,t1.`create_time` DESC", sql);
        }
    }


    @Test
    public void queryMultiFrom1() {
        String sql = easyQueryClient
                .queryable(Topic.class)
                .from(Topic.class)
                .whereById(true, "1")
                .where((a, b) -> a.eq("id", "id"))
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t , `t_topic` t1 WHERE t.`id` = ? AND t.`id` = ?", sql);
    }

}
