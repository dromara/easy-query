package com.easy.query.test.pgsql;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.BaseTest;
import com.easy.query.test.entity.MyCategory;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2024/11/9 09:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest19 extends PgSQLBaseTest {


    @Test
    public void testToTreeList() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<MyCategory> treeList = entityQuery.queryable(MyCategory.class)
                .where(m -> {
                    m.id().eq("1");
                }).asTreeCTE()
                .toTreeList();
        Assert.assertEquals(1, treeList.size());
        MyCategory myCategory = treeList.get(0);
        Assert.assertEquals("1", myCategory.getId());
        Assert.assertEquals(2, myCategory.getChildren().size());
        for (MyCategory child : myCategory.getChildren()) {
            if ("3".equals(child.getId())) {
                Assert.assertEquals(2, child.getChildren().size());
                for (MyCategory childChild : child.getChildren()) {

                    if ("5".equals(childChild.getId())) {
                        Assert.assertEquals(0, childChild.getChildren().size());
                    } else if ("6".equals(childChild.getId())) {
                        Assert.assertEquals(0, childChild.getChildren().size());
                    } else {
                        Assert.assertEquals(1, 2);
                    }
                }
            } else if ("4".equals(child.getId())) {
                Assert.assertEquals(0, child.getChildren().size());
            } else {
                Assert.assertEquals(1, 2);
            }
        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH RECURSIVE \"as_tree_cte\" AS ( (SELECT 0 AS \"cte_deep\",t1.\"id\",t1.\"parent_id\",t1.\"name\" FROM \"category\" t1 WHERE t1.\"id\" = ?)  UNION ALL  (SELECT t2.\"cte_deep\" + 1 AS \"cte_deep\",t3.\"id\",t3.\"parent_id\",t3.\"name\" FROM \"as_tree_cte\" t2 INNER JOIN \"category\" t3 ON t3.\"parent_id\" = t2.\"id\") )  SELECT t.\"id\",t.\"parent_id\",t.\"name\" FROM \"as_tree_cte\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        System.out.println("123");
//        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `cte_deep`,t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL)  UNION ALL  (SELECT t2.`cte_deep` + 1 AS `cte_deep`,t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM `as_tree_cte` t2 INNER JOIN `t_topic` t3 ON t3.`stars` = t2.`id`) )  SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `as_tree_cte` t WHERE t.`cte_deep` <= ?", sql);
    }

    @Test
    public void testToTreeList2() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<MyCategory> treeList = entityQuery.queryable(MyCategory.class)
                .where(m -> {
                    m.parentId().eq("1");
                }).asTreeCTE()
                .toTreeList();
        Assert.assertEquals(2,treeList.size());
        for (MyCategory child : treeList) {
            if ("3".equals(child.getId())) {
                Assert.assertEquals(2, child.getChildren().size());
                for (MyCategory childChild : child.getChildren()) {

                    if ("5".equals(childChild.getId())) {
                        Assert.assertEquals(0, childChild.getChildren().size());
                    } else if ("6".equals(childChild.getId())) {
                        Assert.assertEquals(0, childChild.getChildren().size());
                    } else {
                        Assert.assertEquals(1, 2);
                    }
                }
            } else if ("4".equals(child.getId())) {
                Assert.assertEquals(0, child.getChildren().size());
            } else {
                Assert.assertEquals(1, 2);
            }
        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH RECURSIVE \"as_tree_cte\" AS ( (SELECT 0 AS \"cte_deep\",t1.\"id\",t1.\"parent_id\",t1.\"name\" FROM \"category\" t1 WHERE t1.\"parent_id\" = ?)  UNION ALL  (SELECT t2.\"cte_deep\" + 1 AS \"cte_deep\",t3.\"id\",t3.\"parent_id\",t3.\"name\" FROM \"as_tree_cte\" t2 INNER JOIN \"category\" t3 ON t3.\"parent_id\" = t2.\"id\") )  SELECT t.\"id\",t.\"parent_id\",t.\"name\" FROM \"as_tree_cte\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        System.out.println("123");
//        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `cte_deep`,t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL)  UNION ALL  (SELECT t2.`cte_deep` + 1 AS `cte_deep`,t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM `as_tree_cte` t2 INNER JOIN `t_topic` t3 ON t3.`stars` = t2.`id`) )  SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `as_tree_cte` t WHERE t.`cte_deep` <= ?", sql);
    }
}
