package com.easy.query.test.mysql8;

import com.bestvike.linq.Linq;
import com.easy.query.api.proxy.base.ClassProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.EntityValueState;
import com.easy.query.core.basic.extension.track.TrackContext;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasyTrackUtil;
import com.easy.query.test.dto.UserBankDTO;
import com.easy.query.test.dto.proxy.UserBankDTOProxy;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.dto.MyComment;
import com.easy.query.test.mysql8.dto.MyComment2;
import com.easy.query.test.mysql8.dto.MyComment3;
import com.easy.query.test.mysql8.dto.MyComment4;
import com.easy.query.test.mysql8.entity.BatchInsert;
import com.easy.query.test.mysql8.entity.Comment;
import com.easy.query.test.mysql8.entity.M8Parent;
import com.easy.query.test.mysql8.entity.bank.SysBank;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import com.easy.query.test.mysql8.entity.bank.SysUser;
import com.easy.query.test.mysql8.entity.many.M8Province;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * create time 2025/8/19 15:55
 * 文件说明
 *
 * @author xuejiaming
 */
public class MySQL8Test3 extends BaseTest {

    @Test
    public void testDynamicJoin() {

        String bankCardCode = "";
        String bankCardType = "";
        String bankName = "";
        String userName = "";
        String userPhone = "";
        EasyPageResult<SysBankCard> pageResult = easyEntityQuery.queryable(SysBankCard.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .where(bank_card -> {
                    bank_card.code().contains(bankCardCode);
                    bank_card.type().contains(bankCardType);
                    bank_card.bank().name().contains(bankName);
                    bank_card.user().name().contains(userName);
                    bank_card.user().phone().contains(userPhone);
                })
                .toPageResult(1, 2);
    }


    @Test
    public void thisConfigureTest() {


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        EasyPageResult<Draft1<String>> pageResult = easyEntityQuery.queryable(SysUser.class)
                .thisConfigure(o -> thisConfigure(o))
                .where(user -> {
                    user.name().eq("");
                }).orderBy(user -> {
                    user.bankCards().where(x -> x.type().eq("123")).max(s -> s.openTime()).asc(OrderByModeEnum.NULLS_LAST);
                }).select(user -> Select.DRAFT.of(
                        user.name()
                )).toPageResult(1, 20);
        listenerContextManager.clear();

        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT COUNT(*) FROM `t_sys_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("-1(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`name` AS `value1` FROM `t_sys_user` t LEFT JOIN (SELECT t1.`uid` AS `uid`,MAX(t1.`open_time`) AS `__max2__` FROM `t_bank_card` t1 WHERE t1.`type` = ? GROUP BY t1.`uid`) t2 ON t2.`uid` = t.`id` ORDER BY CASE WHEN t2.`__max2__` IS NULL THEN 1 ELSE 0 END ASC,t2.`__max2__` ASC LIMIT 2", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }

    }

    public static <T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> EntityQueryable<T1Proxy, T1> thisConfigure(EntityQueryable<T1Proxy, T1> queryable) {
        return queryable.filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN));
    }

    @Test
    public void testFlatElement() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8Province> list = easyEntityQuery.queryable(M8Province.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(m -> {
                    m.cities().flatElement().areas().flatElement().name().eq("123");
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `m8_province` t LEFT JOIN (SELECT t1.`pid` AS `pid`,(COUNT(?) > 0) AS `__any2__` FROM `m8_city` t1 LEFT JOIN (SELECT t3.`cid` AS `cid`,(COUNT(?) > 0) AS `__any2__` FROM `m8_area` t3 WHERE t3.`name` = ? GROUP BY t3.`cid`) t4 ON t4.`cid` = t1.`id` WHERE IFNULL(t4.`__any2__`,?) = ? GROUP BY t1.`pid`) t2 ON t2.`pid` = t.`id` WHERE IFNULL(t2.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),1(Integer),123(String),false(Boolean),true(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void testFlatElement1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8Province> list = easyEntityQuery.queryable(M8Province.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(m -> {
                    m.cities().any(a -> {
                        a.areas().flatElement().name().eq("123");
                    });
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `m8_province` t LEFT JOIN (SELECT t1.`pid` AS `pid`,(COUNT(?) > 0) AS `__any2__` FROM `m8_city` t1 LEFT JOIN (SELECT t3.`cid` AS `cid`,(COUNT(?) > 0) AS `__any2__` FROM `m8_area` t3 WHERE t3.`name` = ? GROUP BY t3.`cid`) t4 ON t4.`cid` = t1.`id` WHERE IFNULL(t4.`__any2__`,?) = ? GROUP BY t1.`pid`) t2 ON t2.`pid` = t.`id` WHERE IFNULL(t2.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),1(Integer),123(String),false(Boolean),true(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void testFlatElement2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8Province> list = easyEntityQuery.queryable(M8Province.class)
                .where(m -> {
                    m.cities().flatElement().areas().flatElement().name().eq("123");
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `m8_province` t WHERE EXISTS (SELECT 1 FROM `m8_city` t1 WHERE t1.`pid` = t.`id` AND EXISTS (SELECT 1 FROM `m8_area` t2 WHERE t2.`cid` = t1.`id` AND t2.`name` = ? LIMIT 1) LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void testFlatElement3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8Province> list = easyEntityQuery.queryable(M8Province.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(m -> {
                    m.cities().flatElement().areas().flatElement().builds().flatElement().name().eq("科创楼");
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `m8_province` t LEFT JOIN (SELECT t1.`pid` AS `pid`,(COUNT(?) > 0) AS `__any2__` FROM `m8_city` t1 LEFT JOIN (SELECT t3.`cid` AS `cid`,(COUNT(?) > 0) AS `__any2__` FROM `m8_area` t3 LEFT JOIN (SELECT t5.`aid` AS `aid`,(COUNT(?) > 0) AS `__any2__` FROM `m8_build` t5 WHERE t5.`name` = ? GROUP BY t5.`aid`) t6 ON t6.`aid` = t3.`id` WHERE IFNULL(t6.`__any2__`,?) = ? GROUP BY t3.`cid`) t4 ON t4.`cid` = t1.`id` WHERE IFNULL(t4.`__any2__`,?) = ? GROUP BY t1.`pid`) t2 ON t2.`pid` = t.`id` WHERE IFNULL(t2.`__any2__`,?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),1(Integer),1(Integer),科创楼(String),false(Boolean),true(Boolean),false(Boolean),true(Boolean),false(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


        Assert.assertTrue(!list.isEmpty());


    }

    @Test
    public void testGroupJoin() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8Province> list = easyEntityQuery.queryable(M8Province.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(m -> {
                    m.cities().flatElement().name().eq("杭州");
                    m.id().eq("p1");
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `m8_province` t LEFT JOIN (SELECT t1.`pid` AS `pid`,(COUNT(?) > 0) AS `__any2__` FROM `m8_city` t1 WHERE t1.`name` = ? AND t1.`pid` = ? GROUP BY t1.`pid`) t2 ON t2.`pid` = t.`id` WHERE IFNULL(t2.`__any2__`,?) = ? AND t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),杭州(String),p1(String),false(Boolean),true(Boolean),p1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void testGroupJoin2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8Province> list = easyEntityQuery.queryable(M8Province.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(m -> {
                    m.cities().flatElement().name().eq("杭州");
                    m.id().in(Arrays.asList("p1", "p2"));
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `m8_province` t LEFT JOIN (SELECT t1.`pid` AS `pid`,(COUNT(?) > 0) AS `__any2__` FROM `m8_city` t1 WHERE t1.`name` = ? AND t1.`pid` IN (?,?) GROUP BY t1.`pid`) t2 ON t2.`pid` = t.`id` WHERE IFNULL(t2.`__any2__`,?) = ? AND t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),杭州(String),p1(String),p2(String),false(Boolean),true(Boolean),p1(String),p2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void testGroupJoin3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8Province> list = easyEntityQuery.queryable(M8Province.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(m -> {
                    m.cities().flatElement().name().eq("杭州");
                    m.or(() -> {
                        m.id().eq("p1");
                        m.id().eq("p2");
                        m.id().in(Arrays.asList("p3", "p4"));
                    });
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `m8_province` t LEFT JOIN (SELECT t1.`pid` AS `pid`,(COUNT(?) > 0) AS `__any2__` FROM `m8_city` t1 WHERE t1.`name` = ? AND (t1.`pid` = ? OR t1.`pid` = ? OR t1.`pid` IN (?,?)) GROUP BY t1.`pid`) t2 ON t2.`pid` = t.`id` WHERE IFNULL(t2.`__any2__`,?) = ? AND (t.`id` = ? OR t.`id` = ? OR t.`id` IN (?,?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),杭州(String),p1(String),p2(String),p3(String),p4(String),false(Boolean),true(Boolean),p1(String),p2(String),p3(String),p4(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void testGroupJoin4() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8Province> list = easyEntityQuery.queryable(M8Province.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(m -> {
                    m.cities().any(c -> {
                        c.id().eq("c1");
                        c.name().eq("杭州");

                        c.areas().flatElement().name().eq("上城区");
                    });
                    m.id().eq("p1");
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name` FROM `m8_province` t LEFT JOIN (SELECT t1.`pid` AS `pid`,(COUNT(?) > 0) AS `__any2__` FROM `m8_city` t1 LEFT JOIN (SELECT t3.`cid` AS `cid`,(COUNT(?) > 0) AS `__any2__` FROM `m8_area` t3 WHERE t3.`name` = ? AND t3.`cid` = ? GROUP BY t3.`cid`) t4 ON t4.`cid` = t1.`id` WHERE t1.`id` = ? AND t1.`name` = ? AND IFNULL(t4.`__any2__`,?) = ? AND t1.`pid` = ? GROUP BY t1.`pid`) t2 ON t2.`pid` = t.`id` WHERE IFNULL(t2.`__any2__`,?) = ? AND t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),1(Integer),上城区(String),c1(String),c1(String),杭州(String),false(Boolean),true(Boolean),p1(String),false(Boolean),true(Boolean),p1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void testMany2Many() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<M8Parent> list = easyEntityQuery.queryable(M8Parent.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .where(m -> {
                    m.children2().flatElement().name().eq("123");
                    m.id().eq("123");
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`name`,t.`order` FROM `m8_parent` t LEFT JOIN (SELECT t2.`parent_id` AS `parent_id`,(COUNT(?) > 0) AS `__any2__` FROM `m8_child` t1 INNER JOIN `m8_parent_child` t2 ON t1.`id` = t2.`child_id` WHERE t1.`name` = ? AND t1.`parent_id` = ? GROUP BY t2.`parent_id`) t4 ON t4.`parent_id` = t.`id` WHERE IFNULL(t4.`__any2__`,?) = ? AND t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer),123(String),123(String),false(Boolean),true(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void batchTest() {
        ArrayList<BatchInsert> batchInserts = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BatchInsert batchInsert = new BatchInsert();
            batchInsert.setName(String.valueOf(i));
            batchInserts.add(batchInsert);
        }
        List<BatchInsert> collect = batchInserts.stream().sorted((a, b) -> UUID.randomUUID().hashCode() - UUID.randomUUID().hashCode()).collect(Collectors.toList());
        easyEntityQuery.insertable(collect).batch().executeRows(true);
        System.out.println(collect);
        List<BatchInsert> list = easyEntityQuery.queryable(BatchInsert.class)
                .toList();
        System.out.println(list);
        for (BatchInsert batchInsert : list) {
            BatchInsert batchInsert1 = Linq.of(collect).where(o -> Objects.equals(o.getName(), batchInsert.getName())).firstOrDefault();
            Assert.assertNotNull(batchInsert1);
            Assert.assertEquals(batchInsert1.getId(), batchInsert.getId());
        }
    }

    @Test
    public void testTree() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Comment> treeList = easyEntityQuery.queryable(Comment.class)
                .asTreeCTE()
                .toTreeList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `cte_deep`,t1.`id`,t1.`parent_id`,t1.`content`,t1.`user_id`,t1.`post_id`,t1.`create_at` FROM `t_comment` t1)  UNION ALL  (SELECT t2.`cte_deep` + 1 AS `cte_deep`,t3.`id`,t3.`parent_id`,t3.`content`,t3.`user_id`,t3.`post_id`,t3.`create_at` FROM `as_tree_cte` t2 INNER JOIN `t_comment` t3 ON t3.`parent_id` = t2.`id`) ) SELECT t.`id`,t.`parent_id`,t.`content`,t.`user_id`,t.`post_id`,t.`create_at`,t.`cte_deep` FROM `as_tree_cte` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("1(Integer),1(Integer),上城区(String),c1(String),c1(String),杭州(String),false(Boolean),true(Boolean),p1(String),false(Boolean),true(Boolean),p1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        {

            Comment comment = treeList.get(0);
            Assert.assertEquals("01225d2f-e1a5-46d8-8ef9-535b7b1b7754", comment.getId());
            Assert.assertEquals("03abe9c8-adf1-4934-ae53-3b52c7c3eb2d", comment.getParentId());
            Assert.assertEquals(1, comment.getChildren().size());
            Comment comment1 = comment.getChildren().get(0);
            Assert.assertEquals("1bccba2c-7cff-43af-b117-2e518be4422a", comment1.getId());
            Assert.assertEquals("01225d2f-e1a5-46d8-8ef9-535b7b1b7754", comment1.getParentId());
            Assert.assertEquals(0, comment1.getChildren().size());
        }

        {
            Comment comment = treeList.get(1);
            Assert.assertEquals("03abe9c8-adf1-4934-ae53-3b52c7c3eb2d", comment.getId());
            Assert.assertEquals("0", comment.getParentId());
            Assert.assertEquals(1, comment.getChildren().size());
            Comment comment1 = comment.getChildren().get(0);
            Assert.assertEquals("01225d2f-e1a5-46d8-8ef9-535b7b1b7754", comment1.getId());
            Assert.assertEquals("03abe9c8-adf1-4934-ae53-3b52c7c3eb2d", comment1.getParentId());
            Assert.assertEquals(1, comment1.getChildren().size());
            Comment comment2 = comment1.getChildren().get(0);
            Assert.assertEquals("1bccba2c-7cff-43af-b117-2e518be4422a", comment2.getId());
            Assert.assertEquals("01225d2f-e1a5-46d8-8ef9-535b7b1b7754", comment2.getParentId());
            Assert.assertEquals(0, comment2.getChildren().size());
        }
        {
            Comment comment = treeList.get(2);
            Assert.assertEquals("1bccba2c-7cff-43af-b117-2e518be4422a", comment.getId());
            Assert.assertEquals("01225d2f-e1a5-46d8-8ef9-535b7b1b7754", comment.getParentId());
            Assert.assertEquals(0, comment.getChildren().size());

        }

    }

    @Test
    public void testTree2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MyComment> treeList = easyEntityQuery.queryable(Comment.class)
                .asTreeCTE(o -> {
                    o.setDeepColumnName("deep");
                })
                .selectAutoInclude(MyComment.class)
                .toTreeList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `deep`,t1.`id`,t1.`parent_id`,t1.`content`,t1.`user_id`,t1.`post_id`,t1.`create_at` FROM `t_comment` t1)  UNION ALL  (SELECT t2.`deep` + 1 AS `deep`,t3.`id`,t3.`parent_id`,t3.`content`,t3.`user_id`,t3.`post_id`,t3.`create_at` FROM `as_tree_cte` t2 INNER JOIN `t_comment` t3 ON t3.`parent_id` = t2.`id`) ) SELECT t.`id`,t.`parent_id`,t.`content`,t.`user_id`,t.`post_id`,t.`create_at`,t.`deep` FROM `as_tree_cte` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("1(Integer),1(Integer),上城区(String),c1(String),c1(String),杭州(String),false(Boolean),true(Boolean),p1(String),false(Boolean),true(Boolean),p1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        {

            MyComment comment = treeList.get(0);
            Assert.assertEquals("01225d2f-e1a5-46d8-8ef9-535b7b1b7754", comment.getId());
            Assert.assertEquals("03abe9c8-adf1-4934-ae53-3b52c7c3eb2d", comment.getParentId());
            Assert.assertEquals(1, comment.getChildren().size());
            MyComment comment1 = comment.getChildren().get(0);
            Assert.assertEquals("1bccba2c-7cff-43af-b117-2e518be4422a", comment1.getId());
            Assert.assertEquals("01225d2f-e1a5-46d8-8ef9-535b7b1b7754", comment1.getParentId());
            Assert.assertEquals(0, comment1.getChildren().size());
        }

        {
            MyComment comment = treeList.get(1);
            Assert.assertEquals("03abe9c8-adf1-4934-ae53-3b52c7c3eb2d", comment.getId());
            Assert.assertEquals("0", comment.getParentId());
            Assert.assertEquals(1, comment.getChildren().size());
            MyComment comment1 = comment.getChildren().get(0);
            Assert.assertEquals("01225d2f-e1a5-46d8-8ef9-535b7b1b7754", comment1.getId());
            Assert.assertEquals("03abe9c8-adf1-4934-ae53-3b52c7c3eb2d", comment1.getParentId());
            Assert.assertEquals(1, comment1.getChildren().size());
            MyComment comment2 = comment1.getChildren().get(0);
            Assert.assertEquals("1bccba2c-7cff-43af-b117-2e518be4422a", comment2.getId());
            Assert.assertEquals("01225d2f-e1a5-46d8-8ef9-535b7b1b7754", comment2.getParentId());
            Assert.assertEquals(0, comment2.getChildren().size());
        }
        {
            MyComment comment = treeList.get(2);
            Assert.assertEquals("1bccba2c-7cff-43af-b117-2e518be4422a", comment.getId());
            Assert.assertEquals("01225d2f-e1a5-46d8-8ef9-535b7b1b7754", comment.getParentId());
            Assert.assertEquals(0, comment.getChildren().size());

        }

    }


    @Test
    public void testTree3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MyComment> treeList = easyEntityQuery.queryable(Comment.class)
                .asTreeCTE(o -> {
                    o.setUp(true);
                    o.setDeepColumnName("deep");
                })
                .selectAutoInclude(MyComment.class)
                .toTreeList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `deep`,t1.`id`,t1.`parent_id`,t1.`content`,t1.`user_id`,t1.`post_id`,t1.`create_at` FROM `t_comment` t1)  UNION ALL  (SELECT t2.`deep` + 1 AS `deep`,t3.`id`,t3.`parent_id`,t3.`content`,t3.`user_id`,t3.`post_id`,t3.`create_at` FROM `as_tree_cte` t2 INNER JOIN `t_comment` t3 ON t3.`id` = t2.`parent_id`) ) SELECT t.`id`,t.`parent_id`,t.`content`,t.`user_id`,t.`post_id`,t.`create_at`,t.`deep` FROM `as_tree_cte` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("[MyComment(id=03abe9c8-adf1-4934-ae53-3b52c7c3eb2d, parentId=0, content=写得真详细, userId=3b63ddd9-b038-4c24-969e-8b478fe862a5, postId=73f5d341-c6df-43a1-afcd-e246c4d1fcc9, createAt=2020-01-01T00:00, deep=0, children=[]), MyComment(id=03abe9c8-adf1-4934-ae53-3b52c7c3eb2d, parentId=0, content=写得真详细, userId=3b63ddd9-b038-4c24-969e-8b478fe862a5, postId=73f5d341-c6df-43a1-afcd-e246c4d1fcc9, createAt=2020-01-01T00:00, deep=1, children=[MyComment(id=01225d2f-e1a5-46d8-8ef9-535b7b1b7754, parentId=03abe9c8-adf1-4934-ae53-3b52c7c3eb2d, content=@用户E 具体是指哪方面？, userId=3b63ddd9-b038-4c24-969e-8b478fe862a5, postId=73f5d341-c6df-43a1-afcd-e246c4d1fcc9, createAt=2020-01-02T00:00, deep=0, children=[])]), MyComment(id=03abe9c8-adf1-4934-ae53-3b52c7c3eb2d, parentId=0, content=写得真详细, userId=3b63ddd9-b038-4c24-969e-8b478fe862a5, postId=73f5d341-c6df-43a1-afcd-e246c4d1fcc9, createAt=2020-01-01T00:00, deep=2, children=[MyComment(id=01225d2f-e1a5-46d8-8ef9-535b7b1b7754, parentId=03abe9c8-adf1-4934-ae53-3b52c7c3eb2d, content=@用户E 具体是指哪方面？, userId=3b63ddd9-b038-4c24-969e-8b478fe862a5, postId=73f5d341-c6df-43a1-afcd-e246c4d1fcc9, createAt=2020-01-02T00:00, deep=1, children=[MyComment(id=1bccba2c-7cff-43af-b117-2e518be4422a, parentId=01225d2f-e1a5-46d8-8ef9-535b7b1b7754, content=@用户E 你是指..., userId=3b63ddd9-b038-4c24-969e-8b478fe862a5, postId=73f5d341-c6df-43a1-afcd-e246c4d1fcc9, createAt=2020-01-03T00:00, deep=0, children=[])])])]", treeList.toString());
//        Assert.assertEquals("1(Integer),1(Integer),上城区(String),c1(String),c1(String),杭州(String),false(Boolean),true(Boolean),p1(String),false(Boolean),true(Boolean),p1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testTree4() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MyComment3> treeList = easyEntityQuery.queryable(Comment.class)
                .asTreeCTE(o -> {
                    o.setUp(true);
                    o.setDeepColumnName("deep");
                })
                .selectAutoInclude(MyComment3.class)
                .toTreeList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("WITH RECURSIVE `as_tree_cte` AS ( (SELECT 0 AS `deep`,t1.`id`,t1.`parent_id`,t1.`content`,t1.`user_id`,t1.`post_id`,t1.`create_at` FROM `t_comment` t1)  UNION ALL  (SELECT t2.`deep` + 1 AS `deep`,t3.`id`,t3.`parent_id`,t3.`content`,t3.`user_id`,t3.`post_id`,t3.`create_at` FROM `as_tree_cte` t2 INNER JOIN `t_comment` t3 ON t3.`id` = t2.`parent_id`) ) SELECT t.`id`,t.`parent_id`,t.`content`,t.`user_id`,t.`post_id`,t.`create_at`,t.`deep` FROM `as_tree_cte` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        System.out.println(treeList.toString());
        Assert.assertEquals("[MyComment3(id=03abe9c8-adf1-4934-ae53-3b52c7c3eb2d, parentId=0, content=写得真详细, userId=3b63ddd9-b038-4c24-969e-8b478fe862a5, postId=73f5d341-c6df-43a1-afcd-e246c4d1fcc9, createAt=2020-01-01T00:00, deep=0, children=[]), MyComment3(id=03abe9c8-adf1-4934-ae53-3b52c7c3eb2d, parentId=0, content=写得真详细, userId=3b63ddd9-b038-4c24-969e-8b478fe862a5, postId=73f5d341-c6df-43a1-afcd-e246c4d1fcc9, createAt=2020-01-01T00:00, deep=1, children=[MyComment3(id=01225d2f-e1a5-46d8-8ef9-535b7b1b7754, parentId=03abe9c8-adf1-4934-ae53-3b52c7c3eb2d, content=@用户E 具体是指哪方面？, userId=3b63ddd9-b038-4c24-969e-8b478fe862a5, postId=73f5d341-c6df-43a1-afcd-e246c4d1fcc9, createAt=2020-01-02T00:00, deep=0, children=[])]), MyComment3(id=03abe9c8-adf1-4934-ae53-3b52c7c3eb2d, parentId=0, content=写得真详细, userId=3b63ddd9-b038-4c24-969e-8b478fe862a5, postId=73f5d341-c6df-43a1-afcd-e246c4d1fcc9, createAt=2020-01-01T00:00, deep=2, children=[MyComment3(id=01225d2f-e1a5-46d8-8ef9-535b7b1b7754, parentId=03abe9c8-adf1-4934-ae53-3b52c7c3eb2d, content=@用户E 具体是指哪方面？, userId=3b63ddd9-b038-4c24-969e-8b478fe862a5, postId=73f5d341-c6df-43a1-afcd-e246c4d1fcc9, createAt=2020-01-02T00:00, deep=1, children=[MyComment3(id=1bccba2c-7cff-43af-b117-2e518be4422a, parentId=01225d2f-e1a5-46d8-8ef9-535b7b1b7754, content=@用户E 你是指..., userId=3b63ddd9-b038-4c24-969e-8b478fe862a5, postId=73f5d341-c6df-43a1-afcd-e246c4d1fcc9, createAt=2020-01-03T00:00, deep=0, children=[])])])]", treeList.toString());
//        Assert.assertEquals("1(Integer),1(Integer),上城区(String),c1(String),c1(String),杭州(String),false(Boolean),true(Boolean),p1(String),false(Boolean),true(Boolean),p1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testTree5() {
        Exception ex = null;
        try {

            List<MyComment4> treeList = easyEntityQuery.queryable(Comment.class)
                    .where(t_comment -> {
                        t_comment.parentId().isNotNull();
                    })
                    .whereObject(new Comment())
                    .asTreeCTE()
                    .selectAutoInclude(MyComment4.class)
                    .toTreeList();
        } catch (Exception e) {
            ex = e;
        }

        listenerContextManager.clear();
        Assert.assertNotNull(ex);
        Assert.assertEquals("MyComment4 is unable to access selfProperty during tree structure retrieval.", ex.getMessage());
//        Assert.assertEquals("1(Integer),1(Integer),上城区(String),c1(String),c1(String),杭州(String),false(Boolean),true(Boolean),p1(String),false(Boolean),true(Boolean),p1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testDTO1() {
        List<UserBankDTO> list = easyEntityQuery.queryable(SysUser.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .select(user -> new UserBankDTOProxy()
                        .userId().set(user.id())
                        .bankCardCount().set(
                                user.bankCards().count()
                        )
                        .firstBankCardCode().set(
                                user.bankCards().orderBy(bc -> bc.openTime().asc()).first().code()
                        )
                )
                .toList();

    }


//    @Test
//    public void testDTO2() {
//        easyEntityQuery.queryable(SysBankCard.class)
//                .select(bank_card -> new ClassProxy<>(UserBankDTO.class)
//                                .selectAll(bank_card.user())// bankCard join user 返回user.*

    /// /                        .selectAll(bank_card)// bankCard join user 返回bankCard.*
//                );
//        easyEntityQuery.queryable(SysBankCard.class)
//                .select(UserBankDTO.class, bank_card -> Select.of(
//                                bank_card.user().FETCHER.allFields(),// bankCard join user 返回user.*
//                                //bank_card.FETCHER.allFields()// bankCard join user 返回bankCard.*
//                        )
//                );
//
//        easyEntityQuery.queryable(SysBankCard.class)
//                .leftJoin(SysUser.class,(bank_card, user) -> bank_card.uid().eq(user.id()))
//                .select(UserBankDTO.class);//查询的是bankCard.*
//
//        easyEntityQuery.queryable(SysBankCard.class)
//                .leftJoin(SysUser.class,(bank_card, user) -> bank_card.uid().eq(user.id()))
//                .select(UserBankDTO.class,(bank_card, user) ->Select.of(
//                        user.FETCHER.allFields()//查询的是user.*
//                ));
//    }
    @Test
    public void testSave() {


        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {
            trackManager.begin();

            List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                    .includes(user -> user.bankCards(), bkq -> bkq.include(bk -> bk.bank()))
                    .toList();

            SysUser sysUser = list.get(0);
            sysUser.getBankCards().remove(0);
            int i = 0;
            for (SysBankCard bankCard : sysUser.getBankCards()) {
                if (i % 2 == 1) {
                    System.out.println("bankCard:" + bankCard.getId() + "不变更type");
                } else {
                    String uuid = UUID.randomUUID().toString();
                    System.out.println("bankCard:" + bankCard.getId() + "变更type:" + uuid);
                    bankCard.setType(uuid);
                }
                SysBank bank = bankCard.getBank();
                if(bank!=null){
                    bank.setName("xxxxx123123");
                }
                i++;

            }
            SysBankCard sysBankCard = new SysBankCard();
            sysBankCard.setId("123123123");
            sysBankCard.setType("456456");
            sysUser.getBankCards().add(sysBankCard);

            System.out.println("分割线---------------------");
            try(Transaction transaction = easyEntityQuery.beginTransaction()){
                easyEntityQuery.savable(sysUser).executeCommand();
                transaction.commit();
            }

        } finally {
            trackManager.release();
        }

    }


}
