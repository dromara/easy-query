package com.easy.query.test.lambdaquerytest;

import com.alibaba.fastjson2.JSON;
import com.easy.query.api.lambda.crud.read.LQuery;
import com.easy.query.api.lambda.crud.read.LQuery2;
import com.easy.query.api.lambda.crud.read.LQuery3;
import com.easy.query.api.lambda.crud.read.LQuery4;
import com.easy.query.api.lambda.sqlext.SqlFunctions;
import com.easy.query.api.lambda.sqlext.SqlTypes;
import com.easy.query.core.lambda.common.TempResult;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.h2.domain.*;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class LambdaQueryTest extends LambdaBaseTest
{

    @Test
    public void leftJoin1()
    {
        LQuery2<DefTable, DefTableLeft1> where = elq.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.getId() == t1.getDefId())
                .where((t, t1) -> t.getId() == "1" && t1.getId() == "1");
        List<DefTable> list = where.toList();
        Assert.assertEquals(1, list.size());
        String sql = where.toSQL();
        Assert.assertEquals("SELECT t.id,t.user_name,t.nickname,t.enable,t.score,t.mobile,t.avatar,t.number,t.status,t.created,t.options FROM t_def_table t LEFT JOIN t_def_table_left1 t1 ON t.id = t1.def_id WHERE t.id = ? AND t1.id = ?", sql);
    }

    @Test
    public void leftJoin2()
    {
        LQuery3<DefTable, DefTableLeft1, DefTableLeft2> where = elq.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.getId() == t1.getDefId())
                .leftJoin(DefTableLeft2.class, (t, t1, t2) -> t.getId() == t2.getDef1Id())
                .where((a, b, c) -> a.getId() == "1" && b.getId() == "1" && c.getId() == "1");
        List<DefTable> list = where.toList();
        Assert.assertEquals(1, list.size());
        String sql = where.toSQL();
        Assert.assertEquals("SELECT t.id,t.user_name,t.nickname,t.enable,t.score,t.mobile,t.avatar,t.number,t.status,t.created,t.options FROM t_def_table t LEFT JOIN t_def_table_left1 t1 ON t.id = t1.def_id LEFT JOIN t_def_table_left2 t2 ON t.id = t2.def1_id WHERE t.id = ? AND t1.id = ? AND t2.id = ?", sql);
    }

    @Test
    public void leftJoin3()
    {
        LQuery3<DefTable, DefTableLeft1, DefTableLeft3> where = elq.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.getId() == t1.getDefId())
                .leftJoin(DefTableLeft3.class, (t, t1, t2) -> t.getId() == t2.getDef2Id())
                .where((a, b, c) -> a.getId() == "1" && b.getId() == "1" && c.getId() == "1");
        List<DefTable> list = where.toList();
        Assert.assertEquals(1, list.size());
        String sql = where.toSQL();
        Assert.assertEquals("SELECT t.id,t.user_name,t.nickname,t.enable,t.score,t.mobile,t.avatar,t.number,t.status,t.created,t.options FROM t_def_table t LEFT JOIN t_def_table_left1 t1 ON t.id = t1.def_id LEFT JOIN t_def_table_left3 t2 ON t.id = t2.def2_id WHERE t.id = ? AND t1.id = ? AND t2.id = ?", sql);
    }

    @Test
    public void leftJoin4()
    {
        LQuery4<DefTable, DefTableLeft1, DefTableLeft2, DefTableLeft3> where = elq.queryable(DefTable.class)
                .leftJoin(DefTableLeft1.class, (t, t1) -> t.getId() == t1.getDefId())
                .leftJoin(DefTableLeft2.class, (t, t1, t2) -> t.getId() == t2.getDef1Id())
                .leftJoin(DefTableLeft3.class, (t, t1, t2, t3) -> t.getId() == t3.getDef2Id())
                .where((a, b, c, d) -> a.getId() == "1" && b.getId() == "1" && c.getId() == "1" && d.getId() == "1");
        List<DefTable> list = where.toList();
        Assert.assertEquals(1, list.size());
        String sql = where.toSQL();
        Assert.assertEquals("SELECT t.id,t.user_name,t.nickname,t.enable,t.score,t.mobile,t.avatar,t.number,t.status,t.created,t.options FROM t_def_table t LEFT JOIN t_def_table_left1 t1 ON t.id = t1.def_id LEFT JOIN t_def_table_left2 t2 ON t.id = t2.def1_id LEFT JOIN t_def_table_left3 t3 ON t.id = t3.def2_id WHERE t.id = ? AND t1.id = ? AND t2.id = ? AND t3.id = ?", sql);
    }

    @Test
    public void allTypeTest1()
    {
        ALLTYPE alltype = new ALLTYPE();
        alltype.setId("1234");

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
        long l = elq.insertable(alltype).executeRows();
        Assert.assertEquals(1, l);
        ALLTYPE alltype1 = elq.queryable(ALLTYPE.class)
                .where(a -> a.getId() == "1234").firstOrNull();
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
    public void q1()
    {
        LQuery<DefTable> where = elq.queryable(DefTable.class).where(d -> d.getNumber() > 800);
        String sql = where.toSQL();
        Assert.assertEquals("SELECT id,user_name,nickname,enable,score,mobile,avatar,number,status,created,options FROM t_def_table WHERE number > ?", sql);
        List<DefTable> list = where.toList();
        Assert.assertEquals(199, list.size());
    }

    @Test
    public void q2()
    {
        LQuery<? extends TempResult> select = elq.queryable(DefTable.class)
                .where(d -> d.getId() == "1")
                .select(s -> new TempResult()
                {
                    int num = s.getNumber();
                    String name = s.getUserName();
                });
        Assert.assertEquals("SELECT t.number AS num,t.user_name AS name FROM t_def_table t WHERE t.id = ?", select.toSQL());
        TempResult tempResult = select.firstOrNull();
        Assert.assertEquals("{\"name\":\"username1\",\"num\":1}", JSON.toJSONString(tempResult));
    }

    @Test
    public void q3()
    {
        LQuery<DefTable> select = elq.queryable(DefTable.class)
                .where(d -> d.getId() != "50")
                .select(s ->
                {
                    DefTable defTable = new DefTable();
                    defTable.setId(s.getId());
                    defTable.setEnable(s.getEnable());
                    defTable.setAvatar(s.getAvatar());
                    defTable.setMobile(s.getMobile());
                    defTable.setCreated(s.getCreated());
                    defTable.setNickname(s.getNickname());
                    defTable.setNumber(s.getNumber());
                    defTable.setScore(s.getScore());
                    defTable.setUserName(s.getUserName());
                    defTable.setOptions(s.getOptions());
                    defTable.setStatus(s.getStatus());
                    return defTable;
                });
        String sql = select.toSQL();
        Assert.assertEquals("SELECT t.id,t.enable,t.avatar,t.mobile,t.created,t.nickname,t.number,t.score,t.user_name,t.options,t.status FROM t_def_table t WHERE t.id <> ?", sql);
        DefTable defTable = select.firstOrNull();
        Assert.assertEquals("DefTable(id=0, userName=username0, nickname=nickname0, enable=true, score=0.10, mobile=1330, avatar=http://www.0.com, number=0, status=0, created=2020-01-01T00:00, options=null)", defTable.toString());
    }

    @Test
    public void q4()
    {
        LQuery<DefTable> where = elq.queryable(DefTable.class)
                .where(d -> d.getNumber() > Integer.MAX_VALUE && d.getNumber() < Integer.MIN_VALUE || 1 == 1);
        String sql = where.toSQL();
        Assert.assertEquals("SELECT id,user_name,nickname,enable,score,mobile,avatar,number,status,created,options FROM t_def_table WHERE number > ? AND number < ? OR ? = ?", sql);
        DefTable defTable = where.firstOrNull();
        Assert.assertEquals("DefTable(id=0, userName=username0, nickname=nickname0, enable=true, score=0.10, mobile=1330, avatar=http://www.0.com, number=0, status=0, created=2020-01-01T00:00, options=null)", defTable.toString());
    }

    @Test
    public void q5()
    {
        LQuery<? extends TempResult> select = elq.queryable(DefTable.class)
                .where(a -> a.getNumber() == 800)
                .select(s -> new TempResult()
                {
                    int id = SqlFunctions.cast(s.getId(), SqlTypes.signed());
                    int year = SqlFunctions.getYear(s.getCreated());
                    String userName = s.getUserName();
                });
        String sql = select.toSQL();
        Assert.assertEquals("SELECT CAST(t.id AS SIGNED) AS id,YEAR(t.created) AS year,t.user_name FROM t_def_table t WHERE t.number = ?", sql);
        List<? extends TempResult> list = select.toList();
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void q6()
    {
        long DefTableCount = elq.queryable(DefTable.class).select(s -> SqlFunctions.count()).firstOrNull();
        long DefTable1Count = elq.queryable(DefTableLeft1.class).select(s -> SqlFunctions.count(1)).firstOrNull();
        long DefTable2Count = elq.queryable(DefTableLeft2.class).select(s -> SqlFunctions.count(1)).firstOrNull();
        long DefTable3Count = elq.queryable(DefTableLeft3.class).select(s -> SqlFunctions.count(1)).firstOrNull();
        Assert.assertEquals(DefTableCount, DefTable1Count);
        Assert.assertEquals(DefTableCount, DefTable2Count);
        Assert.assertEquals(DefTableCount, DefTable3Count);
    }

    @Test
    public void q7()
    {
        DefTable defTable = elq.queryable(DefTable.class).select().firstOrNull();
        Assert.assertNotNull(defTable);
    }

    @Test
    public void q8()
    {
        LQuery<? extends TempResult> select = elq.queryable(DefTable.class)
                .groupBy(g -> new Object()
                {
                    int pp = SqlFunctions.cast(g.getId(), SqlTypes.signed());
                    boolean kk = g.getEnable();
                })
                .having(h -> h.key.pp == 110)
                .select(s -> new TempResult()
                {
                    int c1 = s.key.pp;
                    boolean b1 = s.key.kk;
                });
        String sql = select.toSQL();
        Assert.assertEquals("SELECT CAST(t.id AS SIGNED) AS c1,t.enable AS b1 FROM t_def_table t GROUP BY CAST(t.id AS SIGNED),t.enable HAVING CAST(t.id AS SIGNED) = ?", sql);
        List<? extends TempResult> list = select.toList();
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void q9()
    {
        LQuery<? extends TempResult> select = elq.queryable(DefTable.class)
                .groupBy(g -> SqlFunctions.cast(g.getId(), SqlTypes.signed()))
                .having(h -> h.key == 110)
                .select(s -> new TempResult()
                {
                    int c1 = s.key;
                    long b1 = s.count(b -> b.getNickname());
                });
        String sql = select.toSQL();
        Assert.assertEquals("SELECT CAST(t.id AS SIGNED) AS c1,count(t.nickname) AS b1 FROM t_def_table t GROUP BY CAST(t.id AS SIGNED) HAVING CAST(t.id AS SIGNED) = ?", sql);
        List<? extends TempResult> list = select.toList();
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void q10()
    {
        LQuery<DefTable> select = elq.queryable(DefTable.class)
                .where(w -> w.getNumber() % 2 == 0)
                .select(DefTable.class);

        String sql = select.toSQL();
        Assert.assertEquals("SELECT t.id,t.user_name,t.nickname,t.enable,t.score,t.mobile,t.avatar,t.number,t.status,t.created,t.options FROM t_def_table t WHERE t.number % ? = ?", sql);
        List<DefTable> list = select.toList();
        Assert.assertEquals(500, list.size());
    }

    // 展示用
    @Deprecated
    public void display()
    {
        // 创建一个可查询SysUser的表达式
        LQuery<SysUser> queryable = elq.queryable(SysUser.class);

        // 单个条件链式查询
        List<SysUser> sysUsers1 = elq.queryable(SysUser.class)
                .where(o -> o.getId() == "123xxx")
                .toList();// toList表示查询结果集


        // == 运算符会被翻译成sql中的 = 运算符，&& 运算符会被翻译成sql中的 AND 运算符 ，字符串的contains方法翻译成sql中的 LIKE 运算符
        List<SysUser> sysUsers2 = elq.queryable(SysUser.class)
                .where(o -> o.getId() == "123xxx" && o.getIdCard().contains("123"))
                .toList();


        // 多个where之间默认进行 AND 连接
        List<SysUser> sysUsers3 = elq.queryable(SysUser.class)
                .where(o -> o.getId() == "123xxx")
                // AND
                .where(o -> o.getIdCard().contains("123"))
                .toList();


        //返回单个对象没有查询到就返回null
        SysUser sysUser1 = elq.queryable(SysUser.class)
                .where(o -> o.getId() == "123xxx")
                .where(o -> o.getIdCard().contains("123"))
                .firstOrNull();


        //采用创建时间倒序和id正序查询返回第一个
        SysUser sysUser2 = elq.queryable(SysUser.class)
                .where(o -> o.getId() == "123xxx")
                .where(o -> o.getIdCard().contains("123"))
                .orderBy(o -> o.getCreateTime())
                .orderBy(o -> o.getId())
                .firstOrNull();

        //仅查询id和createTime两列
        LQuery<SysUser> sysUserLQuery = elq.queryable(SysUser.class)
                .where(o -> o.getId() == "123xxx")
                .where(o -> o.getIdCard().contains("123"))
                .orderBy(o -> o.getCreateTime())
                .orderBy(o -> o.getId());

        // 匿名类形式返回
        List<? extends TempResult> list = sysUserLQuery
                .select(s -> new TempResult()
                {
                    String tid = s.getId();
                    LocalDateTime ct = s.getCreateTime();
                })
                .toList();

        // 指定类型返回，自动与类字段映射，支持dto
        // 此时会select全字段
        SysUser sysUser3 = sysUserLQuery.select(SysUser.class).firstOrNull();

        // select无参时等同于条件为queryable时填入的class
        SysUser sysUser4 = sysUserLQuery.select().firstOrNull();


        // 指定类型同时限制sql选择的字段
        SysUser sysUser5 = sysUserLQuery
                .select(s ->
                {
                    SysUser temp = new SysUser();
                    temp.setId(s.getId());
                    temp.setCreateTime(s.getCreateTime());
                    //此时选择了SysUser的id和createTime字段
                    return temp;
                }).firstOrNull();
    }
}
