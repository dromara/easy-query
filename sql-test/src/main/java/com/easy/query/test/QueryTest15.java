package com.easy.query.test;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.UserExtra;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * create time 2024/3/8 11:08
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest15 extends BaseTest {

    @Test
    public void test1() {


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<UserExtra> list = easyEntityQuery.queryable(UserExtra.class)
                    .where(u -> {
                        u.fullName().like("123");
                        u.fullName().in(Arrays.asList("1", "2"));
                        u.age().gt(12);
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`first_name`,`last_name`,`birthday`,CONCAT(`first_name`,`last_name`) AS `full_name`,CEILING((timestampdiff(DAY, `birthday`, NOW()) / ?)) AS `age` FROM `t_user_extra` WHERE CONCAT(`first_name`,`last_name`) LIKE ? AND CONCAT(`first_name`,`last_name`) IN (?,?) AND CEILING((timestampdiff(DAY, `birthday`, NOW()) / ?)) > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("365(Integer),%123%(String),1(String),2(String),365(Integer),12(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void test2() {

        try {
            easyEntityQuery.deletable(UserExtra.class).disableLogicDelete().allowDeleteStatement(true)
                    .whereById("test2")
                    .executeRows();
            UserExtra userExtra = new UserExtra();
            userExtra.setId("test2");
            userExtra.setFirstName("孙");
            userExtra.setLastName("悟空");
            userExtra.setBirthday(LocalDateTime.of(2020, 1, 1, 0, 0));
            easyEntityQuery.insertable(userExtra).executeRows();

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<UserExtra> list = easyEntityQuery.queryable(UserExtra.class)
                    .where(u -> {
                        u.id().eq("test2");
                        u.fullName().like("悟");
                    })
                    .toList();
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`first_name`,`last_name`,`birthday`,CONCAT(`first_name`,`last_name`) AS `full_name`,CEILING((timestampdiff(DAY, `birthday`, NOW()) / ?)) AS `age` FROM `t_user_extra` WHERE `id` = ? AND CONCAT(`first_name`,`last_name`) LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("365(Integer),test2(String),%悟%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            Assert.assertEquals(list.size(),1);
            UserExtra userExtra1 = list.get(0);
            Assert.assertEquals("孙悟空",userExtra1.getFullName());
            Assert.assertTrue(userExtra1.getAge()>4);
            listenerContextManager.clear();
        } finally {

            easyEntityQuery.deletable(UserExtra.class).disableLogicDelete().allowDeleteStatement(true)
                    .whereById("test2")
                    .executeRows();
        }
    }


}
