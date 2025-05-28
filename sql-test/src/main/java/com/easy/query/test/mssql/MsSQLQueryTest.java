package com.easy.query.test.mssql;

import com.easy.query.api.proxy.base.MapTypeProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.key.MapKey;
import com.easy.query.api.proxy.key.MapKeys;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mssql.entity.MsSQLCalc;
import com.easy.query.test.mssql.entity.MsSQLMyTopic;
import com.easy.query.test.mssql.entity.MsSQLMyTopic1;
import com.easy.query.test.mssql.entity.proxy.MsSQLCalcProxy;
import com.easy.query.test.mssql.entity.proxy.MsSQLMyTopic1Proxy;
import com.easy.query.test.mssql.entity.proxy.MsSQLMyTopicProxy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * create time 2023/7/27 17:34
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLQueryTest extends MsSQLBaseTest{
    @Before
    public void beforeTest(){
        {

            DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(MsSQLCalc.class));
            codeFirstCommand.executeWithTransaction(s->s.commit());
        }
        {

            DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.dropTableCommand(Arrays.asList(MsSQLCalc.class));
            codeFirstCommand.executeWithTransaction(s->s.commit());
        }
        {

            DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(MsSQLCalc.class));
            codeFirstCommand.executeWithTransaction(s->s.commit());
        }
    }

    @Test
    public void query0() {
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
    public void query4() {

        EasyPageResult<MsSQLMyTopic> topicPageResult = entityQuery
                .queryable(MsSQLMyTopic.class)
                .where(o -> o.id().isNotNull())
                .toPageResult(2, 20);
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
    public void query6() {

        EasyPageResult<MsSQLMyTopic> topicPageResult = entityQuery
                .queryable(MsSQLMyTopic.class)
                .where(o -> o.id().isNotNull())
                .orderBy(o->o.stars().asc())
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
    public void query7(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);



        List<MsSQLMyTopic> list = entityQuery.queryable(MsSQLMyTopic.class)
                .where(o -> {
                    o.title().compareTo("1").eq(1);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT [Id],[Stars],[Title],[CreateTime] FROM [MyTopic] WHERE (CASE WHEN [Title] = ? THEN 0 WHEN [Title] > ? THEN 1 ELSE -1 END) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String),1(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void query8(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);



        List<MsSQLMyTopic> list = entityQuery.queryable(MsSQLMyTopic.class)
                .where(o -> {
                    o.title().compareTo(o.id()).eq(1);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT [Id],[Stars],[Title],[CreateTime] FROM [MyTopic] WHERE (CASE WHEN [Title] = [Id] THEN 0 WHEN [Title] > [Id] THEN 1 ELSE -1 END) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void query9(){
//        String sql = entityQuery.queryable("select * from t_order", Map.class)
//                .limit(10, 20).toSQL();
//        {
//
//            List<Map> list = entityQuery.queryable("select * from t_order", Map.class)
//                    .limit(10,20).toList();
//        }

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft2<String, String>> list = entityQuery.queryable(MsSQLMyTopic.class)
                .groupBy(o -> GroupKeys.of(o.title()))
                .select(o -> Select.DRAFT.of(
                        o.key1(),
                        o.groupTable().id().joining(",")
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.[Title] AS [Value1],STRING_AGG(t.[Id], ?) AS [Value2] FROM [MyTopic] t GROUP BY t.[Title]", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(",(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void query10(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MsSQLMyTopic> aa = entityQuery.queryable(MsSQLMyTopic.class)
                .where(m -> {
                    m.createTime().format("yyyy年MM月dd日").eq("2022年01月01日");
                }).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT [Id],[Stars],[Title],[CreateTime] FROM [MyTopic] WHERE (SUBSTRING(CONVERT(CHAR(8), [CreateTime], 112), 1, 4) + ? + SUBSTRING(CONVERT(CHAR(6), [CreateTime], 12), 3, 2) + ? + SUBSTRING(CONVERT(CHAR(6), [CreateTime], 12), 5, 2) + ?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("年(String),月(String),日(String),2022年01月01日(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void query11(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MsSQLMyTopic> aa = entityQuery.queryable(MsSQLMyTopic.class)
                .where(m -> {
                    m.createTime().format("yyyy年MM月dd日 HH时mm分ss秒").eq("2022年01月01日 01时01分01秒");
                }).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT [Id],[Stars],[Title],[CreateTime] FROM [MyTopic] WHERE (SUBSTRING(CONVERT(CHAR(8), [CreateTime], 112), 1, 4) + ? + SUBSTRING(CONVERT(CHAR(6), [CreateTime], 12), 3, 2) + ? + SUBSTRING(CONVERT(CHAR(6), [CreateTime], 12), 5, 2) + ? + SUBSTRING(CONVERT(CHAR(8), [CreateTime], 24), 1, 2) + ? + SUBSTRING(CONVERT(CHAR(8), [CreateTime], 24), 4, 2) + ? + SUBSTRING(CONVERT(CHAR(8), [CreateTime], 24), 7, 2) + ?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("年(String),月(String),日 (String),时(String),分(String),秒(String),2022年01月01日 01时01分01秒(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void update1(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        entityQuery.updatable(MsSQLMyTopic.class)
                .setColumns(m -> m.title().set("123xx"))
                .where(m -> {
                    m.id().isNull();
                    m.expression().exists(()->{
                        return entityQuery.queryable(MsSQLMyTopic.class)
                                .where(m1 -> {m1.id().eq(m.id());});
                    });
                }).executeRows();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE t SET t.[Title] = ? FROM [MyTopic] t WHERE t.[Id] IS NULL AND EXISTS (SELECT 1 FROM [MyTopic] t1 WHERE t1.[Id] = t.[Id])", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123xx(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
     public void testLikeConcat1(){

//        List<Map<String, Object>> maps = entityQuery.sqlQueryMap("SELECT [Id],[Stars],[Title],[CreateTime] FROM [MyTopic] WHERE [Title] LIKE ('%'+(CAST(? + [Id] + ? AS NVARCHAR(MAX))+'%')");




        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<MsSQLMyTopic> list = entityQuery.queryable(MsSQLMyTopic.class)
                .where(m -> {
                    m.title().like(
                            m.expression().concat(s -> {
                                s.value("%");
                                s.expression(m.id());
                                s.value("%");
                            })
                    );
//                    m.title().likeMatchLeft(
//                            m.expression().concat(s -> {
//                                s.value("%");
//                                s.expression(m.id());
//                                s.value("%");
//                            })
//                    );
//                    m.title().likeMatchRight(
//                            m.expression().concat(s -> {
//                                s.value("%");
//                                s.expression(m.id());
//                                s.value("%");
//                            })
//                    );
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT [Id],[Stars],[Title],[CreateTime] FROM [MyTopic] WHERE [Title] LIKE ('%'+CAST(CONCAT(?,[Id],?) AS NVARCHAR(MAX))+'%')", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%(String),%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
     public void testLikeConcat2(){

//        List<Map<String, Object>> maps = entityQuery.sqlQueryMap("SELECT [Id],[Stars],[Title],[CreateTime] FROM [MyTopic] WHERE [Title] LIKE ('%'+(CAST(? + [Id] + ? AS NVARCHAR(MAX))+'%')");




        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<MsSQLMyTopic> list = entityQuery.queryable(MsSQLMyTopic.class)
                .where(m -> {
//                    m.title().like(
//                            m.expression().concat(s -> {
//                                s.value("%");
//                                s.expression(m.id());
//                                s.value("%");
//                            })
//                    );
                    m.title().likeMatchLeft(
                            m.expression().concat(s -> {
                                s.value("%");
                                s.expression(m.id());
                                s.value("%");
                            })
                    );
//                    m.title().likeMatchRight(
//                            m.expression().concat(s -> {
//                                s.value("%");
//                                s.expression(m.id());
//                                s.value("%");
//                            })
//                    );
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT [Id],[Stars],[Title],[CreateTime] FROM [MyTopic] WHERE [Title] LIKE (CAST(CONCAT(?,[Id],?) AS NVARCHAR(MAX))+'%')", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%(String),%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
     public void testLikeConcat3(){

//        List<Map<String, Object>> maps = entityQuery.sqlQueryMap("SELECT [Id],[Stars],[Title],[CreateTime] FROM [MyTopic] WHERE [Title] LIKE ('%'+(CAST(? + [Id] + ? AS NVARCHAR(MAX))+'%')");




        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<MsSQLMyTopic> list = entityQuery.queryable(MsSQLMyTopic.class)
                .where(m -> {
//                    m.title().like(
//                            m.expression().concat(s -> {
//                                s.value("%");
//                                s.expression(m.id());
//                                s.value("%");
//                            })
//                    );
//                    m.title().likeMatchLeft(
//                            m.expression().concat(s -> {
//                                s.value("%");
//                                s.expression(m.id());
//                                s.value("%");
//                            })
//                    );
                    m.title().likeMatchRight(
                            m.expression().concat(s -> {
                                s.value("%");
                                s.expression(m.id());
                                s.value("%");
                            })
                    );
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT [Id],[Stars],[Title],[CreateTime] FROM [MyTopic] WHERE [Title] LIKE ('%'+CAST(CONCAT(?,[Id],?) AS NVARCHAR(MAX)))", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%(String),%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }


    @Test
    public void testBigDecimal(){
        try {

            entityQuery.deletable(MsSQLCalc.class).allowDeleteStatement(true).whereById("1").executeRows();
            MsSQLCalc msSQLCalc = new MsSQLCalc();
            msSQLCalc.setId("1");
            msSQLCalc.setColumn1(new BigDecimal("-8.87"));
            msSQLCalc.setColumn2(new BigDecimal("-1.15"));
            msSQLCalc.setColumn3(new BigDecimal("1.0000"));
            entityQuery.insertable(msSQLCalc).executeRows();

            List<Draft1<BigDecimal>> list = entityQuery.queryable(MsSQLCalc.class)
                    .select(m -> Select.DRAFT.of(
                            m.expression().caseWhen(() -> {
                                m.id().isNull();
                            }).then(1).elseEnd(m.column1().add(m.column2()).divide(m.column3()), BigDecimal.class)
                    )).toList();
            for (Draft1<BigDecimal> bigDecimalDraft1 : list) {
                int i = new BigDecimal("-10.0200000000000000000").compareTo(bigDecimalDraft1.getValue1());
                Assert.assertEquals(0,i);
                System.out.println(bigDecimalDraft1.getValue1());
            }
        }finally {

            entityQuery.deletable(MsSQLCalc.class).allowDeleteStatement(true).whereById("1").executeRows();
        }
    }
    @Test
    public void testBigDecima2() throws SQLException {
        try {

            entityQuery.deletable(MsSQLCalc.class).allowDeleteStatement(true).whereById("1").executeRows();
            MsSQLCalc msSQLCalc = new MsSQLCalc();
            msSQLCalc.setId("1");
            msSQLCalc.setColumn1(new BigDecimal("-8.87"));
            msSQLCalc.setColumn2(new BigDecimal("-1.15"));
            msSQLCalc.setColumn3(new BigDecimal("1.0000"));
            entityQuery.insertable(msSQLCalc).executeRows();

//
//            try(Connection connection = dataSource.getConnection();
//                PreparedStatement preparedStatement = connection.prepareStatement("SELECT (CASE WHEN t.[Id] IS NULL THEN ? ELSE ((t.[Column1] + t.[Column2]) / t.[Column3]) END) AS [Value1] FROM [t_calc] t");){
//
//                preparedStatement.setBigDecimal(1,BigDecimal.ZERO);
////                preparedStatement.setBigDecimal(1,new BigDecimal("0.0000"));
//                ResultSet resultSet = preparedStatement.executeQuery();
//                boolean next = resultSet.next();
//                BigDecimal bigDecimal = resultSet.getBigDecimal(1);
//                System.out.println(bigDecimal);
//            }
//
//            try(Connection connection = dataSource.getConnection()){
//
//                PreparedStatement preparedStatement = connection.prepareStatement("SELECT (CASE WHEN t.[Id] IS NULL THEN ? ELSE ((t.[Column1] + t.[Column2]) / t.[Column3]) END) AS [Value1] FROM [t_calc] t");
//                preparedStatement.setInt(1,0);
//                ResultSet resultSet = preparedStatement.executeQuery();
//                boolean next = resultSet.next();
//                BigDecimal bigDecimal = resultSet.getBigDecimal(1);
//                System.out.println(bigDecimal);
//            }


            List<MsSQLCalc> list = entityQuery.queryable(MsSQLCalc.class)
                    .select(m -> new MsSQLCalcProxy()
                            .column1().set(
                                    m.expression().caseWhen(() -> {
                                        m.id().isNull();
                                    }).then(BigDecimal.ZERO).elseEnd(m.column1().add(m.column2()).divide(m.column3()), BigDecimal.class)
                            )).toList();
            for (MsSQLCalc bigDecimalDraft1 : list) {
                int i = new BigDecimal("-10.0200000000000000000").compareTo(bigDecimalDraft1.getColumn1());
                Assert.assertEquals(0,i);
                System.out.println(bigDecimalDraft1.getColumn1());
            }
        }finally {

            entityQuery.deletable(MsSQLCalc.class).allowDeleteStatement(true).whereById("1").executeRows();
        }
    }
    @Test
    public void testBigDecima4(){
        try {

            entityQuery.deletable(MsSQLCalc.class).allowDeleteStatement(true).whereById("1").executeRows();
            MsSQLCalc msSQLCalc = new MsSQLCalc();
            msSQLCalc.setId("1");
            msSQLCalc.setColumn1(new BigDecimal("-8.87"));
            msSQLCalc.setColumn2(new BigDecimal("-1.15"));
            msSQLCalc.setColumn3(new BigDecimal("1.0000"));
            entityQuery.insertable(msSQLCalc).executeRows();

            MapKey<BigDecimal> aa = MapKeys.bigDecimalKey("aa");
            MapKey<String> bb = MapKeys.stringKey("bb");

            EntityQueryable<MapTypeProxy, Map<String, Object>> select = entityQuery.queryable(MsSQLCalc.class)
                    .select(m -> new MapTypeProxy()
                            .put(aa, m.column1())
                            .put(bb, m.id())
                    );
            List<MsSQLCalc> list = entityQuery.queryable(MsSQLCalc.class)
                    .leftJoin(select, (m, o2) -> m.column1().eq(o2.get(aa)))
                    .where((a, b) -> {
                        b.get(bb).eq(a.id());
                    }).toList();
        }finally {

            entityQuery.deletable(MsSQLCalc.class).allowDeleteStatement(true).whereById("1").executeRows();
        }
    }

    @Test
    public  void testBatchInsert(){
        try {

            entityQuery.deletable(MsSQLCalc.class).allowDeleteStatement(true).whereByIds(Arrays.asList("2","3","4","5")).executeRows();
            ArrayList<MsSQLCalc> msSQLCalcs = new ArrayList<>();
            {

                MsSQLCalc msSQLCalc = new MsSQLCalc();
                msSQLCalc.setId("2");
                msSQLCalc.setColumn1(new BigDecimal("-8.87"));
                msSQLCalc.setColumn2(new BigDecimal("-1.15"));
                msSQLCalc.setColumn3(new BigDecimal("1.10"));
                msSQLCalcs.add(msSQLCalc);
            }
            {

                MsSQLCalc msSQLCalc = new MsSQLCalc();
                msSQLCalc.setId("3");
                msSQLCalc.setColumn1(new BigDecimal("-8.87"));
                msSQLCalc.setColumn2(new BigDecimal("-1.15"));
                msSQLCalc.setColumn3(new BigDecimal("1.0010"));
                msSQLCalcs.add(msSQLCalc);
            }
            {

                MsSQLCalc msSQLCalc = new MsSQLCalc();
                msSQLCalc.setId("4");
                msSQLCalc.setColumn1(new BigDecimal("-8.87"));
                msSQLCalc.setColumn2(new BigDecimal("-1.15"));
                msSQLCalc.setColumn3(new BigDecimal("1.234"));
                msSQLCalcs.add(msSQLCalc);
            }
            {

                MsSQLCalc msSQLCalc = new MsSQLCalc();
                msSQLCalc.setId("5");
                msSQLCalc.setColumn1(new BigDecimal("-8.87"));
                msSQLCalc.setColumn2(new BigDecimal("-1.15"));
                msSQLCalc.setColumn3(new BigDecimal("1.0000"));
                msSQLCalcs.add(msSQLCalc);
            }
            entityQuery.insertable(msSQLCalcs).batch().executeRows();
            List<MsSQLCalc> list = entityQuery.queryable(MsSQLCalc.class).toList();
            for (MsSQLCalc msSQLCalc : list) {

                System.out.println(msSQLCalc);
            }
        }finally {
            entityQuery.deletable(MsSQLCalc.class).allowDeleteStatement(true).whereByIds(Arrays.asList("2","3","4","5")).executeRows();
        }
    }

    @Test
    public void testFormat(){
//        entityQuery.queryable(MsSQLMyTopic.class);

        String formater="yyyy年MM-01 HH:mm分ss秒";
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<LocalDateTime,String>> list = entityQuery.queryable(MsSQLMyTopic.class)
                .select(d -> Select.DRAFT.of(
                        d.createTime(),
                        d.createTime().format(formater)
                )).toList();
        Assert.assertFalse(list.isEmpty());
        for (Draft2<LocalDateTime,String> timeAndFormat : list) {
            LocalDateTime value1 = timeAndFormat.getValue1();
            String format = value1.format(DateTimeFormatter.ofPattern(formater));
            Assert.assertEquals(format, timeAndFormat.getValue2());
        }

        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.[CreateTime] AS [Value1],(SUBSTRING(CONVERT(CHAR(8), t.[CreateTime], 112), 1, 4) + ? + SUBSTRING(CONVERT(CHAR(6), t.[CreateTime], 12), 3, 2) + ? + SUBSTRING(CONVERT(CHAR(8), t.[CreateTime], 24), 1, 2) + ? + SUBSTRING(CONVERT(CHAR(8), t.[CreateTime], 24), 4, 2) + ? + SUBSTRING(CONVERT(CHAR(8), t.[CreateTime], 24), 7, 2) + ?) AS [Value2] FROM [MyTopic] t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("年(String),-01 (String),:(String),分(String),秒(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void concat1(){
        List<Map> maps = entityQuery.sqlQuery("select CONCAT('a', null , 'b') as [aa] ", Map.class);
        System.out.println(maps);
        Object o = maps.get(0).get("aa");
        Assert.assertEquals("ab",o);
    }
}
