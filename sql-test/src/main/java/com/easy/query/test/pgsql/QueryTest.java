package com.easy.query.test.pgsql;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.func.def.enums.TimeUnitEnum;
import com.easy.query.core.proxy.SQLMathExpression;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.core.draft.Draft7;
import com.easy.query.core.proxy.part.Part1;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.BlogPartitionVO;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/5/10 16:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest extends PgSQLBaseTest {

    @Test
    public void query1() {
        EntityQueryable<BlogEntityProxy, BlogEntity> queryable = entityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq("123"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT \"id\",\"create_time\",\"update_time\",\"create_by\",\"update_by\",\"deleted\",\"title\",\"content\",\"url\",\"star\",\"publish_time\",\"score\",\"status\",\"order\",\"is_top\",\"top\" FROM \"t_blog\" WHERE \"deleted\" = ? AND \"id\" = ?", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNull(blogEntity);

    }

    @Test
    public void query2() {
        EntityQueryable<BlogEntityProxy, BlogEntity> queryable = entityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq( "97"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT \"id\",\"create_time\",\"update_time\",\"create_by\",\"update_by\",\"deleted\",\"title\",\"content\",\"url\",\"star\",\"publish_time\",\"score\",\"status\",\"order\",\"is_top\",\"top\" FROM \"t_blog\" WHERE \"deleted\" = ? AND \"id\" = ?", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNotNull(blogEntity);
        Assert.assertEquals("97", blogEntity.getId());
    }

    @Test
    public void query3() {
        EntityQueryable<BlogEntityProxy, BlogEntity> queryable = entityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq( "97")).limit(1).select(" 1 ");
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT  1  FROM \"t_blog\" WHERE \"deleted\" = ? AND \"id\" = ? LIMIT 1", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNotNull(blogEntity);
        Assert.assertNull(blogEntity.getId());
    }

    @Test
    public void query4() {
        EntityQueryable<BlogEntityProxy, BlogEntity> queryable = entityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq( "97")).limit(1).select("1 as id");
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT 1 as id FROM \"t_blog\" WHERE \"deleted\" = ? AND \"id\" = ? LIMIT 1", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNotNull(blogEntity);
        Assert.assertEquals("1", blogEntity.getId());
    }

    @Test
    public void query5() {
        EntityQueryable<BlogEntityProxy, BlogEntity> queryable = entityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().in(Arrays.asList("97", "98")));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT \"id\",\"create_time\",\"update_time\",\"create_by\",\"update_by\",\"deleted\",\"title\",\"content\",\"url\",\"star\",\"publish_time\",\"score\",\"status\",\"order\",\"is_top\",\"top\" FROM \"t_blog\" WHERE \"deleted\" = ? AND \"id\" IN (?,?)", sql);
        List<BlogEntity> blogs = queryable.toList();
        Assert.assertNotNull(blogs);
        Assert.assertEquals(2, blogs.size());
        Assert.assertEquals(false, blogs.get(0).getIsTop());
        Assert.assertEquals(false, blogs.get(0).getTop());
        Assert.assertEquals(true, blogs.get(1).getIsTop());
        Assert.assertEquals(true, blogs.get(1).getTop());
    }

    @Test
    public void query6() {
        EntityQueryable<BlogEntityProxy, BlogEntity> queryable = entityQuery.queryable(BlogEntity.class)
                .whereById("97");
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT \"id\",\"create_time\",\"update_time\",\"create_by\",\"update_by\",\"deleted\",\"title\",\"content\",\"url\",\"star\",\"publish_time\",\"score\",\"status\",\"order\",\"is_top\",\"top\" FROM \"t_blog\" WHERE \"deleted\" = ? AND \"id\" = ?", sql);
        List<BlogEntity> blogs = queryable.toList();
        Assert.assertNotNull(blogs);
        Assert.assertEquals(1, blogs.size());
        Assert.assertEquals("97", blogs.get(0).getId());
        Assert.assertEquals(false, blogs.get(0).getIsTop());
        Assert.assertEquals(false, blogs.get(0).getTop());
    }

    @Test
    public void query7() {
        List<BlogEntity> blogEntities = entityQuery.queryable(BlogEntity.class).orderBy(o -> o.createTime().asc()).toList();
        LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
        int i = 0;
        for (BlogEntity blog : blogEntities) {
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
    }


    @Test
    public void query8() {
        Query<String> queryable = entityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq("97"))
                .selectColumn(s -> s.id().concat(s.id()));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT CONCAT(t.\"id\",t.\"id\") FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"id\" = ?", sql);
        String blogEntity = queryable.firstOrNull();
        Assert.assertNotNull(blogEntity);
        Assert.assertEquals("9797", blogEntity);
    }
    @Test
    public void query9() {
        Query<String> queryable = entityQuery.queryable(BlogEntity.class)
                .where(o -> o.id().eq( "97"))
                .selectColumn(s -> s.id().concat(s.title()));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT CONCAT(t.\"id\",t.\"title\") FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"id\" = ?", sql);
        String blogEntity = queryable.firstOrNull();
        Assert.assertNotNull(blogEntity);
        Assert.assertEquals("97title97", blogEntity);
    }
//    @Test
//    public void query10() {
//        Queryable<String> queryable = entityQuery.queryable(BlogEntity.class)
//                .where(o -> o.id().eq( "97"))
//                .select(String.class,o->o.func(o.sqlFunc().join(",",BlogEntity::getId,BlogEntity::getTitle)));
//        String sql = queryable.toSQL();
//        Assert.assertEquals("SELECT t.\"id\" || ? || t.\"title\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"id\" = ?", sql);
//        String blogEntity = queryable.firstOrNull();
//        Assert.assertNotNull(blogEntity);
//        Assert.assertEquals("97,title97", blogEntity);
//    }
@Test
public void query10() {
    Query<String> queryable = entityQuery.queryable(BlogEntity.class)
            .where(o -> o.id().eq( "97"))
            .selectColumn(t_blog -> t_blog.id().nullOrDefault("1"));
    String sql = queryable.toSQL();
    Assert.assertEquals("SELECT COALESCE(t.\"id\",?) FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"id\" = ?", sql);
}



    @Test
    public void testDraft9() {
        String id = "123456zz9";
        entityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
        BlogEntity blog = new BlogEntity();
        blog.setId(id);
        blog.setCreateBy("z" );
        blog.setCreateTime(LocalDateTime.of(2022, 1, 2, 3, 4, 5));
        blog.setUpdateBy("z" );
        blog.setUpdateTime(LocalDateTime.of(2022, 2, 3, 4, 5, 6));
        blog.setTitle("titlez" );
        blog.setContent("contentz" );
        blog.setUrl("http://blog.easy-query.com/z" );
        blog.setStar(1);
        blog.setScore(new BigDecimal("1.2" ));
        blog.setStatus(1);
        blog.setOrder(new BigDecimal("1.2" ).multiply(BigDecimal.valueOf(1)));
        blog.setIsTop(false);
        blog.setTop(true);
        blog.setDeleted(false);
        entityQuery.insertable(blog)
                .executeRows();
        Draft3<LocalDateTime, LocalDateTime, LocalDateTime> draft31 = entityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        o.createTime().plus(1, TimeUnitEnum.DAYS),
                        o.createTime().plus(2, TimeUnitEnum.SECONDS),
                        o.createTime().plus(3, TimeUnitEnum.MINUTES)
                )).firstOrNull();

        Draft7<Long, Long, Long, Long, Long, Long, Long> draft3 = entityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        o.updateTime().duration(o.createTime()).toDays(),
                        o.updateTime().duration(o.createTime()).toHours(),
                        o.updateTime().duration(o.createTime()).toMinutes(),
                        o.updateTime().duration(o.createTime()).toSeconds(),
                        o.createTime().plus(1,TimeUnitEnum.DAYS).duration(o.createTime()).toDays(),
                        o.createTime().plus(2,TimeUnitEnum.SECONDS).duration(o.createTime()).toSeconds(),
                        o.createTime().plus(3,TimeUnitEnum.MINUTES).duration(o.createTime()).toMinutes()
                )).firstOrNull();

        Assert.assertNotNull(draft3);
        Long value1 = draft3.getValue1();
        Assert.assertEquals(-32, (long) value1);
        Long value2 = draft3.getValue2();
        Assert.assertEquals(-769, (long) value2);
        Long value3 = draft3.getValue3();
        Assert.assertEquals(-46141, (long) value3);
        Long value4 = draft3.getValue4();
        Assert.assertEquals(-2768461, (long) value4);
        Long value5 = draft3.getValue5();
        Assert.assertEquals(-1, (long) value5);
        Long value6 = draft3.getValue6();
        Assert.assertEquals(-2, (long) value6);
        Long value7 = draft3.getValue7();
        Assert.assertEquals(-3, (long) value7);
        Draft7<Long, Long, Long, Long, Long, Long, Long> draft4 = entityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        SQLMathExpression.abs(o.updateTime().duration(o.createTime()).toDays()),
                        o.updateTime().duration(o.createTime()).toHours(),
                        o.updateTime().duration(o.createTime()).toMinutes(),
                        o.updateTime().duration(o.createTime()).toSeconds(),
                        o.createTime().plus(1,TimeUnitEnum.DAYS).duration(o.createTime()).toDays(),
                        o.createTime().plus(2,TimeUnitEnum.SECONDS).duration(o.createTime()).toSeconds(),
                        o.createTime().plus(1,TimeUnitEnum.DAYS).plus(3,TimeUnitEnum.MINUTES).duration(o.createTime()).toMinutes()
                )).firstOrNull();
        Assert.assertEquals(-1443,(long)draft4.getValue7());

        entityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
    }



    @Test
    public void testDraft9_1() {
        String id = "123456zz91";
        entityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
        BlogEntity blog = new BlogEntity();
        blog.setId(id);
        blog.setCreateBy("z" );
        blog.setCreateTime(LocalDateTime.of(2022, 1, 2, 3, 4, 5));
        blog.setUpdateBy("z" );
        blog.setUpdateTime(LocalDateTime.of(2022, 2, 3, 4, 5, 6));
        blog.setTitle("titlez" );
        blog.setContent("contentz" );
        blog.setUrl("http://blog.easy-query.com/z" );
        blog.setStar(1);
        blog.setScore(new BigDecimal("1.2" ));
        blog.setStatus(1);
        blog.setOrder(new BigDecimal("1.2" ).multiply(BigDecimal.valueOf(1)));
        blog.setIsTop(false);
        blog.setTop(true);
        blog.setDeleted(false);
        entityQuery.insertable(blog)
                .executeRows();
        Draft3<LocalDateTime, LocalDateTime, LocalDateTime> draft31 = entityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        o.createTime().plus(1, TimeUnitEnum.DAYS),
                        o.createTime().plus(2, TimeUnitEnum.SECONDS),
                        o.createTime().plus(3, TimeUnitEnum.MINUTES)
                )).firstOrNull();

        Draft7<Long, Long, Long, Long, Long, Long, Long> draft3 = entityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        o.updateTime().duration(o.createTime()).toDays(),
                        o.updateTime().duration(o.createTime()).toHours(),
                        o.updateTime().duration(o.createTime()).toMinutes(),
                        o.updateTime().duration(o.createTime()).toSeconds(),
                        o.createTime().plus(1,TimeUnitEnum.DAYS).duration(o.createTime()).toDays(),
                        o.createTime().plus(2,TimeUnitEnum.SECONDS).duration(o.createTime()).toSeconds(),
                        o.createTime().plus(3,TimeUnitEnum.MINUTES).duration(o.createTime()).toMinutes()
                ))
//                .select(o -> Select.DRAFT.of(
//                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Days),
//                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Hours),
//                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Minutes),
//                        o.createTime().duration(o.updateTime(), DateTimeDurationEnum.Seconds),
//                        o.createTime().duration(o.createTime().plus(1,TimeUnitEnum.DAYS), DateTimeDurationEnum.Days),
//                        o.createTime().duration(o.createTime().plus(2,TimeUnitEnum.SECONDS),DateTimeDurationEnum.Seconds),
//                        o.createTime().duration(o.createTime().plus(3,TimeUnitEnum.MINUTES),DateTimeDurationEnum.Minutes)
//                ))
                .firstOrNull();

        Assert.assertNotNull(draft3);
        Long value1 = draft3.getValue1();
        Assert.assertEquals(-32, (long) value1);
        Long value2 = draft3.getValue2();
        Assert.assertEquals(-769, (long) value2);
        Long value3 = draft3.getValue3();
        Assert.assertEquals(-46141, (long) value3);
        Long value4 = draft3.getValue4();
        Assert.assertEquals(-2768461, (long) value4);
        Long value5 = draft3.getValue5();
        Assert.assertEquals(-1, (long) value5);
        Long value6 = draft3.getValue6();
        Assert.assertEquals(-2, (long) value6);
        Long value7 = draft3.getValue7();
        Assert.assertEquals(-3, (long) value7);
        Draft7<Long, Long, Long, Long, Long, Long, Long> draft4 = entityQuery.queryable(BlogEntity.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        SQLMathExpression.abs(o.updateTime().duration(o.createTime()).toDays()),
                        o.updateTime().duration(o.createTime()).toHours(),
                        o.updateTime().duration(o.createTime()).toMinutes(),
                        o.updateTime().duration(o.createTime()).toSeconds(),
                        o.createTime().plus(1,TimeUnitEnum.DAYS).duration(o.createTime()).toDays(),
                        o.createTime().plus(2,TimeUnitEnum.SECONDS).duration(o.createTime()).toSeconds(),
                        o.createTime().plus(1,TimeUnitEnum.DAYS).plus(3,TimeUnitEnum.MINUTES).duration(o.createTime()).toMinutes()
                )).firstOrNull();
        Assert.assertEquals(-1443,(long)draft4.getValue7());

        entityQuery.deletable(BlogEntity.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
    }
    @Test
    public void test123(){

        List<BlogEntity> yyyy年MM月dd日 = entityQuery.queryable(BlogEntity.class)
                .where(m -> {
                    m.or(()->{
                        m.createTime().format("yyyy年MM月dd日").eq("2022年01月01日");
                        m.id().isNotNull();
                    });
                }).toList();
        String sql = entityQuery.queryable(BlogEntity.class)
                .where(m -> {
                    m.createTime().format("yyyy年MM月dd日").eq("2022年01月01日");
                }).toSQL();
        Assert.assertEquals("SELECT \"id\",\"create_time\",\"update_time\",\"create_by\",\"update_by\",\"deleted\",\"title\",\"content\",\"url\",\"star\",\"publish_time\",\"score\",\"status\",\"order\",\"is_top\",\"top\" FROM \"t_blog\" WHERE \"deleted\" = ? AND TO_CHAR((\"create_time\")::TIMESTAMP,'YYYY年MM月DD日') = ?",sql);
    }
    @Test
    public void testPartitionBy1(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Part1<BlogEntity, Long>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PART.of(
                        b,
                        b.expression().rowNumberOver().partitionBy(b.title()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2L);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"id\",t1.\"create_time\",t1.\"update_time\",t1.\"create_by\",t1.\"update_by\",t1.\"deleted\",t1.\"title\",t1.\"content\",t1.\"url\",t1.\"star\",t1.\"publish_time\",t1.\"score\",t1.\"status\",t1.\"order\",t1.\"is_top\",t1.\"top\",t1.\"__part__column1\" AS \"__part__column1\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(ROW_NUMBER() OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__part__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Part1<BlogEntity, Long> blogEntityLongPart1 : list) {

            BlogEntity entity = blogEntityLongPart1.getEntity();
            Assert.assertNotNull(entity);
            Long partitionColumn1 = blogEntityLongPart1.getPartColumn1();
            Assert.assertNotNull(partitionColumn1);
            Assert.assertEquals(1L, (long) partitionColumn1);

        }
        System.out.println(list);
    }
    @Test
    public void testPartitionBy2(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<Long, String>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PART.of(
                        b,
                        b.expression().rowNumberOver().partitionBy(b.title()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2L);
                }).select(p -> Select.DRAFT.of(
                        p.partColumn1(),
                        p.entityTable().id()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"__part__column1\" AS \"value1\",t1.\"id\" AS \"value2\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(ROW_NUMBER() OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__part__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Draft2<Long, String> longStringDraft2 : list) {
            String value2 = longStringDraft2.getValue2();
            Long value1 = longStringDraft2.getValue1();
        }
        System.out.println(list);
    }


    @Test
    public void testPartitionBy3(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Part1<BlogEntity, Long>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PART.of(
                        b,
                        b.expression().rankOver().partitionBy(b.title()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2L);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"id\",t1.\"create_time\",t1.\"update_time\",t1.\"create_by\",t1.\"update_by\",t1.\"deleted\",t1.\"title\",t1.\"content\",t1.\"url\",t1.\"star\",t1.\"publish_time\",t1.\"score\",t1.\"status\",t1.\"order\",t1.\"is_top\",t1.\"top\",t1.\"__part__column1\" AS \"__part__column1\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(RANK() OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__part__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Part1<BlogEntity, Long> blogEntityLongPart1 : list) {

            BlogEntity entity = blogEntityLongPart1.getEntity();
            Assert.assertNotNull(entity);
            Long partitionColumn1 = blogEntityLongPart1.getPartColumn1();
            Assert.assertNotNull(partitionColumn1);
            Assert.assertEquals(1L, (long) partitionColumn1);

        }
        System.out.println(list);
    }
    @Test
    public void testPartitionBy3_1(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Part1<BlogEntity, Long>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PART.of(
                        b,
                        b.expression().rankOver().partitionBy(b.title()).orderBy(b.createTime().nullOrDefault(LocalDateTime.of(2020, 1, 1, 1, 1)))
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2L);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"id\",t1.\"create_time\",t1.\"update_time\",t1.\"create_by\",t1.\"update_by\",t1.\"deleted\",t1.\"title\",t1.\"content\",t1.\"url\",t1.\"star\",t1.\"publish_time\",t1.\"score\",t1.\"status\",t1.\"order\",t1.\"is_top\",t1.\"top\",t1.\"__part__column1\" AS \"__part__column1\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(RANK() OVER (PARTITION BY t.\"title\" ORDER BY COALESCE(t.\"create_time\",?) ASC)) AS \"__part__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2020-01-01T01:01(LocalDateTime),false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Part1<BlogEntity, Long> blogEntityLongPart1 : list) {

            BlogEntity entity = blogEntityLongPart1.getEntity();
            Assert.assertNotNull(entity);
            Long partitionColumn1 = blogEntityLongPart1.getPartColumn1();
            Assert.assertNotNull(partitionColumn1);
            Assert.assertEquals(1L, (long) partitionColumn1);

        }
        System.out.println(list);
    }
    @Test
    public void testPartitionBy3_2(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogPartitionVO> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PART.of(
                        b,
                        b.expression().rankOver().partitionBy(b.title()).orderBy(b.createTime().nullOrDefault(LocalDateTime.of(2020, 1, 1, 1, 1)))
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2L);
                }).select(BlogPartitionVO.class,partition->Select.of(
                        partition.entityTable().FETCHER.allFields(),
                        partition.partColumn1().as(BlogPartitionVO::getRank)
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"title\",t1.\"url\",t1.\"__part__column1\" AS \"rank\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(RANK() OVER (PARTITION BY t.\"title\" ORDER BY COALESCE(t.\"create_time\",?) ASC)) AS \"__part__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2020-01-01T01:01(LocalDateTime),false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (BlogPartitionVO blogPartitionVO : list) {
            Assert.assertNull(blogPartitionVO.getContent());
            Assert.assertNotNull(blogPartitionVO.getTitle());
            Assert.assertNotNull(blogPartitionVO.getUrl());
            Assert.assertNotNull(blogPartitionVO.getRank());
        }
        System.out.println(list);
    }
    @Test
    public void testPartitionBy4(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<Long, String>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PART.of(
                        b,
                        b.expression().rankOver().partitionBy(b.title()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2L);
                }).select(p -> Select.DRAFT.of(
                        p.partColumn1(),
                        p.entityTable().id()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"__part__column1\" AS \"value1\",t1.\"id\" AS \"value2\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(RANK() OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__part__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Draft2<Long, String> longStringDraft2 : list) {
            String value2 = longStringDraft2.getValue2();
            Long value1 = longStringDraft2.getValue1();
        }
        System.out.println(list);
    }



    @Test
    public void testPartitionBy5(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Part1<BlogEntity, Long>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PART.of(
                        b,
                        b.expression().denseRankOver().partitionBy(b.title()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2L);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"id\",t1.\"create_time\",t1.\"update_time\",t1.\"create_by\",t1.\"update_by\",t1.\"deleted\",t1.\"title\",t1.\"content\",t1.\"url\",t1.\"star\",t1.\"publish_time\",t1.\"score\",t1.\"status\",t1.\"order\",t1.\"is_top\",t1.\"top\",t1.\"__part__column1\" AS \"__part__column1\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(DENSE_RANK() OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__part__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Part1<BlogEntity, Long> blogEntityLongPart1 : list) {

            BlogEntity entity = blogEntityLongPart1.getEntity();
            Assert.assertNotNull(entity);
            Long partitionColumn1 = blogEntityLongPart1.getPartColumn1();
            Assert.assertNotNull(partitionColumn1);
            Assert.assertEquals(1L, (long) partitionColumn1);

        }
        System.out.println(list);
    }
    @Test
    public void testPartitionBy6(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<Long, String>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PART.of(
                        b,
                        b.expression().denseRankOver().partitionBy(b.title()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2L);
                }).select(p -> Select.DRAFT.of(
                        p.partColumn1(),
                        p.entityTable().id()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"__part__column1\" AS \"value1\",t1.\"id\" AS \"value2\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(DENSE_RANK() OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__part__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Draft2<Long, String> longStringDraft2 : list) {
            String value2 = longStringDraft2.getValue2();
            Long value1 = longStringDraft2.getValue1();
        }
        System.out.println(list);
    }



    @Test
    public void testPartitionBy7(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Part1<BlogEntity, Integer>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PART.of(
                        b,
                        b.expression().sumOver(b.star()).partitionBy(b.title()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"id\",t1.\"create_time\",t1.\"update_time\",t1.\"create_by\",t1.\"update_by\",t1.\"deleted\",t1.\"title\",t1.\"content\",t1.\"url\",t1.\"star\",t1.\"publish_time\",t1.\"score\",t1.\"status\",t1.\"order\",t1.\"is_top\",t1.\"top\",t1.\"__part__column1\" AS \"__part__column1\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(SUM(t.\"star\") OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__part__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Part1<BlogEntity, Integer> blogEntityLongPart1 : list) {

            BlogEntity entity = blogEntityLongPart1.getEntity();
            Assert.assertNotNull(entity);
            Integer partitionColumn1 = blogEntityLongPart1.getPartColumn1();
            Assert.assertNotNull(partitionColumn1);
            Assert.assertEquals(1, (int)partitionColumn1);

        }
        System.out.println(list);
    }
    @Test
    public void testPartitionBy8(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<Integer, String>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PART.of(
                        b,
                        b.expression().sumOver(b.star()).partitionBy(b.title()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2);
                }).select(p -> Select.DRAFT.of(
                        p.partColumn1(),
                        p.entityTable().id()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"__part__column1\" AS \"value1\",t1.\"id\" AS \"value2\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(SUM(t.\"star\") OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__part__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Draft2<Integer, String> longStringDraft2 : list) {
            String value2 = longStringDraft2.getValue2();
            Integer value1 = longStringDraft2.getValue1();
        }
        System.out.println(list);
    }


    @Test
    public void testPartitionBy9(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Part1<BlogEntity, BigDecimal>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PART.of(
                        b,
                        b.expression().avgOver(b.star()).partitionBy(b.title()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(BigDecimal.valueOf(2));
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"id\",t1.\"create_time\",t1.\"update_time\",t1.\"create_by\",t1.\"update_by\",t1.\"deleted\",t1.\"title\",t1.\"content\",t1.\"url\",t1.\"star\",t1.\"publish_time\",t1.\"score\",t1.\"status\",t1.\"order\",t1.\"is_top\",t1.\"top\",t1.\"__part__column1\" AS \"__part__column1\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(AVG(t.\"star\") OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__part__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Part1<BlogEntity, BigDecimal> blogEntityLongPart1 : list) {

            BlogEntity entity = blogEntityLongPart1.getEntity();
            Assert.assertNotNull(entity);
            BigDecimal partitionColumn1 = blogEntityLongPart1.getPartColumn1();
            Assert.assertNotNull(partitionColumn1);
            Assert.assertEquals(0,BigDecimal.ONE.compareTo(partitionColumn1));

        }
        System.out.println(list);
    }
    @Test
    public void testPartitionBy10(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<BigDecimal, String>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PART.of(
                        b,
                        b.expression().avgOver(b.star()).partitionBy(b.title()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(BigDecimal.valueOf(2));
                }).select(p -> Select.DRAFT.of(
                        p.partColumn1(),
                        p.entityTable().id()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"__part__column1\" AS \"value1\",t1.\"id\" AS \"value2\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(AVG(t.\"star\") OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__part__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Draft2<BigDecimal, String> longStringDraft2 : list) {
            String value2 = longStringDraft2.getValue2();
            BigDecimal value1 = longStringDraft2.getValue1();
        }
        System.out.println(list);
    }



    @Test
    public void testPartitionBy11(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Part1<BlogEntity, Integer>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PART.of(
                        b,
                        b.expression().maxOver(b.star()).partitionBy(b.title()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"id\",t1.\"create_time\",t1.\"update_time\",t1.\"create_by\",t1.\"update_by\",t1.\"deleted\",t1.\"title\",t1.\"content\",t1.\"url\",t1.\"star\",t1.\"publish_time\",t1.\"score\",t1.\"status\",t1.\"order\",t1.\"is_top\",t1.\"top\",t1.\"__part__column1\" AS \"__part__column1\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(MAX(t.\"star\") OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__part__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Part1<BlogEntity, Integer> blogEntityLongPart1 : list) {

            BlogEntity entity = blogEntityLongPart1.getEntity();
            Assert.assertNotNull(entity);
            Integer partitionColumn1 = blogEntityLongPart1.getPartColumn1();
            Assert.assertNotNull(partitionColumn1);
            Assert.assertEquals(1,(int)partitionColumn1);

        }
        System.out.println(list);
    }
    @Test
    public void testPartitionBy12(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<Integer, String>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PART.of(
                        b,
                        b.expression().maxOver(b.star()).partitionBy(b.title()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2);
                }).select(p -> Select.DRAFT.of(
                        p.partColumn1(),
                        p.entityTable().id()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"__part__column1\" AS \"value1\",t1.\"id\" AS \"value2\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(MAX(t.\"star\") OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__part__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Draft2<Integer, String> longStringDraft2 : list) {
            String value2 = longStringDraft2.getValue2();
            Integer value1 = longStringDraft2.getValue1();
        }
        System.out.println(list);
    }

    @Test
    public void testPartitionBy13(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Part1<BlogEntity, Integer>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PART.of(
                        b,
                        b.expression().minOver(b.star()).partitionBy(b.title()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"id\",t1.\"create_time\",t1.\"update_time\",t1.\"create_by\",t1.\"update_by\",t1.\"deleted\",t1.\"title\",t1.\"content\",t1.\"url\",t1.\"star\",t1.\"publish_time\",t1.\"score\",t1.\"status\",t1.\"order\",t1.\"is_top\",t1.\"top\",t1.\"__part__column1\" AS \"__part__column1\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(MIN(t.\"star\") OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__part__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Part1<BlogEntity, Integer> blogEntityLongPart1 : list) {

            BlogEntity entity = blogEntityLongPart1.getEntity();
            Assert.assertNotNull(entity);
            Integer partitionColumn1 = blogEntityLongPart1.getPartColumn1();
            Assert.assertNotNull(partitionColumn1);
            Assert.assertEquals(1,(int)partitionColumn1);

        }
        System.out.println(list);
    }
    @Test
    public void testPartitionBy14(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<Integer, String>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PART.of(
                        b,
                        b.expression().minOver(b.star()).partitionBy(b.title()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2);
                }).select(p -> Select.DRAFT.of(
                        p.partColumn1(),
                        p.entityTable().id()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"__part__column1\" AS \"value1\",t1.\"id\" AS \"value2\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(MIN(t.\"star\") OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__part__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Draft2<Integer, String> longStringDraft2 : list) {
            String value2 = longStringDraft2.getValue2();
            Integer value1 = longStringDraft2.getValue1();
        }
        System.out.println(list);
    }


    @Test
    public void testPartitionBy15(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Part1<BlogEntity, Long>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PART.of(
                        b,
                        b.expression().countOver(b.star()).partitionBy(b.title()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2L);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"id\",t1.\"create_time\",t1.\"update_time\",t1.\"create_by\",t1.\"update_by\",t1.\"deleted\",t1.\"title\",t1.\"content\",t1.\"url\",t1.\"star\",t1.\"publish_time\",t1.\"score\",t1.\"status\",t1.\"order\",t1.\"is_top\",t1.\"top\",t1.\"__part__column1\" AS \"__part__column1\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(COUNT(t.\"star\") OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__part__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Part1<BlogEntity, Long> blogEntityLongPart1 : list) {

            BlogEntity entity = blogEntityLongPart1.getEntity();
            Assert.assertNotNull(entity);
            Long partitionColumn1 = blogEntityLongPart1.getPartColumn1();
            Assert.assertNotNull(partitionColumn1);
            Assert.assertEquals(1L,(long)partitionColumn1);

        }
        System.out.println(list);
    }
    @Test
    public void testPartitionBy16(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<Long, String>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PART.of(
                        b,
                        b.expression().countOver(b.star()).partitionBy(b.title()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2L);
                }).select(p -> Select.DRAFT.of(
                        p.partColumn1(),
                        p.entityTable().id()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"__part__column1\" AS \"value1\",t1.\"id\" AS \"value2\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(COUNT(t.\"star\") OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__part__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Draft2<Long, String> longStringDraft2 : list) {
            String value2 = longStringDraft2.getValue2();
            Long value1 = longStringDraft2.getValue1();
        }
        System.out.println(list);
    }
    @Test
    public void testPartitionBy16_1(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<Long, String>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PART.of(
                        b,
                        b.expression().countOver(b.star()).partitionBy(b.title())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2L);
                }).select(p -> Select.DRAFT.of(
                        p.partColumn1(),
                        p.entityTable().id()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"__part__column1\" AS \"value1\",t1.\"id\" AS \"value2\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(COUNT(t.\"star\") OVER (PARTITION BY t.\"title\")) AS \"__part__column1\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Draft2<Long, String> longStringDraft2 : list) {
            String value2 = longStringDraft2.getValue2();
            Long value1 = longStringDraft2.getValue1();
        }
        System.out.println(list);
    }

    @Test
    public void testNullDefault(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.id().nullOrDefault("123").eq("123");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT \"id\",\"create_time\",\"update_time\",\"create_by\",\"update_by\",\"deleted\",\"title\",\"content\",\"url\",\"star\",\"publish_time\",\"score\",\"status\",\"order\",\"is_top\",\"top\" FROM \"t_blog\" WHERE \"deleted\" = ? AND COALESCE(\"id\",?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }



    @Test
    public void testToWithAs2(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


            ClientQueryable<BlogEntity> topicSQL = entityQuery.getEasyQueryClient().queryable(BlogEntity.class)
                    .where(t_topic -> t_topic.eq("id", "456"))
                    .toCteAs();

            List<BlogEntity> list = entityQuery.getEasyQueryClient().queryable(BlogEntity.class)
                    .leftJoin(topicSQL, (t_topic, t_topic1) -> t_topic.eq(t_topic1, "id", "id"))
                    .leftJoin(topicSQL, (t_topic, t_topic1, t_topic2) -> t_topic.eq(t_topic2, "id", "id"))
                    .where((t_topic, t_topic1,t_topic2) -> {
                        t_topic.eq("id", "123");
                        t_topic2.eq("id", "t2123");
                    }).toList();

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH \"with_BlogEntity\" AS (SELECT t1.\"id\",t1.\"create_time\",t1.\"update_time\",t1.\"create_by\",t1.\"update_by\",t1.\"deleted\",t1.\"title\",t1.\"content\",t1.\"url\",t1.\"star\",t1.\"publish_time\",t1.\"score\",t1.\"status\",t1.\"order\",t1.\"is_top\",t1.\"top\" FROM \"t_blog\" t1 WHERE t1.\"deleted\" = ? AND t1.\"id\" = ?)  SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\" FROM \"t_blog\" t LEFT JOIN \"with_BlogEntity\" t2 ON t2.\"deleted\" = ? AND t.\"id\" = t2.\"id\" LEFT JOIN \"with_BlogEntity\" t3 ON t3.\"deleted\" = ? AND t.\"id\" = t3.\"id\" WHERE t.\"deleted\" = ? AND t.\"id\" = ? AND t3.\"id\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),456(String),false(Boolean),false(Boolean),false(Boolean),123(String),t2123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testDuration(){
        List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.createTime().duration(LocalDateTime.now()).toDays().gt(1L);
                }).toList();
    }
}
