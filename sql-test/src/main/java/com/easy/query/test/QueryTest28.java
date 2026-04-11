package com.easy.query.test;

import com.easy.query.api.proxy.base.ClassProxy;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.proxy.AggregateQueryable;
import com.easy.query.core.proxy.columns.types.SQLAnyTypeColumn;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.StringTypeExpression;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.doc.MySignUp;
import com.easy.query.test.doc.MySignUpDTOx;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicTypeArrayJson;
import com.easy.query.test.entity.TopicTypeArrayJson2;
import com.easy.query.test.entity.TopicTypeJsonValue;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.InterceptorEntity;
import com.easy.query.test.mysql8.entity.M8User;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
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
    public static class TypeA {
        private String id;
    }

    @Test
    public void testQueryNullOrDefault() {

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
    public void testQueryNullOrDefault2() {

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
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
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
    public void testQueryAggregate1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<Long, BigDecimal>> list = easyEntityQuery.queryable(Topic.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .asTracking()
                .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
                .where((t, b2) -> {
                    t.title().isNotNull();
                    t.createTime().le(LocalDateTime.of(2021, 3, 4, 5, 6));
                })
                .groupBy()
                .select((t_topic, t_blog) -> {
                    return Select.DRAFT.of(
                            t_topic.count(),
                            t_topic.where(s -> s.title().contains("123")).sumBigDecimal(s -> s.stars())
                    );
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT COUNT(*) AS `value1`,SUM((CASE WHEN t.`title` LIKE CONCAT('%',?,'%') THEN t.`stars` ELSE ? END)) AS `value2` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`title` IS NOT NULL AND t.`create_time` <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),0(Integer),false(Boolean),2021-03-04T05:06(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testQueryAggregate2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<Long, BigDecimal>> list1 = easyEntityQuery.queryable(Topic.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(t_topic -> t_topic.title().contains("123"))
                .groupBy()
                .select(t_topic -> Select.DRAFT.of(
                        t_topic.count(),
                        t_topic.avg(s -> s.stars())
                ))
                .toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT COUNT(*) AS `value1`,AVG(t.`stars`) AS `value2` FROM `t_topic` t WHERE t.`title` LIKE CONCAT('%',?,'%')", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testIncrementNullZero() {

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

    @Test
    public void testNoValueConverter1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        TopicTypeJsonValue topicTypeJsonValue1 = new TopicTypeJsonValue();
        topicTypeJsonValue1.setName("123");
        List<TopicTypeArrayJson> list3 = easyEntityQuery.queryable(TopicTypeArrayJson.class)
                .where(o -> {
                    o.title2().asStr().like("123");
                    o.title2().asStr().contains("123");
                    o.title2().asStr().startsWith("123");
                    o.title2().asStr().endsWith("123");
                    o.title2().eq(Collections.singletonList(topicTypeJsonValue1));
                    o.title().likeRaw("456");
                    TopicTypeJsonValue topicTypeJsonValue = new TopicTypeJsonValue();
                    topicTypeJsonValue.setAge(1);
                    topicTypeJsonValue.setName("1");
                    o.title().eq(topicTypeJsonValue);
                    o.title().toStr().contains("123");
                })
                .orderBy(o -> {
                    o.title2().asc();
                })
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`title2`,`topic_type`,`create_time` FROM `t_topic_type_array` WHERE `title2` LIKE ? AND `title2` LIKE CONCAT('%',?,'%') AND `title2` LIKE CONCAT(?,'%') AND `title2` LIKE CONCAT('%',?) AND `title2` = ? AND `title` LIKE ? AND `title` = ? AND CAST(`title` AS CHAR) LIKE CONCAT('%',?,'%') ORDER BY `title2` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String),123(String),123(String),123(String),[{\"age\":null,\"name\":\"123\"}](String),%456%(String),{\"age\":1,\"name\":\"1\"}(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void test() {

        Map<String, String> columns = new LinkedHashMap<>();
        columns.put("id", "id");
        columns.put("content", "title");
        columns.put("title", "content");
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(t_blog -> {
                    ClassProxy<BlogEntity> result = ClassProxy.of(BlogEntity.class);
                    for (Map.Entry<String, String> kv : columns.entrySet()) {
                        SQLAnyTypeColumn<BlogEntityProxy, Object> selectColumn = t_blog.anyColumn(kv.getValue());
//                        result.field(kv.getKey()).set(selectColumn);
                        result.columns(selectColumn.as(kv.getKey()));
                    }
                    return result;
                }).toList();
    }

    @Test
    public void testInterceptor1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<InterceptorEntity> list = easyEntityQuery.queryable(InterceptorEntity.class)
                    .toList();
        } catch (Exception ignored) {

        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`name` FROM `m8_interceptor` WHERE 1=1", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("%123%(String),123(String),123(String),123(String),[{\"age\":null,\"name\":\"123\"}](String),%456%(String),{\"age\":1,\"name\":\"1\"}(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testInterceptor2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<InterceptorEntity> list = easyEntityQuery.queryable(InterceptorEntity.class)
                    .noInterceptor()
                    .where(i -> {
                        i.list().configure(s->s.noInterceptor()).any();
                    })
                    .toList();
        } catch (Exception ignored) {

        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `m8_interceptor` t WHERE EXISTS (SELECT 1 FROM `m8_interceptor2` t1 WHERE t1.`aid` = t.`id` LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("%123%(String),123(String),123(String),123(String),[{\"age\":null,\"name\":\"123\"}](String),%456%(String),{\"age\":1,\"name\":\"1\"}(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testInterceptor3() {


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<InterceptorEntity> list = easyEntityQuery.queryable(InterceptorEntity.class)
                .noInterceptor()
                .include(i -> i.list())
                .toList();

        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT `id`,`name` FROM `m8_interceptor`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`id`,t.`aid`,t.`name` FROM `m8_interceptor2` t WHERE t.`aid` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        listenerContextManager.clear();
    }

}
