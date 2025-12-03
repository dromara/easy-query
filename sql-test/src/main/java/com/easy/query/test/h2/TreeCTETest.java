package com.easy.query.test.h2;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.MyCategory;
import com.easy.query.test.entity.vo.MyCategoryVO2;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2025/12/3 20:53
 * 文件说明
 *
 * @author xuejiaming
 */
public class TreeCTETest extends H2BaseTest{

    @Test
    public void tree8() {
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(MyCategory.class));
        codeFirstCommand.executeWithTransaction(s->s.commit());


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MyCategoryVO2> list = easyEntityQuery.queryable(MyCategory.class)
                .where(m -> {
                    m.id().eq("1");
                })
                .asTreeCTE()
                .selectAutoInclude(MyCategoryVO2.class).toTreeList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH RECURSIVE as_tree_cte(cte_deep,id,parent_id,name) AS ( (SELECT 0 AS cte_deep,t1.id,t1.parent_id,t1.name FROM category t1 WHERE t1.id = ?)  UNION ALL  (SELECT t2.cte_deep + 1 AS cte_deep,t3.id,t3.parent_id,t3.name FROM as_tree_cte t2 INNER JOIN category t3 ON t3.parent_id = t2.id) ) SELECT t.id,t.parent_id,t.name,t.cte_deep FROM as_tree_cte t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
}
