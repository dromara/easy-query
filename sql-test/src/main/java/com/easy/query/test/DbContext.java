package com.easy.query.test;

import com.easy.query.api.proxy.client.BaseEntityClient;
import com.easy.query.api.proxy.client.DbSetContext;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.proxy.DbSet;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.mysql8.entity.save.M8SaveA;
import com.easy.query.test.mysql8.entity.save.proxy.M8SaveAProxy;

/**
 * create time 2025/9/28 21:23
 * 文件说明
 *
 * @author xuejiaming
 */
public class DbContext implements DbSetContext {

    private final EasyEntityQuery easyEntityQuery;

    public DbContext(EasyEntityQuery easyEntityQuery){
        this.easyEntityQuery = easyEntityQuery;
    }
    @Override
    public BaseEntityClient getBaseEntityClient() {
        return easyEntityQuery;
    }

    public DbSet<TopicProxy, Topic> topic(){
        return getBaseEntityClient().createDbSet(TopicProxy.createTable());
    }
    public DbSet<BlogEntityProxy, BlogEntity> blog(){
        return getBaseEntityClient().createDbSet(BlogEntityProxy.createTable());
    }
    public DbSet<M8SaveAProxy, M8SaveA> m8SaveA(){
        return getBaseEntityClient().createDbSet(M8SaveAProxy.createTable());
    }

}
