package com.easy.query.test.pgsql;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

/**
 * create time 2024/8/6 11:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class PartitionByTest  extends PgSQLBaseTest{
    @Test
    public void testPartitionBy16_1(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft3<Long, Long, String>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PARTITION.of(
                        b,
                        b.expression().countOver(b.star()).partitionBy(b.title()),
                        b.expression().rowNumberOver().partitionBy(b.title()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2L);
                    partition.partColumn2().lt(2L);
                }).select(p -> Select.DRAFT.of(
                        p.partColumn1(),
                        p.partColumn2(),
                        p.entityTable().id()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"__part__column1\" AS \"value1\",t1.\"__part__column2\" AS \"value2\",t1.\"id\" AS \"value3\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(COUNT(t.\"star\") OVER (PARTITION BY t.\"title\")) AS \"__part__column1\",(ROW_NUMBER() OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__part__column2\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ? AND t1.\"__part__column2\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Long),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Draft3<Long, Long, String> longStringDraft2 : list) {
            Long value2 = longStringDraft2.getValue2();
            String value3 = longStringDraft2.getValue3();
            Long value1 = longStringDraft2.getValue1();
        }
        System.out.println(list);
    }
    @Test
    public void testPartitionBy16_2(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft3<Long, Long, String>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PARTITION.of(
                        b,
                        b.expression().countOver(b.star()).partitionBy(b.title()),
                        b.expression().rowNumberOver().partitionBy(b.title()).orderBy(false,b.createTime()).orderBy(b.createTime())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2L);
                    partition.partColumn2().lt(2L);
                }).select(p -> Select.DRAFT.of(
                        p.partColumn1(),
                        p.partColumn2(),
                        p.entityTable().id()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"__part__column1\" AS \"value1\",t1.\"__part__column2\" AS \"value2\",t1.\"id\" AS \"value3\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(COUNT(t.\"star\") OVER (PARTITION BY t.\"title\")) AS \"__part__column1\",(ROW_NUMBER() OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC)) AS \"__part__column2\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ? AND t1.\"__part__column2\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Long),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Draft3<Long, Long, String> longStringDraft2 : list) {
            Long value2 = longStringDraft2.getValue2();
            String value3 = longStringDraft2.getValue3();
            Long value1 = longStringDraft2.getValue1();
        }
        System.out.println(list);
    }
    @Test
    public void testPartitionBy16_3(){


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft3<Long, Long, String>> list = entityQuery.queryable(BlogEntity.class)
                .where(b -> b.createTime().gt(LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(b -> Select.PARTITION.of(
                        b,
                        b.expression().countOver(b.star()).partitionBy(b.title()),
                        b.expression().rowNumberOver().partitionBy(b.title()).orderBy(true,b.createTime()).orderBy(b.updateTime())
                )).where(partition -> {
                    partition.entityTable().star().gt(1);
                    partition.partColumn1().lt(2L);
                    partition.partColumn2().lt(2L);
                }).select(p -> Select.DRAFT.of(
                        p.partColumn1(),
                        p.partColumn2(),
                        p.entityTable().id()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.\"__part__column1\" AS \"value1\",t1.\"__part__column2\" AS \"value2\",t1.\"id\" AS \"value3\" FROM (SELECT t.\"id\",t.\"create_time\",t.\"update_time\",t.\"create_by\",t.\"update_by\",t.\"deleted\",t.\"title\",t.\"content\",t.\"url\",t.\"star\",t.\"publish_time\",t.\"score\",t.\"status\",t.\"order\",t.\"is_top\",t.\"top\",(COUNT(t.\"star\") OVER (PARTITION BY t.\"title\")) AS \"__part__column1\",(ROW_NUMBER() OVER (PARTITION BY t.\"title\" ORDER BY t.\"create_time\" ASC, t.\"update_time\" ASC)) AS \"__part__column2\" FROM \"t_blog\" t WHERE t.\"deleted\" = ? AND t.\"create_time\" > ?) t1 WHERE t1.\"star\" > ? AND t1.\"__part__column1\" < ? AND t1.\"__part__column2\" < ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2020-01-01T01:01(LocalDateTime),1(Integer),2(Long),2(Long)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        for (Draft3<Long, Long, String> longStringDraft2 : list) {
            Long value2 = longStringDraft2.getValue2();
            String value3 = longStringDraft2.getValue3();
            Long value1 = longStringDraft2.getValue1();
        }
        System.out.println(list);
    }
}
