package com.easy.query.test;

import com.easy.query.api.proxy.entity.update.EntityUpdatable;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.jdbc.parameter.BeanSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.common.ToSQLResult;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.func.def.enums.TimeUnitEnum;
import com.easy.query.core.proxy.columns.types.SQLIntegerTypeColumn;
import com.easy.query.core.proxy.columns.types.SQLStringTypeColumn;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.proxy.DraftProxy;
import com.easy.query.core.proxy.extension.functions.type.BooleanTypeExpression;
import com.easy.query.core.proxy.extension.functions.type.StringTypeExpression;
import com.easy.query.core.proxy.sql.Draft;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.proxy.sql.draft.Draft1Builder;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicTypeTest1;
import com.easy.query.test.entity.blogtest.SysRole;
import com.easy.query.test.entity.blogtest.proxy.SysMenuProxy;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.SysUserProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.enums.TopicTypeEnum;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.vo.GenericDTO;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
                    SQLIntegerTypeColumn<BlogEntityProxy> star = t_blog.star();
                    StringTypeExpression<String> rtrim = t_blog.title().rtrim();
                    StringTypeExpression<String> stringStringTypeExpression = t_blog.title().subString(1, 2);
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
        EntityUpdatable<TopicProxy, Topic> updatable = easyEntityQuery.updatable(topic);
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
                    t_blog.and(() -> {
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

    @Test
    public void test14() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        LocalDateTime begin = null;
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(t_blog -> {
                    t_blog.or(() -> {
                        t_blog.createTime().nullOrDefault(t_blog.createTime()).rangeClosedOpen(begin, LocalDateTime.of(2021, 1, 1, 0, 0));
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
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (IFNULL(`create_time`,`create_time`) < ? OR (IFNULL(`star`,`star`) >= ? AND IFNULL(`star`,`star`) < ?)) AND (`create_time` >= ? OR `create_time` >= ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2021-01-01T00:00(LocalDateTime),1(Integer),100(Integer),2020-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test15() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime end = null;
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(t_blog -> {
                    t_blog.or(() -> {
                        t_blog.createTime().nullOrDefault(t_blog.createTime()).rangeClosedOpen(begin, end);
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
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (IFNULL(`create_time`,`create_time`) >= ? OR (IFNULL(`star`,`star`) >= ? AND IFNULL(`star`,`star`) < ?)) AND (`create_time` >= ? OR `create_time` >= ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T00:00(LocalDateTime),1(Integer),100(Integer),2020-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test16() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        LocalDateTime begin = null;
        LocalDateTime end = null;
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(t_blog -> {
                    t_blog.or(() -> {
                        t_blog.createTime().nullOrDefault(t_blog.createTime()).rangeClosedOpen(begin, end);
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
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (IFNULL(`star`,`star`) >= ? AND IFNULL(`star`,`star`) < ?) AND (`create_time` >= ? OR `create_time` >= ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(Integer),100(Integer),2020-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test17() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        LocalDateTime begin = null;
        LocalDateTime end = LocalDateTime.of(2021, 1, 1, 0, 0);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(t_blog -> {
                    t_blog.or(() -> {
                        t_blog.createTime().rangeClosed(begin, end);
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
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (`create_time` <= ? OR (`star` >= ? AND `star` <= ?)) AND (`create_time` >= ? OR `create_time` >= ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2021-01-01T00:00(LocalDateTime),1(Integer),100(Integer),2020-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test18() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime end = null;

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(t_blog -> {
                    t_blog.or(() -> {
                        t_blog.createTime().rangeClosed(begin, end);
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
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (`create_time` >= ? OR (`star` >= ? AND `star` <= ?)) AND (`create_time` >= ? OR `create_time` >= ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T00:00(LocalDateTime),1(Integer),100(Integer),2020-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test19() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        LocalDateTime begin = null;
        LocalDateTime end = null;

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(t_blog -> {
                    t_blog.or(() -> {
                        t_blog.createTime().rangeClosed(begin, end);
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
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (`star` >= ? AND `star` <= ?) AND (`create_time` >= ? OR `create_time` >= ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(Integer),100(Integer),2020-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }


    @Test
    public void test20() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        LocalDateTime begin = null;
        LocalDateTime end = LocalDateTime.of(2021, 1, 1, 0, 0);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(t_blog -> {
                    t_blog.or(() -> {
                        t_blog.star().rangeClosed(1, 100);
                        t_blog.createTime().rangeClosed(begin, end);
                    });
                    t_blog.or(() -> {
                        t_blog.createTime().ge(LocalDateTime.of(2020, 1, 1, 0, 0));
                        t_blog.createTime().ge(LocalDateTime.of(2020, 1, 1, 0, 0));
                    });
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND ((`star` >= ? AND `star` <= ?) OR `create_time` <= ?) AND (`create_time` >= ? OR `create_time` >= ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(Integer),100(Integer),2021-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test21() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime end = null;

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(t_blog -> {
                    t_blog.or(() -> {
                        t_blog.star().rangeClosed(1, 100);
                        t_blog.createTime().rangeClosed(begin, end);
                    });
                    t_blog.or(() -> {
                        t_blog.createTime().ge(LocalDateTime.of(2020, 1, 1, 0, 0));
                        t_blog.createTime().ge(LocalDateTime.of(2020, 1, 1, 0, 0));
                    });
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND ((`star` >= ? AND `star` <= ?) OR `create_time` >= ?) AND (`create_time` >= ? OR `create_time` >= ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(Integer),100(Integer),2020-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test22() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        LocalDateTime begin = null;
        LocalDateTime end = null;

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(t_blog -> {
                    t_blog.or(() -> {
                        t_blog.star().rangeClosed(1, 100);
                        t_blog.createTime().rangeClosed(begin, end);
                    });
                    t_blog.or(() -> {
                        t_blog.createTime().ge(LocalDateTime.of(2020, 1, 1, 0, 0));
                        t_blog.createTime().ge(LocalDateTime.of(2020, 1, 1, 0, 0));
                    });
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (`star` >= ? AND `star` <= ?) AND (`create_time` >= ? OR `create_time` >= ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(Integer),100(Integer),2020-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }


    @Test
    public void test23() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        LocalDateTime begin = null;
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(t_blog -> {
                    t_blog.or(() -> {
                        t_blog.star().nullOrDefault(t_blog.star()).rangeClosedOpen(1, 100);
                        t_blog.createTime().nullOrDefault(t_blog.createTime()).rangeClosedOpen(begin, LocalDateTime.of(2021, 1, 1, 0, 0));
                    });
                    t_blog.or(() -> {
                        t_blog.createTime().ge(LocalDateTime.of(2020, 1, 1, 0, 0));
                        t_blog.createTime().ge(LocalDateTime.of(2020, 1, 1, 0, 0));
                    });
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND ((IFNULL(`star`,`star`) >= ? AND IFNULL(`star`,`star`) < ?) OR IFNULL(`create_time`,`create_time`) < ?) AND (`create_time` >= ? OR `create_time` >= ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(Integer),100(Integer),2021-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test24() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 0, 0);
        LocalDateTime end = null;
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(t_blog -> {
                    t_blog.or(() -> {
                        t_blog.star().nullOrDefault(t_blog.star()).rangeClosedOpen(1, 100);
                        t_blog.createTime().nullOrDefault(t_blog.createTime()).rangeClosedOpen(begin, end);
                    });
                    t_blog.or(() -> {
                        t_blog.createTime().ge(LocalDateTime.of(2020, 1, 1, 0, 0));
                        t_blog.createTime().ge(LocalDateTime.of(2020, 1, 1, 0, 0));
                    });
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND ((IFNULL(`star`,`star`) >= ? AND IFNULL(`star`,`star`) < ?) OR IFNULL(`create_time`,`create_time`) >= ?) AND (`create_time` >= ? OR `create_time` >= ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(Integer),100(Integer),2020-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test25() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        LocalDateTime begin = null;
        LocalDateTime end = null;
        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(t_blog -> {
                    t_blog.or(() -> {
                        t_blog.createTime().nullOrDefault(t_blog.createTime()).rangeClosedOpen(begin, end);
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
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (IFNULL(`star`,`star`) >= ? AND IFNULL(`star`,`star`) < ?) AND (`create_time` >= ? OR `create_time` >= ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),1(Integer),100(Integer),2020-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test26() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Boolean> list = easyEntityQuery.queryable(BlogEntity.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(t_blog -> {
                    t_blog.expression().valueOf(() -> {
                        t_blog.createTime().isBefore(LocalDateTime.of(2020, 1, 1, 0, 0));
                    }).eq(true);

                    t_blog.createTime().nullOrDefault(LocalDateTime.of(2022, 1, 1, 0, 0)).isBefore(LocalDateTime.of(2020, 1, 1, 0, 0));
                    t_blog.createTime().nullOrDefault(t_blog.createTime()).isAfter(LocalDateTime.of(2020, 1, 1, 0, 0));
                    t_blog.createTime().isBefore(LocalDateTime.of(2020, 1, 1, 0, 0));
                    t_blog.createTime().isBefore(t_blog.createTime());
                    t_blog.createTime().nullOrDefault(t_blog.createTime()).isAfter(t_blog.createTime());
                    t_blog.createTime().plus(1, TimeUnitEnum.DAYS).isAfter(LocalDateTime.of(2020, 1, 1, 0, 0));

                }).selectColumn(t_blog -> t_blog.expression().valueOf(() -> {
                    t_blog.createTime().isBefore(LocalDateTime.of(2020, 1, 1, 0, 0));
                    t_blog.title().eq("123");
                })).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (t.`create_time` < ? AND t.`title` = ?) FROM `t_blog` t WHERE t.`deleted` = ? AND (t.`create_time` < ?) = ? AND IFNULL(t.`create_time`,?) < ? AND IFNULL(t.`create_time`,t.`create_time`) > ? AND t.`create_time` < ? AND t.`create_time` < t.`create_time` AND IFNULL(t.`create_time`,t.`create_time`) < t.`create_time` AND date_add(t.`create_time`, interval (?) day) > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2020-01-01T00:00(LocalDateTime),123(String),false(Boolean),2020-01-01T00:00(LocalDateTime),true(Boolean),2022-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime),2020-01-01T00:00(LocalDateTime),1(Long),2020-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test27() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<String, Boolean>> list = easyEntityQuery.queryable(BlogEntity.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .select(t_blog -> Select.DRAFT.of(
                        t_blog.createTime().format("yyyy-MM"),
                        t_blog.expression().valueOf(() -> {
                            t_blog.createTime().format("yyyy-MM").startsWith("2020-02");
                        })
                )).toList();
        for (Draft2<String, Boolean> booleanDraft1 : list) {
            Assert.assertEquals(booleanDraft1.getValue2(), booleanDraft1.getValue1().startsWith("2020-02"));
        }
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT DATE_FORMAT(t.`create_time`,'%Y-%m') AS `value1`,(DATE_FORMAT(t.`create_time`,'%Y-%m') LIKE CONCAT(?,'%')) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2020-02(String),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test28() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<String, Boolean>> list = easyEntityQuery.queryable(BlogEntity.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .select(t_blog -> Select.DRAFT.of(
                        t_blog.createTime().format("yyyy-MM"),
                        t_blog.expression().valueOf(() -> {
                            t_blog.or(() -> {
                                t_blog.createTime().format("yyyy-MM").startsWith("2020-02");
                                t_blog.title().startsWith("小明");
                            });
                        })
                )).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT DATE_FORMAT(t.`create_time`,'%Y-%m') AS `value1`,((DATE_FORMAT(t.`create_time`,'%Y-%m') LIKE CONCAT(?,'%') OR t.`title` LIKE CONCAT(?,'%'))) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2020-02(String),小明(String),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test29() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Boolean> list = easyEntityQuery.queryable(BlogEntity.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(t_blog -> {
                    t_blog.createTime().plus(1, TimeUnitEnum.DAYS).isAfter(LocalDateTime.of(2020, 1, 1, 0, 0));

                }).selectColumn(t_blog -> t_blog.expression().valueOf(() -> {
                    t_blog.createTime().isBefore(LocalDateTime.of(2020, 1, 1, 0, 0));
                    t_blog.title().eq("123");
                })).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (t.`create_time` < ? AND t.`title` = ?) FROM `t_blog` t WHERE t.`deleted` = ? AND date_add(t.`create_time`, interval (?) day) > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2020-01-01T00:00(LocalDateTime),123(String),false(Boolean),1(Long),2020-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testx13() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        TopicTypeEnum topicTypeEnum = easyEntityQuery.queryable(TopicTypeTest1.class).selectColumn(t -> t.topicType()).firstOrNull();
        System.out.println(topicTypeEnum);
        Assert.assertEquals(TopicTypeEnum.CLASSER, topicTypeEnum);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`topic_type` FROM `t_topic_type` t LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        listenerContextManager.clear();
    }

    @Test
    public void testx14() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        TopicTypeEnum topicTypeEnum = easyEntityQuery.queryable(TopicTypeTest1.class).selectColumn(t -> t.topicType().max()).firstOrNull();
        System.out.println(topicTypeEnum);
        Assert.assertEquals(TopicTypeEnum.CLASSER, topicTypeEnum);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT MAX(t.`topic_type`) FROM `t_topic_type` t LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        listenerContextManager.clear();
    }

    @Test
    public void testx15() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        TopicTypeEnum topicTypeEnum = easyEntityQuery.queryable(TopicTypeTest1.class).selectColumn(t -> t.topicType().min()).firstOrNull();
        System.out.println(topicTypeEnum);
        Assert.assertEquals(TopicTypeEnum.CLASSER, topicTypeEnum);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT MIN(t.`topic_type`) FROM `t_topic_type` t LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        listenerContextManager.clear();
    }

    @Test
    public void testx16() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        easyEntityQuery.queryable(TopicTypeTest1.class).whereById(null).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`topic_type`,`create_time` FROM `t_topic_type` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("null(null)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testx17() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.expression().not(() -> {
                        t_blog.title().startsWith("123");
                        t_blog.createTime().isAfter(LocalDateTime.of(2024, 1, 1, 0, 0));
                    });
                }).toList();


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (NOT (`title` LIKE CONCAT(?,'%') AND `create_time` > ?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String),2024-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testx18() {
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.expression().not(() -> {
                        t_blog.or(() -> {
                            t_blog.title().startsWith("123");
                            t_blog.createTime().isAfter(LocalDateTime.of(2024, 1, 1, 0, 0));
                        });
                    });

                }).toList();


        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND (NOT ((`title` LIKE CONCAT(?,'%') OR `create_time` > ?)))", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String),2024-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
//
//        List<SysUserRoleMenuDTO> users = easyEntityQuery.queryable(SysUser.class)
//                .where(u -> {
//                    u.username().startsWith("金");
//                })
//                .orderBy(u -> u.createTime().asc())
//                .selectAutoInclude(SysUserRoleMenuDTO.class)
//                .toList();


        List<GenericDTO> list1 = easyEntityQuery.queryable(SysUser.class)
                .where(s -> {
                    s.phone().startsWith("186");
                    SQLStringTypeColumn<SysUserProxy> phone = s.phone();
                    StringTypeExpression<String> stringStringTypeExpression = phone.subString(1, 2);
                })
                .select(GenericDTO.class, s -> Select.of(
//                        s.FETCHER.allFields(),//如果需要全字段映射
                        s.phone().as(GenericDTO.Fields.value1),
                        s.address().subString(1, 10).as(GenericDTO.Fields.value2)
                )).toList();


//        List<Draft2<String, Long>> list2 = easyEntityQuery.queryable(Topic.class)
//                .innerJoin(BlogEntity.class, (t_topic, t_blog) -> t_blog.id().eq(t_blog.id()))
//                .where((t_topic, t_blog) -> {
//                    t_blog.title().startsWith("123");
//                })
//                .groupBy((t_topic, t_blog) -> GroupKeys.of(t_blog.title()))
//                .select(group -> Select.DRAFT.of(
//                        group.key1(),
//                        group.count()
//                )).toList();
//       easyEntityQuery.queryable(Topic.class)
//                .innerJoin(BlogEntity.class, (t_topic, t_blog) -> t_blog.id().eq(t_blog.id()))
//                .where((t_topic, t_blog) -> {
//                    t_blog.title().startsWith("123");
//                })
//                .groupBy((t_topic, t_blog) -> GroupKeys.of(t_blog.title()))
//                .select(group -> new Draft()
//                        .value1(group.key1())
//                        .value2(group.count())
//                        .build()
//                ).toList();

//        List<SysRole> list3 = easyEntityQuery.queryable(SysRole.class)
//                .where(s -> {
//                    SysMenuProxy first = s.menus().orderBy(menu -> menu.createTime().asc()).element(1);
//
//                    first.createTime().lt(LocalDateTime.of(2020, 1, 1, 0, 0));
//                    first.route().startsWith("/admin");
//
//
//                    s.menus().orderBy(menu -> menu.createTime().asc()).elements(0,2)
//                            .any(menu->{
//                                menu.route().startsWith("/admin");
//                            });
//
//                }).toList();


    }

    @Test
    public void testdraft1() {
        List<Draft1<String>> list = easyEntityQuery.queryable(Topic.class)
                .rightJoin(BlogEntity.class, (t_topic, t_blog) -> t_topic.id().eq(t_blog.id()))
                .where((t_topic, t_blog) -> {
                    t_blog.score().eq(BigDecimal.ZERO);
                })
                .leftJoin(Topic.class, (a, b) -> a.stars().eq(b.stars()))

                .select((a, b) -> Select.DRAFT
                        .value1(
                                a.title().subString(1, 2)
                        )
                        .build()
                ).limit(1).toList();
        System.out.println(list);
    }

    @Test
    public void testJoinParameterCount() {
        Exception e=null;
        try {

            List<Draft1<String>> list = easyEntityQuery.queryable(Topic.class)
                    .rightJoin(BlogEntity.class, (t_topic, t_blog) -> t_topic.id().eq(t_blog.id()))
                    .leftJoin(Topic.class, (a,  c) -> a.stars().eq(c.stars()))
                    .where((a, b) -> {
                        b.stars().isNotNull();
                    })
                    .select((a, b) -> Select.DRAFT
                            .value1(
                                    a.title().subString(1, 2)
                            )
                            .build()
                    ).toList();
        }catch (Exception ex){
            e=ex;
        }
        Assert.assertEquals("plz use three-parameter lambda expression (a, b, c) -> {}",e.getMessage());
    }

}
