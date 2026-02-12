package com.easy.query.test;

import com.easy.query.api.proxy.base.ClassProxy;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.proxy.AggregateQueryable;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.listener.ListenerContext;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * create time 2025/11/28 22:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest28 extends BaseTest {
    @Test
    public void testQueryMap() {
//        Topic[] objects = easyEntityQuery.queryable(Topic.class)
//                .where(t_topic -> {
//                    t_topic.title().isNotNull();
//                }).streamBy(s -> s.toArray(Topic[]::new));

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Map<String, Object>> list = easyEntityQuery.mapQueryable("t_topic")
                .where(t_topic -> {
                    t_topic.get("id").eq("123");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT * FROM `t_topic` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testQueryMap2() {
//        Topic[] objects = easyEntityQuery.queryable(Topic.class)
//                .where(t_topic -> {
//                    t_topic.title().isNotNull();
//                }).streamBy(s -> s.toArray(Topic[]::new));

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<TypeA> list = easyEntityQuery.queryable(ClassProxy.of(TypeA.class))
                .asTable("t_topic")
                .where(t_topic -> {
                    t_topic.field("id").eq("123");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id` FROM `t_topic` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Data
    public static class TypeA{
        private String id;
    }

    @Test
    public void testQueryNullOrDefault(){

        EasyQueryOption easyQueryOption = easyEntityQuery.getRuntimeContext().getQueryConfiguration().getEasyQueryOption();
        SQLExecuteStrategyEnum updateStrategy = easyQueryOption.getUpdateStrategy();

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Topic> list1 = easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.stars().nullOrDefaultZero().eq(1);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE IFNULL(`stars`,0) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testQueryNullOrDefault2(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Topic> list1 = easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.stars().nullOrDefaultFormat(1).eq(1);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE IFNULL(`stars`,1) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }


    @Test
    public void testQueryAggregate() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<Long, BigDecimal>> list = easyEntityQuery.queryable(Topic.class)
                .asTracking()
                .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
                .where((t, b2) -> {
                    t.title().isNotNull();
                    t.createTime().le(LocalDateTime.of(2021, 3, 4, 5, 6));
                })
                .select((t_topic, t_blog) -> {
                    AggregateQueryable<TopicProxy, Topic> result = AggregateQueryable.of(t_topic);
                    return Select.DRAFT.of(
                            result.count(),
                            result.where(s -> s.title().contains("123")).sumBigDecimal(s -> s.stars())
                    );
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT COUNT(*) AS `value1`,SUM((CASE WHEN t.`title` LIKE CONCAT('%',?,'%') THEN t.`stars` ELSE ? END)) AS `value2` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`title` IS NOT NULL AND t.`create_time` <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),0(Integer),false(Boolean),2021-03-04T05:06(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testIncrementNullZero(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        long rows1 = easyEntityQuery.updatable(Topic.class)
                .setColumns(t_topic -> t_topic.stars().set(t_topic.stars().nullOrDefault(0).add(1).asInteger()))
                .whereById("5xxxx1").executeRows();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `t_topic` SET `stars` = (IFNULL(`stars`,?) + ?) WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("0(Integer),1(Integer),5xxxx1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

}
