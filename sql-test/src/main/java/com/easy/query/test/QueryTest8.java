package com.easy.query.test;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.select.EntityQueryable2;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.test.dto.BlogEntityTest;
import com.easy.query.test.dto.proxy.BlogEntityTestProxy;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.company.ValueCompany;
import com.easy.query.test.entity.company.ValueCompanyDTO;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * create time 2023/10/31 07:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest8 extends BaseTest {

    @Test
    public void test1() {
        EntityQueryable<TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .limit(100);
        EntityQueryable2<TopicProxy, Topic, BlogEntityProxy, BlogEntity> join = query.select(o -> o.FETCHER.id().stars().fetchProxy())
                .innerJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()));
        EntityQueryable2<BlogEntityTestProxy, BlogEntityTest, BlogEntityProxy, BlogEntity> join2 = join.select((t, t1) -> new BlogEntityTestProxy()
                        .url().set(t.id())
                        .title().set(t1.title())
                )
                .innerJoin(BlogEntity.class, (t, t1) -> {
                    t.url().eq(t1.id());
                });
        List<BlogEntity> list = join2.select(BlogEntity.class, (t, t1) -> {
           return Select.of(
                    t.url().as(BlogEntity::getUrl),
                    t.title().as(BlogEntity::getContent)
            );
        }).toList();
    }

    @Test
    public void test3() {
        ValueCompanyDTO companyDTO = easyEntityQuery.queryable(ValueCompany.class)
                .select(ValueCompanyDTO.class)
                .firstOrNull();
        Assert.assertNotNull(companyDTO.getName());
        Assert.assertNotNull(companyDTO.getLicense());
        Assert.assertNotNull(companyDTO.getLicense().getLicenseNo());
        Assert.assertNotNull(companyDTO.getLicense().getExtra());
        Assert.assertNotNull(companyDTO.getLicense().getLicenseDeadline());
        Assert.assertNotNull(companyDTO.getLicense().getExtra().getLicenseContent());
        Assert.assertNotNull(companyDTO.getLicense().getExtra().getLicenseImage());
        Assert.assertNotNull(companyDTO.getAddress());
        Assert.assertNotNull(companyDTO.getAddress().getProvince());
        Assert.assertNotNull(companyDTO.getAddress().getArea());

//        ValueCompanyDTO companyDTO1 = easyEntityQuery.queryable(ValueCompany.class)
//                .select(ValueCompanyDTO.class,v -> Select.of(
//                        v.address().province().as()
//                ))
//                .select(ValueCompanyDTO.class, o -> o.columnAs(x -> x.getAddress().getProvince(), x -> x.getLicense().getExtra().getLicenseContent()))
//                .firstOrNull();
//        Assert.assertNotNull(companyDTO1);
//        Assert.assertEquals(companyDTO.getAddress().getProvince(), companyDTO1.getLicense().getExtra().getLicenseContent());
    }

    @Test
    public void test4() {

        Supplier<Exception> f = () -> {
            try {
                ValueCompanyDTO companyDTO = easyEntityQuery.queryable(ValueCompany.class)
                        .asTable("COMPANY_A")
                        .select(ValueCompanyDTO.class)
//                 .select(BlogEntity.class, o -> o.column(BlogEntity::getId).columnCountAs(BlogEntity::getId, BlogEntity::getStar))
                        .firstOrNull();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        Assert.assertEquals("SELECT t.`name`,t.`province`,t.`area`,t.`license_no`,t.`license_deadline`,t.`license_image`,t.`license_content` FROM `COMPANY_A` t LIMIT 1", easyQuerySQLStatementException.getSQL());

    }



    @Test
    public void test4x() {

        List<Map<String, Object>> maps = easyEntityQuery.sqlQueryMap("SHOW TABLES");
        System.out.println(maps);
        List<String> tables = maps.stream().flatMap(o -> o.values().stream()).map(o -> o.toString()).collect(Collectors.toList());

        System.out.println(tables);
    }


    @Test
     public void testJoin5(){
         String sql = easyEntityQuery.queryable(Topic.class)
                 .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4) -> t1.id().eq(t2.id()))
                 .leftJoin(BlogEntity.class, (t1, t2, t3, t4, t5) -> t1.id().eq(t2.id()))
                 .where((t1, t2, t3, t4, t5) -> {
                     t1.stars().eq(1);
                     t5.order().eq(new BigDecimal("1"));
                 }).toSQL();
         Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t1.`id` LEFT JOIN `t_blog` t4 ON t4.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`stars` = ? AND t4.`order` = ?",sql);
     }
    @Test
     public void testJoin6(){
         String sql = easyEntityQuery.queryable(Topic.class)
                 .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5) -> t1.id().eq(t2.id()))
                 .leftJoin(BlogEntity.class, (t1, t2, t3, t4, t5,t6) -> t1.id().eq(t2.id()))
                 .where((t1, t2, t3, t4, t5,t6) -> {
                     t1.stars().eq(1);
                     t6.order().eq(new BigDecimal("1"));
                 }).toSQL();
         Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t1.`id` LEFT JOIN `t_blog` t5 ON t5.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`stars` = ? AND t5.`order` = ?",sql);
     }
    @Test
     public void testJoin7(){
         String sql = easyEntityQuery.queryable(Topic.class)
                 .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6) -> t1.id().eq(t2.id()))
                 .leftJoin(BlogEntity.class, (t1, t2, t3, t4, t5,t6,t7) -> t1.id().eq(t2.id()))
                 .where((t1, t2, t3, t4, t5,t6,t7) -> {
                     t1.stars().eq(1);
                     t7.order().eq(new BigDecimal("1"));
                 }).toSQL();
         Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t1.`id` LEFT JOIN `t_blog` t6 ON t6.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`stars` = ? AND t6.`order` = ?",sql);
     }
    @Test
     public void testJoin8(){
         String sql = easyEntityQuery.queryable(Topic.class)
                 .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6,t7) -> t1.id().eq(t2.id()))
                 .leftJoin(BlogEntity.class, (t1, t2, t3, t4, t5,t6,t7,t8) -> t1.id().eq(t2.id()))
                 .where((t1, t2, t3, t4, t5,t6,t7,t8) -> {
                     t1.stars().eq(1);
                     t8.order().eq(new BigDecimal("1"));
                 }).toSQL();
         Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t1.`id` LEFT JOIN `t_blog` t7 ON t7.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`stars` = ? AND t7.`order` = ?",sql);
     }
    @Test
     public void testJoin9(){
         String sql = easyEntityQuery.queryable(Topic.class)
                 .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6,t7) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6,t7,t8) -> t1.id().eq(t2.id()))
                 .leftJoin(BlogEntity.class, (t1, t2, t3, t4, t5,t6,t7,t8,t9) -> t1.id().eq(t2.id()))
                 .where((t1, t2, t3, t4, t5,t6,t7,t8,t9) -> {
                     t1.stars().eq(1);
                     t9.order().eq(new BigDecimal("1"));
                 }).toSQL();
         Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t1.`id` LEFT JOIN `t_blog` t8 ON t8.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`stars` = ? AND t8.`order` = ?",sql);
     }
    @Test
     public void testJoin10(){
         String sql = easyEntityQuery.queryable(Topic.class)
                 .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6,t7) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6,t7,t8) -> t1.id().eq(t2.id()))
                 .leftJoin(Topic.class, (t1, t2, t3, t4, t5,t6,t7,t8,t9) -> t1.id().eq(t2.id()))
                 .leftJoin(BlogEntity.class, (t1, t2, t3, t4, t5,t6,t7,t8,t9,t10) -> t1.id().eq(t2.id()))
                 .where((t1, t2, t3, t4, t5,t6,t7,t8,t9,t10) -> {
                     t1.stars().eq(1);
                     t10.order().eq(new BigDecimal("1"));
                 }).toSQL();
         Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t1.`id` LEFT JOIN `t_blog` t9 ON t9.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`stars` = ? AND t9.`order` = ?",sql);
     }

}
