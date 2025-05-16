package com.easy.query.test.mssqlrownumber;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.func.def.enums.TimeUnitEnum;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.core.draft.Draft4;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.test.mssql.entity.MsSQLMyTopic;
import com.easy.query.test.mssql.entity.MsSQLMyTopic1;
import com.easy.query.test.mssql.entity.proxy.MsSQLMyTopic1Proxy;
import com.easy.query.test.mssql.entity.proxy.MsSQLMyTopicProxy;
import com.easy.query.test.mssqlrownumber.entity.MsSQLRowNumberMyTopic;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/7/27 17:34
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLRowNumberQueryTest extends MsSQLRowNumberBaseTest {

    @Test
    public void query0() {
//
//
//        {
//
//            List<Map> list = entityQuery.queryable("select * from t_order", Map.class)
//                    .limit(10,20).toList();
//        }

        EntityQueryable<MsSQLMyTopicProxy, MsSQLMyTopic> queryable = entityQuery.queryable(MsSQLMyTopic.class)
                .where(o -> o.id().eq("123xxx"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT [Id],[Stars],[Title],[CreateTime] FROM [MyTopic] WHERE [Id] = ?", sql);
        MsSQLMyTopic msSQLMyTopic = queryable.firstOrNull();
        Assert.assertNull(msSQLMyTopic);
    }
    @Test
    public void query1() {
        EntityQueryable<MsSQLMyTopicProxy, MsSQLMyTopic> queryable = entityQuery.queryable(MsSQLMyTopic.class)
                .where(o -> o.id().eq( "1"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT [Id],[Stars],[Title],[CreateTime] FROM [MyTopic] WHERE [Id] = ?", sql);
        MsSQLMyTopic msSQLMyTopic = queryable.firstOrNull();
        Assert.assertNotNull(msSQLMyTopic);
        Assert.assertNotNull(msSQLMyTopic.getId());
        Assert.assertNotNull(msSQLMyTopic.getStars());
        Assert.assertNotNull(msSQLMyTopic.getTitle());
        Assert.assertNotNull(msSQLMyTopic.getCreateTime());
    }
    @Test
    public void query2() {
        EntityQueryable<MsSQLMyTopic1Proxy, MsSQLMyTopic1> queryable = entityQuery.queryable(MsSQLMyTopic1.class)
                .where(o -> o.Id().eq("123xxx"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT [Id],[Stars],[Title],[CreateTime] FROM [MyTopic] WHERE [Id] = ?", sql);
        MsSQLMyTopic1 msSQLMyTopic = queryable.firstOrNull();
        Assert.assertNull(msSQLMyTopic);
    }
    @Test
    public void query3() {
        EntityQueryable<MsSQLMyTopic1Proxy, MsSQLMyTopic1> queryable = entityQuery.queryable(MsSQLMyTopic1.class)
                .where(o -> o.Id().eq( "1"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT [Id],[Stars],[Title],[CreateTime] FROM [MyTopic] WHERE [Id] = ?", sql);
        MsSQLMyTopic1 msSQLMyTopic = queryable.firstOrNull();
        Assert.assertNotNull(msSQLMyTopic);
        Assert.assertNotNull(msSQLMyTopic.getId());
        Assert.assertNotNull(msSQLMyTopic.getStars());
        Assert.assertNotNull(msSQLMyTopic.getTitle());
        Assert.assertNotNull(msSQLMyTopic.getCreateTime());
    }
    @Test
    public void query4_1() {

        List<MsSQLMyTopic> list = entityQuery
                .queryable(MsSQLMyTopic.class)
                .where(o -> o.id().isNotNull())
                .limit(20).toList();
        Assert.assertEquals(20, list.size());
    }
    @Test
    public void query4_2() {

        List<MsSQLMyTopic> list = entityQuery
                .queryable(MsSQLMyTopic.class)
                .where(o -> o.id().isNotNull())
                .orderBy(o->o.id().asc())
                .limit(20).toList();
        Assert.assertEquals(20, list.size());
    }
    @Test
    public void query4() {

        EasyPageResult<MsSQLMyTopic> topicPageResult = entityQuery
                .queryable(MsSQLMyTopic.class)
                .where(o -> o.id().isNotNull())
                .toPageResult(2, 20);
        List<MsSQLMyTopic> data = topicPageResult.getData();
        Assert.assertEquals(20, data.size());
    }
    @Test
    public void query5_1() {

        EasyPageResult<MsSQLMyTopic> topicPageResult = entityQuery
                .queryable(MsSQLMyTopic.class)
                .where(o -> o.id().isNotNull())
                .orderBy(o->o.id().asc())
                .toPageResult(1, 20);
        List<MsSQLMyTopic> data = topicPageResult.getData();
        Assert.assertEquals(20, data.size());
    }
    @Test
    public void query5() {

        EasyPageResult<MsSQLMyTopic> topicPageResult = entityQuery
                .queryable(MsSQLMyTopic.class)
                .where(o -> o.id().isNotNull())
                .orderBy(o->o.id().asc())
                .toPageResult(2, 20);
        List<MsSQLMyTopic> data = topicPageResult.getData();
        Assert.assertEquals(20, data.size());
    }

    @Test
    public void query5_2() {

        String sql = entityQuery
                .queryable(MsSQLMyTopic.class)
                .where(o -> o.id().isNotNull())
                .orderBy(o->o.id().asc())
                .limit(40, 20).toSQL();
        Assert.assertEquals("WITH rt AS ( SELECT [Id],[Stars],[Title],[CreateTime], ROW_NUMBER() OVER( ORDER BY [Id] ASC ) AS __rownum__ FROM [MyTopic] WHERE [Id] IS NOT NULL ) SELECT rt.* FROM rt where rt.__rownum__ BETWEEN 41 AND 60",sql);
    }
    @Test
    public void query5_3() {

        String sql = entityQuery
                .queryable(MsSQLMyTopic.class)
                .where(o -> o.id().isNotNull())
                .limit(40, 20).toSQL();
        Assert.assertEquals("WITH rt AS ( SELECT [Id],[Stars],[Title],[CreateTime], ROW_NUMBER() OVER( ORDER BY [Id] ) AS __rownum__ FROM [MyTopic] WHERE [Id] IS NOT NULL ) SELECT rt.* FROM rt where rt.__rownum__ BETWEEN 41 AND 60",sql);
    }
    @Test
    public void query6() {

        EasyPageResult<MsSQLMyTopic> topicPageResult = entityQuery
                .queryable(MsSQLMyTopic.class)
                .where(o -> o.id().isNotNull())
                .orderBy(o-> o.stars().asc())
                .toPageResult(2, 20);
        List<MsSQLMyTopic> data = topicPageResult.getData();
        Assert.assertEquals(20, data.size());
        for (int i = 0; i < 20; i++) {
            MsSQLMyTopic msSQLMyTopic = data.get(i);
            Assert.assertEquals(msSQLMyTopic.getId(),String.valueOf(i+20) );
            Assert.assertEquals(msSQLMyTopic.getStars(),(Integer)(i+120) );
        }
    }


    @Test
    public void testDraft9() {
        String id = "123456zz9";
        entityQuery.deletable(MsSQLRowNumberMyTopic.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
        MsSQLRowNumberMyTopic blog = new MsSQLRowNumberMyTopic();
        blog.setId(id);
        blog.setCreateTime(LocalDateTime.of(2022, 1, 2, 3, 4, 5));
        blog.setTitle("titlez" );
        blog.setStars(1);
        entityQuery.insertable(blog)
                .executeRows();
        Draft3<LocalDateTime, LocalDateTime, LocalDateTime> draft31 = entityQuery.queryable(MsSQLRowNumberMyTopic.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        o.createTime().plus(1, TimeUnitEnum.DAYS),
                        o.createTime().plus(2, TimeUnitEnum.SECONDS),
                        o.createTime().plus(3, TimeUnitEnum.MINUTES)
                )).firstOrNull();

        Draft4<Long, Long, Long, Long> draft3 = entityQuery.queryable(MsSQLRowNumberMyTopic.class)
                .whereById(id)
                .select(o -> Select.DRAFT.of(
                        o.createTime().plus(1, TimeUnitEnum.DAYS).duration(o.createTime()).toDays(),
                        o.createTime().plus(2,TimeUnitEnum.SECONDS).duration(o.createTime()).toSeconds(),
                        o.createTime().plus(3,TimeUnitEnum.MINUTES).duration(o.createTime()).toMinutes(),
                        o.createTime().plus(3,TimeUnitEnum.HOURS).duration(o.createTime()).toMinutes()
                )).firstOrNull();

        Assert.assertNotNull(draft3);
        Long value1 = draft3.getValue1();
        Assert.assertEquals(-1, (long) value1);
        Long value2 = draft3.getValue2();
        Assert.assertEquals(-2, (long) value2);
        Long value3 = draft3.getValue3();
        Assert.assertEquals(-3, (long) value3);
        Long value4 = draft3.getValue4();
        Assert.assertEquals(-180, (long) value4);


        entityQuery.deletable(MsSQLRowNumberMyTopic.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
    }
}
