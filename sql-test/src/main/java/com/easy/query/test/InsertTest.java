package com.easy.query.test;

import com.easy.query.api4j.insert.EntityInsertable;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.TopicAuto;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @FileName: InsertTest.java
 * @Description: 文件说明
 * @Date: 2023/3/16 21:25
 * @author xuejiaming
 */
public class InsertTest extends BaseTest {

    @Test
    public void insertTest(){
        List<TopicAuto> topicAutos = easyQuery.queryable(TopicAuto.class).where(o->o.lt(TopicAuto::getStars,999)).toList();
        Assert.assertEquals(10,topicAutos.size());
        int i=1;
        for (TopicAuto topicAuto : topicAutos) {
            Assert.assertNotNull(topicAuto.getId());
            Assert.assertEquals(0, topicAuto.getId().compareTo(i));
            i++;
        }
    }
    @Test
    public void insertTest1(){
        long l = easyQuery.insertable(null).executeRows();
        Assert.assertEquals(0,l);
        long l1 = easyQuery.insertable(null).insert(null).executeRows();
        Assert.assertEquals(0,l1);
        Object en=null;
        long l3 = easyQuery.insertable(null).insert(en).executeRows();
        Assert.assertEquals(0,l3);
        long l2 = easyQuery.insertable(null).useInterceptor().noInterceptor().useInterceptor("1").noInterceptor("1").executeRows();
        Assert.assertEquals(0,l2);
    }

    @Test
    public void insertTest2(){

        TopicAuto topicAuto = new TopicAuto();
        topicAuto.setStars(999);
        topicAuto.setTitle("title" + 999);
        topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
        Assert.assertNull(topicAuto.getId());
        long l = easyQuery.insertable(topicAuto).executeRows(true);
        Assert.assertEquals(1,l);
        Assert.assertNotNull(topicAuto.getId());
    }
    @Test
    public void insertTest3(){

        TopicAuto topicAuto = new TopicAuto();
        topicAuto.setStars(999);
        topicAuto.setTitle("title" + 999);
        topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
        Assert.assertNull(topicAuto.getId());
        EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).asTable("aaa").asSchema(o->"xxx");
        String sql = insertable.toSQL(topicAuto);
        Assert.assertEquals("INSERT INTO `xxx`.`aaa` (`stars`,`title`,`create_time`) VALUES (?,?,?)",sql);
    }
    @Test
    public void insertTest5(){

        TopicAuto topicAuto = new TopicAuto();
        topicAuto.setStars(999);
        topicAuto.setTitle("title" + 999);
        topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
        Assert.assertNull(topicAuto.getId());
        EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).asTable(o->o+"aaa").asSchema("xxx");
        String sql = insertable.toSQL(topicAuto);
        Assert.assertEquals("INSERT INTO `xxx`.`t_topic_autoaaa` (`stars`,`title`,`create_time`) VALUES (?,?,?)",sql);
    }
    @Test
    public void insertTest6(){

        try {

            TopicAuto topicAuto = new TopicAuto();
            topicAuto.setStars(999);
            topicAuto.setTitle("title" + 999);
            topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
            Assert.assertNull(topicAuto.getId());
            EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).useInterceptor("Topic1Interceptor").asTable(o->o+"aaa").asSchema("xxx");
            long l = insertable.executeRows();
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
            Assert.assertEquals("INSERT INTO `xxx`.`t_topic_autoaaa` (`stars`,`create_time`) VALUES (?,?)", sql);
        }
    }
    @Test
    public void insertTest7(){

        try {

            TopicAuto topicAuto = new TopicAuto();
            topicAuto.setStars(999);
            topicAuto.setTitle("title" + 999);
            topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
            Assert.assertNull(topicAuto.getId());
            EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).useInterceptor("Topic1Interceptor").noInterceptor().asTable(o->o+"aaa").asSchema("xxx");
            long l = insertable.executeRows();
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
            Assert.assertEquals("INSERT INTO `xxx`.`t_topic_autoaaa` (`stars`,`title`,`create_time`) VALUES (?,?,?)", sql);
        }
    }
    @Test
    public void insertTest8(){

        try {

            TopicAuto topicAuto = new TopicAuto();
            topicAuto.setStars(999);
            topicAuto.setTitle("title" + 999);
            topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
            Assert.assertNull(topicAuto.getId());
            EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).noInterceptor().useInterceptor("Topic1Interceptor").asTable(o->o+"aaa").asSchema("xxx");
            long l = insertable.executeRows();
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
            Assert.assertEquals("INSERT INTO `xxx`.`t_topic_autoaaa` (`stars`,`create_time`) VALUES (?,?)", sql);
        }
    }
    @Test
    public void insertTest8_1(){

        try {

            TopicAuto topicAuto = new TopicAuto();
            topicAuto.setStars(999);
            topicAuto.setTitle("title" + 999);
            topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
            Assert.assertNull(topicAuto.getId());
            EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).onDuplicateKeyIgnore().noInterceptor().useInterceptor("Topic1Interceptor").asTable(o->o+"aaa").asSchema("xxx");
            long l = insertable.executeRows();
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
            Assert.assertEquals("INSERT IGNORE INTO `xxx`.`t_topic_autoaaa` (`stars`,`create_time`) VALUES (?,?)", sql);
        }
    }
    @Test
    public void insertTest9(){
        TopicAuto topicAuto=null;
        long l = easyQuery.insertable(topicAuto)
                .noInterceptor().useInterceptor("11")
                .useInterceptor("11").useInterceptor()
                .asAlias("a")
                .asSchema("b")
                .asTable("c")
                .asSchema(o -> o + "ab")
                .asTable(o -> o + "bb")
                .executeRows();
        Assert.assertEquals(0,l);
    }

    @Test
    public void insertDuplicateKeyUpdate1(){

        TopicAuto topicAuto = new TopicAuto();
        topicAuto.setStars(999);
        topicAuto.setTitle("title" + 999);
        topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
        Assert.assertNull(topicAuto.getId());
        EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).onDuplicateKeyUpdate();
        String sql = insertable.toSQL(topicAuto);
        Assert.assertEquals("INSERT INTO `t_topic_auto` (`stars`,`title`,`create_time`) VALUES (?,?,?) ON DUPLICATE KEY UPDATE `stars` = VALUES(`stars`), `title` = VALUES(`title`), `create_time` = VALUES(`create_time`)",sql);
    }
    @Test
    public void insertDuplicateKeyUpdate2(){

        TopicAuto topicAuto = new TopicAuto();
        topicAuto.setStars(999);
        topicAuto.setTitle("title" + 999);
        topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
        Assert.assertNull(topicAuto.getId());
        EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).onDuplicateKeyUpdate(t->t.column(TopicAuto::getStars).column(TopicAuto::getTitle));
        String sql = insertable.toSQL(topicAuto);
        Assert.assertEquals("INSERT INTO `t_topic_auto` (`stars`,`title`,`create_time`) VALUES (?,?,?) ON DUPLICATE KEY UPDATE `stars` = VALUES(`stars`), `title` = VALUES(`title`)",sql);
    }



    @Test
    public void insertTestx() {
        easyQuery.deletable(BlogEntity.class)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .whereById("200")
                .executeRows();
        LocalDateTime begin = LocalDateTime.of(2000, 1, 1, 1, 1, 1);
        String indexStr = "200";
        BlogEntity blog = new BlogEntity();
        blog.setId(indexStr);
        blog.setCreateBy(indexStr);
        blog.setCreateTime(begin.plusDays(1));
        blog.setUpdateBy(indexStr);
        blog.setUpdateTime(begin.plusDays(1));
        blog.setTitle("title" + indexStr);
        blog.setContent("content" + indexStr);
        blog.setUrl("http://blog.easy-query.com/" + indexStr);
        blog.setStar(1);
        blog.setScore(new BigDecimal("1.2"));
        blog.setStatus(1);
        blog.setOrder(new BigDecimal("1.2").multiply(BigDecimal.valueOf(1)));
        blog.setIsTop(false);
        blog.setTop(false);
        blog.setDeleted(false);
        long l = easyQuery.insertable(blog)
                .onDuplicateKeyUpdate()
                .executeRows();
        Assert.assertEquals(1,l);
        long l3 = easyQuery.insertable(blog)
                .onDuplicateKeyUpdate(t->t.column(BlogEntity::getStar).column(BlogEntity::getContent))
                .executeRows();

        Assert.assertEquals(1,l3);
        blog.setContent("xxx");
        long l4 = easyQuery.insertable(blog)
                .onDuplicateKeyUpdate(t->t.column(BlogEntity::getStar).column(BlogEntity::getContent))
                .executeRows();

        Assert.assertEquals(2,l4);
        long l1 = easyQuery.insertable(blog)
                .onDuplicateKeyIgnore()
                .executeRows();
        Assert.assertEquals(0,l1);

        blog.setContent("abc");
        long l2 = easyQuery.insertable(blog)
                .onDuplicateKeyUpdate()
                .executeRows();
        Assert.assertEquals(2,l2);
        BlogEntity blogEntity = easyQuery.queryable(BlogEntity.class)
                .whereById("200")
                .firstNotNull("xxx");
        Assert.assertEquals("abc",blogEntity.getContent());
        easyQuery.deletable(BlogEntity.class)
                .whereById("200")
                .executeRows();
    }

    @Test
    public void insertBatch(){

        easyQuery.deletable(BlogEntity.class)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .whereByIds(Arrays.asList("500","300","400")).executeRows();
        String indexStr = "500";
        LocalDateTime begin = LocalDateTime.of(2000, 1, 1, 1, 1, 1);
        List<BlogEntity> r=new ArrayList<>(2);
        {
            BlogEntity blog = new BlogEntity();
            blog.setId(indexStr);
            blog.setCreateBy(indexStr);
            blog.setCreateTime(begin.plusDays(1));
            blog.setUpdateBy(indexStr);
            blog.setUpdateTime(begin.plusDays(1));
            blog.setTitle("title" + indexStr);
            blog.setContent("content" + indexStr);
            blog.setUrl("http://blog.easy-query.com/" + indexStr);
            blog.setStar(500);
            blog.setScore(new BigDecimal("1.2"));
            blog.setStatus(1);
            blog.setOrder(new BigDecimal("1.2").multiply(BigDecimal.valueOf(1)));
            blog.setIsTop(false);
            blog.setTop(false);
            blog.setDeleted(false);
            r.add(blog);
        }
        indexStr="300";
        {
            BlogEntity blog = new BlogEntity();
            blog.setId(indexStr);
            blog.setCreateBy(indexStr);
            blog.setCreateTime(begin.plusDays(1));
            blog.setUpdateBy(indexStr);
            blog.setUpdateTime(begin.plusDays(1));
            blog.setTitle("title" + indexStr);
            blog.setContent("content" + indexStr);
            blog.setUrl("http://blog.easy-query.com/" + indexStr);
            blog.setStar(300);
            blog.setScore(new BigDecimal("1.2"));
            blog.setStatus(1);
            blog.setOrder(new BigDecimal("1.2").multiply(BigDecimal.valueOf(1)));
            blog.setIsTop(false);
            blog.setTop(false);
            blog.setDeleted(false);
            r.add(blog);
        }
        indexStr="400";
        {
            BlogEntity blog = new BlogEntity();
            blog.setId(indexStr);
            blog.setCreateBy(indexStr);
            blog.setCreateTime(begin.plusDays(1));
            blog.setUpdateBy(indexStr);
            blog.setUpdateTime(begin.plusDays(1));
            blog.setTitle("title" + indexStr);
            blog.setContent("content" + indexStr);
            blog.setUrl("http://blog.easy-query.com/" + indexStr);
            blog.setStar(400);
            blog.setScore(new BigDecimal("1.2"));
            blog.setStatus(1);
            blog.setOrder(new BigDecimal("1.2").multiply(BigDecimal.valueOf(1)));
            blog.setIsTop(false);
            blog.setTop(false);
            blog.setDeleted(false);
            r.add(blog);
        }
        long l = easyQuery.insertable(r).executeRows();
        Assert.assertEquals(3,l);
        easyQuery.deletable(BlogEntity.class)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .whereByIds(Arrays.asList("500","300","400")).executeRows();
        long l2 = easyQuery.insertable(r).batch().executeRows();
        Assert.assertEquals(-6,l2);
        easyQuery.deletable(BlogEntity.class)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .whereByIds(Arrays.asList("500","300","400")).executeRows();
    }
}
