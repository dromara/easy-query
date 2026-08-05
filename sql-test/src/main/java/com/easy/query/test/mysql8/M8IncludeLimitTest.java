package com.easy.query.test.mysql8;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.enums.IncludeLimitModeEnum;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.test.mysql8.entity.M8IncludeLimitChild;
import com.easy.query.test.mysql8.entity.M8IncludeLimitTopic;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2026/8/4
 * include+limit 场景测试:
 * 1. include+limit 且主实体含 @Column(autoSelect=false) 列时,PARTITION 模式生成 SQL 报 Unknown column 't1.deleted'
 * 2. PARTITION 与 UNION_ALL 两种模式在分页+include limit 场景均返回完整关联(行为对照)
 */
public class M8IncludeLimitTest extends BaseTest {

    private void clearData() {
        easyEntityQuery.deletable(M8IncludeLimitTopic.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(M8IncludeLimitChild.class).disableLogicDelete().allowDeleteStatement(true).where(o -> o.id().isNotNull()).executeRows();
    }

    private void insertData() {
        clearData();
        M8IncludeLimitTopic t1 = new M8IncludeLimitTopic();
        t1.setId("t1");
        t1.setName("topic1");
        M8IncludeLimitTopic t2 = new M8IncludeLimitTopic();
        t2.setId("t2");
        t2.setName("topic2");
        M8IncludeLimitTopic t3 = new M8IncludeLimitTopic();
        t3.setId("t3");
        t3.setName("topic3");
        easyEntityQuery.insertable(Arrays.asList(t1, t2, t3)).executeRows();

        M8IncludeLimitChild c1 = child("c1", "t1", "child1", 1);
        M8IncludeLimitChild c2 = child("c2", "t1", "child2", 2);
        M8IncludeLimitChild c3 = child("c3", "t1", "child3", 3);
        M8IncludeLimitChild c4 = child("c4", "t2", "child4", 1);
        M8IncludeLimitChild c5 = child("c5", "t2", "child5", 2);
        M8IncludeLimitChild c6 = child("c6", "t3", "child6", 1);
        easyEntityQuery.insertable(Arrays.asList(c1, c2, c3, c4, c5, c6)).executeRows();
    }

    private M8IncludeLimitChild child(String id, String parentId, String name, Integer order) {
        M8IncludeLimitChild child = new M8IncludeLimitChild();
        child.setId(id);
        child.setParentId(parentId);
        child.setName(name);
        child.setOrder(order);
        return child;
    }

    /**
     * include+limit 且主实体含 @Column(autoSelect=false) 列时:
     * 修复前 PARTITION 模式报 Unknown column 't1.deleted'
     * 修复后正常返回且关联条数受 limit 限制
     */
    @Test
    public void testIncludeLimit() {
        insertData();
        List<M8IncludeLimitTopic> list = easyEntityQuery.queryable(M8IncludeLimitTopic.class)
                .includes(m -> m.children(), s -> s.orderBy(x -> x.order().asc()).limit(2))
                .toList();
        Assert.assertEquals(3, list.size());
        M8IncludeLimitTopic t1 = list.stream().filter(o -> "t1".equals(o.getId())).findFirst().orElse(null);
        Assert.assertNotNull(t1);
        Assert.assertEquals(2, t1.getChildren().size());
        Assert.assertEquals("c1", t1.getChildren().get(0).getId());
        Assert.assertEquals("c2", t1.getChildren().get(1).getId());
        M8IncludeLimitTopic t3 = list.stream().filter(o -> "t3".equals(o.getId())).findFirst().orElse(null);
        Assert.assertNotNull(t3);
        Assert.assertEquals(1, t3.getChildren().size());
        Assert.assertEquals("c6", t3.getChildren().get(0).getId());
    }

    /**
     * PARTITION 模式:主表分页 offset>0 时 include limit 关联数据完整。
     * 窗口参数来自 include 子查询自身(offset 恒 0,rows=include limit),每个主行独立分区取前 N 条,语义正确。
     */
    @Test
    public void testIncludeLimitPagingPartition() {
        insertData();
        List<M8IncludeLimitTopic> page2 = easyEntityQuery.queryable(M8IncludeLimitTopic.class)
                .orderBy(m -> m.id().asc())
                .limit(2, 1)
                .includes(m -> m.children(), s -> s.orderBy(x -> x.order().asc()).limit(2))
                .toList();
        Assert.assertEquals(1, page2.size());
        Assert.assertEquals("t3", page2.get(0).getId());
        Assert.assertEquals(1, page2.get(0).getChildren().size());
        Assert.assertEquals("c6", page2.get(0).getChildren().get(0).getId());
    }

    /**
     * UNION_ALL 模式:主表分页 offset>0 时 include limit 关联数据完整(正确行为)
     */
    @Test
    public void testIncludeLimitPagingUnionAll() {
        insertData();
        EasyQueryClient unionAllClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> op.setIncludeLimitMode(IncludeLimitModeEnum.UNION_ALL))
                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
                .build();
        EasyEntityQuery unionAllQuery = new DefaultEasyEntityQuery(unionAllClient);
        List<M8IncludeLimitTopic> page2 = unionAllQuery.queryable(M8IncludeLimitTopic.class)
                .orderBy(m -> m.id().asc())
                .limit(2, 1)
                .includes(m -> m.children(), s -> s.orderBy(x -> x.order().asc()).limit(2))
                .toList();
        Assert.assertEquals(1, page2.size());
        Assert.assertEquals("t3", page2.get(0).getId());
        Assert.assertEquals(1, page2.get(0).getChildren().size());
        Assert.assertEquals("c6", page2.get(0).getChildren().get(0).getId());
    }
}
