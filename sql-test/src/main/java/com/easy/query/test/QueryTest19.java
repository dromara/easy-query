package com.easy.query.test;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.relation.MyRelationUser;
import com.easy.query.test.entity.relation.MyRelationUserDTO;
import com.easy.query.test.entity.relation.MyRelationUserDTO1;
import com.easy.query.test.listener.ListenerContext;
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

}
