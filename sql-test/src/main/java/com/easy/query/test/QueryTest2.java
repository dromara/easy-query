package com.easy.query.test;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.jdbc.parameter.ConstLikeSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.EasyConstSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.AggregatePredicateCompare;
import com.easy.query.core.exception.EasyQuerySingleMoreElementException;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.test.dto.BlogEntityGroup;
import com.easy.query.test.dto.BlogQueryRequest;
import com.easy.query.test.dto.BlogSortJoinRequest;
import com.easy.query.test.dto.BlogSortMultiRequest;
import com.easy.query.test.dto.BlogSortRequest;
import com.easy.query.test.dto.TopicGroupTestDTO;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicY;
import com.easy.query.test.entity.Topicx;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * create time 2023/6/8 21:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest2 extends BaseTest {
    @Test
    public void query1231(){

        String sql = easyQueryClient.queryable(Topic.class)
                .where(o -> {
                    o.sqlNativeSegment("{0} = ({1})", c -> {
                        ClientQueryable<LocalDateTime> maxCreateTimeQuery = easyQueryClient.queryable(Topic.class)
                                .select(LocalDateTime.class, x -> x.columnMax("createTime"));
                        c.expression(o, "createTime")
                                .expression(maxCreateTimeQuery);
                    });
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` = (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1)",sql);
    }
    @Test
    public void query1232(){

        ClientQueryable<LocalDateTime> maxCreateTimeQuery = easyQueryClient.queryable(Topic.class)
                .select(LocalDateTime.class, x -> x.columnMax("createTime"));
        String sql = easyQueryClient.queryable(Topic.class)
                .where(o -> {
                    o.eq("createTime",maxCreateTimeQuery);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` = (SELECT MAX(t1.`create_time`) AS `create_time` FROM `t_topic` t1)",sql);
    }

    @Test
    public void query124() {

        String toSql = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3"))
                .limit(1, 2)
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` = ? LIMIT 2 OFFSET 1", toSql);
    }

    @Test
    public void query124_1() {
        String toSql = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3"))
                .limit(1, 2)
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` = ? LIMIT 2 OFFSET 1", toSql);
    }

    @Test
    public void query125() {
        String toSql = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3"))
                .select(BlogEntity.class, (t, t1,t2) -> t.column(Topic::getId)
                        .then(t1)
                        .column(BlogEntity::getTitle)
                        .then(t2)
                        .column(BlogEntity::getStar))
                .limit(1, 2)
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t1.`title`,t2.`star` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` INNER JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` = ? LIMIT 2 OFFSET 1", toSql);
    }

    @Test
    public void query126() {
        {

            List<BlogEntity> list = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3"))
                    .select(BlogEntity.class, (t, t1,t2) -> t.column(Topic::getId)
                            .then(t1)
                            .column(BlogEntity::getTitle)
                            .then(t2)
                            .column(BlogEntity::getStar))
                    .limit(1, 2)
                    .toList();
            Assert.assertEquals(0, list.size());
        }
        {

            List<BlogEntity> list = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3"))
                    .select(BlogEntity.class, (t, t1,t2) -> t.column(Topic::getId)
                            .then(t1)
                            .column(BlogEntity::getTitle)
                            .then(t2)
                            .column(BlogEntity::getStar))
                    .limit(1)
                    .toList();
            Assert.assertEquals(1, list.size());
        }
        {

            List<BlogEntity> list = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3"))
                    .select(BlogEntity.class, (t, t1,t2) -> {
                        t.column(Topic::getId);
                        t1.column(BlogEntity::getTitle);
                        t2.column(BlogEntity::getStar);
                    })
                    .limit(1)
                    .toList();
            Assert.assertEquals(1, list.size());
        }
    }

    @Test
    public void query112() {
        {

            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3"))
                    .sumOrDefault((t, t1) -> t1.column(BlogEntity::getScore), BigDecimal.ZERO);
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {

            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3"))
                    .sumOrDefaultMerge(c -> c.t1().column(BlogEntity::getScore), BigDecimal.ZERO);
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
    }

    @Test
    public void query113() {
        {

            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3x"))
                    .sumOrNull((t, t1,t2) -> t1.column(BlogEntity::getScore));
            Assert.assertTrue(bigDecimal == null);
        }
        {

            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3x"))
                    .sumOrNullMerge(c -> c.t1().column(BlogEntity::getScore));
            Assert.assertTrue(bigDecimal == null);
        }
    }

    @Test
    public void query114() {
        {

            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3"))
                    .sumBigDecimalOrNull((t, t1,t2) -> t2.column(BlogEntity::getScore));
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {

            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3"))
                    .sumBigDecimalOrNullMerge(c -> c.t2().column(BlogEntity::getScore));
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
    }

    @Test
    public void query115() {
        {

            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3x"))
                    .sumBigDecimalOrDefault((t, t1,t2) -> t2.column(BlogEntity::getScore), null);
            Assert.assertTrue(bigDecimal == null);
            BigDecimal bigDecimal1 = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3x"))
                    .sumBigDecimalOrDefault((t, t1,t2) -> t1.column(BlogEntity::getScore), BigDecimal.ZERO);
            Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
        }
        {

            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3x"))
                    .sumBigDecimalOrDefaultMerge(c -> c.t2().column(BlogEntity::getScore), null);
            Assert.assertTrue(bigDecimal == null);
            BigDecimal bigDecimal1 = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3x"))
                    .sumBigDecimalOrDefaultMerge(c -> c.t1().column(BlogEntity::getScore), BigDecimal.ZERO);
            Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
        }
    }

    @Test
    public void query116() {
        {

            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3x"))
                    .maxOrNull((t, t1,t2) -> t2.column(BlogEntity::getScore));
            Assert.assertTrue(bigDecimal == null);
            BigDecimal bigDecimal1 = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3x"))
                    .maxOrDefault((t, t1,t2) -> t1.column(BlogEntity::getScore), BigDecimal.ZERO);
            Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
        }
        {

            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3x"))
                    .maxOrNullMerge(c -> c.t2().column(BlogEntity::getScore));
            Assert.assertTrue(bigDecimal == null);
            BigDecimal bigDecimal1 = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3x"))
                    .maxOrDefaultMerge(c -> c.t1().column(BlogEntity::getScore), BigDecimal.ZERO);
            Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
        }
    }

    @Test
    public void query117() {
        {

            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "3")))
                    .maxOrNull((t, t1,t2) -> t2.column(BlogEntity::getScore));
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {

            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "3")))
                    .maxOrNullMerge(c -> c.t2().column(BlogEntity::getScore));
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
    }

    @Test
    public void query118() {
        {

            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3x"))
                    .minOrNull((t, t1,t2) -> t1.column(BlogEntity::getScore));
            Assert.assertTrue(bigDecimal == null);
            BigDecimal bigDecimal1 = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3x"))
                    .minOrDefault((t, t1,t2) -> t2.column(BlogEntity::getScore), BigDecimal.ZERO);
            Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
        }
        {

            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3x"))
                    .minOrNullMerge(c -> c.t1().column(BlogEntity::getScore));
            Assert.assertTrue(bigDecimal == null);
            BigDecimal bigDecimal1 = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3x"))
                    .minOrDefaultMerge(c -> c.t2().column(BlogEntity::getScore), BigDecimal.ZERO);
            Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
        }
    }

    @Test
    public void query119() {
        {

            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "3")))
                    .minOrNull((t, t1,t2) -> t2.column(BlogEntity::getScore));
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {

            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "3")))
                    .minOrNullMerge(c -> c.t2().column(BlogEntity::getScore));
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
    }

    @Test
    public void query120() {
        {
            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .avgBigDecimalOrNull((t, t1,t2) -> t1.column(BlogEntity::getScore));
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {
            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .avgBigDecimalOrNullMerge(c -> c.t1().column(BlogEntity::getScore));
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {
            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5x")))
                    .avgBigDecimalOrNull((t, t1,t2) -> t2.column(BlogEntity::getScore));
            Assert.assertTrue(bigDecimal == null);
        }
        {
            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5x")))
                    .avgBigDecimalOrNullMerge(c -> c.t2().column(BlogEntity::getScore));
            Assert.assertTrue(bigDecimal == null);
        }
        {
            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5")))
                    .avgBigDecimalOrDefault((t, t1,t2) -> t2.column(BlogEntity::getScore), BigDecimal.ZERO);
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {
            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5")))
                    .avgBigDecimalOrDefaultMerge(c -> c.t2().column(BlogEntity::getScore), BigDecimal.ZERO);
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {
            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5x")))
                    .avgBigDecimalOrDefault((t, t1) -> t1.column(BlogEntity::getScore), BigDecimal.ZERO);
            Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal) == 0);
        }
        {
            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5x")))
                    .avgBigDecimalOrDefaultMerge(c -> c.t1().column(BlogEntity::getScore), BigDecimal.ZERO);
            Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal) == 0);
        }
        {
            Double bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .avgOrNull((t, t1) -> t1.column(BlogEntity::getStar));
            Assert.assertTrue(3.3333d == bigDecimal);
        }
        {
            Double bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .avgOrNullMerge(c -> c.t1().column(BlogEntity::getStar));
            Assert.assertTrue(3.3333d == bigDecimal);
        }
        {
            Double bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5x")))
                    .avgOrDefault((t, t1) -> t1.column(BlogEntity::getStar), null);
            Assert.assertTrue(null == bigDecimal);
        }
        {
            Double bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5x")))
                    .avgOrDefaultMerge(c -> c.t1().column(BlogEntity::getStar), null);
            Assert.assertTrue(null == bigDecimal);
        }
        {
            Double bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5")))
                    .avgOrDefault((t, t1,t2) -> t2.column(BlogEntity::getStar), null);
            Assert.assertTrue(5d == bigDecimal);
        }
        {
            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5")))
                    .avgOrDefault((t, t1,t2) -> t2.column(BlogEntity::getStar), null, BigDecimal.class);
            Assert.assertTrue(new BigDecimal("5").compareTo(bigDecimal) == 0);
        }
        {
            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5")))
                    .avgOrDefaultMerge(c -> c.t2().column(BlogEntity::getStar), null, BigDecimal.class);
            Assert.assertTrue(new BigDecimal("5").compareTo(bigDecimal) == 0);
        }
        {
            Float bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5")))
                    .avgOrDefault((t, t1,t2) -> t1.column(BlogEntity::getStar), null, Float.class);
            Assert.assertTrue(5f == bigDecimal);
        }
        {
            Float bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5")))
                    .avgOrDefaultMerge(c -> c.t1().column(BlogEntity::getStar), null, Float.class);
            Assert.assertTrue(5f == bigDecimal);
        }
    }

    @Test
    public void query121() {
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .orderByAsc((t, t1,t2) -> t1.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) ORDER BY t1.`order` ASC", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .orderByAsc(false, (t, t1,t2) -> t1.column(BlogEntity::getOrder))
                    .orderByAsc(true, (t, t1,t2) -> t2.column(BlogEntity::getId)).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) ORDER BY t2.`id` ASC", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .orderByDesc((t, t1,t2) -> t2.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) ORDER BY t2.`order` DESC", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .orderByDesc(false, (t, t1,t2) -> t2.column(BlogEntity::getOrder))
                    .orderByDesc(true, (t, t1,t2) -> t2.column(BlogEntity::getId).column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) ORDER BY t2.`id` DESC,t2.`order` DESC", sql);
        }
    }

    @Test
    public void query122() {
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy(o -> o.column(Topic::getId))
                    .orderByAsc((t, t1,t2) -> t1.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t1.`order` ASC", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1,t2) -> t2.column(BlogEntity::getId))
                    .orderByAsc((t, t1,t2) -> t2.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t2.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) GROUP BY t2.`id` ORDER BY t2.`order` ASC", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy(false, (t, t1,t2) -> t.column(Topic::getStars))
                    .groupBy(true, (t, t1,t2) -> t.column(Topic::getId))
                    .orderByAsc((t, t1,t2) -> t1.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` INNER JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t1.`order` ASC", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy(false, (t, t1,t2) -> t.column(Topic::getStars))
                    .groupBy(true, (t, t1,t2) -> t.column(Topic::getId))
                    .orderByAsc((t, t1,t2) -> t2.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` INNER JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t2.`order` ASC", sql);
        }
    }

    @Test
    public void query123() {
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy(o -> o.column(Topic::getId))
                    .distinct()
                    .orderByAsc((t, t1,t2) -> t2.column(BlogEntity::getOrder))
                    .limit(10).toSQL();
            Assert.assertEquals("SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` INNER JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t2.`order` ASC LIMIT 10", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy(o -> o.column(Topic::getId))
                    .orderByAsc((t, t1,t2) -> t2.column(BlogEntity::getOrder))
                    .distinct()
                    .select(Topic.class, o -> o.column(Topic::getId))
                    .select(Long.class, o -> o.columnCount(Topic::getId)).toSQL();
            Assert.assertEquals("SELECT COUNT(t3.`id`) AS `id` FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` INNER JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t2.`order` ASC) t3", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy(o -> o.column(Topic::getId))
                    .orderByAsc((t, t1,t2) -> t1.column(BlogEntity::getOrder))
                    .distinct()
                    .select(Topic.class, o -> o.column(Topic::getId))
                    .select("count(1)").toSQL();
            Assert.assertEquals("SELECT count(1) FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t1.`order` ASC) t3", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy(o -> o.column(Topic::getId))
                    .orderByDesc((t) -> t.column(Topic::getStars))
                    .distinct()
                    .select(Topic.class, o -> o.column(Topic::getId))
                    .select("count(1)").toSQL();
            Assert.assertEquals("SELECT count(1) FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t.`stars` DESC) t3", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1,t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy(o -> o.column(Topic::getId))
                    .orderByAsc((t) -> t.column(Topic::getStars))
                    .distinct()
                    .select(Topic.class, o -> o.column(Topic::getId))
                    .select("count(1)").toSQL();
            Assert.assertEquals("SELECT count(1) FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t.`stars` ASC) t3", sql);
        }
    }

    @Test
    public void groupTest() {
        String sql = easyQuery.queryable(BlogEntity.class)
                .groupBy(o -> o.column(BlogEntity::getId))
                .select(BlogEntityGroup.class, o -> o
                        .columnAs(BlogEntity::getId, BlogEntityGroup::getId)
                        .columnSumAs(BlogEntity::getScore, BlogEntityGroup::getScoreSum)
                        .columnCountAs(BlogEntity::getId, BlogEntityGroup::getIdCount)
                        .columnLenAs(BlogEntity::getTitle, BlogEntityGroup::getTitleLength)
                        .columnMaxAs(BlogEntity::getPublishTime, BlogEntityGroup::getPublishTimeMax)
                        .columnMinAs(BlogEntity::getOrder, BlogEntityGroup::getOrderMin)
                        .columnAvgAs(BlogEntity::getStatus, BlogEntityGroup::getStatusAvg)
                ).toSQL();
        Assert.assertEquals("SELECT t.`id` AS `id`,SUM(t.`score`) AS `score_sum`,COUNT(t.`id`) AS `id_count`,LENGTH(t.`title`) AS `title_length`,MAX(t.`publish_time`) AS `publish_time_max`,MIN(t.`order`) AS `order_min`,AVG(t.`status`) AS `status_avg` FROM `t_blog` t WHERE t.`deleted` = ? GROUP BY t.`id`", sql);
    }

    @Test
    public void groupTest1() {
        String sql = easyQuery.queryable(BlogEntity.class)
                .groupBy(o -> o.column(BlogEntity::getId))
                .select(BlogEntity.class, o -> o
                        .columnAs(BlogEntity::getId, BlogEntity::getId)
                        .columnSum(BlogEntity::getScore)
                        .columnLen(BlogEntity::getTitle)
                        .columnMax(BlogEntity::getPublishTime)
                        .columnMin(BlogEntity::getOrder)
                        .columnAvg(BlogEntity::getStatus)
                ).toSQL();
        Assert.assertEquals("SELECT t.`id` AS `id`,SUM(t.`score`) AS `score`,LENGTH(t.`title`) AS `title`,MAX(t.`publish_time`) AS `publish_time`,MIN(t.`order`) AS `order`,AVG(t.`status`) AS `status` FROM `t_blog` t WHERE t.`deleted` = ? GROUP BY t.`id`", sql);
    }

    @Test
    public void groupTest2() {
        String sql = easyQuery.queryable(BlogEntity.class)
                .groupBy(o -> o.column(BlogEntity::getId))
                .select(BlogEntityGroup.class, o -> o
                        .columnAs(BlogEntity::getId, BlogEntityGroup::getId)
                        .columnSumAs(BlogEntity::getScore, BlogEntityGroup::getScoreSum,c->c.distinct(true))
                        .columnCountAs(BlogEntity::getId, BlogEntityGroup::getIdCount,c->c.distinct(true))
                        .columnAvgAs(BlogEntity::getStatus, BlogEntityGroup::getStatusAvg,c->c.distinct(true))
                ).toSQL();
        Assert.assertEquals("SELECT t.`id` AS `id`,SUM(DISTINCT t.`score`) AS `score_sum`,COUNT(DISTINCT t.`id`) AS `id_count`,AVG(DISTINCT t.`status`) AS `status_avg` FROM `t_blog` t WHERE t.`deleted` = ? GROUP BY t.`id`", sql);
    }

    @Test
    public void groupTest3() {
        String sql = easyQuery.queryable(BlogEntity.class)
                .groupBy(o -> o.column(BlogEntity::getId))
                .select(BlogEntity.class, o -> o
                        .columnAs(BlogEntity::getId, BlogEntity::getId)
                        .columnSum(BlogEntity::getScore,c->{
                            c.distinct(true);
                        })
                        .columnCount(BlogEntity::getOrder,c->c.distinct(true))
                        .columnAvg(BlogEntity::getStatus,c->c.distinct(true))
                ).toSQL();
        Assert.assertEquals("SELECT t.`id` AS `id`,SUM(DISTINCT t.`score`) AS `score`,COUNT(DISTINCT t.`order`) AS `order`,AVG(DISTINCT t.`status`) AS `status` FROM `t_blog` t WHERE t.`deleted` = ? GROUP BY t.`id`", sql);
    }

    @Test
    public void groupTest4() {
        String sql = easyQuery.queryable(BlogEntity.class)
                .groupBy(o -> o.column(BlogEntity::getId))
                .select(BlogEntity.class, o -> o
                        .columnAs(BlogEntity::getId, BlogEntity::getId)
                        .columnSum(BlogEntity::getScore,c->c.distinct(true))
                        .columnCount(BlogEntity::getOrder,c->c.distinct(true))
                        .columnAvg(BlogEntity::getStatus,c->c.distinct(true))
                ).toSQL();
        Assert.assertEquals("SELECT t.`id` AS `id`,SUM(DISTINCT t.`score`) AS `score`,COUNT(DISTINCT t.`order`) AS `order`,AVG(DISTINCT t.`status`) AS `status` FROM `t_blog` t WHERE t.`deleted` = ? GROUP BY t.`id`", sql);
    }
    @Test
    public void groupTest5() {
        String sql = easyQuery.queryable(BlogEntity.class)
                .groupBy(o -> o.column(BlogEntity::getId))
                .select(BlogEntity.class, o -> o
                        .columnAs(BlogEntity::getId, BlogEntity::getId)
                        .columnSum(BlogEntity::getScore,c->c.distinct(true).nullDefault(BigDecimal.ZERO))
                        .columnCount(BlogEntity::getOrder,c->c.distinct(true).nullDefault(BigDecimal.ZERO))
                        .columnAvg(BlogEntity::getStatus,c->c.distinct(true).nullDefault(BigDecimal.ZERO))
                ).toSQL();
        Assert.assertEquals("SELECT t.`id` AS `id`,SUM(DISTINCT IFNULL(t.`score`,?)) AS `score`,COUNT(DISTINCT IFNULL(t.`order`,?)) AS `order`,AVG(DISTINCT IFNULL(t.`status`,?)) AS `status` FROM `t_blog` t WHERE t.`deleted` = ? GROUP BY t.`id`", sql);
    }

    @Test
    public void groupTest5_1() {
        BlogEntity blogEntity = easyQuery.queryable(BlogEntity.class)
                .groupBy(o -> o.column(BlogEntity::getId))
                .select(BlogEntity.class, o -> o
                        .columnAs(BlogEntity::getId, BlogEntity::getId)
                        .columnSum(BlogEntity::getScore, c -> c.distinct(true).nullDefault(BigDecimal.ZERO))
                        .columnCount(BlogEntity::getOrder, c -> c.distinct(true).nullDefault(BigDecimal.ONE))
                        .columnAvg(BlogEntity::getStatus, c -> c.distinct(true).nullDefault(BigDecimal.valueOf(3)))
                ).firstOrNull();
        Assert.assertNotNull(blogEntity);
    }

    @Test
    public void groupTest9() {
        String sql = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.ne(BlogEntity::getId, "1").ne(true, BlogEntity::getStar, 1).ne(false, BlogEntity::getTitle, "x")).toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` <> ? AND `star` <> ?", sql);
    }

    @Test
    public void groupTest10() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.notLikeMatchLeft(BlogEntity::getId, "id").notLikeMatchLeft(true, BlogEntity::getContent, "content").notLikeMatchLeft(false, BlogEntity::getTitle, "title"));
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
    public void groupTest11() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.like(BlogEntity::getId, "id").like(true, BlogEntity::getContent, "content").like(false, BlogEntity::getTitle, "title"));
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
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.notLikeMatchRight(BlogEntity::getId, "id").notLikeMatchRight(true, BlogEntity::getContent, "content").notLikeMatchRight(false, BlogEntity::getTitle, "title"));
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
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.notLike(BlogEntity::getId, "id").notLike(true, BlogEntity::getContent, "content").notLike(false, BlogEntity::getTitle, "title"));
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
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.lt(BlogEntity::getStar, 9).lt(true, BlogEntity::getPublishTime, now).lt(false, BlogEntity::getOrder, 4));

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
        blogQueryRequest.setPublishTimeBegin(LocalDateTime.now());
        blogQueryRequest.setPublishTimeEnd(LocalDateTime.now());
        blogQueryRequest.setScore(new BigDecimal("123"));
        blogQueryRequest.setStatus(1);
        blogQueryRequest.setOrder(new BigDecimal("12"));
        blogQueryRequest.setIsTop(false);
        blogQueryRequest.getOrders().add("status");
        blogQueryRequest.getOrders().add("score");
        String sql = easyQuery.queryable(BlogEntity.class)
                .whereObject(blogQueryRequest)
                .orderByObject(blogQueryRequest)
                .toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `title` LIKE ? AND `url` LIKE ? AND `star` = ? AND `publish_time` >= ? AND `publish_time` <= ? AND `score` >= ? AND `status` <= ? AND `order` > ? AND `is_top` <> ? ORDER BY `status` ASC,`score` ASC", sql);
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
        String sql = easyQuery.queryable(BlogEntity.class)
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
        String sql = easyQuery.queryable(BlogEntity.class)
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
        String sql = easyQuery.queryable(BlogEntity.class)
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
        String sql = easyQuery.queryable(BlogEntity.class)
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
        String sql = easyQuery.queryable(BlogEntity.class)
                .whereObject(blogQueryRequest)
                .orderByObject(blogQueryRequest)
                .toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `title` LIKE ? AND `url` LIKE ? AND `star` = ? AND `publish_time` >= ? AND `publish_time` <= ? AND `score` >= ? AND `status` <= ? AND `order` > ? AND `is_top` <> ? AND `status` IN (?) AND `status` NOT IN (?,?) ORDER BY `status` ASC,`score` ASC", sql);
    }

    @Test
    public void query22() {
        Queryable<TopicGroupTestDTO> topicGroupTestDTOQueryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "1"))
                .groupBy(o -> o.column(BlogEntity::getId))
                .select(TopicGroupTestDTO.class, o -> o.columnAs(BlogEntity::getId, TopicGroupTestDTO::getId).columnCountAs(BlogEntity::getId, TopicGroupTestDTO::getIdCount))
                .orderByAsc(o -> o.sqlNativeSegment("RAND()"));
        String sql = topicGroupTestDTOQueryable.toSQL();
        Assert.assertEquals("SELECT t1.`id` AS `id`,t1.`id_count` AS `id_count` FROM (SELECT t.`id` AS `id`,COUNT(t.`id`) AS `id_count` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? GROUP BY t.`id`) t1 ORDER BY RAND()", sql);
        List<TopicGroupTestDTO> list = topicGroupTestDTOQueryable.toList();
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void query23() {
        Queryable<TopicGroupTestDTO> topicGroupTestDTOQueryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "1"))
                .groupBy(o -> o.sqlNativeSegment("RAND()"))
                .select(TopicGroupTestDTO.class, o -> o.columnAs(BlogEntity::getId, TopicGroupTestDTO::getId).columnCountAs(BlogEntity::getId, TopicGroupTestDTO::getIdCount))
                .orderByAsc(o -> o.sqlNativeSegment("RAND()"));
        String sql = topicGroupTestDTOQueryable.toSQL();
        Assert.assertEquals("SELECT t1.`id` AS `id`,t1.`id_count` AS `id_count` FROM (SELECT t.`id` AS `id`,COUNT(t.`id`) AS `id_count` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? GROUP BY RAND()) t1 ORDER BY RAND()", sql);
        List<TopicGroupTestDTO> list = topicGroupTestDTOQueryable.toList();
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void query24() {
        Queryable<TopicGroupTestDTO> topicGroupTestDTOQueryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "1"))
                .groupBy(o -> o.sqlNativeSegment("RAND()"))
                .select(TopicGroupTestDTO.class, o -> o.sqlNativeSegment("RAND()", it->it.setAlias("rad")).columnAs(BlogEntity::getId, TopicGroupTestDTO::getId).columnCountAs(BlogEntity::getId, TopicGroupTestDTO::getIdCount))
                .orderByAsc(o -> o.sqlNativeSegment("RAND() ASC"));
        String sql = topicGroupTestDTOQueryable.toSQL();
        Assert.assertEquals("SELECT t1.`rad` AS `rad`,t1.`id` AS `id`,t1.`id_count` AS `id_count` FROM (SELECT RAND() AS `rad`,t.`id` AS `id`,COUNT(t.`id`) AS `id_count` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? GROUP BY RAND()) t1 ORDER BY RAND() ASC", sql);
        List<TopicGroupTestDTO> list = topicGroupTestDTOQueryable.toList();
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void query25() {
        String[] id1s = {"97", "98"};
        String[] id2s = {"99", "100"};
        String[] id3s = {"101", "102"};
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.and(x -> x
                        .in(BlogEntity::getId, id1s)
                        .or()
                        .in(BlogEntity::getId, id2s)
                        .or()
                        .in(BlogEntity::getId, id3s)
                ));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (`id` IN (?,?) OR `id` IN (?,?) OR `id` IN (?,?))", sql);
    }

    @Test
    public void query122x() {
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy(o -> o.column(Topic::getId))
                    .orderByAsc((t, t1) -> t1.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t1.`order` ASC", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1) -> t.column(Topic::getId))
                    .orderByAsc((t, t1) -> t1.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t1.`order` ASC", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy(false, (t, t1) -> t.column(Topic::getStars))
                    .groupBy(true, (t, t1) -> t.column(Topic::getId))
                    .orderByAsc((t, t1) -> t1.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t1.`order` ASC", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy(false, (t, t1) -> t.column(Topic::getStars))
                    .groupBy(true, (t, t1) -> t.column(Topic::getId))
                    .orderByAsc((t, t1) -> t1.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t1.`order` ASC", sql);
        }
        {
            List<Topic> list = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1) -> t.column(Topic::getId))
                    .orderByAsc((t, t1) -> t1.column(BlogEntity::getOrder))
                    .toList();
            Assert.assertEquals(3, list.size());
            for (Topic topic : list) {
                Assert.assertNotNull(topic.getId());
                Assert.assertNull(topic.getStars());
                Assert.assertNull(topic.getTitle());
            }
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy(o -> o.column(Topic::getId))
                    .having(o -> o.count(Topic::getId, AggregatePredicateCompare.GE, 1))
                    .orderByAsc((t, t1) -> t1.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t1.`order` ASC", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy(o -> o.column(Topic::getId))
                    .having(false, (t, t1) -> t1.count(BlogEntity::getId, AggregatePredicateCompare.GE, 1))
                    .having(true, (t, t1) -> t.count(Topic::getId, AggregatePredicateCompare.GE, 1))
                    .orderByAsc((t, t1) -> t1.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t1.`order` ASC", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy(o -> o.column(Topic::getId))
                    .having(false, (t, t1) -> t1.count(BlogEntity::getId, AggregatePredicateCompare.GE, 1))
                    .having((t, t1) -> t.count(Topic::getId, AggregatePredicateCompare.GE, 1))
                    .orderByAsc((t, t1) -> t1.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` HAVING COUNT(t.`id`) >= ? ORDER BY t1.`order` ASC", sql);
        }
    }

    @Test
    public void query123x() {
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy(o -> o.column(Topic::getId))
                    .distinct()
                    .orderByAsc((t, t1) -> t1.column(BlogEntity::getOrder))
                    .limit(10).toSQL();
            Assert.assertEquals("SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t1.`order` ASC LIMIT 10", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy(o -> o.column(Topic::getId))
                    .orderByAsc((t, t1) -> t1.column(BlogEntity::getOrder))
                    .distinct()
                    .select(Topic.class, o -> o.column(Topic::getId))
                    .select(Long.class, o -> o.columnCount(Topic::getId)).toSQL();
            Assert.assertEquals("SELECT COUNT(t2.`id`) AS `id` FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t1.`order` ASC) t2", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy(o -> o.column(Topic::getId))
                    .orderByAsc((t, t1) -> t1.column(BlogEntity::getOrder))
                    .distinct()
                    .select(Topic.class, o -> o.column(Topic::getId))
                    .select("count(1)").toSQL();
            Assert.assertEquals("SELECT count(1) FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t1.`order` ASC) t2", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy(o -> o.column(Topic::getId))
                    .orderByDesc((t) -> t.column(Topic::getStars))
                    .distinct()
                    .select(Topic.class, o -> o.column(Topic::getId))
                    .select("count(1)").toSQL();
            Assert.assertEquals("SELECT count(1) FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t.`stars` DESC) t2", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .groupBy(o -> o.column(Topic::getId))
                    .orderByAsc((t) -> t.column(Topic::getStars))
                    .distinct()
                    .select(Topic.class, o -> o.column(Topic::getId))
                    .select("count(1)").toSQL();
            Assert.assertEquals("SELECT count(1) FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t.`stars` ASC) t2", sql);
        }
    }

    @Test
    public void query12x() {
        EasyPageResult<Topicx> pageResult = easyQuery.queryable(Topic.class).where(o -> o.isNotNull(Topic::getCreateTime))
                .select(Topicx.class).toPageResult(1, 20);
        System.out.println(pageResult);
    }

    @Test
    public void query13x() {
        Queryable<SysUser> queryable = easyQuery.queryable(SysUser.class)
                .where(o -> o.eq(SysUser::getId, "123xxx")
                        .like(false, SysUser::getPhone, "133"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE `id` = ?", sql);
        SysUser sysUser = queryable.firstOrNull();
        Assert.assertNull(sysUser);
    }
    @Test
    public void query13x_1() {
        Queryable<Topic> topicQueryable = easyQuery.queryable(Topic.class)
                .groupBy(o->o.column(Topic::getId))
                .select(o->o.sqlNativeSegment("GROUP_CONCAT({0})",c->{
                    c.expression(Topic::getTitle);
                }));
        Queryable<SysUser> queryable = easyQuery.queryable(SysUser.class)
                .where(o -> o.eq(SysUser::getId, "123xxx").like(false, SysUser::getPhone, "133"))
                .where(o->o.sqlNativeSegment("FIND_IN_SET({0},({1}))",c->{
                    c.expression(SysUser::getId)
                            .expression(topicQueryable);
                }));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t WHERE t.`id` = ? AND FIND_IN_SET(t.`id`,(SELECT GROUP_CONCAT(t1.`title`) FROM `t_topic` t1 GROUP BY t1.`id`))", sql);
    }
    @Test
    public void query13x_2() {
        Function<EntitySQLTableOwner<SysUser>,Queryable<String>> subQueryCreator=table->{
            Queryable<String> groupConcatQuery = easyQuery.queryable(Topic.class)
                    .where(o->o.eq(table,Topic::getId,SysUser::getId))
                    .groupBy(o -> o.column(Topic::getId))
                    .select(String.class, o -> o.sqlNativeSegment("GROUP_CONCAT({0})", c -> {
                        c.expression(Topic::getTitle);
                    }));
            return groupConcatQuery;
        };
        Queryable<SysUser> queryable = easyQuery.queryable(SysUser.class)
                .where(o -> o.eq(SysUser::getId, "123xxx").like(false, SysUser::getPhone, "133"))
                .where(o->o.sqlNativeSegment("FIND_IN_SET({0},({1}))",c->{
                    c.expression(SysUser::getId)
                            .expression(subQueryCreator.apply(o));
                }));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t WHERE t.`id` = ? AND FIND_IN_SET(t.`id`,(SELECT GROUP_CONCAT(t1.`title`) FROM `t_topic` t1 WHERE t1.`id` = t.`id` GROUP BY t1.`id`))", sql);
    }
    @Test
    public void query13x_3() {
        Queryable<SysUser> queryable = easyQuery.queryable(SysUser.class)
                .where(o -> o.eq(SysUser::getId, "123xxx").like(false, SysUser::getPhone, "133"))
                .where(o->o.sqlNativeSegment("FIND_IN_SET({0},({1}))",c->{
                    c.expression(SysUser::getId)
                            .expression(subQueryGenerator(o));
                }));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t WHERE t.`id` = ? AND FIND_IN_SET(t.`id`,(SELECT GROUP_CONCAT(t1.`title`) FROM `t_topic` t1 WHERE t1.`id` = t.`id` GROUP BY t1.`id`))", sql);
    }
    private Queryable<String> subQueryGenerator(EntitySQLTableOwner<SysUser> table){
        Queryable<String> groupConcatQuery = easyQuery.queryable(Topic.class)
                .where(o->o.eq(table,Topic::getId,SysUser::getId))
                .groupBy(o -> o.column(Topic::getId))
                .select(String.class, o -> o.sqlNativeSegment("GROUP_CONCAT({0})", c -> {
                    c.expression(Topic::getTitle);
                }));
        return groupConcatQuery;
    }

    @Test
    public void query14x() {
        try {

            Map<String, String> phone = null;
            Queryable<SysUser> queryable = easyQuery.queryable(SysUser.class)
                    .where(o -> o.eq(SysUser::getId, "123xxx")
                            .like(phone != null && phone.containsKey("phone"), SysUser::getPhone, phone.get("phone")));
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
        Queryable<SysUser> queryable = easyQuery.queryable(SysUser.class)
                .where(o -> o.eq(SysUser::getId, "123xxx"))
                .where(phone != null && phone.containsKey("phone"), o -> o.like(SysUser::getPhone, phone.get("phone")));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE `id` = ?", sql);
        SysUser sysUser = queryable.firstOrNull();
        Assert.assertNull(sysUser);

    }

    @Test
    public void query26() {

//        List<TopicY> list = easyQuery.queryable(TopicY.class)
//                .include(t->t.many(TopicY::getTopicxList))
//                .toList();
        List<TopicY> list1 = easyQuery.queryable(TopicY.class)
                .include(t -> t.one(TopicY::getTopic))
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
            String sql = easyQuery.queryable(BlogEntity.class)
                    .orderByObject(blogSortRequest)
                    .select(o->o.column(BlogEntity::getId).column(BlogEntity::getTitle)
                            .column(BlogEntity::getContent))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`title`,`content` FROM `t_blog` WHERE `deleted` = ? ORDER BY `title` ASC",sql);
        }
        {

            BlogSortRequest blogSortRequest = new BlogSortRequest();
            blogSortRequest.setSort("title");
            blogSortRequest.setAsc(false);
            String sql = easyQuery.queryable(BlogEntity.class)
                    .orderByObject(blogSortRequest)
                    .select(o->o.column(BlogEntity::getId).column(BlogEntity::getTitle)
                            .column(BlogEntity::getContent))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`title`,`content` FROM `t_blog` WHERE `deleted` = ? ORDER BY `title` DESC",sql);
        }
        {

            BlogSortRequest blogSortRequest = new BlogSortRequest();
            blogSortRequest.setSort("title");
            String sql = easyQuery.queryable(BlogEntity.class)
                    .orderByObject(blogSortRequest)
                    .select(o->o.column(BlogEntity::getId).column(BlogEntity::getTitle)
                            .column(BlogEntity::getContent))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`title`,`content` FROM `t_blog` WHERE `deleted` = ?",sql);
        }
        {

            BlogSortRequest blogSortRequest = new BlogSortRequest();
            blogSortRequest.setAsc(true);
            String sql = easyQuery.queryable(BlogEntity.class)
                    .orderByObject(blogSortRequest)
                    .select(o->o.column(BlogEntity::getId).column(BlogEntity::getTitle)
                            .column(BlogEntity::getContent))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`title`,`content` FROM `t_blog` WHERE `deleted` = ?",sql);
        }
    }

    @Test
    public void dynamicSort2(){

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
            String sql = easyQuery.queryable(BlogEntity.class)
                    .orderByObject(blogSortRequest)
                    .select(o->o.column(BlogEntity::getId).column(BlogEntity::getTitle)
                            .column(BlogEntity::getContent))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`title`,`content` FROM `t_blog` WHERE `deleted` = ? ORDER BY `title` ASC,`star` DESC",sql);
        }
    }
    @Test
    public void dynamicSort3(){

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
            String sql = easyQuery.queryable(BlogEntity.class)
                    .innerJoin(Topic.class,(t, t1)->t.eq(t1,BlogEntity::getId, Topic::getId))
                    .orderByObject(blogSortRequest)
                    .select(o->o.column(BlogEntity::getId).column(BlogEntity::getTitle)
                            .column(BlogEntity::getContent))
                    .toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`title`,t.`content` FROM `t_blog` t INNER JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ? ORDER BY t.`title` ASC,t1.`create_time` DESC",sql);
        }
    }
    @Test
    public void dynamicSort4(){

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
            String sql = easyQuery.queryable(BlogEntity.class)
                    .innerJoin(Topic.class,(t, t1)->t.eq(t1,BlogEntity::getId, Topic::getId))
                    .orderByObject(blogSortRequest)
                    .select(o->o.columns(Arrays.asList(BlogEntity::getId,BlogEntity::getTitle,BlogEntity::getContent)))
                    .toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`title`,t.`content` FROM `t_blog` t INNER JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ? ORDER BY t.`title` ASC,t1.`create_time` DESC",sql);
        }
    }
    @Test
    public void queryColumns1(){
        String toSql1 = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle).then(t).eq(Topic::getId, "3"))
                .select(BlogEntity.class, (t, t1) -> t1
                        .columns(Arrays.asList(BlogEntity::getId,BlogEntity::getCreateTime,BlogEntity::getUpdateTime,BlogEntity::getCreateBy))
                        .then(t)
                        .columns(Arrays.asList(Topic::getTitle,Topic::getStars))
                ).toSQL();
        Assert.assertEquals("SELECT t1.`id`,t1.`create_time`,t1.`update_time`,t1.`create_by`,t.`title`,t.`stars` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL AND t.`id` = ?",
                toSql1);
    }


    @Test
    public void queryMultiFrom1(){
        String sql = easyQueryClient
                .queryable(Topic.class)
                .from(Topic.class)
                .whereById(true, "1")
                .where((a, b) -> a.eq("id", "id"))
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t , `t_topic` t1 WHERE t.`id` = ? AND t.`id` = ?",sql);
    }

}
