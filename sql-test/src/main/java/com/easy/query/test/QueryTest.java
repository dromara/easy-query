package com.easy.query.test;

import com.easy.query.BaseTest;
import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.entity.BlogEntity;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @FileName: QueryTest.java
 * @Description: 文件说明
 * @Date: 2023/3/16 17:31
 * @Created by xuejiaming
 */
public class QueryTest extends BaseTest {
    @Test
    public void query1(){
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "123"));
        String sql = queryable.toSql();
        Assert.assertEquals("SELECT t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top`,t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted` FROM t_blog t WHERE t.`deleted` = ? AND t.`id` = ?",sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNull(blogEntity);
    }
    @Test
    public void query2(){
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "97"));
        String sql = queryable.toSql();
        Assert.assertEquals("SELECT t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top`,t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted` FROM t_blog t WHERE t.`deleted` = ? AND t.`id` = ?",sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNotNull(blogEntity);
        Assert.assertEquals("97",blogEntity.getId());
    }
    @Test
    public void query3(){
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "97")).limit(1).select(" 1 ");
        String sql = queryable.toSql();
        Assert.assertEquals("SELECT  1  FROM t_blog t WHERE t.`deleted` = ? AND t.`id` = ? LIMIT 1",sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNotNull(blogEntity);
        Assert.assertNull(blogEntity.getId());
    }
    @Test
    public void query4(){
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "97")).limit(1).select("1 as id");
        String sql = queryable.toSql();
        Assert.assertEquals("SELECT 1 as id FROM t_blog t WHERE t.`deleted` = ? AND t.`id` = ? LIMIT 1",sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNotNull(blogEntity);
        Assert.assertEquals("1",blogEntity.getId());
    }
    @Test
    public void query5(){
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("97","98")));
        String sql = queryable.toSql();
        Assert.assertEquals("SELECT t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top`,t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted` FROM t_blog t WHERE t.`deleted` = ? AND t.`id` IN (?,?)",sql);
        List<BlogEntity> blogs = queryable.toList();
        Assert.assertNotNull(blogs);
        Assert.assertEquals(2,blogs.size());
        Assert.assertEquals(false,blogs.get(0).getIsTop());
        Assert.assertEquals(false,blogs.get(0).getTop());
        Assert.assertEquals(true,blogs.get(1).getIsTop());
        Assert.assertEquals(true,blogs.get(1).getTop());
    }
    @Test
    public void query6(){
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .whereId("97");
        String sql = queryable.toSql();
        Assert.assertEquals("SELECT t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top`,t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted` FROM t_blog t WHERE t.`deleted` = ? AND t.`id` = ?",sql);
        List<BlogEntity> blogs = queryable.toList();
        Assert.assertNotNull(blogs);
        Assert.assertEquals(1,blogs.size());
        Assert.assertEquals("97",blogs.get(0).getId());
        Assert.assertEquals(false,blogs.get(0).getIsTop());
        Assert.assertEquals(false,blogs.get(0).getTop());
    }
    @Test
    public void query7(){
        List<BlogEntity> blogEntities = easyQuery.queryable(BlogEntity.class).orderByAsc(o->o.column(BlogEntity::getCreateTime)).toList();
        LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
        int i=0;
        for (BlogEntity blog : blogEntities) {
            String indexStr = String.valueOf(i);
            Assert.assertEquals(indexStr,blog.getId());
            Assert.assertEquals(indexStr,blog.getCreateBy());
            Assert.assertEquals(begin.plusDays(i),blog.getCreateTime());
            Assert.assertEquals(indexStr,blog.getUpdateBy());
            Assert.assertEquals(begin.plusDays(i),blog.getUpdateTime());
            Assert.assertEquals("title"+indexStr,blog.getTitle());
            Assert.assertEquals("content"+indexStr,blog.getContent());
            Assert.assertEquals("http://blog.easy-query.com/"+indexStr,blog.getUrl());
            Assert.assertEquals(i,(int)blog.getStar());
            Assert.assertEquals(0,new BigDecimal("1.2").compareTo(blog.getScore()));
            Assert.assertEquals(i%3==0?0:1,(int)blog.getStatus());
            Assert.assertEquals(0,new BigDecimal("1.2").multiply(BigDecimal.valueOf(i)).compareTo(blog.getOrder()));
            Assert.assertEquals(i % 2 == 0,blog.getIsTop());
            Assert.assertEquals(i % 2 == 0,blog.getTop());
            Assert.assertEquals(false,blog.getDeleted());
            i++;
        }
    }

    @Override
    public void customInit() {
        boolean any = easyQuery.queryable(BlogEntity.class).any();
        if(!any){

            LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
            List<BlogEntity> blogs=new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                String indexStr = String.valueOf(i);
                BlogEntity blog = new BlogEntity();
                blog.setId(indexStr);
                blog.setCreateBy(indexStr);
                blog.setCreateTime(begin.plusDays(i));
                blog.setUpdateBy(indexStr);
                blog.setUpdateTime(begin.plusDays(i));
                blog.setTitle("title"+indexStr);
                blog.setContent("content"+indexStr);
                blog.setUrl("http://blog.easy-query.com/"+indexStr);
                blog.setStar(i);
                blog.setScore(new BigDecimal("1.2"));
                blog.setStatus(i%3==0?0:1);
                blog.setOrder(new BigDecimal("1.2").multiply(BigDecimal.valueOf(i)));
                blog.setIsTop(i % 2 == 0);
                blog.setTop(i % 2 == 0);
                blog.setDeleted(false);
                blogs.add(blog);
            }
            easyQuery.insertable(blogs).executeRows();
        }
    }
}