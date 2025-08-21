package com.easy.query.test.h2.v2;

import com.easy.query.test.h2.domain.ALLTYPE;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

/**
 * create time 2025/8/21 21:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class H2V2Test extends H2V2BaseTest{
    @Test
    public void testAllType(){

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
}
