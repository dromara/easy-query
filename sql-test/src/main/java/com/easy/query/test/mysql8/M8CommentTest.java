package com.easy.query.test.mysql8;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.M8Comment;
import com.easy.query.test.mysql8.entity.M8User2;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2025/5/6 14:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class M8CommentTest extends BaseTest {
    @Test
    public void testPartitionBy() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<M8Comment> list = easyEntityQuery.queryable(M8Comment.class)
                .includes(m -> m.subComments(), s -> s.limit(3))
                .toList();
        listenerContextManager.clear();
//        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());

        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
//                    Assert.assertEquals("SELECT t.`class_id`,t.`name`,t.`id` AS `__relation__id` FROM `school_student` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("SELECT `id`,`parent_id`,`content`,`username`,`time` FROM `comment`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t2.`id` AS `id`,t2.`parent_id` AS `parent_id`,t2.`content` AS `content`,t2.`username` AS `username`,t2.`time` AS `time` FROM (SELECT t1.`id` AS `id`,t1.`parent_id` AS `parent_id`,t1.`content` AS `content`,t1.`username` AS `username`,t1.`time` AS `time` FROM (SELECT t.`id`,t.`parent_id`,t.`content`,t.`username`,t.`time`,(ROW_NUMBER() OVER (PARTITION BY t.`parent_id`)) AS `__row__` FROM `comment` t WHERE t.`parent_id` IN (?,?,?,?,?)) t1 WHERE t1.`__row__` >= ? AND t1.`__row__` <= ?) t2", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),2(String),3(String),4(String),5(String),1(Long),3(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
    }
}
