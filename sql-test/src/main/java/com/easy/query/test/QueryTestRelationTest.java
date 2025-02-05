package com.easy.query.test;

import com.easy.query.api.proxy.base.LongProxy;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyRelationalUtil;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.test.doc.MyComUser;
import com.easy.query.test.doc.MyComUser1;
import com.easy.query.test.doc.MyCompany;
import com.easy.query.test.doc.MySignUp;
import com.easy.query.test.doc.MyUser;
import com.easy.query.test.doc.dto.MyComUserDTO1;
import com.easy.query.test.doc.dto.MyComUserDTO2;
import com.easy.query.test.doc.dto.MyComUserDTO3;
import com.easy.query.test.doc.dto.MyComUserDTO4;
import com.easy.query.test.doc.dto.MyComUserDTO5;
import com.easy.query.test.doc.dto.MyComUserDTO6;
import com.easy.query.test.doc.dto.MyComUserDTO7;
import com.easy.query.test.doc.dto.MyComUserDTO8;
import com.easy.query.test.doc.dto.MyComUserDTO9;
import com.easy.query.test.doc.proxy.MySignUpProxy;
import com.easy.query.test.dto.autodto.SchoolClassAOProp14;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.school.SchoolClass;
import com.easy.query.test.entity.school.SchoolClassAggregate;
import com.easy.query.test.listener.ListenerContext;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * create time 2024/10/14 11:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTestRelationTest extends BaseTest {
    @Data
    public static class AA{
        private String name;

        private BB myCompany;

        @Data
        public static class BB{
            private String name;
        }
    }
    @Test
    public void test1(){
        List<MySignUp> list3 = easyEntityQuery.queryable(MyComUser.class)
                .toList(o -> o.mySignUps().flatElement());

        List<Draft1<Long>> list2 = easyEntityQuery.queryable(MyComUser.class)
                .select(m -> Select.DRAFT.of(
                        m.mySignUps().count()
                )).toList();


        List<MySignUp> list1 = easyEntityQuery.queryable(MySignUp.class)
                .where(m -> {
                    m.comUser().myCompany().name().like("123");
                }).toList();

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        String companyName="公司";
        List<MyComUser> list = easyEntityQuery.queryable(MyComUser.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(su -> {
                    su.myCompany().name().like(companyName);
                }).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`com_id`,t.`user_id`,t.`gw` FROM `my_com_user` t LEFT JOIN `my_company_info` t1 ON t1.`id` = t.`com_id` WHERE t1.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%公司%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void test1_1(){
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        String companyName="公司";
        easyEntityQuery.queryable(MyComUser.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(su -> {
                    su.myCompany().name().like(companyName);
                })
                .select(m -> Select.DRAFT.of(
                        m.expression().caseWhen(()->{
                            m.myCompany().name().like(companyName);
                        }).then(1).elseEnd(0).asAnyType(Integer.class)
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (CASE WHEN t1.`name` LIKE ? THEN ? ELSE ? END) AS `value1` FROM `my_com_user` t LEFT JOIN `my_company_info` t1 ON t1.`id` = t.`com_id` WHERE t1.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%公司%(String),1(Integer),0(Integer),%公司%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void test2(){
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        String companyName="";
        List<MyComUser> list = easyEntityQuery.queryable(MyComUser.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(su -> {
                    su.myCompany().name().like(companyName);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`com_id`,t.`user_id`,t.`gw` FROM `my_com_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
        listenerContextManager.clear();
    }
    @Test
    public void test3(){
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        String companyName="";
        List<MyComUser> list = easyEntityQuery.queryable(MyComUser.class)
                .where(su -> {
                    if(EasyStringUtil.isNotBlank(companyName)){
                        su.myCompany().name().like(companyName);
                    }
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`com_id`,`user_id`,`gw` FROM `my_com_user`", jdbcExecuteAfterArg.getBeforeArg().getSql());
        listenerContextManager.clear();
    }

    @Test
    public void relationTest0() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<MyComUser> list1 = easyEntityQuery.queryable(MyComUser.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(m -> {
                    m.mySignUps()
                            .configure(s -> {
                                s.filterConfigure(NotNullOrEmptyValueFilter.DEFAULT);
                            })
                            .any(s -> s.userId().eq(""));
                }).toList();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`com_id`,t.`user_id`,t.`gw` FROM `my_com_user` t WHERE EXISTS (SELECT 1 FROM `my_sign_up` t1 WHERE (t1.`com_id` = t.`com_id` AND t1.`user_id` = t.`user_id`) LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        List<SQLParameter> sqlParameters = jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0);
        Assert.assertEquals(1,jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().size());
        Assert.assertEquals(0,sqlParameters.size());
        listenerContextManager.clear();
    }
    @Test
    public void relationTest0_1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<MySignUp> list = easyEntityQuery.queryable(MySignUp.class)
                .where(m -> {
                    m.comUser().userId().eq("");
                    m.comId().eq("123");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`com_id`,t.`user_id`,t.`time`,t.`content` FROM `my_sign_up` t LEFT JOIN `my_com_user` t1 ON (t1.`com_id` = t.`com_id` AND t1.`user_id` = t.`user_id`) WHERE t1.`user_id` = ? AND t.`com_id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void relationTest0_2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<MySignUp> list = easyEntityQuery.queryable(MySignUp.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(m -> {
                    m.comUser().userId().eq("");
                    m.comId().eq("123");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`com_id`,t.`user_id`,t.`time`,t.`content` FROM `my_sign_up` t WHERE t.`com_id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        listenerContextManager.clear();
    }
    @Test
    public void relationTest0_x() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<MySignUp> list = easyEntityQuery.queryable(MySignUp.class)
                .where(m -> {
                    m.expression().sql("{0} = {1}",c->{
                        c.value("123").expression(m.comUser().userId());
                    });
                    m.comId().eq("123");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`com_id`,t.`user_id`,t.`time`,t.`content` FROM `my_sign_up` t LEFT JOIN `my_com_user` t1 ON (t1.`com_id` = t.`com_id` AND t1.`user_id` = t.`user_id`) WHERE ? = t1.`user_id` AND t.`com_id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void relationTest0_3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<MySignUp> list = easyEntityQuery.queryable(MySignUp.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(m -> {
                    m.comId().in(
                            easyEntityQuery.queryable(MySignUp.class).where(m1 -> {
                                m1.comId().eq(m.comUser().comId());
                            }).select(m1->m1.comId())
                    );
                    m.comId().eq("123");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`com_id`,t.`user_id`,t.`time`,t.`content` FROM `my_sign_up` t LEFT JOIN `my_com_user` t1 ON (t1.`com_id` = t.`com_id` AND t1.`user_id` = t.`user_id`) WHERE t.`com_id` IN (SELECT t2.`com_id` FROM `my_sign_up` t2 WHERE t2.`com_id` = t1.`com_id`) AND t.`com_id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void relationTest0_4() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<MySignUp> list = easyEntityQuery.queryable(MySignUp.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(m -> {
                    m.expression().exists(()->{
                        return easyEntityQuery.queryable(MySignUp.class).where(m1 -> {
                            m1.comId().eq(m.comUser().comId());
                        });
                    });
                    m.comId().eq("123");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`com_id`,t.`user_id`,t.`time`,t.`content` FROM `my_sign_up` t LEFT JOIN `my_com_user` t1 ON (t1.`com_id` = t.`com_id` AND t1.`user_id` = t.`user_id`) WHERE EXISTS (SELECT 1 FROM `my_sign_up` t2 WHERE t2.`com_id` = t1.`com_id`) AND t.`com_id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void relationTest0_5() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


       easyEntityQuery.queryable(MySignUp.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(m -> {
                    m.comId().eq("123");
                }).select(m -> {
                   MySignUpProxy mySignUpProxy = new MySignUpProxy();
                   mySignUpProxy.id().setSubQuery(EasyObjectUtil.typeCastNullable(easyEntityQuery.queryable(MySignUp.class).where(m1 -> {
                       m1.comId().eq(m.comUser().comId());
                   }).selectCount()));
                   return mySignUpProxy;
               }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (SELECT COUNT(*) FROM `my_sign_up` t2 WHERE t2.`com_id` = t1.`com_id`) AS `id` FROM `my_sign_up` t LEFT JOIN `my_com_user` t1 ON (t1.`com_id` = t.`com_id` AND t1.`user_id` = t.`user_id`) WHERE t.`com_id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void relationTest0_6() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


       easyEntityQuery.queryable(MySignUp.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(m -> {
                    m.comId().eq("123");
                }).orderBy(m -> m.comUser().id().asc()).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`com_id`,t.`user_id`,t.`time`,t.`content` FROM `my_sign_up` t LEFT JOIN `my_com_user` t1 ON (t1.`com_id` = t.`com_id` AND t1.`user_id` = t.`user_id`) WHERE t.`com_id` = ? ORDER BY t1.`id` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void relationTest0_7() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


       easyEntityQuery.queryable(MySignUp.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(m -> {
                    m.comId().eq("123");
                }).orderBy(m -> m.expression().sql("{0} ASC",c->c.expression( m.comUser().id()))).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`com_id`,t.`user_id`,t.`time`,t.`content` FROM `my_sign_up` t LEFT JOIN `my_com_user` t1 ON (t1.`com_id` = t.`com_id` AND t1.`user_id` = t.`user_id`) WHERE t.`com_id` = ? ORDER BY t1.`id` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void relationTest0_8() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<Draft1<Object>> list = easyEntityQuery.queryable(MySignUp.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(m -> {
                    m.comId().eq("123");
                }).select(m -> Select.DRAFT.of(
                        m.expression().sqlSegment("{0}", c -> c.expression(m.comUser().comId()))
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`com_id` AS `value1` FROM `my_sign_up` t LEFT JOIN `my_com_user` t1 ON (t1.`com_id` = t.`com_id` AND t1.`user_id` = t.`user_id`) WHERE t.`com_id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void relationTest1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<MySignUp> list = easyEntityQuery.queryable(MySignUp.class)
                .where(m -> {
                    m.comUser().userId().eq("1234");
                    m.comId().eq("123");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`com_id`,t.`user_id`,t.`time`,t.`content` FROM `my_sign_up` t LEFT JOIN `my_com_user` t1 ON (t1.`com_id` = t.`com_id` AND t1.`user_id` = t.`user_id`) WHERE t1.`user_id` = ? AND t.`com_id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1234(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void relationTest2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Draft2<String, String>> list = easyEntityQuery.queryable(MySignUp.class)
                .where(m -> {
                    m.comId().eq("123");
                }).select(m -> Select.DRAFT.of(
                        m.id(),
                        m.comUser().gw()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id` AS `value1`,t1.`gw` AS `value2` FROM `my_sign_up` t LEFT JOIN `my_com_user` t1 ON (t1.`com_id` = t.`com_id` AND t1.`user_id` = t.`user_id`) WHERE t.`com_id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testRelation() {
        deleteRelation();
        before();
        try {


            {
                System.out.println("1");
                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                List<MyComUserDTO1> list = easyEntityQuery.queryable(MyComUser.class)
                        .selectAutoInclude(MyComUserDTO1.class)
                        .toList();
                for (MyComUserDTO1 myComUserDTO1 : list) {
                    Assert.assertNotNull(myComUserDTO1.getMyUser());
                    if (myComUserDTO1.getUserId().equals("u1") && myComUserDTO1.getComId().equals("c1")) {
                        Assert.assertNotNull(myComUserDTO1.getMySignUps());
                        Assert.assertEquals(2, myComUserDTO1.getMySignUps().size());
                    } else if (myComUserDTO1.getUserId().equals("u3") && myComUserDTO1.getComId().equals("c1")) {
                        Assert.assertNotNull(myComUserDTO1.getMySignUps());
                        Assert.assertEquals(1, myComUserDTO1.getMySignUps().size());
                    } else if (myComUserDTO1.getUserId().equals("u2") && myComUserDTO1.getComId().equals("c2")) {
                        Assert.assertNotNull(myComUserDTO1.getMySignUps());
                        Assert.assertEquals(1, myComUserDTO1.getMySignUps().size());
                    } else {
                        Assert.assertNotNull(myComUserDTO1.getMySignUps());
                        Assert.assertTrue("cu2".equals(myComUserDTO1.getId()) || "cu3".equals(myComUserDTO1.getId()));
                        Assert.assertEquals(0, myComUserDTO1.getMySignUps().size());
                    }
                }

                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(4, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`id`,t.`com_id`,t.`user_id`,t.`gw` FROM `my_com_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t.`id`,t.`com_id`,t.`user_id`,t.`time`,t.`content` FROM `my_sign_up` t WHERE ((t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("c1(String),u1(String),c2(String),u1(String),c1(String),u2(String),c1(String),u3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT t.`id`,t.`name` FROM `my_company_info` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("c1(String),c2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                    Assert.assertEquals("SELECT t.`id`,t.`name`,t.`age` FROM `my_user` t WHERE t.`id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("u1(String),u2(String),u3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }

                System.out.println("11");
            }

            {
                System.out.println("3");
                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                List<MyComUserDTO3> list = easyEntityQuery.queryable(MyComUser.class)
                        .selectAutoInclude(MyComUserDTO3.class)
                        .toList();


                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(4, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`gw`,t.`com_id` AS `__relation__comId`,t.`user_id` AS `__relation__userId` FROM `my_com_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t.`id`,t.`time`,t.`content`,t.`com_id` AS `__relation__comId`,t.`user_id` AS `__relation__userId` FROM `my_sign_up` t WHERE ((t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("c1(String),u1(String),c2(String),u1(String),c1(String),u2(String),c1(String),u3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
                    Assert.assertEquals("SELECT t.`name`,t.`id` AS `__relation__id` FROM `my_company_info` t WHERE t.`id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("c1(String),c2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
                    Assert.assertEquals("SELECT t.`name`,t.`age`,t.`id` AS `__relation__id` FROM `my_user` t WHERE t.`id` IN (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("u1(String),u2(String),u3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }

                System.out.println("33");
            }
            {
                System.out.println("4");
                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                List<MyComUserDTO4> list = easyEntityQuery.queryable(MyComUser.class)
                        .selectAutoInclude(MyComUserDTO4.class)
                        .toList();


                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`gw`,t.`com_id` AS `__relation__comId`,t.`user_id` AS `__relation__userId` FROM `my_com_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t.`id`,t.`time`,t.`content`,t.`com_id` AS `__relation__comId`,t.`user_id` AS `__relation__userId` FROM `my_sign_up` t WHERE ((t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?)) ORDER BY t.`com_id` ASC,CASE WHEN t.`time` IS NULL THEN 1 ELSE 0 END ASC,t.`time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("c1(String),u1(String),c2(String),u1(String),c1(String),u2(String),c1(String),u3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }

                System.out.println("44");
            }
            {
                System.out.println("5");
                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                List<MyComUser1> list = easyEntityQuery.queryable(MyComUser1.class)
                        .includes(s -> s.mySignUps())
                        .toList();


                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT `id`,`com_id`,`user_id`,`gw` FROM `my_com_user`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t.`id`,t.`com_id`,t.`user_id`,t.`time`,t.`content` FROM `my_sign_up` t WHERE ((t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?)) ORDER BY t.`com_id` ASC,CASE WHEN t.`time` IS NULL THEN 0 ELSE 1 END ASC,t.`time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("c1(String),u1(String),c2(String),u1(String),c1(String),u2(String),c1(String),u3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }

                System.out.println("55");
            }
            {
                System.out.println("6");
                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                List<MyComUserDTO4> list = easyEntityQuery.queryable(MyComUser1.class)
                        .selectAutoInclude(MyComUserDTO4.class)
                        .toList();


                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`gw`,t.`com_id` AS `__relation__comId`,t.`user_id` AS `__relation__userId` FROM `my_com_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t.`id`,t.`time`,t.`content`,t.`com_id` AS `__relation__comId`,t.`user_id` AS `__relation__userId` FROM `my_sign_up` t WHERE ((t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?)) ORDER BY t.`com_id` ASC,CASE WHEN t.`time` IS NULL THEN 1 ELSE 0 END ASC,t.`time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("c1(String),u1(String),c2(String),u1(String),c1(String),u2(String),c1(String),u3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }

                System.out.println("66");
            }
            {
                System.out.println("8");
                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                List<MyComUserDTO8> list = easyEntityQuery.queryable(MyComUser1.class)
                        .selectAutoInclude(MyComUserDTO8.class)
                        .toList();


                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`gw`,t.`com_id` AS `__relation__comId`,t.`user_id` AS `__relation__userId` FROM `my_com_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t.`id`,t.`time`,t.`content`,t.`com_id` AS `__relation__comId`,t.`user_id` AS `__relation__userId` FROM `my_sign_up` t WHERE t.`time` = ? AND ((t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?)) ORDER BY t.`time` ASC", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("123123(String),c1(String),u1(String),c2(String),u1(String),c1(String),u2(String),c1(String),u3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }

                System.out.println("66");
            }
            {
                System.out.println("8-1");
                HashMap<String, String> props = new HashMap<>();
                props.put("time", "123123");
                props.put("id", "456");
                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                List<MyComUserDTO9> list = easyEntityQuery.queryable(MyComUser1.class)
                        .configure(o->{
                            o.setConfigureArgument(props);
                        })
                        .selectAutoInclude(MyComUserDTO9.class)
                        .toList();


                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`gw`,t.`com_id` AS `__relation__comId`,t.`user_id` AS `__relation__userId` FROM `my_com_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t.`id`,t.`time`,t.`content`,t.`com_id` AS `__relation__comId`,t.`user_id` AS `__relation__userId` FROM `my_sign_up` t WHERE t.`time` = ? AND t.`id` = ? AND ((t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?))", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("123123(String),456(String),c1(String),u1(String),c2(String),u1(String),c1(String),u2(String),c1(String),u3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }

                System.out.println("66");
            }
            {
                System.out.println("7");
                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                List<MyComUserDTO5> list = easyEntityQuery.queryable(MyComUser1.class)
                        .selectAutoInclude(MyComUserDTO5.class)
                        .toList();


                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`gw`,t.`com_id` AS `__relation__comId`,t.`user_id` AS `__relation__userId` FROM `my_com_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t.`id`,t.`time`,t.`content`,t.`com_id` AS `__relation__comId`,t.`user_id` AS `__relation__userId` FROM `my_sign_up` t WHERE ((t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?) OR (t.`com_id` =? AND t.`user_id` =?)) ORDER BY t.`com_id` ASC,CASE WHEN t.`time` IS NULL THEN 0 ELSE 1 END ASC,t.`time` DESC", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("c1(String),u1(String),c2(String),u1(String),c1(String),u2(String),c1(String),u3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }

                System.out.println("77");
            }
            {
                System.out.println("8");
                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                List<MyComUserDTO6> list = easyEntityQuery.queryable(MyComUser1.class)
                        .selectAutoInclude(MyComUserDTO6.class)
                        .toList();
                for (MyComUserDTO6 myComUserDTO6 : list) {
                    if ("JAVA".equals(myComUserDTO6.getGw())) {
                        Assert.assertEquals(2, myComUserDTO6.getMySignUps().size());
                        Assert.assertEquals("s2", myComUserDTO6.getMySignUps().get(0).getId());
                        Assert.assertEquals("s1", myComUserDTO6.getMySignUps().get(1).getId());
                    }
                    if ("测试".equals(myComUserDTO6.getGw())) {
                        Assert.assertEquals(0, myComUserDTO6.getMySignUps().size());
                    }
                    if ("VUE".equals(myComUserDTO6.getGw())) {
                        Assert.assertEquals(0, myComUserDTO6.getMySignUps().size());
                    }
                    if ("C#".equals(myComUserDTO6.getGw())) {
                        Assert.assertEquals(1, myComUserDTO6.getMySignUps().size());
                        Assert.assertEquals("s3", myComUserDTO6.getMySignUps().get(0).getId());
                    }
                }

                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`gw`,t.`com_id` AS `__relation__comId`,t.`user_id` AS `__relation__userId` FROM `my_com_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t1.`id`,t1.`time`,t1.`content`,t1.`com_id` AS `__relation__comId`,t1.`user_id` AS `__relation__userId` FROM ( (SELECT t.`id`,t.`com_id`,t.`user_id`,t.`time`,t.`content` FROM `my_sign_up` t WHERE ((t.`com_id` =? AND t.`user_id` =?)) ORDER BY t.`com_id` ASC,CASE WHEN t.`time` IS NULL THEN 0 ELSE 1 END ASC,t.`time` DESC LIMIT 2)  UNION ALL  (SELECT t.`id`,t.`com_id`,t.`user_id`,t.`time`,t.`content` FROM `my_sign_up` t WHERE ((t.`com_id` =? AND t.`user_id` =?)) ORDER BY t.`com_id` ASC,CASE WHEN t.`time` IS NULL THEN 0 ELSE 1 END ASC,t.`time` DESC LIMIT 2)  UNION ALL  (SELECT t.`id`,t.`com_id`,t.`user_id`,t.`time`,t.`content` FROM `my_sign_up` t WHERE ((t.`com_id` =? AND t.`user_id` =?)) ORDER BY t.`com_id` ASC,CASE WHEN t.`time` IS NULL THEN 0 ELSE 1 END ASC,t.`time` DESC LIMIT 2)  UNION ALL  (SELECT t.`id`,t.`com_id`,t.`user_id`,t.`time`,t.`content` FROM `my_sign_up` t WHERE ((t.`com_id` =? AND t.`user_id` =?)) ORDER BY t.`com_id` ASC,CASE WHEN t.`time` IS NULL THEN 0 ELSE 1 END ASC,t.`time` DESC LIMIT 2) ) t1", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("c1(String),u1(String),c2(String),u1(String),c1(String),u2(String),c1(String),u3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }

                System.out.println("88");
            }
            {
                System.out.println("9");
                ListenerContext listenerContext = new ListenerContext(true);
                listenerContextManager.startListen(listenerContext);
                List<MyComUserDTO7> list = easyEntityQuery.queryable(MyComUser1.class)
                        .selectAutoInclude(MyComUserDTO7.class)
                        .toList();
                for (MyComUserDTO7 myComUserDTO6 : list) {
                    if ("JAVA".equals(myComUserDTO6.getGw())) {
                        Assert.assertEquals(1, myComUserDTO6.getMySignUps().size());
                        Assert.assertEquals("s1", myComUserDTO6.getMySignUps().get(0).getId());
                    }
                    if ("测试".equals(myComUserDTO6.getGw())) {
                        Assert.assertEquals(0, myComUserDTO6.getMySignUps().size());
                    }
                    if ("VUE".equals(myComUserDTO6.getGw())) {
                        Assert.assertEquals(0, myComUserDTO6.getMySignUps().size());
                    }
                    if ("C#".equals(myComUserDTO6.getGw())) {
                        Assert.assertEquals(0, myComUserDTO6.getMySignUps().size());
                    }
                }

                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
                Assert.assertEquals(2, listenerContext.getJdbcExecuteAfterArgs().size());

                {

                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
                    Assert.assertEquals("SELECT t.`gw`,t.`com_id` AS `__relation__comId`,t.`user_id` AS `__relation__userId` FROM `my_com_user` t", jdbcExecuteAfterArg.getBeforeArg().getSql());
//                    Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }
                {
                    JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
                    Assert.assertEquals("SELECT t1.`id`,t1.`time`,t1.`content`,t1.`com_id` AS `__relation__comId`,t1.`user_id` AS `__relation__userId` FROM ( (SELECT t.`id`,t.`com_id`,t.`user_id`,t.`time`,t.`content` FROM `my_sign_up` t WHERE ((t.`com_id` =? AND t.`user_id` =?)) ORDER BY t.`com_id` ASC,CASE WHEN t.`time` IS NULL THEN 0 ELSE 1 END ASC,t.`time` DESC LIMIT 2 OFFSET 1)  UNION ALL  (SELECT t.`id`,t.`com_id`,t.`user_id`,t.`time`,t.`content` FROM `my_sign_up` t WHERE ((t.`com_id` =? AND t.`user_id` =?)) ORDER BY t.`com_id` ASC,CASE WHEN t.`time` IS NULL THEN 0 ELSE 1 END ASC,t.`time` DESC LIMIT 2 OFFSET 1)  UNION ALL  (SELECT t.`id`,t.`com_id`,t.`user_id`,t.`time`,t.`content` FROM `my_sign_up` t WHERE ((t.`com_id` =? AND t.`user_id` =?)) ORDER BY t.`com_id` ASC,CASE WHEN t.`time` IS NULL THEN 0 ELSE 1 END ASC,t.`time` DESC LIMIT 2 OFFSET 1)  UNION ALL  (SELECT t.`id`,t.`com_id`,t.`user_id`,t.`time`,t.`content` FROM `my_sign_up` t WHERE ((t.`com_id` =? AND t.`user_id` =?)) ORDER BY t.`com_id` ASC,CASE WHEN t.`time` IS NULL THEN 0 ELSE 1 END ASC,t.`time` DESC LIMIT 2 OFFSET 1) ) t1", jdbcExecuteAfterArg.getBeforeArg().getSql());
                    Assert.assertEquals("c1(String),u1(String),c2(String),u1(String),c1(String),u2(String),c1(String),u3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
                }

                System.out.println("99");
            }


        } finally {
            deleteRelation();
        }
    }

    private void deleteRelation() {

        easyEntityQuery.deletable(MyUser.class).disableLogicDelete().allowDeleteStatement(true).where(m -> m.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(MyCompany.class).disableLogicDelete().allowDeleteStatement(true).where(m -> m.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(MyComUser.class).disableLogicDelete().allowDeleteStatement(true).where(m -> m.id().isNotNull()).executeRows();
        easyEntityQuery.deletable(MySignUp.class).disableLogicDelete().allowDeleteStatement(true).where(m -> m.id().isNotNull()).executeRows();
    }

    private void before() {
        List<MyUser> users = new ArrayList<>();
        List<MyCompany> companies = new ArrayList<>();
        List<MyComUser> comUsers = new ArrayList<>();
        List<MySignUp> signUps = new ArrayList<>();
        {
            MyUser myUser = new MyUser();
            myUser.setId("u1");
            myUser.setName("小明");
            myUser.setAge(12);
            users.add(myUser);
        }
        {
            MyUser myUser = new MyUser();
            myUser.setId("u2");
            myUser.setName("小红");
            myUser.setAge(12);
            users.add(myUser);
        }
        {
            MyUser myUser = new MyUser();
            myUser.setId("u3");
            myUser.setName("小蓝");
            myUser.setAge(12);
            users.add(myUser);
        }
        {
            MyCompany myCompany = new MyCompany();
            myCompany.setId("c1");
            myCompany.setName("公司1");
            companies.add(myCompany);
        }
        {
            MyCompany myCompany = new MyCompany();
            myCompany.setId("c2");
            myCompany.setName("公司2");
            companies.add(myCompany);
        }
        {
            MyComUser myComUser = new MyComUser();
            myComUser.setId("cu1");
            myComUser.setComId("c1");
            myComUser.setUserId("u1");
            myComUser.setGw("JAVA");
            comUsers.add(myComUser);
        }
        {
            MyComUser myComUser = new MyComUser();
            myComUser.setId("cu2");
            myComUser.setComId("c2");
            myComUser.setUserId("u1");
            myComUser.setGw("测试");
            comUsers.add(myComUser);
        }
        {
            MyComUser myComUser = new MyComUser();
            myComUser.setId("cu3");
            myComUser.setComId("c1");
            myComUser.setUserId("u2");
            myComUser.setGw("VUE");
            comUsers.add(myComUser);
        }
        {
            MyComUser myComUser = new MyComUser();
            myComUser.setId("cu4");
            myComUser.setComId("c1");
            myComUser.setUserId("u3");
            myComUser.setGw("C#");
            comUsers.add(myComUser);
        }
        {
            MySignUp mySignUp = new MySignUp();
            mySignUp.setId("s1");
            mySignUp.setComId("c1");
            mySignUp.setUserId("u1");
            mySignUp.setTime(LocalDateTime.of(2024, 1, 1, 0, 0));
            mySignUp.setContent("写了一些代码1");
            signUps.add(mySignUp);
        }
        {
            MySignUp mySignUp = new MySignUp();
            mySignUp.setId("s2");
            mySignUp.setComId("c1");
            mySignUp.setUserId("u1");
            mySignUp.setTime(LocalDateTime.of(2024, 2, 1, 0, 0));
            mySignUp.setContent("写了一些代码2");
            signUps.add(mySignUp);
        }
        {
            MySignUp mySignUp = new MySignUp();
            mySignUp.setId("s3");
            mySignUp.setComId("c1");
            mySignUp.setUserId("u3");
            mySignUp.setTime(LocalDateTime.of(2024, 1, 1, 0, 0));
            mySignUp.setContent("写了一些代码3");
            signUps.add(mySignUp);
        }
        {
            MySignUp mySignUp = new MySignUp();
            mySignUp.setId("s4");
            mySignUp.setComId("c2");
            mySignUp.setUserId("u2");
            mySignUp.setTime(LocalDateTime.of(2024, 1, 1, 0, 0));
            mySignUp.setContent("写了一些代码5");
            signUps.add(mySignUp);
        }

        easyEntityQuery.insertable(users).executeRows();
        easyEntityQuery.insertable(companies).executeRows();
        easyEntityQuery.insertable(comUsers).executeRows();
        easyEntityQuery.insertable(signUps).executeRows();
    }


    @Test
    public void relation10(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft1<Object>> list = easyEntityQuery.queryable(SchoolClassAggregate.class)
                .where(s -> {
                    s.name().like("123");
                }).select(s -> Select.DRAFT.of(
                        s.expression().sqlSegment("{0}", c -> c.expression(s.studentSize()))
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (SELECT COUNT(t1.`id`) AS `id` FROM `school_student` t1 WHERE t1.`class_id` = t.`id`) AS `value1` FROM `school_class` t WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void relation11(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Draft1<Long>> list = easyEntityQuery.queryable(SchoolClassAggregate.class)
                .where(s -> {
                    s.name().like("123");
                }).select(s -> Select.DRAFT.of(
                        s.studentSize()
                )).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT (SELECT COUNT(t1.`id`) AS `id` FROM `school_student` t1 WHERE t1.`class_id` = t.`id`) AS `value1` FROM `school_class` t WHERE t.`name` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("%123%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
}
