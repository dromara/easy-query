package com.easy.query.test.h2;

import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.api.proxy.entity.select.EntityQueryable3;
import com.easy.query.api.proxy.entity.select.EntityQueryable4;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.h2.domain.ALLTYPE;
import com.easy.query.test.h2.domain.ALLTYPE1;
import com.easy.query.test.h2.domain.ALLTYPESharding;
import com.easy.query.test.h2.domain.DefTable;
import com.easy.query.test.h2.domain.DefTableLeft1;
import com.easy.query.test.h2.domain.DefTableLeft2;
import com.easy.query.test.h2.domain.DefTableLeft3;
import com.easy.query.test.h2.domain.H2BookTest;
import com.easy.query.test.h2.domain.proxy.ALLTYPE1Proxy;
import com.easy.query.test.h2.domain.proxy.ALLTYPEProxy;
import com.easy.query.test.h2.domain.proxy.DefTableLeft1Proxy;
import com.easy.query.test.h2.domain.proxy.DefTableLeft2Proxy;
import com.easy.query.test.h2.domain.proxy.DefTableLeft3Proxy;
import com.easy.query.test.h2.domain.proxy.DefTableProxy;
import com.easy.query.test.h2.vo.ALLTYPEVO1;
import com.easy.query.test.h2.vo.proxy.ALLTYPEVO1Proxy;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * create time 2023/6/6 13:25
 * 文件说明
 *
 * @author xuejiaming
 */
public class H2QueryTest extends H2BaseTest {
    @Test
    public void leftJoin1() {

        EntityQueryable2<DefTableProxy, DefTable, DefTableLeft1Proxy, DefTableLeft1> where = easyEntityQuery.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.id().eq(t1.defId()))
                .where((t, t1) -> {
                    t.id().eq("1");
                    t1.id().eq("1");
                });
        List<DefTable> list = where.cloneQueryable()
                .toList();
        Assert.assertEquals(1, list.size());
        String sql = where.cloneQueryable().toSQL();
        Assert.assertEquals("SELECT t.id,t.user_name,t.nickname,t.enable,t.score,t.mobile,t.avatar,t.number,t.status,t.created,t.options FROM t_def_table t LEFT JOIN t_def_table_left1 t1 ON t.id = t1.def_id WHERE t.id = ? AND t1.id = ?", sql);
    }

    @Test
    public void leftJoin1_1() {
        EntityQueryable2<DefTableProxy, DefTable, DefTableLeft1Proxy, DefTableLeft1> where = easyEntityQuery.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.id().eq(t1.defId()))
                .where(t -> t.id().eq("1"));
        List<DefTable> list = where.cloneQueryable()
                .toList();
        Assert.assertEquals(1, list.size());
        String sql = where.cloneQueryable().toSQL();
        Assert.assertEquals("SELECT t.id,t.user_name,t.nickname,t.enable,t.score,t.mobile,t.avatar,t.number,t.status,t.created,t.options FROM t_def_table t LEFT JOIN t_def_table_left1 t1 ON t.id = t1.def_id WHERE t.id = ?", sql);
    }

    @Test
    public void leftJoin2() {
        EntityQueryable3<DefTableProxy, DefTable, DefTableLeft1Proxy, DefTableLeft1, DefTableLeft2Proxy, DefTableLeft2> where = easyEntityQuery.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.id().eq(t1.defId()))
                .leftJoin(DefTableLeft2.class, (t, t1, t2) -> t.id().eq(t2.def1Id()))
                .where((t, t1, t2) -> {
                    t.id().eq("1");
                    t1.id().eq("1");
                    t2.id().eq("1");
                });
        List<DefTable> list = where.cloneQueryable()
                .toList();
        Assert.assertEquals(1, list.size());
        String sql = where.cloneQueryable().toSQL();
        Assert.assertEquals("SELECT t.id,t.user_name,t.nickname,t.enable,t.score,t.mobile,t.avatar,t.number,t.status,t.created,t.options FROM t_def_table t LEFT JOIN t_def_table_left1 t1 ON t.id = t1.def_id LEFT JOIN t_def_table_left2 t2 ON t.id = t2.def1_id WHERE t.id = ? AND t1.id = ? AND t2.id = ?", sql);
    }

    @Test
    public void leftJoin2_1() {
        EntityQueryable3<DefTableProxy, DefTable, DefTableLeft1Proxy, DefTableLeft1, DefTableLeft2Proxy, DefTableLeft2> where = easyEntityQuery.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.id().eq(t1.defId()))
                .leftJoin(DefTableLeft2.class, (t, t1, t2) -> t.id().eq(t2.def1Id()))
                .where(t -> t.id().eq("1"));
        List<DefTable> list = where.cloneQueryable()
                .toList();
        Assert.assertEquals(1, list.size());
        String sql = where.cloneQueryable().toSQL();
        Assert.assertEquals("SELECT t.id,t.user_name,t.nickname,t.enable,t.score,t.mobile,t.avatar,t.number,t.status,t.created,t.options FROM t_def_table t LEFT JOIN t_def_table_left1 t1 ON t.id = t1.def_id LEFT JOIN t_def_table_left2 t2 ON t.id = t2.def1_id WHERE t.id = ?", sql);
    }

    @Test
    public void leftJoin3() {
        EntityQueryable3<DefTableProxy, DefTable, DefTableLeft1Proxy, DefTableLeft1, DefTableLeft3Proxy, DefTableLeft3> where = easyEntityQuery.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.id().eq(t1.defId()))
                .leftJoin(DefTableLeft3.class, (t, t1, t3) -> t.id().eq(t3.def2Id()))
                .where((t, t1, t2) -> {
                    t.id().eq("1");
                    t1.id().eq("1");
                    t2.id().eq("1");
                });
        List<DefTable> list = where.cloneQueryable()
                .toList();
        Assert.assertEquals(1, list.size());
        String sql = where.cloneQueryable().toSQL();
        Assert.assertEquals("SELECT t.id,t.user_name,t.nickname,t.enable,t.score,t.mobile,t.avatar,t.number,t.status,t.created,t.options FROM t_def_table t LEFT JOIN t_def_table_left1 t1 ON t.id = t1.def_id LEFT JOIN t_def_table_left3 t2 ON t.id = t2.def2_id WHERE t.id = ? AND t1.id = ? AND t2.id = ?", sql);
    }

    @Test
    public void leftJoin3_1() {
        EntityQueryable3<DefTableProxy, DefTable, DefTableLeft1Proxy, DefTableLeft1, DefTableLeft3Proxy, DefTableLeft3> where = easyEntityQuery.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.id().eq(t1.defId()))
                .leftJoin(DefTableLeft3.class, (t, t1, t3) -> t.id().eq(t3.def2Id()))
                .where(t -> t.id().eq("1"));
        List<DefTable> list = where.cloneQueryable()
                .toList();
        Assert.assertEquals(1, list.size());
        String sql = where.cloneQueryable().toSQL();
        Assert.assertEquals("SELECT t.id,t.user_name,t.nickname,t.enable,t.score,t.mobile,t.avatar,t.number,t.status,t.created,t.options FROM t_def_table t LEFT JOIN t_def_table_left1 t1 ON t.id = t1.def_id LEFT JOIN t_def_table_left3 t2 ON t.id = t2.def2_id WHERE t.id = ?", sql);
    }

    @Test
    public void leftJoin4() {
        EntityQueryable4<DefTableProxy, DefTable, DefTableLeft1Proxy, DefTableLeft1, DefTableLeft2Proxy, DefTableLeft2, DefTableLeft3Proxy, DefTableLeft3> where = easyEntityQuery.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.id().eq(t1.defId()))
                .leftJoin(DefTableLeft2.class, (t, t1, t2) -> t.id().eq(t2.def1Id()))
                .leftJoin(DefTableLeft3.class, (t, t1, t2, t3) -> t.id().eq(t3.def2Id()))
                .where((t, t1, t2, t3) -> {
                    t.id().eq("1");
                    t1.id().eq("1");
                    t2.id().eq("1");
                    t3.id().eq("1");
                });
        List<DefTable> list = where.cloneQueryable()
                .toList();
        Assert.assertEquals(1, list.size());
        String sql = where.cloneQueryable().toSQL();
        Assert.assertEquals("SELECT t.id,t.user_name,t.nickname,t.enable,t.score,t.mobile,t.avatar,t.number,t.status,t.created,t.options FROM t_def_table t LEFT JOIN t_def_table_left1 t1 ON t.id = t1.def_id LEFT JOIN t_def_table_left2 t2 ON t.id = t2.def1_id LEFT JOIN t_def_table_left3 t3 ON t.id = t3.def2_id WHERE t.id = ? AND t1.id = ? AND t2.id = ? AND t3.id = ?", sql);
    }

    @Test
    public void leftJoin4_1() {
        EntityQueryable4<DefTableProxy, DefTable, DefTableLeft1Proxy, DefTableLeft1, DefTableLeft2Proxy, DefTableLeft2, DefTableLeft3Proxy, DefTableLeft3> where = easyEntityQuery.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.id().eq(t1.defId()))
                .leftJoin(DefTableLeft2.class, (t, t1, t2) -> t.id().eq(t2.def1Id()))
                .leftJoin(DefTableLeft3.class, (t, t1, t2, t3) -> t.id().eq(t3.def2Id()))
                .where(t -> t.id().eq("1"));
        List<DefTable> list = where.cloneQueryable()
                .toList();
        Assert.assertEquals(1, list.size());
        String sql = where.cloneQueryable().toSQL();
        Assert.assertEquals("SELECT t.id,t.user_name,t.nickname,t.enable,t.score,t.mobile,t.avatar,t.number,t.status,t.created,t.options FROM t_def_table t LEFT JOIN t_def_table_left1 t1 ON t.id = t1.def_id LEFT JOIN t_def_table_left2 t2 ON t.id = t2.def1_id LEFT JOIN t_def_table_left3 t3 ON t.id = t3.def2_id WHERE t.id = ?", sql);
    }

    @Test
    public void allTypeTest1() {
        ALLTYPE alltype = new ALLTYPE();
        alltype.setId("123");

        alltype.setNumberDecimal(new BigDecimal("12.33"));
        alltype.setNumberFloat(12.3f);
        alltype.setNumberDouble(22.1d);
        alltype.setNumberShort(new Short("12"));
        alltype.setNumberInteger(33);
        alltype.setNumberLong(12345678911L);
        alltype.setNumberByte(new Byte("-1"));
        alltype.setEnable(true);
        alltype.setTimeLocalDateTime(LocalDateTime.of(2021, 1, 1, 0, 0));
        alltype.setTimeLocalDate(LocalDate.of(2121, 1, 2));
        alltype.setTimeLocalTime(LocalTime.of(21, 1, 9));
        alltype.setOnlyDate(new Date());
        long epochMilli = LocalDateTime.now().toLocalDate().atStartOfDay()
                .toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        java.sql.Date date = new java.sql.Date(epochMilli);
        alltype.setSqlDate(date);
        alltype.setOnlyTime(Time.valueOf("12:09:10"));
        alltype.setValue("3322");
        alltype.setUid(UUID.randomUUID());
        alltype.setNumberFloatBasic(12.3f);
        alltype.setNumberDoubleBasic(22.1d);
        alltype.setNumberShortBasic(new Short("12"));
        alltype.setNumberIntegerBasic(33);
        alltype.setNumberLongBasic(12345678911L);
        alltype.setNumberByteBasic(new Byte("-1"));
        alltype.setEnableBasic(true);
        long l = easyEntityQuery.insertable(alltype).executeRows();
        Assert.assertEquals(1, l);
        ALLTYPE alltype1 = easyEntityQuery.queryable(ALLTYPE.class)
                .whereById("123").firstOrNull();
        Assert.assertNotNull(alltype1);
        Assert.assertEquals(alltype1.getId(), alltype.getId());
        Assert.assertEquals(alltype1.getNumberDecimal(), alltype.getNumberDecimal());
        Assert.assertEquals(alltype1.getNumberFloat(), alltype.getNumberFloat());
        Assert.assertEquals(alltype1.getNumberDouble(), alltype.getNumberDouble());
        Assert.assertEquals(alltype1.getNumberShort(), alltype.getNumberShort());
        Assert.assertEquals(alltype1.getNumberInteger(), alltype.getNumberInteger());
        Assert.assertEquals(alltype1.getNumberLong(), alltype.getNumberLong());
        Assert.assertEquals(alltype1.getNumberByte(), alltype.getNumberByte());
        Assert.assertEquals(alltype1.getTimeLocalDateTime(), alltype.getTimeLocalDateTime());
        Assert.assertEquals(alltype1.getTimeLocalDate(), alltype.getTimeLocalDate());
        Assert.assertEquals(alltype1.getTimeLocalTime(), alltype.getTimeLocalTime());
        Assert.assertEquals(alltype1.getEnable(), alltype.getEnable());
        Assert.assertEquals(alltype1.getValue(), alltype.getValue());
        Assert.assertEquals(alltype1.getUid(), alltype.getUid());
        Assert.assertEquals(alltype1.getSqlDate(), alltype.getSqlDate());
        Assert.assertEquals(alltype1.getOnlyDate(), alltype.getOnlyDate());
        Assert.assertEquals(alltype1.getOnlyTime(), alltype.getOnlyTime());
        Assert.assertTrue(alltype1.getNumberFloatBasic() == alltype.getNumberFloatBasic());
        Assert.assertTrue(alltype1.getNumberDoubleBasic() == alltype.getNumberDoubleBasic());
        Assert.assertEquals(alltype1.getNumberShortBasic(), alltype.getNumberShortBasic());
        Assert.assertEquals(alltype1.getNumberIntegerBasic(), alltype.getNumberIntegerBasic());
        Assert.assertEquals(alltype1.getNumberLongBasic(), alltype.getNumberLongBasic());
        Assert.assertEquals(alltype1.getNumberByteBasic(), alltype.getNumberByteBasic());
        Assert.assertEquals(alltype1.isEnableBasic(), alltype.isEnableBasic());
    }

    @Test
    public void allTypeTest1_1() {
        ALLTYPE1 alltype = new ALLTYPE1();
        alltype.setId("1234x");

        alltype.setNumberDecimal(new BigDecimal("12.33"));
        alltype.setNumberFloat(12.3f);
        alltype.setNumberDouble(22.1d);
        alltype.setNumberShort(new Short("12"));
        alltype.setNumberInteger(33);
        alltype.setNumberLong(12345678911L);
        alltype.setNumberByte(new Byte("-1"));
        alltype.setEnable(true);
        alltype.setTimeLocalDateTime(LocalDateTime.of(2021, 1, 1, 0, 0));
        alltype.setTimeLocalDate(LocalDate.of(2121, 1, 2));
        alltype.setTimeLocalTime(LocalTime.of(21, 1, 9));
        alltype.setOnlyDate(new Date());
        long epochMilli = LocalDateTime.now().toLocalDate().atStartOfDay()
                .toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        java.sql.Date date = new java.sql.Date(epochMilli);
        alltype.setSqlDate(date);
        alltype.setOnlyTime(Time.valueOf("12:09:10"));
        alltype.setValue("3322");
        alltype.setUid(UUID.randomUUID());

        alltype.setNumberFloatBasic(12.3f);
        alltype.setNumberDoubleBasic(22.1d);
        alltype.setNumberShortBasic(new Short("12"));
        alltype.setNumberIntegerBasic(33);
        alltype.setNumberLongBasic(12345678911L);
        alltype.setNumberByteBasic(new Byte("-1"));
        alltype.setEnableBasic(true);
        long l = easyEntityQuery.insertable(alltype).executeRows();
        Assert.assertEquals(1, l);
        ALLTYPE1 alltype1 = easyEntityQuery.queryable(ALLTYPE1.class)
                .whereById("1234x").firstOrNull();
        Assert.assertNotNull(alltype1);
        Assert.assertEquals(alltype1.getId(), alltype.getId());
        Assert.assertEquals(alltype1.getNumberDecimal(), alltype.getNumberDecimal());
        Assert.assertEquals(alltype1.getNumberFloat(), alltype.getNumberFloat());
        Assert.assertEquals(alltype1.getNumberDouble(), alltype.getNumberDouble());
        Assert.assertEquals(alltype1.getNumberShort(), alltype.getNumberShort());
        Assert.assertEquals(alltype1.getNumberInteger(), alltype.getNumberInteger());
        Assert.assertEquals(alltype1.getNumberLong(), alltype.getNumberLong());
        Assert.assertEquals(alltype1.getNumberByte(), alltype.getNumberByte());
        Assert.assertEquals(alltype1.getTimeLocalDateTime(), alltype.getTimeLocalDateTime());
        Assert.assertEquals(alltype1.getTimeLocalDate(), alltype.getTimeLocalDate());
        Assert.assertEquals(alltype1.getTimeLocalTime(), alltype.getTimeLocalTime());
        Assert.assertEquals(alltype1.getEnable(), alltype.getEnable());
        Assert.assertEquals(alltype1.getValue(), alltype.getValue());
        Assert.assertEquals(alltype1.getUid(), alltype.getUid());
        Assert.assertEquals(alltype1.getSqlDate(), alltype.getSqlDate());
        Assert.assertEquals(alltype1.getOnlyDate(), alltype.getOnlyDate());
        Assert.assertEquals(alltype1.getOnlyTime(), alltype.getOnlyTime());
        Assert.assertTrue(alltype1.getNumberFloatBasic() == alltype.getNumberFloatBasic());
        Assert.assertTrue(alltype1.getNumberDoubleBasic() == alltype.getNumberDoubleBasic());
        Assert.assertEquals(alltype1.getNumberShortBasic(), alltype.getNumberShortBasic());
        Assert.assertEquals(alltype1.getNumberIntegerBasic(), alltype.getNumberIntegerBasic());
        Assert.assertEquals(alltype1.getNumberLongBasic(), alltype.getNumberLongBasic());
        Assert.assertEquals(alltype1.getNumberByteBasic(), alltype.getNumberByteBasic());
        Assert.assertEquals(alltype1.isEnableBasic(), alltype.isEnableBasic());
    }

    @Test
    public void allTypeTest1_2() {
        ALLTYPE1 alltype = new ALLTYPE1();
        alltype.setId("1235");

        alltype.setNumberDecimal(new BigDecimal("12.33"));
        alltype.setNumberFloat(12.3f);
        alltype.setNumberDouble(22.1d);
        alltype.setNumberShort(new Short("12"));
        alltype.setNumberInteger(33);
        alltype.setNumberLong(12345678911L);
        alltype.setNumberByte(new Byte("-1"));
        alltype.setEnable(true);
        alltype.setTimeLocalDateTime(LocalDateTime.of(2021, 1, 1, 0, 0));
        alltype.setTimeLocalDate(LocalDate.of(2121, 1, 2));
        alltype.setTimeLocalTime(LocalTime.of(21, 1, 9));
        alltype.setOnlyDate(new Date());
        long epochMilli = LocalDateTime.now().toLocalDate().atStartOfDay()
                .toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        java.sql.Date date = new java.sql.Date(epochMilli);
        alltype.setSqlDate(date);
        alltype.setOnlyTime(Time.valueOf("12:09:10"));
        alltype.setValue("3322");
        alltype.setUid(UUID.randomUUID());

        alltype.setNumberFloatBasic(12.3f);
        alltype.setNumberDoubleBasic(22.1d);
        alltype.setNumberShortBasic(new Short("12"));
        alltype.setNumberIntegerBasic(33);
        alltype.setNumberLongBasic(12345678911L);
        alltype.setNumberByteBasic(new Byte("-1"));
        alltype.setEnableBasic(true);
        long l = easyEntityQuery.insertable(alltype).executeRows();
        Assert.assertEquals(1, l);
        ALLTYPEVO1 alltype1 = easyEntityQuery.queryable(ALLTYPE1.class)
                .whereById("1235").select(ALLTYPEVO1.class).firstOrNull();
        Assert.assertNotNull(alltype1);
        Assert.assertEquals(alltype1.getId(), alltype.getId());
        Assert.assertEquals(alltype1.getNumberDecimal(), alltype.getNumberDecimal());
        Assert.assertEquals(alltype1.getNumberFloat(), alltype.getNumberFloat());
        Assert.assertEquals(alltype1.getNumberDouble(), alltype.getNumberDouble());
        Assert.assertEquals(alltype1.getNumberShort(), alltype.getNumberShort());
        Assert.assertEquals(alltype1.getNumberInteger(), alltype.getNumberInteger());
        Assert.assertEquals(alltype1.getNumberLong(), alltype.getNumberLong());
        Assert.assertEquals(alltype1.getNumberByte(), alltype.getNumberByte());
        Assert.assertEquals(alltype1.getTimeLocalDateTime(), alltype.getTimeLocalDateTime());
        Assert.assertEquals(alltype1.getTimeLocalDate(), alltype.getTimeLocalDate());
        Assert.assertEquals(alltype1.getTimeLocalTime(), alltype.getTimeLocalTime());
        Assert.assertEquals(alltype1.getEnable(), alltype.getEnable());
        Assert.assertEquals(alltype1.getValue(), alltype.getValue());
        Assert.assertEquals(alltype1.getUid(), alltype.getUid());
        Assert.assertEquals(alltype1.getSqlDate(), alltype.getSqlDate());
        Assert.assertEquals(alltype1.getOnlyDate(), alltype.getOnlyDate());
        Assert.assertEquals(alltype1.getOnlyTime(), alltype.getOnlyTime());
        Assert.assertTrue(alltype1.getNumberFloatBasic() == alltype.getNumberFloatBasic());
        Assert.assertTrue(alltype1.getNumberDoubleBasic() == alltype.getNumberDoubleBasic());
        Assert.assertEquals(alltype1.getNumberShortBasic(), alltype.getNumberShortBasic());
        Assert.assertEquals(alltype1.getNumberIntegerBasic(), alltype.getNumberIntegerBasic());
        Assert.assertEquals(alltype1.getNumberLongBasic(), alltype.getNumberLongBasic());
        Assert.assertEquals(alltype1.getNumberByteBasic(), alltype.getNumberByteBasic());
        Assert.assertEquals(alltype1.isEnableBasic(), alltype.isEnableBasic());
    }

    @Test
    public void allTypeTestSharding1() {
        ArrayList<ALLTYPESharding> alltypeShardings = new ArrayList<>();
        for (int i = 0; i < 13; i++) {

            ALLTYPESharding alltypeSharding = new ALLTYPESharding();
            alltypeSharding.setId(String.valueOf(i));

            alltypeSharding.setNumberDecimal(new BigDecimal("12.33"));
            alltypeSharding.setNumberFloat(12.3f);
            alltypeSharding.setNumberDouble(22.1d);
            alltypeSharding.setNumberShort(new Short("12"));
            alltypeSharding.setNumberInteger(i);
            alltypeSharding.setNumberLong(12345678911L);
            alltypeSharding.setNumberByte(new Byte("-1"));
            alltypeSharding.setTimeLocalDateTime(LocalDateTime.of(2021, 1, 1, 0, 0));
            alltypeSharding.setTimeLocalDate(LocalDate.of(2121, 1, 2));
            alltypeSharding.setTimeLocalTime(LocalTime.of(21, 1, 9));
            alltypeSharding.setOnlyDate(new Date());
            long epochMilli = LocalDateTime.now().toLocalDate().atStartOfDay()
                    .toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
            java.sql.Date date = new java.sql.Date(epochMilli);
            alltypeSharding.setSqlDate(date);
            alltypeSharding.setOnlyTime(Time.valueOf("12:09:10"));
            alltypeSharding.setEnable(true);
            alltypeSharding.setValue("3322");
            alltypeSharding.setUid(UUID.randomUUID());
            alltypeSharding.setNumberFloatBasic(12.3f);
            alltypeSharding.setNumberDoubleBasic(22.1d);
            alltypeSharding.setNumberShortBasic(new Short("12"));
            alltypeSharding.setNumberIntegerBasic(33);
            alltypeSharding.setNumberLongBasic(12345678911L);
            alltypeSharding.setNumberByteBasic(new Byte("-1"));
            alltypeSharding.setEnableBasic(true);
            alltypeShardings.add(alltypeSharding);
        }
        long l = easyEntityQuery.insertable(alltypeShardings).executeRows();
        Assert.assertEquals(13, l);
        List<ALLTYPESharding> alltypes = easyEntityQuery.queryable(ALLTYPESharding.class).toList();
        Assert.assertEquals(13, alltypes.size());

        for (ALLTYPESharding alltype : alltypes) {
            int i = Integer.parseInt(alltype.getId());
            ALLTYPESharding alltypeSharding = alltypeShardings.get(i);

            Assert.assertEquals(alltype.getId(), alltypeSharding.getId());
            Assert.assertEquals(alltype.getNumberDecimal(), alltypeSharding.getNumberDecimal());
            Assert.assertEquals(alltype.getNumberFloat(), alltypeSharding.getNumberFloat());
            Assert.assertEquals(alltype.getNumberDouble(), alltypeSharding.getNumberDouble());
            Assert.assertEquals(alltype.getNumberShort(), alltypeSharding.getNumberShort());
            Assert.assertEquals(alltype.getNumberInteger(), alltypeSharding.getNumberInteger());
            Assert.assertEquals(alltype.getNumberLong(), alltypeSharding.getNumberLong());
            Assert.assertEquals(alltype.getNumberByte(), alltypeSharding.getNumberByte());
            Assert.assertEquals(alltype.getTimeLocalDateTime(), alltypeSharding.getTimeLocalDateTime());
            Assert.assertEquals(alltype.getTimeLocalDate(), alltypeSharding.getTimeLocalDate());
            Assert.assertEquals(alltype.getTimeLocalTime(), alltypeSharding.getTimeLocalTime());
            Assert.assertEquals(alltype.getEnable(), alltypeSharding.getEnable());
            Assert.assertEquals(alltype.getValue(), alltypeSharding.getValue());
            Assert.assertEquals(alltype.getUid(), alltypeSharding.getUid());
            Assert.assertEquals(alltype.getSqlDate(), alltypeSharding.getSqlDate());
            Assert.assertEquals(alltype.getOnlyDate(), alltypeSharding.getOnlyDate());
            Assert.assertEquals(alltype.getOnlyTime(), alltypeSharding.getOnlyTime());
            Assert.assertTrue(alltype.getNumberFloatBasic() == alltypeSharding.getNumberFloatBasic());
            Assert.assertTrue(alltype.getNumberDoubleBasic() == alltypeSharding.getNumberDoubleBasic());
            Assert.assertEquals(alltype.getNumberShortBasic(), alltypeSharding.getNumberShortBasic());
            Assert.assertEquals(alltype.getNumberIntegerBasic(), alltypeSharding.getNumberIntegerBasic());
            Assert.assertEquals(alltype.getNumberLongBasic(), alltypeSharding.getNumberLongBasic());
            Assert.assertEquals(alltype.getNumberByteBasic(), alltypeSharding.getNumberByteBasic());
            Assert.assertEquals(alltype.isEnableBasic(), alltypeSharding.isEnableBasic());
        }
        {

            List<ALLTYPESharding> list = easyEntityQuery.queryable(ALLTYPESharding.class).orderBy(o -> o.numberInteger().desc()).toList();
            Assert.assertEquals(13, list.size());
            int i = 12;
            for (ALLTYPESharding alltype : list) {

                ALLTYPESharding alltypeSharding = alltypeShardings.get(i);

                Assert.assertEquals(alltype.getId(), alltypeSharding.getId());
                Assert.assertEquals(alltype.getNumberDecimal(), alltypeSharding.getNumberDecimal());
                Assert.assertEquals(alltype.getNumberFloat(), alltypeSharding.getNumberFloat());
                Assert.assertEquals(alltype.getNumberDouble(), alltypeSharding.getNumberDouble());
                Assert.assertEquals(alltype.getNumberShort(), alltypeSharding.getNumberShort());
                Assert.assertEquals(alltype.getNumberInteger(), alltypeSharding.getNumberInteger());
                Assert.assertEquals(alltype.getNumberLong(), alltypeSharding.getNumberLong());
                Assert.assertEquals(alltype.getNumberByte(), alltypeSharding.getNumberByte());
                Assert.assertEquals(alltype.getTimeLocalDateTime(), alltypeSharding.getTimeLocalDateTime());
                Assert.assertEquals(alltype.getTimeLocalDate(), alltypeSharding.getTimeLocalDate());
                Assert.assertEquals(alltype.getTimeLocalTime(), alltypeSharding.getTimeLocalTime());
                Assert.assertEquals(alltype.getEnable(), alltypeSharding.getEnable());
                Assert.assertEquals(alltype.getValue(), alltypeSharding.getValue());
                Assert.assertEquals(alltype.getUid(), alltypeSharding.getUid());
                Assert.assertEquals(alltype.getSqlDate(), alltypeSharding.getSqlDate());
                Assert.assertEquals(alltype.getOnlyDate(), alltypeSharding.getOnlyDate());
                Assert.assertEquals(alltype.getOnlyTime(), alltypeSharding.getOnlyTime());
                Assert.assertTrue(alltype.getNumberFloatBasic() == alltypeSharding.getNumberFloatBasic());
                Assert.assertTrue(alltype.getNumberDoubleBasic() == alltypeSharding.getNumberDoubleBasic());
                Assert.assertEquals(alltype.getNumberShortBasic(), alltypeSharding.getNumberShortBasic());
                Assert.assertEquals(alltype.getNumberIntegerBasic(), alltypeSharding.getNumberIntegerBasic());
                Assert.assertEquals(alltype.getNumberLongBasic(), alltypeSharding.getNumberLongBasic());
                Assert.assertEquals(alltype.getNumberByteBasic(), alltypeSharding.getNumberByteBasic());
                Assert.assertEquals(alltype.isEnableBasic(), alltypeSharding.isEnableBasic());
                i--;
            }
        }
        {

            List<ALLTYPESharding> list = easyEntityQuery.queryable(ALLTYPESharding.class).orderBy(o -> o.numberInteger().desc()).limit(3, 10).toList();
            Assert.assertEquals(10, list.size());
            int i = 9;
            for (ALLTYPESharding alltype : list) {

                ALLTYPESharding alltypeSharding = alltypeShardings.get(i);

                Assert.assertEquals(alltype.getId(), alltypeSharding.getId());
                Assert.assertEquals(alltype.getNumberDecimal(), alltypeSharding.getNumberDecimal());
                Assert.assertEquals(alltype.getNumberFloat(), alltypeSharding.getNumberFloat());
                Assert.assertEquals(alltype.getNumberDouble(), alltypeSharding.getNumberDouble());
                Assert.assertEquals(alltype.getNumberShort(), alltypeSharding.getNumberShort());
                Assert.assertEquals(alltype.getNumberInteger(), alltypeSharding.getNumberInteger());
                Assert.assertEquals(alltype.getNumberLong(), alltypeSharding.getNumberLong());
                Assert.assertEquals(alltype.getNumberByte(), alltypeSharding.getNumberByte());
                Assert.assertEquals(alltype.getTimeLocalDateTime(), alltypeSharding.getTimeLocalDateTime());
                Assert.assertEquals(alltype.getTimeLocalDate(), alltypeSharding.getTimeLocalDate());
                Assert.assertEquals(alltype.getTimeLocalTime(), alltypeSharding.getTimeLocalTime());
                Assert.assertEquals(alltype.getEnable(), alltypeSharding.getEnable());
                Assert.assertEquals(alltype.getValue(), alltypeSharding.getValue());
                Assert.assertEquals(alltype.getUid(), alltypeSharding.getUid());
                Assert.assertEquals(alltype.getSqlDate(), alltypeSharding.getSqlDate());
                Assert.assertEquals(alltype.getOnlyDate(), alltypeSharding.getOnlyDate());
                Assert.assertEquals(alltype.getOnlyTime(), alltypeSharding.getOnlyTime());
                Assert.assertTrue(alltype.getNumberFloatBasic() == alltypeSharding.getNumberFloatBasic());
                Assert.assertTrue(alltype.getNumberDoubleBasic() == alltypeSharding.getNumberDoubleBasic());
                Assert.assertEquals(alltype.getNumberShortBasic(), alltypeSharding.getNumberShortBasic());
                Assert.assertEquals(alltype.getNumberIntegerBasic(), alltypeSharding.getNumberIntegerBasic());
                Assert.assertEquals(alltype.getNumberLongBasic(), alltypeSharding.getNumberLongBasic());
                Assert.assertEquals(alltype.getNumberByteBasic(), alltypeSharding.getNumberByteBasic());
                Assert.assertEquals(alltype.isEnableBasic(), alltypeSharding.isEnableBasic());
                i--;
            }
        }
//        {
//
//            List<ALLTYPESharding> list = easyEntityQuery.queryable(ALLTYPESharding.class)
//                    .groupBy(a -> GroupKeys.of(a.value()))
//                    .select(ALLTYPESharding.class, group -> Select.of(
//                            group.key1(),
//                            group.sum(s -> s.numberDecimal()),
//                            group.sum(s -> s.numberFloat()),
//                            group.sum(s -> s.numberDouble())
//                    ))
//                    .toList();
//            Assert.assertEquals(1, list.size());
//            ALLTYPESharding alltype = list.get(0);
//
//            Assert.assertNull(alltype.getId());
//            Assert.assertEquals(alltype.getNumberDecimal(), new BigDecimal("12.33").multiply(BigDecimal.valueOf(13)));
//            Assert.assertEquals(alltype.getNumberFloat(), (Float) (12.3f * 13));
//            Assert.assertEquals(alltype.getNumberDouble(), (Double) (22.1d * 13));
//        }
    }


    @Test
    public void allTypeTest1_proxy() {
        ALLTYPE alltype = new ALLTYPE();
        alltype.setId("123456");

        alltype.setNumberDecimal(new BigDecimal("12.33"));
        alltype.setNumberFloat(12.3f);
        alltype.setNumberDouble(22.1d);
        alltype.setNumberShort(new Short("12"));
        alltype.setNumberInteger(33);
        alltype.setNumberLong(12345678911L);
        alltype.setNumberByte(new Byte("-1"));
        alltype.setEnable(true);
        alltype.setTimeLocalDateTime(LocalDateTime.of(2021, 1, 1, 0, 0));
        alltype.setTimeLocalDate(LocalDate.of(2121, 1, 2));
        alltype.setTimeLocalTime(LocalTime.of(21, 1, 9));
        alltype.setOnlyDate(new Date());
        long epochMilli = LocalDateTime.now().toLocalDate().atStartOfDay()
                .toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        java.sql.Date date = new java.sql.Date(epochMilli);
        alltype.setSqlDate(date);
        alltype.setOnlyTime(Time.valueOf("12:09:10"));
        alltype.setValue("3322");
        alltype.setUid(UUID.randomUUID());
        alltype.setNumberFloatBasic(12.3f);
        alltype.setNumberDoubleBasic(22.1d);
        alltype.setNumberShortBasic(new Short("12"));
        alltype.setNumberIntegerBasic(33);
        alltype.setNumberLongBasic(12345678911L);
        alltype.setNumberByteBasic(new Byte("-1"));
        alltype.setEnableBasic(true);
        long l = easyEntityQuery.insertable(alltype).executeRows();
        Assert.assertEquals(1, l);
        ALLTYPE alltype1 = easyEntityQuery.queryable(ALLTYPE.class)
                .whereById("123456").firstOrNull();
        Assert.assertNotNull(alltype1);
        Assert.assertEquals(alltype1.getId(), alltype.getId());
        Assert.assertEquals(alltype1.getNumberDecimal(), alltype.getNumberDecimal());
        Assert.assertEquals(alltype1.getNumberFloat(), alltype.getNumberFloat());
        Assert.assertEquals(alltype1.getNumberDouble(), alltype.getNumberDouble());
        Assert.assertEquals(alltype1.getNumberShort(), alltype.getNumberShort());
        Assert.assertEquals(alltype1.getNumberInteger(), alltype.getNumberInteger());
        Assert.assertEquals(alltype1.getNumberLong(), alltype.getNumberLong());
        Assert.assertEquals(alltype1.getNumberByte(), alltype.getNumberByte());
        Assert.assertEquals(alltype1.getTimeLocalDateTime(), alltype.getTimeLocalDateTime());
        Assert.assertEquals(alltype1.getTimeLocalDate(), alltype.getTimeLocalDate());
        Assert.assertEquals(alltype1.getTimeLocalTime(), alltype.getTimeLocalTime());
        Assert.assertEquals(alltype1.getEnable(), alltype.getEnable());
        Assert.assertEquals(alltype1.getValue(), alltype.getValue());
        Assert.assertEquals(alltype1.getUid(), alltype.getUid());
        Assert.assertEquals(alltype1.getSqlDate(), alltype.getSqlDate());
        Assert.assertEquals(alltype1.getOnlyDate(), alltype.getOnlyDate());
        Assert.assertEquals(alltype1.getOnlyTime(), alltype.getOnlyTime());
        Assert.assertTrue(alltype1.getNumberFloatBasic() == alltype.getNumberFloatBasic());
        Assert.assertTrue(alltype1.getNumberDoubleBasic() == alltype.getNumberDoubleBasic());
        Assert.assertEquals(alltype1.getNumberShortBasic(), alltype.getNumberShortBasic());
        Assert.assertEquals(alltype1.getNumberIntegerBasic(), alltype.getNumberIntegerBasic());
        Assert.assertEquals(alltype1.getNumberLongBasic(), alltype.getNumberLongBasic());
        Assert.assertEquals(alltype1.getNumberByteBasic(), alltype.getNumberByteBasic());
        Assert.assertEquals(alltype1.isEnableBasic(), alltype.isEnableBasic());
    }

    @Test
    public void allTypeTest1_1_proxy() {
        ALLTYPE1 alltype = new ALLTYPE1();
        alltype.setId("1234567");

        alltype.setNumberDecimal(new BigDecimal("12.33"));
        alltype.setNumberFloat(12.3f);
        alltype.setNumberDouble(22.1d);
        alltype.setNumberShort(new Short("12"));
        alltype.setNumberInteger(33);
        alltype.setNumberLong(12345678911L);
        alltype.setNumberByte(new Byte("-1"));
        alltype.setEnable(true);
        alltype.setTimeLocalDateTime(LocalDateTime.of(2021, 1, 1, 0, 0));
        alltype.setTimeLocalDate(LocalDate.of(2121, 1, 2));
        alltype.setTimeLocalTime(LocalTime.of(21, 1, 9));
        alltype.setOnlyDate(new Date());
        long epochMilli = LocalDateTime.now().toLocalDate().atStartOfDay()
                .toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        java.sql.Date date = new java.sql.Date(epochMilli);
        alltype.setSqlDate(date);
        alltype.setOnlyTime(Time.valueOf("12:09:10"));
        alltype.setValue("3322");
        alltype.setUid(UUID.randomUUID());

        alltype.setNumberFloatBasic(12.3f);
        alltype.setNumberDoubleBasic(22.1d);
        alltype.setNumberShortBasic(new Short("12"));
        alltype.setNumberIntegerBasic(33);
        alltype.setNumberLongBasic(12345678911L);
        alltype.setNumberByteBasic(new Byte("-1"));
        alltype.setEnableBasic(true);
        long l = easyEntityQuery.insertable(alltype).executeRows();
        Assert.assertEquals(1, l);
        ALLTYPE1 alltype1 = easyEntityQuery.queryable(ALLTYPE1.class)
                .whereById("1234567").firstOrNull();
        Assert.assertNotNull(alltype1);
        Assert.assertEquals(alltype1.getId(), alltype.getId());
        Assert.assertEquals(alltype1.getNumberDecimal(), alltype.getNumberDecimal());
        Assert.assertEquals(alltype1.getNumberFloat(), alltype.getNumberFloat());
        Assert.assertEquals(alltype1.getNumberDouble(), alltype.getNumberDouble());
        Assert.assertEquals(alltype1.getNumberShort(), alltype.getNumberShort());
        Assert.assertEquals(alltype1.getNumberInteger(), alltype.getNumberInteger());
        Assert.assertEquals(alltype1.getNumberLong(), alltype.getNumberLong());
        Assert.assertEquals(alltype1.getNumberByte(), alltype.getNumberByte());
        Assert.assertEquals(alltype1.getTimeLocalDateTime(), alltype.getTimeLocalDateTime());
        Assert.assertEquals(alltype1.getTimeLocalDate(), alltype.getTimeLocalDate());
        Assert.assertEquals(alltype1.getTimeLocalTime(), alltype.getTimeLocalTime());
        Assert.assertEquals(alltype1.getEnable(), alltype.getEnable());
        Assert.assertEquals(alltype1.getValue(), alltype.getValue());
        Assert.assertEquals(alltype1.getUid(), alltype.getUid());
        Assert.assertEquals(alltype1.getSqlDate(), alltype.getSqlDate());
        Assert.assertEquals(alltype1.getOnlyDate(), alltype.getOnlyDate());
        Assert.assertEquals(alltype1.getOnlyTime(), alltype.getOnlyTime());
        Assert.assertTrue(alltype1.getNumberFloatBasic() == alltype.getNumberFloatBasic());
        Assert.assertTrue(alltype1.getNumberDoubleBasic() == alltype.getNumberDoubleBasic());
        Assert.assertEquals(alltype1.getNumberShortBasic(), alltype.getNumberShortBasic());
        Assert.assertEquals(alltype1.getNumberIntegerBasic(), alltype.getNumberIntegerBasic());
        Assert.assertEquals(alltype1.getNumberLongBasic(), alltype.getNumberLongBasic());
        Assert.assertEquals(alltype1.getNumberByteBasic(), alltype.getNumberByteBasic());
        Assert.assertEquals(alltype1.isEnableBasic(), alltype.isEnableBasic());
    }

    @Test
    public void allTypeTest1_2_proxy() {
        ALLTYPE1 alltype = new ALLTYPE1();
        alltype.setId("1235678");

        alltype.setNumberDecimal(new BigDecimal("12.33"));
        alltype.setNumberFloat(12.3f);
        alltype.setNumberDouble(22.1d);
        alltype.setNumberShort(new Short("12"));
        alltype.setNumberInteger(33);
        alltype.setNumberLong(12345678911L);
        alltype.setNumberByte(new Byte("-1"));
        alltype.setEnable(true);
        alltype.setTimeLocalDateTime(LocalDateTime.of(2021, 1, 1, 0, 0));
        alltype.setTimeLocalDate(LocalDate.of(2121, 1, 2));
        alltype.setTimeLocalTime(LocalTime.of(21, 1, 9));
        alltype.setOnlyDate(new Date());
        long epochMilli = LocalDateTime.now().toLocalDate().atStartOfDay()
                .toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        java.sql.Date date = new java.sql.Date(epochMilli);
        alltype.setSqlDate(date);
        alltype.setOnlyTime(Time.valueOf("12:09:10"));
        alltype.setValue("3322");
        alltype.setUid(UUID.randomUUID());

        alltype.setNumberFloatBasic(12.3f);
        alltype.setNumberDoubleBasic(22.1d);
        alltype.setNumberShortBasic(new Short("12"));
        alltype.setNumberIntegerBasic(33);
        alltype.setNumberLongBasic(12345678911L);
        alltype.setNumberByteBasic(new Byte("-1"));
        alltype.setEnableBasic(true);
        long l = easyEntityQuery.insertable(alltype).executeRows();
        Assert.assertEquals(1, l);
        ALLTYPEVO1 alltype1 = easyEntityQuery.queryable(ALLTYPE1.class)
                .whereById("1235678").select(ALLTYPEVO1.class).firstOrNull();
        Assert.assertNotNull(alltype1);
        Assert.assertEquals(alltype1.getId(), alltype.getId());
        Assert.assertEquals(alltype1.getNumberDecimal(), alltype.getNumberDecimal());
        Assert.assertEquals(alltype1.getNumberFloat(), alltype.getNumberFloat());
        Assert.assertEquals(alltype1.getNumberDouble(), alltype.getNumberDouble());
        Assert.assertEquals(alltype1.getNumberShort(), alltype.getNumberShort());
        Assert.assertEquals(alltype1.getNumberInteger(), alltype.getNumberInteger());
        Assert.assertEquals(alltype1.getNumberLong(), alltype.getNumberLong());
        Assert.assertEquals(alltype1.getNumberByte(), alltype.getNumberByte());
        Assert.assertEquals(alltype1.getTimeLocalDateTime(), alltype.getTimeLocalDateTime());
        Assert.assertEquals(alltype1.getTimeLocalDate(), alltype.getTimeLocalDate());
        Assert.assertEquals(alltype1.getTimeLocalTime(), alltype.getTimeLocalTime());
        Assert.assertEquals(alltype1.getEnable(), alltype.getEnable());
        Assert.assertEquals(alltype1.getValue(), alltype.getValue());
        Assert.assertEquals(alltype1.getUid(), alltype.getUid());
        Assert.assertEquals(alltype1.getSqlDate(), alltype.getSqlDate());
        Assert.assertEquals(alltype1.getOnlyDate(), alltype.getOnlyDate());
        Assert.assertEquals(alltype1.getOnlyTime(), alltype.getOnlyTime());
        Assert.assertTrue(alltype1.getNumberFloatBasic() == alltype.getNumberFloatBasic());
        Assert.assertTrue(alltype1.getNumberDoubleBasic() == alltype.getNumberDoubleBasic());
        Assert.assertEquals(alltype1.getNumberShortBasic(), alltype.getNumberShortBasic());
        Assert.assertEquals(alltype1.getNumberIntegerBasic(), alltype.getNumberIntegerBasic());
        Assert.assertEquals(alltype1.getNumberLongBasic(), alltype.getNumberLongBasic());
        Assert.assertEquals(alltype1.getNumberByteBasic(), alltype.getNumberByteBasic());
        Assert.assertEquals(alltype1.isEnableBasic(), alltype.isEnableBasic());
    }

    @Test
    public void conditionTest1() {
        String id = "";
        String userName = null;
        String nickname = "BBB";
        Boolean leftEnable = true;

        {

            String sql = easyEntityQuery.queryable(DefTable.class)
                    .leftJoin(DefTableLeft1.class, (t, t1) -> t.id().eq(t1.defId()))
                    .filterConfigure((t, p, v) -> {
                        if ("id".equals(p)) {
                            return true;
                        }
                        return NotNullOrEmptyValueFilter.DEFAULT.accept(t, p, v);
                    })
                    .where((t, t1) -> {
                        t.id().eq(id);
                        t.userName().eq(userName);
                        t.nickname().eq(nickname);
                        t1.enable().eq(leftEnable);
                    }).toSQL();
            Assert.assertEquals("SELECT t.id,t.user_name,t.nickname,t.enable,t.score,t.mobile,t.avatar,t.number,t.status,t.created,t.options FROM t_def_table t LEFT JOIN t_def_table_left1 t1 ON t.id = t1.def_id WHERE t.id = ? AND t.nickname = ? AND t1.enable = ?", sql);
        }

        {

            String sql = easyEntityQuery.queryable(DefTable.class)
                    .leftJoin(DefTableLeft1.class, (t, t1) -> t.id().eq(t1.defId()))
                    .filterConfigure((t, p, v) -> {
                        if ("enable".equals(p)) {
                            return false;
                        }
                        return NotNullOrEmptyValueFilter.DEFAULT.accept(t, p, v);
                    })
                    .where((t, t1) -> {
                        t.id().eq(id);
                        t.userName().eq(userName);
                        t.nickname().eq(nickname);
                        t1.enable().eq(leftEnable);
                    }).toSQL();
            Assert.assertEquals("SELECT t.id,t.user_name,t.nickname,t.enable,t.score,t.mobile,t.avatar,t.number,t.status,t.created,t.options FROM t_def_table t LEFT JOIN t_def_table_left1 t1 ON t.id = t1.def_id WHERE t.nickname = ?", sql);
        }
    }


}
