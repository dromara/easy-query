package com.easy.query.test.mysql8;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.func.def.enums.OrderByModeEnum;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.dto.BlogQuery1Request;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.many.M8Province;
import com.easy.query.test.mysql8.vo.M8ProvinceDTO;
import com.easy.query.test.mysql8.vo.M8ProvinceDTO2;
import com.easy.query.test.mysql8.vo.M8ProvinceDTO3;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2025/7/17 22:27
 * 文件说明
 *
 * @author xuejiaming
 */
public class M8PCABTest extends BaseTest{

    @Test
    public void testFlat(){
        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        List<M8ProvinceDTO> list = easyEntityQuery.queryable(M8Province.class)
                .selectAutoInclude(M8ProvinceDTO.class)
                .toList();
        for (M8ProvinceDTO m8ProvinceDTO : list) {

            for (M8ProvinceDTO.InternalCities city : m8ProvinceDTO.getCities()) {
                for (M8ProvinceDTO.InternalBuilds build : city.getBuilds()) {

                }
            }
        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(4, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.`id`,t.`name` FROM `m8_province` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`id`,t.`pid`,t.`name` FROM `m8_city` t WHERE t.`pid` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("p1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
            Assert.assertEquals("SELECT `id` AS `__relation__id`,`cid` AS `__relation__cid` FROM `m8_area` WHERE `cid` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("c1(String),c2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
            Assert.assertEquals("SELECT t.`id`,t.`aid`,t.`name` FROM `m8_build` t WHERE t.`aid` IN (?,?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("a1(String),a2(String),a3(String),a4(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
//        Assert.assertEquals("0(String),1(String),2(String),3(String),4(String),5(String),6(String),7(String),8(String),9(String),10(String),11(String),12(String),13(String),14(String),15(String),16(String),17(String),18(String),19(String),20(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testFlat1(){


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<M8ProvinceDTO2> list = easyEntityQuery.queryable(M8Province.class)
                .selectAutoInclude(M8ProvinceDTO2.class)
                .toList();
        for (M8ProvinceDTO2 m8ProvinceDTO : list) {

            for (M8ProvinceDTO2.InternalCities city : m8ProvinceDTO.getCities()) {
                for (M8ProvinceDTO2.InternalBuilds build : city.getBuilds()) {

                }
            }
        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(5, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.`id`,t.`name` FROM `m8_province` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`id`,t.`pid`,t.`name` FROM `m8_city` t WHERE t.`pid` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("p1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
            Assert.assertEquals("SELECT `id` AS `__relation__id`,`cid` AS `__relation__cid` FROM `m8_area` WHERE `cid` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("c1(String),c2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
            Assert.assertEquals("SELECT t.`id`,t.`aid`,t.`name` FROM `m8_build` t WHERE t.`aid` IN (?,?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("a1(String),a2(String),a3(String),a4(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
            Assert.assertEquals("SELECT `id`,`build_id` AS `__relation__buildId` FROM `m8_build_license` WHERE `build_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("b1(String),b2(String),b3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
//        Assert.assertEquals("0(String),1(String),2(String),3(String),4(String),5(String),6(String),7(String),8(String),9(String),10(String),11(String),12(String),13(String),14(String),15(String),16(String),17(String),18(String),19(String),20(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testFlat2(){


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        List<M8ProvinceDTO3> list = easyEntityQuery.queryable(M8Province.class)
                .selectAutoInclude(M8ProvinceDTO3.class)
                .toList();
        for (M8ProvinceDTO3 m8ProvinceDTO : list) {

            for (M8ProvinceDTO3.InternalCities city : m8ProvinceDTO.getCities()) {
                for (M8ProvinceDTO3.InternalBuilds build : city.getBuilds()) {

                }
            }
        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(5, listenerContext.getJdbcExecuteAfterArgs().size());
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("SELECT t.`id`,t.`name` FROM `m8_province` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("class1(String),class2(String),class3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("SELECT t.`id`,t.`pid`,t.`name` FROM `m8_city` t WHERE t.`pid` IN (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("p1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
            Assert.assertEquals("SELECT `id` AS `__relation__id`,`cid` AS `__relation__cid` FROM `m8_area` WHERE `cid` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("c1(String),c2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
            Assert.assertEquals("SELECT t.`id`,t.`aid`,t.`name` FROM `m8_build` t WHERE t.`aid` IN (?,?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("a1(String),a2(String),a3(String),a4(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(4);
            Assert.assertEquals("SELECT t.`id`,t.`no`,t.`build_id` AS `__relation__buildId` FROM `m8_build_license` t WHERE t.`build_id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("b1(String),b2(String),b3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
//        Assert.assertEquals("0(String),1(String),2(String),3(String),4(String),5(String),6(String),7(String),8(String),9(String),10(String),11(String),12(String),13(String),14(String),15(String),16(String),17(String),18(String),19(String),20(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }


//    @Test
//    public void test(){
//        BlogQuery1Request query = new BlogQuery1Request();
//        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
//                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT_PROPAGATION_SUPPORTS)
//                .where(t_blog -> {
//                    t_blog.content().like(query.getContent());
//                    t_blog.order().eq(query.getOrder());
//                    t_blog.publishTime().rangeClosed(query.getPublishTimeBegin(), query.getPublishTimeEnd());
//                    t_blog.status().in(query.getStatusList());
//                }).orderBy(t_blog -> {
//                    t_blog.createTime().asc(OrderByModeEnum.NULLS_LAST);
//                }).limit(100)
//                .toList();
//    }
}
