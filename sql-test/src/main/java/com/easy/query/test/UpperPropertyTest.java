package com.easy.query.test;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.configuration.bean.PropertyDescriptorMatcher;
import com.easy.query.core.configuration.bean.entity.EntityPropertyDescriptorMatcher;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.easy.query.test.common.LowerUnderlinedNameConversion;
import com.easy.query.test.common.MyQueryConfiguration;
import com.easy.query.test.entity.MyALLTYPE;
import com.easy.query.test.entity.UpperTopic;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import com.easy.query.test.testvo.MyTopicTestDTO;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * create time 2024/11/1 14:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class UpperPropertyTest {
    public static HikariDataSource dataSource;
    public static ListenerContextManager listenerContextManager;
    public static EasyQueryClient easyQueryClient;
    public static DefaultEasyEntityQuery easyEntityQuery;
    static {
//        EasyBeanUtil.FAST_BEAN_FUNCTION = ReflectBean::new;
        LogFactory.useStdOutLogging();
        init();


    }
    public static void init() {
        initDatasource();
        initEasyQuery();
    }

    public static void initDatasource() {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setMaximumPoolSize(20);

//        postgres://postgres:postgrespw@localhost:55000
    }

    public static void initEasyQuery() {
        listenerContextManager = new ListenerContextManager();
        MyJdbcListener myJdbcListener = new MyJdbcListener(listenerContextManager);
        easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                })
//                .replaceService(Column2MapKeyConversion.class, UpperColumn2MapKeyConversion.class)
                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
//                .replaceService(Dialect.class, DefaultDialect.class)
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
                .replaceService(QueryConfiguration.class, MyQueryConfiguration.class)
                .replaceService(PropertyDescriptorMatcher.class, EntityPropertyDescriptorMatcher.class)
                .replaceService(NameConversion.class, LowerUnderlinedNameConversion.class)
//                .replaceService(PropertyDescriptorMatcher.class, UpperPropertyDescriptorMatcher.class)
//                .replaceService(SQLKeyword.class, DefaultSQLKeyword.class)
//                .replaceService(BeanValueCaller.class, ReflectBeanValueCaller.class)
                .build();
        easyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);


    }
    @Test
    public void test1(){
        EntityMetadata entityMetadata = easyEntityQuery.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(UpperTopic.class);
        Map<String, ColumnMetadata> property2ColumnMap = entityMetadata.getProperty2ColumnMap();
        Assert.assertTrue(property2ColumnMap.containsKey(UpperTopic.Fields.ID));
        Assert.assertTrue(property2ColumnMap.containsKey(UpperTopic.Fields.Stars));
        Assert.assertTrue(property2ColumnMap.containsKey(UpperTopic.Fields.CreateTime));
        Assert.assertTrue(property2ColumnMap.containsKey(UpperTopic.Fields.Title));
        List<UpperTopic> list = easyEntityQuery.queryable(UpperTopic.class).toList();
        for (UpperTopic upperTopic : list) {
            Assert.assertNotNull(upperTopic.getID());
            Assert.assertNotNull(upperTopic.getStars());
            if("10".equals(upperTopic.getID())||"9".equals(upperTopic.getID())){

                Assert.assertNull(upperTopic.getTitle());
            }else{
                Assert.assertNotNull(upperTopic.getTitle());
            }
            Assert.assertNotNull(upperTopic.getCreateTime());
        }
        System.out.println(list);

        {

            easyEntityQuery.deletable(UpperTopic.class).whereById("xxxxxxaaappll").executeRows();
            try {

                UpperTopic upperTopic = new UpperTopic();
                upperTopic.setID("xxxxxxaaappll");
                upperTopic.setTitle("xxxxxxaaappll");
                upperTopic.setStars(123);
                upperTopic.setCreateTime(LocalDateTime.now());
                easyEntityQuery.insertable(upperTopic).executeRows();
                UpperTopic upperTopic1 = easyEntityQuery.queryable(UpperTopic.class).where(u -> {
                    u.ID().eq(upperTopic.getID());
                }).singleNotNull();
                Assert.assertEquals(upperTopic.getID(),upperTopic1.getID());
                Assert.assertEquals(upperTopic.getStars(),upperTopic1.getStars());
                Assert.assertEquals(upperTopic.getTitle(),upperTopic1.getTitle());
//        Assert.assertEquals(upperTopic.getCreateTime(),upperTopic1.getCreateTime());
                easyEntityQuery.deletable(upperTopic).executeRows();
            }finally {

                easyEntityQuery.deletable(UpperTopic.class).whereById("xxxxxxaaappll").executeRows();
            }
        }

        {

            easyEntityQuery.deletable(MyTopicTestDTO.class).whereById("xxxxxxaaappll").executeRows();
            try {

                MyTopicTestDTO upperTopic = new MyTopicTestDTO();
                upperTopic.setECgi("xxxxxxaaappll");
                upperTopic.setSQL("xxxxxxaaappll");
                upperTopic.setStars(123);
                easyEntityQuery.insertable(upperTopic).executeRows();
                MyTopicTestDTO upperTopic1 = easyEntityQuery.queryable(MyTopicTestDTO.class).where(u -> {
                    u.eCgi().eq(upperTopic.getECgi());
                }).singleNotNull();
                Assert.assertEquals("xxxxxxaaappll",upperTopic.getECgi());
                Assert.assertEquals("xxxxxxaaappll",upperTopic.getSQL());
                Assert.assertEquals(upperTopic.getECgi(),upperTopic1.getECgi());
                Assert.assertEquals(upperTopic.getSQL(),upperTopic1.getSQL());
                Assert.assertEquals(upperTopic.getStars(),upperTopic1.getStars());
//        Assert.assertEquals(upperTopic.getCreateTime(),upperTopic1.getCreateTime());
                easyEntityQuery.deletable(upperTopic).executeRows();
            }finally {

                easyEntityQuery.deletable(MyTopicTestDTO.class).whereById("xxxxxxaaappll").executeRows();
            }
        }

    }
    @Test
    public void test2(){
        EntityMetadata entityMetadata = easyEntityQuery.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(MyALLTYPE.class);
        Map<String, ColumnMetadata> property2ColumnMap = entityMetadata.getProperty2ColumnMap();
        Assert.assertTrue(property2ColumnMap.containsKey(MyALLTYPE.Fields.id));
        Assert.assertTrue(property2ColumnMap.containsKey(MyALLTYPE.Fields.isEnableBasic));
        Assert.assertTrue(property2ColumnMap.containsKey(MyALLTYPE.Fields.isEnable));
        Assert.assertTrue(property2ColumnMap.containsKey(MyALLTYPE.Fields.isNumberDecimal));
        Assert.assertTrue(property2ColumnMap.containsKey(MyALLTYPE.Fields.isNumberInteger));
        Assert.assertTrue(property2ColumnMap.containsKey(MyALLTYPE.Fields.isTimeLocalDateTime));
        Assert.assertTrue(property2ColumnMap.containsKey(MyALLTYPE.Fields.isValue));
        Assert.assertTrue(property2ColumnMap.containsKey(MyALLTYPE.Fields.isNumberIntegerBasic));


        {

            easyEntityQuery.deletable(MyALLTYPE.class).whereById("xxxxxxaaappll").executeRows();
            try {

                MyALLTYPE alltype = new MyALLTYPE();
                alltype.setId("xxxxxxaaappll");
                alltype.setIsNumberDecimal(new BigDecimal("123.32"));
                alltype.setIsNumberInteger(999);
                alltype.setIsEnable(true);
                alltype.setIsNumberIntegerBasic(-999);
                alltype.setEnableBasic(true);
                alltype.setIsTimeLocalDateTime(LocalDateTime.of(2024,1,1,1,1));
                alltype.setIsValue("123");
                easyEntityQuery.insertable(alltype).executeRows();
                MyALLTYPE upperTopic1 = easyEntityQuery.queryable(MyALLTYPE.class).where(u -> {
                    u.id().eq(alltype.getId());
                }).singleNotNull();
                Assert.assertEquals(alltype.getId(),upperTopic1.getId());
                Assert.assertEquals(alltype.getIsEnable(),upperTopic1.getIsEnable());
                Assert.assertEquals(alltype.isEnableBasic(),upperTopic1.isEnableBasic());
                Assert.assertEquals(alltype.getIsNumberDecimal(),upperTopic1.getIsNumberDecimal());
                Assert.assertEquals(alltype.getIsNumberIntegerBasic(),upperTopic1.getIsNumberIntegerBasic());
                Assert.assertEquals(alltype.getIsValue(),upperTopic1.getIsValue());
//        Assert.assertEquals(upperTopic.getCreateTime(),upperTopic1.getCreateTime());
                easyEntityQuery.deletable(alltype).executeRows();
            }finally {

                easyEntityQuery.deletable(UpperTopic.class).whereById("xxxxxxaaappll").executeRows();
            }
        }

    }
}
