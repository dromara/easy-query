package com.easy.query.test;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.kingbase.es.config.KingbaseESDatabaseConfiguration;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.listener.ListenerContext;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2025/5/23 10:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest25 extends BaseTest {

    @Test
    public void testOrderCaseWhen() {

        List<Topic> list = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t_topic, t_blog) -> t_topic.id().eq(t_blog.id()))
                .leftJoin(BlogEntity.class, (t_topic, t_blog, t_blog1) -> t_topic.id().eq(t_blog.id()))
                .orderBy((t_topic, t_blog, t_blog1) -> {
                    t_blog.expression().caseWhen(() -> {
                                t_blog.title().like("123");
                            }).then(1)
                            .caseWhen(() -> {
                                t_blog.content().like("456");
                            }).then(2)
                            .caseWhen(() -> {
                                t_blog.title().like("4561");
                            }).then(3)
                            .caseWhen(() -> {
                                t_blog.title().like("4561");
                            }).then(4)
                            .elseEnd(5).asc();
                }).toList();
    }


}
