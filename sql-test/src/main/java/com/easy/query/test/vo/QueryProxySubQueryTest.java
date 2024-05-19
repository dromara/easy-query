package com.easy.query.test.vo;

import com.easy.query.api.proxy.base.LocalDateTimeProxy;
import com.easy.query.api.proxy.base.StringProxy;
import com.easy.query.api.proxy.select.ProxyQueryable;
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

        BlogEntityProxy blogTable = BlogEntityProxy.createTable();
        ProxyQueryable<StringProxy, String> select = easyProxyQuery.queryable(blogTable)
                .select(StringProxy.createTable(), x -> x.column(blogTable.id()));
        TopicProxy topicTable = TopicProxy.createTable();
        String sql = easyProxyQuery.queryable(topicTable)
                .where(o -> {
                    o.in(topicTable.id(), select);
                    o.in(false,topicTable.id(), select);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` IN (SELECT t1.`id` FROM `t_blog` t1 WHERE t1.`deleted` = ?)",sql);
    }
    @Test
    public void test2(){

        BlogEntityProxy blogTable = BlogEntityProxy.createTable();
        ProxyQueryable<StringProxy, String> select = easyProxyQuery.queryable(blogTable)
                .select(StringProxy.createTable(), x -> x.column(blogTable.id()));
        TopicProxy topicTable = TopicProxy.createTable();
        String sql = easyProxyQuery.queryable(topicTable)
                .where(o -> {
                    o.notIn(topicTable.id(), select);
                    o.notIn(false,topicTable.id(), select);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` NOT IN (SELECT t1.`id` FROM `t_blog` t1 WHERE t1.`deleted` = ?)",sql);
    }
    @Test
    public void test3(){
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();

        ProxyQueryable<LocalDateTimeProxy, LocalDateTime> select = easyProxyQuery.queryable(blogTable)
                .select(LocalDateTimeProxy.createTable(), x -> x.column(blogTable.createTime()));
        TopicProxy topicTable = TopicProxy.createTable();
        String sql = easyProxyQuery.queryable(topicTable)
                .where(o -> {
                    o.eq(topicTable.createTime(),select);
                    o.eq(false,topicTable.createTime(),select);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` = (SELECT t1.`create_time` FROM `t_blog` t1 WHERE t1.`deleted` = ?)",sql);
    }
    @Test
    public void test4(){
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();
        ProxyQueryable<LocalDateTimeProxy, LocalDateTime> select = easyProxyQuery.queryable(blogTable)
                .select(LocalDateTimeProxy.createTable(), x -> x.column(blogTable.createTime()));
        TopicProxy topicTable = TopicProxy.createTable();
        String sql = easyProxyQuery.queryable(topicTable)
                .where(o -> {
                    o.ge(topicTable.createTime(),select);
                    o.ge(false,topicTable.createTime(),select);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` >= (SELECT t1.`create_time` FROM `t_blog` t1 WHERE t1.`deleted` = ?)",sql);
    }
    @Test
    public void test5(){
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();
        ProxyQueryable<LocalDateTimeProxy, LocalDateTime> select = easyProxyQuery.queryable(blogTable)
                .select(LocalDateTimeProxy.createTable(), x -> x.column(blogTable.createTime()));
        TopicProxy topicTable = TopicProxy.createTable();
        String sql = easyProxyQuery.queryable(topicTable)
                .where(o -> {
                    o.gt(topicTable.createTime(),select);
                    o.gt(false,topicTable.createTime(),select);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` > (SELECT t1.`create_time` FROM `t_blog` t1 WHERE t1.`deleted` = ?)",sql);
    }
    @Test
    public void test6(){
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();
        ProxyQueryable<LocalDateTimeProxy, LocalDateTime> select = easyProxyQuery.queryable(blogTable)
                .select(LocalDateTimeProxy.createTable(), x -> x.column(blogTable.createTime()));
        TopicProxy topicTable = TopicProxy.createTable();
        String sql = easyProxyQuery.queryable(topicTable)
                .where(o -> {
                    o.ne(topicTable.createTime(),select);
                    o.ne(false,topicTable.createTime(),select);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` <> (SELECT t1.`create_time` FROM `t_blog` t1 WHERE t1.`deleted` = ?)",sql);
    }
    @Test
    public void test7(){
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();
        ProxyQueryable<LocalDateTimeProxy, LocalDateTime> select = easyProxyQuery.queryable(blogTable)
                .select(LocalDateTimeProxy.createTable(), x -> x.column(blogTable.createTime()));
        TopicProxy topicTable = TopicProxy.createTable();
        String sql = easyProxyQuery.queryable(topicTable)
                .where(o -> {
                    o.le(topicTable.createTime(),select);
                    o.le(false,topicTable.createTime(),select);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` <= (SELECT t1.`create_time` FROM `t_blog` t1 WHERE t1.`deleted` = ?)",sql);
    }
    @Test
    public void test8(){
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();
        ProxyQueryable<LocalDateTimeProxy, LocalDateTime> select = easyProxyQuery.queryable(blogTable)
                .select(LocalDateTimeProxy.createTable(), x -> x.column(blogTable.createTime()));
        TopicProxy topicTable = TopicProxy.createTable();
        String sql = easyProxyQuery.queryable(topicTable)
                .where(o -> {
                    o.lt(topicTable.createTime(),select);
                    o.lt(false,topicTable.createTime(),select);
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`create_time` < (SELECT t1.`create_time` FROM `t_blog` t1 WHERE t1.`deleted` = ?)",sql);
    }
    @Test
    public void test9(){
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();
        ProxyQueryable<BlogEntityProxy, BlogEntity> queryable = easyProxyQuery.queryable(blogTable);
        TopicProxy topicTable = TopicProxy.createTable();
        String sql = easyProxyQuery.queryable(topicTable)
                .where(o -> {
                    o.exists(queryable.where(x->x.eq(blogTable.id(),topicTable.id())));
                    o.exists(false,queryable.where(x->x.eq(blogTable.id(),topicTable.id())));
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE EXISTS (SELECT 1 FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = t.`id`)",sql);
    }
    @Test
    public void test10(){
        BlogEntityProxy blogTable = BlogEntityProxy.createTable();
        ProxyQueryable<BlogEntityProxy, BlogEntity> queryable = easyProxyQuery.queryable(blogTable);
        TopicProxy topicTable = TopicProxy.createTable();
        String sql = easyProxyQuery.queryable(topicTable)
                .where(o -> {
                    o.notExists(queryable.where(x->x.eq(blogTable.id(),topicTable.id())));
                    o.notExists(false,queryable.where(x->x.eq(blogTable.id(),topicTable.id())));
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE NOT EXISTS (SELECT 1 FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = t.`id`)",sql);
    }
}
