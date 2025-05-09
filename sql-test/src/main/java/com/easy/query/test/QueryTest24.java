package com.easy.query.test;

import com.easy.query.api4j.update.EntityUpdatable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.jdbc.parameter.BeanSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.common.ToSQLResult;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * create time 2025/4/28 11:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest24 extends BaseTest {
    @Test
    public void testContains() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
//        easyEntityQuery.sqlQueryMap("select * from table where id=?", Arrays.asList("1"))

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.star().asStr().contains("30%");
                    t_blog.star().nullOrDefault(1).asStr().startsWith("30%");
                    t_blog.title().contains(t_blog.expression().constant("30%"));
                    t_blog.title().nullOrDefault("1").contains("30%");
                    t_blog.title().subString(1, 6).contains("30%");
                    t_blog.title().contains(t_blog.content());
                    t_blog.title().contains(t_blog.content().nullOrDefault(""));
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND LOCATE(?,`star`) > 0 AND LOCATE(?,IFNULL(`star`,?)) = 1 AND LOCATE(?,`title`) > 0 AND LOCATE(?,IFNULL(`title`,?)) > 0 AND LOCATE(?,SUBSTR(`title`,2,6)) > 0 AND `title` LIKE CONCAT('%',`content`,'%') AND `title` LIKE CONCAT('%',IFNULL(`content`,?),'%')", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),30%(String),30%(String),1(Integer),30%(String),30%(String),1(String),30%(String),(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void testContains2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(t_blog -> {
                    t_blog.star().asStr().contains("");
                    t_blog.star().asStr().contains("1");
                    t_blog.star().asStr().startsWith("");
                    t_blog.star().asStr().startsWith("2");
                    t_blog.star().asStr().endsWith("");
                    t_blog.star().asStr().endsWith("3");
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `star` LIKE CONCAT('%',?,'%') AND `star` LIKE CONCAT(?,'%') AND `star` LIKE CONCAT('%',?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(String),2(String),3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testContains3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(t_blog -> {
                    t_blog.star().asStr().contains("");
                    t_blog.star().asStr().contains("1");
                    t_blog.star().asStr().startsWith("");
                    t_blog.star().asStr().startsWith("2");
                    t_blog.star().asStr().endsWith("");
                    t_blog.star().asStr().endsWith("3");
                    t_blog.title().asNumber(Integer.class).asNumber(Long.class).asBoolean().asNumber(BigDecimal.class).asStr().asDateTime(LocalDateTime.class).asStr().eq("123");
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `star` LIKE CONCAT('%',?,'%') AND `star` LIKE CONCAT(?,'%') AND `star` LIKE CONCAT('%',?) AND `title` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(String),2(String),3(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testContains4() {
//
//        easyEntityQuery.updatable(BlogEntity.class)
//                .setColumns(t_blog -> {
//                    t_blog.title().set("123");
//                }).whereById("12345")
//                .executeRows();
//
//        BlogEntity blogEntity = new BlogEntity();
//        easyEntityQuery.updatable(blogEntity)
//                .setColumns(t_blog -> t_blog.FETCHER.title().score())
//                .whereColumns(t_blog -> t_blog.FETCHER.columnKeys())
//                .executeRows();


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .select(t_blog -> new BlogEntityProxy()
                        .title().set(t_blog.title()) // 标题
                        .content().set(t_blog.content().nullOrDefault("")) // 内容
                ).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`title` AS `title`,IFNULL(t.`content`,?) AS `content` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("(String),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void aaa() {
        LocalDateTime localDateTime = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t_topic, t_blog) -> t_topic.id().eq(t_blog.id()))
                .selectColumn((t_topic, t_blog) -> t_blog.createTime().max())
                .singleOrNull();

        LocalDateTime localDateTime1 = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t_topic, t_blog) -> t_topic.id().eq(t_blog.id()))
                .maxOrNull((a, b) -> b.createTime());

        LocalDateTime localDateTime2 = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t_topic, t_blog) -> t_topic.id().eq(t_blog.id()))
                .orderBy((t_topic, t_blog) -> t_blog.createTime().desc())
                .selectColumn((t_topic, t_blog) -> t_blog.createTime())
                .firstOrNull();

    }

    @Test
    public void test123() {

        Topic topic = new Topic();
        topic.setId("123");
        topic.setTitle("title123");
        topic.setStars(1);
        topic.setCreateTime(LocalDateTime.now());
        EntityUpdatable<Topic> updatable = easyQuery.updatable(topic);
        String sql1 = updatable.toSQL(topic);
        System.out.println("sql:" + sql1);
        ToSQLResult sqlResult1 = updatable.getClientUpdate().toSQLResult(topic);
        for (SQLParameter parameter : sqlResult1.getSqlContext().getParameters()) {
            if (parameter instanceof BeanSQLParameter) {
                BeanSQLParameter beanSQLParameter = (BeanSQLParameter) parameter;
                beanSQLParameter.setBean(topic);
            }
            System.out.println("parameters:" + parameter.getValue());
        }
    }

    @Test
    public void test11() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.or(() -> {
                        t_blog.createTime().rangeClosed(LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0));
                        t_blog.star().rangeClosed(1, 100);
                    });
                    t_blog.or(() -> {
                        t_blog.createTime().ge(LocalDateTime.of(2020, 1, 1, 0, 0));
                        t_blog.createTime().ge(LocalDateTime.of(2020, 1, 1, 0, 0));
                    });
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND ((`create_time` >= ? AND `create_time` <= ?) OR (`star` >= ? AND `star` <= ?)) AND (`create_time` >= ? OR `create_time` >= ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T00:00(LocalDateTime),2021-01-01T00:00(LocalDateTime),1(Integer),100(Integer),2020-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test12() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        easyQueryClient.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.and(()->{
                        t_blog.rangeClosed("createTime", LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0));
                        t_blog.or();
                        t_blog.rangeClosed("star", 1, 100);
                    });
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND ((`create_time` >= ? AND `create_time` <= ?) OR (`star` >= ? AND `star` <= ?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T00:00(LocalDateTime),2021-01-01T00:00(LocalDateTime),1(Integer),100(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
    @Test
    public void test13() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.or(() -> {
                        t_blog.createTime().nullOrDefault(t_blog.createTime()).rangeClosedOpen(LocalDateTime.of(2020, 1, 1, 0, 0), LocalDateTime.of(2021, 1, 1, 0, 0));
                        t_blog.star().nullOrDefault(t_blog.star()).rangeClosedOpen(1, 100);
                    });
                    t_blog.or(() -> {
                        t_blog.createTime().ge(LocalDateTime.of(2020, 1, 1, 0, 0));
                        t_blog.createTime().ge(LocalDateTime.of(2020, 1, 1, 0, 0));
                    });
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND ((IFNULL(`create_time`,`create_time`) >= ? AND IFNULL(`create_time`,`create_time`) < ?) OR (IFNULL(`star`,`star`) >= ? AND IFNULL(`star`,`star`) < ?)) AND (`create_time` >= ? OR `create_time` >= ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T00:00(LocalDateTime),2021-01-01T00:00(LocalDateTime),1(Integer),100(Integer),2020-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
}
