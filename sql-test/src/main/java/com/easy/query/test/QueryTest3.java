package com.easy.query.test;

import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.api4j.extension.SQL4JFunc;
import com.easy.query.api4j.select.Queryable;
import com.easy.query.core.api.dynamic.sort.ObjectSort;
import com.easy.query.core.api.dynamic.sort.ObjectSortBuilder;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.JdbcStreamResult;
import com.easy.query.core.basic.jdbc.executor.internal.reader.BeanDataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.DataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.EmptyDataReader;
import com.easy.query.core.basic.jdbc.executor.internal.reader.PropertyDataReader;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.exception.EasyQueryTableNotInSQLContextException;
import com.easy.query.core.expression.builder.core.AnyValueFilter;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.extension.client.SQLClientFunc;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.test.common.MyLimitPager;
import com.easy.query.test.common.MyPager;
import com.easy.query.test.common.PageResult;
import com.easy.query.test.dto.BlogQuery1Request;
import com.easy.query.test.dto.BlogQuery2Request;
import com.easy.query.test.dto.TopicTypeVO;
import com.easy.query.test.dto.UserBookEncryptVO;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUserEncrypt;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicAuto;
import com.easy.query.test.entity.TopicGenericKey;
import com.easy.query.test.entity.UserBookEncrypt;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicAutoProxy;
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
        String toSql = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(o -> o.eq(Topic::getId, "3" ))
                .limit(1, 2)
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` = ? LIMIT 2 OFFSET 1" , toSql);
    }

    @Test
    public void query124_5() {
        String toSql = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(o -> o.eq(Topic::getId, "" ))
                .filterConfigure(AnyValueFilter.DEFAULT)
                .limit(1, 2)
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` LIMIT 2 OFFSET 1" , toSql);
    }

    @Test
    public void query124_4() {
        String toSql = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3, t4) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                .leftJoinMerge(BlogEntity.class, c -> c.t().eq(c.t4(), Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3" ))
                .limit(1, 2)
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` LEFT JOIN `t_blog` t4 ON t4.`deleted` = ? AND t.`id` = t3.`id` LEFT JOIN `t_blog` t5 ON t5.`deleted` = ? AND t.`id` = t4.`id` WHERE t.`id` = ? LIMIT 2 OFFSET 1" , toSql);
    }


    @Test
    public void query124_1() {
        String toSql = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId)).asTableLink(join -> join + " lateral" )
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3" ))
                .limit(1, 2)
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN lateral `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` = ? LIMIT 2 OFFSET 1" , toSql);
    }

    @Test
    public void query124_2() {
        String toSql = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .leftJoinMerge(BlogEntity.class, o -> o.t().eq(o.t3(), Topic::getId, BlogEntity::getId))
                .whereMerge(o -> o.t().eq(Topic::getId, "3" ))
                .limit(1, 2)
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` = ? LIMIT 2 OFFSET 1" , toSql);
    }

    @Test
    public void query125() {
        String toSql = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .innerJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .innerJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3" ))
                .select(BlogEntity.class, (t, t1, t2, t3) -> t.column(Topic::getId)
                        .then(t1)
                        .column(BlogEntity::getTitle)
                        .then(t2)
                        .column(BlogEntity::getStar))
                .limit(1, 2)
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t1.`title`,t2.`star` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` INNER JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` INNER JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` = ? LIMIT 2 OFFSET 1" , toSql);
    }

    @Test
    public void query126() {
        {

            List<BlogEntity> list = easyQuery
                    .queryable(Topic.class)
//                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId).then(t1).eq(BlogEntity::getId,3))
                    .leftJoin(BlogEntity.class, (t, t1) -> {
                        t.eq(t1, Topic::getId, BlogEntity::getId);
                        t1.eq(BlogEntity::getId, 3);
                    })
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .rightJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3" ))
                    .select(BlogEntity.class, (t, t1, t2, t3) -> t.column(Topic::getId)
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
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "3" ))
                    .select(BlogEntity.class, (t, t1, t2, t3) -> t.column(Topic::getId)
                            .then(t1)
                            .column(BlogEntity::getTitle)
                            .then(t2)
                            .column(BlogEntity::getStar))
                    .limit(1)
                    .toList();
            Assert.assertEquals(1, list.size());
        }
    }

    @Test
    public void query112() {
        BigDecimal bigDecimal = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .innerJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3" ))
                .sumOrDefault((t, t1, t2, t3) -> t1.column(BlogEntity::getScore), BigDecimal.ZERO);
        Assert.assertTrue(new BigDecimal("1.2" ).compareTo(bigDecimal) == 0);
    }

    @Test
    public void query113() {
        BigDecimal bigDecimal = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3x" ))
                .sumOrNull((t, t1, t2, t3) -> t1.column(BlogEntity::getScore));
        Assert.assertTrue(bigDecimal == null);
    }

    @Test
    public void query114() {
        BigDecimal bigDecimal = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3" ))
                .sumBigDecimalOrNull((t, t1, t2, t3) -> t3.column(BlogEntity::getScore));
        Assert.assertTrue(new BigDecimal("1.2" ).compareTo(bigDecimal) == 0);
    }

    @Test
    public void query115() {
        BigDecimal bigDecimal = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3x" ))
                .sumBigDecimalOrDefault((t, t1, t2, t3) -> t3.column(BlogEntity::getScore), null);
        Assert.assertTrue(bigDecimal == null);
        BigDecimal bigDecimal1 = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .innerJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3x" ))
                .sumBigDecimalOrDefault((t, t1, t2, t3) -> t3.column(BlogEntity::getScore), BigDecimal.ZERO);
        Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
    }

    @Test
    public void query116() {
        BigDecimal bigDecimal = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3x" ))
                .maxOrNull((t, t1, t2, t3) -> t3.column(BlogEntity::getScore));
        Assert.assertTrue(bigDecimal == null);
        BigDecimal bigDecimal1 = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3x" ))
                .maxOrDefault((t, t1, t2, t3) -> t1.column(BlogEntity::getScore), BigDecimal.ZERO);
        Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
    }

    @Test
    public void query117() {
        BigDecimal bigDecimal = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .innerJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                .where(o -> o.in(Topic::getId, Arrays.asList("3x" , "3" )))
                .maxOrNull((t, t1, t2, t3) -> t3.column(BlogEntity::getScore));
        Assert.assertTrue(new BigDecimal("1.2" ).compareTo(bigDecimal) == 0);
    }

    @Test
    public void query118() {
        BigDecimal bigDecimal = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .innerJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .rightJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3x" ))
                .minOrNull((t, t1, t2, t3) -> t3.column(BlogEntity::getScore));
        Assert.assertTrue(bigDecimal == null);
        BigDecimal bigDecimal1 = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .innerJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .rightJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3x" ))
                .minOrDefault((t, t1, t2, t3) -> t3.column(BlogEntity::getScore), BigDecimal.ZERO);
        Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
    }

    @Test
    public void query119() {
        BigDecimal bigDecimal = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .innerJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                .where(o -> o.in(Topic::getId, Arrays.asList("3x" , "3" )))
                .minOrNull((t, t1, t2, t3) -> t3.column(BlogEntity::getScore));
        Assert.assertTrue(new BigDecimal("1.2" ).compareTo(bigDecimal) == 0);
    }

    @Test
    public void query120() {
        {
            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3" , "2" , "5" )))
                    .avgBigDecimalOrNull((t, t1, t2, t3) -> t1.column(BlogEntity::getScore));
            Assert.assertTrue(new BigDecimal("1.2" ).compareTo(bigDecimal) == 0);
        }
        {
            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x" , "2x" , "5x" )))
                    .avgBigDecimalOrNull((t, t1, t2, t3) -> t3.column(BlogEntity::getScore));
            Assert.assertTrue(bigDecimal == null);
        }
        {
            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x" , "2x" , "5" )))
                    .avgBigDecimalOrDefault((t, t1, t2, t3) -> t3.column(BlogEntity::getScore), BigDecimal.ZERO);
            Assert.assertTrue(new BigDecimal("1.2" ).compareTo(bigDecimal) == 0);
        }
        {
            Double bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x" , "2x" , "5" )))
                    .avgOrDefault((t, t1, t2, t3) -> t3.column(BlogEntity::getStar), null);
            Assert.assertTrue(5d == bigDecimal);
        }
        {
            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x" , "2x" , "5" )))
                    .avgOrDefault((t, t1, t2, t3) -> t3.column(BlogEntity::getStar), null, BigDecimal.class);
            Assert.assertTrue(new BigDecimal("5" ).compareTo(bigDecimal) == 0);
        }
        {
            Float bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x" , "2x" , "5" )))
                    .avgOrDefault((t, t1, t2, t3) -> t1.column(BlogEntity::getStar), null, Float.class);
            Assert.assertTrue(5f == bigDecimal);
        }
    }

    @Test
    public void query121() {
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3" , "2" , "5" )))
                    .orderByAsc((t, t1, t2, t3) -> t1.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) ORDER BY t1.`order` ASC" , sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3" , "2" , "5" )))
                    .orderByAsc((t, t1, t2, t3) -> {
                        Assert.assertNotNull(t.getTable());
                        Assert.assertNotNull(t.getRuntimeContext());
                        t1.column(BlogEntity::getOrder).then(t2).column(BlogEntity::getScore);
                    }).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) ORDER BY t1.`order` ASC,t2.`score` ASC" , sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3" , "2" , "5" )))
                    .orderByAsc(false, (t, t1, t2, t3) -> t1.column(BlogEntity::getOrder))
                    .orderByAsc(true, (t, t1, t2, t3) -> t3.column(BlogEntity::getId)).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` INNER JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) ORDER BY t3.`id` ASC" , sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .rightJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3" , "2" , "5" )))
                    .orderByDesc((t, t1, t2, t3) -> t3.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` RIGHT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) ORDER BY t3.`order` DESC" , sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3" , "2" , "5" )))
                    .orderByDesc(false, (t, t1, t2, t3) -> t3.column(BlogEntity::getOrder))
                    .orderByDesc(true, (t, t1, t2, t3) -> t3.column(BlogEntity::getId).column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) ORDER BY t3.`id` DESC,t3.`order` DESC" , sql);
        }
    }

    @Test
    public void query122() {
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3" , "2" , "5" )))
                    .groupBy(o -> o.column(Topic::getId))
                    .orderByAsc((t, t1, t2, t3) -> t1.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t1.`order` ASC" , sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3" , "2" , "5" )))
                    .groupBy((t, t1, t2, t3) -> t3.column(BlogEntity::getId))
                    .orderByAsc((t, t1, t2, t3) -> t3.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t3.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` INNER JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) GROUP BY t3.`id` ORDER BY t3.`order` ASC" , sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .rightJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3" , "2" , "5" )))
                    .groupBy(false, (t, t1, t2, t3) -> t.column(Topic::getStars))
                    .groupBy(true, (t, t1, t2, t3) -> t.column(Topic::getId))
                    .orderByAsc((t, t1, t2, t3) -> t1.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` INNER JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` RIGHT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t1.`order` ASC" , sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .rightJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3" , "2" , "5" )))
                    .groupBy(false, (t, t1, t2, t3) -> t.column(Topic::getStars))
                    .groupBy(true, (t, t1, t2, t3) -> t3.column(BlogEntity::getId))
                    .orderByAsc((t, t1, t2, t3) -> t3.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t3.`id` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` INNER JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` RIGHT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) GROUP BY t3.`id` ORDER BY t3.`order` ASC" , sql);
        }
    }

    @Test
    public void query123() {
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .rightJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3" , "2" , "5" )))
                    .groupBy(o -> o.column(Topic::getId))
                    .distinct()
                    .orderByAsc((t, t1, t2, t3) -> t3.column(BlogEntity::getOrder))
                    .limit(10).toSQL();
            Assert.assertEquals("SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` INNER JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` RIGHT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t3.`order` ASC LIMIT 10" , sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .innerJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3" , "2" , "5" )))
                    .groupBy(o -> o.column(Topic::getId))
                    .orderByAsc((t, t1, t2, t3) -> t3.column(BlogEntity::getOrder))
                    .distinct()
                    .select(Topic.class, o -> o.column(Topic::getId))
                    .where(o -> o.eq(Topic::getId, "x" ))
                    .select(Long.class, o -> o.columnCount(Topic::getId)).toSQL();
            Assert.assertEquals("SELECT COUNT(t4.`id`) AS `id` FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` INNER JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t3.`order` ASC) t4 WHERE t4.`id` = ?" , sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3" , "2" , "5" )))
                    .groupBy(o -> o.column(Topic::getId))
                    .orderByAsc((t, t1, t2, t3) -> t3.column(BlogEntity::getOrder))
                    .distinct()
                    .select(Topic.class, o -> o.column(Topic::getId))
                    .select("count(1)" ).toSQL();
            Assert.assertEquals("SELECT count(1) FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t3.`order` ASC) t4" , sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .rightJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3" , "2" , "5" )))
                    .groupBy(o -> o.column(Topic::getId))
                    .orderByDesc((t) -> t.column(Topic::getStars))
                    .distinct()
                    .select(Topic.class, o -> o.column(Topic::getId))
                    .select("count(1)" ).toSQL();
            Assert.assertEquals("SELECT count(1) FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` RIGHT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t.`stars` DESC) t4" , sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .rightJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3" , "2" , "5" )))
                    .groupBy(o -> o.column(Topic::getId))
                    .orderByAsc((t) -> t.column(Topic::getStars))
                    .distinct()
                    .select(Topic.class, o -> o.column(Topic::getId))
                    .select("count(1)" ).toSQL();
            Assert.assertEquals("SELECT count(1) FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` RIGHT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t.`stars` ASC) t4" , sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .leftJoin(BlogEntity.class, (t, t1, t2) -> t.eq(t2, Topic::getId, BlogEntity::getId))
                    .rightJoin(BlogEntity.class, (t, t1, t2, t3) -> t.eq(t3, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3" , "2" , "5" )))
                    .groupBy(o -> o.column(Topic::getId))
                    .orderByAsc((t) -> t.column(Topic::getStars))
                    .distinct()
                    .select(Topic.class, o -> o.groupKeys(0))
                    .select("count(1)" ).toSQL();
            Assert.assertEquals("SELECT count(1) FROM (SELECT DISTINCT t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` LEFT JOIN `t_blog` t2 ON t2.`deleted` = ? AND t.`id` = t2.`id` RIGHT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t.`id` = t3.`id` WHERE t.`id` IN (?,?,?) GROUP BY t.`id` ORDER BY t.`stars` ASC) t4" , sql);
        }
    }

    @Test
    public void testProxy1() {
        TopicProxy table = TopicProxy.createTable();
        List<Topic> list1 = easyProxyQuery
                .queryable(table)
                .where(o -> {
                    o.id().eq("123" );
                    o.title().like("xxx" );
                })
                .where(t -> {
                    t.id().eq("123");
                    t.title().like("xxx");
                })
                .select(o -> o.FETCHER.id().title().fetchProxy())
                .toList();
        {
            try {

                List<Topic> list = easyProxyQuery.queryable(TopicProxy.createTable())
                        .where(o -> o.id().eq("123" ))
                        .toList();
            } catch (Exception ex) {
                Assert.assertTrue(ex instanceof EasyQueryTableNotInSQLContextException);
                Assert.assertTrue(ex.getMessage().startsWith("not found table:[Topic:" ));
                Assert.assertTrue(ex.getMessage().endsWith("] in sql context" ));
            }
        }

        String sql = easyProxyQuery.queryable(BlogEntityProxy.createTable())
                .leftJoin(TopicAutoProxy.createTable(), (a,b) -> a.id().eq(b.title()))
                .where((a,b) -> b.title().eq(a.id())).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN `t_topic_auto` t1 ON t.`id` = t1.`title` WHERE t.`deleted` = ? AND t1.`title` = t.`id`" , sql);

        EasyPageResult<Topic> topicPageResult = easyProxyQuery
                .queryable(TopicProxy.createTable())
                .where(o -> o.id().isNotNull())
                .toPageResult(3, 10);
        List<Topic> data = topicPageResult.getData();
        Assert.assertEquals(10, data.size());
    }

    @Test
    public void testLambda3() {
        Topic topic = easyQuery.queryable(Topic.class)
                .where(t -> t.eq(Topic::getId, "3" ).or().like(Topic::getTitle, "你好" ))
                .firstOrNull();
        Assert.assertNotNull(topic);
    }

    @Test
    public void testProperty3() {
        Topic topic = easyQueryClient.queryable(Topic.class)
                .where(t -> t.eq("id" , "3" ).or().like("title" , "你好" ))
                .firstOrNull();
        Assert.assertNotNull(topic);
    }

    @Test
    public void testProperty3_1() {
        SQLFunc sqlFunc = easyQueryClient.getRuntimeContext().fx();
        Topic topic = easyQueryClient.queryable(Topic.class)
                .where(t -> t.eq(sqlFunc.valueOrDefault("id" , "1" ), sqlFunc.valueOrDefault("stars" , "3" )).or().like("title" , "你好" ))
                .firstOrNull();
        Assert.assertNotNull(topic);
        String sql = easyQueryClient.queryable(Topic.class)
                .where(t -> t.eq(sqlFunc.valueOrDefault("id" , "1" ), sqlFunc.valueOrDefault("stars" , "3" )).or().like("title" , "你好" ))
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (IFNULL(`id`,?) = IFNULL(`stars`,?) OR `title` LIKE ?)" , sql);

        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(t -> t.eq(sqlFunc.valueOrDefault("id" , "1" ), sqlFunc.valueOrDefault(x -> x.column("stars" ).column("id" ).value("3" ))).or().like("title" , "你好" ))
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (IFNULL(`id`,?) = IFNULL(`stars`,`id`,?) OR `title` LIKE ?)" , sql1);


        String sql2 = easyQueryClient.queryable(Topic.class)
                .where(t -> {
                    t.eq(
                            sqlFunc.valueOrDefault("id" , "1" ),
                            sqlFunc.valueOrDefault(x -> x.column("stars" ).column("id" ).value("3" ))
                    );
                    t.or().like("title" , "你好" );
                })
                .toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (IFNULL(`id`,?) = IFNULL(`stars`,`id`,?) OR `title` LIKE ?)" , sql1);

    }

    @Test
    public void testSelfPredicate1() {
        {
            String sql = easyQueryClient.queryable(BlogEntity.class)
                    .where(t -> t.eq("score" , "3" ).eq(t, "score" , "order" ))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` = `order`" , sql);
        }
        {
            String sql = easyQueryClient.queryable(BlogEntity.class)
                    .where(t -> t.eq("score" , "3" ).ne(t, "score" , "order" ))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` <> `order`" , sql);
        }
        {
            String sql = easyQueryClient.queryable(BlogEntity.class)
                    .where(t -> t.eq("score" , "3" ).gt(t, "score" , "order" ))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` > `order`" , sql);
        }
        {
            String sql = easyQueryClient.queryable(BlogEntity.class)
                    .where(t -> t.eq("score" , "3" ).ge(t, "score" , "order" ))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` >= `order`" , sql);
        }
        {
            String sql = easyQueryClient.queryable(BlogEntity.class)
                    .where(t -> t.eq("score" , "3" ).le(t, "score" , "order" ))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` <= `order`" , sql);
        }
        {
            String sql = easyQueryClient.queryable(BlogEntity.class)
                    .where(t -> t.eq("score" , "3" ).lt(t, "score" , "order" ))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` < `order`" , sql);
        }
        {

            String sql1 = easyQuery.queryable(BlogEntity.class)
                    .where(t -> t.eq(BlogEntity::getScore, "3" ).eq(t, BlogEntity::getScore, BlogEntity::getOrder))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` = `order`" , sql1);
        }
        {

            String sql1 = easyQuery.queryable(BlogEntity.class)
                    .where(t -> t.eq(BlogEntity::getScore, "3" ).ne(t, BlogEntity::getScore, BlogEntity::getOrder))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` <> `order`" , sql1);
        }
        {

            String sql1 = easyQuery.queryable(BlogEntity.class)
                    .where(t -> t.eq(BlogEntity::getScore, "3" ).le(t, BlogEntity::getScore, BlogEntity::getOrder))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` <= `order`" , sql1);
        }
        {

            String sql1 = easyQuery.queryable(BlogEntity.class)
                    .where(t -> t.eq(BlogEntity::getScore, "3" ).lt(t, BlogEntity::getScore, BlogEntity::getOrder))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` < `order`" , sql1);
        }
        {

            String sql1 = easyQuery.queryable(BlogEntity.class)
                    .where(t -> t.eq(BlogEntity::getScore, "3" ).gt(t, BlogEntity::getScore, BlogEntity::getOrder))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` > `order`" , sql1);
        }
        {

            String sql1 = easyQuery.queryable(BlogEntity.class)
                    .where(t -> t.eq(BlogEntity::getScore, "3" ).ge(t, BlogEntity::getScore, BlogEntity::getOrder))
                    .toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `score` = ? AND `score` >= `order`" , sql1);
        }
    }

    @Test
    public void testProxy6() {

        List<Map<String, Object>> list = easyProxyQuery
                .queryable(TopicProxy.createTable())
                .select(o -> {
                    MapProxy table = new MapProxy();
                    table.selectExpression(o.id(),o.title());
                    return table;
                })
                .toList();
        for (Map<?, ?> map : list) {
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                System.out.println(entry.getKey());
                System.out.println(entry.getValue());
            }
        }

    }

    @Test
    public void testSample() {
        List<Topic> list = easyQueryClient.queryable(Topic.class)
                .where(o -> o.like("title" , "someTitle" ))
                .orderByAsc(o -> o.column("createTime" ).column("id" ))
                .toList();
        Assert.assertEquals(0, list.size());
        List<Topic> list1 = easyQuery.queryable(Topic.class)
                .where(o -> o.like(Topic::getTitle, "someTitle" ))
                .orderByAsc(o -> o.column(Topic::getCreateTime).column(Topic::getId))
                .toList();
        Assert.assertEquals(0, list1.size());
    }

    @Test
    public void testCaseWhen() {

        Queryable<Topic> select = easyQuery.queryable(Topic.class)
                .where(o -> o.like(Topic::getTitle, "someTitle" ))
                .orderByAsc(o -> o.column(Topic::getCreateTime).column(Topic::getId))
                .select(Topic.class, o -> o.sqlSegmentAs(SQL4JFunc.caseWhenBuilder(o)
                        .caseWhen(f -> f.eq(Topic::getTitle, "123" ).ge(Topic::getStars, 1), "first1" )
                        .caseWhen(f -> f.eq(Topic::getTitle, "456" ), "first2" )
                        .caseWhen(f -> f.eq(Topic::getTitle, "789" ), "first3" )
                        .elseEnd("firstEnd" ), Topic::getTitle));
        ExpressionContext expressionContext = select.getSQLEntityExpressionBuilder().getExpressionContext();
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(expressionContext.getTableContext());
        String sql = select.toSQL(toSQLContext);
        Assert.assertEquals("SELECT (CASE WHEN t.`title` = ? AND t.`stars` >= ? THEN ? WHEN t.`title` = ? THEN ? WHEN t.`title` = ? THEN ? ELSE ? END) AS `title` FROM `t_topic` t WHERE t.`title` LIKE ? ORDER BY t.`create_time` ASC,t.`id` ASC" , sql);
        Assert.assertEquals(9, toSQLContext.getParameters().size());
        Assert.assertEquals("123" , toSQLContext.getParameters().get(0).getValue());
        Assert.assertEquals(1, toSQLContext.getParameters().get(1).getValue());
        Assert.assertEquals("first1" , toSQLContext.getParameters().get(2).getValue());
        Assert.assertEquals("456" , toSQLContext.getParameters().get(3).getValue());
        Assert.assertEquals("first2" , toSQLContext.getParameters().get(4).getValue());
        Assert.assertEquals("789" , toSQLContext.getParameters().get(5).getValue());
        Assert.assertEquals("first3" , toSQLContext.getParameters().get(6).getValue());
        Assert.assertEquals("firstEnd" , toSQLContext.getParameters().get(7).getValue());
        Assert.assertEquals("%someTitle%" , toSQLContext.getParameters().get(8).getValue());
    }

    @Test
    public void propertyCaseWhen() {

        String sql = easyQueryClient.queryable(Topic.class)
                .where(t -> t.like("title" , "someTitle" ))
                .select(Topic.class, t -> t
                        .sqlSegmentAs(
                                SQLClientFunc.caseWhenBuilder(t)
                                        .caseWhen(f -> f.eq("title" , "123" ), "111" )
                                        .caseWhen(f -> f.eq("title" , "456" ), "222" )
                                        .elseEnd("222" )
                                , "title" )
                        .column("id" )
                )
                .toSQL();
        Assert.assertEquals("SELECT (CASE WHEN t.`title` = ? THEN ? WHEN t.`title` = ? THEN ? ELSE ? END) AS `title`,t.`id` FROM `t_topic` t WHERE t.`title` LIKE ?" , sql);
        List<Topic> list = easyQueryClient.queryable(Topic.class)
                .where(t -> t.like("title" , "someTitle" ))
                .select(Topic.class, t -> t
                        .sqlSegmentAs(
                                SQLClientFunc.caseWhenBuilder(t)
                                        .caseWhen(f -> f.eq("title" , "123" ), "111" )
                                        .caseWhen(f -> f.eq("title" , "456" ), "222" )
                                        .elseEnd("222" )
                                , "title" )
                        .column("id" )
                ).toList();
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void lambdaCaseWhen() {
        {

            String sql = easyQuery.queryable(Topic.class)
                    .where(t -> t.like(Topic::getTitle, "someTitle" ))
                    .select(Topic.class, t -> t
                            .sqlSegmentAs(
                                    SQL4JFunc.caseWhenBuilder(t)
                                            .caseWhen(f -> f.eq(Topic::getTitle, "123" ), "111" )
                                            .caseWhen(f -> f.eq(Topic::getTitle, "456" ), "222" )
                                            .elseEnd("222" )
                                    , Topic::getTitle)
                            .column(Topic::getId)
                    )
                    .toSQL();
            Assert.assertEquals("SELECT (CASE WHEN t.`title` = ? THEN ? WHEN t.`title` = ? THEN ? ELSE ? END) AS `title`,t.`id` FROM `t_topic` t WHERE t.`title` LIKE ?" , sql);
            List<Topic> list = easyQuery.queryable(Topic.class)
                    .where(t -> t.like(Topic::getTitle, "someTitle" ))
                    .select(Topic.class, t -> t
                            .sqlSegmentAs(
                                    SQL4JFunc.caseWhenBuilder(t)
                                            .caseWhen(f -> f.eq(Topic::getTitle, "123" ), "111" )
                                            .caseWhen(f -> f.eq(Topic::getTitle, "456" ), "222" )
                                            .elseEnd("222" )
                                    , Topic::getTitle)
                            .column(Topic::getId)
                    ).toList();
            Assert.assertEquals(0, list.size());
        }
        {

            String sql = easyQuery.queryable(Topic.class)
                    .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(t -> t.like(Topic::getTitle, "someTitle" ))
                    .select(Topic.class, (t, t1) -> t
                            .sqlSegmentAs(
                                    SQL4JFunc.caseWhenBuilder(t, t1)
                                            .caseWhen((f, f1) -> f.eq(Topic::getTitle, "123" ).then(f1).le(BlogEntity::getStar, 100), "111" )
                                            .caseWhen((f, f1) -> f.eq(Topic::getTitle, "456" ).then(f1).ge(BlogEntity::getStar, 200), "222" )
                                            .elseEnd("222" )
                                    , Topic::getTitle)
                            .column(Topic::getId)
                    )
                    .toSQL();
            Assert.assertEquals("SELECT (CASE WHEN t.`title` = ? AND t1.`star` <= ? THEN ? WHEN t.`title` = ? AND t1.`star` >= ? THEN ? ELSE ? END) AS `title`,t.`id` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`title` LIKE ?" , sql);
            List<Topic> list = easyQuery.queryable(Topic.class)
                    .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(t -> t.like(Topic::getTitle, "someTitle" ))
                    .select(Topic.class, (t, t1) -> t
                            .sqlSegmentAs(
                                    SQL4JFunc.caseWhenBuilder(t, t1)
                                            .caseWhen((f, f1) -> f.eq(Topic::getTitle, "123" ).then(f1).le(BlogEntity::getStar, 100), "111" )
                                            .caseWhen((f, f1) -> f.eq(Topic::getTitle, "456" ).then(f1).ge(BlogEntity::getStar, 200), "222" )
                                            .elseEnd("222" )
                                    , Topic::getTitle)
                            .column(Topic::getId)
                    ).toList();
            Assert.assertEquals(0, list.size());
        }
    }

    @Test
    public void lambdaCaseWhen1() {
        {

            String sql = easyQuery.queryable(Topic.class)
                    .where(t -> t.like(Topic::getTitle, "someTitle" ))
                    .select(Topic.class, t -> t
                            .sqlSegmentAs(
                                    SQL4JFunc.caseWhenBuilder(t)
                                            .caseWhen(f -> f.eq(Topic::getTitle, "123" ), Topic::getId)
                                            .caseWhen(f -> f.eq(Topic::getTitle, "456" ), "222" )
                                            .elseEnd(Topic::getId)
                                    , Topic::getTitle)
                            .column(Topic::getId)
                    )
                    .toSQL();
            Assert.assertEquals("SELECT (CASE WHEN t.`title` = ? THEN t.`id` WHEN t.`title` = ? THEN ? ELSE t.`id` END) AS `title`,t.`id` FROM `t_topic` t WHERE t.`title` LIKE ?" , sql);
            List<Topic> list = easyQuery.queryable(Topic.class)
                    .where(t -> t.like(Topic::getTitle, "someTitle" ))
                    .select(Topic.class, t -> t
                            .sqlSegmentAs(
                                    SQL4JFunc.caseWhenBuilder(t)
                                            .caseWhen(f -> f.eq(Topic::getTitle, "123" ), Topic::getId)
                                            .caseWhen(f -> f.eq(Topic::getTitle, "456" ), "222" )
                                            .elseEnd("222" )
                                    , Topic::getTitle)
                            .column(Topic::getId)
                    ).toList();
            Assert.assertEquals(0, list.size());
        }
        {

            String sql = easyQuery.queryable(Topic.class)
                    .leftJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                    .where(t -> t.like(Topic::getTitle, "someTitle" ))
                    .select(Topic.class, (t, t1) -> t
                            .sqlSegmentAs(
                                    SQL4JFunc.caseWhenBuilder(t)
                                            .caseWhen(f -> f.eq(Topic::getTitle, "123" ), t1, Topic::getId)
                                            .caseWhen(f -> f.eq(Topic::getTitle, "456" ), "222" )
                                            .elseEnd(Topic::getId)
                                    , Topic::getTitle)
                            .column(Topic::getId)
                    )
                    .toSQL();
            Assert.assertEquals("SELECT (CASE WHEN t.`title` = ? THEN t1.`id` WHEN t.`title` = ? THEN ? ELSE t.`id` END) AS `title`,t.`id` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` WHERE t.`title` LIKE ?" , sql);
            List<Topic> list = easyQuery.queryable(Topic.class)
                    .leftJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                    .where(t -> t.like(Topic::getTitle, "someTitle" ))
                    .select(Topic.class, t -> t
                            .sqlSegmentAs(
                                    SQL4JFunc.caseWhenBuilder(t)
                                            .caseWhen(f -> f.eq(Topic::getTitle, "123" ), t, Topic::getId)
                                            .caseWhen(f -> f.eq(Topic::getTitle, "456" ), "222" )
                                            .elseEnd(Topic::getId)
                                    , Topic::getTitle)
                            .column(Topic::getId)
                    ).toList();
            Assert.assertEquals(0, list.size());
        }
        {

            String sql = easyQuery.queryable(Topic.class)
                    .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(t -> t.like(Topic::getTitle, "someTitle" ))
                    .select(Topic.class, (t, t1) -> t
                            .sqlSegmentAs(
                                    SQL4JFunc.caseWhenBuilder(t, t1)
                                            .caseWhen((f, f1) -> f.eq(Topic::getTitle, "123" ).then(f1).le(BlogEntity::getStar, 100), t1, BlogEntity::getId)
                                            .caseWhen((f, f1) -> f.eq(Topic::getTitle, "456" ).then(f1).ge(BlogEntity::getStar, 200), "222" )
                                            .elseEnd(t1, BlogEntity::getOrder)
                                    , Topic::getTitle)
                            .column(Topic::getId)
                    )
                    .toSQL();
            Assert.assertEquals("SELECT (CASE WHEN t.`title` = ? AND t1.`star` <= ? THEN t1.`id` WHEN t.`title` = ? AND t1.`star` >= ? THEN ? ELSE t1.`order` END) AS `title`,t.`id` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`title` LIKE ?" , sql);
            List<Topic> list = easyQuery.queryable(Topic.class)
                    .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(t -> t.like(Topic::getTitle, "someTitle" ))
                    .select(Topic.class, (t, t1) -> t
                            .sqlSegmentAs(
                                    SQL4JFunc.caseWhenBuilder(t, t1)
                                            .caseWhen((f, f1) -> f.eq(Topic::getTitle, "123" ).then(f1).le(BlogEntity::getStar, 100), t1, BlogEntity::getId)
                                            .caseWhen((f, f1) -> f.eq(Topic::getTitle, "456" ).then(f1).ge(BlogEntity::getStar, 200), "222" )
                                            .elseEnd(t1, BlogEntity::getOrder)
                                    , Topic::getTitle)
                            .column(Topic::getId)
                    ).toList();
            Assert.assertEquals(0, list.size());
        }
    }

    @Test
    public void testAndOr1() {
        Topic topic = easyQuery.queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "1" ).and(
                        x -> x.like(Topic::getTitle, "你好" )
                                .or()
                                .eq(Topic::getTitle, "我是title" )
                                .or()
                                .le(Topic::getCreateTime, LocalDateTime.now())
                )).firstOrNull();
        Assert.assertNotNull(topic);
        String sql = easyQuery.queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "1" ).and(
                        x -> x.like(Topic::getTitle, "你好" )
                                .or()
                                .eq(Topic::getTitle, "我是title" )
                                .or()
                                .le(Topic::getCreateTime, LocalDateTime.now())
                )).toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` = ? AND (`title` LIKE ? OR `title` = ? OR `create_time` <= ?)" , sql);

        List<Topic> topic2 = easyQuery.queryable(Topic.class)
                .where(o -> o.like(Topic::getTitle, "你好" )
                        .or()
                        .eq(Topic::getTitle, "我是title" )
                        .or()
                        .le(Topic::getCreateTime, LocalDateTime.now())).toList();
        Assert.assertNotNull(topic2);
        Assert.assertTrue(topic2.size() > 1);

        String sql2 = easyQuery.queryable(Topic.class)
                .where(o -> o.like(Topic::getTitle, "你好" )
                        .or()
                        .eq(Topic::getTitle, "我是title" )
                        .or()
                        .le(Topic::getCreateTime, LocalDateTime.now())).toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`title` LIKE ? OR `title` = ? OR `create_time` <= ?)" , sql2);


        Topic topic3 = easyQuery.queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "1" ).or(
                        x -> x.like(Topic::getTitle, "你好" )
                                .eq(Topic::getTitle, "我是title" )
                                .le(Topic::getCreateTime, LocalDateTime.now())
                )).firstOrNull();
        Assert.assertNotNull(topic3);
        String sql3 = easyQuery.queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "1" ).or(
                        x -> x.like(Topic::getTitle, "你好" )
                                .eq(Topic::getTitle, "我是title" )
                                .le(Topic::getCreateTime, LocalDateTime.now())
                )).toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE (`id` = ? OR (`title` LIKE ? AND `title` = ? AND `create_time` <= ?))" , sql3);
    }

    @Test
    public void testAndOr2() {
        BlogEntity blog = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "1" ).and(
                        x -> x.like(BlogEntity::getTitle, "你好" )
                                .or()
                                .eq(BlogEntity::getTitle, "我是title" )
                                .or()
                                .le(BlogEntity::getCreateTime, LocalDateTime.now())
                )).firstOrNull();
        Assert.assertNotNull(blog);
        String sql = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "1" ).and(
                        x -> x.like(BlogEntity::getTitle, "你好" )
                                .or()
                                .eq(BlogEntity::getTitle, "我是title" )
                                .or()
                                .le(BlogEntity::getCreateTime, LocalDateTime.now())
                )).toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` = ? AND (`title` LIKE ? OR `title` = ? OR `create_time` <= ?)" , sql);

        BlogEntity blog1 = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.like(BlogEntity::getTitle, "你好" )
                        .or()
                        .eq(BlogEntity::getTitle, "我是title" )
                        .or()
                        .le(BlogEntity::getCreateTime, LocalDateTime.now())).firstOrNull();
        Assert.assertNotNull(blog1);

        String sql1 = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.like(BlogEntity::getTitle, "你好" )
                        .or()
                        .eq(BlogEntity::getTitle, "我是title" )
                        .or()
                        .le(BlogEntity::getCreateTime, LocalDateTime.now())).toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (`title` LIKE ? OR `title` = ? OR `create_time` <= ?)" , sql1);
    }

    @Test
    public void queryBasic1() {
        List<String> list = easyQuery.queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "1" ))
                .select(String.class, o -> o.column(Topic::getId))
                .toList();
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("1" , list.get(0));
        String sql = easyQuery.queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "1" ))
                .select(String.class, o -> o.column(Topic::getId))
                .toSQL();
        Assert.assertEquals("SELECT t.`id` FROM `t_topic` t WHERE t.`id` = ?" , sql);


        List<String> list1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id" , "1" ))
                .select(String.class, o -> o.column("id" ))
                .toList();
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals("1" , list1.get(0));

        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id" , "1" ))
                .select(String.class, o -> o.column("id" )).toSQL();
        Assert.assertEquals("SELECT t.`id` FROM `t_topic` t WHERE t.`id` = ?" , sql1);


    }

    @Test
    public void queryBasic2() {
        List<Integer> list = easyQuery.queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "1" ))
                .select(Integer.class, o -> o.column(Topic::getStars))
                .toList();
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(101, (int) list.get(0));
        String sql = easyQuery.queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "1" ))
                .select(Integer.class, o -> o.column(Topic::getStars))
                .toSQL();
        Assert.assertEquals("SELECT t.`stars` FROM `t_topic` t WHERE t.`id` = ?" , sql);


        List<Integer> list1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id" , "1" ))
                .select(Integer.class, o -> o.column("stars" ))
                .toList();
        Assert.assertEquals(1, list1.size());
        Assert.assertEquals(101, (int) list1.get(0));

        String sql1 = easyQueryClient.queryable(Topic.class)
                .where(o -> o.eq("id" , "1" ))
                .select(Integer.class, o -> o.column("stars" )).toSQL();
        Assert.assertEquals("SELECT t.`stars` FROM `t_topic` t WHERE t.`id` = ?" , sql1);

    }

    @Test
    public void queryBasic3() {
        Class<Map<String, Object>> mapClass = EasyObjectUtil.typeCastNullable(Map.class);
        {

            List<Map<String, Object>> list = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "1" ))
                    .select(mapClass, o -> o.columnAll())
                    .toList();
            Assert.assertEquals(1, list.size());
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "1" ))
                    .select(mapClass, o -> o.columnAll())
                    .toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` = ?" , sql);
        }
        {

            List<Map<String, Object>> list = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "1" ))
                    .select(mapClass)
                    .toList();
            Assert.assertEquals(1, list.size());
            String sql = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "1" ))
                    .select(mapClass)
                    .toSQL();
            Assert.assertEquals("SELECT * FROM `t_topic` t WHERE t.`id` = ?" , sql);
        }


        {
            List<Map<String, Object>> list1 = easyQueryClient.queryable(Topic.class)
                    .where(o -> o.eq("id" , "1" ))
                    .select(mapClass, o -> o.columnAll())
                    .toList();
            Assert.assertEquals(1, list1.size());

            String sql1 = easyQueryClient.queryable(Topic.class)
                    .where(o -> o.eq("id" , "1" ))
                    .select(mapClass, o -> o.columnAll()).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` = ?" , sql1);
        }

        {
            List<Map<String, Object>> list1 = easyQueryClient.queryable(Topic.class)
                    .where(o -> o.eq("id" , "1" ))
                    .select(mapClass)
                    .toList();
            Assert.assertEquals(1, list1.size());

            String sql1 = easyQueryClient.queryable(Topic.class)
                    .where(o -> o.eq("id" , "1" ))
                    .select(mapClass).toSQL();
            Assert.assertEquals("SELECT * FROM `t_topic` t WHERE t.`id` = ?" , sql1);
        }
    }


    @Test
    public void dynamicWhere() {
        {
            BlogQuery1Request query = new BlogQuery1Request();
            query.setOrder(BigDecimal.valueOf(1));
            query.setContent("标题" );
            query.setPublishTimeBegin(LocalDateTime.now());
            query.setPublishTimeEnd(LocalDateTime.now());
            query.setStatusList(Arrays.asList(1, 2));

            Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                    .where(o -> o
                            //当query.getContext不为空是添加查询条件 content like query.getContext
                            .like(EasyStringUtil.isNotBlank(query.getContent()), BlogEntity::getContent, query.getContent())
                            //当query.getOrder不为null是添加查询条件 content = query.getContext
                            .eq(query.getOrder() != null, BlogEntity::getOrder, query.getOrder())
                            //当query.getPublishTimeBegin()不为null添加左闭区间,右侧同理 publishTimeBegin <= publishTime <= publishTimeEnd
                            .rangeClosed(BlogEntity::getPublishTime, query.getPublishTimeBegin() != null, query.getPublishTimeBegin(), query.getPublishTimeEnd() != null, query.getPublishTimeEnd())
                            //添加in条件
                            .in(EasyCollectionUtil.isNotEmpty(query.getStatusList()), BlogEntity::getStatus, query.getStatusList())
                    );

            String sql = queryable.cloneQueryable().toSQL();
            System.out.println(sql);
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `content` LIKE ? AND `order` = ? AND (`publish_time` >= ? AND `publish_time` <= ?) AND `status` IN (?,?)" , sql);
            List<BlogEntity> list = queryable.cloneQueryable().toList();
            Assert.assertEquals(0, list.size());
        }
        {
            BlogQuery1Request query = new BlogQuery1Request();
            query.setContent("标题" );
            query.setPublishTimeBegin(LocalDateTime.now());
            query.setPublishTimeEnd(LocalDateTime.now());
            query.setStatusList(Arrays.asList(1, 2));

            Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                    .where(o -> o
                            //当query.getContext不为空是添加查询条件 content like query.getContext
                            .like(EasyStringUtil.isNotBlank(query.getContent()), BlogEntity::getContent, query.getContent())
                            //当query.getOrder不为null是添加查询条件 content = query.getContext
                            .eq(query.getOrder() != null, BlogEntity::getOrder, query.getOrder())
                            //当query.getPublishTimeBegin()不为null添加左闭区间,右侧同理 publishTimeBegin <= publishTime <= publishTimeEnd
                            .rangeClosed(BlogEntity::getPublishTime, query.getPublishTimeBegin() != null, query.getPublishTimeBegin(), query.getPublishTimeEnd() != null, query.getPublishTimeEnd())
                            //添加in条件
                            .in(EasyCollectionUtil.isNotEmpty(query.getStatusList()), BlogEntity::getStatus, query.getStatusList())
                    );

            String sql = queryable.cloneQueryable().toSQL();
            System.out.println(sql);
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `content` LIKE ? AND (`publish_time` >= ? AND `publish_time` <= ?) AND `status` IN (?,?)" , sql);
            List<BlogEntity> list = queryable.cloneQueryable().toList();
            Assert.assertEquals(0, list.size());
        }
    }

    @Test
    public void dynamicWhere1() {
        {
            BlogQuery2Request query = new BlogQuery2Request();
            query.setOrder(BigDecimal.valueOf(1));
            query.setContent("标题" );
            query.setPublishTimeBegin(LocalDateTime.now());
            query.setPublishTimeEnd(LocalDateTime.now());
            query.setStatusList(Arrays.asList(1, 2));

            Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                    .whereObject(query);

            String sql = queryable.cloneQueryable().toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `content` LIKE ? AND `publish_time` >= ? AND `publish_time` <= ? AND `order` = ? AND `status` IN (?,?)" , sql);
            List<BlogEntity> list = queryable.cloneQueryable().toList();
            Assert.assertEquals(0, list.size());
        }
        {
            BlogQuery2Request query = new BlogQuery2Request();
            query.setContent("标题" );
            query.setPublishTimeEnd(LocalDateTime.now());
            query.setStatusList(Arrays.asList(1, 2));

            Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                    .whereObject(query);

            String sql = queryable.cloneQueryable().toSQL();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `content` LIKE ? AND `publish_time` <= ? AND `status` IN (?,?)" , sql);
            List<BlogEntity> list = queryable.cloneQueryable().toList();
            Assert.assertEquals(0, list.size());
        }
    }

    @Test
    public void query9() {
        try (JdbcStreamResult<BlogEntity> streamResult = easyQuery.queryable(BlogEntity.class).where(o -> o.le(BlogEntity::getStar, 100)).orderByAsc(o -> o.column(BlogEntity::getCreateTime)).toStreamResult(100)) {

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
                Assert.assertEquals(0, new BigDecimal("1.2" ).compareTo(blog.getScore()));
                Assert.assertEquals(i % 3 == 0 ? 0 : 1, (int) blog.getStatus());
                Assert.assertEquals(0, new BigDecimal("1.2" ).multiply(BigDecimal.valueOf(i)).compareTo(blog.getOrder()));
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
        try (JdbcStreamResult<BlogEntity> streamResult = easyQuery.queryable(BlogEntity.class).where(o -> o.le(BlogEntity::getStar, 100)).orderByAsc(o -> o.column(BlogEntity::getCreateTime)).toStreamResult(x -> {
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
                Assert.assertEquals(0, new BigDecimal("1.2" ).compareTo(blog.getScore()));
                Assert.assertEquals(i % 3 == 0 ? 0 : 1, (int) blog.getStatus());
                Assert.assertEquals(0, new BigDecimal("1.2" ).multiply(BigDecimal.valueOf(i)).compareTo(blog.getOrder()));
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
        try (JdbcStreamResult<BlogEntity> streamResult = easyProxyQuery.queryable(BlogEntityProxy.createTable()).where(o -> o.star().le(100))
                .orderBy(o -> o.createTime().asc()).toStreamResult(100)) {
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
                Assert.assertEquals(0, new BigDecimal("1.2" ).compareTo(blog.getScore()));
                Assert.assertEquals(i % 3 == 0 ? 0 : 1, (int) blog.getStatus());
                Assert.assertEquals(0, new BigDecimal("1.2" ).multiply(BigDecimal.valueOf(i)).compareTo(blog.getOrder()));
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
        List<Topic> list = easyQuery.queryable(Topic.class).toList();
    }

    @Test
    public void userBookTest() {
        easyQuery.deletable(SysUserEncrypt.class)
                .whereByIds(Arrays.asList("1" , "2" ))
                .disableLogicDelete().executeRows();
        easyQuery.deletable(UserBookEncrypt.class)
                .whereByIds(Arrays.asList("1" , "2" , "3" , "4" ))
                .disableLogicDelete().executeRows();
        {

            SysUserEncrypt sysUser = new SysUserEncrypt();
            sysUser.setId("1" );
            sysUser.setName("用户1" );
            sysUser.setPhone("12345678901" );
            sysUser.setAddress("浙江省绍兴市越城区城市广场1234号" );
            sysUser.setCreateTime(LocalDateTime.now());
            ArrayList<UserBookEncrypt> userBooks = new ArrayList<>();
            UserBookEncrypt userBook = new UserBookEncrypt();
            userBook.setId("1" );
            userBook.setUserId("1" );
            userBook.setName("语文" );
            userBooks.add(userBook);
            UserBookEncrypt userBook1 = new UserBookEncrypt();
            userBook1.setId("2" );
            userBook1.setUserId("1" );
            userBook1.setName("数学" );
            userBooks.add(userBook1);
            easyQuery.insertable(sysUser).executeRows();
            easyQuery.insertable(userBooks).executeRows();
        }
        {

            SysUserEncrypt sysUser = new SysUserEncrypt();
            sysUser.setId("2" );
            sysUser.setName("用户2" );
            sysUser.setPhone("19012345678" );
            sysUser.setAddress("浙江省杭州市上城区武林广场1234号" );
            sysUser.setCreateTime(LocalDateTime.now());
            ArrayList<UserBookEncrypt> userBooks = new ArrayList<>();
            UserBookEncrypt userBook = new UserBookEncrypt();
            userBook.setId("3" );
            userBook.setUserId("2" );
            userBook.setName("语文" );
            userBooks.add(userBook);
            UserBookEncrypt userBook1 = new UserBookEncrypt();
            userBook1.setId("4" );
            userBook1.setUserId("2" );
            userBook1.setName("英语" );
            userBooks.add(userBook1);
            easyQuery.insertable(sysUser).executeRows();
            easyQuery.insertable(userBooks).executeRows();
        }


        List<UserBookEncryptVO> userBooks = easyQuery.queryable(UserBookEncrypt.class)
                .leftJoin(SysUserEncrypt.class, (t, t1) -> t.eq(t1, UserBookEncrypt::getUserId, SysUserEncrypt::getId))
                .where((t, t1) -> t1.like(SysUserEncrypt::getAddress, "越城区" ))
                .select(UserBookEncryptVO.class, (t, t1) -> t.columnAll()
                        .then(t1)
                        .columnAs(SysUserEncrypt::getName, UserBookEncryptVO::getUserName)
                        .columnAs(SysUserEncrypt::getPhone, UserBookEncryptVO::getUserPhone)
                        .columnAs(SysUserEncrypt::getAddress, UserBookEncryptVO::getUserAddress)
                )
                .toList();

        for (UserBookEncryptVO userBook : userBooks) {
            Assert.assertEquals("12345678901" , userBook.getUserPhone());
            Assert.assertEquals("浙江省绍兴市越城区城市广场1234号" , userBook.getUserAddress());
        }
        easyQuery.deletable(SysUserEncrypt.class)
                .whereByIds(Arrays.asList("1" , "2" ))
                .disableLogicDelete().executeRows();
        easyQuery.deletable(UserBookEncrypt.class)
                .whereByIds(Arrays.asList("1" , "2" , "3" , "4" ))
                .disableLogicDelete().executeRows();
    }

    @Test
    public void orTest() {
        String sql = easyQuery.queryable(Topic.class)
                .where(o -> o.and(
                        x -> x.like(false, Topic::getTitle, "123" )
                                .or()
                                .like(false, Topic::getId, "123" )
                )).toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic`" , sql);
    }

    @Test
    public void orTest1() {
        String sql = easyQuery.queryable(Topic.class)
                .where(o -> o.eq(Topic::getCreateTime, LocalDateTime.now()).and(
                        x -> x.like(false, Topic::getTitle, "123" )
                                .or()
                                .like(false, Topic::getId, "123" )
                                .or()
                                .like(false, Topic::getId, "123" )
                )).toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `create_time` = ?" , sql);
    }

    @Test
    public void orTest2() {
        String sql = easyQuery.queryable(Topic.class)
                .where(o -> o.eq(Topic::getCreateTime, LocalDateTime.now()).and(
                        x -> x.or().like(false, Topic::getTitle, "123" )
                                .or()
                                .like(false, Topic::getId, "123" )
                                .or()
                                .like(false, Topic::getId, "123" )
                )).toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `create_time` = ?" , sql);
    }

    @Test
    public void orTest3() {
        String sql = easyQuery.queryable(Topic.class)
                .where(o -> o.eq(false, Topic::getCreateTime, LocalDateTime.now()).and(
                        x -> x.or().like(false, Topic::getTitle, "123" )
                                .or()
                                .like(false, Topic::getId, "123" )
                                .or()
                                .like(false, Topic::getId, "123" )
                )).toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic`" , sql);
    }

    @Test
    public void orTest4() {
        String sql = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(false, BlogEntity::getCreateTime, LocalDateTime.now()).and(
                        x -> x.or().like(false, BlogEntity::getTitle, "123" )
                                .or()
                                .like(false, BlogEntity::getId, "123" )
                                .or()
                                .like(false, BlogEntity::getId, "123" )
                )).toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ?" , sql);
    }

    @Test
    public void orTest5() {
        EasyPageResult<BlogEntity> pageResult = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(false, BlogEntity::getCreateTime, LocalDateTime.now()).and(
                        x -> x.or().like(false, BlogEntity::getTitle, "123" )
                                .or()
                                .like(false, BlogEntity::getId, "123" )
                                .or()
                                .like(false, BlogEntity::getId, "123" )
                )).toPageResult(1, 10);
        Assert.assertEquals(10, pageResult.getData().size());
    }

    @Test
    public void extendsUserTest() throws NoSuchFieldException, IllegalAccessException {
        easyEntityQuery.queryable(EqUser.class)
                .where(e -> e._accc())
                .fetchBy(e -> e.FETCHER._accc());
        EntityMetadataManager entityMetadataManager = easyQuery.getRuntimeContext().getEntityMetadataManager();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(EqUser.class);
        DataReader dataReader = entityMetadata.getDataReader();
        Assert.assertNotNull(dataReader);
        Class<? extends DataReader> aClass = dataReader.getClass();
        for (int i = 0; i < 20; i++) {
            Field previousDataReader = aClass.getDeclaredField("previousDataReader" );
            Field nextDataReader = aClass.getDeclaredField("nextDataReader" );
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
        Assert.assertEquals("id" , first);

    }

//    @Test
//    public  void newPredicateTest(){
//                String sqlz1= easyProxyQuery
//                .queryable(TOPIC_TEST_PROXY)
//                .where1(t->{
//                    TopicTestProxy testProxy = t.t();
//                    //`id` = ? AND (`title` >= ? OR `id` >= ? OR (`id` = `title` AND `id` = ?)) AND `title` = `id`
//                    SQLPredicate and = testProxy.id().eq("123").and(testProxy.title().ge("432").or(testProxy.id().ge("432")).or(testProxy.id().eq(testProxy.title()).and(testProxy.id().eq("666"))));
//                    return and.and(testProxy.title().eq(testProxy.id()));
//                })
//                .orderByAsc(o->o.column(o.t().id()))
//                .select(s -> s.columns(s.t().id(), s.t().title()))
//                .toSQL();
//                Assert.assertEquals("SELECT `id`,`title` FROM `t_topic` WHERE `id` = ? AND (`title` >= ? OR `id` >= ? OR (`id` = `title` AND `id` = ?)) AND `title` = `id` ORDER BY `id` ASC",sqlz1);
//    }
//    @Test
//    public  void newPredicateTest1(){
//                String sqlz1= easyProxyQuery
//                .queryable(TOPIC_TEST_PROXY)
//                        .leftJoin(TopicAutoProxy.createTable(),o->o.eq(o.t().id(),o.t1().title()))
//                .where1(t->{
//                    TopicTestProxy testProxy = t.t();
//                    TopicAutoProxy topicAutoProxy = t.t1();
//                    //t.`id` = ? AND (t1.`title` >= ? OR t.`id` >= ? OR (t.`id` = t.`title` AND t.`id` = ?)) AND t.`title` = t1.`title`
//                    SQLPredicate and = testProxy.id().eq("123")
//                            .and(
//                                    topicAutoProxy.title().ge("432").or(
//                                            testProxy.id().ge("432")
//                                            )
//                                    .or(testProxy.id().eq(testProxy.title()).or(testProxy.title().ge("xxx")).and(testProxy.id().eq("666"))
//                                    )
//                            );
//                    return and.and(testProxy.title().eq(topicAutoProxy.title()));
//                })
//                .orderByAsc(o->o.column(o.t().id()))
//                .select(s -> s.columns(s.t().id(), s.t().title()))
//                .toSQL();
//                Assert.assertEquals("SELECT t.`id`,t.`title` FROM `t_topic` t LEFT JOIN `t_topic_auto` t1 ON t.`id` = t1.`title` WHERE t.`id` = ? AND (t1.`title` >= ? OR t.`id` >= ? OR (t.`id` = t.`title` OR t.`title` >= ? AND t.`id` = ?)) AND t.`title` = t1.`title` ORDER BY t.`id` ASC",sqlz1);
//    }
//
//    @Test
//    public  void newPredicateTest2(){
//                String sqlz1= easyProxyQuery
//                .queryable(TOPIC_TEST_PROXY)
//                        .leftJoin(TopicAutoProxy.createTable(),o->o.eq(o.t().id(),o.t1().title()))
//                .where1(t->{
//                    TopicTestProxy testProxy = t.t();
//                    TopicAutoProxy topicAutoProxy = t.t1();
//
//                    return testProxy.id().eq("123").and(false,topicAutoProxy.stars().eq(1)).or(topicAutoProxy.title().eq(topicAutoProxy.title()));
//
//                })
//                .orderByAsc(o->o.column(o.t().id()))
//                .select(s -> s.columns(s.t().id(), s.t().title()))
//                .toSQL();
//                Assert.assertEquals("SELECT t.`id`,t.`title` FROM `t_topic` t LEFT JOIN `t_topic_auto` t1 ON t.`id` = t1.`title` WHERE (t.`id` = ? OR t1.`title` = t1.`title`) ORDER BY t.`id` ASC",sqlz1);
//    }



    @Test
    public void newPredicateTest4() {
        String sqlz1 = easyQuery
                .queryable(Topic.class)
                .leftJoin(TopicAuto.class, (t, t1) -> t.eq(t1, Topic::getId, TopicAuto::getTitle))
                .where((t, t1) -> {

// t.`id` = ? AND t1.`stars` = ? AND (t1.`title` = t1.`title` AND (t1.`title` = t1.`title` OR t1.`stars` = ? AND t1.`title` = t1.`title`))


                    t.eq(Topic::getId, "123" )
                            .and(x -> {
                                x.and(y -> {
                                            y.eq(Topic::getTitle, "111" )
                                                    .eq(Topic::getId, "111" );
                                        })
                                        .or(z -> {
                                            z.eq(Topic::getTitle, "111" )
                                                    .eq(Topic::getId, "111" );
                                        });
                            });
//                    return testProxy.id().eq("123").and(
//                            topicAutoProxy.stars().eq(1).and(topicAutoProxy.title().eq(topicAutoProxy.title())).and(topicAutoProxy.title().eq(topicAutoProxy.title()))
//                                    .or(
//                                            topicAutoProxy.stars().eq(1)).and(topicAutoProxy.title().eq(topicAutoProxy.title())
//                                    )
//
//                    );


                })
                .toSQL();
        //SELECT t.`id`,t.`title` FROM `t_topic` t LEFT JOIN `t_topic_auto` t1 ON t.`id` = t1.`title` WHERE t.`id` = ? AND t1.`stars` = ? AND (t1.`title` = t1.`title` AND (t1.`title` = t1.`title` OR t1.`stars` = ? AND t1.`title` = t1.`title`)) ORDER BY t.`id` ASC
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic_auto` t1 ON t.`id` = t1.`title` WHERE t.`id` = ? AND ((t.`title` = ? AND t.`id` = ?) OR (t.`title` = ? AND t.`id` = ?))" , sqlz1);
    }

    @Test
    public void newPredicateTest5() {
        String sqlz1 = easyQuery
                .queryable(Topic.class)
                .leftJoin(TopicAuto.class, (t, t1) -> t.eq(t1, Topic::getId, TopicAuto::getTitle))
                .where((t, t1) -> {


                    t.eq(Topic::getId, "123" )
                            .and(t1, (x1, x2) -> {
                                x1.and(x2, (y1, y2) -> {
                                            y1.eq(Topic::getTitle, "111" )
                                                    .then(y2).eq(TopicAuto::getId, "111" );
                                        })
                                        .or(x2, (z1, z2) -> {
                                            z1.eq(Topic::getTitle, "111" )
                                                    .then(z2).eq(TopicAuto::getId, "111" );
                                        });
                            });


                    t.eq(Topic::getId, "123" );

                    t.and(t1, (a, b) -> {
                        a.eq(Topic::getTitle, 1).or();

                        b.eq(TopicAuto::getStars, 1);
                    });

                })
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic_auto` t1 ON t.`id` = t1.`title` WHERE t.`id` = ? AND ((t.`title` = ? AND t1.`id` = ?) OR (t.`title` = ? AND t1.`id` = ?)) AND t.`id` = ? AND (t.`title` = ? OR t1.`stars` = ?)" , sqlz1);
    }


    @Test
    public void testOrder() {
        String sql = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "123" ))
                .orderByDesc(o -> o.sqlNativeSegment(
                        "CASE \n" +
                                "    WHEN {0} > NOW() THEN TIMESTAMPDIFF(SECOND, NOW(), {0})\n" +
                                "    ELSE TIMESTAMPDIFF(SECOND, deadline, NOW())\n" +
                                "  END ASC" , c -> {
                            c.expression(BlogEntity::getPublishTime);
                        }).column(BlogEntity::getCreateTime))
                .toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` = ? ORDER BY CASE \n" +
                "    WHEN `publish_time` > NOW() THEN TIMESTAMPDIFF(SECOND, NOW(), `publish_time`)\n" +
                "    ELSE TIMESTAMPDIFF(SECOND, deadline, NOW())\n" +
                "  END ASC,`create_time` DESC" , sql);
    }

    @Test
    public void testSelectAs() {
        String sql = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "123" ))
                .select(Topic.class, o -> o.sqlNativeSegment("{0} AS {1},{2} AS {3}" , c -> {
                    c.expression(BlogEntity::getId)
                            .expressionAlias(Topic::getCreateTime)
                            .expression(BlogEntity::getScore)
                            .expressionAlias(Topic::getStars);
                }))
                .toSQL();
        Assert.assertEquals("SELECT t.`id` AS `create_time`,t.`score` AS `stars` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ?" , sql);
    }

    @Test
    public void testSelectAs1() {
        String sql = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "123" ))
                .select(Topic.class, o -> o.sqlNativeSegment("100 - {0}" , c -> {
                    c.expression(BlogEntity::getId).setPropertyAlias(Topic::getStars);
                }))
                .toSQL();
        Assert.assertEquals("SELECT 100 - t.`id` AS `stars` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ?" , sql);
    }

    @Test
    public void testOrder1() {
        String sql = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "123" ))
                .orderByAsc(o -> o.column(BlogEntity::getId).sqlNativeSegment("user_name {0}" , c -> c.format(1 == 1 ? "ASC" : "DESC" )))
                .toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` = ? ORDER BY `id` ASC,user_name ASC" , sql);
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
        id.put("id" , true);
        id.put("title" , false);
        String sql = easyQuery.queryable(BlogEntity.class)
                .orderByObject(new UISort(id))
                .toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? ORDER BY `id` ASC,`title` DESC" , sql);
    }

    @Test
    public void orderTest2() {
        HashMap<String, Boolean> id = new HashMap<String, Boolean>();
        id.put("id1" , true);
        id.put("title" , false);
        try {

            String sql = easyQuery.queryable(BlogEntity.class)
                    .orderByObject(new UISort(id))
                    .toSQL();
        } catch (EasyQueryOrderByInvalidOperationException exception) {
            Assert.assertEquals("id1" , exception.getPropertyName());
            Assert.assertEquals("BlogEntity not found [id1] in entity class" , exception.getMessage());
        }
    }

    @Test
    public void testGenericKey() {

        String sql = easyQuery
                .queryable(TopicGenericKey.class)
                .limit(1).toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` LIMIT 1" , sql);
    }

    @Test
    public void testGenericKey1() {
        List<TopicGenericKey> list = easyQuery
                .queryable(TopicGenericKey.class)
                .whereById("1" )
                .limit(1).toList();
        Assert.assertEquals(1, list.size());
        TopicGenericKey topicGenericKey = list.get(0);
        Assert.assertEquals("1" , topicGenericKey.getId());
    }

    @Test
    public void testGenericKey2() {
        PageResult<TopicGenericKey> pageResult = easyQuery
                .queryable(TopicGenericKey.class)
                .whereById("1" )
                .toPageResult(new MyPager<>(1, 2));

       var pageResult1 = easyQuery
                .queryable(TopicGenericKey.class)
                .whereById("1" )
                .select(TopicTypeVO.class)
                .toPageResult(new MyPager<>(1, 2));
        List<TopicTypeVO> list = pageResult1.getList();

        Assert.assertEquals(1, pageResult.getTotalCount());
        Assert.assertEquals("1" , pageResult.getList().get(0).getId());
    }
//    @Test
//    public void testGenericKey2_1() {
//        PageResult<TopicGenericKey> pageResult = easyQuery
//                .queryable(TopicGenericKey.class)
//                .whereById("1" )
//                .toPageResult(new MyLimitPager<>(1, 20));
//
//       var pageResult1 = easyQuery
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

        EasyPageResult<Map> pageResult = easyQuery.queryable("select * from t_topic" , Map.class)
                .toPageResult(1, 20);
        Assert.assertEquals(20, pageResult.getData().size());
        EasyPageResult<Map> pageResult1 = easyQuery.queryable("select * from t_topic" , Map.class)
                .toPageResult(2, 20);
        Assert.assertEquals(20, pageResult1.getData().size());
    }

    @Test
    public void nativePage2() {
        try {
            long count = easyQuery.queryable("select * from t_topicxx" , Map.class).count();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause = (EasyQuerySQLStatementException) ex1.getCause();
            Assert.assertEquals("SELECT COUNT(*) FROM (select * from t_topicxx) t" , cause.getSQL());
        }
        try {
            List<Map> list = easyQuery.queryable("select * from t_topicxx" , Map.class).limit(1, 10).toList();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause = (EasyQuerySQLStatementException) ex1.getCause();
            Assert.assertEquals("SELECT * FROM (select * from t_topicxx) t LIMIT 10 OFFSET 1" , cause.getSQL());
        }
        try {
            List<Map> list = easyQuery.queryable("select * from t_topicxx" , Map.class).limit(2, 10).toList();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause = (EasyQuerySQLStatementException) ex1.getCause();
            Assert.assertEquals("SELECT * FROM (select * from t_topicxx) t LIMIT 10 OFFSET 2" , cause.getSQL());
        }

    }

    @Test
    public void nativePage3() {

        EasyPageResult<Map> pageResult = easyQuery.queryable("select * from t_topic where id = ?" , Map.class, Arrays.asList("1" ))
                .toPageResult(1, 20);
        Assert.assertEquals(1, pageResult.getData().size());
        Assert.assertEquals(1, pageResult.getTotal());
    }

    @Test
    public void nativePage4() {

        EasyPageResult<BlogEntity> pageResult = easyQuery.queryable("select * from t_blog where id = ?" , BlogEntity.class, Arrays.asList("1" ))
                .where(o -> o.like(BlogEntity::getContent, "-" ))
                .toPageResult(1, 20);
        Assert.assertEquals(1, pageResult.getData().size());
        Assert.assertEquals(1, pageResult.getTotal());
    }

    @Test
    public void nativePage5() {

        EasyPageResult<BlogEntity> pageResult = easyQuery.queryable("select * from t_blog where id = ?" , BlogEntity.class, Arrays.asList("1" ))
                .where(o -> o.like(BlogEntity::getContent, "-" ))
                .select(BlogEntity.class, o -> o.column(BlogEntity::getId).sqlNativeSegment("CONCAT({0},{1},{2})" , c -> {
                    c.value("MySQL" )
                            .value("5.7-" ).expression(BlogEntity::getId)
                            .setPropertyAlias(BlogEntity::getContent);
                }))
                .toPageResult(1, 20);
        Assert.assertEquals(1, pageResult.getData().size());
        Assert.assertEquals(1, pageResult.getTotal());
        BlogEntity blogEntity = pageResult.getData().get(0);
        Assert.assertEquals("1" , blogEntity.getId());
        Assert.assertEquals("MySQL5.7-1" , blogEntity.getContent());
    }

    @Test
    public void nativePage6() {

        EasyPageResult<BlogEntity> pageResult = easyQuery.queryable("select * from t_blog where id = ?" , BlogEntity.class, Arrays.asList("1" ))
                .where(o -> o.like(BlogEntity::getContent, "-" ))
                .select(BlogEntity.class, o -> o.column(BlogEntity::getId).sqlNativeSegment("CONCAT({0},{1},{2})" , c -> {
                    c.format("'MySQL'" )
                            .format("'5.7-'" ).expression(BlogEntity::getId)
                            .setPropertyAlias(BlogEntity::getContent);
                }))
                .toPageResult(1, 20);
        Assert.assertEquals(1, pageResult.getData().size());
        Assert.assertEquals(1, pageResult.getTotal());
        BlogEntity blogEntity = pageResult.getData().get(0);
        Assert.assertEquals("1" , blogEntity.getId());
        Assert.assertEquals("MySQL5.7-1" , blogEntity.getContent());
    }

}
