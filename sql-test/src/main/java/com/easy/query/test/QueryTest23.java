package com.easy.query.test;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2025/3/11 20:33
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest23 extends BaseTest{


    @Test
    public void testElement3(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, String>> list = easyEntityQuery.queryable(DocUser.class)
                .manyJoin(x -> x.bankCards())
                .select(user -> Select.DRAFT.of(
                        user.bankCards().where(o -> o.type().eq("123")).max(x -> x.code()),
                        user.bankCards().where(o -> o.type().eq("123")).element(0).type()
                )).toList();

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.`__max2__` AS `value1`,t2.`__min3__` AS `value2` FROM `doc_user` t LEFT JOIN (SELECT t1.`uid`,MAX((CASE WHEN t1.`type` = ? THEN t1.`code` ELSE ? END)) AS `__max2__`,MIN(t1.`type`) AS `__min3__` FROM `doc_bank_card` t1 GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),null(null)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }
}
