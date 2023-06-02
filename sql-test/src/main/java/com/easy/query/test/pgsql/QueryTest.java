package com.easy.query.test.pgsql;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.test.entity.BlogEntity;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2023/5/10 16:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest extends PgSqlBaseTest{

    @Test
    public void query1() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "123"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT \"id\",\"create_time\",\"update_time\",\"create_by\",\"update_by\",\"deleted\",\"title\",\"content\",\"url\",\"star\",\"publish_time\",\"score\",\"status\",\"order\",\"is_top\",\"top\" FROM \"t_blog\" WHERE \"deleted\" = ? AND \"id\" = ?", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNull(blogEntity);
    }

    @Test
    public void query2() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "97"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT \"id\",\"create_time\",\"update_time\",\"create_by\",\"update_by\",\"deleted\",\"title\",\"content\",\"url\",\"star\",\"publish_time\",\"score\",\"status\",\"order\",\"is_top\",\"top\" FROM \"t_blog\" WHERE \"deleted\" = ? AND \"id\" = ?", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNotNull(blogEntity);
        Assert.assertEquals("97", blogEntity.getId());
    }

    @Test
    public void query3() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "97")).limit(1).select(" 1 ");
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT  1  FROM \"t_blog\" WHERE \"deleted\" = ? AND \"id\" = ? LIMIT 1", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNotNull(blogEntity);
        Assert.assertNull(blogEntity.getId());
    }

    @Test
    public void query4() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "97")).limit(1).select("1 as id");
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT 1 as id FROM \"t_blog\" WHERE \"deleted\" = ? AND \"id\" = ? LIMIT 1", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNotNull(blogEntity);
        Assert.assertEquals("1", blogEntity.getId());
    }

    @Test
    public void query5() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("97", "98")));
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
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
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
        List<BlogEntity> blogEntities = easyQuery.queryable(BlogEntity.class).orderByAsc(o -> o.column(BlogEntity::getCreateTime)).toList();
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
}
