package com.easy.query.test;

import com.easy.query.api.proxy.client.BaseDbSetContext;
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
public class DbContext extends BaseDbSetContext {


    public DbContext(EasyEntityQuery easyEntityQuery){
        super(easyEntityQuery);
    }

    public DbSet<TopicProxy, Topic> topic(){
        return createDbSet(new TopicProxy());
    }
    public DbSet<BlogEntityProxy, BlogEntity> blog(){
        return createDbSet(new BlogEntityProxy());
    }
    public DbSet<M8SaveAProxy, M8SaveA> m8SaveA(){
        return createDbSet(new M8SaveAProxy());
    }

}
