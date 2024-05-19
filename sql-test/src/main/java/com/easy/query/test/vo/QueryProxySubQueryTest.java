package com.easy.query.test.vo;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.proxy.columns.types.SQLLocalDateTimeTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLStringTypeColumn;
import com.easy.query.test.BaseTest;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * create time 2023/11/21 15:01
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryProxySubQueryTest extends BaseTest {
    
    @Test
    public void test1(){


        EntityQueryable<SQLStringTypeColumn<BlogEntityProxy>, String> select = easyProxyQuery.queryable(BlogEntityProxy.createTable())
                .select(b -> b.id());


        String sql = easyProxyQuery.queryable(TopicProxy.createTable())
                .where(o -> {
                    o.id().in(select);
                    o.id().in(false,select);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` IN (SELECT t1.`id` FROM `t_blog` t1 WHERE t1.`deleted` = ?)",sql);
    }
    @Test
    public void test2(){

        BlogEntityProxy blogTable = BlogEntityProxy.createTable();
        EntityQueryable<SQLStringTypeColumn<BlogEntityProxy>, String> select = easyProxyQuery.queryable(blogTable)
                .select(b -> blogTable.id());
        TopicProxy topicTable = TopicProxy.createTable();
        String sql = easyProxyQuery.queryable(topicTable)
                .where(o -> {
                    topicTable.id().notIn( select);
                    topicTable.id().notIn(false,select);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` NOT IN (SELECT t1.`id` FROM `t_blog` t1 WHERE t1.`deleted` = ?)",sql);
    }
    @Test
    public void test3(){
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();

        EntityQueryable<SQLLocalDateTimeTypeColumn<BlogEntityProxy>, LocalDateTime> select = easyProxyQuery.queryable(blogTable)
                .select(x -> blogTable.createTime());
        String sql = easyProxyQuery.queryable(TopicProxy.createTable())
                .where(o -> {
                    o.createTime().eq(select);
                    o.createTime().eq(false,select);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` = (SELECT t1.`create_time` FROM `t_blog` t1 WHERE t1.`deleted` = ?)",sql);
    }
    @Test
    public void test4(){
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();
        EntityQueryable<SQLLocalDateTimeTypeColumn<BlogEntityProxy>, LocalDateTime> select = easyProxyQuery.queryable(blogTable)
                .select(x -> x.createTime());
        TopicProxy topicTable = TopicProxy.createTable();
        String sql = easyProxyQuery.queryable(topicTable)
                .where(o -> {
                    o.createTime().ge(select);
                    o.createTime().ge(false,select);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` >= (SELECT t1.`create_time` FROM `t_blog` t1 WHERE t1.`deleted` = ?)",sql);
    }
    @Test
    public void test5(){
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();
        EntityQueryable<SQLLocalDateTimeTypeColumn<BlogEntityProxy>, LocalDateTime> select = easyProxyQuery.queryable(blogTable)
                .select(x -> x.createTime());
        TopicProxy topicTable = TopicProxy.createTable();
        String sql = easyProxyQuery.queryable(topicTable)
                .where(o -> {
                    o.createTime().gt(select);
                    o.createTime().gt(false,select);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` > (SELECT t1.`create_time` FROM `t_blog` t1 WHERE t1.`deleted` = ?)",sql);
    }
    @Test
    public void test6(){
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();
        EntityQueryable<SQLLocalDateTimeTypeColumn<BlogEntityProxy>, LocalDateTime> select = easyProxyQuery.queryable(blogTable)
                .select(x -> x.createTime());
        TopicProxy topicTable = TopicProxy.createTable();
        String sql = easyProxyQuery.queryable(topicTable)
                .where(o -> {
                    o.createTime().ne(select);
                    o.createTime().ne(false,select);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` <> (SELECT t1.`create_time` FROM `t_blog` t1 WHERE t1.`deleted` = ?)",sql);
    }
    @Test
    public void test7(){
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();
        EntityQueryable<SQLLocalDateTimeTypeColumn<BlogEntityProxy>, LocalDateTime> select = easyProxyQuery.queryable(blogTable)
                .select(x -> x.createTime());
        TopicProxy topicTable = TopicProxy.createTable();
        String sql = easyProxyQuery.queryable(topicTable)
                .where(o -> {
                    o.createTime().le(select);
                    o.createTime().le(false,select);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` <= (SELECT t1.`create_time` FROM `t_blog` t1 WHERE t1.`deleted` = ?)",sql);
    }
    @Test
    public void test8(){
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();
        EntityQueryable<SQLLocalDateTimeTypeColumn<BlogEntityProxy>, LocalDateTime> select = easyProxyQuery.queryable(blogTable)
                .select(x -> x.createTime());
        TopicProxy topicTable = TopicProxy.createTable();
        String sql = easyProxyQuery.queryable(topicTable)
                .where(o -> {
                    o.createTime().lt(select);
                    o.createTime().lt(false,select);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` < (SELECT t1.`create_time` FROM `t_blog` t1 WHERE t1.`deleted` = ?)",sql);
    }
    @Test
    public void test9(){
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();
        EntityQueryable<BlogEntityProxy, BlogEntity> queryable = easyProxyQuery.queryable(blogTable);
        TopicProxy topicTable = TopicProxy.createTable();
        String sql = easyProxyQuery.queryable(topicTable)
                .where(o -> {
                    o.exists(()->queryable.where(x->x.id().eq(o.id())));
                    o.exists(false,()->queryable.where(x->x.id().eq(o.id())));
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE EXISTS (SELECT 1 FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = t.`id`)",sql);
    }
    @Test
    public void test10(){
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();
        EntityQueryable<BlogEntityProxy, BlogEntity> queryable = easyProxyQuery.queryable(blogTable);
        TopicProxy topicTable = TopicProxy.createTable();
        String sql = easyProxyQuery.queryable(topicTable)
                .where(o -> {
                    o.notExists(()->queryable.where(x->x.id().eq(o.id())));
                    o.notExists(false,()->queryable.where(x->x.id().eq(o.id())));
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE NOT EXISTS (SELECT 1 FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = t.`id`)",sql);
    }
}
