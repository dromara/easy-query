package com.easy.query.test;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2023/12/23 23:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class MyTest1 extends BaseTest{

    @Test
    public void testDraft1(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, String>> list2 = entityQuery.queryable(BlogEntity.class)
                .where(o->{
                    o.title().length().eq(123);
//                    o.createTime().
//                    LocalDateTime.now().plus(1, TimeUnit.MILLISECONDS)
                })
                .groupBy(o -> o.content())
                .selectDraft(o -> Select.draft(
                        o.groupKeys(0).toDraft(String.class),
                        o.content().length()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`content` AS `value1`,CHAR_LENGTH(t.`content`) AS `value2` FROM `t_blog` t WHERE t.`deleted` = ? AND CHAR_LENGTH(t.`title`) = ? GROUP BY t.`content`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }
}
