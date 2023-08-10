package com.easy.query.test;

import com.easy.query.api4j.insert.EntityInsertable;
import com.easy.query.core.basic.jdbc.parameter.BeanSQLParameter;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.CustomIncrement;
import com.easy.query.test.entity.SysUserSQLEncryption;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicAuto;
import com.easy.query.test.entity.TopicAutoNative;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @FileName: InsertTest.java
 * @Description: 文件说明
 * @Date: 2023/3/16 21:25
 * @author xuejiaming
 */
public class InsertTest extends BaseTest {

    @Test
    public void insertTest(){
        List<TopicAuto> topicAutos = easyQuery.queryable(TopicAuto.class).where(o->o.lt(TopicAuto::getStars,999)).toList();
        Assert.assertEquals(10,topicAutos.size());
        int i=1;
        for (TopicAuto topicAuto : topicAutos) {
            Assert.assertNotNull(topicAuto.getId());
            Assert.assertEquals(0, topicAuto.getId().compareTo(i));
            i++;
        }
    }
    @Test
    public void insertTest1(){
        long l = easyQuery.insertable(null).executeRows();
        Assert.assertEquals(0,l);
        long l1 = easyQuery.insertable(null).insert(null).executeRows();
        Assert.assertEquals(0,l1);
        Object en=null;
        long l3 = easyQuery.insertable(null).insert(en).executeRows();
        Assert.assertEquals(0,l3);
        long l2 = easyQuery.insertable(null).useInterceptor().noInterceptor().useInterceptor("1").noInterceptor("1").executeRows();
        Assert.assertEquals(0,l2);
    }

    @Test
    public void insertTest2(){

        TopicAuto topicAuto = new TopicAuto();
        topicAuto.setStars(999);
        topicAuto.setTitle("title" + 999);
        topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
        Assert.assertNull(topicAuto.getId());
        long l = easyQuery.insertable(topicAuto).executeRows(true);
        Assert.assertEquals(1,l);
        Assert.assertNotNull(topicAuto.getId());
    }
    @Test
    public void insertTest3(){

        TopicAuto topicAuto = new TopicAuto();
        topicAuto.setStars(999);
        topicAuto.setTitle("title" + 999);
        topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
        Assert.assertNull(topicAuto.getId());
        EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).asTable("aaa").asSchema(o->"xxx");
        String sql = insertable.toSQL(topicAuto);
        Assert.assertEquals("INSERT INTO `xxx`.`aaa` (`stars`,`title`,`create_time`) VALUES (?,?,?)",sql);
    }
    @Test
    public void insertTest5(){

        TopicAuto topicAuto = new TopicAuto();
        topicAuto.setStars(999);
        topicAuto.setTitle("title" + 999);
        topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
        Assert.assertNull(topicAuto.getId());
        EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).asTable(o->o+"aaa").asSchema("xxx");
        String sql = insertable.toSQL(topicAuto);
        Assert.assertEquals("INSERT INTO `xxx`.`t_topic_autoaaa` (`stars`,`title`,`create_time`) VALUES (?,?,?)",sql);
    }
    @Test
    public void insertTest6(){

        try {

            TopicAuto topicAuto = new TopicAuto();
            topicAuto.setStars(999);
            topicAuto.setTitle("title" + 999);
            topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
            Assert.assertNull(topicAuto.getId());
            EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).useInterceptor("Topic1Interceptor").asTable(o->o+"aaa").asSchema("xxx");
            long l = insertable.executeRows();
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
            Assert.assertEquals("INSERT INTO `xxx`.`t_topic_autoaaa` (`stars`,`create_time`) VALUES (?,?)", sql);
        }
    }
    @Test
    public void insertTest7(){

        try {

            TopicAuto topicAuto = new TopicAuto();
            topicAuto.setStars(999);
            topicAuto.setTitle("title" + 999);
            topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
            Assert.assertNull(topicAuto.getId());
            EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).useInterceptor("Topic1Interceptor").noInterceptor().asTable(o->o+"aaa").asSchema("xxx");
            long l = insertable.executeRows();
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
            Assert.assertEquals("INSERT INTO `xxx`.`t_topic_autoaaa` (`stars`,`title`,`create_time`) VALUES (?,?,?)", sql);
        }
    }
    @Test
    public void insertTest8(){

        try {

            TopicAuto topicAuto = new TopicAuto();
            topicAuto.setStars(999);
            topicAuto.setTitle("title" + 999);
            topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
            Assert.assertNull(topicAuto.getId());
            EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).noInterceptor().useInterceptor("Topic1Interceptor").asTable(o->o+"aaa").asSchema("xxx");
            long l = insertable.executeRows();
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
            Assert.assertEquals("INSERT INTO `xxx`.`t_topic_autoaaa` (`stars`,`create_time`) VALUES (?,?)", sql);
        }
    }
    @Test
    public void insertTest8_1(){

        try {

            TopicAuto topicAuto = new TopicAuto();
            topicAuto.setStars(999);
            topicAuto.setTitle("title" + 999);
            topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
            Assert.assertNull(topicAuto.getId());
            EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).onDuplicateKeyIgnore().noInterceptor().useInterceptor("Topic1Interceptor").asTable(o->o+"aaa").asSchema("xxx");
            long l = insertable.executeRows();
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
            Assert.assertEquals("INSERT IGNORE INTO `xxx`.`t_topic_autoaaa` (`stars`,`create_time`) VALUES (?,?)", sql);
        }
    }
    @Test
    public void insertTest9(){
        TopicAuto topicAuto=null;
        long l = easyQuery.insertable(topicAuto)
                .noInterceptor().useInterceptor("11")
                .useInterceptor("11").useInterceptor()
                .asAlias("a")
                .asSchema("b")
                .asTable("c")
                .asSchema(o -> o + "ab")
                .asTable(o -> o + "bb")
                .executeRows();
        Assert.assertEquals(0,l);
    }

    @Test
    public void insertDuplicateKeyUpdate1(){

        TopicAuto topicAuto = new TopicAuto();
        topicAuto.setStars(999);
        topicAuto.setTitle("title" + 999);
        topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
        Assert.assertNull(topicAuto.getId());
        EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).onDuplicateKeyUpdate();
        String sql = insertable.toSQL(topicAuto);
        Assert.assertEquals("INSERT INTO `t_topic_auto` (`stars`,`title`,`create_time`) VALUES (?,?,?) ON DUPLICATE KEY UPDATE `stars` = VALUES(`stars`), `title` = VALUES(`title`), `create_time` = VALUES(`create_time`)",sql);
    }
    @Test
    public void insertDuplicateKeyUpdate2(){

        TopicAuto topicAuto = new TopicAuto();
        topicAuto.setStars(999);
        topicAuto.setTitle("title" + 999);
        topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
        Assert.assertNull(topicAuto.getId());
        EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).onDuplicateKeyUpdate(t->t.column(TopicAuto::getStars).column(TopicAuto::getTitle));
        String sql = insertable.toSQL(topicAuto);
        Assert.assertEquals("INSERT INTO `t_topic_auto` (`stars`,`title`,`create_time`) VALUES (?,?,?) ON DUPLICATE KEY UPDATE `stars` = VALUES(`stars`), `title` = VALUES(`title`)",sql);
    }



    @Test
    public void insertTestx() {
        easyQuery.deletable(BlogEntity.class)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .whereById("200")
                .executeRows();
        LocalDateTime begin = LocalDateTime.of(2000, 1, 1, 1, 1, 1);
        String indexStr = "200";
        BlogEntity blog = new BlogEntity();
        blog.setId(indexStr);
        blog.setCreateBy(indexStr);
        blog.setCreateTime(begin.plusDays(1));
        blog.setUpdateBy(indexStr);
        blog.setUpdateTime(begin.plusDays(1));
        blog.setTitle("title" + indexStr);
        blog.setContent("content" + indexStr);
        blog.setUrl("http://blog.easy-query.com/" + indexStr);
        blog.setStar(1);
        blog.setScore(new BigDecimal("1.2"));
        blog.setStatus(1);
        blog.setOrder(new BigDecimal("1.2").multiply(BigDecimal.valueOf(1)));
        blog.setIsTop(false);
        blog.setTop(false);
        blog.setDeleted(false);
        long l = easyQuery.insertable(blog)
                .onDuplicateKeyUpdate()
                .executeRows();
        Assert.assertEquals(1,l);
        long l3 = easyQuery.insertable(blog)
                .onDuplicateKeyUpdate(t->t.column(BlogEntity::getStar).column(BlogEntity::getContent))
                .executeRows();

        Assert.assertEquals(1,l3);
        blog.setContent("xxx");
        long l4 = easyQuery.insertable(blog)
                .onDuplicateKeyUpdate(t->t.column(BlogEntity::getStar).column(BlogEntity::getContent))
                .executeRows();

        Assert.assertEquals(2,l4);
        long l1 = easyQuery.insertable(blog)
                .onDuplicateKeyIgnore()
                .executeRows();
        Assert.assertEquals(0,l1);

        blog.setContent("abc");
        long l2 = easyQuery.insertable(blog)
                .onDuplicateKeyUpdate()
                .executeRows();
        Assert.assertEquals(2,l2);
        BlogEntity blogEntity = easyQuery.queryable(BlogEntity.class)
                .whereById("200")
                .firstNotNull("xxx");
        Assert.assertEquals("abc",blogEntity.getContent());
        easyQuery.deletable(BlogEntity.class)
                .whereById("200")
                .executeRows();
    }

    @Test
    public void insertBatch(){

        easyQuery.deletable(BlogEntity.class)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .whereByIds(Arrays.asList("500","300","400")).executeRows();
        String indexStr = "500";
        LocalDateTime begin = LocalDateTime.of(2000, 1, 1, 1, 1, 1);
        List<BlogEntity> r=new ArrayList<>(2);
        {
            BlogEntity blog = new BlogEntity();
            blog.setId(indexStr);
            blog.setCreateBy(indexStr);
            blog.setCreateTime(begin.plusDays(1));
            blog.setUpdateBy(indexStr);
            blog.setUpdateTime(begin.plusDays(1));
            blog.setTitle("title" + indexStr);
            blog.setContent("content" + indexStr);
            blog.setUrl("http://blog.easy-query.com/" + indexStr);
            blog.setStar(500);
            blog.setScore(new BigDecimal("1.2"));
            blog.setStatus(1);
            blog.setOrder(new BigDecimal("1.2").multiply(BigDecimal.valueOf(1)));
            blog.setIsTop(false);
            blog.setTop(false);
            blog.setDeleted(false);
            r.add(blog);
        }
        indexStr="300";
        {
            BlogEntity blog = new BlogEntity();
            blog.setId(indexStr);
            blog.setCreateBy(indexStr);
            blog.setCreateTime(begin.plusDays(1));
            blog.setUpdateBy(indexStr);
            blog.setUpdateTime(begin.plusDays(1));
            blog.setTitle("title" + indexStr);
            blog.setContent("content" + indexStr);
            blog.setUrl("http://blog.easy-query.com/" + indexStr);
            blog.setStar(300);
            blog.setScore(new BigDecimal("1.2"));
            blog.setStatus(1);
            blog.setOrder(new BigDecimal("1.2").multiply(BigDecimal.valueOf(1)));
            blog.setIsTop(false);
            blog.setTop(false);
            blog.setDeleted(false);
            r.add(blog);
        }
        indexStr="400";
        {
            BlogEntity blog = new BlogEntity();
            blog.setId(indexStr);
            blog.setCreateBy(indexStr);
            blog.setCreateTime(begin.plusDays(1));
            blog.setUpdateBy(indexStr);
            blog.setUpdateTime(begin.plusDays(1));
            blog.setTitle("title" + indexStr);
            blog.setContent("content" + indexStr);
            blog.setUrl("http://blog.easy-query.com/" + indexStr);
            blog.setStar(400);
            blog.setScore(new BigDecimal("1.2"));
            blog.setStatus(1);
            blog.setOrder(new BigDecimal("1.2").multiply(BigDecimal.valueOf(1)));
            blog.setIsTop(false);
            blog.setTop(false);
            blog.setDeleted(false);
            r.add(blog);
        }
        long l = easyQuery.insertable(r).executeRows();
        Assert.assertEquals(3,l);
        easyQuery.deletable(BlogEntity.class)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .whereByIds(Arrays.asList("500","300","400")).executeRows();
        long l2 = easyQuery.insertable(r).batch().executeRows();
        Assert.assertEquals(-6,l2);
        easyQuery.deletable(BlogEntity.class)
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .whereByIds(Arrays.asList("500","300","400")).executeRows();
    }

    @Test
    public void insertCustom(){
        TopicAutoNative topicAuto = new TopicAutoNative();
        topicAuto.setStars(1);
        try {

            easyQuery.insertable(topicAuto)
                    .asTable("xxxxx")
                    .columnConfigure(o->o.column(TopicAutoNative::getId,"sde.next_rowid('sde',{0})",(context, sqlParameter)->{
                        context.value(sqlParameter);
                    })).executeRows();
        }catch (Exception ex){
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("INSERT INTO `xxxxx` (`stars`) VALUES (?)",sql);
        }
    }
    @Test
    public void insertCustom1(){
        TopicAutoNative topicAuto = new TopicAutoNative();
        topicAuto.setStars(1);
        topicAuto.setId(0);
        try {

            easyQuery.insertable(topicAuto)
                    .asTable("xxxxx")
                    .columnConfigure(o->o.column(TopicAutoNative::getId,"sde.next_rowid('sde',{0})",(context, sqlParameter)->{
                        context.value(sqlParameter);
                    })).executeRows();
        }catch (Exception ex){
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("INSERT INTO `xxxxx` (`id`,`stars`) VALUES (sde.next_rowid(sde,?),?)",sql);
        }
    }
    @Test
    public void insertSQLConvert1(){


        try {

            SysUserSQLEncryption user = new SysUserSQLEncryption();
            user.setId("123");
            user.setUsername("username");
            user.setPhone("13232456789");
            user.setIdCard("12345678");
            user.setAddress("xxxxxxx");
            user.setCreateTime(LocalDateTime.now());
            easyQuery.insertable(user)
                    .asTable("xxxxx").executeRows();
        }catch (Exception ex){
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("INSERT INTO `xxxxx` (`id`,`username`,`phone`,`id_card`,`address`,`create_time`) VALUES (?,?,to_base64(AES_ENCRYPT(?,?)),?,?,?)",sql);
        }
    }
    @Test
    public void insertSQLConvert2(){

        SysUserSQLEncryption user = new SysUserSQLEncryption();
        user.setId("123");
        user.setUsername("username");
        user.setPhone("13232456789");
        user.setIdCard("12345678");
        user.setAddress("xxxxxxx");
        LocalDateTime now = LocalDateTime.now();
        user.setCreateTime(now);
        EntityInsertable<SysUserSQLEncryption> insertable = easyQuery.insertable(user);
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(insertable.getEntityInsertExpressionBuilder().getExpressionContext().getTableContext());
        insertable.asTable("xxxxx").toSQL(user, toSQLContext);
        List<SQLParameter> parameters = toSQLContext.getParameters();
        Assert.assertEquals(parameters.size(),7);

        for (SQLParameter parameter : parameters) {
            TableAvailable tableOrNull = parameter.getTableOrNull();
            if(tableOrNull==null){
                Object value = parameter.getValue();
                Assert.assertEquals("1234567890123456",value);
            }else {

                Assert.assertTrue(parameter instanceof BeanSQLParameter);
                Assert.assertTrue(parameter instanceof PropertySQLParameter);
                PropertySQLParameter propertySQLParameter = (PropertySQLParameter) parameter;
                propertySQLParameter.setBean(user);
                String propertyNameOrNull = propertySQLParameter.getPropertyNameOrNull();
                switch (propertyNameOrNull){
                    case "id":Assert.assertEquals("123",propertySQLParameter.getValue());break;
                    case "username":Assert.assertEquals("username",propertySQLParameter.getValue());break;
                    case "phone":Assert.assertEquals("13232456789",propertySQLParameter.getValue());break;
                    case "idCard":Assert.assertEquals("12345678",propertySQLParameter.getValue());break;
                    case "address":Assert.assertEquals("xxxxxxx",propertySQLParameter.getValue());break;
                    case "createTime":Assert.assertEquals(now,propertySQLParameter.getValue());break;
                }
            }
        }
    }
    @Test
    public void insertSQLConvert3(){
        easyQuery.deletable(SysUserSQLEncryption.class).disableLogicDelete()
                .whereById("12345").executeRows();

        SysUserSQLEncryption user = new SysUserSQLEncryption();
        user.setId("12345");
        user.setUsername("username");
        user.setPhone("13232456789");
        user.setIdCard("12345678");
        user.setAddress("xxxxxxx");
        user.setCreateTime(LocalDateTime.now());
        long l = easyQuery.insertable(user).executeRows();
        Assert.assertEquals(1, l);
        SysUserSQLEncryption sysUserSQLEncryption = easyQuery.queryable(SysUserSQLEncryption.class)
                .whereById("12345")
                .firstOrNull();
        System.out.println(sysUserSQLEncryption);
        Assert.assertNotNull(sysUserSQLEncryption);

        Assert.assertEquals("13232456789",sysUserSQLEncryption.getPhone());
        Assert.assertEquals(sysUserSQLEncryption.getId(),user.getId());
        Assert.assertEquals(sysUserSQLEncryption.getUsername(),user.getUsername());
        Assert.assertEquals(sysUserSQLEncryption.getPhone(),user.getPhone());
        Assert.assertEquals(sysUserSQLEncryption.getIdCard(),user.getIdCard());
        Assert.assertEquals(sysUserSQLEncryption.getAddress(),user.getAddress());
        Assert.assertEquals(sysUserSQLEncryption.getCreateTime().getYear(),user.getCreateTime().getYear());
        Assert.assertEquals(sysUserSQLEncryption.getCreateTime().getMonth(),user.getCreateTime().getMonth());
        Assert.assertEquals(sysUserSQLEncryption.getCreateTime().getDayOfYear(),user.getCreateTime().getDayOfYear());
        Assert.assertEquals(sysUserSQLEncryption.getCreateTime().getHour(),user.getCreateTime().getHour());
        Assert.assertEquals(sysUserSQLEncryption.getCreateTime().getMinute(),user.getCreateTime().getMinute());

        sysUserSQLEncryption.setPhone("111123456");
        long l2 = easyQuery.updatable(sysUserSQLEncryption).executeRows();
        Assert.assertEquals(1,l2);
        long l1 = easyQuery.updatable(SysUserSQLEncryption.class)
                .set(SysUserSQLEncryption::getPhone, "1111234")
                .whereById("12345").executeRows();
        Assert.assertEquals(1,l1);

        SysUserSQLEncryption sysUserSQLEncryption1 = easyQuery.queryable(SysUserSQLEncryption.class)
                .leftJoin(Topic.class, (t, t1) -> t.eq(t1, SysUserSQLEncryption::getId, Topic::getId))
                .where((t, t1) -> t.eq(SysUserSQLEncryption::getPhone, "1111234"))
                .select(SysUserSQLEncryption.class, (t, t1) -> t.columnAll())
                .firstOrNull();
        Assert.assertNotNull(sysUserSQLEncryption1);


        easyQuery.deletable(SysUserSQLEncryption.class).disableLogicDelete()
                .whereById("12345").executeRows();
    }

    @Test
    public void  incrementTest1(){
        try {

            CustomIncrement customIncrement=new CustomIncrement();
            customIncrement.setName("name");
            customIncrement.setAddress("address");
            easyQuery.insertable(customIncrement)
                    .executeRows();
        }catch (Exception ex){
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("INSERT INTO `custom_increment` (`id`,`name`,`address`) VALUES (mysqlNextId(),?,?)",sql);
        }
    }
    @Test
    public void  incrementTest2(){
        try {
            CustomIncrement customIncrement=new CustomIncrement();
            customIncrement.setId("id");
            customIncrement.setName("name");
            customIncrement.setAddress("address");
            easyQuery.insertable(customIncrement)
                    .executeRows();
        }catch (Exception ex){
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("INSERT INTO `custom_increment` (`id`,`name`,`address`) VALUES (mysqlNextId(),?,?)",sql);
        }
    }
}
