package com.easy.query.test.dameng;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.test.dameng.entity.DamengMyTopic;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2023/7/28 14:29
 * 文件说明
 *
 * @author xuejiaming
 */
public class DamengQueryTest extends DamengBaseTest{
    @Test
    public void query0() {
        Queryable<DamengMyTopic> queryable = easyQuery.queryable(DamengMyTopic.class)
                .where(o -> o.eq(DamengMyTopic::getId, "123xxx"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT \"ID\",\"STARS\",\"TITLE\",\"CREATE_TIME\" FROM \"MY_TOPIC\" WHERE \"ID\" = ?", sql);
        DamengMyTopic msSQLMyTopic = queryable.firstOrNull();
        Assert.assertNull(msSQLMyTopic);
    }
    @Test
    public void query1() {
        Queryable<DamengMyTopic> queryable = easyQuery.queryable(DamengMyTopic.class)
                .where(o -> o.eq(DamengMyTopic::getId, "123xxx")).limit(10,10);
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT rt.* FROM(SELECT \"ID\",\"STARS\",\"TITLE\",\"CREATE_TIME\", ROWNUM AS \"__rownum__\" FROM \"MY_TOPIC\" WHERE \"ID\" = ? AND ROWNUM < 21) rt WHERE rt.\"__rownum__\" > 10", sql);
        List<DamengMyTopic> msSQLMyTopic = queryable.toList();
        Assert.assertNotNull(msSQLMyTopic);
    }
    @Test
    public void query2() {
        Queryable<DamengMyTopic> queryable = easyQuery.queryable(DamengMyTopic.class)
                .where(o -> o.eq(DamengMyTopic::getId, "123xxx")).limit(10,10).orderByAsc(o->o.column(DamengMyTopic::getCreateTime));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT rt1.* FROM (SELECT rt.*, ROWNUM AS \"__rownum__\" FROM (SELECT \"ID\",\"STARS\",\"TITLE\",\"CREATE_TIME\" FROM \"MY_TOPIC\" WHERE \"ID\" = ? ORDER BY \"CREATE_TIME\" ASC) rt WHERE ROWNUM < 21) rt1 WHERE rt1.\"__rownum__\" > 10", sql);
        List<DamengMyTopic> msSQLMyTopic = queryable.toList();
        Assert.assertNotNull(msSQLMyTopic);
    }


    @Test
    public void query4() {

        EasyPageResult<DamengMyTopic> topicPageResult = easyQuery
                .queryable(DamengMyTopic.class)
                .where(o -> o.isNotNull(DamengMyTopic::getId))
                .toPageResult(2, 20);
        List<DamengMyTopic> data = topicPageResult.getData();
        Assert.assertEquals(20, data.size());
    }
    @Test
    public void query5() {

        EasyPageResult<DamengMyTopic> topicPageResult = easyQuery
                .queryable(DamengMyTopic.class)
                .where(o -> o.isNotNull(DamengMyTopic::getId))
                .orderByAsc(o->o.column(DamengMyTopic::getId))
                .toPageResult(2, 20);
        List<DamengMyTopic> data = topicPageResult.getData();
        Assert.assertEquals(20, data.size());
    }
    @Test
    public void query6() {

        EasyPageResult<DamengMyTopic> topicPageResult = easyQuery
                .queryable(DamengMyTopic.class)
                .where(o -> o.isNotNull(DamengMyTopic::getId))
                .orderByAsc(o->o.column(DamengMyTopic::getStars))
                .toPageResult(2, 20);
        List<DamengMyTopic> data = topicPageResult.getData();
        Assert.assertEquals(20, data.size());
        for (int i = 0; i < 20; i++) {
            DamengMyTopic msSQLMyTopic = data.get(i);
            Assert.assertEquals(msSQLMyTopic.getId(),String.valueOf(i+20) );
            Assert.assertEquals(msSQLMyTopic.getStars(),(Integer)(i+120) );
        }
    }
}
