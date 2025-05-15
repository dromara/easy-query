package com.easy.query.test;

import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.dynamic.sort.ObjectSortBuilder;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.executor.internal.reader.BeanDataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.DataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.EmptyDataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.PropertyDataReader;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.exception.EasyQueryPropertyNotFoundException;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.expression.builder.core.AnyValueFilter;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.extension.client.SQLClientFunc;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.test.common.MyPager;
import com.easy.query.test.common.PageResult;
import com.easy.query.test.dto.BlogQuery1Request;
import com.easy.query.test.dto.BlogQuery2Request;
import com.easy.query.test.dto.TopicTypeVO;
import com.easy.query.test.dto.UserBookEncryptVO;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUserEncrypt;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicGenericKey;
import com.easy.query.test.entity.UserBookEncrypt;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.entity.solon.EqUser;
import lombok.var;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create time 2023/6/8 21:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest3 extends BaseTest {
    @Test
    public void query124() {
        String toSql = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(o -> o.id().eq("3"))
                .limit(1, 2)
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` = ? LIMIT 2 OFFSET 1", toSql);
    }

    @Test
    public void query124_5() {
        String toSql = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(o -> o.id().eq(""))
                .filterConfigure(AnyValueFilter.DEFAULT)
                .limit(1, 2)
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` LIMIT 2 OFFSET 1", toSql);
    }

    @Test
    public void query124_4() {
        String toSql = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3, t4) -> t.id().eq(t3.id()))
                .leftJoinMerge(BlogEntity.class, c -> c.t1.id().eq(c.t5.id()))
                .where(o -> o.id().eq("3"))
                .limit(1, 2)
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` LEFT JOIN `t_blog` t4 ON t4.`deleted` = ? AND t.`id` = t3.`id` LEFT JOIN `t_blog` t5 ON t5.`deleted` = ? AND t.`id` = t4.`id` WHERE t.`id` = ? LIMIT 2 OFFSET 1", toSql);
    }


    @Test
    public void query124_1() {
        String toSql = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id())).asTableLink(join -> join + " lateral")
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .where(o -> o.id().eq("3"))
                .limit(1, 2)
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN lateral `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` = ? LIMIT 2 OFFSET 1", toSql);
    }

    @Test
    public void query124_2() {
        String toSql = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoinMerge(BlogEntity.class, o -> o.t1.id().eq(o.t4.id()))
                .whereMerge(o -> o.t1.id().eq("3"))
                .limit(1, 2)
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` = ? LIMIT 2 OFFSET 1", toSql);
    }

    @Test
    public void query125() {
        String toSql = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .innerJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .where(o -> o.id().eq("3"))
                .select(BlogEntity.class, (t, t1, t2, t3) -> Select.of(
                        t.id(),
                        t1.title(),
                        t2.star()
                ))
//                .select(BlogEntity.class, (t, t1, t2, t3) -> t.column(Topic::getId)
//                        .then(t1)
//                        .column(BlogEntity::getTitle)
//                        .then(t2)
//                        .column(BlogEntity::getStar))
                .limit(1, 2)
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t1.`title`,t2.`star` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` INNER JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` INNER JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` = ? LIMIT 2 OFFSET 1", toSql);
    }

    @Test
    public void query126() {
        {

            List<BlogEntity> list = easyEntityQuery
                    .queryable(Topic.class)
//                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()).then(t1).eq(BlogEntity::getId,3))
                    .leftJoin(BlogEntity.class, (t, t1) -> {
                        t.id().eq(t1.id());
                        t1.id().eq("3");
                    })
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .rightJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().eq("3"))
                    .select(BlogEntity.class, (t, t1, t2, t3) -> Select.of(
                            t.id(),
                            t1.title(),
                            t2.star()
                    ))
//                .select(BlogEntit
                    .limit(1, 2)
                    .toList();
            Assert.assertEquals(0, list.size());
        }
        {

            List<BlogEntity> list = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().eq("3"))
                    .select(BlogEntity.class, (t, t1, t2, t3) -> Select.of(
                            t.id(),
                            t1.title(),
                            t2.star()
                    ))
//                .select(BlogEntit
                    .limit(1)
                    .toList();
            Assert.assertEquals(1, list.size());
        }
    }

    @Test
    public void query112() {
        BigDecimal bigDecimal = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .where(o -> o.id().eq("3"))
                .sumOrDefault((t, t1, t2, t3) -> t1.score(), BigDecimal.ZERO);
        Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
    }

    @Test
    public void query113() {
        BigDecimal bigDecimal = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .where(o -> o.id().eq("3x"))
                .sumOrNull((t, t1, t2, t3) -> t1.score());
        Assert.assertTrue(bigDecimal == null);
    }

    @Test
    public void query114() {
        BigDecimal bigDecimal = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .where(o -> o.id().eq("3"))
                .sumBigDecimalOrNull((t, t1, t2, t3) -> t3.score());
        Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
    }

    @Test
    public void query115() {
        BigDecimal bigDecimal = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .where(o -> o.id().eq("3x"))
                .sumBigDecimalOrDefault((t, t1, t2, t3) -> t3.score(), null);
        Assert.assertTrue(bigDecimal == null);
        BigDecimal bigDecimal1 = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .where(o -> o.id().eq("3x"))
                .sumBigDecimalOrDefault((t, t1, t2, t3) -> t3.score(), BigDecimal.ZERO);
        Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
    }

    @Test
    public void query116() {
        BigDecimal bigDecimal = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .where(o -> o.id().eq("3x"))
                .maxOrNull((t, t1, t2, t3) -> t3.score());
        Assert.assertTrue(bigDecimal == null);
        BigDecimal bigDecimal1 = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .where(o -> o.id().eq("3x"))
                .maxOrDefault((t, t1, t2, t3) -> t1.score(), BigDecimal.ZERO);
        Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
    }

    @Test
    public void query117() {
        BigDecimal bigDecimal = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .innerJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .where(o -> o.id().in(Arrays.asList("3x", "3")))
                .maxOrNull((t, t1, t2, t3) -> t3.score());
        Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
    }

    @Test
    public void query118() {
        BigDecimal bigDecimal = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .rightJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .where(o -> o.id().eq("3x"))
                .minOrNull((t, t1, t2, t3) -> t3.score());
        Assert.assertTrue(bigDecimal == null);
        BigDecimal bigDecimal1 = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .rightJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .where(o -> o.id().eq("3x"))
                .minOrDefault((t, t1, t2, t3) -> t3.score(), BigDecimal.ZERO);
        Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
    }

    @Test
    public void query119() {
        BigDecimal bigDecimal = easyEntityQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                .where(o -> o.id().in(Arrays.asList("3x", "3")))
                .minOrNull((t, t1, t2, t3) -> t3.score());
        Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
    }

    @Test
    public void query120() {
        {
            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .avgBigDecimalOrNull((t, t1, t2, t3) -> t1.score());
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {
            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "2x", "5x")))
                    .avgBigDecimalOrNull((t, t1, t2, t3) -> t3.score());
            Assert.assertTrue(bigDecimal == null);
        }
        {
            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "2x", "5")))
                    .avgBigDecimalOrDefault((t, t1, t2, t3) -> t3.score(), BigDecimal.ZERO);
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {
            Double bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "2x", "5")))
                    .avgOrDefault((t, t1, t2, t3) -> t3.star(), null);
            Assert.assertTrue(5d == bigDecimal);
        }
        {
            BigDecimal bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "2x", "5")))
                    .avgOrDefault((t, t1, t2, t3) -> t3.star(), null, BigDecimal.class);
            Assert.assertTrue(new BigDecimal("5").compareTo(bigDecimal) == 0);
        }
        {
            Float bigDecimal = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().in(Arrays.asList("3x", "2x", "5")))
                    .avgOrDefault((t, t1, t2, t3) -> t1.star(), null, Float.class);
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
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .orderBy((t, t1, t2, t3) -> t1.order().asc()).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) ORDER BY t1.`order` ASC", sql);
        }
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .orderBy((t, t1, t2, t3) -> {
                        t1.order().asc();
                        t2.score().asc();
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) ORDER BY t1.`order` ASC,t2.`score` ASC", sql);
        }
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .orderBy(false, (t, t1, t2, t3) -> t1.order().asc())
                    .orderBy(true, (t, t1, t2, t3) -> t3.id().asc()).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` INNER JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) ORDER BY t3.`id` ASC", sql);
        }
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .rightJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .orderBy((t, t1, t2, t3) -> t3.order().desc()).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` RIGHT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) ORDER BY t3.`order` DESC", sql);
        }
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .orderBy(false, (t, t1, t2, t3) -> t3.order().desc())
                    .orderBy(true, (t, t1, t2, t3) -> {
                        t3.id().desc();
                        t3.order().desc();
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) ORDER BY t3.`id` DESC,t3.`order` DESC", sql);
        }
    }

    @Test
    public void query122() {
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1, t2, t3) -> GroupKeys.of(t.id()))
                    .orderBy(group -> group.groupTable().t2.order().asc()).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t1.`order` ASC", sql);
        }
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1, t2, t3) -> GroupKeys.of(t3.id()))
                    .orderBy(group -> group.groupTable().t4.order().asc()).toSQL();
            Assert.assertEquals("SELECT t3.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` INNER JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) GROUP BY t3.`id` ORDER BY t3.`order` ASC", sql);
        }
    }

    @Test
    public void query123() {
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .rightJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1, t2, t3) -> GroupKeys.of(t.id()))
                    .distinct()
                    .orderBy(g -> g.groupTable().t4.order().asc())
                    .limit(10).toSQL();
            Assert.assertEquals("SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` INNER JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` RIGHT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t3.`order` ASC LIMIT 10", sql);
        }
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1, t2, t3) -> GroupKeys.of(t.id()))
                    .orderBy(group -> group.groupTable().t4.order().asc())
                    .distinct()
                    .select(g -> new TopicProxy().id().set(g.key1()))
                    .where(o -> o.id().eq("x"))
                    .selectColumn(o -> o.id().count()).toSQL();
            Assert.assertEquals("SELECT COUNT(t4.`id`) AS `id` FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` INNER JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t3.`order` ASC) t4 WHERE t4.`id` = ?", sql);
        }
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1, t2, t3) -> GroupKeys.of(t.id()))
                    .orderBy(g -> g.groupTable().t4.order().asc())
                    .distinct()
                    .select(o -> new TopicProxy().id().set(o.key1()))
                    .select("count(1)").toSQL();
            Assert.assertEquals("SELECT count(1) FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t3.`order` ASC) t4", sql);
        }
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .rightJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1, t2, t3) -> GroupKeys.of(t.id()))
                    .orderBy(group -> group.groupTable().t1.stars().desc())
                    .distinct()
                    .select(Topic.class, o -> Select.of(o.key1()))
                    .select("count(1)").toSQL();
            Assert.assertEquals("SELECT count(1) FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` RIGHT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t.`stars` DESC) t4", sql);
        }
        {
            String sql = easyEntityQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.id().eq(t2.id()))
                    .rightJoin(BlogEntity.class, (t, t1, t2, t3) -> t.id().eq(t3.id()))
                    .where(o -> o.id().in(Arrays.asList("3", "2", "5")))
                    .groupBy((t, t1, t2, t3) -> GroupKeys.of(t.id()))
                    .orderBy(group -> group.groupTable().t1.stars().asc())
                    .distinct()
                    .select(Topic.class, o -> Select.of(o.key1()))
                    .select("count(1)").toSQL();
            Assert.assertEquals("SELECT count(1) FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` RIGHT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t.`stars` ASC) t4", sql);
        }
    }

    @Test
    public void testLambda3() {
        Topic topic = easyEntityQuery.queryable(Topic.class)
                .where(t -> {
                    t.or(() -> {
                        t.id().eq("3");
                        t.title().like("你好");
                    });
                })
                .firstOrNull();
        Assert.assertNotNull(topic);
    }

    @Test
    public void testProperty3() {
        Topic topic = easyQueryClient.queryable(Topic.class)
                .where(t -> t.eq("id", "3").or().like("title", "你好"))
                .firstOrNull();
        Assert.assertNotNull(topic);
    }

    @Test
    public void testProperty3_1() {
        SQLFunc sqlFunc = easyQueryClient.getRuntimeContext().fx();
        Topic topic = easyQueryClient.queryable(Topic.class)
                .where(t -> t.eq(sqlFunc.nullOrDefault("id", "1"), sqlFunc.nullOrDefault("stars", "3")).or().like("title", "你好"))
                .firstOrNull();
        Assert.assertNotNull(topic);
        String sql = easyQueryClient.queryable(Topic.class)
                .where(t -> t.eq(sqlFunc.nullOrDefault("id", "1"), sqlFunc.nullOrDefault("stars", "3")).or().like("title", "你好"))
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (IFNULL(`id`,?) = IFNULL(`stars`,?) OR `title` LIKE ?)", sql);

        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(t -> t.eq(sqlFunc.nullOrDefault("id", "1"), sqlFunc.nullOrDefault(x -> x.column("stars").column("id").value("3"))).or().like("title", "你好"))
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (IFNULL(`id`,?) = IFNULL(`stars`,`id`,?) OR `title` LIKE ?)", sql1);


        String sql2 = easyQueryClient.queryable(Topic.class)
                .where(t -> {
                    t.eq(
                            sqlFunc.nullOrDefault("id", "1"),
                            sqlFunc.nullOrDefault(x -> x.column("stars").column("id").value("3"))
                    );
                    t.or().like("title", "你好");
                })
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (IFNULL(`id`,?) = IFNULL(`stars`,`id`,?) OR `title` LIKE ?)", sql1);

    }

    @Test
    public void testSelfPredicate1() {
        {
            String sql = easyQueryClient.queryable(BlogEntity.class)
                    .where(t -> t.eq("score", "3").eq(t, "score", "order"))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` = `order`", sql);
        }
        {
            String sql = easyQueryClient.queryable(BlogEntity.class)
                    .where(t -> t.eq("score", "3").ne(t, "score", "order"))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` <> `order`", sql);
        }
        {
            String sql = easyQueryClient.queryable(BlogEntity.class)
                    .where(t -> t.eq("score", "3").gt(t, "score", "order"))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` > `order`", sql);
        }
        {
            String sql = easyQueryClient.queryable(BlogEntity.class)
                    .where(t -> t.eq("score", "3").ge(t, "score", "order"))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` >= `order`", sql);
        }
        {
            String sql = easyQueryClient.queryable(BlogEntity.class)
                    .where(t -> t.eq("score", "3").le(t, "score", "order"))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` <= `order`", sql);
        }
        {
            String sql = easyQueryClient.queryable(BlogEntity.class)
                    .where(t -> t.eq("score", "3").lt(t, "score", "order"))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` < `order`", sql);
        }
        {

            String sql1 = easyEntityQuery.queryable(BlogEntity.class)
                    .where(t_blog -> {
                        t_blog.score().eq(BigDecimal.valueOf(3));
                        t_blog.score().eq(t_blog.order());
                    })
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` = `order`", sql1);
        }
        {

            String sql1 = easyEntityQuery.queryable(BlogEntity.class)
                    .where(t_blog -> {
                        t_blog.score().eq(BigDecimal.valueOf(3));
                        t_blog.score().ne(t_blog.order());
                    })
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` <> `order`", sql1);
        }
        {

            String sql1 = easyEntityQuery.queryable(BlogEntity.class)
                    .where(t_blog -> {
                        t_blog.score().eq(BigDecimal.valueOf(3));
                        t_blog.score().le(t_blog.order());
                    })
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` <= `order`", sql1);
        }
        {

            String sql1 = easyEntityQuery.queryable(BlogEntity.class)
                    .where(t_blog -> {
                        t_blog.score().eq(BigDecimal.valueOf(3));
                        t_blog.score().lt(t_blog.order());
                    })
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` < `order`", sql1);
        }
        {

            String sql1 = easyEntityQuery.queryable(BlogEntity.class)
                    .where(t_blog -> {
                        t_blog.score().eq(BigDecimal.valueOf(3));
                        t_blog.score().gt(t_blog.order());
                    })
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` > `order`", sql1);
        }
        {

            String sql1 = easyEntityQuery.queryable(BlogEntity.class)
                    .where(t_blog -> {
                        t_blog.score().eq(BigDecimal.valueOf(3));
                        t_blog.score().ge(t_blog.order());
                    })
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` >= `order`", sql1);
        }
    }

    @Test
    public void testSample() {
        List<Topic> list = easyQueryClient.queryable(Topic.class)
                .where(o -> o.like("title", "someTitle"))
                .orderByAsc(o -> o.column("createTime").column("id"))
                .toList();
        Assert.assertEquals(0, list.size());
        List<Topic> list1 = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.title().like("someTitle"))
                .orderBy(t_topic -> {
                    t_topic.createTime().asc();
                    t_topic.id().asc();
                })
                .toList();
        Assert.assertEquals(0, list1.size());
    }

    @Test
    public void testCaseWhen() {

        Query<Topic> select = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.title().like("someTitle"))
                .orderBy(t_topic -> {
                    t_topic.createTime().asc();
                    t_topic.id().asc();
                })
                .select(Topic.class, t_topic -> Select.of(
                        t_topic.expression().caseWhen(() -> {
                                    t_topic.title().eq("123");
                                    t_topic.stars().ge(1);
                                }).then("first1")
                                .caseWhen(() -> {
                                    t_topic.title().eq("456");
                                }).then("first2")
                                .caseWhen(() -> {
                                    t_topic.title().eq("789");
                                }).then("first3").elseEnd("firstEnd").as(Topic::getTitle)
                ));
        ExpressionContext expressionContext = select.getSQLEntityExpressionBuilder().getExpressionContext();
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(expressionContext.getTableContext());
        String sql = select.toSQL(toSQLContext);
        Assert.assertEquals("SELECT (CASE WHEN t.`title` = ? AND t.`stars` >= ? THEN ? WHEN t.`title` = ? THEN ? WHEN t.`title` = ? THEN ? ELSE ? END) AS `title` FROM `t_topic` t WHERE t.`title` LIKE ? ORDER BY t.`create_time` ASC,t.`id` ASC", sql);
        Assert.assertEquals(9, toSQLContext.getParameters().size());
        Assert.assertEquals("123", toSQLContext.getParameters().get(0).getValue());
        Assert.assertEquals(1, toSQLContext.getParameters().get(1).getValue());
        Assert.assertEquals("first1", toSQLContext.getParameters().get(2).getValue());
        Assert.assertEquals("456", toSQLContext.getParameters().get(3).getValue());
        Assert.assertEquals("first2", toSQLContext.getParameters().get(4).getValue());
        Assert.assertEquals("789", toSQLContext.getParameters().get(5).getValue());
        Assert.assertEquals("first3", toSQLContext.getParameters().get(6).getValue());
        Assert.assertEquals("firstEnd", toSQLContext.getParameters().get(7).getValue());
        Assert.assertEquals("%someTitle%", toSQLContext.getParameters().get(8).getValue());
    }

    @Test
    public void propertyCaseWhen() {

        String sql = easyQueryClient.queryable(Topic.class)
                .where(t -> t.like("title", "someTitle"))
                .select(Topic.class, t -> t
                        .sqlSegmentAs(
                                SQLClientFunc.caseWhenBuilder(t)
                                        .caseWhen(f -> f.eq("title", "123"), "111")
                                        .caseWhen(f -> f.eq("title", "456"), "222")
                                        .elseEnd("222")
                                , "title")
                        .column("id")
                )
                .toSQL();
        Assert.assertEquals("SELECT (CASE WHEN t.`title` = ? THEN ? WHEN t.`title` = ? THEN ? ELSE ? END) AS `title`,t.`id` FROM `t_topic` t WHERE t.`title` LIKE ?", sql);
        List<Topic> list = easyQueryClient.queryable(Topic.class)
                .where(t -> t.like("title", "someTitle"))
                .select(Topic.class, t -> t
                        .sqlSegmentAs(
                                SQLClientFunc.caseWhenBuilder(t)
                                        .caseWhen(f -> f.eq("title", "123"), "111")
                                        .caseWhen(f -> f.eq("title", "456"), "222")
                                        .elseEnd("222")
                                , "title")
                        .column("id")
                ).toList();
        Assert.assertEquals(0, list.size());
    }


    @Test
    public void testAndOr1() {
        Topic topic = easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.id().eq("1");
                    t_topic.or(() -> {
                        t_topic.title().like("你好");
                        t_topic.title().eq("我是title");
                        t_topic.createTime().le(LocalDateTime.now());
                    });
                })
                .firstOrNull();
        Assert.assertNotNull(topic);
        String sql = easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.id().eq("1");
                    t_topic.or(() -> {
                        t_topic.title().like("你好");
                        t_topic.title().eq("我是title");
                        t_topic.createTime().le(LocalDateTime.now());
                    });
                })
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` = ? AND (`title` LIKE ? OR `title` = ? OR `create_time` <= ?)", sql);

        List<Topic> topic2 = easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.or(() -> {
                        t_topic.title().like("你好");
                        t_topic.title().eq("我是title");
                        t_topic.createTime().le(LocalDateTime.now());
                    });
                })
                .toList();
        Assert.assertNotNull(topic2);
        Assert.assertTrue(topic2.size() > 1);

        String sql2 = easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.or(() -> {
                        t_topic.title().like("你好");
                        t_topic.title().eq("我是title");
                        t_topic.createTime().le(LocalDateTime.now());
                    });
                })
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`title` LIKE ? OR `title` = ? OR `create_time` <= ?)", sql2);


        Topic topic3 = easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.or(() -> {
                        t_topic.id().eq("1");
                        t_topic.and(() -> {
                            t_topic.title().like("你好");
                            t_topic.title().eq("我是title");
                            t_topic.createTime().le(LocalDateTime.now());
                        });
                    });
                })
                .firstOrNull();
        Assert.assertNotNull(topic3);
        String sql3 = easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.or(() -> {
                        t_topic.id().eq("1");
                        t_topic.and(() -> {
                            t_topic.title().like("你好");
                            t_topic.title().eq("我是title");
                            t_topic.createTime().le(LocalDateTime.now());
                        });
                    });
                })
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` = ? OR (`title` LIKE ? AND `title` = ? AND `create_time` <= ?))", sql3);
    }

    @Test
    public void queryBasic1() {
        List<String> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().eq("1"))
                .select(String.class, o -> Select.of(o.id()))
                .toList();
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("1", list.get(0));
        String sql = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().eq("1"))
                .select(String.class, o -> Select.of(o.id()))
                .toSQL();
        Assert.assertEquals("SELECT t.`id` FROM `t_topic` t WHERE t.`id` = ?", sql);


        List<String> list1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1"))
                .select(String.class, o -> o.column("id"))
                .toList();
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals("1", list1.get(0));

        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1"))
                .select(String.class, o -> o.column("id")).toSQL();
        Assert.assertEquals("SELECT t.`id` FROM `t_topic` t WHERE t.`id` = ?", sql1);


    }

    @Test
    public void queryBasic2() {
        List<Integer> list = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().eq("1"))
                .select(Integer.class, o -> Select.of(o.stars()))
                .toList();
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(101, (int) list.get(0));
        String sql = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.id().eq("1"))
                .select(Integer.class, o -> Select.of(o.stars()))
                .toSQL();
        Assert.assertEquals("SELECT t.`stars` FROM `t_topic` t WHERE t.`id` = ?", sql);


        List<Integer> list1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1"))
                .select(Integer.class, o -> o.column("stars"))
                .toList();
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(101, (int) list1.get(0));

        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id", "1"))
                .select(Integer.class, o -> o.column("stars")).toSQL();
        Assert.assertEquals("SELECT t.`stars` FROM `t_topic` t WHERE t.`id` = ?", sql1);

    }

    @Test
    public void queryBasic3() {
        Class<Map<String, Object>> mapClass = EasyObjectUtil.typeCastNullable(Map.class);
        {

            List<Map<String, Object>> list = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq("1"))
                    .select(mapClass)
                    .toList();
            Assert.assertEquals(1, list.size());
            String sql = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq("1"))
                    .select(mapClass)
                    .toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` = ?", sql);
        }
        {

            List<Map<String, Object>> list = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq("1"))
                    .select(mapClass)
                    .toList();
            Assert.assertEquals(1, list.size());
            String sql = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq("1"))
                    .select(mapClass)
                    .toSQL();
            Assert.assertEquals("SELECT * FROM `t_topic` t WHERE t.`id` = ?", sql);
        }


        {
            List<Map<String, Object>> list1 = easyQueryClient.queryable(Topic.class)
                    .where(o -> o.eq("id", "1"))
                    .select(mapClass, o -> o.columnAll())
                    .toList();
            Assert.assertEquals(1, list1.size());

            String sql1 = easyQueryClient.queryable(Topic.class)
                    .where(o -> o.eq("id", "1"))
                    .select(mapClass, o -> o.columnAll()).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` = ?", sql1);
        }

        {
            List<Map<String, Object>> list1 = easyQueryClient.queryable(Topic.class)
                    .where(o -> o.eq("id", "1"))
                    .select(mapClass)
                    .toList();
            Assert.assertEquals(1, list1.size());

            String sql1 = easyQueryClient.queryable(Topic.class)
                    .where(o -> o.eq("id", "1"))
                    .select(mapClass).toSQL();
            Assert.assertEquals("SELECT * FROM `t_topic` t WHERE t.`id` = ?", sql1);
        }
    }


    @Test
    public void dynamicWhere() {
        {
            BlogQuery1Request query = new BlogQuery1Request();
            query.setOrder(BigDecimal.valueOf(1));
            query.setContent("标题");
            query.setPublishTimeBegin(LocalDateTime.now());
            query.setPublishTimeEnd(LocalDateTime.now());
            query.setStatusList(Arrays.asList(1, 2));

            Query<BlogEntity> queryable = easyEntityQuery.queryable(BlogEntity.class)
                    .where(t_blog -> {
                        //当query.getContext不为空是添加查询条件 content like query.getContext
                        t_blog.content().like(EasyStringUtil.isNotBlank(query.getContent()), query.getContent());
                        //当query.getOrder不为null是添加查询条件 content = query.getContext
                        t_blog.order().eq(query.getOrder() != null, query.getOrder());
                        //当query.getPublishTimeBegin()不为null添加左闭区间,右侧同理 publishTimeBegin <= publishTime <= publishTimeEnd
                        t_blog.publishTime().rangeClosed(query.getPublishTimeBegin() != null, query.getPublishTimeBegin(), query.getPublishTimeEnd() != null, query.getPublishTimeEnd());
                        //添加in条件
                        t_blog.status().in(EasyCollectionUtil.isNotEmpty(query.getStatusList()), query.getStatusList());
                    });

            String sql = queryable.cloneQueryable().toSQL();
            System.out.println(sql);
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `content` LIKE ? AND `order` = ? AND (`publish_time` >= ? AND `publish_time` <= ?) AND `status` IN (?,?)", sql);
            List<BlogEntity> list = queryable.cloneQueryable().toList();
            Assert.assertEquals(0, list.size());
        }
        {
            BlogQuery1Request query = new BlogQuery1Request();
            query.setContent("标题");
            query.setPublishTimeBegin(LocalDateTime.now());
            query.setPublishTimeEnd(LocalDateTime.now());
            query.setStatusList(Arrays.asList(1, 2));

            Query<BlogEntity> queryable = easyEntityQuery.queryable(BlogEntity.class).where(t_blog -> {

                //当query.getContext不为空是添加查询条件 content like query.getContext
                t_blog.content().like(EasyStringUtil.isNotBlank(query.getContent()), query.getContent());
                //当query.getOrder不为null是添加查询条件 content = query.getContext
                t_blog.order().eq(query.getOrder() != null, query.getOrder());
                //当query.getPublishTimeBegin()不为null添加左闭区间,右侧同理 publishTimeBegin <= publishTime <= publishTimeEnd
                t_blog.publishTime().rangeClosed(query.getPublishTimeBegin() != null, query.getPublishTimeBegin(), query.getPublishTimeEnd() != null, query.getPublishTimeEnd());
                //添加in条件
                t_blog.status().in(EasyCollectionUtil.isNotEmpty(query.getStatusList()), query.getStatusList());
            });

            String sql = queryable.cloneQueryable().toSQL();
            System.out.println(sql);
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `content` LIKE ? AND (`publish_time` >= ? AND `publish_time` <= ?) AND `status` IN (?,?)", sql);
            List<BlogEntity> list = queryable.cloneQueryable().toList();
            Assert.assertEquals(0, list.size());
        }
    }

    @Test
    public void dynamicWhere1() {
        {
            BlogQuery2Request query = new BlogQuery2Request();
            query.setOrder(BigDecimal.valueOf(1));
            query.setContent("标题");
            query.setPublishTimeBegin(LocalDateTime.now());
            query.setPublishTimeEnd(LocalDateTime.now());
            query.setStatusList(Arrays.asList(1, 2));

            Query<BlogEntity> queryable = easyEntityQuery.queryable(BlogEntity.class)
                    .whereObject(query);

            String sql = queryable.cloneQueryable().toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `content` LIKE ? AND `publish_time` >= ? AND `publish_time` <= ? AND `order` = ? AND `status` IN (?,?)", sql);
            List<BlogEntity> list = queryable.cloneQueryable().toList();
            Assert.assertEquals(0, list.size());
        }
        {
            BlogQuery2Request query = new BlogQuery2Request();
            query.setContent("标题");
            query.setPublishTimeEnd(LocalDateTime.now());
            query.setStatusList(Arrays.asList(1, 2));

            Query<BlogEntity> queryable = easyEntityQuery.queryable(BlogEntity.class)
                    .whereObject(query);

            String sql = queryable.cloneQueryable().toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `content` LIKE ? AND `publish_time` <= ? AND `status` IN (?,?)", sql);
            List<BlogEntity> list = queryable.cloneQueryable().toList();
            Assert.assertEquals(0, list.size());
        }
    }

    @Test
    public void query9() {
        try (JdbcStreamResult<BlogEntity> streamResult = easyEntityQuery.queryable(BlogEntity.class).where(o -> o.star().le(100)).orderBy(o -> o.createTime().asc()).toStreamResult(100)) {

            LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
            int i = 0;
            for (BlogEntity blog : streamResult.getStreamIterable()) {
                String indexStr = String.valueOf(i);
                Assert.assertEquals(indexStr, blog.getId());
                Assert.assertEquals(indexStr, blog.getCreateBy());
                Assert.assertEquals(begin.plusDays(i), blog.getCreateTime());
                Assert.assertEquals(indexStr, blog.getUpdateBy());
                Assert.assertEquals(begin.plusDays(i), blog.getUpdateTime());
                Assert.assertEquals("title" + indexStr, blog.getTitle());
//            Assert.assertEquals("content" + indexStr, blog.getContent());
                Assert.assertEquals("http://blog.easy-query.com/" + indexStr, blog.getUrl());
                Assert.assertEquals(i, (int) blog.getStar());
                Assert.assertEquals(0, new BigDecimal("1.2").compareTo(blog.getScore()));
                Assert.assertEquals(i % 3 == 0 ? 0 : 1, (int) blog.getStatus());
                Assert.assertEquals(0, new BigDecimal("1.2").multiply(BigDecimal.valueOf(i)).compareTo(blog.getOrder()));
                Assert.assertEquals(i % 2 == 0, blog.getIsTop());
                Assert.assertEquals(i % 2 == 0, blog.getTop());
                Assert.assertEquals(false, blog.getDeleted());
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void query9_x() {
        try (JdbcStreamResult<BlogEntity> streamResult = easyEntityQuery.queryable(BlogEntity.class).where(o -> o.star().le(100)).orderBy(o -> o.createTime().asc()).toStreamResult(x -> {
            x.setFetchSize(1000);
//            StatementImpl x1 = (StatementImpl) x;
//            x1.enableStreamingResults();
        })) {

            LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
            int i = 0;
            for (BlogEntity blog : streamResult.getStreamIterable()) {
                String indexStr = String.valueOf(i);
                Assert.assertEquals(indexStr, blog.getId());
                Assert.assertEquals(indexStr, blog.getCreateBy());
                Assert.assertEquals(begin.plusDays(i), blog.getCreateTime());
                Assert.assertEquals(indexStr, blog.getUpdateBy());
                Assert.assertEquals(begin.plusDays(i), blog.getUpdateTime());
                Assert.assertEquals("title" + indexStr, blog.getTitle());
//            Assert.assertEquals("content" + indexStr, blog.getContent());
                Assert.assertEquals("http://blog.easy-query.com/" + indexStr, blog.getUrl());
                Assert.assertEquals(i, (int) blog.getStar());
                Assert.assertEquals(0, new BigDecimal("1.2").compareTo(blog.getScore()));
                Assert.assertEquals(i % 3 == 0 ? 0 : 1, (int) blog.getStatus());
                Assert.assertEquals(0, new BigDecimal("1.2").multiply(BigDecimal.valueOf(i)).compareTo(blog.getOrder()));
                Assert.assertEquals(i % 2 == 0, blog.getIsTop());
                Assert.assertEquals(i % 2 == 0, blog.getTop());
                Assert.assertEquals(false, blog.getDeleted());
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void process(List<BlogEntity> blogs) {

    }

    @Test
    public void query9_1() {

        try (JdbcStreamResult<BlogEntity> streamResult = easyEntityQuery.queryable(BlogEntity.class).where(t_blog -> t_blog.star().le(100))
                .orderBy(t_blog -> t_blog.createTime().asc()).toStreamResult(100)) {
//            StreamIterable<BlogEntity> streamIterable = streamResult.getStreamIterable();
//            Iterator<BlogEntity> iterator = streamIterable.iterator();
//            int fetchSize=10;
//            ArrayList<BlogEntity> blogEntities = new ArrayList<>(fetchSize);
//            while(iterator.hasNext()){
//                fetchSize--;
//                blogEntities.add(iterator.next());
//                if(fetchSize==0){
//                    process(blogEntities);
//                    blogEntities.clear();
//                    fetchSize=10;
//                }
//            }

            LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
            int i = 0;
            for (BlogEntity blog : streamResult.getStreamIterable()) {
                String indexStr = String.valueOf(i);
                Assert.assertEquals(indexStr, blog.getId());
                Assert.assertEquals(indexStr, blog.getCreateBy());
                Assert.assertEquals(begin.plusDays(i), blog.getCreateTime());
                Assert.assertEquals(indexStr, blog.getUpdateBy());
                Assert.assertEquals(begin.plusDays(i), blog.getUpdateTime());
                Assert.assertEquals("title" + indexStr, blog.getTitle());
//            Assert.assertEquals("content" + indexStr, blog.getContent());
                Assert.assertEquals("http://blog.easy-query.com/" + indexStr, blog.getUrl());
                Assert.assertEquals(i, (int) blog.getStar());
                Assert.assertEquals(0, new BigDecimal("1.2").compareTo(blog.getScore()));
                Assert.assertEquals(i % 3 == 0 ? 0 : 1, (int) blog.getStatus());
                Assert.assertEquals(0, new BigDecimal("1.2").multiply(BigDecimal.valueOf(i)).compareTo(blog.getOrder()));
                Assert.assertEquals(i % 2 == 0, blog.getIsTop());
                Assert.assertEquals(i % 2 == 0, blog.getTop());
                Assert.assertEquals(false, blog.getDeleted());
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void queryToList1() {
        List<Topic> list = easyEntityQuery.queryable(Topic.class).toList();
    }

    @Test
    public void userBookTest() {

        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(UserBookEncrypt.class));
        codeFirstCommand.executeWithTransaction(s -> s.commit());

        easyEntityQuery.deletable(SysUserEncrypt.class)
                .whereByIds(Arrays.asList("1", "2"))
                .disableLogicDelete().executeRows();
        easyEntityQuery.deletable(UserBookEncrypt.class)
                .whereByIds(Arrays.asList("1", "2", "3", "4"))
                .disableLogicDelete().executeRows();
        {

            SysUserEncrypt sysUser = new SysUserEncrypt();
            sysUser.setId("1");
            sysUser.setName("用户1");
            sysUser.setPhone("12345678901");
            sysUser.setAddress("浙江省绍兴市越城区城市广场1234号");
            sysUser.setCreateTime(LocalDateTime.now());
            ArrayList<UserBookEncrypt> userBooks = new ArrayList<>();
            UserBookEncrypt userBook = new UserBookEncrypt();
            userBook.setId("1");
            userBook.setUserId("1");
            userBook.setName("语文");
            userBooks.add(userBook);
            UserBookEncrypt userBook1 = new UserBookEncrypt();
            userBook1.setId("2");
            userBook1.setUserId("1");
            userBook1.setName("数学");
            userBooks.add(userBook1);
            easyEntityQuery.insertable(sysUser).executeRows();
            easyEntityQuery.insertable(userBooks).executeRows();
        }
        {

            SysUserEncrypt sysUser = new SysUserEncrypt();
            sysUser.setId("2");
            sysUser.setName("用户2");
            sysUser.setPhone("19012345678");
            sysUser.setAddress("浙江省杭州市上城区武林广场1234号");
            sysUser.setCreateTime(LocalDateTime.now());
            ArrayList<UserBookEncrypt> userBooks = new ArrayList<>();
            UserBookEncrypt userBook = new UserBookEncrypt();
            userBook.setId("3");
            userBook.setUserId("2");
            userBook.setName("语文");
            userBooks.add(userBook);
            UserBookEncrypt userBook1 = new UserBookEncrypt();
            userBook1.setId("4");
            userBook1.setUserId("2");
            userBook1.setName("英语");
            userBooks.add(userBook1);
            easyEntityQuery.insertable(sysUser).executeRows();
            easyEntityQuery.insertable(userBooks).executeRows();
        }


        List<UserBookEncryptVO> userBooks = easyEntityQuery.queryable(UserBookEncrypt.class)
                .leftJoin(SysUserEncrypt.class, (t, t1) -> t.userId().eq(t1.id()))
                .where((t, t1) -> t1.Address().like("越城区"))
                .select(UserBookEncryptVO.class, (t, t1) -> Select.of(
                        t.FETCHER.allFields(),
                        t1.name().as(UserBookEncryptVO::getUserName),
                        t1.phone().as(UserBookEncryptVO::getUserPhone),
                        t1.Address().as(UserBookEncryptVO::getUserAddress)
                ))
                .toList();

        for (UserBookEncryptVO userBook : userBooks) {
            Assert.assertEquals("12345678901", userBook.getUserPhone());
            Assert.assertEquals("浙江省绍兴市越城区城市广场1234号", userBook.getUserAddress());
        }
        easyEntityQuery.deletable(SysUserEncrypt.class)
                .whereByIds(Arrays.asList("1", "2"))
                .disableLogicDelete().executeRows();
        easyEntityQuery.deletable(UserBookEncrypt.class)
                .whereByIds(Arrays.asList("1", "2", "3", "4"))
                .disableLogicDelete().executeRows();
    }

    @Test
    public void orTest() {
        String sql = easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.and(() -> {
                        t_topic.or(() -> {
                            t_topic.title().like("123");
                            t_topic.id().like("123");
                        });
                    });
                }).toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic`", sql);
    }

    @Test
    public void orTest1() {
        String sql = easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.createTime().eq(LocalDateTime.now());
                    t_topic.or(() -> {
                        t_topic.title().like("123");
                        t_topic.id().like("123");
                        t_topic.id().like("123");
                    });
                })
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `create_time` = ?", sql);
    }

    @Test
    public void orTest2() {
        String sql = easyEntityQuery.queryable(Topic.class).where(t_topic -> {
            t_topic.createTime().eq(LocalDateTime.now());
            t_topic.or(() -> {
                t_topic.title().like(false, "123");
                t_topic.id().like(false, "123");
                t_topic.id().like(false, "123");
            });
        }).toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `create_time` = ?", sql);
    }

    @Test
    public void orTest3() {
        String sql = easyEntityQuery.queryable(Topic.class).where(t_topic -> {
            t_topic.createTime().eq(false, LocalDateTime.now());
            t_topic.or(() -> {
                t_topic.title().like(false, "123");
                t_topic.id().like(false, "123");
                t_topic.id().like(false, "123");
            });
        }).toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic`", sql);
    }

    @Test
    public void orTest4() {
        String sql = easyEntityQuery.queryable(BlogEntity.class).where(t_topic -> {
            t_topic.createTime().eq(false, LocalDateTime.now());
            t_topic.or(() -> {
                t_topic.title().like(false, "123");
                t_topic.id().like(false, "123");
                t_topic.id().like(false, "123");
            });
        }).toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ?", sql);
    }

    @Test
    public void orTest5() {
        EasyPageResult<BlogEntity> pageResult = easyEntityQuery.queryable(BlogEntity.class).where(t_topic -> {
            t_topic.createTime().eq(false, LocalDateTime.now());
            t_topic.or(() -> {
                t_topic.title().like(false, "123");
                t_topic.id().like(false, "123");
                t_topic.id().like(false, "123");
            });
        }).toPageResult(1, 10);
        Assert.assertEquals(10, pageResult.getData().size());
    }

    @Test
    public void extendsUserTest() throws NoSuchFieldException, IllegalAccessException {
        easyEntityQuery.queryable(EqUser.class)
                .where(e -> e._accc())
                .select(e -> e.FETCHER._accc());
        EntityMetadataManager entityMetadataManager = easyEntityQuery.getRuntimeContext().getEntityMetadataManager();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(EqUser.class);
        DataReader dataReader = entityMetadata.getDataReader();
        Assert.assertNotNull(dataReader);
        Class<? extends DataReader> aClass = dataReader.getClass();
        for (int i = 0; i < 20; i++) {
            Field previousDataReader = aClass.getDeclaredField("previousDataReader");
            Field nextDataReader = aClass.getDeclaredField("nextDataReader");
            previousDataReader.setAccessible(true);
            nextDataReader.setAccessible(true);
            Object previous = previousDataReader.get(dataReader);
            Object next = nextDataReader.get(dataReader);
            dataReader = (DataReader) previous;
            if (i < 19) {
                Assert.assertTrue(previous instanceof BeanDataReader);
                Assert.assertTrue(next instanceof PropertyDataReader);
                aClass = dataReader.getClass();
            } else {
                Assert.assertTrue(previous instanceof EmptyDataReader);
            }

        }
        Collection<String> keyProperties = entityMetadata.getKeyProperties();
        Assert.assertEquals(1, keyProperties.size());
        String first = EasyCollectionUtil.first(keyProperties);
        Assert.assertEquals("id", first);

    }


    @Test
    public void testSelectAs1() {
        String sql = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq("123"))
                .select(Topic.class, o -> o.expression().sqlSegment("100 - {0}", c -> {
                    c.expression(o.id());
                }).as(Topic::getStars))
                .toSQL();
        Assert.assertEquals("SELECT 100 - t.`id` AS `stars` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ?", sql);
    }

    @Test
    public void testOrder1() {
        String sql = easyEntityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq("123"))
                .orderBy(t_blog -> {
                    t_blog.id().asc();
                    t_blog.expression().sql("user_name {0}", c -> {
                        c.format(1 == 1 ? "ASC" : "DESC");
                    });
                })
                .toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` = ? ORDER BY `id` ASC,user_name ASC", sql);
    }

    public static class UISort implements ObjectSort {

        private final Map<String, Boolean> sort;

        public UISort(Map<String, Boolean> sort) {

            this.sort = sort;
        }

        @Override
        public void configure(ObjectSortBuilder builder) {
            for (Map.Entry<String, Boolean> s : sort.entrySet()) {

                builder.orderBy(s.getKey(), s.getValue());
            }
        }
    }

    @Test
    public void orderTest1() {
        HashMap<String, Boolean> id = new HashMap<String, Boolean>();
        id.put("id", true);
        id.put("title", false);
        String sql = easyEntityQuery.queryable(BlogEntity.class)
                .orderByObject(new UISort(id))
                .toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? ORDER BY `id` ASC,`title` DESC", sql);
    }

    @Test
    public void orderTest2() {
        HashMap<String, Boolean> id = new HashMap<String, Boolean>();
        id.put("id1", true);
        id.put("title", false);
        try {

            String sql = easyEntityQuery.queryable(BlogEntity.class)
                    .orderByObject(new UISort(id))
                    .toSQL();
        } catch (EasyQueryPropertyNotFoundException exception) {
            Assert.assertEquals(BlogEntity.class, exception.getEntityClass());
            Assert.assertEquals("id1", exception.getPropertyName());
            Assert.assertEquals("BlogEntity not found property:[id1] mapping column name, please confirm that the field exists in the Java bean. if you want to use a non-standard Java bean, please set [propertyMode] to [same_as_entity]", exception.getMessage());
        }
    }

    @Test
    public void testGenericKey() {

        String sql = easyEntityQuery
                .queryable(TopicGenericKey.class)
                .limit(1).toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` LIMIT 1", sql);
    }

    @Test
    public void testGenericKey1() {
        List<TopicGenericKey> list = easyEntityQuery
                .queryable(TopicGenericKey.class)
                .whereById("1")
                .limit(1).toList();
        Assert.assertEquals(1, list.size());
        TopicGenericKey topicGenericKey = list.get(0);
        Assert.assertEquals("1", topicGenericKey.getId());
    }

    @Test
    public void testGenericKey2() {
        PageResult<TopicGenericKey> pageResult = easyEntityQuery
                .queryable(TopicGenericKey.class)
                .whereById("1")
                .toPageResult(new MyPager<>(1, 2));

        var pageResult1 = easyEntityQuery
                .queryable(TopicGenericKey.class)
                .whereById("1")
                .select(TopicTypeVO.class)
                .toPageResult(new MyPager<>(1, 2));
        List<TopicTypeVO> list = pageResult1.getList();

        Assert.assertEquals(1, pageResult.getTotalCount());
        Assert.assertEquals("1", pageResult.getList().get(0).getId());
    }
//    @Test
//    public void testGenericKey2_1() {
//        PageResult<TopicGenericKey> pageResult = easyEntityQuery
//                .queryable(TopicGenericKey.class)
//                .whereById("1" )
//                .toPageResult(new MyLimitPager<>(1, 20));
//
//       var pageResult1 = easyEntityQuery
//                .queryable(TopicGenericKey.class)
//                .whereById("1" )
//                .select(TopicTypeVO.class)
//                .toPageResult(new MyLimitPager<>(1, -1));
//
//        List<TopicTypeVO> list = pageResult1.getList();
//
//        Assert.assertEquals(1, pageResult.getTotalCount());
//        Assert.assertEquals("1" , pageResult.getList().get(0).getId());
//    }

    @Test
    public void nativePage1() {

        EasyPageResult<Map> pageResult = easyQueryClient.queryable("select * from t_topic", Map.class)
                .toPageResult(1, 20);
        Assert.assertEquals(20, pageResult.getData().size());
        EasyPageResult<Map> pageResult1 = easyQueryClient.queryable("select * from t_topic", Map.class)
                .toPageResult(2, 20);
        Assert.assertEquals(20, pageResult1.getData().size());
    }

    @Test
    public void nativePage2() {
        try {
            long count = easyQueryClient.queryable("select * from t_topicxx", Map.class).count();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause = (EasyQuerySQLStatementException) ex1.getCause();
            Assert.assertEquals("SELECT COUNT(*) FROM (select * from t_topicxx) t", cause.getSQL());
        }
        try {
            List<Map> list = easyQueryClient.queryable("select * from t_topicxx", Map.class).limit(1, 10).toList();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause = (EasyQuerySQLStatementException) ex1.getCause();
            Assert.assertEquals("SELECT * FROM (select * from t_topicxx) t LIMIT 10 OFFSET 1", cause.getSQL());
        }
        try {
            List<Map> list = easyQueryClient.queryable("select * from t_topicxx", Map.class).limit(2, 10).toList();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause = (EasyQuerySQLStatementException) ex1.getCause();
            Assert.assertEquals("SELECT * FROM (select * from t_topicxx) t LIMIT 10 OFFSET 2", cause.getSQL());
        }

    }

    @Test
    public void nativePage3() {

        EasyPageResult<Map> pageResult = easyQueryClient.queryable("select * from t_topic where id = ?", Map.class, Arrays.asList("1"))
                .toPageResult(1, 20);
        Assert.assertEquals(1, pageResult.getData().size());
        Assert.assertEquals(1, pageResult.getTotal());
    }

    @Test
    public void nativePage4() {

        EasyPageResult<BlogEntity> pageResult = easyEntityQuery.queryable("select * from t_blog where id = ?", BlogEntity.class, Arrays.asList("1"))
                .where(o -> o.content().like("-"))
                .toPageResult(1, 20);
        Assert.assertEquals(1, pageResult.getData().size());
        Assert.assertEquals(1, pageResult.getTotal());
    }

    @Test
    public void nativePage5() {

        EasyPageResult<BlogEntity> pageResult = easyEntityQuery.queryable("select * from t_blog where id = ?", BlogEntity.class, Arrays.asList("1"))
                .where(t_blog -> {
                    t_blog.content().like("-");
                })
                .select(BlogEntity.class, t_blog -> Select.of(
                        t_blog.id(),
                        t_blog.expression().sqlSegment("CONCAT({0},{1},{2})", c -> {
                            c.value("MySQL")
                                    .value("5.7-").expression(t_blog.id());
                        }).as(BlogEntity::getContent)
                ))
                .toPageResult(1, 20);
        Assert.assertEquals(1, pageResult.getData().size());
        Assert.assertEquals(1, pageResult.getTotal());
        BlogEntity blogEntity = pageResult.getData().get(0);
        Assert.assertEquals("1", blogEntity.getId());
        Assert.assertEquals("MySQL5.7-1", blogEntity.getContent());
    }

    @Test
    public void nativePage6() {

        EasyPageResult<BlogEntity> pageResult = easyEntityQuery.queryable("select * from t_blog where id = ?", BlogEntity.class, Arrays.asList("1"))
                .where(t_blog -> {
                    t_blog.content().like("-");
                })
                .select(BlogEntity.class, t_blog -> Select.of(
                        t_blog.id(),
                        t_blog.expression().sqlSegment("CONCAT({0},{1},{2})", c -> {
                            c.format("MySQL")
                                    .format("5.7-").expression(t_blog.id());
                        }).as(BlogEntity::getContent)
                ))
                .toPageResult(1, 20);
        Assert.assertEquals(1, pageResult.getData().size());
        Assert.assertEquals(1, pageResult.getTotal());
        BlogEntity blogEntity = pageResult.getData().get(0);
        Assert.assertEquals("1", blogEntity.getId());
        Assert.assertEquals("MySQL5.7-1", blogEntity.getContent());
    }

}
