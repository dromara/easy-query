package com.easy.query.test;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.proxy.DbSet;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;

/**
 * create time 2025/9/28 21:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class DbContext {
    private final EasyEntityQuery easyEntityQuery;

    public DbContext(EasyEntityQuery easyEntityQuery){
        this.easyEntityQuery = easyEntityQuery;
    }

    public DbSet<TopicProxy, Topic> topic(){
        return easyEntityQuery.createDbSet(TopicProxy.createTable());
    }
    public DbSet<BlogEntityProxy, BlogEntity> blog(){
        return easyEntityQuery.createDbSet(BlogEntityProxy.createTable());
    }
}
