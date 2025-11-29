package com.easy.query.test.mssql;

import com.easy.query.api.proxy.base.MapTypeProxy;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.key.MapKey;
import com.easy.query.api.proxy.key.MapKeys;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft4;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.BigDecimalUtils;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mssql.entity.MsSQLCalc;
import com.easy.query.test.mssql.entity.MsSQLMyTopic;
import com.easy.query.test.mssql.entity.MsSQLMyTopic1;
import com.easy.query.test.mssql.entity.proxy.MsSQLCalcProxy;
import com.easy.query.test.mssql.entity.proxy.MsSQLMyTopic1Proxy;
import com.easy.query.test.mssql.entity.proxy.MsSQLMyTopicProxy;
import com.easy.query.test.mysql8.entity.bank.SysBankCard;
import com.easy.query.test.mysql8.entity.bank.SysUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * create time 2023/7/27 17:34
 * 文件说明
 *
 * @author xuejiaming
 */
public class MsSQLQueryTest extends MsSQLBaseTest {
    @Before
    public void beforeTest() {
        {

            DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(MsSQLCalc.class));
            codeFirstCommand.executeWithTransaction(s -> s.commit());
        }
        {

            DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.dropTableCommand(Arrays.asList(MsSQLCalc.class));
            codeFirstCommand.executeWithTransaction(s -> s.commit());
        }
        {

            DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(MsSQLCalc.class));
            codeFirstCommand.executeWithTransaction(s -> s.commit());
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
                .where(o -> o.id().eq("1"));
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
                .where(o -> o.Id().eq("1"));
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
                .orderBy(o -> o.id().asc())
                .toPageResult(2, 20);
        List<MsSQLMyTopic> data = topicPageResult.getData();
        Assert.assertEquals(20, data.size());
    }

    @Test
    public void query6() {

        EasyPageResult<MsSQLMyTopic> topicPageResult = entityQuery
                .queryable(MsSQLMyTopic.class)
                .where(o -> o.id().isNotNull())
                .orderBy(o -> o.stars().asc())
                .toPageResult(2, 20);
        List<MsSQLMyTopic> data = topicPageResult.getData();
        Assert.assertEquals(20, data.size());
        for (int i = 0; i < 20; i++) {
            MsSQLMyTopic msSQLMyTopic = data.get(i);
            Assert.assertEquals(msSQLMyTopic.getId(), String.valueOf(i + 20));
            Assert.assertEquals(msSQLMyTopic.getStars(), (Integer) (i + 120));
        }
    }

    @Test
    public void query7() {

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
    public void query8() {

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
    public void query9() {
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
    public void query10() {

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
    public void query11() {

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
    public void update1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        entityQuery.updatable(MsSQLMyTopic.class)
                .setColumns(m -> m.title().set("123xx"))
                .where(m -> {
                    m.id().isNull();
                    m.expression().exists(
                            entityQuery.queryable(MsSQLMyTopic.class)
                                    .where(m1 -> {
                                        m1.id().eq(m.id());
                                    })
                    );
                }).executeRows();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE t SET t.[Title] = ? FROM [MyTopic] t WHERE t.[Id] IS NULL AND EXISTS (SELECT 1 FROM [MyTopic] t1 WHERE t1.[Id] = t.[Id])", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123xx(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testLikeConcat1() {

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
    public void testLikeConcat2() {

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
    public void testLikeConcat3() {

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
    public void testBigDecimal() {
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
                Assert.assertEquals(0, i);
                System.out.println(bigDecimalDraft1.getValue1());
            }
        } finally {

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
                Assert.assertEquals(0, i);
                System.out.println(bigDecimalDraft1.getColumn1());
            }
        } finally {

            entityQuery.deletable(MsSQLCalc.class).allowDeleteStatement(true).whereById("1").executeRows();
        }
    }

    @Test
    public void testBigDecima4() {
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
        } finally {

            entityQuery.deletable(MsSQLCalc.class).allowDeleteStatement(true).whereById("1").executeRows();
        }
    }

    @Test
    public void testBatchInsert() {
        try {

            entityQuery.deletable(MsSQLCalc.class).allowDeleteStatement(true).whereByIds(Arrays.asList("2", "3", "4", "5")).executeRows();
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
        } finally {
            entityQuery.deletable(MsSQLCalc.class).allowDeleteStatement(true).whereByIds(Arrays.asList("2", "3", "4", "5")).executeRows();
        }
    }

    @Test
    public void testFormat() {
//        entityQuery.queryable(MsSQLMyTopic.class);

        String formater = "yyyy年MM-01 HH:mm分ss秒";
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<LocalDateTime, String>> list = entityQuery.queryable(MsSQLMyTopic.class)
                .select(d -> Select.DRAFT.of(
                        d.createTime(),
                        d.createTime().format(formater)
                )).toList();
        Assert.assertFalse(list.isEmpty());
        for (Draft2<LocalDateTime, String> timeAndFormat : list) {
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
    public void concat1() {
        List<Map> maps = entityQuery.sqlQuery("select CONCAT('a', null , 'b') as [aa] ", Map.class);
        System.out.println(maps);
        Object o = maps.get(0).get("aa");
        Assert.assertEquals("ab", o);
    }

    @Test
    public void testMaxColumns() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<MsSQLCalc> list = entityQuery.queryable(MsSQLCalc.class)
                .where(m -> {
                    m.expression().maxColumns(m.column1(), m.column2(), m.column3()).eq(BigDecimal.valueOf(1));
                    m.expression().maxColumns(m.column1(), m.column2(), m.column3()).eq(BigDecimal.valueOf(2));
                }).toList();
        listenerContextManager.clear();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT [Id],[Column1],[Column2],[Column3] FROM [t_calc] WHERE (SELECT MAX(__col__) FROM (VALUES ([Column1]), ([Column2]), ([Column3])) AS t(__col__)) = ? AND (SELECT MAX(__col__) FROM (VALUES ([Column1]), ([Column2]), ([Column3])) AS t(__col__)) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(BigDecimal),2(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void testMaxColumns2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft4<BigDecimal, BigDecimal, BigDecimal, BigDecimal>> list = entityQuery.queryable(MsSQLCalc.class)
                .select(m -> Select.DRAFT.of(
                        m.column1(),
                        m.column2(),
                        m.column3(),
                        m.expression().maxColumns(m.column1(), m.column2(), m.column3())
                )).toList();
        listenerContextManager.clear();
        for (Draft4<BigDecimal, BigDecimal, BigDecimal, BigDecimal> item : list) {
            BigDecimal max = BigDecimalUtils.max(item.getValue1(), item.getValue2(), item.getValue3());
            Assert.assertEquals(0, max.compareTo(item.getValue4()));
        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.[Column1] AS [Value1],t.[Column2] AS [Value2],t.[Column3] AS [Value3],(SELECT MAX(__col__) FROM (VALUES (t.[Column1]), (t.[Column2]), (t.[Column3])) AS t(__col__)) AS [Value4] FROM [t_calc] t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("1(BigDecimal),2(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }


    @Test
    public void testAll() {
        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<SysUser> list = entityQuery.queryable(SysUser.class)
                .includes(user -> user.bankCards())
                .where(user -> {
                    user.bankCards().where(bc -> bc.type().eq("储蓄卡")).all(bc -> bc.code().startsWith("33123"));
                }).toList();
        int size = list.size();
        Assert.assertEquals(1, size);
        List<SysUser> newCards = list.stream().filter(user -> {
            //因为null记录不会被like返回所以直接过滤null
            return user.getBankCards().stream().filter(o -> Objects.equals(o.getType(), "储蓄卡") && o.getCode() != null).allMatch(o -> o.getCode().startsWith("33123"));
        }).collect(Collectors.toList());
        Assert.assertEquals(1, newCards.size());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.[Id],t.[Name],t.[Phone],t.[Age],t.[CreateTime] FROM [t_sys_user] t WHERE NOT ( EXISTS (SELECT TOP 1 1 FROM [t_bank_card] t1 WHERE t1.[Uid] = t.[Id] AND t1.[Type] = ? AND (NOT (t1.[Code] LIKE (CAST(? AS NVARCHAR(MAX))+'%')))))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("储蓄卡(String),33123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.[Id],t.[Uid],t.[Code],t.[Type],t.[BankId],t.[OpenTime] FROM [t_bank_card] t WHERE t.[Uid] IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("u2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        listenerContextManager.clear();
    }

    @Test
    public void testAll1() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<SysUser> list = entityQuery.queryable(SysUser.class)
                .includes(user -> user.bankCards())
                .where(user -> {
                    user.bankCards().where(bc -> bc.type().eq("储蓄卡")).all(bc -> bc.code().nullOrDefault("").startsWith("33123"));
                }).toList();
        int size = list.size();
        Assert.assertEquals(1, size);
        List<SysUser> newCards = list.stream().filter(user -> {
            return user.getBankCards().stream().filter(o -> Objects.equals(o.getType(), "储蓄卡")).allMatch(o -> (o.getCode() == null ? "" : o.getCode()).startsWith("33123"));
        }).collect(Collectors.toList());
        Assert.assertEquals(1, newCards.size());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.[Id],t.[Name],t.[Phone],t.[Age],t.[CreateTime] FROM [t_sys_user] t WHERE NOT ( EXISTS (SELECT TOP 1 1 FROM [t_bank_card] t1 WHERE t1.[Uid] = t.[Id] AND t1.[Type] = ? AND (NOT (ISNULL(t1.[Code],?) LIKE (CAST(? AS NVARCHAR(MAX))+'%')))))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("储蓄卡(String),(String),33123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.[Id],t.[Uid],t.[Code],t.[Type],t.[BankId],t.[OpenTime] FROM [t_bank_card] t WHERE t.[Uid] IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("u2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        listenerContextManager.clear();
    }

    @Test
    public void testAll2() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<SysUser> list = entityQuery.queryable(SysUser.class)
                .includes(user -> user.bankCards())
                .where(user -> {
                    user.bankCards().where(bc -> bc.type().eq("储蓄卡")).all(bc -> {
                        bc.code().startsWith("33123");
                        bc.code().startsWith("45678");
                    });
                }).toList();
        int size = list.size();
        Assert.assertEquals(1, size);
        List<SysUser> newCards = list.stream().filter(user -> {
            //因为null记录不会被like返回所以直接过滤null
            return user.getBankCards().stream().filter(o -> Objects.equals(o.getType(), "储蓄卡") && o.getCode() != null).allMatch(o -> o.getCode().startsWith("33123") && o.getCode().startsWith("45678"));
        }).collect(Collectors.toList());
        Assert.assertEquals(1, newCards.size());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.[Id],t.[Name],t.[Phone],t.[Age],t.[CreateTime] FROM [t_sys_user] t WHERE NOT ( EXISTS (SELECT TOP 1 1 FROM [t_bank_card] t1 WHERE t1.[Uid] = t.[Id] AND t1.[Type] = ? AND (NOT (t1.[Code] LIKE (CAST(? AS NVARCHAR(MAX))+'%') AND t1.[Code] LIKE (CAST(? AS NVARCHAR(MAX))+'%')))))", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("储蓄卡(String),33123(String),45678(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.[Id],t.[Uid],t.[Code],t.[Type],t.[BankId],t.[OpenTime] FROM [t_bank_card] t WHERE t.[Uid] IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("u2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        listenerContextManager.clear();
    }

    @Test
    public void testAllGroupJoin() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<SysUser> list = entityQuery.queryable(SysUser.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .includes(user -> user.bankCards())
                .where(user -> {
                    user.bankCards().where(bc -> bc.type().eq("储蓄卡")).all(bc -> bc.code().startsWith("33123"));
                }).toList();
        int size = list.size();
        Assert.assertEquals(1, size);
        List<SysUser> newCards = list.stream().filter(user -> {
            //因为null记录不会被like返回所以直接过滤null
            return user.getBankCards().stream().filter(o -> Objects.equals(o.getType(), "储蓄卡") && o.getCode() != null).allMatch(o -> o.getCode().startsWith("33123"));
        }).collect(Collectors.toList());
        Assert.assertEquals(1, newCards.size());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.[Id],t.[Name],t.[Phone],t.[Age],t.[CreateTime] FROM [t_sys_user] t LEFT JOIN (SELECT t1.[Uid] AS [__group_key1__],(CASE WHEN (COUNT(?) <= 0) THEN ? ELSE ? END) AS [__none2__] FROM [t_bank_card] t1 WHERE t1.[Type] = ? AND (NOT (t1.[Code] LIKE (CAST(? AS NVARCHAR(MAX))+'%'))) GROUP BY t1.[Uid]) t2 ON t2.[__group_key1__] = t.[Id] WHERE ISNULL(t2.[__none2__],?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(Integer),true(Boolean),false(Boolean),储蓄卡(String),33123(String),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.[Id],t.[Uid],t.[Code],t.[Type],t.[BankId],t.[OpenTime] FROM [t_bank_card] t WHERE t.[Uid] IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("u2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        listenerContextManager.clear();
    }

    @Test
    public void testAllGroupJoin2() {

        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<SysUser> list = entityQuery.queryable(SysUser.class)
                .configure(s -> s.getBehavior().add(EasyBehaviorEnum.ALL_SUB_QUERY_GROUP_JOIN))
                .includes(user -> user.bankCards())
                .where(user -> {
                    user.bankCards().where(bc -> bc.type().eq("储蓄卡")).all(bc -> {
                        bc.code().startsWith("33123");
                        bc.id().startsWith("45678");
                    });
                }).toList();

        int size = list.size();
        Assert.assertEquals(1, size);
        List<SysUser> newCards = list.stream().filter(user -> {
            //因为null记录不会被like返回所以直接过滤null
            return user.getBankCards().stream().filter(o -> Objects.equals(o.getType(), "储蓄卡") && o.getCode() != null).allMatch(o -> o.getCode().startsWith("33123") && o.getCode().startsWith("45678"));
        }).collect(Collectors.toList());
        Assert.assertEquals(1, newCards.size());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.[Id],t.[Name],t.[Phone],t.[Age],t.[CreateTime] FROM [t_sys_user] t LEFT JOIN (SELECT t1.[Uid] AS [__group_key1__],(CASE WHEN (COUNT(?) <= 0) THEN ? ELSE ? END) AS [__none2__] FROM [t_bank_card] t1 WHERE t1.[Type] = ? AND (NOT (t1.[Code] LIKE (CAST(? AS NVARCHAR(MAX))+'%') AND t1.[Id] LIKE (CAST(? AS NVARCHAR(MAX))+'%'))) GROUP BY t1.[Uid]) t2 ON t2.[__group_key1__] = t.[Id] WHERE ISNULL(t2.[__none2__],?) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(Integer),true(Boolean),false(Boolean),储蓄卡(String),33123(String),45678(String),true(Boolean),true(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.[Id],t.[Uid],t.[Code],t.[Type],t.[BankId],t.[OpenTime] FROM [t_bank_card] t WHERE t.[Uid] IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("u2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        listenerContextManager.clear();
    }


    @Test
    public void testWithNolock1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysBankCard> list1 = entityQuery.queryable(SysBankCard.class)
                .where(bank_card -> {
                    bank_card.configure(s -> s.asTableSegment(WITHNOLOCK.DEFAULT));
                    bank_card.user().configure(s -> s.asTableSegment(WITHNOLOCK.DEFAULT));
                    bank_card.user().name().like("123");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.[Id],t.[Uid],t.[Code],t.[Type],t.[BankId],t.[OpenTime] FROM [t_bank_card] t WITH(NOLOCK) LEFT JOIN [t_sys_user] t1 WITH(NOLOCK) ON t1.[Id] = t.[Uid] WHERE t1.[Name] LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testWithNolock2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<SysUser> list = entityQuery.queryable(SysUser.class)
                .asTableSegment(WITHNOLOCK.DEFAULT)
                .leftJoin(SysBankCard.class, (user, bank_card) -> user.id().eq(bank_card.uid()))
                .asTableSegment(WITHNOLOCK.DEFAULT)
                .toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.[Id],t.[Name],t.[Phone],t.[Age],t.[CreateTime] FROM [t_sys_user] t WITH(NOLOCK) LEFT JOIN [t_bank_card] t1 WITH(NOLOCK) ON t.[Id] = t1.[Uid]", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testWithIndex() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Exception e=null;
        try {

            List<SysUser> list = entityQuery.queryable(SysUser.class)
                    .asTableSegment(TABLEINDEX.of("myIndex"))
                    .leftJoin(SysBankCard.class, (user, bank_card) -> user.id().eq(bank_card.uid()))
                    .toList();
        } catch (Exception ex){
            e=ex;
        }
        Assert.assertNotNull( e);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.[Id],t.[Name],t.[Phone],t.[Age],t.[CreateTime] FROM [t_sys_user] t WITH(INDEX(myIndex)) LEFT JOIN [t_bank_card] t1 ON t.[Id] = t1.[Uid]", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }


    public static class WITHNOLOCK implements BiFunction<String, String, String> {
        public static final WITHNOLOCK DEFAULT = new WITHNOLOCK();

        @Override
        public String apply(String table, String alias) {
            if (alias == null) {
                return table + " WITH(NOLOCK)";
            }
            return table + " " + alias + " WITH(NOLOCK)";
        }
    }

    public static class TABLEINDEX implements BiFunction<String, String, String> {
        private final String indexName;

        private TABLEINDEX(String indexName) {
            if (indexName == null) {
                throw new IllegalArgumentException("indexName can not be null");
            }
            this.indexName = indexName;
        }

        public static TABLEINDEX of(String indexName) {
            return new TABLEINDEX(indexName);
        }

        @Override
        public String apply(String table, String alias) {
            if (alias == null) {
                return table + " WITH(INDEX(" + indexName + "))";
            }
            return table + " " + alias + " WITH(INDEX(" + indexName + "))";
        }
    }

}
