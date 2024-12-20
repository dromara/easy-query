package com.easy.query.test;

import com.easy.query.api.proxy.base.StringProxy;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.blogtest.SysUser;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.relation.MyRelationUser;
import com.easy.query.test.entity.relation.MyRelationUserDTO;
import com.easy.query.test.entity.relation.MyRelationUserDTO1;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.vo.MyUnion;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2024/11/25 20:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest19 extends BaseTest{

    @org.junit.Test
    public void testDoc99(){
        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        try {

            List<MyRelationUserDTO> users = easyEntityQuery.queryable(MyRelationUser.class)
                    .selectAutoInclude(MyRelationUserDTO.class)
                    .toList();
        }catch (Exception ex){

        }
        Assert.assertEquals(2,listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.`id`,t.`name` FROM `t_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("1(Integer),%小学%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`id`,t.`user_id`,t.`name`,t.`book_type`,t.`create_time` FROM `relation_book` t WHERE (t.`user_id` IN (?) AND t.`create_time` <= ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),2022-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        listenerContextManager.clear();
    }
    @Test
    public void testDoc100(){
        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        try {

            List<MyRelationUserDTO1> users = easyEntityQuery.queryable(MyRelationUser.class)
                    .selectAutoInclude(MyRelationUserDTO1.class)
                    .toList();
        }catch (Exception ex){
        }

        Assert.assertEquals(3,listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.`id`,t.`name` FROM `t_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("1(Integer),%小学%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT `first_id`,`second_id` FROM `relation_route` WHERE `first_id` IN (?) AND `type` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
            Assert.assertEquals("SELECT t.`id`,t.`name` FROM `relation_teacher` t WHERE (t.`id` IN (?) AND t.`name` = ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2(String),12345(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
       listenerContextManager.clear();
    }

    @Test
    public  void test1(){



        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<SysUser> userInHz = easyEntityQuery.queryable(SysUser.class)
                    .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                    .where(u -> {
                        u.name().eq("小明");
                        //隐式子查询会自动join地址表
                        //根据条件是否生效自动添加address表的join，比如eq("")和eq("杭州生成的表不存在address和city的区别")
                        u.address().city().eq("");
                    }).toList();
        }catch (Exception ignored){

        }

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t WHERE t.`name` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("小明(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
    @Test
    public  void test2(){



        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<SysUser> userInHz = easyEntityQuery.queryable(SysUser.class)
                    .where(u -> {
                        u.name().eq("小明");
                        //隐式子查询会自动join地址表
                        //根据条件是否生效自动添加address表的join，比如eq("")和eq("杭州生成的表不存在address和city的区别")
                        u.address().city().eq("");
                    }).toList();
        }catch (Exception ignored){

        }

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`company_id`,t.`name`,t.`age`,t.`create_time` FROM `t_user` t LEFT JOIN `t_user_address` t1 ON t1.`user_id` = t.`id` WHERE t.`name` = ? AND t1.`city` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("小明(String),(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
      public void testUnion1(){
          String sql = "SELECT '未开始'AS task_status\n" +
                  "UNION ALL SELECT '进行中'\n" +
                  "UNION ALL SELECT '已完成'\n" +
                  "UNION ALL SELECT '审核中'\n" +
                  "UNION ALL SELECT '已关闭'\n" +
                  "UNION ALL SELECT '待修改（无需审核）'\n" +
                  "UNION ALL SELECT '待修改（需审核）'";
        List<BlogEntity> list = easyEntityQuery.queryable(sql, MyUnion.class)
                .leftJoin(Topic.class, (m, t2) -> {
                    m.taskStatus().eq(t2.stars());
                    t2.id().eq("10");
                    
                }).groupBy((m1, t2) -> GroupKeys.TABLE2.of(m1.taskStatus()))
                .select(group -> {
                    BlogEntityProxy r = new BlogEntityProxy();
                    r.id().set(group.key1());
                    r.title().set(String.valueOf(group.groupTable().t2.stars().count()));//把表达式字符串化给title
                    r.title().set(group.groupTable().t2.stars().count().asAnyType(String.class));//语言层面的强转
                    r.title().set(group.groupTable().t2.stars().count().toStr());//sql层面的cast
                    return r;

                }).toList();
    }

}
