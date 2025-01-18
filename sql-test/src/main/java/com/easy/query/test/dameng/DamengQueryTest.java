package com.easy.query.test.dameng;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.func.def.enums.DateTimeDurationEnum;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.test.dameng.entity.DamengMyTopic;
import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * create time 2023/7/28 14:29
 * 文件说明
 *
 * @author xuejiaming
 */
public class DamengQueryTest extends DamengBaseTest {
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
    public void query0_1() {
        Queryable<DamengMyTopic> queryable = easyQuery.queryable(DamengMyTopic.class)
                .where(o -> o.eq(DamengMyTopic::getId, "123xxx")).limit(1);
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT \"ID\",\"STARS\",\"TITLE\",\"CREATE_TIME\" FROM \"MY_TOPIC\" WHERE \"ID\" = ? AND ROWNUM < 2", sql);
    }

    @Test
    public void query1() {
        Queryable<DamengMyTopic> queryable = easyQuery.queryable(DamengMyTopic.class)
                .where(o -> o.eq(DamengMyTopic::getId, "123xxx")).limit(10, 10);
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT rt.* FROM(SELECT \"ID\",\"STARS\",\"TITLE\",\"CREATE_TIME\", ROWNUM AS \"__rownum__\" FROM \"MY_TOPIC\" WHERE \"ID\" = ? AND ROWNUM < 21) rt WHERE rt.\"__rownum__\" > 10", sql);
        List<DamengMyTopic> msSQLMyTopic = queryable.toList();
        Assert.assertNotNull(msSQLMyTopic);
    }

    @Test
    public void query2() {
        Queryable<DamengMyTopic> queryable = easyQuery.queryable(DamengMyTopic.class)
                .where(o -> o.eq(DamengMyTopic::getId, "123xxx")).limit(10, 10).orderByAsc(o -> o.column(DamengMyTopic::getCreateTime));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT rt1.* FROM (SELECT rt.*, ROWNUM AS \"__rownum__\" FROM (SELECT \"ID\",\"STARS\",\"TITLE\",\"CREATE_TIME\" FROM \"MY_TOPIC\" WHERE \"ID\" = ? ORDER BY \"CREATE_TIME\" ASC) rt WHERE ROWNUM < 21) rt1 WHERE rt1.\"__rownum__\" > 10", sql);
        List<DamengMyTopic> msSQLMyTopic = queryable.toList();
        Assert.assertNotNull(msSQLMyTopic);
    }

    @Test
    public void query3() {
        Queryable<DamengMyTopic> queryable = easyQuery.queryable(DamengMyTopic.class)
                .where(o -> o.eq(DamengMyTopic::getId, "123xxx")).limit(0, 10).orderByAsc(o -> o.column(DamengMyTopic::getCreateTime));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT rt.* FROM (SELECT \"ID\",\"STARS\",\"TITLE\",\"CREATE_TIME\" FROM \"MY_TOPIC\" WHERE \"ID\" = ? ORDER BY \"CREATE_TIME\" ASC) rt WHERE ROWNUM < 11", sql);
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
                .orderByAsc(o -> o.column(DamengMyTopic::getId))
                .toPageResult(2, 20);
        List<DamengMyTopic> data = topicPageResult.getData();
        Assert.assertEquals(20, data.size());
    }

    @Test
    public void query6() {
        List<DamengMyTopic> list = easyQuery
                .queryable(DamengMyTopic.class).toList();
        EasyPageResult<DamengMyTopic> topicPageResult = easyQuery
                .queryable(DamengMyTopic.class)
                .where(o -> o.isNotNull(DamengMyTopic::getId))
                .orderByAsc(o -> o.column(DamengMyTopic::getStars))
                .toPageResult(2, 20);
        List<DamengMyTopic> data = topicPageResult.getData();
        Assert.assertEquals(20, data.size());
        for (int i = 0; i < 20; i++) {
            DamengMyTopic msSQLMyTopic = data.get(i);
            Assert.assertEquals(msSQLMyTopic.getId(), String.valueOf(i + 20));
            Assert.assertEquals(msSQLMyTopic.getStars(), (Integer) (i + 120));
        }
    }

    @Test
    public void query7() {
        List<DamengMyTopic> list = easyQuery.getEasyQueryClient()
                .queryable(DamengMyTopic.class)
                .where(o -> o.eq("id", "1"))
                .asTreeCTE()
                .toList();
        Assert.assertNotNull(list);
    }

    @Test
    public void testJoin1() {
        List<Draft2<String, String>> list = entityQuery.queryable(DamengMyTopic.class)
                .groupBy(o -> GroupKeys.of(o.title()))
                .select(o -> Select.DRAFT.of(
                        o.key1(),
                        o.groupTable().id().join(",")
                )).toList();
    }

    @Test
    public void test111() {
        List<Draft1<String>> list = entityQuery.queryable(DamengMyTopic.class)
                .select(d -> Select.DRAFT.of(
                        d.createTime().format("yyyy-MM").concat("-01")
                )).toList();
        System.out.println(list);
    }
//    @Test
//    public void test112(){
//        List<Draft1<String>> list = entityQuery.queryable(DamengMyTopic.class)
//                .select(d -> Select.DRAFT.of(
//                        d.createTime().format("yyyy-MM月-01")
//                )).toList();
//        System.out.println(list);
//    }

    @Test
    public void testDraft9() {
        String id = "123456zz9";
        entityQuery.deletable(DamengMyTopic.class)
                .whereById(id)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
        DamengMyTopic blog = new DamengMyTopic();
        blog.setId(id);
        blog.setCreateTime(LocalDateTime.of(2022, 1, 2, 3, 4, 5));
        blog.setTitle("titlez");
        blog.setStars(1);
        entityQuery.insertable(blog)
                .executeRows();


        try {

            LocalDateTime of = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
            LocalDateTime localDateTime = of.plusMinutes(3);

            Draft1<BigDecimal> localDateTimeDraft12 = entityQuery.queryable(DamengMyTopic.class)
                    .whereById(id)
                    .select(o -> Select.DRAFT.of(
                            o.expression().constant().valueOf(of).duration(o.expression().constant().valueOf(localDateTime), DateTimeDurationEnum.Seconds).asAnyType(BigDecimal.class)
                    )).firstOrNull();
            Assert.assertEquals(BigDecimal.valueOf(-180).compareTo(localDateTimeDraft12.getValue1()), 0);
            Draft3<Long, LocalDateTime, LocalDateTime> draft31 = entityQuery.queryable(DamengMyTopic.class)
                    .whereById(id)
                    .select(o -> Select.DRAFT.of(
                            o.createTime().duration(o.createTime().plus(1, TimeUnit.DAYS), DateTimeDurationEnum.Days),
                            o.createTime().plus(2, TimeUnit.SECONDS),
                            o.createTime().plus(3, TimeUnit.MINUTES)
                    )).firstOrNull();
            {

                Draft3<Long, Long, Long> draft3 = entityQuery.queryable(DamengMyTopic.class)
                        .whereById(id)
                        .select(o -> Select.DRAFT.of(
                                o.createTime().duration(o.createTime().plus(1, TimeUnit.DAYS), DateTimeDurationEnum.Days),
                                o.createTime().duration(o.createTime().plus(2, TimeUnit.SECONDS), DateTimeDurationEnum.Seconds),
                                o.createTime().duration(o.createTime().plus(3, TimeUnit.MINUTES), DateTimeDurationEnum.Minutes)
                        )).firstOrNull();

                Assert.assertNotNull(draft3);
                Long value1 = draft3.getValue1();
                Assert.assertEquals(-1, (long) value1);
                Long value2 = draft3.getValue2();
                Assert.assertEquals(-2, (long) value2);
                Long value3 = draft3.getValue3();
                Assert.assertEquals(-3, (long) value3);

            }

            Draft1<LocalDateTime> localDateTimeDraft1 = entityQuery.queryable(DamengMyTopic.class)
                    .whereById(id)
                    .select(o -> Select.DRAFT.of(
                            o.expression().constant().valueOf(of).plus(3, TimeUnit.MINUTES)
                    )).firstOrNull();
            Assert.assertEquals(localDateTimeDraft1.getValue1(), of.plusMinutes(3));

            Draft1<BigDecimal> localDateTimeDraft121 = entityQuery.queryable(DamengMyTopic.class)
                    .whereById(id)
                    .select(o -> Select.DRAFT.of(
                            o.expression().constant().valueOf(of).duration(o.expression().constant().valueOf(of).plus(3, TimeUnit.MINUTES), DateTimeDurationEnum.Seconds).asAnyType(BigDecimal.class)
                    )).firstOrNull();

            Assert.assertEquals(BigDecimal.valueOf(-180).compareTo(localDateTimeDraft121.getValue1()), 0);

            {

                Draft3<Long, Long, Long> draft3 = entityQuery.queryable(DamengMyTopic.class)
                        .whereById(id)
                        .select(o -> Select.DRAFT.of(
                                o.createTime().duration(o.createTime().plus(1, TimeUnit.DAYS), DateTimeDurationEnum.Days),
                                o.createTime().duration(o.createTime().plus(2, TimeUnit.SECONDS), DateTimeDurationEnum.Seconds),
                                o.createTime().duration(o.createTime().plus(1, TimeUnit.DAYS).plus(3, TimeUnit.MINUTES), DateTimeDurationEnum.Minutes)
                        )).firstOrNull();


                Assert.assertNotNull(draft3);
                Long value1 = draft3.getValue1();
                Assert.assertEquals(-1, (long) value1);
                Long value2 = draft3.getValue2();
                Assert.assertEquals(-2, (long) value2);
                Long value3 = draft3.getValue3();
                Assert.assertEquals(-1443, (long) value3);
            }
        } finally {

            entityQuery.deletable(DamengMyTopic.class)
                    .whereById(id)
                    .disableLogicDelete()
                    .allowDeleteStatement(true)
                    .executeRows();
        }
    }

//    @Test
//    public void batchInsert(){
//        ArrayList<DamengMyTopic> damengMyTopics = new ArrayList<>();
//        for (int i = 0; i < 10000; i++) {
//            DamengMyTopic damengMyTopic = new DamengMyTopic();
//            damengMyTopic.setId(UUID.randomUUID().toString().replaceAll("-",""));
//            damengMyTopic.setStars(i);
//            damengMyTopic.setTitle("111");
//            damengMyTopic.setCreateTime(LocalDateTime.now());
//            damengMyTopics.add(damengMyTopic);
//        }
//        long begin = System.currentTimeMillis();
//        long l = easyQuery.insertable(damengMyTopics).batch().executeRows();
//        long end = System.currentTimeMillis();
//        String s = "插入:" + damengMyTopics.size() + "条,用时:" + (end - begin) + "(ms)";
//        System.out.println(s);
//    }

    @Test
    public void test1() {
        List<DamengMyTopic> list = easyQuery.queryable(DamengMyTopic.class)
                .where(d -> d.ge(DamengMyTopic::getCreateTime, LocalDateTime.now()))
                .toList();
        List<DamengMyTopic> list1 = entityQuery.queryable(DamengMyTopic.class)
                .where(x -> {
                    x.createTime().duration(LocalDateTime.now(), DateTimeDurationEnum.Seconds).gt(100L);
                }).toList();
    }


    @Test
    public void testaa() {
        List<VO> list = entityQuery.queryable(DamengMyTopic.class)
                .groupBy(d -> GroupKeys.of(d.createTime().format("yyyyMMddHH")))
                .select(VO.class, group -> Select.of(
                        group.key1().as(VO.Fields.hourStr),
                        group.count().as(VO.Fields.count)
                )).toList();
        for (VO vo : list) {
            System.out.println("hourStr:" + vo.getHourStr() + ",time:" + vo.getTime() + ",count:" + vo.getCount());
        }
    }

    @Data
    @FieldNameConstants
    public static class VO {
        private String hourStr;
        private Long count;


        public LocalDateTime getTime() {
            return LocalDateTime.parse(hourStr, DateTimeFormatter.ofPattern("yyyyMMddHH"));
        }
    }


//    @Data
//    @FieldNameConstants
//    public static class TestVO {
//        private Integer status;
//
//        public void setStatus(Integer status) {
//            if (status == 1) {
//                this.statusName = "正常";
//            } else {
//                this.statusName = "不正常";
//            }
//            this.status = status;
//        }
//
//        private String statusName;
//    }
}
