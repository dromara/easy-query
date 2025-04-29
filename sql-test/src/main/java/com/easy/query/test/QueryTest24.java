package com.easy.query.test;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import javax.sql.DataSource;
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
}
