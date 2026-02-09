package com.easy.query.test.pgsql;

import com.easy.query.api.proxy.base.ClassProxy;
import com.easy.query.api.proxy.base.MapProxy;
import com.easy.query.api.proxy.base.MapTypeProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.key.MapKey;
import com.easy.query.api.proxy.key.MapKeys;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.inject.ServiceProvider;
import com.easy.query.core.migration.MigrationsSQLGenerator;
import com.easy.query.core.migration.data.TableMigrationData;
import com.easy.query.core.proxy.columns.types.SQLBigDecimalTypeColumn;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.proxy.Draft2Proxy;
import com.easy.query.core.proxy.extension.functions.type.NumberTypeExpression;
import com.easy.query.core.proxy.grouping.Grouping1;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.dto.MyCategoryDTO;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.MyCategory;
import com.easy.query.test.entity.UUIDEntity;
import com.easy.query.test.entity.proxy.MyCategoryProxy;
import com.easy.query.test.entity.vo.MyCategoryVO2;
import com.easy.query.test.entity.vo.MyCategoryVO3;
import com.easy.query.test.entity.vo.MyCategoryVO4;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.view.TreeC;
import com.easy.query.test.pgsql.proxy.PgItemProxy;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * create time 2024/11/9 09:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest19 extends PgSQLBaseTest {
    @Test
    public void tree7() {

//
//        Query<String> pidQuery = entityQuery.queryable(MyCategory.class)
//                .where(m -> {
//                    m.name().eq("myuid");//查询我执行的
//                }).asTreeCTE(op -> op.setUp(true))//向上递归
//                .where(m -> m.parentId().isNull())//只要树根节点
//                .selectColumn(m -> m.id());//构建顶级节点的id集合
//
//        EasyPageResult<MyCategory> pageResult = entityQuery.queryable(MyCategory.class)
//                .where(m -> {
//                    m.parentId().isNull();
//                    m.id().in(pidQuery);
//                }).toPageResult(1, 20);


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MyCategoryVO3> list = entityQuery.queryable(MyCategory.class)
                .where(m -> {
                    m.id().eq("1");
                })
                .asTreeCTE(op -> {
                    op.setDeepColumnName("deep1");
                })
                .selectAutoInclude(MyCategoryVO3.class).toTreeList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH RECURSIVE \"as_tree_cte\" AS ( (SELECT 0 AS \"deep1\",t1.\"id\",t1.\"parent_id\",t1.\"name\" FROM \"category\" t1 WHERE t1.\"id\" = ?)  UNION ALL  (SELECT t2.\"deep1\" + 1 AS \"deep1\",t3.\"id\",t3.\"parent_id\",t3.\"name\" FROM \"as_tree_cte\" t2 INNER JOIN \"category\" t3 ON t3.\"parent_id\" = t2.\"id\") ) SELECT t.\"id\",t.\"parent_id\",t.\"name\",t.\"deep1\" FROM \"as_tree_cte\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void tree7_1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MyCategoryVO4> list = entityQuery.queryable(MyCategory.class)
                .where(m -> {
                    m.id().eq("1");
                })
                .asTreeCTE(op -> {
                    op.setDeepColumnName("deep");
                    op.setDeepInCustomSelect(true);
                })
                .leftJoin(MyCategory.class, (m, b2) -> m.id().eq(b2.id()))
                .select(MyCategoryVO4.class,(m1, m2) -> Select.of(
                        m1.FETCHER.allFields(),
                        m2.name().as("joinName")//,
//                        m1.expression().rawSQLStatement("{0}",new ColumnParameter(m1,"deep1")).as("deep1")
                )).toTreeList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH RECURSIVE \"as_tree_cte\" AS ( (SELECT 0 AS \"deep\",t1.\"id\",t1.\"parent_id\",t1.\"name\" FROM \"category\" t1 WHERE t1.\"id\" = ?)  UNION ALL  (SELECT t2.\"deep\" + 1 AS \"deep\",t3.\"id\",t3.\"parent_id\",t3.\"name\" FROM \"as_tree_cte\" t2 INNER JOIN \"category\" t3 ON t3.\"parent_id\" = t2.\"id\") ) SELECT t.\"id\",t.\"parent_id\",t.\"name\",t6.\"name\" AS \"join_name\",t.\"deep\" FROM \"as_tree_cte\" t LEFT JOIN \"category\" t6 ON t.\"id\" = t6.\"id\"", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void tree7_2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MyCategoryVO4> list = entityQuery.queryable(MyCategory.class)
                .where(m -> {
                    m.id().eq("1");
                })
                .asTreeCTE(op -> {
                    op.setDeepColumnName("deep1");
                    op.setDeepInCustomSelect(true);
                })
                .leftJoin(MyCategory.class, (m, b2) -> m.id().eq(b2.id()))
                .select(MyCategoryVO4.class,(m1, m2) -> Select.of(
                        m1.FETCHER.allFields(),
                        m2.name().as("joinName")//,
//                        m1.expression().rawSQLStatement("{0}",new ColumnParameter(m1,"deep1")).as("deep1")
                )).toTreeList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH RECURSIVE \"as_tree_cte\" AS ( (SELECT 0 AS \"deep1\",t1.\"id\",t1.\"parent_id\",t1.\"name\" FROM \"category\" t1 WHERE t1.\"id\" = ?)  UNION ALL  (SELECT t2.\"deep1\" + 1 AS \"deep1\",t3.\"id\",t3.\"parent_id\",t3.\"name\" FROM \"as_tree_cte\" t2 INNER JOIN \"category\" t3 ON t3.\"parent_id\" = t2.\"id\") ) SELECT t.\"id\",t.\"parent_id\",t.\"name\",t6.\"name\" AS \"join_name\",t.\"deep1\" FROM \"as_tree_cte\" t LEFT JOIN \"category\" t6 ON t.\"id\" = t6.\"id\"", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (MyCategoryVO4 myCategoryVO4 : list) {
            Assert.assertNotNull(myCategoryVO4.getDeep1());
        }
    }

    @Test
    public void tree8() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MyCategoryVO2> list = entityQuery.queryable(MyCategory.class)
                .where(m -> {
                    m.id().eq("1");
                })
                .asTreeCTE()
                .selectAutoInclude(MyCategoryVO2.class).toTreeList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH RECURSIVE \"as_tree_cte\" AS ( (SELECT 0 AS \"cte_deep\",t1.\"id\",t1.\"parent_id\",t1.\"name\" FROM \"category\" t1 WHERE t1.\"id\" = ?)  UNION ALL  (SELECT t2.\"cte_deep\" + 1 AS \"cte_deep\",t3.\"id\",t3.\"parent_id\",t3.\"name\" FROM \"as_tree_cte\" t2 INNER JOIN \"category\" t3 ON t3.\"parent_id\" = t2.\"id\") ) SELECT t.\"id\",t.\"parent_id\",t.\"name\",t.\"cte_deep\" FROM \"as_tree_cte\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void tree9() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, Number>> list = entityQuery.queryable(MyCategory.class)
                .where(m -> {
                    m.id().eq("1");
                })
                .asTreeCTE()
                .leftJoin(BlogEntity.class, (m, b2) -> m.id().eq(b2.id()))
                .groupBy((m1, b2) -> GroupKeys.of(m1.name()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.groupTable().t2.star().sum()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH RECURSIVE \"as_tree_cte\" AS ( (SELECT 0 AS \"cte_deep\",t1.\"id\",t1.\"parent_id\",t1.\"name\" FROM \"category\" t1 WHERE t1.\"id\" = ?)  UNION ALL  (SELECT t2.\"cte_deep\" + 1 AS \"cte_deep\",t3.\"id\",t3.\"parent_id\",t3.\"name\" FROM \"as_tree_cte\" t2 INNER JOIN \"category\" t3 ON t3.\"parent_id\" = t2.\"id\") ) SELECT t.\"name\" AS \"value1\",SUM(t6.\"star\") AS \"value2\" FROM \"as_tree_cte\" t LEFT JOIN \"t_blog\" t6 ON t6.\"deleted\" = ? AND t.\"id\" = t6.\"id\" GROUP BY t.\"name\"", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void tree10() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<MyCategory> treeList = entityQuery.queryable(MyCategory.class)
                .where(m -> {
                    m.id().eq("1");
                }).useInterceptor("aaa")
                .asTreeCTE()
                .orderBy(m -> m.id().desc())
                .toTreeList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH RECURSIVE \"as_tree_cte\" AS ( (SELECT 0 AS \"cte_deep\",t1.\"id\",t1.\"parent_id\",t1.\"name\" FROM \"category\" t1 WHERE 1=1 AND t1.\"id\" = ?)  UNION ALL  (SELECT t2.\"cte_deep\" + 1 AS \"cte_deep\",t3.\"id\",t3.\"parent_id\",t3.\"name\" FROM \"as_tree_cte\" t2 INNER JOIN \"category\" t3 ON 1=1 AND t3.\"parent_id\" = t2.\"id\" WHERE 1=1) ) SELECT t.\"id\",t.\"parent_id\",t.\"name\",t.\"cte_deep\" FROM \"as_tree_cte\" t WHERE 1=1 ORDER BY t.\"id\" DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void tree10_1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<MyCategory> treeList = entityQuery.queryable(MyCategory.class)
                .where(m -> {
                    m.id().eq("1");
                }).useInterceptor("aaa").noInterceptor()
                .asTreeCTE()
                .orderBy(m -> m.id().desc())
                .toTreeList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH RECURSIVE \"as_tree_cte\" AS ( (SELECT 0 AS \"cte_deep\",t1.\"id\",t1.\"parent_id\",t1.\"name\" FROM \"category\" t1 WHERE t1.\"id\" = ?)  UNION ALL  (SELECT t2.\"cte_deep\" + 1 AS \"cte_deep\",t3.\"id\",t3.\"parent_id\",t3.\"name\" FROM \"as_tree_cte\" t2 INNER JOIN \"category\" t3 ON t3.\"parent_id\" = t2.\"id\") ) SELECT t.\"id\",t.\"parent_id\",t.\"name\",t.\"cte_deep\" FROM \"as_tree_cte\" t ORDER BY t.\"id\" DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void tree11() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<MyCategory> treeList = entityQuery.queryable(MyCategory.class)
                .where(m -> {
                    m.id().eq("1");
                }).noInterceptor()
                .asTreeCTE()
                .orderBy(m -> m.id().desc())
                .toTreeList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH RECURSIVE \"as_tree_cte\" AS ( (SELECT 0 AS \"cte_deep\",t1.\"id\",t1.\"parent_id\",t1.\"name\" FROM \"category\" t1 WHERE t1.\"id\" = ?)  UNION ALL  (SELECT t2.\"cte_deep\" + 1 AS \"cte_deep\",t3.\"id\",t3.\"parent_id\",t3.\"name\" FROM \"as_tree_cte\" t2 INNER JOIN \"category\" t3 ON t3.\"parent_id\" = t2.\"id\") ) SELECT t.\"id\",t.\"parent_id\",t.\"name\",t.\"cte_deep\" FROM \"as_tree_cte\" t ORDER BY t.\"id\" DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
//        String sql = entityQuery.getEasyQueryClient().queryable(Topic.class)
//                .leftJoin(Topic.class, (b, t2) -> b.eq(t2, "id", "id"))
//                .leftJoin(Topic.class, (b1, t2, t3) -> t2.eq(t3, "id", "id"))
//                .leftJoin(Topic.class, (b1, t2, t3, t4) -> t3.eq(t4, "id", "id"))
//                .leftJoin(MyCategory.class, (b1, t2, t3, t4, t5) -> t4.eq(t5, "id", "id"))
//                .select(Topic.class, t -> t.columnAll())
//                .toSQL();
//        System.out.println(sql);

//        List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
//                .leftJoin(Topic.class,(t, b2) -> {
//                    t.id().eq(b2.id());
//                })
//                .leftJoin(Topic.class,(t, b2,b3) -> {
//                    b2.id().eq(b3.id());
//                })
//                .leftJoin(Topic.class,(t, b2,b3,b4) -> {
//                    b2.id().eq(b3.id());
//                })
//                .leftJoin(Topic.class,(t, b2,b3,b4,b5) -> {
//                    b2.id().eq(b3.id());
//                })
//                .where(t -> {
//                    t.id().eq("1");
//                }).toList();


//        List<Draft2<String, Number>> list = entityQuery.queryable(MyCategory.class)
//                                            .where(m -> {
//                                                m.id().eq("1");
//                                            })
//                                            .asTreeCTE()
//                                            .leftJoin(BlogEntity.class, (m, b2) -> m.id().eq(b2.id()))
//                                            .groupBy((m1, b2) -> GroupKeys.of(m1.name()))
//                                            .select(group -> Select.DRAFT.of(
//                                                    group.key1(),
//                                                    group.groupTable().t2.star().sum()
//                                            )).toList();


    }

    @Test
    public void tree12() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.id().in(
                            entityQuery.queryable(MyCategory.class)
                                    .where(m -> {
                                        m.id().eq("1");
                                    })
                                    .asTreeCTE().select(m -> m.id())
                    );
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH RECURSIVE \"as_tree_cte\" AS ( (SELECT 0 AS \"cte_deep\",t2.\"id\",t2.\"parent_id\",t2.\"name\" FROM \"category\" t2 WHERE t2.\"id\" = ?)  UNION ALL  (SELECT t3.\"cte_deep\" + 1 AS \"cte_deep\",t4.\"id\",t4.\"parent_id\",t4.\"name\" FROM \"as_tree_cte\" t3 INNER JOIN \"category\" t4 ON t4.\"parent_id\" = t3.\"id\") ) SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"id\" IN (SELECT t1.\"id\" FROM \"as_tree_cte\" t1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

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
        Assert.assertEquals("WITH RECURSIVE \"as_tree_cte\" AS ( (SELECT 0 AS \"cte_deep\",t1.\"id\",t1.\"parent_id\",t1.\"name\" FROM \"category\" t1 WHERE t1.\"id\" = ?)  UNION ALL  (SELECT t2.\"cte_deep\" + 1 AS \"cte_deep\",t3.\"id\",t3.\"parent_id\",t3.\"name\" FROM \"as_tree_cte\" t2 INNER JOIN \"category\" t3 ON t3.\"parent_id\" = t2.\"id\") ) SELECT t.\"id\",t.\"parent_id\",t.\"name\",t.\"cte_deep\" FROM \"as_tree_cte\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        System.out.println("123");
//        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `cte_deep`,t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL)  UNION ALL  (SELECT t2.`cte_deep` + 1 AS `cte_deep`,t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM `as_tree_cte` t2 INNER JOIN `t_topic` t3 ON t3.`stars` = t2.`id`) ) SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `as_tree_cte` t WHERE t.`cte_deep` <= ?", sql);
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
        Assert.assertEquals(2, treeList.size());
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
        Assert.assertEquals("WITH RECURSIVE \"as_tree_cte\" AS ( (SELECT 0 AS \"cte_deep\",t1.\"id\",t1.\"parent_id\",t1.\"name\" FROM \"category\" t1 WHERE t1.\"parent_id\" = ?)  UNION ALL  (SELECT t2.\"cte_deep\" + 1 AS \"cte_deep\",t3.\"id\",t3.\"parent_id\",t3.\"name\" FROM \"as_tree_cte\" t2 INNER JOIN \"category\" t3 ON t3.\"parent_id\" = t2.\"id\") ) SELECT t.\"id\",t.\"parent_id\",t.\"name\",t.\"cte_deep\" FROM \"as_tree_cte\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        System.out.println("123");
//        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `cte_deep`,t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL)  UNION ALL  (SELECT t2.`cte_deep` + 1 AS `cte_deep`,t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM `as_tree_cte` t2 INNER JOIN `t_topic` t3 ON t3.`stars` = t2.`id`) ) SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `as_tree_cte` t WHERE t.`cte_deep` <= ?", sql);
    }


    @Test
    public void testJoining() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, String>> list = entityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.createTime().lt(LocalDateTime.of(2022, 1, 1, 0, 0));
                }).groupBy(t_blog -> GroupKeys.of(t_blog.title()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.groupTable().content().joining(",")
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.\"title\" AS \"value1\",STRING_AGG((t.\"content\")::TEXT, ?) AS \"value2\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" < ? GROUP BY t.\"title\"", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),false(Boolean),2022-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testJoining2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, String>> list = entityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.createTime().lt(LocalDateTime.of(2022, 1, 1, 0, 0));
                }).groupBy(t_blog -> GroupKeys.of(t_blog.title()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.joining(x -> x.content())
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.\"title\" AS \"value1\",STRING_AGG((t.\"content\")::TEXT, ?) AS \"value2\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" < ? GROUP BY t.\"title\"", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),false(Boolean),2022-01-01T00:00(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testJoining3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        EntityQueryable<Draft2Proxy<String, String>, Draft2<String, String>> select = entityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.createTime().lt(LocalDateTime.of(2022, 1, 1, 0, 0));
                }).groupBy(t_blog -> GroupKeys.of(t_blog.title()))
                .select(group -> Select.DRAFT.of(
                        group.key1(),
                        group.joining(x -> x.content(), ",")
                ));

        List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                .leftJoin(select, (a, b) -> a.content().eq(b.value2()))
                .where((a, b) -> {
                    b.value1().eq("123");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\" FROM \"t_blog\" t LEFT JOIN (SELECT t1.\"title\" AS \"value1\",STRING_AGG((t1.\"content\")::TEXT, ?) AS \"value2\" FROM \"t_blog\" t1 WHERE t1.\"deleted\" = ? AND t1.\"create_time\" < ? GROUP BY t1.\"title\") t3 ON t.\"content\" = t3.\"value2\" WHERE t.\"deleted\" = ? AND t3.\"value1\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),false(Boolean),2022-01-01T00:00(LocalDateTime),false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testJoining4() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        MapKey<String> column1 = MapKeys.stringKey("column1");
        MapKey<String> column2 = MapKeys.stringKey("column2");
        EntityQueryable<MapTypeProxy, Map<String, Object>> select = entityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.createTime().lt(LocalDateTime.of(2022, 1, 1, 0, 0));
                }).groupBy(t_blog -> GroupKeys.of(t_blog.title()))
                .select(group -> new MapTypeProxy()
                        .put(column1, group.key1())
                        .put(column2, group.joining(x -> x.content(), ","))
                );

        List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                .leftJoin(select, (a, b) -> a.content().eq(b.get(column1)))
                .where((a, b) -> {
                    b.get(column2).eq("123");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\" FROM \"t_blog\" t LEFT JOIN (SELECT t1.\"title\" AS \"column1\",STRING_AGG((t1.\"content\")::TEXT, ?) AS \"column2\" FROM \"t_blog\" t1 WHERE t1.\"deleted\" = ? AND t1.\"create_time\" < ? GROUP BY t1.\"title\") t3 ON t.\"content\" = t3.\"column1\" WHERE t.\"deleted\" = ? AND t3.\"column2\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String),false(Boolean),2022-01-01T00:00(LocalDateTime),false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testChunk() {
        entityQuery.queryable(BlogEntity.class)
                .toChunk(100, s -> {
                });
    }

    @Test
    public void testMapChain() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Map<String, Object>> list = entityQuery.queryable(BlogEntity.class)
                .select(t_blog -> new MapProxy()
                        .put("aaa", t_blog.score())
                        .put("bbb", t_blog.star())
                ).where(o -> {
                    o.get("aaa").eq(123);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"aaa\" AS \"aaa\",t1.\"bbb\" AS \"bbb\" FROM (SELECT t.\"score\" AS \"aaa\",t.\"star\" AS \"bbb\" FROM \"t_blog\" t WHERE t.\"deleted\" = ?) t1 WHERE t1.\"aaa\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testMapChain2() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Map<String, Object>> list = entityQuery.queryable(BlogEntity.class)
                .select(t_blog -> new MapProxy()
                        .put("aaa", t_blog.score().multiply(100))
                        .put("bbb", t_blog.star())
                ).where(o -> {
                    o.get("aaa").eq(123);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"aaa\" AS \"aaa\",t1.\"bbb\" AS \"bbb\" FROM (SELECT (t.\"score\" * ?) AS \"aaa\",t.\"star\" AS \"bbb\" FROM \"t_blog\" t WHERE t.\"deleted\" = ?) t1 WHERE t1.\"aaa\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("100(Integer),false(Boolean),123(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }


    @Test
    public void testToTreeList3() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<MyCategory> treeList = entityQuery.queryable(MyCategory.class)
                .where(m -> {
                    m.id().eq("1");
                }).asTreeCTE(op -> {
                    op.setChildFilter(child -> {
                        child.name().like("123");
                    });
                })
                .toTreeList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH RECURSIVE \"as_tree_cte\" AS ( (SELECT 0 AS \"cte_deep\",t1.\"id\",t1.\"parent_id\",t1.\"name\" FROM \"category\" t1 WHERE t1.\"id\" = ?)  UNION ALL  (SELECT t2.\"cte_deep\" + 1 AS \"cte_deep\",t3.\"id\",t3.\"parent_id\",t3.\"name\" FROM \"as_tree_cte\" t2 INNER JOIN \"category\" t3 ON t3.\"parent_id\" = t2.\"id\" WHERE t3.\"name\" LIKE ?) ) SELECT t.\"id\",t.\"parent_id\",t.\"name\",t.\"cte_deep\" FROM \"as_tree_cte\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String),%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        System.out.println("123");
//        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `cte_deep`,t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL)  UNION ALL  (SELECT t2.`cte_deep` + 1 AS `cte_deep`,t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM `as_tree_cte` t2 INNER JOIN `t_topic` t3 ON t3.`stars` = t2.`id`) ) SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `as_tree_cte` t WHERE t.`cte_deep` <= ?", sql);
        LocalDateTime localDateTime = entityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.content().like("123");
                }).maxOrNull(t -> t.createTime());
        List<Draft2<Long, LocalDateTime>> list = entityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.content().like("123");
                })
                .select(t_blog -> Select.DRAFT.of(
                        t_blog.id().count(),
                        t_blog.createTime().max()
                )).toList();
        List<Grouping1<Integer>> list1 = entityQuery.queryable(BlogEntity.class)
                .where(t_blog -> {
                    t_blog.content().like("123");
                    t_blog.content().max();//你觉得这段代码在干嘛
                    //他只是定义了一个content字段的max函数片段即没有比较也没有干嘛你觉得eq应该怎么给你生成
                })
                .groupBy(t_blog -> GroupKeys.of(t_blog.star()))
                .having(x -> x.groupTable().createTime().max().lt(localDateTime.now()))
                .toList();


//        entityQuery.queryable(BlogEntity.class)
//                .where(t_blog -> {
//                    t_blog.content().eq(
//                            entityQuery.queryable(BlogEntity.class)
//                                    .where(t_blog1 -> {
//                                        t_blog1.star().gt(1);
//                                    }).selectColumn(t_blog1 -> t_blog1.content().max())
//                    );
//                })
    }


    @Test
    public void datetimeformat() {

        String formater = "yyyy年MM-01 HH:mm分ss秒";
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<LocalDateTime, String>> list = entityQuery.queryable(BlogEntity.class)
                .select(d -> Select.DRAFT.of(
                        d.createTime(),
                        d.createTime().format(formater)
                )).toList();
        Assert.assertFalse(list.isEmpty());
        for (Draft2<LocalDateTime, String> timeAndFormat : list) {
            LocalDateTime value1 = timeAndFormat.getValue1();
            String format = value1.format(DateTimeFormatter.ofPattern(formater));
            Assert.assertEquals(format, timeAndFormat.getValue2());
        }

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.\"create_time\" AS \"value1\",TO_CHAR((t.\"create_time\")::TIMESTAMP,'YYYY年MM-01 HH24:MI分SS秒') AS \"value2\" FROM \"t_blog\" t WHERE t.\"deleted\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    //    @Test
//    public void test(){
//        EntityMetadata entityMetadata = entityQuery.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(TestPgSQLEntity.class);
//        String tableName = entityMetadata.getTableName();
//        System.out.println("TestPgSQLEntity:"+tableName);
//        List<TestPgSQLEntity> list = entityQuery.queryable(TestPgSQLEntity.class).toList();
//    }
    @Test
    public void concat1() {
        List<Map> maps = entityQuery.sqlQuery("select CONCAT('a', null , 'b') as \"aa\" ", Map.class);
        System.out.println(maps);
        Object o = maps.get(0).get("aa");
        Assert.assertEquals("ab", o);
    }


    @Test
    public void test() {
        BlogEntity blogEntity = entityQuery.queryable(BlogEntity.class).includes(t_blog -> t_blog.users()).where(t_blog -> {
            t_blog.title().eq("title0");
        }).singleNotNull();
        Assert.assertNotNull(blogEntity.getUsers());
        Assert.assertEquals(1, blogEntity.getUsers().size());
        List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                .configure(s -> s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(t_blog -> {
                    t_blog.users().count().gt(0L);
                }).toList();
        List<BlogEntity> list1 = entityQuery.queryable(BlogEntity.class)
                .configure(s -> s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(t_blog -> {
                    t_blog.users().any();
                }).toList();
        List<BlogEntity> list2 = entityQuery.queryable(BlogEntity.class)
                .configure(s -> s.getBehavior().addBehavior(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(t_blog -> {
                    t_blog.users().where(s -> s.id().isNotBlank()).any();
                }).toList();
    }


    @Test
    public void testUUId() {
        UUID uuid = UUID.randomUUID();

        UUIDEntity uuidEntity = new UUIDEntity();
        uuidEntity.setId1(uuid);
        uuidEntity.setId2(uuid);

        entityQuery.insertable(uuidEntity).executeRows();

        UUIDEntity uuidEntity1 = entityQuery.queryable(UUIDEntity.class).firstNotNull();
        Assert.assertEquals(uuid, uuidEntity1.getId1());
        Assert.assertEquals(uuid, uuidEntity1.getId2());
    }

    @Test
    public void testToTreeListDTO() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<MyCategoryDTO> treeList = entityQuery.queryable(MyCategory.class)
                .where(m -> {
                    m.id().eq("1");
                }).asTreeCTE()
                .select(MyCategoryDTO.class)
                .toTreeList();
        Assert.assertEquals(1, treeList.size());
        MyCategoryDTO myCategory = treeList.get(0);
        Assert.assertEquals("1", myCategory.getId());
        Assert.assertEquals(2, myCategory.getChildren().size());
        for (MyCategoryDTO child : myCategory.getChildren()) {
            if ("3".equals(child.getId())) {
                Assert.assertEquals(2, child.getChildren().size());
                for (MyCategoryDTO childChild : child.getChildren()) {

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
        Assert.assertEquals("WITH RECURSIVE \"as_tree_cte\" AS ( (SELECT 0 AS \"cte_deep\",t1.\"id\",t1.\"parent_id\",t1.\"name\" FROM \"category\" t1 WHERE t1.\"id\" = ?)  UNION ALL  (SELECT t2.\"cte_deep\" + 1 AS \"cte_deep\",t3.\"id\",t3.\"parent_id\",t3.\"name\" FROM \"as_tree_cte\" t2 INNER JOIN \"category\" t3 ON t3.\"parent_id\" = t2.\"id\") ) SELECT t.\"id\",t.\"parent_id\",t.\"name\",t.\"cte_deep\" FROM \"as_tree_cte\" t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        System.out.println("123");
//        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `cte_deep`,t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NOT NULL)  UNION ALL  (SELECT t2.`cte_deep` + 1 AS `cte_deep`,t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM `as_tree_cte` t2 INNER JOIN `t_topic` t3 ON t3.`stars` = t2.`id`) ) SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `as_tree_cte` t WHERE t.`cte_deep` <= ?", sql);
    }


    @Test
    public void cteViewTree1() {
        ServiceProvider service = entityQuery.getRuntimeContext().getService(ServiceProvider.class);

        List<TreeC> list = entityQuery.queryable(TreeC.class)
                .asTreeCTE()
                .toList();
    }


    @Test
    public void testAll() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<DocUser> list = entityQuery.queryable(DocUser.class)
                .includes(user -> user.bankCards())
                .where(user -> {
                    user.bankCards().where(bc -> bc.type().eq("储蓄卡")).all(bc -> bc.code().startsWith("33123"));
                }).toList();
        int size = list.size();
        Assert.assertEquals(3, size);
        List<DocUser> newCards = list.stream().filter(user -> {
            //因为null记录不会被like返回所以直接过滤null
            return user.getBankCards().stream().filter(o -> Objects.equals(o.getType(), "储蓄卡")&&o.getCode() != null).allMatch(o -> o.getCode().startsWith("33123"));
        }).collect(Collectors.toList());
        Assert.assertEquals(3, newCards.size());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.\"id\",t.\"name\",t.\"phone\",t.\"age\" FROM \"doc_user\" t WHERE NOT ( EXISTS (SELECT 1 FROM \"doc_bank_card\" t1 WHERE t1.\"uid\" = t.\"id\" AND t1.\"type\" = ? AND (NOT (t1.\"code\" LIKE CONCAT((?)::TEXT,'%'))) LIMIT 1))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("储蓄卡(String),33123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.\"id\",t.\"uid\",t.\"code\",t.\"type\",t.\"bank_id\" FROM \"doc_bank_card\" t WHERE t.\"uid\" IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("小明id(String),小红id(String),小黄id(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        listenerContextManager.clear();
    }
    @Test
    public void testAll1() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<DocUser> list = entityQuery.queryable(DocUser.class)
                .includes(user -> user.bankCards())
                .where(user -> {
                    user.bankCards().where(bc -> bc.type().eq("储蓄卡")).all(bc -> bc.code().nullOrDefault("").startsWith("33123"));
                }).toList();
        int size = list.size();
        Assert.assertEquals(1, size);
        List<DocUser> newCards = list.stream().filter(user -> {
            return user.getBankCards().stream().filter(o -> Objects.equals(o.getType(), "储蓄卡")).allMatch(o -> (o.getCode() == null ? "" : o.getCode()).startsWith("33123"));
        }).collect(Collectors.toList());
        Assert.assertEquals(1, newCards.size());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.\"id\",t.\"name\",t.\"phone\",t.\"age\" FROM \"doc_user\" t WHERE NOT ( EXISTS (SELECT 1 FROM \"doc_bank_card\" t1 WHERE t1.\"uid\" = t.\"id\" AND t1.\"type\" = ? AND (NOT (COALESCE(t1.\"code\",?) LIKE CONCAT((?)::TEXT,'%'))) LIMIT 1))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("储蓄卡(String),(String),33123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.\"id\",t.\"uid\",t.\"code\",t.\"type\",t.\"bank_id\" FROM \"doc_bank_card\" t WHERE t.\"uid\" IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("小黄id(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        listenerContextManager.clear();
    }

    @Test
    public void testAll2() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<DocUser> list = entityQuery.queryable(DocUser.class)
                .includes(user -> user.bankCards())
                .where(user -> {
                    user.bankCards().where(bc -> bc.type().eq("储蓄卡")).all(bc -> {
                        bc.code().startsWith("33123");
                        bc.code().startsWith("45678");
                    });
                }).toList();
        int size = list.size();
        Assert.assertEquals(3, size);
        List<DocUser> newCards = list.stream().filter(user -> {
            //因为null记录不会被like返回所以直接过滤null
            return user.getBankCards().stream().filter(o -> Objects.equals(o.getType(), "储蓄卡")&&o.getCode() != null).allMatch(o -> o.getCode().startsWith("33123")&&o.getCode().startsWith("45678"));
        }).collect(Collectors.toList());
        Assert.assertEquals(3, newCards.size());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.\"id\",t.\"name\",t.\"phone\",t.\"age\" FROM \"doc_user\" t WHERE NOT ( EXISTS (SELECT 1 FROM \"doc_bank_card\" t1 WHERE t1.\"uid\" = t.\"id\" AND t1.\"type\" = ? AND (NOT (t1.\"code\" LIKE CONCAT((?)::TEXT,'%') AND t1.\"code\" LIKE CONCAT((?)::TEXT,'%'))) LIMIT 1))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("储蓄卡(String),33123(String),45678(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.\"id\",t.\"uid\",t.\"code\",t.\"type\",t.\"bank_id\" FROM \"doc_bank_card\" t WHERE t.\"uid\" IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("小明id(String),小红id(String),小黄id(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        listenerContextManager.clear();
    }

    @Test
    public void testAllGroupJoin() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<DocUser> list = entityQuery.queryable(DocUser.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .includes(user -> user.bankCards())
                .where(user -> {
                    user.bankCards().where(bc -> bc.type().eq("储蓄卡")).all(bc -> bc.code().startsWith("33123"));
                }).toList();
        int size = list.size();
        Assert.assertEquals(3, size);
        List<DocUser> newCards = list.stream().filter(user -> {
            //因为null记录不会被like返回所以直接过滤null
            return user.getBankCards().stream().filter(o -> Objects.equals(o.getType(), "储蓄卡")&&o.getCode() != null).allMatch(o -> o.getCode().startsWith("33123"));
        }).collect(Collectors.toList());
        Assert.assertEquals(3, newCards.size());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.\"id\",t.\"name\",t.\"phone\",t.\"age\" FROM \"doc_user\" t LEFT JOIN (SELECT t1.\"uid\" AS \"__group_key1__\",(COUNT(?) <= 0) AS \"__none2__\" FROM \"doc_bank_card\" t1 WHERE t1.\"type\" = ? AND (NOT (t1.\"code\" LIKE CONCAT((?)::TEXT,'%'))) GROUP BY t1.\"uid\") t2 ON t2.\"__group_key1__\" = t.\"id\" WHERE COALESCE(t2.\"__none2__\",?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(Integer),储蓄卡(String),33123(String),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.\"id\",t.\"uid\",t.\"code\",t.\"type\",t.\"bank_id\" FROM \"doc_bank_card\" t WHERE t.\"uid\" IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("小明id(String),小红id(String),小黄id(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        listenerContextManager.clear();
    }

    @Test
    public void testAllGroupJoin2() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<DocUser> list = entityQuery.queryable(DocUser.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .includes(user -> user.bankCards())
                .where(user -> {
                    user.bankCards().where(bc -> bc.type().eq("储蓄卡")).all(bc -> {
                        bc.code().startsWith("33123");
                        bc.code().startsWith("45678");
                    });
                }).toList();

        int size = list.size();
        Assert.assertEquals(3, size);
        List<DocUser> newCards = list.stream().filter(user -> {
            //因为null记录不会被like返回所以直接过滤null
            return user.getBankCards().stream().filter(o -> Objects.equals(o.getType(), "储蓄卡")&&o.getCode() != null).allMatch(o -> o.getCode().startsWith("33123")&&o.getCode().startsWith("45678"));
        }).collect(Collectors.toList());
        Assert.assertEquals(3, newCards.size());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.\"id\",t.\"name\",t.\"phone\",t.\"age\" FROM \"doc_user\" t LEFT JOIN (SELECT t1.\"uid\" AS \"__group_key1__\",(COUNT(?) <= 0) AS \"__none2__\" FROM \"doc_bank_card\" t1 WHERE t1.\"type\" = ? AND (NOT (t1.\"code\" LIKE CONCAT((?)::TEXT,'%') AND t1.\"code\" LIKE CONCAT((?)::TEXT,'%'))) GROUP BY t1.\"uid\") t2 ON t2.\"__group_key1__\" = t.\"id\" WHERE COALESCE(t2.\"__none2__\",?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(Integer),储蓄卡(String),33123(String),45678(String),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.\"id\",t.\"uid\",t.\"code\",t.\"type\",t.\"bank_id\" FROM \"doc_bank_card\" t WHERE t.\"uid\" IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("小明id(String),小红id(String),小黄id(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        listenerContextManager.clear();
    }


    @Test
    public void testItem(){
        List<Draft2<String, BigDecimal>> list = entityQuery.queryable(PgItem.class)
                .groupBy(p -> GroupKeys.of(p.type()))
                .select(group -> {
                    NumberTypeExpression<BigDecimal> suma = group.sumBigDecimal(o -> o.column2());
                    NumberTypeExpression<BigDecimal> sumb = group.sumBigDecimal(o -> o.column3());
                    NumberTypeExpression<BigDecimal> val1 = group.expression().constant(new BigDecimal("100"));
                    NumberTypeExpression<BigDecimal> val2 = group.expression().constant(new BigDecimal("100"));
                    NumberTypeExpression<BigDecimal> sumc = group.expression().caseWhen(() -> {
                        group.groupTable().column4().eq(BigDecimal.ZERO);
                    }).then(null).elseEnd(group.groupTable().column4()).sum();
//                    NumberTypeExpression<BigDecimal> sumc = group.expression().rawSQLStatement("nullif(sum({0}), 0)", group.groupTable().column4()).asBigDecimal();
                    return Select.DRAFT.of(
                            group.key1(),
                            val1.subtract(
                                    suma.add(sumb).multiply(val2).divide(sumc)
                            ).round(3)
                    );
                }).toList();
        List<MyClass> list1 = entityQuery.queryable(PgItem.class)
                .groupBy(p -> GroupKeys.of(p.type()))
                .select(group -> {
                    SQLBigDecimalTypeColumn<PgItemProxy> column2 = group.groupTable().column2();
                    SQLBigDecimalTypeColumn<PgItemProxy> column3 = group.groupTable().column3();
                    SQLBigDecimalTypeColumn<PgItemProxy> column4 = group.groupTable().column4();
//                    MapProxy mapProxy = new MapProxy();
//                    mapProxy.put("col1", group.key1());
//                    mapProxy.put("col2", group.expression().rawSQLStatement("round(100 - (sum({0}) + sum({1})) * 100.0 / nullif(sum({2}), 0)::numeric, 3)", column2, column3, column4).asBigDecimal());
//                    return mapProxy;
//                    return Select.DRAFT.of(
//                            group.key1(),
//                            group.expression().rawSQLStatement("round(100 - (sum({0}) + sum({1})) * 100.0 / nullif(sum({2}), 0)::numeric, 3)",column2,column3,column4).asBigDecimal()
//                    );
                    return new ClassProxy<>(MyClass.class)
                            .columns(
                                    group.key1().as("type1"),
                                    group.expression().rawSQLStatement("round(100 - (sum({0}) + sum({1})) * 100.0 / nullif(sum({2}), 0)::numeric, 3)", column2, column3, column4).asBigDecimal().as("result")
                            );
                }).toList();
//        DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
//        MigrationsSQLGenerator migrationsSQLGenerator = entityQuery.getRuntimeContext().getMigrationsSQLGenerator();
//        TableMigrationData tableMigrationData = migrationsSQLGenerator.parseEntity(BlogEntity.class);
//        tableMigrationData.setTableName(tableMigrationData.getTableName()+"_202501");
//        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommandByMigrationData(Arrays.asList(tableMigrationData));
//        codeFirstCommand.executeWithTransaction(s->{
//            s.commit();
//        });
    }

    @Data
    public static class MyClass{
        private String type1;
        private BigDecimal result;
    }
}
