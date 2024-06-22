package com.easy.query.test.lambdaquerytest;

import com.alibaba.fastjson2.JSON;
import com.easy.query.api.lambda.crud.read.LQuery;
import com.easy.query.api.lambda.crud.read.LQuery2;
import com.easy.query.api.lambda.crud.read.LQuery3;
import com.easy.query.api.lambda.crud.read.LQuery4;
import com.easy.query.api.lambda.sqlext.SqlFunctions;
import com.easy.query.api.lambda.sqlext.SqlTypes;
import com.easy.query.core.lambda.common.TempResult;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.h2.domain.*;
import com.easy.query.test.vo.BlogEntityVO1;
import com.easy.query.test.vo.BlogEntityVO2;
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

@SuppressWarnings("all")
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

//    // 展示用
//    @Deprecated
//    public void display1()
//    {
//        // 创建一个可查询SysUser的表达式
//        LQuery<SysUser> queryable = elq.queryable(SysUser.class);
//
//        // 单个条件链式查询
//        List<SysUser> sysUsers1 = elq.queryable(SysUser.class)
//                // sql: id = "123xxx"
//                .where(o -> o.getId() == "123xxx")
//                .toList();// toList表示查询结果集
//
//
//        // == 运算符会被翻译成sql中的 = 运算符，&& 运算符会被翻译成sql中的 AND 运算符 ，字符串的contains方法翻译成sql中的 LIKE 运算符
//        List<SysUser> sysUsers2 = elq.queryable(SysUser.class)
//                // sql: id = "123xxx" AND idCard LIKE "%123%"
//                .where(o -> o.getId() == "123xxx" && o.getIdCard().contains("123"))
//                .toList();
//
//
//        // 多个where之间默认进行 AND 连接
//        List<SysUser> sysUsers3 = elq.queryable(SysUser.class)
//                // sql: id = "123xxx"
//                .where(o -> o.getId() == "123xxx")
//                // AND
//                // sql: idCard LIKE "%123%"
//                .where(o -> o.getIdCard().contains("123"))
//                .toList();
//
//
//        //返回单个对象没有查询到就返回null
//        SysUser sysUser1 = elq.queryable(SysUser.class)
//                .where(o -> o.getId() == "123xxx")
//                .where(o -> o.getIdCard().contains("123"))
//                .firstOrNull();
//
//
//        //采用创建时间倒序和id正序查询返回第一个
//        SysUser sysUser2 = elq.queryable(SysUser.class)
//                // sql: id = "123xxx" AND idCard LIKE "%123%" ORDER BY createTime,id
//                .where(o -> o.getId() == "123xxx")
//                .where(o -> o.getIdCard().contains("123"))
//                .orderBy(o -> o.getCreateTime())
//                .orderBy(o -> o.getId())
//                .firstOrNull();
//
//        //仅查询id和createTime两列，支持多种自由返回，请往下看
//        LQuery<SysUser> sysUserLQuery = elq.queryable(SysUser.class)
//                .where(o -> o.getId() == "123xxx")
//                .where(o -> o.getIdCard().contains("123"))
//                .orderBy(o -> o.getCreateTime())
//                .orderBy(o -> o.getId());
//
//        // 匿名类形式返回
//        List<? extends TempResult> list = sysUserLQuery
//                // sql: select id AS tid,createTime AS ct
//                .select(s -> new TempResult()
//                {
//                    String tid = s.getId();
//                    LocalDateTime ct = s.getCreateTime();
//                })
//                .toList();
//
//        // 指定类型返回，自动与类字段映射，支持dto
//        // 此时会select全字段
//        SysUser sysUser3 = sysUserLQuery.select(SysUser.class).firstOrNull();
//
//        // select无参时等同于条件为queryable时填入的class
//        SysUser sysUser4 = sysUserLQuery.select().firstOrNull();
//
//
//        // 指定类型同时限制sql选择的字段
//        SysUser sysUser5 = sysUserLQuery
//                .select(s ->
//                {
//                    // sql: select id,createTime
//                    SysUser temp = new SysUser();
//                    temp.setId(s.getId());
//                    temp.setCreateTime(s.getCreateTime());
//                    return temp;
//                }).firstOrNull();
//    }
//
//    @Deprecated
//    public void display2()
//    {
//        // 创建一个可查询SysUser的表达式
//        LQuery<SysUser> queryable = elq.queryable(SysUser.class);
//
//
//        List<Topic> list = elq
//                .queryable(Topic.class)
//                //第一个join采用双参数,参数1表示第一张表Topic 参数2表示第二张表 BlogEntity
//                // sql: LEFT JOIN BlogEntity ON t.id = t1.id
//                .leftJoin(BlogEntity.class, (t, t1) -> t.getId() == t1.getId())
//                //第二个join采用三参数,参数1表示第一张表Topic 参数2表示第二张表 BlogEntity 第三个参数表示第三张表 SysUser
//                // sql: LEFT JOIN SysUser ON t.id = t2.id
//                .leftJoin(SysUser.class, (t, t1, t2) -> t.getId() == t2.getId())
//                // && 运算符会被翻译成sql中的 AND 运算符，同理 || 运算符等同于 OR 运算符
//                // sql: WHERE t.id = "123" AND t1.title LIKE "%456%" AND t2.createTime = NOW()
//                .where((t, t1, t2) -> t.getId() == "123"
//                        && t1.getTitle().contains("456")
//                        && t2.getCreateTime().isEqual(SqlFunctions.now())
//                )
//                //toList默认只查询主表数据
//                .toList();
//    }
//
//    @Deprecated
//    public void display3()
//    {
//        // 创建一个可查询SysUser的表达式
//        LQuery<SysUser> queryable = elq.queryable(SysUser.class);
//
//
//        LQuery3<Topic, BlogEntity, SysUser> topicBlogEntitySysUserLQuery = elq
//                .queryable(Topic.class)
//                .leftJoin(BlogEntity.class, (t, t1) -> t.getId() == t1.getId())
//                .leftJoin(SysUser.class, (t, t1, t2) -> t.getId() == t2.getId())
//                .where((t, t1, t2) -> t.getId() == "123"
//                        && t1.getTitle().contains("456")
//                        && t2.getCreateTime().isEqual(SqlFunctions.now())
//                );
//
//
//        // 1. 声明对象并且调用setter进行赋值的写法，支持所有对象，包括各种vo，dto
//        LQuery<AnyYouWant> select1 = topicBlogEntitySysUserLQuery.select((t, t1, t2) ->
//        {
//            AnyYouWant want = new AnyYouWant();
//            want.setF1(t.getId());// 选择t表的id字段，映射到java对象的f1字段
//            want.setF2(t1.getTitle());// 选择t1表的title字段，映射到java对象的f2字段
//            want.setF3(SqlFunctions.cast(t2.getCreateTime(), SqlTypes.varChar()));// 选择t2表的star字段，映射到java对象的f3字段,因为类型不同，所有调用了sql的cast函数进行了类型转换
//            return want;
//        });
//        // 注意这个时候的返回类型是AnyYouWant，具体的返回类型由你的代码决定
//        List<AnyYouWant> list = select1.toList();
//
//
//        // 2. （推荐）当无需对查询结果进行后续操作时，推荐使用匿名对象，可以直接返回给前端使用
//        LQuery<? extends TempResult> select2 = topicBlogEntitySysUserLQuery.select((t, t1, t2) -> new TempResult()
//        {
//            String f1 = t.getId();// 选择t表的id字段，映射到匿名对象的f1字段
//            String f2 = t1.getTitle();// 选择t1表的title字段，映射到匿名对象的f2字段
//            LocalDateTime f3 = t2.getCreateTime();// 选择t3表的createTime字段，映射到匿名对象的f3字段
//        });
//        // 注意这个时候的返回类型是匿名TempResult类，具体的返回类型由你的代码决定
//        List<? extends TempResult> list1 = select2.toList();
//
//        // 3. 直接传入类对象，此时会自动进行sql字段与java对象字段的映射
//        LQuery<TopicType> select3 = topicBlogEntitySysUserLQuery.select(TopicType.class);
//        // 注意这个时候的返回类型是匿名TopicType类，具体的返回类型由你的代码决定
//        List<TopicType> list2 = select3.toList();
//    }
//
//    @Deprecated
//    public void display4()
//    {
//        //根据条件查询表中的第一条记录
//        List<Topic> topics = elq
//                .queryable(Topic.class)
//                .limit(1)
//                .toList();
//
//
//        //根据条件查询表中的第一条记录,查不到就返回空对象
//        Topic topic1 = easyEntityQuery
//                .queryable(Topic.class)
//                .firstOrNull();
//
//
//        //根据条件查询id为3的记录,查不到就返回空对象
//        Topic topic2 = elq
//                .queryable(Topic.class)
//                .where(o -> o.getId() == "3")
//                .firstNotNull();
//
//        //根据条件查询id为3同时title匹配"%3%"的记录,查不到就返回空对象
//        Topic topic3 = elq
//                .queryable(Topic.class)
//                .where(o -> o.getId() == "3" && o.getTitle().contains("3"))
//                .firstOrNull();
//    }
//
//    @Deprecated
//    public void display5()
//    {
//        Topic topic = elq
//                .queryable(Topic.class)
//                .leftJoin(BlogEntity.class, (t, b) -> t.getId() == b.getId())
//                .where((t, b) -> t.getId() == "3")
//                .firstOrNull();
//
//        List<BlogEntity> blogEntities = elq
//                .queryable(Topic.class)
//                //join 后面是多参数委托,第一个主表,第二个参数为join表
//                .innerJoin(BlogEntity.class, (t, b) -> t.getId() == b.getId())
//                .where((t, b) -> t.getTitle() != null && b.getId() == "3")
//                //这里我们需要返回BlogEntity列表，所以直接传入BlogEntity
//                .select(BlogEntity.class)
//                .toList();
//
//    }
//
//    @Deprecated
//    public void display6()
//    {
//        LQuery<Topic> lQuery = elq
//                .queryable(Topic.class)
//                .where(o -> o.getId() == "3");
//        //SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM t_topic t WHERE t.`id` = ?
//        display7();
//        List<BlogEntity> blogEntities = elq
//                .queryable(BlogEntity.class)
//                .leftJoin(lQuery, (a, b) -> a.getId() == b.getId())
//                .where((a, b) -> a.getId() != null && b.getId() != null)
//                .toList();
//    }
//
//    @Deprecated
//    public void display7()
//    {
//        // 不指定select字段的场合，此时会自动将从BlogEntity表查询的结果映射到BlogEntityTest对象上
//        List<BlogEntityTest> list1 = elq.queryable(BlogEntity.class)
//                .select(BlogEntityTest.class).toList();
//
//
//        // 想要指定select字段的场合
//        // 1.使用匿名对象
//        String sql1 = elq.queryable(BlogEntity.class)
//                .where(o -> o.getId() == "2")
//                .select(o -> new TempResult()
//                {
//                    BigDecimal score = o.getOrder();//将查询的order映射到匿名对象的score上
//                    // 填写更多你想要的字段{var a = o.getXxx()}
//                })
//                .limit(1).toSQL();
//
//        // 2. 使用java类型
//        String sql2 = elq.queryable(BlogEntity.class)
//                .where(o -> o.getId() == "2")
//                .select(o ->
//                {
//                    BlogEntity blogEntity = new BlogEntity();
//                    blogEntity.setScore(o.getOrder());//将查询的order映射到java对象的score上
//                    // 填写更多你想要的字段{obj.setXxx(o.getXxx())}
//                    return blogEntity;
//                })
//                .limit(1).toSQL();
//
//        // 两者等价
//        Assert.assertEquals("SELECT t.`order` AS `score` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? LIMIT 1", sql1);
//        Assert.assertEquals("SELECT t.`order` AS `score` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? LIMIT 1", sql2);
//    }
//
//    @Deprecated
//    public void display8()
//    {
//        List<String> list1 = elq.queryable(Topic.class)
//                .where(f -> f.getId() == "1")
//                .select(s -> s.getId())
//                .toList();
//
//        List<Integer> list2 = elq.queryable(Topic.class)
//                .where(f -> f.getId() == "1")
//                .select(s -> s.getStars())
//                .toList();
//
//        List<Map<String, Object>> list3 = elq.queryable(Topic.class)
//                .where(f -> f.getId() == "1")
//                .toMaps();
//    }
//
//    @Deprecated
//    public void display9()
//    {
//        List<TopicGroupTestDTO> topicGroupTestDTOS = elq.queryable(Topic.class)
//                .where(o -> o.getId() == "3")
//                // 这里相当于声明了一个有一个元素(id)的组(group)
//                // 支持声明多个元素
//                .groupBy(o -> new Grouper()
//                {
//                    String id = o.getId();
//                    //int num = o.getStars();
//                })
//                // 出于安全考虑，groupBy之后想要tolist就必须显示的select字段
//                .select(g ->
//                {
//                    TopicGroupTestDTO result = new TopicGroupTestDTO();
//                    // 选择了Topic表的id字段
//                    // 注意此时g.key.id代表了Topic的id字段，以此类推group多个字段时相当于g.key.xxx,等同于你在上面new Grouper(){...}时声明的字段
//                    result.setId(g.key.id);
//                    // 选择了Topic表的id字段,并且套了一层count聚合函数
//                    result.setIdCount((int) g.count(s -> s.getId()));
//                    // 选择了Topic表的id字段,并且套了一层min聚合函数
//                    result.setIdMin(g.min(s -> s.getId()));
//                    return result;
//                })
//                // 显然你可以使用匿名类，上下相等
//                //.select(g -> new TempResult()
//                //{
//                //    String id = g.key;
//                //    long idCount = g.count(s -> s.getId());
//                //    String idMin = g.min(s -> s.getId());
//                //})
//                .toList();
//    }
//
//    @Deprecated
//    public void display10()
//    {
//        List<TopicGroupTestDTO> topicGroupTestDTOS = elq.queryable(Topic.class)
//                .where(o -> o.getId() == "3")
//                // 单个元素(id)的组(group)
//                .groupBy(o -> o.getId())
//                // 出于安全考虑，groupBy之后想要tolist就必须显示的select字段
//                .select(g ->
//                {
//                    TopicGroupTestDTO result = new TopicGroupTestDTO();
//                    // 选择了Topic表的id字段
//                    // 注意此时g.key代表了Topic的id字段，因为只有一个对象，所以不需要g.key.xxx
//                    result.setId(g.key);
//                    // 选择了Topic表的id字段,并且套了一层count聚合函数
//                    result.setIdCount((int) g.count(s -> s.getId()));
//                    // 选择了Topic表的id字段,并且套了一层min聚合函数
//                    result.setIdMin(g.min(s -> s.getId()));
//                    return result;
//                })
//                .toList();
//    }
//
//    @Deprecated
//    public void display11()
//    {
////        List<BlogGroupIdAndName> list = easyEntityQuery.queryable(Topic.class)
////                .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
////                .where((t, b2) ->
////                {
////                    t.title().isNotNull();
////                    t.createTime().le(LocalDateTime.of(2021, 3, 4, 5, 6));
////                })
////                .groupBy((t1, b2) -> GroupKeys.TABLE2.of(t1.id(), b2.star()))
////                .select(group -> new BlogGroupIdAndNameProxy()
////                        .id().set(group.key1())
////                        .idCount().set(group.groupTable().t2.id().count())
////                ).toList();
//
//        List<BlogGroupIdAndName> list = elq.queryable(Topic.class)
//                .leftJoin(BlogEntity.class, (t, b2) -> t.getId() == b2.getId())
//                .where((t, b2) -> t.getTitle() != null
//                        && (t.getCreateTime().isEqual(LocalDateTime.of(2021, 3, 4, 5, 6))
//                        || t.getCreateTime().isBefore(LocalDateTime.of(2021, 3, 4, 5, 6))
//                ))
//                // 选择了两个元素的group
//                .groupBy((t1, b2) -> new Grouper()
//                {
//                    String k1 = t1.getId();
//                    int k2 = b2.getStar();
//                })
//                .select(group ->
//                {
//                    BlogGroupIdAndName blogGroupIdAndName = new BlogGroupIdAndName();
//                    // group的k1(Topic表的id字段)映射到BlogGroupIdAndName类的id字段
//                    blogGroupIdAndName.setId(group.key.k1);
//                    // group的k2(COUNT(BlogEntity的id字段))映射到BlogGroupIdAndName类的idCount字段
//                    blogGroupIdAndName.setIdCount(group.count((t1, t2) -> t2.getId()));
//                    return blogGroupIdAndName;
//                }).toList();
//    }

    public void display12()
    {
        String sql1 = elq.queryable(DefTable.class).orderBy(a -> a.getId(), false).toSQL();
        System.out.println(sql1);
        String sql2 = elq.queryable(DefTable.class).leftJoin(DefTableLeft1.class, (t, t1) -> t.getId() == t1.getDefId())
                .orderBy((a, b) -> a.getId(), false).toSQL();
        System.out.println(sql2);

        List<Topic> list1 = elq.queryable(Topic.class)
                .orderBy(t -> t.getId())
                .toList();

        List<Topic> list2 = elq.queryable(Topic.class)
                .orderBy(t -> t.getId())
                // 反向排序
                .orderBy(t -> t.getCreateTime(), false)
                .toList();

        LQuery<Topic> queryable = elq.queryable(Topic.class);

        // 后续考虑加入orderByIf
        if (false)
        {
            queryable.orderBy(t -> t.getId());
        }

        queryable.orderBy(t -> t.getCreateTime(), false);
        List<Topic> list4 = queryable.toList();

        List<Topic> list5 = elq.queryable(Topic.class)
                .orderBy(t -> SqlFunctions.dateFormat(t.getCreateTime(), "%Y-%m-%d"))
                .toList();

        //直接映射到BlogEntityVO1.class
        elq.queryable(BlogEntity.class)
                .where(o -> o.getId() == "2")
                .select(BlogEntityVO1.class).firstOrNull();

        //只查询id和title,映射到logEntityVO2.class
        elq.queryable(BlogEntity.class)
                .where(o -> o.getId() == "2")
                .select(s ->
                {
                    BlogEntityVO2 vo2 = new BlogEntityVO2();
                    vo2.setId(s.getId());
                    vo2.setTitle(s.getTitle());
                    return vo2;
                })
                .firstOrNull();

        //只查询id和title,映射到匿名类
        elq.queryable(BlogEntity.class)
                .where(o -> o.getId() == "2")
                .select(s -> new TempResult()
                {
                    String id = s.getId();
                    String tt = s.getTitle();
                })
                .firstOrNull();


        List<Topic> list = elq.queryable(BlogEntity.class)
                .where(b -> b.getStar() > 1)
                //对其group by
                .groupBy(b -> b.getId())
                //生成中间对象并且变成匿名表(每次select都是生成匿名表,后续如果没有别的操作那么匿名表会被展开)
                // select * from (select blogId,blogCount from xxx group by id) t
                //如果select后续没有非终结操作那么会被展开为 select blogId,blogCount from xxx group by id
                .select(g -> new TempResult()
                {
                    String blogId = g.key;
                    long blogCount = g.count();
                })
                //对匿名表进行join
                .leftJoin(Topic.class, (g, topic) -> g.blogId == topic.getId())
                .where((g, topic) -> g.blogCount <= 123)
                //再次生成匿名表
                .select((g, topic) -> topic)
                //后续无操作了所以会被展开
                .toList();

    }
}
