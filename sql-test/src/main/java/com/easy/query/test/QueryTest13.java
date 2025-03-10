package com.easy.query.test;

import com.easy.query.api.proxy.base.MapTypeProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.key.MapKey;
import com.easy.query.api.proxy.key.MapKeys;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.dto.TopicTypeVO;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.company.ValueCompany;
import com.easy.query.test.entity.company.ValueCompanyDTO;
import com.easy.query.test.entity.company.proxy.ValueCompanyDTOProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * create time 2024/1/19 22:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest13 extends BaseTest {
    @Test
    public void orderTest1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        MapKey<String> blogId = MapKeys.stringKey("blogId");
        MapKey<Integer> blogCount = MapKeys.integerKey("blogCount");
//        easyEntityQuery.getRuntimeContext().getEntityMetadataManager().getEntityMetadata()
        List<Topic> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.star().gt(1);
                })
                .groupBy(b -> GroupKeys.of(b.id()))
                .select(group -> {
                    MapTypeProxy r = new MapTypeProxy();
                    r.put(blogId, group.key1());
                    r.put(blogCount, group.intCount());
                    return r;
                })
                .leftJoin(Topic.class, (g, topic) -> {
                    g.get(blogId).eq(topic.id());
                })
                .where((g, topic) -> {
                    g.get(blogCount).le(123);
                }).select((g, topic) -> topic).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`id`,t2.`stars`,t2.`title`,t2.`create_time` FROM (SELECT t.`id` AS `blogId`,COUNT(*) AS `blogCount` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`star` > ? GROUP BY t.`id`) t1 LEFT JOIN `t_topic` t2 ON t1.`blogId` = t2.`id` WHERE t1.`blogCount` <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(Integer),123(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void orderTest1_1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        MapKey<String> blogId = MapKeys.stringKey("blogId");
        MapKey<Integer> blogCount = MapKeys.integerKey("blogCount");

        EntityQueryable<MapTypeProxy, Map<String, Object>> groupAndCount = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.star().gt(1);
                })
                .groupBy(b -> GroupKeys.of(b.id()))
                .select(group -> new MapTypeProxy().put(blogId, group.key1()).put(blogCount, group.intCount()));

//        easyEntityQuery.getRuntimeContext().getEntityMetadataManager().getEntityMetadata()
        List<Topic> list = groupAndCount
                .leftJoin(Topic.class, (g, topic) -> {
                    g.get(blogId).eq(topic.id());
                })
                .where((g, topic) -> {
                    g.get(blogCount).le(123);
                }).select((g, topic) -> topic).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`id`,t2.`stars`,t2.`title`,t2.`create_time` FROM (SELECT t.`id` AS `blogId`,COUNT(*) AS `blogCount` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`star` > ? GROUP BY t.`id`) t1 LEFT JOIN `t_topic` t2 ON t1.`blogId` = t2.`id` WHERE t1.`blogCount` <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(Integer),123(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void orderTest2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        MapKey<String> blogId = MapKeys.stringKey("blogId");
        MapKey<LocalDateTime> dateTime = MapKeys.localDateTimeKey("dateTime");

//        easyEntityQuery.getRuntimeContext().getEntityMetadataManager().getEntityMetadata()
        List<Map<String, Object>> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.star().gt(1);
                })
                .select(b -> new MapTypeProxy().adapter(r -> {
                    r.put(blogId, b.id());
                    r.put(dateTime, b.createTime());
                }))
                .toList();
        for (Map<String, Object> stringObjectMap : list) {

            Object o = stringObjectMap.get(dateTime.getName());
            if (o != null) {
                Assert.assertEquals(o.getClass(), dateTime.getPropType());
            }
        }

//        List<TopicTypeVO> list1 = easyEntityQuery.queryable(BlogEntity.class)
//                .leftJoin(Topic.class, (b, t2) -> b.id().eq(t2.id()))
//                .select((b, t2) -> new TopicTypeVOProxy(r -> {
//                    r.selectAll(b);
//                    r.id().set(t2.id());
//                })).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `blogId`,t.`create_time` AS `dateTime` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`star` > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testMultiSelect(){
//        {
//
//            EntityQueryProxyManager.create(SysUser.class);
//            long start = System.currentTimeMillis();
//            for (int i = 0; i < 100000; i++) {
//                SysUserProxy sysUserProxy = EntityQueryProxyManager.create(SysUser.class);
//            }
//            long end = System.currentTimeMillis();
//            System.out.println("EntityQueryProxyManager耗时:"+(end-start)+"(ms)");
//        }
//        {
//
//            long start = System.currentTimeMillis();
//            for (int i = 0; i < 100000; i++) {
//                SysUserProxy sysUserProxy = new SysUserProxy();
//            }
//            long end = System.currentTimeMillis();
//            System.out.println("new Proxy耗时:"+(end-start)+"(ms)");
//        }

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<TopicTypeVO> vo = easyEntityQuery.queryable(BlogEntity.class)
                .leftJoin(SysUser.class, (b, s2) -> b.id().eq(s2.id()))
                .select(TopicTypeVO.class, (b1, s2) -> Select.of(
                        b1.FETCHER.id().content().createTime().as("createTime"),
                        s2.FETCHER.address().idCard()
                )).toList();


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`content`,t.`create_time` AS `create_time`,t1.`address`,t1.`id_card` FROM `t_blog` t LEFT JOIN `easy-query-test`.`t_sys_user` t1 ON t.`id` = t1.`id` WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();


        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.createTime().format("yyyy-MM-dd").eq("2023-01-01");
                    b.title().nullOrDefault("我的title").eq("title");
                    b.title().subString(1, 2).like("你好");
                    b.title().isBlank();
                    b.title().isNotBlank();
                    b.title().isEmpty();
                    b.title().isNotEmpty();
                }).toList();

    }

    @Test
     public void testValueObject(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<ValueCompanyDTO> list = easyEntityQuery.queryable(ValueCompany.class)
                .where(v -> {
//                    v.address().area().eq("123");
                    v.address().province().eq(v.address().province());
                })
                .select(v -> new ValueCompanyDTOProxy().adapter(r -> {
                    r.address().area().set(v.address().area());
                })).toList();


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`area` AS `area` FROM `my_company` t WHERE t.`province` = t.`province`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        Assert.assertFalse(list.isEmpty());
        for (ValueCompanyDTO companyDTO : list) {
            Assert.assertNotNull(companyDTO.getAddress().getArea());
        }
    }
}
